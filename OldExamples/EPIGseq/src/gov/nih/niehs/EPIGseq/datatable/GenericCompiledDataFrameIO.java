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
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class GenericCompiledDataFrameIO extends JFrame {
  private int startRow=-1,startCol=-1,celllineNameRow=-1,celllineProfileRow=-1,
          sampleNameRow=0,bioReplicateRow=-1,techReplicateRow=-1,labelingRow=-1;
  private GenericTable myTable1;


  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu_File = new JMenu();
  private JMenuItem jMenuItem_close = new JMenuItem();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel_table = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JMenuItem jMenuItem_saveData = new JMenuItem();
  private JLabel jLabel_dyeSwap = new JLabel();
  private JTextField jTextField_dyeSwap = new JTextField();
  private JMenu jMenu_uitility = new JMenu();
  private JLabel jLabel_bioGroupRow = new JLabel();
  private JLabel jLabel_repProfileRow = new JLabel();
  private JTextField jTextField_bioGroupRow = new JTextField();
  private JTextField jTextField_repProfileRow = new JTextField();
  private JMenuItem jMenuItem_minusOp = new JMenuItem();
  private JLabel jLabel1 = new JLabel();
  private JTextField jTextField_techRep = new JTextField();
  private JMenuItem jMenuItem_removeBlackRow = new JMenuItem();
  private JMenuItem jMenuItem_removeColorRow = new JMenuItem();
  private JMenuItem jMenuItem_loadDataNegConjection = new JMenuItem();
  private JMenuItem jMenuItem_loadDataConjunction = new JMenuItem();
  private JMenuItem jMenuItem_expConversion = new JMenuItem();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JPopupMenu jPopupMenu_columnNameList = new JPopupMenu();
//  private JMenuItem jMenuItem_dyeSwapHistogram = new JMenuItem();
  private int selectedCol=0, previousCol = 0;
  private JTextField popupText;
  private TitledBorder titledBorder1;
  private JLabel jLabel_startLine = new JLabel();
  private JTextField jTextField_startLine = new JTextField();
  private JPopupMenu jPopupMenu_cellTextSelection = new JPopupMenu();
  private JMenuItem jMenuItem_startLineText = new JMenuItem();
  private JMenuItem jMenuItem_startColumn = new JMenuItem();
  private JLabel jLabel_startColumn = new JLabel();
  private JTextField jTextField_startColumn = new JTextField();
  private ColoredTableCellRendererClassIO [] renderer;
  private MessageBoard msg;
  private File f;
  private String filename,fileName_wo_ext;
  private JPopupMenu jPopupMenu_search = new JPopupMenu();
  private JMenuItem jMenuItem_search = new JMenuItem();
  private SearchStringFrameIO searchStringFrame;
  private JMenuItem jMenuItem_logConversion = new JMenuItem();
  private DecimalFormat df = new DecimalFormat("0.000000");
  private TreeSet conversionColumn = new TreeSet();
  private JMenuItem jMenuItem_sampleName = new JMenuItem();
  private JMenuItem jMenuItem_replicate = new JMenuItem();
  private JLabel jLabel_SampleName = new JLabel();
  private JTextField jTextField_sampleName = new JTextField();
  private JLabel jLabel_replicate = new JLabel();
  private JTextField jTextField_replicate = new JTextField();
//  private JTextField jTextField_r_threshold = new JTextField();
  private JMenu jMenu_analysis = new JMenu();
  private JMenuItem jMenuItem_heatMap = new JMenuItem();
  private JMenuItem jMenuItem_clustering = new JMenuItem();
  private JMenuItem jMenuItem_PCA = new JMenuItem();
  private JMenuItem jMenuItem_fuzzyART = new JMenuItem();
  private JMenuItem jMenuItem_bayesianClassification = new JMenuItem();
  private JMenuItem jMenuItem_relevanceAnalysis = new JMenuItem();
  private JMenuItem jMenuItem_linearDiscrimination = new JMenuItem();
  private JMenuItem jMenuItem_geneSelectionCategorization = new JMenuItem();
  private JMenuItem jMenuItem_sameGeneMerge = new JMenuItem();
  private int xSzie = 600, ySize = 680;
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem_cellLineRow = new JMenuItem();
  private JMenuItem jMenuItem_cellLineProfileRow = new JMenuItem();
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem_techReplicate = new JMenuItem();
  private JMenuItem jMenuItem_dyeSwapRow = new JMenuItem();
  private JMenu jMenu3 = new JMenu();
//  private JMenuItem jMenuItem_undoRemoveRow = new JMenuItem();
  private JMenuItem jMenuItem_DeleteRow = new JMenuItem();
  private JMenuItem jMenuItem_insertRow = new JMenuItem();
  private JMenu jMenu5 = new JMenu();
  private JMenuItem jMenuItem_uniGene_id = new JMenuItem();
  private JMenuItem jMenuItem_genBank_id = new JMenuItem();
  private JMenu jMenu6 = new JMenu();
  private JMenuItem jMenuItem_sort_descending = new JMenuItem();
  private JMenuItem jMenuItem_sort_ascending = new JMenuItem();
  private JMenu jMenu7 = new JMenu();
  private JMenuItem jMenuItem_dyeDeviation = new JMenuItem();
  private JMenuItem jMenuItem_dyeSwapCorrectionTechRepOnly = new JMenuItem();
  private ButtonGroup plotOptionGroup = new ButtonGroup();
  private JMenu jMenu10 = new JMenu();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem_cellLineCorrection = new JMenuItem();
  private JMenu jMenu11 = new JMenu();
  private JMenuItem jMenuItem_log2ConversionAllCol = new JMenuItem();
  private JMenuItem jMenuItem_exp2conversionAllCol = new JMenuItem();
//  private JMenuItem jMenuItem_randomTest = new JMenuItem();
  private JMenuItem jMenuItem_load_cellCycle_comp = new JMenuItem();
  private JMenuItem jMenuItem_cellLineNorm = new JMenuItem();
  private JMenu jMenu12 = new JMenu();
  private JMenuItem jMenuItem_replicateAverageStd = new JMenuItem();
//  private JMenuItem jMenuItem_normalDistribution = new JMenuItem();
  private DataPlot dataPlot;
  private JMenu jMenu13 = new JMenu();
  private JMenuItem jMenuItem_plotRow = new JMenuItem();
  private JMenuItem jMenuItem_plotReplicate = new JMenuItem();
  private JMenuItem jMenuItem_plotCellLine = new JMenuItem();
  private JMenuItem jMenuItem_markInvalidRow = new JMenuItem();
  private JMenu jMenu9 = new JMenu();
  private JMenuItem jMenuItem_helpNote = new JMenuItem();
  private JMenuItem jMenuItem_negation_AllCol = new JMenuItem();
  private JMenu jMenu14 = new JMenu();
  private JMenuItem jMenuItem_loadFirstSets = new JMenuItem();
  private JMenuItem jMenuItem_secondDataSets = new JMenuItem();
  private JMenuItem jMenuItem_saveTranspose = new JMenuItem();
  private JMenu jMenu15 = new JMenu();
  private JMenuItem jMenuItem_techRep_diff_avg = new JMenuItem();
  private JMenuItem jMenuItem_normalRandomGenerator = new JMenuItem();
  private JMenuItem jMenuItem_replaceZeroByNormalDeviates = new JMenuItem();
  private JMenuItem jMenuItem_average = new JMenuItem();
  private JMenuItem jMenuItem_stats = new JMenuItem();
  private JMenuItem jMenuItem_replicateMerge = new JMenuItem();
  private JMenu jMenu16 = new JMenu();
  private JMenuItem jMenuItem_loadDataSets = new JMenuItem();
  private JMenuItem jMenuItem_columnCorrelation = new JMenuItem();
  private JMenuItem jMenuItem_row_t_Test = new JMenuItem();
//  private JMenuItem jMenuItem_row_f_test = new JMenuItem();
  private JMenuItem jMenuItem_row_paired_sample_t_test = new JMenuItem();
  private JMenuItem jMenuItem_row_ANOVA = new JMenuItem();
  private JMenuItem jMenuItem_anova_absolute = new JMenuItem();
  private JMenuItem jMenuItem_profileSNR = new JMenuItem();
  private JMenuItem jMenuItem_dyeBiasAnalysis = new JMenuItem();
  private JMenuItem jMenuItem_highlightZero = new JMenuItem();
  private JMenuItem jMenuItem_replcaceInvalidDataByItsAverage = new JMenuItem();
  private JMenuItem jMenuItem_sort_deci_asce = new JMenuItem();
  private JMenuItem jMenuItem_sort_deci_desc = new JMenuItem();
  private JMenuItem jMenuItem_geneName = new JMenuItem();
  private JMenuItem jMenuItem_cellLineAverage = new JMenuItem();
//  private JMenuItem jMenuItem_errorType_I = new JMenuItem();
//  private JMenuItem jMenuItemerrorType_II = new JMenuItem();
  private JMenuItem jMenuItem_profileAnalysis = new JMenuItem();
  private JMenuItem jMenuItem_FDR = new JMenuItem();
  private JMenuItem jMenuItem_power = new JMenuItem();
  private JMenuItem jMenuItem_normalDeviateP = new JMenuItem();
  private JMenuItem jMenuItem_rowDataNorm = new JMenuItem();
  private JMenuItem jMenuItem_avg_selectedRows = new JMenuItem();
  private JMenuItem jMenuItem_subtractRepAvg = new JMenuItem();
  private JMenuItem jMenuItem_getIntersectionList = new JMenuItem();
  private JMenuItem jMenuItem_rowData_sub_avg = new JMenuItem();
  private JMenuItem jMenuItem_columnPlot = new JMenuItem();
  private JMenu jMenu8 = new JMenu();
  private JCheckBoxMenuItem jCheckBoxMenuItem_blkwht = new JCheckBoxMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem_yLabel = new JCheckBoxMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem_xLabel = new JCheckBoxMenuItem();
  private JMenuItem jMenuItem_subtractFirstRepavg = new JMenuItem();
  private JMenuItem jMenuItem_dyeSwapMerged = new JMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem_SNR_relativeChange = new JCheckBoxMenuItem();
  private JMenuItem jMenuItem_plotThumbnails_rep = new JMenuItem();
  private JMenuItem jMenuItem_thumbnails = new JMenuItem();
  private JMenuItem jMenuItem_thumbnail_cellline = new JMenuItem();
  private JMenuItem jMenuItem_reconstitueCellcycle = new JMenuItem();
  private JMenuItem jMenuItem_wg_Correlation = new JMenuItem();
  private JMenuItem jMenuItem_sameGeneCount = new JMenuItem();
  private JMenuItem jMenuItem_withingroup_avg = new JMenuItem();
  private JMenuItem jMenuItem_TF_correlationTest = new JMenuItem();
  private JMenuItem jMenuItem_resetRowAssignment = new JMenuItem();
  private JMenuItem jMenuItem_equal_between_group_avg = new JMenuItem();
  private JMenuItem jMenuItem_ScatterPlot_w_errorBar = new JMenuItem();
  private JMenuItem jMenuItem_Barplot_w_errorBar = new JMenuItem();
  private JMenuItem jMenuItem_peplicatescatterPlotwithError_2 = new JMenuItem();
  private JMenuItem jMenuItem_barplot_with_error_2 = new JMenuItem();
  private JMenuItem jMenuItem_twoWayAnova = new JMenuItem();
  private JMenuItem jMenuItem_highlight_missingValue = new JMenuItem();
  private JMenuItem jMenuItem_factor3 = new JMenuItem();
  private JMenuItem jMenuItem_threewayANOVA = new JMenuItem();
  private JMenuItem jMenuItem_output_MarkedData = new JMenuItem();
  private JMenuItem jMenuItem_output_BlackMarkedData = new JMenuItem();
  private JMenuItem jMenuItem_mergeFiles_to_one = new JMenuItem();
  private JMenuItem jMenuItem_derivative = new JMenuItem();
  private JMenuItem jMenuItem_logisticConversion = new JMenuItem();
  private JMenuItem jMenuItem_derivativeMulptiFile = new JMenuItem();
  private JMenuItem jMenuItem_insertData = new JMenuItem();
  private JMenuItem jMenuItem_logisticFitting = new JMenuItem();
  private JMenuItem jMenuItem_rowDerivative = new JMenuItem();
  private JMenuItem jMenuItem_rowLogisticConversion = new JMenuItem();
  private JMenuItem jMenuItem_deglitch = new JMenuItem();
  private JMenuItem jMenuItem_withingroupVarianceNorm = new JMenuItem();
  private JMenu jMenu_test = new JMenu();
  private JMenu jMenu_factorAnalysis = new JMenu();
  private JMenu jMenu_statisticalProcessing = new JMenu();
  private JMenu jMenu_numericalProcessing = new JMenu();
  private JMenuItem jMenuItem_contain_one_ls = new JMenuItem();
  private JMenuItem jMenuItem_containg_all_ls = new JMenuItem();
//  private JMenuItem jMenuItem_car_norm_test = new JMenuItem();
  private JMenuItem jMenuItem_profileSimilarity = new JMenuItem();
  private JMenuItem jMenuItem_celllinealignmenttofirstshamaverage = new JMenuItem();
  private JMenuItem jMenuItem_pairWiseCorrelation = new JMenuItem();
  private JMenuItem jMenuItem1 = new JMenuItem();

  public GenericCompiledDataFrameIO(File file, String fileExtension,MessageBoard _msg, DataPlot _dataPlot) throws HeadlessException {
    FileIO.GenericCompiledDataFrameIO = datatable.GenericCompiledDataFrameIO.class.getResource("GenericCompiledDataFrameIO.txt");
    msg = _msg;
    dataPlot = _dataPlot;
    f = file;
    filename = f.getName();
    java.util.StringTokenizer token = new java.util.StringTokenizer(filename,".");
    fileName_wo_ext = token.nextToken();
  //  FileIO tt = new FileIO();
    String [][] s;
    if (fileExtension.equals("xls")) {
      s = read_excel(f,"Sheet1");
      if(s!=null)
      setTableData(s);
//    setTableData(new FileIO().read_excel_1(f,"Sheet1"));
    }
      else if (fileExtension.equals("csv"))
        setTableData(FileIO.read_csv(f));
       else
       setTableData(FileIO.read_txt(f,"\t"));
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (FileIO.imageIcon != null)
      setIconImage(FileIO.imageIcon.getImage());
    setBounds(0, 0, xSzie, ySize);
    exitConfirm();
  }
  public GenericCompiledDataFrameIO(String [][] str, MessageBoard _msg, DataPlot _dataPlot) throws HeadlessException {
    FileIO.GenericCompiledDataFrameIO = datatable.GenericCompiledDataFrameIO.class.getResource("GenericCompiledDataFrameIO.txt");
    msg = _msg;
    dataPlot = _dataPlot;
    setTableData(str);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (FileIO.imageIcon != null)
      setIconImage(FileIO.imageIcon.getImage());
    setBounds(0, 0, xSzie, ySize);
    exitConfirm();
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
    jMenuItem_close.setText("Close");
    jMenuItem_close.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_close_actionPerformed(e);
      }
    });
    jMenu_File.setText("File");


    jMenuItem_search.setBackground(new Color(220, 220, 220));
    jMenuItem_search.setBorder(null);
    jMenuItem_search.setText("String Find over Selected Column");
    jMenuItem_search.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_search_actionPerformed(e);
      }
    });
    jMenuItem_startColumn.setBorder(null);

    jTextField_startColumn.setPreferredSize(new Dimension(50, 25));
    jTextField_startColumn.setText("-1");
   if (f != null)
    setTitle(f.getName());
    jMenuItem_logConversion.setBackground(Color.lightGray);
    jMenuItem_logConversion.setBorder(null);
    jMenuItem_logConversion.setText("Log2 Converstion");
    jMenuItem_logConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_logConversion_actionPerformed(e);
      }
    });
    jMenuItem_sampleName.setBackground(new Color(220, 220, 220));
    jMenuItem_sampleName.setBorder(null);
    jMenuItem_sampleName.setToolTipText("");
    jMenuItem_sampleName.setText("Sample Name Row");
    jMenuItem_sampleName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sampleName_actionPerformed(e);
      }
    });
    jMenuItem_replicate.setBackground(Color.lightGray);
    jMenuItem_replicate.setBorder(null);
    jMenuItem_replicate.setToolTipText("");
    jMenuItem_replicate.setText("Replicate Row");
    jMenuItem_replicate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_replicate_actionPerformed(e);
      }
    });
    jLabel_SampleName.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_SampleName.setText("Sample Name Row (size): ");
    jTextField_sampleName.setPreferredSize(new Dimension(50, 25));
    jTextField_sampleName.setText("0");
    jLabel_replicate.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_replicate.setText("Bio Replicate Row(n above threshold): ");
    jTextField_startLine.setPreferredSize(new Dimension(50, 25));
    jTextField_replicate.setPreferredSize(new Dimension(50, 25));
    jTextField_replicate.setText("-1");
    jMenu_analysis.setText("Analysis");
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


    jMenuItem_geneSelectionCategorization.setText("EPIG");
    jMenuItem_geneSelectionCategorization.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_geneSelectionCategorization_actionPerformed(e);
      }
    });
    jMenuItem_sameGeneMerge.setBackground(Color.lightGray);
    jMenuItem_sameGeneMerge.setBorder(null);
    jMenuItem_sameGeneMerge.setText("Data Merge over Same Name Genes");
    jMenuItem_sameGeneMerge.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sameGeneMerge_actionPerformed(e);
      }
    });
    jPanel_table.setLayout(gridBagLayout3);
    myTable1.setColumnSelectionAllowed(true);
    jMenuItem_saveData.setText("Save Data");
    jMenuItem_saveData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_saveData_actionPerformed(e);
      }
    });
    jLabel_dyeSwap.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_dyeSwap.setText("Cy3/Cy5 Label Row(r threshold): ");
    jTextField_dyeSwap.setText("-1");
    jMenu_uitility.setText("Data");
    jLabel_bioGroupRow.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_bioGroupRow.setText("Cell Line Name Row: ");
    jLabel_repProfileRow.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_repProfileRow.setText("Cell Line Profile Row: ");
    jTextField_bioGroupRow.setText("-1");
    jTextField_repProfileRow.setText("-1");
    jMenuItem_minusOp.setBackground(new Color(220, 220, 220));
    jMenuItem_minusOp.setText("Minus Conversion");
    jMenuItem_minusOp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_minusOp_actionPerformed(e);
      }
    });
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Tech Replicate Row (column number): ");
    jTextField_techRep.setText("-1");
    jMenuItem_loadDataConjunction.setBackground(Color.lightGray);
    jMenuItem_loadDataConjunction.setText("Load Data Conjunction");
    jMenuItem_loadDataConjunction.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadDataConjunction_actionPerformed(e);
      }
    });
    jMenuItem_loadDataNegConjection.setBackground(new Color(220, 220, 220));
    jMenuItem_loadDataNegConjection.setText("Load Data Neg. Conjunction");
    jMenuItem_loadDataNegConjection.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadDataNegConjection_actionPerformed(e);
      }
    });
    jMenuItem_removeColorRow.setBackground(Color.lightGray);
    jMenuItem_removeColorRow.setText("Remove Color Marked in Row");
    jMenuItem_removeColorRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_removeColorRow_actionPerformed(e);
      }
    });
    jMenuItem_removeBlackRow.setBackground(new Color(220, 220, 220));
    jMenuItem_removeBlackRow.setText("Remove Black Marked in Row");
    jMenuItem_removeBlackRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_removeBlackRow_actionPerformed(e);
      }
    });
    jMenuItem_expConversion.setBackground(new Color(220, 220, 220));
    jMenuItem_expConversion.setText("Exp Base 2 Conversion");
    jMenuItem_expConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_expConversion_actionPerformed(e);
      }
    });
    jMenu1.setBackground(Color.lightGray);
    jMenu1.setText("Cell Line Row Selection");
    jMenuItem_cellLineRow.setBackground(Color.lightGray);
    jMenuItem_cellLineRow.setText("Cell Line Name Row (Factor 1)");
    jMenuItem_cellLineRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineRow_actionPerformed(e);
      }
    });
    jMenuItem_cellLineProfileRow.setBackground(new Color(220, 220, 220));
    jMenuItem_cellLineProfileRow.setText("Cell Line Profile Row (Factor 2)");
    jMenuItem_cellLineProfileRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineProfileRow_actionPerformed(e);
      }
    });
    jMenu2.setBackground(new Color(220, 220, 220));
    jMenu2.setText("Dye Swap Correction Row Selection");
    jMenuItem_techReplicate.setBackground(new Color(220, 220, 220));
    jMenuItem_techReplicate.setText("Tech Replicate Row");
    jMenuItem_techReplicate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_techReplicate_actionPerformed(e);
      }
    });
    jMenuItem_dyeSwapRow.setBackground(Color.lightGray);
    jMenuItem_dyeSwapRow.setText("Dye Swap Label Row");
    jMenuItem_dyeSwapRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_dyeSwapRow_actionPerformed(e);
      }
    });
