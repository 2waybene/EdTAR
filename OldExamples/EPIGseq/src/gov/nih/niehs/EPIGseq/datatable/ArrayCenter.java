package gov.nih.niehs.EPIGseq.datatable;


/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
import myutility.statistics.*;

public class ArrayCenter {
  float [][] x;
  float [] test_to_eachArray, training_to_center;
  int [] selectedArrayIndex;
  int minDistIndex;
  float test_to_minDist, minDist_to_center, average_dist_trainging_to_center;
  float [] centerX;
  float test_to_centerDist;
  public ArrayCenter() {
  }
  public void setArrayData(float [][] _x) {
    x = _x;
   // System.out.println("row="+x.length+" column="+x[0].length);
    centerX = new float[x[0].length];
    for (int i=0;i<x[0].length;i++) {
      centerX[i] = 0;
      for (int j=0;j<x.length;j++)
        centerX[i] += x[j][i];
      centerX[i] /= x.length;
    }
    training_to_center =  new float[x.length];
    for (int i=0;i<x.length;i++) {
      training_to_center[i] = Correlation.Eucilean_distance(centerX,x[i]);
    }
    average_dist_trainging_to_center = Statistics.mean(training_to_center);
    test_to_eachArray = new float[x.length];
  }
  public void setSelectedArray(int [] _index) {
    selectedArrayIndex = _index;
  }
  public void distanceAnalysis(float [] y) {
    for (int i=0;i<x.length;i++)
      test_to_eachArray[i] = Correlation.Eucilean_distance(y,x[i]);
    test_to_minDist = Statistics.minVal(test_to_eachArray);
     minDistIndex = 0;
    while(test_to_eachArray[minDistIndex] != test_to_minDist) {
      minDistIndex++;
    }
    minDist_to_center = Correlation.Eucilean_distance(x[minDistIndex],centerX);
    test_to_centerDist = Correlation.Eucilean_distance(y,centerX);
  }
}