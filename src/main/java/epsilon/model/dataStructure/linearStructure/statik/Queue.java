package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.DataBatch;

public class Queue extends Stack{
    public Queue(int capacity){
        super(capacity);
    }
    @Override
    public Object remove() {
        if(isEmpty() == false){
            Object firstObject = data[0];
            for (int i = 0; i < upperIndex; i++) {
                data[i] = data[i+1];
            }
            upperIndex -= 1;
            return firstObject;
        }
        else{
            return null;
        }
    }
    @Override
    public void print() {
        for (int i = 0; i <= upperIndex; i++) {
            System.out.println(data[i]);
        }
    }
    @Override
    public Object getTop() {
        return data[0];
    }
    public Object getLastObject(){
        return data[upperIndex];
    }
    @Override
    public Array toArray(){
        Array array = super.toArray();
        array.reverse();
        return array;
    }
    @Override
    public DataBatch copy(){
        Queue copy = new Queue(capacity);
        for (int i = upperIndex; i >= 0; i--) {
            copy.add(data[i]);
        }
        return copy;
    }
}