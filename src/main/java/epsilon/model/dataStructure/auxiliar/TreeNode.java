package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public class TreeNode extends BinaryNode{
    protected int counter;
    public TreeNode(Object data){
        super(data);
        counter = 1;
    }
    public int getCounter(){
        return counter;
    }
    @Override
    public void addData(Object newData, Comparator comparator){
        if(newData != null){
            int comparedValue = comparator.compare(data, newData);
            if(comparedValue > 0){
                setRightBranch(newData, comparator);
            }
            else if (comparedValue < 0) {
                setLeftBranch(newData, comparator);
            }
            else{
                counter++;
            }
        }
    }
    @Override
    public void setRightBranch(BinaryNode rightBranch){
        if(rightBranch instanceof TreeNode){
            this.rightBranch = rightBranch;
        }
    }
    @Override
    public void setLeftBranch(BinaryNode leftBranch){
        if (leftBranch instanceof TreeNode) {
            this.leftBranch = leftBranch;
        }
    }
    @Override
    public Object remove(){
        Object removedData = data;
        if(counter == 1){
            if(hasNextBranch() == true){
                if(rightBranch == null){
                    counter = ((TreeNode)leftBranch).getCounter();
                }
                else if(leftBranch == null){
                    counter = ((TreeNode)rightBranch).getCounter();
                }
                else{
                    Array lastNodes = rightBranch.minValueNode(null);
                    TreeNode minNode = (TreeNode)lastNodes.get(0);
                    counter = minNode.getCounter();
                }
                return super.remove();
            }
            else{
                data = null;
            }
        }
        else{
            reduceCounter();
        }
        return removedData;
    }
    public boolean reduceCounter(){
        if(counter > 1){
            counter--;
            return true;
        }
        else{
            return false;
        }
    }
    public void setRightBranch(Object data, Comparator comparator){
        if(rightBranch == null){
            rightBranch = new TreeNode(data);
        }
        else{
            rightBranch.addData(data, comparator);
            balanceNode();
        }
    }
    public void setRightBranch(Object data){
        setRightBranch(data, new BaseObjectComparator());
    }
    public void setLeftBranch(Object data, Comparator comparator){
        if(leftBranch == null){
            leftBranch = new TreeNode(data);
        }
        else{
            leftBranch.addData(data, comparator);
            balanceNode();
        }
    }
    public void setLeftBranch(Object data){
        setLeftBranch(data, new BaseObjectComparator());
    }
    @Override
    protected void LLRotation(){
        TreeNode left = (TreeNode)leftBranch;
        int tempCounter = left.getCounter();
        super.LLRotation();
        counter = tempCounter;
    }
    @Override
    protected void RRRotation(){
        TreeNode right = (TreeNode)rightBranch;
        int tempCounter = right.getCounter();
        super.RRRotation();
        counter = tempCounter;
    }
    @Override
    protected BinaryNode cloneNode(){
        TreeNode copy = new TreeNode(data);
        copy.rightBranch = rightBranch;
        copy.leftBranch = leftBranch;
        copy.counter = counter;
        return copy;
    }
}