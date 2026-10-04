package epsilon.program;

import epsilon.model.dataStructure.linearStructure.dynamic.NumericList;

public class arrayConvolutionTest{
    public static void main(String[] args) {
        NumericList vector = new NumericList();
        vector.add(5);
        vector.add(2);
        vector.add(8);
        vector.add(3);
        vector.add(7);
        NumericList filter = new NumericList();
        filter.add(1);
        filter.add(4);
        filter.add(6);
        System.out.println(vector);
        System.out.println(filter);
        System.out.println(vector.convolution((NumericList)filter.copy()));
    }
}