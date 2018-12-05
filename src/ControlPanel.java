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
    private static final int PADDING = 5;
    private static final int PANEL_HEIGHT = 40;
    private final GameTimer gameTimer;
    private final Counter flagCounter;
    private final JFrame frame;
    private JButton winnersBoardButton;
    
	ControlPanel(JFrame frame) {
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		gameTimer = new GameTimer();
		add(gameTimer);

		add(Box.createHorizontalGlue());
		createWinnersBoardButton();
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		createResetButton();
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		createInstructionsButton();
		add(Box.createHorizontalGlue());
		
		flagCounter = new Counter();
		add(flagCounter);
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
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
	
	public void addWinnersBoardFunction(MouseAdapter mouseAdapter) {
		winnersBoardButton.addMouseListener(mouseAdapter);
	}
	
	private void createWinnersBoardButton() {
		winnersBoardButton = new Button(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {}
        });
		
		winnersBoardButton.setText("W");
		winnersBoardButton.setFont(winnersBoardButton.getFont().deriveFont(14.0f));
		add(winnersBoardButton);
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
		instructionsButton.setFont(instructionsButton.getFont().deriveFont(14.0f));
		instructionsButton.setHorizontalAlignment(SwingConstants.CENTER);
		add(instructionsButton);
	}
}
