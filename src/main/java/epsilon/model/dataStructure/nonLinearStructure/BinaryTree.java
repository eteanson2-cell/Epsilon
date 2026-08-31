package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.auxiliar.TreeNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.enums.TreeTraversal;
import static epsilon.utils.FunctionUtils.selectBatch;

public class BinaryTree extends AbstractBinaryTree<TreeNode> {
    public BinaryTree(){
        this(new BaseObjectComparator());
    }
    public BinaryTree(Comparator comparator){
        super(comparator);
    }

    @Override
    public boolean add(Object object){
        if(object != null){
            if(root == null){
                root = new TreeNode(object);
            }
            else{
                root.addData(object, comparator);
            }
            return true;
        }
        else{
            return false;
        }
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
        stack.add(root);
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
    @Override
    protected DynamicStack findNode(Object object, Comparator comparator){
        DynamicStack nodes = new DynamicStack();
        TreeNode tempNode = (TreeNode)root;
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
    
    
    @Override
    public Object remove(Object object){
        DynamicStack nodes = findNode(object);
        if(nodes != null){
            TreeNode tempNode = (TreeNode)nodes.remove();
            Object removedObject = tempNode.getData();
            if(tempNode.getCounter() == 1){
                if(tempNode == root && tempNode.hasNextBranch() == false){
                    root = null;
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
        nodes.add(root);
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
        batch.add(root);
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
    public int count(Object object){
        TreeNode tempNode = (TreeNode)findNode(object).remove();
        if(tempNode != null){
            return tempNode.getCounter();
        }
        else{
            return 0;
        }
    }
}