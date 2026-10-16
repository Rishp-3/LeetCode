class Solution {
    public void sortColors(int[] nums) {
        int[] cou=new int[3];
        int n=nums.length;
        for(int i=0;i<n;i++){
            cou[nums[i]]++;
        }
        for(int i=0;i<n;i++){
            for (int k=0;k<cou[0];k++){
                nums[i]=0;
                i++;
            }
            for (int k=0;k<cou[1];k++){
                nums[i]=1;
                i++;
            }
            for (int k=0;k<cou[2];k++){
                nums[i]=2;
                i++;
            }
        }
    }
}