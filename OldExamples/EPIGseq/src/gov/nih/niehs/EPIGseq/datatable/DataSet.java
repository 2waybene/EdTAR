package gov.nih.niehs.EPIGseq.datatable;


import java.util.*;
import java.text.*;
import java.io.*;
//import util.*;
import gov.nih.niehs.EPIGseq.util.*;
import java.awt.*;
import myutility.statistics.*;
import myutility.numerical.*;
import myutility.misc.*;
import gov.nih.niehs.EPIGseq.myutility.io.*;
import myutility.arrayObject.*;
import gov.nih.niehs.EPIGseq.myutility.plot2D.*;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
public class DataSet {
  private float [] columnMean;
  private String [][] d; // numColumnInfo = columnInfo.length
  private String [] colname; // numColumnInfo = columnInfo.length
  public static int stringType = 0;
  public static int floatType = 1;
  public static int intType = 2;
  public static final int bioRepPlot = 1, cellLineProfilePlot = 0, samplePlot=2,
    scatterPlot_withErrorBar = 3, barPlot_withErrorBar=4, averagedDat_scatterPlot=5, averagedData_barPlot=6;
  private int numberDataRow, numberExp, numColumnGeneDescInfo, numRowExpDescInfo;
  private String [][] columnInfo,generalInfo; // numColumnInfo = columnInfo.length
  public int [][] repNameIndex, cellLineNameIndex;
  public int [][][] cellLineProfileIndex;
  public String [] trimRepName,trimDataPlotOptionName,cellLineName;
  private float [] expValue,expAvg,expStd;
  private MessageBoard msg;
  RowDataIO [] rowData,removedRowData; //numRowInfo = rowData.getRowinfo().length
  private int repRow=-1,dataPlotOption=2,expNameRow=-1,cellLineProfileRow=-1,cellLineNameRow=-1,stepRow=-1,factor3_row=-1;
  private boolean displayTable = false, xTicMark = true, yTicMark=true;
 // private String [] extraColumnName;
//  private DataPlot plot;
  private int [] numGeneInEachCat;
//  private int [] dataType,numGeneInEachCat;
  private int[] removedIndex;
  private boolean [] mergedColumnIndex;
  private String geneSetProperty;
  public DataPlot dataPlot;// =  new DataPlot();
  public DataSet(String [][] _data, String [] _columnInfo,MessageBoard _msg) {

    msg = _msg;
//    dataPlot = _dataPlot;
    loadData(_data,_columnInfo);
  }
  public DataSet(String [][] _generalInfo, String [][] _columnInfo,
                 String [][] _rowInfo, float [][] _data,MessageBoard _msg,
                 int _expRow, int _bioReplicateRow,int _dataPlotOption,
                 int _cellLineProfileRow,int _cellLineRow,int _stepRow, boolean creatRowData, DataPlot _dataPlot) {

    msg = _msg;
    dataPlot = _dataPlot;
    loadData(_generalInfo,_columnInfo,_rowInfo,_data,_expRow,_bioReplicateRow,
             _dataPlotOption,_cellLineProfileRow,_cellLineRow,_stepRow,creatRowData);
  }

  public DataSet(MessageBoard _msg,DataPlot _dataPlot) {
    msg = _msg;
     dataPlot = _dataPlot;
  }
//  public void data_transpose(DataSet _ds) {
//    columnInfo = DataConversion.transposeString(_ds.getColumnInfo());
//   generalInfo = DataConversion.transposeString(_ds.getGneralInfo());
//   _ds.getRowInfo()
//  }
  public DataPlot getDataPlot() {
    return dataPlot;
  }
  public void createCellLineProfileIndex(String [] cellLineNameRow, String [] cellLineProfile) {

    if (cellLineName == null || cellLineProfile == null ) return;
    if (cellLineName ==null) {
       cellLineName = FactorAnalysis.treeSetArray(cellLineNameRow);
       cellLineNameIndex = FactorAnalysis.getGroupIndex(cellLineNameRow,cellLineName);
    }
    cellLineProfileIndex =  FactorAnalysis.getGroupProfileIndex(cellLineNameIndex,cellLineProfile);
  }
  public void createCellLineNameIndex(String [] cellLineNameRow) {
    if (cellLineNameRow == null ){
      return;
    }
    cellLineName = FactorAnalysis.treeSetArray(cellLineNameRow);
    cellLineNameIndex = FactorAnalysis.getGroupIndex(cellLineNameRow,cellLineName);
  }

  public int [][][] getCellLineProfileIndex() {
    return cellLineProfileIndex;
  }
  public void setCellLineProfileIndex(int [][][] index) {
    cellLineProfileIndex=index;
  }

  public int [][] getRepProfileIndex(int i) {
    return cellLineProfileIndex[i];
  }
  public void loadData(String [][] _generalInfo, String [][] _columnInfo,
                       String [][] _rowInfo, float [][] _data,
                       int _expRow, int _bioReplicateRow,int _dataPlotOption,
                       int _cellLineProfileRow,int _cellLineNameRow , int _stepRow, boolean creatRowData) {

    expNameRow=_expRow;
    repRow=_bioReplicateRow;
    numberDataRow = _rowInfo.length;
    numberExp=_columnInfo[expNameRow].length;
    numColumnGeneDescInfo = _rowInfo[0].length;
    numRowExpDescInfo=_columnInfo.length;
    columnInfo=_columnInfo;
    generalInfo=_generalInfo;
    if (repRow != -1) {
      createReplicateNameIndex(columnInfo[repRow]);
    }
    stepRow=_stepRow;
    if (stepRow != -1) {
      setExpValue(columnInfo[stepRow]);
    }
    else {
      setExpValue();
    }
    dataPlotOption=_dataPlotOption;
    cellLineNameRow = _cellLineNameRow;
    if (cellLineNameRow != -1) {
      createCellLineNameIndex(columnInfo[cellLineNameRow]);
    }
    cellLineProfileRow = _cellLineProfileRow;
    if (cellLineProfileRow!=-1 && cellLineNameRow != -1) {
      createCellLineProfileIndex(columnInfo[cellLineNameRow],columnInfo[cellLineProfileRow]);
    }


   if (creatRowData) {
    rowData = new RowDataIO[numberDataRow];
    for (int i=0;i<numberDataRow;i++) {
      rowData[i] = new RowDataIO(_rowInfo[i], _data[i]);
      rowData[i].setIndex(i);
    }
   }
  }
  public void loadData(String [][]  _data, String [] colName) {
    numberDataRow = _data.length;
    numberExp=colName.length;
   d =_data;
   colname = colName;
  }
  public String [] getColName() {
    return colname;
  }
  public String getColName(int i) {
    return colname[i];
  }
  public String [][] getD() {
    return d;
  }
  public String [] getD(int i) {
    return d[i];
  }
  public String getD(int i, int j) {
    return d[i][j];
  }
  public void setD(int i, int j, String dd) {
    d[i][j]=dd;
  }
  public String [] getCellLineName() {
    return cellLineName;
  }
  public void setCellLineName(String [] str) {
    cellLineName=str;
  }
  public DataPlot getPlot() {
    if (dataPlot == null) {
      dataPlot = new DataPlot(msg);
}

    return dataPlot;
  }
  public  void setPlot(DataPlot p) {
    dataPlot = p;
  }

  public void loadData(DataSet ds, boolean creatRowData) {
    expNameRow =ds.getExpNameRow();
    numberDataRow = ds.getNumberDataRow();
    numberExp = ds.getNumberExp();
    numColumnGeneDescInfo = ds.getNumColumnGeneDescInfo();
    numRowExpDescInfo = ds.getNumRowExpDescInfo();
    columnInfo = ds.getColumnInfo();
    generalInfo = ds.getGneralInfo();
    repRow =  ds.getRepRow();
   if (repRow!=-1) {
    repNameIndex = ds.getRepNameIndex();
    trimRepName = ds.getTrimRepName();
   }
    stepRow =  ds.getStepRow();
    expValue = ds.getExpValue();
    if (expValue == null) {
      setExpValue();
    }
    trimDataPlotOptionName = ds.getTrimDataPlotOptionName();
//    dataPlotOptionInfo = ds.getIsDataPlotOptionInfo();
    dataPlotOption = ds.getDataPlotOption();
    cellLineNameRow = ds.getCellLineNameRow();
    if (cellLineNameRow!=-1) {
      cellLineName = ds.getCellLineName();
      cellLineNameIndex =  ds.getCellLineNameIndex();
    }
    cellLineProfileRow = ds.getCellLineProfileRow();
    if (cellLineProfileRow!=-1 && cellLineNameRow != -1) {
      cellLineProfileIndex =  ds.getCellLineProfileIndex();
    }
    expNameRow =  ds.getExpNameRow();
    dataPlot = ds.getPlot();
    msg = ds.getMSG();
    if (creatRowData)
       setRowData(ds.getRowData());

    if (displayTable)
      displayData();
  }

  public void createReplicateNameIndex(int row) {
    String [] repName = columnInfo[row];
   createReplicateNameIndex(repName);
  }
  public void createCellLineNameIndex(int row) {
    String [] cellLineName = columnInfo[row];
   createCellLineNameIndex(cellLineName);
  }
  public void createReplicateNameIndex(String [] repName) {
    if (repName == null ){
      trimRepName = null;
      repNameIndex = null;
      return;
    }
    trimRepName = FactorAnalysis.treeSetArray(repName);
    repNameIndex =FactorAnalysis.getGroupIndex(repName,trimRepName);

  }

  public int [] getGeneIndex(String [] str) {
    int [] index = new int[str.length];
    int m=0;
    for (int i=0;i<str.length;i++) {
      index[i] = getGeneIndex(str[i]);
      if (index[i] != -1) m++;
    }
    if (m == index.length)
    return index;
    else {
      int [] inx = new int[m];
      m=0;
      for (int i=0;i<index.length;i++) {
        if (index[i] != -1) {
          inx[m] = index[i];
          m++;
        }
      }
      return inx;
    }
  }
    public int  getGeneIndex(String str) {
      int numRow = getNumberDataRow();
      String s;
    for (int i=0;i<numRow;i++) {
      s = rowData[i].getRowInfoString()[0];
      if (s.equals(str)) return i;
      }
     return -1;
  }
  public void profileReplicateAverageAndStandardDeviation() {
   int numRow = getNumberDataRow();
   float [] data;
   float [][] replicateResult;
   float [] intraCorrelationResult;
   boolean b;
   int num=0;
   if (repNameIndex != null) {

     float [][] decomposedData = new float[repNameIndex.length][];
     for (int i=0;i<repNameIndex.length;i++)
       decomposedData[i] = new float[repNameIndex[i].length];

   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    replicateResult = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(data,repNameIndex,decomposedData);
    rowData[i].setRepAverageAndStd(replicateResult);
   }
   }
   if (cellLineProfileIndex != null)
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    intraCorrelationResult = Correlation.profile_intraCorrelation(data,cellLineProfileIndex);
    rowData[i].setIntraCorrelation(intraCorrelationResult);
   }
    saveData_repAvgAndStd(FileIO.saveSingleFile(),rowData);
  }


  public void profileReplicateAverage() {
   int numRow = getNumberDataRow();
   float [] data;
   float [][] replicateResult;
   float [] intraCorrelationResult;
   boolean b;
   int num=0;
   if (repNameIndex != null) {

     float [][] decomposedData = new float[repNameIndex.length][];
     for (int i=0;i<repNameIndex.length;i++)
       decomposedData[i] = new float[repNameIndex[i].length];

   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    replicateResult = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(data,repNameIndex,decomposedData);
    rowData[i].setRepAverageAndStd(replicateResult);
   }
   }
   if (cellLineProfileIndex != null)
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    intraCorrelationResult = Correlation.profile_intraCorrelation(data,cellLineProfileIndex);
    rowData[i].setIntraCorrelation(intraCorrelationResult);
   }
    saveData_repAvg(FileIO.saveSingleFile(),rowData);
  }
  public String techRepName(int [][] techRepIndex, String [] name) {
    String ss="";
    for (int i=0;i<generalInfo[0].length;i++)
      ss += generalInfo[0][i] + "\t";
    for (int i=0;i<techRepIndex.length;i++) {
      if (i ==  techRepIndex.length-1)
      ss += name[techRepIndex[i][0]]+"\n";
      else
        ss += name[techRepIndex[i][0]]+"\t";
    }
    return ss;
  }
  public void exportDyeSwapMergedFile(int [][] techRepIndex, String [] techName, String [] bioName) {
    File f = FileIO.saveSingleFile();
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(techRepName(techRepIndex,techName));
      out.print(techRepName(techRepIndex,bioName));
      for (int i=0;i<getRowData().length;i++) {
        out.print(getRowData()[i].dyeSwapMergedData(techRepIndex));
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }
  }

  public float [] subtractReplicateAverage(int i, float [][] decomposedData) {
   return rowData[i].subtractReplicateAverage(repNameIndex,decomposedData);
}
public float [] get_equal_between_group_average(int i, float [][] decomposedData) {
 return rowData[i].get_equal_between_group_average(repNameIndex,decomposedData);
}
public float [] subtractFirstReplicateAverage(int i, float [][] decomposedData) {
 return rowData[i].subtractFirstReplicateAverage(repNameIndex,decomposedData);
}



