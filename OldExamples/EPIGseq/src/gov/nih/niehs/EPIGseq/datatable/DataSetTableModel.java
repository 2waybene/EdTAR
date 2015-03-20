package gov.nih.niehs.EPIGseq.datatable;


import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
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

public class DataSetTableModel extends DefaultTableModel
    {
  private MessageBoard msg;
  private boolean dataInluded;
   private DataSet dataSets;
//   String [][] data;
   String [] colName;
  int colNum,rowNum;
//  private ColorStringClassIO [][] data, removedData;
  protected boolean m_sortAsc = true;
  protected int m_sortCol = 0;
  private Object barcode, agent;
  protected int fileNumber=-1;
  private  boolean geneid = false;
  private int widthScale = 10;
  private  ColumnNameClassIO [] columnNameClass;
 // int colNumberNotIncludeExtra = 0;
/*
  public DataSetTableModel(String [][] _data, String [] colName,MessageBoard _msg) {
    msg = _msg;
    dataSets = new DataSet(_data,colName);
    colNum = colName.length;
    rowNum = data.length;
    columnNameClass = new ColumnNameClassIO[colNum];
    String columnName;
    int width;
      for (int i=0;i<colNum;i++) {
         columnName=colName[i];
         width = columnName.length();
        columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
      }
      data = _data;
  }
*/
  public DataSetTableModel(DataSet _ds,MessageBoard _msg) {
    msg = _msg;
    dataSets = _ds;
    colNum = dataSets.getNumColumnGeneDescInfo() + dataSets.getNumberExp();
    rowNum = dataSets.getNumberDataRow();
    columnNameClass = new ColumnNameClassIO[colNum];
    String columnName;
    int width;
      for (int i=0;i<colNum;i++) {
         columnName=dataSets.getColumnName(i);
         if (columnName==null)
           columnName = "Column "+i;
         width = columnName.length();
        columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
      }
  }

  public DataSetTableModel() {

  }
  public void loadData(DataSet _ds,MessageBoard _msg, boolean _dataInluded) {
    msg = _msg;
    dataSets = _ds;
    dataInluded = _dataInluded;
    if (dataInluded) {
      colNum = dataSets.getNumColumnGeneDescInfo() + dataSets.getNumberExp();
 //     colNumberNotIncludeExtra = colNum;
 //     if (dataSets.getExtraColumnName() != null)
 //       colNum += dataSets.getExtraColumnName().length;
      rowNum = dataSets.getNumberDataRow();
      columnNameClass = new ColumnNameClassIO[colNum];
      String columnName;
      int width;
        for (int i=0;i<colNum;i++) {
 //         if (i <colNumberNotIncludeExtra) {
           columnName=dataSets.getColumnName(i);
           width = columnName.length();
          columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
          }
  //      else {
  //        columnName=dataSets.getExtraColumnName(i-colNumberNotIncludeExtra);
  //        width = columnName.length();
  //        columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
  //      }

    }
    else {
      colNum = dataSets.getNumColumnGeneDescInfo();
 //     colNumberNotIncludeExtra = colNum;
      rowNum = dataSets.getNumberDataRow();
      columnNameClass = new ColumnNameClassIO[colNum];
      String columnName;
      int width;
        for (int i=0;i<colNum;i++) {
           columnName=dataSets.getColumnName(i);
           width = columnName.length();
          columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
    }
    }
  }
  public void setNumberRow(int k) {
    dataSets.setNumberDataRow(k);
  }
  public int getColumnCount() {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
     return colNum;
  }
  public int getRowCount() {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
    return rowNum;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
//    if (dataSets != null)
        return dataSets.getRow(rowIndex).getData(columnIndex);
//    else
//      return data[rowIndex][columnIndex];
  }
  public void setValueAt(Object value, int row, int col) {
//    if (dataSets != null)
      dataSets.getRow(row).setData(col,value);
//    else
//      data[row][col] = (String)value;
     fireTableCellUpdated(row, col);
  }

  public float [] getColumnData(int col) {
//    if (dataSets!=null)
    return dataSets.getColumnData(col);
//    else
//      return colName;
  }
  public Object [] getRow(int k, int [] geneDataIndex, int numDataItem) {
    return null;
  }
  public String getColumnName(int col) {
    return columnNameClass[col].getColumnName();
  }
  public ColumnNameClassIO getColumnNameClass(int col) {
    return columnNameClass[col];
  }
  public ColumnNameClassIO [] getColumnNameClasses() {
    return columnNameClass;
  }
  public String [] getColumnNames() {
    String [] str = new String[columnNameClass.length];
    for (int i=0;i<columnNameClass.length;i++)
      str [i] = columnNameClass[i].getColumnName();
    return str;
  }

  public void setColumnName(int col, String str) {
    columnNameClass[col].setColumnName(str);
  }
  public String columnName_toString() {
    String str = "";
    for (int i=0;i<colNum;i++)
      if (i== colNum -1)
        str += columnNameClass[i].getColumnName() + "\n";
        else
      str += columnNameClass[i].getColumnName() + "\t";
        return str;
  }


public DataSet getDataSet() {
  return dataSets;
}
public boolean isCellEditable(int rowIndex,
                              int columnIndex) {
  return true;
}

public void removeUnmarked(int col) {
  dataSets.removeUnmarked(col);
  rowNum = dataSets.getNumberDataRow();
  /*
  */
}
public void removeMarked(int col) {
  dataSets.removeMarked(col);
  rowNum = dataSets.getNumberDataRow();

}
public void removeRow(int row) {
  dataSets.removeRow(row);
  rowNum = dataSets.getNumberDataRow();

}
public void undoRemove() {
  dataSets.undoRemove();
  rowNum = dataSets.getNumberDataRow();
}
//getValueAt
public void saveDataInEachCat() {
  int [] numInEachCat = dataSets.getNumberGeneInEachCat();
  if (numInEachCat == null) return;
  int start =0,end=0;
  for (int i=0;i<numInEachCat.length;i++) {
    start = end;
    end += numInEachCat[i];
    saveDataInCat(i+1,start,end);
  }
}
private void saveDataInCat(int cat, int startGene,int endGene) {
  dataSets.saveData(new File(FileIO.path,"selectedGenesInPattern_"+cat+"_"+(endGene-startGene)+".txt"),startGene,endGene);
}
public void saveData(File f) {
  dataSets.saveData(f);
}

public void sorting(int selectedCol, boolean acending) {
  dataSets.sorting(selectedCol,acending);

}

public RowDataIO [] getRowDataIO () {
  return dataSets.getRowData();
}
public RowDataIO [] getRowDataIO (int [] rowIndex) {
  RowDataIO [] rd = new RowDataIO[rowIndex.length];
  for (int i=0;i<rowIndex.length;i++)
    rd[i] = dataSets.getRow(rowIndex[i]);
  return rd;
}
public RowDataIO getRowDataIO (int index) {
  return dataSets.getRow(index);
}
public void plotRow(int [] selectedRow, int plotOption) {
  dataSets.plotRow_dataSetTableModel(selectedRow,plotOption);

}
public void plotColumn(int [] selectedColumn) {
  dataSets.plotColumn(selectedColumn);

}
public int [] getConjunctionList(int col,String [] strList) {
  Object objValue;
  String strValue;
  TreeSet v = new TreeSet();
  for (int k=0;k<strList.length;k++)
    for (int i = 0; i<rowNum ; i++) {
       objValue = getValueAt(i,col);
       if (objValue != null) {
         strValue = ((ColorStringClassIO)objValue).toString();
            if (strValue.equals(strList[k])){
              v.add(""+i);
              i = rowNum;
          }
    }
}

if (v.size()==0) return null;
int [] index = new int[v.size()];
int size=0;
      Iterator cur = v.iterator();
      while(cur.hasNext()) {
        Object o1 = cur.next();
        index[size++] = Integer.parseInt((String)o1);

      }
 return index;

}
public int [] getNegConjunctionList(int col,String [] strList) {
  Object objValue;
  String strValue;
  TreeSet v = new TreeSet();
  for (int i = 0; i<rowNum ; i++)
     v.add(""+i);
  for (int k=0;k<strList.length;k++)
    for (int i = 0; i<rowNum ; i++) {
       objValue = getValueAt(i,col);
       if (objValue != null) {
         strValue = ((ColorStringClassIO)objValue).toString();
            if (strValue.equals(strList[k])){
              v.remove(""+i);
              i = rowNum;
          }
    }
}
if (v.size()==0) return null;
int [] index = new int[v.size()];
int size=0;
      Iterator cur = v.iterator();
      while(cur.hasNext()) {
        Object o1 = cur.next();
        index[size++] = Integer.parseInt((String)o1);

      }
 return index;

}
public void updateStringSearch( int col,int [] findIndex) {
  ColorStringClassIO csc;
  for (int i=0;i<rowNum;i++) {
    csc = (ColorStringClassIO)getValueAt(i,col);
    if (csc != null)
    csc.setColor(Color.black);
  }
  if (findIndex == null) {
    return;
  }
  for (int i=0;i<findIndex.length;i++) {
    if(findIndex[i] >= 0) {
    csc = (ColorStringClassIO)getValueAt(findIndex[i],col);
    if (csc != null)
    csc.setColor(Color.red);
    }
  }

}

}