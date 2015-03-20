package gov.nih.niehs.EPIGseq.datatable;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import util.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import myutility.numerical.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
import myutility.plot2D.*;
import myutility.statistics.*;
import normalization.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class AffyDataSVNFrame extends JFrame {
  public static int scatter = 1;
  public static int line = 2;
  public static int line_and_scatter = 3;
  public static int histogram = 4;
  private DataSet ds;
  GenericDataTableModel md;
//  private int plotOption;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList jList_columnName = new JList();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private TitledBorder titledBorder1;
  private int [] columnSelected;
  private int xCol=-1,yCol=-1,y2Col=-1;
  String [] columnName;
//  private JPlot2DFrame plot;
  private MessageBoard msg;
  public DataPlot dataPlot;
  private JPanel jPanel2 = new JPanel();
  private JButton jButton_bkg_histo = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTextField_bkg = new JTextField();
  private JButton jButton_sub_bkg = new JButton();
  private JButton jButton_multiNorm = new JButton();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JButton jButton_undo_bkg = new JButton();
  ArrayCenter [] arrayCenter;

  public AffyDataSVNFrame(DataPlot _plot,DataSet _ds,GenericDataTableModel _md,MessageBoard _msg) throws HeadlessException {
    ds=_ds;
    md = _md;
    msg = _msg;
    dataPlot = _plot;
    if (dataPlot == null)
      dataPlot = new DataPlot(msg);
    if (!dataPlot.isVisible())
      dataPlot.setVisible(true);
//    plotOption = _plotOption;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (FileIO.imageIcon != null)
      setIconImage(FileIO.imageIcon.getImage());
    exitConfirm();
    columnName =  ds.getColumnInfo(0);
    jList_columnName.setListData(columnName);
    jList_columnName.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        //       tableFormMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        //       tableFormMouseReleased(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList_columnName_FormMouseClicked(evt);
      }
    }
    );

  }
  public AffyDataSVNFrame(DataPlot _plot,DataSet _ds,MessageBoard _msg) throws HeadlessException {
    ds=_ds;
    msg = _msg;
    dataPlot = _plot;
    if (dataPlot == null)
      dataPlot = new DataPlot(msg);
    if (!dataPlot.isVisible())
      dataPlot.setVisible(true);
//    plotOption = _plotOption;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (FileIO.imageIcon != null)
      setIconImage(FileIO.imageIcon.getImage());
    exitConfirm();
    columnName =  ds.getColumnInfo(0);
    jList_columnName.setListData(columnName);
    jList_columnName.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        //       tableFormMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        //       tableFormMouseReleased(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList_columnName_FormMouseClicked(evt);
      }
    }
    );

  }

  public AffyDataSVNFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void exitConfirm() {
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    };
    addWindowListener(l);
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Single Channel Normalization");
    this.getContentPane().setLayout(gridBagLayout1);
    jScrollPane1.setBorder(titledBorder1);
    jScrollPane1.setPreferredSize(new Dimension(350, 156));
    this.setTitle("Select Column For Plotting");
    jButton_bkg_histo.setText("Selected Array Histogram");
    jButton_bkg_histo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_bkg_histo_actionPerformed(e);
      }
    });
    jPanel2.setBorder(titledBorder1);
    jPanel2.setPreferredSize(new Dimension(400, 300));
    jPanel2.setLayout(gridBagLayout2);
    jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel3.setText("Background:");
    jButton_sub_bkg.setText("Subtract Bkg - 1");
    jButton_sub_bkg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_sub_bkg_actionPerformed(e);
      }
    });
    jButton_multiNorm.setText("Multiarray Norm (vs top one) - 2");
    jButton_multiNorm.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_multiNorm_actionPerformed(e);
      }
    });
    jButton_undo_bkg.setText("Undo Subtraction");
    jButton_undo_bkg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_undo_bkg_actionPerformed(e);
      }
    });
    jButton_update.setText("Update Display Table");
    jButton_update.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_update_actionPerformed(e);
      }
    });
    jButton_multiNorm_MA.setText("M-A adjustment (vs top one) - 4");
    jButton_multiNorm_MA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_multiNorm_MA_actionPerformed(e);
      }
    });
    jButton_linearShearAdjustmemt.setText("Linear Shear Adjust (vs top one) - 3");
    jButton_linearShearAdjustmemt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_linearShearAdjustmemt_actionPerformed(e);
      }
    });
    jButton_polyShearAdjustment.setText("Ploy Shear Adjust (vs top one) - 3");
    jButton_polyShearAdjustment.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_polyShearAdjustment_actionPerformed(e);
      }
    });
    jLabelpolyDegree.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelpolyDegree.setText("Poly Degree:");
    jTextField_polyDegree.setPreferredSize(new Dimension(50, 21));
    jTextField_polyDegree.setText("3");
    jButton_X_column.setText("<-> X");
    jButton_X_column.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_X_column_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout3);
    jButton_Y_column.setText("<-> Y");
    jButton_Y_column.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_Y_column_actionPerformed(e);
      }
    });
    jTextField_dataPointPercent.setText("100");
    jRadioButton_symbol.setSelected(true);
    jRadioButton_symbol.setText("Symbol");
    jButton_plot.setText("2D Plot");
    jButton_plot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_plot_actionPerformed(e);
      }
    });
    jRadioButton_line.setSelected(true);
    jRadioButton_line.setText("Line");
    jLabel2.setText("% of Total Data Point");
    jButton_concel.setText("Clear");
    jButton_concel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_concel_actionPerformed(e);
      }
    });
    jButton1.setText("Histogram of Selected Columns");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton_Y2_column.setText("<-> Y2");
    jButton_Y2_column.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_Y2_column_actionPerformed(e);
      }
    });
    jButton_columnCorrelation.setText("Correlation of Selected Columns");
    jButton_columnCorrelation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_columnCorrelation_actionPerformed(e);
      }
    });
    jTabbedPane1.setPreferredSize(new Dimension(400, 300));
    jButton_getTraining.setText("Get Training Set");
    jButton_getTraining.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_getTraining_actionPerformed(e);
      }
    });
    jButton_distSelected.setText("Distance of Selected Array");
    jButton_distSelected.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_distSelected_actionPerformed(e);
      }
    });
    jButton_distAll.setText("Distance of all Arrays");
    jButton_distAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_distAll_actionPerformed(e);
      }
    });
    jButton_reset.setText("Reset");
    jButton_reset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_reset_actionPerformed(e);
      }
    });
    jPanel3.setLayout(gridBagLayout4);
    jTextField_bkg.setPreferredSize(new Dimension(50, 21));
    jMenu1.setText("Action");
    jMenuItem_rmaNormalization.setText("RMA normalization");
    jMenuItem_rmaNormalization.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_rmaNormalization_actionPerformed(e);
      }
    });
    jButton_polyPair.setText("Poly Adjust (pair) -3");
    jButton_polyPair.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_polyPair_actionPerformed(e);
      }
    });
    jButton_linearPair.setText("Linear Adjust (pair) -3");
    jButton_linearPair.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_linearPair_actionPerformed(e);
      }
    });
    jButton_MA_pair.setText("M-A adjustment (pair) - 4");
    jButton_MA_pair.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_MA_pair_actionPerformed(e);
      }
    });
    jButton_multiScale.setText("Multiarray Norm (vs grand mean) - 2");
    jButton_multiScale.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_multiScale_actionPerformed(e);
      }
    });
    jButton_2DPlot_histo.setText("2D HistoColor");
    jButton_2DPlot_histo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_2DPlot_histo_actionPerformed(e);
      }
    });
    this.getContentPane().add(jScrollPane1,                                     new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jScrollPane1.getViewport().add(jList_columnName, null);
    this.getContentPane().add(jPanel2,             new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    this.getContentPane().add(jTabbedPane1,        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jTabbedPane1.add(jPanel1,   "Plot");
    jPanel1.add(jButton_X_column,                               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_Y_column,                              new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jTextField_X_column,                            new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jTextField_Y2_column,                new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jRadioButton_symbol,             new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jTextField_Y_column,          new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_plot,          new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jRadioButton_line,         new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_Y2_column,       new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton1,  new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_columnCorrelation, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jTextField_dataPointPercent,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_concel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jButton_2DPlot_histo,  new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jTabbedPane1.add(jPanel3,  "Distance");
    jPanel3.add(jButton_getTraining,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_bkg_histo,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_sub_bkg,       new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 3, 15, 3), 0, 0));
    jPanel2.add(jButton_undo_bkg,        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel3,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_multiNorm_MA, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_polyDegree, new GridBagConstraints(0, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_MA_pair, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabelpolyDegree, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_polyPair, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_polyShearAdjustment, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_bkg,  new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_linearShearAdjustmemt, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_multiNorm, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_multiScale, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_linearPair, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jButton_update,  new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel3.add(jButton_distSelected,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel3.add(jButton_distAll,   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel3.add(jButton_reset,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jMenuBar1.add(jMenu1);
    jMenu1.add(jMenuItem_rmaNormalization);
    setJMenuBar(jMenuBar1);

    setBounds(0, 0, 600, 700);
  }
  private void jList_columnName_FormMouseClicked(java.awt.event.MouseEvent evt) {
    // Add your handling code here:
    columnSelected = jList_columnName.getSelectedIndices();
    if (columnSelected.length == 1) {
      jButton_X_column.setEnabled(true);
      jButton_Y_column.setEnabled(true);
    }
    else {
      jButton_X_column.setEnabled(false);
      jButton_Y_column.setEnabled(false);
    }
  }

  void jButton_X_column_actionPerformed(ActionEvent e) {
    columnSelected = jList_columnName.getSelectedIndices();
    if (columnSelected.length != 1) {
      System.out.println("Select one column at time.");
      return;
    }
    if (yCol == columnSelected[0] || y2Col == columnSelected[0]) return;
    if (xCol == columnSelected[0]) {
      xCol = -1;
      jTextField_X_column.setText("");
      return;
    }
    xCol = columnSelected[0];
    jTextField_X_column.setText(columnName[xCol]);
  }

  void jButton_Y_column_actionPerformed(ActionEvent e) {
    columnSelected = jList_columnName.getSelectedIndices();
    if (columnSelected.length != 1) {
      System.out.println("Select one column at time.");
      return;
    }
    if (xCol == columnSelected[0] || y2Col == columnSelected[0]) return;

    if (yCol == columnSelected[0]) {
      yCol = -1;
      jTextField_Y_column.setText("");
      return;
    }
    yCol = columnSelected[0];
    jTextField_Y_column.setText(columnName[yCol]);

  }

  void jButton_plot_actionPerformed(ActionEvent e) {
    int percent = 10;
    try {
      percent = Integer.parseInt(jTextField_dataPointPercent.getText());
    }
    catch (NumberFormatException ee) {
      msg.display("Use all the data points.");
      percent=100;
    }
  //  msg.display("plot coloumn " + percent + "% data point.");
if (!dataPlot.isVisible())
  dataPlot.setVisible(true);
    //.getText();


//      msg.display("plot start...");
      if (xCol == -1 || yCol == -1) {
        msg.display("xCol=",xCol);
        msg.display("yCol=",yCol);
        return;
      }
      String [] label = ds.getColumnLabel_1(xCol);
      float [] x = ds.getColumnData_1(xCol);
      if (x == null)  {
        System.out.println("x column data is nou decimal.");
        return;
      }
      float [] y = ds.getColumnData_1(yCol);
      if (y == null)  {
        System.out.println("y column data is nou decimal.");
        return;
      }
      float [] y2 = null;
      if (y2Col != -1)
        y2 = ds.getColumnData_1(y2Col);

      if (percent < 100 && percent > 0) {
         int totalDataPoints = x.length;
         int percentDataPoint = (totalDataPoints*percent)/100;
         float [] xx = new float[percentDataPoint];
         float [] yy = new float[percentDataPoint];
         String [] labelXX = new String[percentDataPoint];
         for (int i=0;i<percentDataPoint;i++) {
           xx[i] = x[i];
           yy[i] = y[i];
           labelXX[i] = label[i];
         }
         x = xx;
         y = yy;
         label = labelXX;
         float [] yy2 = null;
         if (y2 != null) {
           yy2 = new float[percentDataPoint];
           for (int i=0;i<percentDataPoint;i++) {
             yy2[i] = y[i];
          }
           y2 = yy2;
         }

      }
        if (y2!=null) {
          float [][] yvals = new float[2][];
          yvals[0] = y;
          yvals[1] = y2;
          String [] profilename = new String[2];
          profilename[0] = columnName[yCol];
          profilename[1] = columnName[y2Col];
         dataPlot.plotXY_profile_multi(x,yvals,columnName[yCol],columnName[y2Col],"X-Y plot",false,
                             null,profilename,jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
        }
        else {
          dataPlot.plotXY_profile(x,y,columnName[xCol],columnName[yCol],"X-Y plot",
                                  Color.blue,label,columnName[yCol],
                                  jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
        }
  }

  void jButton_concel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jButton1_actionPerformed(ActionEvent e) {
    histogramPlot();
  }
   private void histogramPlot() {
     int [] selectedColum = jList_columnName.getSelectedIndices();
     if (selectedColum == null || selectedColum.length==0) return;

     float [][] x = new float[selectedColum.length][];
     msg.display("Statistics evaluation\tmean\tstd\tskewness\tkurtosis");
     for (int i=0;i<x.length;i++) {
       x[i] = ds.getColumnData_1(selectedColum[i]);
       msg.display("column "+selectedColum[i] + "\t"+ Statistics.mean(x[i]) + "\t"+ Statistics.stdDev(x[i])+"\t" +
                   Statistics.skewness(x[i])+"\t"+Statistics.kurtosis(x[i]));
     }
     float [][] histoData = DataConversion.getMultiHistogramData(x);
     if (selectedColum.length <= 3) {
     dataPlot.plotXY_histogram(histoData[0],histoData[1],"Log2 Ratio","Frequency","Histogram",
                             MyColor.getGeneColorBasic(0),null,columnName[selectedColum[0]],
                             jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
//     public void plotXY_firstLine(float [] xVal, float [] yVal, String xLabel, String yLabel,
//                         String title, Color color, String [] label,String curveName, boolean lineState,boolean markerState) {


     for (int i=1;i<selectedColum.length;i++)
        dataPlot.plotXY_add(histoData[0],histoData[i+1],MyColor.getGeneColorBasic(i),null,columnName[selectedColum[i]],
                            jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
     }
     else {
       dataPlot.plotXY_histogram(histoData[0],histoData[1],"Log2 Ratio","Frequency","Histogram",
                               Color.green,null,columnName[selectedColum[0]],jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
       for (int i=1;i<selectedColum.length;i++)
          dataPlot.plotXY_add(histoData[0],histoData[i+1],MyColor.getGeneColorBasic(i),null,columnName[selectedColum[i]],
                              jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());
     }
     float [][] d = new float[histoData.length-1][];
     for (int i=0;i<d.length;i++)
       d[i] = histoData[i+1];
     dataPlot.setScale(DataConversion.getScale(histoData[0]),DataConversion.getScale(d));
     dataPlot.repaint();

   }

  void jButton_Y2_column_actionPerformed(ActionEvent e) {
    columnSelected = jList_columnName.getSelectedIndices();
    if (columnSelected.length != 1) {
      System.out.println("Select one column at time.");
      return;
    }
    if (xCol == columnSelected[0] || yCol == columnSelected[0]) return;
    if (y2Col == columnSelected[0]) {
      y2Col = -1;
      jTextField_Y2_column.setText("");
      return;
    }
    y2Col = columnSelected[0];
    jTextField_Y2_column.setText(columnName[y2Col]);

  }

  void jButton_bkg_histo_actionPerformed(ActionEvent e) {
     bkg_histogram();
  }

  private void bkg_histogram() {
    int  selectedColum = jList_columnName.getSelectedIndex();
    float [] x;
      x = ds.getColumnData_1(selectedColum);
      msg.display("column "+selectedColum + " has average: "+ Statistics.mean(x) + "\nstandard deviation: "+
                  Statistics.stdDev(x));
    float [][] histoData = DataConversion.getHistogramData(x);
    dataPlot.plotXY_histogram(histoData[0],histoData[1],"Log2 Ratio","Frequency","Histogram",
                            MyColor.getGeneColorBasic(0),null,columnName[selectedColum],
                            jRadioButton_line.isSelected(),jRadioButton_symbol.isSelected());

    dataPlot.repaint();

  }

  void jButton_sub_bkg_actionPerformed(ActionEvent e) {
         String str_bkg =  jTextField_bkg.getText();
         float bkg;
         try {
           bkg = Float.parseFloat(str_bkg);
         }
         catch(NumberFormatException ee) {
           return;
         }
         bkg = (float)Math.exp(bkg*ConstantValue.log2);
         int  selectedColum = jList_columnName.getSelectedIndex();
         float []  x = ds.getColumnData_1(selectedColum);
         original_x = ds.getColumnData_1(selectedColum);
        for (int i=0;i<x.length;i++) {
          x[i] = (float)Math.exp(x[i]*ConstantValue.log2);
          x[i] -= bkg;
          if (x[i] >= 1)
          x[i] = (float)Math.log(x[i])/ConstantValue.log2;
          else
          x[i] = 0;
        }
       ds.setColumnData_1(selectedColum,x);
       float x_mean = Statistics.mean(x);
       msg.display("column "+selectedColum + " has average: "+ x_mean + "\nstandard deviation: "+
                   Statistics.stdDev(x));
       ds.setColumn_mean(selectedColum,x_mean);
  }
  float [] original_x;
  private JButton jButton_update = new JButton();
  private JButton jButton_multiNorm_MA = new JButton();
  private JButton jButton_linearShearAdjustmemt = new JButton();
  private JButton jButton_polyShearAdjustment = new JButton();
  private JLabel jLabelpolyDegree = new JLabel();
  private JTextField jTextField_polyDegree = new JTextField();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JButton jButton_X_column = new JButton();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JButton jButton_Y_column = new JButton();
  private JTextField jTextField_X_column = new JTextField();
  private JTextField jTextField_Y2_column = new JTextField();
  private JTextField jTextField_dataPointPercent = new JTextField();
  private JRadioButton jRadioButton_symbol = new JRadioButton();
  private JTextField jTextField_Y_column = new JTextField();
  private JButton jButton_plot = new JButton();
  private JRadioButton jRadioButton_line = new JRadioButton();
  private JLabel jLabel2 = new JLabel();
  private JButton jButton_concel = new JButton();
  private JButton jButton1 = new JButton();
  private JButton jButton_Y2_column = new JButton();
  private JButton jButton_columnCorrelation = new JButton();
  private JButton jButton_getTraining = new JButton();
  private JButton jButton_distSelected = new JButton();
  private JButton jButton_distAll = new JButton();
  private JButton jButton_reset = new JButton();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem_rmaNormalization = new JMenuItem();
  private JButton jButton_polyPair = new JButton();
  private JButton jButton_linearPair = new JButton();
  private JButton jButton_MA_pair = new JButton();
  private JButton jButton_multiScale = new JButton();
  private JButton jButton_2DPlot_histo = new JButton();
  void jButton_undo_bkg_actionPerformed(ActionEvent e) {
   if (original_x != null) {
     int  selectedColum = jList_columnName.getSelectedIndex();
     ds.setColumnData_1(selectedColum,original_x);
   }
  }


public void columnData_rescale(float [] x, float mean) {
  float x_mean = Statistics.mean(x);
  DataConversion.scaling2D_exclude_zero(x,mean/x_mean);
}
public void columnData_rescale(float [] x, float thismean, float mean) {
  DataConversion.scaling2D_exclude_zero(x,mean/thismean);
}

  void jButton_update_actionPerformed(ActionEvent e) {
     md.updateDataSetValue();
  }

  void jButton_columnCorrelation_actionPerformed(ActionEvent e) {
     columnCorrelation();
  }
  private void columnCorrelation() {
    int [] selectedColum = jList_columnName.getSelectedIndices();
    if (selectedColum == null || selectedColum.length==0) return;

    float [][] x = new float[selectedColum.length][];
    float [] xmax = new float[selectedColum.length];
    String [] selectedColumnName = new String[selectedColum.length];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(selectedColum[i]);
      xmax[i] = Statistics.maxVal(x[i]);
      selectedColumnName[i] = columnName[selectedColum[i]];
    }
    float max = Statistics.maxVal(xmax);


    int numPairs = x.length*(x.length-1)/2;
    float [] rValue = new float[numPairs];
    int num=0;
    for (int i=0;i<x.length;i++) {
      for (int j=i+1;j<x.length;j++) {
        rValue[num++] = Correlation.pearsonCorrelation_rValue(x[i],x[j]);
      }
    }
    msg.display("pair wised correlation r-values",rValue);
    float avgRvalue = Statistics.mean(rValue);
    float minRvalue = Statistics.minVal(rValue);
    float maxRvalue = Statistics.maxVal(rValue);
    msg.display("average r-value = "+avgRvalue+ " minimum r-value = "+minRvalue + " maximum r-value = "+maxRvalue);



    boolean line = false, mark=true;
    Color lineColor = Color.blue, markColor = Color.blue;
    dataPlot.plotXY_pair_wise_scatter_plot(x,selectedColumnName, "", lineColor, line,markColor, mark,max);
    dataPlot.repaint();

  }

  void jButton_multiNorm_MA_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];

    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }
    for (int i=1;i<x.length;i++) {
      x[i] = ma_adjustment(x[i],x[0]);
      ds.setColumnData_1(i,x[i]);
    }

  }
  private float [] ma_adjustment(float [] treated, float [] control) {
    float [] a_x = new float[treated.length];
    float [] m_y = new float[treated.length];

  for (int i=0;i<a_x.length;i++) {
    a_x[i] = (treated[i]+control[i])*0.5f;
    m_y[i] = treated[i]-control[i];
  }
  float a_minX = Statistics.minVal(a_x);
  float a_maxX = Statistics.maxVal(a_x);
  float max_min = a_maxX-a_minX;
  float delta = max_min/200;
  float start_x,end_x,temp;
  float [] a_ave_x = new float[200];
  float [] m_ave_y = new float[200];
    float delta_y = 0.1f;
    int num;
    float [][] xx_histo = new float[200][];
    for (int i=0;i<200;i++) {
      start_x = a_minX+i*delta;
      end_x = start_x+delta;
      num=0;
      a_ave_x[i] = (start_x+end_x)*0.5f;
      for (int j=0;j<a_x.length;j++) {
        if (a_x[j] >= start_x && a_x[j] < end_x) {
           num++;
        }
      }
      xx_histo[i] = new float[num];
      num=0;
      for (int j=0;j<a_x.length;j++) {
        if (a_x[j] >= start_x && a_x[j] < end_x) {
           xx_histo[i][num] = m_y[j];
           num++;
        }
      }
    }
    float tt;
     for (int i=0;i<a_ave_x.length;i++) {
       if (xx_histo[i].length < 1) {
         m_ave_y[i] = 0;
         }
       else  {
         m_ave_y[i] = Statistics.meanExcludeHigherZscoreData(xx_histo[i],3);
         }
     }
      for (int k=0;k<15;k++) {
      m_ave_y = NumericalAnalysis.getSmoothData(m_ave_y,5);
      }
     for (int i=0;i<200;i++) {
       start_x = a_minX+i*delta;
       end_x = start_x+delta;
       for (int j=0;j<a_x.length;j++) {
         if (a_x[j] >= start_x && a_x[j] < end_x) {
            m_y[j]-=m_ave_y[i];
         }
       }
     }

     for (int i=0;i<a_x.length;i++) {
       treated[i] = a_x[i] + m_y[i]/2;
       if (treated[i] < 0) treated[i] = 0;
     }
    return treated;
  }

//  private float [] linearRegression_shearTransformation_adjustment(float [] control,float [] treated) {
//  float []  fittingResult = SVNormalization.linearRegression(control,treated,null,null,msg);
//          fittingResult = SVNormalization.linearRegression_shearTransformation_Adjustment(control,
//            treated,null,null,fittingResult,msg);
//       return treated;
//  }
  private float [] linearRegressionAdjustment(float [] control_original,float [] treated) {
    float [] control = new float [control_original.length];
    for (int i=0;i<control_original.length;i++)
      control[i] = control_original[i];
      float []  fittingResult = SVNormalization.linearRegression(control,treated,null,null,msg);
      fittingResult = SVNormalization.linearRegressionAdjustment(control,treated,null,null,fittingResult,msg);
      float []  newfittingResult = SVNormalization.linearRegression(control_original,treated,null,null,msg);
      int num= 1;
      while (Math.abs(newfittingResult[0]) > 0.1f || Math.abs(newfittingResult[1])-1 > 0.01f  ) {
        for (int i=0;i<control_original.length;i++)
          control[i] = control_original[i];
         fittingResult = SVNormalization.linearRegression(control,treated,null,null,msg);
         fittingResult = SVNormalization.linearRegressionAdjustment(control,treated,null,null,fittingResult,msg);
         newfittingResult = SVNormalization.linearRegression(control_original,treated,null,null,msg);

         num++;
      }
      msg.display("interation number = "+num +" ");
       return treated;
  }
//  private float [] ployRegression_shearTransformation_adjustment( float [] control_original,float [] treated) {
//    int degree=3;
//  float []  fittingResult = SVNormalization.polynormialRegression(control,treated,null,null,degree,msg);
//  SVNormalization.polynormial_shearTransformation_Adjustment(control,treated,fittingResult,degree);
//       return treated;
//  }
  private float [] ployRegressionAdjustment( float [] control_original,float [] treated,
      int degree) {
//    int degree=3;
    float [] control = new float [control_original.length];
    for (int i=0;i<control_original.length;i++)
      control[i] = control_original[i];

   float []  fittingResult = SVNormalization.polynormialRegression(control,treated,null,null,degree,msg);
  SVNormalization.polynormialRegressionAdjustment(control,treated,null,null,fittingResult,msg);
  float []  newfittingResult = SVNormalization.linearRegression(control_original,treated,null,null,msg);
  int num= 1;
  while (Math.abs(newfittingResult[0]) > 0.1f || Math.abs(newfittingResult[1])-1 > 0.01f  ) {
    for (int i=0;i<control_original.length;i++)
      control[i] = control_original[i];
     fittingResult = SVNormalization.polynormialRegression(control,treated,null,null,degree,msg);
     fittingResult = SVNormalization.polynormialRegressionAdjustment(control,treated,null,null,fittingResult,msg);
     newfittingResult = SVNormalization.linearRegression(control_original,treated,null,null,msg);

     num++;
  }
  msg.display("interation number = "+num +" ");

       return treated;
  }

  void jButton_linearShearAdjustmemt_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }

    for (int i=1;i<x.length;i++) {
      x[i] = linearRegressionAdjustment(x[0],x[i]);
      ds.setColumnData_1(i,x[i]);
    }

  }
  void jButton_linearPair_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }

    for (int i=0;i<x.length-1;i=i+2) {
      x[i] = linearRegressionAdjustment(x[i+1],x[i]);
      ds.setColumnData_1(i,x[i]);
    }

  }

  void jButton_polyShearAdjustment_actionPerformed(ActionEvent e) {
    String str_bkg =  jTextField_polyDegree.getText();
    int degree = 2;
    try {
      degree = Integer.parseInt(str_bkg);
    }
    catch(NumberFormatException ee) {
      degree = 2;
    }
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }
   if (degree < 2) {
     for (int i=1;i<x.length;i++) {
      x[i] = linearRegressionAdjustment(x[0],x[i]);
      ds.setColumnData_1(i,x[i]);
    }
   }
   else
    for (int i=1;i<x.length;i++) {
      x[i] = ployRegressionAdjustment(x[0],x[i],degree);
      ds.setColumnData_1(i,x[i]);
    }


  }

  void jButton_getTraining_actionPerformed(ActionEvent e) {
    int [] selectedColum = jList_columnName.getSelectedIndices();
    if (selectedColum == null || selectedColum.length==0) return;
    float [][] x = new float[selectedColum.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(selectedColum[i]);
    }
     if (arrayCenter == null) {
       arrayCenter = new ArrayCenter[2];
       arrayCenter[0] = new ArrayCenter();
       arrayCenter[0].setArrayData(x);
       arrayCenter[0].setSelectedArray(selectedColum);
       Display.display("create array training center "+1);
     }
     else {
       int m = 0;
       while (arrayCenter[m] != null) {
         m++;
       }
       if (m < arrayCenter.length) {
         arrayCenter[m] = new ArrayCenter();
         arrayCenter[m].setArrayData(x);
         arrayCenter[m].setSelectedArray(selectedColum);
         Display.display("create array training center "+(m+1));
       }
       else {
         ArrayCenter [] ac = new ArrayCenter[arrayCenter.length*2];
         for (int i=0;i<arrayCenter.length;i++)
           ac[i] = arrayCenter[i];
         arrayCenter = ac;
         arrayCenter[m] = new ArrayCenter();
         arrayCenter[m].setArrayData(x);
         arrayCenter[m].setSelectedArray(selectedColum);
         Display.display("create array training center "+(m+1));
       }
     }
  }

  void jButton_distSelected_actionPerformed(ActionEvent e) {
    if (arrayCenter == null) {
      return;
    }
    int numCenter = arrayCenter.length;
    for (int i=0;i<numCenter;i++) {
      for (int j=0;j<numCenter;j++) {
          if (arrayCenter[i] != null && arrayCenter[j] != null && i<j) {
             msg.displayln("ArrayCenter "+i+" and "+ j +"distance=\t"+Correlation.Eucilean_distance(arrayCenter[i].centerX,arrayCenter[j].centerX));
            }
      }
    }
    int [] selectedColum = jList_columnName.getSelectedIndices();
    if (selectedColum == null || selectedColum.length==0) return;
    float [][] x = new float[selectedColum.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(selectedColum[i]);
     int m=0;
      while(arrayCenter[m] != null) {
        msg.display("Array "+selectedColum[i]+"\t"+columnName[selectedColum[i]]+"\tArrayCenter "+m+"\t");
          arrayCenter[m].distanceAnalysis(x[i]);
          displayResult(arrayCenter[m]);
          m++;
          if (m == arrayCenter.length) break;
      }
    }
  }
  private void displayResult(ArrayCenter ac) {
    msg.displayln("minDistIndex\t"+ac.minDistIndex+"\t"+columnName[ac.minDistIndex]+"\ttest_to_minDist\t" + ac.test_to_minDist+"\ttest_to_centerDist\t"
                    +ac.test_to_centerDist+"\tminDist_to_center\t"+ac.minDist_to_center
                    +"\tavg_of_test_to_minDist_and_test_to_centerDist\t"+(ac.test_to_minDist+ac.test_to_centerDist)*0.5f);
  }
  void jButton_distAll_actionPerformed(ActionEvent e) {
    if (arrayCenter == null) {
        return;
    }
    int total_number = jList_columnName.getComponentCount();
    int [] selectedColum = new int[total_number];
    for (int i=0;i<total_number;i++)
      selectedColum[i] = i;
    float [][] x = new float[selectedColum.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(selectedColum[i]);
     int m=0;
      while(arrayCenter[m] != null) {
        Display.display("Array "+i+"\t"+columnName[i]+"\tArrayCenter "+m);
          arrayCenter[m].distanceAnalysis(x[i]);
          displayResult(arrayCenter[m]);
      }
    }

  }

  void jButton_reset_actionPerformed(ActionEvent e) {
     arrayCenter = null;
  }

  void jMenuItem_rmaNormalization_actionPerformed(ActionEvent e) {

  }


  void jButton_polyPair_actionPerformed(ActionEvent e) {
    String str_bkg =  jTextField_polyDegree.getText();
    int degree = 2;
    try {
      degree = Integer.parseInt(str_bkg);
    }
    catch(NumberFormatException ee) {
      degree = 2;
    }
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }
   if (degree < 2) {
     for (int i=0;i<x.length-1;i=i+2) {
      x[i] = linearRegressionAdjustment(x[i+1],x[i]);
      ds.setColumnData_1(i,x[i]);
    }
   }
   else
    for (int i=0;i<x.length-1;i=i+2) {
      x[i] = ployRegressionAdjustment(x[i+1],x[i],degree);
      ds.setColumnData_1(i,x[i]);
    }

  }

  void jButton_MA_pair_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];

    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }
    for (int i=0;i<x.length-1;i=i+2) {
      x[i] = ma_adjustment(x[i+1],x[i]);
      ds.setColumnData_1(i,x[i]);
    }

  }
  void jButton_multiNorm_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
    }
    float mean_col_mean = Statistics.mean(x[0]);
    float std_col_mean = Statistics.stdDev(x[0]);
    msg.display("mean_col_mean : "+ mean_col_mean + "\nstandard deviation: "+ std_col_mean);
 //   ds.columnData_rescale(mean_col_mean);
 //   float [] rescaled_x;
    for (int i=0;i<x.length;i++) {
      columnData_rescale(x[i],mean_col_mean);
      ds.setColumnData_1(i,x[i]);
    }
  }

  void jButton_multiScale_actionPerformed(ActionEvent e) {
    if (columnName.length==1) return;
    float [][] x = new float[columnName.length][];
    float [] mean = new float[columnName.length];
    for (int i=0;i<x.length;i++) {
      x[i] = ds.getColumnData_1(i);
      mean[i] = Statistics.mean(x[i]);
      msg.display("\nmean_col : "+i +" mean= "+ mean[i] );
    }
    float mean_col_mean = Statistics.mean(mean);
    msg.display("\ngrand mean = "+ mean_col_mean );
    for (int i=0;i<x.length;i++) {
      columnData_rescale(x[i],mean[i],mean_col_mean);
      ds.setColumnData_1(i,x[i]);
    }

  }

  void jButton_2DPlot_histo_actionPerformed(ActionEvent e) {
    int percent = 10;
    try {
      percent = Integer.parseInt(jTextField_dataPointPercent.getText());
    }
    catch (NumberFormatException ee) {
      msg.display("Use all the data points.");
      percent=100;
    }
  //  msg.display("plot coloumn " + percent + "% data point.");
if (!dataPlot.isVisible())
  dataPlot.setVisible(true);
    //.getText();


//      msg.display("plot start...");
      if (xCol == -1 || yCol == -1) {
        msg.display("xCol=",xCol);
        msg.display("yCol=",yCol);
        return;
      }
      String [] label = ds.getColumnLabel_1(xCol);
      float [] x = ds.getColumnData_1(xCol);
      if (x == null)  {
        System.out.println("x column data is nou decimal.");
        return;
      }
      float [] y = ds.getColumnData_1(yCol);
      if (y == null)  {
        System.out.println("y column data is nou decimal.");
        return;
      }
 //     float [] y2 = null;
 //     if (y2Col != -1)
 //       y2 = ds.getColumnData_1(y2Col);

      if (percent < 100 && percent > 0) {
         int totalDataPoints = x.length;
         int percentDataPoint = (totalDataPoints*percent)/100;
         float [] xx = new float[percentDataPoint];
         float [] yy = new float[percentDataPoint];
         String [] labelXX = new String[percentDataPoint];
         for (int i=0;i<percentDataPoint;i++) {
           xx[i] = x[i];
           yy[i] = y[i];
           labelXX[i] = label[i];
         }
         x = xx;
         y = yy;
         label = labelXX;
      }
          dataPlot.plotXY_scatter_histoContour(x,y,columnName[xCol],columnName[yCol]);

  }
}