//    jMenuItem_undoRemoveRow.setBackground(Color.lightGray);
//    jMenuItem_undoRemoveRow.setText("Undo Remove Row");
//    jMenuItem_undoRemoveRow.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        jMenuItem_undoRemoveRow_actionPerformed(e);
//      }
//    });
    jMenu3.setBackground(Color.lightGray);
    jMenu3.setText("Row Profile Analysis");
    jMenuItem_DeleteRow.setBackground(new Color(220, 220, 220));
    jMenuItem_DeleteRow.setText("Delete Row");
    jMenuItem_DeleteRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_DeleteRow_actionPerformed(e);
      }
    });
    jMenuItem_insertRow.setBackground(Color.lightGray);
    jMenuItem_insertRow.setText("Insert Row");
    jMenu5.setBackground(new Color(220, 220, 220));
    jMenu5.setText("Internet Search");
    jMenuItem_genBank_id.setBackground(new Color(220, 220, 220));
    jMenuItem_genBank_id.setText("GenBank id");
    jMenuItem_genBank_id.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_genBank_id_actionPerformed(e);
      }
    });
    jMenuItem_uniGene_id.setBackground(Color.lightGray);
    jMenuItem_uniGene_id.setText("UniGene id");
    jMenuItem_uniGene_id.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_uniGene_id_actionPerformed(e);
      }
    });
    jMenu6.setBackground(new Color(220, 220, 220));
    jMenu6.setText("Sorting");
    jMenuItem_sort_ascending.setBackground(Color.lightGray);
    jMenuItem_sort_ascending.setText("String Descending");
    jMenuItem_sort_ascending.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sort_ascending_actionPerformed(e);
      }
    });
    jMenuItem_sort_descending.setBackground(new Color(220, 220, 220));
    jMenuItem_sort_descending.setText("String Ascending");
    jMenuItem_sort_descending.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sort_descending_actionPerformed(e);
      }
    });
    jMenu7.setToolTipText("");
    jMenu7.setText("Dye Label");
    jMenuItem_dyeSwapCorrectionTechRepOnly.setText("Dye Bias Correction");
    jMenuItem_dyeSwapCorrectionTechRepOnly.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_dyeSwapCorrectionTechRepOnly_actionPerformed(e);
      }
    });
    jMenuItem_dyeDeviation.setText("Dye Bias Deviation");
    jMenuItem_dyeDeviation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_dyeDeviation_actionPerformed(e);
      }
    });
    jMenu10.setText("Cell Line");
    jMenuItem2.setText("Cell Line Alignment to Sham Average");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineCorrectionToAverage(e);
      }
    });
    jMenuItem_cellLineCorrection.setText("Cell Line Alignment to Sham Zero");
    jMenuItem_cellLineCorrection.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineCorrection_actionPerformed(e);
      }
    });
    jMenu11.setText("Data Operations");
    jMenuItem_log2ConversionAllCol.setText("Log2 Conversion");
    jMenuItem_log2ConversionAllCol.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_log2ConversionAllCol_actionPerformed(e);
      }
    });
    jMenuItem_exp2conversionAllCol.setText("Exponential Base 2 conversion");
    jMenuItem_exp2conversionAllCol.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_exp2conversionAllCol_actionPerformed(e);
      }
    });
 //   jMenuItem_randomTest.setText("Correlation Probability Test");
 //   jMenuItem_randomTest.addActionListener(new java.awt.event.ActionListener() {
 //     public void actionPerformed(ActionEvent e) {
 //       jMenuItem_randomTest_actionPerformed(e);
 //     }
 //   });
    jMenuItem_load_cellCycle_comp.setText("Load Cell Cycle Compartment");
    jMenuItem_load_cellCycle_comp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_load_cellCycle_comp_actionPerformed(e);
      }
    });
    jMenuItem_cellLineNorm.setText("Cell Line Normalization");
    jMenuItem_cellLineNorm.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineNorm_actionPerformed(e);
      }
    });
    jMenu12.setText("Row Data Processing");
    jMenuItem_replicateAverageStd.setText("Within-group Average and Standard Deviation");
    jMenuItem_replicateAverageStd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_replicateAverageStd_actionPerformed(e);
      }
    });
 //   jMenuItem_normalDistribution.setText("Get Normal Distribution");
 //   jMenuItem_normalDistribution.addActionListener(new java.awt.event.ActionListener() {
 //     public void actionPerformed(ActionEvent e) {
 //       jMenuItem_normalDistribution_actionPerformed(e);
 //     }
 //   });
    jMenu13.setBackground(Color.lightGray);
    jMenu13.setText("Plot Selected Rows Data");
    jMenuItem_plotCellLine.setText("Plot Cell Line");
    jMenuItem_plotCellLine.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plotCellLine_actionPerformed(e);
      }
    });
    jMenuItem_plotReplicate.setText("Plot BioReplicate");
    jMenuItem_plotReplicate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plotReplicate_actionPerformed(e);
      }
    });
    jMenuItem_plotRow.setText("Plot Row");
    jMenuItem_plotRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plotRow_actionPerformed(e);
      }
    });
    jMenuItem_markInvalidRow.setText("Mark valid row (w/o zero)");
    jMenuItem_markInvalidRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_markInvalidRow_actionPerformed(e);
      }
    });
    jMenu9.setText("Help");
    jMenuItem_helpNote.setText("Help Note");
    jMenuItem_helpNote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_helpNote_actionPerformed(e);
      }
    });
    jMenuItem_negation_AllCol.setText("Negation");
    jMenuItem_negation_AllCol.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_negation_AllCol_actionPerformed(e);
      }
    });
    jMenu14.setText("Data Sets Venn Diagrams");
    jMenuItem_loadFirstSets.setText("First Data Sets");
    jMenuItem_loadFirstSets.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadFirstSets_actionPerformed(e);
      }
    });
    jMenuItem_secondDataSets.setText("Second Data Sets");
    jMenuItem_secondDataSets.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_secondDataSets_actionPerformed(e);
      }
    });
    jMenuItem_saveTranspose.setText("Save Transposed Data");
    jMenuItem_saveTranspose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_saveTranspose_actionPerformed(e);
      }
    });
    jMenu15.setText("Column Data Operations");
    jMenuItem_techRep_diff_avg.setText("Each Two Pair Difference and Average");
    jMenuItem_techRep_diff_avg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_techRep_diff_avg_actionPerformed(e);
      }
    });
    jMenuItem_normalRandomGenerator.setText("Replace All Data By its Normal Deviates");
    jMenuItem_normalRandomGenerator.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_normalRandomGenerator_actionPerformed(e);
      }
    });
    jMenuItem_replaceZeroByNormalDeviates.setText("Replace Invalid Data By its Simulation Normal Deviates");
    jMenuItem_replaceZeroByNormalDeviates.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_replaceZeroByNormalDeviates_actionPerformed(e);
      }
    });
    jMenuItem_average.setText("Row Average and Standard Deviation");
    jMenuItem_average.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_average_actionPerformed(e);
      }
    });
    jMenuItem_stats.setText("Column Data Average and Standard Deviation");
    jMenuItem_stats.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_stats_actionPerformed(e);
      }
    });
    jMenuItem_replicateMerge.setText("Replicate Column Merge");
    jMenuItem_replicateMerge.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_replicateMerge_actionPerformed(e);
      }
    });
    jMenu16.setText("Utility");
    jMenuItem_loadDataSets.setText("Load Data Sets");
    jMenuItem_loadDataSets.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_loadDataSets_actionPerformed(e);
      }
    });
    jMenuItem_columnCorrelation.setText("Column Correlations");
    jMenuItem_columnCorrelation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_columnCorrelation_actionPerformed(e);
      }
    });
    jMenuItem_row_t_Test.setText("Sample t-Test");
    jMenuItem_row_t_Test.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_row_t_Test_actionPerformed(e);
      }
    });
    jMenuItem_row_paired_sample_t_test.setText("Paired Sample t-test");
    jMenuItem_row_paired_sample_t_test.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_row_paired_sample_t_test_actionPerformed(e);
      }
    });
    jMenuItem_row_ANOVA.setText("One Way ANOVA");
    jMenuItem_row_ANOVA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_row_ANOVA_actionPerformed(e);
      }
    });
    jMenuItem_anova_absolute.setText("ANOVA with baseline");
    jMenuItem_anova_absolute.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_anova_absolute_actionPerformed(e);
      }
    });
    jMenuItem_profileSNR.setText("Profile SNR Analysis");
    jMenuItem_profileSNR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_profileSNR_actionPerformed(e);
      }
    });
    jMenuItem_dyeBiasAnalysis.setText("Dye Bias Statistics Analysis");
    jMenuItem_dyeBiasAnalysis.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_dyeBiasAnalysis_actionPerformed(e);
      }
    });
    jMenuItem_highlightZero.setText("Highlight Row Containing Zero");
    jMenuItem_highlightZero.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_highlightZero_actionPerformed(e);
      }
    });
    jMenuItem_replcaceInvalidDataByItsAverage.setText("Replace MissingData By its replicate average");
    jMenuItem_replcaceInvalidDataByItsAverage.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_replcaceInvalidDataByItsAverage_actionPerformed(e);
      }
    });
    jMenuItem_sort_deci_desc.setText("Decimal Descending");
    jMenuItem_sort_deci_desc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sort_deci_desc_actionPerformed(e);
      }
    });
    jMenuItem_sort_deci_asce.setText("Decimal Ascending");
    jMenuItem_sort_deci_asce.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sort_deci_asce_actionPerformed(e);
      }
    });
 //   jMenuItem1.setText("Plot Selected Row With Error");
 //   jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
 //     public void actionPerformed(ActionEvent e) {
 //       jMenuItem1_actionPerformed(e);
 //     }
 //   });
    jMenuItem_geneName.setText("Gene Name");
    jMenuItem_geneName.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_geneName_actionPerformed(e);
      }
    });
    jMenuItem_cellLineAverage.setText("Cell Line Alignment to Average");
    jMenuItem_cellLineAverage.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_cellLineAverage_actionPerformed(e);
      }
    });
    jMenuItem_profileAnalysis.setText("Profile Statistical Analysis");
    jMenuItem_profileAnalysis.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_profileAnalysis_actionPerformed(e);
      }
    });
    jMenuItem_FDR.setText("False Discovery Rate");
    jMenuItem_FDR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_FDR_actionPerformed(e);
      }
    });
    jMenuItem_power.setText("Statistical Power");
    jMenuItem_power.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_power_actionPerformed(e);
      }
    });
    jMenuItem_normalDeviateP.setText("Peplace All Data By its Avg and Std Reserved Normal Deviates");
    jMenuItem_normalDeviateP.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_normalDeviateP_actionPerformed(e);
      }
    });
    jMenuItem_rowDataNorm.setText("Row data normalization");
    jMenuItem_rowDataNorm.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_rowDataNorm_actionPerformed(e);
      }
    });
    jMenuItem_avg_selectedRows.setText("Average selected Rows");
    jMenuItem_avg_selectedRows.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_avg_selectedRows_actionPerformed(e);
      }
    });
    jMenuItem_subtractRepAvg.setText("Subtract repAverage");
    jMenuItem_subtractRepAvg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_subtractRepAvg_actionPerformed(e);
      }
    });
    jMenuItem_getIntersectionList.setText("Load Files to get Mapping List");
    jMenuItem_getIntersectionList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_getIntersectionList_actionPerformed(e);
      }
    });
    jMenuItem_rowData_sub_avg.setText("Row data subtracting average");
    jMenuItem_rowData_sub_avg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_rowData_sub_avg_actionPerformed(e);
      }
    });
    jMenuItem_columnPlot.setText("Affy Data SVN normalization");
    jMenuItem_columnPlot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_columnPlot_actionPerformed(e);
      }
    });
    jMenu8.setText("Row Plot Option");
    jCheckBoxMenuItem_blkwht.setText("Black/White");
    jCheckBoxMenuItem_yLabel.setSelected(true);
    jCheckBoxMenuItem_yLabel.setText("Y axis tic mark");
    jCheckBoxMenuItem_xLabel.setSelected(true);
    jCheckBoxMenuItem_xLabel.setText("X axis tic mark");
    jMenuItem_subtractFirstRepavg.setText("Subtrac first repplicate average");
    jMenuItem_subtractFirstRepavg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_subtractFirstRepavg_actionPerformed(e);
      }
    });
    jMenuItem_dyeSwapMerged.setText("Export dye-swap merged data ");
    jMenuItem_dyeSwapMerged.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_dyeSwapMerged_actionPerformed(e);
      }
    });
    jCheckBoxMenuItem_SNR_relativeChange.setText("SNR - relative change");
    jMenuItem_plotThumbnails_rep.setText("Plot Thumbnails - replicate");
    jMenuItem_plotThumbnails_rep.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_plotThumbnails_rep_actionPerformed(e);
      }
    });
    jMenuItem_thumbnail_cellline.setText("Plot Thumbnails - cellline");
    jMenuItem_thumbnail_cellline.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_thumbnail_cellline_actionPerformed(e);
      }
    });
    jMenuItem_thumbnails.setText("Plot Thumbnails");
    jMenuItem_thumbnails.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_thumbnails_actionPerformed(e);
      }
    });
    jMenuItem_reconstitueCellcycle.setText("Reconstitute With Cell Cycle Phase");
    jMenuItem_reconstitueCellcycle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_reconstitueCellcycle_actionPerformed(e);
      }
    });
    jMenuItem_wg_Correlation.setText("Between Sub-profiles Correlation ");
    jMenuItem_wg_Correlation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_wg_Correlation_actionPerformed(e);
      }
    });
    jMenuItem_sameGeneCount.setText("Same Gene Count");
    jMenuItem_sameGeneCount.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_sameGeneCount_actionPerformed(e);
      }
    });
    jMenuItem_withingroup_avg.setText("Within-group Average");
    jMenuItem_withingroup_avg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_withingroup_avg_actionPerformed(e);
      }
    });
    jMenuItem_TF_correlationTest.setText("Transcription Factor Correlation Analysis");
    jMenuItem_TF_correlationTest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_TF_correlationTest_actionPerformed(e);
      }
    });
    jMenuItem_resetRowAssignment.setText("Reset  Row Assignment");
    jMenuItem_resetRowAssignment.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_resetRowAssignment_actionPerformed(e);
      }
    });
    jMenuItem_equal_between_group_avg.setText("Replace equal between-group average");
    jMenuItem_equal_between_group_avg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_equal_between_group_avg_actionPerformed(e);
      }
    });
    jMenuItem_ScatterPlot_w_errorBar.setText("Scatter Plot - replicate profile with Error Bar");
    jMenuItem_ScatterPlot_w_errorBar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_ScatterPlot_w_errorBar_actionPerformed(e);
      }
    });
    jMenuItem_Barplot_w_errorBar.setText("Bar Plot - replicate profile with Error Bar");
    jMenuItem_Barplot_w_errorBar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_Barplot_w_errorBar_actionPerformed(e);
      }
    });
    jMenuItem_peplicatescatterPlotwithError_2.setText("Scatter Plot - averaged data with error bar");
    jMenuItem_peplicatescatterPlotwithError_2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_peplicatescatterPlotwithError_2_actionPerformed(e);
      }
    });
    jMenuItem_barplot_with_error_2.setText("Bar Plot - averaged data with error bar");
    jMenuItem_barplot_with_error_2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_barplot_with_error_2_actionPerformed(e);
      }
    });
    jMenuItem_twoWayAnova.setText("Two Way ANOVA");
    jMenuItem_twoWayAnova.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_twoWayAnova_actionPerformed(e);
      }
    });
    jMenuItem_highlight_missingValue.setText("Highlight Missing Value Row");
    jMenuItem_highlight_missingValue.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_highlight_missingValue_actionPerformed(e);
      }
    });
    jMenuItem_factor3.setText("Factor 3");
    jMenuItem_factor3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_factor3_actionPerformed(e);
      }
    });
    jMenuItem_threewayANOVA.setText("Three Way ANOVA");
    jMenuItem_threewayANOVA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_threewayANOVA_actionPerformed(e);
      }
    });
    jMenuItem_output_MarkedData.setText("Output Color Marked Data");
    jMenuItem_output_MarkedData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_output_MarkedData_actionPerformed(e);
      }
    });
    jMenuItem_output_BlackMarkedData.setText("Output Black Marked Data");
    jMenuItem_output_BlackMarkedData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_output_BlackMarkedData_actionPerformed(e);
      }
    });
    jMenuItem_mergeFiles_to_one.setText("Merge Files to One");
    jMenuItem_mergeFiles_to_one.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_mergeFiles_to_one_actionPerformed(e);
      }
    });
    jMenuItem_derivative.setText("Derivative");
    jMenuItem_derivative.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_derivative_actionPerformed(e);
      }
    });
    jMenuItem_logisticConversion.setText("logistic conversion");
    jMenuItem_logisticConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_logisticConversion_actionPerformed(e);
      }
    });
    jMenuItem_derivativeMulptiFile.setText("Derivative Multiple Files");
    jMenuItem_derivativeMulptiFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_derivativeMulptiFile_actionPerformed(e);
      }
    });
    jMenuItem_insertData.setText("Insert Data Matching Coloum and Row ID");
    jMenuItem_insertData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_insertData_actionPerformed(e);
      }
    });
    jMenuItem_logisticFitting.setText("Logistic Fitting");
    jMenuItem_logisticFitting.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_logisticFitting_actionPerformed(e);
      }
    });
    jMenuItem_rowDerivative.setText("Derivative");
    jMenuItem_rowDerivative.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_rowDerivative_actionPerformed(e);
      }
    });
    jMenuItem_rowLogisticConversion.setText("Logistic Conversion");
    jMenuItem_rowLogisticConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_rowLogisticConversion_actionPerformed(e);
      }
    });
    jMenuItem_deglitch.setText("Deglitch");
    jMenuItem_deglitch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_deglitch_actionPerformed(e);
      }
    });
    jMenuItem_withingroupVarianceNorm.setText("With-group Standard Deviation Normalization");
    jMenuItem_withingroupVarianceNorm.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_withingroupVarianceNorm_actionPerformed(e);
      }
    });
    jMenu_test.setText("On Going");
    jMenu_factorAnalysis.setText("Factor Analysis");
    jMenu_statisticalProcessing.setText("Statistical Processing");
    jMenu_numericalProcessing.setText("Numerical Processing");
    jMenuItem_contain_one_ls.setText("highlight one value less");
    jMenuItem_contain_one_ls.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_contain_one_ls_actionPerformed(e);
      }
    });
    jMenuItem_containg_all_ls.setText("Highlight all values less");
    jMenuItem_containg_all_ls.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_containg_all_ls_actionPerformed(e);
      }
    });
