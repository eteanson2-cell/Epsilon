package epsilon.program;

import epsilon.model.dataStructure.nonLinearStructure.DynamicGraph;

public class dynamicGraphTest{
    public static void main(String[] args) {
        DynamicGraph graph = new DynamicGraph((Object obj1, Object obj2) -> {
            return obj1.toString().compareTo(obj2.toString());
        });
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("D", "A");
        graph.addEdge("E", "D");
        graph.addEdge("B", "D");
        graph.print();
        graph.getConnectedNodes("D").print();
        System.out.println(graph.find("E"));
    }
}