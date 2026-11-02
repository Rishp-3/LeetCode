class Solution {
    public int maxArea(int[] height) {
        int l=0, r=height.length-1;
        int s=0;
        while(l<r){
            int mh=Math.min(height[l] , height[r]);
            int w=r-l;
            s=Math.max(s,w*mh);
            if(height[l]>height[r]){
                r--;
            }else{
                l++;
            }
        }
        return s;
    }
}