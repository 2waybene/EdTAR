package gov.nih.niehs.EPIGseq.datatable;


import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import util.*;
import java.text.DecimalFormat;
import myutility.statistics.*;
import myutility.matrix.*;
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

public class GenericDataTableModel extends DefaultTableModel
{
  DecimalFormat df = new DecimalFormat("0.0000000");
  private MessageBoard msg;
  private boolean xTicMark=true, yTicMark=true;
  private DataSet dataSets;
  private int colNum,rowNum;
  private ColorStringClassIO [][] data, removedData;
  private int[] removedIndex;
  protected boolean m_sortAsc = true;
  protected int m_sortCol = 0;
  protected int fileNumber=-1;
  private DataPlot dataPlot;
  private int sampleNameRow=0,  replicateRow=-1,dataPlotOption = -1,dataStartCol=-1,
  dyeSwapLabelRow=-1,widthScale = 10,
  stepRow=-1,cellLineNameRow = -1, cellLineProfileRow=-1,techRepliateRow=-1,
  dataStartRow=-1;
  private  ColumnNameClassIO [] columnNameClass;

  public GenericDataTableModel(String [][] str, String [] _columnName,MessageBoard _msg,DataPlot _dataPlot) {
    msg = _msg;
    dataPlot = _dataPlot;
    int colNumber = 0;
    for (int i=0;i<str.length;i++)
      if (colNumber < str[i].length) colNumber = str[i].length;

    colNum = colNumber;
//    System.out.print("colMun "+colNum);
    rowNum = str.length;
    if (_columnName == null) {
      columnNameClass = new ColumnNameClassIO[colNum];
      String columnName;
      int width;
      for (int i=0;i<colNum;i++) {
        columnName = "column "+i;
        width = columnName.length();
        columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
      }
    }
    else {
      columnNameClass = new ColumnNameClassIO[_columnName.length];
      for (int i=0;i<colNum;i++) {
        int width = _columnName[i].length();
        columnNameClass[i] = new ColumnNameClassIO(_columnName[i], width*widthScale, JLabel.LEFT,i);
      }
    }
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++) {
      for (int j=0;j<str[i].length;j++) {
        if (str[i][j] != null)
          data[i][j] = new ColorStringClassIO(str[i][j]);//setData(str[i][j]);
      }
    }
  }
  public GenericDataTableModel(String [][] str) {
  colNum = str[0].length;

//    System.out.print("colMun "+colNum);
  rowNum = str.length-1;
    columnNameClass = new ColumnNameClassIO[colNum];
    int width=str[0][0].length();
    for (int i=0;i<colNum;i++) {
      columnNameClass[i] = new ColumnNameClassIO(str[0][i], width*widthScale, JLabel.LEFT,i);
    }
   data = new ColorStringClassIO[rowNum][colNum];
  for (int i=0;i<rowNum;i++) {
    for (int j=0;j<str[i+1].length;j++) {
      if (str[i+1][j] != null)
        data[i][j] = new ColorStringClassIO(str[i+1][j]);//setData(str[i][j]);
    }
  }
}

  public String row_toString(int row) {
        String s = "";
     for (int i=0;i<data[row].length;i++) {
       if (i==data[row].length-1) {
         if(data[row][i] != null)
         s += data[row][i].toString()+"\n";
         else
           s += "\n";
       }
       else {
         if (data[row][i] != null)
         s += data[row][i].toString()+"\t";
         else
           s += "\t";
       }
     }
     return s;
 }

  public void loadData(String [][] str) {
    colNum = str[0].length;
    rowNum = str.length;
    columnNameClass = new ColumnNameClassIO[colNum];
    String columnName;
    int width;
    for (int i=0;i<colNum;i++) {
      columnName = "column "+i;
      width = columnName.length();
      columnNameClass[i] = new ColumnNameClassIO(columnName, width*widthScale, JLabel.LEFT,i);
    }
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++) {
      for (int j=0;j<str[i].length;j++) {
        if (str[i][j] != null)
          data[i][j] = new ColorStringClassIO(str[i][j]);//setData(str[i][j]);
      }
    }
  }
  public DataPlot getDataPlot() {
    return dataPlot;
  }

  public int getColumnCount() {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
    return colNum;
  }
  public Object getValueAt(int rowIndex, int columnIndex) {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
    if (rowIndex < 0 || rowIndex >= rowNum)  return null;
    else if (columnIndex < 0 || columnIndex >= colNum) return null;
    else {
      if (data[rowIndex][columnIndex] == null) return null;
      else return data[rowIndex][columnIndex];
    }
  }
  public void setValueAt(Object value, int row, int col) {
    ColorStringClassIO csc = new ColorStringClassIO((String)value);
    data[row][col] = csc;
    fireTableCellUpdated(row, col);
  }

  public int getRowCount() {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
    return rowNum;
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

  public void setColumnName(int col, String str) {
    columnNameClass[col].setColumnName(str);
//    if(str.equals("CLONE")) {
//      geneid = true;
 //   }
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
  public String getRow_toString(int row) {
    String str = "";
    for (int i=0;i<colNum;i++)
      if (i== colNum -1)
        str += ((ColorStringClassIO)getValueAt(row, i)).toString() + "\n";
    else
      str += ((ColorStringClassIO)getValueAt(row, i)).toString() + "\t";
    return str;
  }

  class ColumnListener extends MouseAdapter
  {
    protected JTable m_table;

    public ColumnListener(JTable table) {
      m_table = table;
    }
    public void formMousePressed(MouseEvent e) {
 //     System.out.println("formMousePressed");
        /*
          TableColumnModel colModel = m_table.getColumnModel();
          int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
          int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();


          for (int i=0; i < colNum; i++) {
              TableColumn column = colModel.getColumn(i);
              column.setHeaderValue(getColumnName(column.getModelIndex()));
          }
          m_table.getTableHeader().repaint();
          m_table.repaint();
        */
    }

    public void mouseClicked(MouseEvent e) {
      //       System.out.println("mouseClicked");
      TableColumnModel colModel = m_table.getColumnModel();
      int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
      int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();


      for (int i=0; i < colNum; i++) {
        TableColumn column = colModel.getColumn(i);
        column.setHeaderValue(getColumnName(column.getModelIndex()));
      }
      m_table.getTableHeader().repaint();
      m_table.repaint();

    }

    public void formMouseReleased(MouseEvent e) {
//      System.out.println("formMouseReleased");
        /*
          TableColumnModel colModel = m_table.getColumnModel();
          int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
          int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();


          for (int i=0; i < colNum; i++) {
              TableColumn column = colModel.getColumn(i);
              column.setHeaderValue(getColumnName(column.getModelIndex()));
          }
          m_table.getTableHeader().repaint();
          m_table.repaint();
        */
    }


  }

  public void setStartColumn(int _startCol){
    dataStartCol = _startCol;
    createDataSet();
  }
  public int getStartColumn(){
    return dataStartCol;
  }
  public void setStartDataRow(int _startRow){
    dataStartRow = _startRow;
    createDataSet();
  }
  public  int getStartDataRow(){
    return dataStartRow;
  }

  public void setSampleNameRow(int k){
    sampleNameRow = k;
  }
  public int getSampleNameRow(){
    return sampleNameRow;
  }
  public void setDyeSwapLabelRow(int k) {
    dyeSwapLabelRow = k;

  }
  public int getDyeSwapLabelRow() {
    return dyeSwapLabelRow;
  }

  public void setCellLineProfileRow(int k) {
    if (cellLineProfileRow == -1) {
      cellLineProfileRow = k;
      if (dataSets != null) {
        dataSets.setCellLineProfileRow(cellLineProfileRow);
      }
    }
    else if (cellLineProfileRow != k) {
      cellLineProfileRow = k;
      if (dataSets != null) {
        dataSets.setCellLineProfileRow(cellLineProfileRow);
      }
    }

  }
  public int getCellLineProfileRow() {
    return cellLineProfileRow;
  }
  public void setCellLineNameRow(int k) {
    if (cellLineNameRow == -1) {
      cellLineNameRow = k;
      if (dataSets != null) {
        dataSets.setCellLineNameRow(cellLineNameRow);
      }
    }
    else if (cellLineNameRow != k) {
      cellLineNameRow = k;
      if (dataSets != null) {
        dataSets.setCellLineNameRow(replicateRow);
      }
    }

  }
  public int getCellLineNameRow() {
    return cellLineNameRow;
  }
  public void setFactor3Row(int k) {
//      cellLineNameRow = k;
      if (dataSets != null) {
        dataSets.setFactor3Row(k);
      }


  }

  public void setReplicateRow(int k){
    if (replicateRow == -1) {
      replicateRow = k;
      if (dataSets != null) {
        dataSets.setRepRow(replicateRow);
      }
    }
    else if (replicateRow != k) {
      replicateRow = k;
      if (dataSets != null) {
        dataSets.setRepRow(replicateRow);
      }
    }

  }
  public int getReplicateRow(){
    return replicateRow;
  }
  public void setTechReplicateRow(int k) {
    techRepliateRow = k;
  }
  public int getTechReplicateRow(){
    return techRepliateRow;
  }
  public void setStepRow(int k) {
    stepRow = k;
  }
  public int getStepRow() {
    return stepRow;
  }

  public void setDataPlotOptionRow(int k){
    dataPlotOption = k;
    if (dataSets != null)
      dataSets.setDataPlotOption(k);
  }
  public void setXTicMark(boolean b) {
    xTicMark = b;
    if (dataSets != null)
      dataSets.setXTicMark(xTicMark);
  }
  public void setYTicMark(boolean b) {
    yTicMark = b;
    if (dataSets != null)
      dataSets.setYTicMark(yTicMark);
  }
  public int getDataPlotOptionRow(){
    return dataPlotOption;
  }
  public String [] getGeneralInfo(int row) {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
//    return dataSets.getGeneralInfo(row);
    if (dataStartCol == 0) return null;
    String [] str = new String[dataStartCol];
    ColorStringClassIO obj;
    for (int j=0;j<dataStartCol;j++) {
      obj = (ColorStringClassIO)getValueAt(row,j);
      if (obj!=null)
        str[j] =obj.toString();
      else str[j] = "";
    }
    return str;
  }
  public String [][] getGeneralInfo() {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
//    return dataSets.getGeneralInfo();

    String [][] str = new String[dataStartRow][];
    Object obj;
    for (int i=0;i<dataStartRow;i++)
      str[i] = getGeneralInfo(i);
    return str;
  }

  public String [] getColumnInfo(int row) {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
 //   return dataSets.getColumnInfo(row);
    String [] str = new String[colNum-dataStartCol];
    ColorStringClassIO obj;
    for (int j=dataStartCol;j<colNum;j++) {
      obj = (ColorStringClassIO)getValueAt(row,j);
      if (obj!=null)
        str[j-dataStartCol] =obj.toString();
      else str[j-dataStartCol] = "";
    }
    return str;
  }
  public String [][] getColumnInfo() {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
 //   return dataSets.getColumnInfo();
    String [][] str = new String[dataStartRow][];
    Object obj;
    for (int i=0;i<dataStartRow;i++)
      str[i] = getColumnInfo(i);
    return str;
  }

  public float [] getRowDecimalValue(int row) {
//    if (dataStartCol == -1) return null;
    if (dataStartRow == -1 || dataStartCol == -1) return null;
    int dataItem=colNum-dataStartCol;
    float [] dat = new float[dataItem];
    ColorStringClassIO obj;
    String str;
    float d;
    for (int j=dataStartCol;j<colNum;j++) {
      obj = (ColorStringClassIO)getValueAt(row,j);
      if (obj != null) {
        str = obj.toString();
        try {
          d  = Float.parseFloat(str);
          str = df.format(d);
          dat[j-dataStartCol] = Float.parseFloat(str);
        }
        catch (NumberFormatException ee) {
          dat[j-dataStartCol] = 0;
        }
      }
      else {
        dat[j-dataStartCol] = 0;
 //       data[row-dataStartRow][j-dataStartCol] = new ColorStringClassIO("0");
        setValueAt("0",row,j);
      }
    }
    return dat;
  }

  public float [][] getRowDecimalValue() {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
    float [][] d = new float[rowNum-dataStartRow][];
    for (int i=dataStartRow;i<rowNum;i++)
      d[i-dataStartRow] = getRowDecimalValue(i);
    return d;

  }
  public String [] getRowInfo(int row)   {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
    if (dataStartCol == 0) return null;
    String [] str = new String[dataStartCol];
    ColorStringClassIO obj;
    for (int i=0;i<dataStartCol;i++) {
      obj = (ColorStringClassIO)getValueAt(row,i);
      if (obj==null) str[i]="";
      else  str[i]  = obj.toString();
    }
    return str;
  }
  public String [][] getRowInfo() {
    if (dataStartRow == -1 || dataStartCol == -1) return null;
    String [][] str = new String[rowNum-dataStartRow][];
    for (int i=dataStartRow;i<rowNum;i++)
      str[i-dataStartRow] = getRowInfo(i);
    return str;
  }
  public DataSet getDataSets() {
    return dataSets;
  }

public void createDataSet() {
  if (dataStartCol != -1 && dataStartRow != -1) {
//    System.out.println("dataStartCol="+dataStartCol + " dataStartRow="+dataStartRow);
    System.out.println("creating dataset...");
    boolean createRowData = true;
    dataSets = new DataSet(getGeneralInfo(), getColumnInfo(),
                             getRowInfo(),  getRowDecimalValue(), msg,
                             sampleNameRow,replicateRow,dataPlotOption,cellLineProfileRow,cellLineNameRow, stepRow,createRowData,dataPlot);

   }
   else if (dataStartCol == -1 && dataStartRow == -1) {
     Display.display("Need assign data start row and column.");
   }
}
  public boolean isCellEditable(int rowIndex,
                                int columnIndex) {
    return true;
  }
  private void justifyRows(int from, int to) {
    if (dataSets == null) return;


    for (int i = from; i < to; i++) {
      if (data[i] == null) {
        data[i] = new ColorStringClassIO[colNum];
        for (int j=0;j<colNum;j++) {
          data[i][j] = new ColorStringClassIO();//setData(str[i][j]);
        }
      }
    }
  }
  public void insertRow(ColorStringClassIO [] csc,int row) {
    if (dataSets == null) return;
    if (row < 0 || row >=rowNum) return;
    int tempRowNum = rowNum+1;
    ColorStringClassIO [][] temp = new ColorStringClassIO[tempRowNum][colNum];
    for (int i=0;i<row;i++)
      temp[i] = data[i];
    temp[row] = new ColorStringClassIO[colNum];
    for (int j=0;j<colNum;j++) {
      temp[row][j] = csc[j];
    }
    for (int i=row;i<rowNum;i++)
      temp[i+1] = data[i];
    rowNum =tempRowNum;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];

  }

  public void insertRow(int row) {
    if (dataSets == null) return;
    if (row < 0 || row >=rowNum) return;
    int tempRowNum = rowNum+1;
    ColorStringClassIO [][] temp = new ColorStringClassIO[tempRowNum][colNum];
    for (int i=0;i<row;i++)
      temp[i] = data[i];
    temp[row] = new ColorStringClassIO[colNum];
    for (int j=0;j<colNum;j++) {
      temp[row][j] = new ColorStringClassIO();//setData(str[i][j]);
    }
    for (int i=row;i<rowNum;i++)
      temp[i+1] = data[i];
    rowNum =tempRowNum;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];
  }
  public void removeMarked(int col) {
//    if (dataSets == null) return;
   Object obj;
    ColorStringClassIO csc;
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    int item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
      if (csc.getColor().equals(Color.black)) {
        csc.setFlag(true);
        if (dataSets != null)
        dataSets.getRow(i-dataStartRow).setValid(true);
        item++;
      }
      else {
        csc.setFlag(false);
        if (dataSets != null)
        dataSets.getRow(i-dataStartRow).setValid(false);
      }
    }
    ColorStringClassIO [][] temp = new ColorStringClassIO[item][];
    for (int i=0;i<startRow;i++) {
      temp[i] = data[i];
    }
    item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
      if (csc.getFlag()) {
        temp[item] = data[i];
        item++;
      }
    }
    rowNum =item;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];
    if (dataSets != null)
    dataSets.removeInvalidData(rowNum-dataStartRow);
