package epsilon.visual.panels;

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
    public int WIDTH = 320;
	public int HEIGHT = 240;
	public int SCALE = 2;
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
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
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

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
			if (wait < 0){
				wait = 0;
            }
            FrameCounter = FPS - ((int)(elapsed/1000000)/targetTime);
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	private void draw() {
		gsm.draw(g2d);
        g2d.drawString("FPS: " + FrameCounter, 20, 70);
	}
    private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
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