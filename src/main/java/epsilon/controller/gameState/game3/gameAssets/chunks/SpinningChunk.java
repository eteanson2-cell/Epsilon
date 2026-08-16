package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.isInRange;

public class SpinningChunk extends ObstacleChunk{
    double xMinimum;
    double xMaximum;
    public SpinningChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        xMinimum = 40;
        xMaximum = 600;
        double fixer = getFixer();
        setHeight();
        double initX = xMinimum + ((double)seed)*5.6;
        lasers.addKey(new Line(-10, 0, initX-fixer, 0));
        lasers.addKey(new Line(initX+fixer, 0, 650, 0));
        int angle = getAngle(initX);
        int segments = getSegments();
        double lenght = height/(segments+1);
        rockPoints.add(new Point(initX, 0));
        
        for (int i = 0; i < segments; i++) {
            double xPoint = initX + (degreeCosine(angle)*lenght);
            int loopCounter = 0;
            while (isInRange(xMinimum,xMaximum,xPoint) == false) { 
                reRollSeed();
                angle = getAngle(initX);
                if(loopCounter > 100){
                    angle = 90;
                }
                xPoint = initX + (degreeCosine(angle)*lenght);
                loopCounter++;
            }
            Point newRock = new Point(xPoint,lenght*(i+1));
            rockPoints.add(newRock);
            double halfLenght = lenght/2;
            Point laserPoint = new Point(initX + (degreeCosine(angle)*halfLenght),lenght * ((double)i + 0.5));
            generateCircle(laserPoint);
            Point laserAP1 = new Point(initX-fixer, lenght*i);
            Point laserBP1 = new Point(initX+fixer, lenght*i);
            Point laserAP2 = new Point(xPoint-fixer, lenght*(i+1));
            Point laserBP2 = new Point(xPoint+fixer, lenght*(i+1));
            lasers.addKey(new Line(laserAP1, laserAP2));
            lasers.addKey(new Line(laserBP1, laserBP2));
            initX = xPoint;
            reRollSeed();
            angle = getAngle(initX);
        }
        generateCircle(new Point(initX,height));
        lasers.addKey(new Line(initX-fixer, lenght*segments, initX-fixer, height));
        lasers.addKey(new Line(initX+fixer, lenght*segments, initX+fixer, height));
        lasers.addKey(new Line(-10, height, initX-fixer, height));
        lasers.addKey(new Line(initX+fixer, height, 650, height));
        rockPoints.add(new Point(initX,height));
    }
    protected int getFixer(){
        int fixer = 100 + (int)(benchmark/500);
        if(fixer < 40){
            fixer = 40;
        }
        return fixer;
    }
    protected void setHeight(){
        height = 400;
        if(benchmark < -2000){
            height = 200*(getSegments()+1);
        }
    }
    protected int getAngle(double initX){
        int angle = 90;
        int m;
        if(seed < 50){
            m = -60;
        }
        else {
            m = 60;
        }
        angle += (int)(Math.sin(Math.toRadians((seed*benchmark)/1000))*m);
        if(angle > 90 && initX < xMinimum){
            angle -= 60;
        }
        else if(angle < 90 && initX > xMaximum){
            angle += 60;
        }
        return angle;
    }
    protected void generateCircle(Point center){
        int radix = 35;
        int numLasers = 1;
        if(benchmark < -5000){
            numLasers = (int)(benchmark/-5000) + 1;
            if(numLasers > 3){
                numLasers = 3;
            }
        }
        double rotation;
        reRollSeed();
        if(seed < 50){
            rotation = -1;
        }
        else{
            rotation = 1;
        }
        for (int i = 0; i < (numLasers*2); i += 2) {
            Line laserLine = new Line(
                center.getX() + (degreeCosine(60*i)*radix), 
                center.getY() + (degreeSine(60*i)*radix), 
                center.getX() + (degreeCosine(60*(i+1))*radix), 
                center.getY() + (degreeSine(60*(i+1))*radix));
            if(numLasers == 2 && i == 0){
                i++;
            }
            lasers.addKey(laserLine);
            Point p1 = new Point(center.getX(),center.getY());
            Point p2 = new Point(center.getX(),center.getY());
            lasers.addObject(
                new LaserMovement(null, null, false, p1, p2, rotation, rotation), laserLine
            );
            if(benchmark > -20000){
                rockPoints.add(center);
            }
        }
    }
    protected int getSegments(){
        int segments = 1;
        if(benchmark < -2000){
            segments -= benchmark/2000;
        }
        return segments;
    }
}