package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import suite.Gui;

@SuppressWarnings("serial")
public class SortMenuActions {

	public static class street extends AbstractAction{

		Gui UX;
		public street(Gui UX){
			this.UX = UX;
		}
		
		
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	public static class name extends AbstractAction{

		Gui UX;
		public name(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	public static class precinct extends AbstractAction{

		Gui UX;
		public precinct(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	public static class rank extends AbstractAction{

		Gui UX;
		public rank(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	public static class timesVoted extends AbstractAction{

		Gui UX;
		public timesVoted(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}

	
	
}
