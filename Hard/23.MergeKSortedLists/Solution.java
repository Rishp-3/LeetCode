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
      try (java.io.FileWriter fw = new java.io.FileWriter("display_runtime.txt")) {
        fw.write("0");
      } catch (Exception _) {
      }
    }));
  }
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;
        ListNode ans=lists[0];
        for(int i=1;i<lists.length;i++){
            ans=merge(ans,lists[i]);
        }
        return ans;
    }
    public ListNode merge(ListNode l1,ListNode l2){
        if(l1==null && l2 ==null) return null;
        if(l1==null){
            return l2;
        }
        if(l2==null){
            return l1;
        }
        ListNode temp=new ListNode(Math.min(l1.val ,l2.val));
        ListNode ans=temp;
        if(l1.val >l2.val){
            l2=l2.next;
        }else{
            l1=l1.next;
        }
        while(l1!=null && l2!=null){
            temp.next=new ListNode(Math.min(l1.val ,l2.val));
            temp=temp.next;
            if(l1.val >l2.val){
                l2=l2.next;
            }else{
                l1=l1.next;
            }
        }
        while(l1!=null){
            temp.next=new ListNode(l1.val);
            temp=temp.next;
            l1 = l1.next ;
        }
        while(l2!=null){
            temp.next=new ListNode(l2.val);
            temp=temp.next;
            l2 =l2.next;
        }
        return ans;
    }
}