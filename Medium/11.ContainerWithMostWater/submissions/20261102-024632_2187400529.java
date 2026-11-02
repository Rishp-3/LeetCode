class Solution {
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int best = 0;

        while (l < r) {
            int hl = height[l], hr = height[r];
            int h = Math.min(hl, hr);
            best = Math.max(best, h * (r - l));

            if (hl < hr) {
                while (l < r && height[l] <= h) l++;
            } else {
                while (l < r && height[r] <= h) r--;
            }
        }
        return best;
    }
}