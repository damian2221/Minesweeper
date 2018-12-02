import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GameTimer extends Counter {
		private int time = 0;
		
		GameTimer() {
			super();
			startTimer();
		}
		
		private void startTimer() {
			Timer timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	updateTimer();
	            }
	        });
			
	        timer.start();
		}
		
		private void updateTimer() {
			time++;
			setText(getThreeDigitCount(time));
		}
	}