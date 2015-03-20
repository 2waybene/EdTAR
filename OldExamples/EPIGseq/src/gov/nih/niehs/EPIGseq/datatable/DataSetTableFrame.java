package gov.nih.niehs.EPIGseq.datatable;


import javax.swing.*;
import java.awt.*;
//import dependenceextraction.*;
import analysis.heatMap.*;
import analysis.bayesiananalysis.*;  //1
import analysis.principlecomponentanalysis.*;//2
import analysis.fuzzyartmap.*;//3
import analysis.lineardiscrimination.*;//4
import analysis.clustering.*;//5
import analysis.relevanceAnalysis.*;//6
import analysis.quantitativeSupervisedClassification.*;//7
import analysis.epig.*;//8
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import util.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
import myutility.plot2D.*;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class DataSetTableFrame extends JFrame {
  private DataSetTableModel dataSetTableModel;
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem_saveData = new JMenuItem();
  private JMenuItem jMenuItem_close = new JMenuItem();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private DataSetTable myTable1 = new DataSetTable();
  private JPopupMenu jPopupMenu1 = new JPopupMenu();
  private JMenuItem jMenuItem_internet = new JMenuItem();
  private int [] selectedRow,selectedCols;
  private int selectedCol=0;
//  private JMenuItem jMenuItem_plot = new JMenuItem();
//  private JPlot2DFrame plot;
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem_heatMap = new JMenuItem();
  private JMenuItem jMenuItem_clustering = new JMenuItem();
  private JMenuItem jMenuItem_PCA = new JMenuItem();
  private MessageBoard msg;
  private JMenuItem jMenuItem_geneID = new JMenuItem();
  private JMenuItem jMenuItem_plotSelectedColumn = new JMenuItem();
//  private DataSet dataSets;
  private JMenuItem jMenuItem_relevanceAnalysis = new JMenuItem();
  private JMenuItem jMenuItem_linearDiscrimination = new JMenuItem();
  private JMenuItem jMenuItem_fuzzyART = new JMenuItem();
  private JMenuItem jMenuItem_bayesianClassification = new JMenuItem();
  private JPopupMenu jPopupMenu2 = new JPopupMenu();
  private JMenuItem jMenuItem_removeUnmarked = new JMenuItem();
  private JMenuItem jMenuItem_loadList_conjunction = new JMenuItem();
  private JMenuItem jMenuItem_loadList_neg_conj = new JMenuItem();
  private JMenuItem jMenuItem_search = new JMenuItem();
  private JMenuItem jMenuItem_removeMarkedList = new JMenuItem();
  private boolean performSorting = false;
    private JMenuItem jMenuItem_DataSetClasification = new JMenuItem();
    private JMenu jMenu3 = new JMenu();
    private JMenuItem jMenuItem_scatter = new JMenuItem();
    private JMenu jMenu4 = new JMenu();
    private JMenuItem jMenuItem_sortDescending = new JMenuItem();
    private JMenuItem jMenuItem_sortAscending = new JMenuItem();
    private JMenuItem jMenuItem_saveFiles = new JMenuItem();
    private JMenu jMenu5 = new JMenu();
    private JMenuItem jMenuItem_plainPlot = new JMenuItem();
    private JMenuItem jMenuItem_bioRepPlot = new JMenuItem();
    private JMenuItem jMenuItem_cellLinePlot = new JMenuItem();

//  private boolean sortingOrderACSENDING=false;
  private GenericIntensityDataSetsFrameIO genericIntensityDataSetsFrameIO;
  private File file;
  private DataPlot dataPlot;
  public DataSetTableFrame() {

  }
  public DataSetTableFrame(File f, GenericIntensityDataSetsFrameIO _genericIntensityDataSetsFrameIO,MessageBoard _msg) {
    msg = _msg;
    file = f;
   genericIntensityDataSetsFrameIO =  _genericIntensityDataSetsFrameIO;
   dataSetTableModel =  new DataSetTableModel();

  }
  public DataSetTableFrame(DataPlot _plot,DataSet _ds, MessageBoard _msg) throws HeadlessException {
    msg = _msg;
    dataPlot = _plot;
    dataSetTableModel =  new DataSetTableModel(_ds,msg);
    myTable1.setModel(dataSetTableModel);
    tableSetup(myTable1);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (_ds.getGeneSetProperty() != null)
    setTitle("There are " + dataSetTableModel.getRowCount() + " "+_ds.getGeneSetProperty()+".");
    else
      setTitle("There are " + dataSetTableModel.getRowCount() + " rows.");

  }

  private void jbInit() throws Exception {
    jMenuItem_saveData.setText("Save Data");
    jMenuItem_saveData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_saveData_actionPerformed(e);
      }
    });
    jMenuItem_close.setText("Close");
    jMenuItem_close.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_close_actionPerformed(e);
      }
    });
    jScrollPane1.setPreferredSize(new Dimension(500, 500));
    jMenu1.setText("File");
