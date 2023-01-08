package utils;

import org.junit.Test;
import commons.ListNode;

public class LinkedListUtil {
    /**
     * 构建链表
     *
     * @param arr
     * @return
     */
    public static ListNode createLinkedList(int[] arr) {
        if (arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    /**
     * 打印链表
     *
     * @param head
     */
    public static void printLinkedList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.printf("%d -> ", current.val);
            current = current.next;
        }
        System.out.println("NULL");
    }

    @Test
    public void test() {
        int[] x = {1, 2, 3, 4, 5, 6};
        ListNode list = createLinkedList(x);
        printLinkedList(list);
    }
}
