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
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return head;
        }
        int num=Integer.MIN_VALUE;
        while(head.next!=null&&head.val==head.next.val){
            if(head.val==head.next.val){
                num=head.val;
                while(head.next!=null&&head.val==num){
                  head=head.next;
                }
            }
        }
        if(head.val==num){
            head=null;
            return head;
        }
        ListNode pri=head;
        while(pri!=null&&pri.next!=null){
            ListNode temp=pri.next;
            while(temp.next!=null&&temp.val==temp.next.val){
                if(temp.val==temp.next.val){
                    num=temp.val;
                    while(temp.next!=null&&temp.val==num){
                        temp=temp.next;
                    }
                }
            }
            if(num==temp.val){
                temp=null;
            }
            if(pri.next!=temp){
                pri.next=temp;
            }else{
                pri=pri.next;
            }
        }
        return head;
    }
}