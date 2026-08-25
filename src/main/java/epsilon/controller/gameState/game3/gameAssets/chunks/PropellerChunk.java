package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;

public class PropellerChunk extends ObstacleChunk{
    private double speed;
    public PropellerChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        height = 640;
        setSpeed(1.0);
        int rods = getRods();
        int sections = getSections();
        double span = 320/sections;
        int laserSections = (int)Math.round(sections*(2.0/3.0));
        for (int i = 1; i <= sections; i++) {
            generateRock(i*span-(span/2));
        }
        for (int i = 0; i < rods; i++) {
            int angle = i*(360/rods);
            Array storedIndexes = new Array(laserSections);
            while(storedIndexes.size() < laserSections){
                for (int j = 1; j <= sections && storedIndexes.size() < laserSections; j++) {
                    if((int)storedIndexes.find(j) >= 0){
                        continue;
                    }
                    if(seed < 50){
                        storedIndexes.add(j);
                        generateSection((j-1)*span, j*span, angle);
                    }
                    reRollSeed();
                }
            }
        }
    }
    public int getRods(){
        int rods = 2;
        if(benchmark < -7500){
            rods -= benchmark/7500;
            if(rods > 12){
                rods = 12;
            }
        }
        return rods;
    }
    public int getSections(){
        int sections = 3;
        if(benchmark < -10000){
            sections -= benchmark/10000;
            if(sections > 20){
                sections = 20;
            }
        }
        return sections;
    }
    public void setSpeed(double baseValue){
        if(seed > 50){
            speed = -baseValue;
        }
        else{
            speed = baseValue;
        }
    }
    public void generateRock(double range){
        for (int i = 0; i < 4; i++) {
            int angle = i*90;
            rockPoints.add(
            new Point(320 + (range*degreeCosine(angle)),
                      320 + (range*degreeSine(angle)))
            );
        }
    }
    public void generateSection(double xStart, double xEnd, int angle){
        double yCenter = 320;
        double xCenter = 320;
        Line section = new Line(
            xCenter + (xStart*degreeCosine(angle)), 
            yCenter + (xStart*degreeSine(angle)), 
            xCenter + (xEnd*degreeCosine(angle)),
            yCenter + (xEnd*degreeSine(angle))
        );
        lasers.addKey(section);
        lasers.addObject(
            new LaserMovement(null, null, false, new Point(xCenter,yCenter), new Point(xCenter,yCenter), speed, speed), 
        section);
    }
}