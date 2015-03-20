package gov.nih.niehs.EPIGseq.datatable;


import classification.*;
import java.sql.*;
import java.sql.ResultSet.*;

import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import expressionPredictor.*;
import analysis.heatMap.*;
import analysis.principlecomponentanalysis.*;
import analysis.quantitativeSupervisedClassification.*;
import analysis.relevanceAnalysis.*;
import analysis.lineardiscrimination.*;
import analysis.bayesiananalysis.*;
import analysis.relevanceAnalysis.*;
import analysis.fuzzyartmap.*;
import analysis.clustering.*;
import analysis.epig.*;
import util.*;
import myutility.statistics.*;
import myutility.numerical.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
import myutility.plot2D.*;
import analysis.TFanalysis.*;
public class CompiledDataFrame extends JFrame {
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel3 = new JPanel();
  private JTextField jTextField_startCol = new JTextField();
  private JPanel jPanel1 = new JPanel();
  private JButton jButton_loadDataFile = new JButton();
  private JTextField jTextField_startRow = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JButton jButton_loadExpDesignFile = new JButton();
  private CompiledData compiledData = new CompiledData();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private GenericTable myTable1;
  private javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
  private JPopupMenu jPopupMenu1 = new JPopupMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();
  private JMenuItem jMenuItem4 = new JMenuItem();

  public CompiledDataFrame() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws HeadlessException {
    CompiledDataFrame compiledDataFrame1 = new CompiledDataFrame();
  }
  private void jbInit() throws Exception {
    jLabel1.setText("Data Start Column:  ");
    jLabel2.setText("Data Start Row:  ");
    jTextField_startRow.setText("2");
    jTextField_startRow.setPreferredSize(new Dimension(25, 21));
    jButton_loadDataFile.setText("Load Data File");
    jButton_loadDataFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_loadDataFile_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout1);
    jTextField_startCol.setText("2");
    jTextField_startCol.setPreferredSize(new Dimension(25, 21));
    jButton_loadExpDesignFile.setText("Load Experiment Design File");
    jButton_loadExpDesignFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_loadExpDesignFile_actionPerformed(e);
      }
    });
    jScrollPane1.setPreferredSize(new Dimension(400, 400));
    jMenuItem1.setText("Replicate Row");
    jMenuItem2.setText("Dye label Row");
    jMenuItem3.setText("TechRep Row");
    jMenuItem4.setText("Cell Line Row");
    this.getContentPane().add(jTabbedPane1, BorderLayout.NORTH);
    jTabbedPane1.add(jPanel1,   "Load Data");
    jTabbedPane1.add(jPanel3,   "Analysis");
    jPanel1.add(jLabel1,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel2,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jTextField_startRow,     new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jTextField_startCol,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_loadDataFile,     new GridBagConstraints(3, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 5, 20, 5), 0, 0));
    jPanel1.add(jButton_loadExpDesignFile,    new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane1,        new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPopupMenu1.add(jMenuItem4);
    jPopupMenu1.add(jMenuItem3);
    jPopupMenu1.add(jMenuItem2);
    jPopupMenu1.add(jMenuItem1);
//    jScrollPane1.getViewport().add(myTable1, null);
    setJMenuBar(jMenuBar1);
    setBounds(0, 0, 500, 600);
  }

  void jButton_loadDataFile_actionPerformed(ActionEvent e) {
      compiledData.loadDataFile(jTextField_startCol.getText(),jTextField_startRow.getText());
  }

  void jButton_loadExpDesignFile_actionPerformed(ActionEvent e) {
    String [][] expDesign = compiledData.loadExperimentDesignFile();
    if (expDesign==null)return;
    myTable1 = new GenericTable(new GenericDataTableModel(expDesign));
    GenericDataTableModel genericDataTableModel = (GenericDataTableModel)myTable1.getModel();
//System.out.print("col num = "+genericDataTableModel.getColumnCount());
//System.out.print("row num = "+genericDataTableModel.getRowCount());

    tableSetup(myTable1);
    jScrollPane1.getViewport().add(myTable1, null);

    myTable1.repaint();
//    DefaultTableModel tableModel = new DefaultTableModel();
//    for(int i=0;i<expDesign.length;i++)
//    tableModel.addRow(expDesign[i]);
//    jTable1.setModel(tableModel);

//    jTable1.repaint();

 //   jTable1.setM


  }
  private void tableSetup(JTable table) {
    table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 400));
    table.setAutoCreateColumnsFromModel(false);
    table.setCellSelectionEnabled(true);
    table.setColumnSelectionAllowed(true);
