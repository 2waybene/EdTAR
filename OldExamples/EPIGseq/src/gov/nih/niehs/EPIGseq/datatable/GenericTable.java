package gov.nih.niehs.EPIGseq.datatable;


import javax.swing.JTable;
//import io.*;
import util.*;
import java.util.*;

import java.awt.*;

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

import myutility.arrayObject.*;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class GenericTable extends JTable {

  public GenericTable(GenericDataTableModel model) {
    super(model);
  }
  public GenericTable() {
  }
  public boolean isCellSelected(int row, int col) {
   GenericDataTableModel model = (GenericDataTableModel)getModel();
   if (super.isCellSelected(row,col))  {
     Object obj;
       obj = model.getValueAt(row,col);
       if (obj != null)
       ((ColorStringClassIO)obj).setBackgroundColor(Color.lightGray);
   }
   else {
     Object obj;
     ColorStringClassIO csc;
       csc = (ColorStringClassIO)model.getValueAt(row,col);
       if (csc != null)
       csc.setBackgroundColor(Color.white);

   }
    return super.isCellSelected(row,col);
  }
/*
  public boolean isColumnSelected(int col) {
    TableColumnModel tcm = getColumnModel();
    TableColumn column = tcm.getColumn(col);
    ColoredTableCellRendererClassIO ctcr = (ColoredTableCellRendererClassIO)column.getHeaderRenderer();
    if (super.isColumnSelected(col)) {
      ctcr.setForeground(Color.blue);
    }
    else {
      ctcr.setForeground(Color.black);
    }
    return super.isColumnSelected(col);
  }

*/
}