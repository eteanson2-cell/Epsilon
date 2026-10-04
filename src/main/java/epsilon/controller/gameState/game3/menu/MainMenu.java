package epsilon.controller.gameState.game3.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.statik.Array;

public class MainMenu extends Game3Menu{
    public MainMenu(Array options) {
        super(options);
    }

    @Override
    public void init(){
        isEnabled = true;
    }
    @Override
    public void draw(Graphics2D g2d){
        if(isEnabled){
            g2d.setColor(new Color(0, 0, 0, 255));
            g2d.fillRect(0, 0, 640, 480);
            super.draw(g2d);
        }
    }
    @Override
    public void drawFont(Graphics2D g2d) {
        rfm.draw(220, 180, g2d, new String[]{"START","","EXIT"});
    }
}