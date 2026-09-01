package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.enums.Operation;
import static epsilon.utils.FunctionUtils.isNumeric;
import static epsilon.utils.FunctionUtils.isNumericList;
import static epsilon.utils.FunctionUtils.objectToDouble;
import static epsilon.utils.FunctionUtils.randomNumber;

public class NumericArray extends Array{
    public NumericArray(int capacity){
        super(capacity);
    }
    @Override
    public boolean add(Object object){
        if(isNumeric(object)){
            return super.add((Number)object);
        }
        else{
            return false;
        }
    }
    public boolean addSorted(Object object){
        if(isNumeric(object) && isFilled() == false){
            Number number = (Number)object;
            if(isSorted() == false){
                quickSort();
            }
            for (int i = 0; i < size(); i++) {
                Number tempNumber = (Number)data[i];
                if(number.doubleValue() < tempNumber.doubleValue()){
                    insert(object, i);
                    return true;
                }
            }
            return super.add((Number)object);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean modify(Object object, int index){
        if(isNumeric(object)){
            return super.modify((Number)object, index);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insert(Object object, int index){
        if(isNumeric(object)){
            return super.insert((Number)object, index);
        }
        else{
            return false;
        }
    }
    @Override
    public void fill(Object object){
        if(isNumeric(object)){
            super.fill(object);
        }
    }
    @Override
    public void refill(Object object){
        if(isNumeric(object)){
            super.refill(object);
        }
    }
    @Override
    public boolean addList(DataList dataList){
        if(isNumericList(dataList)){
            return super.addList(dataList);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean equals(DataList dataList){
        if(isNumericList(dataList)){
            return super.equals(dataList);
        }
        else{
            return false;
        }
    }
    @Override
    public DataList copy(){
        NumericArray copy = (NumericArray)super.copy();
        return copy;
    }
    @Override
    public boolean replace(DataList dataList){
        if(isNumericList(dataList)){
            return super.replace(dataList);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean modifyIterator(Object object){
        if(isNumeric(object)){
            return super.modifyIterator(object);
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
    public boolean addScalars(NumericArray array){
        return totalOperation(array, Operation.ADITTION);
    }
    public boolean subtractScalars(NumericArray array){
        return totalOperation(array, Operation.SUBTRACTION);
    }
    public boolean multiplyScalars(NumericArray array){
        return totalOperation(array, Operation.MULTIPLICATION);
    }
    public boolean divideScalars(NumericArray array){
        return totalOperation(array, Operation.DIVISION);
    }
    public boolean powScalars(NumericArray array){
        return totalOperation(array, Operation.POW);
    }
    public NumericArray ArrayPlusScalar(Number scalar){
        NumericArray copy = (NumericArray)copy();
        copy.addScalar(scalar);
        return copy;
    }
    public NumericArray ArrayForScalar(Number scalar){
        NumericArray copy = (NumericArray)copy();
        copy.multiplyScalar(scalar);
        return copy;
    }
    public NumericArray ArrayDividedScalar(Number scalar){
        NumericArray copy = (NumericArray)copy();
        copy.divideScalar(scalar);
        return copy;
    }
    public NumericArray ArrayPowScalar(Number scalar){
        NumericArray copy = (NumericArray)copy();
        copy.powScalar(scalar);
        return copy;
    }
    public NumericArray ArrayPlusArray(NumericArray array){
        NumericArray copy = (NumericArray)copy();
        copy.addScalars(array);
        return copy;
    }
    public NumericArray ArrayMinusArray(NumericArray array){
        NumericArray copy = (NumericArray)copy();
        copy.subtractScalars(array);
        return copy;
    }
    public NumericArray ArrayForArray(NumericArray array){
        NumericArray copy = (NumericArray)copy();
        copy.multiplyScalars(array);
        return copy;
    }
    public NumericArray ArrayDividedArray(NumericArray array){
        NumericArray copy = (NumericArray)copy();
        copy.divideScalars(array);
        return copy;
    }
    public NumericArray ArrayPowArray(NumericArray array){
        NumericArray copy = (NumericArray)copy();
        copy.powScalars(array);
        return copy;
    }
    public boolean scalarOperation(Number scalar, Operation operation){
        if(isEmpty() == false){
            for (int index = 0; index < size(); index++) {
                double number = objectToDouble(get(index));
                double result = operation.solveOperation(number, scalar.doubleValue());
                modify(result, index);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean totalOperation(NumericArray array, Operation operation){
        if(isEmpty() == false && array.size() == size()){
            for (int index = 0; index < size(); index++) {
                double number1 = objectToDouble(get(index));
                double number2 = objectToDouble(array.get(index));
                double result = operation.solveOperation(number1, number2);
                modify(result, index);
            }
            return true;
        }
        else{
            return false;
        }
    }
    public double getTotal(){
        double total = 0;
        for (int i = 0; i < size(); i++) {
            Number tempNumber = (Number)get(i);
            total += tempNumber.doubleValue();
        }
        return total;
    }
    public double getAverage(){
        if(isEmpty() == false){
            double average = getTotal();
            return average/size();
        }
        else{
            return 0;
        }
    }
    public double getHighestNumber(){
        if(isEmpty() == false){
            double highest = objectToDouble(get(0));
            for(int i = 1; i < size(); i++){
                double tempDouble = objectToDouble(get(i));
                if(tempDouble > highest){
                    highest = tempDouble;
                }
            }
            return highest;
        }
        else{
            return 0;
        }
    }
    public double getLowestNumber(){
        if(isEmpty() == false){
            double lowest = objectToDouble(get(0));
            for(int i = 1; i < size(); i++){
                double tempDouble = objectToDouble(get(i));
                if(tempDouble < lowest){
                    lowest = tempDouble;
                }
            }
            return lowest;
        }
        else{
            return 0;
        }
    }
    public NumericArray getDistances(){
        NumericArray distances = new NumericArray(size());
        for (int i = 1; i < size(); i++) {
            double d1 = objectToDouble(get(i-1));
            double d2 = objectToDouble(get(i));
            distances.add(d2-d1);
        }
        distances.add(objectToDouble(get(0))-objectToDouble(get(upperIndex)));
        return distances;
    }
    public double getNorm(){
        NumericArray copy = (NumericArray)copy();
        copy.powScalar(2);
        double total = copy.getTotal();
        return Math.sqrt(total);
    }
    public double scalarProduct(NumericArray array){
        if(isEmpty() == false && array.size() == size()){
            array.multiplyScalars(this);
            return array.getTotal();
        }
        else{
            return 0;
        }
    }
    public double euclideanDistance(NumericArray array){
        if(isEmpty() == false && array.size() == size()){
            array.subtractScalars(this);
            array.powScalar(2);
            double total = array.getTotal();
            return Math.sqrt(total);
        }
        else{
            return 0;
        }
    }
    public boolean isOrthogonal(NumericArray array){
        if(isEmpty() == false && size() == array.size()){
            double scalarProduct = scalarProduct(array);
            return scalarProduct == 0;
        }
        else{
            return false;
        }
    }
    public boolean isParallel(NumericArray array){
        if(isEmpty() == false && size() == array.size()){
            array.divideScalars(this);
            double firstNumber = objectToDouble(array.get(0));
            for (int i = 1; i < array.size(); i++) {
                if(firstNumber != objectToDouble(array.get(i))){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isSorted(){
        for (int i = 1; i < size(); i++) {
            double number1 = objectToDouble(data[i-1]);
            double number2 = objectToDouble(data[i]);
            if(number2 < number1){
                return false;
            }
        }
        return true;
    }
    public void quickSort(){
        if(size() > 1){
            int index = randomNumber(0,size());
            double pivot = objectToDouble(get(index));
            NumericArray leftArray = new NumericArray(size());
            NumericArray rightArray = new NumericArray(size());
            Object obj;
            for (int i = 0; i < size(); i++) {
                if(i != index){
                    obj = get(i); 
                    double number = objectToDouble(obj);
                    if(number < pivot){
                        leftArray.add(obj);
                    }
                    else if(number >= pivot){
                        rightArray.add(obj);
                    }
                }
            }
            obj = get(index);
            clear();
            addList(leftArray);
            add(obj);
            addList(rightArray);
        }
    }
}