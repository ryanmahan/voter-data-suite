package suite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileHandler {
	
	File file;
	
	public FileHandler(File f){
		file = f;
	}
	
	public LinkedList<Person> getList() {

     	
    	LinkedList<Person> list = null;
    	if(file.getName().endsWith(".txt")){
    		list = this.txtParse();
    	} else if(file.getName().endsWith(".xml")){
    		try {
				list = this.xmlParse();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else {
    		throw new IllegalArgumentException("File not a .xml or .txt");
    	}
    	
    	return list;
    	
	}
	
	
	public File xmlWrite(LinkedList<Person> list) {
		
		File output = null;
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("auburn");
			doc.appendChild(rootElement);
			
			Element people = doc.createElement("people");
			rootElement.appendChild(people);
			
			for(Person p : list){
				Element person = doc.createElement("person"); //create element
	    		person.setAttribute("first", p.first); //give it data
	    		person.setAttribute("last", p.last);
	    		person.setAttribute("number", p.num);
	    		person.setAttribute("sname", p.sname);
	    		person.setAttribute("snum", p.snum);
	    		person.setAttribute("notes", p.notes);
	    		person.setAttribute("rank", Integer.toString(p.rank));
	    		person.setAttribute("precinct", Integer.toString(p.precinct));
	    		person.setAttribute("timesVoted", Integer.toString(p.timesVoted));
	    		person.setAttribute("party", p.party);
	    	
	    		people.appendChild(person); //finalize element
			}
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
	        String date = df.format(new Date());
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//System.out.println("Writing: " + "list+"+date+".xml");
			output = new File("list+"+date+".xml");
			StreamResult result = new StreamResult(output);
			
			
			transformer.transform(source, result);
		
			
		}catch (Exception e){
			System.out.println("XML WRITE ERROR");
			e.printStackTrace();
		}
		return output;
		
		

			
			
	}

	//literally learned from tutorialpoint.com shoutout to them
	public LinkedList<Person> xmlParse() throws ParserConfigurationException, SAXException, IOException{
		
		LinkedList<Person> returnList = new LinkedList<Person>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version=\"1.0\"?> <class> </class>");
//		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8")); Used if inputting a new file or something
		Document doc = builder.parse(file);
		Element root = doc.getDocumentElement();
		NodeList listPersons = root.getElementsByTagName("person");
		
		for(int i = 0 ; i < listPersons.getLength() ; i++){    	
			Node curr = listPersons.item(i);
			if(curr.getNodeType() == Node.ELEMENT_NODE){
				Element person = (Element) curr;
				//Broke it up into these lines for readability's sake
				String party 		= person.getAttribute("party");
				String lname 		= person.getAttribute("last");
				String fname 		= person.getAttribute("first");
				String sname 		= person.getAttribute("sname");
				String snum  		= person.getAttribute("snum");
	    		String notes 		= person.getAttribute("notes");
	    		String rank	 		= person.getAttribute("rank");
	    		String precinct 	= person.getAttribute("precinct");
	    		String timesVoted 	= person.getAttribute("timesVoted");
	    		String phone	 	= person.getAttribute("number");
	    		 
	    		//make new person and add em to the list
				returnList.add(new Person(party, lname, fname, snum, sname, rank, timesVoted, precinct, notes, phone));
			}
		}
		
		
		return returnList;
		
	}
	
	public File xmlHouseWrite(LinkedList<House> houseList){
		
		File output = null;
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("auburn");
			doc.appendChild(rootElement);
			
			Element people = doc.createElement("people");
			rootElement.appendChild(people);
			
			for(House h : houseList){
				Element person = doc.createElement("person"); //create element
	    		person.setAttribute("first", h.head.first); //give it data
	    		person.setAttribute("last", h.head.last);
	    		person.setAttribute("number", h.head.num);
	    		person.setAttribute("sname", h.head.sname);
	    		person.setAttribute("snum", h.head.snum);
	    		person.setAttribute("notes", h.head.notes);
	    		person.setAttribute("rank", Integer.toString(h.head.rank));
	    		person.setAttribute("precinct", Integer.toString(h.head.precinct));
	    		person.setAttribute("timesVoted", Integer.toString(h.head.timesVoted));
	    		person.setAttribute("party", h.head.party);
	    	
	    		people.appendChild(person); //finalize element
			}
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
	        String date = df.format(new Date());
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//System.out.println("Writing: " + "list+"+date+".xml");
			output = new File("list+"+date+".xml");
			StreamResult result = new StreamResult(output);
			
			
			transformer.transform(source, result);
		
			
		}catch (Exception e){
			System.out.println("XML WRITE ERROR");
			e.printStackTrace();
		}
		return output;

	}

	public void displayXML(Gui UX){
		
		LinkedList<Person> list = null;
		
		try {
			list = this.xmlParse();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String display = "Party\tFirst Name\tLast Name\tSt Num\tSt Name\tPhone Num\tRank\tTimes Voted\tPrecinct\tNotes\n"; //Header
		
		for (Person p : list){
			String temp = p.getAllAvail();
			temp += "\n";
			display += temp;
		}
		
		UX.setTextArea(display);
		return;
		
	}

	//END XML R/W
	
	
	/* textParse()
	 * Also known as the hard-code-iest code Ive ever written intentionally
	 * 
	 * PLease forgive me, Ill figure out a way to make this legacy code one day
	 * The idea is to basically only make this a dev-only function
	 */
	public LinkedList<Person> txtParse(){
	    
		Scanner names = null;
		try {
			names = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        LinkedList<Person> list = new LinkedList<Person>();
        
        while (names.hasNextLine()) {
        	
            String curr = names.nextLine();
            String[] splits = curr.split("\t");
            if(splits.length == 2){
            	list.add(new Person(splits[0], splits[1]));
            }
            else if(splits.length == 3){
            	list.add(new Person(splits[0], splits[1], splits[2]));
            }
            else if(splits.length == 5){
            	list.add(new Person(splits[0], splits[2], splits[1], splits[3], splits[4]));
            }
            else if(splits.length == 9){
            	list.add(new Person(splits[0], splits[1], splits[2], splits[3], splits[4], "-1", splits[6], splits[5], splits[7], "1"));
            }
            else if(splits.length == 10){
            	list.add(new Person(splits[0], splits[1], splits[2], splits[3], splits[4], splits[5], splits[6], splits[7], splits[8], splits[9]));
            }
            
            
        }	
	    
	        
	    names.close();
	    return list;
	}
	
}