//    jMenuItem_car_norm_test.setText("Variance Norm Test");
//    jMenuItem_car_norm_test.addActionListener(new java.awt.event.ActionListener() {
 //     public void actionPerformed(ActionEvent e) {
 //       jMenuItem_car_norm_test_actionPerformed(e);
 //     }
 //   });
    jMenuItem_profileSimilarity.setText("Profile Similarity in Patterns");
    jMenuItem_profileSimilarity.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_profileSimilarity_actionPerformed(e);
      }
    });
    jMenuItem_celllinealignmenttofirstshamaverage.setText("CellLine Alignment to First Sham Average");
    jMenuItem_celllinealignmenttofirstshamaverage.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_celllinealignmenttofirstshamaverage_actionPerformed(e);
      }
    });
    jMenuItem_pairWiseCorrelation.setText("Get Selected Rows Pair Wise Correlation");
    jMenuItem_pairWiseCorrelation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_pairWiseCorrelation_actionPerformed(e);
      }
    });
    jMenu_analysis.add(jMenuItem_PCA);
    jMenu_analysis.add(jMenuItem_geneSelectionCategorization);
    jMenu_analysis.add(jMenuItem_heatMap);
    jMenu_analysis.add(jMenuItem_clustering);
    jMenu_analysis.add(jMenuItem_fuzzyART);
    jMenu_analysis.add(jMenuItem_relevanceAnalysis);
    jMenu_analysis.add(jMenuItem_linearDiscrimination);
    jMenu_analysis.add(jMenuItem_bayesianClassification);
    jMenu_analysis.add(jMenuItem_TF_correlationTest);

    jMenuBar1.add(jMenu_File);
    jMenuBar1.add(jMenu_analysis);
    jMenuBar1.add(jMenu_uitility);
    jMenuBar1.add(jMenu9);
    jMenu_File.add(jMenuItem_saveData);
    jMenu_File.add(jMenuItem_saveTranspose);
    jMenu_File.add(jMenu8);
    jMenu_File.add(jMenuItem_close);
    setJMenuBar(jMenuBar1);


    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Click on Column and Select Column Name");
    jPanel1.setLayout(gridBagLayout1);
    jPanel1.setPreferredSize(new Dimension(500, 550));
    jPanel2.setPreferredSize(new Dimension(500, 120));
    jPanel2.setToolTipText("");
    jPanel2.setLayout(gridBagLayout2);
    jPanel_table.setPreferredSize(new Dimension(500, 480));
    jScrollPane1.setBorder(titledBorder1);
    jScrollPane1.setPreferredSize(new Dimension(500, 480));
    myTable1.setPreferredScrollableViewportSize(new Dimension(500, 400));
    jLabel_startLine.setPreferredSize(new Dimension(120, 25));
    jLabel_startLine.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_startLine.setHorizontalTextPosition(SwingConstants.RIGHT);
    jLabel_startLine.setText("Data Start Row: ");
    jTextField_startLine.setToolTipText("");
    jTextField_startLine.setActionMap(null);
    jTextField_startLine.setFocusAccelerator(' ');
    jTextField_startLine.setText("-1");

    jMenuItem_startLineText.setBackground(new Color(220, 220, 220));
    jMenuItem_startLineText.setBorder(null);
    jMenuItem_startLineText.setText("Select Data Start Row");
    jMenuItem_startLineText.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_startLineText_actionPerformed(e);
      }
    });
    jMenuItem_startColumn.setBackground(Color.lightGray);
    jMenuItem_startColumn.setText("Select Data Start Column");
    jMenuItem_startColumn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_startColumn_actionPerformed(e);
      }
    });
    jLabel_startColumn.setPreferredSize(new Dimension(100, 25));
    jLabel_startColumn.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel_startColumn.setHorizontalTextPosition(SwingConstants.RIGHT);
    jLabel_startColumn.setText("Data Start Column: ");
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel2.add(jLabel_SampleName,             new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_sampleName,             new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_startColumn,           new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_replicate,           new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_replicate,            new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_startLine,           new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_startColumn,         new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_startLine,       new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_bioGroupRow,   new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_repProfileRow,    new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_bioGroupRow,   new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_repProfileRow,   new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel_dyeSwap, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_dyeSwap, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel1, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jTextField_techRep, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jPanel_table,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
    jPanel_table.add(jScrollPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jPanel2,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jScrollPane1.getViewport().add(myTable1, null);
    jPopupMenu_cellTextSelection.add(jMenuItem_startColumn);
    jPopupMenu_cellTextSelection.add(jMenuItem_startLineText);
    jPopupMenu_cellTextSelection.add(jMenuItem_replicate);
    jPopupMenu_cellTextSelection.add(jMenuItem_sampleName);
    jPopupMenu_cellTextSelection.add(jMenu2);
    jPopupMenu_cellTextSelection.add(jMenu1);
    jPopupMenu_cellTextSelection.addSeparator();
    jPopupMenu_cellTextSelection.add(jMenu13);
    jPopupMenu_cellTextSelection.add(jMenu3);
    jPopupMenu_cellTextSelection.add(jMenu5);
    jPopupMenu_cellTextSelection.add(jMenuItem_pairWiseCorrelation);
    jPopupMenu_search.add(jMenuItem_expConversion);
    jPopupMenu_search.add(jMenuItem_logConversion);
    jPopupMenu_search.add(jMenuItem_minusOp);
    jPopupMenu_search.add(jMenuItem_power);
    jPopupMenu_search.add(jMenuItem_FDR);
    jPopupMenu_search.addSeparator();
    jPopupMenu_search.add(jMenuItem_search);
    jPopupMenu_search.add(jMenuItem_sameGeneMerge);
    jPopupMenu_search.add(jMenu6);
    jPopupMenu_search.addSeparator();
    jPopupMenu_search.add(jMenuItem_loadDataConjunction);
    jPopupMenu_search.add(jMenuItem_loadDataNegConjection);
    jPopupMenu_search.add(jMenuItem_removeColorRow);
    jPopupMenu_search.add(jMenuItem_output_MarkedData);
    jPopupMenu_search.add(jMenuItem_removeBlackRow);
    jPopupMenu_search.add(jMenuItem_output_BlackMarkedData);
    jPopupMenu_search.add(jMenuItem_markInvalidRow);
    jPopupMenu_search.add(jMenuItem_getIntersectionList);
    jMenu_uitility.add(jMenu11);
    jMenu_uitility.add(jMenu12);
    jMenu_uitility.add(jMenu15);
    jMenu_uitility.add(jMenu14);
    jMenu_uitility.add(jMenu16);
    jMenu1.add(jMenuItem_cellLineRow);
    jMenu1.add(jMenuItem_cellLineProfileRow);
    jMenu1.add(jMenuItem_factor3);
    jMenu2.add(jMenuItem_techReplicate);
    jMenu2.add(jMenuItem_dyeSwapRow);
//    jMenu3.add(jMenuItem_undoRemoveRow);
    jMenu3.add(jMenuItem_DeleteRow);
    jMenu3.add(jMenuItem_insertRow);
    jMenu3.add(jMenuItem_avg_selectedRows);
    jMenu3.add(jMenuItem_logisticFitting);
    jMenu3.add(jMenuItem_rowDerivative);
    jMenu3.add(jMenuItem_rowLogisticConversion);
    jMenu3.add(jMenuItem_deglitch);
    jMenu5.add(jMenuItem_genBank_id);
    jMenu5.add(jMenuItem_uniGene_id);
    jMenu5.add(jMenuItem_geneName);
    jMenu6.add(jMenuItem_sort_ascending);
    jMenu6.add(jMenuItem_sort_descending);
    jMenu6.add(jMenuItem_sort_deci_desc);
    jMenu6.add(jMenuItem_sort_deci_asce);
    jMenu7.add(jMenuItem_dyeSwapCorrectionTechRepOnly);
    jMenu7.add(jMenuItem_dyeDeviation);
    jMenu7.add(jMenuItem_dyeBiasAnalysis);
    jMenu7.add(jMenuItem_dyeSwapMerged);
    jMenu10.add(jMenuItem_cellLineCorrection);
    jMenu10.add(jMenuItem2);
    jMenu10.add(jMenuItem_cellLineAverage);
    jMenu10.add(jMenuItem_cellLineNorm);
    jMenu10.add(jMenuItem_load_cellCycle_comp);
    jMenu10.add(jMenuItem_reconstitueCellcycle);
    jMenu10.add(jMenuItem_wg_Correlation);
    jMenu10.add(jMenuItem_celllinealignmenttofirstshamaverage);
    jMenu11.add(jMenuItem_log2ConversionAllCol);
    jMenu11.add(jMenuItem_exp2conversionAllCol);
    jMenu11.add(jMenuItem_negation_AllCol);
    jMenu11.add(jMenuItem_logisticConversion);
    jMenu11.add(jMenuItem_resetRowAssignment);
//    jMenu11.add(jMenuItem_randomTest);
//    jMenu11.add(jMenuItem_normalDistribution);
    jMenu12.add(jMenuItem_highlightZero);
    jMenu12.add(jMenuItem_containg_all_ls);
    jMenu12.add(jMenuItem_contain_one_ls);
    jMenu12.add(jMenuItem_highlight_missingValue);
    jMenu12.add(jMenuItem_profileSimilarity);
    jMenu12.add(jMenuItem_profileAnalysis);
    jMenu12.add(jMenu_factorAnalysis);
    jMenu12.add(jMenu7);
    jMenu12.add(jMenu10);
    jMenu12.add(jMenu_statisticalProcessing);
    jMenu12.add(jMenu_numericalProcessing);
   jMenu13.add(jMenuItem_plotCellLine);
    jMenu13.add(jMenuItem_plotReplicate);
    jMenu13.add(jMenuItem_plotRow);
    jMenu13.add(jMenuItem_plotThumbnails_rep);
    jMenu13.add(jMenuItem_thumbnail_cellline);
    jMenu13.add(jMenuItem_thumbnails);
    jMenu13.add(jMenuItem_ScatterPlot_w_errorBar);
    jMenu13.add(jMenuItem_peplicatescatterPlotwithError_2);
    jMenu13.add(jMenuItem_Barplot_w_errorBar);
    jMenu13.add(jMenuItem_barplot_with_error_2);
//    jMenu13.add(jMenuItem1);
    jMenu9.add(jMenuItem_helpNote);
    jMenu14.add(jMenuItem_loadFirstSets);
    jMenu14.add(jMenuItem_secondDataSets);
    jMenu15.add(jMenuItem_techRep_diff_avg);
    jMenu15.add(jMenuItem_stats);
    jMenu15.add(jMenuItem_replicateMerge);
    jMenu15.add(jMenuItem_columnCorrelation);
    jMenu15.add(jMenuItem_columnPlot);
    jMenu16.add(jMenuItem_insertData);
    jMenu16.add(jMenuItem_mergeFiles_to_one);
    jMenu16.add(jMenuItem_loadDataSets);
    jMenu16.add(jMenuItem_sameGeneCount);
    jMenu16.add(jMenu_test);
    jMenu8.add(jCheckBoxMenuItem_xLabel);
    jMenu8.add(jCheckBoxMenuItem_yLabel);
    jMenu8.add(jCheckBoxMenuItem_blkwht);
    jMenu8.add(jCheckBoxMenuItem_SNR_relativeChange);
    jMenu_test.add(jMenuItem_derivativeMulptiFile);
    jMenu_test.add(jMenuItem_threewayANOVA);
    jMenu_factorAnalysis.add(jMenuItem_row_ANOVA);
    jMenu_factorAnalysis.add(jMenuItem_anova_absolute);
    jMenu_factorAnalysis.add(jMenuItem_profileSNR);
    jMenu_factorAnalysis.add(jMenuItem_row_t_Test);
    jMenu_factorAnalysis.add(jMenuItem_row_paired_sample_t_test);
    jMenu_factorAnalysis.add(jMenuItem_twoWayAnova);
    jMenu_factorAnalysis.add(jMenuItem_withingroup_avg);
    jMenu_factorAnalysis.add(jMenuItem_replicateAverageStd);
    jMenu_factorAnalysis.add(jMenuItem_withingroupVarianceNorm);
    jMenu_factorAnalysis.add(jMenuItem_average);
//    jMenu_factorAnalysis.add(jMenuItem_car_norm_test);
    jMenu_statisticalProcessing.add(jMenuItem_equal_between_group_avg);
    jMenu_statisticalProcessing.add(jMenuItem_replcaceInvalidDataByItsAverage);
    jMenu_statisticalProcessing.add(jMenuItem_replaceZeroByNormalDeviates);
    jMenu_statisticalProcessing.add(jMenuItem_normalDeviateP);
    jMenu_statisticalProcessing.add(jMenuItem_normalRandomGenerator);
    jMenu_statisticalProcessing.add(jMenuItem_rowData_sub_avg);
    jMenu_statisticalProcessing.add(jMenuItem_rowDataNorm);
    jMenu_statisticalProcessing.add(jMenuItem1);
    jMenu_numericalProcessing.add(jMenuItem_derivative);
    jMenu_numericalProcessing.add(jMenuItem_subtractFirstRepavg);
    jMenu_numericalProcessing.add(jMenuItem_subtractRepAvg);
  }

  public void setTableData(String [][] str) {
    String [] columnName = null;
    int startRow=-1, barcodeRow=-1, barcodeCol=-1;
    jTextField_startColumn.setText("-1");
    jTextField_startLine.setText("-1");

    if (myTable1 != null)
      jScrollPane1.getViewport().remove(myTable1);
    myTable1 = new GenericTable(new GenericDataTableModel(str,columnName,msg,dataPlot));
    tableSetup(myTable1);

  }
  public GenericDataTableModel arrayToModel(String [][] str) {
    GenericDataTableModel gd = new GenericDataTableModel(str,null,msg,dataPlot);
    return gd;
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
    Dimension d = jPanel_table.getPreferredSize();
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
      public void mousePressed(java.awt.event.MouseEvent evt) {
        tableFormMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        tableFormMouseReleased(evt);
      }
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

  private void setTableModel(GenericDataTableModel model) {
    if (myTable1 != null)
      jScrollPane1.getViewport().remove(myTable1);
    myTable1 = new GenericTable(model);
    tableSetup(myTable1);
  }

  private void headermotionformMouseDragged(java.awt.event.MouseEvent evt) {
//   if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_search.isShowing())
//    jPopupMenu_showColumnHint.show( jTable1, evt.getX(), evt.getY() );
  }

  private void headermotionFormMouseMoved(java.awt.event.MouseEvent evt) {
//    jPopupMenu_columnNameList.setVisible(false);
//    jPopupMenu_search.setVisible(false);
//    if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_search.isShowing())
//      jPopupMenu_showColumnHint.show( myTable1, evt.getX(), evt.getY() );

  }
  private void tablemotionformMouseDragged(java.awt.event.MouseEvent evt) {
    if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
        && !jPopupMenu_search.isShowing())
      jPopupMenu_cellTextSelection.show( myTable1, evt.getX(), evt.getY() );
  }
  private void tablemotionFormMouseMoved(java.awt.event.MouseEvent evt) {
    if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
        && !jPopupMenu_search.isShowing())
      jPopupMenu_cellTextSelection.show( myTable1, evt.getX(), evt.getY() );
  }

  private void headerformMouseClicked(java.awt.event.MouseEvent evt) {
    myTable1.clearSelection();
    TableColumnModel colModel = myTable1.getColumnModel();
    JTableHeader t=myTable1.getTableHeader();
    Point p = new Point();
    p.setLocation(evt.getX(),evt.getY());
    int columnModelIndex = t.columnAtPoint(p);
    selectedCol = columnModelIndex;
 //   System.out.println("selectedCol="+selectedCol);
    if(SwingUtilities.isLeftMouseButton(evt)){
      jPopupMenu_columnNameList.show( myTable1, evt.getX(), evt.getY() );
    }
    else if (SwingUtilities.isRightMouseButton(evt)) {
      jPopupMenu_search.show( myTable1, evt.getX(), evt.getY() );
    }

  }
  private void highLightHeader(int selectedCol) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    TableColumnModel tcm = myTable1.getColumnModel();
    for (int i=0;i<tm.getColumnCount();i++) {
      TableColumn column = tcm.getColumn(i);
      ColoredTableCellRendererClassIO headRenderer =
          (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
      if (i==selectedCol)
        headRenderer.setBackground(Color.darkGray);
      else
        headRenderer.setBackground(Color.lightGray);
      headRenderer.repaint();
    }
//    myTable1.repaint();
  }

  private void headerformMousePressed(java.awt.event.MouseEvent evt) {
  }
  private void headerformMouseReleased(java.awt.event.MouseEvent evt) {
//    TableColumnModel colModel = myTable1.getColumnModel();
//   int columnModelIndex = colModel.getColumnIndexAtX(evt.getX());
//    selectedCol = colModel.getColumn(columnModelIndex).getModelIndex();
//    highLightHeader(selectedCol);
//    jPopupMenu_columnNameList.setVisible(false);
//    jPopupMenu_search.setVisible(false);
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
      if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
          && !jPopupMenu_search.isShowing())
        jPopupMenu_cellTextSelection.show( myTable1, evt.getX(), evt.getY() );
    }

  }
  private void tableFormMousePressed(java.awt.event.MouseEvent evt) {
//   System.out.println("formMouseClicked");
  }
  private void tableFormMouseReleased(java.awt.event.MouseEvent evt) {
//    System.out.println("formMouseReleased");
  }

  private void saveAll() {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.saveData();
  }

  void jMenuItem_startLineText_actionPerformed(ActionEvent e) {
    int startRow = myTable1.getSelectedRow();
    jTextField_startLine.setText(startRow+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setStartDataRow(startRow);
//    myTable1.repaint();
 }

  void jMenuItem_startColumn_actionPerformed(ActionEvent e) {
    int startCol = myTable1.getSelectedColumn();
    jTextField_startColumn.setText(startCol+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setStartColumn(startCol);
//    myTable1.repaint();
  }



  public JTable getTable() {
    return myTable1;
  }

  public void updateStringSearch(int [] findIndex) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.updateStringSearch(selectedCol,findIndex);
    myTable1.repaint();
  }

  public void initialStringSearch() {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int totalRowNum = tm.getRowCount();
    Object obj;
    ColorStringClassIO csc;
    for (int i=0;i<totalRowNum;i++) {
      csc = (ColorStringClassIO)tm.getValueAt(i,selectedCol);
      if (csc != null)
        csc.setColor(Color.black);
    }

  }
  void jMenuItem_search_actionPerformed(ActionEvent e) {
    if (searchStringFrame == null) {
      searchStringFrame = new SearchStringFrameIO(this,selectedCol,msg);
      searchStringFrame.setVisible(true);
    }
    else {
      searchStringFrame.setSelectedCol(selectedCol);
      if (!searchStringFrame.isShowing())
        searchStringFrame.setVisible(true);
    }
  }

  void jMenuItem_close_actionPerformed(ActionEvent e) {
    dispose();
  }


  void jMenuItem_exit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void jMenuItem_logConversion_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int selCol = myTable1.getSelectedColumn();
    tm.log2Conversion(selCol);
    tm.fireTableDataChanged();
  }

  void jMenuItem_insertRow_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = myTable1.getSelectedRow();
    tm.insertRow(startRow);
    tm.fireTableDataChanged();
  }

  void jMenuItem_DeleteRow_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int [] startRows = myTable1.getSelectedRows();
    for (int i=0;i<startRows.length;i++) {
      tm.removeRow( startRows[i]);
    }
    tm.fireTableDataChanged();
  }

  void jMenuItem_sampleName_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_sampleName.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setSampleNameRow(n);

  }

  void jMenuItem_replicate_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_replicate.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setReplicateRow(n);

  }


  void jMenuItem_heatMap_actionPerformed(ActionEvent e) {
    heatMap();
  }
  private void heatMap() {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    tm.highlightRowDataContainsZero();
//    tm.removeMarked(0);
    int startRow = tm.getStartDataRow();
    int startColumn = tm.getStartColumn();
    if (startRow == -1 || startColumn == -1) return;
    float [][] geneData = tm.getRowDecimalValue();
    String [][] rowInfo = tm.getRowInfo();
    String [][] colInfo = tm.getColumnInfo();
    new HeatMapFrame(colInfo,rowInfo,geneData,msg,dataPlot).setVisible(true);
  }
  void jMenuItem_PCA_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null) return;
    new PCAFrame(ds, msg,dataPlot).setVisible(true);
  }

  void jMenuItem_clustering_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null) return;
    new HierarchicalClusteringFrame(ds, msg,dataPlot).setVisible(true);
  }
  void jMenuItem_fuzzyART_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null || ds.getRepRow()==-1) {
      Display.display("Need assign start row and column numbers and replicate group row.");
      return;
    }
    new FuzzyARTMAPFrame(ds, msg,dataPlot).setVisible(true);
  }

  void jMenuItem_linearDiscrimination_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null || ds.getRepRow()==-1) {
      Display.display("Need assign start row and column numbers and replicate group row.");
      return;
    }
    new LinearDiscriminationFrame(ds, msg,dataPlot).setVisible(true);
  }

  void jMenuItem_relevanceAnalysis_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null || ds.getRepRow()==-1) {
      Display.display("Need assign start row and column numbers and replicate group row.");
      return;
    }
    new RelevanceAnalysisFrame(ds, msg,dataPlot).setVisible(true);
  }

  void jMenuItem_bayesianClassification_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    tm.removeMarked(0);
    DataSet ds = tm.getDataSets();
    if (ds == null || ds.getRepRow()==-1) {
      Display.display("Need assign start row and column numbers and replicate group row.");
      return;
    }
 //   new bayeFrame(ds, msg,dataPlot).show();

    new BayesianAnalysisFrame(ds, msg,dataPlot).setVisible(true);
  }



  void jMenuItem_geneSelectionCategorization_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    DataSet ds = tm.getDataSets();
    if (ds == null) return;

    int plotOption;
    if (ds.getCellLineNameRow()!= -1)
      plotOption = DataSet.cellLineProfilePlot;
    else if (ds.getRepRow() != -1)
      plotOption = DataSet.bioRepPlot;
    else
      plotOption = DataSet.samplePlot;
    ds.setDataPlotOption(plotOption);

    new EPIGFrame(ds, msg,dataPlot).setVisible(true);

  }
  public void sameGeneTobeMerged() {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sameRowDataTobeMerged(selectedCol);
    tm.fireTableDataChanged();
  }

  void jMenuItem_sameGeneMerge_actionPerformed(ActionEvent e) {
    sameGeneTobeMerged();
  }

  void jMenuItem_saveData_actionPerformed(ActionEvent e) {
    saveAll();
  }
