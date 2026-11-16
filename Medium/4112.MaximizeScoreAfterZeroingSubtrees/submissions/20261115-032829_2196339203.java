class Solution {
    public int maxScore(int[] parent, int[] op, int[] score) {
        int n=parent.length;
        int[] d=parent;
        long[] s=new long[n],m=new long[n];
        java.util.Arrays.fill(m ,Long.MAX_VALUE);
        int[] cnt=new int[n] ,q=new int[n];
        int h=0 ,t=0;
        for(int i=1;i<n;i++)cnt[d[i]]++;
        for(int i=0;i<n;i++)if(cnt[i]==0)q[t++]=i;
        while(true){
            int v=q[h++];
            long b=score[v]+s[v];
            if(m[v]!=Long.MAX_VALUE && (op[v]==1|| m[v]<0))b-=m[v];
            if(v==0)return (int)b;
            int p=d[v];
            s[p]+=b;
            m[p]=Math.min(m[p],b);
            if(--cnt[p]==0)q[t++]=p;
        }
    }
}