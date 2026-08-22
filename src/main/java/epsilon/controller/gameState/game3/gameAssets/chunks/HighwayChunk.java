package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class HighwayChunk extends ObstacleChunk{

    public HighwayChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        int increment = setIncrement();
        for (int y = 0; y <= height; y += height/2) {
            for (int i = 100; i <= 540; i += increment) {
                rockPoints.add(new Point(i, y));
            }
            for (int i = 100-(increment/2); i < 640; i += increment) {
                addCube(new Point(i,y));
            }
        }
        
        for (int y = 25; y < (height/2)-24; y+= 25) {
            addCar(new Point(getX(), y), true);
            reRollSeed();
        }
        for (int y = (height/2) + 25; y < height-24; y+= 25) {
            addCar(new Point(getX(), y), false);
            reRollSeed();
        }
    }
    public int setIncrement(){
        int width = 440;
        int rocks = 6;
        if(benchmark < -10000){
            rocks += (int)(benchmark/10000);
            if(rocks < 3){
                rocks = 3;
            }
        }
        int increment = width/(rocks-1);
        return increment;
    }
    public int getX(){
        int x = (int)(seed*6.4);
        x -= x%10;
        return x;
    }
    public void setHeight(){
        height = 150;
        if(benchmark <-7500){
            height -= 50 * (int)(benchmark/7500);
            if(height > 650){
                height = 650;
            }
        }
    }
    public void addCube(Point center){
        int radix = 15;
        for (int i = 0; i < 4; i++) {
            int angle = i*90;
            lasers.addKey(new Line(
                center.getX() + (radix*degreeCosine(angle-45)), 
                center.getY() + (radix*degreeSine(angle-45)), 
                center.getX() + (radix*degreeCosine(angle+45)), 
                center.getY() + (radix*degreeSine(angle+45))
            ));
        }
    }
    public void addCar(Point center, boolean right){
        double xCenter = center.getX();
        double yCenter = center.getY();
        for (int i = -1; i < 3; i+=2) {
            int xFixer = 20*i;
            int yFixer = 10*i;
            Line horizontalLine = new Line(xCenter+xFixer, yCenter+yFixer, xCenter-xFixer, yCenter+yFixer);
            Line verticalLine = new Line(xCenter+xFixer, yCenter+yFixer, xCenter+xFixer, yCenter-yFixer);
            addMovement(horizontalLine, center, right);
            addMovement(verticalLine, center, right);
        }
        
    }
    public void addMovement(Line line, Point center, boolean right){
        lasers.addKey(line);
        int fixer;
        int speed = 5;
        int toRight = 660-(int)center.getX();
        int toLeft = (int)center.getX()+20;
        int toEdge;
        int toOrigin;
        if (right) {
            fixer = 1;
            toEdge = toRight;
            toOrigin = toLeft;
        }
        else{
            fixer = -1;
            toEdge = toLeft;
            toOrigin = toRight;
        }
        LaserMovement mv = new LaserMovement(
            new Point(speed*fixer,0), new Point(speed*fixer,0), true, null, null, null, null
        );
        lasers.addObject(mv, line);
        
        LaserMovement reset = new LaserMovement(
            new Point(-680*fixer, 0), new Point(-680*fixer, 0), true, null, null, null, null
        );
        lasers.addObject(1-(toEdge/speed), line);
        lasers.addObject(reset, line);
        lasers.addObject(mv, line);
        lasers.addObject(1-(toOrigin/speed), line);
    }
}