package epsilon.controller.gameState.game3.menu;

import java.awt.Graphics2D;

import epsilon.model.dataStructure.nonLinearStructure.Array2D;
import epsilon.model.entities.figures.Image;
import epsilon.model.entities.figures.auxiliar.Pixel;
import epsilon.model.entities.font.GraphicChar;
import epsilon.model.entities.font.GraphicFont;

public class RedFontManager{
    GraphicFont redFont;
    String chars;
    public RedFontManager(double x, double y){
        redFont = new GraphicFont(x, y);
        chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890:";
    }
    public void setCharSpacing(int charSpacing){
        redFont.characterSpacing = charSpacing;
    }
    public void setLineSpacing(int lineSpacing){
        redFont.lineSpacing = lineSpacing;
    }
    public void readImage(String file){
        Image textureOutlast = new Image(redFont.getCenter().getX(), redFont.getCenter().getY(), file);
        textureOutlast.interchangePixel(new Pixel(191,255,0,0), new Pixel(191,0,255,255), 10);
        Array2D dataPixel = textureOutlast.getDataPixel();
        int minX = 0;
        int width = 1;
        int stringCounter = 0;
        for (int column = 1; column < dataPixel.getWidth(); column++) {
            for (int row = 0; row < dataPixel.getHeight() && stringCounter < chars.length(); row++) {
                Pixel currentPixel = (Pixel)dataPixel.getObject(row, column);
                if(currentPixel.getAlpha() > 0){
                    break;
                }
                if(row == dataPixel.getHeight()-1){
                    if(width > 1){ 
                        Image gChar = textureOutlast.getSubimage(0, minX, dataPixel.getHeight()-1, width);
                        redFont.addChar(new GraphicChar(chars.charAt(stringCounter), gChar));
                        stringCounter++;
                    }
                    minX = column;
                    width = 0;
                }
            }
            width++;
        }
    }
    public void rescale(double newScale){
        redFont.rescale(newScale);
    }
    public void draw(Graphics2D g2d, String[] text){
        redFont.draw(g2d, text);
    }
    public void draw(double x, double y, Graphics2D g2d, String[] text){
        redFont.draw(y, x, g2d, text);
    }
}