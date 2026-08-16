package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;

public class LaserBarrier{
    private final Point laserPointA;
    private final Point laserPointB;
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
    public void draw(Graphics2D g2d, LaserBarrier killerLaser){
        Line laserLine = getLine();
        Point intersectionPoint = laserLine.getIntersectionPoint(killerLaser.getLine());
        if(intersectionPoint != null){
            Point highestPoint;
            if(laserPointA.getY() < laserPointB.getY()){
                highestPoint = laserPointA;
            }
            else{
                highestPoint = laserPointB;
            }
            g2d.setColor(new Color(0, 255, 0));
            g2d.drawLine((int)highestPoint.getX(), (int)highestPoint.getY(), 
            (int)intersectionPoint.getX(), (int)intersectionPoint.getY());
        }
        else{
            draw(g2d);
        }
    }
    public void draw(Graphics2D g2d){
        Line laserLine = getLine();
        g2d.setColor(new Color(0, 255, 0));
        laserLine.draw(g2d);
    }
}