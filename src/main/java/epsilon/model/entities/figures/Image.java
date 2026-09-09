package epsilon.model.entities.figures;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import epsilon.model.dataStructure.nonLinearStructure.Array2D;
import epsilon.model.entities.figures.auxiliar.Pixel;
import epsilon.model.entities.interfaces.IEntity;

public class Image extends Figure{
    protected BufferedImage image;
    protected BufferedImage newImage;
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
            InputStream is = this.getClass().getResourceAsStream("/" + file);
            image = ImageIO.read(is);
            this.xCenter = xCenter;
            this.yCenter = yCenter;
            readImage();
        } catch (IOException e) {
            System.err.println(file + "was not found or there was an error");
            System.exit(1);
        }
    }
    public Array2D getDataPixel(){
        return dataPixel;
    }
    public void readImage(){
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
    public void createNewImage(){
        newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                newImage.setRGB(x, y, pixel.toInteger());
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
        Array2D nImage = new Array2D(height+(thickness*2), width+(thickness*2));
        nImage.fill(color);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = (Pixel)dataPixel.getObject(y, x);
                nImage.modify(pixel, y+thickness, x+thickness);
            }
        }
        dataPixel.redefine(nImage);
        reasignValues();
    }
    public void scaleImage(double heightScale, double widthScale){
        changeSize((int)Math.abs(height*heightScale), (int)Math.abs(width*widthScale));
    }
    public void changeSize(int heightScale, int widthScale){
        Array2D nImage = new Array2D(heightScale,widthScale);
        nImage.fill(new Pixel(0));
        for (int y = 0; y < heightScale; y++) {
            for (int x = 0; x < widthScale; x++) {
                int origY = (y*height)/heightScale;
                int origX =(x*width)/widthScale;
                Pixel pixel = (Pixel)dataPixel.getObject(origY, origX);
                nImage.modify(pixel, y, x);
            }
        }
    }
    public BufferedImage getBufferedImage(boolean create){
        if(create){
            createNewImage();
        }
        return newImage;
    }
    public void rotateRows(int rotations){
        dataPixel.rotateRows(rotations);
    }
    public void rotateColumns(int rotations){
        dataPixel.rotateColumns(rotations);
    }
    @Override
    public Point getCenter() {
        return new Point(xCenter,yCenter);
    }

    @Override
    public void draw(Graphics2D g2d) {
        draw(g2d,true);
    }
    public void draw(Graphics2D g2d, boolean create) {
        draw(g2d, null, create);
    }
    public void draw(Graphics2D g2d, BufferedImageOp op) {
        draw(g2d, op, true);
    }
    public void draw(Graphics2D g2d, BufferedImageOp op, boolean create) {
        g2d.drawImage(getBufferedImage(create), op, (int)Math.round(xCenter), (int)Math.round(yCenter));
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

    @Override
    public IEntity copy() {
        Image copy = new Image(xCenter, yCenter, height, width);
        copy.dataPixel = dataPixel.copy();
        copy.image = image;
        return copy;
    }
}