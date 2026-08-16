package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;

public class BaseObjectComparator implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        return obj1.toString().compareToIgnoreCase(obj2.toString());
    }

}