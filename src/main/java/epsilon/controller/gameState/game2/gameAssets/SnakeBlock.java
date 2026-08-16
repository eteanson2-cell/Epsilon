package epsilon.controller.gameState.game2.gameAssets;

import epsilon.model.entities.figures.auxiliar.Pixel;

public class SnakeBlock{
    protected int x, y;
    private Pixel pixel;
    public SnakeBlock(int x, int y){
        this.x = x;
        this.y = y;
    }
    public SnakeBlock(int x, int y, Pixel pixel){
        this.x = x;
        this.y = y;
        this.pixel = pixel;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Pixel getPixel() {
        return pixel;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setPixel(Pixel pixel) {
        this.pixel = pixel;
    }
}