//    myTable1.setColumnSelectionAllowed(true);
    jMenuItem_internet.setText("UniGene id");
    jMenuItem_internet.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_internet_actionPerformed(e);
      }
    });
    jMenu2.setText("Analysis");
    jMenuItem_heatMap.setText("Heat Map");
    jMenuItem_heatMap.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_heatMap_actionPerformed(e);
      }
    });
    jMenuItem_clustering.setText("Clustering");
    jMenuItem_clustering.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_clustering_actionPerformed(e);
      }
    });
    jMenuItem_PCA.setText("PCA");
    jMenuItem_PCA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_PCA_actionPerformed(e);
      }
    });
    jMenuItem_geneID.setText("GenBank id");
    jMenuItem_geneID.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_geneID_actionPerformed(e);
      }
    });
    jMenuItem_plotSelectedColumn.setText("Plot Selected Columns");
    jMenuItem_plotSelectedColumn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plotSelectedColumn_actionPerformed(e);
      }
    });
    jMenuItem_fuzzyART.setText("Fuzzy Art Map");
    jMenuItem_fuzzyART.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_fuzzyART_actionPerformed(e);
      }
    });
    jMenuItem_linearDiscrimination.setText("Linear Discrimination");
    jMenuItem_linearDiscrimination.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_linearDiscrimination_actionPerformed(e);
      }
    });
    jMenuItem_relevanceAnalysis.setText("Relevance Analysis");
    jMenuItem_relevanceAnalysis.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_relevanceAnalysis_actionPerformed(e);
      }
    });
    jMenuItem_bayesianClassification.setText("Bayesian Classification");
    jMenuItem_bayesianClassification.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_bayesianClassification_actionPerformed(e);
      }
    });
    jMenuItem_loadList_conjunction.setBackground(Color.lightGray);
    jMenuItem_loadList_conjunction.setText("Load a List - Conjunction");
    jMenuItem_loadList_conjunction.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadList_conjunction_actionPerformed(e);
      }
    });
    jMenuItem_removeUnmarked.setBackground(new Color(220, 220, 220));
    jMenuItem_removeUnmarked.setToolTipText("");
    jMenuItem_removeUnmarked.setText("Remove Color Marked List");
    jMenuItem_removeUnmarked.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_removeUnmarked_actionPerformed(e);
      }
    });
    jMenuItem_loadList_neg_conj.setBackground(new Color(220, 220, 220));
    jMenuItem_loadList_neg_conj.setText("Load a List - Neg Conjunction");
    jMenuItem_loadList_neg_conj.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadList_neg_conj_actionPerformed(e);
      }
    });
    jMenuItem_search.setBackground(Color.lightGray);
    jMenuItem_search.setText("Find");
    jMenuItem_search.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_search_actionPerformed(e);
      }
    });
    jMenuItem_removeMarkedList.setBackground(Color.lightGray);
    jMenuItem_removeMarkedList.setText("Remove Unmarked List");
    jMenuItem_removeMarkedList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_removeMarkedList_actionPerformed(e);
      }
    });
    jMenuItem_DataSetClasification.setText("Unknown Data Set (Column) Classification");
    jMenuItem_DataSetClasification.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_DataSetClasification_actionPerformed(e);
      }
    });
    jMenu3.setText("Plot");
    jMenuItem_scatter.setText("Column Plot");
    jMenuItem_scatter.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_scatter_actionPerformed(e);
      }
    });
    jMenu4.setBackground(new Color(220, 220, 220));
    jMenu4.setText("Sorting");
    jMenuItem_sortAscending.setBackground(Color.lightGray);
    jMenuItem_sortAscending.setText("Ascending");
    jMenuItem_sortAscending.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sortAscending_actionPerformed(e);
      }
    });
    jMenuItem_sortDescending.setBackground(new Color(220, 220, 220));
    jMenuItem_sortDescending.setText("Descending");
    jMenuItem_sortDescending.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sortDescending_actionPerformed(e);
      }
    });
    jMenuItem_saveFiles.setText("Save in Seperate Files");
    jMenuItem_saveFiles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_saveFiles_actionPerformed(e);
      }
    });
    jMenu5.setText("Row Plot");
    jMenuItem_cellLinePlot.setText("CellLine Plot");
    jMenuItem_cellLinePlot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLinePlot_actionPerformed(e);
      }
    });
    jMenuItem_bioRepPlot.setText("BioReplicate Plot");
    jMenuItem_bioRepPlot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_bioRepPlot_actionPerformed(e);
      }
    });
    jMenuItem_plainPlot.setText("Plain Plot");
    jMenuItem_plainPlot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plainPlot_actionPerformed(e);
      }
    });
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenuBar1.add(jMenu3);
    jMenu1.add(jMenuItem_saveData);
    jMenu1.add(jMenuItem_saveFiles);
    jMenu1.add(jMenuItem_close);
    this.setJMenuBar(jMenuBar1);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(myTable1, null);
    jPopupMenu1.add(jMenuItem_geneID);
    jPopupMenu1.add(jMenuItem_internet);
    jPopupMenu1.add(jMenuItem_plotSelectedColumn);
    jPopupMenu1.add(jMenu5);
    jMenu2.add(jMenuItem_heatMap);
    jMenu2.add(jMenuItem_clustering);
    jMenu2.add(jMenuItem_PCA);
    jMenu2.add(jMenuItem_fuzzyART);
    jMenu2.add(jMenuItem_linearDiscrimination);
    jMenu2.add(jMenuItem_relevanceAnalysis);
    jMenu2.add(jMenuItem_bayesianClassification);
    jMenu2.add(jMenuItem_DataSetClasification);
    jPopupMenu2.add(jMenuItem_loadList_conjunction);
    jPopupMenu2.add(jMenuItem_loadList_neg_conj);
    jPopupMenu2.add(jMenuItem_removeMarkedList);
    jPopupMenu2.add(jMenuItem_removeUnmarked);
    jPopupMenu2.add(jMenuItem_search);
    jPopupMenu2.add(jMenu4);
    jMenu3.add(jMenuItem_scatter);
    jMenu4.add(jMenuItem_sortAscending);
    jMenu4.add(jMenuItem_sortDescending);
    jMenu5.add(jMenuItem_cellLinePlot);
    jMenu5.add(jMenuItem_bioRepPlot);
    jMenu5.add(jMenuItem_plainPlot);
    setBounds(0, 0, 600, 600);
    setIconImage(FileIO.imageIcon.getImage());
    pack();
  }
  private void tableSetup(JTable table) {
    table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    table.setPreferredScrollableViewportSize(new java.awt.Dimension(600, 350));
    table.setAutoCreateColumnsFromModel(false);
    table.setCellSelectionEnabled(true);
    table.setColumnSelectionAllowed(true);
    table.setGridColor(Color.blue);
    table.setRowSelectionAllowed(true);
    table.setSelectionBackground(Color.gray);
    table.setCellSelectionEnabled(true);
    TableColumnModel tcm = table.getColumnModel();
    tcm.setColumnSelectionAllowed(true);
    ColoredTableCellRendererClassIO renderer, headRenderer;
    DataSetTableModel model = (DataSetTableModel)table.getModel();
    Dimension d = this.getPreferredSize();
    ColumnNameClassIO []  cnc = model.getColumnNameClasses();
    float width = (float)d.getWidth()/model.getColumnCount();
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
        if (colWidth < (int)width) colWidth = (int)width;
        column.setPreferredWidth(colWidth);
    }

    myTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//        public void mousePressed(java.awt.event.MouseEvent evt) {
