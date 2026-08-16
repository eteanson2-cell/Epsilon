package epsilon.model.dataStructure.linearStructure.statik;

public class Set extends Array{
    public Set(int capacity){
        super(capacity);
    }
    @Override
    public boolean add(Object object){
        int objectPosition = (int)find(object);
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