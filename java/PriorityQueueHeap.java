package Placementprep.java;
// *** => Perfomance of Heap compared to other DS
/*

			    Insertion      Search      Find_Min      Delete_Min

Unsorted Array     O(1)  	   O(N)  	   O(N) 	     O(N)	                                              

Sorted Array       O(N)  	   O(logN)     O(1) 	     O(N)                                                    

Unsorted LL        O(1)  	   O(N)  	   O(N) 	     O(N)                                                       

Sorted LL          O(logN)     O(logN)     O(1) 	     O(1)  

Min Heap           O(logN)     O(N)        O(1)          O(logN)


HEAP => 1. Tree DS
	  2. Complete Binary Tree
	  3. Heap property (keep track of max/min element at root).
	  4. are of two types (max and min).


Max-Heap => Root should always be maximum and same goes for all sub trees.
Min-Heap => Root should always be minimum and same goes for all sub trees.

Heap Representation => Node -> i
			     parent -> floor(i-1/2)
			     left child -> 2*i + 1
			     right child -> 2*i + 2 	

Insertion In Heap => First insert element at last and then swap with parent till the heap property is satisfied

Deletion in heap => first delete the root element from heap and then put last element at root and swap with children till the 
			  heap property is satisfied.

Heapify => An algorithm to make the heap from regular array having time complexity of O(n). Traverse array from the end(n/2) since leaf are bound 
	     to be heap and check if the current node is forming a heap, if not then swap with the max(leftchild, rightchild) else continue.

Heapsort => Takes O(NlogN). Delete the element from heap N times.

Heap in JAVA stl => 1. Max Heap -> PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
			 2. Min Heap -> PriorityQueue<Integer> pq = new PriorityQueue<>();

Question Identification => Kth + `Smalles/Largest`
                                      |      |
                                     Max    Min
                                    Heap    Heap
                    
                        => Question can be solved by sorting(NlogN) and then can be improved by heap(NlogK or N).

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map;

// Questions

class Pair{
    int first;
    int second;
    Pair(int first,int second){
        this.first = first;
        this.second = second;
    }
}

@FunctionalInterface
interface Comparator {
    int compare(Integer a, Integer b);
}



@SuppressWarnings("all")
public class PriorityQueueHeap{

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

    // ** HEAPIFY Implementation
    // Heapify subtree rooted at index i in array of size n
    // TC : O(logN)
    public static void heapify(int[] arr, int n, int i) {
        int largest = i;           // Assume current node is largest
        int left = 2 * i + 1;      // Left child index
        int right = 2 * i + 2;     // Right child index

        // If left child exists and is greater than current largest
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child exists and is greater than current largest
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not the root
        if (largest != i) {
            // Swap current node with the largest child
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }
    }
    // Function to build a max heap
    // TC : O(N)
    public static void buildMaxHeap(int[] arr) {
        int n = arr.length;

        // Start from last non-leaf node and move up
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }
    // HEAP SORT
    // TC : O(N*logN), SC: O(1)
    public void heapSort(int[] arr) {
        int n = arr.length;

        // Step 1: Build max heap (heapify the array)
        // TC : O(N)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Step 2: Extract elements from heap one by one
        // TC : O(N*logN)
        for (int i = n - 1; i > 0; i--) {
            // Move current root (largest) to the end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // ? 1.  Kth Largest Element in an Array -> Given an integer array nums and an integer k, return the kth largest element in the array.
    // code => 
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0; i<nums.length; i++){
            pq.add(nums[i]);
            if(pq.size()>k) pq.poll();
        }
        return pq.peek();
    }
    

    // ? 2.  Kth Smallest Element in an Array -> Given an integer array nums and an integer k, return the kth Smallest element in the array.
    // code => 
    public static int kthSmallest(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((Integer a, Integer b) -> b-a);
        for(int i=0; i<arr.length; i++){
            pq.add(arr[i]);
            if(pq.size()>k) pq.poll();
        }
        return pq.peek();
    }

    // ? 3. Sort a Nearly sorted array
    public void nearlySorted(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        int idx = 0;
        for(int i=0; i<arr.length; i++){
            pq.add(arr[i]);
            if(pq.size()==k+1){
                arr[idx++]=pq.poll();
            }
            
        }
    
        while(!pq.isEmpty()){
            arr[idx++] = pq.poll();
        }
    }

    // 4.  Find K Closest Elements to x
    // 1. using comparator
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>();
        for(int num: arr) list.add(num);
        list.sort((a,b) -> {
            int diffA = Math.abs(a - x);
            int diffB = Math.abs(b - x);
            if(diffA!=diffB) return Integer.compare(diffA,diffB);
            return Integer.compare(a,b);
        });
        
        List<Integer> result = list.subList(0,k);
        Collections.sort(result);
        return result;
    } 
    //2. using priority queue
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        // max heap
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((Pair a,Pair b) -> {
            if(a.first!=b.first) return Integer.compare(b.first,a.first);
            return Integer.compare(b.second,a.second);
        });
        for(int i=0; i<arr.length; i++){
            pq.add(new Pair(Math.abs(arr[i]-x),arr[i]));
            if(pq.size()>k) pq.poll();
        }

        List<Integer> result = new ArrayList();
        while(!pq.isEmpty()){
            result.add(pq.poll().second);
        }

        Collections.sort(result);
        return result;
    }
    // 3. binary search (TODO)


    // 5. Frequency Sort
    public ArrayList<Integer> sortByFreq(int arr[]) {
        Map<Integer,Integer> mp = new HashMap<>();
        for(int i=0; i<arr.length; i++){
            mp.put( arr[i],mp.getOrDefault(arr[i],0)+1 );
        }
        PriorityQueue<Map.Entry<Integer,Integer>> pq = new PriorityQueue<>((Map.Entry<Integer,Integer>  a,Map.Entry<Integer,Integer>  b) -> {
            if(a.getValue()==b.getValue()) return Integer.compare(a.getKey(),b.getKey());
            
            return Integer.compare(b.getValue(),a.getValue());
        });
        pq.addAll(mp.entrySet());
        
        ArrayList<Integer> ans = new ArrayList<>();
        while(!pq.isEmpty()){
            Map.Entry<Integer,Integer> en = pq.poll();
            for(int i=0; i<en.getValue(); i++){
                ans.add(en.getKey());
            }
        }
        return ans;
    }


    // 6. Top K Frequent element
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> mp = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            mp.put(nums[i], mp.getOrDefault(nums[i],0)+1);
        }

        PriorityQueue<Map.Entry<Integer,Integer>> pq = new PriorityQueue<>((Map.Entry<Integer,Integer>  a,Map.Entry<Integer,Integer>  b) -> {
            return Integer.compare(a.getValue(),b.getValue());
        });

        for(Map.Entry<Integer,Integer> en : mp.entrySet()){
            pq.add(en);
            if(pq.size()>k) pq.poll();
        }

        int[] ans = new int[k];
        int idx = 0;
        while(!pq.isEmpty()){
            ans[idx++]=pq.poll().getKey();
        }
        return ans;
    }

    // 7. K Closest Points to Origin
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a,int[] b) -> {
            int dist1 = (a[0]*a[0]) + (a[1]*a[1]);
            int dist2 = (b[0]*b[0]) + (b[1]*b[1]);

            return Integer.compare(dist2,dist1);
        });

        for(var point: points){
            pq.add(point);
            if(pq.size()>k) pq.poll();
        }

        int[][] ans = new int[k][2];
        int idx = 0;
        while(!pq.isEmpty()){
            ans[idx++]=pq.poll();
        }
        return ans;
    }

    // 8. Minimum Cost of ropes
    public static int minCost(int[] arr) {
        int ans = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Arrays.stream(arr).forEach(pq::add);
        
        while(pq.size()!=1){
            int temp = (pq.poll() + pq.poll());
            ans += temp;
            pq.add(temp);
        }
        return ans;
    }

    // 9. Magician and Chocolates
    public int nchoc(int A, ArrayList<Integer> B) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        B.stream().forEach(pq::add);
        int ans = 0;
        int mod = 1000000007;
        
        while(A>0){
            int maxChoc = pq.poll();
            ans += (maxChoc%mod);
            ans = ans%mod;
            pq.add(maxChoc/2);
            A--;
        }
        return ans;
    }

    // 10. heigh of heap(heap is complete binary tree so heigh = floor(logbase2 of N))
    int heapHeight(int N, int arr[]){
        return (int)(Math.log(N)/Math.log(2));
    }

    // 11. Last Stone Weight
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i:stones) pq.add(i);

        while(pq.size()>1){
            int firstStone = pq.poll();
            int secondStone = pq.poll();
            if(firstStone!=secondStone){
                pq.add(firstStone-secondStone);
            }
        }
        return pq.size()!=0 ? pq.peek() : 0;
    }

    // 12. Take Gifts From the Richest Pile
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i:gifts) pq.add(i);
        long ans = 0;
        
        while(k>0){
            int pile = pq.poll();
            pq.add((int)Math.sqrt(pile));
            k--;
        }
        while(!pq.isEmpty()) ans+=pq.poll();
        return ans;
    }

    // 13.  Profit Maximisation
    public int solve(ArrayList<Integer> A, int B) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        A.stream().forEach(pq::add);
        int ans = 0;
        while(B>0){
            int maxTicketPrice = pq.poll();
            ans += maxTicketPrice;
            pq.add(maxTicketPrice-1);
            B--;
        }
        return ans;
    }

    // 14. Kth Largest in a Stream
    int[] kthLargest(int k, int[] arr, int n) {
        int[] ans = new int[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int idx = 0;
        for(int i:arr){
            pq.add(i);
            if(pq.size()>k) pq.poll();
            if(pq.size()<k) ans[idx++]=-1;
            else ans[idx++]=pq.peek();
        }
        return ans;
    }

    // 15. Sum of elements between k1'th and k2'th smallest elements
    public static long sumBetweenTwoKth(long A[], long N, long k1, long k2)
    {
        PriorityQueue<Long> pqk1 = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Long> pqk2 = new PriorityQueue<>(Collections.reverseOrder());
        
        for(long i:A){
            pqk1.add(i);
            if(pqk1.size()>k1) pqk1.poll();
            pqk2.add(i);
            if(pqk2.size()>k2-1) pqk2.poll();
        }
        
        long sumK2 = 0;
        long sumK1 = 0;
        while(!pqk1.isEmpty()) sumK1+=pqk1.poll();
        while(!pqk2.isEmpty()) sumK2+=pqk2.poll();
        return sumK2-sumK1;
    }

    // 16. Merge two binary Max heaps
    public int[] mergeHeaps(int[] a, int[] b, int n, int m) {
        int[] ans = new int[n+m];
        int idx=0;
        for(int i:a) ans[idx++]=i;
        for(int i:b) ans[idx++]=i;
        
        for(int i=(n+m)/2-1; i>=0; i--){
            heapify(ans,i,n+m);
        }
        
        return ans;
    }


    // 17. Is Binary Tree Heap
    int n = 0;
    public int numberOfNode(Node root){
        if(root==null) return 0;
        return 1+numberOfNode(root.left)+numberOfNode(root.right);
    }
    public boolean isCompleteTree(Node root,int idx) {
        if(root==null) return true;
        if(idx>=n) return false;
        return isCompleteTree(root.left,2*idx+1) && isCompleteTree(root.right,2*idx+2);
    }
    boolean isMaxHeapProperty(Node tree){
        if(tree==null) return true;
        if(tree.left!=null && tree.data<tree.left.data) return false;
        if(tree.right!=null && tree.data<tree.right.data) return false;
        
        return isMaxHeapProperty(tree.left) && isMaxHeapProperty(tree.right);
    }
    boolean isHeap(Node tree) {
        n = numberOfNode(tree);
        return isCompleteTree(tree,0) && isMaxHeapProperty(tree);
    }



    // ** method when you need top k elements according to freq(pq comprator according to value)
    // 18. Top K Frequent Words
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String,Integer> mp = new HashMap<>();
        for(String word : words){
            mp.put(word,mp.getOrDefault(word,0)+1);
        }
        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(
            (a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue()
        );
        List<String> ans = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:mp.entrySet()){
            pq.add(entry);
            if(pq.size()>k){
                pq.poll();
            }
        }
        while(!pq.isEmpty()){
            ans.add(pq.poll().getKey());
        }
        Collections.reverse(ans);
    
        return ans;
    }



    // *** rearranging accordint to property
    
}
