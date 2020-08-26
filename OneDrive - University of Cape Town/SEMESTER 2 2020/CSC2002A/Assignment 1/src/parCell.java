import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class parCell {

    static Float [][] textGrid;
    static int row, col;


    static  String []basinGridOut;
    static  String []basinGridPara;
    static  int valsOut= 0;
    static  int valsOut1= 0;
    static long startTime = 0;
    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static String[] para(Float [][] textGrid1,int row1,int col1){
        return fjPool.invoke(new parallel(textGrid1,basinGridPara,0,row1,col1,0));
    }
    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main( String [] args )

    {
        /**
         * first try section extracts extracts the textfile contents
         * puts the textfile contents into a 2D float array
         *
         */

        int x=0;
        int y= 0;





        try{
            File file= new File(args[0]);
            Scanner sc= new Scanner(file);
            String firstLine = sc.nextLine();
            String[] lineSplit= firstLine.split(" ");
            col=Integer.parseInt(lineSplit[0]) ;
             row= Integer.parseInt(lineSplit[1]) ;
            textGrid= new Float[col][row];

            basinGridOut= new String[col];
            basinGridPara= new String[col];



            while(sc.hasNextFloat()){

                textGrid[y][x]= sc.nextFloat();
                x= x+1;
                if (x==row){
                    x=0;
                    y= y+1;
                }


            }
            sc.close();


        }catch (FileNotFoundException e){
            e.printStackTrace();

        }


        /**
         * initializes the parallel code and times how long it takes to find basins. This is done 20 times across 3 different textfiles to compare times
         */

        String [] paraBasin=  para(textGrid,row,col) ;
        for (int i= 0; i< 20;i++){

            tick();
            String [] paraBasin1=  para(textGrid,row,col) ;

            Float time= tock();
            System.out.println("parallel time is: "+ time+" "+i+" Seconds");
            try{

                /**
                 * creates parallel times textfile
                 */
                File file2 = new File("Parallel times.txt");

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



        }

        int numBasins= 0;
        //fjPool.shutdown();


        for (int k=0; k<paraBasin.length;k++){

            if (paraBasin[k]!= null){
                numBasins= numBasins+1;
            }

        }

        try{

            /**
             * creates basin output textfile if file does not exist
             */
            File file1 = new File(args[1]);

            //Create the file
            if (file1.createNewFile())
            {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            /**
             * Write Content from basin array to textfile
             */

            PrintWriter writer1 = new PrintWriter(file1);
            writer1.print("");
            writer1.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file1, true));

           writer.write(Integer.toString(numBasins));
            writer.newLine();
            for (int k=0; k<numBasins;k++){
                writer.write(paraBasin[k]);
                writer.newLine();

            }
            //for (int k=0; k<plz.getValsOut();k++){
             //
            //}

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

}
