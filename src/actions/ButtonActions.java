package actions;

import java.io.File;
import java.util.LinkedList;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import suite.DataDriver;
import suite.FileHandler;
import suite.Gui;
import suite.House;
import suite.Person;
import suite.PersonCombiner;

@SuppressWarnings("serial")
public class ButtonActions {
	
	private final static File internal = new File("data/temp.xml");
	
	public static class PhoneBank extends AbstractAction{

		Gui UX;
		public PhoneBank(Gui g){
			UX = g;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object[] options = {"Net" , "File" , "Cancel"};
			JFrame popUp = new JFrame("Pop Up");
			int n = JOptionPane.showOptionDialog(popUp, "Would you like to phonebank from the masterlist or net?",
					"File or Net?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[2]);
			if(n == 0){//net
				try {
					DataDriver.phoneBankMaker(internal, UX);
				} catch (Exception except) {
					except.printStackTrace();
				}
			} else if(n == 1){//file
				FileHandler fh = new FileHandler(internal);
				FileHandler mast = new FileHandler(new File("data/masterlist.xml"));
				LinkedList<Person> masterList = mast.getList();
				LinkedList<Person> need = fh.getList();
				System.out.println("Running");
				for(Person p : need){
					for(Person m : masterList){
						PersonCombiner pc = new PersonCombiner(p, m);
						if(pc.isMatch()){
							p.setNum(m.getNum());
						}
					}
				}
				for(Person p : need){
					if(p.getNum().equals("")){
						p.setNum("None Found");
					}
				}
		    	fh.xmlWrite("data/temp.xml", need);
				System.out.println("Done");
		    	UX.setTableData(fh.to3DArray());
				
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
			
			FileHandler fh = new FileHandler(internal);
	       
			LinkedList<Person> voters = fh.getList();
	    	LinkedList<House> houses = new LinkedList<House>();
	    	String curr = null;
	    	House nextHouse = null;
	    	
	    	
	    	
	    	
	    	for(Person p : voters){
	    		if(p.getAddress().equals(curr)){//if the same house
	    			
	    			nextHouse.addMember(p);
	    			
	    		} else {//if a new house
	    			nextHouse = new House(p);
	    			houses.add(nextHouse);
	    			curr = p.getAddress();
	    		}
	    	}
	    	
	    	fh.xmlHouseWrite(houses);
			UX.setTableData(fh.to3DArray());
		
		}
		
	}
	
	public static class NotHome extends AbstractAction{
		//TODO: Bugtest
		Gui UX;
		
		public NotHome(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LinkedList<House> homeList = DataDriver.houseMaker(internal);
			LinkedList<Person> notHomes = new LinkedList<Person>();
			for (House h : homeList){
				h.setNotHome();
				if(h.isNotHome()){
					notHomes.add(h.getHead());
				}
			}
			
			FileHandler fh = new FileHandler(internal);
			fh.xmlWrite("data/temp.xml", notHomes);
			UX.setTableData(fh.to3DArray());
		}
		
	}
	
	
	public static class Combine extends AbstractAction{
		Gui UX;
		public Combine(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Write new Combine Method
		}
	}
	
	public static class saveTableEdits extends AbstractAction{
		
		Gui UX;
		public saveTableEdits(Gui UX){
			this.UX = UX;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			Object[][][] array = UX.getTableData();
			String[] dataType = new String[array[0].length];
			LinkedList<Person> list = new LinkedList<Person>();
			
			for(int i = 0 ; i < array[0].length ; i++){
				dataType[i] = (String) array[0][i][1];
				System.out.println(dataType[i]);
			}
			
			for(int i = 0 ; i < array.length ; i++){
				String[] values = new String[array[0].length];
				for(int j = 0 ; j < array[0].length ; j++){
					values[j] = (String) array[i][j][0];
				}
				list.add(new Person(values, dataType));
			}
			
			FileHandler fh = new FileHandler(internal);
			fh.xmlWrite(internal.getAbsolutePath(), list);
			UX.setTableData(fh.to3DArray());
			
		}
		
		
		
	}
	
}
