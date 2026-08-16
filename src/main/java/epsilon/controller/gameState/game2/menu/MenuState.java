package epsilon.controller.gameState.game2.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game2.gameAssets.SnakeBoardState;
import epsilon.controller.gameState.game2.gameAssets.SnakeColor;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.randomNumber;

public class MenuState implements GameState{
    @SuppressWarnings("FieldMayBeFinal")
    private GameStateManager gsm;
    private MainMenu mainMenu;
    private OptionsMenu optionsMenu;
    Color currentColor;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
        build();
    }
    private void build(){
        Array actionEvents1 = new Array(3);
        actionEvents1.add((ActionMenu) () -> {
            startGame();
        });
        actionEvents1.add((ActionMenu) () -> {
            mainMenu.changeMenu(optionsMenu);
        });
        actionEvents1.add((ActionMenu) () -> {
            System.exit(0);
        });
        mainMenu = new MainMenu(actionEvents1);
        Array actionEvents2 = new Array(1);
        actionEvents2.add((ActionMenu) () -> {
            optionsMenu.changeMenu(mainMenu);
        });
        optionsMenu = new OptionsMenu(actionEvents2);
    }
    @Override
    public void init() {
        mainMenu.init();
        int randomNumber = randomNumber(0,13);
        switch (randomNumber) {
            case 1 -> currentColor = SnakeColor.BLUE.getColor();
            case 2 -> currentColor = SnakeColor.CYAN.getColor();
            case 3 -> currentColor = SnakeColor.GREEN.getColor();
            case 4 -> currentColor = SnakeColor.LEMON_YELLOW.getColor();
            case 5 -> currentColor = SnakeColor.YELLOW.getColor();
            case 6 -> currentColor = SnakeColor.ORANGE_YELLOW.getColor();
            case 7 -> currentColor = SnakeColor.ORANGE.getColor();
            case 8 -> currentColor = SnakeColor.ORANGE_RED.getColor();
            case 9 -> currentColor = SnakeColor.RED.getColor();
            case 10 -> currentColor = SnakeColor.SCARLET.getColor();
            case 11 -> currentColor = SnakeColor.MAGENTA.getColor();
            case 12 -> currentColor = SnakeColor.VIOLET.getColor();
            default -> currentColor = SnakeColor.BLUE.getColor();
        }
    }
    public void startGame(){
        GameState gs = (GameState)gsm.getState(0);
        if(gs instanceof SnakeBoardState sbs){
            mainMenu.setActive(false);
            sbs.setBoardHeight(optionsMenu.getBoardHeight());
            sbs.setBoardWidth(optionsMenu.getBoardWidth());
            sbs.setSnakeSize(optionsMenu.getSnakeSize());
            sbs.setStartingSpeed(optionsMenu.getSnakeSpeed());
            gsm.setState(0);
        }
        else{
            System.out.println(gs);
        }
    }
    @Override
    public void update() {
    }
    @Override
    public void draw(Graphics2D g2d) {
        mainMenu.draw(g2d);
        optionsMenu.draw(g2d);
        g2d.setFont(new Font("",Font.PLAIN,40));
        g2d.setColor(currentColor);
        g2d.drawString("SnakeBlock", 50, 40);
    }

    @Override
    public void keyPressed(int k) {
        if (mainMenu.isEnabled()) {
            mainMenu.KeyPressed(k);
        }
        else if (optionsMenu.isEnabled()) {
            optionsMenu.KeyPressed(k);
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

    }

    @Override
    public void mouseReleased(int x, int y, int button) {

    }

    @Override
    public void mouseDragged(int x, int y, int button) {
        
    }

    @Override
    public void mouseMoved(int x, int y) {
        
        
    }
}