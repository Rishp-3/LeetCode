class Solution {
    public String convertToTitle(int columnNumber) {
        StringBuilder res =new StringBuilder();
        while(columnNumber>0){
            columnNumber--;
            int re=columnNumber%26;
            char ch=(char)('A'+re);
            res.insert(0,ch);
            columnNumber=columnNumber/26;
        }
        return res.toString();
    }
}