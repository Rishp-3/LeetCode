# 82. Remove Duplicates from Sorted List II

- **LeetCode:** https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
- **Difficulty:** Medium
- **Topics:** Linked List, Two Pointers

## Description

You are given the `head` of a **sorted** linked list.

Delete all nodes that have **duplicate** numbers, leaving only **distinct** numbers from the original list.

Return the linked list **sorted** as well.

**Example 1:**

![](https://assets.leetcode.com/uploads/2021/01/04/linkedlist1.jpg)

```text

Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]

```

**Example 2:**

![](https://assets.leetcode.com/uploads/2021/01/04/linkedlist2.jpg)

```text

Input: head = [1,1,1,2,3]
Output: [2,3]

```

**Constraints:**

- The number of nodes in the list is in the range `[0, 300]`.
- `-100 <= Node.val <= 100`
- The list is guaranteed to be **sorted** in ascending order.
