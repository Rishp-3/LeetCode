class Solution {
    public int[] sortedSquares(int[] nums) {
        int n=nums.length;
        int[] sqr=new int[n];
        for(int i=0;i<n;i++){
            sqr[i]=(int)Math.pow(nums[i],2);
        }
        Arrays.sort(sqr);
        return sqr;
    }
}