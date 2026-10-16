class Solution {
    public int[] runningSum(int[] nums) {
        int s=nums.length;
        int[] out=new int[s];
        out[0]=nums[0];
        for(int i=1;i<s;i++){
            out[i]=out[i-1]+nums[i];
        }
        
        return out;
    }
}