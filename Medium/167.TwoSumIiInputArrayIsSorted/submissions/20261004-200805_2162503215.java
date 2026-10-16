class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int s=numbers.length;
        int l=0;
        int r=s-1;
        while(r>=l){
            int sum=numbers[l]+numbers[r];
            if(sum==target){
                break;
            }else if(sum>target){
                r--;
            }else{
                l++;
            }
        }
        int[] ans=new int[2];
        ans[0]=l+1;
        ans[1]=r+1;
        return ans;
    }
}