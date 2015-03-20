package gov.nih.niehs.EPIGseq.myutility.misc;


/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
import java.text.*;
import java.util.*;
import java.io.*;
import gov.nih.niehs.EPIGseq.myutility.arrayObject.*;


import gov.nih.niehs.EPIGseq.myutility.statistics.*;

public class Display {
  public static DecimalFormat df;

  public static void display(String name) {
    System.out.println(name);
  }
  public static void display(String name, float x) {
    System.out.println(name + " " + x + "\n");
  }
  public static void display(String name, int x) {
    System.out.println(name + " " + x + "\n");
  }
  public static void display(float [] x) {
    System.out.println("");
    for (int i=0;i<x.length;i++)
    System.out.println(x[i] + "\t");
    System.out.println("");
  }

  public static void display(String name,float [] x) {
    System.out.println("\n"+name);
    for (int i=0;i<x.length;i++)
    System.out.println(x[i] + "\t");
    System.out.println("");
  }
  public static void display(String name,int [] x) {
    System.out.println("\n"+name);
    for (int i=0;i<x.length;i++)
    System.out.println(x[i] + "\t");
    System.out.println("");
  }
  public static void display(String [] x) {
    System.out.println("");
    for (int i=0;i<x.length;i++)
    System.out.println(x[i] + "\t");
    System.out.println("");
  }
  public static void display(String name,String [] x) {
    System.out.println("\n"+name);
    for (int i=0;i<x.length;i++)
      if (i==x.length-1)
        System.out.print(x[i] + "\n");
        else
    System.out.print(x[i] + "\t");
    System.out.println("");
  }
  public static void display(float [][] x) {
    System.out.println("");
    for (int j=0;j<x.length;j++) {
    for (int i=0;i<x[j].length;i++)
       System.out.println(x[j][i] + "\t");
    System.out.println("");
    }
  }
  public static void display(String name,float [][] x) {
    System.out.println("\n"+name);
    for (int j=0;j<x.length;j++) {
    for (int i=0;i<x[j].length;i++)
       System.out.println(x[j][i] + "\t");
    System.out.println("");
    }
  }
  public static void display(int [][] x) {
    System.out.println("");
    for (int j=0;j<x.length;j++) {
    for (int i=0;i<x[j].length;i++)
       System.out.println(x[j][i] + "\t");
    System.out.println("");
    }
  }
  public static void display(String name,int [][] x) {
    System.out.println("\n"+name);
    for (int j=0;j<x.length;j++) {
      for (int i=0;i<x[j].length;i++) {
        if (i == x[j].length-1)
          System.out.print(x[j][i] + "\n");
          else
         System.out.print(x[j][i] + "\t");
      }
//    System.out.println("");
    }
  }

  public static void display(String [][] x) {
    System.out.println("");
    for (int j=0;j<x.length;j++) {
    for (int i=0;i<x[j].length;i++)
       System.out.println(x[j][i] + "\t");
    System.out.println("");
    }
  }
  public static void display(String name,String [][] x) {
    System.out.println("\n"+name);
    for (int j=0;j<x.length;j++) {
    for (int i=0;i<x[j].length;i++)
       System.out.println(x[j][i] + "\t");
    System.out.println("");
    }
  }
  public static void setDecimalFormat(DecimalFormat _df) {
    df=_df;
  }
  public static void display(String [] title,String [] name, int [] intValue, float [] value1, float [] value2)
  {
      display(title);
      if (df == null) {
        df = new DecimalFormat("0.00000000");
      }
      for (int i=0;i<name.length;i++) {
          display(name[i] + "  " + i +"  "+ df.format(value1[i]) + "\t"+ df.format(value2[i]) +"\n");
      }

      float mean = Statistics.mean(value1);
      float median = Statistics.median(value1);
      float stdDev = Statistics.stdDev(value1);
      float var = Statistics.var(value1);
      display("\nGene mean:\t"+mean + "\n");
      display("Gene median:\t"+median + "\n");
     display("Gene stdDev:\t"+stdDev + "\n");
      display("variance:\t"+var + "\n");
  }
  public static void GenBank_id(String id) {
    if (id == null) return;
      try {
        Runtime runtime = Runtime.getRuntime();
        String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=nucleotide&cmd=search&term="+id;
        Process process = runtime.exec(myURL);
      }
      catch (IOException ee) {
        System.out.println(ee.toString());
      }
  }
  public static void GenName(String id) {
    if (id == null) return;
      try {
        Runtime runtime = Runtime.getRuntime();
        String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=gene&cmd=search&term="+id;
        Process process = runtime.exec(myURL);
      }
      catch (IOException ee) {
        System.out.println(ee.toString());
      }
  }

    public static void UniGene_id(String id_1,String id_2) {
      if (id_1.equals("Hs") || id_1.equals("Rn") || id_1.equals("Mm")) {
        try {
          Runtime runtime = Runtime.getRuntime();
          String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  http://www.ncbi.nlm.nih.gov/UniGene/clust.cgi?ORG="+id_1+"&CID="+id_2;
          Process process = runtime.exec(myURL);
        }
        catch (IOException ee) {
          System.out.println(ee.toString());
        }
      }
  }

  public static void ie(String str) {
    StringTokenizer parser = new StringTokenizer(str, ".");
    if (parser.countTokens() >= 2) {
      String s1 = parser.nextToken();
      String s2 = parser.nextToken();
      if (s1.equals("Hs") || s1.equals("Rn") || s1.equals("Mm")) {
        UniGene_id(s1,s2);
      }
    }
    else {
      String s = parser.nextToken();
      GenBank_id(s);
      GenName(s);
    }
  }

}