class Solution {
    public int maxArea(int[] height) {
        int l=0;
        int r= height.length-1;
        int maxp=0;
        while(l<r){
            int ln=height[l];
            int rn=height[r];
            int low =Math.min(ln,rn);
            maxp=Math.max(low*(r-l) ,maxp);
            if(ln<rn){
                l++;
            }else{
                r--;
            }
        }
        return maxp;
    }
}