class Solution {
    public int firstUniqChar(String s) {
        int n=s.length();
        for(int i=0;i<n;i++){
            char c=s.charAt(i);
            int v=s.indexOf(c);
            int x=s.lastIndexOf(c);
            if(v==x){
                return x;
            }
        }
        return -1;
    }
}