/*
  void jMenuItem_randomTest_actionPerformed(ActionEvent e) {
    Random random = new Random();
    String str = jTextField_sampleName.getText();
    int size;
    try {
      size = Integer.parseInt(str);
    }
    catch (NumberFormatException ee) {
      size = 30;
    }
    System.out.println("data size "+size);

    str = jTextField_replicate.getText();
    int numberAboveThreshold;
    try {
      numberAboveThreshold = Integer.parseInt(str);
    }
    catch (NumberFormatException ee) {
      numberAboveThreshold = 10;
    }
    System.out.println("numberAboveThreshold "+numberAboveThreshold);

    str = jTextField_dyeSwap.getText();
    float threshold;
    try {
      threshold = Float.parseFloat(str);
    }
    catch (NumberFormatException ee) {
      threshold = 0.8f;
    }
    System.out.println("correlation coefficiency threshold "+threshold);

    float [] x = new float[size];
    float [] y = new float[size];
    float [] result;
    for(int i=0;i<size;i++)
      x[i]= (float)random.nextDouble();
    int numExceedZeroOfEight =0;
    int iterationNumber = 0;
    while (numExceedZeroOfEight<numberAboveThreshold) {
      for(int i=0;i<size;i++)
        y[i]= (float)random.nextDouble();
      result = Correlation.pearsn(x,y);
      iterationNumber++;
      if (result[0] > threshold) numExceedZeroOfEight++;
    }
    System.out.println("final iterationNumber "+iterationNumber);
  }
*/
  void jMenuItem_minusOp_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int selCol = myTable1.getSelectedColumn();
   tm.minusOperation(selCol);
    tm.fireTableDataChanged();
  }



  void jMenuItem_cellLineCorrection_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int n = tm.getCellLineNameRow();
    if (n==-1)return;
    n = tm.getCellLineProfileRow();
    if (n==-1)return;
    n= tm.getReplicateRow();
    if (n==-1) return;
    n = tm.getStartDataRow();
    if (n==-1)return;
    n=tm.getStartColumn();
    if (n==-1)return;
    tm.cellLineCorrectionToZero();
    tm.fireTableDataChanged();

  }

  void jMenuItem_replicateMerge_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m= tm.getReplicateRow();
    if (m==-1) return;
    m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    DataSet ds =  tm.replicateArrayMerge();
    new DataSetTableFrame(dataPlot,ds, msg).setVisible(true);
  }

  void jMenuItem_loadDataConjunction_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    String [] str = FileIO.loadData(0,"\t");
    int [] index = tm.getConjunctionList(selectedCol,str);
    tm.updateStringSearch(selectedCol,index);
    myTable1.repaint();
  }

  void jMenuItem_loadDataNegConjection_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    String [] str = FileIO.loadData(0,"\t");
    int [] index = tm.getNegConjunctionList(selectedCol,str);
    tm.updateStringSearch(selectedCol,index);
    myTable1.repaint();
  }

  void jMenuItem_removeColorRow_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.removeMarked(selectedCol);
    myTable1.repaint();
  }

  void jMenuItem_removeBlackRow_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.removeUnmarked(selectedCol);
    myTable1.repaint();
  }

  void jMenuItem_log2ConversionAllCol_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    tm.log2Conversion();
    myTable1.repaint();
  }

  void jMenuItem_exp2conversionAllCol_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    tm.exp2Conversion();
    myTable1.repaint();
  }

  void jMenuItem_expConversion_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int selCol = myTable1.getSelectedColumn();
    tm.exp2Conversion(selCol);
    myTable1.repaint();
  }

  void jMenuItem_cellLineCorrectionToAverage(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.cellLineCorrectionToShamAverage();
    tm.fireTableDataChanged();
  }
  void jMenuItem_cellLineAverage_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.cellLineCorrectionToAverage();
    tm.fireTableDataChanged();

  }

  void jMenuItem_cellLineProfileRow_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_repProfileRow.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setCellLineProfileRow(n);
  }

  void jMenuItem_cellLineRow_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_bioGroupRow.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setCellLineNameRow(n);
  }

  void jMenuItem_dyeSwapRow_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_dyeSwap.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setDyeSwapLabelRow(n);
  }

  void jMenuItem_techReplicate_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
    jTextField_techRep.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setTechReplicateRow(n);
  }


  void jMenuItem_genBank_id_actionPerformed(ActionEvent e) {
    int selRow =myTable1.getSelectedRow();
    int selCol = myTable1.getSelectedColumn();
    ColorStringClassIO csc = (ColorStringClassIO)myTable1.getValueAt(selRow,selCol);
      if (csc !=null)
      Display.GenBank_id(csc.toString());
     myTable1.clearSelection();
  }

  void jMenuItem_uniGene_id_actionPerformed(ActionEvent e) {
    int selRow =myTable1.getSelectedRow();
    int selCol = myTable1.getSelectedColumn();
    ColorStringClassIO csc;
      csc = (ColorStringClassIO)myTable1.getValueAt(selRow,selCol);
      if (csc!=null) {
        Display.ie(csc.toString());
       }
    myTable1.clearSelection();
  }

  void jMenuItem_sort_ascending_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sortingString(selectedCol,true);
    tm.fireTableDataChanged();
  }

  void jMenuItem_sort_descending_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sortingString(selectedCol,false);
    tm.fireTableDataChanged();
  }

  void jMenuItem_load_cellCycle_comp_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    String [][] str = FileIO.loadData("\t");
    tm.purityCorrection(str);
    myTable1.repaint();
  }

  void jMenuItem_dyeSwapCorrectionTechRepOnly_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    boolean techRepOnly = true;
    tm.dyeSwapCorrection();
    tm.fireTableDataChanged();
  }


  void jMenuItem_dyeDeviation_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int n = tm.getDyeSwapLabelRow();
    if (n==-1)return;
    int m= tm.getReplicateRow();
    if (m==-1) return;
    m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    tm.dyeSwapStandardDeviation();
  }