private String pair_wise_trimRepName(String str) {
  String s="";
  for (int i=0;i<trimRepName.length-1;i++) {
    for (int j=i+1;j<trimRepName.length;j++) {
      if (i == trimRepName.length-2 && j == trimRepName.length-1)
      s += str+"_"+trimRepName[i] + "_vs_" + trimRepName[j] + "\tp_value_"+trimRepName[i] + "_vs_" + trimRepName[j]
         + "\tdif_"+trimRepName[i] + "_vs_" + trimRepName[j];
      else
        s += str+"_"+trimRepName[i] + "_vs_" + trimRepName[j] + "\tp_value_"+trimRepName[i] + "_vs_" + trimRepName[j]
        + "\tdif_"+trimRepName[i] + "_vs_" + trimRepName[j]+"\t";
    }
  }
  return s;
}
private String pair_wise_trimCelllineName(String str) {
  String s="";
  for (int i=0;i<cellLineName.length-1;i++) {
    for (int j=i+1;j<cellLineName.length;j++) {
      if (i == cellLineName.length-2 && j == cellLineName.length-1)
      s += str+"_"+cellLineName[i] + "_vs_" + cellLineName[j] + "\tp_value_"+cellLineName[i] + "_vs_" + cellLineName[j]
         + "\tdif_"+cellLineName[i] + "_vs_" + cellLineName[j];
      else
        s += str+"_"+cellLineName[i] + "_vs_" + cellLineName[j] + "\tp_value_"+cellLineName[i] + "_vs_" + cellLineName[j]
        + "\tdif_"+cellLineName[i] + "_vs_" + cellLineName[j]+"\t";
    }
  }
  return s;
}
private String [][] pair_wise_trimRepArrayName(String str) {
  Vector v1 = new Vector();
  Vector v2 = new Vector();
  for (int i=0;i<trimRepName.length-1;i++) {
    for (int j=i+1;j<trimRepName.length;j++) {
      v1.add(str+"_"+trimRepName[i] + "_vs_" + trimRepName[j]);
      v2.add("p_value_"+trimRepName[i] + "_vs_" + trimRepName[j]);
//        s += str+"_"+trimRepName[i] + "_vs_" + trimRepName[j] + "\tp_value_"+trimRepName[i] + "_vs_" + trimRepName[j]+"\t";
    }
  }
//  trimRepName.length
  Object [] o1 = v1.toArray();
  Object [] o2 = v2.toArray();
  String [][] s = new String[2][o1.length];
  for (int i=0;i<o1.length;i++){
    s[0][i] = (String)o1[i];
    s[1][i] = (String)o2[i];
  }
  return s;
}
public void dyeSwapAnalysis(int [][]techRepIndex) {
  int numRow = getNumberDataRow();
    if ( techRepIndex==null) return;
    File f = new File(FileIO.path,"dyeSwapAnalysis.txt");
    try {
    FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
    PrintWriter out = new PrintWriter(wrt);
    float [] result;
    float [] data;
    Random random = new Random();
    for (int i=0;i<techRepIndex.length-1;i++)
      if (i == techRepIndex.length-2)
       out.print((i+2)+ " pairs avgD\tstdD\n");
      else
       out.print((i+2)+" stdD\t");

      float [][] decomposedData = new float[techRepIndex.length][];
      for (int i=0;i<techRepIndex.length;i++)
        decomposedData[i] = new float[techRepIndex[i].length];

    for (int i=0;i<numRow;i++) {
      data=rowData[i].getData();
      result = Statistics.dyeSwapCorrectionAnalysis(data,techRepIndex,random,decomposedData);
      out.print(DataConversion.getStringRow(result));
    }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }
}
  public void rowData_subprofiles_correlation(String filename) {
   int numRow = getNumberDataRow();
   float [] data;
   float [][][] result = new float[numRow][][];
   String str;
   if (repNameIndex != null) {
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
      ss.append(generalInfo[0][k]+"\t");
     str = pair_wise_trimCelllineName("correlation");
     msg.display(str+"\n");
     ss.append(str+"\n");
     String s1;

     float [][] decomposedData = new float[cellLineNameIndex.length][];
    for (int i=0;i<cellLineNameIndex.length;i++)
      decomposedData[i] = new float[cellLineNameIndex[i].length];


   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    result[i] = Correlation.rowData_subprofiles_correlation(data,cellLineNameIndex,decomposedData);
    for (int k=0;k<result[i].length;k++) {
      s1 = result[i][k][1]+"";
      if (s1.equals("NaN")) {
        result[i][k][0]=0;
        result[i][k][1]=1;
        result[i][k][2]=1;
      }
    }
    rowData[i].set_t_test(result[i]);
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    msg.display1(result[i]);
    for (int k=0;k<result[i].length;k++) {
     for (int m=0;m<result[i][k].length;m++)
     if (k==result[i].length-1 && m == result[i][k].length-1)
       ss.append(result[i][k][m]+"\n");
       else
     ss.append(result[i][k][m]+"\t");
   }

  }
  FileIO.saveResult(filename+ "_intraCorrelation.txt",ss.toString());
//   display_FDR(result,filename,"ttest");
   }
  }
  public void rowData_t_test(String filename) {
   int numRow = getNumberDataRow();
   float [] data;
   float [][][] result = new float[numRow][][];
   String str;
   if (repNameIndex != null) {
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
      ss.append(generalInfo[0][k]+"\t");
     str = pair_wise_trimRepName("ttest");
     msg.display(str+"\n");
     ss.append(str+"\n");
     String s1;
   float [][] decomposedData = new float[repNameIndex.length][];
   for (int i=0;i<repNameIndex.length;i++)
     decomposedData[i] = new float[repNameIndex[i].length];
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    result[i] = t_test.rowData_t_test(data,repNameIndex,decomposedData);
    for (int k=0;k<result[i].length;k++) {
      s1 = result[i][k][1]+"";
      if (s1.equals("NaN")) {
        result[i][k][0]=0;
        result[i][k][1]=1;
        result[i][k][2]=0;
      }
    }
    rowData[i].set_t_test(result[i]);
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
//    msg.display1(result[i]);
    for (int k=0;k<result[i].length;k++) {
     for (int m=0;m<result[i][k].length;m++)
     if (k==result[i].length-1 && m == result[i][k].length-1)
       ss.append(result[i][k][m]+"\n");
       else
     ss.append(result[i][k][m]+"\t");
   }

  }
  FileIO.saveResult(filename+ "_ttest.txt",ss.toString());
   display_FDR(result,filename,"ttest");
   }
  }

  public void sameGeneCount(String filename) {
    String [] geneID = new String[rowData.length];
    for (int i=0;i<rowData.length;i++) {
      geneID[i] = rowData[i].getRowInfo(0).toString();
    }
    Sorting.quicksort(true,geneID,rowData);
    int numGene = numberDataRow;
    Vector newList = new Vector();
    Vector geneNumList = new Vector();
    Vector mergedList = new Vector();
    boolean sameGene = false;
    float [] y;
    for (int i=0;i<numGene-1;i++) {
      if (!sameGene && !rowData[i].getRowInfo(0).toString().equals( rowData[i+1].getRowInfo(0).toString() )) {
        newList.add(rowData[i].getRowInfo(0).toString());
        geneNumList.add(1+"");
        if (i == numGene-1) {
          newList.add(rowData[i+1].getRowInfo(0).toString());
          geneNumList.add(1+"");
        }
      }
      else if (!sameGene){
        mergedList.add(rowData[i]);  // condition not same gene yet, but same id
        sameGene = true;              // set to same gene
        if (i == numGene-1) {

          mergedList.add(rowData[i+1]);
          newList.add(rowData[i+1].getRowInfo(0).toString());
          geneNumList.add(mergedList.size()+"");
        }
      }
      else if (sameGene && !rowData[i].getRowInfo(0).toString().equals( rowData[i+1].getRowInfo(0).toString() )) {
        mergedList.add(rowData[i]);  //  condition same gene, but not same id
//        RowDataIO rd = getMergedRowData(mergedList);
//        newList.add(rd);
//        Display.display("size="+mergedList.size());
        newList.add(rowData[i].getRowInfo(0).toString());
        geneNumList.add(mergedList.size()+"");

        mergedList = new Vector();
        sameGene = false;
        if (i == numGene-1) {
//          newList.add(rowData[i+1]);
          newList.add(rowData[i+1].getRowInfo(0).toString());
          geneNumList.add(1+"");
        }
      }
      else {  // condistion same gene and same id
        mergedList.add(rowData[i]);
        if (i == numGene-1) {
          mergedList.add(rowData[i+1]);
//          RowDataIO rd = getMergedRowData(mergedList);
//          newList.add(rd);
          newList.add(rowData[i+1].getRowInfo(0).toString());
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


  }

  public void rowData_paired_sample_t_test(String filename) {
   int numRow = getNumberDataRow();
   float [] data;
   String str;
//   float [] pvalue;// = new float[numRow];
   float [][][] result = new float[numRow][][];
   if (repNameIndex != null) {
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
     ss.append(generalInfo[0][k]+"\t");
     str = pair_wise_trimRepName("paired_t_test");
//     msg.display(str);
     ss.append(str+"\n");
     String s1;

     float [][] decomposedData = new float[repNameIndex.length][];
     for (int i=0;i<repNameIndex.length;i++)
       decomposedData[i] = new float[repNameIndex[i].length];


   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    result[i] = t_test.rowData_paired_t_test(data,repNameIndex,decomposedData);

    for (int k=0;k<result[i].length;k++) {
      s1 = result[i][k][1]+"";
      if (s1.equals("NaN")) {
        result[i][k][0]=0;
        result[i][k][1]=1;
        result[i][k][2]=1;
      }
    }
    rowData[i].set_paired_t_test(result[i]);
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    msg.display1(result[i]);
    for (int k=0;k<result[i].length;k++) {
     for (int m=0;m<result[i][k].length;m++)
     if (k==result[i].length-1 && m == result[i][k].length-1)
       ss.append(result[i][k][m]+"\n");
       else
     ss.append(result[i][k][m]+"\t");
   }

   }
   FileIO.saveResult(filename+ "_paired_ttest.txt",ss.toString());

   display_FDR(result,filename,"paired_ttest");
   }
  }

  private void display_FDR(float [][][] result,String filename,String testname) {
    int numRow = result.length;
    String [][] s = pair_wise_trimRepArrayName(testname);
    float [][] pvalue = new float[numRow][result[0].length];
 for (int i=0;i<numRow;i++) {
   for (int j=0;j<result[0].length;j++)
     pvalue[i][j] = result[i][j][1];
 }
  float [][] trasposed_pValue = DataConversion.transposeData(pvalue);
  for (int i=0;i<trasposed_pValue.length;i++ ){
    calculateFDR(filename + "_" + s[0][i]+"_FDR.txt",trasposed_pValue[i]);
  }
  }


  public void purity_linearCombination(String [][] str) {
    String [][] s = new String[str.length][str[0].length-1];
    for (int i=0;i<s.length;i++)
      for (int j=0;j<s[i].length;j++)
        s[i][j] = str[i][j+1];

    float [][] reconstitueCoff = DataConversion.convertStringToFloat(s);
    for (int i=0;i<reconstitueCoff.length;i++)
       reconstitueCoff[i] = DataConversion.array_normalization(reconstitueCoff[i]);
    if (reconstitueCoff==null)return;
    int numberCoffColDataItem=reconstitueCoff[0].length;
    int totalCoffRowNumber = reconstitueCoff.length;
    for (int i=1;i<totalCoffRowNumber;i++)
      if (reconstitueCoff[i].length != numberCoffColDataItem) {
    msg.display("Input data rows have different number of columns.");
    return;
      }


    int numberCellLine = getCellLineNameIndex().length;
    int numberArray_in_eachCellline = getCellLineNameIndex()[0].length;

    int numRow = getNumberDataRow();
    StringBuffer ss = new StringBuffer();
    for (int k=0;k<generalInfo[0].length;k++)
       ss.append(generalInfo[0][k]+"\t");
    for (int i=0;i<cellLineName.length;i++) {
      for (int j=0;j<str.length;j++) {
        if (i==cellLineName.length-1 && j==str.length-1)
          ss.append(cellLineName[i]+"_"+str[j][0]+"\n");
        else
          ss.append(cellLineName[i]+"_"+str[j][0]+"\t");
      }
    }
    float [] dd;

    float [][] decomposedData = new float[cellLineNameIndex.length][];
   for (int i=0;i<cellLineNameIndex.length;i++)
     decomposedData[i] = new float[cellLineNameIndex[i].length];

    for (int i=0;i<numRow;i++) {
     dd=  rowData[i].purity_linearCombination(cellLineNameIndex,reconstitueCoff,decomposedData);
     for (int k=0;k<generalInfo[0].length;k++) {
       ss.append(rowData[i].getRowInfoString()[k]+"\t");
     }
     for (int j=0;j<dd.length;j++) {
       if (j == dd.length-1)
         ss.append(dd[j]+"\n");
       else
         ss.append(dd[j]+"\t");
     }
    }
    FileIO.saveResult(FileIO.saveSingleFile(),ss.toString());
  }

  public void rowData_threeWayANOVA(String filename) {
    int numRow = getNumberDataRow();
    float [] data;
    if (cellLineProfileRow!=-1 && cellLineNameRow != -1 && factor3_row!=-1) {

      String [] factor_1_name = FactorAnalysis.oneFactorName(columnInfo[cellLineNameRow]);
      String factor_1 = generalInfo[cellLineNameRow][0];
      String [] factor_2_name = FactorAnalysis.oneFactorName(columnInfo[cellLineProfileRow]);
      String factor_2 = generalInfo[cellLineProfileRow][0];
      String [] factor_3_name = FactorAnalysis.oneFactorName(columnInfo[factor3_row]);
      String factor_3 = generalInfo[factor3_row][0];
      int [][] factor_1_index = FactorAnalysis.oneFactorNameIndex(columnInfo[cellLineNameRow],factor_1_name);
      int [][] factor_2_index = FactorAnalysis.oneFactorNameIndex(columnInfo[cellLineProfileRow],factor_2_name);
      int [][] factor_3_index = FactorAnalysis.oneFactorNameIndex(columnInfo[factor3_row],factor_3_name);

      String [][] factor_12_name= FactorAnalysis.twoFactorName(factor_1_name,factor_2_name);
      int [][][] factor_12_index= FactorAnalysis.twoFactorNameIndex(columnInfo[cellLineNameRow], factor_1_name,
                                 columnInfo[cellLineProfileRow],factor_2_name);

      String [][] factor_13_name= FactorAnalysis.twoFactorName(factor_1_name,factor_3_name);
      int [][][] factor_13_index= FactorAnalysis.twoFactorNameIndex(columnInfo[cellLineNameRow], factor_1_name,
                                 columnInfo[factor3_row],factor_3_name);

      String [][] factor_23_name= FactorAnalysis.twoFactorName(factor_2_name,factor_3_name);
      int [][][] factor_23_index= FactorAnalysis.twoFactorNameIndex(columnInfo[cellLineProfileRow], factor_2_name,
                                 columnInfo[factor3_row],factor_3_name);


      String [][][] factor_123_name= FactorAnalysis.threeFactorName(factor_1_name,factor_2_name,factor_3_name);
      int [][][][] factor_123_index= FactorAnalysis.threeFactorNameIndex(columnInfo[cellLineNameRow], factor_1_name,
                                 columnInfo[cellLineProfileRow], factor_2_name,
                                 columnInfo[factor3_row],factor_3_name);

//      DataConversion.threeFactor(columnInfo[cellLineNameRow], factor_1_name,factor_1_index,
//                                 columnInfo[cellLineProfileRow],factor_2_name,factor_2_index,
//                                 columnInfo[factor3_row],factor_3_name,factor_3_index,
//                                 factor_12_name,factor_12_index,
//                                 factor_13_name,factor_13_index,
//                                 factor_23_name,factor_23_index,
//                                 factor_123_name,factor_123_index);

      float [][] decomposedData1 = new float[factor_1_index.length][];
      for (int i=0;i<decomposedData1.length;i++) {
        if (factor_1_index[i]!=null) {
          decomposedData1[i] = new float[factor_1_index[i].length];
          }
      }
      float [][] decomposedData2 = new float[factor_2_index.length][];
      for (int i=0;i<decomposedData2.length;i++) {
        if (factor_2_index[i]!=null) {
          decomposedData2[i] = new float[factor_2_index[i].length];
          }
      }
      float [][] decomposedData3 = new float[factor_3_index.length][];
      for (int i=0;i<decomposedData3.length;i++) {
        if (factor_3_index[i]!=null) {
          decomposedData3[i] = new float[factor_3_index[i].length];
          }
      }


      float [][][] decomposedData12 = new float[factor_12_index.length][][];
      for (int i=0;i<decomposedData12.length;i++) {
        if (factor_12_index[i]!=null) {
          decomposedData12[i] = new float[factor_12_index[i].length][];
          for (int j=0;j<factor_12_index[i].length;j++) {
            if (factor_12_index[i][j]!=null) {
               decomposedData12[i][j] = new float[factor_12_index[i][j].length];
              }
          }
          }
      }

      float [][][] decomposedData13 = new float[factor_13_index.length][][];
      for (int i=0;i<decomposedData13.length;i++) {
        if (factor_13_index[i]!=null) {
          decomposedData13[i] = new float[factor_13_index[i].length][];
          for (int j=0;j<factor_13_index[i].length;j++) {
            if (factor_13_index[i][j]!=null) {
               decomposedData13[i][j] = new float[factor_13_index[i][j].length];
              }
          }
          }
      }
      float [][][] decomposedData23 = new float[factor_23_index.length][][];
      for (int i=0;i<decomposedData23.length;i++) {
        if (factor_23_index[i]!=null) {
          decomposedData23[i] = new float[factor_23_index[i].length][];
          for (int j=0;j<factor_23_index[i].length;j++) {
            if (factor_23_index[i][j]!=null) {
               decomposedData23[i][j] = new float[factor_23_index[i][j].length];
              }
          }
          }
      }

      float [][][][] decomposedData123 = new float[factor_123_index.length][][][];
      for (int i=0;i<factor_123_index.length;i++) {
        if (factor_123_index[i]!=null) {
        decomposedData123[i] = new float[factor_123_index[i].length][][];
        for (int j=0;j<factor_123_index[i].length;j++) {
          if (factor_123_index[i][j]!=null) {
          decomposedData123[i][j] = new float[factor_123_index[i][j].length][];
          for (int k=0;k<factor_123_index[i][j].length;k++) {
            if (factor_123_index[i][j][k]!=null) {
              decomposedData123[i][j][k] = new float[factor_123_index[i][j][k].length];
          }
          }
          }
        }
        }
      }
      float [] mean1 = new float[factor_1_name.length];
      float [] mean2 = new float[factor_2_name.length];
      float [] mean3 = new float[factor_3_name.length];
      float [][] mean12 = new float[factor_1_name.length][factor_2_name.length];
      float [][] mean13 = new float[factor_1_name.length][factor_3_name.length];
      float [][] mean23 = new float[factor_2_name.length][factor_3_name.length];
      float [][][] mean123 = new float[factor_1_name.length][factor_2_name.length][factor_3_name.length];
      float [] threewayanovaresult = new float[ANOVA.get_Three_Way_ANOVA_Name().length];

      StringBuffer ss = new StringBuffer();
      ss.append(factor_1+"_1 vs "+factor_2+"_2 vs " + factor_3+"_3"+"\t");
//      ss.append(generalInfo[0][0]+"\t");
      for (int k=0;k<ANOVA.get_Three_Way_ANOVA_Name().length;k++) {
        if (k == ANOVA.get_Three_Way_ANOVA_Name().length-1) {
          ss.append(ANOVA.get_Three_Way_ANOVA_Name()[k] + "\n");
        }
        else {
          ss.append(ANOVA.get_Three_Way_ANOVA_Name()[k] + "\t");
        }
      }

       for (int i=0;i<numRow;i++) {
        data=rowData[i].getData();

        ANOVA.rowData_threeWayANOVA(data,
                                    factor_1_index,mean1,decomposedData1,
                                    factor_2_index,mean2,decomposedData2,
                                    factor_3_index,mean3,decomposedData3,
                                    factor_12_index,mean12,decomposedData12,
                                    factor_13_index,mean13,decomposedData13,
                                    factor_23_index,mean23,decomposedData23,
                                    factor_123_index,mean123,decomposedData123,
                                    threewayanovaresult);
        rowData[i].set_ANOVA(threewayanovaresult);
          ss.append(rowData[i].getRowInfoString()[0]+"\t");
        for (int k=0;k<threewayanovaresult.length;k++) {
          if (k==threewayanovaresult.length-1)
            ss.append(threewayanovaresult[k]+"\n");
            else
          ss.append(threewayanovaresult[k]+"\t");
        }
       }
       FileIO.saveResult(filename+ "_Three_Way_ANOVA.txt",ss.toString());
  }
  }

  public void rowData_twoWayANOVA(String filename) {
    int numRow = getNumberDataRow();
    float [] data;
    if (cellLineProfileRow!=-1 && cellLineNameRow != -1 ) {


      String [] factor_1_name = FactorAnalysis.oneFactorName(columnInfo[cellLineNameRow]);
      String factor_1 = generalInfo[cellLineNameRow][0];
      String [] factor_2_name = FactorAnalysis.oneFactorName(columnInfo[cellLineProfileRow]);
      String factor_2 = generalInfo[cellLineProfileRow][0];
      int [][] factor_1_index = FactorAnalysis.oneFactorNameIndex(columnInfo[cellLineNameRow],factor_1_name);
      int [][] factor_2_index = FactorAnalysis.oneFactorNameIndex(columnInfo[cellLineProfileRow],factor_2_name);
      String [][] factor_12_name= FactorAnalysis.twoFactorName(factor_1_name,factor_2_name);
      int [][][] factor_12_index= FactorAnalysis.twoFactorNameIndex(columnInfo[cellLineNameRow], factor_1_name,
                                 columnInfo[cellLineProfileRow],factor_2_name);

      float [][] decomposedData1 = new float[factor_1_index.length][];
      for (int i=0;i<decomposedData1.length;i++) {
        if (factor_1_index[i]!=null) {
          decomposedData1[i] = new float[factor_1_index[i].length];
          }
      }
      float [][] decomposedData2 = new float[factor_2_index.length][];
      for (int i=0;i<decomposedData2.length;i++) {
        if (factor_2_index[i]!=null) {
          decomposedData2[i] = new float[factor_2_index[i].length];
          }
      }


      float [][][] decomposedData12 = new float[factor_1_index.length][factor_2_index.length][];
      for (int i=0;i<factor_1_index.length;i++) {
          for (int j=0;j<factor_2_index.length;j++) {
            if (factor_12_index[i][j]!=null) {
                 decomposedData12[i][j] = new float[factor_12_index[i][j].length];
              }
          }
      }
      float [] mean1 = new float[factor_1_name.length];
      float [] mean2 = new float[factor_2_name.length];
      float [][] mean12 = new float[factor_1_name.length][factor_2_name.length];
      float [] twowayanovaresult = new float[ANOVA.get_Two_Way_ANOVA_Name().length];

      StringBuffer ss = new StringBuffer();
      ss.append(factor_1+"_1 vs "+factor_2+"_2" +"\t");
      for (int k=0;k<ANOVA.get_Two_Way_ANOVA_Name().length;k++) {
        if (k == ANOVA.get_Two_Way_ANOVA_Name().length-1) {
          ss.append(ANOVA.get_Two_Way_ANOVA_Name()[k] + "\n");
        }
        else {
          ss.append(ANOVA.get_Two_Way_ANOVA_Name()[k] + "\t");
        }
      }

       for (int i=0;i<numRow;i++) {
        data=rowData[i].getData();

//        ANOVA.rowData_twoWayANOVA_good(data,factor_12_index,decomposedData12,
//                                     mean1,mean2,mean12,twowayanovaresult);


        ANOVA.rowData_twoWayANOVA(data,
                                    factor_1_index,mean1,decomposedData1,
                                    factor_2_index,mean2,decomposedData2,
                                    factor_12_index,mean12,decomposedData12,
                                    twowayanovaresult);
        rowData[i].set_ANOVA(twowayanovaresult);
          ss.append(rowData[i].getRowInfoString()[0]+"\t");
        for (int k=0;k<twowayanovaresult.length;k++) {
          if (k==twowayanovaresult.length-1)
            ss.append(twowayanovaresult[k]+"\n");
            else
          ss.append(twowayanovaresult[k]+"\t");
        }
       }
       FileIO.saveResult(filename+ "_Two_Way_ANOVA.txt",ss.toString());
  }
  }

/*


  public void rowData_twoWayANOVA(String filename) {
    int numRow = getNumberDataRow();
    float [] data;
    if (cellLineProfileRow!=-1 && cellLineNameRow != -1) {
      int [][][] factor_index = DataConversion.twoReplicateGroupIndex(columnInfo[cellLineNameRow],columnInfo[cellLineProfileRow]);
      float [][][] d = new float[factor_index.length][][];
      for (int i=0;i<factor_index.length;i++) {
         d[i] = new float[factor_index[i].length][];
         for (int j=0;j<factor_index[i].length;j++) {
           d[i][j] = new float[factor_index[i][j].length];
         }
      }
      StringBuffer ss = new StringBuffer();
      for (int k=0;k<generalInfo[0].length;k++)
      ss.append(generalInfo[0][k]+"\t");
      for (int k=0;k<ANOVA.get_Two_Way_ANOVA_Name().length;k++) {
        if (k == ANOVA.get_Two_Way_ANOVA_Name().length-1) {
          ss.append(ANOVA.get_Two_Way_ANOVA_Name()[k] + "\n");
        }
        else {
          ss.append(ANOVA.get_Two_Way_ANOVA_Name()[k] + "\t");
        }
      }
       float [] mean1 = new float[factor_index.length];
       float [] mean2 = new float[factor_index[0].length];
       float [][] mean12 = new float[factor_index.length][factor_index[0].length];
       float [] twowayanovaresult = new float[16];
       for (int i=0;i<numRow;i++) {
        data=rowData[i].getData();
        ANOVA.rowData_twoWayANOVA(data,factor_index,d,mean1,mean2,mean12,twowayanovaresult);
        rowData[i].set_ANOVA(twowayanovaresult);
        for (int k=0;k<generalInfo[0].length;k++) {
          ss.append(rowData[i].getRowInfoString()[k]+"\t");
        }
        for (int k=0;k<twowayanovaresult.length;k++) {
          if (k==twowayanovaresult.length-1)
            ss.append(twowayanovaresult[k]+"\n");
            else
          ss.append(twowayanovaresult[k]+"\t");
        }

//        msg.display(result);
       }
       FileIO.saveResult(filename+ "_Two_Way_ANOVA.txt",ss.toString());
//       calculateFDR(filename+ "_ANOVA_FDR.txt",pvalue);

  }




  }
*/
//  public void plotXY_profile(float [] xVal, float [] yVal, String xLabel, String yLabel,
  //                  String title, Color color, String [] label,String curveName, boolean lineState,boolean markerState) {

  public void rowData_logisticConversion_linearFitting(int selectedRow, int subsetNum) {
    float [] xData = DataConversion.convertStringToFloat(columnInfo[0]);
    float [] yData=rowData[selectedRow- columnInfo.length].getData();
    float [] xData_valid = xData;
    float [] yData_valid = yData;
    float max = Statistics.maxVal(yData);
    float min = Statistics.minVal(yData);
    int start_index = 0, end_index = yData.length-1;
    boolean wholeRangeData = true;
    if (min != yData[0]) {
      wholeRangeData = false;
    for (int i=0;i<yData.length;i++) {
      if (min == yData[i]) {
        start_index = i;
        i = yData.length;
        }
    }
    }
    if (max != yData[end_index]) {
      wholeRangeData = false;
      for (int i=0;i<yData.length;i++) {
        if (max == yData[i]) {
          end_index = i;
          i = yData.length;
          }
      }
    }
    if (end_index - start_index +1 < subsetNum) {
      System.out.println("bad data");
      return;
    }
    int validDataNum = end_index-start_index+1;
    if (!wholeRangeData) {
      xData_valid = new float[validDataNum];
      yData_valid = new float[validDataNum];
      for (int i=start_index;i<validDataNum;i++) {
        xData_valid[i-start_index] = xData[i];
        yData_valid[i-start_index] = yData[i];
      }
    }
    System.out.println("validDataNum="+validDataNum);
    float [] convertedData=new float[validDataNum];
    DataConversion.logisticConversion(yData_valid, convertedData);

    float [][] yVal = new float[2][];
    yVal[0] = yData_valid;
    yVal[1] = convertedData;

    String [] name = new String[2];
    name[0] = "data";
    name[1] = "converted";
    DataModeling dm = new DataModeling();
    float [] x_subset = new float[subsetNum];
    float [] y_subset = new float[subsetNum];
    float [] chi_sq = new float[validDataNum-subsetNum];
    int j=0;
    float [] fittingResult;
    float alpha, alpha_fromFitting,beta, kapa, gamma=0, start_time, end_time,range,max_growth_rate,chi_square;
    // gamma = time value at maximum growth rate
    alpha = Statistics.minVal(yData_valid);
    beta = Statistics.maxVal(yData_valid);
    float tim;
    msg.display("j\tchi2\ta\tb\tymin\talpha_fitting\tmax_beta\tkapa\tgamma\trange\tmax_growth_rate\tstart_time\tend_time\n");
    while(j <validDataNum-subsetNum) {
    for (int i=j;i<j+subsetNum;i++) {
      x_subset[i-j] = xData_valid[i];
      y_subset[i-j] = convertedData[i];
      }
      fittingResult = dm.fit_original(x_subset, y_subset, subsetNum,null, false);
      kapa = fittingResult[1];
      gamma = -fittingResult[0]/fittingResult[1];
      if (gamma > 0) {
      chi_sq[j] = fittingResult[4];
//      tim = 60*kapa*kapa+12*kapa+1;
//      tim = (float)Math.sqrt(tim);
//      start_time = -(1/kapa)*(float)Math.log((1+6*kapa-tim)/12*kapa)+gamma;
//      end_time = -(1/kapa)*(float)Math.log((1+6*kapa+tim)/12*kapa)+gamma;
      range = beta - alpha;
      max_growth_rate = kapa*range*0.25f;
      tim = (float)Math.exp(fittingResult[0]);
      alpha_fromFitting = (alpha*(1+tim)-beta)/tim;
      msg.display(j +"\t" +chi_sq[j]+ "\t"+fittingResult[0]
                  + "\t"+fittingResult[1]+"\t"+alpha+"\t"+alpha_fromFitting+"\t"+beta
                  + "\t"+kapa+"\t"+gamma+"\t"+range+"\t"+max_growth_rate+"\n");
      j++;
      }
      else {
        for (int k=j;k<validDataNum-subsetNum;k++) {
          chi_sq[k] = Float.MAX_VALUE;
        }
        j= validDataNum-subsetNum;
      }
    }
    float minChiSquare = Statistics.minVal(chi_sq);
    msg.display("minChiSquare="+minChiSquare+"\n");
    int minChi_index = 0;
    for (int k=0;k<chi_sq.length;k++) {
      if (chi_sq[k] == minChiSquare) {
        minChi_index = k;
        k = chi_sq.length;

        for (int i=minChi_index;i<minChi_index+subsetNum;i++) {
          x_subset[i-minChi_index] = xData_valid[i];
          y_subset[i-minChi_index] = convertedData[i];
          }

          fittingResult = dm.fit_original(x_subset, y_subset, subsetNum,null, false);
          kapa = fittingResult[1];
          gamma = -fittingResult[0]/fittingResult[1];
          chi_square = fittingResult[4];
//          tim = 60*kapa*kapa+12*kapa+1;
//          tim = (float)Math.sqrt(tim);
//          start_time = -(1/kapa)*(float)Math.log((1+6*kapa-tim)/12*kapa)+gamma;
//          end_time = -(1/kapa)*(float)Math.log((1+6*kapa+tim)/12*kapa)+gamma;
          range = beta - alpha;
          max_growth_rate = kapa*range*0.25f;
          tim = (float)Math.exp(fittingResult[0]);
          alpha_fromFitting = (alpha*(1+tim)-beta)/tim;
          msg.display(minChi_index +"\t" +chi_square+ "\t"+fittingResult[0]
                      + "\t"+fittingResult[1]+"\t"+alpha+"\t"+alpha_fromFitting+"\t"+beta
                      + "\t"+kapa+"\t"+gamma+"\t"+range+"\t"+max_growth_rate+"\n");
      }
    }
    float [] derivative=NumericalAnalysis.get_local_derivative_with_expandedData(xData_valid, yData_valid,5);
//    float [] derivative=DataConversion.get_local_derivative_with_expandedData(xData_valid, yData_valid,5, gamma);
    msg.display("maximun growth rate time point\t"+derivative[0] +"\tmaximun growth rate\t"+derivative[1]+"\n");


    dataPlot.plotXY_profile_multi(xData_valid, yVal, "Hour", "",
       "", false, null,name, true,true);

  }
  public void rowData_logisticFitting(int selectedRow) {
    int numRow = getNumberDataRow();
float [] xData = DataConversion.convertStringToFloat(columnInfo[0]);
float [] yData=rowData[selectedRow- columnInfo.length].getData();
float [] yRegression=new float[yData.length];
float [] fittingResult = new float[10];
DataModeling dm = new DataModeling();

dm.logisticFitting( xData, yData, yRegression,  fittingResult);

msg.display("fittingResult",fittingResult);
 String [] profileName = new String[2];
 profileName[0] = "Data";
 profileName[1] = "regression";
// String title ="1st_d_time="+ fisrt_derivative_max_x;
// title += " max_growth_rate=" +fisrt_derivative_max;
// title += " start=" +second_derivative_max_x;
// title += " end=" +second_derivative_min_x;
 String title="";
 float [][] yVal = new float[2][];
 yVal[0] = yData;
 yVal[1] = yRegression;
dataPlot.plotXY_profile_multi(xData, yVal, "Hour", "",
   title, false, null,profileName, true,true);


  }
//  public float [] get_
  public void rowData_deglitch(int selectedRow) {
    float [] xData = DataConversion.convertStringToFloat(columnInfo[0]);
    float [] yData=rowData[selectedRow- columnInfo.length].getData();
    float [] yData_deglitch = singlepoint_deglitch_using_local_diff(yData);
    float [][] yVal = new float[2][];
    yVal[0] = yData;
    yVal[1] = yData_deglitch;
    String []profileName = new String[2];
    profileName[0] = "raw";
    profileName[1] = "deglitch";
    dataPlot.plotXY_profile_multi(xData, yVal, "Hour", "",
       "deglitch test", false, null,profileName, true,true);

  }

  private float [] singlepoint_deglitch_using_local_diff(float [] data) {
  if (data.length>3) {
    float [] deglitchData = new float[data.length];
    for (int i=0;i<deglitchData.length;i++)
      deglitchData[i] = data[i];
  float test1,test2,test3,test4;
  test1 = Math.abs( data[1] - data[0]);
  test2 = Math.abs(data[2] - data[1]);
  if (test1>3*test2 || test1 < 3*test2) {
    deglitchData[0] = data[1] - test2;
    System.out.print("remove glitch 0\n");
  }

  test1 = Math.abs( data[data.length-1] - data[data.length-2]);
  test2 = Math.abs(data[data.length-2] - data[data.length-3]);
  if (test1>3*test2 || test1 < 3*test2) {
    deglitchData[data.length-1] = data[data.length-2] + test2;
    System.out.print("remove glitch last\n");
  }

  test1 = data[1] - data[0];
  test2 = data[2] - data[1];
  test3 = data[3] - data[2];
  if (test1*test2 < 0 && test2*test3 > 0 && test1*test3 < 0 && (Math.abs(test1)+Math.abs(test3)) > 6*Math.abs(test2)) { //1
    deglitchData[1] = (data[2] + data[0])*0.5f;
    System.out.print("remove glitch 1\n");
  }
  else if (test1*test2 < 0 && test2*test3 < 0 && test1*test3 > 0 && (Math.abs(test1)+Math.abs(test3)) > 6*Math.abs(test2)) { //2
    deglitchData[1] = (data[2] + data[0])*0.5f;
    System.out.print("remove glitch 1\n");
  }



  for (int i=4;i<data.length;i++) {
    test1 = data[i-3] - data[i-4];
    test2 = data[i-2] - data[i-3];
    test3 = data[i-1] - data[i-2];
    test4 = data[i] - data[i-1];

    if (test1*test2 < 0 && test1*test3 > 0 && test1*test4 > 0 && test2*test3 < 0 && test2*test4 < 0 && test3*test4 > 0
       && (Math.abs(test2)+Math.abs(test3)) > 3*(Math.abs(test2)+Math.abs(test4)) ) { //1
      deglitchData[i-2] = (data[i-1] + data[i-3])*0.5f;
      System.out.print("remove glitch "+(i-2)+ "\n");
    }
    else if (test1*test2 > 0 && test1*test3 < 0 && test1*test4 > 0 && test2*test3 < 0 && test2*test4 > 0 && test3*test4 < 0
            && (Math.abs(test2)+Math.abs(test3)) > 3*(Math.abs(test2)+Math.abs(test4)) ) { //1
      deglitchData[i-2] = (data[i-1] + data[i-3])*0.5f;
      System.out.print("remove glitch "+(i-2)+ "\n");
    }
  }
  test1 = data[1] - data[0];
  test2 = data[2] - data[1];
  test3 = data[3] - data[2];

  test1 = data[data.length-2] - data[data.length-1];
  test2 = data[data.length-3] - data[data.length-2];
  test3 = data[data.length-4] - data[data.length-3];
  if (test1*test2 < 0 && test2*test3 > 0 && test1*test3 < 0 && (Math.abs(test1)+Math.abs(test3)) > 6*Math.abs(test2)) { //1
    deglitchData[data.length-2] = (data[data.length-1] + data[data.length-3])*0.5f;
    System.out.print("remove glitch next to last\n");
  }
  else if (test1*test2 < 0 && test2*test3 < 0 && test1*test3 > 0 && (Math.abs(test1)+Math.abs(test3)) > 6*Math.abs(test2)) { //2
    deglitchData[data.length-2] = (data[data.length-1] + data[data.length-3])*0.5f;
    System.out.print("remove glitch next to last\n");
  }

  return deglitchData;
  }
  else return null;
}


  public void rowData_derivative(int selectedRow) {
    int numRow = getNumberDataRow();
float [] xData = DataConversion.convertStringToFloat(columnInfo[0]);
float [] xData_ex = new float[5*xData.length-4];
float [] yData_ex = new float[5*xData.length-4];
for (int i=0;i<xData.length;i++) {
  if (i == xData.length-1) {
    xData_ex[i*5] = xData[i];
  }
  else {
  xData_ex[i*5] = xData[i];
  xData_ex[i*5+1] = xData[i]+0.3f;
  xData_ex[i*5+2] = xData[i]+0.6f;
  xData_ex[i*5+3] = xData[i]+0.9f;
  xData_ex[i*5+4] = xData[i]+1.2f;
  }
}
float [] y_1st_DataOut= new float[xData.length];
float [] y_2nd_DataOut= new float[xData.length];
float [] y_1st_DataOut_ex= new float[xData_ex.length];
float [] y_2nd_DataOut_ex= new float[xData_ex.length];
float [] yData;
//  ss1.append("fisrt_derivative_max_x\tfisrt_derivative_max_y\tfisrt_derivative_max\t");
//  ss2.append("second_derivative_max_x\tsecond_derivative_max_y\tsecond_derivative_min_x\tsecond_derivative_min_y\t");
//  ss2.append("width_x\theight_y\tslope\t");

float fisrt_derivative_max_x=0,fisrt_derivative_max_y=0,fisrt_derivative_max=0;
float second_derivative_max_x=0,second_derivative_max_y;
float second_derivative_min_x=0,second_derivative_min_y;
float [] splineY= new float[xData.length];
float [] splineY_ex= new float[xData_ex.length];
float [] u = new float[xData.length];
float [] u_ex = new float[xData_ex.length];
float width_x,width_y,slope;
int index_1stDev,index_2ndDev_max,index_2ndDev_min;

 yData=rowData[selectedRow- columnInfo.length].getData();
 NumericalAnalysis.get_Spline_first_and_second_derivative(xData,yData,u,splineY,msg,y_1st_DataOut,y_2nd_DataOut);
 for (int j=0;j<xData_ex.length;j++)
   yData_ex[j] = NumericalAnalysis.splint(xData,yData,y_2nd_DataOut,xData_ex[j]);
 //work on extended data now
 NumericalAnalysis.get_Spline_first_and_second_derivative(xData_ex,yData_ex,u_ex,splineY_ex,msg,y_1st_DataOut_ex,y_2nd_DataOut_ex);
//  y_1st_DataOut = y_1st_DataOut_ex;
//   fisrt_derivative_max =  Statistics.maxVal(y_1st_DataOut_ex);
 fisrt_derivative_max = -Float.MAX_VALUE;
 index_1stDev = -1;
 for (int k=10;k<y_1st_DataOut_ex.length-10;k++) {
   if (y_1st_DataOut_ex[k] > fisrt_derivative_max) {
     fisrt_derivative_max = y_1st_DataOut_ex[k];
     index_1stDev = k;
   }
 }
 if (index_1stDev==10 || index_1stDev==y_1st_DataOut_ex.length-11) {
   fisrt_derivative_max = -1;
   fisrt_derivative_max_x = -1;
   fisrt_derivative_max_y = -1;
 }
 else
 for (int k=0;k<y_1st_DataOut_ex.length;k++)
   if (fisrt_derivative_max == y_1st_DataOut_ex[k]) {
     fisrt_derivative_max_x = xData_ex[k];
     fisrt_derivative_max_y = yData_ex[k];
     k=y_1st_DataOut_ex.length;
   }
//    y_2nd_DataOut = y_2nd_DataOut_ex;
   index_2ndDev_max=-1;
//      int index_min = -1;
   second_derivative_max_y = -Float.MAX_VALUE;
   second_derivative_min_y = Float.MAX_VALUE;

   for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
     if (y_2nd_DataOut_ex[k] > second_derivative_max_y) {
       second_derivative_max_y = y_2nd_DataOut_ex[k];
       index_2ndDev_max = k;
     }
   }
   index_2ndDev_min = -1;
   for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
     if (y_2nd_DataOut_ex[k] < second_derivative_min_y) {
       second_derivative_min_y = y_2nd_DataOut_ex[k];
       index_2ndDev_min = k;
     }
   }

   if (index_2ndDev_max==10 || index_2ndDev_max==y_1st_DataOut_ex.length-11 || index_2ndDev_min==10 || index_2ndDev_min==y_1st_DataOut_ex.length-11) {
     second_derivative_max_x = -1;
     second_derivative_max_y = -1;
     second_derivative_min_x =-1;
     second_derivative_min_y =-1;
   }
 else{
 for (int k=0;k<y_2nd_DataOut_ex.length;k++)
   if (second_derivative_max_y == y_2nd_DataOut_ex[k]) {
     second_derivative_max_x = xData_ex[k];
     second_derivative_max_y = yData_ex[k];
     k=y_2nd_DataOut.length;
   }

   for (int k=0;k<y_2nd_DataOut_ex.length;k++)
     if (second_derivative_min_y == y_2nd_DataOut_ex[k]) {
       second_derivative_min_x = xData_ex[k];
       second_derivative_min_y = yData_ex[k];
       k=y_2nd_DataOut_ex.length;
     }
}
     width_x = second_derivative_min_x-second_derivative_max_x;
     width_y = second_derivative_min_y-second_derivative_max_y;

 if (width_x !=0) slope = width_y/width_x;
 else slope = -1;

 float [][] yVal = new float[3][];
 yVal[0] = yData_ex;
 yVal[1] = y_1st_DataOut_ex;
 yVal[2] = y_2nd_DataOut_ex;
 for (int i=0;i<yData_ex.length;i++) {
   yVal[1][i] *=10;
   yVal[2][i] *=30;
 }
 String [] profileName = new String[3];
 profileName[0] = "Data";
 profileName[1] = "1st derivative Data";
 profileName[2] = "2nd derivative Data";
 String title ="1st_d_time="+ fisrt_derivative_max_x;
 title += " max_growth_rate=" +fisrt_derivative_max;
 title += " start=" +second_derivative_max_x;
 title += " end=" +second_derivative_min_x;
dataPlot.plotXY_profile_multi(xData_ex, yVal, "Hour", "",
   title, false, null,profileName, true,true);


  }
  public void rowData_derivative(String filename) {
  //  System.out.print("derivative");
   int numRow = getNumberDataRow();
   float [] xData = DataConversion.convertStringToFloat(columnInfo[0]);
   float [] xData_ex = new float[5*xData.length-4];
   float [] yData_ex = new float[5*xData.length-4];
   for (int i=0;i<xData.length;i++) {
     if (i == xData.length-1) {
       xData_ex[i*5] = xData[i];
     }
     else {
     xData_ex[i*5] = xData[i];
     xData_ex[i*5+1] = xData[i]+0.3f;
     xData_ex[i*5+2] = xData[i]+0.6f;
     xData_ex[i*5+3] = xData[i]+0.9f;
     xData_ex[i*5+4] = xData[i]+1.2f;
     }
   }
   float [] x_1st_DataOut = new float[xData.length];
   float [] y_1st_DataOut= new float[xData.length];
   float [] x_2nd_DataOut= new float[xData.length];
   float [] y_2nd_DataOut= new float[xData.length];

   float [] x_1st_DataOut_ex= new float[xData_ex.length];
   float [] y_1st_DataOut_ex= new float[xData_ex.length];
   float [] x_2nd_DataOut_ex= new float[xData_ex.length];
   float [] y_2nd_DataOut_ex= new float[xData_ex.length];


   float [] yData;
   StringBuffer ss0 = new StringBuffer();
   StringBuffer ss1 = new StringBuffer();
   StringBuffer ss2 = new StringBuffer();

     for (int k=0;k<generalInfo[0].length;k++) {
       ss0.append(generalInfo[0][k]+"\t");
       ss1.append(generalInfo[0][k]+"\t");
       ss2.append(generalInfo[0][k]+"\t");
     }
     ss1.append("fisrt_derivative_max_x\tfisrt_derivative_max_y\tfisrt_derivative_max\t");
     ss2.append("second_derivative_max_x\tsecond_derivative_max_y\tsecond_derivative_min_x\tsecond_derivative_min_y\t");
     ss2.append("width_x\theight_y\tslope\t");
     for (int k=0;k<xData_ex.length;k++) {
       if (k == xData_ex.length-1) {
         ss0.append(xData_ex[k] + "\n");
         ss1.append(xData_ex[k] + "\n");
         ss2.append(xData_ex[k] + "\n");
       }
       else {
         ss0.append(xData_ex[k] + "\t");
         ss1.append(xData_ex[k] + "\t");
         ss2.append(xData_ex[k] + "\t");
       }
     }

   float fisrt_derivative_max_x=0,fisrt_derivative_max_y=0,fisrt_derivative_max=0;
   float second_derivative_max_x=0,second_derivative_max_y;
   float second_derivative_min_x=0,second_derivative_min_y;
   float [] splineY= new float[xData.length];
   float [] splineY_ex= new float[xData_ex.length];
   float [] u = new float[xData.length];
   float [] u_ex = new float[xData_ex.length];
   float width_x,width_y,slope;
   int index_1stDev,index_2ndDev_max,index_2ndDev_min;

   for (int i=0;i<numRow;i++) {
    yData=rowData[i].getData();
    NumericalAnalysis.get_Spline_first_and_second_derivative(xData,yData,u,splineY,msg,y_1st_DataOut,y_2nd_DataOut);
    for (int j=0;j<xData_ex.length;j++)
      yData_ex[j] = NumericalAnalysis.splint(xData,yData,y_2nd_DataOut,xData_ex[j]);
    //work on extended data now
    NumericalAnalysis.get_Spline_first_and_second_derivative(xData_ex,yData_ex,u_ex,splineY_ex,msg,y_1st_DataOut_ex,y_2nd_DataOut_ex);
 //  y_1st_DataOut = y_1st_DataOut_ex;
 //   fisrt_derivative_max =  Statistics.maxVal(y_1st_DataOut_ex);
    fisrt_derivative_max = -Float.MAX_VALUE;
    index_1stDev = -1;
    for (int k=10;k<y_1st_DataOut_ex.length-10;k++) {
      if (y_1st_DataOut_ex[k] > fisrt_derivative_max) {
        fisrt_derivative_max = y_1st_DataOut_ex[k];
        index_1stDev = k;
      }
    }
    if (index_1stDev==10 || index_1stDev==y_1st_DataOut_ex.length-11) {
      fisrt_derivative_max = -1;
      fisrt_derivative_max_x = -1;
      fisrt_derivative_max_y = -1;
    }
    else
    for (int k=0;k<y_1st_DataOut_ex.length;k++)
      if (fisrt_derivative_max == y_1st_DataOut_ex[k]) {
        fisrt_derivative_max_x = xData_ex[k];
        fisrt_derivative_max_y = yData_ex[k];
        k=y_1st_DataOut_ex.length;
      }
//    y_2nd_DataOut = y_2nd_DataOut_ex;
      index_2ndDev_max=-1;
//      int index_min = -1;
      second_derivative_max_y = -Float.MAX_VALUE;
      second_derivative_min_y = Float.MAX_VALUE;

      for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
        if (y_2nd_DataOut_ex[k] > second_derivative_max_y) {
          second_derivative_max_y = y_2nd_DataOut_ex[k];
          index_2ndDev_max = k;
        }
      }
      index_2ndDev_min = -1;
      for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
        if (y_2nd_DataOut_ex[k] < second_derivative_min_y) {
          second_derivative_min_y = y_2nd_DataOut_ex[k];
          index_2ndDev_min = k;
        }
      }

