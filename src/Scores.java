import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Scores {
    private static final String SCORES_FILE = "files/scores.txt";
    private static final int MAX_SCORES_IN_ONE_LEVEL = 5;
    private final JFrame frame;
    private final Level level;
    private final File scoresFile = new File(SCORES_FILE);
    private BufferedReader scoresReader;
    private BufferedWriter scoresWriter;
    private Map<Level, Map<String, Integer>> winners;
    
    Scores(JFrame frame, Level level) {
    	this.frame = frame;
    	this.level = level;
    	openScoresReader();
    }
    
    public void saveNewWinner(int score) {
		if (winners == null) {
			return;
		}
		Map<String, Integer> currentLevel = winners.get(level);
		if (currentLevel == null) {
			currentLevel = new HashMap<String, Integer>();
			winners.put(level, currentLevel);
		}

		Map.Entry<String, Integer> worstScoreWinner = getWorstScoreInLevel();
		if (currentLevel.size() >= MAX_SCORES_IN_ONE_LEVEL && 
				worstScoreWinner.getValue() <= score) {
			return;
		}
		
		String winnerName = askForWinnerName();
		
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
                "Congratulations! You won the game! Please write your name (min. 1 letter, max"
                + "25 letters a-zA-Z possibly with space) to be saved on the board of winners!", 
                "You won!!", JOptionPane.PLAIN_MESSAGE);
		
		if (!isCorrectName(name)) {
			return askForWinnerName();
		}
		
		return name;
	}
    
    private boolean isCorrectName(String name) {
    	return name.matches("[a-zA-Z ]{1,25}");
    }
    
    private void showErrorMessage() {
    	JOptionPane.showMessageDialog(frame,
			    "There occured an error with scores.txt file!",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
    }
}
