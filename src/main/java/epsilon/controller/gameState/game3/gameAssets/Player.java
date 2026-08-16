package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.Queue;
import epsilon.model.entities.figures.Oval;
import static epsilon.utils.FunctionUtils.isInRange;

public class Player{
    int maxSpeed;
    double deceleration;
    public Oval circle;
    public double speedx;
    public double speedy;
    private Queue traces;
    int xlimit;
    MetallicRock hookedRock;
    double maxHeight;
    public Player(){
        init();
    }
    public final void init(){
        circle = new Oval(0, 0, 8);
        traces = new Queue(10);
        speedx = 0;
        speedy = 0;
        hookedRock = null;
        maxSpeed = 5;
        deceleration = 0.1;
        xlimit = 640;
        maxHeight = 0;
        circle.setInsideColor(new Color(0, 0, 255));
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
            pullTowards(hookedRock);
        }
        else{
            setInertiaSpeed(); 
        }
        if(getMagnitude() > maxSpeed){
            setMagnitude(maxSpeed);
        }
        fixSpeed();
        circle.move(speedx, speedy);
        if(circle.getYCenter() < maxHeight){
            
            maxHeight = circle.getYCenter();
        }
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
    private void pullTowards(MetallicRock metallicRock){
        double y = metallicRock.getCircle().getYCenter()-circle.getYCenter();
        double x = metallicRock.getCircle().getXCenter()-circle.getXCenter();
        double theta = Math.atan2(y, x);
        double distA = Math.pow(x, 2);
        double distB = Math.pow(y, 2);
        double distance = Math.sqrt(distA + distB);
        if(distance < maxSpeed*2){
            speedx = x;
            speedy = y;
        }
        else{
            double pullRate = metallicRock.getPullRate();
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
        circle.fill(g2d);
    }
}