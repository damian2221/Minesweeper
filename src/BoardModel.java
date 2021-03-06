import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BoardModel {
	static final private int LOSE_GAME_TIME = 999;
	
	final private BoardField boardFields[][];
	final private Level level;
	private int minesRemaining;
	private int flagsRemaining;
	private final List<Integer> randomMinesPositions;
	private boolean isGameFinished = false;
	private boolean isGameLost = false;
	final private ControlPanel controlPanel;
	final private JFrame frame;
	
	/* latestNotUsedX and latestNotUsedY are both -1 when board is fully initialized*/
	private int latestNotUsedX = 1;
	private int latestNotUsedY = 1;
	
	BoardModel(Level level, ControlPanel controlPanel, JFrame frame) {
		this.level = level;
		minesRemaining = flagsRemaining = level.getMines();
		randomMinesPositions = randomizeMinesPositions();
		boardFields = new BoardField[getWidth()][getHeight()];
		this.frame = frame;
		this.controlPanel = controlPanel;
		initializeControlPanel();
	}
	
	final private void initializeControlPanel() {
		controlPanel.startTimer(new TimeListener() {
			public void timeChanged(int time) {
				if (time >= LOSE_GAME_TIME) {
					loseGame();
				}
			}
		});
		controlPanel.addWinnersBoardFunction(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	(new Scores(frame, level)).openWinnersBoard(-1);
            }
		});
		controlPanel.updateFlagCounter(flagsRemaining);
	}
	
	public void loseGame() {
		finishGame();
		isGameLost = true;
		uncoverAllMines();

		JOptionPane.showMessageDialog(frame,
			    "You've lost the game. :( Click \"OK\" to start again!",
			    "You lost",
			    JOptionPane.PLAIN_MESSAGE);
		controlPanel.restartGame();
	}
	
	private void uncoverAllMines() {
		for (int i = 1; i <= getWidth(); i++) {
			for (int j = 1; j <= getHeight(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				if (isMine(coordinate)) {
					uncover(coordinate);
				}
			}
		}
	}

	private void winGame() {
		finishGame();
		
		int timeEllapsed = controlPanel.getTimeEllapsed();
		Scores score = new Scores(frame, level);
		score.saveNewWinner(timeEllapsed);
		score.openWinnersBoard(timeEllapsed);
		controlPanel.restartGame();
	}
	
	private void finishGame() {
		isGameFinished = true;
		controlPanel.stopTimer();
	}
	
	public List<Coordinate> getAdjacentFields(Coordinate coordinate) {
		List<Coordinate> adjacentFields = new LinkedList<Coordinate>();
		for (int i = Math.max(coordinate.getX()-1, 1); 
				i <= Math.min(coordinate.getX()+1, getWidth()); i++) {
			for (int j = Math.max(coordinate.getY()-1, 1); 
					j <= Math.min(coordinate.getY()+1, getHeight()); j++) {
				if (i != coordinate.getX() || j != coordinate.getY()) {
					adjacentFields.add(new Coordinate(i, j));
				}
			}
		}
		
		return adjacentFields;
	}
	
	public int getAdjacentMines(Coordinate coordinate) {
		int adjacentMines = 0;
		List<Coordinate> adjacentFields = getAdjacentFields(coordinate);
		for (int i = 0; i < adjacentFields.size(); i++) {
			if (isMine(adjacentFields.get(i))) {
				adjacentMines++;
			}
		}
		
		return adjacentMines;
	}

	public void flag(Coordinate coordinate) {
		BoardField boardField = getBoardField(coordinate);
		if (flagsRemaining > 0 || boardField.isFlagged()) {
			boardField.flag();
		}
	}
	
	public void flag(boolean isMine, boolean isDeflagged) {
		if (isDeflagged) {
			flagsRemaining++;
			if (isMine) {
				minesRemaining++;
			}
		} else {
			flagsRemaining--;
			if (isMine) {
				minesRemaining--;
			}
		}
		
		controlPanel.updateFlagCounter(flagsRemaining);
		if (minesRemaining == 0) {
			winGame();
		}
	}
	
	public void uncover(Coordinate coordinate) {
		getBoardField(coordinate).uncover();
	}
	
	public void addBoardField(Button fieldButton) {
		if (isBoardInitialized()) {
			return;
		}
		
		Coordinate coordinate = new Coordinate(latestNotUsedX, latestNotUsedY);
		int position = (getWidth()*(coordinate.getY()-1))+coordinate.getX();
		BoardField boardField;
		if (randomMinesPositions.contains(position)) {
			boardField = new MineField(fieldButton, this, coordinate);
		} else {
			boardField = new SafeField(fieldButton, this, coordinate);
		}
		boardFields[coordinate.getX()-1][coordinate.getY()-1] = boardField;
		
		if (latestNotUsedX >= getWidth()) {
			if (latestNotUsedY >= getHeight()) {
				latestNotUsedX = -1;
				latestNotUsedY = -1; 
			} else {
				latestNotUsedX = 1;
				latestNotUsedY++;
			}
		} else {
			latestNotUsedX++;
		}
	}
	
	private List<Integer> randomizeMinesPositions() {
		final int totalFields = getWidth()*getHeight();
		final List<Integer> fieldsPositions = new ArrayList<Integer>(totalFields);
		for (int i = 1; i <= totalFields; i++) {
			fieldsPositions.add(i);
		}
		Collections.shuffle(fieldsPositions);
		
		return fieldsPositions.subList(0, getMinesRemaining());
	}
	
	public boolean isMine(Coordinate coordinate) {
		return getBoardField(coordinate).isMine();
	}
	
	private BoardField getBoardField(Coordinate coordinate) {
		return boardFields[coordinate.getX()-1][coordinate.getY()-1];
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
	
	public boolean isGameFinished() {
		return isGameFinished;
	}
	
	public boolean isGameLost() {
		return isGameLost;
	}
}
