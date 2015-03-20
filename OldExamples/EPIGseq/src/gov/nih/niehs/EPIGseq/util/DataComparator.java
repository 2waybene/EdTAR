package gov.nih.niehs.EPIGseq.util;

import java.util.*;
import gov.nih.niehs.EPIGseq.datatable.*;

import myutility.arrayObject.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class DataComparator  implements Comparator {

  protected int     m_sortCol;
  protected boolean m_sortAsc;
  protected int m_sortSelect;

  public DataComparator(int sortCol, boolean sortAsc, int sortSelect) {
      m_sortCol = sortCol;
      m_sortAsc = sortAsc;
      m_sortSelect = sortSelect;
  }
//    public Object clone() {
//        GeneComparator g = new GeneComparator(this.m_sortCol, this.m_sortAsc, this.m_sortSelect);
//        return g;
//    }
  public int compare(Object o1, Object o2) {
      if(!(o1 instanceof RowDataIO) || !(o2 instanceof RowDataIO)) {
          return 0;
      }
      Object obj1 = ((RowDataIO)o1).getData(m_sortCol);
      Object obj2 = ((RowDataIO)o2).getData(m_sortCol);
      int result = 0;
      float d1, d2;
      if (m_sortSelect == 1) {
                  String str1 = obj1.toString();
                  String str2 = obj2.toString();
                  try {
                  d1 = Float.parseFloat(str1);
                  d2 = Float.parseFloat(str2);
                  result = d1<d2 ? -1 : (d1>d2 ? 1 : 0);
                  }
                  catch (NumberFormatException e) {
                   result = str1.compareTo(str2);
                  }
      }
      else {
                  String str1 = obj1.toString();
                  String str2 = obj2.toString();
                  result = str1.compareTo(str2);
      }

      if (!m_sortAsc)
          result = -result;
      return result;
  }

  public boolean equals(Object obj) {
	  if (obj instanceof DataComparator) {
          DataComparator compObj = (DataComparator)obj;
          return (compObj.m_sortCol==m_sortCol) &&
          (compObj.m_sortAsc==m_sortAsc);
      }
      return false;
  }
}
             
