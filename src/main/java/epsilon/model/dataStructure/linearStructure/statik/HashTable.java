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
        int[] indexes = hashFunction.hash(newData);
        boolean added = false;
        for (int i = 0; i < indexes.length; i++) {
            int index = fixIndex(indexes[i]);
            if(data[index] == null){
                data[index] = newData;
                added = true;
            }
        }
        return added;
    }
    public LinkedList addList(DataList datalist){
        LinkedList booleans = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            booleans.add(add(nodeObject));
            return true;
        });
        return booleans;
    }
    public LinkedList push(Object newData){
        int[] indexes = hashFunction.hash(newData);
        LinkedList pushedObjects= new LinkedList();
        for (int i = 0; i < indexes.length; i++) {
            int index = fixIndex(indexes[i]);
            Object prevData = data[index];
            if(prevData != null){
                pushedObjects.add(prevData);
            }
            data[index] = newData;
        }
        return pushedObjects;
    }
    public LinkedList pushList(DataList datalist){
        LinkedList removedObjects = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            Object shovedObject = push(nodeObject);
            if(shovedObject != null){
                removedObjects.add(shovedObject);
            }
            return true;
        });
        return removedObjects;
    }
    public LinkedList shove(Object newData){
        int[] indexes = hashFunction.hash(newData);
        LinkedList shovedObjects = new LinkedList();
        for (int i = 0; i < indexes.length; i++) {
            int index = fixIndex(indexes[i]);
            Object prevData = data[index];
            if(prevData != null){
                int comparison = compareHashes(newData, prevData);
                if(comparison < 0){
                    shovedObjects.add(newData);
                }
                else{
                    data[index] = newData;
                    shovedObjects.add(prevData);
                }
            }
            else{
                data[index] = newData;
            }
        }
        return shovedObjects;
    }
    public LinkedList shoveList(DataList datalist){
        LinkedList removedObjects = new LinkedList();
        datalist.iterateList((Object nodeObject) -> {
            Object shovedObject = shove(nodeObject);
            if(shovedObject != null){
                removedObjects.add(shovedObject);
            }
            return true;
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
    public LinkedList find(Object obj){
        int[] indexes = hashFunction.hash(obj);
        LinkedList foundIndexes = new LinkedList();
        for (int i = 0; i < indexes.length; i++) {
            int index = fixIndex(indexes[i]);
            if(data[index] != null){
                foundIndexes.add(index);
            }
        }
        return foundIndexes;
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
    public LinkedList delete(Object obj){
        int[] indexes = hashFunction.hash(obj);
        LinkedList deletedIndexes = new LinkedList();
        for (int i = 0; i < indexes.length; i++) {
            int index = fixIndex(indexes[i]);
            if(data[index] != null){
                data[index] = null;
                deletedIndexes.add(index);
            }
        }
        return deletedIndexes;
    }
    public void clear(){
        clear(data.length);
    }
    public void clear(int newSize){
        data = new Object[newSize];
    }
    public int[] hashObject(Object obj){
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
            return true;
        });
    }
    public void resize(int newSize, boolean reorganize){
        if(reorganize == true){
            resize(newSize);
        }
        else{
            Object[] newData = new Object[newSize];
            for (int i = 0; i < data.length && i < newSize; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
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
            return true;
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
        int[] indexes1 = hashFunction.hash(obj1);
        int[] indexes2 = hashFunction.hash(obj2);
        if(indexes1.length > indexes2.length){
            return -1;
        }
        else if (indexes1.length < indexes2.length) {
            return 1;
        }
        else{
            for (int i = 0; i < indexes1.length; i++) {
                int hash1 = indexes1[i];
                int hash2 = indexes2[i];
                if(hash1 > hash2){
                    return -1;
                }
                else if(hash1 < hash2){
                    return 1;
                }
            }
        }
        return 0;
    }
    protected int fixIndex(int index){
        return Math.abs(index)%data.length;
    }
    protected boolean validIndex(int index){
        return index >= 0 && index < data.length;
    }
}