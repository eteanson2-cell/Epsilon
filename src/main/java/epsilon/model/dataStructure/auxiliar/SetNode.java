package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;

public class SetNode extends BinaryNode<SetNode>{

    public SetNode(Object data) {
        super(data);
    }
    @Override
    public void setData(Object data){
        setData(data, new BaseObjectComparator());
    }
    public boolean setData(Object newData, Comparator comparator){
        if(hasObject(newData, comparator) == false){
            data = newData;
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void setRightBranch(SetNode rightBranch){
        if(rightBranch != null){
            this.rightBranch = rightBranch;
        }
    }
    @Override
    public void setLeftBranch(SetNode leftBranch){
        if(leftBranch != null){
            this.leftBranch = leftBranch;
        }
    }
    @Override
    public void addData(Object newData, Comparator comparator) {
        add(newData, comparator);
    }
    public boolean add(Object newData, Comparator comparator){
        int comparison = comparator.compare(data, newData);
        boolean validData = false;
        if(comparison > 0){
            if(rightBranch == null){
                rightBranch = new SetNode(newData);
                validData = true;
            }
            else{
                validData = rightBranch.add(newData, comparator);
                balanceNode();
            }
        }
        else if(comparison < 0){
            if(leftBranch == null){
                leftBranch = new SetNode(newData);
                validData = true;
            }
            else{
                validData = leftBranch.add(newData, comparator);
                balanceNode();
            }
        }
        return validData;
    }
    public boolean hasObject(Object setData, Comparator comparator){
        int comparison = comparator.compare(data, setData);
        if(comparison > 0){
            if(rightBranch != null){
                return rightBranch.hasObject(setData, comparator);
            }
            else{
                return false;
            }
        }
        else if (comparison < 0) {
            if(leftBranch != null){
                return leftBranch.hasObject(setData, comparator);
            }
            else{
                return false;
            }
        }
        else{
            return true;
        }
    }
    @Override
    protected SetNode cloneNode() {
        SetNode copyNode = new SetNode(data);
        copyNode.rightBranch = rightBranch;
        copyNode.leftBranch = leftBranch;
        return copyNode;
    }

}