
import java.util.concurrent.RecursiveTask;


public class parallel extends RecursiveTask<String []> {
    private final int seqThreshold = 600;
    private int valsOut= 0;
    static  String []textGridFinal;
    private Float[][] data;
    private int start, end;


    parallel(Float[][] data,String[] textGridO, int start, int end){
        this.data = data;
        this.start = start;
        this.end = end;
     this.textGridFinal = textGridO;
    }

    private void basinFind( String[] textGridO,int i,int j){


        if (( i>0) && (i <(end-1)) && (j>0) && (j<(end-1))){
            Float basin= data[i][j];
            Float num1=  data[i][j+1];
            Float num2= data[i+1][j];
            Float num3= data[i][j-1];
            Float num4= data[i-1][j];
            Float num5= data[i+1][j+1];
            Float num6= data[i-1][j-1];
            Float num7= data[i-1][j+1];
            Float num8= data[i+1][j-1];

            if ((num1>basin)&& (num2>basin)&&(num3>basin)&&(num4>basin)&&(num5>basin)&&(num6>basin)&&(num7>basin)&&(num8>basin)){
                Float diff1= num1 - basin;
                Float diff2= num2- basin;
                Float diff3= num3 -basin;
                Float diff4= num4-basin;
                Float diff5= num5-basin;
                Float diff6= num6-basin;
                Float diff7= num7-basin;
                Float diff8= num8-basin;

                if ((diff1> 0.01) && (diff2> 0.01) &&(diff3> 0.01) &&(diff4> 0.01) &&(diff5> 0.01) &&(diff6> 0.01) &&(diff7> 0.01) &&(diff8> 0.01) ){

                    textGridO[valsOut]= Integer.toString(i)+ " "+ Integer.toString(j);

                    valsOut= valsOut+1;



                }

            }
        }

    }
    public int getValsOut(){

        return  this.valsOut;
    }





    protected String[] compute()
    {

        String []textGridOut1= new String[end];

        if ((end - start) < seqThreshold) {
            for (int i = start; i < end; i++) {
                for (int j = start; j < end; j++) {
                    basinFind(textGridOut1,i, j);
                }
            }
            return textGridOut1;
        }
        else {
            int middle = (start + end) / 2;

            parallel subtaskA = new parallel(data,textGridFinal, start, middle);
            parallel subtaskB = new parallel(data,textGridFinal, middle, end);

            subtaskA.fork();
            subtaskB.compute();
           subtaskA.join();



        }
        return textGridFinal;
    }

}
