package suite;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.jsoup.Jsoup;
import java.io.*;


public class DataDriver {
	
    public static File phoneBankMaker(File fileName, Gui UX) throws IOException {
    	
    	FileHandler inputFileHandler = new FileHandler(fileName);
    	LinkedList<Person> voters = inputFileHandler.getList();
        
		double total = voters.size();
		double counter = 0;
        
        String HTML, num = "No phone number";
        String all = "";
        
        for (Person p : voters) {
        	counter++;
            HTML = HTMLGet(p);
            num = recursivePhoneFinder(HTML, num);
            p.num = num;
            String text = p.first + " " + p.last + " " + num + "\n";
            all = all.concat(text);
            UX.progressBar((int) ((counter/total)*100.0));
            UX.setTextArea(all);
            num = "No phone number";
        }
       
        return inputFileHandler.xmlWrite(voters);
        
    }
    
    public static File phoneFromFile(File f, Gui UX) throws IOException{
    	
    	FileHandler needFileIO = new FileHandler(f);
    	FileHandler masterFileIO = new FileHandler(new File("masterlist.txt"));
    	
    	LinkedList<Person> need = needFileIO.getList(); 
    	LinkedList<Person> master = masterFileIO.getList();
    	
    	double total = need.size();
        double counter = 0;
    	for(Person p : need){
    		p.num = "No phone number";
    		counter++;
    		
    		for(Person p2 : master){
    			if((p.first).equals(p2.first) && (p.last).equals(p2.last)){
    				p.num = p2.num;
    			}
    		}
    		
    		UX.progressBar((int) ((counter/total)*100.0));
    		
    	}
    	
    	//Output to TextArea
         
         String all = "";
         
    	for(Person p : need){
    		String text = p.first + " " + p.last + " " + p.num + "\n";
            all = all.concat(text);

    	}
    	UX.setTextArea(all);
    	
    	//Write to XML file and return said file
		return needFileIO.xmlWrite(need);
    	
    }

    /* HTMLGet
     * @returns a large HTML file formatted as a string
     * @param person object with the names filled in at the very least
     * 
     * Uses the JSoup API to download a HTML file from yellowpages
     * if the API returns an error (most likely a connection error from yellowpages blocking the IP)
     * 	Print the error, sleep for 10 seconds, then recursively try again with the same name, eventually returning the html file
     * 
     * if no error, return this HTML file as string
     * 
     */
    public static String HTMLGet(Person p) {
    	String html = null;
        try {
        	String streetName = p.sname;
        	streetName = streetName.replaceAll(" ", "+");
        	//String link = "http://www.whitepages.com/search/FindNearby?&street="+ p.snum + "+" + streetName + "&where=Auburn+Ma";
        	
        	//String link = "http://people.yellowpages.com/whitepages?first=" + p.first + "&last=" + p.last + "&zip=auburn&state=ma&site=79";
        	String link = "https://thatsthem.com/name/" + p.first + "-" + p.last + "/Auburn-MA";
        	System.out.println(link);
            html = Jsoup.connect((String)(link)).timeout(2000).get().html();
        }
        catch (Exception e) {
        	System.out.println("error? " + e);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			html = HTMLGet(p);
            return html;
        }
       
        return html;
    }

    /* recursivePhoneFinder
     * @returns String of the phone number of the HTML provided as a param
     * @param String Text - the HTML file as string provided by HTMLGet or a string to search through
     * @param num - the target num as a string
     * 
     * Searches for the phone numbers in Area, runs a loop over all areas, tests if its in the html input, then isolates it
     * if its not found, or if a text with these numbers but incorrect, it gets rid of the current finding by moving forward one
     * then running the function again. Does this until no area codes are found, then returns "No phone number"
     * 
     * Using an address-based http://www.whitepages.com/search/FindNearby?utf8=%E2%9C%93&street=129+Ramshorn+Rd&where=Charlton+Ma might work better
     */
    public static String recursivePhoneFinder(String text, String num){
    	String area[] = {"(508) 832", "(508) 407", "(508) 729", "(774) 221", "(508) 721"};
    	String areaByAddress[] = {"508-832" , "508-407" , "508-729" , "774-221" , "508-721"};
    	//System.out.println("Recursive loop");
    	for(int i = 0 ; i < area.length ; i++){
    		
        	if (text.contains(areaByAddress[i]) == true) {
                 int start = text.indexOf(areaByAddress[i].substring(0, 3));
                 num = text.substring(start, start + 12);//this is okay, start will never be at the front or end of the text
                 
            }
        }

    	if(num == "No phone number"){
    		return num;
    	}
    	return num;
    }
    
