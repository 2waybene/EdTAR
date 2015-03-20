package gov.nih.niehs.EPIGseq.myutility.io;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
import java.sql.*;
import java.sql.ResultSet.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import myutility.misc.*;
import myutility.numerical.*;
import java.net.URL;
public class FileIO {
  private MessageBoard msg;
  private String version,pathIcon;
  private Vector [] dataHolder;
  private File [] f;
  public static URL imageIconURL = null,expressionPredictor,expressionPredictorDoc,GenericCompiledDataFrameIO,GenericIntensityDataSetsFrameIO,AffyDataFrame;
  public static ImageIcon imageIcon = null;
  public static String path = null,mainPath=null,docPath=null;
  public static URL logoImageURL = null;
  public static URL backgroundImageURL = null;
// public static String imagePath = null;
  static JFileChooser chooser;
  public FileIO() {

  }
  public FileIO(MessageBoard _msg) {
    msg = _msg;
  }
  private static void filter() {
    chooser.addChoosableFileFilter(new MyFileFilter("xml", "XML Files"));
    chooser.addChoosableFileFilter(new MyFileFilter("gpr", "Text Files"));
    chooser.addChoosableFileFilter(new MyFileFilter("lsr", "Text Files"));
    chooser.addChoosableFileFilter(new MyFileFilter("csv", "Microsoft csv Files"));
    chooser.addChoosableFileFilter(new MyFileFilter("xls", "Microsoft excel Files"));
      chooser.addChoosableFileFilter(new MyFileFilter(new String []{"jpg","gif"}, "Gif and JPG Images"));
      chooser.addChoosableFileFilter(new MyFileFilter("txt", "Text Files"));
  }

  private static boolean result() {
      int result = chooser.showOpenDialog(null);
      if (result ==  chooser.CANCEL_OPTION) {
//          JOptionPane.showMessageDialog(null,"Canceled");
          return false;
      }
      if (result != chooser.APPROVE_OPTION) {
//          JOptionPane.showMessageDialog(null,"Canceled");
          return false;
      }
      return true;
   }

  public static File [] openMultiFiles(boolean filter) {
      if (path == null) {
          chooser = new JFileChooser();
      }
      else {
          chooser = new JFileChooser(path);
      }
//        chooser.showOpenDialog(null);
      chooser.setDialogTitle("Select Files");
      chooser.setApproveButtonText("OPEN");
      chooser.setMultiSelectionEnabled(true);
      // Create Filters
      if (filter)
      filter();
     if(! result()) return null;

      File [] f = chooser.getSelectedFiles();
      path = f[0].getParent();
      return f;
  }

  public static File openSingleFile(String file) {
      if (path == null) chooser = new JFileChooser();
      else chooser = new JFileChooser(path);
      chooser.setDialogTitle("file");
      chooser.setApproveButtonText("OPEN");
      filter();
     if(! result()) return null;

      File f = chooser.getSelectedFile();
      path = f.getParent();
      return f;
  }
  public static File openSingleFile() {
      if (path == null) chooser = new JFileChooser();
      else chooser = new JFileChooser(path);
      chooser.setDialogTitle("Select a File");
      chooser.setApproveButtonText("OPEN");
      filter();
     if(! result()) return null;

      File f = chooser.getSelectedFile();
      path = f.getParent();
      return f;
  }

