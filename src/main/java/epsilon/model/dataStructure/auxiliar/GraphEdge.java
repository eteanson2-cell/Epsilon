package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.nonLinearStructure.TreeMap;

public class GraphEdge{
    private Object key;
    public Object weight;
    public GraphEdge(Object key, Object weight){
        if(key != null){
            this.key = key;
        }
        this.weight = weight;
    }
    public Object getWeight() {
        return weight;
    }
    public void setWeight(Object weight) {
        this.weight = weight;
    }
    public Object getKey(){
        return key;
    }
    public void setKey(Object key, TreeMap map){
        if(map.hasKey(key)){
            this.key = key;
        }
    }
    @Override
    public String toString(){
        return "{" + key.toString() + "," + weight + "}";
    }
}