import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MineField extends BoardField {
    private static final String BOMB_FILE = "files/bomb.png";
    
	MineField(Button fieldButton, BoardModel boardModel, Coordinate coordinate) {
		super(fieldButton, boardModel, coordinate);
	}
	
	public boolean isMine() {
		return true;
	}
	
	public void uncoverListener() {
		try {
			ImageIcon icon = null;
			icon = new ImageIcon(ImageIO.read(new File(BOMB_FILE)));
			fieldButton.setIcon(icon);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } finally {
        	if (!boardModel.isGameFinished()) {
        		boardModel.loseGame();
        	}
        }
	}
}
