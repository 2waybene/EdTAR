package gov.nih.niehs.EPIGseq.myutility.numerical;


/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
import java.util.*;
import java.text.*;
//import io.*;
import myutility.statistics.*;
import myutility.misc.*;
import gov.nih.niehs.EPIGseq.myutility.numerical.*;
import myutility.io.*;
//import java.util.*;
//import java.text.*;
import java.io.*;
import util.*;
//import java.awt.*;

public class Algorithms {
  int [][] c_table,b_table;
  MessageBoard msg;
//  String lcsString;
  public Algorithms(MessageBoard _msg) {
    if (_msg == null) {
      msg = new MessageBoard();
      msg.setVisible(true);
    }
    else
    msg = _msg;
  }
  public static void main(String[] args) {
    Algorithms a1 = new Algorithms(new MessageBoard());
    float [] x = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f,1.0f};
    float [] y = {0.01f,0.04f,0.09f,0.16f,0.25f,0.36f,0.49f,0.64f,0.81f,1.0f};

    int size = x.length;
    float [] splineY= new float[size];
    float [] u = new float[size];
    float [] firstDerivative = new float[size];
    float [] secondDerivative = new float[size];

//   float [] secondDerivative = float[size];
//   public static void spline(float [] x, float [] y,float yp1, float ypn, float [] u,float [] secondDerivative) {
//   public static float [][] get_Spline_first_and_second_derivative(float [] x, float [] y,float []u,float [] _secondDerivative, MessageBoard msg) {


    NumericalAnalysis.get_Spline_first_and_second_derivative(x,y,u,splineY,new MessageBoard(),
        firstDerivative,secondDerivative);
/*
    String [] a ={"a","b","c","b","d","a","b"};
    String [] b ={"b","d","c","a","b","a"};
    String [] str = a1.Global_sequence_match(a,b);
    if (str != null) {
      Display.display("LCS list:");
    for (int i=0;i<str.length;i++)
      Display.display(str[i]);
    }
//    int [] a = {1,2,3,2,4,1,2};
//    int [] b = {2,4,3,1,2,1};
//    a1.LCS_Length(a,b);
    */
  }
  public int [][] getC_Table() {
    return c_table;
  }
  public int [][] getB_Table() {
    return b_table;
  }
  public void Global_sequence_match(String A, String B) {
//    lcsString = "";
    char [] a = A.toCharArray();
    char [] b = B.toCharArray();
    LCS_Length(a,b);
//    Display.display("lcs string "+lcsString);
  }
  public String [] Global_sequence_match(String [] A, String [] B) {
//    lcsString = "";
    Vector v= new Vector();
    LCS_Length(A,B,v);
    if (v.size()>0) {
//    Display.display("vector size",v.size());
    Object [] o = v.toArray();
    String [] str = new String[o.length];
    for (int k=0;k<o.length;k++) {
      str[k] = (String)o[k];
//      Display.display("from vector "+k+" " +str[k]);
    }
    return str;

    }
    else
    return null;
//    Display.display("lcs string "+lcsString);
  }
  public String [] Global_sequence_match(char [] A, char [] B) {
//    lcsString = "";
    Vector v= new Vector();
    LCS_Length(A,B,v);
    if (v.size()>0) {
//    Display.display("vector size",v.size());
    Object [] o = v.toArray();
    String [] str = new String[o.length];
    for (int k=0;k<o.length;k++) {
      str[k] = (String)o[k];
//      Display.display("from vector "+k+" " +str[k]);
    }
    return str;

    }
    else
    return null;
//    Display.display("lcs string "+lcsString);
  }

  public void LCS_Length(String [] x, String [] y,Vector v) {
    /*
    Longest common subsequence - a recursive solution
    two sequences x[m] and y[n] as inputs
    c_table is the length of an LCS of x and y.
    c_table[m][n] contains the LCS length
    b_table is an simplified construction of an optimal solution.
    b_table[i][j] = 0, diaganal arrow
    b_table[i][j] = 1, up-arrow
    b_table[i][j] = -1, left-arrow
    */
    int m=x.length;
    int n=y.length;
    c_table = new int[m+1][n+1];
    b_table = new int[m][n];
    for (int i=0;i<=m;i++)
      c_table[i][0] = 0;
    for (int j=0;j<=n;j++)
      c_table[0][j] = 0;
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        b_table[i][j] = 0;
      }
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
//        if (x[i] == y[j]) {
        if (x[i].compareTo(y[j]) == 0) {
          c_table[i+1][j+1] = c_table[i][j] +1;
          b_table[i][j] = 0;
        }
        else if (c_table[i][j+1] >= c_table[i+1][j]) {
          c_table[i+1][j+1] = c_table[i][j+1];
          b_table[i][j] = 1;
        }
        else {
          c_table[i+1][j+1] = c_table[i+1][j];
          b_table[i][j] = -1;
        }
      }
