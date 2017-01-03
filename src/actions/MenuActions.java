package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import suite.FileHandler;
import suite.Gui;


@SuppressWarnings("serial")
public class MenuActions {
	
	public static class SaveAs extends AbstractAction {
		Gui UX;
		public SaveAs(Gui UX){
			this.UX = UX;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Ryans Voter Data","vd", "xml");
			fc.setFileFilter(filter);
			int choice = fc.showSaveDialog(null);
		    if (choice == JFileChooser.APPROVE_OPTION) {
		        String filename = fc.getSelectedFile().getAbsolutePath();
		        
		        if(!filename.endsWith(".xml")){
		        	filename += ".xml";
		        }
		        
		        if(new File(filename).exists()){
		        	
		        	Object[] options = {"OK", "CANCEL"};
		        	int reply = JOptionPane.showOptionDialog(null, "This will overwrite the current file, Click OK to continue", 
		        			"Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		        			null, options, options[0]);
		        	
		        	if(reply == 0){ //if they choose OK
		        		
		        		FileHandler internal = new FileHandler(new File("data/temp.xml"));
					    internal.xmlWrite(filename, internal.getList());
					    return;
					    
		        	} else { //if they hit cancel, pop a new save as dialog up
		        		this.actionPerformed(e);
		        	}
		        	
		        } else { //if file doesnt exist
		        	
		        	System.out.println("doesnt exists create");
		        	File f = new File(filename);
		        	try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        	FileHandler internal = new FileHandler(new File("data/temp.xml"));
				    internal.xmlWrite(filename, internal.getList());
		        }
			    
		    }
		}

	}

	public static class Open extends AbstractAction{
		Gui UX;
		public Open(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Ryans Voter Data","xml","vd");
			fc.setFileFilter(filter);
			int choice = fc.showOpenDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				
				String filename = fc.getSelectedFile().getAbsolutePath();
				File f = new File(filename);
				FileHandler fh = new FileHandler(f);
				File appFile = new File("data/temp.xml");
				
				if(appFile.exists())
					appFile.delete();
				
				try {
					appFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				fh.xmlWrite("data/temp.xml", fh.getList());
				FileHandler internal = new FileHandler(appFile);
				//UX.setTextArea(internal.xmlToString());
				String[][] display = internal.to2DArray();
				String[] columns = new String[]{"Party", "Name", "Last", "St. Number", "St. Name"};
				UX.setTable(display, columns);
			}
			
	    	
		}
		
	}

	public static class Export extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: Write method to export to tab-collated txt file
			
		}

	}

	public static class Import extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: Write method to import tab-collacted txt file
			
		}

	}
	

}
