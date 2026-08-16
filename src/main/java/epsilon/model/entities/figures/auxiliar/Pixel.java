package epsilon.model.entities.figures.auxiliar;

import epsilon.model.dataStructure.linearStructure.statik.Array;

public class Pixel{
    private int alpha;
    private int red;
    private int green;
    private int blue;
    public Pixel(int alpha, int red, int green, int blue){
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    public Pixel(int bit32){
        int[] splitedPixel = splitInt(bit32);
        this.alpha = splitedPixel[0];
        this.red = splitedPixel[1];
        this.green = splitedPixel[2];
        this.blue = splitedPixel[3];
    }
    private int[] splitInt(int pixel){
        int[] subpixels = new int[4];
        String bits = Integer.toBinaryString(pixel);
        while(bits.length() != 32){
            bits = "0" + bits; 
        }
        String sbyte = "";
        int index = 0;
        for (int i = 1; i <= 32; i++) {
            sbyte = sbyte + bits.charAt(i-1);
            if(i%8 == 0){
                @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
                Integer numero = Integer.parseInt(sbyte,2);
                subpixels[index] = numero;
                sbyte = "";
                index++;
            }
        }
        return subpixels;
    }
    public int toInteger(){
        int colorInt = (alpha << 24) | (red << 16) | (green << 8) | blue;
        return colorInt;
    }
    public int getAlpha() {
        return alpha;
    }
    public int getRed() {
        return red;
    }
    public int getGreen() {
        return green;
    }
    public int getBlue() {
        return blue;
    }
    public void setAlpha(int alpha) {
        this.alpha = byteLimit(alpha);
    }
    public void setRed(int red) {
        this.red = byteLimit(red);
    }
    public void setGreen(int green) {
        this.green = byteLimit(green);
    }
    public void setBlue(int blue) {
        this.blue = byteLimit(blue);
    }
    private int byteLimit(int subpixel){
        if(subpixel > 255){
            subpixel = 255;
        }
        else if(subpixel < 0){
            subpixel = 0;
        }
        return subpixel;
    }
    public void toGray(){
        int average = (int)((red+green+blue)/3);
        setRed(average);
        setBlue(average);
        setGreen(average);
    }
    public void increaseBrigthness(int brigthness){
        setRed(red+brigthness);
        setBlue(blue+brigthness);
        setGreen(green+brigthness);
    }
    public Array createGradient(Pixel pixel, int size){
        Array gradient = new Array(size);
        gradient.add(this);
        while (gradient.isFilled() == false) { 
            int newAlpha = ((getAlpha()*(size-gradient.getQuantity()))+(pixel.getAlpha()*(gradient.getQuantity())))/size;
            int newRed = ((getRed()*(size-gradient.getQuantity()))+(pixel.getRed()*(gradient.getQuantity())))/size;
            int newGreen = ((getGreen()*(size-gradient.getQuantity()))+(pixel.getGreen()*(gradient.getQuantity())))/size;
            int newBlue = ((getBlue()*(size-gradient.getQuantity()))+(pixel.getBlue()*(gradient.getQuantity())))/size;
            Pixel tempPixel = new Pixel(newAlpha,newRed,newGreen,newBlue);
            gradient.add(tempPixel);
        }
        gradient.add(pixel);
        return gradient;
    }
}