//      Display.display("c_table",c_table);
//      Display.display("b_table",b_table);
      print_LCS(b_table,x,m-1,n-1,v);
  }
  public void LCS_Length(char [] x, char [] y,Vector v) {
    /*
    Longest common subsequence - a recursive solution
    two sequences x[m] and y[n] as inputs
    c_table is the length of an LCS of x and y.
    c_table[m][n] contains the LCS length
    b_table is an simplified construction of an optimal solution.
    b_table[i][j] = 0, diaganal arrow
    b_table[i][j] = 1, up-arrow
    b_table[i][j] = -1, left-arrow
    */
    int m=x.length;
    int n=y.length;
    c_table = new int[m+1][n+1];
    b_table = new int[m][n];
    for (int i=0;i<=m;i++)
      c_table[i][0] = 0;
    for (int j=0;j<=n;j++)
      c_table[0][j] = 0;
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        b_table[i][j] = 0;
      }
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        if (x[i] == y[j]) {
//        if (x[i].compareTo(y[j]) == 0) {
          c_table[i+1][j+1] = c_table[i][j] +1;
          b_table[i][j] = 0;
        }
        else if (c_table[i][j+1] >= c_table[i+1][j]) {
          c_table[i+1][j+1] = c_table[i][j+1];
          b_table[i][j] = 1;
        }
        else {
          c_table[i+1][j+1] = c_table[i+1][j];
          b_table[i][j] = -1;
        }
      }
//      Display.display("c_table",c_table);
//      Display.display("b_table",b_table);
      print_LCS(b_table,x,m-1,n-1,v);
  }
  public void LCS_Length(char [] x, char [] y) {
    /*
    Longest common subsequence - a recursive solution
    two sequences x[m] and y[n] as inputs
    c_table is the length of an LCS of x and y.
    c_table[m][n] contains the LCS length
    b_table is an simplified construction of an optimal solution.
    b_table[i][j] = 0, diaganal arrow
    b_table[i][j] = 1, up-arrow
    b_table[i][j] = -1, left-arrow
    */
    int m=x.length;
    int n=y.length;
    c_table = new int[m+1][n+1];
    b_table = new int[m][n];
    for (int i=0;i<=m;i++)
      c_table[i][0] = 0;
    for (int j=0;j<=n;j++)
      c_table[0][j] = 0;
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        b_table[i][j] = 0;
      }
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        if (x[i] == y[j]) {
          c_table[i+1][j+1] = c_table[i][j] +1;
          b_table[i][j] = 0;
        }
        else if (c_table[i][j+1] >= c_table[i+1][j]) {
          c_table[i+1][j+1] = c_table[i][j+1];
          b_table[i][j] = 1;
        }
        else {
          c_table[i+1][j+1] = c_table[i+1][j];
          b_table[i][j] = -1;
        }
      }
