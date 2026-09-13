package epsilon.model.dataStructure.interfaces;

import epsilon.model.enums.Operation;

public interface NumberList extends DataList{
    boolean scalarOperation(Number scalar, Operation operation);
    boolean totalOperation(NumberList numbers, Operation operation);
    double getTotal();
    double getAverage();
    double getHighestNumber();
    double getLowestNumber();
    NumberList getDistances();
    double getNorm();
    double scalarProduct(NumberList numbers);
    double euclideanDistance(NumberList numbers);
    boolean isOrthogonal(NumberList numbers);
    boolean isParallel(NumberList numbers);
    boolean isSorted();
}