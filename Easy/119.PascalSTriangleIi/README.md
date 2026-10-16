# 119. Pascal's Triangle II

- **LeetCode:** https://leetcode.com/problems/pascals-triangle-ii/
- **Difficulty:** Easy
- **Topics:** Array, Dynamic Programming

## Description

Given an integer `rowIndex`, return the `rowIndex^th` (**0-indexed**) row of the **Pascal's triangle**.

In **Pascal's triangle**, each number is the sum of the two numbers directly above it as shown:

![](https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif)

**Example 1:**

```text
Input: rowIndex = 3
Output: [1,3,3,1]

```

**Example 2:**

```text
Input: rowIndex = 0
Output: [1]

```

**Example 3:**

```text
Input: rowIndex = 1
Output: [1,1]

```

**Constraints:**

- `0 <= rowIndex <= 33`

**Follow up:** Could you optimize your algorithm to use only `O(rowIndex)` extra space?
