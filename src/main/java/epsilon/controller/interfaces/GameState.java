package epsilon.controller.interfaces;

import java.awt.Graphics2D;

public interface GameState{
    void init();
    void update();
    void draw(Graphics2D g2d);
    void keyPressed(int k);
    void keyReleased(int k);
    void keyTyped(int k);
    void mouseClicked(int x, int y, int button);
    void mousePressed(int x, int y, int button);
    void mouseReleased(int x, int y, int button);
    void mouseDragged(int x, int y, int button);
    void mouseMoved(int x, int y);
}