package epsilon.model.dataStructure.interfaces;

public interface DataList{
    boolean isEmpty();
    void clear();
    boolean add(Object object);
    Object find(Object object);
    Object find(Object object, Comparator comparator);
    Object remove();
    Object remove(Object object);
    Object remove(Object object, Comparator comparator);
    void reverse();
    boolean addList(DataList dataList);
    int size();
    int count(Object object);
    int count(Object object, Comparator comparator);
    boolean equals(DataList dataList);
    DataList copy();
    boolean replace(DataList dataList);
    void print();
    void reversePrint();
    void initializeIterator();
    void moveIteratorToRight();
    void moveIteratorToLeft();
    boolean validIterator();
    Object getIterator();
    boolean modifyIterator(Object object);
    void iterateList(Iterator iterator);
}