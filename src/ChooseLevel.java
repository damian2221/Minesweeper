import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChooseLevel {
	final private JFrame frame;
	private JPanel chooseLevelPanel;
	
	ChooseLevel(JFrame frame) {
		this.frame = frame;
	}
	
	public void openChooseLevelScreen() {
		clearFrame();
		
		chooseLevelPanel = new JPanel();
        chooseLevelPanel.add(new JLabel("Choose the level"));
        addButton("Easy", Level.EASY);
        addButton("Medium", Level.MEDIUM);
        addButton("Hard", Level.HARD);

        frame.add(chooseLevelPanel, BorderLayout.CENTER);
        frame.pack();
	}
	
	private void addButton(String name, Level level) {
        final JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	startGame(level);
            }
        });
        chooseLevelPanel.add(button);
	}
	
	private void startGame(Level level) {
		clearFrame();
    	final ControlPanel controlPanel = new ControlPanel(frame);
        frame.add(controlPanel, BorderLayout.NORTH);
        
        final BoardModel boardModel = new BoardModel(level, controlPanel, frame);

        final GamePanel gamePanel = new GamePanel(boardModel);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
	}
	
	private void clearFrame() {
		frame.getContentPane().removeAll();
	}
}
