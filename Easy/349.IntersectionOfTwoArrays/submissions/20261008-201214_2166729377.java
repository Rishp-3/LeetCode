class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> ans=new ArrayList<>();
        int n1=0, n2=0;
        int l1=nums1.length ,l2=nums2.length;
        while(n1<l1 && n2<l2){
            if( nums1[n1]==nums2[n2]){
                ans.add(nums1[n1]);
                 n1++;
                while( n1<l1 && nums1[n1]==nums1[n1-1]){ n1++;}
                n2++;
                while( n2<l2 && nums2[n2]==nums2[n2-1] ) n2++;
            } else if(nums1[n1]<nums2[n2]){
                n1++;
                while(n1<l1 && nums1[n1]==nums1[n1-1]) n1++;
            }else{
                n2++;
                while(n2<l2 && nums2[n2]==nums2[n2-1]) n2++;
            }

        }
        int s=ans.size();
        int[] out=new int[s];
        for (int i=0;i<s;i++){
            out[i]=ans.get(i);
        }
        return out;
    }
}