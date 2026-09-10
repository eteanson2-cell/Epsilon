package epsilon.program;

import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.randomNumber;

public class splitListTest{
    public static void main(String[] args) {
        Array arr = new Array(10);
        for (int i = 0; i <= 10; i++) {
            arr.add(randomNumber(0,11));
        }
        System.out.println(arr);
        LinkedList splitArr = arr.split(0);
        System.out.println(splitArr);
        DataList[] slicedList = splitArr.slice(1);
        if(slicedList != null){
            System.out.println(slicedList[0] + " | " + slicedList[1] + "\n");
        }
        arr.rotate(4);
        System.out.println(arr);
    }
}