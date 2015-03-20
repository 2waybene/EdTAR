package gov.nih.niehs.EPIGseq.myutility.plot2D;


import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.*;
import myutility.misc.*;
import myutility.statistics.*;
import myutility.numerical.*;
import myutility.arrayObject.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class DataPlot {

  private ProfilePlotFrame profilePlotFrame;
  private int plotOption = -1;
  MessageBoard  msg;
  public DataPlot(MessageBoard _msg) {
    msg = _msg;
    if (profilePlotFrame == null) {
      profilePlotFrame = new ProfilePlotFrame(msg);
    }
  }
  public boolean isVisible() {
    return profilePlotFrame.isVisible();
  }
  public void setVisible(boolean b) {
    profilePlotFrame.setVisible(b);
  }

  public void plotXY_cellLineProfile(float [] xVal, float [] yVal, String xLabel, String yLabel,
                                String title, String [] label, String profileName,
                                int [][] cellNameIndex, int [][] repNameIndex, boolean blackColor) {
    profilePlotFrame.setVisible(true);
    if (cellNameIndex != null) {
    float [][] x_cellLine = FactorAnalysis.decompositionOneFactorData(xVal,cellNameIndex);
    float [][] y_cellLine = FactorAnalysis.decompositionOneFactorData(yVal,cellNameIndex);
    for (int i=0;i<yVal.length;i++) {
      if(blackColor)
      plot_celline(x_cellLine,y_cellLine,Color.black,profileName ,true,false);
      else
        plot_celline(x_cellLine,y_cellLine,MyColor.getGeneColorBasic(i),profileName,true,false);
    }
    }
if (repNameIndex != null) {
    float [][] x_replicate = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
    float [][] y_replicate = FactorAnalysis.decompositionOneFactorData(yVal,repNameIndex);
    String [][] labels = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);

//    for (int i=0;i<yVal.length;i++) {
      plot_replicate_add(x_replicate,y_replicate,blackColor,labels,profileName ,false,true);
//    }
}

    setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
    repaint();

 }

 public void plot_celline(float [][] x,float [][] y,Color c,String profileName, boolean line, boolean marker ) {
   profilePlotFrame.plotXY_general(x[0], y[0], "", "", "", c,null,profileName + " cellLine 1",line,marker);
   for (int i=1;i<y.length;i++) {
     plotXY_add(x[i], y[i],c, null,profileName + " cellLine "+(i+1),line,marker);
   }
 }
 public void plot_celline_add(float [][] x,float [][] y,Color c,String profileName, boolean line, boolean marker ) {
   for (int i=0;i<y.length;i++) {
     plotXY_add(x[i], y[i],c, null,profileName + " cellLine "+(i+1),line,marker);
   }
 }

 public void plot_replicate_add(float [][] x,float [][] y,boolean blackColor,String [][] repName,String profileName, boolean line, boolean marker ) {
   for (int i=0;i<y.length;i++) {
     if (blackColor)
       plotXY_add(x[i], y[i],Color.black, repName[i],profileName + " replicate "+(i+1),line,marker);
     else
       plotXY_add(x[i], y[i],MyColor.getGeneColorBasic(i), repName[i],profileName + " replicate "+(i+1),line,marker);
   }
 }

 public void plotXY_replicateProfile_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
                               String title, String [] label, String [] profileName,
                                int [][] repNameIndex, boolean blackColor) {
    profilePlotFrame.setVisible(true);
    if(blackColor)
      profilePlotFrame.plotXY_general(xVal, yVal[0], xLabel, yLabel, "", Color.black,null,profileName[0] ,true,false);
    else
      profilePlotFrame.plotXY_general(xVal, yVal[0], xLabel, yLabel, "", MyColor.getGeneColorBasic(0),null,profileName[0],true,false);
    for (int i=1;i<yVal.length;i++) {
      if (blackColor)
      plotXY_add(xVal, yVal[i],Color.black,label,profileName[i] ,true,false);
      else
        plotXY_add(xVal, yVal[i],MyColor.getGeneColorBasic(i),label,profileName[i] ,true,false);
    }

    float [][] x_replicate = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
    float [][][] y_replicate = new float[yVal.length][][];
    for (int i=0;i<yVal.length;i++)
        y_replicate[i] = FactorAnalysis.decompositionOneFactorData(yVal[i],repNameIndex);
    String [][] repname = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);

    for (int i=0;i<yVal.length;i++) {
      plot_replicate_add(x_replicate,y_replicate[i],blackColor,repname,profileName[i] ,false,true);
    }

    setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
    repaint();
}
public void plotXY_cellLineProfile_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
                              String title, String [] label, String [] profileName,
                              int [][] cellNameIndex, int [][] repNameIndex, boolean blackColor) {
   profilePlotFrame.setVisible(true);
   if (cellNameIndex!=null) {
   float [][] x_cellLine = FactorAnalysis.decompositionOneFactorData(xVal,cellNameIndex);
   float [][][] y_cellLine = new float[yVal.length][][];
   for (int i=0;i<y_cellLine.length;i++) {
     y_cellLine[i] = FactorAnalysis.decompositionOneFactorData(yVal[i],cellNameIndex);
   }
   if(blackColor)
   plot_celline(x_cellLine,y_cellLine[0],Color.black,profileName[0] ,true,false);
   else
     plot_celline(x_cellLine,y_cellLine[0],MyColor.getGeneColorBasic(0),profileName[0],true,false);



   for (int i=1;i<yVal.length;i++) {
     if(blackColor)
     plot_celline_add(x_cellLine,y_cellLine[i],Color.black,profileName[i] ,true,false);
     else
       plot_celline_add(x_cellLine,y_cellLine[i],MyColor.getGeneColorBasic(i),profileName[i],true,false);
   }
   }

   if (repNameIndex != null) {

   float [][] x_replicate = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
   float [][][] y_replicate = new float[yVal.length][][];
   for (int i=0;i<yVal.length;i++)
       y_replicate[i] = FactorAnalysis.decompositionOneFactorData(yVal[i],repNameIndex);
   String [][] repname = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);

   for (int i=0;i<yVal.length;i++) {
     plot_replicate_add(x_replicate,y_replicate[i],blackColor,repname,profileName[i] ,false,true);
   }
   }
   setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
   repaint();
}

 public void plotXY_cellLineProfile_add(float [] xVal, float [] yVal, String [] label, String profileName,
                               int [][] cellNameIndex, int [][] repNameIndex,boolean blackColor) {
    float [][] x = FactorAnalysis.decompositionOneFactorData(xVal,cellNameIndex);
    float [][] y = FactorAnalysis.decompositionOneFactorData(yVal,cellNameIndex);
    if (blackColor) {
      for (int i=0;i<x.length;i++) {
        plotXY_add(x[i],y[i],Color.black,null,profileName+" cellLine "+i,true,false);
      }

    }
    else {
      for (int i=0;i<x.length;i++) {
        plotXY_add(x[i],y[i],MyColor.getGeneColorBasic(i),null,profileName+" cellLine "+i,true,false);
      }

    }

    float [][] u = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
    float [][] v = FactorAnalysis.decompositionOneFactorData(yVal,repNameIndex);
    String [][] labels = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);
    if (blackColor) {
      for (int i=0;i<u.length;i++) {
        plotXY_add(u[i],v[i],Color.black,labels[i],profileName+" replicate "+i,false,true);
      }

    }
    else {
      for (int i=0;i<u.length;i++) {
//        plotXY_add(u[i],v[i],MyColor.getGeneColorBasic(i),labels[i],profileName+" replicate "+i,false,true);
        plotXY_add(u[i],v[i],MyColor.getGeneColorBasic(i),null,profileName+" replicate "+i,false,true);
      }

    }
}

