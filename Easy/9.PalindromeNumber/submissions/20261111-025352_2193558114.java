class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        long s = 0;
        int y=x;

        while(y>0){
            s = s*10+y%10;
            y/=10;
        }
        return s == x;
    }
}