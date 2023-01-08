package array;

import org.junit.Test;

import java.util.Arrays;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 请注意，必须在不复制数组的情况下原地对数组进行操作。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/move-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/8 22:39
 */
public class LeetCode0283移动零 {
    class Solution {
        /**
         * 暴力法
         * 时间复杂度：O(N)
         * 空间复杂度：O(N)
         *
         * @param nums
         */
        public void moveZeroes01(int[] nums) {
            int[] copyNums = new int[nums.length];
            for (int i = 0, j = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    copyNums[j++] = nums[i];
                }
            }
            System.arraycopy(copyNums, 0, nums, 0, copyNums.length);
            System.out.println(Arrays.toString(nums));
        }

        /**
         * 双指针法之快慢指针
         * 时间复杂度：O(N)
         * 空间复杂度：O(1)
         *
         * @param nums
         */
        public void moveZeroes02(int[] nums) {
            int noZeroIndex = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[noZeroIndex++] = nums[i];
                }
            }
            while (noZeroIndex < nums.length) {
                nums[noZeroIndex++] = 0;
            }
            System.out.println(Arrays.toString(nums));
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();

        System.out.println("暴力法：");
        solution.moveZeroes01(new int[]{0, 1, 0, 3, 12});
        solution.moveZeroes01(new int[]{0});

        System.out.println("双指针法之快慢指针：");
        solution.moveZeroes02(new int[]{0, 1, 0, 3, 12});
        solution.moveZeroes02(new int[]{0});
    }
}