//      second_derivative_max_y =  Statistics.maxVal(y_2nd_DataOut_ex);
//      second_derivative_min_y =  Statistics.minVal(y_2nd_DataOut_ex);

      if (index_2ndDev_max==10 || index_2ndDev_max==y_1st_DataOut_ex.length-11 || index_2ndDev_min==10 || index_2ndDev_min==y_1st_DataOut_ex.length-11) {
        second_derivative_max_x = -1;
        second_derivative_max_y = -1;
        second_derivative_min_x =-1;
        second_derivative_min_y =-1;
      }
    else{
    for (int k=0;k<y_2nd_DataOut_ex.length;k++)
      if (second_derivative_max_y == y_2nd_DataOut_ex[k]) {
        second_derivative_max_x = xData_ex[k];
        second_derivative_max_y = yData_ex[k];
        k=y_2nd_DataOut.length;
      }

      for (int k=0;k<y_2nd_DataOut_ex.length;k++)
        if (second_derivative_min_y == y_2nd_DataOut_ex[k]) {
          second_derivative_min_x = xData_ex[k];
          second_derivative_min_y = yData_ex[k];
          k=y_2nd_DataOut_ex.length;
        }
   }
        width_x = second_derivative_min_x-second_derivative_max_x;
        width_y = second_derivative_min_y-second_derivative_max_y;

    for (int k=0;k<generalInfo[0].length;k++) {
      ss0.append(rowData[i].getRowInfoString()[k]+"\t");
      ss1.append(rowData[i].getRowInfoString()[k]+"\t");
      ss2.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    ss1.append(fisrt_derivative_max_x+"\t"+fisrt_derivative_max_y+"\t"+fisrt_derivative_max+"\t");
    ss2.append(second_derivative_max_x+"\t"+second_derivative_max_y+"\t"+second_derivative_min_x+"\t"+second_derivative_min_y+"\t");
    if (width_x !=0) slope = width_y/width_x;
    else slope = -1;
    ss2.append(width_x+"\t"+width_y+"\t"+slope+"\t");

 //   ss2.append(width_x+"\t"+width_y+"\t"+width_y/width_x+"\t");
    for (int k=0;k<y_1st_DataOut_ex.length;k++) {
      if (k==y_1st_DataOut_ex.length-1) {
        ss0.append(yData_ex[k]+"\n");
        ss1.append(y_1st_DataOut_ex[k]+"\n");
        ss2.append(y_2nd_DataOut_ex[k]+"\n");
      }
        else {
      ss0.append(yData_ex[k]+"\t");
      ss1.append(y_1st_DataOut_ex[k]+"\t");
      ss2.append(y_2nd_DataOut_ex[k]+"\t");
        }
    }

   }
   FileIO.saveResult(filename+ "_extend.txt",ss0.toString());
   FileIO.saveResult(filename+ "_1st_derivative.txt",ss1.toString());
   FileIO.saveResult(filename+ "_2nd_derivative.txt",ss2.toString());
  }
