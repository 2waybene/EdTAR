package gov.nih.niehs.EPIGseq.datatable;

import myutility.arrayObject.*;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class ColumnNameClassIO extends ColorStringClassIO {

  private int     width;
  private int     alignment;
  private int colNum;
  private String originalName;
  public ColumnNameClassIO(String _name, int _width, int _alignment, int col) {
//    columnName = new ColorStringClassIO(_name);
    setData(_name);
    originalName = _name;
    colNum = col;
      width = _width;
      alignment = _alignment;
  }
  public String getColumnName() {
    return (String)getData();
  }
//  public String getColumnName() {
//    return (String)getData();
//  }
  public String undoColumnName() {
    return originalName;
  }
  public void setColumnName(String s) {
    setData(s);
  }
  public int getWidth() {
    return width;
  }
  public void setWidth(int d) {
    width = d;
  }
  public int getAlignment() {
    return alignment;
  }
  public void setAlignment(int d) {
    alignment = d;
  }

}