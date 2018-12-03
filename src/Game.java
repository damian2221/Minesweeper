/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(300, 200);
        frame.setResizable(false);

        (new ChooseLevel(frame)).openChooseLevelScreen();

        //frame.add(gamePanel, BorderLayout.CENTER);
        /*
        final ControlPanel controlPanel = new ControlPanel();
        frame.add(controlPanel, BorderLayout.NORTH);
        
        final BoardModel boardModel = new BoardModel(Level.HARD, controlPanel);

        // Main playing area
        final GamePanel court = new GamePanel(boardModel);
        frame.add(court, BorderLayout.CENTER);
        */
        // Put the frame on the screen

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Start game
        //court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}