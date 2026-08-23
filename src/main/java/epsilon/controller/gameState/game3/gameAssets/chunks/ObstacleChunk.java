package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserBarrier;
import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.controller.gameState.game3.gameAssets.MetallicRock;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.nonLinearStructure.TreeMap;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;


public abstract class ObstacleChunk{
    protected int height;
    protected int seed;
    protected final double benchmark;
    protected LinkedList rockPoints = new LinkedList();
    protected TreeMap lasers;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ObstacleChunk(double benchmark, int seed){
        this.benchmark = benchmark;
        this.seed = seed;
        lasers = new TreeMap(new LineComparator());
        init();
    }
    public abstract void init();
    public int getHeight(){
        return height;
    }
    public Array exportRocks(){
        Array newRocks = new Array(rockPoints.getQuantity());
        rockPoints.iterateList((Object nodeObject) -> {
            Point rockPoint = (Point)nodeObject;
            newRocks.add(new MetallicRock(
                rockPoint.getX(),
                benchmark-rockPoint.getY()));
            return true;
        });
        return newRocks;
    }
    protected void reRollSeed(){
        if(seed < 2){
            seed = 3;
        }
        seed = (seed*seed)%101;
    }
    public Array exportLasers(){
        LinkedList laserLines = lasers.getKeys();
        Array newLasers = new Array(laserLines.size());
        laserLines.iterateList((Object nodeObject) -> {
            Line laserLine = (Line)nodeObject;
            LaserBarrier newLaser = new LaserBarrier(
                laserLine.getFirstX(),
                benchmark-laserLine.getFirstY(),
                laserLine.getSecondX(),
                benchmark-laserLine.getSecondY()
            );
            LinkedList laserMovements = lasers.getList(laserLine);
            laserMovements.iterateList((Object nodeObject1) -> {
                switch (nodeObject1) {
                    case LaserMovement lm -> {  
                        lm.fixToBenchmark(benchmark);
                        newLaser.addMovement(lm);
                    }
                    case Number number -> newLaser.addNumber(number.intValue());
                    case Boolean bool -> newLaser.addBoolean(bool);
                    default -> {}
                }
                return true;
            });
            newLasers.add(newLaser);
            return true;
        });
        return newLasers;
    }
}