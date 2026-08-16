package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public class BTreeNode{
    Array keys;
    Array nodes;
    int size;
    public BTreeNode(int size){
        this.size = size;
        keys = new Array(size);
        nodes = new Array(size+1);
    }
    public boolean isFilled(){
        return keys.isFilled();
    }
    public boolean isEmpty(){
        return keys.isEmpty();
    }
    public void add(Object newData, Comparator comparator){
        if(isEmpty()){
            keys.add(newData);
            nodes.add(new BTreeNode(size));
        }
        else if(isFilled()){

        }
        else{
            for (int i = 0; i < keys.size(); i++) {
                Object current = keys.get(i);
                int comparison = comparator.compare(current, newData);
                if(comparison < 0){
                    
                }
            }
        }
    }
}