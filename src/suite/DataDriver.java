package suite;


import java.util.LinkedList;
import org.jsoup.Jsoup;
import java.io.*;


public class DataDriver {
	
    public static File phoneBankMaker(File fileName, Gui UX) throws IOException {
    	
    	FileHandler inputFileHandler = new FileHandler(fileName);
    	LinkedList<Person> voters = inputFileHandler.getList();
        
		double total = voters.size();
		double counter = 0;
        
        String HTML, num = "No phone number";
        Object[] elements = UX.createProgressBar(0, "Phone Banking from Internet");
        
        
        for (Person p : voters) {
        	UX.setProgress(elements, counter, total);
        	counter++;
            HTML = HTMLGet(p);
            num = recursivePhoneFinder(HTML, num);
            p.setNum(num);
            num = "No phone number";
        }
        File output = inputFileHandler.xmlWrite("data/temp.xml", voters);
        UX.setTableData(inputFileHandler.to3DArray());
        //TODO: Progress Bar
        return output;
        
    }
    
    public static File phoneFromFile(File f, Gui UX) throws IOException{
    	
    	FileHandler needFileIO = new FileHandler(f);
    	FileHandler masterFileIO = new FileHandler(new File("masterlist.txt"));
    	
    	LinkedList<Person> need = needFileIO.getList(); 
    	LinkedList<Person> master = masterFileIO.getList();
    	
    	double total = need.size();
        double counter = 0;
    	for(Person p : need){
    		p.setNum("No phone number");
    		counter++;
    		
    		for(Person p2 : master){
    			if((p.first).equals(p2.first) && (p.last).equals(p2.last)){
    				p.setNum(p2.getNum());
    			}
    		}
    		
    		
    		
    	}
    	
    	File output = needFileIO.xmlWrite("data/temp.xml", need);
    	UX.setTableData(needFileIO.to3DArray());
    	

    	
    	//Write to XML file and return said file
		return output;
    	
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
            html = Jsoup.connect((String)(link)).timeout(5000).get().html();
        }
        catch (Exception e) {
        	System.out.println("error? " + e);
			try {
				Thread.sleep(3000);
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
     * Breaks list into precincts since thats a huge efficiency increase but a minimal impact on final product
     * Runs through lists in recursiveSortHelper
     * Combines the list into a larger list used to compile the precincts back together
     * 
     * But why break them into precincts?
     * Well, if I run 2500 houses through a list here, I have to run it at O(n^2) but if I break it into the precincts
     * 		first, I end up running (500^2)*5, which is 5 times more efficient!
     * 
     */
    
	public static LinkedList<House> sortByDist(LinkedList<House> list){
		
		//double progress = 0;
		
		@SuppressWarnings("unchecked")
		LinkedList<House> precincts[] = new LinkedList[5];
		for(int i = 0 ; i < 5 ; i++){
			precincts[i] = new LinkedList<House>();
		}
		
		for(House h : list){
			int precinct = Integer.parseInt(h.getHead().getPrecinct());
			if(precinct > 5){
				precincts[4].add(h);
			} else {
				precincts[precinct-1].add(h);
			}
			
			if(!h.getLatLong()){
				System.out.println(h.getAddress());
				precincts[precinct-1].remove(h);
			}
			//progress++;
			//TODO: Progress bar
		}
		
		System.out.println("Got all lat long, moving on");
		
		LinkedList<House> output = new LinkedList<House>();
		
		for(LinkedList<House> pre : precincts){
			LinkedList<House> preOut = new LinkedList<House>();
			preOut = recursiveSortHelper(pre.peek(), pre, preOut);
			output.addAll(preOut);
			//progress++;
			//UX.progressBar((int) progress/list.size()/2); 
		}
		
		for(House h : output){
			if(h != null){
				System.out.println(h.getAddress());
			} else {
				System.out.println("null house found");
			}
		}
		
		return output;
		

	}

	/*recursiveSortHelper
	 * @param House curr - the current house we are using as a node in our LL of houses
	 * @param LLHouse input - the list we used as input, also contains the list of unvisited houses
	 * @param LLHouse output - the list we used as output, containing the list of visited houses, in order of distance
	 * 		from the first curr, by default the first house in the precinct
	 * @returns LLHouse - the houses visited in the order of distance
	 * 
	 * Runs through the input list recursively until there is no more curr->next, at which point curr must be the closest
	 * 		to the previous node
	 * Remove the current node, so it doesnt return it being closest to itself
	 * Find the closest next node
	 * Add our current node to the output list
	 * Run on the next node
	 * 
	 */
	private static LinkedList<House> recursiveSortHelper(House curr, LinkedList<House> input, LinkedList<House> output){
		
		if(input.size() > 1){
			
			input.remove(curr);
			House next = findClosestSortHelper(curr, input);
			output.add(curr);
			/* Debug!
			System.out.println(input.size() + " " + output.size());
			System.out.println(curr.getAddress());
			System.out.println(next.getAddress() + "\n");
			*/
			output = recursiveSortHelper(next, input, output);
			
		} else {
			return output;
		}
		return output;
		
	}
	
	
	private static House findClosestSortHelper(House from, LinkedList<House> list){
		HouseDist ret = null;
		double low = 100000;
		for(House h : list){
			HouseDist temp = new HouseDist(h, getDist(from, h));
			if(temp.distance < low){
				ret = temp;
				low = temp.distance;
			}
		}
		
		return ret.toHouse;
	}
	
	
    private static double getDist(House h1, House h2){
	   double xdif = h1.lat - h2.lat;
	   double ydif = h1.lng - h2.lng;
	   return (Math.sqrt(Math.pow(xdif, 2) + Math.pow(ydif, 2)));
   }
	
	
}
    
