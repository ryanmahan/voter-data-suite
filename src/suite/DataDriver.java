package suite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import java.util.Scanner;
import org.jsoup.Jsoup;
import suite.*;


public class DataDriver {
	
    public static File phoneBankMaker(File fileName, Gui UX) throws IOException {
    	
        LinkedList<Person> voters = tokenizer(fileName);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
        String date = df.format(new Date());
        System.out.println("File is named: Phonebank "+ date + ".txt");
        File output = new File("PhoneBank " + date + ".txt");
        PrintWriter out = null;
      
			output.createNewFile();
			out = new PrintWriter(output);
		
        
        
        String HTML, num = "No phone number";
        String all = "";
        for (Person p : voters) {
            HTML = HTMLGet(p);
            num = recursivePhoneFinder(HTML, num);
            p.num = num;
            String text = p.getFirst() + " " + p.getLast() + " " + num + "\n";
            all = all.concat(text);
            UX.setTextArea(all);
            out.println(text);
            out.flush();
            num = "No phone number";
        }
        
        out.close();
        return output;
        
    }
    /* Tokenizer
     * @returns a LinkedList of Person objects, filled in with info from a file
     * @param a file of tab-delimited names "FIRST	LAST"
     * 
     * Runs through every line in the file, splitting the line on tabs, then creates a new person object
     * and adds person object to the list
     */
    public static LinkedList<Person> tokenizer(File f)  {
    	
        Scanner names = null;
		try {
			names = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        LinkedList<Person> list = new LinkedList<Person>();
        
        while (names.hasNextLine()) {
        	
            String curr = names.nextLine();
            String[] splits = curr.split("\t");
            if(splits.length == 2){
            	list.add(new Person(splits[0], splits[1]));
            }
            if(splits.length == 5){
            	list.add(new Person(splits[0], splits[2], splits[1], splits[3], splits[4]));
            }
            
        }
        
        names.close();
        return list;
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
    	
    	//if(num.matches(".*[a-zA-Z]+.*") && text.length() > 0){
    		//num = num.substring(1, num.length());
    		//num = recursivePhoneFinder(text, num);
    	//}
    	
    	return num;
    }
    
    //END PHONE METHODS
    
    //START HOUSE METHODS
    
    public static LinkedList<House> houseMaker(File fin) {
    	
    	LinkedList<Person> voters = tokenizer(fin);
    	
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
    
    	
    	/*System.out.println("Test print");
    	
    	for(House test : houses){
    		System.out.println("NEW HOUSE");
    		for(Person p : test.getMembers()){
        		System.out.println("\t" + p.first);
        	}
    	}*/
  
    }
    
    
}