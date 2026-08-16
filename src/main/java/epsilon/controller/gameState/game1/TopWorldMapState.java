package epsilon.controller.gameState.game1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import epsilon.controller.GameStateManager;
import epsilon.controller.interfaces.GameState;
import epsilon.model.entities.Camera;
import epsilon.model.entities.figures.Polygon;
import epsilon.model.enums.StretchingPoint;
import static epsilon.utils.FunctionUtils.isInRange;

public class TopWorldMapState implements GameState{
    @SuppressWarnings("unused")
    private final GameStateManager gsm;
	private Player player;
    private Polygon map; 
    private Camera camera;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TopWorldMapState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
    }
    @Override
    public void init() {
        player = new Player();
        camera = new Camera(160,120,320,240);
        map = new Polygon(new double[]{10,120,120,150,150,300,300,275,275,250,250,300,300, 30, 30, 10, 10,  0,  0,320,320,  0,  0}, 
                          new double[]{10, 10, 45, 45, 10, 10, 80, 80, 50, 50,110,110,220,220,200,200, 10, 10,  0,  0,240,240, 10});
        map.resizeXAxis(2, StretchingPoint.LEFT);
        map.resizeYAxis(2, StretchingPoint.UP);
        player.setMap(map);
    }

    @Override
    public void update() {
        player.update();
        camera.update();
        if(camera.getDX() == 0 && camera.getDY() == 0 && camera.isOnCamera(player.getHitbox()) == false){
            camera.setMoveLaps(10);
            repositionCamera();
        }
        if(camera.getDX() == 0 && camera.getDY() == 0){
            player.setControl(true);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(-(camera.getX()-160),-(camera.getY()-120));
        g2d.setColor(new Color(0,0,0));
        g2d.fillRect((int)(camera.getX()-320), (int)(camera.getY()-240), 640, 480);
        g2d.setColor(new Color(128,0,255));
        player.draw(g2d);
        camera.draw(g2d);
        map.fill(g2d);
        if(map.intersection(player.getHitbox().toPolygon()) != null){
            map.intersection(player.getHitbox().toPolygon()).draw(g2d);
        }
        g2d.translate(camera.getX()-160,camera.getY()-120);
    }
    public void repositionCamera(){
        if(isInRange(camera.getX()-(camera.getWidth()/2),camera.getX()+(camera.getWidth()/2),player.getX()) == false){
            if(camera.getX() < player.getX()){
                camera.setDX(camera.getWidth()/camera.getMoveLaps());
                //camera.move(camera.getWidth(), 0);
            }
            else if(camera.getX() > player.getX()){
                camera.setDX(-camera.getWidth()/camera.getMoveLaps());
                //camera.move(-camera.getWidth(), 0);
            }
        }
        if(isInRange(camera.getY()-(camera.getHeight()/2),camera.getY()+(camera.getHeight()/2),player.getY()) == false){
            if(camera.getY() < player.getY()){
                //camera.move(0,camera.getHeight());
                camera.setDY(camera.getHeight()/camera.getMoveLaps());
            }
            else if(camera.getY() > player.getY()){
                camera.setDY(-camera.getHeight()/camera.getMoveLaps());
                //camera.move(0,-camera.getHeight());
            }
        }
    }
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_A)
			player.setLeft(true);
		if (k == KeyEvent.VK_D)
			player.setRight(true);
		if (k == KeyEvent.VK_S)
			player.setDown(true);
		if (k == KeyEvent.VK_W)
			player.setUp(true);
		if (k == KeyEvent.VK_UP)
			player.setUp(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_A)
			player.setLeft(false);
		if (k == KeyEvent.VK_D)
			player.setRight(false);
		if (k == KeyEvent.VK_D);
			player.setDown(false);
		if (k == KeyEvent.VK_W)
			player.setUp(false);
		if (k == KeyEvent.VK_UP)
			player.setUp(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
    }

    @Override
    public void keyTyped(int k) {
        
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(int x, int y, int button) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(int x, int y, int button) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}