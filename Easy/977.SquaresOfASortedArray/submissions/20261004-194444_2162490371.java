class Solution {
    public int[] sortedSquares(int[] nums) {
        int n=nums.length;
        int[] sqr=new int[n];
        for(int i=0;i<n;i++){
            int q=nums[i];
            sqr[i]=q*q;
        }
        Arrays.sort(sqr);
        return sqr;
    }
}