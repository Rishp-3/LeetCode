class Solution {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int lh = height[left], rh = height[right];
            int minHeight = lh < rh ? lh : rh;
            
            // Calculate once
            int currentArea = minHeight * (right - left);
            maxArea = currentArea > maxArea ? currentArea : maxArea;

            // Skip useless heights (practical speedup)
            if (lh < rh) {
                while (left < right && height[++left] <= minHeight);
            } else {
                while (left < right && height[--right] <= minHeight);
            }
        }

        return maxArea;
    }
}