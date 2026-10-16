class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] arr=new int[nums1.length+nums2.length];
        int i=0,j=0;
        for(int k=0;k<arr.length;k++){
            if(i<nums1.length && j<nums2.length){
                if(nums1[i]<nums2[j]){
                   arr[k]=nums1[i];
                   i++;
                }else{
                   arr[k]=nums2[j];
                   j++;
                }                
            }else if(j==nums2.length){
                arr[k]=nums1[i];
                i++;
            }else{
                arr[k]=nums2[j];
                j++;
            }    
        }
        if(arr.length%2==0){
            i=arr.length/2;
            double ou=(arr[i]+arr[i-1])/2.0;
            return ou;
        }else{
            i=arr.length/2;
            return arr[i];
        }
        
    }
}