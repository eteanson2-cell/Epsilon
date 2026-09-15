package epsilon.program;

import epsilon.model.dataStructure.linearStructure.dynamic.NumericList;
import static epsilon.utils.FunctionUtils.randomNumber;

public class numericListTest{
    public static void main(String[] args) {
        long quickSortElapsed;
        long mergeSortElapsed;
        do { 
            long start;
            NumericList list1 = new NumericList();
            NumericList list2 = new NumericList();
            for (int i = 0; i < 100; i++) {
                int rint = randomNumber(0,10000);
                list1.add(rint);
                list2.add(rint);
            }

            System.out.println(list1);
            start = System.nanoTime();
            list1.mergeSort();
            mergeSortElapsed = System.nanoTime() - start;

            start = System.nanoTime();
            list2.quickSort();
            quickSortElapsed = System.nanoTime() - start;

            System.out.println("Quicksort = " + quickSortElapsed
                        + "\nMergesort = " + mergeSortElapsed);
            System.out.println(list1 + "\n");
        } while (quickSortElapsed < mergeSortElapsed);
        
    }
}