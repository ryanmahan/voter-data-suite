package suite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
	
	public FileHandler(File file){
		this.file = file;
	}

	
	public LinkedList<Person> getList() {

    	LinkedList<Person> list = null;
    	if(file.getName().endsWith(".xml")){
    		try {
				list = this.xmlParse();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else {
    		throw new IllegalArgumentException("File not a .xml");
    	}
    	
    	return list;
    	
	}
	
	public File xmlCreate (String filename, LinkedList<Person> list) {
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
	    		person.setAttribute("number", p.getNum());
	    		person.setAttribute("sname", p.sname);
	    		person.setAttribute("snum", p.snum);
	    		person.setAttribute("notes", p.notes);
	    		person.setAttribute("rank", p.rank);
	    		person.setAttribute("precinct", p.getPrecinct());
	    		person.setAttribute("timesVoted", p.timesVoted);
	    		person.setAttribute("party", p.party);
	    	
	    		people.appendChild(person); //finalize element
			}
			if(filename == null){
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		        String date = df.format(new Date());
		        output = new File("list+"+date+".xml");
			} else {
				output = new File(filename);
			}
			
		}catch (Exception e){
			System.out.println("XML CREATION ERROR");
			e.printStackTrace();
		}
		return output;
	}
	
	public File xmlWrite(String filename, LinkedList<Person> list) {
		
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
	    		person.setAttribute("number", p.getNum());
	    		person.setAttribute("sname", p.sname);
	    		person.setAttribute("snum", p.snum);
	    		person.setAttribute("notes", p.notes);
	    		person.setAttribute("rank", p.rank);
	    		person.setAttribute("precinct", p.getPrecinct());
	    		person.setAttribute("timesVoted", p.timesVoted);
	    		person.setAttribute("party", p.party);
	    	
	    		people.appendChild(person); //finalize element
			}
			if(filename == null){
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		        String date = df.format(new Date());
		        output = new File("list+"+date+".xml");
			} else {
				output = new File(filename);
			}
			
			
			
			//System.out.println("Writing: " + "list+"+date+".xml");
			
			StreamResult result = new StreamResult(output);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		
			
		}catch (Exception e){
			System.out.println("XML CREATION ERROR");
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
	    		person.setAttribute("first", h.getHead().first); //give it data
	    		person.setAttribute("last", h.getHead().last);
	    		person.setAttribute("number", h.getHead().getNum());
	    		person.setAttribute("sname", h.getHead().sname);
	    		person.setAttribute("snum", h.getHead().snum);
	    		person.setAttribute("notes", h.getHead().notes);
	    		person.setAttribute("rank", h.getHead().rank);
	    		person.setAttribute("precinct", h.getHead().getPrecinct());
	    		person.setAttribute("timesVoted", h.getHead().timesVoted);
	    		person.setAttribute("party", h.getHead().party);
	    		if(h.lat != 0){
	    			
	    		}
	    	
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

	public String xmlToString(){
		
		LinkedList<Person> list = null;
		
		try {
			list = this.xmlParse();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String display = "Party\tFirst Name\tLast Name\tSt Num\tSt Name\tPhone Num\tRank\tTimes Voted\tPrecinct\tNotes\n"; //Header
		
		for (Person p : list){
			String temp = p.getAllAvail();
			temp += "\n";
			display += temp;
		}
		
		return display;
		
	}
	
	public File convertToXML(String filename){
		file = (this.xmlCreate(filename, this.getList()));
		return (this.xmlCreate(filename, this.getList()));
	}
	
	public String[][][] to3DArray(){
		
		
		LinkedList<Person> list = this.getList();
		String[][][] output = new String[list.size()][list.peek().toArray().length][2];
		
		for(int i = 0 ; i < output.length ; i++){
			Person p = list.poll();
			output[i] = p.toArray();
		}
		
		return output;
	
	}

	//END XML R/W
	
	public LinkedList<Person> importTxt(String[] data){
		
		LinkedList<Person> list = new LinkedList<Person>();
		Scanner scans = null;
		try {
			scans = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(scans.hasNextLine()){
			String line = scans.nextLine();
			String[] splits = line.split("\t");
			list.add(new Person(splits, data));
		}
		
		return list;
	}
	
	public void exportXML(File f){
		
		if(!f.getName().endsWith("txt")){
			return;
		}
		
		String[][][] list = this.to3DArray();
		String firstLine = "";
		for(String s : list[0][0]){
			firstLine.concat(s);
			firstLine += "\t";
		}
		String data = "";
		for(String[][] a : list){
			for(int i = 0 ; i < a.length ; i++){
				data.concat(a[i][0]);
				data += "\t";
			}
			data += "\n";
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(file.getName()), "utf-8"))) {
		writer.write(firstLine + "\n" + data);
			
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
		
	}
	
}