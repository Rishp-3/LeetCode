class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        int[] st = new int[asteroids.length];
        int top = 0;

        for (int curr : asteroids) {
            boolean alive = true;
            while (alive && top > 0 && st[top - 1] > 0 && curr < 0) {
                int t = st[top - 1];
                if (t < -curr) {
                    top--;
                } else if (t == -curr) {
                    top--;
                    alive = false;
                } else {
                    alive = false;
                }
            }
            if (alive) st[top++] = curr;
        }
        return Arrays.copyOf(st, top);
    }
}