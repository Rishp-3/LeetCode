class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int len = flowerbed.length;

        for (int i = 0; i < len; i++) {
            boolean emptyLeft  = (i == 0) || flowerbed[i - 1] == 0;
            boolean emptyRight = (i == len - 1) || flowerbed[i + 1] == 0;

            if (flowerbed[i] == 0 && emptyLeft && emptyRight) {
                flowerbed[i] = 1;
                n--;
                if (n <= 0) return true;
            }
        }

        return n <= 0;
    }
}