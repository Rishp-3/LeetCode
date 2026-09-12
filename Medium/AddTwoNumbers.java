package Medium;

// Definition for singly-linked list
class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // Dummy node
        ListNode l3 = new ListNode(0);

        // Iterator
        ListNode itr = l3;

        // Carry
        int carr = 0;

        while (l1 != null || l2 != null || carr != 0) {

            int sum = carr;

            // Add value from l1
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            // Add value from l2
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            // Calculate carry
            carr = sum / 10;

            // Add new node
            itr.next = new ListNode(sum % 10);

            itr = itr.next;
        }

        return l3.next;
    }

    // Print Linked List
    public static void printList(ListNode head) {

        while (head != null) {
            System.out.print(head.val);

            if (head.next != null) {
                System.out.print(" -> ");
            }

            head = head.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        // Number 342
        ListNode l1 = new ListNode(
            2,
            new ListNode(
                4,
                new ListNode(3)
            )
        );

        // Number 465
        ListNode l2 = new ListNode(
            5,
            new ListNode(
                6,
                new ListNode(4)
            )
        );

        // Add two numbers
        ListNode result = addTwoNumbers(l1, l2);

        // Output: 7 -> 0 -> 8
        printList(result);
    }
}