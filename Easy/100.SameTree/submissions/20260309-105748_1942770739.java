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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(q==null && p==null){
            return true;
        }else if(q==null || p==null){
            return false;
        }
        return isSame(p,q);
    }
    public boolean isSame(TreeNode p, TreeNode q){
        if(q==null && p==null){
            return true;
        }else if(q==null || p==null){
            return false;
        }
        if(p.val==q.val){
            if(isSame(p.left,q.left)){
                if(isSame(p.right,q.right)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        
    }

}