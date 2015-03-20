package gov.nih.niehs.EPIGseq.ExpressionPredictor;


import gov.nih.niehs.EPIGseq.util.VennDiagram;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import java.sql.*;
import java.util.zip.*;
import javax.swing.border.*;
import java.util.Random;
import gov.nih.niehs.EPIGseq.myutility.statistics.*;
import gov.nih.niehs.EPIGseq.myutility.misc.*;
import gov.nih.niehs.EPIGseq.myutility.io.*;
import gov.nih.niehs.EPIGseq.myutility.plot2D.*;
import normalization.*;
import gov.nih.niehs.EPIGseq.datatable.*;
import java.net.URL;
import java.text.DateFormat;
import java.net.MalformedURLException;
import gov.nih.niehs.EPIGseq.myutility.numerical.*;
import analysis.epig.*;
import classification.*;
import analysis.SVM.svmjava.*;
import analysis.bicluster.*;
import analysis.geneontology.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
public class ExpressionPredictorFrame extends JFrame {
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel_versionInfo = new JPanel();
  private MessageBoard msg;
  private boolean validConnection;
  protected Connection con = null;
  protected SVNDataset [] QRH;
  protected SVNDataset qrh;
//  private String version,smallImage,logoImage;
  private String version;
  private Vector [] dataHolder;

  private JPanel jPanel_logo;
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel_niehs = new JLabel();
  private JLabel jLabel_nmcGroup = new JLabel();
  private JLabel jLabel_version = new JLabel();
  private JLabel jLabel_predictor = new JLabel();
  private JLabel jLabel_exp = new JLabel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenu jMenu3 = new JMenu();
  private JMenuItem jMenuItem_exit = new JMenuItem();
  private JMenu jMenu4 = new JMenu();
  private JMenuItem jMenuItem_help = new JMenuItem();
  private TitledBorder titledBorder1;
  private TitledBorder titledBorder2;
  private JButton jButton_logRatio = new JButton();
  private JButton jButton_intensity = new JButton();
  private JPanel jPanel2 = new JPanel();
  private JButton jButton_generic = new JButton();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private JButton jButton_preprocess = new JButton();
  private JMenuItem jMenuItem_gc = new JMenuItem();
//  DataPlot new DataPlot(_msg)
   private   DataPlot dataPlot;
  private JCheckBoxMenuItem jCheckBoxMenuItem_megaDataSet = new JCheckBoxMenuItem();
  private JMenuItem jMenuItem_simulationData = new JMenuItem();
  private JMenuItem jMenuItem_load_TF_gene_matrix = new JMenuItem();
//  private JMenuItem jMenuItem_load_fileNameMapping = new JMenuItem();
//  private JMenuItem jMenuItem_loadFileNameCahnge = new JMenuItem();
//  Properties fileNameMapping;
//  private JMenuItem jMenuItem_getFileName = new JMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem_EPIG = new JCheckBoxMenuItem();
  private JMenuItem jMenuItem_SVM = new JMenuItem();
  private JMenuItem jMenuItem_BiCluster = new JMenuItem();
  private JRadioButtonMenuItem jRadioButtonMenuItem_compiledDataWithoutDisplay = new JRadioButtonMenuItem();

