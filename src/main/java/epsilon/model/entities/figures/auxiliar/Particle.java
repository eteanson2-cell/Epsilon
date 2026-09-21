package epsilon.model.entities.figures.auxiliar;

import java.awt.Graphics2D;

import epsilon.model.entities.figures.Point;
import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class Particle{
    protected IEntity entity;
    protected Point originPoint;
    protected double angle;
    protected double range;
    protected double speed;
    public Particle(IEntity entity, Point originPoint, double angle, double range, double speed){
        this.entity = entity;
        this.originPoint = originPoint;
        this.angle = angle;
        this.range = range;
        this.speed = speed;
    }
    public IEntity getEntity(){
        return entity;
    }
    public Point getOriginPoint(){
        return originPoint;
    }
    public double getAngle(){
        return angle;
    }
    public double getRange(){
        return range;
    }
    public double getSpeed(){
        return speed;
    }
    public void setEntity(IEntity entity){
        this.entity = entity;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public void setRange(double range){
        this.range = range;
    }
    public void move(double speed){
        double xMove = speed*degreeCosine(angle);
        double yMove = speed*degreeSine(angle);
        entity.move(xMove, yMove);
    }
    public void draw(Graphics2D g2d){
        entity.fill(g2d);
    }
}