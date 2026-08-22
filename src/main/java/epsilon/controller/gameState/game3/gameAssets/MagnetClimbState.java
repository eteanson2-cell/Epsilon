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
import epsilon.controller.gameState.game3.gameAssets.chunks.RotatingPipesChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.RowsChunk;
import epsilon.controller.gameState.game3.gameAssets.chunks.SpinningChunk;
import epsilon.controller.gameState.game3.menu.GameOverMenu;
import epsilon.controller.gameState.game3.menu.PauseMenu;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.nonLinearStructure.Graph;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Point;
import static epsilon.utils.FunctionUtils.isInRange;
import static epsilon.utils.FunctionUtils.randomNumber;

public class MagnetClimbState implements GameState{
    @SuppressWarnings("unused")
    private final GameStateManager gsm;
    private Player player;
    private double xOffset;
    private double yOffset;
    private LinkedList rocks;
    private LinkedList lasers;
    private LaserBarrier killerLaser;
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
        player.circle.move(xOffset, 0);
        rocks = new LinkedList();
        lasers = new LinkedList();
        killerLaser = new LaserBarrier(0, 500, 700, 500);
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
            rocks.removeAll(killerLaser, (Object obj1, Object obj2) -> {
                MetallicRock currentRock = (MetallicRock)obj1;
                currentRock.update();
                LaserBarrier killLaser = (LaserBarrier)obj2;
                if (currentRock.getCircle().intersects(killLaser.getLine())) {
                    return 0;
                }
                return 1;
            });
            if(player.circle.getYCenter() < benchMark+ySpawn){
                int randomChunk = randomNumber(1, 8);
                //randomChunk = 4;
                generateChunk(randomChunk);
            }
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
        if(restart == true){
            init();
        }
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
        }
        benchMark -= oc.getHeight()+100;

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
        });
        player.draw(g2d);
        killerLaser.draw(g2d);
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
        g2d.setColor(new Color(colorSaturation, colorSaturation, 0));
        Line warningLine = new Line(x, currentLaser.getPointA().getY(),
                                    x, currentLaser.getPointB().getY());
        warningLine.draw(g2d);
    }
    protected void drawLasers(Graphics2D g2d){
        Graph laserPoints = new Graph(lasers.size());
        lasers.iterateList((Object nodeObject) -> {
            LaserBarrier laser = (LaserBarrier)nodeObject;
            laserPoints.add(laser.getPointA());
            laserPoints.add(laser.getPointB());
            laserPoints.addEdge(laser.getPointA(), laser.getPointB());
        });
        
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
            rocks.initializeIterator();
            while (rocks.validIterator()) { 
                MetallicRock mr = (MetallicRock)rocks.getIterator();
                if(mr.intersects(mousePosition)){
                    player.hookRock(mr);
                    break;
                }
                rocks.moveIteratorToRight();
            }
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
        System.out.println(y);
        if(y > 200){
            yOffset = y;
        }
    }

}