  public ExpressionPredictorFrame() throws HeadlessException {
//    public static float FisherExactProbability (int selected_list_hit_term,
//    int selected_list_hit_ALL, int bkg_list_hit_term, int bkg_list_hit_All) {

//   System.out.println("factorial = "+Statistics.factorial(15,1));
//   System.out.println("factorial_log = "+Statistics.factorial_log(15,1));
//   System.out.println("FisherExactProbability = "+Statistics.FisherExactProbability(1,37,1620,15360));
//   System.out.println("FisherExactProbability = "+Statistics.FisherExactProbability(4,9,631,9317));
 //  System.out.println("FisherExactProbability = "+Statistics.FisherExactProbability(4,9,702,9317));
 //  System.out.println("FisherExactProbability = "+Statistics.FisherExactProbability(4,9,757,9317));
//    int listB_gene_number_hit_GO_BP_or_MF_or_CC_ALL_in_a_given_genelist,

//    int listC_gene_number_hit_particular_GO_term,
//   int listD_gene_number_background_belong_to_GO_term,
//  int listE_total_gene_number


/*
    Probe_Ontology probe_Ontology = new Probe_Ontology();
    probe_Ontology.load_specie_GOID_ProbeID_mapping_file();
    String str1 = probe_Ontology.GO_to_Probes_toString();
    if (str1 != null)
      FileIO.saveResult("GO_to_Probes.txt",str1);
    else
      System.out.print("str1 == null");
    String str2 = probe_Ontology.Probe_to_GOs_toString();
    if (str2 != null)
      FileIO.saveResult("Probe_to_GOs.txt",str2);
*/
/*
    Gene_Ontology go = new Gene_Ontology();
    go.load_gene_ontology_obo_file();
    FileIO.saveResult("biological_process.txt",go.biological_process_toString());
    FileIO.saveResult("cellular_component.txt",go.cellular_component_toString());
    FileIO.saveResult("molecular_function.txt",go.molecular_function_toString());
    */
 /*
    System.out.print("0>>4=  " + (0>>4) + "\n");
 System.out.print("1>>4=  " + (1>>4) + "\n");
 System.out.print("2>>4=  " + (2>>4) + "\n");
 System.out.print("3>>4=  " + (3>>4) + "\n");

 System.out.print("14>>4=  " + (14>>4) + "\n");
 System.out.print("15>>4=  " + (15>>4) + "\n");
 System.out.print("16>>4=  " + (16>>4) + "\n");
 System.out.print("17>>4=  " + (17>>4) + "\n");

 System.out.print("32>>4=  " + (32>>4) + "\n");
 System.out.print("33>>4=  " + (33>>4) + "\n");
 System.out.print("34>>4=  " + (34>>4) + "\n");
 System.out.print("35>>4=  " + (35>>4) + "\n");
*/
//    if (f == null ) return;
//    FileIO.getSmallerFiles(f,100);

//    public static float [] ttest(float avg1, float std1,int N1, float avg2, float std2, int N2) {
/*
      float [] r0 = t_test.ttest(0.056816f,0.014865f,116,0.048534f,0.012579f,125);
      Display.display("diff="+r0[0]+" p value="+r0[1]+"\n");
      float [] r01 = t_test.ttest(0.056816f,0.014865f,3,0.048534f,0.012579f,3);
      Display.display("diff="+r01[0]+" p value="+r01[1]+"\n");

      float [] r1 = t_test.ttest(0.086209f,0.020986f,215,0.11485f,0.028783f,207);
      Display.display("diff="+r1[0]+" p value="+r1[1]+"\n");
      float [] r11 = t_test.ttest(0.086209f,0.020986f,3,0.11485f,0.028783f,3);
      Display.display("diff="+r11[0]+" p value="+r11[1]+"\n");

      float [] r4 = t_test.ttest(0.10729f,0.024841f,253,0.146813f,0.033719f,212);
      Display.display("diff="+r4[0]+" p value="+r4[1]+"\n");
      float [] r41 = t_test.ttest(0.10729f,0.024841f,3,0.146813f,0.033719f,3);
      Display.display("diff="+r41[0]+" p value="+r41[1]+"\n");

      float [] r5 = t_test.ttest(0.090895f,0.018094f,202,0.107133f,0.023017f,151);
      Display.display("diff="+r5[0]+" p value="+r5[1]+"\n");
      float [] r51 = t_test.ttest(0.090895f,0.018094f,3,0.107133f,0.023017f,3);
      Display.display("diff="+r51[0]+" p value="+r51[1]+"\n");
*/
    FileIO.expressionPredictor = expressionPredictor.ExpressionPredictorFrame.class.getResource("expressionPredictor.txt");
    FileIO.imageIconURL = expressionPredictor.ExpressionPredictorFrame.class.getResource("smallExPLogo.gif");

     StringTokenizer p = new StringTokenizer((FileIO.imageIconURL).toString(),"/");
     int num =p.countTokens();

     String [] s = new String[num];
     int i=0;
     while(p.hasMoreTokens()) {
       s[i] =  p.nextToken();
       i++;
     }

     FileIO.docPath="";
     for (int k=1;k<s.length-3;k++)
       FileIO.docPath+=FileIO.removeChar(s[k])+"\\";
    FileIO.imageIcon = new ImageIcon(FileIO.imageIconURL);
    FileIO.logoImageURL = expressionPredictor.ExpressionPredictorFrame.class.getResource("largeExPLogo.gif");
    FileIO.backgroundImageURL = expressionPredictor.ExpressionPredictorFrame.class.getResource("whiteBackground.jpg");
    setIconImage(FileIO.imageIcon.getImage());

    jPanel_logo = new BackgroundPanel(FileIO.logoImageURL);

    version = "Version 1.1 August 1, 2006";
    jLabel_version.setText(version);

    msg = new MessageBoard();
    msg.setVisible(true);
/*
    File  f = FileIO.openSingleFile();
    String [][] str = FileIO.read_txt(f,"\t");

    float [][] d = DataConversion.convertStringToFloat(str);
    float [] result;
    for (int k=0;k<d.length;k++) {
      result = t_test.ttest(d[k][0],d[k][1],(int)d[k][2],d[k][3],d[k][4],(int)d[k][5]);
      msg.display(k+"",result);
    }
*/
    dataPlot = new DataPlot(msg);

    exitConfirm();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
//    File  f = FileIO.openSingleFile();
//    String [][] str = FileIO.read_txt(f,"\t");
//    for (int k=0;k<str.length-1;k=k+2) {
//      correlation(str[k],str[k+1]);
//    }


  }
//  private void correlation(String [] s1, String [] s2){
//    float [] d1 = new float[s1.length-1];
//    float [] d2 = new float[s1.length-1];
//    for (int i=0;i<d1.length;i++) {
//      try {
//       d1[i] = Float.parseFloat(s1[i+1]);
//       d2[i] = Float.parseFloat(s2[i+1]);
//     }
//     catch(NumberFormatException e) {
//     }
//    }
//    float r = Correlation.pearsonCorrelation_rValue(d1,d2);
//    msg.display("r="+r+"\n");
//  }
  public Image readImage(URL imageName){
    Image image = Toolkit.getDefaultToolkit().getImage(imageName);
    MediaTracker imageTracker = new MediaTracker(this);
    imageTracker.addImage(image, 0);
    try{
        imageTracker.waitForID(0);
    }catch(InterruptedException e){ return null;}
    return image;
}