//    IsdataUpdate = false;
  }
  public void removeUnmarked(int col) {
//    if (dataSets == null) return;
   Object obj;
    ColorStringClassIO csc;
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    int item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
        if (!csc.getColor().equals(Color.black)) {
      csc.setFlag(true);
      if (dataSets != null)
      dataSets.getRow(i-dataStartRow).setValid(true);
      item++;
        }
        else {
          csc.setFlag(false);
          if (dataSets != null)
          dataSets.getRow(i-dataStartRow).setValid(false);
       }
    }
    ColorStringClassIO [][] temp = new ColorStringClassIO[item][];
    for (int i=0;i<startRow;i++) {
      temp[i] = data[i];
    }
    item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
        if (csc.getFlag()) {
      temp[item] = data[i];
      item++;
        }
    }
    rowNum =item;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];
    if (dataSets != null)
    dataSets.removeInvalidData(rowNum-dataStartRow);
  }
  public void removeRow(int row) {
//    if (dataSets == null) return;
//    if (row < dataStartRow) return;
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;

    int tempRowNum = rowNum-1;
    ColorStringClassIO [][] temp = new ColorStringClassIO[tempRowNum][colNum];
    for (int i=0;i<startRow;i++) {
      temp[i] = data[i];
    }

    for (int i=startRow;i<row;i++) {
      temp[i] = data[i];
      if (dataSets != null)
      dataSets.getRow(i-dataStartRow).setValid(true);
    }
    if (dataSets != null)
    dataSets.getRow(row-dataStartRow).setValid(false);
    for (int i=row+1;i<rowNum;i++) {
      temp[i-1] = data[i];
      if (dataSets != null)
      dataSets.getRow(i-dataStartRow).setValid(true);
    }
    rowNum =tempRowNum;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];
    if (dataSets != null)
    dataSets.removeInvalidData(rowNum);
