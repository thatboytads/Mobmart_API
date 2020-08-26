
import java.util.concurrent.RecursiveTask;
import java.lang.Math;

public class parallel extends RecursiveTask<String []> {
    private final int seqThreshold = 512;
    private int valsOut= 0;
    static  String []textGridFinal;
    private Float[][] data;
    private  int start, row,col,divnum;

    /**
     * instantiates the objecy
     * @param data
     * @param textGridO
     * @param start
     * @param col
     * @param row
     */
    parallel(Float[][] data,String[] textGridO, int start, int col, int row,int divNum){
        this.data = data;
        this.start = start;
        this.col = col;
        this.row= row;
        this.divnum= divNum;
     this.textGridFinal = textGridO;
    }

    /**
     * takes in one element of 2d array data and if value is basin stores it in a one dimension string array
     * @param textGridO
     * @param i
     * @param j
     */
    private void basinFind(Float[][] dataOut, String[] textGridO,int i,int j){


        if (( i>0) && (i <(row)) && (j>0) && (j<(col))){
            Float basin= dataOut[i][j];
            Float num1=  dataOut[i][j+1];
            Float num2= dataOut[i+1][j];
            Float num3= dataOut[i][j-1];
            Float num4= dataOut[i-1][j];
            Float num5= dataOut[i+1][j+1];
            Float num6= dataOut[i-1][j-1];
            Float num7= dataOut[i-1][j+1];
            Float num8= dataOut[i+1][j-1];

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


    /**
     * compute method for recursive task, returns a string array which stores our basin
     * @return an array of strings
     */
    protected String[] compute()
    {
        int end= row;
        String []textGridOut1= new String[end];
        if (row <col){
             end= col;
        }

        if ((end/Math.pow( 2,divnum)- start) < seqThreshold) {
            for (int i = 1; i < end-1; i++) {
                for (int j = 1; j < end-1; j++) {
                    basinFind(data,textGridFinal,i, j);
                }
            }
           return textGridFinal;
        }
        else {
            divnum=divnum+1;
            int middle = ( end+start) / 2;

            parallel subtaskA = new parallel(data,textGridFinal, start, middle,end,divnum);
            parallel subtaskB = new parallel(data,textGridFinal, middle, end,end,divnum);

            subtaskA.fork();
            subtaskB.compute();
           subtaskA.join();



        }
        return textGridFinal;
    }

}
