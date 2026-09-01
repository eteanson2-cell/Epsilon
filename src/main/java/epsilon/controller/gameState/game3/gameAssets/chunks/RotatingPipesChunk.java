package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.controller.gameState.game3.gameAssets.LaserMovement;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.getSign;

public class RotatingPipesChunk extends ObstacleChunk{
    int width;
    double rad = 100;
    public RotatingPipesChunk(double benchmark, int seed) {
        super(benchmark, seed);
    }

    @Override
    public void init() {
        setHeight();
        setWidth();
        for (int y = 100; y < height; y += 200) {
            lasers.addKey(new Line(-10, y+width, 20, y+width));
            lasers.addKey(new Line(-10, y-width, 20, y-width));
            Array savedSeeds = new Array(2);
            for (int x = 120; x < 640; x += 200) {
                Point center = new Point(x, y);
                savedSeeds.add(seed%4);
                switch (seed%4) {
                    case 0 -> {
                        reRollSeed();
                        switch (seed%2) {
                            case 0 -> generateColumn(center);
                            case 1 -> generateRow(center);
                        }
                    }
                    case 1 -> generateCross(center);
                    case 2 -> generateCorner(center, fixAngle((int)(seed*3.6)));
                    case 3 -> generateT(center, fixAngle((int)(seed*3.6)));
                }
                int loopCounter = 0;
                do { 
                    reRollSeed();
                    loopCounter++;
                    if(loopCounter > 100){
                        seed++;
                        break;
                    }
                } while ((int)savedSeeds.find(seed%4) >= 0);
                rockPoints.add(center);
                if(y == 100){
                    rockPoints.add(new Point(x,-25));
                    if(benchmark > -20000){
                        rockPoints.add(new Point(x, height + 25));
                    }
                }
            }
            lasers.addKey(new Line(620, y+width, 650, y+width));
            lasers.addKey(new Line(620, y-width, 650, y-width));
        }
        height += 50;
    }

    public void setWidth(){
        width = 50;
        if(benchmark < -5000){
            width += benchmark/5000;
            if(width < 15){
                width = 15;
            }
        }
    }

    public void setHeight(){
        height = 200;
        if(benchmark < -10000){
            height = 200*((int)(benchmark/-5000));
        }
    }
    public int fixAngle(int angle){
        int residual = angle%90;
        if(residual != 0){
            if(residual < 45){
                angle -= residual;
            }
            else{
                angle += (90-residual);
            }
        }
        return angle;
    }
    public void generateCorner(Point center, int initAngle){
        double xCenter = center.getX();
        double yCenter = center.getY();
        int yFixer = getSign(degreeSine(initAngle+45));
        int xFixer = getSign(degreeCosine(initAngle+45));
        Point[] points = {
            new Point(xCenter+(width*xFixer),yCenter+(width*yFixer)),
            new Point(xCenter-(width*xFixer),yCenter-(width*yFixer))
        };
        for (Point currentPoint : points) {
            Line line1 = new Line(currentPoint,new Point(xCenter+(99.8*xFixer),currentPoint.getY()));
            Line line2 = new Line(currentPoint,new Point(currentPoint.getX(),yCenter+(99.8*yFixer)));
            addRotations(line1, center, 90/10);
            addRotations(line2, center, 90/10);
        }
    }
    public void generateColumn(Point center){
        double xCenter = center.getX();
        double yCenter = center.getY();
        double highY = yCenter+99.8;
        double lowY = yCenter-99.8;
        for (int i = -1; i < 3; i += 2) {
            Line newLine = new Line(
                xCenter + (width*i),
                highY,
                xCenter + (width*i),
                lowY
            );
            addRotations(newLine, center, 90/10);
        }
    }
    public void generateRow(Point center){
        double xCenter = center.getX();
        double yCenter = center.getY();
        double highX = xCenter+99.8;
        double lowX = xCenter-99.8;
        for (int i = -1; i < 3; i += 2) {
            Line newLine = new Line(
                highX,
                yCenter + (width*i),
                lowX,
                yCenter + (width*i)
            );
            addRotations(newLine, center, 90/10);
        }
    }
    public void generateT(Point center, int tAngle){
        double xCenter = center.getX();
        double yCenter = center.getY();
        for (int i = 0; i < 2; i++) {
            int angle = tAngle + (90*i);
            int yFixer = getSign(degreeSine(angle+45));
            int xFixer = getSign(degreeCosine(angle+45));
            Line lines[] = {
                new Line(
                    xCenter+(width*xFixer), yCenter+(width*yFixer), 
                    xCenter+(99.8*xFixer), yCenter+(width*yFixer)
                ),
                new Line(
                    xCenter+(width*xFixer), yCenter+(width*yFixer), 
                    xCenter+(width*xFixer), yCenter+(99.8*yFixer)
                )
            };
            for (Line currentLine: lines){
                addRotations(currentLine, center, 90/10);
            }
        }
        int yFixer = getSign(degreeSine(tAngle-135));
        int xFixer = getSign(degreeCosine(tAngle-135));
        Line longLine;
        if(tAngle%180 == 0){
            longLine = new Line(
                xCenter+(99.8*xFixer), yCenter+(width*yFixer), 
                xCenter+(-99.8*xFixer), yCenter+(width*yFixer)
            );
        }
        else{
            longLine = new Line(
                xCenter+(width*xFixer), yCenter+(+99.8*yFixer), 
                xCenter+(width*xFixer), yCenter+(-99.8*yFixer)
            );
        }
        addRotations(longLine, center, 90/10);
    }
    public void generateCross(Point center){
        double xCenter = center.getX();
        double yCenter = center.getY();
        for (int i = 0; i < 4; i++) {
            int angle = 45 + (90*i);
            int yFixer = getSign(degreeSine(angle));
            int xFixer = getSign(degreeCosine(angle));
            Line lines[] = {
                new Line(
                    xCenter+(width*xFixer), yCenter+(width*yFixer), 
                    xCenter+(99.8*xFixer), yCenter+(width*yFixer)
                ),
                new Line(
                    xCenter+(width*xFixer), yCenter+(width*yFixer), 
                    xCenter+(width*xFixer), yCenter+(99.8*yFixer)
                )
            };
            for (Line currentLine: lines){
                addRotations(currentLine, center, 90/10);
            }
        }
        
    }
    public void addRotations(Line currentLine, Point center, double orientation){
        if(seed < 50){
            orientation = orientation*-1;
        }
        lasers.addKey(currentLine);
        lasers.addObject(150, currentLine);
        for (int j = 0; j < 3; j++) {
            lasers.addObject(false, currentLine);
            lasers.addObject(1, currentLine);
            lasers.addObject(true, currentLine);
            lasers.addObject(17, currentLine);
        }
        lasers.addObject(new LaserMovement(null, null, false, 
            center.copy(), center.copy(), 
            orientation, orientation), 
            currentLine);
        lasers.addObject(-9, currentLine);
    }
}