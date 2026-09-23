package epsilon.controller.gameState.game3.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Polygon;

public class PauseMenu extends GameMenu{
    private final Polygon arrow;
    private RedFontManager rfm;
    public PauseMenu(Array options){
        super(options);
        arrow = new Polygon(new double[]{50,100,50},new double[]{100,125,150});
        arrow.moveFromCenter(150, 190);
        arrow.setInsideColor(new Color(255, 255, 255));
    }
    public void setFontManager(RedFontManager rfm){
        this.rfm = rfm;
    }
    @Override
    public void changeOption(byte optionNumber) {
        super.changeOption(optionNumber);
        arrow.moveFromCenter(150, 190+(this.optionNumber*100));
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
            g2d.setFont(new Font("",Font.PLAIN,24));
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(0, 0, 640, 480);
            g2d.setColor(new Color(255, 255, 255));
            String[] text = {"RESUME", "RESTART", "EXIT"};
            rfm.draw(220, 180, g2d, text);
            arrow.fill(g2d);
        }
    }

}