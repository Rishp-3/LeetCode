class Solution {
    public long maxDifference(int[] nums) {
        int[] m=nums;
        int n=m.length;
        long[] f=new long[n+2];
        for(int i=n-1;i>=0;i--){
            long a=m[i];
            f[i]=a%3==0? a-f[i+1]: a+f[i+1];
            if(i+1<n){
                long s=a+m[i+1];
                f[i]=Math.max(f[i],s%3==0 ? s-f[i+2]: s+f[i+2]);
            }
        }
        return f[0];
    }
}