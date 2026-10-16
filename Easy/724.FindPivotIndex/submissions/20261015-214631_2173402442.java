class Solution {
    public int pivotIndex(int[] nums) {
        int l=0;
        int s=0;
        for(int i=0;i<nums.length;i++){
            s+=nums[i];
        }
        int i=0;
        while(i<nums.length){
            int r=s-l-nums[i];
            if(r==l){
                return i;
            }
            l+=nums[i];
            i++;
        }
        return -1;
    }
}