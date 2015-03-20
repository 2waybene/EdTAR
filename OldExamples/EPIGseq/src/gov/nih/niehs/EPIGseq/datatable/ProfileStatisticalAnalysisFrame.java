package gov.nih.niehs.EPIGseq.datatable;

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

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class ProfileStatisticalAnalysisFrame extends JFrame {
  private JPanel jPanel1 = new JPanel();
  private JButton jButton_pairedttest = new JButton();
  private JButton jButton_ttest = new JButton();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton jButton_snr = new JButton();
  private JButton jButton_snr_bootstrapping = new JButton();
//  private JButton jButton_ftest = new JButton();
  private JButton jButton_anova = new JButton();
  private JTextField jTextField_iterNum = new JTextField();
  private JLabel jLabel_iterationNum = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JTextField jTextField_snr = new JTextField();
  DataSet dataSets;
  MessageBoard msg;
  DataPlot dataPlot;
  String fileName;
  private JLabel jLabel_mag = new JLabel();
  private JTextField jTextField_mag = new JTextField();
  private JButton jButton_bt_shuffle = new JButton();
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JButton jButton_anova_bootstrapping_shuffle = new JButton();
  private JButton jButton_anova_bootstrapping = new JButton();
  private JRadioButton jRadioButton_classification = new JRadioButton();
  private JCheckBoxMenuItem jCheckBoxMenuItem_pairedData = new JCheckBoxMenuItem();
  private JRadioButton jRadioButton_relativeChange = new JRadioButton();
  public ProfileStatisticalAnalysisFrame(String _fileName,DataSet _ds, MessageBoard _msg,DataPlot _dataPlot) throws HeadlessException {
    dataSets = _ds;
    msg = _msg;
    dataPlot=_dataPlot;
    fileName = _fileName;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    if (FileIO.imageIcon != null)
      setIconImage(FileIO.imageIcon.getImage());
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
    jButton_pairedttest.setText("paired t-test");
    jButton_pairedttest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_pairedttest_actionPerformed(e);
      }
    });
    jButton_ttest.setText("t-test");
    jButton_ttest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_ttest_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout1);
    jButton_snr.setText("Profile SNR");
    jButton_snr.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_snr_actionPerformed(e);
      }
    });
    jButton_snr_bootstrapping.setText("SNR bootstrapping");
    jButton_snr_bootstrapping.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_SNR_bootstrapping_actionPerformed(e);
      }
    });
    jButton_anova.setText("ANOVA");
    jButton_anova.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_anova_actionPerformed(e);
      }
    });
    jTextField_iterNum.setText("100000");
    jLabel_iterationNum.setText("Iteration Number");
    jLabel1.setText("SNR threshold");
    jTextField_snr.setText("3");
    jLabel_mag.setText("Magnitude thresold");
    jTextField_mag.setText("0.5");
    jButton_bt_shuffle.setText("SNR bootstrapping shuffle");
    jButton_bt_shuffle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_bt_shuffle_actionPerformed(e);
      }
    });
    jMenu1.setText("File");
    jButton_anova_bootstrapping_shuffle.setText("ANOVA bootstrapping shuffle");
    jButton_anova_bootstrapping_shuffle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_anova_bootstrapping_shuffle_actionPerformed(e);
      }
    });
    jButton_anova_bootstrapping.setText("ANOVA bootstrapping");
    jButton_anova_bootstrapping.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_anova_bootstrapping_actionPerformed(e);
      }
    });
    jPanel1.setPreferredSize(new Dimension(700, 258));
    jRadioButton_classification.setText("Classification with shuffle");
    jCheckBoxMenuItem_pairedData.setText("paired data");
    jRadioButton_relativeChange.setSelected(true);
    jRadioButton_relativeChange.setText("Baseline");
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jButton_snr_bootstrapping,            new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel_mag,      new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jTextField_mag,      new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel_iterationNum,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jTextField_iterNum,    new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jRadioButton_classification,    new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel1,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jTextField_snr,    new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_anova_bootstrapping,   new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_bt_shuffle,   new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_anova_bootstrapping_shuffle,    new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_anova,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jButton_snr,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jRadioButton_relativeChange,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButton_pairedttest,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
    jPanel1.add(jButton_ttest,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jMenuBar1.add(jMenu1);
    jMenu1.add(jCheckBoxMenuItem_pairedData);
    setJMenuBar(jMenuBar1);
    setBounds(0, 0, 500, 300);
 }

  void jButton_snr_actionPerformed(ActionEvent e) {
    dataSets.rowData_SNR(fileName,jRadioButton_relativeChange.isSelected());

  }

  void jButton_anova_actionPerformed(ActionEvent e) {
    dataSets.rowData_ANOVA(fileName);

  }

  void jButton_ttest_actionPerformed(ActionEvent e) {
    dataSets.rowData_t_test(fileName);

  }

  void jButton_pairedttest_actionPerformed(ActionEvent e) {
    dataSets.rowData_paired_sample_t_test(fileName);

  }
