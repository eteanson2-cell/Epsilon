package epsilon.controller.gameState.game3.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;

public class PauseMenu extends Game3Menu{
    public PauseMenu(Array options){
        super(options);
    }
    @Override
    public void draw(Graphics2D g2d){
        if(isEnabled){
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(0, 0, 640, 480);
            super.draw(g2d);
        }
    }
    @Override
    public void changeOption(byte optionNumber) {
        super.changeOption(optionNumber);
        highlight.moveFromCenter(350, 190+(this.optionNumber*96));
    }
    @Override
    public void drawFont(Graphics2D g2d) {
        String[] text = {"RESUME", "RESTART", "EXIT"};
        rfm.draw(220, 180, g2d, text);
    }

}