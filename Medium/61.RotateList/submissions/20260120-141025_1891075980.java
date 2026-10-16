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
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null ||head.next==null){
            return head;
        }
        int siz=0;
        ListNode temp=head;
        while(temp!=null){
            siz++;
            temp=temp.next;
        }
        int n=k%siz;
        for(int i=1;i<=n;i++){
            head=ro(head);
        }
        return head;
    }
    public ListNode ro(ListNode head){
        ListNode temp=head;
        ListNode back=temp;
        while(temp.next!=null){
            back=temp;
            temp=temp.next;
        }
        back.next=null;
        temp.next=head;
        return temp;
    }
}

