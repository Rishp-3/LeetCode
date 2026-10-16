class Solution {
    public int removeDuplicates(int[] nums) {
        int n=0;
        int s= nums.length;
        for(int i=1;i<s;i++){
            if(nums[i]==nums[i-1]){
                n++;
                nums[i-n]=nums[i];
            }else if(n>0){
                nums[i-n]=nums[i];
            }
        }
        return s-n;
    }
}