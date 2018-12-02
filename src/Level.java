public enum Level {
	EASY(10, 10, 10),
	MEDIUM(20, 20, 50),
	HARD(30, 20, 100);
	
	final private int width;
	final private int height;
	final private int mines;
	
	Level(int width, int height, int mines) {
		this.width = width;
		this.height = height;
		this.mines = mines;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getMines() {
		return mines;
	}
}
