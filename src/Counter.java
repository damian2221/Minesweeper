import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Counter extends JLabel {
	Counter() {
		update(0);
	}
	
	protected void update(int count) {
		setText(getThreeDigitCount(count));
	}
	
	protected String getThreeDigitCount(int count) {
		String stringCount = Integer.toString(count);
		if (count < 10) {
			stringCount = "00" + stringCount;
		} else if (count < 100) { 
			stringCount = "0" + stringCount;
		}
		
		return stringCount;
	}
}
