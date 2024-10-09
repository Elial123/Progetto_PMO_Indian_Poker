package Game.project.resources.model.elements;

public class GameStopwatch implements IGameStopwatch{
	private long startTime;
    private long elapsedTime;
    private boolean running;

    public GameStopwatch() {
        this.elapsedTime = 0;
        this.running = false;
    }

    @Override
    public void start() {
        if (!running) {
            this.startTime = System.currentTimeMillis();
            this.running = true;
        }
    }

    @Override
    public void stop() {
        if (running) {
            elapsedTime += System.currentTimeMillis() - startTime;
            running = false;
        }
    }

    @Override
    public long getTime() {
        if (running) {
            return elapsedTime + (System.currentTimeMillis() - startTime);
        } else {
            return elapsedTime;
        }
    }

    @Override
    public void reset() {
        elapsedTime = 0;
    }
}
