package gov.nih.niehs.EPIGseq.datatable;


import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import util.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class SearchStringFrameIO extends JFrame {

  private JFrame tableFrame;
  private int selectedCol;
  private MessageBoard msg;
  public SearchStringFrameIO() throws HeadlessException {
  }
   /** Creates new form SearchString */
   public SearchStringFrameIO(JFrame _tableFrame ,int colNum,MessageBoard _msg) {
       tableFrame = _tableFrame;
       selectedCol = colNum;
       msg = _msg;
       try {
         jbInit();
       }
       catch(Exception e) {
         e.printStackTrace();
       }
       if (FileIO.imageIcon != null)
          setIconImage(FileIO.imageIcon.getImage());
       boxGroup = new ButtonGroup();
       boxGroup.add(jCheckBox_whole);
       boxGroup.add(jCheckBox_case);
       java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
       setLocation(screenSize.width-280,0);
       pack ();
   }
   /*
   public SearchStringFrameIO(JFrame _tableFrame ,int colNum,MessageBoard _msg, String [] str, boolean conjunction) {
       tableFrame = _tableFrame;
       selectedCol = colNum;
       msg = _msg;
       try {
         jbInit();
       }
       catch(Exception e) {
         e.printStackTrace();
       }
       if (FileIO.image != null)
          setIconImage(FileIO.image.getImage());
       boxGroup = new ButtonGroup();
       boxGroup.add(jCheckBox_whole);
       boxGroup.add(jCheckBox_case);
       java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
       setLocation(screenSize.width-280,0);
       pack ();
   }
*/
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the FormEditor.
    */
   private void jbInit() {
       jPanel1 = new javax.swing.JPanel();
       jLabel2 = new javax.swing.JLabel();
       jTextField_string = new javax.swing.JTextField();
       jButton_find = new javax.swing.JButton();
       jCheckBox_whole = new javax.swing.JCheckBox();
       jCheckBox_case = new javax.swing.JCheckBox();
       jButton_close = new javax.swing.JButton();
       setTitle("Find");
       setResizable(false);
       addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowClosing(java.awt.event.WindowEvent evt) {
               exitForm(evt);
           }
       }
       );

       jPanel1.setLayout(new java.awt.GridBagLayout());
       jPanel1.setPreferredSize(new java.awt.Dimension(280, 100));

       jLabel2.setText("Find: ");
         jLabel2.setForeground(java.awt.Color.black);
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
         jPanel1.add(jLabel2,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));


       jTextField_string.addCaretListener(new javax.swing.event.CaretListener() {
             public void caretUpdate(javax.swing.event.CaretEvent evt) {
                 jTextField1CaretUpdate(evt);
             }
         }
         );
         jPanel1.add(jTextField_string,      new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


       jButton_find.setFocusPainted(false);
         jButton_find.setForeground(java.awt.Color.gray);
         jButton_find.setText("Find");
         jButton_find.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 jButton_find_ActionPerformed(evt);
             }
         }
         );


       jCheckBox_whole.setSelected(true);
         jCheckBox_whole.setText("Match whole word only");
         jPanel1.add(jCheckBox_whole,     new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));


       jCheckBox_case.setText("Match case");
         jPanel1.add(jCheckBox_case,    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));


       jButton_close.setText("Close");
         jButton_close.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 jButton_close_ActionPerformed(evt);
             }
         }
         );
         jPanel1.add(jButton_close,    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 10), 0, 0));
    jPanel1.add(jButton_find,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 13, 5, 2), 0, 0));


       getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

       setBounds(0, 0, 280, 100);
   }

 private void jButton_close_ActionPerformed(java.awt.event.ActionEvent evt) {
     // Add your handling code here:
     this.setVisible(false);
 }

 private void jButton_find_ActionPerformed(java.awt.event.ActionEvent evt) {
     // Add your handling code here:
 //  tableFrame.initialStringSearch();
   int [] index = null;
   if (tableFrame instanceof GenericIntensityDataSetsFrameIO)
   ((GenericIntensityDataSetsFrameIO)tableFrame).updateStringSearch(index);
   else if (tableFrame instanceof GenericCompiledDataFrameIO)
     ((GenericCompiledDataFrameIO)tableFrame).updateStringSearch(index);
   else if (tableFrame instanceof DataSetTableFrame)
      ((DataSetTableFrame)tableFrame).updateStringSearch(index);


     TreeSet found;
     if(jButton_find.getForeground() == java.awt.Color.red)
         found = search();
     else return;
     if (found != null && found.size() > 0) {
         int totalFound = found.size();
         msg.messageDisplay("There are total " + totalFound + " number of genes found.\n");
         index = new int[totalFound];
         Iterator cur = v.iterator();
         for (int i=0;i<totalFound;i++) {
             Object o1 = cur.next();
             index[i] = Integer.parseInt((String)o1);
         }
         if (tableFrame instanceof GenericIntensityDataSetsFrameIO)
         ((GenericIntensityDataSetsFrameIO)tableFrame).updateStringSearch(index);
         else if (tableFrame instanceof GenericCompiledDataFrameIO)
           ((GenericCompiledDataFrameIO)tableFrame).updateStringSearch(index);
         else if (tableFrame instanceof DataSetTableFrame)
           ((DataSetTableFrame)tableFrame).updateStringSearch(index);
     }
     else {
         msg.messageDisplay("There are no such genes found.\n");
     }
 }
 public void setSelectedCol(int k) {
   if (tableFrame instanceof GenericIntensityDataSetsFrameIO)
   ((GenericIntensityDataSetsFrameIO)tableFrame).initialStringSearch();
   else if (tableFrame instanceof GenericCompiledDataFrameIO)
     ((GenericCompiledDataFrameIO)tableFrame).initialStringSearch();
   else if (tableFrame instanceof DataSetTableFrame)
     ((DataSetTableFrame)tableFrame).initialStringSearch();
   else return;
   selectedCol = k;
 }
 private TreeSet search() {
     String ss = jTextField_string.getText();
     String str = "";
     StringTokenizer parser = new StringTokenizer( ss,",");
     int numToken = parser.countTokens();
     JTable table;
     if (tableFrame instanceof GenericIntensityDataSetsFrameIO)
         table = ((GenericIntensityDataSetsFrameIO)tableFrame).getTable();
     else if (tableFrame instanceof GenericCompiledDataFrameIO)
         table = ((GenericCompiledDataFrameIO)tableFrame).getTable();
     else if (tableFrame instanceof DataSetTableFrame)
         table = ((DataSetTableFrame)tableFrame).getTable();
     else return null;
     int totalRow = table.getRowCount();
     v = new TreeSet();
     String [] s = new String[numToken];
     for (int j=0;j<numToken;j++)
         s[j] = parser.nextToken().trim();
     Object objValue;
     String strValue;
     for (int i = 0; i<totalRow ; i++) {
         objValue = table.getValueAt(i,selectedCol);
         if (objValue != null) {
           strValue = ((ColorStringClassIO)objValue).toString();
           for (int k=0;k<numToken;k++) {
               if (searchString(s[k],strValue)){
                   v.add(""+i);
                   k=numToken;
               }
           }

         }
     }
     return v;
 }

 private static TreeSet searchNegConjunctionStringList(String [] strList,JFrame _tableFrame, int selectedCol) {
     JTable table;
     if (_tableFrame instanceof GenericIntensityDataSetsFrameIO)
         table = ((GenericIntensityDataSetsFrameIO)_tableFrame).getTable();
     else if (_tableFrame instanceof GenericCompiledDataFrameIO)
         table = ((GenericCompiledDataFrameIO)_tableFrame).getTable();
     else if (_tableFrame instanceof DataSetTableFrame)
         table = ((DataSetTableFrame)_tableFrame).getTable();
     else return null;
     int totalRow = table.getRowCount();
     TreeSet v = new TreeSet();
     for (int i=0;i<totalRow;i++)
       v.add(i+"");
     Object objValue;
     String strValue;
     for (int k=0;k<strList.length;k++)
     for (int i = 0; i<totalRow ; i++) {
         objValue = table.getValueAt(i,selectedCol);
         if (objValue != null) {
           strValue = ((ColorStringClassIO)objValue).toString();
               if (strValue.equals(strList[k])){
                 v.remove(""+i);
                   i = totalRow;
               }
         }
     }
     return v;
 }
 private static TreeSet searchStringList(String [] strList,JFrame _tableFrame, int selectedCol) {
   if (strList == null) return null;
     JTable table;
     if (_tableFrame instanceof GenericIntensityDataSetsFrameIO)
         table = ((GenericIntensityDataSetsFrameIO)_tableFrame).getTable();
     else if (_tableFrame instanceof GenericCompiledDataFrameIO)
         table = ((GenericCompiledDataFrameIO)_tableFrame).getTable();
     else if (_tableFrame instanceof DataSetTableFrame)
         table = ((DataSetTableFrame)_tableFrame).getTable();
     else return null;
     int totalRow = table.getRowCount();
     TreeSet v = new TreeSet();
     Object objValue;
     String strValue;
     for (int k=0;k<strList.length;k++)
     for (int i = 0; i<totalRow ; i++) {
         objValue = table.getValueAt(i,selectedCol);
         if (objValue != null) {
           strValue = ((ColorStringClassIO)objValue).toString();
               if (strValue.equals(strList[k])){
                   v.add(""+i);
                   i = totalRow;
               }
         }
     }
     return v;
 }


 private boolean searchString(String s1, String s2) {
     int s1Length = s1.length();
     int s2Length = s2.length();
     if(s1Length > s2Length) return false;
     if (jCheckBox_case.isSelected())
         for (int i=0;i<=(s2Length-s1Length);i++) {
             if (s1.equals(s2.substring(i,i+s1Length))) return true;
         }
     else {
         s1 = s1.toLowerCase();
         s2 = s2.toLowerCase();
         for (int i=0;i<=(s2Length-s1Length);i++) {
             if (s1.equals(s2.substring(i,i+s1Length))) return true;
         }
     }
     return false;
 }
 private void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {
     // Add your handling code here:
     String s = jTextField_string.getText();
     if (s == null) jButton_find.setForeground(java.awt.Color.gray);
     else if (s.equals("")) jButton_find.setForeground(java.awt.Color.gray);
     else jButton_find.setForeground(java.awt.Color.red);
 }

   /** Exit the Application */
   private void exitForm(java.awt.event.WindowEvent evt) {
       //       System.exit (0);
       dispose();
   }
