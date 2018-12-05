import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class Scores {
    private static final String SCORES_FILE = "files/scores.txt";
    private static final int MAX_SCORES_IN_ONE_LEVEL = 5;
    private final JFrame frame;
    private final Level level;
    private final File scoresFile = new File(SCORES_FILE);
    private BufferedReader scoresReader;
    private BufferedWriter scoresWriter;
    private Map<Level, Map<String, Integer>> winners;
    private Map<String, Integer> currentLevel;
    
    Scores(JFrame frame, Level level) {
    	this.frame = frame;
    	this.level = level;
    	openScoresReader();
    	currentLevel = getCurrentLevel();
    }
    
    public void openWinnersBoard(int seconds) {
		if (currentLevel == null) {
			return;
		}
		final JPanel winnersBoard = new JPanel();
		winnersBoard.setLayout(new BoxLayout(winnersBoard, BoxLayout.PAGE_AXIS));
		
		if (seconds >= 0) {
			JLabel titleLabel = new JLabel("Congratulations! You won in " + 
					Integer.toString(seconds) + " seconds!");
			titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			winnersBoard.add(titleLabel);
		}
		
		winnersBoard.add(Box.createRigidArea(new Dimension(200, 0)));
		JLabel titleLabel = new JLabel("Winners board in " + level.toString().toLowerCase() + 
				" level");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		winnersBoard.add(titleLabel);
		winnersBoard.add(Box.createRigidArea(new Dimension(200, 0)));
		
        class WinnersDataConsumer implements Consumer<Map.Entry<String, Integer>> {
        	private int i = 1;
    		private Vector<Vector<String>> winnersData = new Vector<Vector<String>>();
        	public void accept(Map.Entry<String, Integer> winner) {
        		Vector<String> newRow = new Vector<String>();
        		newRow.add(Integer.toString(i) + ".");
        		newRow.add(winner.getKey());
        		newRow.add(Integer.toString(winner.getValue()) + " sec.");
        		winnersData.add(newRow);
        		i++;
        	}
        	public Vector<Vector<String>> getWinnersData() {
        		return winnersData;
        	}
        }
        
		WinnersDataConsumer eachMapEntry = new WinnersDataConsumer();
		
		winners.get(level).entrySet().stream()
	        .sorted(Map.Entry.<String, Integer>comparingByValue())
	        .forEachOrdered(eachMapEntry);
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Lp.");
		columnNames.add("Winner name");
		columnNames.add("Score");

		JTable table = new JTable(eachMapEntry.getWinnersData(), columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < 3; i++) {
		    TableColumn column = table.getColumnModel().getColumn(i);
		    switch(i) {
		    case 0: column.setPreferredWidth(35);break;
		    case 1: column.setPreferredWidth(235);break;
		    default: column.setPreferredWidth(60);break;
		    }
		}

		winnersBoard.add(table.getTableHeader());
		winnersBoard.add(table);
		
		JOptionPane.showMessageDialog(frame, winnersBoard, "Winners Board", 
				JOptionPane.PLAIN_MESSAGE);
    }
    
    private Map<String, Integer> getCurrentLevel() {
		if (winners == null) {
			return null;
		}
		Map<String, Integer> currentLevel = winners.get(level);
		if (currentLevel == null) {
			currentLevel = new HashMap<String, Integer>();
			winners.put(level, currentLevel);
		}
		return currentLevel;
    }
    
    public void saveNewWinner(int score) {
		if (currentLevel == null) {
			return;
		}

		Map.Entry<String, Integer> worstScoreWinner = getWorstScoreInLevel();
		if (currentLevel.size() >= MAX_SCORES_IN_ONE_LEVEL && 
				worstScoreWinner.getValue() <= score) {
			return;
		}
		
		String winnerName = askForWinnerName();
		
		if (winnerName == null) {
			return;
		}
		
		if (currentLevel.size() >= MAX_SCORES_IN_ONE_LEVEL) {
			currentLevel.remove(worstScoreWinner.getKey());
		}
		currentLevel.put(winnerName, score);
		
		saveFile();
    }
    
    private Map.Entry<String, Integer> getWorstScoreInLevel() {
		Map.Entry<String, Integer> worstScoreWinner = null;
    	for (Map.Entry<String, Integer> winner : winners.get(level).entrySet())
		{
    		if (worstScoreWinner == null || worstScoreWinner.getValue() < winner.getValue()) {
    			worstScoreWinner = winner;
    		}
		}
    	return worstScoreWinner;
    }
    
    public void closeFiles() {
    	try {
	    	if (scoresReader != null) {
	    		scoresReader.close();
	    	}
	    	if (scoresWriter != null) {
	    		scoresWriter.close();
	    	}
    	} catch (IOException e) {
			showErrorMessage();
		}
    }
    
    private void openScoresReader() {
    	winners = new HashMap<Level, Map<String, Integer>>();
    	try {
    		scoresFile.createNewFile(); 
    		scoresReader = new BufferedReader(new FileReader(scoresFile)); 
    		String nextLine = scoresReader.readLine();

    		
    		if (nextLine == null) {
    			for (Level currentLevel : Level.values()) {
    				winners.put(currentLevel, new HashMap<String, Integer>());
    			}
    			return;
    		};
    		
    		if (!isCorrectLevelName(nextLine)) {
    			resetCorruptedFile();
    		}
    		
    		Map<String, Integer> levelWinners = new HashMap<String, Integer>();

    		while (nextLine != null) {
    			if (isCorrectLevelName(nextLine)) {
    				Level currentLevel = Level.valueOf(nextLine);
    				if (winners.containsKey(currentLevel)) {
    					resetCorruptedFile();
    					return;
    				}
    				levelWinners = new HashMap<String, Integer>();
    				winners.put(currentLevel, levelWinners);
        			nextLine = scoresReader.readLine();
        			continue;
    			}
    			if (levelWinners.size() >= MAX_SCORES_IN_ONE_LEVEL) {
    				resetCorruptedFile();
    				return;
    			}
    			if (nextLine.charAt(0) != '"') {
    				resetCorruptedFile();
    				return;
    			}
    			int charIndex = 1;
    			String name = "";
    			while (charIndex < nextLine.length()) {
    				char nextChar = nextLine.charAt(charIndex);
    				if (nextChar == '"') {
        				charIndex++;
    					break;
    				}
    				name += nextChar;
    				charIndex++;
    			}
    			if (!isCorrectName(name)) {
    				resetCorruptedFile();
    				return;
    			}
    			String scoreToName = "";
    			while (charIndex < nextLine.length()) {
    				scoreToName += nextLine.charAt(charIndex);
    				charIndex++;
    			}
    			if (!scoreToName.matches("[0-9]{1,3}")) {
    				resetCorruptedFile();
    				return;
    			}
    			levelWinners.put(name, Integer.parseInt(scoreToName));
    			nextLine = scoresReader.readLine();
    		}
    		
    		if (!winners.containsKey(level)) {
    			winners.put(level, new HashMap<String, Integer>());
    		}
    	} catch (IOException e) {
			showErrorMessage();
    	} finally {
    		closeFiles();
    	}
    }
    
    private void saveFile() {
    	try {
			scoresWriter = new BufferedWriter(new FileWriter(scoresFile));
	    	for (Map.Entry<Level, Map<String, Integer>> level : winners.entrySet()) {
	    		scoresWriter.write(level.getKey().toString());
	    		scoresWriter.newLine();
		    	for (Map.Entry<String, Integer> winner : level.getValue().entrySet()) {
		    		scoresWriter.write("\"" + winner.getKey() + "\"" + 
		    				winner.getValue().toString());
		    		scoresWriter.newLine();
				}
			}
		} catch (IOException e) {
			showErrorMessage();
		} finally {
			closeFiles();
		}
    }
    
    private boolean isCorrectLevelName(String levelName) {
    	try {
			Level.valueOf(levelName);
		} catch (IllegalArgumentException e) {
			return false;
		}
    	
    	return true;
    }
    
    private void resetCorruptedFile() {
    	closeFiles();
    	scoresFile.delete();
    	openScoresReader();
    }
    
    private String askForWinnerName() {
		String name = JOptionPane.showInputDialog(frame, 
                "Congratulations! You won the game! Please write your name\n(min. 1 letter, max. "
                + "25 lettersm possibly a-zA-Z with spaces)\n to be saved on the board of winners!", 
                "You won!!", JOptionPane.PLAIN_MESSAGE);
		
		if (name != null && !isCorrectName(name)) {
			return askForWinnerName();
		}
		
		return name;
	}
    
    private boolean isCorrectName(String name) {
    	return name.matches("[a-zA-Z ]{1,25}");
    }
    
    private void showErrorMessage() {
    	JOptionPane.showMessageDialog(frame, "There occured an error with scores.txt file!",
			    "Error", JOptionPane.ERROR_MESSAGE);
    }
}
