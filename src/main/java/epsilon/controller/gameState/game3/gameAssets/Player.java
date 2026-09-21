package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.Queue;
import epsilon.model.entities.figures.Oval;
import epsilon.model.entities.figures.ParticleSpawn;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.Polygon;
import epsilon.model.entities.figures.Rectangle;
import static epsilon.utils.FunctionUtils.euclideanDistance;
import static epsilon.utils.FunctionUtils.getMax;
import static epsilon.utils.FunctionUtils.isInRange;

public class Player{
    int maxSpeed;
    double deceleration;
    public Oval circle;
    public Point center;
    public double speedx;
    public double speedy;
    private Queue traces;
    protected ParticleSpawn particles;
    int xlimit;
    MetallicRock hookedRock;
    double maxHeight;
    public Player(){
        init();
    }
    public final void init(){
        circle = new Oval(0, 0, 8);
        center = new Point(0,0);
        traces = new Queue(10);
        speedx = 0;
        speedy = 0;
        hookedRock = null;
        maxSpeed = 5;
        deceleration = 0.1;
        xlimit = 640;
        maxHeight = 0;
        circle.setInsideColor(new Color(0, 0, 255));
        Rectangle rect = new Rectangle(0, 0, 5, 5);
        rect.setInsideColor(new Color(0, 255, 255, 192));
        particles = new ParticleSpawn(
            new Point(0,359), 0, 0, center, 10, rect, 1, 2
        );
    }
    public double getMagnitude(){
        return Math.sqrt(Math.pow(speedx, 2) + Math.pow(speedy, 2));
    }
    public void hookRock(MetallicRock metallicRock){
        hookedRock = metallicRock;
    }
    public void unhookRock(){
        hookedRock = null;
    }
    public boolean isHooked(){
        return hookedRock != null;
    }
    public double getMaxHeight(){
        return maxHeight;
    }
    public void update(){
        traces.forceAdd(new Oval(circle.getXCenter(),circle.getYCenter(),8));
        if(hookedRock != null){
            MetallicRock copyRock = new MetallicRock(hookedRock.getXCenter(), hookedRock.getYCenter());
            Point rockPoint = copyRock.getCircle().getCenter().copy();
            double pullRate = copyRock.getPullRate();
            pullTowards(rockPoint, pullRate);
            double angle = center.getAngle(rockPoint);
            double range = euclideanDistance(center, rockPoint);
            setParticlesProperties(angle, angle+Double.MIN_NORMAL, range, getMax(particles.range/15,maxSpeed*3));
            particles.update();           
        }
        else{
            setInertiaSpeed(); 
            if(particles.range > 1){
                setParticlesProperties(0, 359, 1, 10);
                particles.clearParticles();
            }
        }
        if(getMagnitude() > maxSpeed){
            setMagnitude(maxSpeed);
        }
        fixSpeed();
        circle.move(speedx, speedy);
        if(circle.getYCenter() < maxHeight){
            maxHeight = circle.getYCenter();
        }
        center.setX(circle.getXCenter());
        center.setY(circle.getYCenter());
    }
    public void setMagnitude(double newMagnitude){
        double magnitude = getMagnitude();
        if(magnitude > 0){
            double squareMag = Math.pow(magnitude, 2);
            double fraccx = Math.pow(speedx, 2)/squareMag;
            double fraccy = Math.pow(speedy, 2)/squareMag;
            magnitude = newMagnitude;
            squareMag = Math.pow(magnitude, 2);
            if(speedx > 0){
                speedx = Math.sqrt(fraccx*squareMag);
            }
            else if(speedx < 0){
                speedx = -Math.sqrt(fraccx*squareMag);
            }
            if(speedy > 0){
                speedy = Math.sqrt(fraccy*squareMag);
            }
            else if(speedy < 0){
                speedy = -Math.sqrt(fraccy*squareMag);
            }
        }
    }
    private void setInertiaSpeed(){
        double magnitude = getMagnitude();
        if(magnitude >= deceleration){
            setMagnitude(magnitude-deceleration);
        }
        else{
            speedx = 0;
            speedy = 0;
        }
    }
    private void pullTowards(Point rockPoint, double pullRate){
        double y = rockPoint.getY()-circle.getYCenter();
        double x = rockPoint.getX()-circle.getXCenter();
        double theta = Math.atan2(y, x);
        double distA = Math.pow(x, 2);
        double distB = Math.pow(y, 2);
        double distance = Math.sqrt(distA + distB);
        if(distance < maxSpeed*2){
            speedx = x;
            speedy = y;
        }
        else{
            speedx += Math.cos(theta)*pullRate;
            speedy += Math.sin(theta)*pullRate;
        }
        
    }
    private void fixSpeed(){
        if(isInRange(0, xlimit, circle.getXCenter()) == false){
            speedx = -speedx;
        }
        if(circle.getXCenter() < 0){
            circle.setXCenter(1);
        }
        else if(circle.getXCenter() > xlimit){
            circle.setXCenter(xlimit-1);
        }
    }
    public void draw(Graphics2D g2d){
        Array traceList = traces.toArray();
        for (int i = 0; i < traceList.size(); i++) {
            Oval currCircle = (Oval)traceList.get(i);
            currCircle.setInsideColor(new Color(0, 0, 255, 255/(traceList.size()-i+1)));
            currCircle.fill(g2d);
        }
        if(isHooked()){
            drawHookLine(g2d);
            particles.draw(g2d);
        }
        circle.fill(g2d);
    }
    private void drawHookLine(Graphics2D g2d){
        Point rockPoint = hookedRock.getCircle().getCenter().copy();
        double angle = Math.toRadians(circle.getCenter().getAngle(rockPoint));
        for (int i = 1; i <= 5; i++) {
            Point pts[] = new Point[4];
            for (int j = -1; j < 2; j+=2) {
                double tempAngle = angle + (j*1.5708);
                double cos = i*Math.cos(tempAngle);
                double sin = i*Math.sin(tempAngle);
                Point pointA = new Point(
                    circle.getXCenter()+cos, 
                    circle.getYCenter()+sin);
                Point pointB = new Point(
                    rockPoint.getX()+cos, 
                    rockPoint.getY()+sin);
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
            grossLine.setInsideColor(new Color(0, 255, 255, 40));
            grossLine.fill(g2d);
        }
    }
    private void setParticlesProperties(double minAngle, double maxAngle, double range, double speed){
        particles.minAngle = minAngle;
        particles.maxAngle = maxAngle;
        particles.range = range;
        particles.speed = speed;
    }

}