public void plotXY_barPlot_averageddata_with_error_bar(float [] yVal, String xLabel, String yLabel,
                              String title, String profileName, boolean blackColor) {
      float [][] avg_and_error = new float[2][yVal.length/2];
      float [] x = new float[yVal.length/2];
      float [] y = new float[yVal.length/2];
      float [] y_error = new float[yVal.length/2];
      float [] yscale = new float[yVal.length/2];
      for (int i=0;i<x.length;i++) {
        x[i] = i+1;
        y[i] = yVal[2*i];
        y_error[i] = yVal[2*i+1];
        if (y[i] < 0)
        yscale[i] = y[i]-y_error[i];
      else
        yscale[i] = y[i]+y_error[i];
      }
      profilePlotFrame.setVisible(true);
      profilePlotFrame.plotXY_barPlot_with_error_bar(x,y,y_error, xLabel, yLabel, title, profileName);
      setScale(DataConversion.getScale(x),DataConversion.getScale(yscale));
      repaint();
}
public void plotXY_barPlot_with_error_bar(float [] xVal, float [] yVal, String xLabel, String yLabel,
                              String title, String [] label, String profileName,
                               int [][] repNameIndex, boolean blackColor) {
      float [][] avg_and_error = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(yVal,repNameIndex);
      float [] x = new float[avg_and_error.length];
      float [] y = new float[avg_and_error.length];
      float [] y_error = new float[avg_and_error.length];
      float [] yscale = new float[avg_and_error.length];
      for (int i=0;i<x.length;i++) {
        x[i] = i+1;
        y[i] = avg_and_error[i][0];
        y_error[i] = avg_and_error[i][1];
        if (y[i] < 0)
        yscale[i] = y[i]-y_error[i];
      else
        yscale[i] = y[i]+y_error[i];

      }
      // avg1, err1
      // avg2, err2
      // avg3, err3
      // ....
      profilePlotFrame.setVisible(true);

      profilePlotFrame.plotXY_barPlot_with_error_bar(x,y,y_error, xLabel, yLabel, title, profileName);
      setScale(DataConversion.getScale(x),DataConversion.getScale(yscale));
      repaint();
}

