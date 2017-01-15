package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import suite.DataDriver;
import suite.FileHandler;
import suite.Gui;
import suite.House;
import suite.Person;

@SuppressWarnings("serial")
public class SortMenuActions {

	private final static File internal = new File("data/temp.xml");
	
	
	public static class street extends AbstractAction{

		Gui UX;
		public street(Gui UX){
			this.UX = UX;
		}

		public void actionPerformed(ActionEvent arg0) {
			
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			Comparator<Person> comparatorSname = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSname().compareToIgnoreCase(p2.getSname());
	            }
	                  };
	        System.out.println(list.poll().getAllAvail());   
			Collections.sort(list, comparatorSname);
			System.out.println(list.poll().getAllAvail());  
			Comparator<Person> comparatorSnum = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSnum().compareTo(p2.getSnum());
	            }
	                  };     
	                  
	       list.sort(comparatorSnum);
	       
	       fh.xmlWrite(internal.getAbsolutePath(), list);
	       UX.setTableData(fh.to3DArray());
	       
		}
	}
	
	public static class name extends AbstractAction{

		Gui UX;
		public name(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			Comparator<Person> comparatorLast = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getLast().compareTo(p2.getLast());
	            }
	                  }; 
	                  
	       Collections.sort(list, comparatorLast);
	       
	       Comparator<Person> comparatorFirst = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	            	if(p1.getLast().equals(p2.getLast()))
	            		return p1.getFirst().compareTo(p2.getFirst());
	            	else
	            		return 0;
	            }
	                  };
	                  
	       Collections.sort(list, comparatorFirst); 
	       
	       fh.xmlWrite(internal.getAbsolutePath(), list);
	       UX.setTableData(fh.to3DArray());
		}
	}
	
	public static class precinct extends AbstractAction{

		Gui UX;
		public precinct(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			Comparator<Person> comparatorPrecinct = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getPrecinct().compareTo(p2.getPrecinct());
	            }
	                  }; 
	                  
	        list.sort(comparatorPrecinct);  
	        
	        Comparator<Person> comparatorSname = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSname().compareTo(p2.getSname());
	            }
	                  };
	                  
			list.sort(comparatorSname);
			
			Comparator<Person> comparatorSnum = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	            	if(p1.getSname().equals(p2.getSname()))
	            		return p1.getSnum().compareTo(p2.getSnum());
	            	else 
	            		return 0;
	            }
	                  };     
	                  
	       list.sort(comparatorSnum);
	       
	       fh.xmlWrite(internal.getAbsolutePath(), list);
	       UX.setTableData(fh.to3DArray());
	        
		}
	}
	
	public static class rank extends AbstractAction{

		Gui UX;
		public rank(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			Comparator<Person> comparatorRank = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getRank().compareTo(p2.getRank());
	            }
	                  }; 
	                  
	        list.sort(comparatorRank);
	        
	        Comparator<Person> comparatorSname = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSname().compareTo(p2.getSname());
	            }
	                  };
	                  
			list.sort(comparatorSname);
			
			Comparator<Person> comparatorSnum = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSnum().compareTo(p2.getSnum());
	            }
	                  };     
	                  
	       list.sort(comparatorSnum);
	       
	       fh.xmlWrite(internal.getAbsolutePath(), list);
	       UX.setTableData(fh.to3DArray());
		}
	}
	
	public static class timesVoted extends AbstractAction{

		Gui UX;
		public timesVoted(Gui UX){
			this.UX = UX;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			FileHandler fh = new FileHandler(internal);
			LinkedList<Person> list = fh.getList();
			
			Comparator<Person> comparatorTimesVoted = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getTimesVoted().compareTo(p2.getTimesVoted());
	            }
	                  }; 
	                  
	        list.sort(comparatorTimesVoted);
	        
	        Comparator<Person> comparatorSname = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSname().compareTo(p2.getSname());
	            }
	                  };
	                  
			list.sort(comparatorSname);
			
			Comparator<Person> comparatorSnum = new Comparator<Person>() {
	            @Override
	            public int compare(Person p1, Person p2) {
	                return p1.getSnum().compareTo(p2.getSnum());
	            }
	                  };     
	                  
	       list.sort(comparatorSnum);
	       
	       fh.xmlWrite(internal.getAbsolutePath(), list);
	       UX.setTableData(fh.to3DArray());
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
				  JFrame frame = new JFrame();
				  JOptionPane.showMessageDialog(frame,
						    "No precincts found on list");
			  }
			  if(sortList.size() == 0){
				  JFrame frame = new JFrame();
				  JOptionPane.showMessageDialog(frame,
						    "No Houses found on list");
				  return;
			  }

			  sortList = DataDriver.sortByDist(sortList, UX);
			  System.out.println("Recieved List of Houses: " + sortList.size());

			  FileHandler fhSort = new FileHandler(internal);
			  fhSort.xmlHouseWrite(sortList);
			  UX.setTableData(fhSort.to3DArray());
			  
			
		}
		
	}

	
	
}
