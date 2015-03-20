package gov.nih.niehs.EPIGseq.datatable;



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
import myutility.numerical.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
import myutility.plot2D.*;
import normalization.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class GenericIntensityDataSetsFrameIO extends JFrame {
  String [] agilentColumnName_minus = {"ProbeName","GeneName","Description","LogRatio","gProcessedSignal",
   "rProcessedSignal","gProcessedSigError","rProcessedSigError","gMeanSignal","rMeanSignal","gNumPix","rNumPix",
   "gBGMeanSignal","rBGMeanSignal"};
  String [] agilentColumnName_plus = {"ProbeName","GeneName","Description","LogRatio","rProcessedSignal",
   "gProcessedSignal","rProcessedSigError","gProcessedSigError","rMeanSignal","gMeanSignal","rNumPix","gNumPix",
   "rBGMeanSignal","gBGMeanSignal"};
  static String polarity = "";
  static String [] assignedColumnName =  {"CLONE","CLUST","TITLE","CAL_RATIO","SAMPLE_MEAN_R",
    "SAMPLE_MEAN_G","SAMPLE_DEV_R","SAMPLE_DEV_G","SAMPLE_TOTAL_R","SAMPLE_TOTAL_G",
    "SAMPLE_SIZE_R","SAMPLE_SIZE_G","BKG_MEAN_R","BKG_MEAN_G","BKG_DEV_R","BKG_DEV_G",
    "SUBARRAY","PLATE_COLUMN","PLATE_ROW","FLAG"};
    static int [] assignedColumnIndex = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    public static int startRow = -1, totalRowNumber = 0;
    static int CLONE = 0;
    static int CLUST = 1;
    static int TITLE = 2;
    static int CAL_RATIO = 3;
    static int SAMPLE_MEAN_R = 4;
    static int SAMPLE_MEAN_G = 5;
    static int SAMPLE_DEV_R = 6;
    static int SAMPLE_DEV_G = 7;
    static int SAMPLE_TOTAL_R = 8;
    static int SAMPLE_TOTAL_G = 9;
    static int SAMPLE_SIZE_R = 10;
    static int SAMPLE_SIZE_G = 11;
    static int BKG_MEAN_R = 12;
    static int BKG_MEAN_G = 13;
    static int BKG_DEV_R = 14;
    static int BKG_DEV_G = 15;
    static int SUBARRAY = 16;
    static int PLATE_COLUMN = 17;
    static int PLATE_ROW = 18;
    static int FLAG = 19;
    private boolean genericColumnAssignment=false;
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenu_File = new JMenu();
    private JMenuItem jMenuItem_close = new JMenuItem();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel_table = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private GenericTable genericTable;
    private JLabel jLabel_fileName = new JLabel();
    private JComboBox jComboBox_fileName = new JComboBox();
    private JButton jButton_displayData = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JButton jButton_save = new JButton();
    private JPopupMenu jPopupMenu_columnNameList = new JPopupMenu();
    private JMenuItem jMenuItem_controlTotalIntensity = new JMenuItem();
    private JMenuItem jMenuItem_treatedTotalIntensity = new JMenuItem();
    private JMenuItem jMenuItem_flag = new JMenuItem();
    private int selectedCol=0;
    private JMenuItem jMenuItem_controlPixelNumber = new JMenuItem();
    private JMenuItem jMenuItem_treatedPixelNumber = new JMenuItem();
    private JTextField popupText;
    private TitledBorder titledBorder1;
    private JLabel jLabel_startLine = new JLabel();
    private JTextField jTextField_startLine = new JTextField();
    private JPopupMenu jPopupMenu_cellTextSelection = new JPopupMenu();
    private JMenuItem jMenuItem_startLineText = new JMenuItem();
    private JButton jButton_normalization = new JButton();
    private ColoredTableCellRendererClassIO [] renderer;
    private MessageBoard msg;
    private File [] f;
    private String [] filename,savedFileName;
    private JPopupMenu jPopupMenu_search = new JPopupMenu();
    private JMenuItem jMenuItem_search = new JMenuItem();
    private SearchStringFrameIO searchStringFrame;
    private JMenuItem jMenuItem_logConversion = new JMenuItem();
    private DecimalFormat df = new DecimalFormat("0.000000");
    private TreeSet conversionColumn = new TreeSet();
    private TitledBorder titledBorder2;
    private boolean saveAll = false;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem_resetColumnName = new JMenuItem();
    private JMenuItem jMenuItem_signNegation = new JMenuItem();
    private JMenuItem jMenuItem_conjunction = new JMenuItem();
    private JMenuItem jMenuItem_negConjunction = new JMenuItem();
    private JMenuItem jMenuItem_removeMarkedRow = new JMenuItem();
    private JMenuItem jMenuItem_removeBlackMarked = new JMenuItem();
    public static String [] genericDataType;
    private JMenu jMenu4 = new JMenu();
    private JMenuItem jMenuItem_scatter = new JMenuItem();
    private JMenu jMenu5 = new JMenu();
    private JMenuItem jMenuItem_sortDescending = new JMenuItem();
    private JMenuItem jMenuItem_sortAscending = new JMenuItem();
    private JMenu jMenu6 = new JMenu();
    private JMenuItem jMenuItem_GeneID = new JMenuItem();
    private JMenuItem jMenuItem_GeneClust = new JMenuItem();
    private JMenuItem jMenuItem_geneTitle = new JMenuItem();
    private JMenu jMenu7 = new JMenu();
    private JMenuItem jMenuItem_treatedIntensityDeviation = new JMenuItem();
    private JMenuItem jMenuItem_controlIntensity = new JMenuItem();
    private JMenuItem jMenuItem_TreatedIntensity = new JMenuItem();
    private JMenuItem jMenuItem_LogRatio = new JMenuItem();
    private JMenuItem jMenuItem_controlIntensityDeviation = new JMenuItem();
    private JMenu jMenu8 = new JMenu();
    private JMenuItem jMenuItem_controlBKGDeviation = new JMenuItem();
    private JMenuItem jMenuItem_treatedBKGDeviation = new JMenuItem();
    private JMenuItem jMenuItem_controlBackgroundIntensity = new JMenuItem();
    private JMenuItem jMenuItem_treatedBackground = new JMenuItem();
    private JMenuItem jMenuItem_undo = new JMenuItem();
    private JMenu jMenu9 = new JMenu();
    private JMenuItem jMenuItem_column = new JMenuItem();
    private JMenuItem jMenuItem_row = new JMenuItem();
    private JMenuItem jMenuItem_subArray = new JMenuItem();
    private String [][] readData;
    private DataPlot dataPlot;
    private JMenu jMenu2 = new JMenu();
    private JCheckBoxMenuItem jCheckBoxMenuItem_plus = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem jCheckBoxMenuItem_minus = new JCheckBoxMenuItem();
    private ButtonGroup buttonGroup= new ButtonGroup();
    private JButton jButton_saveColumnAssignment = new JButton();
    private JLabel jLabel_polarity = new JLabel();
    private JTextField jTextField_polarity = new JTextField();
    private JMenu jMenu_help = new JMenu();
    private JMenuItem jMenuItem_helpNote = new JMenuItem();
    private JButton jButton_loadDesingFile = new JButton();
    private JCheckBoxMenuItem jCheckBoxMenuItem_megaData = new JCheckBoxMenuItem();
    private JMenuItem jMenuItem_unigene = new JMenuItem();
    private JMenuItem jMenuItem_geneBankID = new JMenuItem();
    private JMenuItem jMenuItem_geneName = new JMenuItem();
  private JMenu jMenu3 = new JMenu();
  private JMenuItem jMenuItem_Aminus = new JMenuItem();
  private JMenuItem jMenuItem_Aplus = new JMenuItem();
//  private JCheckBoxMenuItem jCheckBoxMenuItem_JinYohamData = new JCheckBoxMenuItem();
//  private JCheckBoxMenuItem jCheckBoxMenuItem_JinYohamData_Excel = new JCheckBoxMenuItem();
//  private ButtonGroup JinYohamDataGroup = new ButtonGroup();
//  private JCheckBoxMenuItem jCheckBoxMenuItem_agilentData = new JCheckBoxMenuItem();
//  jCheckBoxMenuItem_JinYohamData_Excel
  String fileExtension=null;
  private JMenuItem jMenuItem_JinYohanData = new JMenuItem();
    public GenericIntensityDataSetsFrameIO(File [] file, String _fileExtension, MessageBoard _msg,DataPlot _dataPlot) throws HeadlessException {
      FileIO.GenericIntensityDataSetsFrameIO = datatable.GenericCompiledDataFrameIO.class.getResource("GenericIntensityDataSetsFrameIO.txt");
      msg = _msg;
      fileExtension = _fileExtension;
      dataPlot = _dataPlot;
      f = file;
      filename = new String[f.length];
      savedFileName = new String[f.length];
      for (int i=0;i<f.length;i++) {
        jComboBox_fileName.addItem(f[i].getName());
      }
      if (fileExtension.equals("xls"))
      readData = FileIO.read_excel( f[0], "Sheet1");
      else if (fileExtension.equals("csv")) {
        readData = FileIO.read_csv( f[0]);
      }
       else  {
         readData = FileIO.read_txt(f[0],"\t");
//         readData = FileIO.displayData(FileIO.readFile(f[0]));
       }
      setTableModel(new GenericDataTableModel(readData,null,msg,dataPlot));
      buttonGroup.add(jCheckBoxMenuItem_plus);
      buttonGroup.add(jCheckBoxMenuItem_minus);

      try {
        jbInit();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      if (FileIO.imageIcon != null)
        setIconImage(FileIO.imageIcon.getImage());
      setBounds(0, 0, 600, 680);
      exitConfirm();
    }

    public GenericIntensityDataSetsFrameIO(String fileName,String [][] data,
        String [] columnName,MessageBoard _msg,DataPlot _dataPlot) throws HeadlessException {
      FileIO.GenericIntensityDataSetsFrameIO = datatable.GenericCompiledDataFrameIO.class.getResource("GenericIntensityDataSetsFrameIO.txt");
      msg = new MessageBoard();
      dataPlot=_dataPlot;
      setTableModel(new GenericDataTableModel(data,columnName,msg,dataPlot));
      buttonGroup.add(jCheckBoxMenuItem_plus);
      buttonGroup.add(jCheckBoxMenuItem_minus);
      try {
        jbInit();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      if (FileIO.imageIcon != null)
        setIconImage(FileIO.imageIcon.getImage());
      setBounds(0, 0, 600, 680);
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
      titledBorder2 = new TitledBorder("");
      jMenuItem_close.setText("Close");
      jMenuItem_close.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_close_actionPerformed(e);
        }
      });
      jMenu_File.setText("File");


      jMenuItem_search.setBackground(new Color(220, 220, 220));
      jMenuItem_search.setBorder(null);
      jMenuItem_search.setText("Find");
      jMenuItem_search.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_search_actionPerformed(e);
        }
      });

      this.setTitle("Gene Expression Intensity Data Format Conversion");
      jMenuItem_logConversion.setBackground(Color.lightGray);
      jMenuItem_logConversion.setBorder(null);
      jMenuItem_logConversion.setText("Log2 Converstion");
      jMenuItem_logConversion.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_logConversion_actionPerformed(e);
        }
      });
      jMenuItem_treatedTotalIntensity.setBackground(Color.lightGray);
      jMenuItem_treatedTotalIntensity.setBorder(null);
      jMenuItem_treatedPixelNumber.setBackground(Color.lightGray);
      jMenuItem_treatedPixelNumber.setBorder(null);
      jMenuItem_controlTotalIntensity.setBackground(new Color(220, 220, 220));
      jMenuItem_controlTotalIntensity.setBorder(null);
      jMenuItem_controlPixelNumber.setBackground(new Color(220, 220, 220));
      jMenuItem_controlPixelNumber.setBorder(null);
      jMenuItem_flag.setBackground(Color.lightGray);
      jMenuItem_flag.setBorder(null);
      jPanel_table.setLayout(gridBagLayout3);
      jMenu1.setText("Data Format");
      jMenuItem_resetColumnName.setText("Undo Design Assignment");
      jMenuItem_resetColumnName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_resetColumnName_actionPerformed(e);
        }
      });
      jMenuItem_signNegation.setBackground(new Color(220, 220, 220));
      jMenuItem_signNegation.setText("Sign Negation");
      jMenuItem_signNegation.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_signNegation_actionPerformed(e);
        }
      });
      jMenuItem_conjunction.setBackground(Color.lightGray);
      jMenuItem_conjunction.setText("Load Data Conjunction");
      jMenuItem_conjunction.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_conjunction_actionPerformed(e);
        }
      });
      jMenuItem_negConjunction.setBackground(new Color(220, 220, 220));
      jMenuItem_negConjunction.setText("Load Data Neg. Conjunction");
      jMenuItem_negConjunction.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_negConjunction_actionPerformed(e);
        }
      });
      jMenuItem_removeMarkedRow.setBackground(Color.lightGray);
      jMenuItem_removeMarkedRow.setText("Remove Color Marked Row in List");
      jMenuItem_removeMarkedRow.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_removeMarkedRow_actionPerformed(e);
        }
      });
      jMenuItem_removeBlackMarked.setBackground(new Color(220, 220, 220));
      jMenuItem_removeBlackMarked.setText("Remove Black Marked Row in List");
      jMenuItem_removeBlackMarked.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_removeBlackMarked_actionPerformed(e);
        }
      });
      jMenu4.setText("Plot");
      jMenuItem_scatter.setText("Column Plot");
      jMenuItem_scatter.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_scatter_actionPerformed(e);
        }
      });
      jMenu5.setBackground(Color.lightGray);
      jMenu5.setText("Sorting");
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
      jMenu6.setBackground(Color.lightGray);
      jMenu6.setText("Gene Descriptions");
      jMenuItem_GeneID.setBackground(Color.lightGray);
      jMenuItem_GeneID.setText("Gene ID");
      jMenuItem_GeneID.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_GeneID_actionPerformed(e);
        }
      });
      jMenuItem_GeneClust.setBackground(new Color(220, 220, 220));
      jMenuItem_GeneClust.setText("Gene Clust");
      jMenuItem_GeneClust.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_GeneClust_actionPerformed(e);
        }
      });
      jMenuItem_geneTitle.setBackground(Color.lightGray);
      jMenuItem_geneTitle.setText("Gene Title");
      jMenuItem_geneTitle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_geneTitle_actionPerformed(e);
        }
      });
      jMenu7.setBackground(new Color(220, 220, 220));
      jMenu7.setText("Feature Data");
      jMenuItem_LogRatio.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_LogRatio_actionPerformed(e);
        }
      });
      jMenuItem_LogRatio.setBackground(Color.lightGray);
      jMenuItem_LogRatio.setText("Log Ratio");
      jMenuItem_TreatedIntensity.setBackground(new Color(220, 220, 220));
      jMenuItem_TreatedIntensity.setText("Treated Channel Intensity");
      jMenuItem_TreatedIntensity.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_TreatedIntensity_actionPerformed(e);
        }
      });
      jMenuItem_controlIntensity.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlIntensity_actionPerformed(e);
        }
      });
      jMenuItem_controlIntensity.setBackground(Color.lightGray);
      jMenuItem_controlIntensity.setText("Control Channel Intensity");
      jMenuItem_treatedIntensityDeviation.setBackground(new Color(220, 220, 220));
      jMenuItem_treatedIntensityDeviation.setText("Treated Intensity Deviation");
      jMenuItem_treatedIntensityDeviation.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_treatedIntensityDeviation_actionPerformed(e);
        }
      });
      jMenuItem_controlIntensityDeviation.setBackground(Color.lightGray);
      jMenuItem_controlIntensityDeviation.setText("Control Intensity Deviation");
      jMenuItem_controlIntensityDeviation.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlIntensityDeviation_actionPerformed(e);
        }
      });
      jMenu8.setBackground(Color.lightGray);
      jMenu8.setText("Feature Backgound");
      jMenuItem_treatedBackground.setBackground(Color.lightGray);
      jMenuItem_treatedBackground.setText("Treated Background Intensity");
      jMenuItem_treatedBackground.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_treatedBackground_actionPerformed(e);
        }
      });
      jMenuItem_controlBackgroundIntensity.setBackground(new Color(220, 220, 220));
      jMenuItem_controlBackgroundIntensity.setText("Control Background Intensity");
      jMenuItem_controlBackgroundIntensity.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlBackgroundIntensity_actionPerformed(e);
        }
      });
      jMenuItem_treatedBKGDeviation.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_treatedBKGDeviation_actionPerformed(e);
        }
      });
      jMenuItem_treatedBKGDeviation.setBackground(Color.lightGray);
      jMenuItem_treatedBKGDeviation.setText("Treated Background Deviation");
      jMenuItem_controlBKGDeviation.setBackground(new Color(220, 220, 220));
      jMenuItem_controlBKGDeviation.setText("Control Background Deviation");
      jMenuItem_controlBKGDeviation.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlBKGDeviation_actionPerformed(e);
        }
      });
      jMenuItem_undo.setBackground(new Color(220, 220, 220));
      jMenuItem_undo.setText("Undo Column Assignment");
      jMenuItem_undo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_undo_actionPerformed(e);
        }
      });
      jMenu9.setBackground(new Color(220, 220, 220));
      jMenu9.setText("Print Info");
      jMenuItem_subArray.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_subArray_actionPerformed(e);
        }
      });
      jMenuItem_subArray.setBackground(Color.lightGray);
      jMenuItem_subArray.setText("Sub Array");
      jMenuItem_row.setBackground(new Color(220, 220, 220));
      jMenuItem_row.setText("Print Row");
      jMenuItem_row.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_row_actionPerformed(e);
        }
      });
      jMenuItem_column.setBackground(Color.lightGray);
      jMenuItem_column.setText("Print Column");
      jMenuItem_column.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_column_actionPerformed(e);
        }
      });
      jMenu2.setText("Polarity");
      jCheckBoxMenuItem_plus.setText("Plus (Cy3 treated, cy5 control)");
      jCheckBoxMenuItem_plus.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxMenuItem_plus_actionPerformed(e);
        }
      });
      jCheckBoxMenuItem_minus.setText("Minus (Cy5 control, Cy3 treated)");
      jCheckBoxMenuItem_minus.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxMenuItem_minus_actionPerformed(e);
        }
      });
      jButton_saveColumnAssignment.setText("Save Design File");
      jButton_saveColumnAssignment.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton_saveColumnAssignment_actionPerformed(e);
        }
      });
      jLabel_polarity.setHorizontalAlignment(SwingConstants.RIGHT);
      jLabel_polarity.setText("Polarity:");
      jMenu_help.setText("Help");
      jMenuItem_helpNote.setText("Help Note");
      jMenuItem_helpNote.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_helpNote_actionPerformed(e);
        }
      });
      jButton_loadDesingFile.setText("Load Design File");
      jButton_loadDesingFile.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton_loadDesingFile_actionPerformed(e);
        }
      });
      jCheckBoxMenuItem_megaData.setText("Mega Intensity Data Set");
      jMenuItem_unigene.setBorder(null);
      jMenuItem_unigene.setBackground(new Color(220, 220, 220));
      jMenuItem_unigene.setText("Unigene");
      jMenuItem_unigene.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_unigene_actionPerformed(e);
        }
      });
      jMenuItem_geneBankID.setBackground(Color.lightGray);
      jMenuItem_geneBankID.setBorder(null);
      jMenuItem_geneBankID.setText("GeneBank ID");
      jMenuItem_geneBankID.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_geneBankID_actionPerformed(e);
        }
      });
      jMenuItem_geneName.setText("Gene Name");
      jMenuItem_geneName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_geneName_actionPerformed(e);
        }
      });
      jMenu3.setText("Agilent Data");
    jMenuItem_Aplus.setText("Porarity - Plus");
    jMenuItem_Aplus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_Aplus_actionPerformed(e);
      }
    });
    jMenuItem_Aminus.setText("Porarity - Minus");
    jMenuItem_Aminus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_Aminus_actionPerformed(e);
      }
    });
 //   jCheckBoxMenuItem_JinYohamData.setText("JinYohanData");
 //   jCheckBoxMenuItem_JinYohamData_Excel.setText("JinYohanData - Excel");
 //   jCheckBoxMenuItem_agilentData.setText("Agilent Data");
    jMenuItem_JinYohanData.setText("JinYoHanData");
    jMenuItem_JinYohanData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_JinYohanData_actionPerformed(e);
      }
    });
    jMenuBar1.add(jMenu_File);
      jMenuBar1.add(jMenu1);
      jMenuBar1.add(jMenu4);
      jMenuBar1.add(jMenu_help);
      jMenu_File.add(jMenuItem_close);//    jMenu_utility.add(jMenuItem_stringSearch);
      setJMenuBar(jMenuBar1);
      titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Click on Column and Select Column Name");
      jPanel1.setLayout(gridBagLayout1);
      jPanel1.setPreferredSize(new Dimension(500, 550));
      jPanel2.setPreferredSize(new Dimension(500, 130));
      jPanel2.setToolTipText("");
      jPanel2.setLayout(gridBagLayout2);
      jPanel_table.setPreferredSize(new Dimension(500, 470));
      jScrollPane1.setBorder(titledBorder1);
      jScrollPane1.setPreferredSize(new Dimension(500, 480));
      jLabel_fileName.setPreferredSize(new Dimension(120, 25));
      jLabel_fileName.setHorizontalAlignment(SwingConstants.RIGHT);
      jLabel_fileName.setHorizontalTextPosition(SwingConstants.RIGHT);
      jLabel_fileName.setText("File Name:   ");
      jButton_displayData.setPreferredSize(new Dimension(120, 25));
      jButton_displayData.setText("Display");
      jButton_displayData.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton_displayData_actionPerformed(e);
        }
      });
      genericTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
      jButton_save.setPreferredSize(new Dimension(80, 25));
      jButton_save.setText("Save NMG File");
      jButton_save.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton_save_actionPerformed(e);
        }
      });
      jMenuItem_treatedTotalIntensity.setText("Treated Total Intensity");
      jMenuItem_treatedTotalIntensity.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_treatedTotalIntensity_actionPerformed(e);
        }
      });
      jMenuItem_controlTotalIntensity.setText("Control Total Intensity");
      jMenuItem_controlTotalIntensity.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlTotalIntensity_actionPerformed(e);
        }
      });
      jMenuItem_flag.setText("Flag");
      jMenuItem_flag.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_flag_actionPerformed(e);
        }
      });
      jMenuItem_treatedPixelNumber.setText("Treated Pixel Number");
      jMenuItem_treatedPixelNumber.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_treatedPixelNumber_actionPerformed(e);
        }
      });
      jMenuItem_controlPixelNumber.setText("Control Pixel Number");
      jMenuItem_controlPixelNumber.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_controlPixelNumber_actionPerformed(e);
        }
      });
      jLabel_startLine.setPreferredSize(new Dimension(120, 25));
      jLabel_startLine.setHorizontalAlignment(SwingConstants.RIGHT);
      jLabel_startLine.setHorizontalTextPosition(SwingConstants.RIGHT);
      jLabel_startLine.setText("Data start at line:   ");
      jTextField_startLine.setToolTipText("");
      jTextField_startLine.setActionMap(null);
      jTextField_startLine.setFocusAccelerator(' ');
      jTextField_startLine.setText("10");

      jMenuItem_startLineText.setBackground(Color.lightGray);
      jMenuItem_startLineText.setBorder(null);
      jMenuItem_startLineText.setText("Select Data Start Line");
      jMenuItem_startLineText.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMenuItem_startLineText_actionPerformed(e);
        }
      });
      jButton_normalization.setPreferredSize(new Dimension(120, 25));
      jButton_normalization.setText("Normalization");
      jButton_normalization.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton_normalization_actionPerformed(e);
        }
      });
      this.getContentPane().add(jPanel1, BorderLayout.CENTER);
      jPanel1.add(jPanel2,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
      jPanel2.add(jLabel_startLine,                     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jTextField_startLine,                       new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel1.add(jPanel_table,       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      jPanel_table.add(jScrollPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      jScrollPane1.getViewport().add(genericTable, null);
      jPopupMenu_columnNameList.add(jMenu9);
      jPopupMenu_columnNameList.add(jMenu2);
      jPopupMenu_columnNameList.add(jMenu6);
      jPopupMenu_columnNameList.add(jMenu7);
      jPopupMenu_columnNameList.add(jMenu8);
      jPopupMenu_columnNameList.add(jMenuItem_treatedTotalIntensity);
      jPopupMenu_columnNameList.add(jMenuItem_controlTotalIntensity);
      jPopupMenu_columnNameList.add(jMenuItem_treatedPixelNumber);
      jPopupMenu_columnNameList.add(jMenuItem_controlPixelNumber);
      jPopupMenu_columnNameList.add(jMenuItem_flag);
      jPopupMenu_columnNameList.add(jMenuItem_undo);
      //   jPopupMenu_cellTextSelection.add(jMenuItem_selectBarcode);
      jPopupMenu_cellTextSelection.add(jMenuItem_startLineText);
      jPopupMenu_cellTextSelection.add(jMenuItem_unigene);
      jPopupMenu_cellTextSelection.add(jMenuItem_geneBankID);
      jPopupMenu_cellTextSelection.add(jMenuItem_geneName);
      jPanel2.add(jLabel_fileName,                      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPopupMenu_search.add(jMenuItem_signNegation);
      jPopupMenu_search.add(jMenuItem_logConversion);
      jPopupMenu_search.add(jMenuItem_search);
      jPopupMenu_search.add(jMenu5);
      jPopupMenu_search.add(jMenuItem_conjunction);
      jPopupMenu_search.add(jMenuItem_negConjunction);
      jPopupMenu_search.add(jMenuItem_removeMarkedRow);
      jPopupMenu_search.add(jMenuItem_removeBlackMarked);
      jMenu1.add(jMenuItem_resetColumnName);
      jMenu1.add(jCheckBoxMenuItem_megaData);
    jMenu1.add(jMenu3);
 //   jMenu1.add(jCheckBoxMenuItem_agilentData);
 //   jMenu1.add(jCheckBoxMenuItem_JinYohamData);
    jMenu1.add(jMenuItem_JinYohanData);
//    jMenu1.add(jCheckBoxMenuItem_JinYohamData_Excel);
//    JinYohamDataGroup.add(jCheckBoxMenuItem_JinYohamDataText);
//    JinYohamDataGroup.add(jCheckBoxMenuItem_JinYohamData_Excel);
//    JinYohamDataGroup.add(jCheckBoxMenuItem_agilentData);

      jMenu4.add(jMenuItem_scatter);
      jMenu5.add(jMenuItem_sortAscending);
      jMenu5.add(jMenuItem_sortDescending);
      jMenu6.add(jMenuItem_GeneID);
      jMenu6.add(jMenuItem_GeneClust);
      jMenu6.add(jMenuItem_geneTitle);
      jMenu7.add(jMenuItem_LogRatio);
      jMenu7.add(jMenuItem_TreatedIntensity);
      jMenu7.add(jMenuItem_controlIntensity);
      jMenu7.add(jMenuItem_treatedIntensityDeviation);
      jMenu7.add(jMenuItem_controlIntensityDeviation);
      jMenu8.add(jMenuItem_treatedBackground);
      jMenu8.add(jMenuItem_controlBackgroundIntensity);
      jMenu8.add(jMenuItem_treatedBKGDeviation);
      jMenu8.add(jMenuItem_controlBKGDeviation);
      jMenu9.add(jMenuItem_subArray);
      jMenu9.add(jMenuItem_row);
      jMenu9.add(jMenuItem_column);
      jMenu2.add(jCheckBoxMenuItem_plus);
      jMenu2.add(jCheckBoxMenuItem_minus);
      jPanel2.add(jLabel_polarity,        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jTextField_polarity,       new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jComboBox_fileName,      new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      jPanel2.add(jButton_normalization,    new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jButton_displayData,    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jButton_save,  new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jButton_saveColumnAssignment,  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jPanel2.add(jButton_loadDesingFile,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      jMenu_help.add(jMenuItem_helpNote);
    jMenu3.add(jMenuItem_Aplus);
    jMenu3.add(jMenuItem_Aminus);
    }

    public void setTableData(String [][] str) {
      setTableModel(new GenericDataTableModel(str,null,msg,dataPlot));
    }

    public GenericDataTableModel arrayToModel(String [][] str) {
      return new GenericDataTableModel(str,null,msg,dataPlot);
    }

    private void setTableModelColumnName(int selectedColumn,String newColumnName, GenericDataTableModel model) {
      model.setColumnName(selectedColumn,newColumnName);
      ColumnNameClassIO cnc = model.getColumnNameClass(selectedColumn);
      cnc.setColor(Color.red);
      TableColumnModel colModel = genericTable.getColumnModel();
      TableColumn column = colModel.getColumn(selectedColumn);
      column.setHeaderValue(model.getColumnName(selectedColumn));
      genericTable.getTableHeader().repaint();
      genericTable.tableChanged(new TableModelEvent(model));
      genericTable.repaint();
    }

    private void tableSetup(JTable table) {
      table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 400));
      table.setAutoCreateColumnsFromModel(false);
      table.setCellSelectionEnabled(true);
      table.setColumnSelectionAllowed(true);
      table.setGridColor(Color.blue);
      JTableHeader th = table.getTableHeader();
      table.setRowSelectionAllowed(true);
      table.setSelectionBackground(Color.gray);
      table.setCellSelectionEnabled(true);
      TableColumnModel tcm = table.getColumnModel();
      tcm.setColumnSelectionAllowed(true);
      ColoredTableCellRendererClassIO renderer, headRenderer;
      GenericDataTableModel model = (GenericDataTableModel)table.getModel();
      jPanel_table.getBorder();
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
      genericTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          tableFormMouseClicked(evt);
        }
      }
      );
      JTableHeader header = genericTable.getTableHeader();
      header.setUpdateTableInRealTime(true);
      header.setReorderingAllowed(false);
      header.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          headerformMouseClicked(evt);
        }
      }
      );
      table.validate();
      table.repaint();
    }

    private void setTableModel(GenericDataTableModel model) {
      if (genericTable != null)
        jScrollPane1.getViewport().remove(genericTable);
      genericTable = new GenericTable(model);
      tableSetup(genericTable);
      jScrollPane1.getViewport().add(genericTable,null);
    }

    private void tablemotionformMouseDragged(java.awt.event.MouseEvent evt) {
      if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
          && !jPopupMenu_search.isShowing())
        jPopupMenu_cellTextSelection.show( genericTable, evt.getX(), evt.getY() );
    }

    private void tablemotionFormMouseMoved(java.awt.event.MouseEvent evt) {
      if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
          && !jPopupMenu_search.isShowing())
        jPopupMenu_cellTextSelection.show( genericTable, evt.getX(), evt.getY() );
    }

    private void headerformMouseClicked(java.awt.event.MouseEvent evt) {
      if(SwingUtilities.isLeftMouseButton(evt)){
        TableColumnModel colModel = genericTable.getColumnModel();
        int columnModelIndex = colModel.getColumnIndexAtX(evt.getX());
        selectedCol = colModel.getColumn(columnModelIndex).getModelIndex();
        jPopupMenu_columnNameList.show( genericTable, evt.getX(), evt.getY() );
      }
      else if (SwingUtilities.isRightMouseButton(evt)) {
        TableColumnModel colModel = genericTable.getColumnModel();
        int columnModelIndex = colModel.getColumnIndexAtX(evt.getX());
        selectedCol = colModel.getColumn(columnModelIndex).getModelIndex();
        jPopupMenu_search.show( genericTable, evt.getX(), evt.getY() );
      }
    }

    private void tableFormMouseClicked(java.awt.event.MouseEvent evt) {
      if (SwingUtilities.isRightMouseButton(evt)) {
        if (!jPopupMenu_columnNameList.isShowing() && !jPopupMenu_cellTextSelection.isShowing()
            && !jPopupMenu_search.isShowing()) {
          jPopupMenu_cellTextSelection.show( genericTable, evt.getX(), evt.getY() );
        }
      }
    }

    void jButton_displayData_actionPerformed(ActionEvent e) {
      int i = jComboBox_fileName.getSelectedIndex();
      readData = FileIO.displayData(FileIO.readFile(f[i]),"\t");
      setTableModel(new GenericDataTableModel(readData,null,msg,dataPlot));
    }

    void jButton_save_actionPerformed(ActionEvent e) {
      if (GenericIntensityDataSetsFrameIO.startRow == -1) {
        return;
      }
      saveData();
      saveAll = true;
    }

    public void saveData() {
      savedFileName = new String[f.length];
      int num = 0;
      for (int i=0;i<assignedColumnIndex.length;i++)
        if (assignedColumnIndex[i] != -1)
          num++;
      String [] columnname = new String[num];
      int [] columnindex = new int[num];
      num=0;
      for (int i=0;i<assignedColumnIndex.length;i++)
        if (assignedColumnIndex[i] != -1) {
      columnname[num] = assignedColumnName[i];
      columnindex[num] = assignedColumnIndex[i];
      num++;
        }
        for (int i=0;i<f.length;i++) {
          if (i==0) {
            savedFileName[i] = saveData(f[i],readData,columnname,columnindex);
          }
          else {
            readData = FileIO.displayData(FileIO.readFile(f[i]),"\t");
            savedFileName[i] = saveData(f[i],readData,columnname,columnindex);
          }
          Display.display(savedFileName[i] + " was saved.");
        }
    }
    private String [][] convertData( Vector v_data) {
      Object [] o = v_data.toArray();
      String [][] str = new String[o.length][];
      for (int i=0;i<o.length;i++)
         str[i] = (String[])o[i];
      return DataConversion.transposeString(str);
    }
    public void compile_Data_JinHoham_excel() {
      File outputFile = FileIO.saveSingleFile();
      String fileName;
     if (outputFile==null) return;

      String [][] inputData,output;
      Vector curr = new Vector();
      String rowName=null, thisRowName=null;
      try {
        FileWriter wrt = new FileWriter(new File(outputFile.getParent(),outputFile.getName()));
        PrintWriter out = new PrintWriter(wrt);
        out.print("FileName\tRowName\tColumnNumber\tData\n");

      for (int i=0;i<f.length;i++) {
//        public static String [][] read_excel(String filename, String sheetName) {
        fileName = f[i].getName();
        inputData=FileIO.read_excel(f[i],"Sheet1");

//        inputData=FileIO.displayData(FileIO.readFile(f[i]));
        for (int j=0;j<inputData.length;j++) {
          if (j==inputData.length-1) {
 //           System.out.println("writing "+inputData[j][0]);
           output = convertData(curr);
          for (int k=1;k<output.length;k++) {
            out.print(fileName+"\t"+rowName+"\t"+k+"\t");
            for (int n=0;n<output[k].length;n++) {
              if (n==output[k].length-1)
                out.print(output[k][n]+"\n");
                else
                  out.print(output[k][n]+"\t");
            }
          }
          curr.removeAllElements();

          }
          else if (inputData[j][0].equals("A")||inputData[j][0].equals("B")||inputData[j][0].equals("C")
              || inputData[j][0].equals("D")||inputData[j][0].equals("E")||inputData[j][0].equals("F")
              || inputData[j][0].equals("G")||inputData[j][0].equals("H")) {
   //       System.out.println(inputData[j][0]);
          rowName = inputData[j][0];
           if (!curr.isEmpty()) {
    //          System.out.println("writing "+inputData[j][0]);
             output = convertData(curr);
            for (int k=1;k<output.length;k++) {
              out.print(thisRowName+"\t"+k+"\t");
              for (int n=0;n<output[k].length;n++) {
                if (n==output[k].length-1)
                  out.print(output[k][n]+"\n");
                  else
                    out.print(output[k][n]+"\t");
              }
            }
            curr.removeAllElements();
            }
          }
          else if (rowName != null) {
            thisRowName = rowName;
//            System.out.print("\t"+j);
            curr.add(inputData[j]);
          }
        }
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      msg.messageDisplay(e.getMessage()+"\n");
    }

    }
    public void compile_Data_JinHoham_text() {
      File outputFile = FileIO.saveSingleFile();
      String fileName;
      if (outputFile==null) return;

      String [][] inputData,output;
      Vector curr = new Vector();
      String rowName=null, thisRowName=null;
      try {
        FileWriter wrt = new FileWriter(new File(outputFile.getParent(),outputFile.getName()));
        PrintWriter out = new PrintWriter(wrt);
        out.print("FileName\tRowName\tColumnNumber\tData\n");

      for (int i=0;i<f.length;i++) {
//        public static String [][] read_excel(String filename, String sheetName) {
        fileName = f[i].getName();

        inputData=FileIO.read_txt(f[i],"\t");
        for (int j=0;j<inputData.length;j++) {
          if (j==inputData.length-1) {
   //         System.out.println("writing "+inputData[j][0]);
           output = convertData(curr);
          for (int k=1;k<output.length;k++) {
            out.print(fileName+"\t"+rowName+"\t"+k+"\t");
            for (int n=0;n<output[k].length;n++) {
              if (n==output[k].length-1)
                out.print(output[k][n]+"\n");
                else
                  out.print(output[k][n]+"\t");
            }
          }
          curr.removeAllElements();

          }
          else if (inputData[j][0].equals("A")||inputData[j][0].equals("B")||inputData[j][0].equals("C")
              || inputData[j][0].equals("D")||inputData[j][0].equals("E")||inputData[j][0].equals("F")
              || inputData[j][0].equals("G")||inputData[j][0].equals("H")) {
   //       System.out.println(inputData[j][0]);
          rowName = inputData[j][0];
           if (!curr.isEmpty()) {
  //            System.out.println("writing "+inputData[j][0]);
             output = convertData(curr);
            for (int k=1;k<output.length;k++) {
              out.print(thisRowName+"\t"+k+"\t");
              for (int n=0;n<output[k].length;n++) {
                if (n==output[k].length-1)
                  out.print(output[k][n]+"\n");
                  else
                    out.print(output[k][n]+"\t");
              }
            }
            curr.removeAllElements();
            }
          }
          else if (rowName != null) {
            thisRowName = rowName;
 //           System.out.print("\t"+j);
            curr.add(inputData[j]);
          }
        }
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      msg.messageDisplay(e.getMessage()+"\n");
    }

    }
    public void compile_Data_JinHoham_csv() {
      File outputFile = FileIO.saveSingleFile();
      String fileName1 = outputFile.getName();
      java.util.StringTokenizer token = new java.util.StringTokenizer(fileName1,".");
      String fileName_wo_ext = token.nextToken();
      if (outputFile==null) return;
      String fileName2,SquenceID;
      String [][] inputData,output;
      Vector curr = new Vector();
      String rowName, thisRowName, thisSquenceID;
      try {
        FileWriter wrt = new FileWriter(new File(outputFile.getParent(),outputFile.getName()));
        PrintWriter out = new PrintWriter(wrt);
        out.print("Plate\tSequential ID\tRowName\tColumnNumber\tData\n");
      for (int i=0;i<f.length;i++) {
//         fileName = f[i].getName();
         rowName=null;
         thisRowName=null;
         thisSquenceID=null;
         fileName2 =  f[i].getName();
         java.util.StringTokenizer token2 = new java.util.StringTokenizer(fileName2,"(");
         SquenceID = token2.nextToken();
         SquenceID = token2.nextToken();
         token2 = new java.util.StringTokenizer(SquenceID,")");
         SquenceID = token2.nextToken();
         token2 = new java.util.StringTokenizer(SquenceID,"=");
         SquenceID = token2.nextToken();
         SquenceID = token2.nextToken();

        inputData=FileIO.read_csv(f[i]);
        curr.removeAllElements();
        for (int j=0;j<inputData.length;j++) {
          if (j==inputData.length-1) {
            curr.add(inputData[j]);
           output = convertData(curr);
          for (int k=1;k<output.length;k++) {
            out.print(fileName_wo_ext+"\t"+SquenceID+"\t"+rowName+"\t"+k+"\t");
            for (int n=0;n<output[k].length;n++) {
              if (n==output[k].length-1)
                out.print(output[k][n]+"\n");
                else
                  out.print(output[k][n]+"\t");
            }
          }
          curr.removeAllElements();

          }
          else if (inputData[j][0].equals("A")||inputData[j][0].equals("B")||inputData[j][0].equals("C")
              || inputData[j][0].equals("D")||inputData[j][0].equals("E")||inputData[j][0].equals("F")
              || inputData[j][0].equals("G")||inputData[j][0].equals("H")) {
          rowName = inputData[j][0];
           if (!curr.isEmpty()) {
             output = convertData(curr);
            for (int k=1;k<output.length;k++) {
              out.print(fileName_wo_ext+"\t"+thisSquenceID+"\t"+thisRowName+"\t"+k+"\t");
              for (int n=0;n<output[k].length;n++) {
                if (n==output[k].length-1)
                  out.print(output[k][n]+"\n");
                  else
                    out.print(output[k][n]+"\t");
              }
            }
            curr.removeAllElements();
            }
          }
          else if (rowName != null) {
            thisRowName = rowName;
            thisSquenceID = SquenceID;
 //           System.out.print("\t"+j);
            curr.add(inputData[j]);
          }
        }
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      msg.messageDisplay(e.getMessage()+"\n");
    }

    }

    public String saveData(File file, String [][] s,String [] columnname,int [] columnindex) {
      String fileName = file.getName();
      if (!polarity.equals("minus") && !polarity.equals("plus")) return fileName;
      if (polarity.equals("minus")) {
        float d = 0;
        if ( columnindex.length > CAL_RATIO &&            columnindex[CAL_RATIO] != -1) {
          for (int i=startRow;i<s.length;i++) {
            try {
              d  = Float.parseFloat(s[i][assignedColumnIndex[CAL_RATIO]]);
              d *= -1;

            }
            catch (NumberFormatException ee) {
              d =0;
            }
            s[i][assignedColumnIndex[CAL_RATIO]] = d+"";
          }
        }
      }
      StringTokenizer parser = new StringTokenizer(fileName, ".");
      if (parser.hasMoreTokens())
        fileName = parser.nextToken() + "_nmg.txt";
      else
        fileName += ".txt";
      try {
        FileWriter wrt = new FileWriter(new File(file.getParent(),fileName));
        PrintWriter out = new PrintWriter(wrt);
        out.print(fileName +"\t"+(s.length-startRow)+ "\n");
        for (int j=0;j<columnname.length;j++) {
          if ( j == columnname.length-1) {
            out.print(columnname[j] + "\n");
          }
          else  {
            out.print(columnname[j] + "\t");
          }
        }
        for (int i=startRow;i<s.length;i++) {
          for (int j=0;j<columnname.length;j++) {
            if (j == columnname.length-1)
              out.print(s[i][columnindex[j]] + "\n");
            else  {
              out.print(s[i][columnindex[j]] + "\t");
            }
          }
        }
        wrt.flush();
        wrt.close();
      }
      catch (IOException e)
      {
        msg.messageDisplay(e.getMessage()+"\n");
      }
      return fileName;
    }

    void jMenuItem_undo_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      TableColumnModel tcm = genericTable.getColumnModel();
      TableColumn column = tcm.getColumn(selectedCol);
      ColoredTableCellRendererClassIO headRenderer = (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
      headRenderer.setForeground(Color.BLACK);
      setTableModelColumnName(selectedCol,"column "+selectedCol,tm);
    }

    private void columnAssignment(int selectedCol, int columnIndex) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      TableColumnModel tcm = genericTable.getColumnModel();
      TableColumn column = tcm.getColumn(selectedCol);
      ColoredTableCellRendererClassIO headRenderer = (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
      headRenderer.setForeground(Color.blue);
      setTableModelColumnName(selectedCol,assignedColumnName[columnIndex],tm);
      assignedColumnIndex[columnIndex] = selectedCol;
    }

    void jMenuItem_GeneID_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,CLONE);
    }

    void jMenuItem_GeneClust_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,CLUST);

    }

    void jMenuItem_geneTitle_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,TITLE);
    }
    void jMenuItem_LogRatio_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,CAL_RATIO);
    }

    void jMenuItem_TreatedIntensity_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_MEAN_R);
    }

    void jMenuItem_controlIntensity_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_MEAN_G);
    }

    void jMenuItem_treatedIntensityDeviation_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_DEV_R);
    }

    void jMenuItem_controlIntensityDeviation_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_DEV_G);
    }

    void jMenuItem_treatedTotalIntensity_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_TOTAL_R);
    }

    void jMenuItem_controlTotalIntensity_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_TOTAL_G);
    }

    void jMenuItem_treatedBackground_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,BKG_MEAN_R);
    }

    void jMenuItem_controlBackgroundIntensity_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,BKG_MEAN_G);
    }

    void jMenuItem_treatedBKGDeviation_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,BKG_DEV_R);
    }

    void jMenuItem_controlBKGDeviation_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,BKG_DEV_G);
    }

    void jMenuItem_subArray_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SUBARRAY);
    }

    void jMenuItem_column_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,PLATE_COLUMN);
    }

    void jMenuItem_row_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,PLATE_ROW);
    }

    void jMenuItem_flag_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,FLAG);
    }

    void jMenuItem_controlPixelNumber_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_SIZE_G);
    }

    void jMenuItem_treatedPixelNumber_actionPerformed(ActionEvent e) {
      columnAssignment(selectedCol,SAMPLE_SIZE_R);
    }

    void jMenuItem_startLineText_actionPerformed(ActionEvent e) {
      startRow = genericTable.getSelectedRow();
      jTextField_startLine.setText(startRow+"");

    }

    void jButton_normalization_actionPerformed(ActionEvent e) {
      toNormalization();
    }

    private void toNormalization() {
      if (saveAll) {
        new SVNFrame(f[0].getParent(),savedFileName,msg,dataPlot,jCheckBoxMenuItem_megaData.isSelected());
      }
    }

    public JTable getTable() {
      return genericTable;
    }

    public void updateStringSearch(int [] findIndex) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      int totalRowNum = tm.getRowCount();
      Object obj;
      ColorStringClassIO csc;
      for (int i=0;i<totalRowNum;i++) {
        csc = (ColorStringClassIO)tm.getValueAt(i,selectedCol);
        if (csc!=null)
          csc.setColor(Color.black);
      }
      if (findIndex == null) {
        genericTable.repaint();
        return;
      }
      int startDataRow = tm.getStartDataRow();
      for (int i=0;i<findIndex.length;i++) {
        if(findIndex[i] > startDataRow) {
          csc = (ColorStringClassIO)tm.getValueAt(findIndex[i],selectedCol);
          csc.setColor(Color.red);
        }
      }
      genericTable.repaint();
    }

    public void initialStringSearch() {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
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

    void jMenuItem_logConversion_actionPerformed(ActionEvent e) {
      conversionColumn.add(selectedCol+"");
      logConversion(selectedCol);
    }

    private void logConversion(int selCol) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      int rowNum = tm.getRowCount();
      int startLine = tm.getStartDataRow();
      if (startLine == -1 ) startLine = 0;
      ColorStringClassIO csc;
      for (int i=startLine;i<rowNum;i++) {
        csc = (ColorStringClassIO)tm.getValueAt(i,selCol);
        if (csc != null) {
          Object o = csc.getData();
          if (o != null) {
            try {
              float d  = Float.parseFloat((String)o);
              if (d > 0) {
                d = (float)Math.log(d)/ConstantValue.log2;
                csc.setData(df.format(d)+"");
              }
            }
            catch (NumberFormatException ee) {
            }
          }
        }
      }
      genericTable.repaint();
    }

    void jMenuItem_resetColumnName_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      TableColumnModel tcm = genericTable.getColumnModel();
      for (int i=0;i<tm.getColumnCount();i++) {
        TableColumn column = tcm.getColumn(i);
        ColoredTableCellRendererClassIO headRenderer =
            (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
        headRenderer.setForeground(Color.lightGray);
        setTableModelColumnName(i,"column "+i,tm);
      }
      for (int i=0;i<assignedColumnIndex.length;i++)
        assignedColumnIndex[i] = -1;
      startRow = -1;
      jTextField_startLine.setText(startRow+"");
      polarity = "";
      jTextField_polarity.setText(polarity);
    }

    private void highLightHeader(int selectedCol) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      TableColumnModel tcm = genericTable.getColumnModel();
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
    }

    void jMenuItem_signNegation_actionPerformed(ActionEvent e) {
      negationConversion(selectedCol);
    }

    private void negationConversion(int selCol) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      int rowNum = tm.getRowCount();
      int startLine = tm.getStartDataRow();
      if (startLine == -1 ) startLine = 0;
      ColorStringClassIO csc;
      for (int i=startLine;i<rowNum;i++) {
        csc = (ColorStringClassIO)tm.getValueAt(i,selCol);
        if (csc != null) {
          Object o = csc.getData();
          if (o != null) {
            try {
              float d  = Float.parseFloat((String)o);
              d = -d;
              csc.setData(df.format(d)+"");
            }
            catch (NumberFormatException ee) {
            }
          }
        }
      }
      genericTable.repaint();
    }

    private void macro_negationConversion(int selCol,GenericDataTableModel tm) {
      int rowNum = tm.getRowCount();
      int startLine = tm.getStartDataRow();
      if (startLine == -1 ) startLine = 0;
      ColorStringClassIO csc;
      for (int i=startLine;i<rowNum;i++) {
        csc = (ColorStringClassIO)tm.getValueAt(i,selCol);
        if (csc != null) {
          Object o = csc.getData();
          if (o != null) {
            try {
              float d  = Float.parseFloat((String)o);
              d = -d;
              csc.setData(df.format(d)+"");
            }
            catch (NumberFormatException ee) {
            }
          }
        }
      }
    }

    void jMenuItem_conjunction_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      String [] str = FileIO.loadData(0,"\t");
      int [] index = tm.getConjunctionList(selectedCol,str);
      tm.updateStringSearch(selectedCol,index);
      genericTable.repaint();
    }

    void jMenuItem_negConjunction_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      String [] str = FileIO.loadData(0,"\t");
      int [] index = tm.getNegConjunctionList(selectedCol,str);
      tm.updateStringSearch(selectedCol,index);
      genericTable.repaint();

    }

    void jMenuItem_removeMarkedRow_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      tm.removeMarked(selectedCol);
      genericTable.repaint();
    }

    void jMenuItem_removeBlackMarked_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      tm.removeUnmarked(selectedCol);
      genericTable.repaint();
    }

    void jMenuItem_loadColumnNameMinus_actionPerformed(ActionEvent e) {
      loadColumnName();
    }

    private void loadColumnName() {
      File file = FileIO.openSingleFile();
      if (file == null ) return;
      String [][] str = FileIO.displayData(FileIO.readFile(file),"\t");
      msg.display("Column Assignment",str);
      if (str == null) return;
      if (str.length<2) {
        msg.display("Column Assignment file has no Column Assignment.");
        return;

      }
      for (int i=0;i<str.length;i++) {
        for (int j=0;j<assignedColumnName.length;j++) {
          if (assignedColumnName[j].equals(str[i][0])) {
            try {
              assignedColumnIndex[j]  = Integer.parseInt(str[i][1]);
              msg.display(assignedColumnName[j]+"\t" + assignedColumnIndex[j]);
            }
            catch (NumberFormatException ee) {
              msg.display("\"Column Assignment\" is not a number.");
              return;
            }
          }
          else if (str[i][0].equals("Data Start Line")) {
            try {
              startRow  = Integer.parseInt(str[i][1]);
              jTextField_startLine.setText(startRow+"");
              msg.display("Data Start Line\t" + startRow);
            }
            catch (NumberFormatException ee) {
              startRow = -1;
              msg.display("\"Data Start Line\" is not a number.");
              return;
            }
          }
          else if (str[i][0].equals("Polarity")) {
            polarity = str[i][1];
            if (polarity.equals("minus"))
              jCheckBoxMenuItem_minus.setSelected(true);
            else if (polarity.equals("plus"))
              jCheckBoxMenuItem_plus.setSelected(true);
            jTextField_polarity.setText(polarity);
            msg.display("Polarity\t" + polarity);
          }
        }
      }
      assignColumnName();
    }

    void jMenuItem_scatter_actionPerformed(ActionEvent e) {
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      DataSet ds = tm.getDataSets();
      new AffyDataSVNFrame(dataPlot,ds,tm,msg).setVisible(true);

    }

    void jMenuItem_sortAscending_actionPerformed(ActionEvent e) {
      System.out.println("selectedCol "+selectedCol);
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      tm.sorting(selectedCol,startRow,false);
      tm.fireTableDataChanged();
    }

    void jMenuItem_sortDescending_actionPerformed(ActionEvent e) {
      System.out.println("selectedCol "+selectedCol);
      GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
      tm.sorting(selectedCol,startRow,true);
      tm.fireTableDataChanged();

    }

    void jButton_saveColumnAssignment_actionPerformed(ActionEvent e) {
      File f = FileIO.saveSingleFile();
      float testValue;
      if (f == null) return;
      try {
        FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
        PrintWriter out = new PrintWriter(wrt);
        out.print("Polarity\t"+polarity + "\n");
        out.print("Data Start Line\t"+startRow + "\n");
        for (int i=0;i<assignedColumnName.length;i++) {
          if (assignedColumnIndex[i] != -1)
            out.print(assignedColumnName[i] + "\t"+ assignedColumnIndex[i] + "\n");
        }
        wrt.flush();
        wrt.close();
      }
      catch (IOException ee)
      {
        System.out.println(ee.getMessage()+"\n");
      }
    }

    void jCheckBoxMenuItem_plus_actionPerformed(ActionEvent e) {
      polarity = "plus";
      jTextField_polarity.setText(polarity);
    }

    void jCheckBoxMenuItem_minus_actionPerformed(ActionEvent e) {
      polarity = "minus";
      jTextField_polarity.setText(polarity);
    }

    void jMenuItem_helpNote_actionPerformed(ActionEvent e) {
      String ss = FileIO.docPath+"help\\GenericIntensityDataSets.pdf";
      FileIO.openPDF(ss);
    }

    void jButton_loadDesingFile_actionPerformed(ActionEvent e) {
      loadColumnName();
    }

    void jMenuItem_geneName_actionPerformed(ActionEvent e) {
      int  selRow =genericTable.getSelectedRow();
      int  selCol =genericTable.getSelectedColumn();
      String str = genericTable.getValueAt(selRow,selCol).toString();
      if (str != null) {
        Display.GenName(str);
      }
    }

    void jMenuItem_unigene_actionPerformed(ActionEvent e) {
      int selRow =genericTable.getSelectedRow();
      int selCol = genericTable.getSelectedColumn();
      ColorStringClassIO csc;
      csc = (ColorStringClassIO)genericTable.getValueAt(selRow,selCol);
      if (csc!=null) {
        Display.ie(csc.toString());
      }
      genericTable.clearSelection();
    }

    void jMenuItem_geneBankID_actionPerformed(ActionEvent e) {
      int  selRow =genericTable.getSelectedRow();
      int  selCol =genericTable.getSelectedColumn();
      String str = genericTable.getValueAt(selRow,selCol).toString();
      if (str != null) {
        Display.ie(str);
      }
    }
