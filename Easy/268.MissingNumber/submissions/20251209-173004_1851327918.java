class Solution {
    public int missingNumber(int[] nums) {
        for(int i=0 ; i<nums.length;i++){
            int ma=0;
            for(int j=0 ; j<nums.length;j++){
                if(i==nums[j]){
                    ma=1;
                    break;
                }
            }
            
            if(ma==0){
                return i;
            }
        }
        int max= nums.length;
        return max;
    }
    
}