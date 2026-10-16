class Solution {
    public int pivotIndex(int[] nums) {
        int l=0;
        int s=0;
        int i=0;
        while(i<nums.length){
            s+=nums[i];
            i++;
        }
        i=0;
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