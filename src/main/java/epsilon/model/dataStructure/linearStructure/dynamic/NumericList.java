package epsilon.model.dataStructure.linearStructure.dynamic;

import epsilon.model.dataStructure.auxiliar.Node;
import epsilon.model.dataStructure.interfaces.DataList;
import static epsilon.utils.FunctionUtils.isNumeric;
import static epsilon.utils.FunctionUtils.isNumericList;

public class NumericList extends LinkedList{
    @Override
    public boolean addAtStart(Object object){
        if(isNumeric(object)){
            return super.addAtStart(object);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean add(Object object){
        if(isNumeric(object)){
            return super.add(object);
        }
        else{
            return false;
        }
    }
    @Override
    protected Node getNode(Object object){
        if(isNumeric(object)){
            return super.getNode(object);
        }
        else{
            return null;
        }
    }
    @Override
    public Object find(Object object){
        if(isNumeric(object)){
            return super.find(object);
        }
        else{
            return null;
        }
    }
    @Override
    public int findPosition(Object object){
        if(isNumeric(object)){
            return super.findPosition(object);
        }
        else{
            return -1;
        }
    }
    @Override
    public boolean modify(Object object, int index){
        if(isNumeric(object)){
            return super.modify(object,index);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insert(Object object, int index){
        if(isNumeric(object)){
            return super.insert(object,index);
        }
        else{
            return false;
        }
    }
    @Override
    public Object remove(Object object){
        if(isNumeric(object)){
            return super.remove(object);
        }
        else{
            return null;
        }
    }
    @Override
    public boolean addList(DataList dataList){
        if(isNumericList(dataList)){
            return super.addList(dataList);
        }
        else{
            return false;
        }
    }
    @Override
    public int count(Object object){
        if(isNumeric(object)){
            return super.count(object);
        }
        else{
            return 0;
        }
    }
    @Override
    public boolean equals(DataList dataList){
        if(isNumericList(dataList)){
            return super.equals(dataList);
        }
        else{
            return false;
        }
    }
    @Override
    public DataList copy(){
        NumericList copy = new NumericList();
        Node tempNode = first;
        while(tempNode != null){
            copy.add(tempNode.getData());
            tempNode = tempNode.getRightNode();
        }
        return copy;
    }
    @Override
    public boolean replace(DataList dataList){
        if(isNumericList(dataList)){
            return super.replace(dataList);
        }
        else{
            return false;
        }
    }
}