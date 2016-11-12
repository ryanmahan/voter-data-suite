package suite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;

import suite.Person;

public class PhoneDriver {
    public static void main(String[] args) throws Exception {
        Scanner user = new Scanner(System.in);
        System.out.println("Please name a tab-collated file with First/Last Names to a line");
        String fileName = user.nextLine();
        user.close();
        List<Person> voters = PhoneDriver.tokenizer(new File(fileName));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
        String date = df.format(new Date());
        System.out.println(date);
        File output = new File("output" + date + ".txt");
        output.createNewFile();
        PrintWriter out = new PrintWriter(output);
        
        String HTML, num;
        
        for (Person p : voters) {
            HTML = HTMLGet(p);
            num = recursivePhoneFinder(HTML);
            p.num = num;
            out.println(String.valueOf(p.getFirst()) + " " + p.getLast() + " " + p.getNum());
            out.flush();
        }
        out.close();
    }

    public static List<Person> tokenizer(File f) throws FileNotFoundException {
        Scanner names = new Scanner(f);
        LinkedList<Person> list = new LinkedList<Person>();
        while (names.hasNextLine()) {
            String curr = names.nextLine();
            System.out.println(curr);
            String[] firstLast = curr.split("\t");
            list.add(new Person(firstLast[0], firstLast[1]));
        }
        names.close();
        return list;
    }
    
    public static String HTMLGet(Person p) throws InterruptedException{
    	String html = null;
        try {
            html = Jsoup.connect((String)("http://www.yellowpages.com/whitepages?first=" + p.first + "&last=" + p.last + "&zip=Auburn&state=MA")).timeout(10000).get().html();
        }
        catch (Exception e) {
			Thread.sleep(10000);
            return html;
        }
        return html;
    }
    
    public static String recursivePhoneFinder(String text){
    	String area[] = {"508-832", "508-407", "508-729", "774-221"};
    	String num = "No phone number";
    	if(text == null)
    		throw new IllegalArgumentException("no HTML found for number");
    	
    	for(int i = 0 ; i < area.length ; i++){
        	if (text.contains(area[i])) {
                 int start = text.indexOf(area[i].substring(0, 3));
                 num = text.substring(start, start + 12);
            }
        }
    	
    	if(num == "No phone number"){
    		return num;
    	}
    	
    	if(num.matches(".*[a-zA-Z]+.*") && text.length() > 0){
    		num = num.substring(1, num.length());
    		num = recursivePhoneFinder(num);
    	}
    	
    	return num;
    }
    
    
}