package suite;

import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import suite.Person;

public class House {

	LinkedList<Person> members = new LinkedList<Person>();
	Person head;
	String landline;
	int ID;
	int counter = 0;
	boolean notHome;
	double lat;
	double lng;
	
	
	public House(){
		this.ID = counter;
		counter++;
	} //empty constructor
	
	public House(Person head){
		members.add(head);
		this.head = head;
		this.ID = counter;
		counter++;
	}
	
	public void removeMember(Person delete){
		members.remove(delete);
	}
	
	public void addMember(Person add){
		members.add(add);
	}
	
	public String getAddress(){
		return (head.snum + " " + head.sname);
	}
	
	public int getSize(){
		return members.size();
	}
	
	public LinkedList<Person> getMembers(){
		return members;
	}
	
	public void setNotHome(){
		
		boolean test = false;
		
		for(Person p : this.getMembers()){
			if(p.notes.contains("Not Home") || p.notes.contains("NH")){
				test = true;
			}
		}
		notHome = test;
	}
	
    public boolean getLatLong(){
    	org.jsoup.nodes.Document JsoupDoc = null;
        try {
        	String address = this.getAddress().replace(' ', '+');
        	
        	String link = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + address + " +,+Auburn,+MA";
        	System.out.println(link);
            JsoupDoc = Jsoup.connect((String)(link)).timeout(2000).get();
            W3CDom w3cDom = new W3CDom();
            org.w3c.dom.Document xmlDoc = w3cDom.fromJsoup(JsoupDoc);
            Element e = xmlDoc.getDocumentElement();
            Node n = e.getFirstChild();
            n.getNodeName();
            Element root = xmlDoc.getDocumentElement();
            //get first instance of elements text values, latitude and longitude
            String[] location = new String[2];
            location[0] = root.getElementsByTagName("lat").item(0).getTextContent();
            location[1] = root.getElementsByTagName("lng").item(0).getTextContent();
            
            lat = Double.parseDouble(location[0]);
            lng = Double.parseDouble(location[1]);
            
            return true;
            
        }
        catch (NullPointerException e){
        	lat = 0;
        	lng = 0;
        	return false;
        }
        catch (Exception e) {
        	System.out.println("error? " + e);
    		try {
    			//recursion to reattempt if connection is lost
    			Thread.sleep(2000);
    			this.getLatLong();
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		} 
    		
        }
		return false;
       
        
    }

}
