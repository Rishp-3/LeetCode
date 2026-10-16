class Solution {
    public boolean isPalindrome(String s) {
        s=s.toLowerCase();
        int l=0;
        int r=s.length()-1;
        while(l<r){
            if((Character.isLetter(s.charAt(l)) || Character.isDigit(s.charAt(l))) && (Character.isLetter(s.charAt(r)) || Character.isDigit(s.charAt(r)))){
                if(s.charAt(l)==s.charAt(r)){
                    r--;
                    l++;
                }else{
                    return false;
                }
            }else if(Character.isLetter(s.charAt(l)) || Character.isDigit(s.charAt(l))) {
                r--;
            }else{
                l++;
            }
        }
        return true;
    }
}