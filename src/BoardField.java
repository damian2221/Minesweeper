import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class BoardField {
    private static final String FLAG_FILE = "files/flag.png";
    
	final protected Button fieldButton;
	final protected BoardModel boardModel;
	final Coordinate coordinate;
	private boolean isFlagged = false;
	private boolean isUncovered = false;
	
	BoardField(Button fieldButton, BoardModel boardModel, Coordinate coordinate) {
		this.fieldButton = fieldButton;
		this.boardModel = boardModel;
		this.coordinate = coordinate;
	}
	
	public abstract void uncoverListener();
	
	public void uncover() {
		if (!isUncovered && (!isFlagged || boardModel.isGameLost()) &&
				(!boardModel.isGameFinished() || isMine())) {
			isUncovered = true;
			Button.setLockedStyle(fieldButton);
			uncoverListener();
		}
	}
	
	public abstract boolean isMine();
	
	public void flag() {
		if (isUncovered || boardModel.isGameFinished()) {
			return;
		}
		try {
			ImageIcon icon = null;
			if (!isFlagged) {
				icon = new ImageIcon(ImageIO.read(new File(FLAG_FILE)));
			}
			fieldButton.setIcon(icon);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } finally {
        	isFlagged = !isFlagged;
        	boardModel.flag(isMine(), !isFlagged);
        }
	}
	
	public boolean isFlagged() {
		return isFlagged;
	}
}
