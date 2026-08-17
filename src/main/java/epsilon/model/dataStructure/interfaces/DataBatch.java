package epsilon.model.dataStructure.interfaces;

public interface DataBatch{
    boolean isEmpty();
    boolean isFilled();
    void clear();
    boolean add(Object object);
    boolean addList(DataList list);
    boolean hasObject(Object object);
    boolean hasObject(Object object, Comparator comparator);
    Object remove();
    void print();
    Object getTop();
    DataBatch copy();
}