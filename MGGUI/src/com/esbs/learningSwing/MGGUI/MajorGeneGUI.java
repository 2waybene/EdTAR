package com.esbs.learningSwing.MGGUI;

//=========================================================================
//MajorGeneGUI.java
//
//Author: Jianying LI
//
//initial coded: June. 1st, 2002
//
//modified history: June 5th, 2002  : initial linking with other program
//                June  10th, 2002: for overall layout
//                June 11th, 2002 : add FileChooser
//                June 12th, 2002 : try gridbaglayout
//                June 13th, 2002 : iteration is editabel
//                Sept 30th, 2002 : new version
//                Oct. 2nd,  2002 : Take care of the threads
//
//Comment: This GUI constructs an interface with function buttons listed
//       on the top row, user inputs listed on the second and third rows
//       A textArea on the bottom row, that will display the input from
//       user for checking before user click start button.
//==========================================================================
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.*;
import java.util.*;

public class MajorGeneGUI extends JFrame implements ActionListener
{

  
  /*
  *  Labels in the middle part of the frame
  */
  private JLabel indiLabel; //for individual
  private JTextField indiText;//take input from customer

  private JLabel iterLabel; //for interation
  private JComboBox iteration;//provide default iteration number
          //will be implemented as editable, which could also take
          //input from customer
  private String [] iterNumber = {"100000", "250000", "500000", "1000000"};

  private JLabel lagNumLabel;//for lag number
  private JTextField lagNumber; //take input from customer,default is 5

  private JLabel NPLabel;//for number of parents
  private JTextField NumOfParent; //take input from customer, default is 6
  
  private JLabel chainID;
  private JTextField chainInput;


  /*
  *  buttons to display on the top panel
  */
  private JButton startbtn, quitbtn;

  /*
  *  text area where something will be displayed
  */
  private JTextArea interactArea;
  private JTextArea outputArea;


  /*
  *  default lap number
  */
  final String defaultLag = "5";


  /*
  *  Default number of parent
  */
  final String defaultNP = "6";

  /**
   * These are three suffix for three input files
   */
  final String dataFile = "_org";
  final String designMatrixFile = "_incidcr";
  final String initialValFile = "_initial";
  /**
   * Declear strings that take input from user
   */
  private String indiInput;
  private String iterNumInput = "100000";
  private String lagNumInput;
  private String NumOfPareInput;
  private String chainInputStr;
  
  private int repeat =1;
 
  /**
  * Construct a Major Gene GUI
  * @param MGGUI reference to a Major Gene object
  */
  public MajorGeneGUI (){
  	initComponents();
  	
  }
  
