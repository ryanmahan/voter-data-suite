package suite;

public class Person {
	String party;
    String first;
    String last;
    String snum;//Street Number
    String sname;//Street Name
    String num = null;
    int rank;
    int timesVoted;
    int precinct;
    String notes;

    public Person(String f, String l) {
        this.first = f;
        this.last = l;
    }

    public Person(String p, String f, String l, String stNum, String stName){
    	this.party = p;
    	this.first = f;
    	this.last = l;
    	this.snum = stNum;
    	this.sname = stName;
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