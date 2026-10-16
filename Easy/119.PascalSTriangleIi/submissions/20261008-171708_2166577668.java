class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> ans=new ArrayList<>();
        for(int i=0;i<=rowIndex;i++){
            List<Integer> inans=new ArrayList<>();
            for(int j=0;j<=i;j++){
                if(j==0||j==i){
                    inans.add(1);
                }else{
                    inans.add(ans.get(j)+ans.get(j-1));
                }
            }
            if(i==rowIndex){
                return inans;
            }
            ans=inans;
        }
        return ans;
    }
}