package epsilon.controller.gameState.game3.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Polygon;

public abstract class Game3Menu extends GameMenu{
    protected Polygon highlight;
    protected RedFontManager rfm;
    public Game3Menu(Array options) {
        super(options);
        highlight = new Polygon(
            new double[]{0,700,700,0}, 
            new double[]{0,0,75,75});
        highlight.setInsideColor(new Color(0,255,255,128));
        highlight.moveFromCenter(350, 190);
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
    @Override
    public void selectOption() {
        super.selectOption();
        changeOption((byte)0);
        isEnabled = false;
    }
    @Override
    public void KeyPressed(int k) {
        if(isEnabled){
            if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN)
                changeOption((byte)(optionNumber+1));
            if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP)
                changeOption((byte)(optionNumber-1));
            if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_ENTER)
                selectOption();
        }
    }
    @Override
    public void KeyTyped(int k) {
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