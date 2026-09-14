package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;

public class CrusherChunk extends ObstacleChunk{
    public CrusherChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        int blockSpan = setBlockSpan();
        double prevY = 0;
        double prevX1 = 319;
        double prevX2 = 321;
        int freeSpot = setFreeSpot();
        int counter = 0;
        rockPoints.add(new Point(320,-25));
        addMovements(new Line(-10, 0, prevX1, 0), new Line(prevX2, 0, 650, 0));
        for (int y = blockSpan; y < height; y+= blockSpan) {
            Line leftLine = new Line(prevX1, prevY, prevX1, y);
            Line rightLine = new Line(prevX2, prevY, prevX2, y);
            addMovements(leftLine, rightLine);
            int fixer = 0;
            int fixer2 = 0;
            if(y%150 == 0){
                switch (seed%2) {
                    case 0 -> {
                        fixer2 = 1;
                    }
                    case 1 -> {
                        fixer = -1;
                    }
                }
            }
            else if(prevX2 - prevX1 > 2){
                if(prevX1 > 320){
                    fixer2 = -1;
                }
                else{
                    fixer = 1;
                }
            }
            if(seed < 33 && prevX1 > 100){
                fixer += -1;
                fixer2 += -1;
            }
            else if (seed >= 66 && prevX2 < 540) {
                fixer += 1;
                fixer2 += 1;
            }
            addMovements(
                new Line(prevX1, y, prevX1 + (blockSpan*fixer), y), 
                new Line(prevX2, y, prevX2 + (blockSpan*fixer2), y)
            );
            if(counter == freeSpot){
                double rockX = prevX1 + ((prevX2-prevX1)/2);
                rockPoints.add(new Point(rockX, prevY + 25));
                counter = -1;
            }
            counter++;
            prevY = y;
            prevX1 += (blockSpan*fixer);
            prevX2 += (blockSpan*fixer2);
            reRollSeed();
        }
        addMovements(
            new Line(prevX1, prevY, prevX1, height), 
            new Line(prevX2, prevY, prevX2, height)
        );
        addMovements(
            new Line(-10, height, prevX1, height), 
            new Line(prevX2, height, 650, height)
        );
        rockPoints.add(new Point(prevX1 + ((prevX2-prevX1)/2),height+25));
    }
    public void setHeight(){
        height = 200;
        if(benchmark < -2500){
            height -= ((int)benchmark/2500)*50;
            if(height > 4000){
                height = 4000;
            } 
        }
    }
    public int setBlockSpan(){
        int span = 50;
        return span;
    }
    public int setFreeSpot(){
        int freeSpot = 1;
        if(benchmark < -5000){
            freeSpot -= benchmark/5000;
            if(freeSpot > 5){
                freeSpot = 5;
            }
        }
        return freeSpot;
    }
    public void addMovements(Line leftLine, Line rightLine){
        int ipf = 2;
        int duration = -99;
        LaserMovement leftMovement = 
            new LaserMovement(new Point(-ipf,0), new Point(-ipf,0), true, null, null, null, null);
        LaserMovement rightMovement = 
            new LaserMovement(new Point(ipf,0), new Point(ipf,0), true, null, null, null, null);
        addMovementLine(duration, leftLine, leftMovement, rightMovement);
        addMovementLine(duration, rightLine, rightMovement, leftMovement);
    }
    /*public void addMoveLeftLine(int duration, Line leftLine, 
            LaserMovement leftMovement, LaserMovement rightMovement){
        lasers.addKey(leftLine);
        lasers.addObject(leftMovement, leftLine);
        lasers.addObject(duration, leftLine);
        lasers.addObject(rightMovement, leftLine);
        lasers.addObject(duration, leftLine);
    }
    public void addMoveRightLine(int duration, Line rightLine, 
            LaserMovement leftMovement, LaserMovement rightMovement){
        lasers.addKey(rightLine);
        lasers.addObject(rightMovement, rightLine);
        lasers.addObject(duration, rightLine);
        lasers.addObject(leftMovement, rightLine);
        lasers.addObject(duration, rightLine);
    }*/
    public void addMovementLine(int duration, Line line, 
            LaserMovement firstMovement, LaserMovement secondMovement){
        if(line.getFirstX() != line.getSecondX() || line.getFirstY() != line.getSecondY()){
            lasers.addKey(line);
            lasers.addObject(firstMovement, line);
            lasers.addObject(duration, line);
            lasers.addObject(secondMovement, line);
            lasers.addObject(duration, line);
        }
    }
}