class Solution {
    public int countMonobit(int n) {
        int co=1;
        long curr=1;
        while(curr<=n){
            co++;
            curr=(curr<<1)|1;
        }
        return co;
    }
}