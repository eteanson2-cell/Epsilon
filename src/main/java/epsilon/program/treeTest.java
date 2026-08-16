package epsilon.program;

import epsilon.model.dataStructure.nonLinearStructure.TreeMap;

public class treeTest{
    public static void main(String[] args) {
        TreeMap map = new TreeMap((Object obj1, Object obj2) -> {
            if(obj1 instanceof Number n1 && obj2 instanceof Number n2){
                double d1 = n1.doubleValue();
                double d2 = n2.doubleValue();
                if(d1 > d2){
                    return -1;
                }
                else if(d1 < d2){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            else{
                throw new Error("One of the values it's not a number");
            }
        });
        for (int i = 0; i < 10; i++) {
            map.addKey(i);
            map.print();
            System.out.println("|");
        }
        map.getKeys().print();
    }
}