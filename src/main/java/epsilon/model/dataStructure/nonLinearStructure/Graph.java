package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.enums.TreeTraversal;
import static epsilon.utils.FunctionUtils.isInRange;

public class Graph{
    protected Matrix matrix;
    protected LinkedList nodes;
    public Graph(){
        nodes = new LinkedList();
    }
    public Graph(int matrizSize){
        this();
        matrix = new Matrix(matrizSize, matrizSize);
    }
    public boolean isEmpty(){
        return matrix == null;
    }
    public void clear(){
        nodes.clear();
        matrix = null;
    }
    public boolean add(Object object){
        if(object != null && nodes.find(object) == null){
            nodes.add(object);
            if(isEmpty() == true){
                initializeMatrix();
            }
            else if(nodes.size() > matrix.getHeight()){
                NumericArray column = new NumericArray(matrix.getHeight());
                column.fill(0);
                matrix.addColumn(column);
                NumericArray row = new NumericArray(matrix.getWidth());
                row.fill(0);
                matrix.addRow(row);
            }
            return true;
        }
        else{
            return false;
        }
    }
    private void initializeMatrix(){
        matrix = new Matrix(1, 1);
    }
    public boolean addDirectedWeightedEdge(int node1, int node2, int weight){
        if(node1 != node2 && validIndex(node1) && validIndex(node2)){
            matrix.modify(weight, node1, node2);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addDirectedWeightedEdge(Object object1, Object object2, int weight){
        int pos1 = nodes.findPosition(object1);
        int pos2 = nodes.findPosition(object2);
        return addDirectedWeightedEdge(pos1, pos2, weight);
    }
    public boolean addDirectedEdge(int node1, int node2){
        return addDirectedWeightedEdge(node1, node2, 1);
    }
    public boolean addDirectedEdge(Object object1, Object object2){
        return addDirectedWeightedEdge(object1, object2, 1);
    }
    public boolean addWeightedEdge(int node1, int node2, int weight){
        return addDirectedWeightedEdge(node1, node2, weight) && addDirectedWeightedEdge(node2, node1, weight);
    }
    public boolean addWeightedEdge(Object object1, Object object2, int weight){
        int pos1 = nodes.findPosition(object1);
        int pos2 = nodes.findPosition(object2);
        return addWeightedEdge(pos1, pos2, weight);
    }
    public boolean addEdge(int node1, int node2){
        return addDirectedEdge(node1, node2) && addDirectedEdge(node2, node1);
    }
    public boolean addEdge(Object object1, Object object2){
        int pos1 = nodes.findPosition(object1);
        int pos2 = nodes.findPosition(object2);
        return addEdge(pos1, pos2);
    }
    public int size(){
        return nodes.size();
    }
    public Object find(Object object){
        return find(object, 0);
    }
    public Object find(Object object, int startNode){
        return find(object, startNode, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public Object find(Object object, Comparator comparator){
        return find(object, 0, comparator);
    }
    public Object find(Object object, TreeTraversal treeTraversal){
        return find(object, 0, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public Object find(Object object, int startNode, Comparator comparator){
        return find(object, startNode, comparator, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public Object find(Object object, Comparator comparator, TreeTraversal traversal){
        return find(object, 0, comparator, traversal);
    }
    public Object find(Object object, int startNode, TreeTraversal traversal){
        return find(object, startNode, new BaseObjectComparator(), traversal);
    }
    public Object find(Object object, int startNode, Comparator comparator, TreeTraversal traversal){
        if(isEmpty() == false && validIndex(startNode)){
            BinaryTree scannedNodes = new BinaryTree(comparator);
            DataBatch batch;
            if(traversal == TreeTraversal.BREADTH_FIRST_SEARCH){
                batch = new DynamicQueue();
            }
            else{
                batch = new DynamicStack();
            }
            batch.add(nodes.get(startNode));
            while(batch.isEmpty() == false){
                Object tempNode = batch.remove();
                if(comparator.compare(tempNode, object) == 0){
                    return tempNode;
                }
                Array connectedNodes = getConnectedNodes(tempNode);
                for (int index = 0; index < connectedNodes.size(); index++) {
                    Object nextNode = connectedNodes.get(index);
                    if(scannedNodes.find(nextNode) == null && batch.hasObject(nextNode) == false){
                        batch.add(nextNode);
                    }
                }
                scannedNodes.add(tempNode);
            }
            return null;
        }
        else{
            return null;
        }
    }
    public Object find(Object object, Object nodeObject){
        return find(object, nodes.findPosition(nodeObject));
    }
    public boolean removeNode(int node){
        if(validIndex(node)){
            if(nodes.size() > 1){
                nodes.remove(node);
                matrix.removeRow(node);
                matrix.removeColumn(node);
            }
            else{
                nodes.clear();
                matrix = null;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean removeNode(Object object){
        return removeNode(nodes.findPosition(object));
    }
    public boolean removeDirectedEdge(int node1, int node2){
        if(node1 != node2 && validIndex(node1) && validIndex(node2)){
            matrix.modify(0, node1, node2);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean removeDirectedEdge(Object object1, Object object2){
        int pos1 = nodes.findPosition(object1);
        int pos2 = nodes.findPosition(object2);
        return removeDirectedEdge(pos1, pos2);
    }
    public boolean removeEdge(int node1, int node2){
        return removeDirectedEdge(node1, node2) && removeDirectedEdge(node2, node1);
    }
    public boolean removeEdge(Object object1, Object object2){
        int pos1 = nodes.findPosition(object1);
        int pos2 = nodes.findPosition(object2);
        return removeEdge(pos1, pos2);
    }
    public Array getConnectedNodes(int node){
        if(validIndex(node)){
            Array connectedNodes = new Array(nodes.size());
            Array nodeColumn = matrix.getColumn(node);
            for (int index = 0; index < nodeColumn.size(); index++) {
                Number edgeNumber = (Number)nodeColumn.get(index);
                double edge = edgeNumber.doubleValue();
                if(edge != 0){
                    Object connectedNode = nodes.get(index);
                    connectedNodes.add(connectedNode);
                }
            }
            return connectedNodes;
        }
        else{
            return null;
        }
    }
    public Array getConnectedNodes(Object object){
        int position = nodes.findPosition(object);
        return getConnectedNodes(position);
    }
    public void iterate(Iterator iterator, int startNode, TreeTraversal traversal){
        if(isEmpty() == false && validIndex(startNode)){
            BinaryTree scannedNodes = new BinaryTree();
            DataBatch batch;
            if(traversal == TreeTraversal.BREADTH_FIRST_SEARCH){
                batch = new DynamicQueue();
            }
            else{
                batch = new DynamicStack();
            }
            batch.add(startNode);
            while(batch.isEmpty() == false){
                int position = (int)batch.remove();
                Object tempNode = nodes.get(position);
                iterator.iterate(tempNode);
                Array connectedNodes = getConnectedNodes(position);
                for (int index = 0; index < connectedNodes.size(); index++) {
                    Object nextNode = connectedNodes.get(index);
                    if(scannedNodes.find(nextNode) == null && batch.hasObject(nextNode) == false){
                        batch.add(nodes.findPosition(nextNode));
                    }
                }
                scannedNodes.add(position);
            }
        }
    }
    public void print(){
        System.out.println(nodes);
        for (int row = 0; row < matrix.getHeight(); row++) {
            System.out.print("|");
            for (int column = 0; column < matrix.getWidth(); column++) {
                System.out.print(matrix.getObject(row, column) + "|");
            }
            System.out.print(" [" + nodes.get(row) + "]\n");
        }
    }
    protected boolean validIndex(int index){
        return isInRange(0,nodes.size()-1,index);
    }
}