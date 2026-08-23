package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.getMax;
import static epsilon.utils.FunctionUtils.getMin;
import static epsilon.utils.FunctionUtils.roundDouble;

public class Oval extends Figure{
    double xCenter, yCenter, height, width;
    public Oval(double xCenter,double yCenter, double radix){
        this(xCenter,yCenter,radix,radix);
    }
    public Oval(double xCenter,double yCenter,double width,double height){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.width = width;
        this.height = height;
    }
    public double getXCenter(){
        return xCenter;
    }
    public double getYCenter(){
        return yCenter;
    }
    public double getHeight(){
        return height;
    }
    public double getWidth(){
        return width;
    }
    @Override
    public Point getCenter(){
        return new Point(xCenter,yCenter);
    }
    public void setXCenter(double xCenter){
        this.xCenter = xCenter;
    }
    public void setYCenter(double yCenter){
        this.yCenter = yCenter;
    }
    public void setHeight(double height){
        this.height = height;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public Polygon toPolygon(){
        double area = width*height*Math.PI;
        double counter = 360/area;
        Point[] points = new Point[(int)area];
        for (int i = 0;i < area; i++) {
            double x = degreeCosine(counter*i)*(width/2);
            double y = degreeSine(counter*i)*(height/2);
            points[i] = new Point(x,y);
        }
        return new Polygon(points);
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(borderColor);
        g2d.drawOval(roundDouble(xCenter-(width/2)),roundDouble(yCenter-(height/2)),
                     roundDouble(width),roundDouble(height));
    }
    @Override
    public void fill(Graphics2D g2d){
        g2d.setColor(insideColor);
        g2d.fillOval(roundDouble(xCenter-(width/2)),roundDouble(yCenter-(height/2)),
                     roundDouble(width),roundDouble(height));
    }
    public boolean intersects(Point point){
        return isInside(point.getX(), point.getY());
    }
    public boolean isInside(double x, double y) {
        return (Math.pow(x - xCenter, 2) / Math.pow(width, 2) + Math.pow(y - yCenter, 2) / Math.pow(height, 2)) <= 1;
    }
    public boolean intersects(Oval oval, int resolution){
        double xMin = getMin(this.xCenter - this.width, oval.xCenter - oval.width) - 1;
        double xMax = getMax(this.xCenter + this.width, oval.xCenter + oval.width) + 1;
        double yMin = getMin(this.yCenter - this.height, oval.yCenter - oval.height) - 1;
        double yMax = getMax(this.yCenter + this.height, oval.yCenter + oval.height) + 1;
        double step = (xMax - xMin) / resolution;
        for (double x = xMin; x <= xMax; x += step) {
            for (double y = yMin; y <= yMax; y += step) {
                if (this.isInside(x, y) && oval.isInside(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean intersects(IEntity entity) {
        switch (entity) {
            case Point point -> {
                return intersects(point);
            }
            case Oval oval -> {
                return intersects(oval, 3);
            }
            case Line line -> {
                return line.intersects(this);
            }
            case Rectangle rectangle -> {
                return intersects(rectangle.toPolygon());
            }
            case Polygon polygon -> {
                for (int index = 0; index < polygon.getLength(); index++) {
                    Line line = polygon.getLine(index);
                    if(line.intersects(this)){
                        return true;
                    }
                }
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public void move(double x, double y) {
        xCenter += x;
        yCenter += y;
    }
}