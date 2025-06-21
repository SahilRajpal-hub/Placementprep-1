package Placementprep.java;

public class DynamicProgramming {
    // ***? Link -> https://leetcode.com/discuss/study-guide/1437879/Dynamic-Programming-Patterns

    // Pattetn-1. 0-1 Knapsack

    //   * => Main Code
    static int knapsack(int W, int val[], int wt[]) {
        int n = val.length;
        int dp[][] = new int[n+1][W+1];
        for(int i=1; i<=n; i++){
            for(int j=1; j<=W; j++){
                if(j>=wt[i-1]){
                    dp[i][j] = Math.max(dp[i-1][j], val[i-1] + dp[i-1][j-wt[i-1]]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n][W];
    }

    //   ? Subset Sum Problem -> Given an array of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum.
    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;
        boolean[][] dp = new boolean[n+1][sum+1];
        
        for(int i=0; i<=n; i++) dp[i][0]=true;
        
        for(int i=1; i<=n; i++){
            for(int j=1; j<=sum; j++){
                if(j>=arr[i-1]){
                    dp[i][j] = dp[i-1][j-arr[i-1]] || dp[i-1][j];
                }else dp[i][j] = dp[i-1][j];
            }
        }
        
        return dp[n][sum];
    }

    //   ? Equal sum partition ->  Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i:nums) sum+=i;
        if(sum%2==1) return false;
        else return isSubsetSum(nums,sum/2);
    }

    //   ? count of subset sum -> Given an array arr[] of length N and an integer X, the task is to find the number of subsets with a sum equal to X.
    // OLD HINT :: j is starting from 0 as array may contain zero as elements which is diff sub set than empty set, so count that too
    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n+1][target+1];
        dp[0][0]=1;
        
        for(int i=1; i<=n; i++){
            for(int j=0; j<=target; j++){
                if(j>=nums[i-1]){
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i-1]];
                }else dp[i][j] = dp[i-1][j];
            }
        }
        return dp[n][target];
    }

    //  ? minimum subset sum diff. ->
    //   * => code
    // 1. +ve ele only


    //  ? minimum subset sum diff. ->
    //   * => code
    // 2. Integers
}