//          tableFormMousePressed(evt);
//        }
//        public void mouseReleased(java.awt.event.MouseEvent evt) {
//          tableFormMouseReleased(evt);
//        }
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          tableFormMouseClicked(evt);
        }
      }
      );


    JTableHeader header = myTable1.getTableHeader();
    header.setUpdateTableInRealTime(true);
    header.setReorderingAllowed(false);
    header.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
          headerformMousePressed(evt);
        }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        headerformMouseReleased(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        headerformMouseClicked(evt);
      }
    }
    );
    table.validate();
    table.repaint();

  }

  private void headerformMouseClicked(java.awt.event.MouseEvent evt) {
    // Add your handling code here:
 //   jPopupMenu_showColumnHint.setVisible(false);
    myTable1.clearSelection();
    if(SwingUtilities.isLeftMouseButton(evt)){
       TableColumnModel colModel = myTable1.getColumnModel();
       JTableHeader t=myTable1.getTableHeader();
       Point p = new Point();
       p.setLocation(evt.getX(),evt.getY());
      int columnModelIndex = t.columnAtPoint(p);
      selectedCol = columnModelIndex;
       jPopupMenu2.show( myTable1, evt.getX(), evt.getY() );
    }
    else if (SwingUtilities.isRightMouseButton(evt)) {
 //     TableColumnModel colModel = myTable1.getColumnModel();
 //    int columnModelIndex = colModel.getColumnIndexAtX(evt.getX());
 //     selectedCol = colModel.getColumn(columnModelIndex).getModelIndex();
 //     jPopupMenu_search.show( myTable1, evt.getX(), evt.getY() );
    }

  }
  private void headerformMousePressed(java.awt.event.MouseEvent evt) {
  }
  private void headerformMouseReleased(java.awt.event.MouseEvent evt) {
//    jPopupMenu_columnNameList.setVisible(false);
//    jPopupMenu_search.setVisible(false);
  }
  private void tableFormMouseClicked(java.awt.event.MouseEvent evt) {
    // Add your handling code here:

    if (SwingUtilities.isRightMouseButton(evt)) {
      selectedRow = myTable1.getSelectedRows();
      selectedCols = myTable1.getSelectedColumns();
      jPopupMenu1.show( myTable1, evt.getX(), evt.getY() );
    }
    else {
      selectedRow = null;
      selectedCols = null;
    }


    if(SwingUtilities.isLeftMouseButton(evt)){
    }
    else if (SwingUtilities.isRightMouseButton(evt)) {
    }

  }
