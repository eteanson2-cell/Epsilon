package epsilon.controller.gameState.game3.menu;

import java.awt.Graphics2D;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game3.gameAssets.LaserBarrier;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public final class MenuState implements GameState{
    private final GameStateManager gsm;
    private MainMenu mainMenu;
    private LaserBarrier[] titleLines;
    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
        build();
    }
    private void build(){
        Array events = new Array(2);
        events.add((ActionMenu) this::startGame);
        events.add((ActionMenu)() -> System.exit(0));
        mainMenu = new MainMenu(events);
    }
    @Override
    public void init() {
        mainMenu.init();
        titleLines = new LaserBarrier[]{
            new LaserBarrier(10, 10, 10, 50),
            new LaserBarrier(10, 10, 25, 25),
            new LaserBarrier(25, 25, 40, 10),
            new LaserBarrier(40, 10, 40, 50),
            new LaserBarrier(50, 50, 65, 10),
            new LaserBarrier(65, 10, 80, 50),
            new LaserBarrier(57, 30, 73, 30),
            new LaserBarrier(90, 10, 90, 50),
            new LaserBarrier(90, 10, 120, 10),
            new LaserBarrier(90, 50, 120, 50),
            new LaserBarrier(120, 50, 120, 30),
            new LaserBarrier(120, 30, 105, 30),
            new LaserBarrier(130, 10, 130, 50),
            new LaserBarrier(130, 10, 160, 50),
            new LaserBarrier(160, 50, 160, 10),
            new LaserBarrier(170, 10, 170, 50),
            new LaserBarrier(170, 10, 200, 10),
            new LaserBarrier(170, 30, 200, 30),
            new LaserBarrier(170, 50, 200, 50),
            new LaserBarrier(210, 10, 240, 10),
            new LaserBarrier(225, 10, 225, 50),
            new LaserBarrier(270, 10, 270, 50),
            new LaserBarrier(270, 10, 300, 10),
            new LaserBarrier(270, 50, 300, 50),
            new LaserBarrier(310, 10, 310, 50),
            new LaserBarrier(310, 50, 340, 50),
            new LaserBarrier(350, 10, 350, 50),
            new LaserBarrier(360, 10, 360, 50),
            new LaserBarrier(360, 10, 375, 25),
            new LaserBarrier(375, 25, 390, 10),
            new LaserBarrier(390, 10, 390, 50),
            new LaserBarrier(400, 10, 400, 50),
            new LaserBarrier(400, 10, 430, 20),
            new LaserBarrier(430, 20, 415, 30),
            new LaserBarrier(400, 30, 415, 30),
            new LaserBarrier(415, 30, 430, 40),
            new LaserBarrier(430, 40, 400, 50)
        };
        for (LaserBarrier lb: titleLines) {
            lb.moveAPoint(100, 20);
            lb.moveBPoint(100, 20);
        }
    }
    public void setRedFontManager(RedFontManager rfm){
        mainMenu.setFontManager(rfm);
    }
    public void startGame(){
        titleLines = new LaserBarrier[1];
        gsm.setState(0);
    }
    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2d) {
        if(mainMenu.isEnabled()){
            mainMenu.draw(g2d);
        }
        for (LaserBarrier titleLine : titleLines) {
            if(titleLine != null){
                titleLine.draw(g2d);
            }
        }
    }

    @Override
    public void keyPressed(int k) {
        if (mainMenu.isEnabled()) {
            mainMenu.KeyPressed(k);
        }
    }
    private void drawBLine(int barriersIndex, int xOffset, int yOffset){
        LaserBarrier[] barrierB = {
            new LaserBarrier(xOffset, yOffset,  
                             xOffset, yOffset + 40),
            new LaserBarrier(xOffset, yOffset,  
                             xOffset + 30, yOffset + 10),
            new LaserBarrier(xOffset + 30, yOffset + 10, 
                             xOffset + 15, yOffset + 20),
            new LaserBarrier(xOffset, yOffset + 20, 
                             xOffset + 15, yOffset + 20),
            new LaserBarrier(xOffset + 15, yOffset + 20, 
                             xOffset + 30, yOffset + 30),
            new LaserBarrier(xOffset + 30, yOffset + 30, 
                             xOffset, yOffset + 40)
        };
        for (LaserBarrier barrierB1 : barrierB) {
            titleLines[barriersIndex] = barrierB1;
            barriersIndex++;
        }
    }

    @Override
    public void keyReleased(int k) {}

    @Override
    public void keyTyped(int k) {}

    @Override
    public void mouseClicked(int x, int y, int button) {}

    @Override
    public void mousePressed(int x, int y, int button) {}

    @Override
    public void mouseReleased(int x, int y, int button) {}

    @Override
    public void mouseDragged(int x, int y, int button) {}

    @Override
    public void mouseMoved(int x, int y) {}

}