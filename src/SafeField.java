import java.util.Iterator;
import java.util.List;

public class SafeField extends BoardField {
	SafeField(Button fieldButton, BoardModel boardModel, Coordinate coordinate) {
		super(fieldButton, boardModel, coordinate);
	}
	
	public boolean isMine() {
		return false;
	}
	
	public void uncoverListener() {
		final int adjacentMines = boardModel.getAdjacentMines(coordinate);
		if (adjacentMines == 0) {
			final List<Coordinate> adjacentFields = boardModel.getAdjacentFields(coordinate);
			Iterator<Coordinate> iter = adjacentFields.iterator();
			while (iter.hasNext()) {
				boardModel.uncover(iter.next());
			}
		} else {
			fieldButton.setText(Integer.toString(adjacentMines));
		}
	}
}
