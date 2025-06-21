package Placementprep.java;

import java.util.ArrayList;

public class BSTClass {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    // Check for BST
    // *** approach 1 : Inorder traversal (First standard way of solving BST problems)
    // Intution (curr element of bst should be greater than prev value)
    long prev;
    public boolean solve(TreeNode root){
        if(root==null) return true;
        boolean left = solve(root.left);

        if(root.val<=prev) return false;
        else prev=root.val;

        boolean right = solve(root.right);
        return left && right;
    }
    public boolean isValidBST(TreeNode root) {
        prev=Long.MIN_VALUE;
        return solve(root);
    }
    // *** approach 2 : Check range of each node.(Second Standard way of solving BST questions)
    public boolean isValid(TreeNode root,long min, long max){
        if(root==null) return true;

        if(!(root.val>min && root.val<max)) return false;

        return isValid(root.left,min,root.val) && isValid(root.right,root.val,max);
    }
    public boolean isValidBST2(TreeNode root) {
        return isValid(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }

    // Minimum Distance Between BST Nodes
    int diff = Integer.MAX_VALUE;
    int prevEle = -100000; // negative of max value of node provided in question
    public void inorder(TreeNode root) {
        if (root == null)
            return;

        inorder(root.left);
        diff = Math.min(diff, root.val - prevEle);
        prevEle = root.val;
        inorder(root.right);
    }
    public int minDiffInBST(TreeNode root) {
        inorder(root);
        return diff;
    }


    // Sum of k smallest elements in BST
    int ans = 0;
    int K=0;
    void inorder(Node root){
        if(root==null) return;
        
        inorder(root.left);
        if(K>0){
            ans += root.data;
            K--;
        }
        
        inorder(root.right);
    }
    int sum(Node root, int k) { 
        K=k;
        inorder(root);
        return ans;
    } 


    // Kth largest element in BST
    // Intution : Reverse the inorder traversal i.e. Right -> node -> left
    void inorder2(Node root){
        if(root==null) return;
        
        inorder(root.right);
        if(K==0){
            ans = root.data;
            K=-1; // to stop overriding the value in upcoming recursion calls
            return;
        }else K--;
        
        inorder(root.left);
    }
    public int kthLargest(Node root, int k) {
        K=k-1;
        inorder2(root);
        return ans;
    }


    // (sorted)Array to BST
    Node helper(int[] nums,int s,int e){
        if(s>e) return null;
        int m = s+(e-s)/2;
        Node n = new Node(nums[m]);
        n.left = helper(nums,s,m-1);
        n.right = helper(nums,m+1,e);
        return n;
    }
    public Node sortedArrayToBST(int[] nums) {
        return helper(nums,0,nums.length-1);
    }

    // Construct Binary Search Tree from Preorder Traversal
    // approach 1 : since preorder is N->L->R , so first element is node and lets say the first element bigger than node have
    // index idx then {0 , idx-1} is left-subtree, {idx,n} is right-sub tree
    // ** TC : O(N^2)
    private TreeNode build(int[] preorder, int start, int end) {
        if (start > end) return null;

        TreeNode root = new TreeNode(preorder[start]);

        // Find the first index greater than root (start+1 to end)
        int index = start + 1;
        while (index <= end && preorder[index] < root.val) {
            index++;
        }

        // Left: start+1 to index-1
        // Right: index to end
        root.left = build(preorder, start + 1, index - 1);
        root.right = build(preorder, index, end);

        return root;
    }
    public TreeNode bstFromPreorder2(int[] preorder) {
        return build(preorder, 0, preorder.length - 1);
    }

    // ** approach 2 (Optimized): Bounded values(Range) concept
    // ** TC : O(N)
    int idx = 0;
    TreeNode bstFromPreorderHelper(int[] preorder,int l,int u){
        if(idx>=preorder.length || preorder[idx]<=l || preorder[idx]>=u) return null;

        TreeNode n = new TreeNode(preorder[idx++]);

        n.left = bstFromPreorderHelper(preorder,l,n.val);
        n.right = bstFromPreorderHelper(preorder,n.val,u);
        return n;
    }
    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorderHelper(preorder,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }


    // Construct BST from Postorder
    // * approach 1 could be same as of above, only diff is node will be at last || TC O(N^2)
    // ** approach 2 : Bounded values(Range) concept
    // ** NOTE : we will traverse array in reverse bcoz we need node first and in postorder node occurs at last
    // **        so order of traversal will N->R->L (bcoz postorder is L->R->N)
    // ** TC : O(N^2)
    static int idx2;
    public static Node constructTreeHelper(int[] postorder,int lb,int ub){
        if(idx2<0 || postorder[idx2]<lb || postorder[idx2]>ub) return null;
        Node root = new Node(postorder[idx2--]);
        root.right = constructTreeHelper(postorder,root.data,ub);
        root.left = constructTreeHelper(postorder,lb,root.data);
        return root;
    }
    public static Node constructTree(int[] postorder, int n) {
        idx2=postorder.length-1;
        return constructTreeHelper(postorder,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }


    // Preorder Traversal and BST (can represent preorder traversal of a possible BST)
    static int idx3 = 0;
    static void bstFromPreorderHelper2(int[] preorder,int l,int u){
        if(idx3>=preorder.length) return;
        if(preorder[idx3]<=l || preorder[idx3]>=u) return;
        int n = preorder[idx3];
        idx3++;
        bstFromPreorderHelper2(preorder,l,n);
        bstFromPreorderHelper2(preorder,n,u);
        
    }
    static int canRepresentBST(int arr[], int N) {
        idx3=0; // reset since it is a static variable
        bstFromPreorderHelper2(arr,Integer.MIN_VALUE,Integer.MAX_VALUE);
        if(idx3==N) return 1;
        return 0;
    }


    // Lowest Common Ancestor in a BST
    Node LCA(Node root, Node n1, Node n2) {
        if(root==null || n1==null || n2==null) return null;
        if(n1.data<root.data && n2.data<root.data) return LCA(root.left,n1,n2);
        if(n1.data>root.data && n2.data>root.data) return LCA(root.right,n1,n2);
        return root;
    }


    // BST Keys in a Range
    // ** NOTE :: Simple inorder will make it O(N) , this approach is making it O(logN)
    static void solve(ArrayList<Integer> ans,Node root,int low,int high){
        if(root==null) return;
        if(root.data>=low && root.data<=high){
            solve(ans,root.left,low,high);
            ans.add(root.data);
            solve(ans,root.right,low,high);
        }else if(root.data<low){
            solve(ans,root.right,low,high);
        }else{
            solve(ans,root.left,low,high);
        }
    }
    public static ArrayList<Integer> printNearNodes(Node root, int low, int high) {
        ArrayList<Integer> ans = new ArrayList<>();
        solve(ans,root,low,high);
        return ans;
    }

    // Range Sun of BST
    public void solve(TreeNode root,int low,int high){
        if(root==null) return;
        if(root.val>=low && root.val<=high){
            solve(root.left,low,high);
            ans += root.val;
            solve(root.right,low,high);
        }else if(root.val<low){
            solve(root.right,low,high);
        }else{
            solve(root.left,low,high);
        }
    }
    public int rangeSumBST(TreeNode root, int low, int high) {
        ans = 0;
        solve(root,low,high);
        return ans;
    }

    // BST with Dead End
    boolean solve(Node root,int low,int high){
        if(root==null) return false;
        if(root.left==null && root.right==null){
            if(root.data-low>1 || high-root.data>1) return false;
            return true;
        }
        return solve(root.left,low,root.data) || solve(root.right,root.data,high);
    }
    public boolean isDeadEnd(Node root) {
        return solve(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    // Find Common Nodes in two BST
    
}