  public static File saveSingleFile() {
      if (path == null) chooser = new JFileChooser();
      else chooser = new JFileChooser(path);
      chooser.setDialogTitle("Save a File");
      chooser.setApproveButtonText("SAVE");
      filter();
     if(! result()) return null;

      File f = chooser.getSelectedFile();
      path = f.getParent();
//    return      chooser.getCurrentDirectory();
      return f;
  }
  public static File getDirectory() {
      if (path == null) chooser = new JFileChooser();
      else chooser = new JFileChooser(path);
      chooser.setDialogTitle("Save a File");
      chooser.setApproveButtonText("SAVE");
     if(! result()) return null;

      File f = chooser.getCurrentDirectory();
      path = f.getPath();
//    return      chooser.getCurrentDirectory();
      return f;
  }
//  FileIO.saveNormalizedIntensityData(filePath,fileName,totalItems,columnName,strData,flag,colAssignment[colAssignment.length-1]);
public static void deleteFile(String path, String fName) {
  if (path == null || fName == null) return;
  File f = new File(path, fName);
  if (f.exists())
    f.delete();
}

public static void saveNormalizedIntensityData(String path, String fName,
    String [] columnName,String [][] str,String [] flag,int isFlag) {
if (isFlag == -1) {
  try {
      FileWriter wrt = new FileWriter(new File(path,fName));
      PrintWriter out = new PrintWriter(wrt);
     out.print(fName  + "\t" + str.length+"\n");
     for (int i=0;i<columnName.length;i++) {
         out.print(columnName[i] + "\t");
     }
     out.print("FLAG\n");

      for (int i=0;i<str.length;i++) {
        if (!flag[i].equals(ConstantValue.removed_data)) {
        for (int j=0;j<str[i].length;j++ ){
            out.print(str[i][j] + "\t");
        }
        out.print(flag[i]+"\n");
        }
        }
      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
//      msg.display(e.getMessage()+"\n");
  }

}
else {
  try {
      FileWriter wrt = new FileWriter(new File(path,fName));
      PrintWriter out = new PrintWriter(wrt);
     out.print(fName  + "\n");
     for (int i=0;i<columnName.length;i++) {
       if (i==columnName.length-1)
         out.print(columnName[i] + "\n");
         else
           out.print(columnName[i] + "\t");
     }


      for (int i=0;i<str.length;i++) {
        if (!flag[i].equals(ConstantValue.removed_data)) {
        for (int j=0;j<str[i].length;j++ ){
          if (j==str[i].length-1)
             out.print(str[i][j] + "\n");
          else
            out.print(str[i][j] + "\t");
        }
        }
        }
      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
//      msg.display(e.getMessage()+"\n");
  }

}


}
public static void saveArrayDB_MAPS_IntensityData(String path, String fName,
    String [] columnName,String [][] str, String expName,String expID) {

  try {
   if (path == null) {
//     msg.display("path is null");
     return;
   }
   if (fName == null) {
 //    msg.display("fName is null");
     return;
   }
      FileWriter wrt = new FileWriter(new File(path,fName));
      PrintWriter out = new PrintWriter(wrt);
     out.print(expName + "\t" + expID + "\n");
     for (int i=0;i<columnName.length;i++) {
       if (i==columnName.length-1)
           out.print(columnName[i] + "\n");
       else
         out.print(columnName[i] + "\t");
     }

      for (int i=0;i<str.length;i++) {

        for (int j=0;j<str[i].length;j++ ){
          if (j==str[i].length-1)
            out.print(str[i][j] + "\n");
          else
            out.print(str[i][j] + "\t");
        }

        }
      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
 //     msg.display(e.getMessage()+"\n");
  }



}


public static void saveResult(String path,String fName,String s) {
  try {
   if (path == null) {
     return;
   }
      FileWriter wrt = new FileWriter(new File(path,fName));
      PrintWriter out = new PrintWriter(wrt);
     out.print(s);

      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
  }



}
public static void saveResult(File f,String s) {
  try {
   if (path == null) {
     return;
   }
      FileWriter wrt = new FileWriter(f);
      PrintWriter out = new PrintWriter(wrt);
     out.print(s);

      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
  }



}

public static void saveResult(String fName,String s) {
  try {
   if (path == null) {
     return;
   }
      FileWriter wrt = new FileWriter(new File(path,fName));
      PrintWriter out = new PrintWriter(wrt);
     out.print(s);

      wrt.flush();
      wrt.close();
  }
  catch (IOException e)
  {
  }



}

