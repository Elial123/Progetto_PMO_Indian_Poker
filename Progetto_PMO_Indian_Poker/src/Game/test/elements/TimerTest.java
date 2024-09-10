package Game.test.elements;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.GameTimer;

class TimerTest {
	private GameTimer gameTimer; 
	
	@BeforeEach                                         
    void setUp() {
		gameTimer = new GameTimer();
    }
	
	// test del metodo start() e getRemainingTime()
	@Test
	void testGetRemainigTime() {
		gameTimer.start();
		Assert.assertEquals(100, gameTimer.getRemainingTime());
	}

}
