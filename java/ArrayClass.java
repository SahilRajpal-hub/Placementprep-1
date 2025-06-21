package Placementprep.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayClass {    


    // ** TOPIC : Prefix and suffix

    // divide array into 2 subarray with equal sum
    public static boolean canBeDivided(int[] arr) {
        int totalSum = Arrays.stream(arr).sum();

        // If total sum is odd, we can't divide it equally
        if (totalSum % 2 != 0) {
            return false;
        }

        int targetSum = totalSum / 2;
        int currentSum = 0;

        // Try to find a point where left sum equals right sum
        for (int i = 0; i < arr.length - 1; i++) {
            currentSum += arr[i];
            if (currentSum == targetSum) {
                return true;
            }
        }

        return false;
    }

    // maximum sum of a subarray 
    // O(n*n)
    int maxSubarraySum(int[] arr) {
        int ans = Integer.MIN_VALUE;
        int n = arr.length;
        
        for(int i=0; i<n; i++){
            int temp = 0;
            for(int j=i; j<n; j++){
                temp += arr[j];
                 ans = Math.max(ans,temp);
            }
        }
        return ans;
    }
    // kadane's algorithm
    int maxSubarraySum2(int[] arr) {
        int prefix = 0;
        int ans = Integer.MIN_VALUE;
        for(int i:arr){
            prefix += i;
            
            ans = Math.max(ans,prefix);
            
            if(prefix<0) prefix = 0;
        }
        return ans;
    }

    // Maximum Difference between 2 element such that larger element appears after the smaller number
    // SAME AS -> Best Time to Buy and Sell Stock
    // naive approach
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int ans = 0;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                ans = Math.max(ans,prices[j]-prices[i]);
            }
        }
        return ans;
    }
    // optimised approach
    public int maxProfitOptimised(int[] prices) {
        int n = prices.length;
        int ans = 0;
        
        int prev_max = prices[n-1];
        for(int i=n-2; i>=0; i--){
            ans = Math.max(ans,prev_max-prices[i]);
            prev_max = Math.max(prev_max,prices[i]);
        }
        return ans;
    }
    // there exist a dp solution also

    //Maximum prefix sum for a given range
    public List<Integer> maxPrefixes(List<Integer> arr, List<Integer> leftIndex,List<Integer> rightIndex) {

        List<Integer> prefixSum = new ArrayList<Integer>();
        prefixSum.add(arr.get(0));
        for(int i=1; i<arr.size(); i++) prefixSum.add(arr.get(i)+prefixSum.get(i-1));
        
        List<Integer> ans = new ArrayList<>();
        for(int i=0; i<leftIndex.size(); i++){

            // this loop will run for every query
            int maxi = Integer.MIN_VALUE;

            for(int j=leftIndex.get(i); j<=rightIndex.get(i); j++){
                if(leftIndex.get(i)==0){
                    // if left index is 0 then it is a normal max prefix sum problem
                    maxi = Math.max(maxi, prefixSum.get(j));
                }else{
                    // prefix sum when start at index i is prefixSum[i]-prefixSum[i-1]
                    maxi = Math.max(maxi, prefixSum.get(j)-prefixSum.get(leftIndex.get(i)-1));
                }
            }
            ans.add(maxi);
        }
        
        return ans;
    }

    // Equal Sums : https://www.geeksforgeeks.org/problems/equal-sums4801/
    // gfg has some bugs for this question so not tested properly
    public ArrayList<Integer> equalSum(ArrayList<Integer> ar) {
        Integer[] arr = ar.toArray(new Integer[0]);
        int n = arr.length;
        int[] prefixSum = new int[n];
        int[] suffixSum = new int[n];
        
        prefixSum[0]=arr[0];
        for(int i=1; i<n; i++) prefixSum[i] = prefixSum[i-1]+arr[i];
        
        suffixSum[n-1]=arr[n-1];
        for(int i=n-2; i>=0; i--) suffixSum[i] = suffixSum[i+1]+arr[i];
        
        ArrayList<Integer> ans = new ArrayList<>(Arrays.asList(Integer.MAX_VALUE, -1, -1));
        
        for(int i=0; i<n-1; i++){
            int temp = prefixSum[i]-suffixSum[i+1];
            if(Math.abs(temp) < ans.get(0)){
                ans.set(0,Math.abs(temp));
                ans.set(1,1+i+1);
                if(temp>=0) ans.set(2,2);
                else ans.set(2,1);
            }
        }
      
        return ans;
    }

    // Trapping Rain Water
    // intution :: at any point the water trapper is min(maxLeft[i], maxRight[i]) - height[i]
    public int trap(int[] height) {
        int ans = 0;
        int n = height.length;
        int[] greaterLeft = new int[n];
        int[] greaterRight = new int[n];

        greaterLeft[0]=0;
        greaterRight[n-1]=0;

        for(int i=1; i<n; i++) greaterLeft[i]=Math.max(greaterLeft[i-1],height[i-1]);
        for(int i=n-2; i>=0; i--) greaterRight[i]=Math.max(greaterRight[i+1],height[i+1]);

        for(int i=0; i<n; i++){
            int mn = Math.min(greaterLeft[i],greaterRight[i]);
            if(mn-height[i]>0) ans += (mn-height[i]);
        }

        return ans;
    }
    // optimised approach :: No extra space (2 pass)
    /*
        1. Find the max height index (mhi)
        2. first loop from i=0 to i=mhi
            since right is always going to be bigger so we just need to take care of left index
        3. Same as second step just for right, this time left is always largeer only right need to take care of
    */
    public int trap2(int[] height) {
        int ans = 0;
        int n = height.length;
        
        int largestHeightIdx = 0;
        for(int i=0; i<n; i++) if(height[i]>height[largestHeightIdx]) largestHeightIdx=i;

        int leftMaxHeight=height[0];
        int leftIdx = 1;
        while(leftIdx<largestHeightIdx){
            if(leftMaxHeight - height[leftIdx] > 0){
                ans += (leftMaxHeight - height[leftIdx]);
            }
            leftMaxHeight = Math.max(leftMaxHeight,height[leftIdx]);
            leftIdx++;
        }

        int rightMaxHeight=height[n-1];
        int rightIdx = n-2;
        while(rightIdx>largestHeightIdx){
            if(rightMaxHeight - height[rightIdx] > 0){
                ans += (rightMaxHeight - height[rightIdx]);
            }
            rightMaxHeight = Math.max(rightMaxHeight,height[rightIdx]);
            rightIdx--;
        }

        return ans;
    }
    // it has 2 pointer solution also (1 pass) (saw in gpt, difficult to understand and complexity is same as above one so ignored)

    // Zero Sum Subarrays (count of zero sum sub array)
    /*  
        get the count of prefix sum, if one sum is there more than once (like i th sum j th sum is same) then i to j is 
        actually zero sum subarray 
        i.e. if a sum coming n times it has
        (n-1){i th to all remaining sums} + (n-2){i+1 th to all remaining sum} + ... + 2(last second sum to last sum)
    */
    int count(int n){
        if(n<0) return 0;
        if(n==1) return 1;
        return (n*(n+1))/2;
    }
    public int findSubarray(int[] arr) {
        HashMap<Integer,Integer> mp = new HashMap<>();
        int currSum = 0;
        for(int i=0; i<arr.length; i++){
            currSum += arr[i];
            mp.put(currSum,mp.getOrDefault(currSum,0)+1);
        }
        int ans = 0;
        for(Map.Entry<Integer,Integer> entry: mp.entrySet()){
            if(entry.getKey()==0) ans += entry.getValue();
            ans += count(entry.getValue()-1);
        }
        return ans;
    }
    // opitmised :: One pass solution
    public int findSubarray2(int[] arr) {
        HashMap<Integer,Integer> mp = new HashMap<>();
        int currSum = 0;
        int ans = 0;
        for(int i=0; i<arr.length; i++){
            currSum += arr[i];

            // have we seen currSum before then the freqency of prev occurence of currSum is subset of sum 0
            int prevFreq = mp.getOrDefault(currSum,0);
            if(prevFreq!=0) ans += prevFreq;

            // if currSum is zero than it is also one the answer
            if(currSum==0) ans+=1;


            mp.put(currSum,prevFreq+1);
        }
        
        return ans;
    }

    // Subarray Sum Equals K
    // kind of follow up for above question
    public int subarraySum(int[] arr, int k) {
        HashMap<Integer,Integer> mp = new HashMap<>();
        int currSum = 0;
        int ans = 0;
        for(int i=0; i<arr.length; i++){
            currSum += arr[i];

            // have we seen currSum-k before then the freqency of prev occurence of currSum is subset of sum 0
            int prevFreq = mp.getOrDefault(currSum-k,0);
            if(prevFreq!=0) ans += prevFreq;

            // if currSum is k than it is also one the answer
            if(currSum==k) ans+=1;


            mp.put(currSum,mp.getOrDefault(currSum,0)+1);
        }
        
        return ans;
    }

    // Subarray Sums Divisible by K
    // again a follow up logic is twisted
    public int subarraysDivByK(int[] arr, int k) {
        HashMap<Integer,Integer> mp = new HashMap<>();
        int currSum = 0;
        int ans = 0;
        for(int i=0; i<arr.length; i++){
            currSum += arr[i];
            int mod = currSum%k;
            // becuase -3=7*0 + (-3) and -3=7*(-1) + 4  therefore -3 and 4 both represent same remainder
            // also when you remove subarray with prefix sum -3 you get +3 in your array which makes your 
            // new remainder as 4 + 3 == 7(divisible)
            if(mod<0) mod+=k;

            // if the same remainder has seen before, subtracting that subarray will make this subarray divisible by k
            ans += mp.getOrDefault(mod,0);

            if(currSum%k==0) ans+=1;

            mp.put(mod,mp.getOrDefault(mod,0)+1);
        }
        
        return ans;
    }


    // 3Sum With Multiplicity
    // Note : this can be optimised, not doing right now
    int twoSumCountPairs(int arr[], int target,int start) {
        HashMap<Integer,Integer> mp = new HashMap<>();
        int count=0;
        for(int i=start; i<arr.length; i++){
            count+=mp.getOrDefault(target-arr[i],0);
            mp.put(arr[i],mp.getOrDefault(arr[i],0)+1);
        }
        return count;
    }

    public int threeSumMulti(int[] arr, int target) {
        int ans = 0;
        int mod = 1000000007;
        for(int i=0; i<arr.length; i++){
            ans += twoSumCountPairs(arr,target-arr[i],i+1);
            ans = (ans%mod);
        }   
        return ans;
    }




    // ** TOPIC : SLIDING WINDOW

    // Subarray Product Less Than K
    // again a follow up but this would require sliding window technqiue 
    // prefix sum won't work here as you can store product of big numbers(int or long will overflow)
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int start = 0;
        int end = 0;
        int n = nums.length;
        int prefixProd = nums[0];
        int ans = 0;

        while(end<n && start<n){
            if(prefixProd<k){
                // size of windows is the number of subarray we can make from these elements when end is the last index
                ans += (end-start+1);
                end++;
                if(end<n) prefixProd *= nums[end];
            }else{
                prefixProd /= nums[start];
                start++;
                if(start>end && start<n){
                    end=start;
                    prefixProd=nums[start];
                }
            }

        }

        return ans;

    }

    // Count Subarrays With Score Less Than K
    public long countSubarrays(int[] nums, long k) {
        long prefixSum = nums[0];
        int start=0,end=0,n=nums.length;
        long ans = 0;

        while(end<n){
            long score = prefixSum*(end-start+1);
            if(score<k){
                ans += (end-start+1);
                end++;
                if( end <n ) prefixSum += nums[end];
            }else{
                prefixSum -= nums[start];
                start++;
                if(start>end && start<n){
                    end = start;
                    prefixSum = nums[start];
                }
            }
        }

        return ans;
    }

    // Number of subarrays having sum less than K
    // only gfg article is there so not solving it but quite easy when you have done above 4-5 questions


    // Minimum Size Subarray Sum( minimal length of a subarray whose sum is greater than or equal to target)
    public int minSubArrayLen(int target, int[] nums) {
        int start = 0,end=0,prefixSum=nums[0],n=nums.length,ans=Integer.MAX_VALUE;
        
        while(end<n){
            if(prefixSum<target){
                end++;
                if(end<n) prefixSum += nums[end];
            }else{
                ans = Math.min(ans,(end-start+1));
                prefixSum -= nums[start];
                start++;
                if(start>end && start<n){
                    end=start;
                    prefixSum = nums[start];
                }
            }
        }

        return ans==Integer.MAX_VALUE ? 0 : ans;
    }

    // Minimum Window Substring
    // was stuck at this so skipped for that time (kind of copied it)
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
    
        Map<Character, Integer> required = new HashMap<>();
        for (char c : t.toCharArray()) {
            required.put(c, required.getOrDefault(c, 0) + 1);
        }
    
        Map<Character, Integer> window = new HashMap<>();
        int start = 0, end = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        int formed = 0;
        int requiredSize = required.size();
    
        while (end < s.length()) {
            char c = s.charAt(end);
            window.put(c, window.getOrDefault(c, 0) + 1);
    
            if (required.containsKey(c) && window.get(c).intValue() == required.get(c).intValue()) {
                formed++;
            }
    
            // Try to shrink from the left
            while (start <= end && formed == requiredSize) {
                if (end - start + 1 < minLen) {
                    minLen = end - start + 1;
                    minStart = start;
                }
    
                char leftChar = s.charAt(start);
                window.put(leftChar, window.get(leftChar) - 1);
                if (required.containsKey(leftChar) && window.get(leftChar).intValue() < required.get(leftChar).intValue()) {
                    formed--;
                }
                start++;
            }
    
            end++;
        }
    
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
    
    // Length of Longest Subarray With at Most K Frequency
    // correct format of sliding windows
    public int maxSubarrayLength(int[] nums, int k) {
        int start=0,end=0;
        int n = nums.length,ans=0;
        HashMap<Integer,Integer> mp = new HashMap<>();
        while(end<n){
            // add current element
            mp.put(nums[end],mp.getOrDefault(nums[end],0)+1);
            
            // shrunk the window
            while(mp.get(nums[end])>k){
                int removeEle = nums[start];
                mp.put(removeEle,mp.getOrDefault(removeEle,0)-1);
                start++;
            }

            // update the answer
            ans = Math.max(ans,(end-start+1));
            end++;
        }

        return ans;
    }

    // Count Subarrays Where Max Element Appears at Least K Times
    public int getLargestElement(int[] nums){
        int le = Integer.MIN_VALUE;
        for(int i:nums) le=Math.max(le,i);
        return le;
    }
    public long countSubarrays(int[] nums, int k) {
        int maxElement = getLargestElement(nums);
        int start=0,end=0,n=nums.length;
        long ans = 0;
        int countOfMaxElement = 0;

        while(end<n){
            if(nums[end]==maxElement) countOfMaxElement++;

            while(countOfMaxElement>=k){
                // (e-end) bcoz all the subarray after this will have countOfMaxElement >=k
                ans+=(n-end);
                if(nums[start]==maxElement) countOfMaxElement--;
                start++;
            }
            
            end++;
        }
        return ans;
    }

    // Subarrays with exactly K Different Integers  (Important observation)
    // intution ::
    /*
     *  subarray with exactly k different integer = 
     * subarray with atleast (K+1) different integer - subarray with atleast (K) different integer
     * 
     * here, subarray with atleast (K) different integer = subarray(K) diff integers + subarray(K+1) diff integers + 
     * subarray(K+2) diff integers + .....
     * 
     * we can also do it with atmost K integers also
     * sub(exactly k diff) = subarray(atmost k diff) - subarray(atmost k-1 diff)
     */
    private int countAtMostK(int[] nums, int k) {
        int start = 0, end = 0, count = 0;
        HashMap<Integer, Integer> freq = new HashMap<>();

        for (end = 0; end < nums.length; end++) {
            freq.put(nums[end], freq.getOrDefault(nums[end], 0) + 1);

            if (freq.get(nums[end]) == 1) {
                // new unique element
                k--;
            }

            // shrink window if we have more than k distinct
            while (k < 0) {
                freq.put(nums[start], freq.get(nums[start]) - 1);
                if (freq.get(nums[start]) == 0) {
                    freq.remove(nums[start]);
                    k++;
                }
                start++;
            }

            // all subarrays ending at 'end' with valid start positions
            count += end - start + 1;
        }

        return count;
    }
    public int subarraysWithKDistinct(int[] nums, int k) {
        return countAtMostK(nums,k)-countAtMostK(nums,k-1);
    }

    // 


    // NOTE -> string bases sliding window question is in string class file




    // ** TOPIC : Basic logics

    // Missing number in array
    // first method using sum property
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = (n*(n+1))/2;
        for(int i:nums) sum-=i;
        return sum;
    }
    // second methods using xor {a^a = 0 and a^0=a -> means when all number multiplied i.e. i*nums[i]*n(last elements idx) we will get the last element}
    public int missingNumber2(int[] nums) {
        int n = nums.length;
        int xor = 0;
        for(int i=0; i<n; i++){
            xor ^= i;
            xor ^= nums[i];
        }
        xor^=n;
        return xor;
    }

    // Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i=m-1,j=n-1;
        int k=m+n-1;
        while(i>=0 && j>=0){
            if(nums1[i]>nums2[j]){
                nums1[k--] = nums1[i--];
            }else nums1[k--] = nums2[j--];
        }

        while(j>=0) nums1[k--] = nums2[j--];
        
    }


}

