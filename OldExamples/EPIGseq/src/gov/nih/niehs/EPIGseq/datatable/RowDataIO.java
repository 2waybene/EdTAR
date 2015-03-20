package gov.nih.niehs.EPIGseq.datatable;


import java.util.*;
import java.text.*;
//import util.*;
import java.awt.*;

import myutility.plot2D.*;
import myutility.numerical.*;
import myutility.statistics.*;
import myutility.arrayObject.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

//public class RowDataIO extends MyData {
public class RowDataIO extends MyData
 {
  static DecimalFormat df = new DecimalFormat("0.00000");
  ColorStringClassIO [] rowInfo;
  int numDataItem, numDescriptionColumn;
  float [] data, originalData, intraCorrelation,anova,snr,correlation;
  float [][] t_test,f_test,paired_t_test;
  float [][] repAvgAndStd;
  float maxDiff=0,maxStd,variance, repAvg,repStd;
//  String [] extraData;
  private boolean valid = true,validErrorEstimate=true;
  private boolean [] dataFlag;

  int index=-1;
  public RowDataIO() {
  }
  public RowDataIO(RowDataIO rd) {
    setRowInfo(rd.getRowInfo());
    setData(rd.getData());
//    setExtraData(rd.getExtraData());
    setDataFlag(rd.getDataFlag());
    valid = rd.getValid();
    index = rd.getIndex();
  }

  public RowDataIO(String [] _rowInfo, float [] _rowData) {
    setRowInfo(_rowInfo);
    setData(_rowData);
  }
  public void setRowAverage(float s) {
    repAvg = s;
  }
  public float getRowAverage() {
    return repAvg;
  }
  public void setRowData(RowDataIO rd) {
    setRowInfo(rd.getRowInfo());
    setData(rd.getData());
    setValid(rd.getValid());
    setDataFlag(rd.getDataFlag());
//    setExtraData(rd.getExtraData());
    index = rd.getIndex();
  }
  public void setRowData(String [] _rowInfo, float [] _rowData) {
    setRowInfo(_rowInfo);
    setData(_rowData);
  }
  public Object getData(int col) {
    if (col < numDescriptionColumn)
      return rowInfo[col];
    else
      return ""+data[col-numDescriptionColumn];
//    else  {
//      int i = col - (numDataItem + numDescriptionColumn);
//      if (extraData!= null)
//      return extraData[i];
//      else return "";
//    }
  }
  public void setData(int col, Object obj) {
    if (col < numDescriptionColumn)
      rowInfo[col]= new ColorStringClassIO((String)obj);
    else  {
      try {
         float d = Float.parseFloat((String)obj);
         data[col-numDescriptionColumn] = d;
      }
      catch (NumberFormatException e) {

      }
    }
//    else  {
//      int i = col - (numDataItem + numDescriptionColumn);
//      if (extraData!= null)
//           extraData[i] = (String)obj;
//    }
  }
//public void setExtraData(String [] _extraData) {
//  extraData = _extraData;
//}
//public void setExtraData(int i,String _extraData) {
//  extraData[i] = _extraData;
//}

//public String [] getExtraData() {
//  return extraData;
//}
//public String getExtraData(int i) {
//  return extraData[i];
//}

  public void setGeneDataWithDecpWithEmptyTab(StringTokenizer p,
      int numInfoColumn,boolean log2convert,String delimit) {
    String [] str = DataConversion.convertStringTokebizerToArray(p,delimit);
    if (numInfoColumn < str.length) {
      String [] rinfo = new String[numInfoColumn];
      for (int i=0;i<numInfoColumn;i++)
        rinfo[i] = str[i];
      setRowInfo(rinfo);
      String [] da = new String[str.length-numInfoColumn];
      for (int i=numInfoColumn;i<str.length;i++)
        da[i-numInfoColumn] = str[i];
      setData(da,log2convert);
    }
  }


  public void replicateSmooth(int [][] repIndex) {
    float [] thisData,testData;
    float avg,std;
    boolean [] valid;
    int invalidNum;
    for (int i=0;i<repIndex.length;i++) {
      thisData = new float[repIndex[i].length];
      valid = new boolean[repIndex[i].length];
      for (int j=0;j<repIndex[i].length;j++) {
        thisData[j] = data[repIndex[i][j]];
        valid[j] = true;
      }
      avg = Statistics.mean(thisData);
      std = Statistics.stdDev(thisData);
      invalidNum = 0;
      for (int j=0;j<repIndex[i].length;j++) {
        if (Math.abs(thisData[j] - avg) > 1.2*std) {
          valid[j] = false;
          invalidNum++;
        }
      }
      if (invalidNum != 0) {
        testData =  new float[repIndex[i].length-invalidNum];
        int n=0;
        for (int j=0;j<repIndex[i].length;j++) {
          if (valid[j]) {
            testData[n++] = thisData[j];
          }
        }
        avg = Statistics.mean(testData);
        for (int j=0;j<repIndex[i].length;j++) {
          if (!valid[j]) {
            data[repIndex[i][j]] = avg;
          }
        }

      }
    }
  }
  private void smooth(int SmoothNum,float [] d) {
    float [] tem = new float[d.length-SmoothNum+1];
    for (int i=0;i<tem.length;i++)
      tem[i] = (d[i] + d[i+1] + d[i+2])/SmoothNum;
    int start = SmoothNum/2;
    int end = d.length - start;
    int j=0;
    for (int i=start;i<end;i++) {
      d[i] = tem[j++];
    }
  }
  public void setDataFlag(boolean [] d) {
    if (d==null) return;
    dataFlag = new boolean[d.length];
    for (int i=0;i<d.length;i++)
       dataFlag[i] = d[i];
  }
  public void setDataFlag(int i,boolean _dataFlag) {
    if (i >= 0 && i <dataFlag.length)
      dataFlag[i] = _dataFlag;
  }

  public boolean [] getDataFlag() {
    return dataFlag;
  }

  public boolean getDataFlag(int i) {
    if (dataFlag == null) return false;
    else
    return dataFlag[i];
  }
  public float [][] getGeneDataInvalid(float [] x) {
    float [][] invalidData = null;
    if (dataFlag != null && dataFlag.length == x.length) {
      int num=0;
      for (int i=0;i<dataFlag.length;i++) {
        if(!dataFlag[i]) num++;
      }
      if (num == 0) return null;
      invalidData = new float[2][num];
      num=0;
      for (int i=0;i<dataFlag.length;i++) {
        if(!dataFlag[i]){
          invalidData[0][num] = x[i];
          invalidData[1][num] = data[i];
          num++;
        }
      }
    }
    return invalidData;
  }
  public float [] getData() {
    return data;
  }
  public float getAData(int i) {
    return data[i];
  }

  public void setData(String [] x,boolean log2convert){
    numDataItem = x.length;
    data =  new float[x.length];
    for (int i=0;i<x.length;i++)
      try {
        data[i] = Float.parseFloat(x[i]);
      }
      catch(NumberFormatException e) {

      }
      if (log2convert) {
        for (int i=0;i<data.length;i++) {
             data[i] =   (float)Math.log(data[i])/ConstantValue.log2;
        }

      }
  }

  public void setData(float [] x) {
//    originalData = data;
    numDataItem = x.length;
    data =  x;
//    for (int i=0;i<x.length;i++)
//    data[i] = x[i];

  }
  public void setAData(int i,float x) {
    data[i] = x;
  }
   public void setRowInfo(ColorStringClassIO [] str) {
     if (str == null) {
       rowInfo = null;
       return;
     }
     numDescriptionColumn = str.length;
     rowInfo = new ColorStringClassIO[str.length];
     for (int i=0;i<str.length;i++)
        rowInfo[i] = new ColorStringClassIO(str[i].toString());

   }
   public void setRowInfoColor(int i, Color c) {
     if (rowInfo[i] != null)
     rowInfo[i].setColor(c);
   }
   public void setRowInfo(String [] str) {
     if (str == null) {
       rowInfo = null;
       return;
     }
     numDescriptionColumn = str.length;
     rowInfo = new ColorStringClassIO[str.length];
     for (int i=0;i<str.length;i++)
        rowInfo[i] = new ColorStringClassIO(str[i]);

   }
   public void setRowInfo(String str, int i) {
     if (str == null) {
       rowInfo[i] = null;
       return;
     }
       rowInfo[i] = new ColorStringClassIO(str);
   }

  public ColorStringClassIO [] getRowInfo() {
    return rowInfo;
  }
  public String [] getRowInfoString() {
    if (rowInfo == null) return null;
    String [] str = new String[rowInfo.length];
    for (int i=0;i<rowInfo.length;i++)
      str[i] = rowInfo[i].toString();
    return str;
  }
  public ColorStringClassIO getRowInfo(int i) {
    return rowInfo[i];
  }

  public int getExpNumber() {
    return numDataItem;
  }
  public void setExpNumber(int i) {
    numDataItem=i;
  }
  public int getNumDescriptionColumn() {
    return numDescriptionColumn;
  }
  public void setNumDescriptionColumn(int i) {
    numDescriptionColumn = i;
  }
  public boolean getValid() {
    return valid;
  }
  public void setValid(boolean b) {
    valid = b;
  }
  public void setValidErrorEstimate(boolean b) {
    validErrorEstimate = b;
  }
  public boolean getValidErrorEstimate() {
    return validErrorEstimate;
  }
/*  public boolean compareTo(boolean ascending,String attribute, RowDataIO b) {
    if (attribute.equals("2ndColumn"))
      return ascending ? getRowInfo(1).compareTo((String)b.getRowInfo(1)) > 0
      : getRowInfo(1).compareTo(b.getRowInfo(1)) <= 0;
    else if (attribute.equals("3rdColumn"))
      return ascending ? getRowInfo(2).compareTo((String)b.getRowInfo(2)) > 0
      : getRowInfo(2).compareTo(b.getRowInfo(2)) <= 0;
    else
      return ascending ? getRowInfo(0).compareTo((String)b.getRowInfo(0)) > 0
      : getRowInfo(0).compareTo(b.getRowInfo(0)) <= 0;
  }
  */
  public String rowInfo_toString() {
    StringBuffer s = new StringBuffer();
    for (int i=0;i<numDescriptionColumn;i++)
      s.append(rowInfo[i]+"\t");
    return s.toString();

  }
  public String toString() {
    StringBuffer s = new StringBuffer();
    for (int i=0;i<numDescriptionColumn;i++)
      s.append(rowInfo[i]+"\t");
    for (int i=0;i<numDataItem;i++) {
      if (i == numDataItem-1) {
        s.append(data[i]+"\n");
      }
      else {
        s.append(data[i]+"\t");
      }
    }
//      s.append("\n");

    return s.toString();
  }
  public void setIndex(int k) {
    index = k;
  }
  public int getIndex() {
    return index;
  }
  public void plotData(DataPlot dataPlot,float [] x) {

  }
  public void addPlotXY(DataPlot dataPlot,float [] x) {

  }
  public void setIntraCorrelation(float [] r) {
    intraCorrelation = r;
  }
  public float [] getIntraCorrelation() {
    return intraCorrelation;
  }
  public boolean zScoreTest(float zscore,int [][] repIndex) {
    boolean zeroTest = zeroTest(data,repIndex);
    if (!zeroTest) {
//      zeroCount++;
      return false;
    }
    float max = Statistics.maxVal(data);
    float min = Statistics.minVal(data);
    int num=0;
    for (int i=0;i<data.length;i++) {
      if (data[i] != max && data[i] != min)
        num++;
    }
    if (num <= 1) return false;
    float [] trimData = new float[num];
    num = 0;
    for (int i=0;i<data.length;i++) {
      if (data[i] != max && data[i] != min)
        trimData[num++] = data[i];
    }
    float avg = Statistics.mean(trimData);
    float stdDev = Statistics.stdDev(trimData);
    for (int i=0;i<data.length;i++) {
      if  (Math.abs(data[i] - avg) > zscore*stdDev) {
 //       zscoreCount++;
        return false;
      }
    }
    return true;
  }
  private boolean zeroTest( float [] data, int [][] repIndex) {
//    int [][] repIndex = getRepNameIndex();
    boolean [] t;
    int num=0;
    for (int i=0;i<data.length;i++) {
      if (data[i] == 0) num++;
    }
    if (num>0.1*data.length) return false;
    if (repIndex == null) return true;
    for (int i=0;i<repIndex.length;i++) {
      t = new boolean[repIndex[i].length];
      num = 0;
      for (int j=0;j<repIndex[i].length;j++) {
         if (data[repIndex[i][j]]==0) {
              num++;
           }
      }
      if ((float)num/repIndex[i].length >= 0.5) {
        return false;
      }
      else {
        num = 0;
        float newData = 0;
        for (int j=0;j<repIndex[i].length;j++) {
           if (data[repIndex[i][j]] != 0) {
                num++;
                newData += data[repIndex[i][j]];
             }
        }
        newData /= num;
        for (int j=0;j<repIndex[i].length;j++) {
           if (data[repIndex[i][j]] == 0) {
                data[repIndex[i][j]] = newData;
             }
        }
      }
    }
     return true;
  }
  public void setRepAverageAndStd(float [][] s) {
    repAvgAndStd = s;
    float [] d = new float[s.length];
    maxStd = 0;
    for (int i=0;i<s.length;i++) {
      d[i] = s[i][0];
      if (s[i][1] > maxStd) maxStd = s[i][1];
    }
    float min = Statistics.minVal(d);
    float max = Statistics.maxVal(d);
    repAvg = Statistics.mean(d);
    repStd = Statistics.stdDev(d);
    maxDiff = max-min;
    variance = maxDiff/maxStd;
  }
  public void set_t_test(float [][] s) {
    t_test = s;
  }
  public float [][] get_t_test() {
    return t_test;
  }
  public void set_f_test(float [][] s) {
    f_test = s;
  }
  public float [][] get_f_test() {
    return f_test;
  }
  public float [][] get_paired_t_test() {
    return paired_t_test;
  }
  public void set_paired_t_test(float [][] s) {
    paired_t_test = s;
  }
  public void set_ANOVA(float [] s) {
    anova = s;
  }
  public float [] get_ANOVA() {
    return anova;
  }
  public void set_SNR(float [] s) {
    snr = s;
  }
  public float [] get_SNR() {
    return snr;
  }
  public float [] get_Correlation() {
    return correlation;
  }
  public void set_Correlation(float [] s) {
     correlation=s;
  }

  public float [][] getRepAverageAndStd() {
    return repAvgAndStd;
  }
  public String repAvgAndStd_toString() {
    String s = rowInfo_toString();
    float t;
    for (int i=0;i<repAvgAndStd.length;i++) {
      if (repAvgAndStd[i][0] == 0)
        t = 0;
      else
        t = 100*repAvgAndStd[i][1]/repAvgAndStd[i][0];
      if (i == repAvgAndStd.length-1)
      s += repAvgAndStd[i][0] + "\t" + repAvgAndStd[i][1] +  "\t" + t;
      else
        s += repAvgAndStd[i][0] + "\t" + repAvgAndStd[i][1] +  "\t" + t+"\t";

    }
 //   s+=    maxDiff+"\t"+maxStd+"\t"+variance+"\t"+repAvg+"\t"+repStd+"\t"+repAvg/repStd;
    if (intraCorrelation == null)
      s+="\n";
    else {
      s+= "\t"+intraCorrelation[0]+"\t"+intraCorrelation[1]+"\t"+intraCorrelation[2]+"\n";
    }
    return s;
  }
  public String repAvg_toString() {
    String s = rowInfo_toString();
    float t;
    for (int i=0;i<repAvgAndStd.length;i++) {
      if (i == repAvgAndStd.length-1)
      s += repAvgAndStd[i][0] ;
      else
        s += repAvgAndStd[i][0] + "\t"  ;

    }
    if (intraCorrelation == null)
      s+="\n";
    else {
      s+= "\t"+intraCorrelation[0]+"\t"+intraCorrelation[1]+"\t"+intraCorrelation[2]+"\n";
    }
    return s;
  }
public void normalDeviates(int [][] repNameIndex, float [][] decomposedData, float [] deviateData) {
  Simulation.normalDeviates(data,repNameIndex,decomposedData,deviateData);
//  return data;
}
public float [] subtractReplicateAverage(int [][] repNameIndex, float [][] decomposedData) {
  data = FactorAnalysis.subtractReplicateAverage(data,repNameIndex,decomposedData);
  return data;
}
public float [] get_equal_between_group_average(int [][] repNameIndex, float [][] decomposedData) {
  data = FactorAnalysis.get_equal_between_group_average(data,repNameIndex,decomposedData);
  return data;
}

public float [] subtractFirstReplicateAverage(int [][] repNameIndex, float [][] decomposedData) {
  data = FactorAnalysis.subtractFirstReplicateAverage(data,repNameIndex,decomposedData);
  return data;
}


public float [] avg_std_reserved_normalDeviates(int [][] repNameIndex) {
  data = Simulation.avg_std_reserved_normalDeviates(data,repNameIndex);
  return data;
}
public float [] normalDeviatesReplaceZero(int [][] repNameIndex, float [][] decomposedData) {
  data = Simulation.normalDeviatesReplaceZero(data,repNameIndex,decomposedData);
  return data;
}
public float [] std_normalization(int [][] repNameIndex, float [][] decomposedData) {
  data = FactorAnalysis.std_normalization(data,repNameIndex,decomposedData);
  return data;
}

  public float [] replaceZeroByItsAverage(int [][] repNameIndex, float [][] decomposedData) {
    data = FactorAnalysis.replaceZeroByItsAverage(data,repNameIndex,decomposedData);
   return data;

}
public float [] dyeSwapCorrection(int [][] techRepIndex, int [] dyeSwapLabelIndex) {
  data = FactorAnalysis.dyeSwapCorrectionTechRep(data,techRepIndex,dyeSwapLabelIndex);
  return data;
 }
 public String dyeSwapMergedData(int [][] techRepIndex) {
   return dyeSwapMergedData_toString(FactorAnalysis.dyeSwapMergedData(data,techRepIndex));
  }
  public String dyeSwapMergedData_toString(float [] data) {
    String s = rowInfo_toString();
    for (int i=0;i<data.length;i++)
      if (i == data.length-1)
        s += data[i]+"\n";
        else
      s += data[i] + "\t";
    return s;
   }

public float []  exp2Conversion() {
  DataConversion.exp2Conversion(data);
   return data;
 }
 public float []  exp2Conversion(int col) {
   data[col] = DataConversion.exp2Conversion(data[col]);
    return data;
  }
 public float []  rowDataSubtractionAverage() {
   DataConversion.rowDataSubtractionAverage(data);
    return data;
  }
  public float []  rowDataNormalization() {
     DataConversion.rowDataNormalization(data);
     return data;
   }
  public float []  log2Conversion() {
    DataConversion.log2Conversion(data);
     return data;
   }
   public float []  logisticConversion() {
     DataConversion.logisticConversion(data);
//     DataConversion.log2Conversion(data);
      return data;
    }

  public float []  log2Conversion(int col) {
    data[col] = DataConversion.log2Conversion(data[col]);
     return data;
   }
  public float []  minusOperation() {
    DataConversion.minusOperation(data);
     return data;
   }
   public float []  minusOperation(int col) {
     data[col] = -data[col];
      return data;
    }
   public float []  columnSubtractionAndAverage() {
      DataConversion.columnSubtractionAndAverage(data);
      return data;
    }
public float []  cellLineCorrectionToZero(int [][][] cellLineProfileIndex) {
//  data = DataConversion.columnSubtractionAndAverage(data);
  float [] fooCy3, fooCy5,foo;
  int numCellLine = cellLineProfileIndex.length;
  for (int i=0;i<numCellLine;i++) {
    int num = cellLineProfileIndex[i].length;
    int item= cellLineProfileIndex[i][0].length;
    float avg=0;
    for (int j=0;j<item;j++) {
      avg += data[cellLineProfileIndex[i][0][j]];
    }
    avg /= item;
    for (int j=0;j<num;j++) {
      for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
        data[cellLineProfileIndex[i][j][k]] -= avg;
      }
    }
  }
  return data;
}
public float []  cellLineCorrectionToShamAverage(int [][][] cellLineProfileIndex) {
  float [] fooCy3, fooCy5,foo;
  int numCellLine =   cellLineProfileIndex.length;
  float grandAvg=0;
  for (int i=0;i<numCellLine;i++) {
    int num = cellLineProfileIndex[i].length;
    int item= cellLineProfileIndex[i][0].length;
    float avg=0;
    for (int j=0;j<item;j++) {
      avg += data[cellLineProfileIndex[i][0][j]];
    }
    avg /= item;
    grandAvg += avg;
  }
  if (numCellLine != 0)
    grandAvg /= numCellLine;
  for (int i=0;i<numCellLine;i++) {
    int num = cellLineProfileIndex[i].length;
    int item= cellLineProfileIndex[i][0].length;
    float avg=0;
    for (int j=0;j<item;j++) {
      avg += data[cellLineProfileIndex[i][0][j]];
    }
    avg /= item;
    avg -= grandAvg;
    for (int j=0;j<num;j++) {
      for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
        data[cellLineProfileIndex[i][j][k]] -= avg;
      }
    }
  }
  return data;

}
public float []  cellLineCorrectionToFirstShamAverage(int [][][] cellLineProfileIndex) {
  float [] fooCy3, fooCy5,foo;
  int numCellLine =   cellLineProfileIndex.length;
  float firstAvg=0;
//  float avg=0;
  for (int i=0;i<1;i++) {
    int num = cellLineProfileIndex[i].length;
    int item= cellLineProfileIndex[i][0].length;
    for (int j=0;j<item;j++) {
      firstAvg += data[cellLineProfileIndex[i][0][j]];
    }
    firstAvg /= item;
//    grandAvg += avg;
  }
//  if (numCellLine != 0)
//    grandAvg = avg;
  for (int i=1;i<numCellLine;i++) {
    int num = cellLineProfileIndex[i].length;
    int item= cellLineProfileIndex[i][0].length;
    float avg=0;
    for (int j=0;j<item;j++) {
      avg += data[cellLineProfileIndex[i][0][j]];
    }
    avg /= item;
    avg -= firstAvg;
    for (int j=0;j<num;j++) {
      for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
        data[cellLineProfileIndex[i][j][k]] -= avg;
      }
    }
  }
  return data;

}

