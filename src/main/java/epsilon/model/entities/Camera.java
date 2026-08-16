package epsilon.model.entities;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.figures.Figure;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.Polygon;
import epsilon.model.entities.figures.Sprite;
import epsilon.model.entities.interfaces.IEntity;

public class Camera implements IEntity{
    private double x,y,height,width,dx,dy;
    private int moveLaps;
    public Camera(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.width = width;
        this.height = height;
        this.moveLaps = 1;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getHeight(){
        return height;
    }
    public double getWidth(){
        return width;
    }
    public double getDX(){
        return dx;
    }
    public double getDY(){
        return dy;
    }
    public int getMoveLaps() {
        return moveLaps;
    }
    @Override
    public Point getCenter(){
        return new Point(x,y);
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setDX(double dx){
        this.dx = dx;
    }
    public void setDY(double dy){
        this.dy = dy;
    }
    public void setHeight(double height){
        this.height = height;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setMoveLaps(int moveLaps) {
        this.moveLaps = moveLaps;
    }
    public void move(IEntity entity){
        x = entity.getCenter().getX();
        y = entity.getCenter().getY();
    }

    @Override
    public void move(double xVector, double yVector){
        x += xVector;
        y += yVector; 
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.drawLine((int)(x-(width/2))+20,(int)(y-(height/2))+20,(int)(x-(width/2))+100,(int)(y-(height/2))+20);
        g2d.drawLine((int)(x-(width/2))+20,(int)(y-(height/2))+20,(int)(x-(width/2))+20,(int)(y-(height/2))+100);
        g2d.drawLine((int)(x+(width/2))-20,(int)(y-(height/2))+20,(int)(x+(width/2))-100,(int)(y-(height/2))+20);
        g2d.drawLine((int)(x+(width/2))-20,(int)(y-(height/2))+20,(int)(x+(width/2))-20,(int)(y-(height/2))+100);
        g2d.drawLine((int)(x-(width/2))+20,(int)(y+(height/2))-20,(int)(x-(width/2))+100,(int)(y+(height/2))-20);
        g2d.drawLine((int)(x-(width/2))+20,(int)(y+(height/2))-20,(int)(x-(width/2))+20,(int)(y+(height/2))-100);
        g2d.drawLine((int)(x+(width/2))-20,(int)(y+(height/2))-20,(int)(x+(width/2))-100,(int)(y+(height/2))-20);
        g2d.drawLine((int)(x+(width/2))-20,(int)(y+(height/2))-20,(int)(x+(width/2))-20,(int)(y+(height/2))-100);
    }
    @Override
    public void fill(Graphics2D g2d){
        g2d.drawLine((int)(x-(width/2)),(int)(y-(height/2)),(int)(x+(width/2)),(int)(y-(height/2)));
        g2d.drawLine((int)(x-(width/2)),(int)(y-(height/2)),(int)(x-(width/2)),(int)(y+(height/2)));
        g2d.drawLine((int)(x+(width/2)),(int)(y-(height/2)),(int)(x+(width/2)),(int)(y+(height/2)));
        g2d.drawLine((int)(x-(width/2)),(int)(y+(height/2)),(int)(x+(width/2)),(int)(y+(height/2)));
    }
    public boolean isOnCamera(Sprite sprite){
        LinkedList figures = sprite.getFigures();
        for(int i = 0; i < figures.size(); i++){
            Figure figure = (Figure)figures.get(i);
            if (isOnCamera(figure)) {
                return true;
            }
        }
        return false;
    }
    public boolean isOnCamera(IEntity entity){
        Polygon camera = new Polygon(new double[]{x-(width/2),x+(width/2),x+(width/2),x-(width/2)},
                                     new double[]{y-(height/2),y-(height/2),y+(height/2),y+(height/2)});
        return camera.intersects(entity);
    }

    @Override
    public boolean intersects(IEntity entity) {
        return new Polygon(new double[]{x-(width/2),x+(width/2),x+(width/2),x-(width/2)},
                           new double[]{y-(height/2),y-(height/2),y+(height/2),y+(height/2)}
                           ).intersects(entity);
    }
    public void update(){
        x += dx;
        y += dy;
        moveLaps -= 1;
        if(moveLaps <= 0){
            dx = 0;
            dy = 0;
            moveLaps = 0;
        }
    }


}