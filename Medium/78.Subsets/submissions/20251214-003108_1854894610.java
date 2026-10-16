class Solution {
   void sub(int[] num, List<List<Integer>> ans, List<Integer> row, int i) {
    if (i == num.length) {
        ans.add(new ArrayList<>(row));
        return;
    }

    // include
    row.add(num[i]);
    sub(num, ans, row, i + 1);
    row.remove(row.size() - 1);

    // exclude
    sub(num, ans, row, i + 1);
}


    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> row=new ArrayList<>();
        sub(nums , ans,row ,0);
        return ans;
    }
}