//  rowData_derivative(gInfo,colInfo,rowInfo,strData,fileNameWithoutExtension);

  public void rowData_derivative(String [][] gInfo, String [][] colInfo, String [][] rowInfo,String [][] strData,String filename) {
//  System.out.print("derivative");
 int numRow = getNumberDataRow();
 float [] xData = DataConversion.convertStringToFloat(colInfo[0]);
 float [] xData_ex = new float[5*xData.length-4];
 float [] yData_ex = new float[5*xData.length-4];
 for (int i=0;i<xData.length;i++) {
   if (i == xData.length-1) {
     xData_ex[i*5] = xData[i];
   }
   else {
   xData_ex[i*5] = xData[i];
   xData_ex[i*5+1] = xData[i]+0.3f;
   xData_ex[i*5+2] = xData[i]+0.6f;
   xData_ex[i*5+3] = xData[i]+0.9f;
   xData_ex[i*5+4] = xData[i]+1.2f;
   }
 }

 float [] x_1st_DataOut =  new float[xData.length];
 float [] y_1st_DataOut =  new float[xData.length];
 float [] x_2nd_DataOut =  new float[xData.length];
 float [] y_2nd_DataOut =  new float[xData.length];

 float [] x_1st_DataOut_ex= new float[xData_ex.length];
float [] y_1st_DataOut_ex= new float[xData_ex.length];
float [] x_2nd_DataOut_ex= new float[xData_ex.length];
float [] y_2nd_DataOut_ex= new float[xData_ex.length];


 float [] yData;
 StringBuffer ss0 = new StringBuffer();
 StringBuffer ss1 = new StringBuffer();
 StringBuffer ss2 = new StringBuffer();

 for (int k=0;k<generalInfo[0].length;k++) {
   ss0.append(generalInfo[0][k]+"\t");
   ss1.append(generalInfo[0][k]+"\t");
   ss2.append(generalInfo[0][k]+"\t");
 }
 ss1.append("fisrt_derivative_max_x\tfisrt_derivative_max_y\tfisrt_derivative_max\t");
 ss2.append("second_derivative_max_x\tsecond_derivative_max_y\tsecond_derivative_min_x\tsecond_derivative_min_y\t");
 ss2.append("width_x\theight_y\tslope\t");
 for (int k=0;k<xData_ex.length;k++) {
   if (k == xData_ex.length-1) {
     ss0.append(xData_ex[k] + "\n");
     ss1.append(xData_ex[k] + "\n");
     ss2.append(xData_ex[k] + "\n");
   }
   else {
     ss0.append(xData_ex[k] + "\t");
     ss1.append(xData_ex[k] + "\t");
     ss2.append(xData_ex[k] + "\t");
   }
 }

 float fisrt_derivative_max_x=0,fisrt_derivative_max_y=0,fisrt_derivative_max=0;
 float second_derivative_max_x=0,second_derivative_max_y;
 float second_derivative_min_x=0,second_derivative_min_y;

 float [] splineY= new float[xData.length];
 float [] splineY_ex= new float[xData_ex.length];
 float [] u = new float[xData.length];
 float [] u_ex = new float[xData_ex.length];
 float width_x,width_y,slope;
 int index_1stDev,index_2ndDev_max,index_2ndDev_min;


 for (int i=0;i<strData.length;i++) {
  yData=DataConversion.convertStringToFloat(strData[i]);
  NumericalAnalysis.get_Spline_first_and_second_derivative(xData,yData,u,splineY,msg,y_1st_DataOut,y_2nd_DataOut);
  for (int j=0;j<xData_ex.length;j++)
    yData_ex[j] = NumericalAnalysis.splint(xData,yData,y_2nd_DataOut,xData_ex[j]);

  NumericalAnalysis.get_Spline_first_and_second_derivative(xData_ex,yData_ex,u_ex,splineY_ex,msg,y_1st_DataOut_ex,y_2nd_DataOut_ex);
// y_1st_DataOut = y_1st_DataOut_ex;
//  fisrt_derivative_max =  Statistics.maxVal(y_1st_DataOut);

  fisrt_derivative_max = -Float.MAX_VALUE;
   index_1stDev = -1;
   for (int k=10;k<y_1st_DataOut_ex.length-10;k++) {
     if (y_1st_DataOut_ex[k] > fisrt_derivative_max) {
       fisrt_derivative_max = y_1st_DataOut_ex[k];
       index_1stDev = k;
     }
   }
   if (index_1stDev==10 || index_1stDev==y_1st_DataOut_ex.length-11) {
     fisrt_derivative_max = -1;
     fisrt_derivative_max_x = -1;
     fisrt_derivative_max_y = -1;
   }
   else
   for (int k=0;k<y_1st_DataOut_ex.length;k++)
     if (fisrt_derivative_max == y_1st_DataOut_ex[k]) {
       fisrt_derivative_max_x = xData_ex[k];
       fisrt_derivative_max_y = yData_ex[k];
       k=y_1st_DataOut_ex.length;
     }
//    y_2nd_DataOut = y_2nd_DataOut_ex;
     index_2ndDev_max=-1;
//      int index_min = -1;
     second_derivative_max_y = -Float.MAX_VALUE;
     second_derivative_min_y = Float.MAX_VALUE;

     for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
       if (y_2nd_DataOut_ex[k] > second_derivative_max_y) {
         second_derivative_max_y = y_2nd_DataOut_ex[k];
         index_2ndDev_max = k;
       }
     }
     index_2ndDev_min = -1;
     for (int k=10;k<y_2nd_DataOut_ex.length-10;k++) {
       if (y_2nd_DataOut_ex[k] < second_derivative_min_y) {
         second_derivative_min_y = y_2nd_DataOut_ex[k];
         index_2ndDev_min = k;
       }
     }

//      second_derivative_max_y =  Statistics.maxVal(y_2nd_DataOut_ex);
//      second_derivative_min_y =  Statistics.minVal(y_2nd_DataOut_ex);

     if (index_2ndDev_max==10 || index_2ndDev_max==y_2nd_DataOut_ex.length-11 || index_2ndDev_min==10 || index_2ndDev_min==y_2nd_DataOut_ex.length-11) {
       second_derivative_max_x = -1;
       second_derivative_max_y = -1;
       second_derivative_min_x =-1;
       second_derivative_min_y =-1;
     }
   else{
   for (int k=0;k<y_2nd_DataOut_ex.length;k++)
     if (second_derivative_max_y == y_2nd_DataOut_ex[k]) {
       second_derivative_max_x = xData_ex[k];
       second_derivative_max_y = yData_ex[k];
       k=y_2nd_DataOut_ex.length;
     }

     for (int k=0;k<y_2nd_DataOut_ex.length;k++)
       if (second_derivative_min_y == y_2nd_DataOut_ex[k]) {
         second_derivative_min_x = xData_ex[k];
         second_derivative_min_y = yData_ex[k];
         k=y_2nd_DataOut_ex.length;
       }
  }
//

   width_x = second_derivative_min_x-second_derivative_max_x;
   width_y = second_derivative_min_y-second_derivative_max_y;

  for (int k=0;k<generalInfo[0].length;k++) {
    ss0.append(rowInfo[i][k]+"\t");
    ss1.append(rowInfo[i][k]+"\t");
    ss2.append(rowInfo[i][k]+"\t");
  }
  ss1.append(fisrt_derivative_max_x+"\t"+fisrt_derivative_max_y+"\t"+fisrt_derivative_max+"\t");
  ss2.append(second_derivative_max_x+"\t"+second_derivative_max_y+"\t"+second_derivative_min_x+"\t"+second_derivative_min_y+"\t");
  if (width_x !=0) slope = width_y/width_x;
  else slope = -1;
  ss2.append(width_x+"\t"+width_y+"\t"+slope+"\t");
  for (int k=0;k<y_1st_DataOut_ex.length;k++) {
    if (k==y_1st_DataOut_ex.length-1) {
      ss0.append(yData_ex[k]+"\n");
      ss1.append(y_1st_DataOut_ex[k]+"\n");
      ss2.append(y_2nd_DataOut_ex[k]+"\n");
    }
      else {
    ss0.append(yData_ex[k]+"\t");
    ss1.append(y_1st_DataOut_ex[k]+"\t");
    ss2.append(y_2nd_DataOut_ex[k]+"\t");
      }
  }


 }
 FileIO.saveResult(filename+ "_extend.txt",ss0.toString());
 FileIO.saveResult(filename+ "_1st_derivative.txt",ss1.toString());
 FileIO.saveResult(filename+ "_2nd_derivative.txt",ss2.toString());
}

  public void rowData_derivative(File [] f, int startRow, int startCol) {
    for (int i=0;i<f.length;i++) {
      rowData_derivative( f[i],startRow,startCol);
    }
  }
  public void rowData_derivative(File f, int startRow, int startCol) {
    String [][] str = FileIO.read_txt(f,"\t");
    String [][] gInfo =  new String[startRow][startCol];
    for (int i=0;i<startRow;i++)
      for (int j=0;j<startCol;j++) {
        gInfo[i][j] = str[i][j];
//        System.out.println("gInfo "+i+", "+j+"="+gInfo[i][j]);
      }

    String [][] strData = new String[str.length-startRow][str[0].length-startCol];
    for (int i=0;i<str.length-startRow;i++)
      for (int j=0;j<str[0].length-startCol;j++) {
        strData[i][j] = str[startRow+i][startCol+j];
//        System.out.println("strData "+i+", "+j+"="+strData[i][j]);
      }
    String [][] colInfo = new String[startRow][str[0].length-startCol];
    for (int i=0;i<startRow;i++)
      for (int j=0;j<str[0].length-startCol;j++) {
        colInfo[i][j] = str[i][startCol+j];
 //       System.out.println("colInfo "+i+", "+j+"="+colInfo[i][j]);
      }

    String [][] rowInfo =  new String[str.length-startRow][startCol];
    for (int i=0;i<str.length-startRow;i++)
      for (int j=0;j<startCol;j++) {
        rowInfo[i][j] = str[startRow+i][j];
  //      System.out.println("rowInfo "+i+", "+j+"="+rowInfo[i][j]);
     }
 //       String [][] strData =  new String[][];
    String fileNameWithoutExtension = f.getName();
    StringTokenizer parser = new StringTokenizer(fileNameWithoutExtension, ".",true);
    fileNameWithoutExtension = parser.nextToken();
//    float [] xData_ex = new float[5*colInfo[0].length-4];
//    float [] yData_ex = new float[5*colInfo[0].length-4];

    rowData_derivative(gInfo,colInfo,rowInfo,strData,fileNameWithoutExtension);
  }
