package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import suite.FileHandler;
import suite.Gui;
import suite.Person;


@SuppressWarnings("serial")
public class FileMenuActions {
	
	private final static File internal = new File("data/temp.xml");
	
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
		        		
		        		FileHandler fh = new FileHandler(internal);
					    fh.xmlWrite(filename, fh.getList());
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
		        	FileHandler fh = new FileHandler(internal);
				    fh.xmlWrite(filename, fh.getList());
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
				File appFile = internal;
				
				if(appFile.exists())
					appFile.delete();
				
				try {
					appFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fh.xmlWrite("data/temp.xml", fh.getList());
				FileHandler internal = new FileHandler(appFile);
		
				String[][][] from = internal.to3DArray();
				UX.setTableData(from);
			}
			
	    	
		}
		
	}

	public static class Export extends AbstractAction {

		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Ryans Voter Data","txt");
			
			fc.setFileFilter(filter);
			int choice = fc.showSaveDialog(null);
			
			if(choice == JFileChooser.APPROVE_OPTION) {
				
				String filename = fc.getSelectedFile().getAbsolutePath();
				File f = new File(filename);

			FileHandler fh = new FileHandler(internal);	
			fh.exportXML(f);
			}

		}
	}
	
	static File imp;
	public static class Import extends AbstractAction {

		Gui UX;
		boolean created;
		public Import (Gui UX, Boolean created){
			this.UX = UX;
			this.created = created;
		}

		public void actionPerformed(ActionEvent e) {
			if(!created){
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Tab-Collated Excel File","txt");
				fc.setFileFilter(filter);
				int choice = fc.showOpenDialog(null);
				if(choice == JFileChooser.APPROVE_OPTION){
					UX.createImportMenu();
					imp = new File(fc.getSelectedFile().getAbsolutePath());
				}
			} else {
				String[] input = UX.getImportArgs();
				FileHandler fh = new FileHandler(imp);
				LinkedList<Person> list = fh.importTxt(input);
				fh.xmlWrite("data/temp.xml", list);
				fh = new FileHandler(internal);
				UX.setTableData(fh.to3DArray());
			}
		}
	}
}


