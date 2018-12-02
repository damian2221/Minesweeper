import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class Panel extends JPanel {
	final private int WIDTH = 25;
	final private int HEIGHT = 25;
	final private Color BACKGROUND_REST_COLOR = Color.LIGHT_GRAY;
	final private int BORDER_WIDTH = 2;
	final private Color BORDER_REST_LIGHT_COLOR = new Color(226, 226, 226);
	final private Color BORDER_REST_DARK_COLOR = new Color(166, 166, 166);
	final private Color BORDER_PRESSED_COLOR = Color.GRAY;
	
	final protected BoardModel boardModel;
	
	Panel(BoardModel boardModel) {
		this.boardModel = boardModel;
	}
	
	public JButton getButton(MouseAdapter onClick) {
		final JButton button = new JButton();
        button.setBackground(BACKGROUND_REST_COLOR);
        button.setBorder(getRestBorder());
        setFixedSize(button);
        
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                button.setBorder(new MatteBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, 
                		BORDER_WIDTH, BORDER_PRESSED_COLOR));
            }
            public void mouseReleased(MouseEvent e) {
            	button.setBorder(getRestBorder());
            }
        });
        button.addMouseListener(onClick);
        
        return button;
	}
	
	private void setFixedSize(JButton button) {
		Dimension fixedDimension = new Dimension(WIDTH, HEIGHT);
        button.setMinimumSize(fixedDimension);
        button.setPreferredSize(fixedDimension);
        button.setMaximumSize(fixedDimension);
	}
	
	private Border getRestBorder() {
		return new CompoundBorder(new MatteBorder(BORDER_WIDTH, BORDER_WIDTH, 0, 0, 
        		BORDER_REST_LIGHT_COLOR),
        		new MatteBorder(0, 0, BORDER_WIDTH, BORDER_WIDTH, BORDER_REST_DARK_COLOR));
	}
}
