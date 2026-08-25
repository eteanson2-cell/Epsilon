package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.getMax;
import static epsilon.utils.FunctionUtils.getMin;
import static epsilon.utils.FunctionUtils.isInRange;
import static epsilon.utils.FunctionUtils.roundDouble;

public class Line implements IEntity{
    private double x1,y1,x2,y2;
    public Line(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public Line(Point p1, Point p2){
        this(p1.getX(),p1.getY(),p2.getX(),p2.getY());
    }
    public double getFirstX(){
        return x1;
    }
    public double getFirstY(){
        return y1;
    }
    public Point getFirstPoint(){
        return new Point(x1,y1);
    }
    public double getSecondX(){
        return x2;
    }
    public double getSecondY(){
        return y2;
    }
    public Point getSecondPoint(){
        return new Point(x2,y2);
    }
    public void setFirstX(double x1){
        this.x1 = x1;
    }
    public void setFirstY(double y1){
        this.y1 = y1;
    }
    public void setSecondX(double x2){
        this.x2 = x2;
    }
    public void setSecondY(double y2){
        this.y2 = y2;
    }
    @Override
    public Point getCenter(){
        double xDif = getMax(x1,x2)-getMin(x1,x2);
        double yDif = getMax(y1,y2)-getMin(y1,y2);
        Point center = new Point(getMin(x1,x2) + xDif,getMin(y1,y2) + yDif);
        return center;
    }
    public double getOrientation(double x1, double y1, double x2, double y2, double x3, double y3){
		return (y2-y1)*(x3-x2)-(x2-x1)*(y3-y2);
	}
    public double getOrientation(Point p1, Point p2, Point p3){
        return getOrientation(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
    }
    public boolean onSegment(double x1, double y1, double x2, double y2, double x, double y){
        double product = (x2-x1)*(y-y1) - (y2-y1)*(x-x1);
        if (Math.abs(product) > 0.000001) {
            return false;
        }
		return isInRange(getMin(x1,x2),getMax(x1,x2),x) &&
			   isInRange(getMin(y1,y2),getMax(y1,y2),y);
	}
    public boolean onSegment(Point p1, Point p2, Point p){
        return onSegment(p1.getX(),p1.getY(),p2.getX(),p2.getY(),p.getX(),p.getY());
    }
    public Point doIntersect(double x1, double y1, double x2, double y2){
		double o1 = getOrientation(this.x1,this.y1, this.x2,this.y2, x1,y1);
		double o2 = getOrientation(this.x1,this.y1, this.x2,this.y2, x2,y2);	
		double o3 = getOrientation(x1,y1, x2,y2, this.x1,this.y1);	
		double o4 = getOrientation(x1,y1, x2,y2, this.x2,this.y2);	
		if (o1 != o2 && o3 != o4){
			return getIntersectionPoint(new Line(x1,y1,x2,y2));
		}	
		if(o1 == 0 && onSegment(this.x1,this.y1, this.x2,this.y2, x1,y1)){return new Point(x1,y1);}
		if(o2 == 0 && onSegment(this.x1,this.y1, this.x2,this.y2, x2,y2)){return new Point(x2,y2);}
		if(o3 == 0 && onSegment(x1,y1, x2,y2, this.x1,this.y1)){return new Point(this.x1,this.y1);}
		if(o4 == 0 && onSegment(x1,y1, x2,y2, this.x2,this.y2)){return new Point(this.x2,this.y2);}
		return null;
	}
    public Point doIntersect(Line otherLine){
        return doIntersect(otherLine.getFirstX(),otherLine.getFirstY(),otherLine.getSecondX(),otherLine.getSecondY());
    }
    public Point getIntersectionPoint(Line otherLine){
        Point p1 = getFirstPoint();
        Point p2 = getSecondPoint();
        Point p3 = otherLine.getFirstPoint();
        Point p4 = otherLine.getSecondPoint();
		double denom = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - 
                       (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());
		if (denom == 0){
			return null;
		}
		double px = ((p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) - 
                     (p4.getY() - p3.getY()) * (p1.getX() - p3.getX())) / denom;
        double py = ((p2.getX() - p1.getX()) * (p1.getY() - p3.getY()) - 
                     (p2.getY() - p1.getY()) * (p1.getX() - p3.getX())) / denom;
        if (isInRange(0.0,1.0,px) && 
        	isInRange(0.0,1.0,py)) {
            double x = p1.getX() + px * (p2.getX() - p1.getX());
            double y = p1.getY() + px * (p2.getY() - p1.getY());
        	return new Point(x,y);
        }
    return null;
	}
    @Override
    public boolean intersects(IEntity entity) {
        switch (entity) {
            case Line line -> {
                return doIntersect(line) != null;
            }
            case Polygon polygon -> {
                return polygon.intersects(this);
            }
            case Oval oval -> {
                return intersects(oval);
            }
            case Rectangle rectangle -> {
                return rectangle.toPolygon().intersects(toPolygon());
            }
            default -> {
                return false;
            }
        }
    }
    public boolean intersects(Oval oval){
        final double TOL = 1e-6;
        double x11 = this.x1 - oval.getXCenter();
        double y11 = this.y1 - oval.getYCenter();
        double x22 = this.x2 - oval.getXCenter();
        double y22 = this.y2 - oval.getYCenter();
        double dx = x22 - x11;
        double dy = y22 - y11;
        double A = Math.pow(dx,2)/Math.pow(oval.getWidth()/2,2) + Math.pow(dy,2)/(Math.pow(oval.getHeight()/2,2));
        double B = 2*(x11*dx)/(Math.pow(oval.getWidth()/2,2)) + 2*(y11*dy)/(Math.pow(oval.getHeight()/2,2));
        double C = Math.pow(x11,2)/(Math.pow(oval.getWidth()/2,2)) + Math.pow(y11,2)/(Math.pow(oval.getHeight()/2,2)) - 1;
        double discriminante = B*B - 4*A*C;
        if (discriminante < -TOL) {
            return false;
        } else if (Math.abs(discriminante) <= TOL) {
            double t = -B / (2*A);
            return t >= 0 && t <= 1;
        } else {
            double sqrtDisc = Math.sqrt(discriminante);
            double t1 = (-B - sqrtDisc) / (2*A);
            double t2 = (-B + sqrtDisc) / (2*A);
            return (t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1);
        }
    }
    public Point getNorm(){
        double dx = x2-x1;
        double dy = y2-y1;
        return new Point(-dy,dx);
    }
    public double getSlope(){
        double dy = y2-y1;
        double dx = x2-x1;
        if(dx == 0){
            dx = 0.00000000001;
        }
        return dy/dx;
    }
    public Polygon toPolygon(){
        return new Polygon(new double[]{x1,x2},new double[]{y1,y2});
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.drawLine(roundDouble(x1),roundDouble(y1),roundDouble(x2),roundDouble(y2));
    }
    @Override
    public void fill(Graphics2D g2d){
        draw(g2d);
    }

    @Override
    public void move(double x, double y) {
        x1 += x;
        y1 += y;
        x2 += x;
        y2 += y;
    }
    @Override
    public String toString(){
        return "P1: (x:" + x1 + " y:" + y1 +") P2 (x:" + x2 + " y:" + y2 +")";
    }

    @Override
    public IEntity copy() {
        Line copy = new Line(x1, y1, x2, y2);
        return copy;
    }
}