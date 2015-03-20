package gov.nih.niehs.EPIGseq.datatable;


import java.util.*;
import java.text.*;
import java.io.*;
import util.*;
import java.awt.*;
import myutility.statistics.*;
import myutility.numerical.*;
import myutility.misc.*;
import myutility.io.*;
import myutility.arrayObject.*;
import myutility.plot2D.*;

public class CompiledData {
  File dataFile,experimentDesignFile;
  String [][] loadedData,experimentDesign;
  float [][] data;
  int startCol,startRow;
  public CompiledData() {
  }
  protected void loadDataFile(String Col, String Row) {
    dataFile = FileIO.openSingleFile();
    if (dataFile==null) return;
    loadedData =  FileIO.read_txt(dataFile,"\t");
    startCol = Integer.parseInt(Col);
    startRow = Integer.parseInt(Row);
    data = new float[loadedData.length-startRow+1][loadedData[0].length-startCol+1];
    for (int i=0;i<data.length;i++) {
      data[i] = getRowData(loadedData[i+startRow-1],startCol);
    }
//    Display.display(data);
  }
  private float [] getRowData(String [] str, int startcol) {
    float [] d = new float[str.length-startcol+1];
    for (int i=0;i<d.length;i++)
      d[i] = Float.parseFloat(str[i+startcol-1]);
    return d;
  }
  protected String [][] loadExperimentDesignFile() {
    experimentDesignFile = FileIO.openSingleFile();
    if (experimentDesignFile==null) return null;
    experimentDesign =  FileIO.read_txt(experimentDesignFile,"\t");
    return experimentDesign;
  }
}