package epsilon.visual.panels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import epsilon.controller.GameStateManager;
import epsilon.controller.gameState.game3.gameAssets.MagnetClimbState;

public class Game3Panel extends GraphicPanel{
    public int WIDTH = 640;
    public int HEIGHT = 480;
    public int SCALE = 1;
    public Game3Panel(){
        super();
    }
    @Override
    protected void init() {
        
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();
		gsm.addGameState(new MagnetClimbState(gsm));
        gsm.setState(0);

	}
}