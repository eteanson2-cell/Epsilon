package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.auxiliar.TreeNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.enums.TreeTraversal;
import static epsilon.utils.FunctionUtils.selectBatch;

public class BinaryTree{
    protected TreeNode mainNode;
    protected TreeNode iterator;
    protected Comparator comparator;
    public BinaryTree(){
        this(new BaseObjectComparator());
    }
    public BinaryTree(Comparator comparator){
        mainNode = null;
        iterator = null;
        this.comparator = comparator;
    }
    public boolean isEmpty(){
        return mainNode == null;
    }
    public void clear(){
        mainNode = null;
        iterator = null;
    }
    public void add(Object object){
        if(object != null){
            if(mainNode == null){
                mainNode = new TreeNode(object);
            }
            else{
                mainNode.addData(object, comparator);
            }
        }
    }
    public boolean addList(DataList dataList){
        dataList.iterateList((Object nodeObject) -> {
            add(nodeObject);
            return true;
        });
        return true;
    }
    public boolean addTree(BinaryTree tree){
        return addTree(tree, TreeTraversal.DEPTH_FIRST_SEARCH);
    }
    public boolean addTree(BinaryTree tree, TreeTraversal treeTraversal){
        if(tree.isEmpty() == false){
            LinkedList treeList = tree.toList(treeTraversal);
            treeList.iterateList((Object nodeObject) -> {
                add(nodeObject);
                return true;
            });
            return true;
        }
        else{
            return true;
        }
    }
    public boolean replace(BinaryTree tree){
        if(tree.isEmpty() == false){
            clear();
            addTree(tree);
            return true;
        }
        else{
            return true;
        }
    }
    public int size(){
        int counter = 0;
        DynamicStack stack = new DynamicStack();
        stack.add(mainNode);
        while (stack.isEmpty() == false) { 
            TreeNode tempNode = (TreeNode)stack.remove();
            if(tempNode.getLeftBranch() != null){
                stack.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                stack.add(tempNode.getRightBranch());
            }
            counter++;
        }
        return counter;
    }
    protected DynamicStack findNode(Object object){
        DynamicStack nodes = new DynamicStack();
        TreeNode tempNode = mainNode;
        int comparedValue = 1;
        while(tempNode != null && comparedValue != 0){
            nodes.add(tempNode);
            comparedValue = comparator.compare(tempNode.getData(), object);
            if(comparedValue > 0){
                tempNode = (TreeNode)tempNode.getRightBranch();
            }
            else if (comparedValue < 0) {
                tempNode = (TreeNode)tempNode.getLeftBranch();
            }
        }
        if(comparedValue == 0){
            return nodes;
        }
        else{
            return null;
        }
    }
    public Object find(Object object){
        DynamicStack nodes = findNode(object);
        if(nodes != null){
            TreeNode tempNode = (TreeNode)nodes.getTop();
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public Object getMinObject(){
        TreeNode tempNode = mainNode;
        if(tempNode != null){
            while(tempNode.getLeftBranch() != null){
                tempNode = (TreeNode)tempNode.getLeftBranch();
            }
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public Object getMaxObject(){
        TreeNode tempNode = mainNode;
        if(tempNode != null){
            while(tempNode.getRightBranch() != null){
                tempNode = (TreeNode)tempNode.getRightBranch();
            }
            return tempNode.getData();
        }
        else{
            return null;
        }
    }
    public Object remove(Object object){
        DynamicStack nodes = findNode(object);
        if(nodes != null){
            TreeNode tempNode = (TreeNode)nodes.remove();
            Object removedObject = tempNode.getData();
            if(tempNode.getCounter() == 1){
                if(tempNode == mainNode && tempNode.hasNextBranch() == false){
                    mainNode = null;
                }
                else{
                    tempNode.remove();
                    if(tempNode.getData() == null){
                        TreeNode prevNode = (TreeNode)nodes.remove();
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
                    tempNode = (TreeNode)nodes.remove();
                    tempNode.balanceNode();
                }
            }
            else{
                tempNode.reduceCounter();
            }
            return removedObject;
        }
        else{
            return null;
        }
    }
    public Array getBreadth(int breadth){
        DynamicQueue nodes = new DynamicQueue();
        nodes.add(mainNode);
        int counter = 0;
        while(counter < breadth){
            LinkedList tempNodes = new LinkedList();
            while(nodes.isEmpty() == false){
                TreeNode tempNode = (TreeNode)nodes.remove();
                tempNodes.add(tempNode.getLeftBranch());
                tempNodes.add(tempNode.getRightBranch());
            }
            nodes.addList(tempNodes);
            counter++;
        }
        Array arrayNodes = nodes.toArray();
        for (int i = 0; i < arrayNodes.size(); i++) {
            TreeNode tempNode = (TreeNode)arrayNodes.get(i);
            arrayNodes.modify(tempNode.getData(), i);
        }
        return arrayNodes;
    }
    public LinkedList toList(TreeTraversal treeTraversal){
        LinkedList list = new LinkedList();
        DataBatch batch = selectBatch(treeTraversal);
        batch.add(mainNode);
        while (batch.isEmpty() == false) { 
            TreeNode tempNode = (TreeNode)batch.remove();
            if(tempNode.getLeftBranch() != null){
                batch.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                batch.add(tempNode.getRightBranch());
            }
            for (int i = 0; i < tempNode.getCounter(); i++) {
                list.add(tempNode.getData());
            }
        }
        return list;
    }
    public int getHeight(){
        return mainNode.getHeight();
    }
    public int count(Object object){
        TreeNode tempNode = (TreeNode)findNode(object).remove();
        if(tempNode != null){
            return tempNode.getCounter();
        }
        else{
            return 0;
        }
    }
    public void linearPrint(TreeTraversal treeTraversal){
        DataBatch nodes = selectBatch(treeTraversal);
        nodes.add(mainNode);
        while (nodes.isEmpty() == false) { 
            TreeNode tempNode = (TreeNode)nodes.remove();
            System.out.println(tempNode.getData());
            if(tempNode.getLeftBranch() != null){
                nodes.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                nodes.add(tempNode.getRightBranch());
            }
        }
    }
    public void print(){
        print(TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void print(TreeTraversal treeTraversal){
        DataBatch nodes = selectBatch(treeTraversal);
        nodes.add(mainNode);
        LinkedList tempNodes;
        do { 
            tempNodes = new LinkedList();
            while(nodes.isEmpty() == false){
                TreeNode tempNode = (TreeNode)nodes.remove();
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
        iterator = mainNode;
    }
    public void moveIteratorToLeft(){
        if(validIterator()){
            iterator = (TreeNode)iterator.getLeftBranch();
        }
    }
    public void moveIteratorToRight(){
        if(validIterator()){
            iterator = (TreeNode)iterator.getRightBranch();
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
}