# 4113. Game of Divisible Sums

- **LeetCode:** https://leetcode.com/problems/game-of-divisible-sums/
- **Difficulty:** Medium
- **Topics:** -

## Description

Alice and Bob are playing a game with an integer array `nums`. Alice goes first.

On each turn, the current player takes and removes either the **leftmost element** or, if at least two elements remain, the **two leftmost elements**.

The **sum** of the elements taken in that turn determines who plays next:

- If the sum is divisible by 3, the other player plays next.
- Otherwise, the same player plays again.

The **score** of a player is the sum of the values of all elements that player takes.

Both players play optimally, with each player trying to **maximize** their own score.

The game ends when all elements have been taken.

Return Alice's score **minus** Bob's score.

**Example 1:**

**Input:** nums = [3,6,4]

**Output:** 5

**Explanation:**

One sequence of optimal turns is as follows:

- Alice takes the two leftmost elements. Their sum is `3 + 6 = 9`, which is divisible by 3, so Bob plays next. Alice's score is now 9, and the remaining array is `[4]`.
- Bob takes the only remaining element. His score is 4, and the game ends.

Alice's score minus Bob's score is `9 - 4 = 5`, so the answer is 5.

**Example 2:**

**Input:** nums = [6,1,2,1,2]

**Output:** 12

**Explanation:**

One sequence of optimal turns is as follows:

- Alice takes the two leftmost elements. Their sum is `6 + 1 = 7`, which is not divisible by 3, so Alice plays again. Her score is now 7, and the remaining array is `[2, 1, 2]`.
- Alice takes the leftmost element. Its value is 2, which is not divisible by 3, so Alice plays again. Her score is now `7 + 2 = 9`, and the remaining array is `[1, 2]`.
- Alice takes both remaining elements. Her score is now `9 + 1 + 2 = 12`, and the game ends.

Bob takes no elements, so his score is 0. Alice's score minus Bob's score is `12 - 0 = 12`, so the answer is 12.

**Example 3:**

**Input:** nums = [3,3,4,5]

**Output:** -3

**Explanation:**

One sequence of optimal turns is as follows:

- Alice takes the two leftmost elements. Their sum is `3 + 3 = 6`, which is divisible by 3, so Bob plays next. Alice's score is now 6, and the remaining array is `[4, 5]`.
- Bob takes both remaining elements. His score is `4 + 5 = 9`, and the game ends.

Alice's score minus Bob's score is `6 - 9 = -3`, so the answer is -3.

**Constraints:**

- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^9`
