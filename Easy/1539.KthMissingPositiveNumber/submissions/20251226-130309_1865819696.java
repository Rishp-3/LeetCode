class Solution {
    public int findKthPositive(int[] arr, int k) {
        int mis=0;
        int misNum=0;
        int ind=1;
        for(int i=0;i<arr.length;i++){
            if(ind==arr[i]){
                ind++;
            }else if(k==mis){
                break;
            }else{
                mis++;
                misNum=ind;
                ind++;
                i--;
            }
        }
        if(mis!=k){
            for(int i=mis;i<k-1;i++){
                ind++;
            }
            misNum=ind;
        }
        return misNum;
    }
}