public void plotXY_barPlot_with_error_bar_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
                              String title, String [] label, String [] profileName,
                               int [][] repNameIndex, boolean blackColor) {
  float [][][] avg_and_error = new float[yVal.length][][];
  for (int i=0;i<yVal.length;i++)
      avg_and_error[i] = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(yVal[i],repNameIndex);
  float [] x = new float[avg_and_error[0].length];
  float [][] y = new float[yVal.length][avg_and_error[0].length];
  float [][] y_error = new float[yVal.length][avg_and_error[0].length];
  float [][] yscale = new float[yVal.length][avg_and_error[0].length];
  for (int j=0;j<x.length;j++)
    x[j] = j+1;
  for (int i=0;i<yVal.length;i++)
  for (int j=0;j<x.length;j++) {
    y[i][j] = avg_and_error[i][j][0];
    y_error[i][j] = avg_and_error[i][j][1];
        if (y[i][j] < 0)
        yscale[i][j]= y[i][j]-y_error[i][j];
      else
        yscale[i][j] = y[i][j]+y_error[i][j];
  }
//  y_scale[0] = Statistics.minVal(y)-Statistics.maxVal(y_error);
//  y_scale[1] = Statistics.maxVal(y)-Statistics.maxVal(y_error);

      profilePlotFrame.setVisible(true);
      profilePlotFrame.plotXY_barPlot_with_error_bar(x,y[0],y_error[0], xLabel, yLabel, title, profileName[0]);
      for(int i=1;i<yVal.length;i++)
        profilePlotFrame.plotXY_barPlot_with_error_bar_add(x,y[i],y_error[i], xLabel, yLabel, title, profileName[i]);


     setScale(DataConversion.getScale(x),DataConversion.getScale(yscale));
      repaint();

}



