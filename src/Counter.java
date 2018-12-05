import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Counter extends JLabel {
    private static final String A_LCDNOVA_FONT_FILE = "files/a_lcdnova.ttf";
    
	Counter() {
		update(0);
		try {
             Font aLcdnovaFont = Font.createFont(Font.TRUETYPE_FONT, 
           		 new File(A_LCDNOVA_FONT_FILE)).deriveFont(23.0f);
		     GraphicsEnvironment graphicsEnvironment = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     graphicsEnvironment.registerFont(aLcdnovaFont);
		     setFont(aLcdnovaFont);
		     setForeground(Color.PINK);
		     setBackground(Color.BLACK);
		     setOpaque(true);
		     setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		} catch (IOException|FontFormatException e) {}
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