//      Display.display("c_table",c_table);
//      Display.display("b_table",b_table);
      print_LCS(b_table,x,m-1,n-1);
  }

  public void LCS_Length(int [] x, int [] y) {
    /*
    Longest common subsequence - a recursive solution
    two sequences x[m] and y[n] as inputs
    c_table is the length of an LCS of x and y.
    c_table[m][n] contains the LCS length
    b_table is an simplified construction of an optimal solution.
    b_table[i][j] = 0, diaganal arrow
    b_table[i][j] = 1, up-arrow
    b_table[i][j] = -1, left-arrow
    */
    int m=x.length;
    int n=y.length;
    c_table = new int[m+1][n+1];
    b_table = new int[m][n];
    for (int i=0;i<=m;i++)
      c_table[i][0] = 0;
    for (int j=0;j<=n;j++)
      c_table[0][j] = 0;
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        b_table[i][j] = 0;
      }
    for (int i=0;i<m;i++)
      for (int j=0;j<n;j++) {
        if (x[i] == y[j]) {
          c_table[i+1][j+1] = c_table[i][j] +1;
          b_table[i][j] = 0;
        }
        else if (c_table[i][j+1] >= c_table[i+1][j]) {
          c_table[i+1][j+1] = c_table[i][j+1];
          b_table[i][j] = 1;
        }
        else {
          c_table[i+1][j+1] = c_table[i+1][j];
          b_table[i][j] = -1;
        }
      }
//      Display.display("c_table",c_table);
//      Display.display("b_table",b_table);
      print_LCS(b_table,x,m-1,n-1);
  }
  public void print_LCS(int [][] b, int [] x, int i, int j) {
    if (i==-1 || j==-1) return;
    if (b[i][j] == 0) {
      print_LCS(b,x,i-1,j-1);
      Display.display("x[" + i + "] = ",x[i]);
    }
    else if (b[i][j] == 1) {
      print_LCS(b,x,i-1,j);
    }
    else
      print_LCS(b,x,i,j-1);
  }
  public void print_LCS(int [][] b, char [] x, int i, int j) {
    if (i==-1 || j==-1) return;

//    String a = new String("a");
//    a.valueOf()
    if (b[i][j] == 0) {
      print_LCS(b,x,i-1,j-1);
//      lcsString = lcsString + String.valueOf(x[i]) ;
      Display.display("x[" + i + "] = " + String.valueOf(x[i]));
    }
    else if (b[i][j] == 1) {
      print_LCS(b,x,i-1,j);
    }
    else
      print_LCS(b,x,i,j-1);
  }
  public void print_LCS(int [][] b, String [] x, int i, int j,Vector v) {
    if (i==-1 || j==-1) return;
//    Vector v= new Vector();
//    String a = new String("a");
//    a.valueOf()
    if (b[i][j] == 0) {
      print_LCS(b,x,i-1,j-1,v);
      v.add(x[i]);
//      lcsString += " " + x[i] ;
      Display.display("x[" + i + "] = " + x[i]);
    }
    else if (b[i][j] == 1) {
      print_LCS(b,x,i-1,j,v);
    }
    else
      print_LCS(b,x,i,j-1,v);
  }
  public void print_LCS(int [][] b, char [] x, int i, int j,Vector v) {
    if (i==-1 || j==-1) return;
//    Vector v= new Vector();
//    String a = new String("a");
//    a.valueOf()
    if (b[i][j] == 0) {
      print_LCS(b,x,i-1,j-1,v);
      v.add(String.valueOf(x[i]));
//      lcsString += " " + x[i] ;
      msg.display("x[" + i + "]y[ " +j+"] = " + String.valueOf(x[i]));
    }
    else if (b[i][j] == 1) {
      print_LCS(b,x,i-1,j,v);
    }
    else
      print_LCS(b,x,i,j-1,v);
  }
  public void Rabin_Karp_Matcher(String T, String P, int d, int q) {
//    int n = T.length();
//    int m = P.length();
//    double h = Math.pow(d,m-1)%q;
//    int p=0;
//    int t0 = 0;

  }
}















