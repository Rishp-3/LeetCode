class Solution {
    public int vowelConsonantScore(String s) {
        int v=0;
        int c=0;
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(Character.isLetter(ch)){
                if(ch=='a'||ch=='e'|| ch=='o'||ch=='i'||ch=='u'){
                    v++;
                }else{
                    c++;
                }
            }
        }
        int out=(c>0)? v/c : 0; 
        return out;
    }
}