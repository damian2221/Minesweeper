import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GameTimer extends Counter {
		private int time = 0;
		
		public void startTimer(TimeListener timeListener) {
			Timer timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	update(++time);
	            	timeListener.timeChanged(time);
	            }
	        });
			
	        timer.start();
		}
	}