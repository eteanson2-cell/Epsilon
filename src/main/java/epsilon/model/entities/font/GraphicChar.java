package epsilon.model.entities.font;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import epsilon.model.entities.figures.Image;
import epsilon.model.entities.figures.Point;

public class GraphicChar{
    protected Image img;
    protected BufferedImage finalImg;
    protected char letter;
    public GraphicChar(char letter, Image img) {
        this.letter = letter;
        this.img = img;
        finalImg = img.getBufferedImage(true);
    }
    public char getChar(){
        return letter;
    }
    public Image getImage(){
        return img;
    }
    public void rescale(double newScale){
        img.scaleImage(newScale, newScale);
        finalImg = img.getBufferedImage(true);
    }
    public void draw(Point center, Graphics2D g2d){
        g2d.drawImage(finalImg, null, (int)center.getX(), (int)center.getY());
    }
}