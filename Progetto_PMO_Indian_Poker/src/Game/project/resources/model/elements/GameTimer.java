package Game.project.resources.model.elements;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements IGameTimer{
	private Timer timer;
    private int i = 150;
    
    public GameTimer() {
    	this.timer = new Timer();
    }
    
    @Override
	public void start(){
		timer.schedule(new TimerTask() {

		public void run() {	
		    //System.out.println("Time: "+i);
		    i--;
		    if (i < 0) {
		        timer.cancel();
		        //System.out.println("is Over");
		     }
		    }
		 }, 1000, 1000);// delay,period entrambi in ms	  
	}
	@Override
	public int getRemainingTime() {
		return i;
	}		
}
