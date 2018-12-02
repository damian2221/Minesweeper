import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

public class BoardModel {
	final private BoardField boardFields[][];
	final private Level level;
	private int timeElapsed = 0;
	private int minesRemaining;
	private int flagsRemaining;
	final List<Integer> randomMinesPositions;
	/* latestNotUsedX and latestNotUsedY are both -1 when board is fully initialized*/
	private int latestNotUsedX = 1;
	private int latestNotUsedY = 1;
	final private ControlPanel controlPanel;
	
	BoardModel(Level level, ControlPanel controlPanel) {
		this.level = level;
		minesRemaining = flagsRemaining = level.getMines();
		randomMinesPositions = randomizeMinesPositions();
		boardFields = new BoardField[getWidth()][getHeight()];

		this.controlPanel = controlPanel;
		this.controlPanel.startTimer(new TimeListener() {
			public void timeChanged(int time) {
				
			}
		});
		controlPanel.updateFlagCounter(flagsRemaining);
	}
	
	

	public void flag(int coordinateX, int coordinateY) {
		getBoardField(coordinateX, coordinateY).flag();
	}
	
	public void flag(boolean isMine) {
		flagsRemaining--;
		controlPanel.updateFlagCounter(flagsRemaining);
		if (isMine) {
			minesRemaining--;
		}
	}
	
	public void uncover(int coordinateX, int coordinateY) {
		
	}
	
	public void addBoardField(JButton fieldButton) {
		if (isBoardInitialized()) {
			//error
		}
		int position = (10*(latestNotUsedY-1))+latestNotUsedX;
		if (randomMinesPositions.contains(position)) {
			boardFields[latestNotUsedX-1][latestNotUsedY-1] = new MineField(fieldButton, this);
			fieldButton.setText("1");
		} else {
			boardFields[latestNotUsedX-1][latestNotUsedY-1] = new SafeField(fieldButton, this);
		}
		
		if (latestNotUsedY >= getHeight()) {
			if (latestNotUsedX >= getWidth()) {
				checkIfBoardIsValid();
				latestNotUsedX = -1;
				latestNotUsedY = -1; 
			} else {
				latestNotUsedY = 1;
				latestNotUsedX++;
			}
		} else {
			latestNotUsedY++;
		}
	}
	
	private List<Integer> randomizeMinesPositions() {
		final int totalFields = getWidth()*getHeight();
		final List<Integer> fieldsPositions = new ArrayList<Integer>(totalFields);
		for (int i = 1; i <= totalFields; i++) {
			fieldsPositions.add(i);
		}
		Collections.shuffle(fieldsPositions);
		
		return fieldsPositions.subList(0, getMinesRemaining()-1);
	}
	
	private void checkIfBoardIsValid() {
		
	}
	
	public boolean isMine(int coordinateX, int coordinateY) {
		return getBoardField(coordinateX, coordinateY) instanceof MineField;
	}
	
	private BoardField getBoardField(int coordinateX, int coordinateY) {
		return boardFields[coordinateX-1][coordinateY-1];
	}
	
	private boolean isBoardInitialized() {
		return latestNotUsedX == -1 || latestNotUsedY == -1;
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
