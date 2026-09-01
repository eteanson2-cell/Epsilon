package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.getMax;

public abstract class BinaryNode{
    BinaryNode leftBranch;
    BinaryNode rightBranch;
    Object data;
    public BinaryNode(Object data) {
        if(data != null){
            this.data = data;
            leftBranch = null;
            rightBranch = null;
        }
        else{
            System.exit(1);
        }
    }
    public Object getData(){
        return data;
    }
    public BinaryNode getRightBranch(){
        return rightBranch;
    }
    public BinaryNode getLeftBranch(){
        return leftBranch;
    }
    public void setData(Object data){
        if(data != null){
            this.data = data;
        }
    }
    public void setRightBranch(BinaryNode rightBranch){
        this.rightBranch = rightBranch;
    }
    public void setLeftBranch(BinaryNode leftBranch){
        this.leftBranch = leftBranch;
    }
    public abstract void addData(Object newData, Comparator comparator);
    public void addData(Object newData){
        addData(newData, new BaseObjectComparator());
    }
    public int getHeight(){
        if(rightBranch != null || leftBranch != null){
            int leftHeight = 0;
            int rightHeight = 0;
            if(rightBranch != null){
                rightHeight = rightBranch.getHeight();
            }
            if(leftBranch != null){
                leftHeight = leftBranch.getHeight();
            }
            return 1 + (int)getMax(rightHeight,leftHeight);
        }
        else{
            return 0;
        }
    }
    public int getLeftHeight(){
        if(leftBranch != null){
            return 1+leftBranch.getHeight();
        }
        else{
            return 0;
        }
    }
    public int getRightHeight(){
        if(rightBranch != null){
            return 1+rightBranch.getHeight();
        }
        else{
            return 0;
        }
    }
    public int getBalance(){
        return getLeftHeight()-getRightHeight();
    }
    public void clearLeftBranch(){
        leftBranch = null;
    }
    public void clearRightBranch(){
        rightBranch = null;
    }
    public void balanceNode(){
        int balance = getBalance();
        if(balance > 1){
            if(leftBranch.getBalance() < 0){
                LRRotation();
            }
            LLRotation();
        }
        else if(balance < -1){
            if(rightBranch.getBalance() > 0){
                RLRotation();
            }
            RRRotation();
        }
    }
    protected void LLRotation(){
        BinaryNode keyNode = cloneNode();
        keyNode.leftBranch = leftBranch.getRightBranch();
        data = leftBranch.getData();
        leftBranch = leftBranch.getLeftBranch();
        rightBranch = keyNode;
    }
    protected void RRRotation(){
        BinaryNode keyNode = cloneNode();
        keyNode.rightBranch = rightBranch.getLeftBranch();
        data = rightBranch.getData();
        rightBranch = rightBranch.getRightBranch();
        leftBranch = keyNode;
    }
    protected void LRRotation(){
        BinaryNode keyNode = leftBranch.cloneNode();
        keyNode.rightBranch = keyNode.rightBranch.leftBranch;
        leftBranch = leftBranch.getRightBranch();
        leftBranch.leftBranch = keyNode;
    }
    protected void RLRotation(){
        BinaryNode keyNode = rightBranch.cloneNode();
        keyNode.leftBranch = keyNode.leftBranch.rightBranch;
        rightBranch = rightBranch.getLeftBranch();
        rightBranch.rightBranch = keyNode;
    }
    public Object removeLeft(){
        if(leftBranch != null){
            Object leftData = leftBranch.data;
            if(leftBranch.hasNextBranch() == true){
                leftBranch.remove();
            }
            else{
                leftBranch = null;
            }
            return leftData;
        }
        return null;
    }
    public Object removeRight(){
        if(rightBranch != null){
            Object rightData = rightBranch.data;
            if(rightBranch.hasNextBranch() == true){
                rightBranch.remove();
            }
            else{
                rightBranch = null;
            }
            return rightData;
        }
        return null;
    }
    protected abstract BinaryNode cloneNode();
    public boolean hasNextBranch(){
        return (rightBranch != null || leftBranch != null);
    }
    public Object remove(){
        Object removedData = data;
        if(hasNextBranch() == true){
            if(rightBranch == null){
                data = leftBranch.getData();
                rightBranch = leftBranch.getRightBranch();
                leftBranch = leftBranch.getLeftBranch();
            }
            else if(leftBranch == null){
                data = rightBranch.getData();
                leftBranch = rightBranch.getLeftBranch();
                rightBranch = rightBranch.getRightBranch();
            }
            else{
                Array lastNodes = rightBranch.minValueNode(null);
                BinaryNode minNode = (BinaryNode)lastNodes.get(0);
                data = minNode.data;
                if(minNode.hasNextBranch()){
                    minNode.remove();
                }
                else{
                    BinaryNode prevNode = (BinaryNode)lastNodes.get(1);
                    if(prevNode != null){
                        prevNode.removeLeft();
                        prevNode.balanceNode();
                    }
                    else{
                        rightBranch = rightBranch.rightBranch;
                    }
                }
                
            }
        }
        else{
            data = null;
        }
        return removedData;
    }
    protected Array minValueNode(){
        return minValueNode(null);
    }
    
    protected Array minValueNode(BinaryNode prevNode){
        BinaryNode tempNode = this;
        if(tempNode.leftBranch == null){
            Array nodes = new Array(2);
            nodes.add(tempNode);
            nodes.add(prevNode);
            return nodes;
        }
        else{
            return tempNode.leftBranch.minValueNode(this);
        }
    }
    protected Array maxValueNode(){
        return maxValueNode(null);
    }
    protected Array maxValueNode(BinaryNode prevNode){
        BinaryNode tempNode = this;
        if(tempNode.rightBranch == null){
            Array nodes = new Array(2);
            nodes.add(tempNode);
            nodes.add(prevNode);    
            return nodes;
        }
        else{
            return tempNode.rightBranch.maxValueNode(this);
        }
    }
}