//    IsdataUpdate = false;
  }
  /*
  public void undoRemove() {
    if (dataSets == null || dataSets.getRemovedRowData() == null
        || dataSets.getRemovedRowData().length == 0) return;

  }
*/

  public void saveData() {
    File f = FileIO.saveSingleFile();
    if (f==null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      String expNameList = "";
      ColorStringClassIO o;
      for (int i=0;i<rowNum;i++) {
        for (int j=0;j<colNum;j++) {
          o=  (ColorStringClassIO)getValueAt(i, j);
          if (o != null) {
            out.print(o.toString());
          }
          else {
            out.print("\t");
          }
          if (j==colNum-1)
            out.print("\n");
          else
            out.print("\t");
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
  public void saveDataColorMarkedData(int col,boolean isColorMarked) {
    File f = FileIO.saveSingleFile();
    if (f==null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      String expNameList = "";
      ColorStringClassIO o;
      for (int i=0;i<dataStartRow;i++) {
        for (int j=0;j<colNum;j++) {
          o=  (ColorStringClassIO)getValueAt(i, j);
          if (o != null) {
            out.print(o.toString());
          }
          else {
            out.print("\t");
          }
          if (j==colNum-1)
            out.print("\n");
          else
            out.print("\t");
        }
      }

      for (int i=dataStartRow;i<rowNum;i++) {
        o = (ColorStringClassIO)getValueAt(i,col);
        if (isColorMarked && o.getColor().equals(Color.red)) {
        for (int j=0;j<colNum;j++) {
          o=  (ColorStringClassIO)getValueAt(i, j);
          if (o != null) {
            out.print(o.toString());
          }
          else {
            out.print("\t");
          }
          if (j==colNum-1)
            out.print("\n");
          else
            out.print("\t");
        }
      }
      else if (!isColorMarked && o.getColor().equals(Color.black)) {
        for (int j=0;j<colNum;j++) {
          o=  (ColorStringClassIO)getValueAt(i, j);
          if (o != null) {
            out.print(o.toString());
          }
          else {
            out.print("\t");
          }
          if (j==colNum-1)
            out.print("\n");
          else
            out.print("\t");
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

    /*
       Object obj;
    ColorStringClassIO csc;
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    int item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
      if (csc.getColor().equals(Color.black)) {
        csc.setFlag(true);
        if (dataSets != null)
        dataSets.getRow(i-dataStartRow).setValid(true);
        item++;
      }
      else {
        csc.setFlag(false);
        if (dataSets != null)
        dataSets.getRow(i-dataStartRow).setValid(false);
      }
    }
    ColorStringClassIO [][] temp = new ColorStringClassIO[item][];
    for (int i=0;i<startRow;i++) {
      temp[i] = data[i];
    }
    item=startRow;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
      if (csc.getFlag()) {
        temp[item] = data[i];
        item++;
      }
    }
    rowNum =item;
    data = new ColorStringClassIO[rowNum][colNum];
    for (int i=0;i<rowNum;i++)
      data[i] = temp[i];
    if (dataSets != null)
    dataSets.removeInvalidData(rowNum-dataStartRow);

    */
  }

  public void saveTransposedData() {
    File f = FileIO.saveSingleFile();
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      String expNameList = "";
      ColorStringClassIO o;
      for (int j=0;j<colNum;j++) {
        for (int i=0;i<rowNum;i++) {
          o=  (ColorStringClassIO)getValueAt(i, j);
          if (o != null) {
            out.print(o.toString());
          }
          else {
            out.print("\t");
          }
          if (i==rowNum-1)
            out.print("\n");
          else
            out.print("\t");
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
  public void saveDataWithFeature() {
//    if (dyeSwapCorrectionMag == null) {
      saveData();
//      return;
//    }


  }

  public void sortingDecimal(int selectedCol, boolean acending) {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    int item = rowNum-dataStartRow;
    ColorStringClassIO [] compareData = new ColorStringClassIO[item];
//    System.out.println("selected column: " + selectedCol);
    for (int i=dataStartRow;i<rowNum;i++) {
      compareData[i-dataStartRow] = data[i][selectedCol];
    }
    float [] floatData = DataConversion.convertColorStringClassToFloat(compareData);
    if (floatData != null) {
//      System.out.println("decimal sorting");
      Sorting.quicksort(acending,floatData,dataSets.getRowData());
      for (int i=dataStartRow;i<rowNum;i++) {
        updateCorrectedValue(i,dataSets.getRow(i-dataStartRow));
      }
    }
  }

  public void sortingString(int selectedCol, boolean acending) {
    if (dataSets==null) {
      return;
    }
    int item = rowNum-dataStartRow;
    ColorStringClassIO [] compareData = new ColorStringClassIO[item];
//    System.out.println("selected column: " + selectedCol);
    for (int i=0;i<item;i++) {
      compareData[i] = data[i+dataStartRow][selectedCol];
    }
//       System.out.println("string sorting");
    Sorting.quicksort(acending,compareData,dataSets.getRowData());
    for (int i=0;i<item;i++) {
      updateCorrectedValue(i+dataStartRow,dataSets.getRow(i));
    }
  }
  public void sorting(int selectedCol, int startRow,boolean acending) {
    if (startRow == -1) startRow=0;
      ColorStringClassIO [] compareData = new ColorStringClassIO[rowNum-startRow];
      Object [] o = new Object[rowNum-startRow];
//      System.out.println("selected column: " + selectedCol);
      for (int i=startRow;i<rowNum;i++) {
        compareData[i-startRow] = data[i][selectedCol];
        o[i-startRow] = data[i];
        if (compareData[i-startRow]==null)
          compareData[i-startRow] = new ColorStringClassIO("");
      }
      float [] floatData = DataConversion.convertColorStringClassToFloat(compareData);
      if (floatData==null)
      Sorting.quicksort(acending,compareData,o);
      else
        Sorting.quicksort(acending,floatData,o);
      for (int i=startRow;i<rowNum;i++) {
        updateCorrectedValue(i,o[i-startRow]);
      }
      return;
  }
  public void sameRowDataTobeMerged(int selectedCol) {
    int item = rowNum-dataStartRow;
    sortingString(selectedCol, true);

    Vector newList = new Vector();
    Vector mergedList = new Vector();
    boolean sameGene = false;
    float [] y;
    for (int i=dataStartRow;i<rowNum-1;i++) {
      if (!sameGene && !(data[i][selectedCol].toString()).equals( data[i+1][selectedCol].toString() )) {
        newList.add(data[i]);      // condition not same gene yet, not same id
        if (i == rowNum-2) {
          newList.add(data[i+1]);
        }
      }
      else if (!sameGene){
        mergedList.add(data[i]);  // condition not same gene yet, but same id
        sameGene = true;              // set to same gene
        if (i == rowNum-2) {
          mergedList.add(data[i+1]);
          ColorStringClassIO [] gd = getMergedGeneData(mergedList);
          newList.add(gd);
        }
      }
      else if (sameGene && !(data[i][selectedCol].toString()).equals( data[i+1][selectedCol].toString() )) {
        mergedList.add(data[i]);  //  condition same gene, but not same id
        ColorStringClassIO [] gd = getMergedGeneData(mergedList);
        mergedList = new Vector();
        newList.add(gd);
        sameGene = false;
        if (i == rowNum-2)
          newList.add(data[i+1]);
      }
      else {  // condistion same gene and same id
        mergedList.add(data[i]);
        if (i == rowNum-2) {
          mergedList.add(data[i+1]);
          ColorStringClassIO [] gd = getMergedGeneData(mergedList);
          newList.add(gd);
        }
      }
    }

    int mergedRowNum =newList.size();
    ColorStringClassIO [][] mergedData = new ColorStringClassIO[mergedRowNum][];
    for (int i=0;i<mergedRowNum;i++)
      mergedData[i] = (ColorStringClassIO [])newList.elementAt(i);
    rowNum = dataStartRow+mergedRowNum;
    ColorStringClassIO [][] newNergedData = new ColorStringClassIO[rowNum][];
    for (int i=0;i<dataStartRow;i++)
      newNergedData[i] = data[i];
    for (int i=dataStartRow;i<rowNum;i++)
      newNergedData[i] = mergedData[i-dataStartRow];
    data = new ColorStringClassIO[rowNum][];
    for (int i=0;i<rowNum;i++)
      data[i] = newNergedData[i];
    Display.display("merged");
    //unfinished&&&&&&&&&&&&&&&&&&&&&&&&&&
//    IsdataUpdate = false;
  }
  private ColorStringClassIO [] getMergedGeneData(Vector v) {
    int mergedGeneNum = v.size();
    ColorStringClassIO [][] tempData = new ColorStringClassIO[mergedGeneNum][];
    for (int i=0;i<mergedGeneNum;i++)
      tempData[i] = (ColorStringClassIO [])v.elementAt(i);
    float [] tempValue = new float[colNum-dataStartCol];
    for (int i=0;i<tempValue.length;i++) {
      tempValue[i] =0;
    }
    for (int j=0;j<mergedGeneNum;j++)
      for (int i=dataStartCol;i<colNum;i++)
        try {
        float d  = Float.parseFloat(tempData[j][i].toString());
        tempValue[i-dataStartCol] += d;
      }
      catch (NumberFormatException ee) {
        return null;
      }
      for (int i=0;i<tempValue.length;i++) {
        tempValue[i] /= mergedGeneNum;
        tempData[0][i+dataStartCol].setData(tempValue[i]+"");
      }
      return tempData[0];
  }


public void dyeSwapAnalysis() {
  if (dataSets == null ) {
    return;
  }


if (techRepliateRow==-1) {
  msg.display("tech repliate row not assigned yet.");
  return;
}

int item = colNum-dataStartCol;

//  String [] dyeSwapName = new String[item];
  String [] techRepName = new String[item];
  for (int i=0;i<item;i++) {
//    dyeSwapName[i] = data[dyeSwapLabelRow][i+dataStartCol].toString();
    techRepName[i] = data[techRepliateRow][i+dataStartCol].toString();
  }
  int [][] techRepIndex = FactorAnalysis.createTwoLevelNameIndex(techRepName);
//  int [] dyeSwapLabelIndex =  DataConversion.createOneLevelNameIndex(dyeSwapName);

dataSets.dyeSwapAnalysis(techRepIndex);
}
  public void dyeSwapCorrection() {
    if (dyeSwapLabelRow==-1) {
      msg.display("Cy3/Cy5 label row not assigned yet.");
      return;
    }
    if (techRepliateRow==-1) {
      msg.display("tech repliate row not assigned yet.");
      return;
    }
    if (dataSets == null) {
      msg.display("dataset not created yet.");
      return;
    }
    int item = colNum-dataStartCol;
    String [] dyeSwapName = new String[item];
    String [] techRepName = new String[item];
    for (int i=0;i<item;i++) {
      dyeSwapName[i] = data[dyeSwapLabelRow][i+dataStartCol].toString();
      techRepName[i] = data[techRepliateRow][i+dataStartCol].toString();
    }
      int [][] techRepIndex = FactorAnalysis.createTwoLevelNameIndex(techRepName);
      int [] dyeSwapLabelIndex =  FactorAnalysis.createOneLevelNameIndex(dyeSwapName);
      if (dyeSwapLabelIndex==null || techRepIndex==null) return;
      for (int i=dataStartRow;i<rowNum;i++) {
        updateCorrectedValue(i,dataStartCol,colNum,dataSets.dyeSwapCorrection(i-dataStartRow,techRepIndex,dyeSwapLabelIndex));
      }
  }

  private void updateCorrectedValue(int row, int startCol,int endCol,float [] newData) {
    if (newData == null) {
      System.out.println("Row "+row+ " is null. No dye swap corrected.");
    }
    if (newData != null)
    for (int i=startCol;i<endCol;i++)
      data[row][i].setData(newData[i-startCol]+"");
    else
      for (int i=startCol;i<endCol;i++)
        data[row][i].setData(0+"");
  }
  private void updateCorrectedValue(int row, Object rowData) {
    if (rowData != null && rowData instanceof RowDataIO) {
//      System.out.println("Row "+row+ " is null. No dye swap corrected.");
    for (int i=0;i<dataStartCol;i++)
      data[row][i].setData(((RowDataIO)rowData).getRowInfo(i));
    for (int i=dataStartCol;i<colNum;i++)
      data[row][i].setData(((RowDataIO)rowData).getAData(i-dataStartCol)+"");
  }
  else if (rowData != null && rowData instanceof ColorStringClassIO[]) {
    data[row] = (ColorStringClassIO [])rowData;
  }
  }

  public ColorStringClassIO [] getData(int row, int colStart, int colEnd) {
    int item=colEnd-colStart;
    ColorStringClassIO [] o = new ColorStringClassIO[item];
    for (int i=0;i<item;i++)
      o[i] = data[row][i+colStart];
    return o;
  }
  public String [][] getData() {
    String [][] str = new String[data.length][data[0].length];
    for (int i=0;i<data.length;i++)
      for (int j=0;j<data[0].length;j++)
        str[i][j] = (String)data[i][j].getData();
    return str;
  }

  public void cellLineNormalization() {
    if (dataSets == null || cellLineNameRow==-1 || cellLineProfileRow==-1) {
      msg.display("dataset is null or cell line name or profile row not assigned yet.");
      return;
    }
   else {
     for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.cellLineNormalization(i-dataStartRow));
     }

   }

  }
public void cellLineCorrectionToAverage() {
  if (dataSets == null || cellLineNameRow==-1 || cellLineProfileRow==-1) {
    msg.display("dataset is null or cell line name or profile row not assigned yet.");
    return;
  }
 else {
   for (int i=dataStartRow;i<rowNum;i++) {
     updateCorrectedValue(i,dataStartCol,colNum,dataSets.cellLineCorrectionToAverage(i-dataStartRow));
   }
 }
}
  public void cellLineCorrectionToShamAverage() {
    if (dataSets == null || cellLineNameRow==-1 || cellLineProfileRow==-1) {
      msg.display("dataset is null or cell line name or profile row not assigned yet.");
      return;
    }
   else {
     for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.cellLineCorrectionToShamAverage(i-dataStartRow));
     }
   }
  }
  public void cellLineCorrectionToFirstShamAverage() {
    if (dataSets == null || cellLineNameRow==-1 || cellLineProfileRow==-1) {
      msg.display("dataset is null or cell line name or profile row not assigned yet.");
      return;
    }
   else {
     for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.cellLineCorrectionToFirstShamAverage(i-dataStartRow));
     }
   }
  }

  public void cellLineCorrectionToZero() {

    if (dataSets == null || dataSets.cellLineName==null || dataSets.cellLineProfileIndex==null) {
      msg.display("dataset is null or cell line name or profile row not assigned yet.");
      return;
    }
   else {
     for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.cellLineCorrectionToZero(i-dataStartRow));
     }
   }
  }

  public void dyeSwapStandardDeviation() {
    if (dataSets == null) return;
    float [] cy3Data,cy5Data,cy3_cy5;
    float std;
    for (int i=dataStartCol;i<colNum;i+=2) {
      cy3Data = dataSets.getColumnData_1(i-dataStartCol);
      cy5Data = dataSets.getColumnData_1(i+1-dataStartCol);
      if (cy3Data != null && cy5Data != null) {
        cy3_cy5 = new float[cy3Data.length];
        for (int j=0;j<cy3Data.length;j++)
          cy3_cy5[j] = cy3Data[j]-cy5Data[j];
        std = Statistics.stdDev(cy3_cy5);
        msg.messageDisplay(columnNameClass[i].getColumnName()+"-"+
                           columnNameClass[i+1].getColumnName()+" standard deviation:\t"+df.format(std)+"\n");
      }
    }

  }
  public DataSet replicateArrayMerge() {
    DataSet dataSet = getDataSets();

    String [][] mergedRowInfo = dataSet.getRowInfo();

    String [][] generalInfo = dataSet.getGneralInfo();
    String [][] mergedGeneralInfo = new String[1][];
    mergedGeneralInfo[0] = dataSet.getGeneralInfo(dataSet.getRepRow());

    String [][] columnInfo = dataSet.getColumnInfo();
    int numMergedArrayNum = dataSet.getTrimRepName().length;
    String [][] mergedColumnInfo = new String[1][numMergedArrayNum];
    for (int i=0;i<numMergedArrayNum;i++) {
      mergedColumnInfo[0][i] = dataSet.getTrimRepName()[i];
    }

    float [][] decomposedData = new float[dataSet.getRepNameIndex().length][];
   for (int i=0;i<dataSet.getRepNameIndex().length;i++)
     decomposedData[i] = new float[dataSet.getRepNameIndex()[i].length];

    int numDataRow = dataSet.getNumberDataRow();
    float [][] mergedData =  new float[numDataRow][numMergedArrayNum];
    for (int i=0;i<numDataRow;i++) {
      float [] data = dataSet.getRowData(i);
      mergedData[i] = DataSet.getMergedRowData(data,dataSet.getRepNameIndex(),decomposedData);
    }
    DataSet ds = new DataSet(mergedGeneralInfo, mergedColumnInfo,
                             mergedRowInfo, mergedData, msg,
                             0, -1,-1,-1,-1,stepRow,true,dataPlot);
    ds.setMergedColumn(dataSet.getRepNameIndex());
    return ds;

  }

  public int [] getInvalidRowList() {
    if (dataSets == null) {
      System.out.println("DataSet is not created yet.");
      return null;
    }
    String strValue;
    TreeSet v = new TreeSet();
    int numZero=1;
    for (int i = dataStartRow; i<rowNum ; i++) {
      if (Statistics.zeroTest(dataSets.getRowData(i-dataStartRow),numZero)) {
        v.add(""+i);
      }
    }
    if (v.size()==0) return null;
    Object [] o = v.toArray();
    int [] index = new int[o.length];
    for (int i=0;i<o.length;i++) {
      index[i] = Integer.parseInt((String)o[i]);
    }
    return index;
  }
  public int [] getConjunctionList(int col,String [] strList) {
    if (strList == null) return null;
    Object objValue;
    String strValue;
    TreeSet v = new TreeSet();
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    for (int k=0;k<strList.length;k++) {
      for (int i = startRow; i<rowNum ; i++) {
        objValue = getValueAt(i,col);
        if (objValue != null) {
          strValue = ((ColorStringClassIO)objValue).toString();
          if (strValue.equals(strList[k])){
            v.add(""+i);
            i = rowNum;
          }
        }
      }
    }
    if (v.size()==0) return null;
    Object [] o = v.toArray();
    int [] index = new int[o.length];
    for (int i=0;i<o.length;i++) {
      index[i] = Integer.parseInt((String)o[i]);

    }
    return index;

  }
  public void highlightRowDataContainsZero() {
   ColorStringClassIO csc;
    for (int i=dataStartRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,0);
      if (Statistics.isContainsZero(dataSets.getRowData(i-dataStartRow))) {
          csc.setColor(Color.red);
          dataSets.setRowInfoColor(i-dataStartRow,0,Color.red);
      }
      else {
        csc.setColor(Color.black);
      dataSets.setRowInfoColor(i-dataStartRow,0,Color.black);
      }
    }
  }
//  isAllLessThan
//  isOneLessThan
  public void containg_all_less_than(String str_value) {
    float value;
    try {
  value  = Float.parseFloat(str_value);
}
catch (NumberFormatException ee) {
  return;
}
   ColorStringClassIO csc;
    for (int i=dataStartRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,0);
      if (Statistics.isAllLessThan(dataSets.getRowData(i-dataStartRow),value)) {
          csc.setColor(Color.red);
          dataSets.setRowInfoColor(i-dataStartRow,0,Color.red);
      }
      else {
        csc.setColor(Color.black);
      dataSets.setRowInfoColor(i-dataStartRow,0,Color.black);
      }
    }
  }
  public void containg_one_less_than(String str_value) {
    float value;
    try {
  value  = Float.parseFloat(str_value);
}
catch (NumberFormatException ee) {
  return;
}
   ColorStringClassIO csc;
    for (int i=dataStartRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,0);
      if (Statistics.isOneLessThan(dataSets.getRowData(i-dataStartRow),value)) {
          csc.setColor(Color.red);
          dataSets.setRowInfoColor(i-dataStartRow,0,Color.red);
      }
      else {
        csc.setColor(Color.black);
      dataSets.setRowInfoColor(i-dataStartRow,0,Color.black);
      }
    }
  }

  public int [] getNegConjunctionList(int col,String [] strList) {
    if (strList == null) return null;
    Object objValue;
    String strValue;
    TreeSet v = new TreeSet();
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    for (int i = startRow; i<rowNum ; i++)
      v.add(""+i);
    for (int k=0;k<strList.length;k++)
      for (int i = startRow; i<rowNum ; i++) {
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
      Object [] o = v.toArray();
      int [] index = new int[o.length];
      for (int i=0;i<o.length;i++) {
        index[i] = Integer.parseInt((String)o[i]);

      }
      return index;

  }

  public void updateStringSearch( int col,int [] findIndex) {
    ColorStringClassIO csc;
    int columnNum = col;
    int startRow = dataStartRow;
    if (startRow < 0) startRow = 0;
    for (int i=startRow;i<rowNum;i++) {
      csc = (ColorStringClassIO)getValueAt(i,col);
      if (csc != null)
        csc.setColor(Color.black);
      if (dataSets!=null)
     dataSets.setRowInfoColor(i-dataStartRow,columnNum,Color.black);
    }
    if (findIndex == null) {
      return;
    }
    for (int i=0;i<findIndex.length;i++) {
      if(findIndex[i] >= dataStartRow) {
        csc = (ColorStringClassIO)getValueAt(findIndex[i],col);
        if (csc != null)
          csc.setColor(Color.red);
//        System.out.println("row color red row "+(findIndex[i]-dataStartRow));
        if (dataSets!=null)
        dataSets.setRowInfoColor(findIndex[i]-dataStartRow,columnNum,Color.red);
      }
    }

  }
  public void highlight_missing_value_row() {
/*    ColorStringClassIO csc;
     int columnNum = col;
     int startRow = dataStartRow;
     if (startRow < 0) startRow = 0;
     for (int i=startRow;i<rowNum;i++) {
       csc = (ColorStringClassIO)getValueAt(i,col);
       if (csc != null)
         csc.setColor(Color.black);
       if (dataSets!=null)
      dataSets.setRowInfoColor(i-dataStartRow,columnNum,Color.black);
     }
     if (findIndex == null) {
       return;
     }
     for (int i=0;i<findIndex.length;i++) {
       if(findIndex[i] >= dataStartRow) {
         csc = (ColorStringClassIO)getValueAt(findIndex[i],col);
         if (csc != null)
           csc.setColor(Color.red);
//        System.out.println("row color red row "+(findIndex[i]-dataStartRow));
         if (dataSets!=null)
         dataSets.setRowInfoColor(findIndex[i]-dataStartRow,columnNum,Color.red);
       }
     }
*/

    ColorStringClassIO csc;
    for (int i=dataStartRow;i<rowNum;i++) {
      for (int j=0;j<data[i].length;j++) {
       csc = (ColorStringClassIO)getValueAt(i,j);
        if (csc == null || csc.toString().equals("")) {
          csc = (ColorStringClassIO)getValueAt(i,0);
          csc.setColor(Color.red);
          dataSets.setRowInfoColor(i-dataStartRow,0,Color.red);
          j =data[i].length;
        }
        }
    }
  }

  public void purity_linearCombination(String [][] str) {
    if (dataSets == null || dataSets.getRepNameIndex()==null ||
        dataSets.getCellLineNameIndex()==null || dataSets.getCellLineProfileIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }
    dataSets.purity_linearCombination(str);


//      Display.display("B");

  }
  public void purityCorrection(String [][] str) {
    if (dataSets == null || dataSets.getRepNameIndex()==null ||
        dataSets.getCellLineNameIndex()==null || dataSets.getCellLineProfileIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }
    int numberCellLine = dataSets.getCellLineNameIndex().length;
//    Display.display("numberCellLine = "+numberCellLine);  // = 6
    int numberArray_in_eachCellline = dataSets.getCellLineNameIndex()[0].length;
//    Display.display("numberArray_in_eachCellline = "+numberArray_in_eachCellline);  // = 4

    float [][] purityAdjustCoff = DataConversion.convertStringToFloat(str);
    if (purityAdjustCoff==null)return;

    int numberCoffColDataItem=purityAdjustCoff[0].length;
//    Display.display("number of coefficients in each row = "+numberCoffColDataItem);  //=4
    int totalCoffRowNumber = purityAdjustCoff.length;
//    Display.display("total Row Number of coefficients = "+totalCoffRowNumber); //=24
    for (int i=1;i<totalCoffRowNumber;i++)
      if (purityAdjustCoff[i].length != numberCoffColDataItem) {
    msg.display("Input data rows have different number of columns.");
    return;
      }

      int rowNumberPerCellLine=totalCoffRowNumber/numberCellLine; // 24/6 = 4
//      Display.display("row Number of coefficients Per CellLine = "+rowNumberPerCellLine);   // =4        //8

      if (numberCoffColDataItem != rowNumberPerCellLine)   {
        msg.display("row number in each cell line is different from the column number.");
        return;
      }

//      Display.display("B");

    float [][][] coffEachCellLine = new float[numberCellLine][][]; // 6
    int k=0;
    for (int i=0;i<numberCellLine;i++) {
      coffEachCellLine[i] = new float[rowNumberPerCellLine][];  // 4
      for (int j=0;j<rowNumberPerCellLine;j++)
        coffEachCellLine[i][j] = purityAdjustCoff[k++];
    }
    Matrix [] ma = new Matrix[numberCellLine];
    Matrix [] invert_ma = new Matrix[numberCellLine];
    float [][][] invertMatrix = new float[numberCellLine][][];
    for (int i=0;i<numberCellLine;i++) {
      ma[i] = new Matrix(coffEachCellLine[i]);
      invert_ma[i] = ma[i].inverse();
      invertMatrix[i] = invert_ma[i].getArray();
    }
//    Display.display("C");
   float [][] decomposedData = new float[dataSets.getCellLineNameIndex().length][];
   for (int i=0;i<dataSets.getCellLineNameIndex().length;i++)
     decomposedData[i] = new float[dataSets.getCellLineNameIndex()[i].length];
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.purityCorrection(i-dataStartRow,invertMatrix,decomposedData));
    }
