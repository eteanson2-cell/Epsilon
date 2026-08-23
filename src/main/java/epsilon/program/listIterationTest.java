package epsilon.program;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;

public class listIterationTest{
    public static void main(String[] args) {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            list1.add(i+1);
            list2.add(i+11);
        }
        /*System.out.println("list1");
        list1.print();
        System.out.println("list2");
        list2.print();
        list1.addList(list2);
        System.out.println("list1 + list 2");
        list1.print();*/
        list1.iterateList((Object nodeObject) -> {
            Number num = (Number)nodeObject;
            int numInt = num.intValue();
            System.out.println(numInt);
            return numInt != 7;
        });
    }
}