//  private void tableFormMousePressed(java.awt.event.MouseEvent evt) {
//   System.out.println("formMouseClicked");
//  }
//  private void tableFormMouseReleased(java.awt.event.MouseEvent evt) {
//    System.out.println("formMouseReleased");
//  }


  void jMenuItem_saveData_actionPerformed(ActionEvent e) {
    saveData();
  }
  public DataSetTable getTable() {
    return myTable1;
  }
  private void saveData() {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.saveData(FileIO.saveSingleFile());
  }
  void jMenuItem_close_actionPerformed(ActionEvent e) {
    dispose();
  }
  void jMenuItem_internet_actionPerformed(ActionEvent e) {
    if (selectedRow == null) return;
    if (selectedCols == null) return;
   // StringTokenizer parser;
    String url_1;
    int num=selectedRow.length;
    for (int i=0;i<num;i++) {
     url_1= (String)myTable1.getValueAt(selectedRow[i],selectedCols[0]);
     if (url_1!=null)
      Display.ie(url_1);
    }
    selectedRow = null;
    selectedCols = null;
  }



  void jMenuItem_heatMap_actionPerformed(ActionEvent e) {
    heatMap();
  }
  private void heatMap() {
   new HeatMapFrame(dataSetTableModel.getDataSet().getColumnInfo(),dataSetTableModel.getDataSet().getRowInfo(),
               dataSetTableModel.getDataSet().getValues(),msg,dataPlot).setVisible(true);
  }

  void jMenuItem_PCA_actionPerformed(ActionEvent e) {
    new PCAFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  void jMenuItem_clustering_actionPerformed(ActionEvent e) {
    new HierarchicalClusteringFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  public static void main(String[] args) throws HeadlessException {
    new DataSetTableFrame().setVisible(true);
  }

  void jMenuItem_geneID_actionPerformed(ActionEvent e) {

    if (selectedRow == null) return;
    if (selectedCols == null) return;
    StringTokenizer parser;
    String url_1,url_2;
    int num = 5;
    if (num > selectedRow.length) num=selectedRow.length;
    for (int i=0;i<num;i++) {
      url_1 = ((ColorStringClassIO)myTable1.getValueAt(selectedRow[i],selectedCols[0])).toString();
      if (url_1!=null)
        Display.GenBank_id(url_1);
//      try {
//        Runtime runtime = Runtime.getRuntime();
//        String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=nucleotide&cmd=search&term="+url_1;
//      http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=nucleotide&cmd=search&term=AI059504
//        Process process = runtime.exec(myURL);
//      }
//      catch (IOException ee) {
//        System.out.println(ee.toString());
//      }

    }
    selectedRow = null;
    selectedCols = null;


  }

  void jMenuItem_plotSelectedColumn_actionPerformed(ActionEvent e) {
    if (selectedCols == null) return;
    dataSetTableModel.plotColumn(selectedCols);

  }

  void jMenuItem_fuzzyART_actionPerformed(ActionEvent e) {
//    GenericDataTableModelIO tm = (GenericDataTableModelIO)jTable1.getModel();
//    DataSetsIO ds = tm.getDataSets();
//    if (ds == null) return;
//    new FuzzyARTMAPFrame(ds, msg).show();

  if (dataSetTableModel.getDataSet().getRepRow() != -1)
    new FuzzyARTMAPFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  void jMenuItem_linearDiscrimination_actionPerformed(ActionEvent e) {
    if (dataSetTableModel.getDataSet().getRepRow() != -1)
    new LinearDiscriminationFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  void jMenuItem_relevanceAnalysis_actionPerformed(ActionEvent e) {
    if (dataSetTableModel.getDataSet().getRepRow() != -1)
    new RelevanceAnalysisFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  void jMenuItem_bayesianClassification_actionPerformed(ActionEvent e) {
      new BayesianAnalysisFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }

  void jMenuItem_eucledianClustering_actionPerformed(ActionEvent e) {
    new HierarchicalClusteringFrame(dataSetTableModel.getDataSet(), msg,dataPlot).setVisible(true);
  }



  void jMenuItem_loadList_neg_conj_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    String [] str = FileIO.loadData(0,"\t");
    int [] index = tm.getNegConjunctionList(selectedCol,str);
    tm.updateStringSearch(selectedCol,index);
    myTable1.repaint();
//    updateStringSearch(SearchStringFrameIO.getNegConjunctionList(FileIO.loadData(selectedCol),
//        this,selectedCol,msg));
  }

  void jMenuItem_loadList_conjunction_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    String [] str = FileIO.loadData(0,"\t");
    int [] index = tm.getConjunctionList(selectedCol,str);
    tm.updateStringSearch(selectedCol,index);
    myTable1.repaint();

//    updateStringSearch(SearchStringFrameIO.getConjunctionList(FileIO.loadData(0),
//        this,selectedCol,msg));
  }

  void jMenuItem_removeUnmarked_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.removeUnmarked(selectedCol);
    myTable1.repaint();
  }

  void jMenuItem_search_actionPerformed(ActionEvent e) {
    new SearchStringFrameIO(this,selectedCol,msg).setVisible(true);
  }
  public void updateStringSearch(int [] findIndex) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    int totalRowNum = tm.getRowCount();
    Object obj;
    ColorStringClassIO csc;
    for (int i=0;i<totalRowNum;i++) {
      csc = (ColorStringClassIO)tm.getValueAt(i,selectedCol);
      csc.setColor(Color.black);
    }
    if (findIndex == null) {
      myTable1.repaint();
      return;
    }
    for (int i=0;i<findIndex.length;i++) {
      csc = (ColorStringClassIO)tm.getValueAt(findIndex[i],selectedCol);
      csc.setColor(Color.red);
    }
    myTable1.repaint();
  }
  public void initialStringSearch() {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    int totalRowNum = tm.getRowCount();
    Object obj;
    ColorStringClassIO csc;
    for (int i=0;i<totalRowNum;i++) {
      csc = (ColorStringClassIO)tm.getValueAt(i,selectedCol);
      if (csc != null)
        csc.setColor(Color.black);
    }

  }

  void jMenuItem_removeMarkedList_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.removeMarked(selectedCol);
    myTable1.repaint();

  }
