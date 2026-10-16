class Solution {
    public int maxArea(int[] height) {

        int l=0 ,r= height.length-1;
        int maxp=0;
        while(l<r){
            int ln=height[l] , rn=height[r];
            int low =ln<rn?ln:rn;
            maxp=low*(r-l) >maxp?low*(r-l):maxp;
            if (ln < rn) {
                while (l < r && height[++l] <= low);
            } else {
                while (l < r && height[--r] <= low);
            }
        }
        return maxp;
    }
}