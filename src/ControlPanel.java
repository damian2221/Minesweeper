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
            	JOptionPane.showMessageDialog(frame, "Instructions:\n"
            		+ "You are playing the MineSweeper! This is a traditional MineSweeper with\n"
            		+ "no unusual features except for the buttons openning the table with\n"
            		+ "winners and instructions. At first you have to choose one of 3 levels:\n"
            		+ "Easy (board 10x10 with 10 mines), Medium (board 20x20 with 50 mines)\n"
            		+ "and Hard (board 20x30 with 100 mines). Then you proceed to the game.\n"
            		+ "You can restart the game at any point by clicking the smiled face at\n"
            		+ "the top of the game frame. You lose the game when 999 seconds will pass\n"
            		+ "without discovering all mines. You also lose when you discover a mine.\n"
            		+ "You can discover a field in the board by clicking it with left-click.\n"
            		+ "If this field is not a mine, then you will discover a number which\n"
            		+ "denotes the number of adjacent mines to this field (if it is 0, it will\n"
            		+ "automatically discover all adjacent fields). If you think that there is\n"
            		+ "a mine under some covered field, you can flag it with right-click. You\n"
            		+ "have a limited number of flags (look at the counter at the top-right\n"
            		+ "side of the game screen). To win the game, you should correctly flag\n"
            		+ "all the mines. The lower the time, the better - only the lowest 5\n"
            		+ "scores in each level appears on the winners' board.",
        			"Instructions", JOptionPane.PLAIN_MESSAGE);
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
