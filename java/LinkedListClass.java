package Placementprep.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkedListClass {

    // for leetcode
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // for gfg
    public class Node {
        int data;
        Node next;
        Node bottom; // used in one question only
        Node random; // used in one question only

        Node(int d) {
            data = d;
            next = null;
        }
    }

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

    // Delete in a Singly Linked List
    Node deleteNode(Node head, int x) {
        Node prev = null, curr = head;
        x--;
        while (curr != null && x > 0) {
            prev = curr;
            curr = curr.next;
            x--;
        }

        if (prev != null && curr != null)
            prev.next = curr.next;

        // if x==1
        if (prev == null && curr != null)
            head = curr.next;

        return head;
    }

    // Delete without head pointer
    /*
     * By deleting the node value, we do not mean removing it from memory. We mean:
     * 
     * 1. The value of the given node should not exist in the linked list.
     * 2. The number of nodes in the linked list should decrease by one.
     * 3. All the values before & after the del_node node should be in the same
     * order
     * 
     * Also given del_node will not be the last node of the linked list.
     */
    // intution :: we dont need to delete it actually but mimic it,so copy the value
    // of next and change the next to next's next
    void deleteNode(Node node) {
        node.data = node.next.data;
        node.next = node.next.next;
    }

    // Reverse Linked List
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode temp = curr.next;

            // make prev , curr's next and move both pointer ahead and continue till end of linked list
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    // Middle of the Linked List
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (slow != null && head != null && head.next != null) {
            slow = slow.next;
            head = head.next.next;
        }

        return slow;
    }

    // Rotate List :: Given the head of a linked list, rotate the list to the right by k places.
    // in simple terms, rotate it k times
    // intution :
    /*
     * 1. find length n
     * 2. make a start pointer pointing to start and end pointer pointint to end
     * 3. find (n-k)th node(knode) where links needed to be break
     * 4. make end.next=start
     * 5. return knode.next as new head
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0)
            return head;
        ListNode start = head, end = null, knode = head;
        int n = 0;
        while (head != null) {
            end = head;
            head = head.next;
            n++;
        }

        // if k is more than n only k%n rotation needed more
        k = k % n;

        int kFromFirst = n - k - 1; // position of node where's next will link be broken
        while (kFromFirst > 0) {
            knode = knode.next;
            kFromFirst--;
        }

        end.next = start;
        ListNode newHead = knode.next;
        knode.next = null;
        return newHead;
    }

    // Remove Nth Node From End of List
    /*
     * Intution : first find position of element(that is needed to be removed) from
     * starting
     * then remove it
     * there is an edge case when position from first is 0(handle it seperately)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return head;
        int total = 0;
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
            total++;
        }
        n = total - n;

        if (n == 0)
            return head.next;

        temp = head;
        n--;
        while (n > 0) {
            temp = temp.next;
            n--;
        }
        if (temp != null && temp.next != null)
            temp.next = temp.next.next;
        return head;
    }

    // optimised solution one pass
    /*
     * intution : 
     * 1. let make a dummy pointer having next pointing to HAED of our linked list
     * 2. then make two pointers poiniting at dummy .
     * 3. send one pointer(here, second) n+1 distance away
     * 4. now both pointers have n distance b/w them
     * 5. now move both pointers together when second pointer reached end first will
     * be n+1 distance away from end
     * 6. first is at postion of which's next elemnent we need to remove
     * 7. ** remember to return dummy.next in answer and not head as head could also
     * got removed
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy, second = dummy;
        int count = n;
        while (count >= 0) {
            second = second.next;
            count--;
        }
        while (second != null) {
            first = first.next;
            second = second.next;
        }

        first.next = first.next.next;

        return dummy.next;
    }

    // Remove every k'th node
    /* 
     * Intution : Traverse the linkedlist and maintain a count(initialize it with 1) variable and increase it at every node. 
     * If count current value is k remove it and set the count value to 1 again.
     */
    Node deleteK(Node head, int k) {
        if (k == 1)
            return null;
        int count = 1;
        Node prev = null, curr = head;
        while (curr != null) {
            if (count == k) {
                prev.next = curr.next;
                curr = curr.next;
                count = 1;
            } else {
                prev = curr;
                curr = curr.next;
                count++;
            }
        }
        return head;
    }

    // ** Palindrome Linked List
    /*
     *
     * Approach 1 :: Use an array to store values of ll and then use two pointer(first and last)
     *               Takes O(N) of space , not optimised
     * 
     * Approach2 :: without O(N) extra space
     * Intution :: 
     * 1. Break the ll into two parts from the mid(if n is odd,then ignore mid,2 parts will be {0,mid-1 } & {mid+1,n))
     * 2. Reverse any of the part
     * 3. check if both are equal(means their ele does not differ)
     * ex. 
     *    ll =>  A -> B -> C -> B -> A 
     *    1. one part is A->B and another is B->A
     *    2. reverse any part , lets say, second so second will be A -> B
     *    3. Traverse them and check if any element differs
     *              
     * 
     */
    public boolean isPalindrome(ListNode head) {
        if (head.next == null)
            return true;
        ListNode prev = null, slow = head, fast = head;
        boolean isOdd = true;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
            if (fast == null)
                isOdd = false;
        }
        prev.next = null;

        // first break the ll into two equal parts(ignore the mid ele if odd number
        // length as it mid ele will not interfer in palindrome)
        ListNode head2 = isOdd ? slow.next : slow;

        // reverse the ll
        prev = null;
        ListNode curr = head2;
        while (curr != null) {
            ListNode temp = curr.next;

            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        head2 = prev;

        // now you got two ll , traverse them and check if any element differs
        while (head != null && head2 != null) {
            if (head.val != head2.val)
                return false;
            head = head.next;
            head2 = head2.next;
        }

        return true;
    }

    // Remove Duplicates from a Sorted Linked List
    Node removeDuplicates(Node head) {
        if (head.next == null)
            return head;

        Node prev = head, curr = head.next;
        while (curr != null) {
            if (curr.data == prev.data) {
                curr = curr.next;
                prev.next = curr;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
        return head;
    }

    // Merge two sorted linked lists
    /*
     * Intution : 
     *      1. You create a dummy head coach just to get started
     *      2. Use two pointers (head1 and head2) to walk through both lls.
     *      3. At each step, compare the two current nodes and attach the smaller one to the result. Move dummy ahead.
     *      4. Once one ll is fully used, just attach the rest of the other ll to dummy
     *      NOTE -> dummy is just a helper to build the result — final result starts from dummy.next
     */
    Node sortedMerge(Node list1, Node list2) {
        Node dummy = new Node(0);
        Node ans = dummy;
        Node head1=list1, head2=list2;
        while(head1!=null && head2!=null){
            if(head1.data <= head2.data){
                dummy.next = head1;
                head1 = head1.next;
                dummy.next.next=null;
                dummy=dummy.next;
            }else{
                dummy.next = head2;
                head2 = head2.next;
                dummy.next.next=null;
                dummy=dummy.next;
            }
        }
        if(head1!=null) dummy.next=head1;
        if(head2!=null) dummy.next=head2;
        return ans.next;
    }

    // ** Sort a linked list of 0s, 1s and 2s
    /*
     * Intution : 
     *      1. You create 3 dummy head nodes and 3 dummy traverse nodes — one for each type, zero , one & two
     *      2. Traverse the ll, and attach the curr node to dummy nodes according to values(0,1,2)
     *      3. Once the mini ll are made attach the zero's tail next to one's head and one's tail to two's head
     */
    Node segregate(Node head) {
        Node zero = new Node(-1);
        Node one = new Node(-1);
        Node two = new Node(-1);

        Node finalhead = zero;
        Node onehead = one;
        Node twohead = two;

        while (head != null) {
            if (head.data == 0) {
                zero.next = head;
                zero = zero.next;
            } else if (head.data == 1) {
                one.next = head;
                one = one.next;

            } else {
                two.next = head;
                two = two.next;
            }
            head = head.next;
        }
        // link all the list

        one.next = twohead.next;
        two.next = null;
        zero.next = onehead.next;

        return finalhead.next;
    }

    // ** reverse nodes in k-group


    // ** Add Number Linked Lists
    /*
     * Intution :
     *      1. Reverse both ll as addition(even subtraction) happens from last digit.
     *      2. Create a dummy ans node to store the result ll and carry initialized as 0.
     *      3. Traverse both the ll simultaneously till any one of it exist.
     *      4. Add both of current digits (if one list got empty, use 0 as it's digit) and carry.
     *      5. Set the value of carry 1 if sum of digit and previous carry is > 9
     *      6. Set the value of node of ans ll as the modulo of sum.
     *      7. Once both the ll got empty, reverse the ans ll and return it.
     *      8. If asked in question , remove the trailing zeros.
     */
    Node reverseLL(Node n){
        Node prev=null,curr=n;
        while(curr!=null){
            Node nxt = curr.next;
            curr.next=prev;
            prev=curr;
            curr=nxt;
        }
        return prev;
    }
    Node addTwoLists(Node num1, Node num2) {
        // reverse the list to add them
        Node head1=reverseLL(num1);
        Node head2=reverseLL(num2);
        
        Node ans = new Node(0);
        Node temp = ans;
        int carry = 0;
        while(head1!=null || head2!=null){
            int d1 = head1!=null ? head1.data : 0;
            int d2 = head2!=null ? head2.data : 0;

            int sum = d1+d2+carry;
            carry = sum/10;
            temp.next = new Node(sum%10);

            if(head1!=null) head1=head1.next;
            if(head2!=null) head2=head2.next;
            temp = temp.next;
        }
        // handle the last carry
        if(carry!=0) temp.next = new Node(carry);
        
        // reverse the ans to return
        ans = reverseLL(ans.next);
        
        // remove any leading zeros as asked in question
        while(ans!=null && ans.data==0){
            ans=ans.next;
        }
        
        return ans;
    }

    // Reverse Nodes in k-Group
    // this is a |STUPID| method as it is equivalent to store in an array then create linked list again
    ListNode reverseLL(ListNode n){
        ListNode prev=null,curr=n;
        while(curr!=null){
            ListNode nxt = curr.next;
            curr.next=prev;
            prev=curr;
            curr=nxt;
        }
        return prev;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        ArrayList<ListNode> arr = new ArrayList<>();
        ListNode temp = head;
        ListNode lastGroup = null;

        // break the ll into k size segments and store them in arraylist
        while(temp!=null){
            int K = k;
            ListNode klist = temp;
            ListNode prev=null;
            while(K>0 && temp!=null){
                prev=temp;
                temp = temp.next;
                K--;
            }

            /*
            * In leetcode version they asked for to keep lastgroup(remaining elements) as it is
            * but in gfg version they asked to reverse the lastgroup as well
            * therefore the below snippet of if condition on k is not required, only

                arr.add(klist);
                prev.next=null;

             */
            
            if(K==0){
                arr.add(klist);
                prev.next=null;
            }else{
                lastGroup=klist;
            }
        }

        // reverse the segments
        for(int i=0; i<arr.size(); i++){
            arr.set(i,reverseLL(arr.get(i)));
        }
        

        // traverse through the arraylist and create node for every element
        ListNode ans = new ListNode(0);
        ListNode dummy = ans;
        for(int i=0; i<arr.size(); i++){
            ListNode h = arr.get(i);
            while(h!=null){
                dummy.next = new ListNode(h.val);
                h=h.next;
                dummy=dummy.next;
            }
        }

        /*
         * In leetcode version they asked for to keep lastgroup(remaining elements) as it is
         * but in gfg version they asked to reverse the lastgroup as well
         * so the line "dummy.next = lastGroup;" is not needed in gfg as it already stored in list
         */
        dummy.next = lastGroup;
        return ans.next;

    }

    // right approach
    // for gfg
    public Node reverseKGroup(Node head, int k) {
        Node first = new Node(0);
        first.next=head;
        head=first;
        
        while(first.next!=null){
            Node second = first.next;
            Node prev=first,curr=second;
            int x=k;
            while(x>0 && curr!=null){
                Node next = curr.next;
                curr.next=prev;
                prev=curr;
                curr=next;
                x--;
            }
            second.next=curr;
            first.next=prev;
            first=second;
        }
        
        return head.next;
    }
    // for leetcode (Not done by me yet)


    // flatten a binary tree to linked list (morris traversal)
    // leetcode version
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while(curr!=null){
            if(curr.left!=null){
                TreeNode rightMostNode = curr.left;
                while(rightMostNode.right!=null) rightMostNode = rightMostNode.right;
                rightMostNode.right = curr.right;
                curr.right=curr.left;
                curr.left=null;
            }else{
                curr = curr.right;
            }
        }
    }
    
    // ** Flattening a Linked List (different from above)
    // merge two sorted ll where next is replaced with bottom 
    Node sortedMerge2(Node list1, Node list2) {
        Node dummy = new Node(0);
        Node ans = dummy;
        Node head1=list1, head2=list2;
        while(head1!=null && head2!=null){
            if(head1.data <= head2.data){
                dummy.bottom = head1;
                head1 = head1.bottom;
                dummy.bottom.bottom=null;
                dummy=dummy.bottom;
            }else{
                dummy.bottom = head2;
                head2 = head2.bottom;
                dummy.bottom.bottom=null;
                dummy=dummy.bottom;
            }
        }
        if(head1!=null) dummy.bottom=head1;
        if(head2!=null) dummy.bottom=head2;
        return ans.bottom;
    }
    Node flatten(Node root) {
        while(root.next!=null){
            Node temp = root.next.next;
            root = sortedMerge2(root,root.next);
            root.next = temp;
        }
        return root;
    }

    // merge k sorted linked list
    Node sortedMerge3(Node list1, Node list2) {
        Node dummy = new Node(0);
        Node ans = dummy;
        Node head1=list1, head2=list2;
        while(head1!=null && head2!=null){
            if(head1.data <= head2.data){
                dummy.next = head1;
                head1 = head1.next;
                dummy.next.next=null;
                dummy=dummy.next;
            }else{
                dummy.next = head2;
                head2 = head2.next;
                dummy.next.next=null;
                dummy=dummy.next;
            }
        }
        if(head1!=null) dummy.next=head1;
        if(head2!=null) dummy.next=head2;
        return ans.next;
    }
    Node mergeKLists(List<Node> arr) {
        while(arr.size()>1){
            Node l1 = arr.remove(arr.size()-1);
            Node l2 = arr.remove(arr.size()-1);
            arr.add(sortedMerge(l1,l2));
        }
        return arr.size()==1 ? arr.get(0) : null;
    }


    // Copy List with Random Pointer
    // approach 1 : TC-> O(N) and SC-> O(N)
    /*
     * Intution : 
     *      1. Traverse the ll and for each node,create a new copy node & store a mapping of old node to new node.
     *      2. Again traverse the old ll and new ll and store random pointer in new ll node by mapping stored.
     */
    public Node cloneLinkedList(Node head) {
        HashMap<Node,Node> mp = new HashMap<>();
        Node dummy = new Node(0);
        Node ans = dummy , temp=head;
        while(temp!=null){
            dummy.next = new Node(temp.data);
            // store the correspoding address of original list to new copy list
            mp.put(temp,dummy.next);
            dummy = dummy.next;
            temp = temp.next;
        }
        temp=head;
        dummy=ans.next;
        while(temp!=null){
            // use the map to link random pointer of original list to current copy list
            dummy.random=mp.getOrDefault(temp.random,null);
            temp=temp.next;
            dummy=dummy.next;
        }
        return ans.next;
    }
    // optimised approach : TC->(N), SC->O(1)
    /*
     * Intution : 
     *      1. First Pass :: For each original node, create its clone right next to it. 
     *          ex. A → B → C becomes A → A' → B → B' → C → C'
     *      2. Second Pass :: Set Random Pointers for Clones
     *          i.e curr.next.random = curr.random.next
     *          curr.next is the clone, and curr.random.next is the clone of the random
     *      3. Third Pass  :: Separate the original and cloned nodes
     *          original links: curr.next = curr.next.next
     *          cloned links: copyCurr.next = copyCurr.next.next
     */
    public Node cloneLinkedList2(Node head) {
        Node curr = head;
        while(curr!=null){
            Node temp = curr.next;
            curr.next = new Node(curr.data);
            curr.next.next=temp;
            curr = curr.next.next;
        }
        curr = head;
        while(curr!=null){
            if(curr.random!=null) curr.next.random=curr.random.next;
            curr=curr.next.next;
        }
        curr = head;
        Node dummy = new Node(0); 
        Node copyCurr = dummy;

        while (curr != null) {
            copyCurr.next = curr.next;
            curr.next = curr.next.next;
    
            curr = curr.next;
            copyCurr = copyCurr.next;
        }
    
        return dummy.next;
    }




    // *** CYCLE/LOOP based questions

    // ** detect cycle
    // approach 1 , we can use map/set to store the traversed node and if it repeats
    // there is a cycle
    // *** approach 2 , below using 2 pointers (fast and slow)
    /*
     * Intution : 
     *      1. Use two pointers, slow(moves one step at a time) and fast(moves two steps at a time)
     *      2. If there's no cycle, fast will hit null and the loop ends.
     *      3. If there's a cycle, the faster pointer will eventually catch up to the slower one inside the loop.
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    // *** Find length of Loop
    // intution :: in detect cycle , once two pointers met, then keep one pointer stationary and move other pointer, count the step it took thats the lnegth of loop
    public int countNodesinLoop(Node head) {
        Node slow=head,fast=head;
        while(fast!=null && fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast){
                int count = 1;
                slow=slow.next;
                while(slow!=fast){
                    slow=slow.next;
                    count++;
                }
                return count;
            }
        }
        return 0;
    }

    // detect loop and remove it
    // approach 1 , we can use map/set to store the traversed node and if it repeats break that link
    // *** approach 2 : Detect cycle and then node making loop and break it.
    /*
     * Intution :
     *      1. Detect the Loop using fast and slow pointer(above). Let say they both met(loop exist) i.e. fast = slow
     *      2. Find the Starting Node of the Loop
     *          -> Move slow back to head
     *          -> Now move both slow and fast one step at a time.
     *          -> They will meet at the first node where the loop starts.
     *      3. From the fast (or slow, same now) pointer, move forward in the loop until you find the last node (i.e., fast.next == slow)
     *          and then break it (fast.next = null)
     *          
     */
    public static void removeLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                // loop detected

                //move slow to head
                slow=head;

                // find the first node of loop
                while(slow!=fast){
                    slow=slow.next;
                    fast=fast.next;
                }

                // move fast pointer in loop till you find last node of loop
                while(fast.next!=slow){
                    fast=fast.next;
                }

                // break the last node link with first node
                fast.next=null;
            }
        }
    }
    // approach 3 : first find loop length(lets say k) then make two pointer in which the secomd pointer is k distance away 
    // from first then start making loop, they will meet at first node of loop then you can continue traversing the loop 
    // with fast pointer and check when fast.next=slow -> breaks the loop by fast.next=null


    // Intersection in Y Shaped Lists
    // Intution :: find the diff in lengths of two list. Add that diff to the pointer of longer linked list. Then start 
    // traversing both the list together the point where they meet is point of intersection
    int calLength(Node head){
        
        Node temp = head;
        int length = 1;
        while(temp != null){
            temp = temp.next;
            length++;
        }
        return length;
    }
    Node intersectPoint(Node head1, Node head2) {
         int length1 = calLength(head1);
        int length2 = calLength(head2);
        Node curr1 = head1;
        Node curr2 = head2;
        while(length1 != length2){
             if(length1 > length2){
                 curr1 = curr1.next;
                 length1--;
             }else{
                 curr2 = curr2.next;
                 length2--;
             }
        }
        while(curr1 != null && curr2 != null){
            if(curr1 == curr2){
                return curr1;
            }
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        return null;
    }

    
}
