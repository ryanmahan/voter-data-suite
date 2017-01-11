package suite;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Person {
	
	
	
	String party = "";
    private String first = "";
    private String last = "";
    private String snum = "";//Street Number
    private String sname = "";//Street Name
    private String num = "";
    private String rank = "";
    private String timesVoted = "";
    private String precinct = "";
    private String notes = "";
    int ID;

    int counter = 0;
    double lat=0;
    double lng=0;
    
    public Person() {
	}
    
    public Person(String p, String l, String f, String stNum, String stName, String rank, String timesVoted, String precinct, String notes, String phone){
    	if(getFirst().equals("First Name")){
    		return;
    	}
    	this.party 			= p;
    	this.setFirst(f);
    	this.setLast(l);
    	this.setSnum(stNum);
    	this.setSname(stName);
    	this.setRank(rank);
    	this.precinct 		= precinct;
    	this.setTimesVoted(timesVoted);
    	this.setNotes(notes);
    	this.setNum(phone);
    	this.ID				= counter;
    	counter++;
    }

	public Person(String[] splits, String[] data) {
		
		for(int i = 0 ; i < splits.length ; i++){
			if(data[i].equalsIgnoreCase("empty")){
				continue;
			}
			else if(data[i].equalsIgnoreCase("Party")){
				this.party = splits[i];
			}
			else if(data[i].equalsIgnoreCase("First Name")){
				this.setFirst(splits[i]);
			}
			else if(data[i].equalsIgnoreCase("Last Name")){
				this.setLast(splits[i]);
			}
			else if(data[i].equalsIgnoreCase("Street Number")){
				this.setSnum(splits[i]);
			}
			else if(data[i].equalsIgnoreCase("Street Name")){
				this.setSname(splits[i]);
			}
			else if(data[i].equalsIgnoreCase("Precinct")){
				this.precinct = splits[i];
			}
			else if(data[i].equalsIgnoreCase("Phone Number")){
				this.num = splits[i];
			}
			else if(data[i].equalsIgnoreCase("Times Voted")){
				this.setTimesVoted(splits[i]);
			}
			else if(data[i].equalsIgnoreCase("Notes")){
				this.setNotes(splits[i]);
			}
		}
	}

	public String getAddress(){
    	return (this.getSnum() + " " + this.getSname());
    }
    
    public String getAllAvail(){
    	
    	String ret = "";
    	String[][] val = this.toArray();
    	
    	for(int i = 0; i < val.length ; i++){
    		if(val[i][0] != "" && val[i][0] != "-1"){
    			ret += val[i] + "\t";  			
    		}
    	}
    	
    	return ret;
    	
    }
    
    public String[][] toArray(){
    	
    	String[] ret = new String[10];
    	ret[0] = this.party;
    	ret[1] = this.getFirst();
    	ret[2] = this.getLast();
    	ret[3] = this.getSnum();
    	ret[4] = this.getSname();
    	ret[5] = this.getNum();
    	ret[6] = this.getRank();
    	ret[7] = this.getTimesVoted();
    	ret[8] = this.precinct;
    	ret[9] = this.getNotes();
    	
    	boolean filledIn[] = new boolean[10];
    	boolean visited[] = new boolean[10];
    	for(int i = 0 ; i < 10 ; i++){
    		filledIn[i] = false;
    		visited[i] = false;
    	}
    	int arraySize = 0;
    	for(int i = 0 ; i < 10 ; i++){
  		  if(!ret[i].equals("") && ret[i] != null && !ret[i].equals("-1")){
  			  filledIn[i] = true;
  			  arraySize++;
  		  }
  	  	}
    	
    	String output[][] = new String[arraySize][2];

    	for(int i = 0 ; i < arraySize ; i++){
    		if(filledIn[0] && !visited[0]){
    			output[i][0] = ret[0];
    			output[i][1] = "Party";
    			visited[0] = true;
    			continue;
    		}
    		else if(filledIn[1] && !visited[1]){
    			output[i][0] = ret[1];
    			output[i][1] = "First";
    			visited[1] = true;
    			continue;
    		}                
    		else if(filledIn[2] && !visited[2]){
    			output[i][0] = ret[2];
    			output[i][1] = "Last";
    			visited[2] = true;
    			continue;
    		}
    		else if(filledIn[3] && !visited[3]){
    			output[i][0] = ret[3];
    			output[i][1] = "St. Number";
    			visited[3] = true;
    			continue;
    		}
    		else if(filledIn[4] && !visited[4]){
    			output[i][0] = ret[4];
    			output[i][1] = "St. Name";
    			visited[4] = true;
    			continue;
    		}
    		else if(filledIn[5] && !visited[5]){
    			output[i][0] = ret[5];
    			output[i][1] = "Phone";
    			visited[5] = true;
    			continue;
    		}
    		else if(filledIn[6] && !visited[6]){
    			output[i][0] = ret[6];
    			output[i][1] = "Rank";
    			visited[6] = true;
    			continue;
    		}
    		else if(filledIn[7] && !visited[7]){
    			output[i][0] = ret[7];
    			output[i][1] = "Voted";
    			visited[7] = true;
    			continue;
    		}
    		else if(filledIn[8] && !visited[8]){
    			output[i][0] = ret[8];
    			output[i][1] = "Precinct";
    			visited[8] = true;
    			continue;
    		}
    		else if(filledIn[9] && !visited[9]){
    			output[i][0] = ret[9];
    			output[i][1] = "Notes";
    			visited[9] = true;
    			continue;
    		}
    	}

    	return output;
    	
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
            
            lat = Double.parseDouble(location[0]);
            lng = Double.parseDouble(location[1]);
            
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
    
	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSnum() {
		return snum;
	}

	public void setSnum(String snum) {
		this.snum = snum;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getTimesVoted() {
		return timesVoted;
	}

	public void setTimesVoted(String timesVoted) {
		this.timesVoted = timesVoted;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
    
}