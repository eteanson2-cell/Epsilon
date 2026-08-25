package epsilon.model.entities.figures;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.figures.auxiliar.Particle;
import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.euclideanDistance;
import static epsilon.utils.FunctionUtils.randomNumber;

public class ParticleSpawn{
    public Point origin;
    double range;
    @SuppressWarnings("unused")
    int fadeIn;
    @SuppressWarnings("unused")
    int fadeOut;
    double speed;
    IEntity shape;
    int spawnRate;
    Point angleRange;
    LinkedList entities;
    int spawnCounter;
    public ParticleSpawn(Point angleRange, int fadeIn, int fadeOut, Point origin, double range, IEntity shape, int spawnRate, double speed) {
        this.angleRange = angleRange;
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
            int angle = randomNumber((int)angleRange.getX(),(int)angleRange.getY());
            Particle newParticle = new Particle(copy, origin.copy(), angle);
            entities.add(newParticle);
            spawnCounter = 0;
        }
        //entities.print();
    }
    public void draw(Graphics2D g2d){
        entities.iterateList((Object nodeObject) ->{
            Particle particle = (Particle)nodeObject;
            particle.draw(g2d);
            return true;
        });
    }
}