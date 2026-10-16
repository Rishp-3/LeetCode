class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int y=0;
        int z=x;
        while(z>0){
            y=y*10+(z%10);
            z=z/10;
        }
        if(y==x){
            return true;
        }
        return false;
    }
}