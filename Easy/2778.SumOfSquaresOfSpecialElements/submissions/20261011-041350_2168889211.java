class Solution {
    public int sumOfSquares(int[] nums) {
        int ans=nums[0]*nums[0];
        int n=nums.length;
        for(int i=2;i<=n;i++){
            if(n%i==0){
                ans+=nums[i-1]*nums[i-1];
            }
        } 
        return ans;       
    }
}