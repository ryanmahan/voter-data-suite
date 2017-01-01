package suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import actions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final Color darkBlue = new Color(17,14,111);
	private final Font menuFont = new Font("Arial", Font.PLAIN, 12);
	private JFrame mainFrame;
	private JTextField textField;
	private JTextArea fileOutput;
	private JProgressBar phonebankBar;
	private JFrame popUp = null;
	
	public Gui(){
		prepareGUI();
	}
	
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

		JMenuBar menus = this.menuBar();
    	JPanel buttons = this.createButtons();
    	JPanel textAreaPanel = this.textArea();
    	JPanel textFieldPanel = this.textField();
    	
    	mainFrame.setJMenuBar(menus);
    	mainFrame.add(textFieldPanel, BorderLayout.NORTH);
    	mainFrame.add(textAreaPanel);
    	mainFrame.add(buttons, BorderLayout.WEST);
    	
    	mainFrame.setVisible(true);
    	
 
  }
  
  
  //creates the menuBar
  private JMenuBar menuBar(){
	  
	  JMenuBar menuBar = new JMenuBar();
	  
	  //create menu bar
	  menuBar = new JMenuBar();
	  mainFrame.setJMenuBar(menuBar);
  
		//create menu item
	  JMenu file = fileMenuItem();
	  menuBar.add(file);
		
	  return menuBar;
  }
  
  private JMenu fileMenuItem(){
	  
	  JMenu file = new JMenu("File");
	  file.setMnemonic(KeyEvent.VK_A);
	  
	  JMenuItem save = new JMenuItem();
	  save.setAction(new MenuActions.Save());
	  save.setText("Save");
	  save.addActionListener(this);
	  save.setVisible(true);

	  JMenuItem open = new JMenuItem();
	  open.setAction(new MenuActions.Open());
	  open.setText("Open");
	  open.addActionListener(this);
	  
	  JMenuItem saveAs = new JMenuItem();
	  saveAs.setAction(new MenuActions.SaveAs());
	  saveAs.setText("Save As");
	  saveAs.addActionListener(this);
	  
	  JMenuItem export = new JMenuItem();
	  export.setAction(new MenuActions.Export());
	  export.setText("Export to Excel");
	  export.addActionListener(this);
	  
	  JMenuItem imp = new JMenuItem();
	  imp.setAction(new MenuActions.Import());
	  imp.setText("Import");
	  imp.addActionListener(this);
	  
	  file.add(open);
	  file.add(save);
	  file.add(saveAs);
	  file.add(export);
	  file.add(imp);
	  file.setFont(menuFont);
	  
	  Component[] fileMenuItems = file.getMenuComponents();
	  
	  for(Component c : fileMenuItems){
		  c.setFont(menuFont);
	  }
	  
	  file.setVisible(true);

	  return file;
  }
  
  private JPanel textField(){
	  
	  textField = new JTextField(40);
	  textField.setHorizontalAlignment(JTextField.LEFT);
	  textField.setFont(new Font("Serif", Font.PLAIN, 20));
	  JPanel inputPanel = new JPanel(new BorderLayout());
	  JLabel label = new JLabel("File: ");

	  label.setDisplayedMnemonic(KeyEvent.VK_N);
	  label.setLabelFor(textField);
	  label.setFont(new Font("Serif", Font.PLAIN, 20));
	  inputPanel.add(label, BorderLayout.WEST);
	  inputPanel.add(textField, BorderLayout.CENTER);

	  return inputPanel;
	  
  }
  
  private JPanel textArea(){

	  fileOutput = new JTextArea(" Enter a Text File above", 20, 5);
	  fileOutput.setFont(new Font("Serif", Font.PLAIN, 20));
	  fileOutput.setEditable(false);

	  DefaultCaret caret = (DefaultCaret) fileOutput.getCaret(); //keeps scrollbar at top when file is read
	  caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
	  JPanel outputPanel = new JPanel(new BorderLayout());
	  outputPanel.add(fileOutput, BorderLayout.CENTER);
	  outputPanel.setVisible(true);

	  JScrollPane scroll = new JScrollPane(fileOutput);
	  scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	  scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	  scroll.getVerticalScrollBar().setValue(0);
	  outputPanel.add(scroll);
	  scroll.setVisible(true);

	  return outputPanel;
  }
  
  private JPanel createButtons(){

	  GridLayout buttonPanelLayout = new GridLayout(10,1);
	  buttonPanelLayout.setVgap(15);
	  JPanel buttonPanel = new JPanel(buttonPanelLayout);
	  buttonPanel.setBackground(Color.WHITE);
	 
	  JButton phoneBankBut = new JButton("Phone Bank");
	  phoneBankBut.addActionListener(this);
	  phoneBankBut.setText("Phone Bank");
	  phoneBankBut.setAction(new ButtonActions.PhoneBank(this));
	  phoneBankBut.setText("Phone Bank");

	  JButton houseMake = new JButton("Make Houses");
	  houseMake.addActionListener(this);
	  houseMake.setActionCommand("Houses");
	  houseMake.setAction(new ButtonActions.HouseMake());
	  houseMake.setText("Make Houses");

	  JButton showNotHome = new JButton();
	  showNotHome.addActionListener(this);
	  showNotHome.setActionCommand("notHome");
	  showNotHome.setAction(new ButtonActions.NotHome());
	  showNotHome.setText("Not Home");

	  JButton sortByDist = new JButton();
	  sortByDist.addActionListener(this);
	  sortByDist.setActionCommand("sortByDist");
	  sortByDist.setAction(new ButtonActions.Distance());
	  sortByDist.setText("Sort");

	  buttonPanel.add(phoneBankBut);
	  buttonPanel.add(houseMake);
	  buttonPanel.add(showNotHome);
	  buttonPanel.add(sortByDist);

	  Component[] buttons = buttonPanel.getComponents();

	  for(Component c : buttons){
		  c.setBackground(darkBlue);
		  c.setForeground(Color.WHITE);
		  ((JButton) c).setOpaque(true);
	  }

	  return buttonPanel;
  }

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
		  System.out.println("Recieved List of Houses: " + sortList.size());

		  FileHandler fhSort = new FileHandler(read);
		  fhSort.xmlHouseWrite(sortList);

		  all = "";

		  for(House h : sortList){
			  all.concat(h.head.getAllAvail() + "\n");
		  }

		  this.setTextArea(all);

		  break;

	  case "POPfile" : //if phonebank -> file finder

		  try {
			  DataDriver.phoneFromFile(read, this);
		  } catch (Exception except) {
			  except.printStackTrace();
		  }

		  popUp.setVisible(false);
		  popUp.dispose();

		  break;


	  case "POPnet" : //if phonebank -> net finder

		 

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

  public String getTextField(){
	  return textField.getText();
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
