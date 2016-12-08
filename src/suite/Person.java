package suite;

public class Person {
	
	
	
	String party = "";
    String first = "";
    String last = "";
    String snum = "";//Street Number
    String sname = "";//Street Name
    String num = "";
    int rank = -1;
    String timesVoted = "";
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
    	this.party 			= p;
    	this.first 			= f;
    	this.last 			= l;
    	this.snum 			= stNum;
    	this.sname 			= stName;
    	if(rank.equals("")){
    		this.rank 		= 0;
    	} else {
    		this.rank		= Integer.parseInt(rank);
    	}
    	if(precinct.equals("")){
    		this.precinct 	= 0;
    	} else {
    		this.precinct	= Integer.parseInt(precinct);
    	}
    	this.timesVoted 	= timesVoted;
    	this.notes 			= notes;
    	this.num 			= phone;
    	this.ID				= counter;
    	counter++;
    }
    
    public String getFirst() {
        return this.first;
    }

    public String getLast() {
        return this.last;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String input) {
        this.num = input;
    }
    
    public String getAddress(){
    	return (this.snum + this.sname);
    }
    
}