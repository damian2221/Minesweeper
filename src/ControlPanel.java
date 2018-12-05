import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ControlPanel extends JPanel {
    private static final String SMILE_FILE = "files/smile.png";
    private static final String CUP_FILE = "files/cup.png";
    private static final String INFO_FILE = "files/info.png";
    private static final int PADDING = 5;
    private static final int PANEL_HEIGHT = 40;
    private final GameTimer gameTimer;
    private final Counter flagCounter;
    private final JFrame frame;
    private JButton winnersBoardButton;
    
	ControlPanel(JFrame frame) {
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		gameTimer = createGameTimer();
		createMiddleButtons();
		flagCounter = createFlagCounter();
	}
	
	final private GameTimer createGameTimer() {
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		GameTimer gameTimer = new GameTimer();
		add(gameTimer);
		
		return gameTimer;
	}
	
	final private void createMiddleButtons() {
		add(Box.createHorizontalGlue());
		createWinnersBoardButton();
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		createResetButton();
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		createInstructionsButton();
		add(Box.createHorizontalGlue());
	}
	
	final private Counter createFlagCounter() {
		Counter flagCounter = new Counter();
		add(flagCounter);
		add(Box.createRigidArea(new Dimension(PADDING, PANEL_HEIGHT)));
		
		return flagCounter;
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
		

		addButton(winnersBoardButton, CUP_FILE);
	}
	
	private void createResetButton() {
		final JButton resetButton = new Button(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	restartGame();
	            }
	        });

		addButton(resetButton, SMILE_FILE);
	}
	
	private void createInstructionsButton() {
		final JButton instructionsButton = new Button(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //court.reset();
            }
        });
		
		addButton(instructionsButton, INFO_FILE);
	}
	
	private void addButton(JButton button, String iconFile) {
		try {
			button.setIcon(new ImageIcon(ImageIO.read(new File(iconFile))));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
		
		add(button);
	}
}
