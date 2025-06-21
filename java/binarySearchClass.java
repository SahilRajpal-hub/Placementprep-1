package Placementprep.java;

public class binarySearchClass {

    // *** TYPE-1: BASIC IMPLEMENTATION
    // ** basic binary search
    public int binarySearch(int[] nums, int target) {
        int low = 0, high=nums.length-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            if(nums[mid]==target) return mid;
            else if(nums[mid]<target) low=mid+1;
            else high=mid-1;
        }
        return -1;
    }

    // ** first and last occurence of an element in a sorted array
    public int[] searchRange(int[] nums, int target) {
        int idx = binarySearch(nums,target);
        int[] ans = new int[2];
        if(idx==-1){
            ans[0]=-1;
            ans[1]=-1;
            return ans;
        }
        int start = idx,end=idx;
        while(start-1>=0 && nums[start-1]==target) start--;

        while(end+1<=nums.length-1 && nums[end+1]==target) end++; 

        ans[0]=start;
        ans[1]=end;
        return ans;
    }

    // ** count of an element in a sorted array
    public int countOfElement(int[] nums, int target) {
        int idx = binarySearch(nums,target);
        if(idx==-1){
            return 0;
        }
        int start = idx,end=idx;
        while(start-1>=0 && nums[start-1]==target) start--;
        if(nums[0]==target) start=0;

        while(end+1<=nums.length-1 && nums[end+1]==target) end++; 
        if(nums[nums.length-1]==target) end=nums.length-1;

        return end-start+1;
    }


    // *** TYPE-2: ROTATED SORTED ARRAY
    // ** Number of times a array is rotated OR Find minimum element in rotated sorted array
    public int findMin(int[] nums) {
        int n = nums.length;
        int low =0 ,high=n-1;

        while(low<=high){
            int mid = low + (high-low)/2;
            if(nums[mid]<nums[(mid+1)%n] && nums[mid]<nums[(mid-1+n)%n]){
                return nums[mid];
            }
            else if(nums[mid]<nums[high]) high=mid-1;
            else low=mid+1;
        }
        return -1;
    }

    // ** Find an element in a rotated sorted array / Search in Rotated Sorted Array
    public int binarySearch(int[] nums,int target,int l,int h){
        while(l<=h){
            int m = l + (h-l)/2;
            if(nums[m]==target) return m;
            else if(nums[m]<target) l=m+1;
            else h=m-1;
        }
        return -1;
    }
    public int search(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high=n-1;
        int idx = -1;
        while(low<=high){
            int mid = low + (high-low)/2;
            if(nums[mid]<=nums[(mid+1)%n] && nums[mid]<=nums[(mid+n-1)%n]){
                idx=mid;
                break;
            }else if(nums[mid]>nums[high]) low=mid+1;
            else high=mid-1;
        }
        if(idx==-1) return -1;

        if(nums[n-1]>=target) return binarySearch(nums,target,idx,n-1);

        return binarySearch(nums,target,0,idx-1);
    }



    // *** TYPE-3: TAKING STEPS ACCORDING TO BINARY SEARCH
    // ** Find the Smallest Divisor Given a Threshold
    int getDivisionSum(int[] nums,double divisor){
        int res = 0;
        for(int i:nums){
            res += (int)Math.ceil(i/divisor);
        }
        return res;
    }
    public int smallestDivisor(int[] nums, int threshold) {
        long sum = 0;
        int ans = Integer.MAX_VALUE;
        for(int i:nums) sum+=i;
        long i=1,j=sum;
        while(i<=j){
            double m = (double) (i + (j-i)/2);
            int divisionSum = getDivisionSum(nums,m);
            
            if(divisionSum>threshold){
                i=(long) (m+1);
            }else{
                ans = (int)Math.min(ans,m);
                j=(long) (m-1);
            }
        }
        return ans;
    }



    // additional
    // ** 2 sum
    public int binarySearchtwoSun(int[] numbers,int i,int j,int target){
        int start = i, end=j;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(numbers[mid]==target) return mid;
            else if(numbers[mid]>target) end=mid-1;
            else start=mid+1;
        }
        return -1;
    }
    public int[] twoSum(int[] numbers, int target) {
        int[] ans = new int[2];
        for(int i=0; i<numbers.length-1; i++){
            int si = binarySearchtwoSun(numbers,i+1,numbers.length-1,target-numbers[i]);
            if(si != -1){
                ans[0]=i+1;
                ans[1]=si+1;
                return ans;
            }
        }
        return ans;
    }

    

}

