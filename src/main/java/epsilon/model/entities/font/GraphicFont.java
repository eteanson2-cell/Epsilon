package epsilon.model.entities.font;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.HashTable;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.interfaces.IEntity;

public class GraphicFont implements IEntity{
    protected HashTable images;
    public int characterSpacing;
    public int lineSpacing;
    public double x;
    public double y;
    public GraphicFont(double x, double y){
        this.x = x;
        this.y = y;
        this.characterSpacing = 10;
        this.lineSpacing = 10;
        images = new HashTable((Object key) -> {
            GraphicChar gc = (GraphicChar)key;
            return gc.getChar();
        }, 256);
    }
    public void addChar(GraphicChar gc){
        images.add(gc);
    }
    public void draw(Graphics2D g2d, String[] text){
        draw(y, x, g2d, text);
    }
    public void draw(double yOffset, double xOffset, Graphics2D g2d, String[] text){
        double currentY = yOffset;
        for (String line : text) {
            double currentX = xOffset;
            double maxHeight = 0;
            for (int j = 0; j < line.length(); j++) {
                char letter = line.charAt(j);
                GraphicChar gc = (GraphicChar)images.get(letter);
                if(gc != null){
                    Point currentPoint = new Point(currentX, currentY);
                    gc.draw(currentPoint, g2d);
                    currentX += gc.getImage().getWidth();
                    if(gc.getImage().getHeight() > maxHeight){
                        maxHeight = gc.getImage().getHeight();
                    }
                }
                else if (letter == ' ') {
                    currentX += characterSpacing*2;
                }
                currentX += characterSpacing;
            }
            currentY += maxHeight + lineSpacing;
        }
    }

    public void rescale(double newScale){
        for (int i = 0; i < 256; i++) {
            GraphicChar gc = (GraphicChar)images.get(i);
            if(gc != null){
                gc.rescale(newScale);
                images.push(gc);
            }
        }
    }
    @Override
    public Point getCenter() {
        return new Point(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        draw(g2d, new String[1]);
    }

    @Override
    public void fill(Graphics2D g2d) {
        draw(g2d, new String[1]);
    }

    @Override
    public boolean intersects(IEntity entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public IEntity copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}