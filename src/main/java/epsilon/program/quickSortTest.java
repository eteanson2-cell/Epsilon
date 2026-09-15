package epsilon.program;

import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import static epsilon.utils.FunctionUtils.randomNumber;

public class quickSortTest{
    public static void main(String[] args) {
        long quickSortElapsed;
        long mergeSortElapsed;
        do { 
            NumericArray vector1 = new NumericArray(100);
            NumericArray vector2 = new NumericArray(100);
            long start;
            while (vector1.isFilled() == false) { 
                int rint = randomNumber(0,10000);
                vector1.add(rint);
                vector2.add(rint);
            }
            
            System.out.println(vector1);

            start = System.nanoTime();
            vector2.mergeSort();
            mergeSortElapsed = System.nanoTime() - start;

            start = System.nanoTime();
            vector1.quickSort();
            quickSortElapsed = System.nanoTime() - start;

            System.out.println("Quicksort = " + quickSortElapsed
                           + "\nMergesort = " + mergeSortElapsed);
            System.out.println(vector1 + "\n");
        } while (quickSortElapsed > mergeSortElapsed);
        
    }
}