package epsilon.controller.gameState.game3.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.audio.AudioTrack;
import epsilon.model.entities.figures.Polygon;
import static epsilon.utils.FunctionUtils.displaySFX;

public abstract class Game3Menu extends GameMenu{
    protected Polygon highlight;
    protected RedFontManager rfm;
    protected AudioTrack menuSFX;
    protected AudioTrack menuSFX2;
    public Game3Menu(Array options) {
        super(options);
        highlight = new Polygon(
            new double[]{0,700,700,0}, 
            new double[]{0,0,75,75});
        highlight.setInsideColor(new Color(0,255,255,128));
        highlight.moveFromCenter(350, 190);
        menuSFX = new AudioTrack("menuSfx.wav");
        menuSFX.readFile();
        menuSFX2 = new AudioTrack("menuSfx2.wav");
        menuSFX2.readFile();
    }
    public void setFontManager(RedFontManager rfm){
        this.rfm = rfm;
    }
    @Override
    public void changeOption(byte optionNumber) {
        super.changeOption(optionNumber);
        highlight.moveFromCenter(350, 190+(this.optionNumber*160));
    }
    @Override
    public boolean showWarning() {
        return false;
    }
    public AudioTrack getMenuSFX(){
        return menuSFX;
    }
    @Override
    public void selectOption() {
        super.selectOption();
        changeOption((byte)0);
        isEnabled = false;
    }
    @Override
    public void KeyPressed(int k) {
        if(isEnabled){
            if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN){
                changeOption((byte)(optionNumber+1));
                displaySFX(menuSFX2);
            }
            if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP){
                changeOption((byte)(optionNumber-1));
                displaySFX(menuSFX2);
            }
            if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_ENTER){
                displayMainSFX();
                selectOption();
            }
        }
    }
    @Override
    public void KeyTyped(int k) {
        
    }
    public void displayMainSFX(){
        displaySFX(menuSFX);
    }
    @Override
    public void KeyReleased(int k) {
    }
    public void draw(Graphics2D g2d){
        if(isEnabled){
            if(rfm != null){
                drawFont(g2d);
            }
            highlight.fill(g2d);
        }
    }
    public abstract void drawFont(Graphics2D g2d);
}