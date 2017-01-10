package actions;


import java.io.File;
import java.util.LinkedList;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
			
			@SuppressWarnings("unused")
			
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

		@Override
		public void actionPerformed(ActionEvent e) {
			LinkedList<House> homeList = DataDriver.houseMaker(internal);
			  System.out.println(homeList.size());
			  for (House h : homeList){
				  h.setNotHome();
			  }
			  //TODO: Finish this
			
		}
		
	}
	
	public static class Distance extends AbstractAction{
		Gui UX;
		public Distance(Gui UX){
			this.UX = UX;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			 LinkedList<House> sortList = DataDriver.houseMaker(internal);
			  System.out.println("List Made: " + sortList.size());
			  
			  //sorry for this line below
			  if(Integer.parseInt(sortList.peek().getHead().getPrecinct()) == -1){
				  //this.setTextArea("No Precincts found, please enter file with Precincts!");
				  return;
			  }
			  if(sortList.size() == 0){
				  //this.setTextArea("No Homes Found");
				  return;
			  }

			  sortList = DataDriver.sortByDist(sortList);
			  System.out.println("Recieved List of Houses: " + sortList.size());

			  FileHandler fhSort = new FileHandler(internal);
			  fhSort.xmlHouseWrite(sortList);
			  UX.setTableData(fhSort.to3DArray());
			  
			
		}
		
	}
	
	
	public static class Combine extends AbstractAction{
		Gui UX;
		public Combine(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO bugtest
			JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Ryans Voter Data","txt");
			
			fc.setFileFilter(filter);
			int choice = fc.showSaveDialog(null);
			File f = null;
			if(choice == JFileChooser.APPROVE_OPTION) {
				String filename = fc.getSelectedFile().getAbsolutePath();
				f = new File(filename);
				
				FileHandler fhInt = new FileHandler(internal);
				FileHandler fhExt = new FileHandler(f);
				
				LinkedList<Person> list1 =  fhInt.getList();
				LinkedList<Person> list2 = fhExt.getList();
				LinkedList<Person> output = new LinkedList<Person>();
				
				for(Person p1 :  list1){
					for(Person p2 : list2){
						PersonCombiner pc = new PersonCombiner(p1, p2);
						if(pc.isMatch()){
							list1.remove(p1);
							list2.remove(p2);
							output.add(pc.combine());
						}
					}
				}
				
				fhInt.xmlWrite(internal.getName(), output);
				UX.setTableData(fhInt.to3DArray());
				
			}
			
			
			
		}

	}
	
}
