package epsilon.model.entities;

import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Sprite;

public class Animation {

	private Array frames;
	private int currentFrame;

	private long startTime;
	private long delay;

	private boolean playedOnce;
    private boolean loop;

	public Animation() {
		playedOnce = false;
	}
	public Sprite getSprite(){
		return (Sprite)frames.get(currentFrame);
	}
	public void setFrames(Sprite[] frames) {
		for (Sprite sprite : frames) {
			this.frames.add(sprite);
		}
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	public void setDelay(long d) {
		delay = d;
	}

	public void setFrame(int i) {
		currentFrame = i;
	}

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

	public void update() {

		if (delay == -1)
			return;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.size()) {
			playedOnce = true;
			if(isLooping()){
				currentFrame = 0;
			}
			else{
				currentFrame--;
			}
			
		}

	}

	public int getFrame() {
		return currentFrame;
	}

	public  Sprite getImage() {
		return (Sprite)frames.get(currentFrame);
	}

	public boolean hasPlayedOnce() {
		return playedOnce;
	}

    public boolean isLooping() {
        return loop;
    }



}