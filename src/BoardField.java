import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class BoardField {
    private static final String FLAG_FILE = "files/flag.png";
    
	final protected JButton fieldButton;
	final protected BoardModel boardModel;
	boolean isFlagged = false;
	
	BoardField(JButton fieldButton, BoardModel boardModel) {
		this.fieldButton = fieldButton;
		this.boardModel = boardModel;
	}
	
	public abstract void flagListener();
	
	public void flag() {
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
        	flagListener();
        }
	};
}