  public static File saveSingleFile2() {
      if (path == null) chooser = new JFileChooser();
      else chooser = new JFileChooser(path);
      filter();
      chooser.setApproveButtonText("SAVE");
//        chooser.setDialogType(chooser.SAVE_DIALOG);
//        File f = chooser.getCurrentDirectory();
      File f = chooser.getSelectedFile();
      path = f.getParent();
      return f;
  }
//  try {
//     BufferedReader in = new BufferedReader(new FileReader("infilename"));
//     String str;
//     while ((str = in.readLine()) != null) {
//         process(str);
//     }
//     in.close();
// } catch (IOException e) {
// }

  public static Vector readFile(File f) {
  Vector v = new Vector();
  try {
  FileReader reader = new FileReader(new File(f.getPath()));
  BufferedReader readIn = new BufferedReader(reader);
  StringTokenizer  parser;
  String str;
  int col=0;
  while ((str = readIn.readLine()) != null) {
//    while (readIn.ready()) {
    if (str.length()>0) {
     parser = new StringTokenizer(str, "\t",true);
//     parser = new StringTokenizer(readIn.readLine(), "\t",true);
     v.addElement(parser);
    }
  }
  readIn.close();
  reader.close();
  }
  catch (FileNotFoundException e) {
    System.out.println("FileNotFoundException "+e.toString());
  }
  catch (IOException e) {
    System.out.println("IOException "+e.toString());
  }
    return v;
}
public static Vector readFile(File f, int numRow) {
Vector v = new Vector();
try {
FileReader reader = new FileReader(new File(f.getPath()));
BufferedReader readIn = new BufferedReader(reader);
StringTokenizer  parser;
int col=0;
int num=0;
while (readIn.ready() && num <numRow) {
   parser = new StringTokenizer(readIn.readLine(), "\t",true);
   v.addElement(parser);
   num++;
}
readIn.close();
reader.close();
}
catch (FileNotFoundException e) {
  System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
  System.out.println("IOException "+e.toString());
}
  return v;
}
public static void changeFileName(File f) {
  String outputFile = f.getName();
java.util.StringTokenizer token = new java.util.StringTokenizer(outputFile,"=");
String s1 =  token.nextToken();
java.util.StringTokenizer token1 = new java.util.StringTokenizer(s1,"_");
String plateNum = token1.nextToken();
String s2 =  token.nextToken();
java.util.StringTokenizer token2 = new java.util.StringTokenizer(s2,")");
String seqID = token2.nextToken().trim();
outputFile = seqID+"_"+plateNum+".txt";
  try {
FileReader reader = new FileReader(new File(f.getPath()));
BufferedReader readIn = new BufferedReader(reader);

FileWriter wrt = new FileWriter(new File(f.getParent(),outputFile));
PrintWriter out = new PrintWriter(wrt);

//StringTokenizer  parser;
int col=0;
while (readIn.ready()) {
//   parser = new StringTokenizer(readIn.readLine(), "\t",true);
  out.println(readIn.readLine());
}
readIn.close();
reader.close();
wrt.flush();
wrt.close();

}
catch (FileNotFoundException e) {
  System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
  System.out.println("IOException "+e.toString());
}

}
public static String [] readFile_line(File f) {
Vector v = new Vector();
try {
FileReader reader = new FileReader(new File(f.getPath()));
BufferedReader readIn = new BufferedReader(reader);
//StringTokenizer  parser;
int col=0;
while (readIn.ready()) {
//   parser = new StringTokenizer(readIn.readLine(), "\t",true);
   v.addElement(readIn.readLine());
}
readIn.close();
reader.close();
}
catch (FileNotFoundException e) {
  System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
  System.out.println("IOException "+e.toString());
}
  Object [] o = v.toArray();
  String [] str = new String[o.length];
  for (int i=0;i<o.length;i++)
    str[i] = (String)o[i];
  return str;
}
public static String [][] displayData(Vector v, String delimit) {
  int rowNum = v.size();
  StringTokenizer  parser;
  String [][] str = new String[rowNum][];
  for (int i=0;i<rowNum;i++) {
    parser = (StringTokenizer)v.elementAt(i);
    str[i] = DataConversion.convertStringTokebizerToArray(parser,delimit);
  }
  return str;
}

public static void saveMergedFiles(File [] f, int dataStartRow, String delimit) {
  File outputFile = FileIO.saveSingleFile();
  String fileName1 = outputFile.getName();
  String str;
  if (outputFile==null) return;
  try {
    FileWriter wrt = new FileWriter(new File(outputFile.getParent(),outputFile.getName()));
    PrintWriter out = new PrintWriter(wrt);

    FileReader reader = new FileReader(new File(f[0].getPath()));
    BufferedReader readIn = new BufferedReader(reader);
      while (  (str=   readIn.readLine())!=null )
        out.println(str);
    if (f.length> 1) {
      for (int i=1;i<f.length;i++) {
        reader = new FileReader(new File(f[i].getPath()));
        readIn = new BufferedReader(reader);
        for (int j=0;j<dataStartRow;j++) {
          str=   readIn.readLine();
        }
        while (  (str=   readIn.readLine())!=null )
          out.println(str);
      }
    }

  wrt.flush();
  wrt.close();
}
catch (IOException e)
{
  System.out.println(e.getMessage());
//  msg.messageDisplay(e.getMessage()+"\n");
}



//  return DataConversion.mergeData(str,dataStartRow);
}
public static String [][] loadDataSets(File [] f, int dataStartRow, String delimit) {
  String [][][] str = new String[f.length][][];
  for (int i=0;i<f.length;i++) {
    str[i] = displayData(FileIO.readFile(f[i]),delimit);
  }
  return DataConversion.mergeData(str,dataStartRow);
}

public static String [][] convertDataToArray(Vector v,String delimit) {
  int rowNum = v.size();
  StringTokenizer  parser;
  String [][] o = new String[rowNum][];
  for (int i=0;i<rowNum;i++) {
    parser = (StringTokenizer)v.elementAt(i);
    o[i] = DataConversion.convertStringTokebizerToArray(parser,delimit);
  }
  return o;
}

public static String [] loadData(int col, String delimit) {
  File f = FileIO.openSingleFile();
  if (f == null ) return null;
  String filename = f.getName();
  String [][] str = displayData(readFile(f),delimit);
  String [] strList = new String[str.length];
  for (int i=0;i<str.length;i++)
    strList[i] = str[i][col];
 return strList;
}
public static String [] loadData(File f, int col, String delimit) {
  if (f == null ) return null;
  String filename = f.getName();
  String [][] str = displayData(readFile(f),delimit);
  String [] strList = new String[str.length];
  for (int i=0;i<str.length;i++)
    strList[i] = str[i][col];
 return strList;
}

public static String [][] loadData(String delimit) {
  File f = FileIO.openSingleFile();
  if (f == null ) return null;
  String filename = f.getName();
  String [][] str = displayData(readFile(f),delimit);
 return str;
}
private boolean checkGeneList() {
     return true;
}
public static void openDocument(String  [] cmd) {

try {
              Process process = Runtime.getRuntime().exec( cmd );
}
catch (IOException ee) {
System.out.println(ee.toString());
}


}
public static void openDocument(String url) {
  url = docPath + url;
  try {
Runtime runtime = Runtime.getRuntime();  //ExpressionPredictorDocument
String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  " + url;
Process process = runtime.exec(myURL);
}
catch (IOException ee) {
System.out.println(ee.toString());
}
}
public static void setDocPath(String [] s) {
  docPath="";
for (int k=1;k<s.length-3;k++)
  FileIO.docPath+=removeChar(s[k])+"\\";

}
public static String removeChar(String s) {
  StringTokenizer p = new StringTokenizer(s,"%20");
  int num = p.countTokens();
  if (num == 1) return s;
  String t="";
  for (int i=0;i<num;i++)
    if (i == num-1)
      t += p.nextToken();
      else
    t += p.nextToken()+" ";
  return t;
}
public static void openPDF(String path) {
  try {
    Runtime runtime = Runtime.getRuntime();
    String myURL = "c:\\Program Files\\Internet Explorer\\IEXPLORE.exe  "+path;
    Process process;
    File f = new File(path);
    if (f.exists())
     process = runtime.exec(myURL);
    else {
      Display.display("Help File dose not exist.");
      return;
    }
  }
  catch (IOException ee) {
    System.out.println(ee.toString());
  }

}
public static String [][] read_excel(File f, String sheetName) {
  Vector v = new Vector();
Connection c = null;
Statement stmnt = null;
try
{
Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + f.getName());
stmnt = c.createStatement();
String query = "Select * from [Sheet1$]" ;
ResultSet rs = stmnt.executeQuery( query );

String [] str;
int colNum=0;
if (rs.next()) {
ResultSetMetaData rsmd = rs.getMetaData();
colNum = rsmd.getColumnCount();
str = new String[colNum];
for (int i=1;i<= colNum;i++) {
   str[i] = rsmd.getColumnName(i);
  System.out.println("name "+rsmd.getColumnName(i));
}
v.addElement(str);
str = new String[colNum];
for (int i=1;i<= colNum;i++){
  str[i] = rs.getString(i);
  System.out.println( rs.getString(i) );
  v.addElement(str);
}
}
while( rs.next() )
{
  str = new String[colNum];
for (int i=1;i<= colNum;i++)
  str[i] = rs.getString(i);
//  System.out.println( rs.getString(i) );
v.addElement(str);
}
}
catch( Exception e )
{
System.err.println( e );
}
if (v.size()==0)
return null;
else {
  Object [] o=v.toArray();
  String [][] out = new String[o.length][];
  for (int i=0;i<o.length;i++)
      out[i] = (String [])o[i];
  return out;
}
}
public String [][] read_excel_1(File f, String sheetName) {
  Vector v = new Vector();
Connection c = null;
Statement stmnt = null;
try
{
Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + f.getName());
stmnt = c.createStatement();
//stmnt.
//String query = "Select * from ["+sheetName+"$]" ;
String query = "Select * from [Sheet1$]" ;

//  String query = "Select * from ["+ sheetName +"$]" ;
ResultSet rs = stmnt.executeQuery( query );

String [] str;
int colNum=0;
if (rs.next()) {
ResultSetMetaData rsmd = rs.getMetaData();
colNum = rsmd.getColumnCount();
str = new String[colNum];
for (int i=1;i<= colNum;i++) {
   str[i] = rsmd.getColumnName(i);
  System.out.println("name "+rsmd.getColumnName(i));
}
v.addElement(str);
str = new String[colNum];
for (int i=1;i<= colNum;i++){
  str[i] = rs.getString(i);
  System.out.println( rs.getString(i) );
  v.addElement(str);
}
}
while( rs.next() )
{
  str = new String[colNum];
for (int i=1;i<= colNum;i++)
  str[i] = rs.getString(i);
//  System.out.println( rs.getString(i) );
v.addElement(str);
}
}
catch( Exception e )
{
System.err.println( e );
}
if (v.size()==0)
return null;
else {
  Object [] o=v.toArray();
  String [][] out = new String[o.length][];
  for (int i=0;i<o.length;i++)
      out[i] = (String [])o[i];
  return out;
}
}

