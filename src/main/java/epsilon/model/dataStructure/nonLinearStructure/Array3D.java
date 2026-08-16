package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.getMin;
import static epsilon.utils.FunctionUtils.isInRange;

public class Array3D{
    protected Object[][][] data;
    protected int height;
    protected int width;
    protected int depth;
    public Array3D(int height, int width, int depth){
        data = new Object[height][width][depth];
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
    public Object getObject(int row, int column, int deep){
        if(validIndexes(row, column, deep) == true){
            return data[row][column][deep];
        }
        else{
            return null;
        }
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public int getDepth(){
        return depth;
    }
    public Array2D getHorizontalSlice(int slice){
        if(validIndex(slice, depth)){
            Array2D horizontalSlice = new Array2D(height, width);
            for (int row = 0; row < horizontalSlice.getHeight(); row++) {
                for (int column = 0; column < horizontalSlice.getWidth(); column++) {
                    Object obj = getObject(row, column, slice);
                    horizontalSlice.modify(obj, row, column);
                }
            }
            return horizontalSlice;
        }
        else{
            return null;
        }
    }
    public Array2D getVerticalSlice(int slice){
        if(validIndex(slice, width)){
            Array2D verticalSlice = new Array2D(height, depth);
            for (int row = 0; row < verticalSlice.getHeight(); row++) {
                for (int deep = 0; deep < verticalSlice.getWidth(); deep++) {
                    Object obj = getObject(row, slice, deep);
                    verticalSlice.modify(obj, row, deep);
                }
            }
            return verticalSlice;
        }
        else{
            return null;
        }
    }
    public Array2D getFloorSlice(int slice){
        if(validIndex(slice, height)){
            Array2D floorSlice = new Array2D(width, depth);
            for (int column = 0; column < floorSlice.getHeight(); column++) {
                for (int deep = 0; deep < floorSlice.getWidth(); deep++) {
                    Object obj = getObject(slice, column, deep);
                    floorSlice.modify(obj, column, deep);
                }
            }
            return floorSlice;
        }
        else{
            return null;
        }
    }
    public Array getRow(int row, int deep){
        if(validIndex(row, height) && validIndex(deep, depth)){
            Array rowArray = new Array(width);
            for (int column = 0; column < width; column++) {
                rowArray.add(data[row][column][deep]);
            }
            return rowArray;
        }
        else{
            return null;
        }
    }
    public Array getColumn(int column, int deep){
        if(validIndex(column, width) && validIndex(deep, depth)){
            Array columnArray = new Array(height);
            for (int row = 0; row < height; row++) {
                columnArray.add(data[row][column][deep]);
            }
            return columnArray;
        }
        else{
            return null;
        }
    }
    public Array getDeepRow(int row, int column){
        if(validIndex(row, height) && validIndex(column, width)){
            Array deepRowArray = new Array(depth);
            for (int deep = 0; deep < depth; deep++) {
                deepRowArray.add(data[row][column][deep]);
            }
            return deepRowArray;
        }
        else{
            return null;
        }
    }
    public boolean modify(Object object, int row, int column, int deep){
        if(validIndexes(row, column, deep) == true){
            data[row][column][deep] = object;
            return true;
        }
        else{
            return false;
        }
    }
    public void refill(Object object){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                for (int deep = 0; deep < depth; deep++) {
                    data[row][column][deep] = object;
                }
            }
        }
    }
    public void fill(Object object){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                for (int deep = 0; deep < depth; deep++) {
                    if(data[row][column][deep] == null){
                        data[row][column][deep] = object;
                    }
                }
            }
        }
    }
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public boolean resize(int height, int width, int depth){
        if(height > 0 && width > 0 && depth > 0){
            Object[][][] newData = new Object[height][width][depth];
            for (int row = 0; row < getMin(this.height,height); row++) {
                for (int column = 0; column < getMin(this.width,width); column++) {
                    for (int deep = 0; deep < getMin(this.depth,depth); deep++) {
                        newData[row][column][deep] = data[row][column][deep];
                    }
                }
            }
            this.height = height;
            this.width = width;
            this.depth = depth;
            data = newData;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addHorizontalWall(Array2D wall){
        if(wall.getHeight() == height && wall.getWidth() == width){
            resize(height, width, depth+1);
            for (int row = 0; row < wall.getHeight(); row++) {
                for (int column = 0; column < wall.getWidth(); column++) {
                    Object obj = wall.getObject(row, column);
                    modify(obj, row, column, depth-1);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addVerticalWall(Array2D wall){
        if(wall.getHeight() == height && wall.getWidth() == depth){
            resize(height, width+1, depth);
            for (int row = 0; row < wall.getHeight(); row++) {
                for (int column = 0; column < wall.getWidth(); column++) {
                    int deep = column;
                    Object obj = wall.getObject(row, column);
                    modify(obj, row, width-1, deep);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addFloor(Array2D floor){
        if(floor.getWidth() == width && floor.getHeight() == depth){
            resize(height+1, width, depth);
            for (int row = 0; row < floor.getHeight(); row++) {
                int deep = row;
                for (int column = 0; column < floor.getWidth(); column++) {
                    Object obj = floor.getObject(row, column);
                    modify(obj, height-1, column, deep);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    protected boolean validIndexes(int row, int column, int deep){
        return validIndex(row, height) && validIndex(column, width) && validIndex(deep, depth);
    }
    protected boolean validIndex(int index, int size){
        return isInRange(0,size-1,index);
    }
}