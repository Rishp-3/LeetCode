class Solution {
    public int maxArea(int[] height) {
        int l=0 , r=height.length-1;
        int maxw=0;
        while(l<r){
            int h= Math.min(height[l], height[r]);
            int w=r-l;
            int ar=h*w;
            if(ar>maxw)maxw=ar;
            if(height[l]<height[r]){
                l++;
            }else{
                r--;
            }
        } 

        return maxw;
    }
}