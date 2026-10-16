class Solution {
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curr = head.next;
        ListNode prev = head;

        while (curr != null) {
            if (curr.val >= prev.val) {
                prev = curr;
                curr = curr.next;
            } else {
                ListNode temp = dummy;
                while (temp.next.val < curr.val) {
                    temp = temp.next;
                }

                // remove curr
                prev.next = curr.next;

                // insert curr
                curr.next = temp.next;
                temp.next = curr;

                curr = prev.next;
            }
        }
        return dummy.next;
    }
}
