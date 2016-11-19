package suite;

import java.util.LinkedList;
import java.util.Scanner;
import suite.DataDriver;
import java.io.File;

public class suiteMain {
	
	public static void main(String args[]) throws Exception{
		commandlineGUI();
	}
	
	public static void commandlineGUI() throws Exception{
		System.out.println("************************************");
		System.out.println("Welcome to the voter data suite");
		System.out.println("Press a number then enter for the following commands");
		System.out.println("1) Create a PhoneBank file \t 2) Create Houses");
		System.out.println("************************************");
		
		Scanner input = new Scanner(System.in);
		int command = input.nextInt();
		System.out.println("Please input a tab-delimited text file of Voters");
		
		String textFile = input.next();
		File f = null;
		
		try{
			f = new File(textFile);
		} catch (Error e){
			System.out.println("File Error: " + e);
			return;
		}
		
		
		switch(command){
		
			case 1 : //Phonebank make
				DataDriver.phoneBankMaker(f);
				System.out.println("File Created!");
				break;
				
			case 2 : //house make
				DataDriver.houseMaker(f);
				break;
				
			case 3 :
				LinkedList<House> list = DataDriver.houseMaker(f);
				DataDriver.houseNumbers(list);
				
				
				//TEST PRINT
				
				for (House info : list){
					System.out.println("NEW HOUSE");
					System.out.println("\t" + info.head.first + info.head.last);
					System.out.println("\t" + info.landline);
				}
				break;
				
				
				
		}
		
		System.out.println("Would you like to run another command? Y/N");
		String reply = input.next();
		if(reply.equals("Y") || reply.equals("y")){
			commandlineGUI();
		} else { 
			input.close();
			return;
		}
	}
	
	
}
