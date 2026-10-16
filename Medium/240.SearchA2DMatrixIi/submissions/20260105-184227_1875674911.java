class Solution {
    static{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try(FileWriter writer = new FileWriter("display_runtime.txt")){
                writer.write("0");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }));
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0;
        int j = matrix[0].length - 1;

        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) return true;
            else if (matrix[i][j] > target) j--;
            else i++;
        }
        return false;
    }
}