    //END PHONE METHODS
    
    //START HOUSE METHODS
    
    /* houseMaker(File)
     * @param File - either XML or tab-collated file
     * @return LinkedList<House> - a list containing all the houses made from File
     * 
     * Creates a list from a tokenized file, grabs an address, then searches for how many people following
     * the address have the same address, adding them to the same house as the first
     * 
     * If the same address is not found, creates new house with that address and then continues with that
     * address. 
     */
    public static LinkedList<House> houseMaker(File fin) {
    	
    	FileHandler handler = new FileHandler(fin);
    	LinkedList<Person> voters = handler.getList();
       
    	
    	LinkedList<House> houses = new LinkedList<House>();
    	String curr = null;
    	House nextHouse = null;
    	
    	for(Person p : voters){
    		if(p.getAddress().equals(curr)){//if the same house
    			
    			nextHouse.addMember(p);
    			
    		} else {//if a new house
    			nextHouse = new House(p);
    			houses.add(nextHouse);
    			curr = p.getAddress();
    		}
    	}
    	
    	return houses;
    	
    }
    
    /*houseNumbers
     * @param LinkedList<House> - a list of houses, probably made from houseMaker
     * 
     * iterates through the houses, and each houses members, collecting their phone numbers, and storing
     * duplicates as multiple numbers for the same house.
     * 
     * Uses the recursivePhoneFider and the HTMLGet functions.
     */
    public static void houseNumbers(LinkedList<House> list) throws InterruptedException{
    	
    	for(House home : list){
    		
    		LinkedList<Person> residents = home.getMembers();
    		home.landline = "No phone number";
    		
    		for(Person p : residents){
    			
    			String html = HTMLGet(p);
    			String number = "No phone number"; 
    			
    			System.out.println("HTML TEST: " + html.length());
    			
    			number = recursivePhoneFinder(html, number);
    			System.out.println("This num: " + number);
    			
    			/*if(number != "No phone number" && home.landline != "No phone number"){
    				number = " and/or " + number;
    				home.landline.concat(number);
    			}*/
    			
    			if(home.landline == "No phone number" && number != "No phone number"){
    				System.out.println("New num");
    				home.landline = number;
    			}    			
    		}
    		
    		if(home.landline.length() > 15){
    			home.landline = "Multiple numbers found: " + home.landline;
    		}
    		
    	} 

    	return;
    
    }
   
    /*sortByDist
     * @param LinkedList<House> a list of houses probably made by houseMaker
     * @returns 
     * 
     * Sorts through houses by determining length to each other and then sorting by that factor in a pqueue
     * first by using getLatLong
     */
	public static LinkedList<House> sortByDist(LinkedList<House> list){
		
		
		@SuppressWarnings("unchecked")
		LinkedList<House> precincts[] = new LinkedList[5];
		for(int i = 0 ; i < 5 ; i++){
			precincts[i] = new LinkedList<House>();
		}
		LinkedList<House> output = new LinkedList<House>();
		
		for(House h : list){
			precincts[h.head.precinct].add(h);
			h.getLatLong();
		}
		
		LinkedList<House> ret = new LinkedList<House>();
		for(LinkedList<House> pre : precincts){
			pre = recursiveSortHelper(pre.poll(), pre);
			ret.addAll(pre);
			
		}
		
		return ret;
		

	}
	//What am I even trying to do here
	private static LinkedList<House> recursiveSortHelper(House curr, LinkedList<House> list){
		
		if(list.size() > 1){
			House next = findClosestSortHelper(curr, list);
			list = recursiveSortHelper(next, list);
			list.remove(curr);
		} else {
			return list;
		}
		return list;
		
	}
	
	
	private static House findClosestSortHelper(House from, LinkedList<House> list){
		
		Comparator<HouseDist> comp = new HouseDist();
		PriorityQueue<HouseDist> pqueue = new PriorityQueue<HouseDist>(comp);
		
		System.out.println("FCSH List length: " + list.size());
		
		for(House h : list){
			HouseDist temp = new HouseDist(h, getDist(from, h));
			pqueue.add(temp);
		}
		
		return pqueue.poll().toHouse;
	}
	
	
    private static double getDist(House h1, House h2){
	   double xdif = h1.lat - h2.lat;
	   double ydif = h1.lng - h2.lng;
	   return (Math.sqrt(Math.pow(xdif, 2) + Math.pow(ydif, 2)));
   }
	
	
}
    
