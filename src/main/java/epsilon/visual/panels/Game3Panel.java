package epsilon.visual.panels;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game3.gameAssets.MagnetClimbState;
import epsilon.controller.gameState.game3.menu.MenuState;
import epsilon.controller.gameState.game3.menu.RedFontManager;

public class Game3Panel extends GraphicPanel{
    protected RedFontManager rfm;
    public Game3Panel(){
        super();
        panelWidth = 640;
        panelHeight = 480;
        panelScale = 1;
        setPreferredSize(new Dimension(panelWidth * panelScale, panelHeight * panelScale));
        setFocusable(true);
		requestFocus();
    }
    @Override
    protected void init() {
        rfm = new RedFontManager(0,0);
        rfm.readImage("redFont.png");
        rfm.rescale(0.25);
        rfm.setLineSpacing(65);
        rfm.setCharSpacing(1);

		image = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();
        MagnetClimbState mcs = new MagnetClimbState(gsm);
        mcs.setRedFontManager(rfm);
        MenuState ms = new MenuState(gsm);
        ms.setRedFontManager(rfm);
		gsm.addGameState(mcs);
        gsm.addGameState(ms);
        gsm.setState(1);

	}
}