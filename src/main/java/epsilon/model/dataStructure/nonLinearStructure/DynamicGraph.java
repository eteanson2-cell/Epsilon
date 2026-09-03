package epsilon.model.dataStructure.nonLinearStructure;

import epsilon.model.dataStructure.auxiliar.GraphEdge;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.enums.TreeTraversal;
import static epsilon.utils.FunctionUtils.selectBatch;

public class DynamicGraph{
    protected TreeMap nodes;
    public DynamicGraph(Comparator comparator){
        nodes = new TreeMap(comparator);
    }
    public void clear(){
        nodes.clear();
    }
    public boolean addNode(Object node){
        return nodes.addKey(node);
    }
    public boolean addDirectedWeightedEdge(Object node1, Object node2, Object weight){
        if(nodes.hasKey(node2)){
            return nodes.addObject(new GraphEdge(node2, weight), node1);
        }
        else{
            return false;
        }
    }
    public boolean addWeightedEdge(Object node1, Object node2, Object weight){
        if(nodes.hasKey(node1) && nodes.hasKey(node2)){
            nodes.addObject(new GraphEdge(node2, weight), node1);
            nodes.addObject(new GraphEdge(node1, weight), node2);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addDirectedEdge(Object node1, Object node2){
        return addDirectedWeightedEdge(node1, node2, true);
    }
    public boolean addEdge(Object node1, Object node2){
        return addWeightedEdge(node1, node2, true);
    }
    public boolean hasNode(Object key){
        return nodes.hasKey(key);
    }
    public Object find(Object data, Object startNode, Comparator comparator, TreeTraversal treeTraversal){
        if(nodes.hasKey(startNode)){
            DataBatch batch = selectBatch(treeTraversal);
            SetTree scannedNodes = new SetTree(nodes.getComparator());
            batch.add(startNode);
            while (batch.isEmpty() == false) { 
                Object currentNode = batch.remove();
                if(comparator.compare(currentNode, data) == 0){
                    return currentNode;
                }
                LinkedList closeNodes = getConnectedNodes(currentNode);
                closeNodes.iterateList((Object nodeObject) -> {
                    if(scannedNodes.hasObject(nodeObject) == false){
                        batch.add(nodeObject);
                    }
                    return true;
                });
                scannedNodes.add(currentNode);
            }
            return null;
        }
        else{
            return null;
        }
    }
    public Object find(Object data, Object startNode, TreeTraversal treeTraversal){
        return find(data, startNode, nodes.getComparator(), treeTraversal);
    }
    public Object find(Object data, Object startNode, Comparator comparator){
        return find(data, startNode, comparator, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public Object find(Object data, TreeTraversal treeTraversal, Comparator comparator){
        return find(data, nodes.getRootKey(), comparator, treeTraversal);
    }
    public Object find(Object data, Object startNode){
        return find(data, startNode, nodes.getComparator());
    }
    public Object find(Object data, TreeTraversal treeTraversal){
        return find(data, nodes.getRootKey(), treeTraversal);
    }
    public Object find(Object data, Comparator comparator){
        return find(data, nodes.getRootKey(), comparator);
    }
    public Object find(Object data){
        return find(data, nodes.getRootKey());
    }
    public boolean removeNode(Object node){
        if(nodes.hasKey(node)){
            nodes.removeKey(node);
            nodes.iteration((Object nodeObject) -> {
                Array mapNode = (Array)nodeObject;
                LinkedList dataList = (LinkedList)mapNode.get(1);
                dataList.removeAll(node, (Object obj1, Object obj2) -> {
                    GraphEdge edge = (GraphEdge)obj1;
                    return nodes.getComparator().compare(edge.getKey(), obj2);
                });
                return true;
            });
            return true;
        }
        else{
            return false;
        }
    }
    public boolean removeDirectedEdge(Object node1, Object node2){
        if(nodes.hasKey(node1) && nodes.hasKey(node2)){
            nodes.getList(node1).removeAll(node2, (Object obj1, Object obj2) -> {
                GraphEdge edge = (GraphEdge)obj1;
                return nodes.getComparator().compare(edge.getKey(), obj2);
            });
            return true;
        }
        else{
            return false;
        }
    }
    public Object removeEdge(Object node1, Object node2){
        return removeDirectedEdge(node1, node2) && removeDirectedEdge(node2, node1);
    }
    public LinkedList getConnectedNodes(Object node){
        LinkedList linkedNodes = nodes.getList(node);
        if(linkedNodes != null){
            LinkedList nearNodes = new LinkedList();
            linkedNodes.iterateList((Object nodeObject) -> {
                GraphEdge edge = (GraphEdge)nodeObject;
                if(nearNodes.find(edge.getKey(), nodes.getComparator()) == null){
                    nearNodes.add(edge.getKey());
                }
                return true;
            });
            return nearNodes;
        }
        else{
            return null;
        }
    }
    public void iterateGraph(Iterator iterator, Object startNode, TreeTraversal treeTraversal){
        if(nodes.hasKey(startNode)){
            DataBatch batch = selectBatch(treeTraversal);
            SetTree scannedNodes = new SetTree(nodes.getComparator());
            batch.add(startNode);
            while (batch.isEmpty() == false) { 
                Object currentNode = batch.remove();
                boolean keepGoing = iterator.iterate(currentNode);
                if(keepGoing == false){
                    break;
                }
                LinkedList closeNodes = getConnectedNodes(currentNode);
                closeNodes.iterateList((Object nodeObject) -> {
                    if(scannedNodes.hasObject(nodeObject) == false){
                        batch.add(nodeObject);
                    }
                    return true;
                });
                scannedNodes.add(currentNode);
            }
        }
    }
    public void iterateGraph(Iterator iterator, Object startNode){
        iterateGraph(iterator, startNode, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void iterateGraph(Iterator iterator, TreeTraversal treeTraversal){
        iterateGraph(iterator, nodes.getRootKey(), treeTraversal);
    }
    public void iterateGraph(Iterator iterator){
        iterateGraph(iterator, nodes.getRootKey());
    }
    public void iterateNodes(Iterator iterator, TreeTraversal treeTraversal){
        nodes.iteration((Object nodeObject) -> {
            Array mapNode = (Array)nodeObject;
            return iterator.iterate(mapNode.get(0));
        }, treeTraversal);
    }
    public void iterateNodes(Iterator iterator){
        iterateNodes(iterator, TreeTraversal.BREADTH_FIRST_SEARCH);
    }
    public void print(){
        nodes.print();
    }
}