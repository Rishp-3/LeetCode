class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            // current container ki height & width
            int h = height[left] < height[right] ? height[left] : height[right];
            int width = right - left;

            // area update
            int area = h * width;
            if (area > maxArea) {
                maxArea = area;
            }

            // chhoti height wali side ko move karo
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}