package array;

import org.junit.Test;

/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数。
 * <p>
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * </p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author txl
 * @date 2023/1/2 21:30
 */
public class LeetCode0004寻找两个正序数组的中位数 {
    class Solution {
        /**
         * 暴力枚举法
         * 时间复杂度：O(M+N)
         * 空间复杂度：O(M+N)
         *
         * @param nums1 正序数组1，长度为m
         * @param nums2 正序数组2，长度为n
         * @return
         */
        public double findMedianSortedArrays01(int[] nums1, int[] nums2) {
            int len1 = nums1.length, len2 = nums2.length;
            int[] sortArray = new int[len1 + len2];
            // 求中位数，那么排序到中间位置即可，无需全部有序
            for (int i = 0, j = 0, index = 0; index <= (len1 + len2) / 2; ) {
                if (i < len1 && j < len2) {
                    if (nums1[i] <= nums2[j]) {
                        sortArray[index++] = nums1[i++];
                    } else {
                        sortArray[index++] = nums2[j++];
                    }
                } else if (i < len1) {
                    sortArray[index++] = nums1[i++];
                } else if (j < len2) {
                    sortArray[index++] = nums2[j++];
                } else {
                    break;
                }
            }

            return getArrayMid(sortArray);
        }

        private double getArrayMid(int[] sortArray) {
            if (sortArray.length % 2 == 0) {
                return (sortArray[sortArray.length / 2 - 1] + sortArray[sortArray.length / 2]) / 2.0;
            } else {
                return sortArray[sortArray.length / 2];
            }
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        System.out.println(solution.findMedianSortedArrays01(new int[]{1, 3}, new int[]{2}));
        System.out.println(solution.findMedianSortedArrays01(new int[]{1, 2}, new int[]{3, 4}));
    }
}
