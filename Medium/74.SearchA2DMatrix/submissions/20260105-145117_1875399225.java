class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n=matrix.length;
        int m=matrix[0].length;
        int ru=0;
        int rd=n-1;
        int cl=0;
        int cr=m-1;
        while(ru <=rd && cl <=cr){
            int mi=(ru+rd)/2;
            int mj=(cl+cr)/2;
            if(matrix[mi][mj]==target){
                return true;
            }else if(matrix[mi][mj]<target){
                if(matrix[mi][m-1]<target){
                    ru=mi+1;
                }else{
                    cl=mj+1;
                }
            }else if(matrix[mi][mj]>target){
                if(matrix[mi][0]>target){
                    rd=mi-1;
                }else{
                    cr=mj-1;
                }
            }
        }
        return false;

    }
}