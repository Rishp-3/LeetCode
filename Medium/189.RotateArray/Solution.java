class Solution {
    public void rotate(int[] nums, int k) {
        int s=nums.length;
        int r=k%s;
        int[] arr=new int[r];
        for (int i=0; i<r;i++){
            arr[i]=nums[s-i-1];
        }
        for (int i=s-1;i>=r;i--){
            nums[i]=nums[i-r];
        }
        for(int i=0;i<r;i++){
            nums[r-i-1]=arr[i];
        }
    }
}