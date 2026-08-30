package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.Polygon;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class LaserBarrier{
    private final Point laserPointA;
    private final Point laserPointB;
    private boolean isActive;
    private DynamicQueue laserMovements;
    private LaserMovement lastMovement;
    private int numberCommand;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LaserBarrier(double x1, double y1, double x2, double y2){
        laserPointA = new Point(x1, y1);
        laserPointB = new Point(x2, y2);
        init();
    }
    public void init(){
        numberCommand = 0;
        laserMovements = new DynamicQueue();
        lastMovement = null;
        isActive = true;
    }
    public Point getPointA(){
        return laserPointA;
    }
    public Point getPointB(){
        return laserPointB;
    }
    public void moveAPoint(double x, double y){
        laserPointA.move(x, y);
    }
    public void moveBPoint(double x, double y){
        laserPointB.move(x, y);
    }
    public void moveAPointTo(double x, double y){
        laserPointA.moveTo(x, y);
    }
    public void moveBPointTo(double x, double y){
        laserPointB.moveTo(x, y);
    }
    public void rotateAfromB(double theta){
        rotateAfrom(laserPointB.getX(), laserPointB.getY(), theta);
    }
    public void rotateBfromA(double theta){
        rotateBfrom(laserPointA.getX(), laserPointA.getY(), theta);
    }
    public void rotateAfrom(double x, double y, double theta){
        double yDistance = laserPointA.getY()-y;
        double xDistance = laserPointA.getX()-x;
        double distance = Math.sqrt(Math.pow(yDistance,2) + Math.pow(xDistance, 2));
        double angle = Math.atan2(yDistance, xDistance);
        angle += Math.toRadians(theta);
        laserPointA.moveTo((distance*Math.cos(angle))+x, 
                           (distance*Math.sin(angle))+y);
    }
    public void rotateBfrom(double x, double y, double theta){
        double yDistance = laserPointB.getY()-y;
        double xDistance = laserPointB.getX()-x;
        double distance = Math.sqrt(Math.pow(yDistance,2) + Math.pow(xDistance, 2));
        double angle = Math.atan2(yDistance, xDistance);
        angle += Math.toRadians(theta);
        laserPointB.moveTo((distance*Math.cos(angle))+x, 
                           (distance*Math.sin(angle))+y);
    }
    public void addNumber(int number){
        if(number != 0){
            laserMovements.add(number);
        }
    }
    public void addMovement(LaserMovement laserMovement){
        laserMovements.add(laserMovement);
    }
    public void addBoolean(boolean bool){
        laserMovements.add(bool);
    }
    public Line getLine(){
        return new Line(laserPointA, laserPointB);
    }
    public void update(){
        if(laserMovements.isEmpty() == false){
            if(numberCommand > 0){
                numberCommand--;
            }
            else if(numberCommand < 0){
                if(lastMovement != null){
                    lastMovement.executeAll(this);
                }
                numberCommand++;
            }
            else{
                Object queueObject = laserMovements.remove();
                switch (queueObject) {
                    case LaserMovement laserMovement -> {
                        lastMovement = laserMovement;
                        laserMovement.executeAll(this);
                    }
                    case Integer integer -> {
                        numberCommand = integer;
                        update();
                    }
                    case Boolean bool -> {
                        isActive = bool;
                    }
                    default -> {
                    }
                }
                laserMovements.add(queueObject);
            }
        }
    }
    public DynamicQueue getMovements(){
        return laserMovements;
    }
    public boolean hasMovements(){
        return !laserMovements.isEmpty();
    }
    public boolean isActive(){
        return isActive;
    }
    public void draw(Graphics2D g2d, LaserBarrier killerLaser){
        Line laserLine = getLine();
        if(isActive){
            Point intersectionPoint = laserLine.getIntersectionPoint(killerLaser.getLine());
            if(intersectionPoint != null){
                Point highestPoint;
                if(laserPointA.getY() < laserPointB.getY()){
                    highestPoint = laserPointA;
                }
                else{
                    highestPoint = laserPointB;
                }
                Line cutLine = new Line(
                    highestPoint.getX(), highestPoint.getY(), 
                    intersectionPoint.getX(), intersectionPoint.getY()
                );
                draw(g2d, cutLine);
            }
            else{
                draw(g2d, laserLine);
            }
        }
    }
    public void draw(Graphics2D g2d, Line mainLine){
        double angle = Math.toDegrees(mainLine.getAngle());
        g2d.setColor(new Color(0, 255, 0));
        mainLine.draw(g2d);
        //int alpha = 255;
        Array points = new Array(4);
        for (int i = 1; i <= 5; i++) {
            for (int j = -1; j < 2; j+=2) {
                Point pointA = new Point(
                    mainLine.getFirstX()+degreeCosine(angle+(j*90))*i, 
                    mainLine.getFirstY()+degreeSine(angle+(j*90))*i);
                Point pointB = new Point(
                    mainLine.getSecondX()+degreeCosine(angle+(j*90))*i, 
                    mainLine.getSecondY()+degreeSine(angle+(j*90))*i);
                if(j < 0){
                    points.add(pointA);
                    points.add(pointB);
                }
                else{
                    points.add(pointB);
                    points.add(pointA);
                }
            }
            if(points.isFilled()){
                Point[] pts = new Point[4];
                for (int j = 0; j < points.size(); j++) {
                    pts[j] = (Point)points.get(j);
                }
                Polygon grossLine = new Polygon(pts);
                grossLine.setInsideColor(new Color(0, 255, 0, 255/5));
                grossLine.fill(g2d);
                points.clear();
            }
        }
    }
    public void draw(Graphics2D g2d){
        draw(g2d, getLine());
    }
}