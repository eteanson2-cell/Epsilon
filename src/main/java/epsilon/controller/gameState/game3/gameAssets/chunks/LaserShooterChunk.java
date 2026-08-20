package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.objectToDouble;

public class LaserShooterChunk extends ObstacleChunk{
    public LaserShooterChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        double xCenter = 320;
        double yCenter = height;
        rockPoints.add(new Point(xCenter-160,height));
        rockPoints.add(new Point(xCenter+160,height));
        int columns = setColumns();
        int sides = 8;
        double radix = 25;
        LinkedList keys = new LinkedList();
        for (int i = 1; i <= sides; i++) {
            double angle1 = (360/sides)*(i-1);
            double angle2 = (360/sides)*i;
            Line newLaser = new Line(xCenter + (degreeCosine(angle1)*radix), 
                                     yCenter + (degreeSine(angle1)*radix), 
                                     xCenter + (degreeCosine(angle2)*radix), 
                                     yCenter + (degreeSine(angle2)*radix));
            keys.add(newLaser);
        }
        double minAngle = Math.atan2(-height, -xCenter);
        double maxAngle = Math.atan2(-height, +xCenter);
        NumericArray angles = generateAngles(minAngle, maxAngle, columns);
        keys.add(new Line(xCenter-15, height-60, xCenter+15, height-60));
        keys.add(new Line(xCenter-15, height-60, xCenter-15, height-20));
        keys.add(new Line(xCenter+15, height-60, xCenter+15, height-20));
        for (int i = 0; i < height; i += 100) {
            Number currentAngle = (Number)angles.get(seed%columns);
            double currentXPoint = (height-i) * Math.cos(currentAngle.doubleValue());
            rockPoints.add(new Point(320 + currentXPoint, i));
            int prevSeed = seed%columns;
            do { 
                reRollSeed();
            } while (prevSeed == seed%columns);
            
        }
        Line blast = new Line(320,height,320,height);
        lasers.addKey(blast);
        double prevAngle = 0;
        int speed = 60;
        while (angles.isEmpty() == false) { 
            double angle = Math.toDegrees(objectToDouble(angles.remove(seed%angles.size()))) + 90;
            double fixedAngle = angle-prevAngle;
            keys.iterateList((Object nodeObject) -> {
                lasers.addKey(nodeObject);
                LaserMovement lm1 = new LaserMovement(null, null, false, 
                    new Point(xCenter,yCenter), new Point(xCenter,yCenter), 
                    fixedAngle, fixedAngle
                );
                lasers.addObject(lm1, nodeObject);
                lasers.addObject(speed, nodeObject);
                if(angles.isEmpty() == true){
                    lasers.addObject(new LaserMovement(null, null, false,
                        new Point(xCenter,yCenter), new Point(xCenter,yCenter), 
                        -angle, -angle
                    ), nodeObject);
                }
            });
            prevAngle = angle;
            configureBlast(-degreeCosine(angle-90)*(height/speed), blast, speed);
        }
        lasers.addObject(1, blast);
    }
    public void configureBlast(double xVector, Line blast, int speed){
        lasers.addObject(
            new LaserMovement(
                new Point(xVector*10,(height*10)/speed), 
                null, 
                true, null, null, null, null), 
            blast
        );
        lasers.addObject(
            new LaserMovement(
                new Point(xVector,height/speed), 
                new Point(xVector,height/speed), 
                true, null, null, null, null), 
            blast
        );
        lasers.addObject(10-speed, blast);
        lasers.addObject(
            new LaserMovement(
                null, 
                new Point(xVector,height/speed),
                 true, null, null, null, null), 
            blast
        );
        lasers.addObject(-7, blast);
        lasers.addObject(
            new LaserMovement(
                new Point(320,height), 
                new Point(320,height), 
                false, null, null, null, null), 
            blast
        );
    }
    public void setHeight(){
        height = 800;
        if(benchmark < -9000){
            height = ((int)benchmark/-1000)*100;
            if(height > 2000){
                height = 2000;
            }
        }
    }
    public int setColumns(){
        int columns = 3;
        if(benchmark < -6000){
            columns -= benchmark/6000;
            if(columns > 10){
                columns = 10;
            }
        }
        return columns;
    }
    public NumericArray generateAngles(double minAngle, double maxAngle, int columns){
        NumericArray angles = new NumericArray(columns);
        double increment = (maxAngle-minAngle)/(columns-1);
        for (int i = 0; i < columns; i++) {
            angles.add(minAngle + (increment*i));
        }
        return angles;
    }
}