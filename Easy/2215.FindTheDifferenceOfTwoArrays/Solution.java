class Solution {
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        boolean[] in1 = new boolean[2001];
        boolean[] in2 = new boolean[2001];
        for (int x : nums1) in1[x + 1000] = true;
        for (int x : nums2) in2[x + 1000] = true;

        List<Integer> d1 = new ArrayList<>();
        List<Integer> d2 = new ArrayList<>();
        for (int i = 0; i < 2001; i++) {
            if (in1[i] && !in2[i]) d1.add(i - 1000);
            else if (in2[i] && !in1[i]) d2.add(i - 1000);
        }
        return List.of(d1, d2);
    }
}