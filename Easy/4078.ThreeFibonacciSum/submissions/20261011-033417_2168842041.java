class Solution {
    public boolean threeFibonacciSum(int n) {
        int a=0,b=1,c=1;
        while(a+b+c<=n){
            if(a+b+c==n){
                return true;
            }
            int next =b+c;
            a=b;
            b=c;
            c=next;
        }
        return false;
    }
}