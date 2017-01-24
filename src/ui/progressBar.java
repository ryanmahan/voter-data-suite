package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class progressBar {
	
	
	public progressBar(String taskName){
		createProgressBar(0, taskName);
	}
	
	
	static boolean done = false;
	double start = 0;
	double time = 0;
	double task100 = 0;
	double taskTime = 0;
	private final Font menuFont = new Font("Arial", Font.PLAIN, 15);
	private final Color darkBlue = new Color(17,14,111);
	Boolean close = false;
	
	private JFrame progBarFrame = new JFrame();
	private JLabel label;
	private JProgressBar prog = new JProgressBar();
	
	public void createProgressBar(int progress, String taskName){
		
		progBarFrame.setLayout(new BorderLayout());
		progBarFrame.setSize(250, 100);
		
		progBarFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		progBarFrame.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				   setClose(true);
				   progBarFrame.dispose();
			   }
			  });
		
		label = new JLabel("Progress bar for: " + taskName);
		label.setFont(menuFont);
		label.setVisible(true);
		label.setOpaque(true);
		
		
		prog.setForeground(darkBlue);
		prog.setStringPainted(true);
		prog.setOpaque(true);
		prog.setMaximum(100);
		prog.setMinimum(0);
		prog.setValue(progress);
		prog.setVisible(true);
		
		progBarFrame.add(label, BorderLayout.NORTH);
		progBarFrame.add(prog, BorderLayout.SOUTH);
		progBarFrame.setVisible(true);
	}
	
	public void setProgress(double currTaskNumber, double total){
		
		if(currTaskNumber%25 == 0){
			if(currTaskNumber == 0)
				start = System.currentTimeMillis();
			else{
				time = System.currentTimeMillis();
				taskTime = (time-start)/currTaskNumber;
			}
		}
			
		int remainingTime = (int) ((taskTime*(total-currTaskNumber)/1000));
		
		if(remainingTime == 0)
			label.setText("Calculating Remaining time");
		else
			label.setText("Time Remaining: " + remainingTime/60 + "m and " + remainingTime%60 + "s");
		
		int progress = (int) (currTaskNumber/total*100);;
		prog.setValue(progress);
		
		
		prog.update(prog.getGraphics());
		label.update(label.getGraphics());
	}
	
	private void setClose(Boolean close){
		this.close = close;
	}
	
	public Boolean getClose(){
		return close;
	}
	
}
