package hashtable;

import org.junit.Test;

import java.util.*;

/**
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 * <p>
 * 你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/2 22:54
 */
public class leetCode0015三数之和 {
    class Solution {
        /**
         * 暴力枚举法
         * 时间复杂度：O(N^3)
         * 空间复杂度：O(1)
         *
         * @param nums
         * @return
         */
        public List<List<Integer>> threeSum01(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                if (i > 0 && nums[i - 1] == nums[i]) {
                    continue;
                }
                for (int j = i + 1; j < nums.length; j++) {
                    for (int k = j + 1; k < nums.length; k++) {
                        if (nums[i] + nums[j] + nums[k] == 0) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        }
                    }
                }
            }
            return result;
        }

        public List<List<Integer>> threeSum02(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                int[] twoSumResult = twoSum(nums, i, -nums[i]);
                if (twoSumResult != null) {
                    result.add(Arrays.asList(nums[i], twoSumResult[0], twoSumResult[1]));
                }
            }

            return result;
        }

        private int[] twoSum(int[] nums, int index, int target) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < nums.length; i++) {
                if (i != index) {
                    int secondNum = target - nums[i];
                    if (set.contains(secondNum)) {
                        return new int[]{secondNum, nums[i]};
                    } else {
                        set.add(nums[i]);
                    }
                }
            }
            return null;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        System.out.println("暴力枚举法：");
        System.out.println(solution.threeSum01(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum01(new int[]{0, 1, 1}));
        System.out.println(solution.threeSum01(new int[]{0, 0, 0}));

        System.out.println("哈希表：");
        System.out.println(solution.threeSum02(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum02(new int[]{0, 1, 1}));
        System.out.println(solution.threeSum02(new int[]{0, 0, 0}));
    }
}
