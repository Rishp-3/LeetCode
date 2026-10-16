class Solution {
    public int finalElement(int[] nums) {
        int n=nums.length;
        if(n==1) return nums[0];
        int me=nums[0];
        for(int i=1;i<n;i++){
            if(nums[i]>me){
                me=nums[i];
            }
        }
        return Math.max(nums[0] , nums[n-1]);
    }
}