/*
  void jMenuItem_plotWithError_actionPerformed(ActionEvent e) {
//    int cellLineProfilePlot = 2;
 //   if (jCheckBoxMenuItem_cellLineProfile.isSelected()) cellLineProfilePlot = 0;
 //   else if (jCheckBoxMenuItem_bioRepProfile.isSelected()) cellLineProfilePlot = 1;
 //   else if (jCheckBoxMenuItem_sampleProfile.isSelected()) cellLineProfilePlot = 2;
 //   else cellLineProfilePlot = 2;
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setDataPlotOptionRow(DataSet.samplePlot);
    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,true,false,DataSet.samplePlot);
  }
*/
  void jMenuItem_saveDataWithInfo_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.saveDataWithFeature();
  }

  void jMenuItem_cellLineNorm_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.cellLineNormalization();
    tm.fireTableDataChanged();
  }

  void jMenuItem_replicateAverageStd_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.profileReplicateAverageAndStandardDeviation();
  }

  void jMenuItem_normalRandomGenerator_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();

    int m = tm.getStartDataRow();
    if (m==-1)return;

    m=tm.getStartColumn();
    if (m==-1)return;

    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.normalDeviates();
  }
  void jMenuItem_normalDeviateP_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();

    int m = tm.getStartDataRow();
    if (m==-1)return;

    m=tm.getStartColumn();
    if (m==-1)return;

    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.avg_std_reserved_normalDeviates();

  }


