package epsilon.program;

import epsilon.model.dataStructure.nonLinearStructure.Array2D;

public class matrixRotationTest{
    public static void main(String[] args) {
        Array2D arr = new Array2D(3, 3);
        arr.modify(1, 0, 0);
        arr.modify(2, 0, 1);
        arr.modify(3, 0, 2);
        arr.modify(4, 1, 0);
        arr.modify(5, 1, 1);
        arr.modify(6, 1, 2);
        arr.modify(7, 2, 0);
        arr.modify(8, 2, 1);
        arr.modify(9, 2, 2);
        arr.printRows();
        arr.rotateColumns(1);
        arr.printRows();
    }
}