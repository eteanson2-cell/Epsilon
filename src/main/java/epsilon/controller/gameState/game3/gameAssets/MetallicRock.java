package epsilon.controller.gameState.game3.gameAssets;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.entities.figures.Oval;
import epsilon.model.entities.figures.Point;

public final class MetallicRock{
    Oval circle;
    int angle;
    double pullRate;
    public MetallicRock(double xcenter, double ycenter){
        circle = new Oval(xcenter, ycenter, 30);
        init();
    }
    public void init(){
        angle = 0;
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
    public void update(){
        angle = (angle+15)%360;
    }
    public void draw(Graphics2D g2d){
        Oval graphicCircle = new Oval(circle.getXCenter(),circle.getYCenter(),15);
        graphicCircle.setInsideColor(new Color(255, 0, 0));
        graphicCircle.fill(g2d);
    }
}