package suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Scanner;

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
    mainFrame = new JFrame("Java Swing Examples");
    mainFrame.setSize(640, 960);
    mainFrame.setLayout(new BorderLayout());
    menuBar = new JMenuBar();
    JMenu actions = new JMenu("Actions");
    actions.setMnemonic(KeyEvent.VK_A);
    JMenuItem phoneBank = new JMenuItem("Copy Text");
    JMenuItem inputFile = new JMenuItem("Read File");
    inputFile.addActionListener(this);
    inputFile.setActionCommand("input");
    phoneBank.addActionListener(this);
    phoneBank.setActionCommand("Phone Bank");
    actions.add(phoneBank);
    menuBar.add(actions);
    actions.add(inputFile);
    mainFrame.setJMenuBar(menuBar);
    

    textField = new JTextField(40);
    textField.setHorizontalAlignment(JTextField.LEFT);
    JPanel inputPanel = new JPanel(new BorderLayout());
    JPanel outputPanel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("File: ");
    label.setDisplayedMnemonic(KeyEvent.VK_N);
    label.setLabelFor(textField);
    label.setFont(new Font("Serif", Font.PLAIN, 20));
    inputPanel.add(label, BorderLayout.WEST);
    inputPanel.add(textField, BorderLayout.CENTER);
    fileOutput = new JTextArea("Enter a Text File above", 20, 5);
    outputPanel.add(fileOutput, BorderLayout.CENTER);
    textField.setFont(new Font("Serif", Font.PLAIN, 20));
    fileOutput.setFont(new Font("Serif", Font.PLAIN, 20));
    JScrollPane scroll = new JScrollPane(fileOutput);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    outputPanel.add(scroll);
    mainFrame.add(inputPanel, BorderLayout.NORTH);
    mainFrame.add(outputPanel);
    mainFrame.setVisible(true);
    

    
 
  }
  

  public void actionPerformed(ActionEvent e) {
	  System.out.println("RUN");
	  String cmd = e.getActionCommand();
      switch(cmd){
      
      case "input" :
    	  if(textField.getText() == null){
    		  fileOutput.setText("Please insert a file in the field above");
    		  break;
    	  }
    	  File read;
    	  String contents;
    	  try{
    		 read = new File(textField.getText());
    		 Scanner s = new Scanner(read).useDelimiter("\\Z");
    		 contents = s.next();
    		 s.close();
 
    	  } catch(Exception except){
    		  fileOutput.setText("Invalid file, try again");
    		  break;
    	  }
    	  fileOutput.setText(contents);
    	  break;
    	  
      
      case "Phone Bank" :
    	  String text = textField.getText();
    	  fileOutput.setText(text);
    	  break;
      
      }
      
  }
  
}