public float []  cellLineCorrectionToAverage(int [][][] cellLineProfileIndex) {
  float [] fooCy3, fooCy5,foo;
  int numCellLine =   cellLineProfileIndex.length;
  float grandAvg=0;
  float [][] d = Statistics.getCellLineIndexData(data,cellLineProfileIndex);
  float [] avg = new float[d.length];
  for (int i=0;i<numCellLine;i++) {
    avg[i] = Statistics.mean(d[i]);
    grandAvg += avg[i];
  }
  if (numCellLine != 0)
    grandAvg /= numCellLine;
  for (int i=0;i<numCellLine;i++) {
    int num = cellLineProfileIndex[i].length;
    avg[i] -= grandAvg;
    for (int j=0;j<num;j++) {
      for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
        data[cellLineProfileIndex[i][j][k]] -= avg[i];
      }
    }
  }
  return data;

}
  public float []  cellLineNormalization(int [][][] cellLineProfileIndex) {
    float [] fooCy3, fooCy5,foo;
    int numCellLine =   cellLineProfileIndex.length;
    float [] max = new float[cellLineProfileIndex[0].length];
    float [] prev_Data = new float[data.length];
    for (int i=0;i<data.length;i++)
      prev_Data[i]=data[i];
    max[0]=0;
    for (int i=0;i<numCellLine;i++) {
      int num = cellLineProfileIndex[i].length;
      int item= cellLineProfileIndex[i][0].length;
      float avg=0;
      for (int j=0;j<item;j++) {
        avg += data[cellLineProfileIndex[i][0][j]];
      }
      avg /= item;
      max[0] += avg;
    }
    max[0] /= numCellLine;

    for (int i=0;i<numCellLine;i++) {
      int num = cellLineProfileIndex[i].length;
      int item= cellLineProfileIndex[i][0].length;
      float avg=0;
      for (int j=0;j<item;j++) {
        avg += data[cellLineProfileIndex[i][0][j]];
      }
      avg /= item;
      avg -= max[0];
      for (int j=0;j<num;j++) {
        for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
          data[cellLineProfileIndex[i][j][k]] -= avg;
        }
      }
    }

    float grandAvg2 = 0, grandAvgMax=0;
    int n = cellLineProfileIndex[0].length, indexMax=0;
    for (int k=1;k<n;k++) {
      for (int i=0;i<numCellLine;i++) {
        int num = cellLineProfileIndex[i].length;
        int item= cellLineProfileIndex[i][k].length;
        float avg=0;
        for (int j=0;j<item;j++) {
          avg += data[cellLineProfileIndex[i][k][j]];
        }
        avg /= item;
        grandAvg2 += avg;
      }
      grandAvg2 /= numCellLine;
      if (Math.abs(grandAvg2-max[0]) > grandAvgMax) {
        grandAvgMax = Math.abs(grandAvg2-max[0]);
        max[k] = grandAvg2;
        indexMax = k;
      }
    }
    float maxDiff = max[indexMax]-max[0];
    for (int i=0;i<numCellLine;i++) {

      int num = cellLineProfileIndex[i].length;
      int item= cellLineProfileIndex[i][indexMax].length;
      float avg=0;
      for (int j=0;j<item;j++) {
        avg += data[cellLineProfileIndex[i][indexMax][j]];
      }
      avg /= item;
      avg -= max[indexMax];

      for (int j=1;j<num;j++) {
        if (j == indexMax) {
          for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
            data[cellLineProfileIndex[i][j][k]] -= avg;
          }
        }
        else {

          for (int k=0;k<cellLineProfileIndex[i][j].length;k++) {
            data[cellLineProfileIndex[i][j][k]] = maxDiff*(data[cellLineProfileIndex[i][j][k]] - max[0])
                    /(prev_Data[cellLineProfileIndex[i][indexMax][k]]-max[0]) + max[0];
          }

        }
      }

    }

