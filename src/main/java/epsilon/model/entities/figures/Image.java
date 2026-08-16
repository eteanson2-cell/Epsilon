package epsilon.model.entities.figures;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import epsilon.model.dataStructure.nonLinearStructure.Array2D;
import epsilon.model.entities.figures.auxiliar.Pixel;
import epsilon.model.entities.interfaces.IEntity;

public class Image extends Figure{
    protected BufferedImage image;
    protected Array2D dataPixel;
    protected int height;
    protected int width;
    protected double xCenter;
    protected double yCenter; 
    public Image(double xCenter, double yCenter, int height, int width){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.height = height;
        this.width = width;
        dataPixel = new Array2D(height, width);
        dataPixel.fill(new Pixel(0));
    }
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Image(double xCenter, double yCenter, String file){
        try {
            image = ImageIO.read(new File(this.getClass().getResource(file).getFile()));
            this.xCenter = xCenter;
            this.yCenter = yCenter;
            readImage();
        } catch (IOException e) {
            System.exit(1);
        }
    }
    protected void readImage(){
        height = image.getHeight();
        width = image.getWidth();
        dataPixel = new Array2D(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                dataPixel.modify( new Pixel(pixel),y, x);
            }
        }
    }
    public void toGray(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                pixel.toGray();
                dataPixel.modify(pixel, y, x);
            }
        }
    }
    public void increaseBrightness(int brightness){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                pixel.increaseBrigthness(brightness);
                dataPixel.modify(pixel, y, x);
            }
        }
    }
    public void rotateRight(){
        dataPixel.rotateRight();
        reasignValues();
    }
    public void rotateLeft(){
        dataPixel.rotateLeft();
        reasignValues();
    }
    public void rotate180(){
        dataPixel.rotate180();
    }
    public void transposeImage(){
        dataPixel.transposed();
        reasignValues();
    }
    public void addMargin(Pixel color, int thickness){
        thickness = Math.abs(thickness);
        Array2D newImage = new Array2D(height+(thickness*2), width+(thickness*2));
        newImage.fill(color);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                newImage.modify(pixel, y+thickness, x+thickness);
            }
        }
        dataPixel.redefine(newImage);
        reasignValues();
    }
    public void scaleImage(double heightScale, double widthScale){
        changeSize((int)Math.abs(height*heightScale), (int)Math.abs(width*widthScale));
    }
    public void changeSize(int heightScale, int widthScale){
        Array2D newImage = new Array2D(heightScale,widthScale);
        newImage.fill(new Pixel(0));
        for (int y = 0; y < heightScale; y++) {
            for (int x = 0; x < widthScale; x++) {
                int origY = (y*height)/heightScale;
                int origX =(x*width)/widthScale;
                Pixel pixel = (Pixel)dataPixel.getObject(origY, origX);
                newImage.modify(pixel, y, x);
            }
        }
    }
    public BufferedImage getBufferedImage(){
        BufferedImage newImage = new BufferedImage(width, height, 1);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                newImage.setRGB(x, y, pixel.toInteger());
            }
        }
        return newImage;
    }
    @Override
    public Point getCenter() {
        return new Point(xCenter,yCenter);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(getBufferedImage(), new BufferedImageOp() {
            @Override
            public BufferedImage filter(BufferedImage src, BufferedImage dest) {
                return src;
            }
            @Override
            public Rectangle2D getBounds2D(BufferedImage src) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            @Override
            public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            @Override
            public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            @Override
            public RenderingHints getRenderingHints() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }, (int)Math.round(xCenter), (int)Math.round(yCenter));
    }

    @Override
    public void fill(Graphics2D g2d) {
        draw(g2d);
    }

    @Override
    public boolean intersects(IEntity entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    protected void reasignValues(){
        this.height = dataPixel.getHeight();
        this.width = dataPixel.getWidth();
    }
}