class Solution {
    public void moveZeroes(int[] nums) {
        int s=nums.length;
        int n=0;
        for(int i=0;i<s;i++){
            if(nums[i]==0){
                n++;
            }else if(n>0){
                nums[i-n]=nums[i];
            }
            
        }
        for(int i=0;i<n;i++){
            nums[s-i-1]=0;
        }
    }
}