/*
  void jMenuItem_normalDistribution_actionPerformed(ActionEvent e) {
    int dataSize = 10000;
     float [] x = DataConversion.normalDeviates(dataSize);
     float [][] data = DataConversion.getHistogramData(x);
     dataPlot.plotXY_histogram(data[0],data[1],"x","freq","random generator",Color.red,null,"normal distribution",true,false);
  }
*/
  void jMenuItem_plotCellLine_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.cellLineProfilePlot);
  }

  void jMenuItem_plotReplicate_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m= tm.getReplicateRow();
    if (m==-1) return;
    else
      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.bioRepPlot);
  }

  void jMenuItem_plotRow_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.samplePlot);
  }

  void jMenuItem_markInvalidRow_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int [] index = tm.getInvalidRowList();
    if (index == null) return;
//    msg.display("number of marked: " + index.length+"/" + (tm.getRowCount()-tm.getStartDataRow()));
    tm.updateStringSearch(selectedCol,index);
    myTable1.repaint();
  }

  void jMenuItem_replaceZeroByNormalDeviates_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();

    int m = tm.getStartDataRow();
    if (m==-1)return;

    m=tm.getStartColumn();
    if (m==-1)return;

    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.normalDeviatesReplaceZero();
  }

  void jMenuItem_columnPlot_actionPerformed(ActionEvent e) {
  GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
   DataSet ds = tm.getDataSets();
   if (ds != null)
    new AffyDataSVNFrame(dataPlot,ds,tm,msg).setVisible(true);
  }

  void jMenuItem_helpNote_actionPerformed(ActionEvent e) {
    String ss = FileIO.docPath+"help\\GenericCompiledData.pdf";
    FileIO.openPDF(ss);
  }

  void jMenuItem_negation_AllCol_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.minusOperation();
    tm.fireTableDataChanged();
  }

  void jMenuItem_loadFirstSets_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    int startRow = tm.getStartDataRow();
