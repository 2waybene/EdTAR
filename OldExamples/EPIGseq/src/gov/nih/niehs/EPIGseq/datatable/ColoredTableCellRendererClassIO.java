package gov.nih.niehs.EPIGseq.datatable;


import javax.swing.table.*;
import java.awt.*;
import myutility.arrayObject.*;
/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class ColoredTableCellRendererClassIO extends DefaultTableCellRenderer
  {

  public void setValue(Object value) {
      if (value instanceof ColorDataIO) {
          ColorDataIO cvalue = (ColorDataIO)value;
          setForeground(cvalue.m_color);
          setText(cvalue.toString());
      }
      else if (value instanceof ColorStringClassIO) {
 //       System.out.println("ColorStringClassIO value class "+value.getClass().getName());
          ColorStringClassIO cvalue = (ColorStringClassIO)value;
          setForeground(cvalue.getColor());
          setText(cvalue.toString());
          setBackground(cvalue.getBackgroundColor());
      }
      else if (value instanceof ColumnNameClassIO) {
 //       System.out.println("ColumnNameClassIO value class "+value.getClass().getName());
          ColumnNameClassIO cvalue = (ColumnNameClassIO)value;
          setForeground(cvalue.getColor());
          setText(cvalue.toString());
          setBackground(cvalue.getBackgroundColor());
      }
      else {
 //       System.out.println("value class "+value.getClass().getName());
          super.setValue(value);
      }
//      setBackground(Color.cyan);
  }

}
