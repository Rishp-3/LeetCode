class Solution {
    public int[] sortedSquares(int[] nums) {
        int n=nums.length;
        int[] sqr=new int[n];
        int l=0;
        int r=n-1;
        for(int i=n-1;i>=0;i--){
            int lr=nums[l]*nums[l];
            int rr=nums[r]*nums[r];
            if(lr>rr){
                sqr[i]=lr;
                l++;
            }else{
                sqr[i]=rr;
                r--;
            }
        }
        return sqr;
    }
}