/*
  public void rowData_variance_norm_test(String filename) {
   int numRow = getNumberDataRow();
   if (repNameIndex != null) {
     String [] factor_1_name = FactorAnalysis.oneFactorName(columnInfo[repRow]);
     String factor_1 = generalInfo[repRow][0];
     int [][] factor_1_index = FactorAnalysis.oneFactorNameIndex(columnInfo[repRow],factor_1_name);
     float [][] decomposedData1 = new float[factor_1_index.length][];
     for (int i=0;i<decomposedData1.length;i++) {
       if (factor_1_index[i]!=null) {
         decomposedData1[i] = new float[factor_1_index[i].length];
         }
     }
     float [] mean = new float[factor_1_name.length];
     float [] std = new float[factor_1_name.length];
     float [] skewness = new float[factor_1_name.length];
     float [] kurtosis = new float[factor_1_name.length];
 //    float [] onewayanovaresult = new float[ANOVA.getANOVA_Name().length];


     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
       ss.append(factor_1+"\t");
     for (int i=0;i<factor_1_name.length;i++) {
           if (i == factor_1_name.length-1) {
         ss.append(factor_1_name[i]+"_mean\t" + factor_1_name[i]+"_std\t" +factor_1_name[i]+"_skewness\t" +factor_1_name[i]+"_kurtosis\n");
       }
       else {
         ss.append(factor_1_name[i]+"_mean\t" + factor_1_name[i]+"_std\t" +factor_1_name[i]+"_skewness\t" +factor_1_name[i]+"_kurtosis\t");
       }

     }

   float [] data;
   float [] pvalue = new float[numRow];
   String str;
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    ANOVA.rowData_variance_norm_test(data,factor_1_index,mean,std,skewness,kurtosis,decomposedData1);
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    for (int k=0;k<mean.length;k++) {
      if (k==mean.length-1) {
        ss.append(mean[k]+"\t" + std[k]+"\t" +skewness[k]+"\t"+kurtosis[k]+"\n");
      }
        else {
      ss.append(mean[k]+"\t" + std[k]+"\t" +skewness[k]+"\t"+kurtosis[k]+"\t");
        }
    }
   }
   FileIO.saveResult(filename+ "_var_norm_test.txt",ss.toString());
   }
  }
  */
  public void rowData_ANOVA(String filename) {
   int numRow = getNumberDataRow();
   if (repNameIndex != null) {
     String [] factor_1_name = FactorAnalysis.oneFactorName(columnInfo[repRow]);
     String factor_1 = generalInfo[repRow][0];
     int [][] factor_1_index = FactorAnalysis.oneFactorNameIndex(columnInfo[repRow],factor_1_name);
     float [][] decomposedData1 = new float[factor_1_index.length][];
     for (int i=0;i<decomposedData1.length;i++) {
       if (factor_1_index[i]!=null) {
         decomposedData1[i] = new float[factor_1_index[i].length];
         }
     }
     float [] mean1 = new float[factor_1_name.length];
     float [] onewayanovaresult = new float[ANOVA.getANOVA_Name().length];


     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
       ss.append(factor_1+"\t");
     for (int k=0;k<ANOVA.getANOVA_Name().length;k++) {
       if (k == ANOVA.getANOVA_Name().length-1) {
         ss.append(ANOVA.getANOVA_Name()[k] + "\n");
       }
       else {
         ss.append(ANOVA.getANOVA_Name()[k] + "\t");
       }
     }

   float [] data;
   float [] pvalue = new float[numRow];
   String str;
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    ANOVA.rowData_oneWayANOVA(data,factor_1_index,mean1,decomposedData1,onewayanovaresult);
    str = onewayanovaresult[2]+"";
    if (str.equals("NaN")) {
      pvalue[i] = 1;
    }
      else
    pvalue[i] = onewayanovaresult[2];
    rowData[i].set_ANOVA(onewayanovaresult);
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    for (int k=0;k<onewayanovaresult.length;k++) {
      if (k==onewayanovaresult.length-1)
        ss.append(onewayanovaresult[k]+"\n");
        else
      ss.append(onewayanovaresult[k]+"\t");
    }
   }
   FileIO.saveResult(filename+ "_oneWayANOVA.txt",ss.toString());
   calculateFDR(filename+ "_ANOVA_FDR.txt",pvalue);
   }
  }

  public void rowData_ANOVA_with_baseline(String filename) {
    int numRow = getNumberDataRow();
    if (repNameIndex != null) {
      String [] factor_1_name = FactorAnalysis.oneFactorName(columnInfo[repRow]);
      int [][] factor_1_index = FactorAnalysis.oneFactorNameIndex(columnInfo[repRow],factor_1_name);
      float [][] decomposedData1 = new float[factor_1_index.length][];
      for (int i=0;i<decomposedData1.length;i++) {
        if (factor_1_index[i]!=null) {
          decomposedData1[i] = new float[factor_1_index[i].length];
          }
      }
      float [] mean1 = new float[factor_1_name.length];
      float [] onewayanovaresult = new float[ANOVA.getANOVA_Name().length];

      StringBuffer ss = new StringBuffer();
      for (int k=0;k<generalInfo[0].length;k++)
      ss.append(generalInfo[0][k]+"\t");
      for (int k=0;k<ANOVA.getANOVA_Name().length;k++) {
        if (k == ANOVA.getANOVA_Name().length-1) {
          ss.append(ANOVA.getANOVA_Name()[k] + "\n");
        }
        else {
          ss.append(ANOVA.getANOVA_Name()[k] + "\t");
        }
      }

      float [] data;
      float [] zero_data = new float[columnInfo[repRow].length+factor_1_index[0].length];
     float [] pvalue = new float[numRow];
    String str;

    for (int i=0;i<numRow;i++) {
     data=rowData[i].getData();
     ANOVA.rowData_oneWayANOVA_with_baseline(data,zero_data,factor_1_index,mean1,decomposedData1,onewayanovaresult);
     str = onewayanovaresult[2]+"";
     if (str.equals("NaN")) {
       pvalue[i] = 1;
     }
       else
     pvalue[i] = onewayanovaresult[2];
     rowData[i].set_ANOVA(onewayanovaresult);
     for (int k=0;k<generalInfo[0].length;k++) {
       ss.append(rowData[i].getRowInfoString()[k]+"\t");
     }
     for (int k=0;k<onewayanovaresult.length;k++) {
       if (k==onewayanovaresult.length-1)
         ss.append(onewayanovaresult[k]+"\n");
         else
       ss.append(onewayanovaresult[k]+"\t");
     }
   }
   FileIO.saveResult(filename+ "_ANOVA_with_baseline.txt",ss.toString());
calculateFDR(filename+ "_ANOVA_with_baseline_FDR.txt",pvalue);
   }
  }

  public void rowData_SNR(String filename, boolean relativeChange) {
    if (repNameIndex == null) return;
   int numRow = getNumberDataRow();
   float [] data;
   float [] result;
//     StringBuffer ss = new StringBuffer();
   try {
     FileWriter wrt = new FileWriter(new File(FileIO.path,filename+ "_SNR.txt"));
     PrintWriter out = new PrintWriter(wrt);
     for (int k=0;k<generalInfo[0].length;k++)
    out.print(this.generalInfo[0][k]+"\t");
    for (int k=0;k<Signal_to_Noise_Analysis.get_Signal_to_Noise_Name().length;k++) {
        out.print(Signal_to_Noise_Analysis.get_Signal_to_Noise_Name()[k] + "\t");
    }
    out.print("beta\n");
    float [] pvalue=new float[numRow];
    String str;
    float [][] decomposedData = new float[repNameIndex.length][];
   for (int i=0;i<repNameIndex.length;i++)
     decomposedData[i] = new float[repNameIndex[i].length];
   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    result = Signal_to_Noise_Analysis.profile_max_signal_to_pooled_within_variance_analysis(data,
        repNameIndex, relativeChange,decomposedData);
    str = result[3]+"";
    if (str.equals("NaN")) {
      pvalue[i] = 1;
      result[0] = 0;
      result[1] = 1;
      result[2] = 0;
      result[3] = 1;
      result[4] = 1;
    }
      else
    pvalue[i] = result[3];
    rowData[i].set_SNR(result);
    for (int k=0;k<generalInfo[0].length;k++) {
      out.print(rowData[i].getRowInfoString()[k]+"\t");
    }
    for (int k=0;k<result.length;k++) {
      if (k==result.length-1)
        out.print(result[k]+"\n");
        else
      out.print(result[k]+"\t");
    }
    msg.display(result);
   }
   calculateFDR(filename+ "_SNR_FDR.txt",pvalue);


    wrt.flush();
    wrt.close();

 }
 catch (FileNotFoundException e) {
   System.out.println("FileNotFoundException "+e.toString());
 }
 catch (IOException e) {
 System.out.println("IOException "+e.toString());
 }




//   FileIO.saveResult(filename+ "_SNR.txt",ss.toString());
  }
  public void rowData_SNR_bootstrapping(String filename,int number_of_bootstrapping, boolean relativeChange) {
    if (repNameIndex == null) return;
    Signal_to_Noise_Analysis.pvalue = new float[number_of_bootstrapping];
   int numRow = getNumberDataRow();
   float [] data=rowData[0].getData();
   float [] randomData = new float[data.length];
   int [] randomIndex = new int[data.length];
   Random random = new Random();
   float [][] decomposedData = new float[repNameIndex.length][];
  for (int i=0;i<repNameIndex.length;i++)
    decomposedData[i] = new float[repNameIndex[i].length];
   float [] result;

     float [] bin = new float[100];
     for (int i=0;i<100;i++)
       bin[i] = 0.01f*i;
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
     ss.append(this.generalInfo[0][k]+"\t");
 //        msg.messageDisplay(number_of_bootstrapping + " bootTrapping_pvalue\n");
         ss.append(number_of_bootstrapping + " bootTrapping_pvalue\n");
   float [] pvalue=new float[numRow];
   float [] histoData = new float[bin.length];
//   String str;

   for (int i=0;i<numRow;i++) {
//     if (i%300 == 0)
//     Display.display("gene "+ i);
    data=rowData[i].getData();
    pvalue[i] = Signal_to_Noise_Analysis.profile_SNR_bootstrapping(data,randomData,repNameIndex,randomIndex,
        number_of_bootstrapping,random,bin,histoData,relativeChange,decomposedData);
//    histo = Signal_to_Noise_Analysis.pvalue_histogram;
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    ss.append(pvalue[i]+"\t");
    for (int n=0;n<histoData.length;n++) {
      if (n == histoData.length-1)
        ss.append(histoData[n]+"\n");
     else
      ss.append(histoData[n]+"\t");
    }
   }
   FileIO.saveResult(filename+ "_bootstrapping_SNR.txt",ss.toString());

   calculateFDR(filename+ "_SNR_bootstrapping_SNR_FDR.txt",pvalue);

  }

  public void rowData_ANOVA_bootstrapping(String filename,int number_of_bootstrapping) {
//    Signal_to_Noise_Analysis.pvalue = new float[number_of_bootstrapping];
    if (repNameIndex == null) return;
//    ANOVA.pvalue = new float[number_of_bootstrapping];

    int numRow = getNumberDataRow();
    float [] data=rowData[0].getData();
    float [] randomData = new float[data.length];
    int [] randomIndex = new int[data.length];
    Random random = new Random();
    float [][] decomposedData = new float[repNameIndex.length][];
   for (int i=0;i<repNameIndex.length;i++)
     decomposedData[i] = new float[repNameIndex[i].length];
   float [] mean =  new float[repNameIndex.length];
   float [] bootNum_pvalue = new float[number_of_bootstrapping];
   float [] result;

     float [] bin = new float[100];
     for (int i=0;i<100;i++)
       bin[i] = 0.01f*i;
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
     ss.append(this.generalInfo[0][k]+"\t");
  //       msg.messageDisplay(number_of_bootstrapping + " bootTrapping_pvalue\n");
         ss.append(" ANOVA_bootTrapping_pvalue\thistogram");
   float [] pvalue=new float[numRow];
   float [] histoData = new float[bin.length];
///   String str;
//    public static float profile_oneWayANOVA_bootstrapping(float [] _data, float [] random_data,float [] mean,float [] bootsNum_pvalues,
//        int [][] repIndex,int [] randomIndex,
//        int numberOfbootstrapping,Random random, float [] bin, float [][] decomposedData) {

   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
    pvalue[i] = ANOVA.profile_oneWayANOVA_bootstrapping(data,randomData,mean,bootNum_pvalue,repNameIndex,randomIndex,
        number_of_bootstrapping,random,bin,histoData,decomposedData);
 //   histo = ANOVA.pvalue_histogram;
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    ss.append(pvalue[i]+"\t");
    for (int n=0;n<histoData.length;n++) {
      if (n == histoData.length-1)
        ss.append(histoData[n]+"\n");
     else
      ss.append(histoData[n]+"\t");
    }
   }
 //  if (anova) {
     FileIO.saveResult(filename+ "_oneWayANOVA_bootstrapping.txt",ss.toString());

     calculateFDR(filename+ "_oneWayANOVA_bootstrapping_FDR.txt",pvalue);

 //  }
 //  else {
//   FileIO.saveResult(filename+ "_bootstrapping_SNR.txt",ss.toString());

//   calculateFDR(filename+ "_SNR_bootstrapping_SNR_FDR.txt",pvalue);


 //  }
  }
//

  public void rowData_ANOVA_bootstrapping_shuffle(String filename,int number_of_bootstrapping) {
    if (repNameIndex == null) return;

//    ANOVA.pvalue = new float[number_of_bootstrapping];
   int numRow = getNumberDataRow();
   float [] data=rowData[0].getData();
   float [] shuffledData = new float[data.length];
   int [] randomIndex = new int[data.length];
   Random random = new Random();
   float [][] decomposedData = new float[repNameIndex.length][];
  for (int i=0;i<repNameIndex.length;i++)
    decomposedData[i] = new float[repNameIndex[i].length];
  float [] mean = new float[repNameIndex.length];
  float [] boot_num_pvalue = new float[number_of_bootstrapping];
  ArrayList  arrayList = new ArrayList();
    for (int i=0; i<data.length; i++)
        arrayList.add(i+"");

   float [] result;
     float [] bin = new float[100];
     for (int i=0;i<100;i++)
       bin[i] = 0.01f*i;
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
     ss.append(this.generalInfo[0][k]+"\t");
  //       msg.messageDisplay(number_of_bootstrapping + " bootTrapping_pvalue\n");
         ss.append(" ANOVA_bootTrapping_shuffle_pvalue\thistogram");
   float [] pvalue=new float[numRow];
   float [] histoData = new float[bin.length];
//   public static float profile_oneWayANOVA_bootstrapping_shuffle(float [] _data, float [] shuffled_data, float [] mean,float [] bootsNum_pvalues,
//       int [][] repIndex, int [] randomIndex,
//       int numberOfbootstrapping,Random random, float [] bin, float [][] decomposedData,ArrayList  arrayList) {

   for (int i=0;i<numRow;i++) {
    data=rowData[i].getData();
      pvalue[i] = ANOVA.profile_oneWayANOVA_bootstrapping_shuffle(data,shuffledData,mean,boot_num_pvalue,repNameIndex,randomIndex,
          number_of_bootstrapping,random,bin,histoData,decomposedData,arrayList);
  //  histo = ANOVA.pvalue_histogram;
    for (int k=0;k<generalInfo[0].length;k++) {
      ss.append(rowData[i].getRowInfoString()[k]+"\t");
    }
    ss.append(pvalue[i]+"\t");
    for (int n=0;n<histoData.length;n++) {
      if (n == histoData.length-1)
        ss.append(histoData[n]+"\n");
     else
      ss.append(histoData[n]+"\t");
    }
   }
     FileIO.saveResult(filename+ "_ANOVA_bootstrapping_shuffle.txt",ss.toString());
     calculateFDR(filename+ "_ANOVA_bootstrapping_shuffle_FDR.txt",pvalue);
  }

  public void rowData_SNR_bootstrapping_shuffle(String filename,int number_of_bootstrapping, boolean relativeChange) {
    if (repNameIndex == null) return;
  Signal_to_Noise_Analysis.pvalue = new float[number_of_bootstrapping];
 int numRow = getNumberDataRow();
 float [] data=rowData[0].getData();
 float [] shuffledData = new float[data.length];
 int [] randomIndex = new int[data.length];
 Random random = new Random();
 float [][] decomposedData = new float[repNameIndex.length][];
for (int i=0;i<repNameIndex.length;i++)
  decomposedData[i] = new float[repNameIndex[i].length];
ArrayList  arrayList = new ArrayList();
  for (int i=0; i<data.length; i++)
      arrayList.add(i+"");
 float [] result;
   float [] bin = new float[100];
   for (int i=0;i<100;i++)
     bin[i] = 0.01f*i;
   StringBuffer ss = new StringBuffer();
   for (int k=0;k<generalInfo[0].length;k++)
   ss.append(this.generalInfo[0][k]+"\t");
 //      msg.messageDisplay(number_of_bootstrapping + " bootTrapping_pvalue\n");
       ss.append(number_of_bootstrapping + " bootTrapping_pvalue\n");
 float [] pvalue=new float[numRow];
 float [] histoData= new float[bin.length];


 for (int i=0;i<numRow;i++) {
//   if (i%300 == 0)
//   Display.display("gene "+ i);
  data=rowData[i].getData();

  pvalue[i] = Signal_to_Noise_Analysis.rowData_SNR_bootstrapping_shuffle(data,shuffledData,repNameIndex,randomIndex,
      number_of_bootstrapping, random,bin,histoData,relativeChange,decomposedData,arrayList);
//  histo = Signal_to_Noise_Analysis.pvalue_histogram;
  for (int k=0;k<generalInfo[0].length;k++) {
    ss.append(rowData[i].getRowInfoString()[k]+"\t");
  }
  ss.append(pvalue[i]+"\t");
  for (int n=0;n<histoData.length;n++) {
    if (n == histoData.length-1)
      ss.append(histoData[n]+"\n");
   else
    ss.append(histoData[n]+"\t");
  }
 }
 FileIO.saveResult(filename+ "_SNR_bootstrapping_shuffle.txt",ss.toString());

 calculateFDR(filename+ "_SNR_bootstrapping_shuffle_FDR.txt",pvalue);
 //}


  }
//  public static float [] profile_SNR_bootstrapping_shuffle_paired_classification(float [] _data,
//    int [][] repIindex,int [][] pairedIndex, int numberOfbootstrapping,float snr_threshold,

