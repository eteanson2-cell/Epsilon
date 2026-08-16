package epsilon.controller.gameState.game1;

import java.awt.Graphics2D;

import epsilon.model.entities.Animation;
import epsilon.model.entities.figures.Figure;
import epsilon.model.entities.figures.Line;
import epsilon.model.entities.figures.Rectangle;
import epsilon.model.entities.figures.Sprite;
import epsilon.model.entities.interfaces.IEntity;

public class Player{
    protected Animation animation;
    protected Rectangle hitbox;
    protected double movementSpeed;
    protected int health;
    protected double speedX;
    protected double speedY;
    protected boolean left,right,up,down, controlEnable;
    protected Figure map;
    public Player(){
        left = false;
        right = false;
        up = false;
        down = false;
        controlEnable = true;
        speedX = 0;
        speedY = 0;
        movementSpeed = 2;
        hitbox = new Rectangle(50, 50, 25, 25);
        animation = new Animation();
        Sprite[] frames = new Sprite[1];
        Sprite sprite = new Sprite();
        sprite.addFigure(hitbox); 
        frames[0] = sprite;
        animation.setFrames(frames);
    }
    public boolean getControl(){
        return controlEnable;
    }
    public void setControl(boolean b){
        controlEnable = b;
    }
    public void setMap(Figure figure){
        this.map = figure;
    }
    public boolean intersects(IEntity entity){
        return hitbox.intersects(entity);
    }
    public void checkCollision(IEntity entity) {
        double xDest = hitbox.getXCenter()+speedX;
        double yDest = hitbox.getYCenter()+speedY;
        Rectangle hitboxTemp = new Rectangle(xDest,yDest,hitbox.getHeight(),hitbox.getWidth());
        if(speedY < 0){
            Line topLine = hitboxTemp.getTopLine();
            if(topLine.intersects(entity)){
                speedY = 0;
            }
        }
        else if(speedY > 0){
            Line bottomLine = hitboxTemp.getBottomLine();
            if(bottomLine.intersects(entity)){
                speedY = 0;
            }
        }
        if(speedX > 0){
            Line leftLine = hitboxTemp.getRightLine();
            if(leftLine.intersects(entity)){
                speedX = 0;
            }
        }
        else if(speedX < 0){
            Line rightLine = hitboxTemp.getLeftLine();
            if(rightLine.intersects(entity)){
                speedX = 0;
            }
        }
    }
    private void getNextPosition(){
        if(controlEnable){
            if (left) {
                speedX = -movementSpeed;
            }
            else if(right){
                speedX = +movementSpeed;
            }
            else{
                speedX = 0;
            }
            if(up){
                speedY = -movementSpeed;
            }
            else if(down){
                speedY = +movementSpeed;
            }
            else{
                speedY = 0;
            }
        }
        else{
            speedX = 0;
            speedY = 0;
        }
    }
    public void update() {

		getNextPosition();
		checkCollision(map);
        hitbox.move(speedX, speedY);
    }
    public void draw(Graphics2D g2d){
        animation.getSprite().draw(g2d);
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public int getX(){
        return (int)Math.round((float) hitbox.getXCenter());
    }
    public int getY(){
        return (int)Math.round((float) hitbox.getYCenter());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}