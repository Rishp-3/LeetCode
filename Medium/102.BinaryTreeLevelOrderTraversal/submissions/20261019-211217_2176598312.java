/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        q.offer(null);

        List<Integer> in = new ArrayList<>();

        while (!q.isEmpty()) {
            TreeNode f = q.poll();

            if (f == null) {
                ans.add(in);

                if (q.isEmpty()) {
                    break;
                }

                in = new ArrayList<>();
                q.offer(null);
                continue;
            }

            in.add(f.val);

            if (f.left != null) {
                q.offer(f.left);
            }

            if (f.right != null) {
                q.offer(f.right);
            }
        }

        return ans;
    }
}