//  dataSets.rowData_SNR_bootstrapping_shuffle_classification(fileName,
//      numberofbootstrapping,snr,mag,jRadioButton_relativeChange.isSelected());

  public void rowData_SNR_bootstrapping_shuffle_classification(String filename,int number_of_bootstrapping,
      float snr, float mag,  boolean relativeChange) {
    if (repNameIndex == null) return;
   int numRow = getNumberDataRow();
   float [] data=rowData[0].getData();
   float [] shuffledData = new float[data.length];
   int [] randomIndex = new int[data.length];
   Random random = new Random();
   float [][] decomposedData = new float[repNameIndex.length][];
  for (int i=0;i<repNameIndex.length;i++)
    decomposedData[i] = new float[repNameIndex[i].length];
  ArrayList  arrayList = new ArrayList();
    for (int i=0; i<data.length; i++)
        arrayList.add(i+"");
   float [] result;
     StringBuffer ss = new StringBuffer();
     for (int k=0;k<generalInfo[0].length;k++)
     ss.append(this.generalInfo[0][k]+"\t");
    //     msg.messageDisplay(number_of_bootstrapping + " bootTrapping_pvalue\n");
         ss.append("SNR_bootstrapping\tMag_bootstrapping\n");
   float [] snr_mag;
 //  float [] histo;


   for (int i=0;i<numRow;i++) {
//      if (i%1000 == 0)
//        Display.display("gene number " + i+"/" + numRow);
    data=rowData[i].getData();
     snr_mag = Signal_to_Noise_Analysis.profile_SNR_bootstrapping_shuffle_classification(data,shuffledData,
        repNameIndex,randomIndex,
        number_of_bootstrapping,snr,mag,random,relativeChange,decomposedData,arrayList);
      for (int k=0;k<generalInfo[0].length;k++) {
        ss.append(rowData[i].getRowInfoString()[k]+"\t");
      }
      ss.append(snr_mag[0]+"\t"+snr_mag[1]+ "\t"+indexToString(Signal_to_Noise_Analysis.bootstrapping_index) + "\n");
   }
   FileIO.saveResult(filename+ "_snr_bootstrapping_classification.txt",ss.toString());
  }


private String indexToString(int [] index) {
  String str = "";
  for (int i=0;i<index.length;i++)
    if (i==index.length-1)
      str += index[i];
      else
    str += index[i] + "\t";
  return str;
}
  public void saveData_repAvgAndStd(File f,RowDataIO [] rd) {
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_RepName_avg_and_std());
      for (int i=0;i<rd.length;i++) {
        out.print(rd[i].repAvgAndStd_toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }

  }
  public void saveData_repAvg(File f,RowDataIO [] rd) {
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_RepName_avg());
      for (int i=0;i<rd.length;i++) {
        out.print(rd[i].repAvg_toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }

  }

  public void saveData(File f,RowDataIO [] rd) {
    float testValue;
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_ColumnInfo());
      for (int i=0;i<rd.length;i++) {
        out.print(rd[i].toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }

  }
  public void saveData(PrintWriter out, String s) {
      out.print(s);
  }


  public void saveData(File f) {
    float testValue;
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_ColumnInfo());
      for (int i=0;i<rowData.length;i++) {
        out.print(rowData[i].toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }

  }
  public void saveData(String filename) {
  String fileName = filename+rowData.length+".txt";
    try {
      FileWriter wrt = new FileWriter(new File(FileIO.path,fileName));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_ColumnInfo());
      for (int i=0;i<rowData.length;i++) {
        out.print(rowData[i].toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }
   if (numGeneInEachCat != null) {
     int start =0,end=0;
     for (int i=0;i<numGeneInEachCat.length;i++) {
       start = end;
       end += numGeneInEachCat[i];
//       saveDataInCat(i+1,start,end);
       saveData(new File(FileIO.path,"selectedGenesInPattern_"+(i+1)+"_"+(end-start)+".txt"),start,end);
     }

   }
  }
/*
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

  */
  public void saveData(File f, int startRow, int endRow) {
    float testValue;
    if (f == null) return;
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),f.getName()));
      PrintWriter out = new PrintWriter(wrt);
      out.print(toString_ColumnInfo());
      for (int i=startRow;i<endRow;i++) {
        out.print(rowData[i].toString());
      }
      wrt.flush();
      wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }

  }

  float [][] getColumnAvgAndStd(float [][] d) {
    float [] dd = new float[d.length];
    float [][] result = new float[2][d[0].length];
    for (int j=0;j<d[0].length;j++) {
       for (int i=0;i<d.length;i++) {
          dd[i] = d[i][j];
       }
       result[0][j] = Statistics.mean(dd);
       result[1][j] = Statistics.stdDev(dd);
    }
    return result;
  }
  public float [] getExpAvgAndStd(int n) {
    float [] d = getColumnData_1(n);
    float [] r=new float[2];
    r[0] = Statistics.mean(d);
    r[1] = Statistics.stdDev(d);
    return r;
  }
  public float [] getExpValue() {
    return expValue;
  }

  public void setExpValue() {
      expValue = new float[numberExp];
      for (int i=0;i<numberExp;i++)
        expValue[i] = i+1;
  }
  public void setExpValue(float [] d) {
      expValue = d;
  }
  public void setExpValue(String [] s) {
    float [] d = new float[s.length];
    for (int i=0;i<s.length;i++)
      try {
      d[i] = Float.parseFloat(s[i]);
      }
    catch (NumberFormatException e) {
      setExpValue();
      return;
    }
    setExpValue(d);
  }

  public void setNumberDataRow(int num) {
    numberDataRow = num;
    if (rowData == null || rowData.length != num)
      rowData =  new RowDataIO[numberDataRow];
  }

  public int getNumberDataRow() {
    return numberDataRow;
  }

  public void setNumberExp(int i) {
    numberExp = i;
  }

  public int getNumberExp() {
    return numberExp;
  }

  public void setNumColumnGeneDescInfo(int i) {
    numColumnGeneDescInfo = i;
  }

  public int getNumColumnGeneDescInfo() {
    return numColumnGeneDescInfo;
  }

  public void setNumRowExpDescInfo(int i) {
    numRowExpDescInfo = i;
  }

  public int getNumRowExpDescInfo() {
    return numRowExpDescInfo;
  }

  public void displayData() {
       new DataSetTableFrame(dataPlot,this, msg).setVisible(true);
  }

  public String getRowInfo(int i,int j) {
    return rowData[i].getRowInfo(j).toString();
  }

  public String [] getRowInfo(int col) {
    String [] str = new String[numberDataRow];
    for (int i=0;i<numberDataRow;i++) {
      str[i] = getRowInfo(i,col);
    }
//    msg.display("gene numberDataRow"+numberDataRow);
    return str;
  }

  public String [] getColumnInfo(int row) {
    return columnInfo[row];
  }

  public String [][] getColumnInfo() {
    return columnInfo;
  }
  public String  getGeneralInfoAndColumnInfoToString(int row) {
    int num=columnInfo[row].length+generalInfo[row].length;
    String [] str = new String[num];
    for (int i=0;i<generalInfo[row].length;i++)
      str[i] = generalInfo[row][i];
    for (int i=0;i<columnInfo[row].length;i++)
      str[i+generalInfo[row].length] = columnInfo[row][i];
    return arrayToString(str);
  }
  private String arrayToString(String [] str) {
    String s="";
    for (int i=0;i<str.length;i++)
      if (i != str.length-1) {
    s+=str[i]+"\t";
      }
      else
        s+=str[i]+"\n";
      return s;
  }
  public void setColumnInfo(String [][] str) {
    columnInfo = str;
  }
  public void setColumnInfo(int rowNum, int colNum, String str) {
    columnInfo[rowNum][colNum] = new String(str);
  }
  public String [][] getGneralInfo() {
    return generalInfo;
  }
  public String [] getGeneralInfo(int row) {
    return generalInfo[row];
  }
  public String [][] getGeneralInfo() {
    return generalInfo;
  }
  public void setGeneralInfo(String [][] str) {
    generalInfo = str;
  }

  public int [][] getRepNameIndex() {
    return repNameIndex;
  }
  public void setRepNameIndex(int [][] idx) {
    repNameIndex = idx;
  }
  public int [][] getCellLineNameIndex() {//replicateProfileNameIndex,bioGroupNameIndex
    return cellLineNameIndex;
  }
  public void setCellLineNameIndex(int [][] idx) {
    cellLineNameIndex = idx;
  }
//  public int [][] getCellLineProfileIndex() {//replicateProfileNameIndex,bioGroupNameIndex
//    return cellLineProfileIndex;
//  }
//  public void setCellLineProfileIndex(int [][] idx) {
//    cellLineProfileIndex = idx;
//  }
//  public int [][] getDataPlotIndex() {//replicateProfileNameIndex,bioGroupNameIndex
//    return dataPlotIndex;
//  }
//  public void setDataPlotIndex(int [][] idx) {
//    dataPlotIndex = idx;
//  }

//  public int [][] getClusterIndex() {
//    return clusterIndex;
//  }
//  public void setClusterIndex(int [][] idx) {
//    clusterIndex = idx;
//  }

  public String [] getTrimRepName() {
    return trimRepName;
  }
  public String getTrimRepName(int i) {
    return trimRepName[i];
  }
  public void setTrimRepName(Object [] obj) {
    trimRepName = new String[obj.length];
    for (int i=0;i<obj.length;i++)
      trimRepName[i] = obj[i].toString();
  }

  public String [] getTrimDataPlotOptionName() {
    return trimDataPlotOptionName;
  }
  public void setTrimDataPlotOptionName(Object [] obj) {
    trimDataPlotOptionName = new String[obj.length];
    for (int i=0;i<obj.length;i++)
      trimDataPlotOptionName[i] = obj[i].toString();
  }

  public void setColumnInfo(String [][] _generalInfo,String [][] _columnInfo,
                            int [] _columnInfoIndex, int _repRow, int _dataPlotOption) {
    generalInfo = _generalInfo;
    columnInfo = _columnInfo;
    dataPlotOption=_dataPlotOption;
    for (int i=0;i<_columnInfoIndex.length;i++)
      if (_columnInfoIndex[i] == repRow)
        createReplicateNameIndex(repRow);
 //   else if (_columnInfoIndex[i] == _dataPlotRow)
 //     setDataPlotIndex(_dataPlotRow);
  }

  public void setRowInfoAndData(String [][] str,float [][] d) {
    if (str.length != d.length) return;
    int rowNum = str.length;
    rowData = new RowDataIO[rowNum];
    for (int i=0;i<rowNum;i++)
      rowData[i] = new RowDataIO(str[i], d[i]);
  }
  public int getRepRow() {
    return repRow;
  }
  public int getStepRow() {
    return stepRow;
  }
  public int getExpNameRow() {
    return expNameRow;
  }
  public void setExpNameRow(int n) {
    expNameRow = n;
  }

  public int getCellLineProfileRow() {
    return cellLineProfileRow;
  }
  public void setCellLineProfileRow(int n) {
    if (n==-1) {
      cellLineProfileRow=n;
      cellLineProfileIndex = null;
    }
    else if (cellLineProfileRow!=n && cellLineNameRow != -1) {
      cellLineProfileRow=n;
      cellLineProfileIndex =  FactorAnalysis.getGroupProfileIndex(cellLineNameIndex,columnInfo[cellLineProfileRow]);
    }
    else cellLineProfileRow=n;
  }

  public int getCellLineNameRow() {
    return cellLineNameRow;
  }
  public void setCellLineNameRow(int n) {
    if (n==-1){
      cellLineNameRow = n;
      cellLineName = null;
      cellLineNameIndex = null;
      cellLineProfileIndex = null;
    }
   else if (cellLineNameRow != n) {
      cellLineNameRow = n;
      cellLineName = FactorAnalysis.treeSetArray(columnInfo[cellLineNameRow]);
      cellLineNameIndex = FactorAnalysis.getGroupIndex(columnInfo[cellLineNameRow],cellLineName);
      if (cellLineProfileRow!=-1) {
        cellLineProfileIndex =  FactorAnalysis.getGroupProfileIndex(cellLineNameIndex,columnInfo[cellLineProfileRow]);
      }
    }
  }
  public void setFactor3Row(int n) {
       factor3_row = n;
  }

  public void setRepRow(int n) {
    if (n == -1) {
      repRow = n;
      createReplicateNameIndex(null);
    }
    else if (repRow != n) {
      repRow = n;
      createReplicateNameIndex(columnInfo[repRow]);

    }
  }

  public void setStepRow(int n) {
    stepRow = n;
  }
  public int getDataPlotOption() {
    return dataPlotOption;
  }
  public void setDataPlotOption(int n) {
    dataPlotOption = n;
  }
  public MessageBoard getMSG() {
    return msg;
  }

  public void setMSG(MessageBoard m) {
    msg =m;
  }

  public Object getData(int i,int j) {
    return rowData[i].getData(j);
  }

  public String getColumnName(int i) {
    if (i<numColumnGeneDescInfo) return generalInfo[0][i];
    else return columnInfo[0][i-numColumnGeneDescInfo];
  }

  public RowDataIO [] getRowData() {
    return rowData;
  }
  public void setRowData(RowDataIO [] r) {
    if (r == null) return;
    rowData =  new RowDataIO[r.length];
    for (int i=0;i<r.length;i++) {
      rowData[i] = new RowDataIO(r[i]);
    }

  }
  public RowDataIO getRow(int i) {
    return rowData[i];
  }
  public String getRowToString(int i) {
    return rowData[i].toString();
  }

  public float [] getRowData(int i) {
    return rowData[i].getData();
  }
public void setRowInfoColor(int row, int col, Color c) {
  rowData[row].setRowInfoColor(col,c);
}
  public float [][] getValues() {
    float [][] data = new float[numberDataRow][];
    for (int i=0;i<numberDataRow;i++)
      data[i] = getRowData(i);
    return data;
  }
  public void statisticalPower(int col) {
    float [] d = getColumnData(col);
    float [] percent = {0.001f,0.002f,0.005f,0.01f,0.02f,0.05f,0.1f,0.2f,0.5f};
    float [] power = new float[percent.length];
    msg.display("\npercent\tpower\n");
    for (int i=0;i<percent.length;i++) {
     power[i] = Statistics.statiscis_power(d, percent[i]);
     msg.display(percent[i] +"\t" + power[i] + "\n");
    }
  }
  public void calculateFDR(String name, float [] pvalue) {
    float [][] fdr = Statistics.get_FDR(pvalue);
    msg.display("SNR_FDR\tSNR_bootstrapping_pvalue");
    msg.display(fdr[0],fdr[1]);
    StringBuffer s = new StringBuffer();
    s.append("FDR\tp value\n");
    for (int i=0;i<fdr[0].length;i++) {
      s.append(fdr[0][i]+"\t"+fdr[1][i]+"\n");
    }
    FileIO.saveResult(name,s.toString());

  }
  public void FDR(int col) {
     float [] d = getColumnData(col);
     calculateFDR(getColName(col)+".txt",d);
  }
  public float [] getColumnData(int col) {
    if (col < numColumnGeneDescInfo) return null;
    int dataColumn = col-numColumnGeneDescInfo;
    float [] data = new float[numberDataRow];
    for (int i=0;i<numberDataRow;i++)
      data[i] = rowData[i].getAData(dataColumn);
    return data;
  }
  public float [] getColumnData_1(int col) {
    float [] data = new float[numberDataRow];
    for (int i=0;i<numberDataRow;i++)
      data[i] = rowData[i].getAData(col);
    return data;
  }
  public void setColumnData_1(int col, float [] x) {
    for (int i=0;i<numberDataRow;i++)
      rowData[i].setAData(col,x[i]);
  }
public void setColumn_mean(int col, float mean) {
  if (columnMean == null) {
    columnMean = new float[columnInfo[0].length];
    for (int i=0;i<columnMean.length;i++)
      columnMean[i] = -1;
  }
  columnMean[col] = mean;
}

public float [] calculateColumn_mean() {
  columnMean =  new float[columnInfo[0].length];
  float [] x;
    for (int i=0;i<columnMean.length;i++) {
      x = getColumnData_1(i);
      columnMean[i] = Statistics.mean(x);
    }
    return columnMean;
}

public void columnData_rescale(float rescalevalue) {
  float [] x;
  for (int i=0;i<columnInfo[0].length;i++) {
    x = getColumnData_1(i);
    DataConversion.scaling2D(x,rescalevalue/columnMean[i]);
    setColumnData_1(i,x);
  }
}
public float [] getColumn_mean() {
  return columnMean;
}

  public String [] getColumnLabel_1(int col) {
    if (col < columnInfo[0].length) {
    String [] data = new String[numberDataRow];
    for (int i=0;i<numberDataRow;i++) {

      String profileName;
      if (rowData[i].getRowInfoString() !=null)
       data[i] = rowData[i].getRowInfoString()[0];
      else data[i]=null;

 //     data[i] = rowData[i].getRowInfoString()[0];
    }
    return data;
    }
    else return null;
  }

  public float getRowData(int i, int j) {
    return rowData[i].getData()[j];
  }

//  public boolean getIsDataPlotOptionInfo() {
//    return dataPlotOptionInfo;
//  }
//  public void setIsDataPlotOptionInfo(boolean b) {
//    dataPlotOptionInfo = b;
//  }
//  public boolean getIsClusterInfo() {
//    return clusterInfo;
//  }
//  public void setIsClusterInfo(boolean b) {
//    clusterInfo = b;
//  }

//  public boolean getIsBioGroupInfo() {
//    return cellLineNameInfo;
//  }

//  public void setIsBioGroupInfo(boolean b) {
//    cellLineNameInfo = b;
//  }
//  public boolean getIsReplicateProfileInfo() {
//    return cellLineProfileInfo;
//  }

//  public void setIsReplicateProfileInfo(boolean b) {
//    cellLineProfileInfo = b;
//  }
//  public boolean getIsRepInfo() {
//    return repInfo;
//  }
//  public boolean getIsStepInfo() {
//    return stepInfo;
//  }

 // public void setIsRepInfo(boolean b) {
 //   repInfo = b;
 // }