/*
  void jButton_ftest_actionPerformed(ActionEvent e) {
    dataSets.rowData_F_test(fileName);

  }
*/
  void jButton_SNR_bootstrapping_actionPerformed(ActionEvent e) {
    int numberofbootstrapping;
   try {
     numberofbootstrapping = Integer.parseInt(jTextField_iterNum.getText());
   }
   catch (NumberFormatException ee) {
     numberofbootstrapping = 10;
   }
//   System.out.println("number of bootstrapping - SNR = "+numberofbootstrapping);

 dataSets.rowData_SNR_bootstrapping(fileName,numberofbootstrapping,jRadioButton_relativeChange.isSelected());
  }


  void jButton_bt_shuffle_actionPerformed(ActionEvent e) {
    if (jRadioButton_classification.isSelected()) {
      int numberofbootstrapping;
     try {
       numberofbootstrapping = Integer.parseInt(jTextField_iterNum.getText());
     }
     catch (NumberFormatException ee) {
       numberofbootstrapping = 10;
     }
     System.out.println("SNR bootstrapping classification = "+numberofbootstrapping);
     float mag;
    try {
      mag = Float.parseFloat(jTextField_mag.getText());
    }
    catch (NumberFormatException ee) {
      mag = 0.5f;
    }
    float snr;
   try {
     snr = Float.parseFloat(jTextField_snr.getText());
   }
   catch (NumberFormatException ee) {
     snr = 0.5f;
   }

   dataSets.rowData_SNR_bootstrapping_shuffle_classification(fileName,
       numberofbootstrapping,snr,mag,jRadioButton_relativeChange.isSelected());
    }
    else {
    int numberofbootstrapping;
   try {
     numberofbootstrapping = Integer.parseInt(jTextField_iterNum.getText());
   }
   catch (NumberFormatException ee) {
     numberofbootstrapping = 10;
   }

 dataSets.rowData_SNR_bootstrapping_shuffle(fileName,
     numberofbootstrapping,jRadioButton_relativeChange.isSelected());
}
  }

  void jButton_anova_bootstrapping_actionPerformed(ActionEvent e) {
    int numberofbootstrapping;
   try {
     numberofbootstrapping = Integer.parseInt(jTextField_iterNum.getText());
   }
   catch (NumberFormatException ee) {
     numberofbootstrapping = 10;
   }
   System.out.println("number of bootstrapping - ANOVA = "+numberofbootstrapping);
 dataSets.rowData_ANOVA_bootstrapping(fileName,numberofbootstrapping);

  }

  void jButton_anova_bootstrapping_shuffle_actionPerformed(ActionEvent e) {
    int numberofbootstrapping;
   try {
     numberofbootstrapping = Integer.parseInt(jTextField_iterNum.getText());
   }
   catch (NumberFormatException ee) {
     numberofbootstrapping = 10;
   }
   System.out.println("number of bootstrapping - ANOVA shuffle = "+numberofbootstrapping);
// dataSets.rowData_ANOVA_bootstrapping_shuffle(fileName,numberofbootstrapping,jCheckBoxMenuItem_pairedData.isSelected());
 dataSets.rowData_ANOVA_bootstrapping_shuffle(fileName,numberofbootstrapping);

  }


}
