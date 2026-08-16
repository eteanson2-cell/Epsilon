package epsilon.controller.gameState.game2.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game2.menu.GameOverMenu;
import epsilon.controller.gameState.game2.menu.PauseMenu;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import static epsilon.utils.FunctionUtils.randomNumber;

public class SnakeBoardState implements GameState{
    @SuppressWarnings("unused")
    private final GameStateManager gsm;
    private Snake snake;
    private Board board;
    private int boardHeight, boardWidth, snakeSize;
    private double startingSpeed;
    private boolean pause;
    private final PauseMenu pauseMenu;
    private final GameOverMenu gameOverMenu;
    private int xCenter;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SnakeBoardState(GameStateManager gsm) {
		this.gsm = gsm;
        Array options = new Array(3);
        options.add((ActionMenu) () -> {
            pause = false;
            board.setActive(true);
        });
        options.add((ActionMenu) () -> {
            pause = false;
            restartGame();
        });
        options.add((ActionMenu) () -> {
            gsm.setState(1);
        });
        pauseMenu = new PauseMenu(options);
        Array options2 = options.getSublist(1, 2);
        gameOverMenu = new GameOverMenu(options2);
    }
    @Override
    public void init() {
        xCenter = 155;
        int scale = (int)(200/Math.round(Math.hypot(boardHeight,boardWidth))) + 1;
        pause = false;
        board = new Board(boardHeight, boardWidth);
        board.setScale(scale);
        //System.out.println(board);
        snake = new Snake(snakeSize);
        snake.setBoard(board);
        snake.setBlocks(randomNumber(2,snakeSize));
        snake.setSpeed(startingSpeed);
        snake.setScale(scale);
    }
    public int getSnakeSize() {
        return snakeSize;
    }
    public void setSnakeSize(int snakeSize) {
        this.snakeSize = snakeSize;
    }
    public int getBoardHeight() {
        return boardHeight;
    }
    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }
    public int getBoardWidth() {
        return boardWidth;
    }
    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }
    public double getStartingSpeed() {
        return startingSpeed;
    }
    public void setStartingSpeed(double startingSpeed) {
        this.startingSpeed = startingSpeed;
    }
    public void restartGame(){
        board.restartGame();
        snake.setBlocks(randomNumber(2,snakeSize));
        snake.setSpeed(startingSpeed);
    }
    @Override
    public void update() {
        if(board.isActive() == false){
            pause = true;
            gameOverMenu.init();
            gameOverMenu.setScore(board.getScore());
        }
        if(pause == false){
            snake.update();
            board.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(0,0,0));
        g2d.fillRect(0, 0, 600, 400);
        board.draw(g2d,xCenter);
        snake.draw(g2d,xCenter);
        if(pause == true){
            if (board.isActive() == false) {
                gameOverMenu.draw(g2d);
            }
            else{
                pauseMenu.draw(g2d);
            }
        }
    }

    @Override
    public void keyPressed(int k) {
        if(pause == false){
            if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT)
			    snake.setLeft();
            if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN)
                snake.setDown();
            if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT)
                snake.setRight();
            if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_SPACE){
                pause = true;
                pauseMenu.init();
            }
        }
        else{
            if (board.isActive() == false) {
                gameOverMenu.KeyPressed(k);
            }
            else{
                pauseMenu.KeyPressed(k);
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