package epsilon.controller.gameState.game2.gameAssets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.nonLinearStructure.Array2D;
import static epsilon.utils.FunctionUtils.isInRange;

public class Board{
    protected Array2D board;
    protected int height, width;
    protected long score;
    protected boolean active;
    protected int scale;
    public Board(int height, int width){
        active = true;
        board = new Array2D(height,width);
        board.fill(new Block(BlockType.NULL));
        this.height = height;
        this.width = width;
        score = 0;
        scale = 10;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public Object getBlock(int row, int column){
        return board.getObject(row, column);
    } 
    public boolean addBlock(Block newBlock, int y, int x){
        if(isInRange(0,height-1,y) && isInRange(0,width-1,x)){
            board.modify(newBlock, y, x);
            score += 10;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public long getScore(){
        return score;
    }
    public void setScore(long score){
        this.score = score;
    }
    public void setScale(int scale){
        this.scale = scale;
    }
    public void scanRows(){
        for (int row = 0; row < height; row++) {
            Array rowLine = board.getRow(row);
            if(scanRow(rowLine) == true){
                board.removeRow(row);
                Array newRow = new Array(width);
                newRow.fill(new Block(BlockType.NULL));
                board.insertRow(newRow, 0);
                score += 1000;
            }
        }
    }
    public void update(){
        scanRows();
    }
    private boolean scanRow(Array array){
        int counter = array.count(new Block(BlockType.SOLID));
        return (counter >= width);
    }
    public void restartGame(){
        board.refill(new Block(BlockType.NULL));
        setActive(true);
        score = 0;
    }
    public void endGame(){
        active = false;
    }
    public void draw(Graphics2D g2d, int xCenter){
        int x = xCenter-((width*(scale/2)));
        g2d.setColor(new Color(0,0,0));
        g2d.fillRect(100, 0, 900, 100);
        g2d.setColor(new Color(255,255,255));
        g2d.setFont(new Font("",Font.PLAIN,12));
        g2d.drawString("Score:" + score, 0, 15);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Block block = (Block)board.getObject(row, column);
                if(block.getType() == BlockType.NULL){
                    g2d.setColor(new Color(0,0,0));
                    g2d.fillRect(x+(column*scale), (row*scale)+20, scale, scale);
                    g2d.setColor(new Color(25,25,25));
                    g2d.drawRect(x+(column*scale), (row*scale)+20, scale, scale);
                }
                else{
                    g2d.setColor(new Color(128,128,128));
                    g2d.fillRect(x+(column*scale), (row*scale)+20, scale, scale);
                    g2d.setColor(new Color(255,255,255));
                    g2d.drawRect(x+(column*scale), (row*scale)+20, scale, scale);
                }
            }
        }
        g2d.setColor(new Color(255,255,255));
        g2d.drawRect(x, 20, width*scale, height*scale);
    }
}