package suite;

import java.util.LinkedList;
import suite.Person;

public class House {

	LinkedList<Person> members = new LinkedList<Person>();
	Person head;
	String landline;
	
	public House(){} //empty constructor
	
	public House(Person head){
		members.add(head);
		this.head = head;
	}
	
	public void removeMember(Person delete){
		members.remove(delete);
	}
	
	public void addMember(Person add){
		members.add(add);
	}
	
	public String getAddress(){
		return (head.snum + head.sname);
	}
	
	public int getSize(){
		return members.size();
	}
	
	public LinkedList<Person> getMembers(){
		return members;
	}
	
	
	
}
