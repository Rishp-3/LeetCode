import java.util.*;
class Solution {
    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        if(nums[0]>1){
            return 1;
        }
        int ind=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                break;
            }
            ind++;
        }
        if(ind==nums.length){
            return 1;
        }
        int ou=1;
        for(int i=ind;i<nums.length;i++){
            if(nums[i]==ou){
                if(i<nums.length-1){
                    if(nums[i]!=nums[i+1]){
                        ou++;
                    }
                }else{
                    ou++;
                }
            }else{
                return ou;
            }
        }
        return ou;
    }
}