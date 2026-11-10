class Solution {
        public int[] productExceptSelf(int[] nums) {
                int n = nums.length;
                        int[] ans = new int[n];

                                // ans[i] = product of everything left of i
                                        ans[0] = 1;
                                                for (int i = 1; i < n; i++) {
                                                            ans[i] = ans[i - 1] * nums[i - 1];
                                                                    }

                                                                            // multiply by product of everything right of i
                                                                                    int right = 1;
                                                                                            for (int i = n - 1; i >= 0; i--) {
                                                                                                        ans[i] *= right;
                                                                                                                    right *= nums[i];
                                                                                                                            }

                                                                                                                                    return ans;
                                                                                                                                        }
                                                                                                                                        }
