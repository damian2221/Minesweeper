import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class GamePanel extends Panel {
	final protected BoardModel boardModel;
	
	GamePanel(BoardModel boardModel) {
		this.boardModel = boardModel;
		setLayout(new GridLayout(boardModel.getHeight(), boardModel.getWidth()));
		buildBoard();
	}
	
	private void buildBoard() {
		for (int i = 0; i < boardModel.getWidth(); i++) {
			for (int j = 0; j < boardModel.getHeight(); j++) {
				final int coordinateX = i+1;
				final int coordinateY = j+1;
				final JButton fieldButton = getButton(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							boardModel.flag(coordinateX, coordinateY);
						} else {
							boardModel.uncover(coordinateX, coordinateY);
						}
					}
				});
				boardModel.addBoardField(fieldButton);
				add(fieldButton);
			}
		}
	}
}
