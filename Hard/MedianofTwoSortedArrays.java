package Hard;

public class MedianofTwoSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int i = 0;
        int j = 0;

        int le = nums1.length + nums2.length;
        int n = le / 2;

        int op = 0;

        for (int k = 0; k < le; k++) {

            if (i < nums1.length && j < nums2.length) {

                if (nums1[i] < nums2[j]) {

                    if (k == n) {

                        if (le % 2 == 0) {
                            return (op + nums1[i]) / 2.0;
                        } else {
                            return nums1[i];
                        }
                    }

                    op = nums1[i];
                    i++;

                } else {

                    if (k == n) {

                        if (le % 2 == 0) {
                            return (op + nums2[j]) / 2.0;
                        } else {
                            return nums2[j];
                        }
                    }

                    op = nums2[j];
                    j++;
                }

            } else if (j == nums2.length) {

                if (k == n) {

                    if (le % 2 == 0) {
                        return (op + nums1[i]) / 2.0;
                    } else {
                        return nums1[i];
                    }
                }

                op = nums1[i];
                i++;

            } else {

                if (k == n) {

                    if (le % 2 == 0) {
                        return (op + nums2[j]) / 2.0;
                    } else {
                        return nums2[j];
                    }
                }

                op = nums2[j];
                j++;
            }
        }

        return 0;
    }

    public static void main(String[] args) {

        int[] nums1 = {1, 3};
        int[] nums2 = {2};

        double result = findMedianSortedArrays(nums1, nums2);

        System.out.println("Median = " + result);
    }
}