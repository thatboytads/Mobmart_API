public class GridControl {
    //grid postitions, height, and amount of water
    private int gridRow;
    private int gridCol;
    private float gridWater;
    private float gridHei;

    /**
     * constructor
     * @param gridRow the row
     * @param gridCol the column
     * @param gridHei the height of the terrain
     */
    public GridControl(int gridRow,int gridCol, int gridHei){
        this.gridRow= gridRow;
        this.gridCol= gridCol;
        this.gridHei= gridHei;
        this.gridWater=0;

    }

    /**
     *
     * @return returns the row index of our grid
     */
    public int getGridRow(){
        return this.gridRow;
    }

    /**
     *
     * @return returns the column index of our grid
     */
    public int getGridCol(){
        return this.gridCol;
    }
    /**
     *
     * @return returns the height of the item in our grid
     */
    public Float getGridHei(){
        return this.gridHei;
    }
    /**
     *
     * @return returns the height of the water in our grid
     */
    synchronized  Float getGridWater(){
        return this.gridWater;
    }

    /**
     *
     * @returns the water surface which is the height of the postion with water added on
     */
    synchronized  Float getWaterSurface(){
     return this.gridHei+ this.gridWater*0.01f;
    }

    /**
     *
     * @param numWater increases the water layer by 1
     * @return
     */
    synchronized void addGridWater(int numWater){
        this.gridWater += numWater;
    }
    /**
     *
     * @param numWater increases the water layer by value of numWater
     * @return
     */
    synchronized void deductGridWater(int numWater){
        this.gridWater += numWater;
    }

    /**
     * resets water on grid position to zero
     */
    synchronized void resetGridWater(){
        this.gridWater=0;
    }

}
