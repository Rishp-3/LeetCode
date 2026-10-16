class Solution {
    public int reverse(int x) {
        int n=x;
        long ans=0;
        while(n!=0){
            ans=(ans*10)+(n%10);
            n=n/10;
        }
        if(ans>2147483647 || ans<-2147483647){
            return 0;
        }
        return (int)ans;
    }
}