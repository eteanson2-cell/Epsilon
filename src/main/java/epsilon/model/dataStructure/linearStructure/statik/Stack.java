package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import static epsilon.utils.FunctionUtils.getMin;

public class Stack implements DataBatch{
    protected Object[] data;
    protected int upperIndex;
    protected int capacity;
    public Stack(int capacity){
        this.capacity = capacity;
        data = new Object[capacity];
        upperIndex = -1;
    }
    public int getCapacity(){
        return capacity;
    }
    public int size(){
        return upperIndex+1;
    }
    @Override
    public boolean isEmpty() {
        return (upperIndex == -1);
    }
    @Override
    public boolean isFilled() {
        return (upperIndex == capacity-1);
    }
    @Override
    public void clear(){
        upperIndex = -1;
    }
    @Override
    public boolean add(Object object) {
        if(isFilled() == false){
            upperIndex += 1;
            data[upperIndex] = object;
            return true;
        }
        else{
            return false;
        }
    }
    public void forceAdd(Object object){
        if(isFilled() == true){
            remove();
        }
        add(object);
    }
    @Override
    public Object remove() {
        if(isEmpty() == false){
            Object lastObject = data[upperIndex];
            upperIndex--;
            return lastObject;
        }
        else{
            return null;
        }
    }
    @Override
    public void print() {
        for (int i = upperIndex; i >= 0; i--) {
            System.out.println(data[i]);
        }
    }
    @Override
    public Object getTop() {
        if(isEmpty() == false){
            return data[upperIndex];
        }
        else{
            return null;
        }
    }
    @Override
    public boolean hasObject(Object object){
        return hasObject(object, new BaseObjectComparator());
    }
    @Override
    public boolean hasObject(Object object, Comparator comparator) {
        if(isEmpty() == false){
            for (int index = 0; index < size(); index++) {
                if(comparator.compare(object, data[index]) == 0){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public void resize(int newCapacity){
        Object[] newData = new Object[newCapacity];
        for (int index = 0; index < getMin(data.length, newCapacity); index++) {
            newData[index] = data[index];
        }
        capacity = newCapacity;
        data = newData;
        if(upperIndex >= newCapacity){
            upperIndex = newCapacity-1;
        }
    }
    @Override
    public boolean addList(DataList list){
        list.iterateList(this::add);
        return true;
    }
    public boolean addBatch(DataBatch batch){
        while(batch.isEmpty() == false){
            add(batch.remove());
        }
        return true;
    }
    public Array toArray(){
        Array array = new Array(capacity);
        for (int i = upperIndex; i >= 0; i--) {
            array.add(data[i]);
        }
        return array;
    }
    @Override
    public DataBatch copy(){
        Stack copy = new Stack(capacity);
        for (int i = upperIndex; i >= 0; i--) {
            copy.add(data[i]);
        }
        return copy;
    }
}