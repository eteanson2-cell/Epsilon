package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.interfaces.HashFunction;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;

public class HashTable{
    protected HashFunction hashFunction;
    protected Object[] data;
    public HashTable(HashFunction hashFunction, int size){
        data = new Object[size];
        this.hashFunction = hashFunction;
    }
    public HashFunction getHashFunction(){
        return hashFunction;
    }
    public void setHashFunction(HashFunction hashFunction){
        this.hashFunction = hashFunction;
        reorganize();
    }
    public boolean add(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        if(data[index] == null){
            data[index] = newData;
            return true;
        }
        else{
            return false;
        }
    }
    public LinkedList addList(DataList datalist){
        LinkedList booleans = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            booleans.add(add(nodeObject));
        });
        return booleans;
    }
    public Object push(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object prevData = data[index];
        data[index] = newData;
        return prevData;
    }
    public LinkedList pushList(DataList datalist){
        LinkedList removedObjects = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            Object shovedObject = push(nodeObject);
            if(shovedObject != null){
                removedObjects.add(shovedObject);
            }
        });
        return removedObjects;
    }
    public Object shove(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object prevData = data[index];
        if(prevData != null){
            int comparison = compareHashes(newData, prevData);
            if(comparison < 0){
                return newData;
            }
            else{
                data[index] = newData;
                return prevData;
            }
        }
        else{
            data[index] = newData;
            return null;
        }
    }
    public LinkedList shoveList(DataList datalist){
        LinkedList removedObjects = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            Object shovedObject = shove(nodeObject);
            if(shovedObject != null){
                removedObjects.add(shovedObject);
            }
        });
        return removedObjects;
    }
    public Boolean isOcuppied(int index){
        if(validIndex(index) == true) {
            return data[index] != null;
        }
        else {
            return null;
        }
    }
    public Object get(int key){
        if(validIndex(key) == true){
            return data[key];
        }
        else{
            return null;
        }
    }
    public int find(Object obj){
        int index = fixIndex(hashFunction.hash(obj));
        if(data[index] != null){
            return index;
        }
        else{
            return -1;
        }
    }
    public Object remove(int key){
        if(validIndex(key) == true){
            Object keyData = data[key];
            data[key] = null;
            return keyData;
        }
        else{
            return null;
        }
    }
    public int delete(Object obj){
        int index = fixIndex(hashFunction.hash(obj));
        if(data[index] != null){
            data[index] = null;
            return index;
        }
        else{
            return -1;
        }
    }
    public void clear(){
        clear(data.length);
    }
    public void clear(int newSize){
        data = new Object[newSize];
    }
    public int hashObject(Object obj){
        return hashFunction.hash(obj);
    }
    public void resize(int newSize){
        LinkedList storedData = new LinkedList();
        for (Object data1 : data) {
            if (data1 != null) {
                Object currentData = data1;
                storedData.add(currentData);
            }
        }
        data = new Object[newSize];
        storedData.iterateList((Object nodeObject) -> {
            add(nodeObject);
        });
    }
    public void reorganize(){
        LinkedList storedData = new LinkedList();
        for (int i = 0; i < data.length; i++) {
            if(data[i] != null){
                Object currentData = data[i];
                data[i] = null;
                storedData.add(currentData);
            }
        }
        storedData.iterateList((Object nodeObject) -> {
            add(nodeObject);
        });
    }
    public void print(){
        for (int i = 0; i < data.length; i++) {
            Object currentObject = data[i];
            if(currentObject != null){
                System.out.println("Key = " + i + " Object = " + currentObject);
            }
        }
    }
    protected int compareHashes(Object obj1, Object obj2){
        int hash1 = hashFunction.hash(obj1);
        int hash2 = hashFunction.hash(obj2);
        if(hash1 > hash2){
            return -1;
        }
        else if(hash1 < hash2){
            return 1;
        }
        else{
            return 0;
        }
    }
    protected int fixIndex(int index){
        return Math.abs(index)%data.length;
    }
    protected boolean validIndex(int index){
        return index >= 0 && index < data.length;
    }
}