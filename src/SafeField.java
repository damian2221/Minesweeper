import javax.swing.JButton;

public class SafeField extends BoardField {
	SafeField(JButton fieldButton, BoardModel boardModel) {
		super(fieldButton, boardModel);
	}
	
	public void flagListener() {
		boardModel.flag(false);
	}
}
