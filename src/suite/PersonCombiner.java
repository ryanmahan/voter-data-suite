package suite;

public class PersonCombiner {

	Person p1;
	Person p2;
	
	
	public PersonCombiner(Person p1, Person p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean isMatch(){
		
		if(!p1.getFirst().isEmpty() && !p2.getFirst().isEmpty()){//if both not empty
			if(!p1.getFirst().equalsIgnoreCase(p2.getFirst())){//if do not equal
				return false;//return false, we dont care about the rest of this method, since one data point didnt match
			}
		}
		if(!p1.getLast().isEmpty() && !p2.getLast().isEmpty()){
			if(!p1.getLast().equalsIgnoreCase(p2.getLast())){
				return false;
			}
		}
		if(!p1.getSnum().isEmpty() && !p2.getSnum().isEmpty()){
			if(!p1.getSnum().equalsIgnoreCase(p2.getSnum())){
				return false;
			}
		}
		if(!p1.getSname().isEmpty() && !p2.getSname().isEmpty()){
			if(!p1.getSname().equalsIgnoreCase(p2.getSname())){
				return false;
			}
		}
		if(!p1.getNum().isEmpty() && !p2.getNum().isEmpty()){
			if(!p1.getNum().equalsIgnoreCase(p2.getNum())){
				return false;
			}
		}
		if(!p1.getRank().isEmpty() && !p2.getRank().isEmpty()){
			if(!p1.getRank().equalsIgnoreCase(p2.getRank())){
				return false;
			}
		}
		if(!p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			if(!p1.getPrecinct().equalsIgnoreCase(p2.getPrecinct())){
				return false;
			}
		}
		if(!p1.getTimesVoted().isEmpty() && !p2.getTimesVoted().isEmpty()){
			if(!p1.getTimesVoted().equalsIgnoreCase(p2.getTimesVoted())){
				return false;
			}
		}
		if(!p1.getNotes().isEmpty() && !p2.getNotes().isEmpty()){
			if(!p1.getNotes().equalsIgnoreCase(p2.getNotes())){
				return false;
			}
		}
		return true;
	}
	
	public Person combine(){
		
		Person output = new Person();
		
		if(!p1.getFirst().isEmpty() && p2.getFirst().isEmpty()){
			output.setFirst(p1.getFirst());
		} else if(p1.getFirst().isEmpty() && !p2.getFirst().isEmpty()){
			output.setFirst(p2.getFirst());
		} else if(!p1.getFirst().isEmpty() && !p2.getFirst().isEmpty()){
			output.setFirst(p1.getFirst());
		}
		if(!p1.getLast().isEmpty() && p2.getLast().isEmpty()){
			output.setLast(p1.getLast());
		} else if(p1.getLast().isEmpty() && !p2.getLast().isEmpty()){
			output.setLast(p2.getLast());
		} else if(!p1.getLast().isEmpty() && !p2.getLast().isEmpty()){
			output.setLast(p1.getLast());
		}
		if(!p1.getSnum().isEmpty() && p2.getSnum().isEmpty()){
			output.setSnum(p1.getSnum());
		} else if(p1.getSnum().isEmpty() && !p2.getSnum().isEmpty()){
			output.setSnum(p2.getSnum());
		} else if(!p1.getSnum().isEmpty() && !p2.getSnum().isEmpty()){
			output.setSnum(p1.getSnum());
		}
		if(!p1.getSname().isEmpty() && p2.getSname().isEmpty()){
			output.setSname(p1.getSname());
		} else if(p1.getSname().isEmpty() && !p2.getSname().isEmpty()){
			output.setSname(p2.getSname());
		} else if(!p1.getSname().isEmpty() && !p2.getSname().isEmpty()){
			output.setSname(p1.getSname());
		}
		if(!p1.getNum().isEmpty() && p2.getNum().isEmpty()){
			output.setNum(p1.getNum());
		} else if(p1.getNum().isEmpty() && !p2.getNum().isEmpty()){
			output.setNum(p2.getNum());
		} else if(!p1.getNum().isEmpty() && !p2.getNum().isEmpty()){
			output.setNum(p1.getNum());
		}
		if(!p1.getRank().isEmpty() && p2.getRank().isEmpty()){
			output.setRank(p1.getRank());
		} else if(p1.getRank().isEmpty() && !p2.getRank().isEmpty()){
			output.setRank(p2.getRank());
		} else if(!p1.getRank().isEmpty() && !p2.getRank().isEmpty()){
			output.setRank(p1.getRank());
		}
		if(!p1.getPrecinct().isEmpty() && p2.getPrecinct().isEmpty()){
			output.setPrecinct(p1.getPrecinct());
		} else if(p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			output.setPrecinct(p2.getPrecinct());
		} else if(!p1.getPrecinct().isEmpty() && !p2.getPrecinct().isEmpty()){
			output.setPrecinct(p1.getPrecinct());
		}
		if(!p1.getTimesVoted().isEmpty() && p2.getTimesVoted().isEmpty()){
			output.setTimesVoted(p1.getTimesVoted());
		} else if(p1.getTimesVoted().isEmpty() && !p2.getTimesVoted().isEmpty()){
			output.setTimesVoted(p2.getTimesVoted());
		} else if(!p1.getTimesVoted().isEmpty() && !p2.getTimesVoted().isEmpty()){
			output.setTimesVoted(p1.getTimesVoted());
		}
		if(!p1.getNotes().isEmpty() && p2.getNotes().isEmpty()){
			output.setNotes(p1.getNotes());
		} else if(p1.getNotes().isEmpty() && !p2.getNotes().isEmpty()){
			output.setNotes(p2.getNotes());
		} else if(!p1.getNotes().isEmpty() && !p2.getNotes().isEmpty()){
			output.setNotes(p1.getNotes());
		}
		
		return output;
		
	}
	
}