//    table.setDragEnabled(false);
    table.setGridColor(Color.blue);
    JTableHeader th = table.getTableHeader();
    table.setRowSelectionAllowed(true);
    table.setSelectionBackground(Color.gray);
    table.setCellSelectionEnabled(true);
    TableColumnModel tcm = table.getColumnModel();
    tcm.setColumnSelectionAllowed(true);
    ColoredTableCellRendererClassIO renderer, headRenderer;
    GenericDataTableModel model = (GenericDataTableModel)table.getModel();
 //   Dimension d = jPanel_table.getPreferredSize();
    ColumnNameClassIO []  cnc = model.getColumnNameClasses();
 //   float width = (float)d.getWidth()/model.getColumnCount();
    for (int k = 0; k < model.getColumnCount(); k++) {
      renderer = new ColoredTableCellRendererClassIO();
      renderer.setHorizontalAlignment(JLabel.LEFT);
      TableColumn column = tcm.getColumn(k);
      column.setCellRenderer(renderer);

      headRenderer = new ColoredTableCellRendererClassIO();

      headRenderer.setBackground(Color.lightGray);
      Insets insets= headRenderer.getInsets();
      insets.left = 1;
      insets.right = 1;
      column.setHeaderRenderer(headRenderer);
      int colWidth = cnc[k].getWidth();
   //   if (colWidth < (int)width) colWidth = (int)width;
      column.setPreferredWidth(colWidth);
    }
    table.addMouseListener(new java.awt.event.MouseAdapter() {
//      public void mousePressed(java.awt.event.MouseEvent evt) {
//        tableFormMousePressed(evt);
//      }
//      public void mouseReleased(java.awt.event.MouseEvent evt) {
//        tableFormMouseReleased(evt);
//      }
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tableFormMouseClicked(evt);
      }
    }
    );

    JTableHeader header = myTable1.getTableHeader();
    header.setUpdateTableInRealTime(true);
    header.setReorderingAllowed(false);
    header.addMouseListener(new java.awt.event.MouseAdapter() {
 //     public void mousePressed(java.awt.event.MouseEvent evt) {
 //       headerformMousePressed(evt);
 //     }
 //     public void mouseReleased(java.awt.event.MouseEvent evt) {
 //       headerformMouseReleased(evt);
 //     }
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        headerformMouseClicked(evt);
      }
    }
    );

  }
  private void tableFormMouseClicked(java.awt.event.MouseEvent evt) {
    // Add your handling code here:
    //   TableColumnModel cm = jTable1.getColumnModel();
    //   ListSelectionModel sm = cm.getSelectionModel();
    //   sm.clearSelection();
    //   cm.getSelectedColumns()
    if(SwingUtilities.isLeftMouseButton(evt)){
    }
    else if (SwingUtilities.isRightMouseButton(evt)) {
      if (!jPopupMenu1.isShowing())
        jPopupMenu1.show(myTable1, evt.getX(), evt.getY());
//      if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
//          && !jPopupMenu_search.isShowing())
//        jPopupMenu_cellTextSelection.show( myTable1, evt.getX(), evt.getY() );
    }

  }
  private void headerformMouseClicked(java.awt.event.MouseEvent evt) {
    myTable1.clearSelection();
    TableColumnModel colModel = myTable1.getColumnModel();
    JTableHeader t=myTable1.getTableHeader();
    Point p = new Point();
    p.setLocation(evt.getX(),evt.getY());
    int columnModelIndex = t.columnAtPoint(p);
//    selectedCol = columnModelIndex;
//    if(SwingUtilities.isLeftMouseButton(evt)){
//      jPopupMenu_columnNameList.show( myTable1, evt.getX(), evt.getY() );
//    }
//    else if (SwingUtilities.isRightMouseButton(evt)) {
//      jPopupMenu_search.show( myTable1, evt.getX(), evt.getY() );
//    }

  }

}