//    if (startRow == -1) return;
//    int startCol=tm.getStartColumn();
//   if (startCol == -1) return;

    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    tm.firstDataSetsForVennDiagram(f,"\t");
  }

  void jMenuItem_secondDataSets_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = tm.getStartDataRow();
    if (startRow <0) startRow=0;
//    if (startRow == -1) return;
    int startCol=tm.getStartColumn();
    if (startCol <0) startCol=0;
//   if (startCol == -1) return;
    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    tm.secondDataSetsForVennDiagram(f,startRow,startCol,"\t");
  }

  void jMenuItem_loadDataSets_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    int startRow = tm.getStartDataRow();
    String [][] s;
    if (startRow == -1)
     s = FileIO.loadDataSets(f,0,"\t");
    else
       s = FileIO.loadDataSets(f,startRow,"\t");
    if (s != null) {
    tm.loadData(s);
    tm.fireTableDataChanged();
    }
  }

  void jMenuItem_saveTranspose_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.saveTransposedData();
  }

  void jMenuItem_techRep_diff_avg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.columnSubtractionAndAverage();
    tm.fireTableDataChanged();
  }

  void jMenuItem_average_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowAverageAndStandardDeviation();
    tm.fireTableDataChanged();
  }

  void jMenuItem_stats_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.columnStatistics();
    tm.fireTableDataChanged();
  }

  void jMenuItem_columnCorrelation_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.columnCorrelations();
  }

  void jMenuItem_row_t_Test_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_t_test(fileName_wo_ext);
  }

  void jMenuItem_row_paired_sample_t_test_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_paired_sample_t_test(fileName_wo_ext);
  }

  void jMenuItem_row_ANOVA_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_ANOVA(fileName_wo_ext);
  }

  void jMenuItem_anova_absolute_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_ANOVA_with_baseline(fileName_wo_ext);
  }

  void jMenuItem_profileSNR_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_SNR(fileName_wo_ext,jCheckBoxMenuItem_SNR_relativeChange.isSelected());
  }

  void jMenuItem_dyeBiasAnalysis_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    boolean techRepOnly = true;
    tm.dyeSwapAnalysis();
    tm.fireTableDataChanged();
  }

  void jMenuItem_highlightZero_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.highlightRowDataContainsZero();
    myTable1.repaint();
  }

  void jMenuItem_replcaceInvalidDataByItsAverage_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();

    int m = tm.getStartDataRow();
    if (m==-1)return;

    m=tm.getStartColumn();
    if (m==-1)return;

    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.replaceZeroByItsAverage();
  }

  void jMenuItem_sort_deci_asce_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sortingDecimal(selectedCol,false);
    tm.fireTableDataChanged();
  }

  void jMenuItem_sort_deci_desc_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sortingDecimal(selectedCol,true);
    tm.fireTableDataChanged();
  }

  void jMenuItem_geneName_actionPerformed(ActionEvent e) {
    int selRow =myTable1.getSelectedRow();
     int selCol = myTable1.getSelectedColumn();
     ColorStringClassIO csc = (ColorStringClassIO)myTable1.getValueAt(selRow,selCol);
       if (csc !=null)
       Display.GenName(csc.toString());
      myTable1.clearSelection();
  }

  void jMenuItem_profileAnalysis_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
     DataSet ds = tm.getDataSets();
     if (ds != null)
    new ProfileStatisticalAnalysisFrame(fileName_wo_ext,ds, msg,dataPlot).setVisible(true);

  }

  void jMenuItem_power_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int selCol = myTable1.getSelectedColumn();
    tm.statisticalPower(selCol);
    tm.fireTableDataChanged();

  }

  void jMenuItem_FDR_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int selCol = myTable1.getSelectedColumn();
    tm.FDR(selCol);
    tm.fireTableDataChanged();

  }

  void jMenuItem_rowDataNorm_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    tm.rowDataNormalization();
    myTable1.repaint();

  }

  void jMenuItem_avg_selectedRows_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.averageSelectedRows(selectedRow);

  }

  void jMenuItem_subtractRepAvg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.subtractReplicateAverage();

  }

  void jMenuItem_getIntersectionList_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
   File [] f = FileIO.openMultiFiles(true);
   if (f == null ) return;
   int m = tm.getStartDataRow();
   tm.loadFilesToGetMappingOutput(f,"\t",selectedCol,m);


  }

  void jMenuItem_rowData_sub_avg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    tm.rowDataSubtractionAverage();
    myTable1.repaint();

  }

  void jMenuItem_subtractFirstRepavg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.subtractFirstReplicateAverage();

  }

  void jMenuItem_dyeSwapMerged_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    tm.exportDyeSwapMergedFile();

  }

  void jMenuItem_plotThumbnails_rep_actionPerformed(ActionEvent e) {
   int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m= tm.getReplicateRow();
    if (m==-1) return;
    else
      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotThumbnails(selectedRow,false,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.bioRepPlot);
  }

  void jMenuItem_thumbnail_cellline_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
     if (selectedRow == null) return;

     GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
     int m= tm.getReplicateRow();
     if (m==-1) return;
     else
       tm.setReplicateRow(m);

     if (!jCheckBoxMenuItem_xLabel.isSelected())
       tm.setXTicMark(false);
     if (!jCheckBoxMenuItem_yLabel.isSelected())
       tm.setYTicMark(false);
     tm.plotThumbnails(selectedRow,false,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.cellLineProfilePlot);

  }

  void jMenuItem_thumbnails_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
     if (selectedRow == null) return;

     GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
     int m= tm.getReplicateRow();
     if (m==-1) return;
     else
       tm.setReplicateRow(m);

     if (!jCheckBoxMenuItem_xLabel.isSelected())
       tm.setXTicMark(false);
     if (!jCheckBoxMenuItem_yLabel.isSelected())
       tm.setYTicMark(false);
     tm.plotThumbnails(selectedRow,false,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.samplePlot);

  }

  void jMenuItem_reconstitueCellcycle_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    String [][] str = FileIO.loadData("\t");
    tm.purity_linearCombination(str);
    myTable1.repaint();

  }

  void jMenuItem_wg_Correlation_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_subprofiles_correlation(fileName_wo_ext);