  private void initComponents(){ 
     
     addWindowListener(new WindowCloser());
     
      //laying out input information
      JPanel inputPane = new JPanel ();
      inputPane.setLayout(new GridLayout(2,5));
      //first lane
      indiLabel = new JLabel ("Individual");
      inputPane.add(indiLabel);
      indiText = new JTextField (10);
      indiText.addActionListener(this);
      inputPane.add(indiText);
      chainID = new JLabel ("Chain ID");
      inputPane.add(chainID);
      chainInput = new JTextField ("one",6);
      chainInput.getSelectedText();
      chainInput.addActionListener(this);
      inputPane.add(chainInput);
      iterLabel = new JLabel ("Iteration number");
      inputPane.add(iterLabel);
      iteration = new JComboBox (iterNumber);
      iteration.setSelectedIndex(0);
      iteration.setEditable(true); //this JComboBox needs to be editable
      iteration.addActionListener(new ActionListener(){
          public void actionPerformed (ActionEvent e){
              JComboBox cb = (JComboBox)e.getSource();
              iterNumInput = (String)cb.getSelectedItem();
          //  reformat(); //this needs to be figured out
          }
      });
      inputPane.add(iteration);
      
      
      //second lane
      lagNumLabel = new JLabel ("Lag number");
      inputPane.add(lagNumLabel);
      lagNumber = new JTextField (defaultLag, 4);
      lagNumber.getSelectedText();
      lagNumber.addActionListener(this);
      inputPane.add(lagNumber);
      NPLabel = new JLabel ("Number of Parents");
      inputPane.add(NPLabel);
      NumOfParent = new JTextField (defaultNP, 2);
      NumOfParent.getSelectedText();
      NumOfParent.addActionListener(this);
      inputPane.add (NumOfParent);
      startbtn = new JButton ("Start");
      startbtn.addActionListener(this);
      inputPane.add(startbtn);
      quitbtn = new JButton ("Quit");
      quitbtn.addActionListener(this);
      inputPane.add(quitbtn);
      
      inputPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      JSplitPane splitTextPane =  new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      interactArea = new JTextArea(
      	"This is the C++ program windows interface. " +
      	"Thre default parameters have been given, " +
      	"however, you are welcome to make any changed as needed. "+
      	"Please make sure that you input the data name as individual. "+
      	"Once the start button is clicked, the program will look for "+
      	"required files that have supposely been processed with SAS, "+
      	"and invoke C++ executable code. " + "Error message will be "+
      	"shown if any missing file is detected." + "The other window below "+
      	"will be used to show interactive message from running C++ program.\n");
      interactArea.setFont(new Font("Courier", Font.PLAIN, 14));
      interactArea.setLineWrap(true);
      interactArea.setWrapStyleWord(true);
      JScrollPane interactScrollPane = new JScrollPane(interactArea, 
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      interactScrollPane.setMinimumSize(new Dimension(400, 50));
      interactScrollPane.setPreferredSize(new Dimension(400, 100));
      interactScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
      splitTextPane.add(interactScrollPane);
      
      outputArea = new JTextArea();
      JScrollPane outputScrollPane = new JScrollPane(outputArea,
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      outputScrollPane.setMinimumSize(new Dimension(400,100));
      outputScrollPane.setPreferredSize(new Dimension(400, 200));
      outputScrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
      splitTextPane.add(outputScrollPane);
      
      
      Container contentPane = getContentPane();
      contentPane.add (inputPane, BorderLayout.NORTH);
      contentPane.add (splitTextPane, BorderLayout.CENTER);
      pack();
      setVisible (true);
  }
  
	public void actionPerformed (ActionEvent e){
		Object source = e.getSource();
		if (source==startbtn){
			indiInput      = indiText.getText();
	    	chainInputStr  = chainInput.getText();
		    lagNumInput    = lagNumber.getText();
  	    NumOfPareInput = NumOfParent.getText();
  	   	runMGprogram(1);
  	}
		else if (source == quitbtn){System.exit(0);}
	}

/**
*  Display and executes Major Gene GUI
*  @param args Command line arguments with are ignored
*
*/

	public String [] getCommandString(int i){
	
		String temp = "" + i;
		String []stringCommand = new String [8];
		
			stringCommand [0] = "mg4n";
			stringCommand [1] = indiInput + chainInputStr + dataFile;
			stringCommand [2] = indiInput + chainInputStr + designMatrixFile;
			stringCommand [3] = indiInput + chainInputStr + "I" + temp  + initialValFile;
		    stringCommand [4] = indiInput + chainInputStr + "I" + temp ;
		    stringCommand [5] = iterNumInput;
		    stringCommand [6] = lagNumInput;
		    stringCommand [7] = NumOfPareInput;
			return stringCommand;
	    
	}
	public void runMGprogram (int j){
	        repeat ++;
			String [] commandLine = getCommandString(j);
			for (int i=0; i<8; i++){
				interactArea.append(commandLine[i]+"  ");
			}
			interactArea.append("\n");
		    		
			try
			{
				startbtn.setEnabled(false);
			  	Runtime r = Runtime.getRuntime();
			   	Process p = r.exec(commandLine, new String[0], new File("C:\\MGGUI\\"));
			   	(new StreamReader(p.getInputStream())).start();
			   	(new StreamReader(p.getErrorStream())).start();
			   	(new ProcessMonitor(p)).start();
			}
			catch (Exception e)
			{
				outputArea.append("Error: " + e);
			}
			
	}
	
	class ProcessMonitor extends Thread
	{
	    
		Process _p;
		public ProcessMonitor(Process p)
		{
			_p = p;
		}
		
		public void run()
		{
			try
			{
				int rc = _p.waitFor();
				EventQueue.invokeLater(new OutputEvent("Process exit with rc=" + rc));
				
			}
			catch (Exception e)
			{
	       		EventQueue.invokeLater(new OutputEvent("Error: " + e.toString()));
			}
			finally
			{
				if (repeat ==2)
					runMGprogram(2);
				
				else {
				    startbtn.setEnabled(true);
				    repeat =1;
				}
			}
		
		}
	}

	class StreamReader extends Thread
	{
		private InputStream _input;
	    public StreamReader(InputStream input)
	    {
	        _input = input;
	    }
	    
	    public void run()
	    {
	       try
	       {
			   	BufferedReader br = new BufferedReader (new InputStreamReader(_input));
	    	   	String input;
	       		while ((input = br.readLine())!=null)
		        {
		        	EventQueue.invokeLater(new OutputEvent(input));
	       		}
	       	}
	       	catch (final Exception e)
	       	{
	       		EventQueue.invokeLater(new OutputEvent("Error: " + e.toString()));
	       	}
	    }
	}
	
	class OutputEvent implements Runnable
	{
		private String _str;
		public OutputEvent (String str)
		{
			_str = str;
		}
		
		public void run()
		{
			outputArea.append(_str + "\n");
		}
	}

public static void main (String [] args) {
 
  MajorGeneGUI MGGUI =new MajorGeneGUI ();
  MGGUI.setTitle("Major Gene Program");
}    

/**
* Exits application if window is closed by user
*/

private class WindowCloser extends WindowAdapter {
  /**
  * Exits application if window is closed by user
  * @param event WindowEvent
  */
  public void windowClosing (WindowEvent event) {
      System.exit(0); //Exit the application
  }
}
}


