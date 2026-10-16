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
    public ListNode swapPairs(ListNode head) {
        ListNode temp=head;
        while(temp!=null&&temp.next!=null){
            int data=temp.val;
            temp.val=temp.next.val;
            temp.next.val=data;
            temp=temp.next.next;
        }
        return head;
    }
}