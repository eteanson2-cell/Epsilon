package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.MapNode;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.enums.TreeTraversal;

public class TreeMap{
    protected MapNode root;
    protected MapNode iterator;
    protected Comparator comparator;
    public TreeMap(Comparator comparator){
        this.comparator = comparator;
        root = null;
        iterator = null;
    }
    public boolean isEmpty(){
        return root == null;
    }
    public void clear(){
        root = null;
        iterator = null;
    }
    public boolean addKey(Object key){
        if(root == null){
            root = new MapNode(key);
            return true;
        }
        else{
            return root.addKey(key, comparator);
        }
    }
    protected DynamicStack findNode(Object key){
        DynamicStack nodes = new DynamicStack();
        MapNode tempNode = root;
        while(tempNode != null){
            nodes.add(tempNode);
            int comparedValue = comparator.compare(tempNode.getKey(), key);
            if(comparedValue > 0){
                tempNode = (MapNode)tempNode.getRightBranch();
            }
            else if (comparedValue < 0) {
                tempNode = (MapNode)tempNode.getLeftBranch();
            }
            else{
                return nodes;
            }
        }
        return null;
    }
    public boolean addObject(Object data, Object key){
        DynamicStack nodes = findNode(key);
        if(nodes != null){
            MapNode currentNode = (MapNode)nodes.getTop();
            currentNode.addData(data);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean clearData(Object key){
        DynamicStack nodes = findNode(key);
        if(nodes != null){
            MapNode currentNode = (MapNode)nodes.getTop();
            currentNode.clearData();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean replaceData(LinkedList newData, Object key){
        DynamicStack nodes = findNode(key);
        if(nodes != null){
            MapNode currentNode = (MapNode)nodes.getTop();
            currentNode.setDatalist(newData);
            return true;
        }
        else{
            return false;
        }
    }
    public LinkedList getList(Object key){
        DynamicStack nodes = findNode(key);
        if(nodes != null){
            MapNode currentNode = (MapNode)nodes.getTop();
            return (LinkedList)currentNode.getData();
        }
        else{
            return null;
        }
    }
    public LinkedList removeKey(Object key){
        DynamicStack nodes = findNode(key);
        if(nodes != null){
            MapNode tempNode = (MapNode)nodes.remove();
            if(tempNode == root && root.hasNextBranch() == false){
                root = null;
            }
            else{
                tempNode.remove();
                if(tempNode.getKey() == null){
                    MapNode prevNode = (MapNode)nodes.remove();
                    if(tempNode == prevNode.getLeftBranch()){
                        prevNode.clearLeftBranch();
                    }
                    else if(tempNode == prevNode.getRightBranch()){
                        prevNode.clearRightBranch();
                    }
                    prevNode.balanceNode();
                }
            }
            while(nodes.isEmpty() == false){
                tempNode = (MapNode)nodes.remove();
                tempNode.balanceNode();
            }
            LinkedList nodeList = tempNode.getDatalist();
            return nodeList;
        }
        else{
            return null;
        }
    }
    public void iteration(Iterator iterator, TreeTraversal treeTraversal){
        DataBatch batch = selectBatch(treeTraversal);
        batch.add(root);
        while (batch.isEmpty() == false) { 
            MapNode tempNode = (MapNode)batch.remove();
            iterator.iterate(tempNode);
            if(tempNode.getLeftBranch() != null){
                batch.add(tempNode.getLeftBranch());
            }
            if(tempNode.getRightBranch() != null){
                batch.add(tempNode.getRightBranch());
            }
        }
    }
    public LinkedList getKeys(){
        return getKeys(TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public LinkedList getKeys(TreeTraversal treeTraversal){
        LinkedList keys = new LinkedList();
        iteration((Object nodeObject) -> {
            MapNode tempNode = (MapNode)nodeObject;
            keys.add(tempNode.getKey());
        }, treeTraversal);
        return keys;
    }
    public void printKeys(){
        printKeys(TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void printKeys(TreeTraversal treeTraversal){
        DataBatch nodes = selectBatch(treeTraversal);
        nodes.add(root);
        LinkedList tempNodes;
        do { 
            tempNodes = new LinkedList();
            while(nodes.isEmpty() == false){
                MapNode tempNode = (MapNode)nodes.remove();
                System.out.print(tempNode.getKey() + "\t");
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
    public void print(){
        print(TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void print(TreeTraversal treeTraversal){
        iteration((Object nodeObject) -> {
            MapNode tempNode = (MapNode)nodeObject;
            System.out.print("Key: " + tempNode.getKey());
            System.out.println(", Data: " + tempNode.getData());
        }, treeTraversal);
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