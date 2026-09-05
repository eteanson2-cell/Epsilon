package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.euclideanDistance;
import static epsilon.utils.FunctionUtils.getSign;
import static epsilon.utils.FunctionUtils.isInRange;

public class PinballChunk extends ObstacleChunk{

    public PinballChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }
    @Override
    public void init() {
        setHeight();
        rockPoints.add(new Point(320, 0));
        rockPoints.add(new Point(320, 325));
        Point plp = new Point(); 
        Point prp = new Point(); 
        for(int i = -1; i < 2; i+=2){
            generateFlipper(new Point(320+(i*150), 100), 20, i<0);
            double angle = 90;
            double length1 = 180;
            double length2 = 125;
            Point p1 = new Point(320+(i*150), 40);
            Point p2 = new Point((320+(i*160)),90);
            Point p3 = new Point(p1.getX() + (degreeCosine(angle-(60*i))*length1), 
                p1.getY() + (degreeSine(angle-(60*i))*length1));
            Point p4 = new Point(p2.getX() + (degreeCosine(angle-(60*i))*length2), 
                p2.getY() + (degreeSine(angle-(60*i))*length2));
            Point p5 = new Point(p3.getX(), p3.getY() + 200);
            if(i < 0){
                plp = p5;
            }else{
                prp = p5;
            }
            Point p6 = new Point(p4.getX(), p4.getY() + 100);
            Point[] pts = {p1, p2, p3, p4, p5, p6};
            for (int j = 0; j < pts.length-2; j++) {
                Line newline = new Line(pts[j], pts[j+2]);
                lasers.addKey(newline);
            }
            lasers.addKey(new Line(p4.getX(), p4.getY(), 320+(i*150),120));
            rockPoints.add(new Point(320+(i*285), 145));
            rockPoints.add(new Point(320+(i*285), 300));
        }
        if(benchmark < -5000){
            generatePipeball(new Point(320, 300), new Point(320, 50), 10, 100);
            Point pl = new Point(300, 300);
            Point pr = new Point(340, 300);
            Point pl2 = new Point(300-25, 350);
            Point pr2 = new Point(340+25, 350);
            lasers.addKey(new Line(pl,pr));
            lasers.addKey(new Line(pl, pl2));
            lasers.addKey(new Line(pr, pr2));
            lasers.addKey(new Line(pl2,pr2));
        }
        int increment = 100;
        Point prevLPoint = plp;
        Point prevRPoint = prp;
        for (int y = 500; y <= height; y+=increment) {
            Point nextLPoint;
            Point nextRPoint;
            int loopCounter = 0;
            do{
                double angle1 = 45 + seed*0.9;
                double angle2 = 135 - (seed%91);
                int modBenchmark = ((int)benchmark)%seed;
                if(modBenchmark < 51){
                    angle1 = 135 - (seed%91);
                    angle2 = 45 + seed*0.9;
                }                
                reRollSeed();
                nextLPoint = new Point(prevLPoint.getX() + degreeCosine(angle1)*increment, y);
                nextRPoint = new Point(prevRPoint.getX() + degreeCosine(angle2)*increment, y);
                loopCounter++;
                if(loopCounter > 100){
                    nextLPoint = new Point(prevLPoint.getX(), y);
                    nextRPoint = new Point(prevRPoint.getX(), y);
                    seed = 3;
                    break;
                }
            }while(isInRange( 10,310,nextLPoint.getX()) == false || 
                   isInRange(330,630,nextRPoint.getX()) == false);
            reRollSeed();
            double distancePoint = nextRPoint.getX() - nextLPoint.getX();
            Point centerPoint = new Point(nextLPoint.getX() + (distancePoint/2), y);
            if(distancePoint < 350){
                generateSpinningBumpers(centerPoint, getBumpers(), 50, seed<50);
                y += 100;
                rockPoints.add(centerPoint);
            }
            else if(distancePoint > 450){
                Point origin = new Point();
                Point destination = new Point();
                switch(seed%4){
                    case 0 -> {
                        origin = nextLPoint.copy();
                        reRollSeed();
                        switch(seed%2){
                            case 0 -> {
                                destination = nextRPoint.copy();
                            }
                            case 1 -> {
                                destination = prevRPoint.copy();
                            }
                        }
                    }
                    case 1 -> {
                        origin = nextRPoint.copy();
                        reRollSeed();
                        switch(seed%2){
                            case 0 -> {
                                destination = nextLPoint.copy();
                            }
                            case 1 -> {
                                destination = prevLPoint.copy();
                            }
                        }
                    }
                    case 2 -> {
                        origin = prevLPoint.copy();
                        destination = nextRPoint.copy();
                    }
                    case 3 -> {
                        origin = prevRPoint.copy();
                        destination = nextLPoint.copy();
                    }
                }
                generatePipeball(origin, destination, 10, getBallDelay());
                double orientation = getSign(origin.getX()-320);
                for (int i = -1; i < 2; i+=2) {
                    Point p1 = new Point(origin.getX(), origin.getY()+(20*i));
                    Point p2 = new Point(destination.getX(), destination.getY()+(20*i));
                    lasers.addKey(new Line(
                        p1, new Point(320 + (orientation * 350), origin.getY()+(20*i)))
                    );
                    lasers.addKey(new Line(
                        p2, new Point(320 + (-orientation * 350), destination.getY()+(20*i)))
                    );
                    lasers.addKey(new Line(p1,origin));
                    lasers.addKey(new Line(p2,destination));
                }
                rockPoints.add(centerPoint);
            }
            else{
                reRollSeed();
                int delay = (int)(seed*0.5);
                switch(seed%3){
                    case 0 -> {
                        generateFlipper(new Point(nextLPoint.getX()+20, y), delay, true);
                        rockPoints.add(new Point(nextLPoint.getX()-20, y));
                    }
                    case 1 -> {
                        generateFlipper(new Point(nextRPoint.getX()-20, y), delay, false);
                        rockPoints.add(new Point(nextRPoint.getX()+20, y));
                    }
                    case 2 -> {
                        generateFlipper(new Point(nextLPoint.getX()+20, y), delay, true);
                        generateFlipper(new Point(nextRPoint.getX()-20, y), delay, false);
                        rockPoints.add(new Point(nextLPoint.getX()-20, y));
                        rockPoints.add(new Point(nextRPoint.getX()+20, y));
                    }
                }
            }
            lasers.addKey(new Line(prevLPoint, nextLPoint));
            lasers.addKey(new Line(prevRPoint, nextRPoint));
            prevLPoint = nextLPoint;
            prevRPoint = nextRPoint;

        }
    }
    public int getBumpers(){
        int bumpers = 1;
        if(benchmark < -10000){
            bumpers -= benchmark/10000;
            if(bumpers > 4){
                bumpers = 4;
            }
        }
        return bumpers;
    }
    public int getBallDelay(){
        int delay = 50;
        if(benchmark < -1000){
            delay -= benchmark/1000;
            if(delay < 20){
                delay = 20;
            }
        }
        return delay;
    }
    public void setHeight(){
        height = 400;
        if(benchmark < -2000){
            height -= (benchmark/2000)*100;
        }
    }
    public void generateFlipper(Point center, int delay, boolean orientation){
        double xCenter = center.getX();
        double yCenter = center.getY();
        double flipperLength = 100;
        int fixer = -1;
        if(orientation){
            fixer = 1;
        }
        Point flipperEdge = new Point(xCenter + (fixer*flipperLength), yCenter-20);
        Line[] newLines = new Line[3];
        newLines[0] = new Line(new Point(xCenter, yCenter+20), flipperEdge);
        newLines[1] = new Line(new Point(xCenter-(fixer*10), yCenter-10), flipperEdge);
        newLines[2] = new Line(xCenter-(fixer*10), yCenter-10, xCenter, yCenter+20);
        double speed = 4;
        for (Line newLine : newLines) {
            lasers.addKey(newLine);
            lasers.addObject(
                new LaserMovement(null, null, false, center.copy(), center.copy(), 
                    -speed*fixer, -speed*fixer), newLine
            );
            lasers.addObject(-10,newLine);
            lasers.addObject(
                new LaserMovement(null, null, false, center.copy(), center.copy(), 
                    speed*fixer, speed*fixer), newLine
            );
            lasers.addObject(-10, newLine);
            lasers.addObject(delay, newLine);
        }
    }
    public void generatePipeball(Point origin, Point destination, double speed, int delay){
        Line[] ball = new Line[8];
        int radix = 10;
        double inc = 360/ball.length;
        for (int i = 0; i < ball.length; i++) {
            double angle = inc*i;
            Point tempPoint = new Point(
                origin.getX() + degreeCosine(angle)*radix, 
                origin.getY() + degreeSine(angle)*radix
            );
            ball[i] = new Line(tempPoint.copy(), tempPoint.copy());
            lasers.addKey(ball[i]);
        }
        double ang = origin.getAngle(destination);
        double xSpeed = speed*degreeCosine(ang);
        double ySpeed = speed*degreeSine(ang);
        double mps = euclideanDistance(origin, destination)/speed;
        for (int i = 0; i < ball.length; i++) {
            double angle = inc*i;
            double nextAngle = inc*(i+1);
            lasers.addObject(
                new LaserMovement(
                    new Point(
                        origin.getX() + degreeCosine(angle)*radix, 
                        origin.getY() + degreeSine(angle)*radix), 
                    new Point(
                        origin.getX() + degreeCosine(nextAngle)*radix, 
                        origin.getY() + degreeSine(nextAngle)*radix), 
                    false, null, null, null, null), 
                ball[i]
            );
            lasers.addObject(true, ball[i]);
            lasers.addObject(
                new LaserMovement(new Point(xSpeed,-ySpeed), new Point(xSpeed,-ySpeed), true, 
                    null, null, null, null), 
                ball[i]
            );
            lasers.addObject(1-mps, ball[i]);
            lasers.addObject(false, ball[i]);
            lasers.addObject(delay, ball[i]);
        }
    }
    public void generateSpinningBumpers(Point center, int bumpers, double radix, boolean orientation){
        double fixer = 1;
        if(orientation){
            fixer = -fixer;
        }
        double inc = 360/bumpers;
        for (int i = 0; i < bumpers; i++) {
            double angle = inc*i;
            Point bumperPoint = new Point(
                center.getX() + degreeCosine(angle)*radix, 
                center.getY() + degreeSine(angle)*radix
            );
            int sides = 6;
            double sideInc = 360/sides;
            for (int j = 0; j < sides; j++) {
                double radix2 = 20;
                Line bumperLine = new Line(
                    new Point(bumperPoint.getX() + degreeCosine(j*sideInc)*radix2, 
                              bumperPoint.getY() + degreeSine(j*sideInc)*radix2),
                    new Point(bumperPoint.getX() + degreeCosine((j+1)*sideInc)*radix2, 
                              bumperPoint.getY() + degreeSine((j+1)*sideInc)*radix2)
                );
                lasers.addKey(bumperLine);
                lasers.addObject(
                    new LaserMovement(null, null, false, center.copy(), center.copy(), 
                        fixer, fixer), 
                    bumperLine
                );
            }
            
        }
    }
}