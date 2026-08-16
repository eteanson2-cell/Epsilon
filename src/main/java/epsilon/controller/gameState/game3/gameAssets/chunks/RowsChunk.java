package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;

public class RowsChunk extends ObstacleChunk{

    public RowsChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        double numSpan = getLaserSpan();
        int increment = getIncrement();
        int width = getWidth();
        int angle = generateAngle();
        for (int y = 0; y < height ; y += increment) {
            double xPoint = getXPoint(y, angle);
            Point newRock = new Point(xPoint, y);
            rockPoints.add(newRock);
        }
        for (int y = 50; y < height; y += numSpan) {
            double xPoint = getXPoint(y, angle);
            Line newLaser1 = new Line(0, y, xPoint-width, y);
            Line newLaser2 = new Line(xPoint+width, y, 640, y);
            lasers.addKey(newLaser1);
            lasers.addKey(newLaser2);
            if(benchmark < -2000 && (int)(Math.abs(benchmark)*seed*xPoint)%2 == 0){
                setLaserMovements(width, newLaser1, newLaser2);
            }
        }
        rockPoints.add(new Point(getXPoint(height, angle),height));
    }
    protected int getIncrement(){
        int increment = (int)(-benchmark/50);
        if(increment < 100){
            increment = 100;
        }
        else if(increment > 300){
            increment = 300;
        }
        return increment;
    }
    protected void setHeight(){
        height = 300;
        if(benchmark < -3000){
            height = (int)(-benchmark/10);
            if(height > 2000){
            //    height = 2000;
            }
        }
    }
    protected int getWidth(){
        int width = 50;
        if(benchmark < -1000){
            width += (benchmark/1000)*2;
            if(width < 15){
                width = 15;
            }
        }
        return width;
    }
    protected double getLaserSpan(){
        double numSpan = 100;
        if(benchmark < -2500){
            numSpan += benchmark/2500;
            if(numSpan < 10){
                numSpan = 10;
            }
        }
        return numSpan;
    }
    protected double getXPoint(int y, int angle){
        double xPoint = 320 + ((y-(height/2))/Math.tan(Math.toRadians(angle)));
        /*if(xPoint < 5){
            xPoint = 5;
        }
        else if(xPoint > 635){
            xPoint = 635;
        }*/
        return xPoint;
    }
    protected int generateAngle(){
        int m;
        if(seed < 50){
            m = -35;
        }
        else{
            m = 35;
        }
        int v = 90 + (int)(Math.sin(Math.toRadians(benchmark/1000))*m);
        if(Math.abs((height/2)/Math.tan(Math.toRadians(v))) > 310){
            double radian = 90;
            if(m > 0){
                radian = Math.atan2(height/2, 310);
            }
            else if(m < 0){
                radian = Math.atan2(height/2, -310);
            }
            return (int)Math.round(Math.toDegrees(radian));
        }
        return v;
    }
    protected void setLaserMovements(int width, Line laser1, Line laser2){
        int speed = 45;
        if(seed < 33){
            LaserMovement lmv1 = new LaserMovement(null, 
                new Point(((double)width*2)/speed,0), true, null, null, null, null);
            LaserMovement lmv2 = new LaserMovement(null, 
                new Point(((double)-width*2)/speed,0), true, null, null, null, null);
            setUpList(laser1, lmv1, lmv2, speed);
        }
        else if (seed > 66){
            LaserMovement lmv1 = new LaserMovement( 
                new Point((double)-width*2/speed,0), null, true, null, null, null, null);
            LaserMovement lmv2 = new LaserMovement(
                new Point((double)width*2/speed,0), null, true, null, null, null, null);
            setUpList(laser2, lmv1, lmv2, speed);
        }
        else{
            LaserMovement lmv1 = new LaserMovement(null, 
                new Point(((double)width)/speed,0), true, null, null, null, null);
            LaserMovement lmv2 = new LaserMovement(null, 
                new Point(((double)-width)/speed,0), true, null, null, null, null);
            LaserMovement lmv3 = new LaserMovement( 
                new Point((double)-width/speed,0), null, true, null, null, null, null);
            LaserMovement lmv4 = new LaserMovement(
                new Point((double)width/speed,0), null, true, null, null, null, null);
            setUpList(laser1, lmv1, lmv2, speed);
            setUpList(laser2, lmv3, lmv4, speed);
        }
    }
    protected void setUpList(Line laser, LaserMovement lmv1, LaserMovement lmv2, int speed){
        lasers.addObject(lmv1, laser);
        lasers.addObject((-speed)+1, laser);
        lasers.addObject(lmv2, laser);
        lasers.addObject((-speed)+1, laser);
    }
}