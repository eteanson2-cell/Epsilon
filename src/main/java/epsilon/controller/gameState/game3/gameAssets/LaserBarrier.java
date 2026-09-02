package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.Polygon;

public class LaserBarrier{
    private final Point laserPointA;
    private final Point laserPointB;
    private boolean isActive;
    private DynamicQueue laserMovements;
    private LaserMovement lastMovement;
    private double angle;
    private int numberCommand;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LaserBarrier(double x1, double y1, double x2, double y2){
        laserPointA = new Point(x1, y1);
        laserPointB = new Point(x2, y2);
        init();
    }
    public void init(){
        numberCommand = 0;
        angle = getLine().getAngle();
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
        recalculateAngle();
    }
    public void moveBPoint(double x, double y){
        laserPointB.move(x, y);
        recalculateAngle();
    }
    public void moveAPointTo(double x, double y){
        laserPointA.moveTo(x, y);
        recalculateAngle();
    }
    public void moveBPointTo(double x, double y){
        laserPointB.moveTo(x, y);
        recalculateAngle();
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
        double tempAngle = Math.atan2(yDistance, xDistance);
        tempAngle += Math.toRadians(theta);
        laserPointA.moveTo((distance*Math.cos(tempAngle))+x, 
                           (distance*Math.sin(tempAngle))+y);
        recalculateAngle();
    }
    public void rotateBfrom(double x, double y, double theta){
        double yDistance = laserPointB.getY()-y;
        double xDistance = laserPointB.getX()-x;
        double distance = Math.sqrt(Math.pow(yDistance,2) + Math.pow(xDistance, 2));
        double tempAngle = Math.atan2(yDistance, xDistance);
        tempAngle += Math.toRadians(theta);
        laserPointB.moveTo((distance*Math.cos(tempAngle))+x, 
                           (distance*Math.sin(tempAngle))+y);
        recalculateAngle();
    }
    private void recalculateAngle(){
        double dy = laserPointB.getY() - laserPointA.getY();
        double dx = laserPointB.getX() - laserPointA.getX();
        if(dx == 0){
            dx = 0.00000000001;
        }
        double m = dy/dx;
        angle = Math.atan(m);
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
        g2d.setColor(new Color(0, 255, 0));
        mainLine.draw(g2d);
        for (int i = 1; i <= 5; i++) {
            Point pts[] = new Point[4];
            for (int j = -1; j < 2; j+=2) {
                double tempAngle = angle + (j*1.5708);
                double cos = i*Math.cos(tempAngle);
                double sin = i*Math.sin(tempAngle);
                Point pointA = new Point(
                    mainLine.getFirstX()+cos, 
                    mainLine.getFirstY()+sin);
                Point pointB = new Point(
                    mainLine.getSecondX()+cos, 
                    mainLine.getSecondY()+sin);
                if(j < 0){
                    pts[j+1] = pointA;
                    pts[j+2] = pointB;
                }
                else{
                    pts[j+1] = pointB;
                    pts[j+2] = pointA;
                }
            }
            Polygon grossLine = new Polygon(pts);
            grossLine.setInsideColor(new Color(0, 255, 0, 51));
            grossLine.fill(g2d);
        }
    }
    public void draw(Graphics2D g2d){
        draw(g2d, getLine());
    }
}