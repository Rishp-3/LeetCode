class Solution {
    public int countConsistent(int[] nums) {
        int n=nums.length;
        int c=0;
        for(int i=0;i<n;i++){
            int a=nums[i] ,m=nums[i];
            for(int j=i;j<n;j++){
                a &=nums[j];
                m=Math.min(m,nums[j]);
                if(a==m)c++;
            }
        }
        return c;
    }
}