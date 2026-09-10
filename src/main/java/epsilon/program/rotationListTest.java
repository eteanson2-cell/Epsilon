package epsilon.program;



import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.randomNumber;

public class rotationListTest{
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        for (int i = 0; i < 10; i++) {
            list.add(randomNumber(0,100));
        }
        System.out.println(list);
        list.rotate(4);
        System.out.println(list + "\n");
        Array arr = new Array(10);
        while (arr.isFilled() == false) { 
            arr.add(randomNumber(0,100));
        }
        System.out.println(arr);
        arr.append(9732);
        System.out.println(arr);
    }
}