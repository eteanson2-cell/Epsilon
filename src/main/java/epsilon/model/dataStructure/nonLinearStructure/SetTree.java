package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.SetNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;

public class SetTree extends AbstractBinaryTree<SetNode> {
    public SetTree(Comparator comparator){
        super(comparator);
    }
    @Override
    public boolean add(Object newData){
        if(isEmpty() == false){
            return root.add(newData, comparator);
        }
        else{
            root = new SetNode(newData);
            return true;
        }
    }
    @Override
    public boolean hasObject(Object data){
        if(isEmpty() == false){
            return root.hasObject(data, comparator);
        }
        else{
            return false;
        }
    }
    @Override
    protected DynamicStack findNode(Object key, Comparator comparator){
        DynamicStack nodes = new DynamicStack();
        SetNode tempNode = root;
        while(tempNode != null){
            nodes.add(tempNode);
            int comparedValue = comparator.compare(tempNode.getData(), key);
            if(comparedValue > 0){
                tempNode = (SetNode)tempNode.getRightBranch();
            }
            else if (comparedValue < 0) {
                tempNode = (SetNode)tempNode.getLeftBranch();
            }
            else{
                return nodes;
            }
        }
        return null;
    }
    public void removeAll(Object data, Comparator comparator){
        DynamicStack nodes = findNode(data, comparator);
        while(nodes != null && nodes.isEmpty() == false){
            SetNode tempNode = (SetNode)nodes.remove();
            remove(tempNode.getData());
            nodes = findNode(data, comparator);
        }

    }
    @Override
    public Object remove(Object object){
        DynamicStack nodes = findNode(object);
        if(nodes != null){
            SetNode tempNode = (SetNode)nodes.remove();
            Object removedObject = tempNode.getData();
            if(tempNode == root && tempNode.hasNextBranch() == false){
                root = null;
            }
            else{
                tempNode.remove();
                if(tempNode.getData() == null){
                    SetNode prevNode = (SetNode)nodes.remove();
                    if(tempNode == prevNode.getLeftBranch()){
                        prevNode.clearLeftBranch();
                    }
                    else if(tempNode == prevNode.getRightBranch()){
                        prevNode.clearRightBranch();
                    }
                    prevNode.balanceNode();
                }    
            }
            while (nodes.isEmpty() == false) { 
                tempNode = (SetNode)nodes.remove();
                tempNode.balanceNode();
            }
            return removedObject;
        }
        else{
            return null;
        }
    }
}