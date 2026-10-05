package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public class MapNode extends BinaryNode<MapNode>{
    protected Object key;
    public MapNode(Object key){
        super(new LinkedList());
        this.key = key;
    }
    public Object getKey(){
        return key;
    }
    @Override
    public Object getData(){
        return data;
    }
    public LinkedList getDatalist(){
        return (LinkedList)data;
    }
    @Override
    public void setData(Object data){
        if(data != null && data instanceof LinkedList){
            this.data = data;
        }
    }
    public void setDatalist(LinkedList data){
        this.data = data;
    }
    @Override
    public void setRightBranch(MapNode rightBranch){
        if(rightBranch != null){
            this.rightBranch = rightBranch;
        }
    }
    @Override
    public void setLeftBranch(MapNode leftBranch){
        if(leftBranch != null){
            this.leftBranch = leftBranch;
        }
    }
    @Override
    public void addData(Object newData, Comparator comparator) {
        addData(newData);
    }
    @Override
    public void addData(Object newData){
        ((LinkedList)data).add(newData);
    }
    public void clearData(){
        ((LinkedList)data).clear();
    }
    public Object removeData(){
        Object removedObject = ((LinkedList)data).remove();
        return removedObject;
    }
    public boolean addKey(Object newKey, Comparator comparator){
        int comparation = comparator.compare(key, newKey);
        boolean validKey = false;
        if(comparation > 0){
            if(rightBranch != null){
                validKey = rightBranch.addKey(newKey, comparator);
                balanceNode();
            }
            else{
                rightBranch = new MapNode(newKey);
                validKey = true;
            }
        }
        else if(comparation < 0){
            if(leftBranch != null){
                validKey = leftBranch.addKey(newKey, comparator);
                balanceNode();
            }
            else{
                leftBranch = new MapNode(newKey);
                validKey = true;
            }
        }
        return validKey;
    }
    @Override
    protected void LLRotation(){
        Object tempkey = leftBranch.getKey();
        super.LLRotation();
        key = tempkey;
    }
    @Override
    protected void RRRotation(){
        Object tempkey = rightBranch.getKey();
        super.RRRotation();
        key = tempkey;
    }
    @Override
    protected MapNode cloneNode(){
        MapNode copy = new MapNode(key);
        copy.rightBranch = rightBranch;
        copy.leftBranch = leftBranch;
        copy.data = ((LinkedList)data).copy();
        return copy;
    }
    @Override
    public Object remove(){
        Object removedKey = key;
        if(hasNextBranch() == true){
            if(rightBranch == null){
                key = leftBranch.getKey();
            }
            else if(leftBranch == null){
                key = rightBranch.getKey();
            }
            else{
                Array lastNodes = rightBranch.minValueNode(null);
                MapNode minNode = (MapNode)lastNodes.get(0);
                key = minNode.key;
            }
            super.remove();
        }
        else{
            key = null;
        }
        return removedKey;
    }
}