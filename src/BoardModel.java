
public class BoardModel {
	private int boardFields[][];
	private Level level;
	private int timeElapsed = 0;
	private int minesRemaining;
	private int flagsRemaining;
	
	BoardModel(Level level) {
		this.level = level;
		minesRemaining = flagsRemaining = level.getMines();
	}

	public void flag(int coordinateX, int coordinateY) {
		
	}
	
	public void uncover(int coordinateX, int coordinateY) {
		
	}
	
	public int getWidth() {
		return level.getWidth();
	}
	
	public int getHeight() {
		return level.getHeight();
	}
	
	public int getMinesRemaining() {
		return minesRemaining;
	}
	
	public int getFlagsRemaining() {
		return flagsRemaining;
	}
}