//  msg.display("normalized ",_data);

    return data;

  }
  public float [] purityCorrection(int [][] cellLineNameIndex,float [][][] matrix, float [][] decomposedData) {
    FactorAnalysis.decompositionOneFactorData(data,cellLineNameIndex,decomposedData);
    float [][] dd = new float[decomposedData.length][decomposedData[0].length];
    for (int i=0;i<decomposedData.length;i++) {
      dd[i] = matrixTimeColumn(decomposedData[i],matrix[i]);
    }
    data = FactorAnalysis.assembleBioRepData(dd,cellLineNameIndex);
    return data;
  }
  public float [] purity_linearCombination(int [][] cellLineNameIndex,float [][] matrix, float [][] decomposedData) {
    FactorAnalysis.decompositionOneFactorData(data,cellLineNameIndex,decomposedData);
    float [] dd = new float[decomposedData.length*matrix.length];
    int k=0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<matrix.length;j++)
        dd[k++] = matrixTimeColumn(matrix[j],decomposedData[i]); // i cell line, j row
    }
    return dd;
  }
  private float [] matrixTimeColumn(float [] a, float [][] b) {
    float [] c = new float[b.length];
    for (int i=0;i<b.length;i++)
      c[i] = matrixTimeColumn(a,b[i]);
    return c;
  }
  private float matrixTimeColumn(float [] a, float [] b) {
    float c=0;
    for (int i=0;i<a.length;i++)
      c+=a[i]*b[i];
    return c;
  }
/*
  private float [] putTogetherCellLineSubData(float [][] d, int [][] index) {
    int item=0;
    for (int i=0;i<d.length;i++)
      item += d[i].length;
    float [] data = new float[item];
//  int k=0;
    for (int i=0;i<d.length;i++) {
      for (int j=0;j<d[i].length;j++)
        data[index[i][j]] = d[i][j];
    }
    return data;
  }
*/
  private float [] getCellLineSubData(float [] d,int [] index) {
  int item=index.length;
  float [] data = new float[item];
  int n=0;
  for (int i=0;i<index.length;i++)
    data[i] = d[index[i]];
  return data;
}

}