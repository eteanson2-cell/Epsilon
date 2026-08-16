package epsilon.model.dataStructure.linearStructure.dynamic;

import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public class DynamicStack implements DataBatch{
    protected LinkedList data;
    public DynamicStack(){
        data = new LinkedList();
    }
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean isFilled() {
        return false;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean add(Object object) {
        return data.add(object);
    }

    @Override
    public Object remove() {
        if(isEmpty() == false){
            return data.remove();
        }
        else{
            return null;
        }
    }

    @Override
    public void print() {
        data.print();
    }

    @Override
    public Object getTop() {
        return data.getLastObject();
    }
    @Override
    public boolean hasObject(Object object){
        if(isEmpty() == false){
            data.initializeIterator();
            while(data.validIterator() == true){
                if(object.toString().equalsIgnoreCase(data.getIterator().toString()) == true){
                    return true;
                }
                data.moveIteratorToRight();
            }
            return false;
        }
        else{
            return false;
        }
    }
    public int size(){
        return data.size();
    }
    @Override
    public boolean addList(DataList list){
        if(list instanceof Array array){
            for (int i = 0; i < array.size(); i++) {
                add(array.get(i));
            }
            return true;
        }
        else if(list instanceof LinkedList linkedList){
            linkedList.initializeIterator();
            while (linkedList.validIterator()) { 
                add(linkedList.getIterator());
                linkedList.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addBatch(DataBatch batch){
        while(batch.isEmpty() == false){
            add(batch.remove());
        }
        return true;
    }
    public Array toArray(){
        Array array = new Array(size());
        data.initializeIterator();
        while (data.validIterator()) { 
            array.add(data.getIterator());
            data.moveIteratorToRight();
        }
        return array;
    }
    @Override
    public DataBatch copy(){
        DynamicStack copy = new DynamicStack();
        data.initializeIterator();
        while (data.validIterator()) { 
            copy.add(data.getIterator());
            data.moveIteratorToRight();
        }
        return copy;
    }
}