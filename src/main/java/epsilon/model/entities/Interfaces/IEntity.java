package epsilon.model.entities.interfaces;

import java.awt.Graphics2D;

import epsilon.model.entities.figures.Point;

public interface IEntity{
    Point getCenter();
    void draw(Graphics2D g2d);
    void fill(Graphics2D g2d);
    boolean intersects(IEntity entity);
    void move(double x, double y);
    IEntity copy();
}