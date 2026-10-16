class Solution {
    public int trap(int[] height) {
        int s =height.length;
        int[] rmax=new int [s];
        rmax[s-1]=height[s-1];
        for(int i=s-2;i>=0;i--){
            rmax[i]=Math.max(rmax[i+1],height[i]);
        }
        int ans=0;
        int l=height[0];
        for(int i=1;i<s-1;i++){
            l=Math.max(l,height[i]);
            int min=Math.min(l,rmax[i]);
            if(min-height[i]>0){
                ans+=min-height[i];
            }
        }
        return ans;

    }
}