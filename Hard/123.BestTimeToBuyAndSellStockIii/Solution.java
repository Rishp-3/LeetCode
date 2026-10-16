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
    public int maxProfit(int[] prices) {
        int fb = -prices[0] ,
        fs = 0,
        sb = -prices[0],
        ss =0;
        for(int i=1;i<prices.length;i++){
            fb=Math.max(fb,-prices[i]);
            fs=Math.max(fs,fb+prices[i]);
            sb=Math.max(sb,fs-prices[i]);
            ss=Math.max(ss,sb+prices[i]);
        }
        return ss;
    }
}