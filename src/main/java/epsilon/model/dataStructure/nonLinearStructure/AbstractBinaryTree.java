package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.auxiliar.BinaryNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.enums.TreeTraversal;
import static epsilon.utils.FunctionUtils.selectBatch;

public abstract class AbstractBinaryTree<Node extends BinaryNode> {
    protected Node root;
    protected Node iterator;
    protected Comparator comparator;
    public AbstractBinaryTree(Comparator comparator){
        this.comparator = comparator;
        root = null;
        iterator = null;
    }
    public AbstractBinaryTree(){
        this(new BaseObjectComparator());
    }
    public Comparator getComparator(){
        return comparator;
    }
    public boolean isEmpty(){
        return root == null;
    }
    public void clear(){
        root = null;
        iterator = null;
    }
    public int getHeight(){
        return root.getHeight();
    }
    public abstract boolean add(Object newData);
    public boolean addList(DataList dataList){
        Array boolArray = new Array(1);
        dataList.iterateList((Object nodeObject) -> {
            boolean added = add(nodeObject);
            if(added == false){
                boolArray.add(false);
                return false;
            }
            else{
                return true;
            }
        });
        return boolArray.isEmpty();
    }
    public Object getRootObject(){
        if(root != null){
            return root.getData();
        }
        else{
            return null;
        }
    }
    public Object getMinObject(){
        Node tempNode = (Node)root;
        if(tempNode != null){
            while(tempNode.getLeftBranch() != null){
                tempNode = (Node)tempNode.getLeftBranch();
            }
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public Object getMaxObject(){
        Node tempNode = (Node)root;
        if(tempNode != null){
            while(tempNode.getRightBranch() != null){
                tempNode = (Node)tempNode.getRightBranch();
            }
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    protected abstract DynamicStack findNode(Object key, Comparator comparator);
    protected DynamicStack findNode(Object key){
        return findNode(key, comparator);
    }
    public Object find(Object object){
        DynamicStack nodes = findNode(object);
        if(nodes != null){
            Node tempNode = (Node)nodes.getTop();
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public boolean hasObject(Object data) {
        DynamicStack nodes = findNode(data);
        return nodes != null;
    }
    public abstract Object remove(Object object);
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
                Node tempNode = (Node)nodes.remove();
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
    public void linearPrint(TreeTraversal treeTraversal){
        DataBatch nodes = selectBatch(treeTraversal);
        nodes.add(root);
        while (nodes.isEmpty() == false) { 
            Node tempNode = (Node)nodes.remove();
            System.out.println(tempNode.getData());
            if(tempNode.getLeftBranch() != null){
                nodes.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                nodes.add(tempNode.getRightBranch());
            }
        }
    }
    public void initializeIterator(){
        iterator = root;
    }
    public void moveIteratorToLeft(){
        if(validIterator()){
            iterator = (Node)iterator.getLeftBranch();
        }
    }
    public void moveIteratorToRight(){
        if(validIterator()){
            iterator = (Node)iterator.getRightBranch();
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
    public void iteration(Iterator iterator, TreeTraversal treeTraversal){
        DataBatch batch = selectBatch(treeTraversal);
        batch.add(root);
        while (batch.isEmpty() == false) { 
            Node tempNode = (Node)batch.remove();
            boolean keepGoing = iterator.iterate(tempNode.getData());
            if(keepGoing == false){
                break;
            }
            if(tempNode.getLeftBranch() != null){
                batch.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                batch.add(tempNode.getRightBranch());
            }
        }
    }
    public void iteration(Iterator iterator){
        iteration(iterator, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
}
