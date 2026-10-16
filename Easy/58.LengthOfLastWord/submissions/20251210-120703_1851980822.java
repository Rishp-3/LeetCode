class Solution {
    public int lengthOfLastWord(String s) {
        int la=s.lastIndexOf(' ');
        if(la!=s.length()-1){
            return s.length()-la-1;
        }
        int su=-1;
        for(int i=s.length()-1;i>=0;i--){
            if(s.charAt(i)!=' '){
                su=i;
                break;
            }
        }
        int re=0;
        for(int i=su;i>=0;i--){
            if(s.charAt(i)==' '){
                break;
            }
            re++;
        }
        return re;
    }
}