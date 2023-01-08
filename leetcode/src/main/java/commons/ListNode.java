package commons;

/**
 * Definition for singly-linked list.
 *
 * @author txl
 * @date 2023/1/8 23:05
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