public static String [][] read_txt(File f, String delimit) {
  Vector v = new Vector();
  StringTokenizer  parser;
  try {
    FileReader reader = new FileReader(new File(f.getPath()));
   BufferedReader readIn = new BufferedReader(reader);
    String str;
   int col=0;
   while ((str = readIn.readLine()) != null) {
     if (str.length()>0) {
      parser = new StringTokenizer(str, delimit,true);
      v.addElement(parser);
     }
   }
   readIn.close();
   reader.close();

  }
  catch (FileNotFoundException e) {
    System.out.println("FileNotFoundException "+e.toString());
  }
  catch (IOException e) {
    System.out.println("IOException "+e.toString());
  }
  if (v.size()==0) return null;
  int rowNum =  v.size();
  String [][] data = new String[rowNum][];
  for (int i=0;i<rowNum;i++) {
    parser = (StringTokenizer)v.elementAt(i);
    data[i] = DataConversion.convertStringTokebizerToArray(parser,delimit);

  }
  return data;
}

public static String [][] read_csv(File f) {
  Vector v = new Vector();
  StringTokenizer  parser;
  try {
    FileReader reader = new FileReader(new File(f.getPath()));
   BufferedReader readIn = new BufferedReader(reader);
    String str;
   int col=0;
   while ((str = readIn.readLine()) != null) {
     if (str.length()>0) {
      parser = new StringTokenizer(str, ",",true);
      v.addElement(parser);
     }
   }
   readIn.close();
   reader.close();

  }
  catch (FileNotFoundException e) {
    System.out.println("FileNotFoundException "+e.toString());
  }
  catch (IOException e) {
    System.out.println("IOException "+e.toString());
  }
  if (v.size()==0) return null;
  int rowNum =  v.size();
  String [][] data = new String[rowNum][];
  for (int i=0;i<rowNum;i++) {
    parser = (StringTokenizer)v.elementAt(i);
    data[i] = DataConversion.convertStringTokebizerToArray(parser,",");

  }
  return data;
}
public static void getFileNames () {
  File [] f = FileIO.openMultiFiles(true);
  if (f == null) return;
  try {

FileWriter wrt = new FileWriter(new File(f[0].getParent(),"fileNameList.txt"));
PrintWriter out = new PrintWriter(wrt);

for (int i=0;i<f.length;i++) {
  out.println(f[i].getName());
}
wrt.flush();
wrt.close();

}
catch (FileNotFoundException ee) {
  System.out.println("FileNotFoundException "+ee.toString());
}
catch (IOException ee) {
  System.out.println("IOException "+ee.toString());
}

}
public static String getFileName_wo_extension(File f) {
  String fname = f.getName();
  java.util.StringTokenizer token = new java.util.StringTokenizer(fname,".");
  return token.nextToken();
}
public static void getSmallerFiles(File f,  int numLine) {
File outputFile = new File(f.getParent(),"Top_"+numLine+".txt");
try {
FileWriter wrt = new FileWriter(outputFile);
PrintWriter out = new PrintWriter(wrt);
FileReader  reader = new FileReader(f);
BufferedReader readIn = new BufferedReader(reader);
int i=0;
String line;
while( i< numLine && readIn.ready()) {
  line = readIn.readLine();
      out.print(line+"\n");
      i++;
  }

readIn.close();
reader.close();

wrt.flush();
wrt.close();

}

catch (FileNotFoundException e) {
System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
System.out.println("IOException "+e.toString());
}

}
/*
public void get_Gene_GO_mapping(File f_geneInfo,File f_gene2go) {
  File outputFile_go_mapping = new File(f_gene2go.getParent(),"GO_mapping.txt");
  File outputFile_gene_go_mapping = new File(f_gene2go.getParent(),"gene_GO_mapping.txt");
try {
FileWriter wrt_geneID_GO_id = new FileWriter(outputFile_gene_go_mapping);
PrintWriter out_geneID_GO_id = new PrintWriter(wrt_geneID_GO_id);

FileWriter wrt_GO_id_term = new FileWriter(outputFile_go_mapping);
PrintWriter out_GO_id_term = new PrintWriter(wrt_GO_id_term);

FileReader  reader_geneInfo = new FileReader(f_geneInfo);
BufferedReader readIn_geneInfo = new BufferedReader(reader_geneInfo);

FileReader  reader_gene2go = new FileReader(f_gene2go);
BufferedReader readIn_gene2go = new BufferedReader(reader_gene2go);

String line;
String [] readInLine;
String key_GO_id, value_GO_term;
String key_gene_id,value_GO_id;
Properties GO_Mapping = new Properties();
Properties gene_GO_Mapping = new Properties();
  if (readIn_gene2go.ready()) {
    line = readIn_gene2go.readLine();
  }
  TreeSet ts_GO_id_List = new TreeSet();
  while( readIn_gene2go.ready()) {
    line = readIn_gene2go.readLine();
    readInLine =  DataConversion.convertStringTokebizerToArray(new java.util.StringTokenizer(line,"\t",true),"\t");
    key_GO_id =  readInLine[2];
    if (!GO_Mapping.containsKey(key_GO_id)) {
    value_GO_term = readInLine[3]+"\t"+readInLine[5]+"\t"+readInLine[7];
    GO_Mapping.setProperty(key_GO_id,value_GO_term);
    ts_GO_id_List.add(key_GO_id);
    }
    key_gene_id = readInLine[1];
    value_GO_id = readInLine[2];
    if (gene_GO_Mapping.containsKey(key_gene_id)) {
      value_GO_id = gene_GO_Mapping.getProperty(key_gene_id) + "\t"+readInLine[2];
      gene_GO_Mapping.setProperty(key_gene_id,value_GO_id);
    }
    else {
      gene_GO_Mapping.setProperty(key_gene_id,value_GO_id);
    }
    }

    if (readIn_geneInfo.ready()) {
      line = readIn_geneInfo.readLine();
    }
    out_geneID_GO_id.print("GeneID\tSymbol\tdescription\tGO_ID\n");
    while( readIn_geneInfo.ready()) {
      line = readIn_geneInfo.readLine();
      readInLine =  DataConversion.convertStringTokebizerToArray(new java.util.StringTokenizer(line,"\t",true),"\t");
      value_GO_id = gene_GO_Mapping.getProperty(readInLine[1]);
      if (value_GO_id != null && !value_GO_id.trim().equals("") && !value_GO_id.trim().equals("null")) {
      out_geneID_GO_id.print(readInLine[1]+"\t"+readInLine[2] +"\t"+readInLine[8] +"\t");
      out_geneID_GO_id.print(value_GO_id+"\n");
      }
    }

int num = ts_GO_id_List.size();
Object [] o = ts_GO_id_List.toArray();
out_GO_id_term.print("GO_ID\tEvidence\tGO_term\tCategory\n");
for (int i=0;i<num;i++) {
  out_GO_id_term.print((String)o[i]+"\t"+ GO_Mapping.getProperty((String)o[i])+"\n");
}

readIn_geneInfo.close();
reader_geneInfo.close();
readIn_gene2go.close();
reader_gene2go.close();
wrt_geneID_GO_id.flush();
wrt_geneID_GO_id.close();
wrt_GO_id_term.flush();
wrt_GO_id_term.close();


}

catch (FileNotFoundException e) {
System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
System.out.println("IOException "+e.toString());
}

}
*/
/*
public void get_Unique_gene_list(File f) {
  File outputFile = new File(f.getParent(),"unique_id.txt");
try {
FileWriter wrt_unique_id = new FileWriter(outputFile);
PrintWriter out_unique_id = new PrintWriter(wrt_unique_id);


FileReader  reader = new FileReader(f);
BufferedReader readIn = new BufferedReader(reader);

String line;
String [] readInLine;
String key, value;
String headLine="";
Properties properties = new Properties();
  if (readIn.ready()) {
    headLine = readIn.readLine();
  }
  Vector v_unique_id_List = new Vector();
  while( readIn.ready()) {
    line = readIn.readLine();
    readInLine =  DataConversion.convertStringTokebizerToArray(new java.util.StringTokenizer(line,"\t",true),"\t");
    if (readInLine.length > 4) {
    key =  readInLine[4];
    if (!key.trim().equals("") &&  !properties.containsKey(key)) {
    value = line;
    properties.setProperty(key,value);
    v_unique_id_List.add(key);
    }
  }
    }


int num = v_unique_id_List.size();
Object [] o = v_unique_id_List.toArray();
out_unique_id.print(headLine+"\n");
for (int i=0;i<num;i++) {
  out_unique_id.print(properties.getProperty((String)o[i])+"\n");
}

readIn.close();
reader.close();
//out_unique_id.close();
//out_unique_id.close();

wrt_unique_id.flush();
wrt_unique_id.close();
}

catch (FileNotFoundException e) {
System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
System.out.println("IOException "+e.toString());
}

}
*/
/*

public void get_specie_Gene_GO_mapping(File f_gene_GO,File f_gene_specie) {
  File outputFile_specie_go_mapping = new File(f_gene_specie.getParent(),"GO_mapping.txt");
try {

FileWriter fileWriter = new FileWriter(outputFile_specie_go_mapping);
PrintWriter out = new PrintWriter(fileWriter);

FileReader  reader_gene_GO = new FileReader(f_gene_GO);
BufferedReader readIn_gene_GO = new BufferedReader(reader_gene_GO);

FileReader  reader_gene_specie = new FileReader(f_gene_specie);
BufferedReader readIn_gene_specie = new BufferedReader(reader_gene_specie);

String line;
String [] readInLine;
String key_gene_id,value_GO_id;
Properties properties = new Properties();
String headLine="";
  if (readIn_gene_GO.ready()) {
    headLine = readIn_gene_GO.readLine();
  }
//  TreeSet ts_GO_id_List = new TreeSet();
  String str;
  while( readIn_gene_GO.ready()) {
    line = readIn_gene_GO.readLine();
    readInLine =  DataConversion.convertStringTokebizerToArray(new java.util.StringTokenizer(line,"\t",true),"\t");
    key_gene_id =  readInLine[0];
    properties.setProperty(key_gene_id.trim(),line);
    }

    if (readIn_gene_specie.ready()) {
      line = readIn_gene_specie.readLine();
    }
    out.print(headLine+"\n");
    while( readIn_gene_specie.ready()) {
      line = readIn_gene_specie.readLine();
      readInLine =  DataConversion.convertStringTokebizerToArray(new java.util.StringTokenizer(line,"\t",true),"\t");
      if (readInLine.length>4) {
      str = readInLine[4];
      String value = properties.getProperty(str.trim());
      if (value != null && !value.equals("null"))
      out.print(properties.getProperty(str.trim())+"\n");
      }
    }

  //  out.flush();
  //  out.close();

readIn_gene_GO.close();
reader_gene_GO.close();
readIn_gene_specie.close();
reader_gene_specie.close();

fileWriter.flush();
fileWriter.close();


}

catch (FileNotFoundException e) {
System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
System.out.println("IOException "+e.toString());
}

}
*/
/*
private void getGeneList(Vector v,String id, String [][] str) {
  v.removeAllElements();
  String s="";
  String ss;
  for (int i=0;i<str.length;i++) {
    ss = getGeneList(id, str[i]);
    if (ss != null)
      v.add(ss);
  }
}
private String getGeneList(String id, String [] str) {
  if (str.length>3)
  for (int i=3;i<str.length;i++) {
     if (id.equals(str[i])) return str[0];
  }
  return null;
}
*/
/*
public void get_specie_GO_Gene_mapping(File f_gene_GO) {
  File outputFile_specie_go_mapping = new File(f_gene_GO.getParent(),"GO_gene_GO_mapping.txt");

try {

FileWriter fileWriter = new FileWriter(outputFile_specie_go_mapping);
PrintWriter out = new PrintWriter(fileWriter);

String [][] read_str = read_txt(f_gene_GO,"\t");
TreeSet GO_id_ts = new TreeSet();
for (int j=1;j<read_str.length;j++) {
    for (int i=3;i<read_str[j].length;i++) {
      GO_id_ts.add(read_str[j][i]);
    }
}
out.print("GO_ID\tGene_ID\n");

Object read_o [] = GO_id_ts.toArray();
Vector v=new Vector();
for (int i=0;i<read_o.length;i++) {
  getGeneList(v,(String)read_o[i], read_str);
  if (v.size()>0) {
    out.print((String)read_o[i]+"\t");
    int size = v.size();
    for (int j=0;j<size;j++) {
      if (j==size-1) {
        out.print((String)v.elementAt(j)+"\n");
      }
      else {
        out.print((String)v.elementAt(j)+"\t");
      }
    }
//     v.elementAt(j)
  }
}


fileWriter.flush();
fileWriter.close();


}

catch (FileNotFoundException e) {
System.out.println("FileNotFoundException "+e.toString());
}
catch (IOException e) {
System.out.println("IOException "+e.toString());
}

}
*/

}



