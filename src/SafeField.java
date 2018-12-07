import java.awt.Color;
import java.util.Iterator;
import java.util.List;

public class SafeField extends BoardField {
	SafeField(Button fieldButton, BoardModel boardModel, Coordinate coordinate) {
		super(fieldButton, boardModel, coordinate);
	}
	
	public boolean isMine() {
		return false;
	}
	
	protected void uncoverListener() {
		final int adjacentMines = boardModel.getAdjacentMines(coordinate);
		if (adjacentMines == 0) {
			final List<Coordinate> adjacentFields = boardModel.getAdjacentFields(coordinate);
			Iterator<Coordinate> iter = adjacentFields.iterator();
			while (iter.hasNext()) {
				boardModel.uncover(iter.next());
			}
		} else {
			fieldButton.setText(Integer.toString(adjacentMines));
			fieldButton.setForeground(new Color(countRed(adjacentMines), 0, 
					countBlue(adjacentMines)));
			fieldButton.setFont(fieldButton.getFont().deriveFont(14.0f));
		}
	}

	private int countBlue(int n) {
		return 255-(n-1)*32;
	}
	
	private int countRed(int n) {
		return Math.min(n*48, 255);
	}
}
