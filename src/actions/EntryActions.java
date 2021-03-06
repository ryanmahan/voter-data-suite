package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import suite.FileHandler;
import suite.Person;
import ui.Gui;


@SuppressWarnings("serial")
public class EntryActions {

	private static final File internal = new File("data/temp.xml");
	
	public static class TableEdits extends AbstractAction {
		
		Gui UX;
		public TableEdits(Gui UX){
			this.UX = UX;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			UX.toggleTableEditing();
		}
		
	}
	
	public static class RankEntry extends AbstractAction {

		Gui UX;
		public RankEntry(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			
			
			for(Person p : list){
				
				String info = p.getLast() + ", " + p.getFirst() + " @ " + p.getAddress();
				String[] options = new String[] {"1", "2", "3", "4", "5", "Skip", "Stop"};
				int output = JOptionPane.showOptionDialog(null, "Please Rank " + info , "Rank Data Entry",
					        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					        null, options, options[0]);
			    
				if(output == 5){//skip
					p.setRank("0");
					continue;
				}
				if(output == 6){//stop
					for(Person q : list){
						if(q.getRank() == "")
							q.setRank("0");
					}
					break;
				}
				p.setRank(Integer.toString(output+1));
			}
			fh.xmlWrite(internal.getAbsolutePath(), list);
			UX.setTableData(fh.to3DArray());
		}
		
	}
	
	public static class NotesEntry extends AbstractAction {

		Gui UX;
		public NotesEntry(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO test
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			for(Person p : list){
				
				String info = p.getLast() + ", " + p.getFirst() + " @ " + p.getAddress();
				JFrame frame = new JFrame("Notes Data Entry");
				String output = JOptionPane.showInputDialog(frame, "Enter Notes for " + info);
			   
				Boolean filled = p.getNotes().length() > 0;
				
				if(filled){
					p.setNotes(p.getNotes().concat(" " + output));
				} else {
					p.setNotes(output);
				}
			    
				fh.xmlWrite(internal.getAbsolutePath(), list);
				UX.setTableData(fh.to3DArray());
			}
		}	
	}
		
	public static class NotHomeEntry extends AbstractAction {

		Gui UX;
		public NotHomeEntry(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			for(Person p : list){
				
				String info = p.getLast() + ", " + p.getFirst() + " @ " + p.getAddress();
				String[] options = new String[] {"Yes", "No", "Skip", "Cancel"};
				int output = JOptionPane.showOptionDialog(null, "Was " + info + " home?" , "Not Home Data Entry",
					        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					        null, options, options[0]);
			    
				
				
				String notes = p.getNotes();
				String homeIds[] = new String[]{"NH", "Was Home", "Home Not Entered"};
				
				for(String id : homeIds){
					
					if(notes.contains(id)){
						if(notes.length() == id.length())
							p.setNotes("");
					}else{
						int index = notes.indexOf(id);
						String before = notes.substring(0, index);
						String after = notes.substring(index+id.length()-1, notes.length()-1);
						p.setNotes(before + after);
					}
				}
				
				Boolean filled = p.getNotes().length() > 0;
				
				if(output == 0){//yes, home
					if(filled)
						p.setNotes(p.getNotes().concat(" Was Home"));
					else
						p.setNotes("Was Home");
				} else if(output == 1){//no not home
					if(filled)
						p.setNotes(p.getNotes().concat(" NH"));
					else
						p.setNotes("NH");
				} else if(output == 2){//skip
					if(filled)
						p.setNotes(p.getNotes().concat(" Home Not Entered"));
					else
						p.setNotes("Home Not Entered");
					continue;
				} else if(output == 3){//stop
					for(Person q : list){
						if(!q.getNotes().contains("NH") && !q.getNotes().contains("Was Home") && !q.getNotes().contains("Home Not Entered")){
							if(filled)
								q.setNotes(q.getNotes().concat(" Home Not Entered"));
							else
								q.setNotes("Home Not Entered");
						} 
					}
					break;
				}
			}	
			fh.xmlWrite(internal.getAbsolutePath(), list);
			UX.setTableData(fh.to3DArray());
		}		
	}
	
	public static class addColumn extends AbstractAction{

		Gui UX;
		String columnType;
		
		public addColumn(Gui UX, String columnType){
			this.UX = UX;
			this.columnType = columnType;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			String[][][] data = UX.getTableData();
			String[][][] withColumn = new String[data.length][data[0].length+1][data[0][0].length];
			System.out.println(columnType);
			
			for(int i = 0 ; i < data.length ; i++){
				for(int j = 0 ; j < data[0].length ; j++){
					for(int k = 0 ; k < data[0][0].length ; k++){
						withColumn[i][j][k] = data[i][j][k];
					}
				}
			}
			
			
			for(int i = 0 ; i < withColumn.length ; i++){
				withColumn[0][withColumn[0].length-1][1] = columnType;
				withColumn[0][withColumn[0].length-1][0] = ""; //TODO do this cleaner
			}
			
			UX.setTableData(withColumn);
			
			
			
		}
		
	}
	
}