public static int [] getConjunctionList(String [] strList,JFrame _tableFrame,
                                        int colNum,MessageBoard _msg) {
 TreeSet found = searchStringList(strList,_tableFrame,colNum);
 if (found == null) return null;
 int size = found.size();
 if (size == 0) return null;
 int [] index = new int[size];
 size=0;
       Iterator cur = searchStringList(strList,_tableFrame,colNum).iterator();
       while(cur.hasNext()) {
         Object o1 = cur.next();
         index[size++] = Integer.parseInt((String)o1);

       }
  return index;

}
public static int [] getNegConjunctionList(String [] strList,JFrame _tableFrame,
                                        int colNum,MessageBoard _msg) {
 TreeSet found = searchNegConjunctionStringList(strList,_tableFrame,colNum);
 int size = found.size();
 if (size == 0) return null;
 int [] index = new int[size];
 size=0;
       Iterator cur = searchStringList(strList,_tableFrame,colNum).iterator();
       while(cur.hasNext()) {
         Object o1 = cur.next();
         index[size++] = Integer.parseInt((String)o1);

       }
  return index;

}
   /**
    * @param args the command line arguments
    */
   //    public static void main (String args[]) {
   //        new SearchString ().show ();
   //    }


   // Variables declaration - do not modify
   private javax.swing.JPanel jPanel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JTextField jTextField_string;
   private javax.swing.JButton jButton_find;
   private javax.swing.JCheckBox jCheckBox_whole;
   private javax.swing.JCheckBox jCheckBox_case;
   private javax.swing.JButton jButton_close;
   // End of variables declaration
   private ButtonGroup boxGroup;
   //    Vector v;
   private TreeSet v;


}