package array;

import org.junit.Test;

import java.util.*;

/**
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/2 22:54
 */
public class LeetCode0015三数之和 {
    class Solution {
        /**
         * 暴力枚举法，超出时间限制……
         * 时间复杂度：O(N^3)
         * 空间复杂度：O(1)
         *
         * @param nums
         * @return
         */
        public List<List<Integer>> threeSum01(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                if (i > 0 && nums[i - 1] == nums[i]) {
                    continue;
                }
                for (int j = i + 1; j < nums.length - 1; j++) {
                    if (j > i + 1 && nums[j - 1] == nums[j]) {
                        continue;
                    }
                    for (int k = j + 1; k < nums.length; k++) {
                        if (k > j + 1 && nums[k - 1] == nums[k]) {
                            continue;
                        }
                        if (nums[i] + nums[j] + nums[k] == 0) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        }
                    }
                }
            }
            return result;
        }

        /**
         * 哈希表
         * 时间复杂度：O(N^2)
         * 空间复杂度：O(N)
         *
         * @param nums
         * @return
         */
        public List<List<Integer>> threeSum02(int[] nums) {
            // 集合实现自动去重
            Set<List<Integer>> result = new HashSet<>();

            // 用于记录已经做过目标值的值，减少重复计算
            Set<Integer> targetSet = new HashSet<>();
            for (int i = 0; i < nums.length - 2; i++) {
                int target = -nums[i];
                if (targetSet.contains(target)) {
                    continue;
                } else {
                    targetSet.add(target);
                }

                Set<Integer> set = new HashSet<>();
                for (int j = i + 1; j < nums.length; j++) {
                    int secondNum = target - nums[j];
                    if (set.contains(secondNum)) {
                        // 利用Set集合的特性前，需要对list列表排序
                        List<Integer> list = Arrays.asList(-target, secondNum, nums[j]);
                        Collections.sort(list);
                        result.add(list);
                    } else {
                        set.add(nums[j]);
                    }
                }
            }

            return new ArrayList<>(result);
        }

        /**
         * 双指针法之前后夹击（在有序列表的前提下）
         * 时间复杂度：O(N^2)
         * 空间复杂度：O(1)
         *
         * @param nums
         * @return
         */
        public List<List<Integer>> threeSum03(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);

            for (int i = 0; i < nums.length - 2; i++) {
                if (i > 0 && nums[i - 1] == nums[i]) {
                    continue;
                }

                int target = -nums[i];
                for (int left = i + 1, right = nums.length - 1; left < right; ) {
                    if (nums[left] + nums[right] == target) {
                        result.add(Arrays.asList(-target, nums[left], nums[right]));
                        // 跳过重复解
                        left++;
                        while (left < right && nums[left] == nums[left - 1]) {
                            left++;
                        }
                        right--;
                        while (left < right && nums[right] == nums[right + 1]) {
                            right--;
                        }
                    } else if (nums[left] + nums[right] < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }

            return result;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        System.out.println("暴力枚举法：");
        System.out.println(solution.threeSum01(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum01(new int[]{0, 1, 1}));
        System.out.println(solution.threeSum01(new int[]{0, 0, 0}));
        System.out.println(solution.threeSum01(new int[]{0, 0, 0, 0}));
        System.out.println(solution.threeSum01(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4}));
        System.out.println(solution.threeSum01(new int[]{-3, -4, -2, 0, 2, 2, -2, 1, -1, 2, 3, -1, -5, -4, -5, 1}));

        System.out.println("哈希表：");
        System.out.println(solution.threeSum02(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum02(new int[]{0, 1, 1}));
        System.out.println(solution.threeSum02(new int[]{0, 0, 0}));
        System.out.println(solution.threeSum02(new int[]{0, 0, 0, 0}));
        System.out.println(solution.threeSum02(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4}));
        System.out.println(solution.threeSum02(new int[]{-3, -4, -2, 0, 2, 2, -2, 1, -1, 2, 3, -1, -5, -4, -5, 1}));

        System.out.println("双指针法之前后夹击：");
        System.out.println(solution.threeSum03(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum03(new int[]{0, 1, 1}));
        System.out.println(solution.threeSum03(new int[]{0, 0, 0}));
        System.out.println(solution.threeSum03(new int[]{0, 0, 0, 0}));
        System.out.println(solution.threeSum03(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4}));
        System.out.println(solution.threeSum03(new int[]{-3, -4, -2, 0, 2, 2, -2, 1, -1, 2, 3, -1, -5, -4, -5, 1}));
    }
}
