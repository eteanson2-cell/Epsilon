package epsilon.model.dataStructure.linearStructure.dynamic;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public class DynamicQueue implements DataBatch{
    protected LinkedList data;
    public DynamicQueue(){
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
            return data.removeFirst();
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
        if(isEmpty() == false){
            return data.getFirstObject();
        }
        else{
            return null;
        }
    }
    @Override
    public boolean hasObject(Object object) {
        return hasObject(object, new BaseObjectComparator());
    }
    @Override
    public boolean hasObject(Object object, Comparator comparator){
        if(isEmpty() == false){
            data.initializeIterator();
            while(data.validIterator() == true){
                if(comparator.compare(object,data.getIterator()) == 0){
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
        Array array = new Array(size());
        data.iterateList(array::add);
        return array;
    }
    @Override
    public DataBatch copy(){
        DynamicQueue copy = new DynamicQueue();
        data.iterateList(copy::add);
        return copy;
    }

    
}