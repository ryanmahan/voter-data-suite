package suite;

import java.util.LinkedList;
import java.util.Scanner;
import suite.DataDriver;
import java.io.File;
import java.io.PrintWriter;


public class suiteMain {
	
	//TODO: Create a method to compile data using Home ID's and Notes
	// I forgot what I meant when I wrote that ^
	//TODO: Algorithm to sort by proximity to eachother
	
	public static void main(String args[]) throws Exception{
		visualGUI();
	}
	
	static Gui UX;
	
	public static void visualGUI(){
		//@SuppressWarnings("unused")
		UX = new Gui();
	}	
	
}
