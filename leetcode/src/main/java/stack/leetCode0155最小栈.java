package stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * 实现 MinStack 类:
 * <p>
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/min-stack
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/2 0:36
 */
public class leetCode0155最小栈 {
    class MinStack {
        /**
         * 容器
         */
        private List<Integer> list;
        /**
         * 辅助容器
         */
        private List<Integer> minList;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            list = new ArrayList<>();
            minList = new ArrayList<>();
        }

        public void push(int x) {
            list.add(x);
            if (minList.isEmpty()) {
                minList.add(x);
            } else {
                minList.add(Math.min(minList.get(minList.size() - 1), x));
            }
        }

        public void pop() {
            list.remove(list.size() - 1);
            minList.remove(minList.size() - 1);
        }

        public int top() {
            return list.get(list.size() - 1);
        }

        public int min() {
            return minList.get(minList.size() - 1);
        }
    }

    @Test
    public void test() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.min());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.min());
    }
}
