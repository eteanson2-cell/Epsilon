package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import static epsilon.utils.FunctionUtils.getMin;
import static epsilon.utils.FunctionUtils.isInRange;

public class Array implements DataList{
    protected int upperIndex;
    protected int capacity;
    protected Object[] data;
    protected int iterator;
    public Array(int capacity){
        this.capacity = capacity;
        data = new Object[capacity];
        upperIndex = -1;
        iterator = -1;
    }
    @Override
    public boolean isEmpty(){
        return (upperIndex == -1);
    }
    public boolean isFilled(){
        return (upperIndex == capacity-1);
    }
    public int getCapacity(){
        return capacity;
    }
    public int getQuantity(){
        return upperIndex+1;
    }
    @Override
    public int size(){
        return getQuantity();
    }
    public Object get(int index){
        if(validIndex(index)){
            return data[index];
        }
        else{
            return null;
        }
    }
    @Override
    public Object find(Object object){
        return find(object, new BaseObjectComparator());
    }
    @Override
    public Object find(Object object, Comparator comparator) {
        for (int i = 0; i < getQuantity(); i++) {
            if(comparator.compare(object, data[i]) == 0){
                return (Integer)i;
            }
        }
        return -1;
    }
    @Override
    public boolean add(Object object){
        if(isFilled() == false){
            upperIndex++;
            data[upperIndex] = object;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean push(Object object){
        if(isFilled()){
            return true;
        }
        else{
            return add(object);
        }
    }
    public boolean modify(Object object, int index){
        if(validIndex(index)){
            data[index] = object;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean insert(Object object, int index){
        if( isFilled() == false && validIndex(index)){
            upperIndex++;
            for (int i = upperIndex; i > index; i--) {
                data[i] = data[i-1];
            }
            data[index] = object;
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public Object remove(){
        if(isEmpty() == false){
            Object lastObject = data[upperIndex];
            upperIndex--;
            return lastObject;
        }
        else{
            return null;
        }
    }
    public Object remove(int index){
        if(validIndex(index)){
            Object removedObject = data[index];
            for (int i = index; i < upperIndex; i++) {
                data[i] = data[i+1];
            }
            upperIndex--;
            return removedObject;
        }
        else{
            return null;
        }
    }
    @Override
    public Object remove(Object object){
        int objectIndex = (int)find(object);
        if(objectIndex >= 0){
            return remove(objectIndex);
        }
        else{
            return null;
        }
    }
    @Override
    public void clear(){
        upperIndex = -1;
    }
    @Override
    public void reverse(){
        Queue queue = new Queue(getQuantity());
        while (isEmpty() == false) { 
            queue.add(remove());
        }
        while(queue.isEmpty() == false){
            add(queue.remove());
        }
    }
    @Override
    public void print(){
        for (int i = 0; i < getQuantity(); i++) {
            System.out.println(data[i]);
        }
    }
    @Override
    public void reversePrint(){
        for (int i = size()-1; i >= 0; i--) {
            System.out.println(data[i]);
        }
    }
    @Override
    public int count(Object object){
        int counter = 0;
        for (int i = 0; i < size(); i++) {
            if(object.toString().equalsIgnoreCase(data[i].toString())){
                counter++;
            }
        }
        return counter;
    }
    public void fill(Object object){
        while (isFilled() == false) { 
            add(object);
        }
    }
    public void refill(Object object){
        for (int i = 0; i < size(); i++) {
            data[i] = object;
        }
    }
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public boolean resize(int size){
        if(size > 0){
            Object[] newData = new Object[size];
            for (int i = 0; i < getMin(size(),size); i++) {
                newData[i] = data[i];
            }
            data = newData;
            capacity = size;
            if(upperIndex >= size){
                upperIndex = size-1;
            }
            return true;
        }
        else{
            return false;
        }
        
    }
    public Array getSublist(int firstIndex, int lastIndex){
        if(firstIndex < lastIndex && validIndex(lastIndex) && validIndex(firstIndex)){
            Array sublist = new Array(lastIndex - firstIndex + 1);
            for (int index = firstIndex; index <= lastIndex; index++) {
                sublist.add(data[index]);
            }
            return sublist;
        }
        else{
            return null;
        }
    }
    public Array getSublist(NumericArray indexes){
        Array subarray = new Array(capacity);
        for (int i = 0; i < indexes.size(); i++) {
            Number indexNumber = (Number)indexes.get(i);
            int index = indexNumber.intValue();
            if(validIndex(index) == true){
                subarray.add(get(index));
            }
        }
        if(subarray.isEmpty() == false){
            return subarray;
        }
        else{
            return null;
        }
    }
    public LinkedList toList(){
        LinkedList arrayList = new LinkedList();
        for (int i = 0; i < size(); i++) {
            arrayList.add(get(i));
        }
        return arrayList;
    }
    public Set toSet(){
        Set arraySet = new Set(size());
        for (int i = 0; i < size(); i++) {
            arraySet.add(get(i));
        }
        return arraySet;
    }
    @Override
    public boolean addList(DataList dataList){
        if(dataList instanceof Array array){
            for (int i = 0; i < array.size() && isFilled() == false; i++) {
                add(array.get(i));
            }
            return true;
        }
        else if(dataList instanceof LinkedList list){
            list.initializeIterator();
            while (list.validIterator() && isFilled() == false) { 
                add(list.getIterator());
                list.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addBatch(DataBatch batch){
        if(isFilled() == true){
            while(batch.isEmpty() == false && isFilled() == false){
                add(batch.remove());
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean equals(DataList dataList){
        if(dataList instanceof Array array && array.size() == size()){
            for (int index = 0; index < array.size(); index++) {
                Object obj1 = get(index);
                Object obj2 = array.get(index);
                if(obj1.equals(obj2) == false){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public DataList copy(){
        Array copy = new Array(capacity);
        for (int i = 0; i < size(); i++) {
            copy.add(get(i));
        }
        return copy;
    }
    @Override
    public boolean replace(DataList dataList){
        if(dataList instanceof Array array){
            clear();
            for (int index = 0; index < array.size(); index++) {
                add(array.get(index));
            }
            return true;
        }
        else if (dataList instanceof LinkedList list) {
            clear();
            list.initializeIterator();
            while (list.validIterator()) { 
                add(list.getIterator());
                list.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void initializeIterator(){
        iterator = 0;
    }
    @Override
    public void moveIteratorToRight(){
        if(validIterator()){
            iterator++;
        }
    }
    @Override
    public void moveIteratorToLeft(){
        if(validIterator()){
            iterator--;
        }
    }
    @Override
    public boolean validIterator(){
        return validIndex(iterator);
    }
    @Override
    public Object getIterator(){
        if(validIterator()){
            return data[iterator];
        }
        else{
            return null;
        }
    }
    @Override
    public boolean modifyIterator(Object object){
        if(validIterator()){
            return modify(object, iterator);
        }
        else{
            return false;
        }
    }
    protected boolean validIndex(int index){
        return isEmpty() == false && isInRange(0,upperIndex,index);
    }
    @Override
    public String toString(){
        if(isEmpty()){
            return "[]";
        }
        else{
            String arrayString = "[" + data[0];
            for (int i = 1; i < getQuantity(); i++) {
                arrayString += "," + data[i];
            }
            arrayString += "]";
            return arrayString;
        }
    }
}