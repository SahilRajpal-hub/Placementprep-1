package Placementprep.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class TwoPointers {
    // I have tried to classify the 2-pointers problem into types to understand it better
    
    // *** Observed Patterns
    //  ** 1. Two Pointers After Sorting (First and Last)
    // ? Goal: Find pairs with a certain property like sum, difference, closest sum, etc.


    // 2 sum
    public int[] twoSum(int[] numbers, int target) {
        int i=0,j=numbers.length-1;
        int[] ans = new int[2];
        while(i<j){
            if(numbers[i]+numbers[j] == target){
                ans[0]=i+1;
                ans[1]=j+1;
                return ans;
            }else if(numbers[i]+numbers[j] > target){
                j--;
            }else{
                i++;
            }
        }
        return ans;
    }

    // 3 sum
    /* 
    initution :: 1. sort the array(if not already) 
                 2. loop for the first element from i=0 to n-2 
                 3. new target required will be target-firstElement
                 4. apply two sum for the newtarget on the remaining array
    */
    public static boolean twoSum(int arr[],int start,int end,int target){
        while(start < end){
            if(arr[start]+arr[end]==target) return true;
            else if(arr[start]+arr[end] > target) end--;
            else start++;
        }
        return false;
    }
    public static boolean hasTripletSum(int arr[], int target) {
        Arrays.sort(arr);
        int n = arr.length;
        for(int i=0; i<n-2; i++){
            int newTarget = target-arr[i];
            if(twoSum(arr,i+1,n-1,newTarget)){
                return true;
            }
        }
        return false;
    }


    // 3 sum : return all the triplets but should not have duplicate triplets
    // approach 1 (Hash Map): TC : O(N^2) but using hashmap so SC is also O(N^2)
    public List<List<Integer>> threeSum(int[] nums) {
        int target = 0;
        Set<List<Integer>> ans = new HashSet<>(); // set to prevent duplicate lists
        HashMap<Integer,Integer> mp = new HashMap<>(); // map to find 3rd element at O(1)
        for(int i=0; i<nums.length; i++) mp.put(nums[i],i);
        for(int i=0; i<nums.length-2; i++){
            for(int j=i+1; j<nums.length-1; j++){
                int ele = target-nums[i]-nums[j];
                if(mp.containsKey(ele) && mp.get(ele)!=i && mp.get(ele)!=j){

                    // sorting the 3 elements to maintain the permutation(as same element with diff permutation will be consider diff list in set)
                    int mx = Math.max(nums[i],Math.max(nums[j],ele));
                    int mn = Math.min(nums[i],Math.min(nums[j],ele));
                    int rm = 0 - mx - mn;

                    ans.add(List.of(mx,rm,mn));
                }
            }
        }
        return new ArrayList<>(ans);
    }
    // approach 2(two pointers) : TC : O(N^2) but SC O(1)
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Sort the array
    
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first number
            if (i > 0 && nums[i] == nums[i - 1]) continue;
    
            int left = i + 1;
            int right = nums.length - 1;
    
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
    
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
    
        return result;
    }
    

    // 4 sum
    // same as 3 sum intution,complexity will be O(n^3)
    public static boolean hasTripletSum(int arr[],int start ,int n ,int target) {
        for(int i=start; i<n; i++){
            int newTarget = target-arr[i];
            if(twoSum(arr,i+1,n-1,newTarget)){
                return true;
            }
        }
        return false;
    }
    boolean find4Numbers(int arr[], int n, int target) 
    {
        Arrays.sort(arr);
        for(int i=0; i<n-3; i++){
            int newTarget = target-arr[i];
            if(hasTripletSum(arr,i+1,n,newTarget)){
                return true;
            }
        }
        return false;
    }


    // 4 sum : return all quadreplt
    // same approach as 3 sum return all triplet
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
    
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicates for i
            if (i > 0 && nums[i] == nums[i - 1]) continue;
    
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicates for j
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
    
                int left = j + 1, right = n - 1;
    
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
    
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
    
                        // Skip duplicates
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
    
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
    
        return result;
    }
    

    // 2 sum closest
    public int twoSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int start = 0, end = nums.length - 1;
        int closestSum = nums[start] + nums[end];
    
        while (start < end) {
            int sum = nums[start] + nums[end];
    
            if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                closestSum = sum;
            }
    
            if (sum < target) start++;
            else if (sum > target) end--;
            else return sum; // exact match
        }
    
        return closestSum;
    }
    

    // 3 sum closest
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0]+nums[1]+nums[2];

        for(int i=0; i<nums.length-2; i++){

            int left=i+1,right=nums.length-1;

            while(left<right){
                int currentSum = nums[left]+nums[right]+nums[i];
                if(Math.abs(currentSum-target) < Math.abs(closestSum-target)){
                    closestSum = currentSum;
                }

                if(currentSum==target){
                    return closestSum;
                }else if(currentSum<target) left++;
                else right--;
            }
        }

        return closestSum;
    }

    // TODO ::  Valid Triangle Number

    // pair with given diff
    public int solve(int[] A, int B) {
        int n = A.length;
        Arrays.sort(A);
        int i=0,j=1;
        B = Math.abs(B); // because if a-b == B then b-a==-B => if +B exist as solution then -B also exist as solution
        while(j<n){
            if(A[j]-A[i]==B) return 1;
            else if(A[j]-A[i] < B) j++;
            else i++;
            
            if(i==j) j++; // if start and end are same moves end as both values cant be same
        }
        return 0;
    }
    
    // Sum of Square Numbers
    public boolean judgeSquareSum(int c) {
        long j = (long)Math.sqrt(c);
        if((j*j)==c) return true;
        int i=1;
        while(i<=j){
            long temp = (i*i) + (j*j);
            if(temp==c) return true;
            else if(temp<c) i++;
            else j--;
        }
        return false;
    }

    // Number of Subsequences That Satisfy the Given Sum Condition
    /* 
        Intution : we just need the *number* of subsequences. To track the "min+max <= target" condition
        we can use two pointer after sorting the array. When we met the condition, we can fix the min element
        and number of subsequence will depend upon the rest of elements = 2^(no. of rest elements)
        
        Why sorting sorting won't affect the subsequence ? : bcoz even in the real order the max and min element is same
        irrespective of their position.
        
    */ 
    public int numSubseq(int[] nums, int target) {
        int ans = 0;
        int mod = (int)1e9+7;
        int n = nums.length;
        int l=0,r=n-1;
        int[] pow = new int[n];
        pow[0]=1;
        for(int i=1; i<n; i++) pow[i]=(pow[i-1]*2)%mod;

        Arrays.sort(nums);

        while(l<=r){
            if(nums[l]+nums[r]>target){
                r--;
            }else{
                ans = (ans + pow[r-l])%mod;
                l++;
            }
        }

        return ans;
    }


    // ** 2. Fast and Slow Pointer for In-Place Modification
    // ** Goal: Modify the array without using extra space.
    // Remove Element
    public int removeElement(int[] nums, int val) {
        int j=0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=val){
                nums[j++]=nums[i];
            }
        }
        return j;
    }

    // Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int j=1;
        for(int i=1; i<nums.length; i++){
            if(nums[i]!=nums[i-1]){
                nums[j++]=nums[i];
            }
        }
        return j;
    }

    // Remove Duplicates from Sorted Array II
    // approach 1 :: mark the thrice(and more) repeating character with INT_MAX and then skip them while storing using two pointer
    // cons :: two pass solution
    public int removeDuplicates2(int[] nums) {
        int j = 0;
        int i = 2;
        // mark the elements which are repeating with INT_MAX
        while(i<nums.length){
            if(nums[i]==nums[i-1] && nums[i-1]==nums[i-2]){
                int tmp = nums[i];
                while(i<nums.length && nums[i]==tmp) nums[i++]=Integer.MAX_VALUE;
            }else i++;
        }

        // using two pointers skip storage of those elements
        for(i=0; i<nums.length; i++){
            if(nums[i]!=Integer.MAX_VALUE){
                nums[j++]=nums[i];
            }
        }
        return j;
    }
    // approach 2 : simply check if in nums[i] != nums[j-2]  e.g old idx not equal to new's second last
    public int removeDuplicates3(int[] nums) {
        int j=2;
        for(int i=2; i<nums.length; i++){
            if(nums[i]!=nums[j-2]){
                nums[j++]=nums[i];
            }
        }
        return j;
    }

    // TODO Move Zeroes

    // TODO Partition Array based on some predicate



    // ** 3. Segregation Based on Identity or Property
    // ** Goal: Move similar elements together without explicitly sorting.
    //Segregate 0s and 1s
    void segregate0and1(int[] arr) {
        int i=0,j=arr.length-1;
        while(i<j){
            if(arr[i]==0) i++;
            else if(arr[j]==1) j--;
            else{
                int temp = arr[j];
                arr[j]=arr[i];
                arr[i]=temp;
                i++;
                j--;
            }
        }
    }
    // is supposed to be right but giving TLE on gfg for last test case
    // therefore this
    void segregate0and1Optimised(int[] arr) {
        int i=0,j=arr.length-1;
        while(i<j){
            if(arr[i]==0) i++;
            else{
                if(arr[j]==0){
                    int temp=arr[j];
                    arr[j]=arr[i];
                    arr[i]=temp;
                    i++;
                    j--;
                }else{
                    j--;
                }
            }
        }
    }

    //  dutch national flag algorithm
    // sort array of 0,1 and 2
    // sort colors
    // intution :: do with the help of three pointer, use mid(1) for moving and put all low(1) to left and high(2) to right
    public void sortColors(int[] nums) {
        int n=nums.length;
        int low=0,mid=0,high=n-1;

        while(mid<=high){
            if(nums[mid]==1) mid++;
            else if(nums[mid]==2){
                int temp = nums[mid];
                nums[mid]=nums[high];
                nums[high]=temp;
                high--;
            }else if(nums[mid]==0){
                int temp = nums[low];
                nums[low]=nums[mid];
                nums[mid]=temp;

                mid++;
                low++;
            }
        }
    }

    // Sort Array By Parity (Separate Even and Odd Numbers)
    public int[] sortArrayByParity(int[] nums) {
        int i=0,j=nums.length-1;
        while(i<=j){
            if(nums[i]%2==0) i++;
            else if(nums[j]%2==1) j--;
            else {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j--;
            }
        }
        return nums;
    }


    // ** 4. Two Pointers on Ends – Greedy Movement
    // ** Goal: Optimize something like score, sum, or permutation using greedy logic.

    // Boats to Save People
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int i=0,j=people.length-1;
        int ans = 0;
        while(i<=j){
            
            // statement from Qs: Each boat carries at most *two* people at the same time, provided the sum of the weight of those people is at most limit.
            if(people[j]+people[i]<=limit){
                i++;
            }
            ans++;
            j--;
            
        }
        return ans;
    }

    // Minimize Maximum Pair Sum in Array
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int i=0,j=nums.length-1;
        int ans = Integer.MIN_VALUE;
        while(i<j){
            ans = Math.max(ans,nums[i++]+nums[j--]);
        }
        return ans;
    }

    // Next Permutation
    /*
     *  Intution :: 
     * Example: 4325413 -> we can only change the last two numbers and have 432531
    What if it was 432531 in the first place? 31 cannot be increased.
    Lets try 531 - still no
    2531 - this can be incrased - the smallest number that can be used to incrase the 2 is 3. so for now we have 3521.
    Next we want to minimize 3521 - thats easier - just sort the numbers to the right of 3 - 3125. So the unswer is 4323125
     */
    public void nextPermutation(int[] nums) {
        if(nums.length<1) return;

        int j=nums.length-1;
        int i = j-1;
        while(i>=0 && nums[i]>=nums[j]){
            i--;
            j--;
        }

        if(i==-1){
            i=0;j=nums.length-1;
            while(i<j){
                int t = nums[i];
                nums[i]=nums[j];
                nums[j]=t;
                i++;j--;
            }
            return;
        }
        j = nums.length-1;

        while(j>=0 && nums[j]<=nums[i]) j--;


        int temp = nums[i];
        nums[i]=nums[j];
        nums[j]=temp;

        j=nums.length-1;
        i++;

        while(i<j){
            int t = nums[i];
            nums[i]=nums[j];
            nums[j]=t;
            i++;
            j--;
        }
    }
    // it's follow up : Minimum Adjacent Swaps to Reach the Kth Smallest Number

    // Bag of Tokens
    // hint :: Greedy: Buy tokens with small values, Sell tokens with large values to increase power!
    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        if(tokens.length <=0 || power<tokens[0]) return 0;
        int score = 0,ans=0;
        int i=0,j=tokens.length-1;
        while(i<=j){
            if(power>=tokens[i]){
                power-=tokens[i];
                score++;
                i++;
            }else if(score>0){
                score--;
                power+=tokens[j];
                j--;
            }else break;

            ans = Math.max(ans,score);

        }
        return ans;
    }

    // ** 5. Merging or Comparing Two Arrays (Two Lists-Strings)
    // **  Two pointers move independently across two sorted arrays or strings.
    // ** Goal: Merge, intersect, or compare.

    // TODO : Merge Sorted Array

    // TODO : Intersection of Two Arrays II

    // TODO : Compare Version Numbers

    // TODO : Is Subsequence

    // ** 6. Palindrome Checking / Symmetry Problems
    // **Goal: Check symmetric properties by moving from both ends.

    // Reverse Vowels of a String
    boolean isVowel(char c){
        if(c=='a' || c=='e' || c=='i' || c=='o' || c=='u' || c=='A' || c=='E' || c=='I' || c=='O' || c=='U')
            return true;
        return false;
    }
    public String reverseVowels(String s) {
        char[] arr = s.toCharArray();
        int i=0,j=arr.length-1;
        while(i<j){
            if(!isVowel(arr[i])) i++;
            else if(!isVowel(arr[j])) j--;
            else {
                char temp = arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
                i++;
                j--;
            }
        }
        return new String(arr);
    }

    // Valid Palindrome I was easy therefore not adding it here
    // Valid Palindrome II
    boolean isPalindrome(char[] arr,int i, int j){
        while(i<j){
            if(arr[i]!=arr[j]) return false;
            i++;j--;
        }
        return true;
    }
    public boolean validPalindrome(String s) {
        if(s.length()<=2) return true;

        int i=0,j=s.length()-1;
        char[] arr = s.toCharArray();
        while(i<j){
            if(arr[i]!=arr[j]){
                return (isPalindrome(arr,i+1,j) || isPalindrome(arr,i,j-1));
                    
            }
            i++;
            j--;
        }
        return true;
    }

    // TODO : Backspace String Compare (can be solved with two pointers from back)


    // *** 7. miscellaneous problems (Not so classic)
    // Rotate Array
    // simple soltion using O(N) space
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        if(k==n) return;
        if(k>n) k = k%n;
        int[] temp = new int[k];
        int idx = 0;
        for(int i=n-k; i<n; i++){
            temp[idx++]=nums[i];
        }
        for(int j=n-k-1; j>=0; j--){
            nums[j+k]=nums[j];
        }
        for(int i=0; i<k; i++){
            nums[i]=temp[i];
        }
    }
    // using reverse with two pointers (no extra space)
    /*
     * intution : first reverse the whole array then reverse the first k and then the last n-k
    */
    public void reverse(int[] nums,int start,int end){
        while(start<end){
            int temp = nums[start];
            nums[start]=nums[end];
            nums[end]=temp;
            start++;
            end--;
        }
    }
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        if(k==n) return;
        if(k>n) k = k%n;
        reverse(nums,0,n-1);
        reverse(nums,0,k-1);
        reverse(nums,k,n-1);
    }

    // Pancake Sorting
    // intution image : https://assets.leetcode.com/users/images/2cdc0679-378a-4538-bc2c-a2667b16cb91_1598705157.840506.png
    int maxIdx(int[] arr,int k){
        int maxEleIdx = k;
        int i=0;
        while(i<=k){
            if(arr[i]>arr[maxEleIdx]) maxEleIdx=i;
            i++;
        }
        return maxEleIdx;
    }
    void reverse(int[] arr,int k){
        int i=0;
        while(i<k){
            int temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;

            i++;
            k--;
        }
    }
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> ans = new ArrayList<>();
        int i=arr.length-1;
        while(i>=0){
            int maxEleIdx = maxIdx(arr,i);
            
            ans.add(maxEleIdx+1);
            ans.add(i+1);
            
            reverse(arr,maxEleIdx);
            reverse(arr,i);

            i--;
        }
        return ans;
    }




    // *** Some final observations
    /* 

    * * * Two-pointers are not ideal when:
    * 1. The array is not sorted and sorting would disturb the original positions.
    * 2. You need to count pairs with a given sum efficiently without sorting.
    * 3. You want to handle duplicates and frequency correctly (e.g., number of ways).


    * * * These problems are often better solved using: 
    * 1. Hash Maps (for frequency counts)
    * 2. Prefix Sums
    * 3. Combinatorics


    * * * Examples That Are NOT Two-Pointer Based
    * 1. Count of Pairs with Target Sum (Unsorted Array)
    * 2. Count the number of subarrays whose sum equals k.

    */

}
