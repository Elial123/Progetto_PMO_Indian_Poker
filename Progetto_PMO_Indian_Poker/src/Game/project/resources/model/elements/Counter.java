package Game.project.resources.model.elements;

public class Counter implements ICounter{
	
	@Override
	public int inc(int fiches, int fichesWon) {
		return fiches + fichesWon;
	}
	@Override
	public int dec(int fiches, int fichesToGiveAway) {
		return fiches - fichesToGiveAway;
	}
}
