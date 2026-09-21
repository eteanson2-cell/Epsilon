package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.auxiliar.VoidComparator;
import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.interfaces.Iterator;
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
            double particleRange = particle.getRange();
            double particleSpeed = particle.getSpeed();
            if(euclideanDistance(entityCenter, origin1) > particleRange){
                return 0;
            }
            if(particle.getEntity() instanceof ParticleSpawn ps){
                ps.update();
            }
            particle.move(particleSpeed);
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
            double angle = randomNumber(minAngle,maxAngle);
            Particle newParticle = new Particle(copy, origin.copy(), angle, range, speed);
            entities.add(newParticle);
            spawnCounter = 0;
        }
    }
    public void clearParticles(){
        entities.clear();
    }
    public void setAllEntities(IEntity newEntity, Comparator comparator){
        shape = newEntity;
        entities.iterateList((Object nodeObject) -> {
            Particle currentParticle = (Particle)nodeObject;
            if(comparator.compare(currentParticle, newEntity) == 0){
                currentParticle.setEntity(newEntity);
            }
            return true;
        });
    }
    public void setAllEntities(IEntity newEntity){
        setAllEntities(newEntity, new VoidComparator());
    }
    public void setAllAngles(double angle, Comparator comparator){
        entities.iterateList((Object nodeObject) -> {
            Particle currentParticle = (Particle)nodeObject;
            if(comparator.compare(currentParticle, angle) == 0){
                currentParticle.setAngle(angle);
            }
            return true;
        });
    }
    public void setAllAngles(double angle){
        setAllAngles(angle, new VoidComparator());
    }
    public void setAllSpeeds(double speed, Comparator comparator){
        this.speed = speed;
        entities.iterateList((Object nodeObject) -> {
            Particle currentParticle = (Particle)nodeObject;
            if(comparator.compare(currentParticle, speed) == 0){
                currentParticle.setSpeed(speed);
            }
            return true;
        });
    }
    public void setAllSpeeds(double speed){
        setAllSpeeds(speed, new VoidComparator());
    }
    public void setAllRanges(double range, Comparator comparator){
        this.range = range;
        entities.iterateList((Object nodeObject) -> {
            Particle currentParticle = (Particle)nodeObject;
            if(comparator.compare(currentParticle, range) == 0){
                currentParticle.setRange(range);
            }
            return true;
        });
    }
    public void setAllRanges(double range){
        setAllRanges(range, new VoidComparator());
    }
    public void iterateParticles(Iterator iterator){
        entities.iterateList(iterator);
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