class Solution {
    public int trap(int[] height) {
        int n=height.length;
        int[] rm=new int[n];
        rm[n-1]=height[n-1];
        for(int i=n-2;i>=0;i--){
            rm[i]=Math.max(rm[i+1] , height[i]);
        }
        int lm=height[0];
        int t=0;
        for(int i=0;i<n;i++){
            lm=Math.max(lm ,height[i]);
            int min=Math.min(rm[i],lm);
            t+=min-height[i];
        }
        return t;
    }
}