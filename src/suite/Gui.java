package suite;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import actions.*;



public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final Color darkBlue = new Color(17,14,111);
	private final Font menuFont = new Font("Arial", Font.PLAIN, 12);
	private JPanel tablePanel = new JPanel(new GridBagLayout());
	private JFrame mainFrame;
	private JTable table;
	private ImageIcon img = new ImageIcon("data/icon.png");
	
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
		
		mainFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     onExit();
			   }
			  });
		
		JPanel tableTest = this.initTablePanel();
		JMenuBar menus = this.menuBar();
    	JPanel buttons = this.createButtons();
    	
    	
    	mainFrame.setIconImage(img.getImage());
    	
    	mainFrame.setJMenuBar(menus);
    	mainFrame.add(tableTest);
    	mainFrame.add(buttons, BorderLayout.WEST);
    	
    	mainFrame.setVisible(true);
    	
 
  }
	
	private JPanel initTablePanel(){
		
		JLabel init = new JLabel("<html>Welcome to Voter Data Suite 2000! <br> Please open a file using File>Open <br> After doing so, use any of the buttons"
				+ " on the sidebar to modify the list!<html>", SwingConstants.CENTER);
		
		init.setFont(new Font("Verdana", Font.PLAIN, 15));
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
	  
	  JMenu sort = createSortMenu();
	  menuBar.add(sort);
		
	  return menuBar;
	  
	}
  
	private JMenu fileMenuItem(){
	  
	  JMenu file = new JMenu("File");
	  file.setMnemonic(KeyEvent.VK_A);

	  JMenuItem open = new JMenuItem();
	  open.setAction(new FileMenuActions.Open(this));
	  open.setText("Open");
	  open.addActionListener(this);
	  
	  JMenuItem saveAs = new JMenuItem();
	  saveAs.setAction(new FileMenuActions.SaveAs(this));
	  saveAs.setText("Save As");
	  saveAs.addActionListener(this);
	  
	  JMenuItem export = new JMenuItem();
	  export.setAction(new FileMenuActions.Export());
	  export.setText("Export to Excel");
	  export.addActionListener(this);
	  
	  JMenuItem imp = new JMenuItem();
	  imp.setAction(new FileMenuActions.Import(this, false));
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
	
	private JMenu createSortMenu(){
		
		JMenu sorts = new JMenu("Sort by");
		sorts.setFont(menuFont);
		
		JMenuItem precinct = new JMenuItem();
		precinct.setAction(new SortMenuActions.precinct(this));
		precinct.setText("Precinct");
		precinct.addActionListener(this);
		
		JMenuItem name = new JMenuItem();
		name.setAction(new SortMenuActions.name(this));
		name.setText("Name");
		name.addActionListener(this);

		JMenuItem street = new JMenuItem();
		street.setAction(new SortMenuActions.street(this));
		street.setText("Street");
		street.addActionListener(this);

		JMenuItem rank = new JMenuItem();
		rank.setAction(new SortMenuActions.rank(this));
		rank.setText("Rank");
		rank.addActionListener(this);
		
		JMenuItem timesVoted = new JMenuItem();
		timesVoted.setAction(new SortMenuActions.timesVoted(this));
		timesVoted.setText("Times Voted");
		timesVoted.addActionListener(this);
		
		sorts.add(precinct);
		sorts.add(name);
		sorts.add(street);
		sorts.add(rank);
		sorts.add(timesVoted);
		sorts.setFont(menuFont);
		
		return sorts;
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
		showNotHome.setAction(new ButtonActions.NotHome(this));
		showNotHome.setText("Not Home");

		JButton sortByDist = new JButton();
		sortByDist.addActionListener(this);
		sortByDist.setActionCommand("sortByDist");
		sortByDist.setAction(new ButtonActions.Distance(this));
		sortByDist.setText("Sort");
		
		JButton combineList = new JButton();
		combineList.addActionListener(this);
		combineList.setAction(new ButtonActions.Combine(this));
		combineList.setText("Combine Lists");

		buttonPanel.add(phoneBankBut);
		buttonPanel.add(houseMake);
		buttonPanel.add(showNotHome);
		buttonPanel.add(sortByDist);
		buttonPanel.add(combineList);

		Component[] buttons = buttonPanel.getComponents();

		for(Component c : buttons){
			c.setBackground(darkBlue);
			c.setForeground(Color.WHITE);
			((JButton) c).setOpaque(true);
		}

		return buttonPanel;
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 0; 
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width +1 , width);
			}
			if(width > 300)
				width = 300;
			if(column == 0){
				width = 0;
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}


	private void setTable(String[][] display, String[] columnNames){
		
		tablePanel.setLayout(new GridLayout(1,1));

		table = new JTable(display, columnNames);
		table.setEnabled(false);

		if(tablePanel != null)
			tablePanel.removeAll();
		tablePanel.add(table);
		tablePanel.revalidate();
		tablePanel.repaint();
		resizeColumnWidth(table);

		JScrollPane js = new JScrollPane(table);
		js.setVisible(true);
		tablePanel.add(js);

	}

	private void onExit(){
		
		int confirm = JOptionPane.showOptionDialog(
	             null, "Do you want to save before closing?", 
	             "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, 
	             JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(confirm == 0){
			new FileMenuActions.SaveAs(this).actionPerformed(null);
		} else if(confirm == 1){
			System.exit(0);
		}
		
	}
	
	public void setTableData(String[][][] from){

		String[][] to = new String[from.length][from[0].length+1];
		
		for(int i = 0 ; i < from.length ; i++){
			for(int j = 0 ; j < from[0].length ; j++){
				if(j == 0)
					to[i][0] = Integer.toString(i+1);
				to[i][j+1] = from[i][j][0];
			}
		}
		String[] columns = new String[from[0].length+1];
		columns[0] = "Row";
		for(int i = 0 ; i < from[0].length ; i++){
			columns[i+1] = from[0][i][1];
		}

		this.setTable(to, columns);
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

	public Object[] createProgressBar(int progress, String taskName){
		
		JFrame progBarFrame = new JFrame();
		progBarFrame.setLayout(new BorderLayout());
		progBarFrame.setSize(250, 100);
		
		progBarFrame.setIconImage(img.getImage());
		JLabel label = new JLabel("Progress bar for: " + taskName);
		label.setFont(menuFont);
		label.setVisible(true);
		label.setOpaque(true);
		
		JProgressBar prog = new JProgressBar();
		prog.setForeground(darkBlue);
		prog.setStringPainted(true);
		prog.setOpaque(true);
		prog.setMaximum(100);
		prog.setMinimum(0);
		prog.setValue(progress);
		prog.setVisible(true);
		
		progBarFrame.add(label, BorderLayout.NORTH);
		progBarFrame.add(prog, BorderLayout.SOUTH);
		progBarFrame.setVisible(true);
		
		return new Object[]{label, prog};
	}
	static boolean done = false;
	double start = 0;
	double time = 0;
	double task100 = 0;
	double taskTime = 0;
	
	public void setProgress(Object[] elements, double currTaskNumber, double total){
		
		JLabel timeRemain = (JLabel) elements[0];
		
		
		if(currTaskNumber%25 == 0){
			if(currTaskNumber == 0)
				start = System.currentTimeMillis();
			else{
				time = System.currentTimeMillis();
				taskTime = (time-start)/currTaskNumber;
			}
		}
			
		
		
		int remainingTime = (int) ((taskTime*(total-currTaskNumber)/1000));
		
		if(remainingTime == 0)
			timeRemain.setText("Calculating Remaining time");
		else
			timeRemain.setText("Time Remaining: " + remainingTime/60 + "m and " + remainingTime%60 + "s");
		
		int progress = (int) (currTaskNumber/total*100);
		JProgressBar bar = (JProgressBar) elements[1];
		bar.setValue(progress);
		
		
		bar.update(bar.getGraphics());
		timeRemain.update(timeRemain.getGraphics());
	}
	
	private ArrayList<JComboBox<String>> columns = new ArrayList<JComboBox<String>>(8);
	private JFrame menu = new JFrame("Import Menu");
	
	public void createImportMenu(){
		
		
		menu.setSize(300,500);
		GridLayout menuLayout = new GridLayout(1,2);
		menuLayout.setColumns(2);
		menuLayout.setRows(9);
		menuLayout.setHgap(10);
		menuLayout.setVgap(25);
		menu.setLayout(new FlowLayout());
		
		String[] personData = new String[]{"Empty", "Party", "First Name", "Last Name", "Street Number", "Street Name",
					"Rank", "Precinct", "Phone Number", "Times Voted", "Notes"};
		
		
		JPanel cbPanel = new JPanel(menuLayout);
		
		for(int i = 0 ; i < 9 ; i++){
			JComboBox<String> cb = new JComboBox<String>(personData);
			cb.setSelectedIndex(0);
			cb.setFont(menuFont);
			JLabel label = new JLabel("Data Type in column " + (i+1));
			label.setFont(menuFont);
			cbPanel.add(label);
			cbPanel.add(cb);
			columns.add(cb);
		}
		
		menu.add(cbPanel);
		
		JButton done = new JButton("Done");
		done.setAction(new FileMenuActions.Import(this, true));
		done.setBackground(darkBlue);
		done.setText("Done");
		done.setForeground(Color.WHITE);
		done.setOpaque(true);

		menu.add(done);
		menu.setVisible(true);
		
	}
	
	public String[] getImportArgs(){
		String[] input = new String[columns.size()];
		
		for(int i = 0 ; i < columns.size() ; i++){
			input[i] = columns.get(i).getSelectedItem().toString();
		}
		menu.setVisible(false);
		menu.dispose();
		
		return input;
		
	}
	
	public void actionPerformed(ActionEvent arg0){
	}
  	
}

