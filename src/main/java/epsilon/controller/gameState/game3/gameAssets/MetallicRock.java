package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.entities.figures.Oval;
import epsilon.model.entities.figures.Point;

public final class MetallicRock{
    Oval circle;
    double pullRate;
    public MetallicRock(double xcenter, double ycenter){
        circle = new Oval(xcenter, ycenter, 15);
        init();
    }
    public void init(){
        circle.setInsideColor(Color.RED); 
        pullRate = 0.5;
    }
    public Oval getCircle(){
        return circle;
    }
    public double getPullRate(){
        return pullRate;
    }
    public boolean intersects(Point point){
        return circle.intersects(point);
    }
    public double getXCenter(){
        return circle.getXCenter();
    }
    public double getYCenter(){
        return circle.getYCenter();
    }
    public void draw(Graphics2D g2d){
        circle.fill(g2d);
    }
    
}