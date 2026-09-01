package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game3.gameAssets.chunks.HighwayChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.HorizontalChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.LaserShooterChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.LaserSystemChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.ObstacleChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.PropellerChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.RotatingPipesChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.RowsChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.SpinningChunk;
import epsilon.controller.gameState.game3.menu.GameOverMenu;
import epsilon.controller.gameState.game3.menu.PauseMenu;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.dataStructure.nonLinearStructure.DynamicGraph;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Oval;
import epsilon.model.entities.figures.ParticleSpawn;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.Polygon;
import static epsilon.utils.FunctionUtils.degreeCosine;
import static epsilon.utils.FunctionUtils.degreeSine;
import static epsilon.utils.FunctionUtils.euclideanDistance;
import static epsilon.utils.FunctionUtils.isInRange;
import static epsilon.utils.FunctionUtils.objectToDouble;
import static epsilon.utils.FunctionUtils.randomNumber;

public class MagnetClimbState implements GameState{
    @SuppressWarnings("unused")
    private final GameStateManager gsm;
    private Player player;
    private double xOffset;
    private double yOffset;
    private LinkedList rocks;
    private LinkedList lasers;
    private LinkedList particles;
    private LaserBarrier killerLaser;
    private DynamicGraph points;
    private double benchMark;
    private double ySpawn;
    private boolean pause;
    private boolean isOver;
    private boolean restart;
    private PauseMenu pauseMenu;
    private GameOverMenu gameOverMenu;
    public MagnetClimbState(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void init() {
        pause = false;
        isOver = false;
        restart = false;
        player = new Player();
        xOffset = 350;
        player.circle.move(xOffset-30, 0);
        rocks = new LinkedList();
        lasers = new LinkedList();
        particles = new LinkedList();
        points = new DynamicGraph((Object obj1, Object obj2) -> {
            Array arr1 = (Array)obj1;
            Array arr2 = (Array)obj2;
            Point p1 = (Point)arr1.get(1);
            Point p2 = (Point)arr2.get(1);
            if(p1.getX() < p2.getX()){
                return -1;
            }
            else if(p1.getX() > p2.getX()){
                return 1;
            }
            else{
                if(p1.getY() < p2.getY()){
                    return -1;
                }
                else if(p1.getY() > p2.getY()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        killerLaser = new LaserBarrier(-10, 100, 650, 100);
        killerLaser.addMovement(new LaserMovement(
            new Point(0,-1), new Point(0, -1), true, null, null, null, null));
        yOffset = 340;
        ySpawn = 500;
        benchMark = -150;
        configureMenus();
    }

    private void configureMenus(){
        Array pauseOptions = new Array(3);
        pauseOptions.add((ActionMenu) () -> {
            pause = false;
        });
        pauseOptions.add((ActionMenu) () -> {
            restart();
        });
        pauseOptions.add((ActionMenu) () -> {
            System.exit(0);
        });
        pauseMenu = new PauseMenu(pauseOptions);
        Array gameOverOptions = pauseOptions.getSublist(1, 2);
        gameOverMenu = new GameOverMenu(gameOverOptions);
    }
    @Override
    public void update() {
        if(pause == false && isOver == false){
            player.update();
            killerLaser.update();
            if(player.circle.intersects(killerLaser.getLine())){
                gameOver();
                return;
            }
            if(player.circle.getYCenter() < benchMark+ySpawn){
                int randomChunk = randomNumber(1, 9);
                //randomChunk = 8;
                generateChunk(randomChunk);
            }
            updateRocks();
            updateLasers();
            removePoints();
        }
        if(restart == true){
            init();
        }
    }
    private void updateRocks(){
        rocks.removeAll(killerLaser, (Object obj1, Object obj2) -> {
            MetallicRock currentRock = (MetallicRock)obj1;
            currentRock.update();
            LaserBarrier killLaser = (LaserBarrier)obj2;
            if (currentRock.getCircle().intersects(killLaser.getLine())) {
                return 0;
            }
            return 1;
        });
    }
    private void updateLasers(){
        lasers.removeAll(killerLaser, (Object obj1, Object obj2) -> {
            LaserBarrier currentLaser = (LaserBarrier)obj1;
            currentLaser.update();
            if(currentLaser.isActive() && player.circle.intersects(currentLaser.getLine())){
                gameOver();
            }
            LaserBarrier killerLaser1 = (LaserBarrier)obj2;
            if (currentLaser.getPointA().getY() > killerLaser1.getPointA().getY() && currentLaser.getPointB().getY() > killerLaser1.getPointA().getY()) {
                return 0;
            }
            return 1;
        });
    }
    private void generateChunk(int chunk){
        int randomSeed = randomNumber(0, 100);
        ObstacleChunk newChunk;
        switch (chunk) {
            case 1 -> newChunk = new RowsChunk(benchMark, randomSeed);
            case 2 -> newChunk = new SpinningChunk(benchMark, randomSeed);
            case 3 -> newChunk = new HorizontalChunk(benchMark, randomSeed);
            case 4 -> newChunk = new LaserSystemChunk(benchMark, randomSeed);
            case 5 -> newChunk = new LaserShooterChunk(benchMark, randomSeed);
            case 6 -> newChunk = new RotatingPipesChunk(benchMark, randomSeed);
            case 7 -> newChunk = new HighwayChunk(benchMark,randomSeed);
            case 8 -> newChunk = new PropellerChunk(benchMark, randomSeed);
            default -> newChunk = new RotatingPipesChunk(benchMark, randomSeed);
        }
        pullChunk(newChunk);
    }
    private void pullChunk(ObstacleChunk oc){
        Array newRocks = oc.exportRocks();
        for (int i = 0; i < newRocks.getQuantity(); i++) {
            MetallicRock newRock = (MetallicRock)newRocks.get(i);
            rocks.add(newRock);
        }
        Array newLasers = oc.exportLasers();
        for (int i = 0; i < newLasers.getQuantity(); i++) {
            LaserBarrier newLaser = (LaserBarrier)newLasers.get(i);
            lasers.add(newLaser);
            if(newLaser.getPointA().getX() != newLaser.getPointB().getX() 
            || newLaser.getPointA().getY() != newLaser.getPointB().getY()){
                addToGraph(newLaser.getPointA(), newLaser.getPointB());
            }
        }
        benchMark -= oc.getHeight()+100;

    }
    private void addToGraph(Point pointA, Point pointB){
        Array arr1 = pointToArray(pointA);
        Array arr2 = pointToArray(pointB);
        points.addNode(arr1);
        points.addNode(arr2);
        points.addEdge(arr1, arr2);
    }
    private Array pointToArray(Point p1){
        Array arr = new Array(2);
        arr.add(p1);
        arr.add(p1.copy());
        return arr;
    }
    private void removePoints(){
        LinkedList pointsToRemove = new LinkedList();
        points.iterateNodes((Object nodeObject) -> {
            Array keyPoint = (Array)nodeObject;
            Point currentPoint = (Point)keyPoint.get(0);
            if(currentPoint.getY() < killerLaser.getPointA().getY()){
                return true;
            }
            LinkedList asociatedNodes = points.getConnectedNodes(nodeObject);
            Array boolArray = new Array(1);
            asociatedNodes.iterateList((Object nodeObject1) -> {
                Array keyPoint1 = (Array)nodeObject1;
                Point currentPoint1 = (Point)keyPoint1.get(0);
                if(currentPoint1.getY() < killerLaser.getPointA().getY()){
                    boolArray.add(false);
                    return false;
                }
                return true;
            });
            if(boolArray.isEmpty()){
                pointsToRemove.add(keyPoint);
            }
            return true;
        });
        pointsToRemove.iterateList((Object nodeObject) -> {
            points.removeNode(nodeObject);
            return true;
        });
    }
    public void gameOver(){
        isOver = true;
        gameOverMenu.init();
    }
    public void restart(){
        restart = true;
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(0, yOffset - player.circle.getYCenter()); 
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, (int)Math.round(player.circle.getYCenter()-1000), 640, 2000);
        rocks.iterateList((Object nodeObject) -> {
            MetallicRock currentRock = (MetallicRock)nodeObject;
            currentRock.draw(g2d);
            return true;
        });
        lasers.iterateList((Object nodeObject) -> {
            LaserBarrier currentLaser = (LaserBarrier)nodeObject;
            if(currentLaser.hasMovements() &&
                    isInRange(0,640,currentLaser.getPointA().getX()) == false &&
                    isInRange(0,640,currentLaser.getPointB().getX()) == false){
                generateWarningLine(currentLaser, g2d);
            }
            else{
                currentLaser.draw(g2d, killerLaser);
            }
            return true;
        });
        particles.iterateList((Object nodeObject)->{
            ParticleSpawn particleSpawn = (ParticleSpawn)nodeObject;
            particleSpawn.draw(g2d);
            return true;
        });
        player.draw(g2d);
        killerLaser.draw(g2d);
        drawEdges(g2d);
        g2d.translate(0, player.circle.getYCenter() - yOffset); 
        if(pause == true){
            pauseMenu.draw(g2d);
        }
        else if(isOver == true){
            gameOverMenu.draw(g2d);
        }
        double highScore = player.getMaxHeight();
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("",Font.PLAIN,24));
        g2d.drawString("HIGHSCORE:" + (long)-highScore, 20, 20);
    }
    protected void generateWarningLine(LaserBarrier currentLaser, Graphics2D g2d){
        double x;
        int colorSaturation = 255;
        if(currentLaser.getPointA().getX() < 0 && currentLaser.getPointB().getX() < 0){
            x = 5;
            colorSaturation += (int)currentLaser.getPointA().getX()/10;
        }
        else {
            x = 635;
            colorSaturation = 255 - ((int)(currentLaser.getPointA().getX()-640)/10);
        }
        if(colorSaturation < 10){
            colorSaturation = 10;
        }
        else if(colorSaturation > 255){
            colorSaturation = 255;
        } 
        g2d.setColor(new Color(colorSaturation, colorSaturation, 0));
        Line warningLine = new Line(x, currentLaser.getPointA().getY(),
                                    x, currentLaser.getPointB().getY());
        warningLine.draw(g2d);
    }
    protected void drawEdges(Graphics2D g2d){
        points.iterateNodes((Object nodeObject) -> {
            Array keyPoint = (Array)nodeObject;
            Point p1 = (Point)keyPoint.get(0);
            if(p1.getY() > killerLaser.getPointA().getY()){
                //return true;
            }
            LinkedList connectedEdges = points.getConnectedNodes(keyPoint);
            NumericArray angles = getAngles(p1, connectedEdges);
            Double freeAngle;
            if(angles.size() == 1){
                freeAngle = objectToDouble(angles.get(0))+180;
            }
            else{
                freeAngle = getEdgeAngle(angles);
            }
            drawEdges(g2d, p1, angles, freeAngle);
            return true;
        });
    }
    protected NumericArray getAngles(Point p1, LinkedList connectedEdges){
        NumericArray angles = new NumericArray(connectedEdges.size());
        connectedEdges.iterateList((Object nodeObject) -> {
            Array keyPoint = (Array)nodeObject;
            Point currentPoint = (Point)keyPoint.get(0);
            angles.add(p1.getAngle(currentPoint));
            return true;
        });
        return angles;
    }
    protected Double getEdgeAngle(NumericArray array){
        array.quickSort();
        NumericArray distances = array.getDistances();
        if(distances == null){
            return null;
        }
        distances.remove();
        distances.add((objectToDouble(array.get(0))+360) - objectToDouble(array.get(array.size()-1)));
        double highestRange = objectToDouble(distances.get(0));
        for (int i = 1; i < distances.size(); i++) {
            double tempDouble = objectToDouble(distances.get(i));
            if(tempDouble > highestRange){
                highestRange = tempDouble;
            }
        }
        if(highestRange < 180){
            return null;
        }
        int position1 = (int)distances.find(highestRange);
        int position2 = (position1+1)%array.size();
        double prev = objectToDouble(array.get(position1));
        double next = objectToDouble(array.get(position2));
        if(next < prev){
            next += 360;
        }
        double diff = next-prev;
        return prev + (diff/2.0);
    }
    protected void drawEdges(Graphics2D g2d, Point edgePoint, 
                             NumericArray angles, Double angleEdge){
        double radix = 10;
        angles.iterateList((Object nodeObject) -> {
            double angle = objectToDouble(nodeObject);
            drawCylinder(edgePoint, angle, radix, 5, g2d);
            return true;
        });
        if(angles.size() > 1){
            Oval centerCircle = new Oval(edgePoint.getX(), edgePoint.getY(), 10);
            centerCircle.setInsideColor(Color.GRAY);
            centerCircle.fill(g2d);
        }
        if(angleEdge != null){
            Point edgeBlaster = new Point(
                edgePoint.getX() + (degreeCosine(angleEdge)*(radix-7)),
                edgePoint.getY() + (degreeSine(angleEdge)*(radix-7))
            );
            drawCylinder(edgePoint, angleEdge, radix-7, 5, g2d);
            drawCylinder(edgeBlaster, angleEdge, 6, 8, g2d);
        }
        else{
        }
    }
    public void drawCylinder(Point edgePoint, double angle, double radix, int width, Graphics2D g2d){
        int pixelSaturation;
        Point radixBlaster = new Point(
            edgePoint.getX() + (degreeCosine(angle)*radix),
            edgePoint.getY() + (degreeSine(angle)*radix)
        );
        Point[] pts = new Point[4];
        Polygon cylinder;
        int fixer = 255/(width+1);
        for (int i = width; i > 0; i--) {
            pixelSaturation = 255-(fixer*i);
            pts[0] = new Point(
                edgePoint.getX() + (degreeCosine(angle+90)*i),
                edgePoint.getY() + (degreeSine(angle+90)*i)
            );
            pts[1] = new Point(
                edgePoint.getX() + (degreeCosine(angle-90)*i),
                edgePoint.getY() + (degreeSine(angle-90)*i)
            );
            pts[2] = new Point(
                radixBlaster.getX() + (degreeCosine(angle-90)*i),
                radixBlaster.getY() + (degreeSine(angle-90)*i)
            );
            pts[3] = new Point(
                radixBlaster.getX() + (degreeCosine(angle+90)*i),
                radixBlaster.getY() + (degreeSine(angle+90)*i)
            );
            cylinder = new Polygon(pts);
            cylinder.setInsideColor(new Color(pixelSaturation,pixelSaturation,pixelSaturation,255));
            cylinder.fill(g2d);
        }
    }
    @Override
    public void keyPressed(int k) {
        if(pause == true){
            pauseMenu.KeyPressed(k);
        }
        else if(isOver == true){
            gameOverMenu.KeyPressed(k);
        }
        else{
            if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_SPACE){
                pause = true;
                pauseMenu.init();
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void keyTyped(int k) {

    }

    @Override
    public void mouseClicked(int x, int y, int button) {
    }

    @Override
    public void mousePressed(int x, int y, int button) {
        if(player.isHooked() == false){
            Point mousePosition = new Point(x, y + player.circle.getYCenter()-yOffset);
            rocks.iterateList((Object nodeObject) -> {
                MetallicRock mr = (MetallicRock)nodeObject;
                if(euclideanDistance(mousePosition, mr.getCircle().getCenter()) <= 30){
                    player.hookRock(mr);
                    return false;
                }
                return true;                
            });
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        player.unhookRock();
    }

    @Override
    public void mouseDragged(int x, int y, int button) {

    }

    @Override
    public void mouseMoved(int x, int y) {
    }

}