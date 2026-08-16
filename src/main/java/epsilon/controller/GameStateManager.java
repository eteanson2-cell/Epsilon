package epsilon.controller;

import java.awt.Graphics2D;

import epsilon.controller.interfaces.GameState;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;

public class GameStateManager{
    private final LinkedList gameStates;
    private int currentState;
    public GameStateManager(){
        gameStates = new LinkedList();
    }
    public void addGameState(GameState gs){
        gameStates.add(gs);
    }
    public void setState(int state) {
		currentState = state;
		GameState gameState = (GameState)gameStates.get(currentState);
		gameState.init();
	}
	public GameState getState(int state) {
		return (GameState)gameStates.get(state);
	}
	public void update() {
		((GameState)gameStates.get(currentState)).update();
	}

	public void draw(Graphics2D g) {
		((GameState)gameStates.get(currentState)).draw(g);
	}

	public void keyPressed(int k) {
		((GameState)gameStates.get(currentState)).keyPressed(k);
	}

	public void keyReleased(int k) {
		((GameState)gameStates.get(currentState)).keyReleased(k);
	}

    public void keyTyped(int k) {
		((GameState)gameStates.get(currentState)).keyTyped(k);
	}
	public void mouseClicked(int x, int y, int button){
		((GameState)gameStates.get(currentState)).mouseClicked(x, y, button);
	}
    public void mousePressed(int x, int y, int button){
		((GameState)gameStates.get(currentState)).mousePressed(x, y, button);
	}
    public void mouseReleased(int x, int y, int button){
		((GameState)gameStates.get(currentState)).mouseReleased(x, y, button);
	}
    public void mouseDragged(int x, int y, int button){
		((GameState)gameStates.get(currentState)).mouseDragged(x, y, button);
	}
    public void mouseMoved(int x, int y){
		((GameState)gameStates.get(currentState)).mouseMoved(x, y);
	}
}