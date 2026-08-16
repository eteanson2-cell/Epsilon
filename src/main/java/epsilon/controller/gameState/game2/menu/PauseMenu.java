package epsilon.controller.gameState.game2.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Polygon;
import epsilon.model.enums.StretchingPoint;

public class PauseMenu extends GameMenu{
    private final Polygon arrow;
    public PauseMenu(Array options){
        super(options);
        arrow = new Polygon(new double[]{100,75,75,50,50,75,75},new double[]{100,150,125,125,75,75,50});
        arrow.resizeYAxis(0.5, StretchingPoint.CENTER);
        arrow.setInsideColor(new Color(255, 255, 255));
    }
    @Override
    public void changeOption(byte optionNumber) {
        super.changeOption(optionNumber);
        arrow.moveFromCenter(75, 95+(this.optionNumber*50));
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
            g2d.setFont(new Font("",Font.PLAIN,12));
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(0, 0, 600, 400);
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("RESUME", 125, 100);
            g2d.drawString("RESTART", 125, 150);
            g2d.drawString("EXIT", 125, 200);
            arrow.fill(g2d);
        }
    }

}