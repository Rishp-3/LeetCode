class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> inans=new ArrayList<>();
            List<Integer> last=new ArrayList<>();
            if(i>0){
                last=ans.get(i-1);
            }
            for(int j=0;j<=i;j++){
                if(j==0||j==i){
                    inans.add(1);
                }else{
                    inans.add(last.get(j)+last.get(j-1));
                }
            }
            ans.add(inans);
        }
        return ans;
    }
}