package suite;

public class PersonCombiner {

	Person p1;
	Person p2;
	
	
	public PersonCombiner(Person p1, Person p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean isMatch(){
		
		if(!p1.first.isEmpty() && !p2.first.isEmpty()){//if both not empty
			if(!p1.first.equalsIgnoreCase(p2.first)){//if do not equal
				return false;//return false, we dont care about the rest of this method, since one data point didnt match
			}
		}
		if(!p1.last.isEmpty() && !p2.last.isEmpty()){
			if(!p1.last.equalsIgnoreCase(p2.last)){
				return false;
			}
		}
		if(!p1.snum.isEmpty() && !p2.snum.isEmpty()){
			if(!p1.snum.equalsIgnoreCase(p2.snum)){
				return false;
			}
		}
		if(!p1.sname.isEmpty() && !p2.sname.isEmpty()){
			if(!p1.sname.equalsIgnoreCase(p2.sname)){
				return false;
			}
		}
		if(!p1.num.isEmpty() && !p2.num.isEmpty()){
			if(!p1.num.equalsIgnoreCase(p2.num)){
				return false;
			}
		}
		if(!p1.rank.isEmpty() && !p2.rank.isEmpty()){
			if(!p1.rank.equalsIgnoreCase(p2.rank)){
				return false;
			}
		}
		if(!p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			if(!p1.getPrecinct().equalsIgnoreCase(p2.getPrecinct())){
				return false;
			}
		}
		if(!p1.timesVoted.isEmpty() && !p2.timesVoted.isEmpty()){
			if(!p1.timesVoted.equalsIgnoreCase(p2.timesVoted)){
				return false;
			}
		}
		if(!p1.notes.isEmpty() && !p2.notes.isEmpty()){
			if(!p1.notes.equalsIgnoreCase(p2.notes)){
				return false;
			}
		}
		return true;
	}
	
	public Person combine(){
		
		Person output = new Person();
		
		if(!p1.first.isEmpty() && p2.first.isEmpty()){
			output.first = p1.first;
		} else if(p1.first.isEmpty() && !p2.first.isEmpty()){
			output.first = p2.first;
		} else if(!p1.first.isEmpty() && !p2.first.isEmpty()){
			output.first = p1.first;
		}
		if(!p1.last.isEmpty() && p2.last.isEmpty()){
			output.last = p1.last;
		} else if(p1.last.isEmpty() && !p2.last.isEmpty()){
			output.last = p2.last;
		} else if(!p1.last.isEmpty() && !p2.last.isEmpty()){
			output.last = p1.last;
		}
		if(!p1.snum.isEmpty() && p2.snum.isEmpty()){
			output.snum = p1.snum;
		} else if(p1.snum.isEmpty() && !p2.snum.isEmpty()){
			output.snum = p2.snum;
		} else if(!p1.snum.isEmpty() && !p2.snum.isEmpty()){
			output.snum = p1.snum;
		}
		if(!p1.sname.isEmpty() && p2.sname.isEmpty()){
			output.sname = p1.sname;
		} else if(p1.sname.isEmpty() && !p2.sname.isEmpty()){
			output.sname = p2.sname;
		} else if(!p1.sname.isEmpty() && !p2.sname.isEmpty()){
			output.sname = p1.sname;
		}
		if(!p1.num.isEmpty() && p2.num.isEmpty()){
			output.num = p1.num;
		} else if(p1.num.isEmpty() && !p2.num.isEmpty()){
			output.num = p2.num;
		} else if(!p1.num.isEmpty() && !p2.num.isEmpty()){
			output.num = p1.num;
		}
		if(!p1.rank.isEmpty() && p2.rank.isEmpty()){
			output.rank = p1.rank;
		} else if(p1.rank.isEmpty() && !p2.rank.isEmpty()){
			output.rank = p2.rank;
		} else if(!p1.rank.isEmpty() && !p2.rank.isEmpty()){
			output.rank = p1.rank;
		}
		if(!p1.getPrecinct().isEmpty() && p2.getPrecinct().isEmpty()){
			output.setPrecinct(p1.getPrecinct());
		} else if(p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			output.setPrecinct(p2.getPrecinct());
		} else if(!p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			output.setPrecinct(p1.getPrecinct());
		}
		if(!p1.timesVoted.isEmpty() && p2.timesVoted.isEmpty()){
			output.timesVoted = p1.timesVoted;
		} else if(p1.timesVoted.isEmpty() && !p2.timesVoted.isEmpty()){
			output.timesVoted = p2.timesVoted;
		} else if(!p1.timesVoted.isEmpty() && !p2.timesVoted.isEmpty()){
			output.timesVoted = p1.timesVoted;
		}
		if(!p1.notes.isEmpty() && p2.notes.isEmpty()){
			output.notes = p1.notes;
		} else if(p1.notes.isEmpty() && !p2.notes.isEmpty()){
			output.notes = p2.notes;
		} else if(!p1.notes.isEmpty() && !p2.notes.isEmpty()){
			output.notes = p1.notes;
		}
		
		return output;
		
	}
	
}
