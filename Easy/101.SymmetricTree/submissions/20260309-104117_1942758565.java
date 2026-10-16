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
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }
        Queue<TreeNode> left=new LinkedList<>();
        Queue<TreeNode> right=new LinkedList<>();
        left.add(root.left);
        right.add(root.right);

        while(!left.isEmpty() && !right.isEmpty()){
            TreeNode currLeft=left.remove();
            TreeNode currRight=right.remove();
            if(currLeft==currRight){}
            else if(currLeft==null){
                return false;
            }
            else if(currRight==null){
                return false;
            }
            else if(currLeft.val==currRight.val){
                left.add(currLeft.left);
                left.add(currLeft.right);
                right.add(currRight.right);
                right.add(currRight.left);
            }else{
                return false;
            }
        }
        if(left.isEmpty() && right.isEmpty()){
            return true;
        }
        return false;

    }
}