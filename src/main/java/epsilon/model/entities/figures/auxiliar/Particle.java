package epsilon.model.entities.figures.auxiliar;

import java.awt.Graphics2D;

import epsilon.model.entities.figures.Point;
import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class Particle{
    protected IEntity entity;
    protected Point originPoint;
    protected int angle;
    public Particle(IEntity entity, Point originPoint, int angle){
        this.entity = entity;
        this.originPoint = originPoint;
        this.angle = angle;
    }
    public IEntity getEntity(){
        return entity;
    }
    public Point getOriginPoint(){
        return originPoint;
    }
    public int getAngle(){
        return angle;
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