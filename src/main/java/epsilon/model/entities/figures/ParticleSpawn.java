package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.figures.auxiliar.Particle;
import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.euclideanDistance;
import static epsilon.utils.FunctionUtils.randomNumber;

public class ParticleSpawn implements IEntity{
    public Point origin;
    public double range;
    @SuppressWarnings("unused")
    public int fadeIn;
    @SuppressWarnings("unused")
    public int fadeOut;
    public double speed;
    public IEntity shape;
    public int spawnRate;
    public double minAngle;
    public double maxAngle;
    public LinkedList entities;
    public int spawnCounter;
    public ParticleSpawn(Point angleRange, int fadeIn, int fadeOut, Point origin, double range, IEntity shape, int spawnRate, double speed) {
        this.minAngle = angleRange.getX();
        this.maxAngle = angleRange.getY();
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.origin = origin;
        this.range = range;
        this.shape = shape;
        this.spawnRate = spawnRate;
        this.speed = speed;
        entities = new LinkedList();
        spawnCounter = 0;
    }
    public void update(){
        entities.removeAll(origin, (Object obj1, Object obj2) -> {
            Particle particle = (Particle)obj1;
            Point entityCenter = particle.getEntity().getCenter();
            Point origin1 = particle.getOriginPoint();
            if(euclideanDistance(entityCenter, origin1) > range){
                return 0;
            }
            if(particle.getEntity() instanceof ParticleSpawn ps){
                ps.update();
            }
            particle.move(speed);
            return 1;
        });
        spawn();
    }
    public void spawn(){
        spawnCounter++;
        if(spawnCounter == spawnRate){
            IEntity copy = shape.copy();
            copy.move(-copy.getCenter().getX(), -copy.getCenter().getY());
            copy.move(origin.getX(), origin.getY());
            int angle = (int)randomNumber(minAngle,maxAngle);
            Particle newParticle = new Particle(copy, origin.copy(), angle);
            entities.add(newParticle);
            spawnCounter = 0;
        }
    }
    @Override
    public void draw(Graphics2D g2d){
        entities.iterateList((Object nodeObject) ->{
            Particle particle = (Particle)nodeObject;
            particle.draw(g2d);
            return true;
        });
    }
    @Override
    public Point getCenter() {
        return origin;
    }

    @Override
    public void fill(Graphics2D g2d) {
        draw(g2d);
    }

    @Override
    public boolean intersects(IEntity entity) {
        return origin.intersects(entity);
    }

    @Override
    public void move(double x, double y) {
        origin.move(x, y);
    }

    @Override
    public IEntity copy() {
        ParticleSpawn copy = new ParticleSpawn(new Point(minAngle, maxAngle), fadeIn, fadeOut, origin.copy(), range, shape, spawnRate, speed);
        return copy;
    }
}