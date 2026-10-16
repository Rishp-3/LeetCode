import java.util.HashSet;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true; // duplicate mila
            }
            set.add(num);
        }
        return false; // koi duplicate nahi
    }
}
