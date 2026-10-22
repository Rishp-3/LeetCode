class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        List<Integer> ans= new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(target==nums[i]){
                ans.add(i);
            }else if(ans.size()!=0){
                return ans;
            }
        }
        return ans;
    }
}