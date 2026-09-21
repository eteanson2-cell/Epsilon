package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.HashFunction;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.nonLinearStructure.TreeMap;

public class HashMap extends HashTable{
    protected LinkedList[] lists;
    public HashMap(HashFunction hashFunction, int size) {
        super(hashFunction, size);
        lists = new LinkedList[size];
    }
    public boolean addKey(Object key){
        return add(key);
    }
    @Override
    public boolean add(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        if(data[index] == null){
            data[index] = newData;
            lists[index] = new LinkedList();
            return true;
        }
        else{
            return false;
        }
    }
    public Object pushKey(Object key){
        return push(key);
    }
    @Override
    public Object push(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object prevData = data[index];
        data[index] = newData;
        if(lists[index] == null){
            lists[index] = new LinkedList();
        }
        return prevData;
    }
    public Object shoveKey(Object key){
        return shove(key);
    }
    @Override
    public Object shove(Object newData){
        int index = fixIndex(hashFunction.hash(newData));
        Object prevData = data[index];
        if(prevData != null){
            return super.shove(newData);
        }
        else{
            data[index] = newData;
            lists[index] = new LinkedList();
            return null;
        }
    }
    public boolean addObject(Object newObject, Object key){
        int index = fixIndex(hashFunction.hash(key));
        if(data[index] != null){
            lists[index].add(newObject);
            return true;
        }
        else{
            return false;
        }
    }
    public Object removeObject(Object key){
        int index = fixIndex(hashFunction.hash(key));
        if(data[index] != null){
           return lists[index].remove();
        }
        else{
            return null;
        }
    }
    public boolean replaceList(LinkedList newList, Object key){
        int index = fixIndex(hashFunction.hash(key));
        if(data[index] != null){
            lists[index] = newList;
           return true;
        }
        else{
            return false;
        }
    }
    public boolean clearList(Object key){
        int index = fixIndex(hashFunction.hash(key));
        if(data[index] != null){
            lists[index].clear();
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public Object get(int key){
        if(validIndex(key) == true){
            return data[key];
        }
        else{
            return null;
        }
    }
    public LinkedList getList(int key){
        if(validIndex(key) == true){
            return lists[key];
        }
        else{
            return null;
        }
    }
    public Object removeKey(int key){
        return remove(key);
    }
    @Override
    public Object remove(int key){
        if(validIndex(key) == true){
            Object keyData = data[key];
            data[key] = null;
            lists[key] = null;
            return keyData;
        }
        else{
            return null;
        }
    }
    public int deleteKey(Object obj){
        return delete(obj);
    }
    @Override
    public int delete(Object obj){
        int index = fixIndex(hashFunction.hash(obj));
        if(data[index] != null){
            data[index] = null;
            lists[index] = null;
            return index;
        }
        else{
            return -1;
        }
    }
    @Override
    public void clear(int newSize){
        data = new Object[newSize];
        lists = new LinkedList[newSize];
    }
    @Override
    public void resize(int newSize){
        TreeMap objects = new TreeMap((Object obj1, Object obj2) -> {
            return compareHashes(obj1, obj2);
        });
        for (int i = 0; i < data.length; i++) {
            Object currentObject = data[i];
            if(currentObject != null){
                objects.addKey(currentObject);
                objects.replaceData(lists[i], currentObject);
            }
        }
        data = new Object[newSize];
        lists = new LinkedList[newSize];
        objects.iteration((Object nodeObject) -> {
            Array mapNode = (Array)nodeObject;
            Object key = mapNode.get(0);
            LinkedList list = (LinkedList)mapNode.get(1);
            int index = fixIndex(hashFunction.hash(key));
            if(data[index] != null){
                data[index] = key;
                lists[index] = list;
            }
            return true;
        });
    }
    @Override
    public void print(){
        for (int i = 0; i < data.length; i++) {
            Object currentObject = data[i];
            if(currentObject != null){
                System.out.println("Index = " + i + " Key = " + currentObject + " Objects = " + lists[i]);
            }
        }
    }
}