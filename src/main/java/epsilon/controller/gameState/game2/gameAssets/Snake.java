package epsilon.controller.gameState.game2.gameAssets;
import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.Queue;
import epsilon.model.entities.figures.auxiliar.Pixel;
import static epsilon.utils.FunctionUtils.isInRange;
import static epsilon.utils.FunctionUtils.randomNumber;

public class Snake {
    protected Queue snakeBlocks;
    protected boolean ybit,xbit;
    //0,0 -> left | 0,1 -> right | 1,0 -> down | 1,1 static
    protected Pixel currentColor;
    protected int scale;
    protected double speed, speedCounter;
    protected final int movementTrigger = 10;
    protected int maxSize;
    protected Board board;
    public Snake(int maxSize){
        ybit = true;
        xbit = false;
        speed = 3;
        speedCounter = 0;
        this.maxSize = Math.abs(maxSize);
    }
    public Queue getBlocks(){
        return snakeBlocks;
    }
    public boolean getXBit(){
        return xbit;
    }
    public boolean getYBit(){
        return ybit;
    }
    public double getSpeed(){
        return speed;
    }
    public double getSpeedCounter(){
        return speedCounter;
    }
    public int getMovementTriger(){
        return movementTrigger;
    }
    public Board getBoard(){
        return board;
    }
    public void setBlocks(int capacity){
        if(board != null && capacity <= maxSize){
            if(snakeBlocks == null){
                snakeBlocks = new Queue(capacity);
            }
            else{
                snakeBlocks.clear();
                snakeBlocks.resize(capacity);
            }   
            SnakeColor snakeColor = SnakeColor.VIOLET;
            switch (capacity) {
                case 2 -> snakeColor = SnakeColor.BLUE;
                case 3 -> snakeColor = SnakeColor.CYAN;
                case 4-> snakeColor = SnakeColor.GREEN;
                case 5 -> snakeColor = SnakeColor.LEMON_YELLOW;
                case 6 -> snakeColor = SnakeColor.YELLOW;
                case 7 -> snakeColor = SnakeColor.ORANGE_YELLOW;
                case 8 -> snakeColor = SnakeColor.ORANGE;
                case 9 -> snakeColor = SnakeColor.ORANGE_RED;
                case 10 -> snakeColor = SnakeColor.RED;
                case 11 -> snakeColor = SnakeColor.SCARLET;
                case 12 -> snakeColor = SnakeColor.MAGENTA;
            }
            currentColor = new Pixel(snakeColor.getColor().getRGB());
            int x = (int)randomNumber(0,board.getWidth());
            for (int y = -capacity; snakeBlocks.isFilled() == false; y++) {
                snakeBlocks.add(new SnakeBlock(x,y,currentColor));
            }
        }
    }
    public void setLeft(){
        if(isStatic() == false && ybit == true){
            ybit = false;
            xbit = false;
        }
    }
    public void setRight(){
        if(isStatic() == false && ybit == true){
            ybit = false;
            xbit = true;
        }
    }
    public void setDown(){
        if(isStatic() == false){
            ybit = true;
            xbit = false;
        }
    }
    public void setStatic(){
        ybit = true;
        xbit = true;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public void setBoard(Board board){
        this.board = board;
    }
    public void setScale(int scale){
        this.scale = scale;
    }
    public void update(){
        if(speedCounter >= movementTrigger){
            speedCounter = 0;
            SnakeBlock snakeBlock = (SnakeBlock)snakeBlocks.getLastObject();
            int x = snakeBlock.getX();
            int y = snakeBlock.getY();
            if(xbit == false && ybit == false){
                checkNextBlock(x-1, y);
            }
            else if (xbit == true && ybit == false) {
                checkNextBlock(x+1, y);
            }
            else if (xbit == false && ybit == true) {
                checkNextBlock(x, y+1);
            }
            else{
                xbit = false;
                checkNextBlock(x, y+1);
            }
        }
        else{
            speedCounter += speed;
        }
    }
    protected void checkNextBlock(int x, int y){
        if(isInRange(0, board.getWidth()-1, x) && isInRange(0, board.getHeight()-1, y)){
            Block nextBlock = (Block)board.getBlock(y, x);
            switch (nextBlock.getType()) {
                case SOLID -> addBlocksToBoard();
                case NULL -> snakeBlocks.forceAdd(new SnakeBlock(x,y,currentColor));
                default -> {
                }
            }
        }
        else{
            addBlocksToBoard();
        }
        
    }
    protected void addBlocksToBoard(){
        while (snakeBlocks.isEmpty() == false) { 
            SnakeBlock removed = (SnakeBlock)snakeBlocks.remove();
            int x = removed.getX();
            int y = removed.getY();
            if(isInRange(0,board.getWidth()-1,x) && isInRange(0,board.getHeight()-1,y)){
                board.addBlock(new Block(BlockType.SOLID),y,x);
            }
            else{
                if (snakeBlocks.size() < 1) {
                    board.endGame();
                    break;
                }
                else{
                    speed += 0.02;
                }
            }
        }
        speedCounter = -20;
        setStatic();
        setBlocks((int)randomNumber(2, maxSize));
    }
    public boolean isStatic(){
        return xbit == true && ybit == true;
    }
    public void draw(Graphics2D g2d, int xCenter){
        int xCorner = xCenter-(board.getWidth()*(scale/2));
        Array snakeArray = snakeBlocks.toArray();
        for (int i = 0; i < snakeArray.size(); i++) {
            SnakeBlock block = (SnakeBlock)snakeArray.get(i);
            int x = block.getX();
            int y = block.getY();
            if(y >= 0){
                g2d.setColor(new Color(block.getPixel().toInteger()));
                g2d.fill3DRect((xCorner)+x*scale,(y*scale)+20,scale,scale,true);
            }
        }
    }
}
