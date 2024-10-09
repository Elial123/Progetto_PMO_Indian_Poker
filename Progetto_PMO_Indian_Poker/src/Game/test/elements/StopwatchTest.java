package Game.test.elements;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.GameStopwatch;

class StopwatchTest {
	private GameStopwatch gameStopwatch; 
	
	@BeforeEach                                         
    void setUp() {
		gameStopwatch = new GameStopwatch();
    }
	
	// test del metodo start() e getRemainingTime()
	@Test
	void testGetRemainigTime() {
		gameStopwatch.start();
		Assert.assertEquals(0, gameStopwatch.getTime());
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		gameStopwatch.stop();
		Assert.assertEquals(2, gameStopwatch.getTime()/1000);
		
		gameStopwatch.reset();
		gameStopwatch.start();
		Assert.assertEquals(0, gameStopwatch.getTime()/1000);
	}

}