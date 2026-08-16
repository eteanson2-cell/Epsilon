package epsilon.controller.gameState.game2.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameMenu;
import epsilon.controller.interfaces.ActionMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Polygon;
import epsilon.model.enums.StretchingPoint;

public class OptionsMenu extends GameMenu{
    private final int MAX_HEIGHT = 50;
    private final int MAX_WIDTH = 50;
    private int boardHeight, boardWidth, snakeSize;
    private double snakeSpeed;
    private final Polygon arrow;
    public OptionsMenu(Array options){
        super(options);
        arrow = new Polygon(new double[]{100,75,75,50,50,75,75},new double[]{100,150,125,125,75,75,50});
        arrow.resizeYAxis(0.5, StretchingPoint.CENTER);
        arrow.setInsideColor(new Color(255, 255, 255));
        boardHeight = 20;
        boardWidth = 10;
        snakeSize = 11;
        snakeSpeed = 1.0;
    }
    @Override
    public void init(){
        isEnabled = true;
    }
    public int getBoardHeight() {
        return boardHeight;
    }

    private void setBoardHeight(int boardHeight) {
        this.boardHeight += boardHeight;
        if(this.boardHeight < 10){
            this.boardHeight = 10;
        }
        else if (this.boardHeight > MAX_HEIGHT) {
            this.boardHeight = MAX_HEIGHT;
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    private void setBoardWidth(int boardWidth) {
        this.boardWidth += boardWidth;
        if(this.boardWidth < 5){
            this.boardWidth = 5;
        }
        else if (this.boardWidth > MAX_WIDTH) {
            this.boardWidth = MAX_WIDTH;
        }
    }
    public int getSnakeSize() {
        return snakeSize;
    }
    private void setSnakeSize(int snakeSize) {
        this.snakeSize += snakeSize;
        if(this.snakeSize < 5){
            this.snakeSize = 5;
        }
        else if (this.snakeSize > 20) {
            this.snakeSize = 20;
        }
    }
    public double getSnakeSpeed() {
        return snakeSpeed;
    }
    private void setSnakeSpeed(double snakeSpeed) {
        this.snakeSpeed += snakeSpeed;
        if(this.snakeSpeed < 0.5){
            this.snakeSpeed = 0.5;
        }
        else if (this.snakeSpeed > 5) {
            this.snakeSpeed = 5;
        }
    }
    @Override
    public void changeOption(byte optionNumber) {
        if(isEnabled){
            if (optionNumber > 4) {
                this.optionNumber = 0;
            }
            else if (optionNumber < 0) {
                this.optionNumber = 4;
            }
            else{
                this.optionNumber = optionNumber;
            }
            arrow.moveFromCenter(75, 95+(this.optionNumber*25));
        }
    }
    public void changeValues(int increase){
        switch (optionNumber) {
            case 0 -> {
                setBoardHeight(increase);
            }
            case 1 -> {
                setBoardWidth(increase);
            }
            case 2 -> {
                setSnakeSize(increase);
            }
            case 3 -> {
                double doubleIncrease = 0.1*increase;
                setSnakeSpeed(doubleIncrease);
            }
            case 4 -> {
                ActionMenu action = (ActionMenu)options.get(0);
                changeOption((byte)0);
                action.run();
            }
        }
    }

    @Override
    public boolean showWarning() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void KeyPressed(int k) {
        if(isEnabled){
            if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN)
                changeOption((byte)(optionNumber+1));
            if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP)
                changeOption((byte)(optionNumber-1));
            if(k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D || 
               k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE)
                changeValues(1);
            if(k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
                changeValues(-1);
        }
    }

    @Override
    public void KeyTyped(int k) {
        
    }

    @Override
    public void KeyReleased(int k) {
        
    }
    public void draw(Graphics2D g2d){
        if(isEnabled){
            g2d.setFont(new Font("",Font.PLAIN,12));
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0, 0, 600, 400);
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("BOARD HEIGHT: " + boardHeight, 125, 100);
            g2d.drawString("BOARD WIDTH: " + boardWidth, 125, 125);
            g2d.drawString("SNAKE SIZE: " + snakeSize, 125, 150);
            g2d.drawString("SPEED: " + Math.round(snakeSpeed*10), 125, 175);
            g2d.drawString("EXIT", 125, 200);
            arrow.fill(g2d);
        }
    }
    
}