package Placementprep.java;

public class DPRecursion {
    // ** count of subset sum 
    int solve(int[] nums, int target,int j){
        if(j==0){
            // we are at the last element , we have two options
            int cnt = 0;
            // excluding last element as target is already reached
            if(target==0) cnt++;  
            // including last element as target needs last element to complete
            if(target==nums[0]) cnt++;

            return cnt;
        }
        
        if(nums[j]>target) return solve(nums,target,j-1);
        
        return solve(nums,target,j-1) + solve(nums,target-nums[j],j-1);
    }
    public int perfectSum(int[] nums, int target) {
        int ans = solve(nums,target,nums.length-1);
        // for(int i:nums) if(i==target) ans++;
        return ans;
    }
}
