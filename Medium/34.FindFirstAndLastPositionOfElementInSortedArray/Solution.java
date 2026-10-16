class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] ans=new int[2];
        int n=nums.length;
        if(n==0){
            ans[0]=-1;
            ans[1]=-1;
            return ans;
        }
        int left=0;
        int right=n-1;
        int f=-1;
        while(left<=right){
            int mid=(left+right)/2;
            if(nums[mid]==target){
                f=mid;
                break;
            }
            if(nums[mid]>target){
                right=mid-1;
            }else if(nums[mid]<target){
                left=mid+1;
            }
        }
        if(f==-1){
            ans[0]=-1;
            ans[1]=-1;
            return ans;
        }else{
            int first=f;
            int last=f;
            while(first>0 && nums[first-1]==target){
                first--;
            }
            while(last<n-1 && nums[last+1]==target){
                last++;
            }
            ans[0]=first;
            ans[1]=last;
        }
        
        return ans;
    }
}