//    Display.display("D");
  }

  public void averageSelectedRows(int [] selectedRow) {
    if (dataSets == null ) {
      return;
    }
    dataSets.averageSelectedRows(selectedRow);
  }
  public void plotRow(int [] selectedRow,boolean blackline,int plotOption) {
    if (dataSets == null ) {
      return;
    }
    dataSets.plotRow_GDTM(selectedRow,blackline,plotOption);
  }
  public void pairwiseCorrelation(int [] selectedRow,boolean blackline,int plotOption, File f) {
    if (dataSets == null ) {
      return;
    }
    dataSets.pairwiseCorrelation(selectedRow,f);
  }

  public void plotThumbnails(int [] selectedRow, boolean withError,boolean blackline,int plotOption) {
 //   Display.display("1 plotThumbnails");
    if (dataSets == null ) {
      return;
    }
    dataSets.plotThumbnails(selectedRow, withError,blackline,plotOption);
 //   dataSets.plotRow_GDTM(selectedRow, withError,blackline,plotOption);
  }

  public void normalDeviates() {

    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }

    float [] deviateData = new float[dataSets.getRowData(0).length];

    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
   for (int i=0;i<dataSets.getRepNameIndex().length;i++)
     decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];


       for (int i=dataStartRow;i<rowNum;i++) {
         dataSets.normalDeviates(i-dataStartRow,decomposedData,deviateData);
        updateCorrectedValue(i,dataStartCol,colNum,deviateData);
      }
 }
 public void avg_std_reserved_normalDeviates() {

   if (dataSets == null || dataSets.getRepNameIndex()==null) {
     msg.display("Bioreplicate row not assigned yet.");
     return;
   }
      for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.avg_std_reserved_normalDeviates(i-dataStartRow));
     }
}
  public void normalDeviatesReplaceZero() {

    if (dataSets==null || dataSets.getRepNameIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }

    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];

      for (int i=dataStartRow;i<rowNum;i++) {
       updateCorrectedValue(i,dataStartCol,colNum,dataSets.normalDeviatesReplaceZero(i-dataStartRow,decomposedData));
     }
  }

  public void profileReplicateAverageAndStandardDeviation() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      return;
    }
    dataSets.profileReplicateAverageAndStandardDeviation();
  }
  public void profileReplicateStandardDeviationNormalization() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      return;
    }
    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];
    for (int i=dataStartRow;i<rowNum;i++) {
     updateCorrectedValue(i,dataStartCol,colNum,dataSets.std_normalization(i-dataStartRow,decomposedData));
   }

   }

  public void profileReplicateAverage() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      return;
    }
    dataSets.profileReplicateAverage();
  }

  public void exportDyeSwapMergedFile() {
    if (dataSets == null) {
      return;
    }
    int item = colNum-dataStartCol;
    String [] techRepName = new String[item];
    String [] bioRepName = null;
    for (int i=0;i<item;i++) {
      techRepName[i] = data[techRepliateRow][i+dataStartCol].toString();
    }
    if (replicateRow != -1 ) {
      bioRepName =  new String[item];
      for (int i=0;i<item;i++) {
        bioRepName[i] = data[replicateRow][i+dataStartCol].toString();
      }
    }
      int [][] techRepIndex = FactorAnalysis.createTwoLevelNameIndex(techRepName);

    dataSets.exportDyeSwapMergedFile(techRepIndex,techRepName,bioRepName);
  }
  public void subtractReplicateAverage() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }

    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];

       for (int i=dataStartRow;i<rowNum;i++) {
        updateCorrectedValue(i,dataStartCol,colNum,dataSets.subtractReplicateAverage(i-dataStartRow,decomposedData));
      }
  }
  public void get_equal_between_group_average() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }
    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];
       for (int i=dataStartRow;i<rowNum;i++) {
        updateCorrectedValue(i,dataStartCol,colNum,dataSets.get_equal_between_group_average(i-dataStartRow,decomposedData));
      }
  }
  public void subtractFirstReplicateAverage() {
    if (dataSets == null || dataSets.getRepNameIndex()==null) {
      msg.display("Bioreplicate row not assigned yet.");
      return;
    }
    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];

       for (int i=dataStartRow;i<rowNum;i++) {
        updateCorrectedValue(i,dataStartCol,colNum,dataSets.subtractFirstReplicateAverage(i-dataStartRow,decomposedData));
      }
  }



  public void rowData_t_test(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_t_test(filename);
  }
  public void rowData_subprofiles_correlation(String filename) {
    if (dataSets == null  || dataSets.getCellLineNameIndex()==null ) {
      return;
    }
    dataSets.rowData_subprofiles_correlation(filename);
//    dataSets.rowData_t_test(filename);
  }
  /*
  public void rowData_F_test(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_F_test(filename);
  }
  */
  public void rowData_paired_sample_t_test(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_paired_sample_t_test(filename);
  }
  public void rowData_derivative(String filename) {
    if (dataSets == null   ) {
      return;
    }
    dataSets.rowData_derivative(filename);
  }

  public void rowData_logisticConversion_linearFitting(int i) {
    if (dataSets == null   ) {
      return;
    }
    dataSets.rowData_logisticConversion_linearFitting(i,8);
  }
  public void rowData_logisticFitting(int i) {
    if (dataSets == null   ) {
      return;
    }
    dataSets.rowData_logisticFitting(i);
  }

  public void rowData_derivative(int i) {
    if (dataSets == null   ) {
      return;
    }
    dataSets.rowData_derivative(i);
  }
  public void rowData_deglitch(int i) {
    if (dataSets == null   ) {
      return;
    }
    dataSets.rowData_deglitch(i);
  }

  public void rowData_derivative(File [] f) {
    if (dataSets == null   ) {
      return;
    }
   dataSets.rowData_derivative(f,dataStartRow,dataStartCol);
  }

  public void insertValues_with_Col_Row_ID(File [] f, int selectedCol) {
    if (dataSets == null   ) {
      return;
    }


    Properties row_KeyID = new Properties();
    Properties col_KeyID = new Properties();
    String value,key;
    for (int i=dataStartRow;i<rowNum;i++) {
      key = data[i][0].toString()+"_"+data[i][1].toString()+"_"+data[i][2].toString();
      value = ""+i;
      row_KeyID.setProperty(key,value);
    }

    for (int j=dataStartCol;j<colNum;j++) {
      key = data[0][j].toString();
      value = ""+j;
      col_KeyID.setProperty(key,value);
    }

    int rowIndex,colIndex;


    String [][] str;
    String rowValue, rowKey,colValue,colKey;
    for (int i=0;i<f.length;i++) {
      str = FileIO.read_txt(f[i],"\t");
      for (int j=0;j<str.length;j++) {
        rowKey = str[j][0]+"_"+str[j][2]+"_"+str[j][3];
        rowValue = row_KeyID.getProperty(rowKey);
        colKey = str[j][1];
        colValue = col_KeyID.getProperty(colKey);
        if (rowValue != null && colValue != null) {
          try {
            rowIndex = Integer.parseInt(rowValue);
            colIndex = Integer.parseInt(colValue);
            data[rowIndex][colIndex] = new ColorStringClassIO(str[j][selectedCol]);
          }
          catch (NumberFormatException ee) {
            msg.display(rowValue + " or "+ colValue + " are not integer");
//            return;;
          }

        }
      }
//      rowData_derivative( f[i],startRow,startCol);
    }



 //   for (int i=dataStartRow;i<rowNum;i++)
 //     for (int j=dataStartCol;j<colNum;j++) {
 //        data[i][j] = new ColorStringClassIO(i*j+"");
 //     }
  }

  public void rowData_ANOVA(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_ANOVA(filename);
  }
  /*
  public void rowData_variance_norm_test(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_variance_norm_test(filename);
  }
*/
  public void rowData_twoWayANOVA(String filename) {
    if (dataSets == null ) {
      return;
    }
    dataSets.rowData_twoWayANOVA(filename);
  }
  public void rowData_threeWayANOVA(String filename) {
    if (dataSets == null ) {
      return;
    }
//    dataSets.rowData_threeWayANOVA_unequal_number_of_replicate(filename);
    dataSets.rowData_threeWayANOVA(filename);
  }

  public void rowData_ANOVA_with_baseline(String filename) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_ANOVA_with_baseline(filename);
  }

  public void rowData_SNR(String filename,boolean relativeChange) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_SNR(filename, relativeChange);
  }
  /*
  public void rowData_SNR_bootstrapping(String filename, int number_of_bootstrapping) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_SNR_bootstrapping(filename,number_of_bootstrapping);
  }
  public void rowData_SNR_bootstrapping_error_type_II(String filename, int number_of_bootstrapping) {
    if (dataSets == null  || dataSets.getRepNameIndex()==null ) {
      return;
    }
    dataSets.rowData_SNR_bootstrapping_error_type_II(filename,number_of_bootstrapping);
  }
*/

  public void columnSubtractionAndAverage() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    if (dyeSwapLabelRow==-1 || techRepliateRow==-1) {
      msg.display("paired column data subtraction (col2-col1) and average (col1+col2)/2");
      for (int i=dataStartRow;i<rowNum;i++)
        updateCorrectedValue(i,dataStartCol,colNum,dataSets.columnSubtractionAndAverage(i-dataStartRow));
    }
    else
    for (int i=dataStartRow;i<rowNum;i++)
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.columnSubtractionAndAverage(i-dataStartRow));
  }

  public void rowAverageAndStandardDeviation() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    dataSets.rowAverageAndStandardDeviation();


  }
  public void columnStatistics() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    dataSets.columnStatistics();

  }
  public void columnCorrelations() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    dataSets.columnCorrelations();
   }

  public void exp2Conversion(int col) {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.exp2Conversion(i-dataStartRow,col-dataStartCol));
    }

  }
  public void minusOperation(int col) {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.minusOperation(i-dataStartRow,col-dataStartCol));
    }

  }
  public void minusOperation() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.minusOperation(i-dataStartRow));
    }
  }

  public void rowDataNormalization() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.rowDataNormalization(i-dataStartRow));
   }
  }
  public void rowDataSubtractionAverage() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.rowDataSubtractionAverage(i-dataStartRow));
   }
  }
  public void profileSimilarity_in_patterns() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    dataSets.profileSimilarity_in_patterns();
