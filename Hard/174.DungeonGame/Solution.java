class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int n=dungeon.length, m=dungeon[0].length;
        if(n==1&&m==1){
            if(1-dungeon[0][0]>0){
                return 1-dungeon[0][0];
            }
            else{
                return 1;
            }
        }
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(i==n-1&&j==m-1){
                    if(1-dungeon[i][j]>0){
                        dungeon[i][j]=1-dungeon[i][j];
                    }else{
                        dungeon[i][j]=1;
                    }
                }else if(i==n-1){
                    if(dungeon[i][j+1]-dungeon[i][j]>0){
                        dungeon[i][j]=dungeon[i][j+1]-dungeon[i][j];
                    }else{
                        dungeon[i][j]=1;
                    }
                }
                else if(j==m-1){
                    if(dungeon[i+1][j]-dungeon[i][j]>0){
                        dungeon[i][j]=dungeon[i+1][j]-dungeon[i][j];
                    }else{
                        dungeon[i][j]=1;
                    }
                }else{
                    int min=Math.min(dungeon[i+1][j],dungeon[i][j+1]);
                    if(min-dungeon[i][j]>0){
                        dungeon[i][j]=min-dungeon[i][j];
                    }else{
                        dungeon[i][j]=1;
                    }
                }
            }
        }
        return dungeon[0][0];
    }
}