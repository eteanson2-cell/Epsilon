package epsilon.model.dataStructure.linearStructure.dynamic;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.auxiliar.Node;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.Stack;
import static epsilon.utils.FunctionUtils.isInRange;

public class LinkedList implements DataList{
    protected Node first;
    protected Node last;
    protected Node iterator;
    public LinkedList(){
        first = null;
        last = null;
        iterator = null;
    }
    @Override
    public boolean isEmpty(){
        return first == null;
    }
    @Override
    public int size(){
        return getQuantity();
    }
    public int getQuantity(){
        int numObjects = 0;
        Node tempNode = first;
        while(tempNode != null){
            numObjects++;
            tempNode = tempNode.getRightNode();
        }
        return numObjects;
    }
    @Override
    public void clear(){
        first = null;
        last = null;
        iterator = null;
    }
    private boolean addFirstObject(Object object){
        first = new Node(object);
        last = first;
        return true;
    }
    public boolean addAtStart(Object object){
        if(object != null){
            if(isEmpty() == false){
                first.setLeft(object);
                first = first.getLeftNode();
                return true;
            }
            else{
                return addFirstObject(object);
            } 
        }
        else{
            return false;
        }
    }
    @Override
    public boolean add(Object object){
        if(object != null){
            if(isEmpty() == false){
                last.setRight(object);
                last = last.getRightNode();
                return true;
            }
            else{
                return addFirstObject(object);
            } 
        }
        else{
            return false;
        }
    }
    protected Node getNode(Object object){
        return getNode(object, new BaseObjectComparator());
    }
    protected LinkedList getNodes(Object object, Comparator comparator){
        LinkedList nodes = new LinkedList();
        if(object != null){
            Node tempNode = first;
            while(tempNode != null){
                if(comparator.compare(tempNode.getData(), object) == 0){
                    nodes.add(tempNode);
                }
                tempNode = tempNode.getRightNode();
            }
        }
        return nodes;
    }
    protected Node getNode(Object object, Comparator comparator){
        Node tempNode = first;
        if(object != null){
            while(tempNode != null){
                if(comparator.compare(tempNode.getData(), object) == 0){
                    return tempNode;
                }
                tempNode = tempNode.getRightNode();
            }
            return null;
        }
        else{
            return null;
        }
    }
    protected Node getNodePos(int index){
        if(isEmpty() == false && isInRange(0,getQuantity()-1,index)){
            int counter = 0;
            Node tempNode = first;
            while(counter < index){
                tempNode = tempNode.getRightNode();
                counter += 1;
            }
            return tempNode;
        }
        else{
            return null;
        }
    }
    @Override
    public Object find(Object object){
        Node nodeTemp = getNode(object);
        if(nodeTemp != null){
            return nodeTemp.getData();
        }
        else{
            return null;
        }
    }
    @Override
    public Object find(Object object, Comparator comparator) {
        Node nodeTemp = getNode(object, comparator);
        if(nodeTemp != null){
            return nodeTemp.getData();
        }
        else{
            return null;
        }
    }
    public int findPosition(Object object){
        return findPosition(object, new BaseObjectComparator());
    }
    public int findPosition(Object object, Comparator comparator){
        Node tempNode = first;
        int position = -1;
        while(tempNode != null){
            position++;
            if(comparator.compare(tempNode.getData(), object) == 0){
                return position;
            }
            tempNode = tempNode.getRightNode();
        }
        return -1;
    }
    public Object getFirstObject(){
        if(isEmpty() == false){
            return first.getData();
        }
        else{
            return null;
        }
    }
    public Object getLastObject(){
        if(isEmpty() == false){
            return last.getData();
        }
        else{
            return null;
        }
    }
    public Object get(int index){
        Node tempNode = getNodePos(index);
        if(tempNode != null){
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public boolean modify(Object object, int index){
        Node tempNode = getNodePos(index);
        if(tempNode != null){
            tempNode.setData(object);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean insert(Object object, int index){
        Node tempNode = getNodePos(index);
        if(index == 0){
            return addAtStart(object);
        }
        else if(index == getQuantity()){
            return add(object);
        }
        else if(tempNode != null){
            Node prevNode = tempNode.getLeftNode();
            prevNode.setRight(object);
            prevNode.getRightNode().setRightNode(tempNode);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public Object remove(){
        if(isEmpty() == false){
            Object removedObject = last.getData();
            last = last.getLeftNode();
            if(last != null){
                last.cleanRight();
            }
            else{
                first = null;
                iterator = null;
            }
            return removedObject;
        }
        else{
            return null;
        }
    }
    public Object removeFirst(){
        if(isEmpty() == false){
            Object removedObject = first.getData();
            first = first.getRightNode();
            if(first != null){
                first.cleanLeft();
            }
            return removedObject;
        }
        else{
            return null;
        }
    }
    @Override
    public Object remove(Object object){
        return remove(object, new BaseObjectComparator());
    }
    @Override
    public Object remove(Object object, Comparator comparator){
        Node tempNode = getNode(object, comparator);
        return removeNode(tempNode);
    }
    public LinkedList removeAll(Object object, Comparator comparator){
        LinkedList objects = new LinkedList();
        LinkedList nodes = getNodes(object, comparator);
        nodes.initializeIterator();
        while(nodes.validIterator() == true){
            Node tempNode = (Node)nodes.getIterator();
            objects.add(removeNode(tempNode));
            nodes.moveIteratorToRight();
        }
        return objects;
    }
    public Object remove(int index){
        Node tempNode = getNodePos(index);
        return removeNode(tempNode);
    }
    protected Object removeNode(Node tempNode){
        if(tempNode == last){
            return remove();
        }
        else if(tempNode == first){
            return removeFirst();
        }
        else if(tempNode != null){
            Object removedObject = tempNode.getData();
            tempNode.getLeftNode().setRightNode(tempNode.getRightNode());
            return removedObject;
        }
        else{
            return null;
        }
    }
    @SuppressWarnings("unused")
    private void recalibrateLastNode(){
        Node tempNode = first;
        while(tempNode.getRightNode() != null){
            tempNode = tempNode.getRightNode();
        }
        last = tempNode;
    }
    @Override
    public void reverse(){
        Stack stack = new Stack(getQuantity());
        while(isEmpty() == false){
            stack.add(removeFirst());
        }
        while(stack.isEmpty() == false){
            add(stack.remove());
        }
    }
    public Array toArray(){
        Array arrayList = new Array(getQuantity());
        Node tempNode = first;
        while (tempNode != null) { 
            arrayList.add(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
        return arrayList;
    }
    @Override
    public void iterateList(Iterator iterator){
        Node temp = first;
        while(temp != null){
            iterator.iterate(temp.getData());
            temp = temp.getRightNode();
        }
    }
    @Override
    public void print(){
        Node tempNode = first;
        while (tempNode != null) { 
            System.out.println(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
    }
    @Override
    public void reversePrint(){
        Node tempNode = last;
        while (tempNode != null) { 
            System.out.println(tempNode.getData());
            tempNode = tempNode.getLeftNode();
        }
    }
    @Override
    public boolean addList(DataList dataList){
        dataList.iterateList(this::add);
        return true;
    }
    @Override
    public int count(Object object){
        return count(object, new BaseObjectComparator());
    }
    @Override
    public int count(Object object, Comparator comparator){
        int counter = 0;
        Node tempNode = first;
        while(tempNode != null){
            if(comparator.compare(tempNode.getData(), object) == 0){
                counter++;
            }
            tempNode = tempNode.getRightNode();
        }
        return counter;
    }
    @Override
    public boolean equals(DataList dataList){
        return equals(dataList, (Object obj1, Object obj2) -> {
            if(obj1.equals(obj2)){
                return 0;
            }
            else{
                return -1;
            }
        });
    }
    public boolean equals(DataList dataList, Comparator comparator){
        if(dataList instanceof LinkedList list && list.size() == size()){
            initializeIterator();
            dataList.initializeIterator();
            while (validIterator() == true) { 
                Object obj1 = getIterator();
                Object obj2 = list.getIterator();
                if(comparator.compare(obj1, obj2) != 0){
                    return false;
                }
                moveIteratorToRight();
                list.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public DataList copy(){
        LinkedList copy = new LinkedList();
        Node tempNode = first;
        while(tempNode != null){
            copy.add(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
        return copy;
    }
    @Override
    public boolean replace(DataList dataList){
        clear();
        dataList.iterateList(this::add);
        return true;
    }
    @Override
    public void initializeIterator(){
        iterator = first;
    }
    @Override
    public void moveIteratorToRight(){
        if(validIterator()){
            iterator = iterator.getRightNode();
        }
    }
    @Override
    public void moveIteratorToLeft(){
        if(validIterator()){
            iterator = iterator.getLeftNode();
        }
    }
    @Override
    public boolean validIterator(){
        return iterator != null;
    }
    @Override
    public Object getIterator(){
        if(validIterator()){
            return iterator.getData();
        }
        else{
            return false;
        }
    }
    @Override
    public boolean modifyIterator(Object object){
        if(validIterator()){
            return iterator.setData(object);
        }
        else{
            return false;
        }
    }
    public Object removeIterator(){
        if(validIterator()){
            return removeNode(iterator);
        }
        else{
            return null;
        }
    }
    @Override
    public String toString(){
        if(isEmpty()){
            return "[]";
        }
        else{
            Node tempNode = first;
            String arrayString = "[" + tempNode.getData();
            tempNode = tempNode.getRightNode();
            while (tempNode != null) {
                arrayString += "," + tempNode.getData();
                tempNode = tempNode.getRightNode();
            }
            arrayString += "]";
            return arrayString;
        }
    }
}