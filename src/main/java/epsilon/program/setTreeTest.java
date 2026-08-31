package epsilon.program;

import epsilon.model.dataStructure.nonLinearStructure.SetTree;

public class setTreeTest{
    public static void main(String[] args) {
        SetTree set = new SetTree((Object obj1, Object obj2) -> {
            return obj2.toString().compareToIgnoreCase(obj1.toString());
        });
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");
        set.add("E");
        set.add("F");
        set.add("g");
        set.print();
        set.remove("D");
        set.print();
    }
}