/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int pairSum(ListNode head) {
        Stack<Integer> st = new Stack<>();
        ListNode slow=head;
        ListNode fast=head;
        while(fast!=null){
            st.push(slow.val);
            slow=slow.next;
            fast=fast.next.next;
            
        }
        int max=0;
        while(slow!=null && !st.isEmpty()){
            max=Math.max(max,slow.val+st.pop());
            slow=slow.next;

        }
        return max;
    }
}