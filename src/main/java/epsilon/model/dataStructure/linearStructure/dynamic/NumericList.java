package epsilon.model.dataStructure.linearStructure.dynamic;

import epsilon.model.dataStructure.auxiliar.Node;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.interfaces.NumberList;
import epsilon.model.enums.Operation;
import static epsilon.utils.FunctionUtils.isNumeric;
import static epsilon.utils.FunctionUtils.isNumericList;
import static epsilon.utils.FunctionUtils.objectToDouble;

public class NumericList extends LinkedList implements NumberList{
    @Override
    public boolean addAtStart(Object object){
        if(isNumeric(object)){
            return super.addAtStart(object);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean add(Object object){
        if(isNumeric(object)){
            return super.add(object);
        }
        else{
            return false;
        }
    }
    @Override
    protected Node getNode(Object object){
        if(isNumeric(object)){
            return super.getNode(object);
        }
        else{
            return null;
        }
    }
    @Override
    public Object find(Object object){
        if(isNumeric(object)){
            return super.find(object);
        }
        else{
            return null;
        }
    }
    @Override
    public int findPosition(Object object){
        if(isNumeric(object)){
            return super.findPosition(object);
        }
        else{
            return -1;
        }
    }
    @Override
    public boolean modify(Object object, int index){
        if(isNumeric(object)){
            return super.modify(object,index);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insert(Object object, int index){
        if(isNumeric(object)){
            return super.insert(object,index);
        }
        else{
            return false;
        }
    }
    @Override
    public Object remove(Object object){
        if(isNumeric(object)){
            return super.remove(object);
        }
        else{
            return null;
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
    public int count(Object object){
        if(isNumeric(object)){
            return super.count(object);
        }
        else{
            return 0;
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
        NumericList copy = new NumericList();
        Node tempNode = first;
        while(tempNode != null){
            copy.add(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
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
    public boolean addScalars(NumericList list){
        return totalOperation(list, Operation.ADITTION);
    }
    public boolean subtractScalars(NumericList list){
        return totalOperation(list, Operation.SUBTRACTION);
    }
    public boolean multiplyScalars(NumericList list){
        return totalOperation(list, Operation.MULTIPLICATION);
    }
    public boolean divideScalars(NumericList list){
        return totalOperation(list, Operation.DIVISION);
    }
    public boolean powScalars(NumericList list){
        return totalOperation(list, Operation.POW);
    }
    public NumericList ArrayPlusScalar(Number scalar){
        NumericList copy = (NumericList)copy();
        copy.addScalar(scalar);
        return copy;
    }
    public NumericList ArrayForScalar(Number scalar){
        NumericList copy = (NumericList)copy();
        copy.multiplyScalar(scalar);
        return copy;
    }
    public NumericList ArrayDividedScalar(Number scalar){
        NumericList copy = (NumericList)copy();
        copy.divideScalar(scalar);
        return copy;
    }
    public NumericList ArrayPowScalar(Number scalar){
        NumericList copy = (NumericList)copy();
        copy.powScalar(scalar);
        return copy;
    }
    public NumericList ArrayPlusArray(NumericList array){
        NumericList copy = (NumericList)copy();
        copy.addScalars(array);
        return copy;
    }
    public NumericList ArrayMinusArray(NumericList array){
        NumericList copy = (NumericList)copy();
        copy.subtractScalars(array);
        return copy;
    }
    public NumericList ArrayForArray(NumericList array){
        NumericList copy = (NumericList)copy();
        copy.multiplyScalars(array);
        return copy;
    }
    public NumericList ArrayDividedArray(NumericList array){
        NumericList copy = (NumericList)copy();
        copy.divideScalars(array);
        return copy;
    }
    public NumericList ArrayPowArray(NumericList array){
        NumericList copy = (NumericList)copy();
        copy.powScalars(array);
        return copy;
    }
    @Override
    public boolean scalarOperation(Number scalar, Operation operation){
        if(isEmpty() == false){
            Node tempNode = first;
            while (tempNode != null) { 
                double number = objectToDouble(tempNode.getData());
                double result = operation.solveOperation(number, scalar.doubleValue());
                tempNode.setData(result);
                tempNode = tempNode.getRightNode();
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean totalOperation(NumberList numbers, Operation operation){
        if(isEmpty() == false && numbers.size() == size()){
            Node tempNode1 = first;
            numbers.initializeIterator();
            while (tempNode1 != null) { 
                double number1 = objectToDouble(tempNode1.getData());
                double number2 = objectToDouble(numbers.getIterator());
                double result = operation.solveOperation(number1, number2);
                tempNode1.setData(result);
                tempNode1 = tempNode1.getRightNode();
                numbers.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public double getTotal(){
        double total = 0;
        Node tempNode = first;
        while(tempNode != null){
            total+= objectToDouble(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
        return total;
    }
    @Override
    public double getAverage(){
        if(isEmpty() == false){
            double average = getTotal();
            return average/size();
        }
        else{
            return 0;
        }
    }
    @Override
    public double getHighestNumber(){
        if(isEmpty() == false){
            double highest = objectToDouble(get(0));
            Node tempNode = first;
            while (tempNode != null) { 
                double tempDouble = objectToDouble(tempNode.getData());
                if(tempDouble > highest){
                    highest = tempDouble;
                }
                tempNode = tempNode.getRightNode();
            }
            return highest;
        }
        else{
            return 0;
        }
    }
    @Override
    public double getLowestNumber(){
        if(isEmpty() == false){
            double lowest = objectToDouble(get(0));
            Node tempNode = first;
            while (tempNode != null) { 
                double tempDouble = objectToDouble(tempNode.getData());
                if(tempDouble < lowest){
                    lowest = tempDouble;
                }
                tempNode = tempNode.getRightNode();
            }
            return lowest;
        }
        else{
            return 0;
        }
    }
    @Override
    public NumberList getDistances(){
        if(size() > 1){
            NumericList distances = new NumericList();
            Node tempNode = first.getRightNode();
            while(tempNode != null) {
                double d1 = objectToDouble(tempNode.getLeftNode().getData());
                double d2 = objectToDouble(tempNode.getData());
                distances.add(d2-d1);
            }
            distances.add(objectToDouble(getFirstObject())-objectToDouble(getLastObject()));
            return distances;
        }
        else{
            return null;
        }
    }
    @Override
    public double getNorm(){
        NumericList copy = (NumericList)copy();
        copy.powScalar(2);
        double total = copy.getTotal();
        return Math.sqrt(total);
    }
    @Override
    public double scalarProduct(NumberList list){
        if(isEmpty() == false && list.size() == size()){
            list.totalOperation(this, Operation.MULTIPLICATION);
            return list.getTotal();
        }
        else{
            return 0;
        }
    }
    @Override
    public double euclideanDistance(NumberList list){
        if(isEmpty() == false && list.size() == size()){
            list.totalOperation(this, Operation.SUBTRACTION);
            list.scalarOperation(2, Operation.POW);
            double total = list.getTotal();
            return Math.sqrt(total);
        }
        else{
            return 0;
        }
    }
    @Override
    public boolean isOrthogonal(NumberList list){
        if(isEmpty() == false && size() == list.size()){
            double scalarProduct = scalarProduct(list);
            return scalarProduct == 0;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean isParallel(NumberList list){
        if(isEmpty() == false && size() == list.size()){
            if(list instanceof NumericList listt){
                listt.divideScalars(this);
                double firstNumber = objectToDouble(listt.getFirstObject());
                Node tempNode = listt.first.getRightNode();
                while(tempNode != null) {
                    if(firstNumber != objectToDouble(tempNode.getData())){
                        return false;
                    }
                    tempNode.getRightNode();
                }
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean isSorted(){
        if(isEmpty() == false){
            Node tempNode = first.getRightNode();
            while(tempNode != null) {
                double number1 = objectToDouble(tempNode.getLeftNode().getData());
                double number2 = objectToDouble(tempNode.getData());
                if(number2 < number1){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}