
import java.util.*;

class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        if (n < 4) return ans;

        Arrays.sort(nums);

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Minimum possible sum
            long min1 = (long) nums[i] + nums[i + 1]
                      + nums[i + 2] + nums[i + 3];

            if (min1 > target) break;

            // Maximum possible sum
            long max1 = (long) nums[i] + nums[n - 1]
                      + nums[n - 2] + nums[n - 3];

            if (max1 < target) continue;

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;

                // Minimum possible sum for current j
                long min2 = (long) nums[i] + nums[j]
                          + nums[j + 1] + nums[j + 2];

                if (min2 > target) break;

                // Maximum possible sum for current j
                long max2 = (long) nums[i] + nums[j]
                          + nums[n - 1] + nums[n - 2];

                if (max2 < target) continue;

                int l = j + 1, r = n - 1;

                while (l < r) {
                    long sum = (long) nums[i] + nums[j]
                             + nums[l] + nums[r];

                    if (sum == target) {
                        ans.add(Arrays.asList(
                            nums[i], nums[j], nums[l], nums[r]
                        ));

                        int leftVal = nums[l];
                        int rightVal = nums[r];

                        while (l < r && nums[l] == leftVal) l++;
                        while (l < r && nums[r] == rightVal) r--;

                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }

        return ans;
    }
}
