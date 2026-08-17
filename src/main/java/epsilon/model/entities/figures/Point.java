package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.entities.interfaces.IEntity;

public class Point implements IEntity{
    private double x,y;
    public Point(){
    }
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    @Override
    public Point getCenter(){
        return this;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public boolean overlaps(Point point){
        return !(y < point.getX() || point.getY() < x);
    }
    public double pointProduct(Point point){
        return this.x * point.getX() + this.y * point.getY();
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.drawRect((int)x,(int)y,1,1);
    }
    @Override
    public void fill(Graphics2D g2d){
        g2d.fillRect((int)x,(int)y,1,1);
    }
    @Override
    public boolean intersects(IEntity entity) {
        if(entity instanceof  Polygon polygon){
            return polygon.intersects(this);
        }
        else{
            return false;
        }
    }

    @Override
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }
    public void moveTo(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Point copy(){
        Point copy = new Point(x, y);
        return copy;
    }
}