public void plotXY_scatterplot_averagedData_with_error_bar(float [] yVal, String xLabel, String yLabel,
                              String title,  String profileName, boolean blackColor) {
  float [][] avg_and_error = new float[2][yVal.length/2];
  float [] x = new float[yVal.length/2];
  float [] y = new float[yVal.length/2];
  float [] y_error = new float[yVal.length/2];
  float [] y_scale = new float[2];
  for (int i=0;i<x.length;i++) {
    x[i] = i+1;
    y[i] = yVal[2*i];
    y_error[i] = yVal[2*i+1];
//    if (y[i] < 0)
//    y_scale[i] = y[i]-y_error[i];
//  else
//    y_scale[i] = y[i]+y_error[i];
  }
  y_scale[0] = Statistics.minVal(y)-Statistics.maxVal(y_error);
  y_scale[1] = Statistics.maxVal(y)-Statistics.maxVal(y_error);
  // avg1, err1
  // avg2, err2
  // avg3, err3
  // ....
  profilePlotFrame.setVisible(true);
  profilePlotFrame.plotXY_scatterplot_with_error_bar(x,y,y_error, xLabel, yLabel, title, profileName,MyColor.getGeneColorBasic(0));
  setScale(DataConversion.getScale(x),y_scale);
  repaint();
}
public void plotXY_scatterplot_with_error_bar_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
                              String title, String [] columnInfo, String [] profileName,
                               int [][] repNameIndex, boolean blackColor) {

  float [][][] avg_and_error = new float[yVal.length][][];
  for (int i=0;i<yVal.length;i++)
      avg_and_error[i] = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(yVal[i],repNameIndex);
  float [] x = new float[avg_and_error[0].length];
  float [][] y = new float[yVal.length][avg_and_error[0].length];
  float [][] y_error = new float[yVal.length][avg_and_error[0].length];
  float [][] y_scale = new float[yVal.length][avg_and_error[0].length];
  for (int j=0;j<x.length;j++)
    x[j] = j+1;
  for (int i=0;i<yVal.length;i++)
  for (int j=0;j<x.length;j++) {
    y[i][j] = avg_and_error[i][j][0];
    y_error[i][j] = avg_and_error[i][j][1];
    if (y[i][j] < 0)
    y_scale[i][j] = y[i][j]-y_error[i][j];
  else
    y_scale[i][j] = y[i][j]+y_error[i][j];
  }
  // avg1, err1
  // avg2, err2
  // avg3, err3
  // ....
  profilePlotFrame.setVisible(true);
  profilePlotFrame.plotXY_scatterplot_with_error_bar(x,y[0],y_error[0], xLabel, yLabel, title, profileName[0],MyColor.getGeneColorBasic(0));
  for (int i=0;i<yVal.length;i++)
  profilePlotFrame.plotXY_scatterplot_with_error_bar_add(x,y[i],y_error[i], xLabel, yLabel, title, profileName[i],MyColor.getGeneColorBasic(i));

  setScale(DataConversion.getScale(x),DataConversion.getScale(y_scale));
  repaint();

}
public void plotXY_scatterplot_with_error_bar(float [] xVal, float [] yVal, String xLabel, String yLabel,
                              String title, String [] label, String profileName,
                               int [][] repNameIndex, boolean blackColor) {
  profilePlotFrame.setVisible(true);
  float [][] avg_and_error = Signal_to_Noise_Analysis.profileReplicateAverageAndStandardDeviation(yVal,repNameIndex);
  float [] x = new float[avg_and_error.length];
  float [] y = new float[avg_and_error.length];
  float [] y_error = new float[avg_and_error.length];
  float [] y_scale = new float[avg_and_error.length];
  for (int i=0;i<x.length;i++) {
    x[i] = i+1;
    y[i] = avg_and_error[i][0];
    y_error[i] = avg_and_error[i][1];
    if (y[i] < 0)
    y_scale[i] = y[i]-y_error[i];
  else
    y_scale[i] = y[i]+y_error[i];
  }
  profilePlotFrame.plotXY_scatterplot_with_error_bar(x,y,y_error, xLabel, yLabel, title, profileName,MyColor.getGeneColorBasic(0));
  setScale(DataConversion.getScale(x),DataConversion.getScale(y_scale));
  repaint();
}

 public void plotXY_replicate_profile(float [] xVal, float [] yVal, String xLabel, String yLabel,
                               String title, String [] label, String profileName,
                                int [][] repNameIndex, boolean blackColor) {

   profilePlotFrame.setVisible(true);
     if(blackColor)
       profilePlotFrame.plotXY_general(xVal, yVal, xLabel, yLabel, title, Color.black,null,profileName ,true,false);
     else
       profilePlotFrame.plotXY_general(xVal, yVal, xLabel, yLabel, title, MyColor.getGeneColorBasic(0),null,profileName,true,false);
if (repNameIndex!=null) {
   float [][] x_replicate = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
   float [][] y_replicate = FactorAnalysis.decompositionOneFactorData(yVal,repNameIndex);
   String [][] replicateName = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);

     plot_replicate_add(x_replicate,y_replicate,blackColor,replicateName,profileName ,false,true);
}
   setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
   repaint();
}


public void plotXY_replicate_add(float [] xVal, float [] yVal,String [] label,String
                       profileName,int [][] repNameIndex,boolean blackColor) {
//    profilePlotFrame.plotXY_add(xVal, yVal, c,label,curveName,lineState,markerState);
  if (blackColor) {
    plotXY_add(xVal,yVal,Color.black,null,profileName,true,false);

  }
  else {
    plotXY_add(xVal,yVal,MyColor.getGeneColorBasic(0),null,profileName,true,false);

  }
  float [][] x = FactorAnalysis.decompositionOneFactorData(xVal,repNameIndex);
  float [][] y = FactorAnalysis.decompositionOneFactorData(yVal,repNameIndex);
  String [][] labels = FactorAnalysis.replicateGroupDecomposition(label,repNameIndex);
  if (blackColor) {
    for (int i=0;i<x.length;i++) {
      plotXY_add(x[i],y[i],Color.black,labels[i],profileName+" replicate "+i,false,true);
    }

  }
  else {
    for (int i=0;i<x.length;i++) {
     plotXY_add(x[i],y[i],MyColor.getGeneColorBasic(i),null,profileName+" replicate "+i,false,true);
   }

  }

}


