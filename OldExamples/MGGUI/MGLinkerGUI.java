//=========================================================================
//MGLinkerGUI.java
//
//Author: Jianying LI
//
//initial coded: June 20th, 2002
//
//modified history: June 25th, 2002 : invoking SAS, Splus programs successful
//                  July 5th, 2002: Currently, the splus program and two
//                                  scripts have to be invoked by a perl
//                                  script.
//                  July 7th, 2002: The help button and output button need to
//                                  be implemented.
//                  October 2nd, 2002: add open file features, help features
//Comment: This GUI provides a linker for completing major gene program
//         running job. Following the flowchart, five main function buttons
//         are listed on the top part of the interface, with specific funtions
//         listed as following:
//         SAS_input:  a SAS program will be invoked with MG_Input.sas loaded
//         Major Gene Program: another interface will show for running mg4n
//                             program
//         SAS_change: a SAS program will be invoked with change.sas loaded
//         S-plus_BOA: a S-plus program will be invoked with programs loaded
//         SAS_result: a SAS program will be invoked with MG_result.sas loaded
//
//         Currently, another two buttons are also listed. "Help" for showing
//         instruction, and "Quit" for quitting this program.
//
//Limitation: Currently, most programs are invoked in a defaulty directory.
//            Further implementation that handles directory is needed.
//
//
//==========================================================================/*

import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.filechooser.*;

public class MGLinkerGUI extends JFrame implements ActionListener {

 
    final JFileChooser fc = new JFileChooser(new File ("C:\\MGGUI\\"));
    private JButton btnSasInput, btnMG, btnSasChange,
            btnSplus, btnSasResult, btnOutPut, btnHelp, btnQuit;
    private static final String fontName = "Courier";
    private static final int fontStyle = Font.PLAIN;
    private static final int fontSize = 14;
    private static final String newLine = "\n";
    private JTextArea textArea;

    public MGLinkerGUI() {
        super ("Major Gene Program Linker");

        //Set up the buttons.
        JPanel buttonPanel = new JPanel(new GridLayout(2,2));
        btnSasInput = new JButton("SAS_input");
        btnSasInput.addActionListener(this);
        buttonPanel.add(btnSasInput);

        btnMG = new JButton("Major Gene Program");
        buttonPanel.add(btnMG);
        btnMG.addActionListener(this);

        btnSasChange = new JButton("SAS_change");
        btnSasChange.addActionListener(this);
        buttonPanel.add(btnSasChange);

        btnSplus = new JButton("S-plus_BOA");
        btnSplus.addActionListener(this);
        buttonPanel.add(btnSplus);

        btnSasResult = new JButton("SAS_result");
        btnSasResult.addActionListener(this);
        buttonPanel.add(btnSasResult);

        btnOutPut = new JButton("Output Data");
        btnOutPut.addActionListener(this);
        buttonPanel.add(btnOutPut);

        btnHelp = new JButton("Help");
        btnHelp.addActionListener(this);
        buttonPanel.add(btnHelp);

        btnQuit = new JButton("Quit");
        btnQuit.addActionListener(this);
        buttonPanel.add(btnQuit);

        //Create a text area.
        textArea = new JTextArea(
                "This is a Major Gene Program " +
                "Linker program, which allows you to link major steps " +
                "to run the major gene programs. Please consult " +
                "the help for instruction to run this interface." + newLine);

        textArea.setFont(new Font(fontName, fontStyle, fontSize));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 250));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Plain Text"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));

      //this is one way to add them on to secondary intermediate container
     /* JPanel contentPane = new JPanel();
      BoxLayout box = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
      contentPane.setLayout(box);
      contentPane.add(buttonPanel);
      contentPane.add(areaScrollPane);
      setContentPane(contentPane);*/

      //this is another way
       Container c = getContentPane();
       c.add(buttonPanel, BorderLayout.NORTH);
       c.add(areaScrollPane, BorderLayout.CENTER);
    }
    public void ExecProgram (String [] commandLine){
      Runtime r = Runtime.getRuntime();
      Process p = null;
      try {
          p = r.exec(commandLine);
      } catch (Exception e) {
          System.out.println("Error!");
      }

 }
 public void ExecProgram (String commandLine){
      Runtime r = Runtime.getRuntime();
      Process p = null;
      try {
          p = r.exec(commandLine);
      } catch (Exception e) {
          System.out.println("Error!");
      }

 }
    public void actionPerformed (ActionEvent e){
       Object source = e.getSource();
       if (source == btnSasInput){
       textArea.append(newLine + "You are invoking SAS_input program." + newLine +
       "Make sure to run SAS_input first, then invoke macro with correct "+
       "directory information and data set." + newLine);
           String [] commandLine = {"sas", "-autoexec", "c:\\mggui\\startCode\\autoexec.sas"};

           ExecProgram (commandLine);
    }
    else if (source == btnMG){

      //  MajorGeneGUI MGGUI = new
         String [] commandLine = {"java", "MajorGeneGUI"};

         textArea.append( newLine + "You are invoking major gene program." + newLine +
       "Another interface will be shown. Please follow the direction and type"
       +" in required information and run the major gene program."+
       "Should you have question, please refer to the help." + newLine);
          ExecProgram (commandLine);
    }

    else if (source == btnSasChange) {
       String [] commandLine = {"sas", "-autoexec","c:\\mggui\\startCode\\autoexec2.sas"};
                          //This SAS program is for changing the
                         //the data format for S-plus_BOA
      textArea.append( newLine + "You are invoking SAS_change program." +
       "Make sure run SAS_change first, then invoke macro with correct "+
       "directory information and data set." + newLine);
       ExecProgram (commandLine);
    }
    else if (source == btnSplus) {
       textArea.append( newLine + "You are invoking s-plus program." + newLine +
       "Two scripts will be loaded. Make sure to run two scripts first,"+
       " then invoke macro from command window."+ newLine);
      String commandLine = "c:\\mggui\\startCode\\SplusBat.bat";

      ExecProgram (commandLine);
      }
    else if (source == btnSasResult){
      String [] commandLine = {"sas", "-autoexec",
      "c:\\mggui\\startCode\\autoexec1.sas"};//this sas program is for outputting result
      textArea.append( newLine + "You are invoking SAS_result program." +
   newLine + "Make sure run SAS_result first, then invoke macro with correct "+
       "directory information and data set." + newLine);
      ExecProgram (commandLine);
    }
    else if (source == btnHelp) {
        //show readme file for this program
      textArea.append( newLine + "You are seeking help for this program." + newLine +
       "A word format help document will be opened for your reference,"+ newLine);
      String commandLine = "c:\\mggui\\startCode\\help.bat";
      ExecProgram (commandLine);
    ;}
    else if (source == btnOutPut) {
    	fc.addChoosableFileFilter(new textFileFilter());
        int returnVal = fc.showOpenDialog(MGLinkerGUI.this);
               if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String [] commandLine = {"notepad", file.getName()};
                    ExecProgram (commandLine);
                    
                } else {
                   textArea.append("There is open file error.\n");
                }//show readme file for this program
    ;}
    else if (source == btnQuit){System.exit(0);}

    }

    public static void main (String args[]) throws NoClassDefFoundError {
         //MGLinkerGUI frame = new MGLinkerGUI();
         JFrame frame = new MGLinkerGUI();
        // frame.setTitle("Major Gene Linker GUI");
         /*frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });*/
        //this is the new and easy way
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
