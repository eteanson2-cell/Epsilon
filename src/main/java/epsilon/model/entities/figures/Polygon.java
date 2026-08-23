package epsilon.model.entities.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.interfaces.IEntity;
import epsilon.model.enums.StretchingPoint;
import static epsilon.utils.FunctionUtils.getMax;
import static epsilon.utils.FunctionUtils.getMin;

public class Polygon extends Figure{
	private double[] xPoints,yPoints;
	private final double[] baseXPoints, baseYPoints;
	private double angle;
	//Constructor
	public Polygon(double[] xPoints, double[] yPoints){
		baseXPoints = xPoints;
		baseYPoints = yPoints;
		if(xPoints.length == yPoints.length){
			this.xPoints = xPoints;
			this.yPoints = yPoints;
			angle = 0;
		}
		else{
			System.out.println("Error: The length of the arrays are inequivalent");
			System.exit(0);
		}
	}
	public Polygon(Point[] points){
		baseXPoints = new double[points.length];
		baseYPoints = new double[points.length];
		for (int i = 0; i < points.length ; i++) {
			baseXPoints[i] = points[i].getX();
			baseYPoints[i] = points[i].getY();
		}
		xPoints = baseXPoints;
		yPoints = baseYPoints;
	}
	public void resetPolygon(){
		angle = 0;
		for(int i = 0; i < xPoints.length;i++){
			xPoints[i] = baseXPoints[i];
			yPoints[i] = baseYPoints[i];
		}
	}
	//getters
	public double[] getXPoints(){
		return xPoints;
	}
	public double[] getYPoints(){
		return yPoints;
	}
	public double[] getBaseXPoints(){
		return baseXPoints;
	}
	public double[] getBaseYPoints(){
		return baseYPoints;
	}
	public double getXPoint(int index){
		try{
			return xPoints[index];
		}
		catch(IndexOutOfBoundsException e){
			return invalidIndexReturn();
		}
	}
	public double getYPoint(int index){
		try{
			return yPoints[index];
		}
		catch(IndexOutOfBoundsException e){
			return invalidIndexReturn();
		}
	}
	public Point[] getPoints(){
		Point[] points = new Point[getLength()];
		for (int i = 0; i < getLength();i++) {
			points[i] = new Point(getXPoint(i),getYPoint(i));
		}
		return points;
	}
	public Point getPoint(int index){
		return new Point(getXPoint(index),getYPoint(index));
	}
	public Line getLine(int index){
		if(index >= 0 && index < getLength()){
			return new Line(getPoint(index),getPoint((index+1)%getLength()));
		}
		else{
			return null;
		}
	}
	@Override
	public Point getCenter(){
		return new Point(getXCenter(),getYCenter());
	}
	public double getAngle(){
		return angle;
	}
	public int[] roundXPoints(){
		return doubleToInt(xPoints);
	}
	public int[] roundYPoints(){
		return doubleToInt(yPoints);
	}
	public int getLength(){
		return xPoints.length;
	}
	public double getXMax(){
		return getMax(xPoints);
	}
	public double getXMin(){
		return getMin(xPoints);
	}
	public double getYMax(){
		return getMax(yPoints);
	}
	public double getYMin(){
		return getMin(yPoints);
	}
	public double getXCenter(){
		return ((getXMax()-getXMin())/2)+getXMin(); 
	}
	public double getYCenter(){
		return ((getYMax()-getYMin())/2)+getYMin(); 
	}
	//setters
	public void setXPoints(double[] xPoints){
		this.xPoints = xPoints;
	}
	public void setYPoints(double[] yPoints){
		this.yPoints = yPoints;
	}
	public void setXPoint(int index, double xPoint){
		try{
			xPoints[index] = xPoint;
		}
		catch(IndexOutOfBoundsException e){
			invalidIndexReturn();
		}
	}
	public void setYPoint(int index, double yPoint){
		try{
			yPoints[index] = yPoint;
		}
		catch(IndexOutOfBoundsException e){
			invalidIndexReturn();
		}
	}
	public void setAngle(double degree){
		rotateOnCenter(-angle);
		rotateOnCenter(degree);
	}
	public void setAngle(int index, double degree){
		rotateOnIndex(index,-angle);
		rotateOnIndex(index,degree);
	}
	public void setAngle(double xCenter, double yCenter, double degree){
		rotatePolygon(xCenter,yCenter,-angle);
		rotatePolygon(xCenter,yCenter,degree);
	}
	//public actions
		//rotators
	public void rotateFigure(double degree){
		rotateOnCenter(degree);
	}
	public void rotateFigure(int index, double degree){
		rotateOnIndex(index,degree);
	}
	public void rotateFigure(double xCenter, double yCenter, double degree){
		rotateOnPoint(xCenter,yCenter,degree);
	}
	public void rotateOnPoint(double xCenter, double yCenter, double degree){
		rotatePolygon(xCenter,yCenter,degree);
	}
	public void rotateOnIndex(int index, double degree){
		try{
			double xCenter = xPoints[index];
			double yCenter = yPoints[index];
			rotatePolygon(xCenter,yCenter,degree);
		}
		catch(IndexOutOfBoundsException e){
			invalidIndexReturn();
		}
	} 
	public void rotateOnCenter(double degree){
		double xCenter = getXCenter();
		double yCenter = getYCenter();
		rotatePolygon(xCenter,yCenter,degree);
	}
		//resizers
	public void resizeXAxis(double i, StretchingPoint sp){
		switch(sp){
		case LEFT->resizeXAxis(i,getXMin());
		case RIGHT->resizeXAxis(i,getXMax());	
		case CENTER->resizeXAxis(i,getXCenter());	
		}
	}
	public void resizeYAxis(double i, StretchingPoint sp){
		switch(sp){
		case UP->resizeYAxis(i,getYMin());
		case DOWN->resizeYAxis(i,getYMax());
		case CENTER->resizeYAxis(i,getYCenter());
		}
	}
	public void resizeXAxis(double i, double x){
		for (int j = 0; j < xPoints.length; j++){
			if (xPoints[j] != x) {
				xPoints[j] = extend(x,xPoints[j],i);
			}
		}
	}
	public void resizeYAxis(double i, double y){
		for (int j = 0; j < yPoints.length; j++){
			if (yPoints[j] != y) {
				yPoints[j] = extend(y,yPoints[j],i);
			}
		}
	}
	public void resizeFigure(double xi, double yi, double extX, double extY){
		resizeXAxis(xi,extX);
		resizeYAxis(yi,extY);
	}
	public void resizeFigure(double xi, double yi, StretchingPoint spx, StretchingPoint spy){
		resizeXAxis(xi,spx);
		resizeYAxis(yi,spy);
	}
	public void turnHorizontal(){
		resizeXAxis(-1,StretchingPoint.CENTER);
	}
	public void turnVertical(){
		resizeYAxis(-1,StretchingPoint.CENTER);
	}
		//movers
	public void moveFromCenter(double xDestiny, double yDestiny){
		movePolygon(xDestiny,yDestiny,getXCenter(),getYCenter());
	}
	public void moveFromIndex(double xDestiny, double yDestiny, int index){
		try{
			movePolygon(xDestiny,yDestiny,xPoints[index],yPoints[index]);
		}
		catch(IndexOutOfBoundsException e){
			invalidIndexReturn();
		}
	}
	public void moveFromPoint(double xDestiny, double yDestiny, double xOrigin, double yOrigin){
		movePolygon(xDestiny,yDestiny,xOrigin,yOrigin);
	}
	public void moveWithVector(double xVector, double yVector){
		for(int i = 0; i < xPoints.length; i++){
			xPoints[i] += xVector; 
			yPoints[i] += yVector; 
		}
	}
	// private actions
	private void rotatePolygon(double xCenter, double yCenter, double degree){
		angle = angle + degree;
		while(angle < 0){
			angle += 360;
		}
		angle = angle%360;
		double xCenterPoint, yCenterPoint, distance, radian;
		for (int i = 0;i < xPoints.length;i++){
			xCenterPoint = xPoints[i]-xCenter;
			yCenterPoint = yPoints[i]-yCenter;
			distance = Math.sqrt(Math.pow(xCenterPoint,2)+Math.pow(yCenterPoint,2));
			radian = Math.atan2(yCenterPoint,xCenterPoint) + Math.toRadians(degree);
			xCenterPoint = Math.cos(radian)*distance;
			yCenterPoint = Math.sin(radian)*distance;
			xPoints[i] = xCenterPoint + xCenter;
			yPoints[i] = yCenterPoint + yCenter;
		}
	}
	private void movePolygon(double xDestiny, double yDestiny, double xOrigin, double yOrigin){
		double xCenterPoint, yCenterPoint; 
		for (int i = 0; i < xPoints.length; i++){
			xCenterPoint = xPoints[i]-xOrigin;
			yCenterPoint = yPoints[i]-yOrigin;
			xPoints[i] = xCenterPoint + xDestiny;
			yPoints[i] = yCenterPoint + yDestiny;
		}
	}
	private double extend(double pointA, double pointB, double ext){
		double distance = pointB - pointA;
		double nDistance = distance*(ext-1);
		return pointB + nDistance;
	}
	private Point polygonProyection(Point point){
		double min = getPoint(0).pointProduct(point);
		double max = min;
		for (int i = 1; i < getLength(); i++) {
			double p = getPoint(i).pointProduct(point);
			if (p < min) {
                min = p;
            } 
			else if (p > max) {
                max = p;
            }
		}
		return new Point(min, max);
	}
	// takes a double array and returns it as an int array
	public int[] doubleToInt(double[] doublesArray){
    	int[] intArray = new int[doublesArray.length];
    	for (int i = 0; i < doublesArray.length; i++){
    		intArray[i] = (int)Math.round(doublesArray[i]);
    	}
    	return intArray;
    }
	// in case the argument index is invalid
	public double invalidIndexReturn(){
		System.out.println("Error: The index value is invalid");
		return 0;
	}
	public Color verifyColor(Color color){
		if(this.insideColor == null){
			this.insideColor = color;
			return color;
		}
		else if(color == null){
			return this.insideColor;
		}
		else{
			return color;
		}
	}
	//draws the figure
	@Override
    public void draw(Graphics2D g2d){
		g2d.setColor(verifyColor(borderColor));
		g2d.drawPolygon(roundXPoints(),roundYPoints(),getLength());
	}
	@Override
    public void fill(Graphics2D g2d){
		g2d.setColor(verifyColor(insideColor));
		g2d.fillPolygon(roundXPoints(),roundYPoints(),getLength());
	}
	//get the polygon in String
	@Override
    public String toString() {
    	String xString = "(" + xPoints[0];
    	String yString = "(" + yPoints[0];
    	for(int i = 1; i < xPoints.length; i++){
    		xString = xString + "," + xPoints[i];
    		yString = yString + "," + yPoints[i];
    	}
    	xString = xString + ")";
    	yString = yString + ")";
    	return "Polygon {" +
    		"xCoords = " + xString +
    		", yCoords = " + yString +
    		", angle = " + angle +
    		"}";
    }
	public boolean intersects(Point point){
		if(getLength() < 3){
			return false;
		}
		else{
			boolean result = false;
			int length = getLength();
			for (int i = 0, j = length-1; i < length; j = i++) {
				Point verticeI = getPoint(i);
				Point verticeJ = getPoint(j);
				if(verticeI.equals(point)){
					return true;
				}
				if(new Line(verticeI,verticeJ).onSegment(verticeI, verticeJ, point) == true){
					return true;
				}
				if ((verticeI.getY() > point.getY()) != (verticeJ.getY() > point.getY())) {
                	double xInterseccion = (verticeJ.getX() - verticeI.getX()) * (point.getY() - 
									verticeI.getY()) / (verticeJ.getY() - verticeI.getY()) + verticeI.getX();
					if (point.getX() <= xInterseccion) {
						result = !result;
					}
				}
			}
			return result;
		}
	}
	public boolean intersects(Line line){
		if(intersects(line.getFirstPoint()) || intersects(line.getSecondPoint())){
			return true;
		}
		for (int i = 0; i < getLength(); i++) {
			Line polygonLine = getLine(i);
			if(line.doIntersect(polygonLine) != null){
				return true;
			}
		}
		return false;
	}
	public Polygon intersection(Polygon polygon){
		Point centerPoints, p1_1, p1_2, p2_1, p2_2;
		LinkedList intersections = new LinkedList();
		for(int i = 0; i < polygon.getLength(); i++){
			p2_1 = polygon.getPoint(i);
			p2_2 = polygon.getPoint((i+1)%polygon.getLength());
			if(intersects(p2_1) == true){
				intersections.add(p2_1);
			}
			for(int j = 0; j < getLength();j++){
				p1_1 = getPoint(j);
				p1_2 = getPoint((j+1)%getLength());
				centerPoints = new Line(p1_1,p1_2).getIntersectionPoint(new Line(p2_1,p2_2));
				if(centerPoints != null){
					intersections.add(centerPoints);
					/*if(polygon.intersects(p1_2) == true){
						intersections.add(p1_2);
					}*/
				}
			}
		}
		if (intersections.isEmpty()) {
			return null;
		}
		else{
			Point[] points = new Point[intersections.getQuantity()];
			for (int index = 0; index < points.length; index++) {
				points[index] = (Point)intersections.get(index);
			}
			return new Polygon(points);
		}
	}
	public boolean intersects(Polygon polygon){
		for (int index = 0; index < getLength(); index++) {
			Line line = getLine(index);
			Point normal = line.getNorm();
			Point proyA = polygonProyection(normal);
			Point proyB = polygon.polygonProyection(normal);
			if(!proyA.overlaps(proyB)){
				return false;
			}
		}
		for (int index = 0; index < polygon.getLength(); index++) {
			Line line = polygon.getLine(index);
			Point normal = line.getNorm();
			Point proyA = polygonProyection(normal);
			Point proyB = polygon.polygonProyection(normal);
			if(!proyA.overlaps(proyB)){
				return false;
			}
		}
		return true;
	}
    @Override
    public boolean intersects(IEntity entity) {
            switch (entity) {
                case Polygon polygon -> {
                    return intersects(polygon);
                }
                case Point point -> {
                    return intersects(point);
                }
                case Line line -> {
                    return intersects(line);
                }
                case Oval oval -> {
                    return oval.intersects(this);
                }
                case Rectangle rectangle -> {
                    return intersects(rectangle.toPolygon());
                }
                default -> {
                    return false;
                }
            }
    }

    @Override
    public void move(double x, double y) {
        moveWithVector(x, y);
    }
}