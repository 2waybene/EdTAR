package gov.nih.niehs.EPIGseq.util;


import java.util.*;
import java.io.*;
import java.text.*;
import javax.swing.*;
import datatable.*;
import myutility.io.*;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class VennDiagram {
  private TreeSet [] tsResultOne, tsResultTwo,tsResultThree;
  private TreeSet tsResultFour;
  private String [] setName;
  private int numSet = 0;
  private File f;
  private String filename;
  boolean next = true;
  private Vector [] v;

  public VennDiagram() {
  }

  private void vennDiagramFour() {
      tsResultTwo = new TreeSet[6];
      tsResultThree = new TreeSet[4];
      for (int i=0;i<6;i++) {
          tsResultTwo[i] = new TreeSet();
      }
      for (int i=0;i<4;i++) {
          tsResultThree[i] = new TreeSet();
      }
      tsResultFour = new TreeSet();
      vennDiagramTwo(tsResultOne[0],tsResultOne[1],tsResultTwo[0]);
      vennDiagramTwo(tsResultOne[2],tsResultTwo[0],tsResultThree[3]);
      vennDiagramTwo(tsResultOne[1],tsResultOne[2],tsResultTwo[3]);
      vennDiagramTwo(tsResultOne[0],tsResultOne[2],tsResultTwo[1]);

      vennDiagramTwo(tsResultThree[3],tsResultOne[3],tsResultFour);

      vennDiagramTwo(tsResultTwo[3],tsResultOne[3],tsResultThree[0]);
      vennDiagramTwo(tsResultTwo[1],tsResultOne[3],tsResultThree[1]);
      vennDiagramTwo(tsResultTwo[0],tsResultOne[3],tsResultThree[2]);

      vennDiagramTwo(tsResultOne[2],tsResultOne[3],tsResultTwo[5]);
      vennDiagramTwo(tsResultOne[1],tsResultOne[3],tsResultTwo[4]);
      vennDiagramTwo(tsResultOne[0],tsResultOne[3],tsResultTwo[2]);



//        System.out.println("four "+tsResultFour.size());
//        for (int i=0;i<4;i++)
//            System.out.println("three "+tsResultThree[i].size());
//        for (int i=0;i<4;i++)
//            System.out.println("two "+tsResultTwo[i].size());
//        for (int i=0;i<4;i++)
//            System.out.println("one "+tsResultOne[i].size());

      outputFour();
  }

  private void vennDiagramThree() {
      tsResultTwo = new TreeSet[3];
      tsResultThree = new TreeSet[1];
      for (int i=0;i<3;i++) tsResultTwo[i] = new TreeSet();
      for (int i=0;i<1;i++) tsResultThree[i] = new TreeSet();
      vennDiagramTwo(tsResultOne[0],tsResultOne[1],tsResultTwo[0]);
      vennDiagramTwo(tsResultOne[2],tsResultTwo[0],tsResultThree[0]);
      vennDiagramTwo(tsResultOne[1],tsResultOne[2],tsResultTwo[1]);
      vennDiagramTwo(tsResultOne[0],tsResultOne[2],tsResultTwo[2]);
      outputThree();
  }

  private void vennDiagramTwo() {
      tsResultTwo = new TreeSet[1];
      tsResultTwo[0] = new TreeSet();
      vennDiagramTwo(tsResultOne[0],tsResultOne[1],tsResultTwo[0]);
      outputTwo();
  }

  private void outputFour() {
      try {
          f = FileIO.saveSingleFile();
          FileWriter wrt = new FileWriter(new File(f.getPath()));
          PrintWriter out = new PrintWriter(wrt);

          out.print("All" + "\t"
          + setName[1]+"/"+setName[2] +"/"+setName[3] +"\t"
          + setName[0]+"/"+setName[2] +"/"+setName[3] +"\t"
          + setName[0]+"/"+setName[1] +"/"+setName[3] +"\t"
          + setName[0]+"/"+setName[1] +"/"+setName[2] +"\t"

          +setName[0]+"/"+setName[1] +"\t"
          +setName[0]+"/"+setName[2] +"\t"
          +setName[0]+"/"+setName[3] +"\t"
          +setName[1]+"/"+setName[2] +"\t"
          +setName[1]+"/"+setName[3] +"\t"
          +setName[2]+"/"+setName[3] +"\t"

          + setName[0]+"\t"
          + setName[1]+"\t"
          + setName[2]+"\t"
          + setName[3]+"\n");

          out.print(tsResultFour.size() +"\t"
          + tsResultThree[0].size()+"\t"+ tsResultThree[1].size()+"\t"+ tsResultThree[2].size()+"\t"+ tsResultThree[3].size()+"\t"
          + tsResultTwo[0].size() +"\t"+tsResultTwo[1].size() +"\t"+ tsResultTwo[2].size() +"\t"+tsResultTwo[3].size() +"\t"+ tsResultTwo[4].size() +"\t"+tsResultTwo[5].size() +"\t"
          + tsResultOne[0].size() +"\t"+tsResultOne[1].size()+"\t" +tsResultOne[2].size() +"\t" +tsResultOne[3].size()+ "\n");

          Iterator [] it = new Iterator[15];
          it[0] = tsResultFour.iterator();
          it[1] = tsResultThree[0].iterator();
          it[2] = tsResultThree[1].iterator();
          it[3] = tsResultThree[2].iterator();
          it[4] = tsResultThree[3].iterator();
          it[5] = tsResultTwo[0].iterator();
          it[6] = tsResultTwo[1].iterator();
          it[7] = tsResultTwo[2].iterator();
          it[8] = tsResultTwo[3].iterator();
          it[9] = tsResultTwo[4].iterator();
          it[10] = tsResultTwo[5].iterator();
          it[11] = tsResultOne[0].iterator();
          it[12] = tsResultOne[1].iterator();
          it[13] = tsResultOne[2].iterator();
          it[14] = tsResultOne[3].iterator();
          int maxLength = tsResultOne[0].size();
          if (maxLength < tsResultOne[1].size())
              maxLength = tsResultOne[1].size();
          if (maxLength < tsResultOne[2].size())
              maxLength = tsResultOne[2].size();
          if (maxLength < tsResultOne[3].size())
              maxLength = tsResultOne[3].size();
          if (maxLength < tsResultTwo[0].size())
              maxLength = tsResultTwo[0].size();
          if (maxLength < tsResultTwo[1].size())
              maxLength = tsResultTwo[1].size();
          if (maxLength < tsResultTwo[2].size())
              maxLength = tsResultTwo[2].size();
          if (maxLength < tsResultTwo[3].size())
              maxLength = tsResultTwo[3].size();
          if (maxLength < tsResultTwo[4].size())
              maxLength = tsResultTwo[4].size();
          if (maxLength < tsResultTwo[5].size())
              maxLength = tsResultTwo[5].size();
          if (maxLength < tsResultThree[0].size())
              maxLength = tsResultThree[0].size();
          if (maxLength < tsResultThree[1].size())
              maxLength = tsResultThree[1].size();
          if (maxLength < tsResultThree[2].size())
              maxLength = tsResultThree[2].size();
          if (maxLength < tsResultThree[3].size())
              maxLength = tsResultThree[3].size();
          if (maxLength < tsResultFour.size())
              maxLength = tsResultFour.size();
          for (int i=0;i<maxLength;i++) {
              if (it[0].hasNext()) out.print(it[0].next()+"\t");
              else out.print("\t");
              if (it[1].hasNext()) out.print(it[1].next()+"\t");
              else out.print("\t");
              if (it[2].hasNext()) out.print(it[2].next()+"\t");
              else out.print("\t");
              if (it[3].hasNext()) out.print(it[3].next()+"\t");
              else out.print("\t");
              if (it[4].hasNext()) out.print(it[4].next()+"\t");
              else out.print("\t");
              if (it[5].hasNext()) out.print(it[5].next()+"\t");
              else out.print("\t");
              if (it[6].hasNext()) out.print(it[6].next()+"\t");
              else out.print("\t");
              if (it[7].hasNext()) out.print(it[7].next()+"\t");
              else out.print("\t");
              if (it[8].hasNext()) out.print(it[8].next()+"\t");
              else out.print("\t");
              if (it[9].hasNext()) out.print(it[9].next()+"\t");
              else out.print("\t");
              if (it[10].hasNext()) out.print(it[10].next()+"\t");
              else out.print("\t");
              if (it[11].hasNext()) out.print(it[11].next()+"\t");
              else out.print("\t");
              if (it[12].hasNext()) out.print(it[12].next()+"\t");
              else out.print("\t");
              if (it[13].hasNext()) out.print(it[13].next()+"\t");
              else out.print("\t");
              if (it[14].hasNext()) out.print(it[14].next()+"\n");
              else out.print("\n");
          }
          wrt.flush();
          wrt.close();
      }
      catch (IOException e)
      {
          System.out.println(e.getMessage());
          //            msg.messageDisplay(e.getMessage());
          return;
      }
  }
  private void outputThree() {
      try {
          f = FileIO.saveSingleFile();
          FileWriter wrt = new FileWriter(new File(f.getPath()));
          PrintWriter out = new PrintWriter(wrt);

          out.print("All" + "\t"

          + setName[0]+"/"+setName[1] + "\t"
          + setName[1]+"/"+setName[2] + "\t"
          + setName[2]+"/"+setName[0] + "\t"

          + setName[0]+ "\t"
          + setName[1]+ "\t"
          + setName[2]+ "\n");

          out.print(tsResultThree[0].size() + "\t"

          + tsResultTwo[0].size() + "\t"
          + tsResultTwo[1].size() + "\t"
          + tsResultTwo[2].size() + "\t"

          + tsResultOne[0].size() + "\t"
          + tsResultOne[1].size() + "\t"
          + tsResultOne[2].size() + "\n");

          Iterator [] it = new Iterator[7];
          it[0] = tsResultThree[0].iterator();
          it[1] = tsResultTwo[0].iterator();
          it[2] = tsResultTwo[1].iterator();
          it[3] = tsResultTwo[2].iterator();
          it[4] = tsResultOne[0].iterator();
          it[5] = tsResultOne[1].iterator();
          it[6] = tsResultOne[2].iterator();

          int maxLength = tsResultOne[0].size();
          if (maxLength < tsResultOne[1].size())
              maxLength = tsResultOne[1].size();
          if (maxLength < tsResultOne[2].size())
              maxLength = tsResultOne[2].size();
          if (maxLength < tsResultTwo[0].size())
              maxLength = tsResultOne[0].size();
          if (maxLength < tsResultTwo[1].size())
              maxLength = tsResultTwo[1].size();
          if (maxLength < tsResultTwo[2].size())
              maxLength = tsResultTwo[2].size();
          if (maxLength < tsResultThree[0].size())
              maxLength = tsResultThree[0].size();

          for (int i=0;i<maxLength;i++) {
              if (it[0].hasNext()) out.print(it[0].next()+"\t");
              else out.print("\t");
              if (it[1].hasNext()) out.print(it[1].next()+"\t");
              else out.print("\t");
              if (it[2].hasNext()) out.print(it[2].next()+"\t");
              else out.print("\t");
              if (it[3].hasNext()) out.print(it[3].next()+"\t");
              else out.print("\t");
              if (it[4].hasNext()) out.print(it[4].next()+"\t");
              else out.print("\t");
              if (it[5].hasNext()) out.print(it[5].next()+"\t");
              else out.print("\t");
              if (it[6].hasNext()) out.print(it[6].next()+"\n");
              else out.print("\n");
          }
          wrt.flush();
          wrt.close();
      }
      catch (IOException e)
      {
          System.out.println(e.getMessage());
          //            msg.messageDisplay(e.getMessage());
          return;
      }
  }
  private void outputTwo() {
      System.out.println("Saving now...");
      try {
          f = FileIO.saveSingleFile();
          FileWriter wrt = new FileWriter(new File(f.getPath()));
          PrintWriter out = new PrintWriter(wrt);
          out.print(
          setName[0]+"/"+setName[1] + "\t"
          + setName[0] + "\t"
          + setName[1] + "\n");

          out.print(
          tsResultTwo[0].size() + "\t"
          + tsResultOne[0].size() + "\t"
          + tsResultOne[1].size() + "\n");

          Iterator [] it = new Iterator[3];
          it[0] = tsResultTwo[0].iterator();
          it[1] = tsResultOne[0].iterator();
          it[2] = tsResultOne[1].iterator();

          int maxLength = tsResultOne[0].size();
          if (maxLength < tsResultOne[1].size())
              maxLength = tsResultOne[1].size();
          if(maxLength < tsResultTwo[0].size())
              maxLength = tsResultTwo[0].size();

          for (int i=0;i<maxLength;i++) {
              if (it[0].hasNext()) out.print(it[0].next()+"\t");
              else out.print("\t");
              if (it[1].hasNext()) out.print(it[1].next()+"\t");
              else out.print("\t");
              if (it[2].hasNext()) out.print(it[2].next()+"\n");
              else out.print("\n");
          }
          wrt.flush();
          wrt.close();
      }
      catch (IOException e)
      {
          System.out.println(e.getMessage());
          //            msg.messageDisplay(e.getMessage());
          return;
      }
  }
  private void vennDiagramTwo(TreeSet a, TreeSet b,TreeSet ab) {
      Iterator [] it = new Iterator[2];
      it[0] = a.iterator();
      it[1] = b.iterator();
      boolean more = true;
      String o1,o2;
      if (it[0].hasNext() && it[1].hasNext()) {
      o1 = (String)it[0].next();
      o2 = (String)it[1].next();
      }
      else return;
      do {
          if (o1.compareTo(o2) == 0) {
              it[0].remove();
              it[1].remove();
              ab.add(o1);
              if (it[0].hasNext() && it[1].hasNext()) {
                  o1 = (String)it[0].next();
                  o2 = (String)it[1].next();
              }
              else {
                  more = false;
              }
          }
          else if (o1.compareTo(o2) < 0) {
              if (it[0].hasNext())
                  o1 = (String)it[0].next();
              else {
                  more = false;
              }
          }
          else {
              if (it[1].hasNext())
                  o2 = (String)it[1].next();
              else {
                  more = false;
              }
          }
      } while (more);
  }

  public void vennDiagram() {
      if (numSet == 2)
          vennDiagramTwo();
      else if (numSet == 3)
          vennDiagramThree();
      else if (numSet==4)
          vennDiagramFour();
      else {
          System.out.println("The program is not available to calculate venn diagram over four sets of data.");
      }
  }
  public String loadData() {
      StringTokenizer parser1, parser2;
      try {
          f = FileIO.openSingleFile();
          if (f == null ) return "Cancel";
          filename = f.getName();
          FileReader reader = new FileReader(f.getPath());
          try {
              BufferedReader readIn = new BufferedReader(reader);
              if (readIn.ready()) {
                  String str = readIn.readLine();
                  parser1 = new StringTokenizer(str, "\t");
                  numSet = parser1.countTokens();
                  tsResultOne = new TreeSet[numSet];
                  setName = new String[numSet];
                  for (int i=0;i<numSet;i++) {
                      setName[i] = parser1.nextToken();
                      tsResultOne[i] = new TreeSet();
                  }
              }
              else {
                  return "Invalid1";
              }
              String temp;
              while(readIn.ready()) {
                  parser1 = new StringTokenizer(readIn.readLine(), "\t");
                  int size = parser1.countTokens();
                  for (int i=0;i<numSet;i++) {
                      if (i<size) {
                      temp = parser1.nextToken();
                      }
                      else temp = null;
                      if (temp != null) tsResultOne[i].add(temp);
                  }
              }
          }
          catch (IOException e) {
              System.out.println(e.toString());
              return "Invalid2";
          }
          reader.close();
      }
      catch (FileNotFoundException e) {
          System.out.println(e.toString());
          return "Invalid3";
      }
      catch (IOException e) {
          System.out.println(e.toString());
          return "Invalid4";
      }
      return "Valid";
  }

}