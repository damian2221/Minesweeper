import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class Button extends JButton {
	static final private int WIDTH = 25;
	static final private int HEIGHT = 25;
	static final private Color BACKGROUND_REST_COLOR = Color.LIGHT_GRAY;
	static final private int BORDER_WIDTH = 2;
	static final private Color BORDER_REST_LIGHT_COLOR = new Color(226, 226, 226);
	static final private Color BORDER_REST_DARK_COLOR = new Color(166, 166, 166);
	static final private Color BORDER_PRESSED_COLOR = Color.GRAY;
	static final private int BORDER_LOCKED_WIDTH = 1;
	static final private Color BORDER_LOCKED_COLOR = new Color(175, 175, 175);
	static final private Color BACKGROUND_LOCKED_COLOR = new Color(192, 192, 192);
	
	private boolean isLockedStyle = false;
	
	Button(MouseAdapter onClick) {
		Button button = this;
        setBackground(BACKGROUND_REST_COLOR);
        setRestBorder();
        setFixedSize();
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	setBorder(new MatteBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, 
                		BORDER_WIDTH, BORDER_PRESSED_COLOR));
            }
            public void mouseReleased(MouseEvent e) {
            	if (isLockedStyle()) {
            		Button.setLockedStyle(button);
            	} else {
            		setRestBorder();
            	}
            }
        });
        addMouseListener(onClick);
	}
	
	static public void setLockedStyle(Button button) {
		button.setLockedStyle();
        button.setBackground(BACKGROUND_LOCKED_COLOR);
        button.setBorder(new MatteBorder(BORDER_LOCKED_WIDTH, BORDER_LOCKED_WIDTH, 
        		BORDER_LOCKED_WIDTH, BORDER_LOCKED_WIDTH, BORDER_LOCKED_COLOR));
	}
	
	public boolean isLockedStyle() {
		return isLockedStyle;
	}
	
	public void setLockedStyle() {
		this.isLockedStyle = true;
	}
	
	private void setFixedSize() {
		Dimension fixedDimension = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(fixedDimension);
        setPreferredSize(fixedDimension);
        setMaximumSize(fixedDimension);
	}
	
	private void setRestBorder() {
		setBorder(new CompoundBorder(new MatteBorder(BORDER_WIDTH, BORDER_WIDTH, 0, 0, 
        		BORDER_REST_LIGHT_COLOR),
        		new MatteBorder(0, 0, BORDER_WIDTH, BORDER_WIDTH, BORDER_REST_DARK_COLOR)));
	}
}
