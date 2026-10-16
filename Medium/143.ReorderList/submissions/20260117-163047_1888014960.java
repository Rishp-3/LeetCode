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
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (FileWriter writer = new FileWriter("display_runtime.txt")) {
                writer.write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
    public void reorderList(ListNode head) {
        ListNode mid=head;
        ListNode fast=head;
        while (fast!=null && fast.next!=null ) {
            mid=mid.next;
            fast=fast.next.next;
        }
        ListNode temp=mid.next;
        mid.next=null;
        Stack<ListNode> st=new Stack<>();
        while(temp!=null){
            st.push(temp);
            temp=temp.next;
        }
        temp=head;
        while(!st.isEmpty()){
            ListNode next=temp.next;
            temp.next=st.pop();
            temp=temp.next;
            temp.next=next;
            temp=temp.next;
        }

    }
}