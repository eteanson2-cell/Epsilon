package epsilon.program;

import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import static epsilon.utils.FunctionUtils.randomNumber;

public class quickSortTest{
    public static void main(String[] args) {
        NumericArray vector = new NumericArray(20);
        while (vector.isFilled() == false) { 
            vector.add(randomNumber(0,1000));
        }
        System.out.println(vector);
        vector.quickSort();
        System.out.println(vector);
    }
}