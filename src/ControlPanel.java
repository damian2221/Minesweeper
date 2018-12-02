import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ControlPanel extends Panel {
    private static final String SMILE_FILE = "files/smile.png";
    private static final int BUTTONS_PADDING = 5;
    private static final int PANEL_HEIGHT = 40;
    
	int timer_count = 0;
    
	ControlPanel(BoardModel boardModel) {
		super(boardModel);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(new GameTimer());
		add(Box.createHorizontalGlue());
		createResetButton();
		add(Box.createRigidArea(new Dimension(BUTTONS_PADDING, PANEL_HEIGHT)));
		createInstructionsButton();
		add(Box.createHorizontalGlue());
		add(new Counter());
	}
	
	private void createResetButton() {
		final JButton resetButton = getButton(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                //court.reset();
	            }
	        });
		
        try {
        	resetButton.setIcon(new ImageIcon(ImageIO.read(new File(SMILE_FILE))));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
        add(resetButton);
	}
	
	private void createInstructionsButton() {
		final JButton instructionsButton = getButton(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //court.reset();
            }
        });
		
		instructionsButton.setText("?");
		instructionsButton.setHorizontalAlignment(SwingConstants.CENTER);
		add(instructionsButton);
	}
	
	
}
