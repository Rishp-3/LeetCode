class Solution {
    public String intToRoman(int num) {
        int re=num;
        String nu="";
        int thu=re/1000;
        re%=1000;
        int hun=re/100;
        re%=100;
        int ten=re/10;
        re%=10;
        while(thu>0){
            nu+="M";
            thu--;
            
        }
        while(hun>0){
            if(hun==9){
                nu+="CM";
                hun-=9;
            }
            else if(hun==4||hun>=5){
                if(hun>=5){
                    nu=nu+"D";
                    hun=hun-5;
                }else{
                    nu+="CD";
                    hun-=4;
                }
            }else{
                nu+="C";
                hun--;
            }
        }
        while(ten>0){
            if(ten==9){
                nu+="XC";
                ten-=9;
            }
            else if(ten==4||ten>=5){
                if(ten>=5){
                    nu=nu+"L";
                    ten=ten-5;
                }else{
                    nu+="XL";
                    ten-=4;
                }
            }else{
                nu+="X";
                ten--;
            }
        }
        while(re>0){
            if(re==9){
                nu+="IX";
                re-=9;
            }
            else if(re==4||re>=5){
                if(re>=5){
                    nu=nu+"V";
                    re=re-5;
                }else{
                    nu+="IV";
                    re-=4;
                }
            }else{
                nu+="I";
                re--;
            }
        }
        return nu ;
    }
}