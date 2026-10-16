class Solution {
    public List<Integer> maxPrimes(int n, int s) {
        int [] vl={n,s};
        boolean[] ip=new boolean[n+1];
        Arrays.fill(ip , true);
        ip[0]=false;
        if(n>=1)ip[1]=false;
        for(int i=2;i<=n/i;i++){
            if(ip[i]){
                for(int j=i*i;j<=n;j+=i){
                    ip[j]=false;
                }
            }
        }
        List<Integer> ans=new ArrayList<>();
        long sum=0;
        for(int i=2;i<=n;i++){
            if(ip[i] && sum+i<=s){
                ans.add(i);
                sum+=i;
            }
        }
        return ans;
    }
}