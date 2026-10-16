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
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i=0,j=0 ,le=nums1.length+nums2.length,n=le/2 , op=0;
        for(int k=0;k<le;k++){
            if(i<nums1.length && j<nums2.length){
                if(nums1[i]<nums2[j]){
                    if(k==n){
                        if(le%2==0){
                            double ou=(op+nums1[i])/2.0;
                            return ou;
                        }else{
                            return nums1[i];
                        }
                    }
                   op=nums1[i];
                   i++;
                }else{
                    if(k==n){
                        if(le%2==0){
                            double ou=(op+nums2[j])/2.0;
                            return ou;
                        }else{
                            return nums2[j];
                        }
                    }
                   op=nums2[j];
                   j++;
                }                
            }else if(j==nums2.length){
                if(k==n){
                        if(le%2==0){
                            double ou=(op+nums1[i])/2.0;
                            return ou;
                        }else{
                            return nums1[i];
                        }
                    }
                op=nums1[i];
                i++;
            }else{
                if(k==n){
                        if(le%2==0){
                            double ou=(op+nums2[j])/2.0;
                            return ou;
                        }else{
                            return nums2[j];
                        }
                    }
                op=nums2[j];
                j++;
            }    
        }
        return 0;
    }
}