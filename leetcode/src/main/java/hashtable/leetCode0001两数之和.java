package hashtable;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/2 11:29
 */
public class leetCode0001两数之和 {
    class Solution {
        /**
         * 暴力枚举法
         * 时间复杂度：O(N^2)
         * 空间复杂度：O(1)
         */
        public int[] twoSum01(int[] nums, int target) {
            for (int i = 0; i < nums.length - 1; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{-1, -1};
        }

        /**
         * 哈希表
         * 时间复杂度：O(N)
         * 空间复杂度：O(N)
         */
        public int[] twoSum02(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int secondNum = target - nums[i];
                if (map.get(secondNum) != null) {
                    return new int[]{map.get(secondNum), i};
                } else {
                    map.put(nums[i], i);
                }
            }
            return new int[]{-1, -1};
        }
    }

    @Test
    public void testTwoSum() {
        Solution solution = new Solution();
        System.out.println("方法一：暴力法");
        System.out.println(Arrays.toString(solution.twoSum01(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(solution.twoSum01(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(solution.twoSum01(new int[]{3, 3}, 6)));

        System.out.println("方法二：哈希表");
        System.out.println(Arrays.toString(solution.twoSum02(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(solution.twoSum02(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(solution.twoSum02(new int[]{3, 3}, 6)));
    }
}
