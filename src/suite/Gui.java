package suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final Color darkBlue = new Color(17,14,111);
	
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JTextField textField;
	private JTextArea fileOutput;
	private JProgressBar phonebankBar;
	
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
    outputPanel.setVisible(true);
    
    
    //make scrollable
    JScrollPane scroll = new JScrollPane(fileOutput);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.getVerticalScrollBar().setValue(0);
    outputPanel.add(scroll);
    mainFrame.add(outputPanel);
    scroll.setVisible(true);
    
    GridLayout buttonPanelLayout = new GridLayout(10,1);
    buttonPanelLayout.setVgap(15);
    JPanel buttonPanel = new JPanel(buttonPanelLayout);
    buttonPanel.setBackground(Color.WHITE);
    
	JButton readFile = new JButton("Read File");
	readFile.addActionListener(this);
	readFile.setActionCommand("input");
	buttonPanel.add(readFile);
	readFile.setBackground(darkBlue);
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
	
	JButton showNotHome = new JButton("Not Homes");
	showNotHome.addActionListener(this);
	showNotHome.setActionCommand("notHome");
	showNotHome.setBackground(new Color(17, 14 ,111));
	showNotHome.setForeground(Color.WHITE);
	showNotHome.setOpaque(true);
	buttonPanel.add(showNotHome);
	
	JButton sortByDist = new JButton("Sort By Dist");
	sortByDist.addActionListener(this);
	sortByDist.setActionCommand("sortByDist");
	sortByDist.setBackground(new Color(17, 14 ,111));
	sortByDist.setForeground(Color.WHITE);
	sortByDist.setOpaque(true);
	buttonPanel.add(sortByDist);
	
	
	
	mainFrame.add(buttonPanel, BorderLayout.WEST);
   
    mainFrame.setVisible(true);
 
  }
  
  JFrame popUp = null;
  
  public void actionPerformed(ActionEvent e) {
	  
	  String cmd = e.getActionCommand();
	  File read = null;
	 
	  
	  System.out.println("Action Command: " + e.getActionCommand());
	  
	  
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
	  
	  FileHandler fileIO = new FileHandler(read);
	  
      switch(cmd){
      
      case "input" :
    	  if (read.getName().endsWith(".txt")){
    		  fileOutput.setText(fileToString(read));
    		  break; 
    	  }
    	  if(read.getName().endsWith(".xml")){
    		  fileIO.displayXML(this);
    		  break;
    	  }
    	 
      
      case "Phone Bank" : 
    	  
    	//create popUp questioning File or Net
    	popUp = new JFrame("Phone Bank from File or Net");
    	popUp.setFont(new Font("Serif", Font.PLAIN, 20));
    	popUp.setLayout(new BorderLayout());
    	popUp.setSize(350,150);
    	
    	//tell them what to do
    	JLabel instructions = new JLabel("Do you want to grab numbers from file or Net?");
    	popUp.add(instructions, BorderLayout.NORTH);
    	instructions.setFont(new Font("Serif", Font.PLAIN, 20));
    	
    	//create button for File find
    	JButton file = new JButton("File");
    	file.setFont(new Font("Serif", Font.PLAIN, 20));
    	file.addActionListener(this);
    	file.setActionCommand("POPfile");
    	file.setBackground(darkBlue);
    	file.setForeground(Color.WHITE);
    	file.setOpaque(true);
    	popUp.add(file, BorderLayout.EAST);
    	
    	//create button for Net Finder
    	JButton net = new JButton("Net");
    	net.setFont(new Font("Serif", Font.PLAIN, 20));
    	net.setBackground(darkBlue);
    	net.setForeground(Color.WHITE);
    	net.addActionListener(this);
    	net.setActionCommand("POPnet");
    	popUp.add(net, BorderLayout.WEST);
    	
    	//create progress bar for % done finding
    	phonebankBar = new JProgressBar();
    	phonebankBar.setForeground(darkBlue);
    	phonebankBar.setStringPainted(true);
    	phonebankBar.setOpaque(true);
    	popUp.add(phonebankBar, BorderLayout.SOUTH);
    	popUp.setVisible(true);
    	
    	break;
      
      
      case "Houses" : 
    	  LinkedList<House> list = DataDriver.houseMaker(read);
    	  System.out.println("Made a list of Houses: " + list.size());
		
    	  String all = "";
    	  
    	  for (House h : list) {
              String text = h.head.getAllAvail();
              text += "\n";
              all = all.concat(text);
    	  }
			 
    	  this.setTextArea(all);
			
			break;
			
      case "notHome" :
    	  
    	  LinkedList<House> homeList = DataDriver.houseMaker(read);
    	  System.out.println(homeList.size());
    	  for (House h : homeList){
    		  h.setNotHome();
    	  }
    	  
    	  all = "";
    	  
    	  for (House h : homeList) {
    		  if(h.notHome){
    			String text = h.head.getAllAvail();
              	text += "\n";
              	all = all.concat(text);
    	  		}
          }
    	  if(all.length() == 0){
    		  all = "No Not Homes found";
    	  }
    	  this.setTextArea(all);
    	  
    	  break;
    	  
      case "sortByDist" :
    	  
    	  	System.out.println("Button Pressed!");
    	  	LinkedList<House> sortList = DataDriver.houseMaker(read);
    	  	System.out.println("List Made: " + sortList.size());
    	  	
    	  	if(sortList.peek().head.precinct == -1){
    	  		this.setTextArea("No Precincts found, please enter file with Precincts!");
    	  		return;
    	  	}
    	  	if(sortList.size() == 0){
    	  		this.setTextArea("No Homes Found");
    	  		return;
    	  	}
    	  	
    	  	sortList = DataDriver.sortByDist(sortList);
    	  	FileHandler fhSort = new FileHandler(read);
        	fhSort.xmlHouseWrite(sortList);
  
    	  	break;
			
      case "POPfile" :
    
    		try {
    			DataDriver.phoneFromFile(read, this);
    		} catch (Exception except) {
    			except.printStackTrace();
    		}
        	
        	popUp.setVisible(false);
          	popUp.dispose();
        	  
        	break;
        	  
    	  
      case "POPnet" : //if phonebank -> net finder
    	 
  		try {
  			DataDriver.phoneBankMaker(read, this);
  		} catch (Exception except) {
  			except.printStackTrace();
  		}
      	
      	
      	popUp.setVisible(false);
    	popUp.dispose();
    	
    	break;
      }
  }
  

  @SuppressWarnings("resource") //I totally close this right? Stop being a dummy eclipse
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
  
  public void progressBar(int i){
	  
	  phonebankBar.setMaximum(100);
	  phonebankBar.setMinimum(0);
	  phonebankBar.setValue(i);
	  
	  phonebankBar.setString(Integer.toString(i) + "%");
	  phonebankBar.update(phonebankBar.getGraphics());

	  
  }
  
  public void setTextArea(String text){
	  fileOutput.setText(text);
	  fileOutput.update(fileOutput.getGraphics());
  }
}
