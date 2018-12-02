import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class GamePanel extends Panel {
	
	
	GamePanel(BoardModel boardModel) {
		super(boardModel);
		setLayout(new GridLayout(boardModel.getHeight(), boardModel.getWidth()));
		buildBoard();
	}
	
	private void buildBoard() {
		final List<Integer> randomMinesPosition = getRandomMinesPositions();
		
		int position = 0;
		for (int i = 0; i < boardModel.getHeight(); i++) {
			for (int j = 0; j < boardModel.getWidth(); j++) {
				final int coordinateX = i+1;
				final int coordinateY = j+1;
				final JButton boardField = getButton(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							boardModel.flag(coordinateX, coordinateY);
						} else {
							boardModel.uncover(coordinateX, coordinateY);
						}
					}
				});
				if (randomMinesPosition.contains(position)) {
					//mine
				} else {
					//safe
				}
				add(boardField);
			}
		}
	}
	
	private List<Integer> getRandomMinesPositions() {
		final int totalFields = boardModel.getWidth()*boardModel.getHeight();
		final List<Integer> fieldsPositions = new ArrayList<Integer>(totalFields);
		for (int i = 0; i < totalFields; i++) {
			fieldsPositions.add(i);
		}
		Collections.shuffle(fieldsPositions);
		
		return fieldsPositions.subList(0, boardModel.getMinesRemaining()-1);
	}
}