  private void exitConfirm() {
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      WindowListener l = new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              int confirm = JOptionPane.showConfirmDialog(null,
              "Really Exit?", "Exit Confirmation",
              JOptionPane.YES_NO_OPTION);
              if (confirm == 0) {
//                  dispose();
                  System.exit(0);
              }
          }
      };
      addWindowListener(l);
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),"Connect to ArrayDB/MAPS Database");
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),"Load Data");
    jPanel1.setLayout(gridBagLayout1);
    jPanel_logo.setPreferredSize(new Dimension(200, 220));
    jPanel_versionInfo.setPreferredSize(new Dimension(220, 270));
    jPanel_versionInfo.setLayout(gridBagLayout2);
    jPanel1.setPreferredSize(new Dimension(450, 300));
//    jLabel_niehs.setForeground(Color.red);
//    jLabel_niehs.setPreferredSize(new Dimension(250, 50));
//    jLabel_niehs.setText("National Center of Toxicogenomics");
    jLabel_nmcGroup.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel_nmcGroup.setForeground(Color.black);
    jLabel_nmcGroup.setPreferredSize(new Dimension(250, 50));
    jLabel_nmcGroup.setText("NIEHS");
    jLabel_version.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel_version.setForeground(Color.blue);
    jLabel_version.setPreferredSize(new Dimension(250, 50));
    jLabel_predictor.setFont(new java.awt.Font("Dialog", 0, 36));
    jLabel_predictor.setForeground(Color.blue);
    jLabel_predictor.setPreferredSize(new Dimension(250, 50));
    jLabel_predictor.setText("Predictor");
    jLabel_exp.setFont(new java.awt.Font("Dialog", 0, 36));
    jLabel_exp.setForeground(Color.blue);
    jLabel_exp.setPreferredSize(new Dimension(250, 50));
    jLabel_exp.setText("Expression");
    jButton_logRatio.setText("Compiled Expression");
    jButton_logRatio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_logRatio_actionPerformed(e);
      }
    });
    jButton_intensity.setPreferredSize(new Dimension(180, 25));
    jButton_intensity.setToolTipText("");
    jButton_intensity.setText("SVN Normalization");
    jButton_intensity.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_intensity_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout4);
    jButton_generic.setPreferredSize(new Dimension(180, 25));
    jButton_generic.setText("Classification");
    jButton_generic.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_generic_actionPerformed(e);
      }
    });
    jMenu1.setText("File");
    jMenu4.setText("Applications");
    jMenu3.setText("Help");
    jMenuItem_exit.setText("Exit");
    jMenuItem_exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_exit_actionPerformed(e);
      }
    });
    jMenuItem_help.setText("Help");
    jMenuItem_help.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_help_actionPerformed(e);
      }
    });
    jButton_preprocess.setRequestFocusEnabled(false);
    jButton_preprocess.setSelected(true);
    jButton_preprocess.setText("File Process Utility");
    jButton_preprocess.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_preprocess_actionPerformed(e);
      }
    });

    jMenuItem_gc.setText("Clear Memory");
    jMenuItem_gc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_gc_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle("Expression Predictor");
    jCheckBoxMenuItem_megaDataSet.setText("Mega Intensity Data Sets");
    jMenuItem_simulationData.setText("Create Simulation Data");
    jMenuItem_simulationData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_simulationData_actionPerformed(e);
      }
    });
    jMenuItem_load_TF_gene_matrix.setText("Convert Gene-TF Matrix");
    jMenuItem_load_TF_gene_matrix.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_load_TF_gene_matrix_actionPerformed(e);
      }
    });
