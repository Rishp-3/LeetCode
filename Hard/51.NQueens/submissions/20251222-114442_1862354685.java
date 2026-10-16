class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> chess=new ArrayList<>();
        if(n==1){
            chess.add(Arrays.asList("Q"));
            return chess;
        }
        if(n==2||n==3){
            return chess;
        }
        List<String> str=new ArrayList<>();
        String  s=".";
        for(int i=0;i<n-1;i++){
            s+=".";
        }
        for(int i=0;i<n;i++){
            str.add(s);
        }
        solve(chess,str,n,0);
        return chess;
    }
    public static void solve(List<List<String>> chess,List<String> str, int n ,int row){
        if(row==n){
            chess.add(new ArrayList<>(str));
            return;
        }
        for(int i=0;i<n;i++){
            if(isSafe(str, n ,row,i)){
                StringBuilder sb= new StringBuilder(str.get(row));
                sb.setCharAt(i,'Q');
                str.set(row,sb.toString());

                solve(chess,str,n,row+1);

                StringBuilder rp= new StringBuilder(str.get(row));
                rp.setCharAt(i,'.');
                str.set(row,rp.toString());
            }
        }
    }

    public static boolean isSafe(List<String> str,int n ,int row , int col){
        for(int i=row-1;i>=0;i--){
            if(str.get(i).charAt(col)=='Q'){
                return false;
            }
        }
        for(int i=row-1 ,j=col-1;i>=0&& j>=0;i--,j--){
            if(str.get(i).charAt(j)=='Q'){
                return false;
            }
        }
        for(int i=row-1 ,j=col+1;i>=0 && j<n;i--,j++){
            if(str.get(i).charAt(j)=='Q'){
                return false;
            }
        }
        return true;
    }
}