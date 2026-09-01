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
    public double getAngle(Point p2){
        double dy = p2.getY() - y;
        double dx = p2.getX() - x;
        if(dx == 0){
            dx = 0.00000000001;
        }
        double m = dy/dx;
        double angle = Math.toDegrees(Math.atan(m));
        if(x > p2.getX()){
            angle += 180;
        }
        if(angle < 0){
            angle+= 360;
        }
        return angle;
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
    @Override
    public Point copy(){
        Point copy = new Point(x, y);
        return copy;
    }
    @Override
    public String toString(){
        return "x: " + x + "|y: " + y;
    }
}