package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.roundDouble;

public class Rectangle extends Figure{
    double xCenter, yCenter, height, width;
    public Rectangle(double xCenter, double yCenter, double width, double height){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.width = width;
        this.height = height;
    }

    public double getXCenter() {
        return xCenter;
    }

    public void setXCenter(double xCenter) {
        this.xCenter = xCenter;
    }

    public double getYCenter() {
        return yCenter;
    }

    public void setYCenter(double yCenter) {
        this.yCenter = yCenter;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public double getXLeftAxis(){
        return xCenter-(width/2);
    }
    public double getXRightAxis(){
        return xCenter+(width/2);
    }
    public double getYUpperAxis(){
        return yCenter-(height/2);
    }
    public double getYLowerAxis(){
        return yCenter+(height/2);
    }

    @Override
    public Point getCenter() {
        return new Point(xCenter,yCenter);
    }
    public Polygon toPolygon(){
        return new Polygon(new double[]{getXLeftAxis(),getXRightAxis(),getXRightAxis(),getXLeftAxis()}, 
                           new double[]{getYUpperAxis(),getYUpperAxis(),getYLowerAxis(),getYLowerAxis()});
    }
    public Line getTopLine(){
        return new Line(new Point(getXLeftAxis(),getYUpperAxis()), new Point(getXRightAxis(),getYUpperAxis()));
    }
    public Line getBottomLine(){
        return new Line(new Point(getXLeftAxis(),getYLowerAxis()), new Point(getXRightAxis(),getYLowerAxis()));
    }
    public Line getRightLine(){
        return new Line(new Point(getXRightAxis(),getYUpperAxis()), new Point(getXRightAxis(),getYLowerAxis()));
    }
    public Line getLeftLine(){
        return new Line(new Point(getXLeftAxis(),getYUpperAxis()), new Point(getXLeftAxis(),getYLowerAxis()));
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(borderColor);
        g2d.drawRect(roundDouble(getXLeftAxis()), roundDouble(getYUpperAxis()), roundDouble(width), roundDouble(height));
    }

    @Override
    public void fill(Graphics2D g2d) {
        g2d.setColor(insideColor);
        g2d.fillRect(roundDouble(getXLeftAxis()), roundDouble(getYUpperAxis()), roundDouble(width), roundDouble(height));
    }

    @Override
    public boolean intersects(IEntity entity) {
        switch (entity) {
            case Rectangle rectangle -> {
                boolean intersectanX = getXLeftAxis() < rectangle.getXRightAxis() &&
                        getXRightAxis() > rectangle.getXLeftAxis();
                boolean intersectanY = getYUpperAxis() < rectangle.getYLowerAxis() &&
                        getYLowerAxis() > rectangle.getYUpperAxis();
                return intersectanX && intersectanY;
            }
            case Line line -> {
                return line.intersects(this);
            }
            case Oval oval -> {
                return oval.intersects(this);
            }
            case Polygon polygon -> {
                return polygon.intersects(this);
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

    @Override
    public IEntity copy() {
        Rectangle copy = new Rectangle(xCenter, yCenter, width, height);
        copy.borderColor = borderColor;
        copy.insideColor = insideColor;
        return copy;
    }
}