package suite;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Person {
	
	
	
	String party = "";
    String first = "";
    String last = "";
    String snum = "";//Street Number
    String sname = "";//Street Name
    String num = "";
    int rank = -1;
    int timesVoted = -1;
    int precinct = -1;
    String notes = "";
    int ID;

    int counter = 0;
    

    
    
    public Person(String f, String l) {
        this.first = f;
        this.last = l;
        this.ID = counter;
        counter++;
    }
    
    public Person(String f, String l, String num){
    	this.first = f;
        this.last = l;
        this.num = num;
        this.ID = counter;
        counter++;
    }

    public Person(String p, String f, String l, String stNum, String stName){
    	this.party = p;
    	this.first = f;
    	this.last = l;
    	this.snum = stNum;
    	this.sname = stName;
    	this.ID = counter;
    	counter++;
    }
    
    public Person(String p, String l, String f, String stNum, String stName, String rank, String timesVoted, String precinct, String notes, String phone){
    	if(first.equals("First Name")){
    		return;
    	}
    	this.party 			= p;
    	this.first 			= f;
    	this.last 			= l;
    	this.snum 			= stNum;
    	this.sname 			= stName;
    	if(rank.equals("")){
    		this.rank 		= -1;
    	} else {
    		this.rank		= Integer.parseInt(rank);
    	}
    	if(precinct.equals("")){
    		this.precinct 	= -1;
    	} else {
    		this.precinct	= Integer.parseInt(precinct);
    	}
    	if(timesVoted.length() > 0){
    		this.timesVoted	= timesVoted.length();
    	} else {
    		this.timesVoted = -1;
    	}
    	
    	this.notes 			= notes;
    	this.num 			= phone;
    	this.ID				= counter;
    	counter++;
    }
 
    public String getAddress(){
    	return (this.snum + this.sname);
    }
    
    public String getAllAvail(){
    	
    	String ret = "";
    	String[] val = this.toArray();
    	
    	for(int i = 0; i < 10 ; i++){
    		if(val[i] != "" && val[i] != "-1"){
    			ret += val[i] + "\t";  			
    		}
    	}
    	
    	return ret;
    	
    }
    
    public String[] toArray(){
    	

    	String[] ret = new String[10];
    	ret[0] = this.party;
    	ret[1] = this.first;
    	ret[2] = this.last;
    	ret[3] = this.snum;
    	ret[4] = this.sname;
    	ret[5] = this.num;
    	if(rank == -1){
    		ret[6] = "";
    	} else {
    		ret[6] = Integer.toString(this.rank);
    	}
    	if(timesVoted == -1){
    		ret[7] = "";
    	} else {
    		ret[7] = Integer.toString(this.timesVoted);
    	}
    	if(precinct == -1){
    		ret[8] = "";
    	} else {
    		ret[8] = Integer.toString(this.precinct);
    	}
    	ret[9] = this.notes;
    	
    	return ret;
    	
    }
    
    public String[] getLatLong(){
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
            
            return location;
            
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
		return null;
       
        
    }
    
}