//  public void setIsStepInfo(boolean b) {
//    stepInfo = b;
//  }

  public String expNameToString() {
    String str = "";
    for (int i = 0;i<numberExp;i++)
      if (i < (numberExp-1))
        str += columnInfo[0][i] + "\t";
    else
      str += columnInfo[0][i];
    return str;

  }

  public void sameGeneTobeMerged() {
    String [] geneID = new String[rowData.length];
    for (int i=0;i<rowData.length;i++) {
      geneID[i] = rowData[i].getRowInfo(0).toString();
    }
    Sorting.quicksort(true,geneID,rowData);
    int numGene = numberDataRow;
    Vector newList = new Vector();
    Vector mergedList = new Vector();
    boolean sameGene = false;
    float [] y;
    for (int i=0;i<numGene-1;i++) {
      if (!sameGene && !rowData[i].getRowInfo(0).toString().equals( rowData[i+1].getRowInfo(0).toString() )) { //1
        newList.add(rowData[i]);      // condition not same gene yet, not same id
        if (i == numGene-1) {
          newList.add(rowData[i+1]);
        }
      }
      else if (!sameGene){
        mergedList.add(rowData[i]);  // condition not same gene yet, but same id
        sameGene = true;              // set to same gene
        if (i == numGene-1) {
          mergedList.add(rowData[i+1]);
          RowDataIO rd = getMergedRowData(mergedList);
          newList.add(rd);
        }
      }
      else if (sameGene && !rowData[i].getRowInfo(0).toString().equals( rowData[i+1].getRowInfo(0).toString() )) {
        mergedList.add(rowData[i]);  //  condition same gene, but not same id
        RowDataIO rd = getMergedRowData(mergedList);
        mergedList = new Vector();
        newList.add(rd);
        sameGene = false;
        if (i == numGene-1)
          newList.add(rowData[i+1]);
      }
      else {  // condistion same gene and same id
        mergedList.add(rowData[i]);
        if (i == numGene-1) {
          mergedList.add(rowData[i+1]);
          RowDataIO rd = getMergedRowData(mergedList);
          newList.add(rd);
        }
      }
    }
    numberDataRow = newList.size();
    Object [] o = newList.toArray();
    rowData = new RowDataIO[numberDataRow];
    for (int i=0;i<numberDataRow;i++)
      rowData[i] = (RowDataIO)o[i];

  }
  public RowDataIO getMergedRowData(Vector v) {
    int mergedGeneNum = v.size();
    float [] y;
    float [] mergedData = new float[numberExp];

    for (int j=0;j<numberExp;j++) mergedData[j] = 0;
    for (int j=0;j<mergedGeneNum;j++) {
      RowDataIO gf = (RowDataIO)v.elementAt(j);
      y = gf.getData();
      for (int k=0;k<numberExp;k++)
        mergedData[k] += y[k];
    }
    for (int j=0;j<numberExp;j++) mergedData[j] /= mergedGeneNum;
    RowDataIO gf = (RowDataIO)v.elementAt(0);
    gf.setData(mergedData);
    return gf;
  }
  /*
  public void addExtraColumn(String [] _extraColumnName, String [][] _extraData,
                             int [] datatype) {
    if (rowData == null || rowData.length != _extraData.length) return;
    extraColumnName = _extraColumnName;
    dataType = datatype;
    for (int i=0;i<numberDataRow;i++)
      rowData[i].setExtraData(_extraData[i]);
  }
  */
//  public String [] getExtraColumnName() {
//    return extraColumnName;
//  }
//  public void setExtraColumnName(String [] str) {
//    extraColumnName=str;
//  }
//  public String getExtraColumnName(int i) {
//    return extraColumnName[i];
//  }
  public String toString() {
    StringBuffer s = new StringBuffer();
//    String [][] str_g = getGneralInfo();
//    String [][] str_c = getColumnInfo();
    for (int i=0;i<generalInfo.length;i++) {
      for (int j=0;j<generalInfo[i].length;j++) {
        s.append(generalInfo[i][j]+"\t");
      }
      for (int j=0;j<columnInfo[i].length;j++) {
        if (j == columnInfo[i].length-1)
        s.append(columnInfo[i][j]+"\n");
        else
        s.append(columnInfo[i][j]+"\t");
      }
//      s.append("\n");
    }
    for (int i=0;i<rowData.length;i++)
      s.append(rowData[i].toString()+"\n");
    return s.toString();
  }
  public String toString_ColumnInfo() {
    StringBuffer s = new StringBuffer();
 //   String [][] str_g = getGneralInfo();
 //   String [][] str_c = getColumnInfo();
    for (int i=0;i<generalInfo.length;i++) {
      for (int j=0;j<generalInfo[i].length;j++) {
        s.append(generalInfo[i][j]+"\t");
      }
      for (int j=0;j<columnInfo[i].length;j++) {
        if (j == columnInfo[i].length-1)
          s.append(columnInfo[i][j]+"\n");
        else
        s.append(columnInfo[i][j]+"\t");
      }
    }
    return s.toString();
  }
  public String toString_ColumnInfo(int i) {
    StringBuffer s = new StringBuffer();
//    String [][] str_g = getGneralInfo();
//    String [][] str_c = getColumnInfo();
//    for (int i=0;i<str_g.length;i++) {
      for (int j=0;j<generalInfo[i].length;j++) {
        s.append(generalInfo[i][j]+"\t");
      }
      for (int j=0;j<columnInfo[i].length;j++) {
        if (j == columnInfo[i].length-1)
          s.append(columnInfo[i][j]);
        else
        s.append(columnInfo[i][j]+"\t");
      }
//    }
    return s.toString();
  }
  public String toString_ColumnInfo_1() {
    StringBuffer s = new StringBuffer();
    for (int i=0;i<generalInfo.length;i++) {
      if(i != generalInfo.length)
      s.append(toString_ColumnInfo(i)+"\n");
      else
        s.append(toString_ColumnInfo(i)+"\t");
    }
    return s.toString();
  }
  public String toString_ColumnInfo_2() {
    StringBuffer s = new StringBuffer();
    for (int i=0;i<generalInfo[0].length;i++) {
      s.append(generalInfo[0][i]+"\t");
    }
    return s.toString();
  }

  public String toString_RepName_avg_and_std() {
    StringBuffer s = new StringBuffer();
    s.append(toString_ColumnInfo_2());
    for (int i=0;i<trimRepName.length;i++) {
      if (i ==trimRepName.length-1)
        s.append(trimRepName[i]+"_avg\t"+trimRepName[i]+"_std\t" +trimRepName[i]+"_relative_std_percent");
        else
        s.append(trimRepName[i]+"_avg\t"+trimRepName[i]+"_std\t" +trimRepName[i]+"_relative_std_percent\t");
    }
//   s.append("MaxDiff\tMaxStd\tVariance\tReplicateAverage\tReplicateStd\treplicateVariance");
   if(cellLineProfileIndex==null) {
     s.append("\n");
   }
   else {
     s.append("\tmaxIntraCorrelation r-value\tminIntraCorrelation r-value\tmeanIntraCorrelation r-value\n");
   }
    return s.toString();
  }
  public String toString_RepName_avg() {
    StringBuffer s = new StringBuffer();
    s.append(toString_ColumnInfo_2());
    for (int i=0;i<trimRepName.length;i++) {
      if (i ==trimRepName.length-1)
        s.append(trimRepName[i]+"_avg");
        else
        s.append(trimRepName[i]+"_avg\t");
    }
//   s.append("MaxDiff\tMaxStd\tVariance\tReplicateAverage\tReplicateStd\treplicateVariance");
   if(cellLineProfileIndex==null) {
     s.append("\n");
   }
   else {
     s.append("\tmaxIntraCorrelation r-value\tminIntraCorrelation r-value\tmeanIntraCorrelation r-value\n");
   }
    return s.toString();
  }

  public String [][] getRowInfo(){
    String [][] rowInfo = new String[numberDataRow][];
//    msg.display("numberDataRow="+numberDataRow);
    for (int i=0;i<numberDataRow;i++)
      rowInfo[i] = rowData[i].getRowInfoString();
    return rowInfo;
  }
  public void pairwiseCorrelation_selectedRows(int [] selectedRow, File f) {
    if (selectedRow.length==1) return;
    int num = selectedRow.length;
    float [][] dat = new float[num][];
    String [] profilename = new String[num];
    for (int i=0;i<num;i++) {
      dat[i] = getRow(selectedRow[i]).getData();
      profilename[i] = getRow(selectedRow[i]).getRowInfo(0).toString();
    }
/*
             data[0] is a,
         data[1] is b,
         data[2] is siga probable uncertainty
         data[3] is sigb probable uncertainty
         data[4] is chi2 chi-square
         data[5] is q goodness-of-fit probability

    */
    float [] correlationResult;
    float [] fittingResult = null;
    DataModeling dm = new DataModeling();
    String s = FileIO.getFileName_wo_extension(f);
    try {
      FileWriter wrt = new FileWriter(new File(f.getParent(),s+"_row_pairwised_correlation.txt"));
      PrintWriter out = new PrintWriter(wrt);
      out.print("probe_vs_probe\tr value\tp value\tdata size\ta\tb\tsiga\tsigb\tchi2\tq goodness-of-fit probability\n");
    for (int i=0;i<num;i++) {
      for (int j=i+1; j<num;j++) {
        correlationResult = Correlation.pearsn_excludeZero(dat[i],dat[j]);
        fittingResult = dm.fit_original(dat[i],dat[j],dat[j].length,null, false);

       out.print(profilename[i] +" vs "+profilename[j] + "\t" +
                          correlationResult[0]+"\t" +
                         correlationResult[1] +"\t"+
                        correlationResult[2] +"\t" );
       out.print(          fittingResult[0]+"\t" +
                         fittingResult[1] +"\t"+
                         fittingResult[2] +"\t"+
                         fittingResult[3] +"\t"+
                         fittingResult[4] +"\t"+
                        fittingResult[5] +"\n" );
      }
    }
    wrt.flush();
    wrt.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage()+"\n");
    }


  }

  public void plotRow_dataSetTableModel(int [] selectedRow, int plotOption) {
    dataPlotOption=plotOption;
      plotRow_general(selectedRow, false,plotOption);
  }
  public void plotRow_general(int [] selectedRow, boolean blackLine, int _dataPlotOption) {
    if (selectedRow == null) return;
    if (dataPlot == null) {
      dataPlot = new DataPlot(msg);
    }
    dataPlotOption = _dataPlotOption;
    if (selectedRow.length==1) {
      boolean markState = true;
      boolean lineState = true;
     RowDataIO rowdata = getRow(selectedRow[0]);
     String title = rowdata.getRowInfo(0).toString();

     String profileName;
     if (rowdata.getRowInfoString() !=null)
      profileName = rowdata.getRowInfoString()[0];
     else profileName=null;

     float [] plotData = rowdata.getData();
      if (dataPlotOption==cellLineProfilePlot && cellLineNameIndex!=null && repNameIndex != null) {
        dataPlot.plotXY_cellLineProfile(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                          title, getColumnInfo(0), profileName,cellLineNameIndex,repNameIndex,blackLine);
      }
      else if (dataPlotOption == bioRepPlot && repNameIndex != null) {
        dataPlot.plotXY_replicate_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                         title, this.getColumnInfo(0),profileName,repNameIndex,blackLine);
      }
      else if (dataPlotOption ==scatterPlot_withErrorBar ) {
        dataPlot.plotXY_scatterplot_with_error_bar(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                         title, this.getColumnInfo(0),profileName,repNameIndex,blackLine);
      }
      else if (dataPlotOption ==averagedDat_scatterPlot ) {
        dataPlot.plotXY_scatterplot_averagedData_with_error_bar(plotData, "Experiments", "Change in Gene Expression",
                         title, profileName,blackLine);
      }
      else if (dataPlotOption ==barPlot_withErrorBar ) {
        dataPlot.plotXY_barPlot_with_error_bar(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                         title, this.getColumnInfo(0),profileName,repNameIndex,blackLine);
      }
      else if (dataPlotOption ==averagedData_barPlot ) {
        dataPlot.plotXY_barPlot_averageddata_with_error_bar( plotData, "Experiments", "Change in Gene Expression",
                         title,profileName,blackLine);
      }
      else if (dataPlotOption ==samplePlot ) {
        if (blackLine)
          dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                                  ,title,Color.black,getColumnInfo(0),profileName,true,true);
          else
        dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                                ,title,Color.blue,getColumnInfo(0),profileName,true,true);
      }
      else {
        if (blackLine)
          dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                                  ,title,Color.black,getColumnInfo(0),profileName,true,true);
          else
        dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                                ,title,Color.blue,getColumnInfo(0),profileName,true,true);
      }
    }
    else {
    int num = selectedRow.length;
    float [][] dat = new float[num][];
    boolean [] mark = new boolean[num];
    boolean [] line = new boolean[num];
    String [] profilename = new String[num];
    for (int i=0;i<num;i++) {
      dat[i] = getRow(selectedRow[i]).getData();
      mark[i] = true;
      line[i] = true;
      profilename[i] = getRow(selectedRow[i]).getRowInfo(0).toString();
    }
    boolean markState = true;
    boolean lineState = true;
    String title = "";
   dataPlot.setXTicMarks(xTicMark);
   dataPlot.setYTicMarks(yTicMark);
    if (dataPlotOption==cellLineProfilePlot && cellLineNameIndex!=null && repNameIndex != null) {
    dataPlot.plotXY_cellLineProfile_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression",
                        title, getColumnInfo(0), profilename,cellLineNameIndex,repNameIndex,blackLine);
    }
    else if (dataPlotOption == bioRepPlot && repNameIndex != null) {
      dataPlot.plotXY_replicateProfile_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression",
                       title, this.getColumnInfo(0),profilename,repNameIndex,blackLine);
    }
    else if (dataPlotOption ==scatterPlot_withErrorBar ) {
      dataPlot.plotXY_scatterplot_with_error_bar_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression",
                       title, this.getColumnInfo(0),profilename,repNameIndex,blackLine);
    }
    else if (dataPlotOption ==barPlot_withErrorBar ) {
      dataPlot.plotXY_barPlot_with_error_bar_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression",
                       title, this.getColumnInfo(0),profilename,repNameIndex,blackLine);
    }
    else if (dataPlotOption ==samplePlot ) {
      dataPlot.plotXY_profile_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression"
                              ,title,blackLine,getColumnInfo(0),
                              profilename,true,false);
    }
    else {
      dataPlot.plotXY_profile_multi(getExpValue(), dat, "Experiments", "Change in Gene Expression"
                              ,title,blackLine,getColumnInfo(0),
                              profilename,true,false);
    }
    }
  }

public void averageSelectedRows(int [] selectedRows) {
  int[] adj_selectedRow = new int [selectedRows.length];
  for (int i=0;i<selectedRows.length;i++)
    adj_selectedRow[i] = selectedRows[i] - columnInfo.length;
  float [][] data = new float[selectedRows.length][];
  for (int i=0;i<selectedRows.length;i++)
    data[i] = getRow(adj_selectedRow[i]).getData();
  float [] d = Statistics.getAverageRowData( data);
  msg.display("average of selected rows",d);
}
public void pairwiseCorrelation(int [] selectedRow, File f) {
  int[] adj_selectedRow = new int [selectedRow.length];
  for (int i=0;i<selectedRow.length;i++)
    adj_selectedRow[i] = selectedRow[i] - columnInfo.length;
     pairwiseCorrelation_selectedRows(adj_selectedRow, f);
}
public void plotRow_GDTM(int [] selectedRow,  boolean blackLine,int plotOption) {
  int[] adj_selectedRow = new int [selectedRow.length];
  for (int i=0;i<selectedRow.length;i++)
    adj_selectedRow[i] = selectedRow[i] - columnInfo.length;
     plotRow_general(adj_selectedRow,blackLine,plotOption);
}

public void plotThumbnails(int [] selectedRow, boolean withError, boolean blackLine,int plotOption) {
//  Display.display("dataSets plotThumbnails");
  int[] adj_selectedRow = new int [selectedRow.length];
  for (int i=0;i<selectedRow.length;i++)
    adj_selectedRow[i] = selectedRow[i] - columnInfo.length;

  plotThumbnails(adj_selectedRow,blackLine,plotOption);
}
public void plotThumbnails(int [] selectedRow, boolean blackLine, int _dataPlotOption) {
  if (selectedRow == null) return;
  if (dataPlot == null) {
    dataPlot = new DataPlot(msg);
  }
  dataPlotOption = _dataPlotOption;
  float [][] ydata = new float[selectedRow.length][];
  for (int i=0;i<selectedRow.length;i++)
    ydata[i] = getRow(selectedRow[i]).getData();
  float [] xdata = getExpValue();
if (_dataPlotOption == bioRepPlot && repNameIndex != null)
  dataPlot.plotThumbnails(xdata,ydata, repNameIndex,null,blackLine);
else if (_dataPlotOption == DataSet.cellLineProfilePlot && repNameIndex != null && cellLineNameIndex != null)
  dataPlot.plotThumbnails(xdata,ydata, repNameIndex,cellLineNameIndex,blackLine);
else
  dataPlot.plotThumbnails(xdata,ydata, null,null,blackLine);
dataPlot.repaint();

  }

  public void addPlotRow(float [] data, String name, boolean blackColor) {
    if (dataPlotOption==cellLineProfilePlot && cellLineProfileRow!=-1 && cellLineNameRow != -1) {
      dataPlot.plotXY_cellLineProfile_add(getExpValue(),data,null,name,cellLineNameIndex,repNameIndex,blackColor);

    }
   else if (dataPlotOption == bioRepPlot && repRow != -1) {
//     public void plotXY_replicate_add(float [] xVal, float [] yVal,String [] label,String
//                            profileName,int [][] repNameIndex) {
     dataPlot.plotXY_replicate_add(getExpValue(),data,null,name,repNameIndex,blackColor);

   }
     else {
       if (blackColor) {
         dataPlot.plotXY_add(getExpValue(),data,Color.black,null,
                            name,true,true);

       }
       else {
         dataPlot.plotXY_add(getExpValue(),data,MyColor.getGeneColorBasic(0),null,
                             name,true,true);

       }

     }
        float [] xscale =dataPlot.getPlot().getXScale();
        float [] yscale =dataPlot.getPlot().getYScale();

        float min = (float)Math.ceil(Statistics.minVal(data)-1);
        float max = (float)Math.floor(Statistics.maxVal(data)+1);
        if (yscale[0] > min) yscale[0] = min;
        if (yscale[1] < max) yscale[1] = max;
        dataPlot.setScale(xscale,yscale);
        dataPlot.repaint();
  }


  public void plotRow(float [] data, String name,boolean blackLine, int plotOption, boolean blackColor) {
    int num = data.length;
    boolean markState = true;
    boolean lineState = true;
    String title = "";
   String profileName = name;
   float [] plotData = data;
  if (dataPlot == null) {
    dataPlot = new DataPlot(msg);
  }
  dataPlotOption = plotOption;
  if (dataPlotOption==cellLineProfilePlot && cellLineNameIndex!=null && repNameIndex != null) {
      dataPlot.plotXY_cellLineProfile(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                        title, getColumnInfo(0), profileName,cellLineNameIndex,repNameIndex,blackColor);
      dataPlot.setScale(DataConversion.getScale(getExpValue()),DataConversion.getScale(plotData));
      dataPlot.repaint();
    }
    else if (dataPlotOption == bioRepPlot && repNameIndex != null) {
      dataPlot.plotXY_replicate_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression",
                       title, getColumnInfo(0),profileName,repNameIndex,blackColor);
      dataPlot.setScale(DataConversion.getScale(getExpValue()),DataConversion.getScale(plotData));
      dataPlot.repaint();
    }
    else if (dataPlotOption ==samplePlot ) {
      dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                              ,title,Color.blue,getColumnInfo(0),profileName,true,true);
      dataPlot.setScale(DataConversion.getScale(getExpValue()),DataConversion.getScale(plotData));
      dataPlot.repaint();
    }
    else {
      dataPlot.plotXY_profile(getExpValue(), plotData, "Experiments", "Change in Gene Expression"
                              ,title,Color.blue,getColumnInfo(0),profileName,true,true);
      dataPlot.setScale(DataConversion.getScale(getExpValue()),DataConversion.getScale(plotData));
      dataPlot.repaint();
    }
  }

  public void plotColumn(int [] selectedCols) {
    if (selectedCols==null)return;
    if (dataPlot == null) {
      dataPlot = new DataPlot(msg);
    }

     int num = selectedCols.length;
      float [][] plotData = new float[selectedCols.length][];
      String [] columnInfo= new String[selectedCols.length];
  for (int i=0;i<selectedCols.length;i++) {
    plotData[i] = getColumnData(selectedCols[i]);
    columnInfo[i] = getColumnInfo(0)[selectedCols[i]];
  }
      boolean markState = true;
      boolean lineState = true;
      String title = "";

      float min,max;
 //     float [] xValue = null;

//      plotData = getColumnData(selectedCols[0]);
      float [] yscale = DataConversion.getScale(plotData); //plotXY_profile_multi
      if (plotData != null ) {
        float []xValue = new float[plotData.length];
        for (int i=0;i<plotData.length;i++)
          xValue[i] = i;
//        dataPlot.plotXY(xValue, plotData, "Genes", "Change in Gene Expression",
//               title, getRowInfo(0),getColumnInfo(0)[selectedCols[0]],samplePlot,false);
        dataPlot.plotXY_profile_multi(xValue, plotData, "Genes", "Change in Gene Expression"
                                ,title,false,getColumnInfo(0),columnInfo,true,false);
      }
      else return;
//      public void plotXY_profile_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
//                          String title, boolean blackLine, String [] label,String [] profileName, boolean lineState,boolean markerState) {

      /*
      for (int i=1;i<num;i++){
        float [] data =getColumnData(selectedCols[i]);
        min = Statistics.minVal(data);
        if (min < yscale[0]) yscale[0]=min;
        max = Statistics.maxVal(data);
        if (max > yscale[1]) yscale[1]=max;
//        dataPlot.addPlotXY(xValue,data,getColumnInfo(0)[selectedCols[i]]);
        dataPlot.plotXY_add(xValue,data,MyColor.getGeneColorBasic(i),null,
                            getColumnInfo(0)[selectedCols[i]],true,false);
      }
      dataPlot.setScale(DataConversion.getScale(getExpValue()),yscale);
      dataPlot.repaint();
      */
  }
