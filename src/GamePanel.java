import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class GamePanel extends JPanel {
	final protected BoardModel boardModel;
	
	GamePanel(BoardModel boardModel) {
		this.boardModel = boardModel;
		setLayout(new GridLayout(boardModel.getHeight(), boardModel.getWidth()));
		buildBoard();
	}
	
	private void buildBoard() {
		for (int i = 0; i < boardModel.getHeight(); i++) {
			for (int j = 0; j < boardModel.getWidth(); j++) {
				Coordinate coordinate = new Coordinate(j+1, i+1);
				final Button fieldButton = new Button(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							boardModel.flag(coordinate);
						} else {
							boardModel.uncover(coordinate);
						}
					}
				});
				boardModel.addBoardField(fieldButton);
				add(fieldButton);
			}
		}
	}
}
