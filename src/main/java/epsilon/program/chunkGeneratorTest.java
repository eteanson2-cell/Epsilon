package epsilon.program;

import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import static epsilon.utils.FunctionUtils.generateRandomIntegers;

public class chunkGeneratorTest{
    public static void main(String[] args) {
        NumericArray arr = generateRandomIntegers(1,48,10);
        System.out.println(arr);
        
    }
}