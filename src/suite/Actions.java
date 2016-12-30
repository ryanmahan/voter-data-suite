package suite;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class Actions {
	
	@SuppressWarnings("serial")
	static class phoneBank extends AbstractAction{
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Work?");
		}
		
		
	}

	
	

}
