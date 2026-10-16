class Solution {
    public void reverseString(char[] s) {
        int siz=s.length-1;
        for(int i=0;i<s.length/2;i++){
            char temp=s[i];
            s[i]=s[siz];
            s[siz]=temp;
            siz--;
        }
    }
}