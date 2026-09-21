package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.HashFunction;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;

public class ChainHashTable extends HashTable{

    public ChainHashTable(HashFunction hashFunction, int size) {
        super(hashFunction, size);
        data = new LinkedList[size];
    }
    @Override
    public boolean add(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        if(data[index] == null){
            data[index] = new LinkedList();
        }
        ((LinkedList)data[index]).add(newData);
        return true;
    }
    @Override
    public Object push(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object pushedObject = null;
        if(data[index] != null){
            data[index] = new LinkedList();
        }
        else{
            pushedObject = ((LinkedList)data[index]).removeFirst();
        }
        ((LinkedList)data[index]).add(newData);
        return pushedObject;
    }
    @Override
    public Object shove(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object shovedObject = null;
        if(data[index] != null){
            LinkedList currentList = (LinkedList)data[index];
            currentList.initializeIterator();
            while(((LinkedList)data[index]).validIterator()){
                Object currentObject = currentList.getIterator();
                int comparison = compareHashes(newData, currentObject);
                if(comparison > 0){
                    currentList.modifyIterator(newData);
                    shovedObject = currentObject;
                    break;
                }
                currentList.moveIteratorToRight();
            }
            data[index] = currentList;
        }
        else{
            data[index] = new LinkedList();
            ((LinkedList)data[index]).add(newData);
        }
        return shovedObject;
    }
    public LinkedList getList(int key){
        if(validIndex(key) == true){
            return (LinkedList)data[key];
        }
        else{
            return null;
        }
    }
    @Override
    public Object get(int key){
        if(validIndex(key) == true){
            return ((LinkedList)data[key]).getFirstObject();
        }
        else{
            return null;
        }
    }
    public LinkedList removeAll(int key){
        if(validIndex(key) == true && data[key] != null){
           LinkedList list = (LinkedList)data[key];
           data[key] = null;
           return list;
        }
        else{
            return null;
        }
    }
    @Override
    public Object remove(int key){
        if(validIndex(key) == true && data[key] != null){
            return ((LinkedList)data[key]).remove();
        }
        else{
            return null;
        }
    }
    public int deleteAll(Object obj){
        int index = fixIndex(hashFunction.hash(obj));
        if(data[index] != null){
            data[index] = null;
            return index;
        }
        else{
            return -1;
        }
    }
    @Override
    public int delete(Object obj){
        int index = fixIndex(hashFunction.hash(obj));
        if(data[index] != null){
            ((LinkedList)data[index]).remove();
            return index;
        }
        else{
            return -1;
        }
    }
    @Override
    public void clear(int newSize){
        data = new LinkedList[newSize];
    }
    @Override
    public void resize(int newSize){
        LinkedList storedData = new LinkedList();
        for (Object data1 : data) {
            if (data1 != null) {
                LinkedList currentList = (LinkedList)data1;
                currentList.iterateList((Object nodeObject) -> {
                    storedData.add(nodeObject);
                    return true;
                });
            }
        }
        data = new LinkedList[newSize];
        storedData.iterateList((Object nodeObject) -> {
            add(nodeObject);
            return true;
        });
    }
}