private void agilent_plus() {
  polarity = "plus";
  jCheckBoxMenuItem_plus.setSelected(true);
  jTextField_polarity.setText(polarity);
 GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
 int numRow = tm.getRowCount();
 ColorStringClassIO csc;
 for (int i=0;i<numRow;i++) {
   csc = (ColorStringClassIO)tm.getValueAt(i,0);
   if (csc!=null) {
     Object o = csc.getData();
     String str = (String)o;
     if (str.equals("FEATURES")) {
       startRow  = i+1;
       jTextField_startLine.setText(startRow+"");
       break;
     }
   }
 }
 int numCol = tm.getColumnCount();
 for (int i=0;i<numCol;i++) {
   csc = (ColorStringClassIO)tm.getValueAt(startRow-1,i);
   if (csc != null) {
     Object o = csc.getData();
     String str = A_plus((String)o);
     if (str!=null)  {
     for (int j=0;j<assignedColumnName.length;j++) {
       if (assignedColumnName[j].equals(str)) {
          assignedColumnIndex[j] = i;
       }
     }
   }
   }
 }
 assignColumnName();

}
private void agilent_minus() {
  polarity = "minus";
  jCheckBoxMenuItem_minus.setSelected(true);
 jTextField_polarity.setText(polarity);
 GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
 int numRow = tm.getRowCount();
 ColorStringClassIO csc;
 for (int i=0;i<numRow;i++) {
   csc = (ColorStringClassIO)tm.getValueAt(i,0);
   if (csc!=null) {
     Object o = csc.getData();
     String str = (String)o;
     if (str.equals("FEATURES")) {
       startRow  = i+1;
       jTextField_startLine.setText(startRow+"");
       break;
     }
   }
 }
 int numCol = tm.getColumnCount();
 for (int i=0;i<numCol;i++) {
   csc = (ColorStringClassIO)tm.getValueAt(startRow-1,i);
   if (csc != null) {
     Object o = csc.getData();
     String str = A_minus((String)o);
     if (str!=null)  {
     for (int j=0;j<assignedColumnName.length;j++) {
       if (assignedColumnName[j].equals(str)) {
          assignedColumnIndex[j] = i;
       }
     }
   }
   }
 }
 assignColumnName();
}
private void assignColumnName() {
  GenericDataTableModel tm = (GenericDataTableModel)genericTable.getModel();
  TableColumnModel tcm = genericTable.getColumnModel();
 for (int i=0;i<assignedColumnName.length;i++) {
   if (assignedColumnIndex[i] != -1) {
     TableColumn column = tcm.getColumn(assignedColumnIndex[i]);
     ColoredTableCellRendererClassIO headRenderer =
         (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
     headRenderer.setForeground(Color.blue);
     setTableModelColumnName(assignedColumnIndex[i],assignedColumnName[i],tm);
   }
 }

}
private String A_minus(String s) {
  for (int i=0;i<agilentColumnName_minus.length;i++) {
    if (s.equals(agilentColumnName_minus[i])) {
      return assignedColumnName[i];
    }
  }
  return null;
}
private String A_plus(String s) {
  for (int i=0;i<agilentColumnName_plus.length;i++) {
    if (s.equals(agilentColumnName_plus[i])) {
      return assignedColumnName[i];
    }
  }
  return null;
}

  void jMenuItem_Aplus_actionPerformed(ActionEvent e) {
      agilent_plus();
  }

  void jMenuItem_Aminus_actionPerformed(ActionEvent e) {
     agilent_minus();
  }

  void jMenuItem_JinYohanData_actionPerformed(ActionEvent e) {
      if (fileExtension.equals("txt")) {  //jCheckBoxMenuItem_JinYohamData_Excel
        compile_Data_JinHoham_text();
      }
      else if (fileExtension.equals("xls")) {  //jCheckBoxMenuItem_JinYohamData_Excel
        compile_Data_JinHoham_excel();
      }
      else if (fileExtension.equals("csv")) {  //jCheckBoxMenuItem_JinYohamData_Excel
        compile_Data_JinHoham_csv();
      }
      else return;

  }
}