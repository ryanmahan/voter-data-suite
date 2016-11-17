package suite;

import java.util.Scanner;
import suite.*;

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
		
		switch(command){
		
			case 1 : //Phonebank make
				PhoneDriver.phoneBankMaker(textFile);
				System.out.println("File Created!");
				
			case 2 : //house make
				//tokenize
				//find matching snum and sname
				//create house objects with that
				
				
		}
		
		System.out.println("Would you like to run another command? Y/N");
		String reply = input.next();
		System.out.println(reply + "end");
		if(reply.equals("Y") || reply.equals("y")){
			commandlineGUI();
		} else { 
			input.close();
			return;
		}
	}
	
	
}
