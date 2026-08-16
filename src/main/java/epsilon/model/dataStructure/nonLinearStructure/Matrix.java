package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.enums.Operation;
import static epsilon.utils.FunctionUtils.isNumeric;

public class Matrix extends Array2D{
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Matrix(int height, int width){
        super(height, width);
        super.fill(0);
    }
    @Override
    public boolean modify(Object object, int row, int column){
        if(object instanceof Number){
            return super.modify(object, row, column);
        }
        else{
            return false;
        }
    }
    @Override
    public void refill(Object object){
        if(isNumeric(object)){
            super.refill(object);
        }
    }
    @Override
    public void fill(Object object){
        if(isNumeric(object)){
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    double currentNumber = objectToDouble(data[row][column]);
                    if(currentNumber == 0){
                        data[row][column] = object;
                    }
                }
            }
        }
    }
    @Override
    public boolean redefine(Array2D matrix){
        if(matrix instanceof Matrix){
            return super.redefine(matrix);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean addColumn(Array column){
        if(isArrayNumeric(column)){
            return super.addColumn(column);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean addRow(Array row){
        if(isArrayNumeric(row)){
            return super.addRow(row);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean addColumnMatrix(Array2D matrix){
        if(matrix instanceof Matrix){
            return super.addColumnMatrix(matrix);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean addRowMatrix(Array2D matrix){
        if(matrix instanceof Matrix){
            return super.addRowMatrix(matrix);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean overrideColumn(Array newColumn, int column){
        if(isArrayNumeric(newColumn)){
            return super.overrideColumn(newColumn, column);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean overrideRow(Array newRow, int row){
        if(isArrayNumeric(newRow)){
            return super.overrideRow(newRow, row);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insertColumn(Array newColumn, int column){
        if(isArrayNumeric(newColumn)){
            return super.insertColumn(newColumn, column);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insertRow(Array newRow, int row){
        if(isArrayNumeric(newRow)){
            return super.insertRow(newRow, row);
        }
        else{
            return false;
        }
    }
    public boolean addScalar(Number scalar){
        return scalarOperation(scalar, Operation.ADITTION);
    }
    public boolean multiplyScalar(Number scalar){
        return scalarOperation(scalar, Operation.MULTIPLICATION);
    }
    public boolean divideScalar(Number scalar){
        return scalarOperation(scalar, Operation.DIVISION);
    }
    public boolean powScalar(Number scalar){
        return scalarOperation(scalar, Operation.POW);
    }
    public Matrix matrixPlusScalar(Number scalar){
        Matrix copy = (Matrix)copy();
        copy.scalarOperation(scalar, Operation.ADITTION);
        return copy;
    }
    public Matrix matrixMultiplyScalar(Number scalar){
        Matrix copy = (Matrix)copy();
        copy.scalarOperation(scalar, Operation.MULTIPLICATION);
        return copy;
    }
    public Matrix matrixDivideScalar(Number scalar){
        Matrix copy = (Matrix)copy();
        copy.scalarOperation(scalar, Operation.DIVISION);
        return copy;
    }
    public Matrix matrixPowScalar(Number scalar){
        Matrix copy = (Matrix)copy();
        copy.scalarOperation(scalar, Operation.POW);
        return copy;
    }
    public boolean addScalars(Array scalars){
        return totalOperation(scalars, Operation.ADITTION);
    }
    public boolean multiplyScalars(Array scalars){
        return totalOperation(scalars, Operation.MULTIPLICATION);
    }
    public boolean divideScalars(Array scalars){
        return totalOperation(scalars, Operation.DIVISION);
    }
    public boolean powScalars(Array scalars){
        return totalOperation(scalars, Operation.POW);
    }
    public Matrix matrixPlusScalars(Array scalars){
        Matrix copy = (Matrix)copy();
        copy.totalOperation(scalars, Operation.ADITTION);
        return copy;
    }
    public Matrix matrixMultiplyScalars(Array scalars){
        Matrix copy = (Matrix)copy();
        copy.totalOperation(scalars, Operation.MULTIPLICATION);
        return copy;
    }
    public Matrix matrixDivideScalars(Array scalars){
        Matrix copy = (Matrix)copy();
        copy.totalOperation(scalars, Operation.DIVISION);
        return copy;
    }
    public Matrix matrixPowScalars(Array scalars){
        Matrix copy = (Matrix)copy();
        copy.totalOperation(scalars, Operation.POW);
        return copy;
    }
    protected boolean scalarOperation(Number scalar, Operation operation){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double number = objectToDouble(getObject(row, column));
                double result = operation.solveOperation(number, scalar.doubleValue());
                modify(result, row, column);
            }
        }
        return true;
    }
    protected boolean totalOperation(Array scalars, Operation operation){
        if(isArrayNumeric(scalars) && scalars.getQuantity() == getArea()){
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    double number = objectToDouble(getObject(row, column));
                    double scalar = objectToDouble(scalars.get(column+(row*width)));
                    double result = operation.solveOperation(number, scalar);
                    modify(result, row, column);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public Matrix multiply(Matrix matrix){
        if(width == matrix.getHeight()){
            Matrix finalMatrix = new Matrix(height, matrix.getWidth());
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < matrix.getWidth(); column++) {
                    double subtotal = 0;
                    for (int index = 0; index < width; index++) {
                        double A = objectToDouble(getObject(row, index));
                        double B = objectToDouble(matrix.getObject(index, column));
                        double result = A*B;
                        subtotal += result;
                    }
                    finalMatrix.modify(subtotal, row, column);
                }
            }
            return finalMatrix;
        }
        else{
            return null;
        }
    }
    public Matrix addMatrix(Matrix matrix){
        if(height == matrix.height && width == matrix.width){
            Matrix newMatrix = new Matrix(height, width);
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    newMatrix.modify(objectToDouble(getObject(row, column))+
                    objectToDouble(matrix.getObject(row, column)), row, column);
                }
            }
            return newMatrix;
        }
        else{
            return null;
        }
    }
    @Override
    public Array2D copy(){
        Matrix arrayCopy = new Matrix(height, width);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                arrayCopy.modify(getObject(row, column), row, column);
            }
        }
        return arrayCopy;
    }
    protected boolean isArrayNumeric(Array array){
        for (int i = 0; i < array.getQuantity(); i++) {
            if (isNumeric(array.get(i)) == false) {
                return false;
            }
        }
        return true;
    }
    protected Double objectToDouble(Object object){
        if(isNumeric(object)){
            Number number = (Number)object;
            return number.doubleValue();
        }
        else{
            return null;
        }
    }
}