//    jMenuItem_load_fileNameMapping.setText("Load File Name Mapping File");
//    jMenuItem_load_fileNameMapping.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        jMenuItem_load_fileNameMapping_actionPerformed(e);
//      }
//    });
//    jMenuItem_loadFileNameCahnge.setText("Load Files For Name Change");
//    jMenuItem_loadFileNameCahnge.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        jMenuItem_loadFileName_rename_actionPerformed(e);
//      }
//    });
//    jMenuItem_getFileName.setText("Get File Name");
//    jMenuItem_getFileName.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        jMenuItem_getFileName_actionPerformed(e);
//      }
//    });
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jCheckBoxMenuItem_EPIG.setText("EPIG");

    jMenuItem_SVM.setText("SVM");
    jMenuItem_SVM.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_SVM_actionPerformed(e);
      }
    });
    jMenuItem_BiCluster.setText("BiCluster");
    jMenuItem_BiCluster.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem_BiCluster_actionPerformed(e);
      }
    });
    jRadioButtonMenuItem_compiledDataWithoutDisplay.setText("Compiled Data without data display");

    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel_logo,        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 3, 3), 0, 0));
    jPanel1.add(jPanel_versionInfo,       new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
    jPanel_versionInfo.add(jLabel_exp,          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel_versionInfo.add(jLabel_predictor,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel_versionInfo.add(jLabel_version,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel_versionInfo.add(jLabel_nmcGroup,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel_versionInfo.add(jLabel_niehs,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jPanel2,        new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 5, 20, 5), 0, 0));
    jPanel2.add(jButton_intensity,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jButton_logRatio,    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jButton_preprocess, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jButton_generic,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu4);
    jMenuBar1.add(jMenu3);
//    jMenu1.add(jMenuItem_loadFileNameCahnge);
//    jMenu1.add(jMenuItem_load_fileNameMapping);
 //   jMenu1.add(jMenuItem_transpose);
 //   jMenu1.add(jMenuItem_dyeSwapCorrection);
//    jMenu1.add(jMenuItem_getFileName);
    jMenu1.add(jMenuItem_exit);
    jMenu4.add(jMenuItem_gc);
    jMenu4.add(jCheckBoxMenuItem_megaDataSet);
    jMenu4.add(jMenuItem_simulationData);
    jMenu4.add(jMenuItem_load_TF_gene_matrix);
    jMenu4.add(jCheckBoxMenuItem_EPIG);
    jMenu4.add(jMenuItem_SVM);
    jMenu4.add(jMenuItem_BiCluster);
    jMenu4.add(jRadioButtonMenuItem_compiledDataWithoutDisplay);
    jMenu3.add(jMenuItem_help);
    setJMenuBar(jMenuBar1);
    setBounds(0, 0, 440, 440);
  }


  void jMenuItem_exit_actionPerformed(ActionEvent e) {
     System.exit(0);
  }

  void jMenuItem_vennDiagram_actionPerformed(ActionEvent e) {
    VennDiagram vd = new VennDiagram();
    String s= vd.loadData();
    vd.vennDiagram();
  }

  void jButton_preprocess_actionPerformed(ActionEvent e) {
     new FileProcessUtilityFrame(msg).setVisible(true);

  }
  void jButton_logRatio_actionPerformed(ActionEvent e) {
    if (jRadioButtonMenuItem_compiledDataWithoutDisplay.isSelected()) {
      new CompiledDataFrame().setVisible(true);
    }
    else {
    File f = FileIO.openSingleFile();
    if (f == null ) {
      return;
    }
    String extension = MyFileFilter.getExtension(f);
    if (jCheckBoxMenuItem_EPIG.isSelected()) {

      String [][] str =  FileIO.read_txt(f,"\t");
      int rowNum = str.length-2;
      int arrayNum = str[0].length-1;
      String [][] generalInfo = new String[2][1];
      String [][] columnInfo = new String[2][arrayNum];
      String [][] rowInfo = new String[rowNum][1];
      float [][] data = new float[rowNum][arrayNum];
      for (int i=0;i<2;i++) {
        generalInfo[i][0] = str[i][0];
        for (int j=0;j<arrayNum;j++) {
          columnInfo[i][j] = str[i][j+1];
        }
      }
      for (int i=0;i<rowNum;i++) {
        rowInfo[i][0] = str[i+2][0];
        for (int j=0;j<arrayNum;j++) {
          try {
            data[i][j] = Float.parseFloat(str[i+2][j+1]);
          }
          catch(NumberFormatException ee) {
            data[i][j] = 0;;
          }
        }
      }
      DataSet ds = new DataSet(generalInfo,columnInfo,rowInfo,data,msg,
                               0,1,DataSet.bioRepPlot,-1,-1,-1,true,dataPlot);
      new EPIGFrame(ds,msg, dataPlot).setVisible(true);
    }
    else
    new GenericCompiledDataFrameIO(f,extension,msg,dataPlot).setVisible(true);
    }
  }

  void jButton_intensity_actionPerformed(ActionEvent e) {
    new SVN(msg,dataPlot).show();

    }

  void jButton_generic_actionPerformed(ActionEvent e) {
    File  f = FileIO.openSingleFile();
    if (f == null ) return;
    new ClassificationAnalysisFrame(f, msg,dataPlot).setVisible(true);

  }

  void jMenuItem_gc_actionPerformed(ActionEvent e) {
    System.gc();
  }

  void jMenuItem_help_actionPerformed(ActionEvent e) {
    String ss = FileIO.docPath+"help\\expressionPredictor.pdf";
    FileIO.openPDF(ss);
  }


  void jMenuItem_simulationData_actionPerformed(ActionEvent e) {
    new simulationData(msg,dataPlot).setVisible(true);

  }
  void jMenuItem_load_TF_gene_matrix_actionPerformed(ActionEvent e) {
    File  f = FileIO.openSingleFile();
    if (f == null ) return;
    DataConversion.TF_gene_matrix(f,"\t");

  }
//  public static void main(String[] args) {
//    new ExpressionPredictorFrame().setVisible(true);
//    new ExpressionPredictorFrame().show();
//  }

  public static void main(String[] args) throws HeadlessException {
//    new ExpressionPredictorFrame().setVisible(true);
      new ExpressionPredictorFrame().setVisible(true);
  }

  public void readexel(String filename)


  {
  Connection c = null;
  Statement stmnt = null;
  try
  {
  Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
  c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + filename);
  stmnt = c.createStatement();
  String query = "Select * from [Sheet1$]" ;
  ResultSet rs = stmnt.executeQuery( query );

  while( rs.next() )
  {
  System.out.println( rs.getString(1) );
  }
  }
  catch( Exception e )
  {
  System.err.println( e );
  }


}

  public void readexcel(String filename) {
Connection c = null;
Statement stmnt = null;
try
{
Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + filename);
stmnt = c.createStatement();
String query = "Select * from [Sheet1$]" ;
ResultSet rs = stmnt.executeQuery( query );
int colNum=0;
if (rs.next()) {
  ResultSetMetaData rsmd = rs.getMetaData();
  colNum = rsmd.getColumnCount();
  for (int i=1;i<= colNum;i++) {
    System.out.println("name "+rsmd.getColumnName(i));
  }
  for (int i=1;i<= colNum;i++)
    System.out.println( rs.getString(i) );

}
while( rs.next() )
{
  for (int i=1;i<= colNum;i++) {
    String str = rs.getString(i);
    if (str != null)
    System.out.println( str +"\t");
  }
  System.out.println( "" );
}
}
catch( Exception e )
{
System.err.println( e );
}
  }
  public void readcsv(String filename) {
    Connection c = null;
   Statement stmnt = null;
   try
   {
     System.out.println( "this" );
   Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
   System.out.println( "this2" );
   c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + filename);
//   c = DriverManager.getConnection("jdbc:odbc:surajtest");
//   c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.txt;*.xls)};DBQ=" + filename);
//   c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Text Driver (*.txt; *.csv)};DBQ=" + filename);
//   c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Text-Treiber (*.txt; *.csv)};DBQ=" + filename);
   System.out.println( "this3" );
   stmnt = c.createStatement();
  System.out.println( "this4" );
//ResultSet rs = stmnt.executeQuery("select * from [test$]");
   String query = "Select * from [Sheet1$]" ;
   ResultSet rs = stmnt.executeQuery( query );
   System.out.println( "this5" );
//if (rs.isFirst())
//  System.out.println( rs.getString(1) );

//if (rs.first())
//    System.out.println( rs.getString(1) );
   int colNum=0;
   if (rs.next()) {
     ResultSetMetaData rsmd = rs.getMetaData();
     colNum = rsmd.getColumnCount();
     for (int i=1;i<= colNum;i++) {
       System.out.println("name "+rsmd.getColumnName(i));
     }
     for (int i=1;i<= colNum;i++)
       System.out.println( rs.getString(i) );

   }
   while( rs.next() )
   {
     for (int i=1;i<= colNum;i++) {
       String str = rs.getString(i);
       if (str != null)
       System.out.println( str +"\t");
     }
     System.out.println( "" );
   }
   }
   catch( Exception e )
   {
   System.err.println( e );
   }

  }

//  void jMenuItem_loadFileName_rename_actionPerformed(ActionEvent ee) {
//  }

//  void jMenuItem_load_fileNameMapping_actionPerformed(ActionEvent e) {

//  }

  void jMenuItem_getFileName_actionPerformed(ActionEvent e) {
  }
/*
  void jMenuItem_agilentFileExtraction_cy3Control_actionPerformed(ActionEvent e) {
    File [] f = FileIO.openMultiFiles(true);
    if (f == null) return;
        AgilentDataConversion.agilentFileConversion(f,true,false);
  }

  void jMenuItem_agilentFileExtraction_cy5control_actionPerformed(ActionEvent e) {
    File [] f = FileIO.openMultiFiles(true);
    if (f == null) return;
        AgilentDataConversion.agilentFileConversion(f,false,false);

  }
*/



  void jMenuItem_SVM_actionPerformed(ActionEvent e) {
    new SVMJFrame().setVisible(true);

  }

  void jMenuItem_BiCluster_actionPerformed(ActionEvent e) {
    new BiclusterFrame(msg,dataPlot).setVisible(true);

  }




}