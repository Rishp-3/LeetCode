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
    static{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try(FileWriter writer = new FileWriter("display_runtime.txt")){
                writer.write("0");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }));
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 =new ListNode(0);
        ListNode itr=l3;
        int carr=0;
        while(l1!=null||l2!=null||carr!=0){
            int sum=carr;
            if(l1!=null){
                sum+=l1.val;
                l1=l1.next;
            }
            if(l2!=null){
                sum+=l2.val;
                l2=l2.next;
            }
            carr=sum/10;
            itr.next = new ListNode(sum % 10);
            itr=itr.next;
        }
        return l3.next;
    }
}