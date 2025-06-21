package Placementprep.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class StackClass {

    // ** Minimum Add to Make Parentheses Valid
    public int minAddToMakeValid(String s) {
        Stack<Character> st = new Stack<>();
        int n = s.length();
        for(int i=0; i<n; i++){
            char c = s.charAt(i);
            if(!st.isEmpty() && st.peek()=='(' && c==')') st.pop();
            else st.push(c);
        }
        return st.size();
    }

    // ** Valid Parentheses
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        int n = s.length();
        for(int i=0; i<n; i++){
            char c = s.charAt(i);
            if(st.isEmpty()) st.push(c);
            else if((st.peek()=='(' && c==')') || (st.peek()=='[' && c==']') || (st.peek()=='{' && c=='}')) st.pop();
            else st.push(c);
        }
        return st.size()==0;
    }

    // ** Backspace String Compare
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> st = new Stack<>();
        Stack<Character> tt = new Stack<>();
        for(char c:s.toCharArray()){
            if(c=='#'){
                if(!st.isEmpty()) st.pop();
            }
            else st.push(c);
        }
        for(char c:t.toCharArray()){
            if(c=='#'){
                if(!tt.isEmpty()) tt.pop();
            }
            else tt.push(c);
        }

        if(st.size()!=tt.size()) return false; // early return

        while(!st.isEmpty()){
            if(st.pop()!=tt.pop()) return false;
        }

        return true;

    }

    // ** Next Greater Element to right
    public ArrayList<Integer> nextLargerElement(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        Stack<Integer> st = new Stack<Integer>();
        for(int i=arr.length-1; i>=0; i--){
            while(!st.isEmpty() && arr[i]>=st.peek()) st.pop();
            if(st.isEmpty()) list.add(-1);
            else list.add(st.peek());
            
            st.push(arr[i]);
        }
        Collections.reverse(list);
        return list;
    }

    // next smallest element to right
    static ArrayList<Integer> nextSmallerElement(ArrayList<Integer> arr, int n){
        ArrayList<Integer> list = new ArrayList<>();
        Stack<Integer> st = new Stack<Integer>();
        for(int i=arr.size()-1; i>=0; i--){
            while(!st.isEmpty() && arr.get(i)<=st.peek()) st.pop();
            if(st.isEmpty()) list.add(-1);
            else list.add(st.peek());
            
            st.push(arr.get(i));
        }
        Collections.reverse(list);
        return list;
    }


    // ** Smaller on Left
    public int[] leftSmaller(int[] arr) {
        int n = arr.length;
        int[] list = new int[n];
        Stack<Integer> st = new Stack<Integer>();
        int idx = 0;
        for(int i=0; i<n; i++){
            while(!st.isEmpty() && arr[i]<=st.peek()) st.pop();
            if(st.isEmpty()) list[idx++]=-1;
            else list[idx++]=st.peek();
            
            st.push(arr[i]);
        }
        return list;
    }

    // next greater element on left
    public static List<Integer> nextGreaterOnLeft(int[] arr) {
        List<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                result.add(-1);
            } else {
                result.add(stack.peek());
            }

            stack.push(arr[i]);
        }

        return result;
    }

    // ** next greater element to right in Circular Array
    public int[] nextGreaterElements(int[] arr) {
        int n = arr.length;
        int[] ans = new int[2*n];
        int idx = 2*n-1;
        Stack<Integer> st = new Stack<Integer>();
        for(int i=2*n-1; i>=0; i--){
            while(!st.isEmpty() && arr[i%n]>=st.peek()) st.pop();
            if(st.isEmpty()) ans[idx--]=-1;
            else ans[idx--]=st.peek();
            
            st.push(arr[i%n]);
        }
        
        return Arrays.stream(ans).limit(n).toArray();   // faster is Arrays.copyOf(ans, n);
    }

    // ** stock span problem
    public ArrayList<Integer> calculateSpan(int[] arr) {
        int n = arr.length;
        ArrayList<Integer> list = new ArrayList<>();
        Stack<Integer> st = new Stack<>();
    
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && arr[i] >= arr[st.peek()]) {
                st.pop();
            }
            if (st.isEmpty()) {
                list.add(i + 1); // elements are smaller
            } else {
                list.add(i - st.peek());
            }
            st.push(i);
        }
    
        return list;
    }


    // ** Largest Rectangle in Histogram
    public int[] nextSmallestLeft(int[] arr){
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[n];
        for(int i=0; i<n; i++){
            while(!st.isEmpty() && arr[i]<=arr[st.peek()]) st.pop();
            if(st.isEmpty()) ans[i]=-1;
            else ans[i]=st.peek();

            st.push(i);
        }
        return ans;
    }
    public int[] nextSmallestRight(int[] arr){
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[n];
        for(int i=n-1; i>=0; i--){
            while(!st.isEmpty() && arr[i]<=arr[st.peek()]) st.pop();
            if(st.isEmpty()) ans[i]=-1;
            else ans[i]=st.peek();

            st.push(i);
        }
        return ans;
    }
    public int largestRectangleArea(int[] heights) {
        int[] nsl = nextSmallestLeft(heights);
        int[] nsr = nextSmallestRight(heights);
        int n = heights.length;

        int ans = 0;
        for(int i=0; i<n; i++){
            if(nsl[i]==-1 && nsr[i]==-1){
                ans = Math.max(ans,n*heights[i]);
            }else if(nsl[i]==-1){
                ans = Math.max(ans,nsr[i]*heights[i]);
            }else if(nsr[i]==-1){
                ans = Math.max(ans,(n-nsl[i]-1)*heights[i]);
            }else{
                ans = Math.max(ans, (nsr[i]-nsl[i]-1)*heights[i]);
            }
        }
        return ans;
    }
    
    // ** Maximal Rectangle
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        int ans = 0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j]=='1'){
                    heights[j] = heights[j]+1;
                }else heights[j]=0;
            }
            ans = Math.max(ans,largestRectangleArea(heights));
        }
        return ans;
    }


    // ** Trapping Rain Water
    // NOTE ::  different than previous. max water storage capacity does'nt depends on NGR/NGL but min (max in left array , max in right array)
    // formulation is : heightWhereWaternCanStore = min(leftMax,rightMax)-currHeight * width(==1)
    // ** first approach : (Non stack) simple 3 loops , two to calculate greatest left & right, third to use above formulation
    // though TC is just O(N) but SC is also O(1)
    public int trap(int[] height) {
        int ans = 0;
        int n = height.length;
        
        int[] greatestInLeft = new int[n];
        int[] greatesInRight = new int[n];

        for(int i=1; i<n; i++) greatestInLeft[i]=Math.max(greatestInLeft[i-1],height[i-1]);
        for(int i=n-2; i>=0; i--) greatesInRight[i]=Math.max(greatesInRight[i+1],height[i+1]);

        for(int i=0; i<n; i++){
            int heightDiff = Math.min(greatestInLeft[i],greatesInRight[i]) - height[i];
            if(heightDiff>0) ans += heightDiff;
        }

        return ans;
    }
    // 



    // ** The Celebrity Problem
    public int celebrity(int mat[][]) {
        int n = mat.length;
        Stack<Integer> st = new Stack<>();
        for(int i=0; i<n; i++) st.push(i);
        
        while(st.size()>1){
            int first = st.pop();
            int second = st.pop();
            
            if(mat[first][second]==1 && mat[second][first]==0){
                st.push(second);
            }else if(mat[first][second]==0 && mat[second][first]==1){
                st.push(first);
            }
        }
        if(st.isEmpty()) return -1;
        int possibleCelebrity = st.peek();
        for(int i=0; i<n; i++) if(i!=possibleCelebrity && mat[i][possibleCelebrity]==0) return -1;
        for(int i=0; i<n; i++) if(i!=possibleCelebrity && mat[possibleCelebrity][i]==1) return -1;
        return possibleCelebrity;
    }

    // ** Min Stack
    // aproach 1 : with SC O(N) || Not good
    // keeps storing the new minimum element coming in supporting stack
    class MinStack {
        Stack<Integer> st;
        Stack<Integer> supportingStack;
        public MinStack() {
            st = new Stack<Integer>();
            supportingStack = new Stack<Integer>();
        }
        
        public void push(int val) {
            st.push(val);
            if(supportingStack.isEmpty() || supportingStack.peek()>=val){
                supportingStack.push(val);
            }
        }
        
        public void pop() {
            if(st.peek().equals(supportingStack.peek())){
                supportingStack.pop();
            }
            st.pop();
        }
        
        public int top() {
            return st.peek();
        }
        
        public int getMin() {
            return supportingStack.peek();
        }
    }
    // approach 2 : using node, where every min node will have a next pointer to prev min node
    class MinStack2 {
        Stack<Node> st;
        Node currMin;
    
        static class Node{
            int data;
            Node next;
    
            Node(int data,Node next){
                this.data=data;
                this.next=next;
            }
        }
    
        public MinStack2() {
            st = new Stack<Node>();
            currMin = null;
        }
        
        public void push(int val) {
            if(currMin!=null && val<=currMin.data){
                Node temp = new Node(val,currMin);
                st.push(temp);
                currMin=temp;
            }else{
                st.push(new Node(val,null));
                if(currMin==null) currMin=st.peek();
            }
            
        }
        
        public void pop() {
            if(st.peek()==currMin){
                currMin = currMin.next;
            }
            st.pop();
        }
        
        public int top() {
            return st.peek().data;
        }
        
        public int getMin() {
            return currMin.data;
        }
    }
    // approach 3 : storing prev min using mathematical trick
    class MinStack3 {
        private Stack<Long> stack;
        private long min;
    
        public MinStack3() {
            stack = new Stack<>();
        }
    
        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);  // diff = val - val = 0
                min = val;
            } else {
                stack.push((long) val - min);  // store the difference
                if (val < min) min = val;      // update min
            }
        }
    
        public void pop() {
            long diff = stack.pop();
            if (diff < 0) {
                // It means the popped value was a new min
                min = min - diff; // revert to previous min
            }
        }
    
        public int top() {
            long diff = stack.peek();
            if (diff >= 0) {
                return (int) (min + diff); // regular value
            } else {
                return (int) (min); // it's the current min
            }
        }
    
        public int getMin() {
            return (int) min;
        }
    }
    
}