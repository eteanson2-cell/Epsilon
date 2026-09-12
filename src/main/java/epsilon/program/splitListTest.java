package epsilon.program;

import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.randomNumber;

public class splitListTest{
    public static void main(String[] args) {
        Array arr = new Array(30);
        while(arr.isFilled() == false) {
            arr.add(randomNumber(0,3));
        }
        System.out.println(arr);
        DataList[] splitArr = arr.split(0);
        for (DataList splitArr1 : splitArr) {
            System.out.print(splitArr1); 
        }
        
    }
}