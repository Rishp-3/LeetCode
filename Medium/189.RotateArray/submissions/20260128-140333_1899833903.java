class Solution {
    public void rotate(int[] nums, int k) {
        int n=nums.length;
        k=k%n;
        int[] ak=new int[k];
        for(int i=n-k , j=0 ; i<n; i++,j++){
            ak[j]=nums[i];
        }
        for(int i=n-1 , j=n-k-1;j>=0;i--,j--){
            nums[i]=nums[j];
        }
        for(int i=0;i<k;i++){
            nums[i]=ak[i];
        }
    }
}