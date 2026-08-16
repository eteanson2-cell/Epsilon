package epsilon.visual.panels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game1.TopWorldMapState;

public class GraphicPT extends GraphicPanel{
    @Override
    protected void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();
        gsm.addGameState(new TopWorldMapState(gsm));

	}
}