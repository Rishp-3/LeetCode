class Solution {
    public void sortColors(int[] nums) {
        int a=0,b=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0){
                a++;
            }else if(nums[i]==1){
                b++;
           }
        }
        for(int i=0;i<nums.length;i++){
            if(a>0){
                nums[i]=0;
                a--;
            }
           else if(b>0){
            nums[i]=1;
            b--;
           }else{
            nums[i]=2;
           }
        }
    }
}