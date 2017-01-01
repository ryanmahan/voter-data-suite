package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import suite.DataDriver;
import suite.Gui;

@SuppressWarnings("serial")
public class ButtonActions {
	
	public static class PhoneBank extends AbstractAction{

		Gui UX;
		
		public PhoneBank(Gui g){
			UX = g;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object[] options = {"File" , "Net" , "Cancel"};
			JFrame popUp = new JFrame("Pop Up");
			int n = JOptionPane.showOptionDialog(popUp, "Would you like to phonebank from the masterlist or net?",
					"File or Net?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[2]);
			if(n == 0){//net
				try {
					DataDriver.phoneBankMaker(new File(UX.getTextField()), UX);
				} catch (Exception except) {
					except.printStackTrace();
				}
			} else if(n == 1){//file
				
			} 
		}
		
	}
	
	public static class HouseMake extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class NotHome extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class Distance extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
