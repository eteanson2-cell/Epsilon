package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.SetNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.enums.TreeTraversal;

public class SetTree {
    SetNode root;
    SetNode iterator;
    Comparator comparator;
    public SetTree(Comparator comparator){
        root = null;
        this.comparator = comparator;
    }
    public boolean isEmpty(){
        return root == null;
    }
    public boolean add(Object newData){
        if(isEmpty() == false){
            return root.add(newData, comparator);
        }
        else{
            root = new SetNode(newData);
            return true;
        }
    }
    public boolean hasObject(Object data){
        return root.hasObject(data, comparator);
    }
    protected DynamicStack findNode(Object key){
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
    public void print(){
        print(TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void print(TreeTraversal treeTraversal){
        DataBatch nodes = selectBatch(treeTraversal);
        nodes.add(root);
        LinkedList tempNodes;
        do { 
            tempNodes = new LinkedList();
            while(nodes.isEmpty() == false){
                SetNode tempNode = (SetNode)nodes.remove();
                System.out.print(tempNode.getData() + "\t");
                if(tempNode.getLeftBranch() != null){
                    tempNodes.add(tempNode.getLeftBranch());
                }
                if(tempNode.getRightBranch() != null){
                    tempNodes.add(tempNode.getRightBranch());
                }
            }
            nodes.addList(tempNodes);
            System.out.print("\n");
        } while (tempNodes.isEmpty() == false);
    }
    public void initializeIterator(){
        iterator = root;
    }
    public void moveIteratorToLeft(){
        if(validIterator()){
            iterator = (SetNode)iterator.getLeftBranch();
        }
    }
    public void moveIteratorToRight(){
        if(validIterator()){
            iterator = (SetNode)iterator.getRightBranch();
        }
    }
    public boolean validIterator(){
        return iterator != null;
    }
    public Object getIterator(){
        if(validIterator()){
            return iterator.getData();
        }
        else{
            return null;
        }
    }
    protected DataBatch selectBatch(TreeTraversal treeTraversal){
        DataBatch batch;
        switch (treeTraversal) {
            case DEPTH_FIRST_SEARCH -> batch = new DynamicStack();
            case BREADTH_FIRST_SEARCH -> batch = new DynamicQueue();
            default -> throw new AssertionError();
        }
        return batch;
    }
}