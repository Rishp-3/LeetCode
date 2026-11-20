# 16. 3Sum Closest

- **LeetCode:** https://leetcode.com/problems/3sum-closest/
- **Difficulty:** Medium
- **Topics:** Array, Two Pointers, Sorting

## Description

You are given an integer array `nums` of length `n` and an integer `target`.

Find three integers at **distinct indices** in `nums` such that the sum is **closest** to `target`.

Return the sum of the three integers.

You may assume that each input would have **exactly** one solution.

**Example 1:**

```text

Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

```

**Example 2:**

```text

Input: nums = [0,0,0], target = 1
Output: 0
Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).

```

**Constraints:**

- `3 <= nums.length <= 500`
- `-1000 <= nums[i] <= 1000`
- `-10^4 <= target <= 10^4`
