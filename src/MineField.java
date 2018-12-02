import javax.swing.JButton;

public class MineField extends BoardField {
	MineField(JButton fieldButton, BoardModel boardModel) {
		super(fieldButton, boardModel);
	}
	
	public void flagListener() {
		boardModel.flag(true);
	}
}
