package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.isInRange;

public class LaserSystemChunk extends ObstacleChunk{

    public LaserSystemChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        double yCenter = height/2;
        double xCenter = 320;
        Point centerPoint = new Point(xCenter, yCenter);
        double radix = 50;
        int sides = 8;
        rockPoints.add(new Point(320, -50));
        for (int i = 1; i <= sides; i++) {
            double angle1 = (360/sides)*(i-1);
            double angle2 = (360/sides)*i;
            Line newLaser = new Line(xCenter + (degreeCosine(angle1)*radix), 
                                     yCenter + (degreeSine(angle1)*radix), 
                                     xCenter + (degreeCosine(angle2)*radix), 
                                     yCenter + (degreeSine(angle2)*radix));
            Point centerPoint1 = new Point(xCenter, yCenter);
            Point centerPoint2 = new Point(xCenter, yCenter);
            lasers.addKey(newLaser);
            lasers.addObject(
                new LaserMovement(null,null, false, centerPoint1, centerPoint2, 0.5, 0.5), newLaser
            );
        }
        int orbits = setOrbits();
        double prevAngle = 0;
        int loopCounter = 0;
        for (int i = 1; i <= orbits; i++) {
            reRollSeed();
            double angle = seed*1.8;
            radix = i*100;
            double x = xCenter + (degreeCosine(angle)*radix);
            double angleDiff = Math.abs(angle - prevAngle);
            if(isInRange(10,630,x) && angleDiff > 5){
                loopCounter = 0;
                prevAngle = angle;
                Point rockA = new Point(
                    xCenter + (degreeCosine(angle)*radix), 
                    yCenter + (degreeSine(angle)*radix));
                Point rockB = new Point(
                    xCenter + (degreeCosine(180+angle)*radix), 
                    yCenter + (degreeSine(180+angle)*radix));
                rockPoints.add(rockA);
                rockPoints.add(rockB);
                if(benchmark < -2000){
                    double angle2 = seed*3.6;
                    Point smallCenter = new Point(
                        xCenter + (degreeCosine(angle2)*radix), 
                        yCenter + (degreeSine(angle2)*radix)
                    );
                    generateCircle(centerPoint, smallCenter,1/Math.pow(i,0.25));
                }
            }
            else{
                i--;
                loopCounter++;
                if(loopCounter > 50){
                    prevAngle = 0;
                    seed = 7;
                }
            }
        }
    }
    public void setHeight(){
        height = 400;
        if(benchmark < -4000){
            height = (int)(benchmark/-1000)*100;
            if(height > 2200){
                height = 2200;
            }
        }
    }
    public int setOrbits(){
        return height/200;
    }
    public void generateCircle(Point bigCenter, Point smallCenter, double speed){
        int radix = 25;
        int numLasers = 6;
        double rotation;
        reRollSeed();
        if(seed < 50){
            rotation = -speed;
        }
        else{
            rotation = speed;
        }
        for (int i = 0; i < numLasers; i ++) {
            double degree = 360/numLasers;
            Line laserLine = new Line(
                smallCenter.getX() + (degreeCosine(degree*i)*radix), 
                smallCenter.getY() + (degreeSine(degree*i)*radix), 
                smallCenter.getX() + (degreeCosine(degree*(i+1))*radix), 
                smallCenter.getY() + (degreeSine(degree*(i+1))*radix));
            lasers.addKey(laserLine);
            Point p1 = new Point(bigCenter.getX(),bigCenter.getY());
            Point p2 = new Point(bigCenter.getX(),bigCenter.getY());
            lasers.addObject(
                new LaserMovement(null, null, false, p1, p2, rotation, rotation), laserLine
            );
        }
    }
}