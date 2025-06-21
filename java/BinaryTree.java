package Placementprep.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;




public class BinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Node{
        int data;
        Node left;
        Node right;
        Node(int data){
            this.data = data;
            left=null;
            right=null;
        }
    }

    



    // *** TOPIC : BFS/DFS traversal of Tree


    // *** SUB-TOPIC-1 : Traversals of Binary Tree

    // ?? NOTE  -> All three – pre-order, in-order, and post-order – are DFS-based traversal strategies. We need either 
    // !!         recursion or stack to perform it    
    // ** preorder
    public void preOrderHelper(TreeNode root,List<Integer> ans){
        if(root==null) return;
        ans.add(root.val);
        preOrderHelper(root.left,ans);
        preOrderHelper(root.right,ans);
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        preOrderHelper(root,ans);
        return ans;
    }

    // ** postorder
    void postorderTraversalHelper(TreeNode root,List<Integer> ans){
        if(root==null) return;
        postorderTraversalHelper(root.left,ans);
        postorderTraversalHelper(root.right,ans);
        ans.add(root.val);
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        postorderTraversalHelper(root,ans);
        return ans;
    }

    // ** inorder
    void inorderTraversalHelper(TreeNode root,List<Integer> ans){
        if(root==null) return;
        inorderTraversalHelper(root.left,ans);
        ans.add(root.val);
        inorderTraversalHelper(root.right,ans);
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        inorderTraversalHelper(root,ans);
        return ans;
    }


    // ** level order traversal (BFS TRAVERSAL of TREE)
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        // check if root is null
        if(root == null) return res;
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> levlList = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
                levlList.add(node.val);
            }
            res.add(levlList);
        }
        return res;
    }
    


    // *** SUB-TOPIC-2 : Modified Traversals of Binary Tree

    // ** boundary of a binary tree
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
    private void addLeftBoundary(TreeNode node, List<Integer> res) {
        while (node != null) {
            if (!isLeaf(node)) res.add(node.val);
            if (node.left != null) node = node.left;
            else node = node.right;
        }
    }
    private void addRightBoundary(TreeNode node, List<Integer> res) {
        Stack<Integer> stack = new Stack<>();
        while (node != null) {
            if (!isLeaf(node)) stack.push(node.val);
            if (node.right != null) node = node.right;
            else node = node.left;
        }
        while (!stack.isEmpty()) res.add(stack.pop()); // bottom-up
    }
    private void addLeaves(TreeNode node, List<Integer> res) {
        if (node == null) return;
        if (isLeaf(node)) {
            res.add(node.val);
            return;
        }
        addLeaves(node.left, res);
        addLeaves(node.right, res);
    }
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        if (!isLeaf(root)) result.add(root.val);

        addLeftBoundary(root.left, result);
        addLeaves(root, result);
        addRightBoundary(root.right, result);

        return result;
    }


    // ** vertical order traversal (DFS also possible for this)
    // * simple vertical order traversal using BFS
    class Pair{
        int hd;
        TreeNode node;
        Pair(int hd,TreeNode node){
            this.hd=hd;
            this.node=node;
        }
    }
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Map<Integer,List<Integer>> mp = new TreeMap<>();
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0,root));
        while(!q.isEmpty()){
            Pair p = q.poll();
            if(p.node.left!=null) q.add(new Pair(p.hd-1,p.node.left));
            if(p.node.right!=null) q.add(new Pair(p.hd+1,p.node.right));
            mp.putIfAbsent(p.hd,new ArrayList<>());
            mp.get(p.hd).add(p.node.val);
        }

        ans.addAll(mp.values());
        return ans;
    }
    // * leetcode question (987) has little tweak
    class Pair2{
        int hd;
        int vd;
        TreeNode node;
        Pair2(int hd,int vd,TreeNode node){
            this.hd=hd;
            this.vd=vd;
            this.node=node;
        }
    }
    public List<List<Integer>> verticalTraversal2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Map<Integer,Map<Integer,List<Integer>>> mp = new TreeMap<>();
        Queue<Pair2> q = new LinkedList<>();
        q.add(new Pair2(0,0,root));
        while(!q.isEmpty()){
            Pair2 p = q.poll();
            if(p.node.left!=null) q.add(new Pair2(p.hd-1,p.vd+1,p.node.left));
            if(p.node.right!=null) q.add(new Pair2(p.hd+1,p.vd+1,p.node.right));
            mp.putIfAbsent(p.hd,new TreeMap<>());
            mp.get(p.hd).putIfAbsent(p.vd,new ArrayList<>());
            mp.get(p.hd).get(p.vd).add(p.node.val);
        }

        for(var it:mp.values()){
            // horizontal distance array
            ArrayList<Integer> temp = new ArrayList<>();
            for(var c:it.values()){
                // vertical distance array sorting
                Collections.sort(c);
                temp.addAll(c);
            }
            ans.add(temp);
        }

        return ans;
    }

    // ** zig zag order traversal
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root==null) return ans;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean flip = false;

        while(!q.isEmpty()){
            int size = q.size();
            List<Integer> temp = new ArrayList<>();
            for(int i=0; i<size; i++){
                TreeNode n = q.poll();
                temp.add(n.val);

                if(n.left!=null) q.add(n.left);
                if(n.right!=null) q.add(n.right);
            }
            if(flip) Collections.reverse(temp);
            flip=!flip;
            ans.add(temp);
        }

        return ans;
    }

    // ** diagonal traversal of binary tree

    // ** SUB TOPIC :: Morris Traversal (In-order/Pre-order without recursion/stack)
    // ** Morris Traversal is an optimized, space-efficient version of DFS (specifically in-order or pre-order)

    // ** Binary Tree Inorder Traversal
    public List<Integer> inorderMorrisTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root==null) return ans;
        TreeNode node = root;
        while(node!=null){
            if(node.left==null){
                ans.add(node.val);
                node = node.right;
            }else{
                TreeNode temp = node.left;
                while(temp.right!=null && temp.right!=node) temp=temp.right;
                if(temp.right==node){
                    temp.right=null;
                    // visit node as left is done
                    ans.add(node.val);
                    node=node.right;
                }else{
                    temp.right=node;
                    node=node.left;
                }
            }
        }
        return ans;
    }

    // ** Binary Tree Preorder Traversal
    public List<Integer> preorderMorrisTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode node = root;
        while(node!=null){
            
            if(node.left==null){
                ans.add(node.val);
                node=node.right;
            }else{
                TreeNode temp = node.left;
                while(temp.right!=null && temp.right!=node) temp=temp.right;
                if(temp.right==node){
                    // node is already visited
                    temp.right=null;
                    node=node.right;
                }else{
                    // visit node before going to left
                    ans.add(node.val);
                    temp.right=node;
                    node=node.left;
                }
            }
        }
        return ans;
    }

    // ** Flatten Binary Tree to Linked List
    public void flatten(TreeNode root) {
        TreeNode node = root;
        while(node!=null){
            if(node.left==null){
                node=node.right;
            }else{
                TreeNode temp = node.left;
                while(temp.right!=null && temp.right!=node) temp=temp.right;
                temp.right=node.right;
                node.right=node.left;
                node.left=null;
            }
        } 
    }

    // ** Recover Binary Search Tree

    // ** Convert BST to Greater Tree



    // *** SUB-TOPIC-3 : View(Left/Right/Top/Bottom) of Binary Tree
    // * Node -> Although it can be done by both BFS & DFS but BFS is always easier and intutive
    
    // ** right view
    // ** DFS 
    public void rightView(TreeNode root, List<Integer> result, int level) {
        if (root == null) return;

        // If this is the first node at this level, add it to the result
        if (level == result.size()) {
            result.add(root.val);
        }

        // Traverse the right subtree first to ensure rightmost nodes are considered first
        rightView(root.right, result, level + 1);
        rightView(root.left, result, level + 1);
    }
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        rightView(root, result, 0);
        return result;
    }
    // ** BFS
    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root==null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            int n = q.size();
            for(int i=0; i<n; i++){
                TreeNode node = q.poll();
                if(i==n-1){
                    result.add(node.val);
                }

                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
        }
        return result;
    }
    // ** left view
    // BFS
    ArrayList<Integer> leftView(Node root) {
        ArrayList<Integer> result = new ArrayList<>();
        if(root==null) return result;
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            int n = q.size();
            for(int i=0; i<n; i++){
                Node node = q.poll();
                if(i==0){
                    result.add(node.data);
                }

                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
        }
        return result;
    }
    // DFS is also similar , just Traverse the left subtree first to ensure rightmost nodes are considered first
    List<Integer> leftView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsLeft(root, 0, result);
        return result;
    }
    private void dfsLeft(TreeNode node, int level, List<Integer> result) {
        if (node == null) return;

        if (level == result.size()) {
            result.add(node.val); // first node at this level
        }

        // traverse left first
        dfsLeft(node.left, level + 1, result);
        dfsLeft(node.right, level + 1, result);
    }

    // ** top view of binary tree
    // BFS 
    class Pair3 {
        TreeNode node;
        int hd;
        Pair3(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }
    public List<Integer> topView(TreeNode root) {
        Map<Integer, Integer> topViewMap = new TreeMap<>();
        Queue<Pair3> queue = new LinkedList<>();

        queue.add(new Pair3(root, 0));

        while (!queue.isEmpty()) {
            Pair3 current = queue.poll();
            TreeNode node = current.node;
            int hd = current.hd;

            if (!topViewMap.containsKey(hd)) {
                topViewMap.put(hd, node.val);
            }

            if (node.left != null)
                queue.add(new Pair3(node.left, hd - 1));
            if (node.right != null)
                queue.add(new Pair3(node.right, hd + 1));
        }

        return new ArrayList<>(topViewMap.values());
    }
    // DFS
    static void topViewHelper(Node root,int col,int row,Map<Integer,List<Integer>> mp){
        if(root==null) return;
        if(!mp.containsKey(col) || mp.get(col).get(0)>row){
            mp.put(col,List.of(row,root.data));
        }
        
        topViewHelper(root.left,col-1,row+1,mp);
        topViewHelper(root.right,col+1,row+1,mp);
    }
    static ArrayList<Integer> topView(Node root) {
        Map<Integer,List<Integer>> mp = new TreeMap<>();
        topViewHelper(root,0,0,mp);
        ArrayList<Integer> ans = new ArrayList<>();
        for(var entry:mp.entrySet()){
            ans.add(entry.getValue().get(1));
        }
        return ans;
    }

    // ** bottom view of binary tree
    // BFS
    public List<Integer> bottomView(TreeNode root) {
        Map<Integer, Integer> bottomViewMap = new TreeMap<>();
        Queue<Pair3> queue = new LinkedList<>();

        queue.add(new Pair3(root, 0));

        while (!queue.isEmpty()) {
            Pair3 current = queue.poll();
            TreeNode node = current.node;
            int hd = current.hd;

            // Overwrite: we want the bottommost (last seen in BFS)
            bottomViewMap.put(hd, node.val);

            if (node.left != null)
                queue.add(new Pair3(node.left, hd - 1));
            if (node.right != null)
                queue.add(new Pair3(node.right, hd + 1));
        }

        return new ArrayList<>(bottomViewMap.values());
    }
    // DFS
    static void bottomViewHelper(Node root,int col,int row,Map<Integer,List<Integer>> mp){
        if(root==null) return;
        if(!mp.containsKey(col) || mp.get(col).get(0)<=row){
            mp.put(col,List.of(row,root.data));
        }
        
        topViewHelper(root.left,col-1,row+1,mp);
        topViewHelper(root.right,col+1,row+1,mp);
    }
    public ArrayList <Integer> bottomView(Node root)
    {
        Map<Integer,List<Integer>> mp = new TreeMap<>();
        bottomViewHelper(root,0,0,mp);
        ArrayList<Integer> ans = new ArrayList<>();
        for(var entry:mp.entrySet()){
            ans.add(entry.getValue().get(1));
        }
        return ans;
    }

    
    



    // *** TOPIC :  Property-Based Questions

    // ** min depth of binary tree
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        // even if any of the child exist, it is not leaf so get answer from that direction
        if(root.left==null && root.right!=null) return 1+minDepth(root.right);
        if(root.right==null && root.left!=null) return 1+minDepth(root.left);

        // only possible cases , 1. both are null , 2. both are not null
        // if both are null, answer is just 1
        // if both are not null, answer is 1 + minimum of left and right
        return 1 + Math.min(minDepth(root.left),minDepth(root.right));
    }

    // ** diameter of binary tree
    // Node -> No +1 in diameter as we have to calculate the number of edges and not nodes
    public int height(TreeNode root){
        if(root==null) return 0;
        return 1+Math.max(height(root.left),height(root.right));
    }
    public int diameterOfBinaryTree(TreeNode root) {
        if(root==null) return 0;
        int diameterPasingThroughRoot = height(root.left) + height(root.right);
        int diameterInLeftSubtree = diameterOfBinaryTree(root.left);
        int diameterInRightSubtree = diameterOfBinaryTree(root.right);
        return Math.max(diameterPasingThroughRoot, Math.max(diameterInLeftSubtree,diameterInRightSubtree));
    }
    //optimised approach
    // ** Important Concept :: Send multiple (usually 2) values from one function to avoid 2 passes 
    class Pair7{
        int height;
        int diameter;
        Pair7(int height,int diameter){
            this.height=height;
            this.diameter=diameter;
        }
    }
    public Pair7 height2(TreeNode root){
        if(root==null) return new Pair7(0,0);
        Pair7 left = height2(root.left);
        Pair7 right = height2(root.right);
        int newHeight = 1 + Math.max(left.height,right.height);
        int newDiameter = Math.max(left.height+right.height,Math.max(left.diameter,right.diameter));
        return new Pair7(newHeight,newDiameter);
    }
    public int diameterOfBinaryTree2(TreeNode root) {
        return height2(root).diameter;
    }

    // ** is balanced binary tree
    public int height4(TreeNode root){
        if(root==null) return 0;
        return 1+Math.max(height4(root.left),height4(root.right));
    }
    public boolean isBalanced4(TreeNode root) {
        if(root==null) return true;
        return Math.abs(height4(root.left)-height4(root.right))<=1 && isBalanced4(root.left) && isBalanced4(root.right);
    }
    //optimised
    class Pair5{
        int height;
        boolean isBalanced;
        Pair5(int height,boolean isBalanced){
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }
    public Pair5 height3(TreeNode root){
        if(root==null) return new Pair5(0,true);

        Pair5 left = height3(root.left);
        Pair5 right = height3(root.right);

        int newHeight = 1 + Math.max(left.height,right.height);
        boolean isBalanced = Math.abs(left.height-right.height)<=1 && left.isBalanced && right.isBalanced;

        return new Pair5(newHeight,isBalanced);
    }
    public boolean isBalanced(TreeNode root) {
        return height3(root).isBalanced;
    }

    // ** is trees identical
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        if(p==null || q==null) return false;
        return p.val==q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

    // ** is sum tree
    class Pair4{
        int sum;
        boolean isSumTree;
        Pair4(int sum,boolean isSumTree){
            this.sum = sum;
            this.isSumTree= isSumTree;
        }
    }
    Pair4 sum(Node root){
        if(root==null) return new Pair4(0,true);
        if(root.left==null && root.right==null) return new Pair4(root.data,true);
        Pair4 left = sum(root.left);
        Pair4 right = sum(root.right);
        
        int newSum = left.sum+right.sum+root.data;
        boolean isSumtreeNew = (root.data==left.sum+right.sum) && left.isSumTree && right.isSumTree;
        
        return new Pair4(newSum,isSumtreeNew);
    }
    boolean isSumTree(Node root) {
        return sum(root).isSumTree;
    }


    //  ** Check Completeness of a Binary Tree
    // Complete binary tree Definition : Every level of the tree, except possibly the last, is completely filled, and all nodes are as far left as possible.
    // ** Approach 1 (using BFS): do a level order traversal and if you find any null node then after that no more non-null node should be there
    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        boolean isNullFound = false;

        while(!q.isEmpty()){
            TreeNode node = q.poll();
            if(node!=null && isNullFound) return false;
            if(node==null) isNullFound=true;

            if(node!=null) q.add(node.left);
            if(node!=null) q.add(node.right);
        }
        return true;
    }
    // ** Approach 2 (using DFS) : give index to each node and then check if any node got index more than the (number of nodes-1)
    int n = 0;
    public boolean giveIndex(TreeNode root,int idx){
        if(root==null) return true;
        if(idx>=n) return false;
        return giveIndex(root.left,2*idx+1) && giveIndex(root.right,2*idx+2);
    }
    public int numberOfNode(TreeNode root){
        if(root==null) return 0;
        return 1+numberOfNode(root.left)+numberOfNode(root.right);
    }
    public boolean isCompleteTree2(TreeNode root) {
        n = numberOfNode(root);
        return giveIndex(root,0);
    }


    // ** Maximum Path Sum in Binary Tree
    int ans3;
    public int solve2(TreeNode root){
        if(root==null) return 0;

        int left = Math.max(0,solve2(root.left));
        int right = Math.max(0,solve2(root.right));

        ans3 = Math.max(ans3, left+right+root.val);
        return Math.max(left,right)+root.val;
    }
    public int maxPathSum(TreeNode root) {
        ans3 = Integer.MIN_VALUE;
        solve2(root);
        return ans3;
    }

    // ** Check if Tree is Symmetric (Mirror)
    boolean mirrotImage(TreeNode p,TreeNode q){
        if(p==null && q==null) return true;
        if(p==null || q==null) return false;
        return p.val==q.val && mirrotImage(p.left,q.right) && mirrotImage(p.right,q.left);
    }
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return mirrotImage(root.left,root.right);
    }

    // ** Subtree of Another Tree
    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        if(p==null || q==null) return false;
        return p.val==q.val && isSameTree2(p.left,q.left) && isSameTree2(p.right,q.right);
    }
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root==null && subRoot==null) return true;
        if(root==null || subRoot==null) return false;
        return isSameTree2(root,subRoot) || isSubtree(root.left,subRoot) || isSubtree(root.right,subRoot);
    }





    // *** TOPIC :: Ancestor problem 

    // ** lowest common ancestor
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || root==p || root==q) return root;
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);

        if(left!=null && right!=null) return root;
        return left==null ? right : left;
    }

    // ** Maximum Difference Between Node and Ancestor
    int ans;
    void solve(TreeNode root, int mn, int mx){
        if(root==null){
            ans = Math.max(ans, Math.abs(mx-mn));
            return;
        }
        mn = Math.min(mn,root.val);
        mx = Math.max(mx,root.val);
        solve(root.left,mn,mx);
        solve(root.right,mn,mx);
    }
    public int maxAncestorDiff(TreeNode root) {
        solve(root,Integer.MAX_VALUE,Integer.MIN_VALUE);
        return ans;
    }

    // ** Lowest Common Ancestor of Deepest Leaves
    class Pair6{
        int depth;
        TreeNode lca;

        Pair6(int depth,TreeNode lca){
            this.depth=depth;
            this.lca=lca;
        }
    }
    public Pair6 solve(TreeNode root){
        if(root==null) return new Pair6(0,null);
        Pair6 left = solve(root.left);
        Pair6 right = solve(root.right);

        if(left.depth==right.depth){
            return new Pair6(left.depth+1,root);
        }else if(left.depth>right.depth){
            return new Pair6(left.depth+1,left.lca);
        }else return new Pair6(right.depth+1,right.lca);
    }
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if(root==null || (root.left==null && root.right==null)) return root;
        return solve(root).lca;
    }

    // ** All Ancestors of a Node
    ArrayList<Integer> ans2;
    public boolean solve(Node root,int target){
        if(root==null) return false;
        if(root.data==target) return true;
        boolean left = solve(root.left,target);
        boolean right = solve(root.right,target);
        if(left==false && right==false) return false;
        
        ans2.add(root.data);
        return true;
    
    }
    public ArrayList<Integer> Ancestors(Node root, int target) {
        ans2 = new ArrayList<>();
        solve(root,target);
        return ans2;
    }

    // ** All Nodes Distance K in Binary Tree
    void createAdjacencyList(TreeNode root,HashMap<Integer, ArrayList<Integer>> adj){
        if(root==null) return;
        if(root.left!=null){
            adj.putIfAbsent(root.left.val,new ArrayList<>());
            adj.get(root.left.val).add(root.val);
            adj.putIfAbsent(root.val,new ArrayList<>());
            adj.get(root.val).add(root.left.val);
        }
        if(root.right!=null){
            adj.putIfAbsent(root.right.val,new ArrayList<>());
            adj.get(root.right.val).add(root.val);
            adj.putIfAbsent(root.val,new ArrayList<>());
            adj.get(root.val).add(root.right.val);
        }
        createAdjacencyList(root.left,adj);
        createAdjacencyList(root.right,adj);
    }
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        HashMap<Integer, ArrayList<Integer>> adj = new HashMap<>();
        createAdjacencyList(root,adj);
        ArrayList<Integer> ans = new ArrayList<>();
        HashMap<Integer,Integer> distance = new HashMap<>();
        HashMap<Integer,Boolean> visited = new HashMap<>();
        distance.put(target.val,0);
        Queue<Integer> q = new LinkedList<>();
        q.add(target.val);
        visited.put(target.val,true);

        while(!q.isEmpty()){
            int front = q.poll();
            for(int i:adj.getOrDefault(front,new ArrayList<>())){
                if(!visited.getOrDefault(i,false)){
                    visited.put(i,true);
                    q.add(i);
                    distance.put(i,1+distance.get(front));
                }
            }
        }

        for(var entry:distance.entrySet()){
            if(entry.getValue()==k){
                ans.add(entry.getKey());
            }
        }
        return ans;
    }

   
   


    // *** TOPIC :: Construction Problems

    // ** Construct Binary Tree from Preorder and Inorder Traversal
    HashMap<Integer,Integer> mp;
    TreeNode buildTreeHelper(int[] preorder, int[] inorder,int pi,int pj, int ii, int ij){
        if(pi>pj || ii>ij) return null;
        int rootVal = preorder[pi];
        TreeNode root = new TreeNode(rootVal);
        int inorderRootIdx = mp.get(rootVal);
        int leftPartSize = inorderRootIdx-ii;
        root.left = buildTreeHelper(preorder,inorder,pi+1, pi+1+leftPartSize ,ii,inorderRootIdx-1);
        root.right = buildTreeHelper(preorder,inorder,pi+leftPartSize+1, pj, inorderRootIdx+1,ij);
        return root;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        mp = new HashMap<>();
        int n = inorder.length;
        for(int i=0; i<n; i++){
            mp.put(inorder[i],i);
        }
        return buildTreeHelper(preorder,inorder,0,n-1,0,n-1);
    }

    // ** Construct Binary Tree from Inorder and Postorder Traversal
    TreeNode buildTreeHelper2(int[] inorder, int[] postorder,int ii,int ij,int pi,int pj){
        if(ii>ij || pi>pj) return null;
        int rootVal = postorder[pj];
        TreeNode root = new TreeNode(rootVal);
        int inorderRootIdx = mp.get(rootVal);
        int leftPartSize = inorderRootIdx - ii;

        root.left = buildTreeHelper(inorder,postorder,ii, inorderRootIdx-1, pi, pi+leftPartSize-1);
        root.right = buildTreeHelper(inorder,postorder,inorderRootIdx+1 ,ij, pi+leftPartSize, pj-1);
        return root;
    }
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        mp = new HashMap<>();
        int n = inorder.length;
        for(int i=0; i<n; i++){
            mp.put(inorder[i],i);
        }
        return buildTreeHelper2(inorder,postorder,0,n-1,0,n-1);
    }

    // ** Serialize and Deserialize a Binary Tree
    // easy but lenght so just telling intution
    /*
     * 
     * Serialization:
        Get preorder traversal of the tree.

        Get inorder traversal of the tree.

        Convert both to strings, and concatenate them with a delimiter (like 'X').

        Serialized String = preorder_str + "X" + inorder_str
        
     *  Deserialization:
        Split the string using 'X'.

        Convert the strings back to preorder[] and inorder[] arrays.

        Reconstruct the binary tree using the well-known algorithm to build tree from preorder and inorder traversals.
     * 
     */

    // ** Build Tree from Level Order and Inorder (Advanced)





    // *** TOPIC :: Counting / Path-Based Problems

    // ** Count Number of Nodes, TC : O(N), SC : O(logN) == height of Complete binary Tree
    int n2;
    public void countNodesHelper(TreeNode root){
        if(root==null) return;
        n2++;
        countNodesHelper(root.left);
        countNodesHelper(root.right);
    }
    public int countNodes(TreeNode root) {
        n2=0;
        countNodesHelper(root);
        return n2;
    }
    // * Optimized approach,  TC : O(logN^2), SC: O(logN)
    private int getHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }
    public int countNodes2(TreeNode root) {
        if (root == null)
            return 0;

        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        if (leftHeight == rightHeight) {
            // left sub tree is full, therefore total nodes = 2^h(for left nodes including root) + countNodesOfRight
            return (1 << leftHeight) + countNodes2(root.right);
        } else {
            // right sub tree is full, therefore total nodes = 2^h(for right nodes including root) + countNodesOfLeft
            return (1 << rightHeight) + countNodes2(root.left);
        }
    }

    // ** Count Leaf Nodes
    int countLeaves(Node node) {
        if(node==null) return 0;
        if(node.left==null && node.right==null) return 1;
        return countLeaves(node.left)+countLeaves(node.right);
    }

    // ** Path Sum Problems
    // ** Path Sum I
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null && root.right==null) return targetSum==root.val;
        return hasPathSum(root.left,targetSum-root.val) || hasPathSum(root.right,targetSum-root.val);
    }

    // ** Path Sum II
    List<List<Integer>> ans4;
    public void solve(TreeNode root, int targetSum,List<Integer> path){
        if(root==null) return;
        if(root.left==null && root.right==null){
            if(root.val==targetSum){
                path.add(root.val);
                ans4.add(new ArrayList<>(path));
                path.remove(path.size()-1);
            }
            return;
        }
        path.add(root.val);
        solve(root.left,targetSum-root.val,path);
        solve(root.right,targetSum-root.val,path);
        path.remove(path.size()-1);
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ans4 = new ArrayList<>();
        solve(root,targetSum,new ArrayList<>());
        return ans4;
    }

    // ** Path Sum III
    
}
