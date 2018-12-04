import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GameTimer extends Counter {
	private int time = 0;
	private boolean isStopped = false;
	
	GameTimer() {
		super();
	}
	
	public void startTimer(TimeListener timeListener) {
		Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (!isStopped) {
            		update(++time);
            		timeListener.timeChanged(time);
            	}
            }
        });
		
        timer.start();
	}
	
	public void stopTimer() {
		isStopped = true;
	}
	
	public int getTimeEllapsed() {
		return time;
	}
}