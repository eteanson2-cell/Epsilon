package epsilon.model.dataStructure.auxiliar;


public class Node{
    protected Object data;
    protected Node next;
    protected Node prev;
    public Node(Object data){
        this.data = data;
    }
    public Object getData(){
        return data;
    }
    public Node getRightNode(){
        return next;
    }
    public Node getLeftNode(){
        return prev;
    }
    public boolean setData(Object data){
        if(data != null){
            this.data = data;
            return true;
        }
        else{
            return false;
        }
    }
    public void setRightNode(Node next){
        this.next = next;
        this.next.prev = this;
    }
    public void setLeftNode(Node prev){
        this.prev = prev;
        this.prev.next = this;
    }
    public void setRight(Object data){
        if(data != null){
            setRightNode(new Node(data));
        }
    }
    public void setLeft(Object data){
        if(data != null){
            setLeftNode(new Node(data));
        }
    }
    public void cleanRight(){
        next = null;
    }
    public void cleanLeft(){
        prev = null;
    }
}