public void setXTicMarks(boolean b) {

    profilePlotFrame.setVisible(true);

  profilePlotFrame.setXTicMarks(b);

}
public void setYTicMarks(boolean b) {

    profilePlotFrame.setVisible(true);

   profilePlotFrame.setYTicMarks(b);
}
public ProfilePlotJPanel getPlot() {
  return profilePlotFrame.getPlot();
}
public void plotXY_profile(float [] xVal, float [] yVal, String xLabel, String yLabel,
                    String title, Color color, String [] label,String curveName, boolean lineState,boolean markerState) {
    profilePlotFrame.setVisible(true);
    profilePlotFrame.plotXY_general(xVal, yVal, xLabel, yLabel, title, color,label,curveName,lineState,markerState);
    setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
    repaint();
}
//dataPlot.plotXY_profile_multi(x,yvals,arrayName_x1,arrayName_y1+"/"+arrayName_y2,"X-Y plot",false,
//                    null,profilename,line,marker);

public void plotXY_profile_multi(float [] xVal, float [][] yVal, String xLabel, String yLabel,
                    String title, boolean blackLine, String [] label,String [] profileName, boolean lineState,boolean markerState) {
  profilePlotFrame.setVisible(true);
  if (blackLine)
  profilePlotFrame.plotXY_general(xVal, yVal[0], xLabel, yLabel, title, Color.black,label,profileName[0] ,lineState,markerState);
else
  profilePlotFrame.plotXY_general(xVal, yVal[0], xLabel, yLabel, title, MyColor.getGeneColorBasic(0),label,profileName[0] ,lineState,markerState);

  for (int i=1;i<yVal.length;i++)
    if (blackLine)
    plotXY_add(xVal, yVal[i], Color.black,label,profileName[i] ,lineState,markerState);
  else
    plotXY_add(xVal, yVal[i], MyColor.getGeneColorBasic(i),label,profileName[i] ,lineState,markerState);
  setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
  repaint();
}

public void plotXY_pair_wise_scatter_plot(float [][] xVal, String [] dataName,
                    String title, Color line_c, boolean lineState,Color mark_c,boolean markerState, float maxValue) {
 // Display.display("DataPlot plotXY_general");
//  Display.display("plotXY_sub");
    profilePlotFrame.setVisible(true);
    profilePlotFrame.plotXY_pair_wise_scatter_plot(xVal, dataName,  title, line_c,lineState,mark_c,markerState,maxValue);
}
public void plotThumbnails(float [] xVal, float [][] yVals,int [][] rep_index, int [][] cellLine_index,boolean blackline) {
    profilePlotFrame.setVisible(true);
    profilePlotFrame.plotThumbnails(xVal, yVals,  rep_index,cellLine_index,blackline);
}


public void plotXY_histogram(float [] xVal, float [] yVal, String xLabel, String yLabel,
                    String title, Color c, String [] label,String curveName, boolean lineState,boolean markerState) {
    profilePlotFrame.setVisible(true);
    profilePlotFrame.plotXY_histogram(xVal, yVal, xLabel, yLabel, title, c,label,curveName,lineState,markerState);
}

public void plotXY_add(float [] xVal, float [] yVal,Color c,String [] label,String
                       curveName,boolean lineState,boolean markerState) {
    profilePlotFrame.plotXY_add(xVal, yVal, c,label,curveName,lineState,markerState);
}

public void setScale(float [] xScale, float [] yScale) {
  profilePlotFrame.setScale(xScale,yScale);
}
public void repaint() {
  profilePlotFrame.repaint();
}
public void plotXY_scatter_histoContour(float [] xVal, float [] yVal, String xLabel, String yLabel) {
    profilePlotFrame.setVisible(true);
    profilePlotFrame.plotXY_scatter_histoContour(xVal, yVal, xLabel, yLabel);
    setScale(DataConversion.getScale(xVal),DataConversion.getScale(yVal));
    repaint();
}

}