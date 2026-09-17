package epsilon.model.dataStructure.linearStructure.statik;

import epsilon.model.dataStructure.auxiliar.BaseObjectComparator;
import epsilon.model.dataStructure.interfaces.Comparator;

public class Set extends Array{
    protected Comparator comparator;
    public Set(int capacity, Comparator comparator){
        super(capacity);
        this.comparator = comparator;
    }
    public Set(int capacity){
        this(capacity, new BaseObjectComparator());
    }
    @Override
    public boolean add(Object object){
        int objectPosition = (int)find(object, comparator);
        if(objectPosition == -1){
            return super.add(object);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean modify(Object object, int index){
        int objectPosition = (int)find(object);
        if(objectPosition == -1){
            return super.modify(object, index);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean insert(Object object, int index){
        int objectPosition = (int)find(object);
        if(objectPosition == -1){
            return super.insert(object, index);
        }
        else{
            return false;
        }
    }
}