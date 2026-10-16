class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> ans=new ArrayList<>();
        if(nums.length==0){
            return ans;
        }
        if(nums.length==1){ 
            ans.add(String.valueOf(nums[0]));
            return ans;
        }
        int n=nums[0];
        int l=n;
        for(int i=1;i<nums.length;i++){
            if(l+1==nums[i]){
                l++;
            }else{
                if(l==n){
                    ans.add(String.valueOf(n));
                }else{
                    ans.add(String.valueOf(n) +"->"+String.valueOf(l));
                }
                n=l=nums[i];
            }
        }
        if(l==n){
                ans.add(String.valueOf(n));
        }else{
            ans.add(String.valueOf(n) +"->"+String.valueOf(l));
        }        
        return ans;
    }
}