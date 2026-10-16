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
    public int[] nextLargerNodes(ListNode head) {
        Stack<Integer> sl=new Stack<>();
        Stack<Integer> an=new Stack<>();
        int n=0;
        ListNode temp=head;
        while(temp!=null){
            sl.push(temp.val);
            temp=temp.next;
            n++;
        }
        int[] arr =new int[n];
        n--;
        while(!sl.isEmpty()){
            int k=sl.pop();
            while(!an.isEmpty()){
                if(an.peek()>k){
                    break;
                }
                an.pop();
            }
            if(an.isEmpty()){
                arr[n]=0;
            }else{
                arr[n]=an.peek();
            }
            n--;
            an.push(k);
        }
        
        return arr;
    }
}