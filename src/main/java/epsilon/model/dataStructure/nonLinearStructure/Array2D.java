package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.getMin;
import static epsilon.utils.FunctionUtils.isInRange;

public class Array2D{
    protected Object[][] data;
    protected int height;
    protected int width;
    public Array2D(int height, int width){
        data = new Object[height][width];
        this.height = height;
        this.width = width;
    }
    public Object getObject(int row, int column){
        if(validIndexes(row, column)){
            return data[row][column];
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
    public int getArea(){
        return height*width;
    }
    public Array getRow(int row){
        if(validIndex(row, height)){
            Array arrayRow = new Array(width);
            for (int column = 0; column < width; column++) {
                arrayRow.add(data[row][column]);
            }
            return arrayRow;
        }
        else{
            return null;
        }
    }
    public Array getColumn(int column){
        if(validIndex(column, width)){
            Array arrayColumn = new Array(height);
            for (int row = 0; row < height; row++) {
                arrayColumn.add(data[row][column]);
            }
            return arrayColumn;
        }
        else{
            return null;
        }
    }
    public boolean modify(Object object, int row, int column){
        if(validIndexes(row, column)){
            data[row][column] = object;
            return true;
        }
        else{
            return false;
        }
    }
    public void refill(Object object){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                data[row][column] = object;
            }
        }
    }
    public void fill(Object object){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if(data[row][column] == null){
                    data[row][column] = object;
                }
            }
        }
    }
    public boolean redefine(Array2D matrix){
        if(matrix != null){
            this.width = matrix.width;
            this.height = matrix.height;
            data = new Object[height][width];
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    data[row][column] = matrix.getObject(row, column);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public void transposed(){
        Array2D transposed = new Array2D(width,height);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                transposed.modify(data[row][column], column, row);
            }
        }
        redefine(transposed);
    }
    public boolean resize(int height, int width){
        if(height > 0 && width > 0){
            Object[][] newData = new Object[height][width];
            for (int row = 0; row < getMin(this.height,height); row++) {
                System.arraycopy(data[row], 0, newData[row], 0, (int)getMin(this.width,width));
            }
            this.height = height;
            this.width = width;
            data = newData;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addColumn(Array column){
        if(column.getQuantity() == height){
            resize(height, width+1);
            for (int row = 0; row < height; row++) {
                modify(column.get(row), row, width-1);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addRow(Array row){
        if(row.getQuantity() == width){
            resize(height+1, width);
            for (int column = 0; column < width; column++) {
                modify(row.get(column), height-1, column);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addColumnMatrix(Array2D matrix){
        if(matrix.getHeight() == height){
            for (int row = 0; row < matrix.getHeight(); row++) {
                addColumn(matrix.getColumn(row));
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addRowMatrix(Array2D matrix){
        if(matrix.getWidth() == width){
            for (int column = 0; column < matrix.getWidth(); column++) {
                addRow(matrix.getRow(column));
            }
            return true;
        }
        else{
            return false;
        }
    }
    public Array2D toColumnVector(){
        Array2D columnVector = new Array2D(getArea(),1);
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                columnVector.modify(data[row][column], row+(column*height), 0);
            }
        }
        return columnVector;
    }
    public Array2D toRowVector(){
        Array2D rowVector = new Array2D(1,getArea());
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                rowVector.modify(data[row][column], 0, column+(row*width));
            }
        }
        return rowVector;
    }
    public Array removeColumn(){
        Array removedColumn = getColumn(width-1);
        resize(height, width-1);
        return removedColumn;
    }
    public Array removeRow(){
        Array removedRow = getRow(height-1);
        resize(height-1, width);
        return removedRow;
    }
    public Array removeColumn(int column){
        if(validIndex(column, width)){
            Array removedColumn = getColumn(column);
            for (int row = 0; row < height; row++) {
                for (int columns = column; columns < width-1; columns++) {
                    data[row][columns] = data[row][columns+1];
                }
            }
            resize(height, width-1);
            return removedColumn;
        }
        else{
            return null;
        }
    }
    public Array removeRow(int row){
        if(validIndex(row, height)){
            Array removedRow = getRow(row);
            for (int column = 0; column < width; column++) {
                for (int rows = row; rows < height-1; rows++) {
                    data[rows][column] = data[rows+1][column];
                }
            }
            resize(height-1, width);
            return removedRow;
        }
        else{
            return null;
        }
    }
    public boolean reverseColumn(int column){
        return reverseColumn(column, 0, height-1);
    }
    public boolean reverseRow(int row){
        return reverseRow(row, 0, width-1);
    }
    public boolean reverseColumn(int column, int minRow, int maxRow){
        if (validIndex(column, width) && validIndex(minRow, height) && validIndex(maxRow, height)) {
            if(minRow > maxRow){
                int temp = maxRow;
                maxRow = minRow;
                minRow = temp;
            }
            while(minRow < maxRow){
                Object temp = data[maxRow][column];
                data[maxRow][column] = data[minRow][column];
                data[minRow][column] = temp;
                minRow++;
                maxRow--;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean reverseRow(int row, int minColumn, int maxColumn){
        if (validIndex(row, height) && validIndex(minColumn, width) && validIndex(maxColumn, width)) {
            if(minColumn > maxColumn){
                int temp = maxColumn;
                maxColumn = minColumn;
                minColumn = temp;
            }
            while(minColumn < maxColumn){
                Object temp = data[row][maxColumn];
                data[row][maxColumn] = data[row][minColumn];
                data[row][minColumn] = temp;
                minColumn++;
                maxColumn--;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean rotateColumn(int rotations, int column){
        if(validIndex(column, width)){
            rotations = rotations%height;
            if(rotations < 0){
                rotations += height;
            }
            else if(rotations == 0){
                return true;
            }
            reverseColumn(column);
            reverseColumn(column, 0, rotations-1);
            reverseColumn(column, rotations, height-1);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean rotateRow(int rotations, int row){
        if(validIndex(row, height)){
            rotations = rotations%width;
            if(rotations < 0){
                rotations += width;
            }
            else if(rotations == 0){
                return true;
            }
            reverseRow(row);
            reverseRow(row, 0, rotations-1);
            reverseRow(row, rotations, width-1);
            return true;
        }
        else{
            return false;
        }
    }
    public void rotateColumns(int rotations){
        for (int row = 0; row < height; row++) {
            rotateRow(rotations, row);
        } 
    }
    public void rotateRows(int rotations){
          for (int column = 0; column < width; column++) {
            rotateColumn(rotations, column);
        }     
    }
    public boolean overrideColumn(Array newColumn, int column){
        if(validIndex(column, width) && newColumn.getQuantity() == height){
            for (int row = 0; row < height; row++) {
                modify(newColumn.get(row), row, column);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean overrideRow(Array newRow, int row){
        if(validIndex(row, height) && newRow.getQuantity() == width){
            for (int column = 0; column < width; column++) {
                modify(newRow.get(column), row, column);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean insertColumn(Array newColumn, int column){
        if(validIndex(column, width) && newColumn.getQuantity() == height){
            resize(height, width+1);
            for (int columns = width-1; columns > column; columns--) {
                overrideColumn(getColumn(columns-1), columns);
            }
            overrideColumn(newColumn, column);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean insertRow(Array newRow, int row){
        if(validIndex(row, height) && newRow.getQuantity() == width){
            resize(height+1, width);
            for (int rows = height-1; rows > row; rows--) {
                overrideRow(getRow(rows-1), rows);
            }
            overrideRow(newRow, row);
            return true;
        }
        else{
            return false;
        }
    }
    public void horizontalInvert(){
        for (int row = 0; row < height; row++) {
            reverseRow(row);
        }
    }
    public void verticalInvert(){
        for (int column = 0; column < width; column++) {
            reverseColumn(column);
        }
    }
    public void rotateLeft(){
        horizontalInvert();
        transposed();
    }
    public void rotateRight(){
        verticalInvert();
        transposed();
    }
    public void rotate180(){
        horizontalInvert();
        verticalInvert();
    }
    public Array2D getSubArray(int lowRow, int lowColumn, int highRow, int highColumn){
        if(validIndexes(lowRow, lowColumn) && validIndexes(highRow, highColumn)){
            if(lowRow > highRow){
                int temp = highRow;
                highRow = lowRow;
                lowRow = temp;
            }
            if(lowColumn > highColumn){
                int temp = highColumn;
                highColumn = lowColumn;
                lowColumn = temp;
            }
            Array2D subArray = new Array2D(highRow-lowRow+1, highColumn-lowColumn+1);
            for (int row = lowRow; row <= highRow; row++) {
                for (int column = lowColumn; column <= highColumn; column++) {
                    subArray.modify(data[row][column], row-lowRow, column-lowColumn);
                }
            }
            return subArray;
        }
        else{
            return null;
        }
    }
    public void iterateRows(Iterator iterator){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                boolean keepGoing = iterator.iterate(data[row][column]);
                if(keepGoing == false){
                    return;
                }
            }
        }
    }
    public void iterateColumns(Iterator iterator){
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                boolean keepGoing = iterator.iterate(data[row][column]);
                if(keepGoing == false){
                    return;
                }
            }
        }
    }
    public Array2D copy(){
        Array2D arrayCopy = new Array2D(height, width);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                arrayCopy.modify(getObject(row, column), row, column);
            }
        }
        return arrayCopy;
    }
    public void printRows(){
        for (int row = 0; row < height; row++) {
            System.out.print("|");
            for (int column = 0; column < width; column++) {
                System.out.print(data[row][column] + "|");
            }
            System.out.print("\n");
        }
    }
    public void printColumns(){
        for (int column = 0; column < width; column++) {
            System.out.print("|");
            for (int row = 0; row < height; row++) {
                System.out.print(data[row][column] + "|");
            }
            System.out.print("\n");
        }
    }
    private boolean validIndexes(int row, int column){
        return isInRange(0,width-1,column) && isInRange(0,height-1,row);
    }
    private boolean validIndex(int index, int size){
        return isInRange(0, size-1, index);
    }
}