//    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    tm.rowData_t_test(fileName_wo_ext);

  }

  void jMenuItem_sameGeneCount_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.sameGeneCount(fileName_wo_ext);

  }

  void jMenuItem_withingroup_avg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.profileReplicateAverage();

  }

  void jMenuItem_TF_correlationTest_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
     DataSet ds = tm.getDataSets();
     if (ds != null)
      new TF_AnalysisFrame(dataPlot,ds,msg).setVisible(true);

  }

  void jMenuItem_resetRowAssignment_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setReplicateRow(-1);
    jTextField_replicate.setText("-1");

    tm.setCellLineNameRow(-1);
    jTextField_bioGroupRow.setText("-1");

    tm.setCellLineProfileRow(-1);
    jTextField_repProfileRow.setText("-1");

    tm.setDyeSwapLabelRow(-1);
     jTextField_dyeSwap.setText("-1");

     tm.setTechReplicateRow(-1);
     jTextField_techRep.setText("-1");

  }

  void jMenuItem_equal_between_group_avg_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.get_equal_between_group_average();

  }

  void jMenuItem_Barplot_w_errorBar_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m= tm.getReplicateRow();
    if (m==-1) return;
    else
      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.barPlot_withErrorBar);

  }

  void jMenuItem_ScatterPlot_w_errorBar_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m= tm.getReplicateRow();
    if (m==-1) return;
    else
      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.scatterPlot_withErrorBar);

  }

  void jMenuItem_peplicatescatterPlotwithError_2_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    int m= tm.getReplicateRow();
//    if (m==-1) return;
//    else
//      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.averagedDat_scatterPlot);

  }

  void jMenuItem_barplot_with_error_2_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    int m= tm.getReplicateRow();
//    if (m==-1) return;
//    else
//      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.plotRow(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.averagedData_barPlot);


  }

  void jMenuItem_twoWayAnova_actionPerformed(ActionEvent e) {


 //   tm.twoWayANOVA(DataSet.cellLineProfilePlot);

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_twoWayANOVA(fileName_wo_ext);

//    int f1 = myTable1.getSelectedRow();
//    int f2 = myTable1.getSelectedRow();
//    jTextField_replicate.setText(f1+"");
//    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    tm.setReplicateRow(n);

//    jTextField_sampleName.setText(n+"");
//    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    tm.twoWayANOVA(f1,f2);
//    tm.setSampleNameRow(n);


  }

  void jMenuItem_highlight_missingValue_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    String [] str = FileIO.loadData(0);
//    int [] index = tm.getConjunctionList(selectedCol,str);
    tm.highlight_missing_value_row();
    myTable1.repaint();

  }

  void jMenuItem_factor3_actionPerformed(ActionEvent e) {
    int n = myTable1.getSelectedRow();
//    jTextField_bioGroupRow.setText(n+"");
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.setFactor3Row(n);

  }

  void jMenuItem_threewayANOVA_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_threeWayANOVA(fileName_wo_ext);

  }

  void jMenuItem_output_MarkedData_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.saveDataColorMarkedData(selectedCol,true);
  }

  void jMenuItem_output_BlackMarkedData_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.saveDataColorMarkedData(selectedCol,false);
  }
  public  String [][] read_excel(File f, String sheetName) {
    Vector v = new Vector();
  Connection c = null;
  Statement stmnt = null;
  try
  {
  Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
  c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + f.getName());
  stmnt = c.createStatement();
  String query = "Select * from [Sheet1$]";

  ResultSet rs = stmnt.executeQuery( query );

  String [] str;
  int colNum=0;
  if (rs.next()) {
  ResultSetMetaData rsmd = rs.getMetaData();
  colNum = rsmd.getColumnCount();
  str = new String[colNum];
  for (int i=1;i<= colNum;i++) {
     str[i] = rsmd.getColumnName(i);
    System.out.println("name "+rsmd.getColumnName(i));
  }
  v.addElement(str);
  str = new String[colNum];
  for (int i=1;i<= colNum;i++){
    str[i] = rs.getString(i);
    System.out.println( rs.getString(i) );
    v.addElement(str);
  }
  }
  while( rs.next() )
  {
    str = new String[colNum];
  for (int i=1;i<= colNum;i++)
    str[i] = rs.getString(i);
//  System.out.println( rs.getString(i) );
  v.addElement(str);
  }
  }
  catch( Exception e )
  {
  System.err.println( e );
  }
  if (v.size()==0)
  return null;
  else {
    Object [] o=v.toArray();
    String [][] out = new String[o.length][];
    for (int i=0;i<o.length;i++)
        out[i] = (String [])o[i];
    return out;
  }
  }

  void jMenuItem_mergeFiles_to_one_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    int startRow = tm.getStartDataRow();
    if (startRow == -1)
     FileIO.saveMergedFiles(f,0,"\t");
    else
      FileIO.saveMergedFiles(f,startRow,"\t");
  }

  void jMenuItem_derivative_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_derivative(fileName_wo_ext);

  }

  void jMenuItem_logisticConversion_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
 //   tm.log2Conversion();
    tm.logisticConversion();
    myTable1.repaint();

  }

  void jMenuItem_derivativeMulptiFile_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = tm.getStartDataRow();
    int startCol = tm.getStartColumn();
    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    if (startRow != -1 && startCol != -1)
     tm.rowData_derivative(f);
//    tm.rowData_derivative(fileName_wo_ext);

  }

  void jMenuItem_insertData_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = tm.getStartDataRow();
    int startCol = tm.getStartColumn();

    String str = jTextField_techRep.getText();
    int colNumber;
    try {
      colNumber = Integer.parseInt(str);
    }
    catch (NumberFormatException ee) {
      return;;
    }
    System.out.println("Extract data from column "+colNumber);


    File [] f = FileIO.openMultiFiles(true);
    if (f == null ) return;
    if (startRow != -1 && startCol != -1)
     tm.insertValues_with_Col_Row_ID(f,colNumber);

  }

  void jMenuItem_logisticFitting_actionPerformed(ActionEvent e) {
//    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    int [] startRows = myTable1.getSelectedRows();
//    for (int i=0;i<startRows.length;i++) {
//      tm.removeRow( startRows[i]);
//    }
//    tm.fireTableDataChanged();
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = myTable1.getSelectedRow();
      tm.rowData_logisticFitting( startRow);

  }

  void jMenuItem_rowDerivative_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = myTable1.getSelectedRow();
 //     tm.removeRow( startRow);
      tm.rowData_derivative( startRow);

  }

  void jMenuItem_rowLogisticConversion_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = myTable1.getSelectedRow();
      tm.rowData_logisticConversion_linearFitting( startRow);

  }

  void jMenuItem_deglitch_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int startRow = myTable1.getSelectedRow();
      tm.rowData_deglitch( startRow);

  }

  void jMenuItem_withingroupVarianceNorm_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1)return;
    m= tm.getReplicateRow();
    if (m==-1) return;
    tm.profileReplicateStandardDeviationNormalization();

  }

  void jMenuItem_containg_all_ls_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.containg_all_less_than(jTextField_repProfileRow.getText());
    myTable1.repaint();

  }
//containg_one_less_than
  void jMenuItem_contain_one_ls_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.containg_one_less_than(jTextField_repProfileRow.getText());
    myTable1.repaint();

  }
/*
  void jMenuItem_car_norm_test_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.rowData_variance_norm_test(fileName_wo_ext);

  }
*/
  void jMenuItem_profileSimilarity_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    int m = tm.getStartDataRow();
    if (m==-1)return;
    m=tm.getStartColumn();
    if (m==-1) return;
    tm.profileSimilarity_in_patterns();
//    tm.rowDataSubtractionAverage();
//    myTable1.repaint();

  }

  void jMenuItem_celllinealignmenttofirstshamaverage_actionPerformed(ActionEvent e) {
    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
    tm.cellLineCorrectionToFirstShamAverage();
    tm.fireTableDataChanged();

  }

  void jMenuItem_pairWiseCorrelation_actionPerformed(ActionEvent e) {
    int [] selectedRow = myTable1.getSelectedRows();
    if (selectedRow == null) return;

    GenericDataTableModel tm = (GenericDataTableModel)myTable1.getModel();
//    int m= tm.getReplicateRow();
//    if (m==-1) return;
//    else
//      tm.setReplicateRow(m);

    if (!jCheckBoxMenuItem_xLabel.isSelected())
      tm.setXTicMark(false);
    if (!jCheckBoxMenuItem_yLabel.isSelected())
      tm.setYTicMark(false);
    tm.pairwiseCorrelation(selectedRow,jCheckBoxMenuItem_blkwht.isSelected(),DataSet.bioRepPlot,f);

  }


}