/*
  void jMenuItem_sorting_actionPerformed(ActionEvent e) {
    performSorting = true;
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.sorting(selectedCol,sortingOrderACSENDING);
    tm.fireTableDataChanged();
    if (sortingOrderACSENDING) {
    sortingOrderACSENDING = false;
    }
    else {
      sortingOrderACSENDING=true;
    }

  }
  */
//  private JMenuItem jMenuItem_heatmap2 = new JMenuItem();

  void jMenuItem_DataSetClasification_actionPerformed(ActionEvent e) {
      dataSetClassification();
  }
  private void dataSetClassification() {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
     DataSet ds = tm.getDataSet();
    if ( ds.getMergedColumn() != null) {
      ds.dataSetClassification();
    }
  }

  void jMenuItem_scatter_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
     DataSet ds = tm.getDataSet();
      new AffyDataSVNFrame(dataPlot,ds,msg).setVisible(true);
  }


  void jMenuItem_sortAscending_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.sorting(selectedCol,true);
    tm.fireTableDataChanged();
    performSorting = true;

  }

  void jMenuItem_sortDescending_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    tm.sorting(selectedCol,false);
    tm.fireTableDataChanged();
    performSorting = true;

  }

  void jMenuItem_saveFiles_actionPerformed(ActionEvent e) {
    DataSetTableModel tm = (DataSetTableModel)myTable1.getModel();
    if (   ! performSorting )
      tm.saveDataInEachCat();
  }

  void jMenuItem_cellLinePlot_actionPerformed(ActionEvent e) {
    if (selectedRow != null)
    dataSetTableModel.plotRow(selectedRow, DataSet.cellLineProfilePlot);

  }

  void jMenuItem_bioRepPlot_actionPerformed(ActionEvent e) {
    dataSetTableModel.plotRow(selectedRow, DataSet.bioRepPlot);

  }

  void jMenuItem_plainPlot_actionPerformed(ActionEvent e) {
    dataSetTableModel.plotRow(selectedRow, DataSet.samplePlot);

  }




}