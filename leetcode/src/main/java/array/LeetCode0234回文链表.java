package array;

import commons.ListNode;
import org.junit.Test;
import utils.LinkedListUtil;
import utils.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/palindrome-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/8 23:28
 */
public class LeetCode0234回文链表 {
    class Solution {
        /**
         * 暴力法，先将链表转为列表，然后再做回文判断
         * 时间复杂度：O(N)
         * 空间复杂度：O(N)
         *
         * @param head
         * @return
         */
        public boolean isPalindrome01(ListNode head) {
            List<Integer> list = new ArrayList<>();
            while (head != null) {
                list.add(head.val);
                head = head.next;
            }

            for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
                if (!Objects.equals(list.get(i), list.get(j))) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 反转链表：反转需要反转的部分即可
         * 时间复杂度：O(N)
         * 空间复杂度：O(1)
         *
         * @param head
         * @return
         */
        public boolean isPalindrome02(ListNode head) {
            ListNode reverseHead = null;

            ListNode node = head;
            while (node != null) {
                ListNode tempNode = new ListNode(node.val);
                node = node.next;
                tempNode.next = reverseHead;
                reverseHead = tempNode;
            }

            node = head;
            while (node != null && reverseHead != null) {
                if (!Objects.equals(node.val, reverseHead.val)) {
                    return false;
                }
                node = node.next;
                reverseHead = reverseHead.next;
            }
            return true;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();

        System.out.println("暴力法：");
        System.out.println(solution.isPalindrome01(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{1, 2, 2, 1}))));
        System.out.println(solution.isPalindrome01(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{1, 2, 3, 1, 2}))));
        System.out.println(solution.isPalindrome01(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{0}))));

        System.out.println("反转链表：");
        System.out.println(solution.isPalindrome02(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{1, 2, 2, 1}))));
        System.out.println(solution.isPalindrome02(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{1, 2, 3, 1, 2}))));
        System.out.println(solution.isPalindrome02(Objects.requireNonNull(LinkedListUtil.createLinkedList(new int[]{0}))));
    }
}
