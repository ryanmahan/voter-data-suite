package suite;

public class Person {
	String party;
    String first;
    String last;
    String snum;//Street Number
    String sname;//Street Name
    
    String num = null;

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

    
//LEGACY CODE NO LONGER IN FUNCTION USE HTMLGET AND RECURSIVEPHONEFINDER IN PHONEDRIVER CLASS 
//    public String findNum() throws IllegalArgumentException, IOException, InterruptedException {
//        if (this.num == null) {
//            String html;
//            String area[] = {"508-832", "508-407", "508-729", "774-221"};
//            try {
//                html = Jsoup.connect((String)("http://www.yellowpages.com/whitepages?first=" + this.first + "&last=" + this.last + "&zip=Auburn&state=MA")).timeout(10000).get().html();
//            }
//            catch (Exception e) {
//                Thread.sleep(10000);
//                return this.findNum();
//            }
//            //TODO: Extend to (508) 832, 407, 729, (774) 221
//            //TODO: Remove possibility of getting letters in number
//            for(int i = 0 ; i < area.length ; i++){
//            	if (html.contains(area[i])) {
//                     int start = html.indexOf(area[i].substring(0, 3));
//                     this.num = html.substring(start, start + 12);
//                }
//            }
//        }
//        System.out.println(String.valueOf(this.first) + " " + this.last + " " + this.num);
//        return this.num;
//    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String input) {
        this.num = input;
    }
}