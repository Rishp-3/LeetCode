class Solution {
    public void sortColors(int[] nums) {
        int[] cou=new int[3];
        int n=nums.length;
        for(int i=0;i<n;i++){
            cou[nums[i]]++;
        }
        for(int i=0;i<n;i++){
            if(cou[0]>0){
                nums[i]=0;
                cou[0]--;
            }
           else if(cou[1]>0){
            nums[i]=1;
            cou[1]--;
           }else{
            nums[i]=2;
           }
        }
    }
}