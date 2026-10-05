package epsilon.visual.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import epsilon.controller.GameStateManager;

public class GraphicPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
    public int panelWidth = 320;
	public int panelHeight = 240;
	public int panelScale= 2;
    protected Thread thread;
	protected boolean running;
	protected final int FPS = 60;
	protected final long targetTime = 1000 / FPS;
    protected long FrameCounter;
    protected BufferedImage image;
	protected Graphics2D g2d;
    protected GameStateManager gsm;
    public GraphicPanel(){
        super();
        setPreferredSize(new Dimension(panelWidth * panelScale, panelHeight * panelScale));
		setFocusable(true);
		requestFocus();
    }
    public GameStateManager getGameStateManager(){
        return gsm;
    }
    public void setGameStateManager(GameStateManager gsm){
        this.gsm = gsm;
    }
    @Override
    public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
            addMouseListener(this);
			thread.start();
		}
	}
    protected void init() {

		image = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();

		running = true;
        FrameCounter = 60;
		gsm = new GameStateManager();

	}
    private void update() {
		gsm.update();
	}
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {

		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
            int counter = 2;
			while (wait < 0){
                update();
                elapsed = System.nanoTime() - start;
                wait = (targetTime*counter) - elapsed / 1000000;
                counter++;
            }
            FrameCounter = FPS - ((int)(elapsed/1000000)/targetTime);
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
                running = false;
			}

		}

	}
	private void draw() {
		gsm.draw(g2d);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString("FPS: " + FrameCounter, 20, 70);
	}
    private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, panelWidth * panelScale, panelHeight * panelScale, null);
		g2.dispose();
	}

    public int getpanelWidth() {
        return panelWidth;
    }

    public void setpanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    public int getpanelHeight() {
        return panelHeight;
    }

    public void setpanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }
	@Override
    public void keyTyped(KeyEvent key) {
        gsm.keyTyped(key.getKeyCode());
	}

    @Override
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

    @Override
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

    @Override
    public void mouseClicked(MouseEvent e) {
        gsm.mouseClicked(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gsm.mousePressed(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gsm.mouseReleased(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        gsm.mouseDragged(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gsm.mouseMoved(e.getX(), e.getY());
    }
}