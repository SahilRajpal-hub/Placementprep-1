package Placementprep.java;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SlidingWindow {

    // *** 1. Fixed-Size Sliding Window
    // ** Desc : Maintain a window of size k, slide right by one step each time. (Window size is known and constant)
    // ** Keywords :: Compute sum/avg/min/max of each window.

    // * Maximum Sum Subarray of Size K
    public int maximumSumSubarray(int[] arr, int k) {
        int currSum = 0;
        int ans = Integer.MIN_VALUE;
        for(int i=0; i<arr.length; i++){
            currSum += arr[i];
            // window size reached
            if(i+1>=k){
                ans = Math.max(ans,currSum);
                currSum -= arr[i-k+1];
            }
        }
        return ans;
    }




    // *** 2. Variable-Size Window with Condition
    // ** Desc : Expand the window until a condition is met, then shrink it while maintaining the condition (Dynamic window resizing)
    // ** Keywords : Used for: Longest/Shortest subarray problems, substring problems.


    // * Minimum Size Subarray Sum
    // method 1 : can be solved via prefix sum and binary search (TC will be O(NlogN))
    // method 2 : sliding window ||  // ! TC : O(N) {as the inner loop (start) can atmost go from 0 to N in whole iterations} , SC : O(1)
    public int minSubArrayLen(int target, int[] nums) {
        int start = 0,end=0,currSum=0,n=nums.length,ans=Integer.MAX_VALUE;
        
        while(end<n){
            currSum += nums[end++];

            while(currSum>=target){
                ans = Math.min(ans,(end-start));
                currSum -= nums[start];
                start++;
                if(start>end && start<n){
                    end=start;
                    currSum = nums[start];
                }
            }
        }

        return ans==Integer.MAX_VALUE ? 0 : ans;
    }

    // * Longest Substring Without Repeating Characters (Map based)
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> st = new HashSet<>();
        int start=0,end=0,n=s.length()-1,ans=0;
        
        while(end<=n){
            // if character at end pointer already there, remove it
            while(st.contains(s.charAt(end))){
                st.remove(s.charAt(start++));
            }
            // add is set and get the answer
            st.add(s.charAt(end));
            ans = Math.max(ans,end-start+1);
            end++;
        }

        return ans;
    }

    // * Longest Substring with At Most K Distinct Characters (Map based)
    // atMostKChar function is O(1) in both TC and SC as chars can be at most 128 only
	public static int kDistinctChars(int k, String str) {
		HashMap<Character,Integer> mp = new HashMap<>(); // O(1) space as chars cant be more than 128
		int ans = 0;
		int start=0,end=0,n=str.length();

		while(end<n){
			mp.put(str.charAt(end),mp.getOrDefault(str.charAt(end),0)+1);

			while(mp.size()>k){
				mp.put(str.charAt(start),mp.get(str.charAt(start))-1);
                if(mp.get(str.charAt(start))==0) mp.remove(str.charAt(start));
				start++;
			}

			ans = Math.max(ans,end-start+1);
			end++;
		}
		return ans;
	}

    // * Fruits into Baskets (Longest Substring/Subarray with 2 distinct chars) (Map based)
    // used same logic as in above question
    public int totalFruit(int[] fruits) {
        HashMap<Integer,Integer> mp = new HashMap<>(); 
		int ans = 0;
		int start=0,end=0,n=fruits.length;

		while(end<n){
			mp.put(fruits[end],mp.getOrDefault(fruits[end],0)+1);

			while(mp.size()>2){
				mp.put(fruits[start],mp.get(fruits[start])-1);
                if(mp.get(fruits[start])==0) mp.remove(fruits[start]);
				start++;
			}

			ans = Math.max(ans,end-start+1);
			end++;
		}
		return ans;
    }
    
    // TODO * Count Occurrences of Anagrams





    // *** 3. Sliding Window with Frequency Map
    // ** Desc : Use a HashMap or array to track character/element frequency inside the window.
    // ** Keywords : Often used for substring matching, anagrams, pattern search, etc.

    // * Permutation in String (Map Based)
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        HashMap<Character, Integer> s1Count = new HashMap<>();
        HashMap<Character, Integer> s2Count = new HashMap<>();
        
        for (int i = 0; i < s1.length(); i++) {
            s1Count.put(s1.charAt(i), s1Count.getOrDefault(s1.charAt(i), 0) + 1);
            s2Count.put(s2.charAt(i), s2Count.getOrDefault(s2.charAt(i), 0) + 1);
        }
        
        if (s1Count.equals(s2Count)) {
            return true;
        }
        
        int start = 0,end=s1.length(),n=s2.length();
        while(end<n) {
            s2Count.put(s2.charAt(end), s2Count.getOrDefault(s2.charAt(end), 0) + 1);
            s2Count.put(s2.charAt(start), s2Count.getOrDefault(s2.charAt(start),0) - 1);

            if (s2Count.get(s2.charAt(start)) == 0) {
                s2Count.remove(s2.charAt(start));
            }
            
            start++;
            
            if (s1Count.equals(s2Count)) {
                return true;
            }

            end++;
        }
        
        return false;        
    }

    // TODO * Minimum Window Substring

    // * Find All Anagrams in a String
    public List<Integer> findAnagrams(String s, String p) {
        HashMap<Character,Integer> pFreqMp = new HashMap<Character,Integer>();
        for(char c:p.toCharArray()) pFreqMp.put(c,pFreqMp.getOrDefault(c,0)+1);

        HashMap<Character,Integer> sFreqMp = new HashMap<Character,Integer>();
        List<Integer> ans = new ArrayList<>();

        int start=0,end=0,n=s.length();

        while(end<n){
            sFreqMp.put(s.charAt(end), sFreqMp.getOrDefault(s.charAt(end),0)+1);

            if(end-start+1==p.length()){
                if(sFreqMp.equals(pFreqMp)){
                    ans.add(start);
                }
                sFreqMp.put(s.charAt(start), sFreqMp.getOrDefault(s.charAt(start),0)-1);
                if(sFreqMp.get(s.charAt(start))==0) sFreqMp.remove(s.charAt(start));
                start++;
            }

            end++;
        }

        return ans;
    }

    // * Longest Repeating Character Replacement
    // this is O(26*N) , and can be optimised into O(N) just by removing below two line(but I didn't understand the logic)
    /*
                if(mp.get(s.charAt(start))==maxCount) maxCount--;
                maxCount = getMaxFromMap(mp);
     */
    int getMaxFromMap(HashMap<Character,Integer> mp){
        int maxValue = 0;
        for(var i : mp.values()) maxValue = Math.max(maxValue,i);
        return maxValue;
    }
    public int characterReplacement(String s, int k) {
        HashMap<Character,Integer> mp = new HashMap<>();
        int start=0,end=0,n=s.length(),ans=Integer.MIN_VALUE,maxCount=0;
        while(end<n){
            mp.put(s.charAt(end),mp.getOrDefault(s.charAt(end),0)+1);
            maxCount = Math.max(maxCount,mp.get(s.charAt(end)));

            while(end-start+1-maxCount > k){
                if(mp.get(s.charAt(start))==maxCount) maxCount--;
                maxCount = getMaxFromMap(mp);
                mp.put(s.charAt(start),mp.getOrDefault(s.charAt(start),0)-1);
                start++;
            }

            ans = Math.max(ans,end-start+1);

            end++;
        }
        return ans;
    }




    // *** 4. Monotonic Queue / Deque Based
    // ** Desc : Maintain a deque (double-ended queue) in increasing or decreasing order to find max/min in the window.
    // ** Keywords/Use-Case : Used when you need max/min value in a dynamic window efficiently.

    // * Sliding Window Maximum (Deque based)
    // * Intution : Use a deque to maintain the largest element of window
    public int[] maxSlidingWindow(int[] nums, int k) {
        int start=0,end=0,n=nums.length;
        int[] ans = new int[n-k+1];
        int idx = 0;
        Deque<Integer> dq = new LinkedList<>();
        
        while(end<n){
            while(!dq.isEmpty() && dq.peekLast()<nums[end]) dq.pollLast();

            dq.addLast(nums[end]);

            if(end-start+1<k){
                end++;
            }else if(end-start+1==k){
                ans[idx++]=dq.peekFirst();
                if(dq.peekFirst()==nums[start]) dq.pollFirst();
                start++;
                end++;
            }
        }

        return ans;
    }

    // * Shortest Subarray with Sum at Least K
    // ? if no negative elements
    // !! Negative numbers can cause the prefix sum to decrease, breaking the assumption that expanding the window always increases the sum.
    public int shortestSubarray(int[] nums, int k) {
        int currSum=0,start=0,end=0,n=nums.length,ans=Integer.MAX_VALUE;

        while(end<n){
            currSum += nums[end];
            while(currSum>=k){
                ans = Math.min(ans,end-start+1);
                currSum-=nums[start];
                start++;
            }
            end++;
        }
        return ans==Integer.MAX_VALUE ? -1 : ans;
    }
    //  TODO ? if negative elements(real question) (didn't understand it though)
    public int shortestSubarray2(int[] nums, int k) {
        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        Deque<Integer> dq = new LinkedList<>();
        int ans = Integer.MAX_VALUE,end=0;

        while(end<=n) {
            while (!dq.isEmpty() && prefixSum[end] - prefixSum[dq.peekFirst()] >= k) {
                ans = Math.min(ans, end - dq.pollFirst());
            }

            while (!dq.isEmpty() && prefixSum[end] <= prefixSum[dq.peekLast()]) {
                dq.pollLast();
            }

            dq.addLast(end);

            end++;
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    // TODO * First Negative Integer in Every Window of Size K


}