//private int numberDataRow, numberExp, numColumnGeneDescInfo, numRowExpDescInfo;
  public void sortGeneID() {
        String [] geneID = new String[getRowData().length];
       for (int i=0;i<getRowData().length;i++) {
          geneID[i] = getRowData()[i].getRowInfo(0).toString();
        }
        Sorting.quicksort(true,geneID,getRowData());


//    Sorting.quicksort(true,"GeneID",getRowData());
  }
  public void sorting(int selectedCol, boolean acending) {
    if (selectedCol < numColumnGeneDescInfo) {//getRowInfo
      String [] compareData = new String[numberDataRow];
      for (int i=0;i<numberDataRow;i++) {
        compareData[i] = (String)rowData[i].getRowInfo(selectedCol).getData();
      }
      float [] compareDataFloat = DataConversion.convertStringToFloat(compareData);
      if (compareDataFloat==null)
      Sorting.quicksort(acending,compareData,rowData);
      else
        Sorting.quicksort(!acending,compareDataFloat,rowData);
    }
    else if (selectedCol<(numColumnGeneDescInfo+numberExp)) {
      float [] compareData = new float[numberDataRow];
      int col = selectedCol-numColumnGeneDescInfo;
      for (int i=0;i<numberDataRow;i++) {
        compareData[i] = rowData[i].getAData(col);
      }
      Sorting.quicksort(!acending,compareData,rowData);
    }
  }
  public void removeMarked(int selectedCol) {
    if (selectedCol < numColumnGeneDescInfo) {
      ColorStringClassIO csc;
      int item=0;
      for (int i=0;i<numberDataRow;i++) {
        csc = rowData[i].getRowInfo(selectedCol);
        if (!csc.getColor().equals(Color.black)) {
          csc.setFlag(true);
          item++;
        }
        else
          csc.setFlag(false);
      }
      RowDataIO [] rd = new RowDataIO[item];
      removedRowData = new RowDataIO[numberDataRow-item];
      removedIndex =  new int[numberDataRow-item];
      item=0;
      int removedItem=0;
      for (int i=0;i<numberDataRow;i++) {
        csc = rowData[i].getRowInfo(selectedCol);
        if (!csc.getColor().equals(Color.black)) {
          csc.setFlag(true);
          rd[item] = rowData[i];
          item++;
        }
        else {
          removedIndex[removedItem] = i;
          removedRowData[removedItem++] = rowData[i];
          csc.setFlag(false);
        }
      }
      rowData = rd;
      numberDataRow = rowData.length;
    }
  }
  public void removeUnmarked(int selectedCol) {
    if (selectedCol < numColumnGeneDescInfo) {
      ColorStringClassIO csc;
      int item=0;
      for (int i=0;i<numberDataRow;i++) {
        csc = rowData[i].getRowInfo(selectedCol);
        if (!csc.getColor().equals(Color.red)) {
          csc.setFlag(true);
          item++;
        }
        else
          csc.setFlag(false);
      }
      RowDataIO [] rd = new RowDataIO[item];
      removedRowData = new RowDataIO[numberDataRow-item];
      removedIndex =  new int[numberDataRow-item];
      item=0;
      int removedItem=0;
      for (int i=0;i<numberDataRow;i++) {
        csc = rowData[i].getRowInfo(selectedCol);
        if (!csc.getColor().equals(Color.red)) {
          csc.setFlag(true);
          rd[item] = rowData[i];
          item++;
        }
        else {
          removedIndex[removedItem] = i;
          removedRowData[removedItem++] = rowData[i];
          csc.setFlag(false);
        }
      }
      rowData = rd;
      numberDataRow = rowData.length;
    }
  }
  public void removeInvalidData(int totalRow) {
    RowDataIO [] rd = new RowDataIO[totalRow];
    removedRowData =  new RowDataIO[numberDataRow-totalRow];
    removedIndex = new int[numberDataRow-totalRow];
    int num=0,num2=0;
    for (int i=0;i<numberDataRow;i++) {
      if (rowData[i].getValid()) {
         rd[num] = rowData[i];
         num++;
      }
      else {
        removedRowData[num2]=rowData[i];
        removedIndex[num2++]=i;
      }
    }
    numberDataRow = num;
    rowData = rd;

  }
  public void removeRow(int row) {
    RowDataIO [] rd = new RowDataIO[numberDataRow-1];
    for (int i=0;i<row;i++)
      rd[i] = rowData[i];
    for (int i=row+1;i<numberDataRow;i++)
      rd[i-1] = rowData[i];
    removedRowData =  new RowDataIO[1];
    removedIndex = new int[1];
    removedRowData[0] = rowData[row];
    removedIndex[0] = row;
    rowData = rd;
    numberDataRow = rowData.length;
  }
  public void undoRemove() {
    if (removedRowData == null || removedRowData.length == 0) return;
    int k = removedRowData.length;
    for (int i=k-1;i>=0;i++) {
      insertRow(i);
    }
    removedRowData = null;
  }
  public RowDataIO [] getRemovedRowData() {
    return removedRowData;
  }
  private void insertRow(int k) {
    RowDataIO [] rd = new RowDataIO[numberDataRow+1];
    int row = removedIndex[k];
    for (int i=0;i<row;i++)
      rd[i] = rowData[i];
    rd[row] = removedRowData[k];
    for (int i=row+1;i<numberDataRow;i++)
      rd[i] = rowData[i];
    rowData = rd;
    numberDataRow += 1;
  }
  public void setNumberGeneInEachCat(int [] num) {
    numGeneInEachCat = num;
  }
  public int [] getNumberGeneInEachCat() {
    return numGeneInEachCat;
  }
public static float [] getMergedRowData(float [] data, int [][] repIndex, float [][] decomposedData) {
  return Statistics.getMergedData( data, repIndex,decomposedData);
}

public boolean [] getMergedColumn() {
  return mergedColumnIndex;
}

public void setMergedColumn(int [][] repIndex)  {
  mergedColumnIndex =  new boolean[repIndex.length];
  for (int i=0;i<repIndex.length;i++) {
    if (repIndex[i].length == 1)
      mergedColumnIndex[i] = false;
    else
      mergedColumnIndex[i] = true;
  }
}
public void dataSetClassification() {
  if (mergedColumnIndex!=null) {
    for (int i=0;i<mergedColumnIndex.length;i++) {
      if (!mergedColumnIndex[i]) {
        float r=-1;
        int assignUnknownData = -1;
        float [] unknownData=getColumnData(i+numColumnGeneDescInfo);
        for (int j=0;j<mergedColumnIndex.length;j++) {
          if (mergedColumnIndex[j]) {
            float [] knowData = getColumnData(j+numColumnGeneDescInfo);
            float rr = Correlation.pearsonCorrelation_rValue(unknownData,knowData);
            msg.display(columnInfo[0][i]+" has correlation coefficient "+rr + " with "+columnInfo[0][j]);
            if (rr > r){
              r=rr;
              assignUnknownData = j;
            }
          }
        }
        msg.display(columnInfo[0][i]+" has Maximum correlation coefficient "+r + " with "+columnInfo[0][assignUnknownData]+"\n");
      }
    }
  }
}
public void setXTicMark(boolean b) {
  xTicMark = b;
}
public void setYTicMark(boolean b) {
 yTicMark=b;
}
/*
public int [] profileZscoreTest() {
  float [] data;
  TreeSet v = new TreeSet();
    for (int i = 0; i<getNumberDataRow() ; i++) {
       data = getRowData(i);
       if (!Statistics.ProfileData_zScoreTest(data,3,1))
              v.add(""+i);
          }

if (v.size()==0) return null;
Object [] o = v.toArray();
int [] index = new int[o.length];
for (int i=0;i<o.length;i++) {
  index[i] = Integer.parseInt((String)o[i]);
}
//msg.display("data set index",index);
 return index;

}
*/
public float [] getIntraProfileCorrelation(float [] data) {
  float [] r=null;
  if (cellLineProfileIndex != null ){
    r = intraProfileCorrelation(data,cellLineProfileIndex);
  }
  return r;
}
public float [] intraProfileCorrelation(float [][] data) {
  if (data == null) return null;
  float [] r =new float[3];
  float [] re;
  int item=0;
  for (int i=data.length-1;i>0;i--)
    item += i;
  float [] r_all = new float[item];
  int n=0;
  for (int i=0;i<data.length-1;i++) {
    for (int j=i+1;j<data.length;j++) {
          re = Correlation.pearsn(data[i],data[j]);
          r_all[n++] = re[0];
    }
  }
  r[0] = Statistics.maxVal(r_all);
  r[1] = Statistics.minVal(r_all);
  r[2] = Statistics.mean(r_all);
  return r;
}
public float [] intraProfileCorrelation(float [] data, int [][][] index) {
 // if (index == null || index.length <= 1) return null;
  float [] r;
  float [][] d = new float[index.length][];
  for (int i=0;i<index.length;i++) {
    int n= index[i].length*index[i][0].length;
    d[i] = new float[n];
    int m=0;
    for (int j=0;j<index[i].length;j++) {
      for (int k=0;k<index[i][j].length;k++)
        d[i][m++] = data[index[i][j][k]];
    }
  }
//  msg.display("cell line intra profile",d);

  r = intraProfileCorrelation(d);
  return r;
}
public String getGeneSetProperty() {
  return geneSetProperty;
}
public void setGeneSetProperty(String s) {
  geneSetProperty = s;
}
public void normalDeviates(int i, float [][] decomposedData,float [] newdata) {
   rowData[i].normalDeviates(repNameIndex,decomposedData,newdata);
}
public float [] avg_std_reserved_normalDeviates(int i) {
   return rowData[i].avg_std_reserved_normalDeviates(repNameIndex);
}
public float [] normalDeviatesReplaceZero(int i, float [][] decomposedData) {
   return rowData[i].normalDeviatesReplaceZero(repNameIndex,decomposedData);
}
public float [] std_normalization(int i, float [][] decomposedData) {
   return rowData[i].std_normalization(repNameIndex,decomposedData);
}

public float [] replaceZeroByItsAverage(int i, float [][] decomposedData) {
  return rowData[i].replaceZeroByItsAverage(repNameIndex,decomposedData);
}
public float [] exp2Conversion(int i) {
  return rowData[i].exp2Conversion();
}
public float [] exp2Conversion(int i, int col) {
  return rowData[i].exp2Conversion(col);
}
public float [] log2Conversion(int i, int col) {
  return rowData[i].log2Conversion(col);
}
//public float [] logisticConversion(int i, int col) {
//  return rowData[i].logisticConversion(col);
//}

public float [] log2Conversion(int i) {
  return rowData[i].log2Conversion();
}
public float [] logisticConversion(int i) {
  return rowData[i].logisticConversion();
}

public float [] rowDataNormalization(int i) {
  return rowData[i].rowDataNormalization();
}

public float [] rowDataSubtractionAverage(int i) {
  return rowData[i].rowDataSubtractionAverage();
}
public float [] minusOperation(int i) {
  return rowData[i].minusOperation();
}
public float [] minusOperation(int i, int col) {
  return rowData[i].minusOperation(col);
}
public float [] columnSubtractionAndAverage(int i) {
  return rowData[i].columnSubtractionAndAverage();
}
public float [] purityCorrection(int i, float [][][] invertMatrix, float [][] decomposedData) {
  return rowData[i].purityCorrection(cellLineNameIndex,invertMatrix,decomposedData);
}
public float [] cellLineCorrectionToZero(int i) {
  return rowData[i].cellLineCorrectionToZero(cellLineProfileIndex);

}
public float [] cellLineCorrectionToAverage(int i) {
  return rowData[i].cellLineCorrectionToAverage(cellLineProfileIndex);

}
public float [] cellLineCorrectionToShamAverage(int i) {
  return rowData[i].cellLineCorrectionToShamAverage(cellLineProfileIndex);

}
public float [] cellLineCorrectionToFirstShamAverage(int i) {
  return rowData[i].cellLineCorrectionToFirstShamAverage(cellLineProfileIndex);

}

public float [] cellLineNormalization(int i) {
  return rowData[i].cellLineNormalization(cellLineProfileIndex);

}

public float [] dyeSwapCorrection(int i, int [][] techRepIndex, int [] dyeSwapLabelIndex) {
  return rowData[i].dyeSwapCorrection(techRepIndex,dyeSwapLabelIndex);
}
public void columnCorrelations() {
  int colNum = columnInfo[0].length;
  float [] x,y;
  float r;
  String [] colName = this.getColumnInfo(0);
  msg.messageDisplay("\t");
  for (int i=0;i<colName.length;i++)
    if (i==colName.length-1)
      msg.messageDisplay (colName[i] + "\n");
      else
        msg.messageDisplay (colName[i] + "\t");

  for (int i=0;i<colNum;i++) {
    x = getColumnData_1(i);
    msg.messageDisplay (colName[i] + "\t");
    for (int j=0;j<colNum;j++) {
      y = getColumnData_1(j);
      r = Correlation.pearsonCorrelation_rValue(x,y);
      if (j == colNum-1)
        msg.messageDisplay (r + "\n");
      else
        msg.messageDisplay (r + "\t");
  }
}

}

public void columnStatistics() {
  int colNum = columnInfo[0].length;
  float [] avg = new float[colNum];
   float [] std = new float[colNum];
   float [] x;
   for (int i=0;i<colNum;i++){
     x = getColumnData_1(i);
     avg[i] = Statistics.mean(x);
     std[i] = Statistics.stdDev(x);
   }
   msg.display("Column average",avg);
   msg.display("Column standard deviation",std);
}
public void rowAverageAndStandardDeviation() {
  int rowNum = rowData.length;
  float [] d;
  msg.display("row average\trow std\n");
  for (int i=0;i<rowNum;i++) {
    d = rowData[i].getData();
    msg.display(rowData[i].rowInfo_toString()+"\t"+  Statistics.mean(d)+"\n");
//    msg.display(Statistics.mean(d)+"\t" + Statistics.stdDev(d));
//    msg.display("relative change",relativeChange(d));
  }
}
private float [] relativeChange(float [] d) {
  float [] relativeD = new float[d.length-1];
  for (int i=0;i<d.length-1;i++)
    relativeD[i] = (d[i+1]-d[i])/d[i];

  return relativeD;
}
public int [] getColumnIndex(String [] exName) {
  int [] index = new int[exName.length];
  for (int i=0;i<index.length;i++)
    index[i] = getColumnIndex(exName[i]);
  return index;
}
private int getColumnIndex(String name) {
  for (int i=0;i<columnInfo[0].length;i++)
    if (name.equals(columnInfo[0][i])) return i;
  return 0;
}
public int [] getRowIndex(String [] geName) {
  int [] index = new int[geName.length];
  for (int i=0;i<index.length;i++)
    index[i] = getRowIndex(geName[i]);
  return index;
}
private int getRowIndex(String name) {
  for (int i=0;i<rowData.length;i++)
    if (name.equals(rowData[i].getRowInfo(0).toString())) return i;
  return 0;
}
public void profileSimilarity_in_patterns() {
  TreeSet ts = new TreeSet();
  for (int i=0;i<rowData.length;i++) {
    ts.add(rowData[i].getRowInfo(1).toString());
  }
  msg.display("pattern number = "+ts.size());

  Object [] o =  ts.toArray();
  String [] str = new String[o.length];
  float [][] averagedProfilesInPattern = new float[o.length][];
  for (int i=0;i<o.length;i++)
    str[i] = (String)o[i];
  Vector v = new Vector();
  for (int k=0;k<o.length;k++) {
    v.removeAllElements();
  for (int i=0;i<rowData.length;i++) {
    if (str[k].equals(rowData[i].getRowInfo(1).toString()))
      v.add(rowData[i]);
  }
  if (v.size()>0)
     averagedProfilesInPattern[k] = similarity_in_pattern(str[k],v);
  }
  float average_r = Correlation.averaged_pairwised_pearsonCorrelation_rValue(averagedProfilesInPattern);
  msg.display("\nbetween cluster average pearsonCorrelation_rValue\t"+average_r+"\t");

}

private float [] similarity_in_pattern(String patternName, Vector v) {
  Object [] o = v.toArray();
  float [][] d = new float[o.length][];
  int size = ((RowDataIO)o[0]).getData().length;
  float [] avg = new float[size];
  for (int i=0;i<o.length;i++) {
    RowDataIO rd = (RowDataIO)o[i];
    d[i] = rd.getData();
    for (int j=0;j<size;j++) {
      avg[j] += d[i][j];
    }
  }
  for (int j=0;j<size;j++) {
    avg[j] /= o.length;
  }
  float average_r = Correlation.averaged_pairwised_pearsonCorrelation_rValue(d);
  msg.display("\npearsonCorrelation_rValue\t"+patternName+"\t"+average_r+"\n");
//  msg.display(patternName,average_r);

//  average_r = Correlation.averaged_pairwised_Cosine_distance(d);
//  msg.display("\nCosine_distance\n");
//  msg.display(patternName,average_r);

//  average_r = Correlation.averaged_pairwised_Eucilean_distance(d);
//  msg.display("\nEucilean_distance\n");
//  msg.display(patternName,average_r);
  return avg;
}











}