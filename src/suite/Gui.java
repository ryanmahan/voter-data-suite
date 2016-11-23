package suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Scanner;
import suite.*;

public class Gui extends JFrame implements ActionListener {

	
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JTextField textField;
	private JTextArea fileOutput;
	
	public Gui(){
		prepareGUI();
	}
	
	//Needs so much reorganization and commenting, but well do that later, right?
  private void prepareGUI()  {
	  
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	  
	//initialize the window/frame
    mainFrame = new JFrame("Voter Data Suite"); 
    mainFrame.setSize((int) (width/1.5), (int) (height/1.5));
    BorderLayout mainFrameLayout = new BorderLayout();
    mainFrameLayout.setHgap(5);
    mainFrameLayout.setVgap(5);
    mainFrame.getContentPane().setBackground(Color.WHITE);
    mainFrame.setLayout(mainFrameLayout);
    mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    //create menu bar
    menuBar = new JMenuBar();
    mainFrame.setJMenuBar(menuBar);
    
    
    //create menu in menubar
    JMenu actions = new JMenu("Actions");
    menuBar.add(actions);
    actions.setMnemonic(KeyEvent.VK_A);
    
    //add items to menu bar
    JMenuItem phoneBank = new JMenuItem("Create Phone Bank File");
    phoneBank.addActionListener(this);
    phoneBank.setActionCommand("Phone Bank");
    actions.add(phoneBank);
    
    JMenuItem inputFile = new JMenuItem("Read File");
    inputFile.addActionListener(this);
    inputFile.setActionCommand("input");
    actions.add(inputFile);
    
    //initialize text field
    textField = new JTextField(40);
    textField.setHorizontalAlignment(JTextField.LEFT);
    textField.setFont(new Font("Serif", Font.PLAIN, 20));
    JPanel inputPanel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("File: ");
    mainFrame.add(inputPanel, BorderLayout.NORTH);
    label.setDisplayedMnemonic(KeyEvent.VK_N);
    label.setLabelFor(textField);
    label.setFont(new Font("Serif", Font.PLAIN, 20));
    inputPanel.add(label, BorderLayout.WEST);
    inputPanel.add(textField, BorderLayout.CENTER);
    
    //initialize text area
    fileOutput = new JTextArea(" Enter a Text File above", 20, 5);
    fileOutput.setFont(new Font("Serif", Font.PLAIN, 20));
    fileOutput.setEditable(false);
    
    DefaultCaret caret = (DefaultCaret) fileOutput.getCaret(); //keeps scrollbar at top when file is read
    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    JPanel outputPanel = new JPanel(new BorderLayout());
    outputPanel.add(fileOutput, BorderLayout.CENTER);
    
    
    //make scrollable
    JScrollPane scroll = new JScrollPane(fileOutput);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.getVerticalScrollBar().setValue(0);
    outputPanel.add(scroll);
    mainFrame.add(outputPanel);
    
    GridLayout buttonPanelLayout = new GridLayout(10,1);
    buttonPanelLayout.setVgap(15);
    JPanel buttonPanel = new JPanel(buttonPanelLayout);
    buttonPanel.setBackground(Color.WHITE);
    
	JButton readFile = new JButton("Read File");
	readFile.addActionListener(this);
	readFile.setActionCommand("input");
	buttonPanel.add(readFile);
	readFile.setBackground(new Color(17,14,111));
	readFile.setForeground(Color.WHITE);
	  
	JButton phoneBankBut = new JButton("Phone Bank");
	phoneBankBut.addActionListener(this);
	phoneBankBut.setActionCommand("Phone Bank");
	buttonPanel.add(phoneBankBut);
	phoneBankBut.setBackground(new Color(17, 14 ,111));
	phoneBankBut.setForeground(Color.WHITE);
	phoneBankBut.setOpaque(true);
	
	JButton houseMake = new JButton("Make Houses");
	houseMake.addActionListener(this);
	houseMake.setActionCommand("Houses");
	houseMake.setBackground(new Color(17, 14 ,111));
	houseMake.setForeground(Color.WHITE);
	houseMake.setOpaque(true);
	buttonPanel.add(houseMake);
	
	
	
	mainFrame.add(buttonPanel, BorderLayout.WEST);
   
    mainFrame.setVisible(true);
 
  }
  
  JFrame popUp = null;
  
  public void actionPerformed(ActionEvent e) {
	  
	  String cmd = e.getActionCommand();
	  Scanner s = null;
	  File read = null;
	  
	  
	  if(cmd != null){
		  try{
			 read = new File(textField.getText());  
		  }
		  catch (Exception except){
			  fileOutput.setText("Invalid File, please try again");
			  return;
		  }
		  if(textField.getText() == null){
    		  fileOutput.setText("Please insert a file in the field above");
    		  return;
    	  }
	  }
	  

	  
      switch(cmd){
      
      case "input" :
    	  fileOutput.setText(fileToString(read));
    	  break;
      
      case "Phone Bank" :
    	  
    	popUp = new JFrame("Phone Bank from File or Net");
    	popUp.setLayout(new BorderLayout());
    	popUp.setSize(250,100);
    	JLabel instructions = new JLabel("Do you want to grab numbers from file or Net?");
    	popUp.add(instructions, BorderLayout.NORTH);
    	JButton file = new JButton("File");
    	file.addActionListener(this);
    	file.setActionCommand("POPfile");
    	popUp.add(file, BorderLayout.EAST);
    	JButton net = new JButton("Net");
    	net.addActionListener(this);
    	net.setActionCommand("POPnet");
    	popUp.add(net, BorderLayout.WEST);
    	popUp.setVisible(true);
    	break;
      
      
      case "Houses" : 
    	  LinkedList<House> list = DataDriver.houseMaker(read);
			//DataDriver.houseNumbers(list);
			
			
			//TEST PRINT
			File worker = new File("HousesList.txt");
			PrintWriter out = null;
		try {
			worker.createNewFile();
			out = new PrintWriter(worker);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			 
			for (House info : list){
				
				out.println("NEW HOUSE");
				out.println("\t" + info.head.first + info.head.last);
				out.println("\t" + info.head.getAddress());
				out.flush();
			}
			out.close();
			
			fileOutput.setText(fileToString(worker));
			
			break;
			
      case "POPfile" :
    	  
    	  popUp.setVisible(false);
    	  popUp.dispose();
    	  File output = null;
    		try {
    			output = DataDriver.phoneFromFile(read, this);
    		} catch (Exception except) {
    			except.printStackTrace();
    		}
        	  fileOutput.setText(fileToString(output));
        	  break;
    	  
      case "POPnet" : 
    	 
    	popUp.setVisible(false);
    	popUp.dispose();
    	File output1 = null;
  		try {
  			output1 = DataDriver.phoneBankMaker(read, this);
  		} catch (Exception except) {
  			except.printStackTrace();
  		}
      	  fileOutput.setText(fileToString(output1));
      	  break;
    	  
      }
  }
  
  @SuppressWarnings("resource")
  private static String fileToString(File f) {
	  String contents; 
	  
	Scanner s = null;

	try {
		s = new Scanner(f).useDelimiter("\\Z");
	} catch (FileNotFoundException e) {
		return "Please enter a valid file";
	}
	  contents = s.next();
	  s.close();
	  return contents;
  }
  
  public void setTextArea(String text){
	  fileOutput.setText(text);
  }
}
