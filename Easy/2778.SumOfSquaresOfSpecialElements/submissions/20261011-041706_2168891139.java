class Solution {
    public int sumOfSquares(int[] nums) {
        int ans=nums[0]*nums[0];
        for(int i=2;i<=nums.length;i++){
            if(nums.length%i==0){
                ans+=nums[i-1]*nums[i-1];
            }
        } 
        return ans;       
    }
}