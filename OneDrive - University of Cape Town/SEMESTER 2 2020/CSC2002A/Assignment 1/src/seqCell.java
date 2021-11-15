import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
public class seqCell {
    static Float [][] textGrid;
    static int row, col;


    static  String []textGridOut;
    static  int valsOut= 0;
    static long startTime = 0;

    /**
     * tick and tock methods used to record the speed of findind basin
     */
    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }

    /**
     * runs the program
     * @param args
     */
    public static void main( String [] args )
    {
        /**
         * first try and catch section  extracts the textfile contents
         * puts the textfile contents into a 2D float array
         *
         */
        int x=0;
        int y= 0;

        Float [] vals;



        try{
            File file= new File(args[0]);
            Scanner sc= new Scanner(file);
            String firstLine = sc.nextLine();
            String[] lineSplit= firstLine.split(" ");
            col=Integer.parseInt(lineSplit[0]) ;
            row= Integer.parseInt(lineSplit[1]) ;
            textGrid= new Float[col][row];
            vals= new Float[col*row];
            textGridOut= new String[col*row];



            while(sc.hasNextFloat()){

                textGrid[y][x]= sc.nextFloat();
                x= x+1;
                if (x==row){
                    x=0;
                    y= y+1;
                }


            }
            sc.close();
            int val;
            System.gc();



        }catch (FileNotFoundException e){
            e.printStackTrace();

        }
        tick();
        for (int i=0; i<row;i++){
            for (int j=0; j<col;j++){

                basinFind(i,j);


            }



        }
        Float time= tock();
        System.out.println("Sequential time is: "+ time+" Seconds");

        try{

            /**
             * creates sequential times textfile
             */
            File file2 = new File("Sequential times.txt");

            //Create the file
            if (file2.createNewFile())
            {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            /**
             * Write recorded times to textfile
             */



            BufferedWriter writer = new BufferedWriter(new FileWriter(file2, true));

            writer.write(  Float.toString(time));
            writer.newLine();

            //for (int k=0; k<plz.getValsOut();k++){
            //
            //}

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
          try{


            File file1 = new File(args[1]);

        //Create the file
              if (file1.createNewFile())
            {
            System.out.println("File is created!");
          } else {
           System.out.println("File already exists.");
        }

              /**
               * Write Content of output array to textfile
               */
        PrintWriter writer1 = new PrintWriter(file1);
        writer1.print("");
            writer1.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file1, true));
          writer.write(Integer.toString(valsOut));
           writer.newLine();
            for (int k=0; k<valsOut;k++){
                  writer.write(textGridOut[k]);
                 writer.newLine();
             }

              writer.close();
            }
          catch(IOException e) {
                 e.printStackTrace();
            }



    }
    /**
     * takes in one element of 2d array data and if value is basin stores it in a one dimension string array
     * @param i
     * @param j
     */
    public static void basinFind(int i,int j){


        if (( i>0) && (i <(row-1)) && (j>0) && (j<(col-1))){
            Float basin= textGrid[i][j];
            Float num1=  textGrid[i][j+1];
            Float num2= textGrid[i+1][j];
            Float num3= textGrid[i][j-1];
            Float num4= textGrid[i-1][j];
            Float num5= textGrid[i+1][j+1];
            Float num6= textGrid[i-1][j-1];
            Float num7= textGrid[i-1][j+1];
            Float num8= textGrid[i+1][j-1];

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
                    textGridOut[valsOut]= Integer.toString(i)+ " "+ Integer.toString(j);

                    valsOut= valsOut+1;


                }

            }
        }

    }

}
