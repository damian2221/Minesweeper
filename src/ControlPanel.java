import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ControlPanel extends JPanel {
    private static final String SMILE_FILE = "files/smile.png";
    private static final int BUTTONS_PADDING = 5;
    private static final int PANEL_HEIGHT = 40;
    private final GameTimer gameTimer;
    private final Counter flagCounter;
    private final JFrame frame;
    
	ControlPanel(JFrame frame) {
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		gameTimer = new GameTimer();
		add(gameTimer);
		
		add(Box.createHorizontalGlue());
		createResetButton();
		add(Box.createRigidArea(new Dimension(BUTTONS_PADDING, PANEL_HEIGHT)));
		createInstructionsButton();
		add(Box.createHorizontalGlue());
		
		flagCounter = new Counter();
		add(flagCounter);
	}
	
	public void startTimer(TimeListener timeListener) {
		gameTimer.startTimer(timeListener);
	}
	
	public void stopTimer() {
		gameTimer.stopTimer();
	}
	
	public int getTimeEllapsed() {
		return gameTimer.getTimeEllapsed();
	}
	
	public void updateFlagCounter(int flagNumber) {
		flagCounter.update(flagNumber);
	}
	
	public void restartGame() {
    	(new ChooseLevel(frame)).openChooseLevelScreen();
	}
	
	private void createResetButton() {
		final JButton resetButton = new Button(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	restartGame();
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
		final JButton instructionsButton = new Button(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //court.reset();
            }
        });
		
		instructionsButton.setText("?");
		instructionsButton.setHorizontalAlignment(SwingConstants.CENTER);
		add(instructionsButton);
	}
}
