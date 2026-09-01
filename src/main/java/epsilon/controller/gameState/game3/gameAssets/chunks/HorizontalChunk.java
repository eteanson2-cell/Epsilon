package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class HorizontalChunk extends ObstacleChunk{

    public HorizontalChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        double initX = getInitX();
        double lastX = 640-initX;
        double middleY = height/2;
        double xDiff = 550;
        double xOffscreen;
        if(initX < 320){
            xOffscreen = 640;
        }
        else{
            xOffscreen = 0;
        }
        double xLaserEdge = getXLaserEdge(initX);
        int thickness = getThickness();
        rockPoints.add(new Point(initX, 0));
        rockPoints.add(new Point(initX, middleY));
        rockPoints.add(new Point(lastX, middleY));
        rockPoints.add(new Point(lastX, height));
        lasers.addKey(new Line(initX+xLaserEdge, middleY-thickness, 
                                xOffscreen, middleY-thickness));
        lasers.addKey(new Line(lastX-xLaserEdge, middleY+thickness, 
                                640-xOffscreen, middleY+thickness));
        double numObstacles = getNumObstacle();
        double spliter = xDiff/(numObstacles+1);
        boolean prevTripleCircle = false;
        double rotation = 1;
        for (int i = 1; i < numObstacles+1; i++) {
            double xObstacle = spliter*i;
            reRollSeed();
            if(seed < 33){
                if(prevTripleCircle == true){
                    if(rotation < 0){
                        seed = seed%3 + 8;
                    }
                    else{
                        seed = seed%8;
                    }
                }
                rotation = generateCircle(new Point(50 + xObstacle, middleY));
                if(benchmark < -10000){
                    prevTripleCircle = true;
                }
            }
            else if(seed > 66){
                generateVerticalDoor(new Point(50 + xObstacle, middleY), thickness);
            }
            else{
                generateSpinningRod(new Point(50 + xObstacle, middleY), thickness);
            }

        }

    }
    protected void setHeight(){
        height = 300;
    }
    protected double getInitX(){
        double initX;
        if(seed < 50){
            initX = 50;
        }
        else{
            initX = 590;
        }
        return initX;
    }
    protected int getThickness(){
        int thickness = 125;
        if(benchmark < -500){
            thickness += benchmark/500;
            if(thickness < 20){
                thickness = 20;
            }
        }
        return thickness;
    }
    protected double getXLaserEdge(double initX){
        double xLaserEdge = 50;
        if(benchmark < -1000){
            xLaserEdge += benchmark/1000;
            if(xLaserEdge < 0){
                xLaserEdge = 0;
            }
        }
        if(initX > 320){
            xLaserEdge = -xLaserEdge;
        }
        return xLaserEdge;
    }
    protected double getNumObstacle(){
        int numObstacles = 1;
        if(benchmark < -3000){
            numObstacles = (int)(benchmark/-3000);
            if(numObstacles > 20){
                numObstacles = 20;
            }
        }
        return numObstacles;
    }
    protected void generateVerticalDoor(Point center, int thickness){
        reRollSeed();
        double speed = 75;
        double x = center.getX();
        double y1 = center.getY()-thickness;
        double y2 = center.getY()+thickness;
        Line newLaser1 = new Line(x,y1,x,y2);
        LaserMovement lm1;
        LaserMovement lm2;
        if(seed < 33){
            lm1 = new LaserMovement(
                new Point(0, ((double)-thickness*2)/speed), null, true, null, null, null, null
            );
            lm2 = new LaserMovement(
                new Point(0, ((double)thickness*2)/speed), null, true, null, null, null, null
            );
        }
        else if(seed > 66){
            lm1 = new LaserMovement(
                null, new Point(0, ((double)thickness*2)/speed), true, null, null, null, null
            );
            lm2 = new LaserMovement(
                null, new Point(0, ((double)-thickness*2)/speed), true, null, null, null, null
            );
        }
        else{
            newLaser1.setSecondY(center.getY());
            lm1 = new LaserMovement(
                null, new Point(0, ((double)thickness)/speed), true, null, null, null, null
            );
            lm2 = new LaserMovement(
                null, new Point(0, ((double)-thickness)/speed), true, null, null, null, null
            );
            LinkedList movements2 = setMovementsList(lm2, lm1, (int)speed);
            Line newLaser2 = new Line(x, y2, x, center.getY()-1);
            lasers.addKey(newLaser2);
            lasers.replaceData(movements2, newLaser2);
        }
        LinkedList movements1 = setMovementsList(lm1, lm2, (int)speed);
        lasers.addKey(newLaser1);
        lasers.replaceData(movements1, newLaser1);
    }
    protected LinkedList setMovementsList(LaserMovement lm1, LaserMovement lm2, int speed){
        LinkedList movements = new LinkedList();
        movements.add(lm1);
        movements.add(1-speed);
        movements.add(lm2);
        movements.add(1-speed);
        return movements;
    }
    protected double generateCircle(Point center){
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
            Line laserLine;
            laserLine = new Line(
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
                new LaserMovement(null, null, false, p1, p2, rotation, rotation),laserLine
            );
            if(benchmark > -20000){
                rockPoints.add(center);
            }
        }
        return rotation;
    }
    protected void generateSpinningRod(Point center, int thickness){
        reRollSeed();
        double speed = 1.0;
        Line newLaser;
        double x = center.getX();
        if(seed <= 50){
            thickness = -thickness;
        }
        double y = center.getY()+thickness;
        newLaser = new Line(x, y, x+thickness, y);
        lasers.addKey(newLaser);
        lasers.addObject(new LaserMovement(null,null,false, null, null, null, speed), newLaser);
        lasers.addObject(1-Math.abs(180/speed), newLaser);
        lasers.addObject(new LaserMovement(null,null,false, null, null, null, -speed), newLaser);
        lasers.addObject(1-Math.abs(180/speed), newLaser);
    }
}