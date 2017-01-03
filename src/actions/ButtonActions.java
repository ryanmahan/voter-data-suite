package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import suite.DataDriver;
import suite.FileHandler;
import suite.Gui;
import suite.House;

@SuppressWarnings("serial")
public class ButtonActions {
	
	public static class PhoneBank extends AbstractAction{

		Gui UX;
		
		public PhoneBank(Gui g){
			UX = g;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object[] options = {"Cancel" , "Net" , "Cancel"};
			JFrame popUp = new JFrame("Pop Up");
			int n = JOptionPane.showOptionDialog(popUp, "Would you like to phonebank from the masterlist or net?",
					"File or Net?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[2]);
			if(n == 0){//net
				try {
					DataDriver.phoneBankMaker(new File("data/temp.xml"), UX);
				} catch (Exception except) {
					except.printStackTrace();
				}
			} else if(n == 1){//file
				//TODO: Make Masterlist
			} 
		}
		
	}
	
	public static class HouseMake extends AbstractAction{
		Gui UX;
		public HouseMake(Gui UX){
			this.UX = UX;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			LinkedList<House> list = DataDriver.houseMaker(new File("data/temp.xml"));
			System.out.println("Made a list of Houses: " + list.size());

			String all = list.size() + " Houses in list\n";

			for (House h : list) {
				String text = h.getHead().getAllAvail();
				text += "\n";
				all = all.concat(text);
			}

			//TODO: UX.setTextArea(all);
		
		}
		
	}
	
	public static class NotHome extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Write over Not Home function from GUI
			
		}
		
	}
	
	public static class Distance extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Write over Distance Function from GUI
			
		}
		
	}
	
}
