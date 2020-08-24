import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
public class parallel extends RecursiveTask<String []> {
    final int seqThreshold = 500;
    int valsOut= 0;
    String []textGridOut;
    Float[][] data;
    int start, end;


    parallel(Float[][] data, int start, int end){
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public void basinFind( int i,int j){


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
                    textGridOut= new String[end];
                    textGridOut[valsOut]= Integer.toString(i)+ " "+ Integer.toString(j);
                    System.out.println(textGridOut[valsOut]);
                    valsOut= valsOut+1;


                }

            }
        }

    }
    public int getValsOut(int valsO){
        valsO= this.valsOut;
        return valsO;
    }


    public String[] getTextGridOut(String[] Textgr) {
        Textgr= this.textGridOut;
        return Textgr;
    }

    protected String[] compute()
    {

        textGridOut= new String[end];

        if ((end - start) < seqThreshold) {
            for (int i = start; i < end; i++){
                for (int j = start; j < end; j++){
                    basinFind(i,j);
                }

                ;}
        }
        else {
            int middle = (start + end) / 2;

            parallel subtaskA = new parallel(data, start, middle);
            parallel subtaskB = new parallel(data, middle, end);

            subtaskA.fork();
           String [] textOut1= subtaskB.compute();
           String [] textOut2= subtaskA.join();

            List list = new ArrayList(Arrays.asList(textOut1));
            list.addAll(Arrays.asList(textOut2));

            list.toArray(textGridOut);

        }
        return textGridOut;
    }
}
