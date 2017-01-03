package suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import actions.*;

import java.io.File;
import java.util.LinkedList;


public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final Color darkBlue = new Color(17,14,111);
	private final Font menuFont = new Font("Arial", Font.PLAIN, 12);
	private JFrame mainFrame;
	private JProgressBar phonebankBar;
	private JFrame popUp = null;
	private JPanel tablePanel = new JPanel(new GridLayout(1,1));
	
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

		JPanel tableTest = this.initTablePanel();
		JMenuBar menus = this.menuBar();
    	JPanel buttons = this.createButtons();
    	//Panel textAreaPanel = this.textArea();
    	
    	mainFrame.setJMenuBar(menus);
    	mainFrame.add(tableTest);
    	mainFrame.add(buttons, BorderLayout.WEST);
    	
    	mainFrame.setVisible(true);
    	
 
  }
	
	private JPanel initTablePanel(){
		
		JLabel init = new JLabel("Please open a file using File>Open");
		tablePanel.add(init);
		
		return tablePanel;
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

	  JMenuItem open = new JMenuItem();
	  open.setAction(new MenuActions.Open(this));
	  open.setText("Open");
	  open.addActionListener(this);
	  
	  JMenuItem saveAs = new JMenuItem();
	  saveAs.setAction(new MenuActions.SaveAs(this));
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
	  
	  houseMake.setAction(new ButtonActions.HouseMake(this));
	  houseMake.setActionCommand("Houses");
	  houseMake.setText("Show Houses");

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

	 

	  switch(cmd){

	  case "Houses" : 

		  

		  break;

	  case "notHome" :

		  LinkedList<House> homeList = DataDriver.houseMaker(read);
		  System.out.println(homeList.size());
		  for (House h : homeList){
			  h.setNotHome();
		  }
		  String all;
		  all = "";

		  for (House h : homeList) {
			  if(h.notHome){
				  String text = h.getHead().getAllAvail();
				  text += "\n";
				  all = all.concat(text);
			  }
		  }
		  if(all.length() == 0){
			  all = "No Not Homes found";
		  }
		  //this.setTextArea(all);

		  break;

	  case "sortByDist" :

		  LinkedList<House> sortList = DataDriver.houseMaker(read);
		  System.out.println("List Made: " + sortList.size());

		  if(sortList.peek().getHead().precinct == -1){
			  //this.setTextArea("No Precincts found, please enter file with Precincts!");
			  return;
		  }
		  if(sortList.size() == 0){
			  //this.setTextArea("No Homes Found");
			  return;
		  }

		  sortList = DataDriver.sortByDist(sortList);
		  System.out.println("Recieved List of Houses: " + sortList.size());

		  FileHandler fhSort = new FileHandler(read);
		  fhSort.xmlHouseWrite(sortList);

		  all = "";

		  for(House h : sortList){
			  all.concat(h.getHead().getAllAvail() + "\n");
		  }

		  //this.setTextArea(all);

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
  private JTable table;
  public void setTable(String[][] display, String[] columnNames){
	  table = new JTable(display, columnNames);
	  table.setEnabled(false);
	  
	  if(tablePanel != null)
		  tablePanel.removeAll();
	  tablePanel.add(table);
	  tablePanel.revalidate();
	  
	  JScrollPane js=new JScrollPane(table);
	  js.setVisible(true);
	  tablePanel.add(js);
	  
  }
  
  public Object[][] getTableData(){
	  Object[][] output = new Object[table.getRowCount()][table.getColumnCount()];
	  
	  for(int i = 0 ; i < table.getRowCount() ; i++){
		  for(int j = 0 ; j < table.getColumnCount() ; j++){
			  output[i][j] = table.getValueAt(i, j);
		  }  
	  }
	  return output;
  }
  
  public void progressBar(int i){

	  phonebankBar.setMaximum(100);
	  phonebankBar.setMinimum(0);
	  phonebankBar.setValue(i);

	  phonebankBar.setString(Integer.toString(i) + "%");
	  phonebankBar.update(phonebankBar.getGraphics());
	  

  }
}