//    for (int i=dataStartRow;i<rowNum;i++) {
//      updateCorrectedValue(i,dataStartCol,colNum,dataSets.rowDataSubtractionAverage(i-dataStartRow));
//   }
  }

  public void logisticConversion() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
//      updateCorrectedValue(i,dataStartCol,colNum,dataSets.log2Conversion(i-dataStartRow));
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.logisticConversion(i-dataStartRow));
   }
  }
  public void log2Conversion() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.log2Conversion(i-dataStartRow));
   }
  }

  public void exp2Conversion() {
    if (dataSets==null) {
      msg.display("dataset is null.");
      return;
    }
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.exp2Conversion(i-dataStartRow));
    }
  }
  public void updateDataSetValue() {
    for (int i=dataStartRow;i<rowNum;i++) {
      updateCorrectedValue(i,dataStartCol,colNum,dataSets.getRowData(i-dataStartRow));
    }

  }
  public void loadFilesToGetMappingOutput(File [] f, String delimit, int selectedCol, int startRow) {
    if (startRow < 0) startRow = 0;
   System.out.println("Selected Column "+selectedCol);
   Properties id_mapping = new Properties();
   Object o;
   String key;
   for (int i=startRow;i<rowNum;i++) {
     o = getValueAt(i,selectedCol);
     if (o != null) {
     key = ((ColorStringClassIO)o).toString();
     if (id_mapping.containsKey(key)) {
       String s = id_mapping.getProperty(key) + "/" + i;

       id_mapping.setProperty(key,s);
//       id_mapping.getP
     }
     else
       id_mapping.setProperty(key,i+"");
     }
   }
   String [] strList;
   for (int m=0;m<f.length;m++) {
     String fileName = f[m].getName();
     StringTokenizer p = new StringTokenizer(fileName,".");
     if (p.hasMoreTokens()) {
       fileName = p.nextToken();
       fileName += "_data.txt";
     }
     else {
       fileName += "_data.txt";
     }
     File file =  new File(f[m].getParent(),fileName);
     strList = FileIO.loadData(f[m],0,delimit);
     try {
       FileWriter wrt = new FileWriter(file);
       PrintWriter out = new PrintWriter(wrt);
       for (int i=0;i<startRow;i++) {
            out.print(row_toString(i));
        }
        TreeSet ts = new TreeSet();
       for (int i=0;i<strList.length;i++) {
         if (!ts.contains(strList[i])) {
           ts.add(strList[i]);
         String value = id_mapping.getProperty(strList[i]);
         if (value != null) {
           StringTokenizer parser = new StringTokenizer(value, "/");
           if (parser.countTokens() == 1) {
           int index = Integer.parseInt(value);
               out.print(row_toString(index));
           }
           else {
             int N = parser.countTokens();
             for (int k=0;k<N;k++) {
               String ss = parser.nextToken();
               int index = Integer.parseInt(ss);
                   out.print(row_toString(index));

             }
           }
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
   /*
   Object objValue;
   String strValue;
   TreeSet v;
   ColorStringClassIO csc;
     for (int m=0;m<f.length;m++) {
       strList = FileIO.loadData(f[m],0,delimit);
        v = new TreeSet();
        for (int k=0;k<strList.length;k++) {
          for (int i = 1; i<rowNum ; i++) {
            objValue = getValueAt(i,0);
            if (objValue != null) {
              strValue = ((ColorStringClassIO)objValue).toString();
              if (strValue.equals(strList[k])){
                v.add(""+i);
                i = rowNum;
              }
            }
          }
        }
        if (v.size()!=0) {
          String fileName = f[m].getName();
          StringTokenizer p = new StringTokenizer(fileName,".");
          if (p.hasMoreTokens()) {
            fileName = p.nextToken();
            fileName += "_geneInfo.txt";
          }
          else {
            fileName += "_geneInfo.txt";
          }
        File file =  new File(f[m].getParent(),fileName);
        Object [] o = v.toArray();
        int [] index = new int[o.length];
        for (int i=0;i<o.length;i++) {
          index[i] = Integer.parseInt((String)o[i]);
        }
        try {
          FileWriter wrt = new FileWriter(file);
          PrintWriter out = new PrintWriter(wrt);
          for (int i=0;i<index.length;i++) {  //row_toString
            out.print(row_toString(index[i]));
           }
          wrt.flush();
          wrt.close();
        }
        catch (IOException e)
        {
          msg.messageDisplay(e.getMessage()+"\n");
        }
        }
    }
    */
  }

  File [] firstDataFiles,secondDataFiles;
  String [][][] firstDataSets,secondDataSets;
  public void firstDataSetsForVennDiagram(File [] f, String delimit) {
    Display.display("load first data sets");
    for (int i=0;i<f.length;i++) {
      Display.display("file "+i+ " " + f[i].getName());
    }
    firstDataFiles = f;
    firstDataSets = new String[f.length][][];
    for (int i=0;i<f.length;i++) {
      firstDataSets[i] = FileIO.displayData(FileIO.readFile(firstDataFiles[i]),delimit);
      firstDataSets[i] = Sorting.sortRow(firstDataSets[i],0,dataStartRow);
    }
  }


  public void secondDataSetsForVennDiagram(File [] f, int start_row, int start_col, String delimit) {
    Display.display("load second data sets");
    for (int i=0;i<f.length;i++) {
      Display.display("file "+i+ " " + f[i].getName());
    }
    secondDataFiles = f;
    secondDataSets = new String[f.length][][];
    for (int i=0;i<f.length;i++) {
      secondDataSets[i] = FileIO.displayData(FileIO.readFile(secondDataFiles[i]),delimit);
      secondDataSets[i] = Sorting.sortRow(secondDataSets[i],0,dataStartRow);
    }
    if (secondDataSets == null ) {
      Display.display("secondDataSets is null");
      return;
    }
    if ( firstDataSets == null) {
      Display.display("firstDataSets is null");
      return;
    }
      DataConversion.vennDiagram(firstDataSets,DataConversion.getFileName(firstDataFiles),
                                 secondDataSets,DataConversion.getFileName(secondDataFiles),start_row,start_col,0);
  }
  public void sameGeneCount(String filename) {
  if (dataSets == null  ) {
    ColorStringClassIO csc;
     String [] geneID = new String[rowNum];
        for (int i=0;i<rowNum;i++) {
       csc = (ColorStringClassIO)getValueAt(i,0);  //3
       geneID[i] = csc.toString();
     }

    Sorting.quicksort(true,geneID);
    Vector newList = new Vector();
    Vector geneNumList = new Vector();
    Vector mergedList = new Vector();
    boolean sameGene = false;
    float [] y;
    for (int i=0;i<rowNum-1;i++) {
      if (!sameGene && !geneID[i].equals( geneID[i+1] )) {
        newList.add(geneID[i]);
        geneNumList.add(1+"");
        if (i == rowNum-1) {
          newList.add(geneID[i+1]);
          geneNumList.add(1+"");
        }
      }
      else if (!sameGene){
        mergedList.add(geneID[i]);  // condition not same gene yet, but same id
        sameGene = true;              // set to same gene
        if (i == rowNum-1) {

          mergedList.add(geneID[i+1]);
          newList.add(geneID[i+1]);
          geneNumList.add(mergedList.size()+"");
        }
      }
      else if (sameGene && !geneID[i].equals( geneID[i+1] )) {
        mergedList.add(geneID[i]);  //  condition same gene, but not same id
//        RowDataIO rd = getMergedRowData(mergedList);
//        newList.add(rd);
//        Display.display("size="+mergedList.size());
        newList.add(geneID[i]);
        geneNumList.add(mergedList.size()+"");

        mergedList = new Vector();
        sameGene = false;
        if (i == rowNum-1) {
//          newList.add(rowData[i+1]);
          newList.add(geneID[i+1]);
          geneNumList.add(1+"");
        }
      }
      else {  // condistion same gene and same id
        mergedList.add(geneID[i]);
        if (i == rowNum-1) {
          mergedList.add(geneID[i+1]);
//          RowDataIO rd = getMergedRowData(mergedList);
//          newList.add(rd);
          newList.add(geneID[i+1]);
          geneNumList.add(mergedList.size()+"");
        }
      }
    }
//    numberDataRow = newList.size();
    Object [] gName = newList.toArray();
    Object [] gNum = geneNumList.toArray();
//    rowData = new RowDataIO[numberDataRow];

     StringBuffer ss = new StringBuffer();
     ss.append("GeneID\tSameGeneNumber\n");
   for (int i=0;i<gName.length;i++) {
//     if(i==gName.length-1)
//    else
     ss.append((String)gName[i]+"\t"+(String)gNum[i]+"\n");
   }

  // }
   FileIO.saveResult(filename+ "_sameGemecount.txt",ss.toString());


    return;
  }
  dataSets.sameGeneCount(filename);

}

public void replaceZeroByItsAverage() {
  if (dataSets == null || replicateRow==-1) {
    msg.display("Bioreplicate row not assigned yet.");
    return;
  }
  else {
    float [][] decomposedData = new float[dataSets.getRepNameIndex().length][];
    for (int i=0;i<dataSets.getRepNameIndex().length;i++)
      decomposedData[i] = new float[dataSets.getRepNameIndex()[i].length];
  for (int i=dataStartRow;i<rowNum;i++) {
    updateCorrectedValue(i,dataStartCol,colNum,dataSets.replaceZeroByItsAverage(i-dataStartRow,decomposedData));
  }

  }

}
public void log2Conversion(int col) {
  if (dataSets==null) {
msg.display("dataset is null.");
return;
}
for (int i=dataStartRow;i<rowNum;i++) {
updateCorrectedValue(i,dataStartCol,colNum,dataSets.log2Conversion(i-dataStartRow,col-dataStartCol));
}
}
public void statisticalPower(int col) {
  if (dataSets==null) {
msg.display("dataset is null.");
return;
}
dataSets.statisticalPower(col);
}
public void FDR(int col) {
  if (dataSets==null) {
msg.display("dataset is null.");
return;
}
dataSets.FDR(col);
}

}