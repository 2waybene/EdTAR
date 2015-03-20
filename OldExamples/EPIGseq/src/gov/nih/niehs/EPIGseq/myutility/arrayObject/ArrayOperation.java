package gov.nih.niehs.EPIGseq.myutility.arrayObject;


/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */
import java.util.Random;
import myutility.numerical.*;

/**
 * The class <code>Array</code> defines a set of static methods
 * designed to manipulate one- and two-dimensional arrays in a
 * convenient fashion.  These methods permit a programmer to treat
 * an array as an entity, and to perform operations on an entire
 * array with a single method invocation.
 * <p>
 * Various methods in this class support data of types <code>int</code>,
 * <code>long</code>, <code>float</code>, <code>double</code>, and
 * <code>Complex</code>, although not all data types are supported for
 * all functions.  Consult the detailed listings below to determine which
 * data types are supported by each method.
 * <p>
 * The methods in this class fall into several categories, as follows:
 * <p>
 *    1.  <b>Calculational Methods</b>
 * <p>
 * These methods perform calculations on the elements of an array.
 * Examples include
 * <code>add</code>, <code>sub</code>, <code>mul</code>, <code>div</code>,
 * <code>sum</code>, <code>product</code>, <code>dotProduct</code>,
 * <code>sin</code>, <code>sinc</code>, <code>sind</code>, <code>sinh</code>,
 * <code>cos</code>, <code>cosd</code>, <code>cosh</code>,
 * <code>tan</code>, <code>tand</code>, <code>tanh</code>,
 * <code>asin</code>, <code>asind</code>, <code>asinh</code>,
 * <code>acos</code>, <code>acosd</code>, <code>acosh</code>,
 * <code>atan</code>, <code>atand</code>, <code>atanh</code>,
 * <code>abs</code>, <code>exp</code>, <code>log</code>, <code>log10</code>,
 * and <code>pow</code>.
 * <p>
 *    2.  <b>Inquiry Methods</b>
 * <p>
 * These methods extract information from an array.  Examples include
 * <code>maxAbs</code>, <code>maxAbsLoc</code>, <code>maxVal</code>,
 * <code>maxLoc</code>, <code>minVal</code>, and <code>minLoc</code>.
 * <p>
 *    3.  <b>Relational Methods</b>
 * <p>
 * These methods perform relational comparisons on the data in an array,
 * producing a <code>boolean</code> result.  Examples include
 * <code>isGreaterThan</code>, <code>isGreaterThanOrEqual</code>,
 * <code>isLessThan</code>, <code>isLessThanOrEqual</code>,
 * <code>isEqual</code>, and <code>isNotEqual</code>.
 * <p>
 *    4.  <b>Logical Methods</b>
 * <p>
 * These methods manipulate <code>boolean</code> arrays, which can be
 * produced by the relational methods described above.  Examples include
 * <code>all</code>, <code>any</code>, <code>count</code>,
 * <code>and</code>, <code>or</code>, <code>xor</code>, and <code>not</code>.
 * The resulting <code>boolean</code> arrays can be used as a mask to control
 * certain operations.
 * <p>
 *    5.  <b>Random Number Methods</b>
 * <p>
 * These methods manipulate return arrays of random values.  Examples include
 * <code>random</code> and <code>randomGaussian</code>.
 *
 * @author  S. J. Chapman
 * @version 0.90, 07/15/98
 */

public final class ArrayOperation {

   //*************************************************
   // Constructors
   //*************************************************

   /**
    * Create an inaccessible constructor, so that this class cannot be
    * instantiated.
    */
   private ArrayOperation() {}

   //******************************************************
   //  abs methods
   //******************************************************

   /**
    * Returns the absolute value of the elements in a 1-D int array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static int[] abs ( int a[] ) {
      int abs[] = new int[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = Math.abs(a[i]);
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 1-D long array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static long[] abs ( long a[] ) {
      long abs[] = new long[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = (long) Math.abs(a[i]);
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 1-D float array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static float[] abs ( float a[] ) {
      float abs[] = new float[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = (float) Math.abs(a[i]);
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 1-D double array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static double[] abs ( double a[] ) {
      double abs[] = new double[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = Math.abs(a[i]);
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 1-D Complex array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static double[] abs ( Complex a[] ) {
      double abs[] = new double[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = Complex.abs(a[i]);
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 2-D int array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static int[][] abs ( int a[][] ) {
      int abs[][] = new int[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = new int[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            abs[i][j] = Math.abs(a[i][j]);
         }
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 2-D long array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static long[][] abs ( long a[][] ) {
      long abs[][] = new long[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = new long[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            abs[i][j] = Math.abs(a[i][j]);
         }
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 2-D float array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static float[][] abs ( float a[][] ) {
      float abs[][] = new float[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = new float[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            abs[i][j] = (float) Math.abs(a[i][j]);
         }
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 2-D double array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static double[][] abs ( double a[][] ) {
      double abs[][] = new double[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            abs[i][j] = Math.abs(a[i][j]);
         }
      }
      return abs;
   }


   /**
    * Returns the absolute value of the elements in a 2-D Complex array.
    *
    * @param   a   input array.
    * @return  an array whose elements are the absolute value of the
    *          elements in <tt>a</tt>.
    */
   public static double[][] abs ( Complex a[][] ) {
      double abs[][] = new double[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         abs[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            abs[i][j] = Complex.abs(a[i][j]);
         }
      }
      return abs;
   }


   //******************************************************
   //  add element-by-element methods
   //******************************************************

   /**
    * Returns the element-by-element sum of two 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] add ( int a[], int b[] ) {

      int add[] = new int[a.length];

      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            add[i] = a[i] + b[i];
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] add ( long a[], long b[] ) {

      long add[] = new long[a.length];

      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            add[i] = a[i] + b[i];
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] add ( float a[], float b[] ) {

      float add[] = new float[a.length];

      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            add[i] = (float) (a[i] + b[i]);
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] add ( double a[], double b[] ) {

      double add[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            add[i] = a[i] + b[i];
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 1-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] add ( Complex a[], Complex b[] ) {

      Complex add[] = new Complex[a.length];

      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            add[i] = a[i].add(b[i]);
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] add ( int a[][], int b[][] ) {
      int add[][] = new int[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "add: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            add[i] = new int[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               add[i][j] = a[i][j] + b[i][j];
            }
         }
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] add ( long a[][], long b[][] ) {
      long add[][] = new long[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "add: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            add[i] = new long[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               add[i][j] = a[i][j] + b[i][j];
            }
         }
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] add ( float a[][], float b[][] ) {
      float add[][] = new float[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "add: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            add[i] = new float[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               add[i][j] = a[i][j] + b[i][j];
            }
         }
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] add ( double a[][], double b[][] ) {
      double add[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "add: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            add[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               add[i][j] = a[i][j] + b[i][j];
            }
         }
      }
      return add;
   }


   /**
    * Returns the element-by-element sum of two 2-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] add ( Complex a[][], Complex b[][] ) {
      Complex add[][] = new Complex[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "add: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "add: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            add[i] = new Complex[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               add[i][j] = a[i][j].add(b[i][j]);
            }
         }
      }
      return add;
   }


   //******************************************************
   //  add array-to-scalar methods
   //******************************************************

   /**
    * Adds a scalar value to a 1-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] add ( int a[], int b ) {
      int add[] = new int[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = a[i] + b;

      return add;
   }


   /**
    * Adds a scalar value to a 1-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] add ( long a[], long b ) {

      long add[] = new long[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = (long) (a[i] + b);

      return add;
   }


   /**
    * Adds a scalar value to a 1-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] add ( float a[], float b ) {

      float add[] = new float[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = (float) (a[i] + b);

      return add;
   }


   /**
    * Adds a scalar value to a 1-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] add ( double a[], double b ) {

      double add[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = a[i] + b;

      return add;
   }


   /**
    * Adds a scalar value to a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
/*   public static Complex[] add ( Complex a[], double b ) {

      Complex add[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = a[i].add(b);

      return add;
   }
*/
   public static Complex[] add ( Complex a[], float b ) {

      Complex add[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = a[i].add(b);

      return add;
   }

   /**
    * Adds a scalar value to a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] add ( Complex a[], Complex b ) {

      Complex add[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         add[i] = a[i].add(b);

      return add;
   }


   /**
    * Adds a scalar value to a 2-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] add ( int a[][], int b ) {
      int add[][] = new int[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new int[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j] + b;
         }
      }
      return add;
   }


   /**
    * Adds a scalar value to a 2-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] add ( long a[][], long b ) {
      long add[][] = new long[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new long[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j] + b;
         }
      }
      return add;
   }


   /**
    * Adds a scalar value to a 2-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] add ( float a[][], float b ) {
      float add[][] = new float[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new float[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j] + b;
         }
      }
      return add;
   }


   /**
    * Adds a scalar value to a 2-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] add ( double a[][], double b ) {
      double add[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j] + b;
         }
      }
      return add;
   }


   /**
    * Adds a scalar value to a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] add ( Complex a[][], float b ) {
      Complex add[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j].add(b);
         }
      }
      return add;
   }


   /**
    * Adds a scalar value to a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] add ( Complex a[][], Complex b ) {
      Complex add[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         add[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            add[i][j] = a[i][j].add(b);
         }
      }
      return add;
   }


   //******************************************************
   //  all methods
   //******************************************************

   /**
    * Returns <code>true</code> if all elements of an array are <code>true</code>.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean all ( boolean a[] ) {
      boolean result = true;
      for ( int i = 0; i < a.length; i++ ) {
         if ( !a[i] ) {
            result = false;
            break;
         }
      }
      return result;
   }

   /**
    * Returns <code>true</code> if all elements of an array are <code>true</code>.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean all ( boolean a[][] ) {
      boolean result = true;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
           if ( !a[i][j] ) {
              result = false;
              break;
           }
         }
      }
      return result;
   }


   //******************************************************
   //  and methods
   //******************************************************

   /**
    * Returns the element-by-element logical <code>and</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[] and ( boolean a[], boolean b[] ) {
      boolean and[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "and: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         and[i] = a[i] && b[i];
      }
      return and;
   }


   /**
    * Returns the element-by-element logical <code>and</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[][] and ( boolean a[][], boolean b[][] ) {
      boolean and[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "and: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "and: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            and[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               and[i][j] = a[i][j] && b[i][j];
            }
         }
      }
      return and;
   }


   //******************************************************
   //  any methods
   //******************************************************

   /**
    * Returns <code>true</code> if any elements of an array are <code>true</code>.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean any ( boolean a[] ) {
      boolean result = false;
      for ( int i = 0; i < a.length; i++ ) {
         if ( a[i] ) {
            result = true;
            break;
         }
      }
      return result;
   }


   /**
    * Returns <code>true</code> if any elements of an array are <code>true</code>.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean any ( boolean a[][] ) {
      boolean result = false;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if ( a[i][j] ) {
               result = true;
               break;
            }
         }
      }
      return result;
   }


   //******************************************************
   //  count methods
   //******************************************************

   /**
    * Returns the number of <code>true</code> elements in an array.
    *
    * @param   a   input array.
    * @return  the number of <code>true</code> elements
    */
   public static int count ( boolean a[] ) {
      int count = 0;
      for ( int i = 0; i < a.length; i++ ) {
         if ( a[i] )
            count++;
      }
      return count;
   }


   /**
    * Returns the number of <code>true</code> elements in an array.
    *
    * @param   a   input array.
    * @return  the number of <code>true</code> elements
    */
   public static int count ( boolean a[][] ) {
      int count = 0;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if ( a[i][j] )
               count++;
         }
      }
      return count;
   }

   //******************************************************
   //  element-by-element division methods
   //******************************************************

   /**
    * Returns the element-by-element division of two 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i] / b[i]</tt>
    */
   public static int[] div ( int a[], int b[] ) {

      int div[] = new int[a.length];

      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            div[i] = a[i] / b[i];
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i] / b[i]</tt>
    */
   public static long[] div ( long a[], long b[] ) {

      long div[] = new long[a.length];

      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            div[i] = a[i] / b[i];
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i] / b[i]</tt>
    */
   public static float[] div ( float a[], float b[] ) {

      float div[] = new float[a.length];

      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            div[i] = (float) (a[i] / b[i]);
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i] / b[i]</tt>
    */
   public static double[] div ( double a[], double b[] ) {

      double div[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            div[i] = a[i] / b[i];
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 1-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i] / b[i]</tt>
    */
   public static Complex[] div ( Complex a[], Complex b[] ) {

      Complex div[] = new Complex[a.length];

      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            div[i] = a[i].div(b[i]);
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i][j] / b[i][j]</tt>
    */
   public static int[][] div ( int a[][], int b[][] ) {
      int div[][] = new int[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "div: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            div[i] = new int[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               div[i][j] = a[i][j] / b[i][j];
            }
         }
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i][j] / b[i][j]</tt>
    */
   public static long[][] div ( long a[][], long b[][] ) {
      long div[][] = new long[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "div: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            div[i] = new long[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               div[i][j] = a[i][j] / b[i][j];
            }
         }
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i][j] / b[i][j]</tt>
    */
   public static float[][] div ( float a[][], float b[][] ) {
      float div[][] = new float[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "div: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            div[i] = new float[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               div[i][j] = a[i][j] / b[i][j];
            }
         }
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i][j] / b[i][j]</tt>
    */
   public static double[][] div ( double a[][], double b[][] ) {
      double div[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "div: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            div[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               div[i][j] = a[i][j] / b[i][j];
            }
         }
      }
      return div;
   }


   /**
    * Returns the element-by-element division of two 2-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <tt>a[i][j] / b[i][j]</tt>
    */
   public static Complex[][] div ( Complex a[][], Complex b[][] ) {
      Complex div[][] = new Complex[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "div: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "div: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            div[i] = new Complex[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               div[i][j] = a[i][j].div(b[i][j]);
            }
         }
      }
      return div;
   }


   //******************************************************
   //  divide array-by-scalar methods
   //******************************************************

   /**
    * Returns the division of a 1-D int array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static int[] div ( int a[], int b ) {

      int div[] = new int[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = a[i] / b;

      return div;
   }


   /**
    * Returns the division of a 1-D long array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static long[] div ( long a[], long b ) {

      long div[] = new long[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = a[i] / b;

      return div;
   }


   /**
    * Returns the division of a 1-D float array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static float[] div ( float a[], float b ) {

      float div[] = new float[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = (float) (a[i] / b);

      return div;
   }


   /**
    * Returns the division of a 1-D double array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static double[] div ( double a[], double b ) {

      double div[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = a[i] / b;

      return div;
   }


   /**
    * Returns the division of a 1-D Complex array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static Complex[] div ( Complex a[], float b ) {

      Complex div[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = a[i].div(b);

      return div;
   }


   /**
    * Returns the division of a 1-D Complex array by a scalar.
    *
    * @param   a   input array.
    * @param   b   input scalar.
    * @return  an array whose elements are <tt>a[i] / b</tt>
    */
   public static Complex[] div ( Complex a[], Complex b ) {

      Complex div[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         div[i] = a[i].div(b);

      return div;
   }


   //******************************************************
   //  dotProduct methods
   //******************************************************

   /**
    * Returns the dot product of two 1-D arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the dot product of <tt>a</tt> and <tt>b</tt>
    */
   public static int dotProduct ( int a[], int b[] ) {

      int product = 0;
      if ( a.length != b.length ) {
         String s = "dotProduct: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            product += a[i] * b[i];
      }
      return product;
   }


   /**
    * Returns the dot product of two 1-D arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the dot product of <tt>a</tt> and <tt>b</tt>
    */
   public static long dotProduct ( long a[], long b[] ) {

      long product = 0;
      if ( a.length != b.length ) {
         String s = "dotProduct: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            product += a[i] * b[i];
      }
      return product;
   }


   /**
    * Returns the dot product of two 1-D arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the dot product of <tt>a</tt> and <tt>b</tt>
    */
   public static float dotProduct ( float a[], float b[] ) {

      float product = 0;
      if ( a.length != b.length ) {
         String s = "dotProduct: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            product += a[i] * b[i];
      }
      return product;
   }


   /**
    * Returns the dot product of two 1-D arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the dot product of <tt>a</tt> and <tt>b</tt>
    */
   public static double dotProduct ( double a[], double b[] ) {

      double product = 0;
      if ( a.length != b.length ) {
         String s = "dotProduct: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            product += a[i] * b[i];
      }
      return product;
   }


   /**
    * Returns the dot product of two 1-D arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the dot product of <tt>a</tt> and <tt>b</tt>
    */
   public static Complex dotProduct ( Complex a[], Complex b[] ) {

      Complex product = new Complex(0.0f, 0.0f);
      if ( a.length != b.length ) {
         String s = "dotProduct: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            product = product.add( a[i].mul(b[i]) );
      }
      return product;
   }


   //******************************************************
   //  isEqual methods
   //******************************************************

   /**
    * Returns the element-by-element "==" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isEqual ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] == b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isEqual ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] == b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isEqual ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] == b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isEqual ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] == b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 1-D <code>Complex</code> arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isEqual ( Complex a[], Complex b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i].equals(b[i]);
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> == <tt>b[i][j]</tt>.
    */
   public static boolean[][] isEqual ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] == b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> == <tt>b[i][j]</tt>.
    */
   public static boolean[][] isEqual ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] == b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> == <tt>b[i][j]</tt>.
    */
   public static boolean[][] isEqual ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] == b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> == <tt>b[i][j]</tt>.
    */
   public static boolean[][] isEqual ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] == b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "==" comparision of two
    * 2-D <code>Complex</code> arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> == <tt>b[i][j]</tt>.
    */
   public static boolean[][] isEqual ( Complex a[][], Complex b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j].equals(b[i][j]);
            }
         }
      }
      return res;
   }


   //******************************************************
   //  isGreaterThan methods
   //******************************************************

   /**
    * Returns the element-by-element ">" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> > <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThan ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] > b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> > <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThan ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] > b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> > <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThan ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] > b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> > <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThan ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] > b[i];
      }
      return res;
   }


   /**
    * Returns the the result of comparing a double array to a
    * specified constant.
    *
    * @param   a   input array.
    * @param   b   input value.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> > <tt>b</tt>.
    */
   public static boolean[] isGreaterThan ( double a[], double b ) {

      boolean res[] = new boolean[a.length];

      for ( int i = 0; i < a.length; i++ )
         res[i] = a[i] > b;
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> > <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThan ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] > b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> > <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThan ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] > b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> > <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThan ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] > b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> > <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThan ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] > b[i][j];
            }
         }
      }
      return res;
   }


   //******************************************************
   //  isGreaterThanOrEqual methods
   //******************************************************

   /**
    * Returns the element-by-element ">=" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> >= <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThanOrEqual ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] >= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> >= <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThanOrEqual ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] >= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> >= <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThanOrEqual ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] >= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> >= <tt>b[i]</tt>.
    */
   public static boolean[] isGreaterThanOrEqual ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] >= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> >= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThanOrEqual ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] >= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> >= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThanOrEqual ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] >= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> >= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThanOrEqual ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] >= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element ">=" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> >= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isGreaterThanOrEqual ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isGreaterThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isGreaterThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] >= b[i][j];
            }
         }
      }
      return res;
   }


   //******************************************************
   //  isLessThan methods
   //******************************************************

   /**
    * Returns the element-by-element "<" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> < <tt>b[i]</tt>.
    */
   public static boolean[] isLessThan ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] < b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> < <tt>b[i]</tt>.
    */
   public static boolean[] isLessThan ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] < b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> < <tt>b[i]</tt>.
    */
   public static boolean[] isLessThan ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] < b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> < <tt>b[i]</tt>.
    */
   public static boolean[] isLessThan ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] < b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> < <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThan ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] < b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> < <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThan ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] < b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> < <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThan ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] < b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> < <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThan ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThan: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThan: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] < b[i][j];
            }
         }
      }
      return res;
   }


   //******************************************************
   //  isLessThanOrEqual methods
   //******************************************************

   /**
    * Returns the element-by-element "<=" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> <= <tt>b[i]</tt>.
    */
   public static boolean[] isLessThanOrEqual ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] <= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> <= <tt>b[i]</tt>.
    */
   public static boolean[] isLessThanOrEqual ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] <= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> <= <tt>b[i]</tt>.
    */
   public static boolean[] isLessThanOrEqual ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] <= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> <= <tt>b[i]</tt>.
    */
   public static boolean[] isLessThanOrEqual ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] <= b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> <= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThanOrEqual ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] <= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> <= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThanOrEqual ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] <= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> <= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThanOrEqual ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] <= b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "<=" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> <= <tt>b[i][j]</tt>.
    */
   public static boolean[][] isLessThanOrEqual ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isLessThanOrEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isLessThanOrEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] <= b[i][j];
            }
         }
      }
      return res;
   }


   //******************************************************
   //  isNotEqual methods
   //******************************************************

   /**
    * Returns the element-by-element "!=" comparision of two
    * 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> != <tt>b[i]</tt>.
    */
   public static boolean[] isNotEqual ( int a[], int b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] != b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> != <tt>b[i]</tt>.
    */
   public static boolean[] isNotEqual ( long a[], long b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] != b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> != <tt>b[i]</tt>.
    */
   public static boolean[] isNotEqual ( float a[], float b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] != b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> != <tt>b[i]</tt>.
    */
   public static boolean[] isNotEqual ( double a[], double b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = a[i] != b[i];
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 1-D <code>Complex</code> arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i]</tt> == <tt>b[i]</tt>.
    */
   public static boolean[] isNotEqual ( Complex a[], Complex b[] ) {

      boolean res[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "isEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            res[i] = ! a[i].equals(b[i]);
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> != <tt>b[i][j]</tt>.
    */
   public static boolean[][] isNotEqual ( int a[][], int b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isNotEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] != b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> != <tt>b[i][j]</tt>.
    */
   public static boolean[][] isNotEqual ( long a[][], long b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isNotEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] != b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> != <tt>b[i][j]</tt>.
    */
   public static boolean[][] isNotEqual ( float a[][], float b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isNotEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] != b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> != <tt>b[i][j]</tt>.
    */
   public static boolean[][] isNotEqual ( double a[][], double b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isNotEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = a[i][j] != b[i][j];
            }
         }
      }
      return res;
   }


   /**
    * Returns the element-by-element "!=" comparision of two
    * 2-D <code>Complex</code> arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are <code>true</code>
    *          if <tt>a[i][j]</tt> != <tt>b[i][j]</tt>.
    */
   public static boolean[][] isNotEqual ( Complex a[][], Complex b[][] ) {
      boolean res[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "isNotEqual: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "isNotEqual: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            res[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               res[i][j] = ! a[i][j].equals(b[i][j]);
            }
         }
      }
      return res;
   }


   //******************************************************
   //  maxAbs methods
   //******************************************************


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i]</tt>
    */
   public static int maxAbs ( int a[] ) {
      int iloc = 0;
      int maxAbs = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxAbs) {
            iloc = i;
            maxAbs = Math.abs(a[i]);
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i]</tt>
    */
   public static long maxAbs ( long a[] ) {
      int iloc = 0;
      long maxAbs = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxAbs) {
            iloc = i;
            maxAbs = Math.abs(a[i]);
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i]</tt>
    */
   public static float maxAbs ( float a[] ) {
      int iloc = 0;
      float maxAbs = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxAbs) {
            iloc = i;
            maxAbs = Math.abs(a[i]);
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i]</tt>
    */
   public static double maxAbs ( double a[] ) {
      int iloc = 0;
      double maxAbs = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxAbs) {
            iloc = i;
            maxAbs = Math.abs(a[i]);
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i]</tt>
    */
   public static double maxAbs ( Complex a[] ) {
      int iloc = 0;
     double test;
      double maxAbs = Complex.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
        test = Complex.abs(a[i]);
         if (test > maxAbs) {
            iloc = i;
            maxAbs = test;
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i][j]</tt>
    */
   public static int maxAbs ( int a[][] ) {
      int iloc = 0;
      int maxAbs = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxAbs) {
               iloc = i;
               maxAbs = Math.abs(a[i][j]);
            }
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i][j]</tt>
    */
   public static long maxAbs ( long a[][] ) {
      int iloc = 0;
      long maxAbs = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxAbs) {
               iloc = i;
               maxAbs = Math.abs(a[i][j]);
            }
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i][j]</tt>
    */
   public static float maxAbs ( float a[][] ) {
      int iloc = 0;
      float maxAbs = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxAbs) {
               iloc = i;
               maxAbs = Math.abs(a[i][j]);
            }
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i][j]</tt>
    */
   public static double maxAbs ( double a[][] ) {
      int iloc = 0;
      double maxAbs = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxAbs) {
               iloc = i;
               maxAbs = Math.abs(a[i][j]);
            }
         }
      }
      return maxAbs;
   }


   /**
    * Returns the maximum absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum absolute value of <tt>a[i][j]</tt>
    */
   public static double maxAbs ( Complex a[][] ) {
      int iloc = 0;
      double maxAbs = Complex.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Complex.abs(a[i][j]) > maxAbs) {
               iloc = i;
               maxAbs = Complex.abs(a[i][j]);
            }
         }
      }
      return maxAbs;
   }


   //******************************************************
   //  maxAbsLoc methods
   //******************************************************

   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i]</tt>
    */
   public static int maxAbsLoc ( int a[] ) {
      int iloc = 0;
      int maxVal = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxVal) {
            iloc = i;
            maxVal = Math.abs(a[i]);
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i]</tt>
    */
   public static int maxAbsLoc ( long a[] ) {
      int iloc = 0;
      long maxVal = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxVal) {
            iloc = i;
            maxVal = Math.abs(a[i]);
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i]</tt>
    */
   public static int maxAbsLoc ( float a[] ) {
      int iloc = 0;
      float maxVal = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxVal) {
            iloc = i;
            maxVal = Math.abs(a[i]);
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i]</tt>
    */
   public static int maxAbsLoc ( double a[] ) {
      int iloc = 0;
      double maxVal = Math.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Math.abs(a[i]) > maxVal) {
            iloc = i;
            maxVal = Math.abs(a[i]);
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i]</tt>
    */
   public static int maxAbsLoc ( Complex a[] ) {
      int iloc = 0;
      double maxVal = Complex.abs(a[0]);
      for ( int i = 0; i < a.length; i++ ) {
         if (Complex.abs(a[i]) > maxVal) {
            iloc = i;
            maxVal = Complex.abs(a[i]);
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i][j]</tt>
    */
   public static int[] maxAbsLoc ( int a[][] ) {
      int iloc[] = {0, 0};
      int maxVal = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = Math.abs(a[i][j]);
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i][j]</tt>
    */
   public static int[] maxAbsLoc ( long a[][] ) {
      int iloc[] = {0, 0};
      long maxVal = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = Math.abs(a[i][j]);
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i][j]</tt>
    */
   public static int[] maxAbsLoc ( float a[][] ) {
      int iloc[] = {0, 0};
      float maxVal = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = Math.abs(a[i][j]);
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i][j]</tt>
    */
   public static int[] maxAbsLoc ( double a[][] ) {
      int iloc[] = {0, 0};
      double maxVal = Math.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Math.abs(a[i][j]) > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = Math.abs(a[i][j]);
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * absolute value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          absolute value of all <tt>a[i][j]</tt>
    */
   public static int[] maxAbsLoc ( Complex a[][] ) {
      int iloc[] = {0, 0};
      double maxVal = Complex.abs(a[0][0]);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (Complex.abs(a[i][j]) > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = Complex.abs(a[i][j]);
            }
         }
      }
      return iloc;
   }


   //******************************************************
   //  maxLoc methods
   //******************************************************

   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int maxLoc ( int a[] ) {
      int iloc = 0;
      int maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            iloc = i;
            maxVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int maxLoc ( long a[] ) {
      int iloc = 0;
      long maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            iloc = i;
            maxVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int maxLoc ( float a[] ) {
      int iloc = 0;
      float maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            iloc = i;
            maxVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int maxLoc ( double a[] ) {
      int iloc = 0;
      double maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            iloc = i;
            maxVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] maxLoc ( int a[][] ) {
      int iloc[] = {0, 0};
      int maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] maxLoc ( long a[][] ) {
      int iloc[] = {0, 0};
      long maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] maxLoc ( float a[][] ) {
      int iloc[] = {0, 0};
      float maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] maxLoc ( double a[][] ) {
      int iloc[] = {0, 0};
      double maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               iloc[0] = i;
               iloc[1] = j;
               maxVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   //******************************************************
   //  maxVal methods
   //******************************************************

   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static int maxVal ( int a[] ) {
      int maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            maxVal = a[i];
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static long maxVal ( long a[] ) {
      long maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            maxVal = a[i];
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static float maxVal ( float a[] ) {
      float maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            maxVal = a[i];
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static double maxVal ( double a[] ) {
      double maxVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] > maxVal) {
            maxVal = a[i];
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static int maxVal ( int a[][] ) {
      int maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               maxVal = a[i][j];
            }
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static long maxVal ( long a[][] ) {
      long maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               maxVal = a[i][j];
            }
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static float maxVal ( float a[][] ) {
      float maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               maxVal = a[i][j];
            }
         }
      }
      return maxVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static double maxVal ( double a[][] ) {
      double maxVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] > maxVal) {
               maxVal = a[i][j];
            }
         }
      }
      return maxVal;
   }


   //******************************************************
   //  minLoc methods
   //******************************************************

   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int minLoc ( int a[] ) {
      int iloc = 0;
      int minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            iloc = i;
            minVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int minLoc ( long a[] ) {
      int iloc = 0;
      long minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            iloc = i;
            minVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int minLoc ( float a[] ) {
      int iloc = 0;
      float minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            iloc = i;
            minVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i]</tt>
    */
   public static int minLoc ( double a[] ) {
      int iloc = 0;
      double minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            iloc = i;
            minVal = a[i];
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] minLoc ( int a[][] ) {
      int iloc[] = {0, 0};
      int minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               iloc[0] = i;
               iloc[1] = j;
               minVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] minLoc ( long a[][] ) {
      int iloc[] = {0, 0};
      long minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               iloc[0] = i;
               iloc[1] = j;
               minVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] minLoc ( float a[][] ) {
      int iloc[] = {0, 0};
      float minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               iloc[0] = i;
               iloc[1] = j;
               minVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   /**
    * Returns the index of the element having the maximum
    * value of any element in an array.
    *
    * @param   a   input array.
    * @return  the index of the element having the maximum
    *          value of all <tt>a[i][j]</tt>
    */
   public static int[] minLoc ( double a[][] ) {
      int iloc[] = {0, 0};
      double minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               iloc[0] = i;
               iloc[1] = j;
               minVal = a[i][j];
            }
         }
      }
      return iloc;
   }


   //******************************************************
   //  minVal methods
   //******************************************************

   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static int minVal ( int a[] ) {
      int minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            minVal = a[i];
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static long minVal ( long a[] ) {
      long minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            minVal = a[i];
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static float minVal ( float a[] ) {
      float minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            minVal = a[i];
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i]</tt>
    */
   public static double minVal ( double a[] ) {
      double minVal = a[0];
      for ( int i = 0; i < a.length; i++ ) {
         if (a[i] < minVal) {
            minVal = a[i];
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static int minVal ( int a[][] ) {
      int minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               minVal = a[i][j];
            }
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static long minVal ( long a[][] ) {
      long minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               minVal = a[i][j];
            }
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static float minVal ( float a[][] ) {
      float minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               minVal = a[i][j];
            }
         }
      }
      return minVal;
   }


   /**
    * Returns the maximum value of any element in an array.
    *
    * @param   a   input array.
    * @return  the maximum value of <tt>a[i][j]</tt>
    */
   public static double minVal ( double a[][] ) {
      double minVal = a[0][0];
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            if (a[i][j] < minVal) {
               minVal = a[i][j];
            }
         }
      }
      return minVal;
   }


   //******************************************************
   //  multiply element-by-element methods
   //******************************************************

   /**
    * Returns the element-by-element product of two 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] mul ( int a[], int b[] ) {

      int mul[] = new int[a.length];

      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            mul[i] = a[i] * b[i];
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] mul ( long a[], long b[] ) {

      long mul[] = new long[a.length];

      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            mul[i] = a[i] * b[i];
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] mul ( float a[], float b[] ) {

      float mul[] = new float[a.length];

      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            mul[i] = (float) (a[i] * b[i]);
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] mul ( double a[], double b[] ) {

      double mul[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            mul[i] = a[i] * b[i];
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 1-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] mul ( Complex a[], Complex b[] ) {

      Complex mul[] = new Complex[a.length];

      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            mul[i] = a[i].mul(b[i]);
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] mul ( int a[][], int b[][] ) {
      int mul[][] = new int[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "mul: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            mul[i] = new int[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               mul[i][j] = a[i][j] * b[i][j];
            }
         }
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] mul ( long a[][], long b[][] ) {
      long mul[][] = new long[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "mul: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            mul[i] = new long[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               mul[i][j] = a[i][j] *+ b[i][j];
            }
         }
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] mul ( float a[][], float b[][] ) {
      float mul[][] = new float[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "mul: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            mul[i] = new float[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               mul[i][j] = a[i][j] * b[i][j];
            }
         }
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] mul ( double a[][], double b[][] ) {
      double mul[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "mul: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            mul[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               mul[i][j] = a[i][j] * b[i][j];
            }
         }
      }
      return mul;
   }


   /**
    * Returns the element-by-element product of two 2-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the product of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] mul ( Complex a[][], Complex b[][] ) {
      Complex mul[][] = new Complex[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "mul: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "mul: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            mul[i] = new Complex[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               mul[i][j] = a[i][j].mul(b[i][j]);
            }
         }
      }
      return mul;
   }


   //******************************************************
   //  multiply array-by-scalar methods
   //******************************************************

   /**
    * Multiplies a scalar value by a 1-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] mul ( int a[], int b ) {
      int mul[] = new int[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = a[i] * b;

      return mul;
   }


   /**
    * Multiplies a scalar value by a 1-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] mul ( long a[], long b ) {

      long mul[] = new long[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = (long) (a[i] * b);

      return mul;
   }


   /**
    * Multiplies a scalar value by a 1-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] mul ( float a[], float b ) {

      float mul[] = new float[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = (float) (a[i] * b);

      return mul;
   }


   /**
    * Multiplies a scalar value by a 1-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] mul ( double a[], double b ) {

      double mul[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = a[i] * b;

      return mul;
   }


   /**
    * Multiplies a scalar value by a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] mul ( Complex a[], float b ) {

      Complex mul[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = a[i].mul(b);

      return mul;
   }


   /**
    * Multiplies a scalar value by a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] mul ( Complex a[], Complex b ) {

      Complex mul[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         mul[i] = a[i].mul(b);

      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] mul ( int a[][], int b ) {
      int mul[][] = new int[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new int[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j] * b;
         }
      }
      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] mul ( long a[][], long b ) {
      long mul[][] = new long[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new long[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j] * b;
         }
      }
      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] mul ( float a[][], float b ) {
      float mul[][] = new float[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new float[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j] * b;
         }
      }
      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] mul ( double a[][], double b ) {
      double mul[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j] * b;
         }
      }
      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] mul ( Complex a[][], float b ) {
      Complex mul[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j].mul(b);
         }
      }
      return mul;
   }


   /**
    * Multiplies a scalar value by a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] mul ( Complex a[][], Complex b ) {
      Complex mul[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         mul[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            mul[i][j] = a[i][j].mul(b);
         }
      }
      return mul;
   }


   //******************************************************
   //  not methods
   //******************************************************

   /**
    * Returns the element-by-element logical <code>not</code> of
    * a boolean array.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean[] not ( boolean a[] ) {
      boolean not[] = new boolean[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         not[i] = ! a[i];
      }
      return not;
   }


   /**
    * Returns the element-by-element logical <code>not</code> of
    * a boolean array.
    *
    * @param   a   input array.
    * @return  the <code>boolean</code> result
    */
   public static boolean[][] not ( boolean a[][] ) {
      boolean not[][] = new boolean[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         not[i] = new boolean[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
             not[i][j] = ! a[i][j];
         }
      }
      return not;
   }


   //******************************************************
   //  or methods
   //******************************************************

   /**
    * Returns the element-by-element logical <code>or</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[] or ( boolean a[], boolean b[] ) {
      boolean or[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "or: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         or[i] = a[i] || b[i];
      }
      return or;
   }


   /**
    * Returns the element-by-element logical <code>or</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[][] or ( boolean a[][], boolean b[][] ) {
      boolean or[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "or: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "or: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            or[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               or[i][j] = a[i][j] || b[i][j];
            }
         }
      }
      return or;
   }


   //******************************************************
   //  product methods
   //******************************************************

   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static int product ( int a[] ) {
      int product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         product *= a[i];
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static long product ( long a[] ) {
      long product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         product *= a[i];
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static float product ( float a[] ) {
      float product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         product *= a[i];
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static double product ( double a[] ) {
      double product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         product *= a[i];
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static Complex product ( Complex a[] ) {
      Complex product = new Complex(1.0f,0.0f);
      for ( int i = 0; i < a.length; i++ ) {
         product = product.mul(a[i]);
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static int product ( int a[][] ) {
      int product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            product *= a[i][j];
         }
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static long product ( long a[][] ) {
      long product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            product *= a[i][j];
         }
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static float product ( float a[][] ) {
      float product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            product *= a[i][j];
         }
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static double product ( double a[][] ) {
      double product = 1;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            product *= a[i][j];
         }
      }
      return product;
   }


   /**
    * Returns the product of the elements in array a.
    *
    * @param   a   input array.
    * @return  the product of the elements in <tt>a</tt>.
    */
   public static Complex product ( Complex a[][] ) {
      Complex product = new Complex(1.0f,0.0f);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            product = product.mul(a[i][j]);
         }
      }
      return product;
   }


   //******************************************************
   //  product (mask) methods
   //******************************************************

   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static int product ( int a[], boolean b[] ) {
      int product = 1;

      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               product *= a[i];
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static long product ( long a[], boolean b[] ) {
      long product = 1;

      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               product *= a[i];
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static float product ( float a[], boolean b[] ) {
      float product = 1;

      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               product *= a[i];
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static double product ( double a[], boolean b[] ) {
      double product = 1;

      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               product *= a[i];
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static Complex product ( Complex a[], boolean b[] ) {
      Complex product = new Complex(1.0f,0.0f);

      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               product = product.mul(a[i]);
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static int product ( int a[][], boolean b[][] ) {
      int product = 1;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "product: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  product *= a[i][j];
               }
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static long product ( long a[][], boolean b[][] ) {
      long product = 1;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "product: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  product *= a[i][j];
               }
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static float product ( float a[][], boolean b[][] ) {
      float product = 1;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "product: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  product *= a[i][j];
               }
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static double product ( double a[][], boolean b[][] ) {
      double product = 1;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "product: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  product *= a[i][j];
               }
            }
         }
      }
      return product;
   }


   /**
    * Masked product--returns the product of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the product of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static Complex product ( Complex a[][], boolean b[][] ) {
      Complex product = new Complex(1.0f,0.0f);

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "product: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "product: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  product = product.mul(a[i][j]);
               }
            }
         }
      }
      return product;
   }


   //******************************************************
   //  subtract element-by-element methods
   //******************************************************

   /**
    * Returns the element-by-element difference of two 1-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] sub ( int a[], int b[] ) {

      int sub[] = new int[a.length];

      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            sub[i] = a[i] - b[i];
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 1-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] sub ( long a[], long b[] ) {

      long sub[] = new long[a.length];

      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            sub[i] = a[i] - b[i];
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 1-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] sub ( float a[], float b[] ) {

      float sub[] = new float[a.length];

      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            sub[i] = a[i] - b[i];
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 1-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] sub ( double a[], double b[] ) {

      double sub[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            sub[i] = a[i] - b[i];
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 1-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] sub ( Complex a[], Complex b[] ) {

      Complex sub[] = new Complex[a.length];

      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ )
            sub[i] = a[i].sub(b[i]);
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 2-D int arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] sub ( int a[][], int b[][] ) {
      int sub[][] = new int[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sub: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            sub[i] = new int[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               sub[i][j] = a[i][j] - b[i][j];
            }
         }
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 2-D long arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] sub ( long a[][], long b[][] ) {
      long sub[][] = new long[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sub: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            sub[i] = new long[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               sub[i][j] = a[i][j] - b[i][j];
            }
         }
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 2-D float arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] sub ( float a[][], float b[][] ) {
      float sub[][] = new float[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sub: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            sub[i] = new float[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               sub[i][j] = a[i][j] - b[i][j];
            }
         }
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 2-D double arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] sub ( double a[][], double b[][] ) {
      double sub[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sub: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            sub[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               sub[i][j] = a[i][j] - b[i][j];
            }
         }
      }
      return sub;
   }


   /**
    * Returns the element-by-element difference of two 2-D Complex arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  an array whose elements are the difference of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] sub ( Complex a[][], Complex b[][] ) {
      Complex sub[][] = new Complex[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sub: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sub: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            sub[i] = new Complex[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               sub[i][j] = a[i][j].sub(b[i][j]);
            }
         }
      }
      return sub;
   }


   //******************************************************
   //  substract scalar-from-array methods
   //******************************************************

   /**
    * Subtracts a scalar value from a 1-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[] sub ( int a[], int b ) {
      int sub[] = new int[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i] - b;

      return sub;
   }


   /**
    * Subtracts a scalar value from a 1-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[] sub ( long a[], long b ) {

      long sub[] = new long[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i] - b;

      return sub;
   }


   /**
    * Subtracts a scalar value from a 1-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[] sub ( float a[], float b ) {

      float sub[] = new float[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i] - b;

      return sub;
   }


   /**
    * Subtracts a scalar value from a 1-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[] sub ( double a[], double b ) {

      double sub[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i] - b;

      return sub;
   }


   /**
    * Subtracts a scalar value from a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] sub ( Complex a[], float b ) {

      Complex sub[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i].sub(b);

      return sub;
   }


   /**
    * Subtracts a scalar value from a 1-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[] sub ( Complex a[], Complex b ) {

      Complex sub[] = new Complex[a.length];

      for ( int i = 0; i < a.length; i++ )
         sub[i] = a[i].sub(b);

      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D int array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static int[][] sub ( int a[][], int b ) {
      int sub[][] = new int[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new int[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j] - b;
         }
      }
      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D long array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static long[][] sub ( long a[][], long b ) {
      long sub[][] = new long[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new long[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j] - b;
         }
      }
      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D float array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static float[][] sub ( float a[][], float b ) {
      float sub[][] = new float[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new float[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j] - b;
         }
      }
      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D double array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static double[][] sub ( double a[][], double b ) {
      double sub[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j] - b;
         }
      }
      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] sub ( Complex a[][], float b ) {
      Complex sub[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j].sub(b);
         }
      }
      return sub;
   }


   /**
    * Subtracts a scalar value from a 2-D Complex array.
    *
    * @param   a   input array.
    * @param   b   input scalar value.
    * @return  an array whose elements are the sum of the
    *          elements in <tt>a</tt> and <tt>b</tt>.
    */
   public static Complex[][] sub ( Complex a[][], Complex b ) {
      Complex sub[][] = new Complex[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sub[i] = new Complex[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sub[i][j] = a[i][j].sub(b);
         }
      }
      return sub;
   }


   //******************************************************
   //  sum methods
   //******************************************************

   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static int sum ( int a[] ) {
      int sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         sum += a[i];
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static long sum ( long a[] ) {
      long sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         sum += a[i];
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static float sum ( float a[] ) {
      float sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         sum += a[i];
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static double sum ( double a[] ) {
      double sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         sum += a[i];
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static Complex sum ( Complex a[] ) {
      Complex sum = new Complex(0.0f,0.0f);
      for ( int i = 0; i < a.length; i++ ) {
         sum = sum.add(a[i]);
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static int sum ( int a[][] ) {
      int sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sum += a[i][j];
         }
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static long sum ( long a[][] ) {
      long sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sum += a[i][j];
         }
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static float sum ( float a[][] ) {
      float sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sum += a[i][j];
         }
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static double sum ( double a[][] ) {
      double sum = 0;
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sum += a[i][j];
         }
      }
      return sum;
   }


   /**
    * Returns the sum of the elements in array a.
    *
    * @param   a   input array.
    * @return  the sum of the elements in <tt>a</tt>.
    */
   public static Complex sum ( Complex a[][] ) {
      Complex sum = new Complex(0.0f,0.0f);
      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sum = sum.add(a[i][j]);
         }
      }
      return sum;
   }


   //******************************************************
   //  sum (mask) methods
   //******************************************************

   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static int sum ( int a[], boolean b[] ) {
      int sum = 0;

      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               sum += a[i];
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static long sum ( long a[], boolean b[] ) {
      long sum = 0;

      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               sum += a[i];
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static float sum ( float a[], boolean b[] ) {
      float sum = 0;

      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               sum += a[i];
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static double sum ( double a[], boolean b[] ) {
      double sum = 0;

      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               sum += a[i];
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static Complex sum ( Complex a[], boolean b[] ) {
      Complex sum = new Complex(0.0f,0.0f);

      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }
      else {
         for ( int i = 0; i < a.length; i++ ) {
            if ( b[i] ) {
               sum = sum.add(a[i]);
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static int sum ( int a[][], boolean b[][] ) {
      int sum = 0;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sum: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  sum += a[i][j];
               }
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static long sum ( long a[][], boolean b[][] ) {
      long sum = 0;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sum: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  sum += a[i][j];
               }
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static float sum ( float a[][], boolean b[][] ) {
      float sum = 0;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sum: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  sum += a[i][j];
               }
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static double sum ( double a[][], boolean b[][] ) {
      double sum = 0;

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sum: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  sum += a[i][j];
               }
            }
         }
      }
      return sum;
   }


   /**
    * Masked sum--returns the sum of the elements
    * in array <tt>a</tt> that correspond to <tt>true</tt> elements
    * in array <tt>b</tt>.
    *
    * @param   a   input array.
    * @param   b   masking array.
    * @return  the sum of the elements in <tt>a</tt>
    *          that correspond to <tt>true</tt> elements in array
    *          <tt>b</tt>
    */
   public static Complex sum ( Complex a[][], boolean b[][] ) {
      Complex sum = new Complex(0.0f,0.0f);

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "sum: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "sum: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            for ( int j = 0; j < a[i].length; j++ ) {
               if ( b[i][j] ) {
                  sum = sum.add(a[i][j]);
               }
            }
         }
      }
      return sum;
   }


   //******************************************************
   //  xor methods
   //******************************************************

   /**
    * Returns the element-by-element logical <code>xor</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[] xor ( boolean a[], boolean b[] ) {
      boolean xor[] = new boolean[a.length];

      if ( a.length != b.length ) {
         String s = "xor: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         xor[i] = a[i] ^ b[i];
      }
      return xor;
   }


   /**
    * Returns the element-by-element logical <code>xor</code> of
    * two boolean arrays.
    *
    * @param   a   input array 1.
    * @param   b   input array 2.
    * @return  the <code>boolean</code> result
    */
   public static boolean[][] xor ( boolean a[][], boolean b[][] ) {
      boolean xor[][] = new boolean[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "xor: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "xor: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            xor[i] = new boolean[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               xor[i][j] = a[i][j] ^ b[i][j];
            }
         }
      }
      return xor;
   }


   //******************************************************
   //  trig and special functions
   //******************************************************

   /**
    * Returns the sine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sin(a)
    */
   public static double[] sin ( double a[] ) {
      double sin[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         sin[i] = Math.sin(a[i]);
      }
      return sin;
   }


   /**
    * Returns the sine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sin(a)
    */
   public static double[][] sin ( double a[][] ) {
      double sin[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sin[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sin[i][j] = Math.sin(a[i][j]);
         }
      }
      return sin;
   }


   /**
    * Returns the sine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sin(z)
    */
   public static Complex[] sin ( Complex z[] ) {
      Complex sin[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         sin[i] = Complex.sin(z[i]);
      }
      return sin;
   }


   /**
    * Returns the sine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sin(z)
    */
   public static Complex[][] sin ( Complex z[][] ) {
      Complex sin[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         sin[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            sin[i][j] = Complex.sin(z[i][j]);
         }
      }
      return sin;
   }


   /**
    * Returns the sine of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  sind(a)
    */
   public static double[] sind ( double a[] ) {
      double sind[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         sind[i] = Maths.sind(a[i]);
      }
      return sind;
   }


   /**
    * Returns the sine of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  sind(a)
    */
   public static double[][] sind ( double a[][] ) {
      double sind[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sind[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sind[i][j] = Maths.sind(a[i][j]);
         }
      }
      return sind;
   }


   /**
    * Returns the hyperbolic sine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sinh(a)
    */
   public static double[] sinh ( double a[] ) {
      double sinh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         sinh[i] = Maths.sinh(a[i]);
      }
      return sinh;
   }


   /**
    * Returns the hyperbolic sine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sinh(a)
    */
   public static double[][] sinh ( double a[][] ) {
      double sinh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sinh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sinh[i][j] = Maths.sinh(a[i][j]);
         }
      }
      return sinh;
   }


   /**
    * Returns the hyperbolic sine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sinh(z)
    */
   public static Complex[] sinh ( Complex z[] ) {
      Complex sinh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         sinh[i] = Complex.sinh(z[i]);
      }
      return sinh;
   }


   /**
    * Returns the hyperbolic sine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sinh(z)
    */
   public static Complex[][] sinh ( Complex z[][] ) {
      Complex sinh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         sinh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            sinh[i][j] = Complex.sinh(z[i][j]);
         }
      }
      return sinh;
   }


   /**
    * Returns the sinc function of the elements in an array, where
    * sinc <i>a</i> is defined as sin <i>a</i> / <i>a</i>.
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sinc(a)
    */
   public static double[] sinc ( double a[] ) {
      double sinc[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         sinc[i] = Maths.sinc(a[i]);
      }
      return sinc;
   }


   /**
    * Returns the sinc function of the elements in an array, where
    * sinc <i>a</i> is defined as sin <i>a</i> / <i>a</i>.
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  sinc(a)
    */
   public static double[][] sinc ( double a[][] ) {
      double sinc[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         sinc[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            sinc[i][j] = Maths.sinc(a[i][j]);
         }
      }
      return sinc;
   }


   /**
    * Returns the cosine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  cos(a)
    */
   public static double[] cos ( double a[] ) {
      double cos[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         cos[i] = Math.cos(a[i]);
      }
      return cos;
   }


   /**
    * Returns the cosine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  cos(a)
    */
   public static double[][] cos ( double a[][] ) {
      double cos[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         cos[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            cos[i][j] = Math.cos(a[i][j]);
         }
      }
      return cos;
   }


   /**
    * Returns the cosine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  cos(z)
    */
   public static Complex[] cos ( Complex z[] ) {
      Complex cos[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         cos[i] = Complex.cos(z[i]);
      }
      return cos;
   }


   /**
    * Returns the cosine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sin(z)
    */
   public static Complex[][] cos ( Complex z[][] ) {
      Complex cos[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         cos[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            cos[i][j] = Complex.cos(z[i][j]);
         }
      }
      return cos;
   }


   /**
    * Returns the cosine of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  cosd(a)
    */
   public static double[] cosd ( double a[] ) {
      double cosd[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         cosd[i] = Maths.cosd(a[i]);
      }
      return cosd;
   }


   /**
    * Returns the cosine of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  cosd(a)
    */
   public static double[][] cosd ( double a[][] ) {
      double cosd[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         cosd[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            cosd[i][j] = Maths.cosd(a[i][j]);
         }
      }
      return cosd;
   }


   /**
    * Returns the hyperbolic cosine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  cosh(a)
    */
   public static double[] cosh ( double a[] ) {
      double cosh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         cosh[i] = Maths.cosh(a[i]);
      }
      return cosh;
   }


   /**
    * Returns the hyperbolic cosine of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  cosh(a)
    */
   public static double[][] cosh ( double a[][] ) {
      double cosh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         cosh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            cosh[i][j] = Maths.cosh(a[i][j]);
         }
      }
      return cosh;
   }


   /**
    * Returns the hyperbolic cosine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  cosh(z)
    */
   public static Complex[] cosh ( Complex z[] ) {
      Complex cosh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         cosh[i] = Complex.cosh(z[i]);
      }
      return cosh;
   }


   /**
    * Returns the hyperbolic cosine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  cosh(z)
    */
   public static Complex[][] cosh ( Complex z[][] ) {
      Complex cosh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         cosh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            cosh[i][j] = Complex.cosh(z[i][j]);
         }
      }
      return cosh;
   }


   /**
    * Returns the tangent of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  tan(a)
    */
   public static double[] tan ( double a[] ) {
      double tan[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         tan[i] = Math.tan(a[i]);
      }
      return tan;
   }


   /**
    * Returns the tangent of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  tan(a)
    */
   public static double[][] tan ( double a[][] ) {
      double tan[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         tan[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            tan[i][j] = Math.tan(a[i][j]);
         }
      }
      return tan;
   }


   /**
    * Returns the tangent of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  tan(z)
    */
   public static Complex[] tan ( Complex z[] ) {
      Complex tan[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         tan[i] = Complex.tan(z[i]);
      }
      return tan;
   }


   /**
    * Returns the tangent of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  sin(z)
    */
   public static Complex[][] tan ( Complex z[][] ) {
      Complex tan[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         tan[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            tan[i][j] = Complex.tan(z[i][j]);
         }
      }
      return tan;
   }


   /**
    * Returns the tangent of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  tand(a)
    */
   public static double[] tand ( double a[] ) {
      double tand[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         tand[i] = Maths.tand(a[i]);
      }
      return tand;
   }


   /**
    * Returns the tangent of the elements in an array, where the
    * input arguments are in <i>degrees</i>.
    *
    * @param   a   input array, in <i>degrees</i>.
    * @return  tand(a)
    */
   public static double[][] tand ( double a[][] ) {
      double tand[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         tand[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            tand[i][j] = Maths.tand(a[i][j]);
         }
      }
      return tand;
   }


   /**
    * Returns the hyperbolic tangent of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  tanh(a)
    */
   public static double[] tanh ( double a[] ) {
      double tanh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         tanh[i] = Maths.tanh(a[i]);
      }
      return tanh;
   }


   /**
    * Returns the hyperbolic tangent of the elements in an array
    *
    * @param   a   input array, in <i>radians</i>.
    * @return  tanh(a)
    */
   public static double[][] tanh ( double a[][] ) {
      double tanh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         tanh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            tanh[i][j] = Maths.tanh(a[i][j]);
         }
      }
      return tanh;
   }


   /**
    * Returns the hyperbolic tangent of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  tanh(z)
    */
   public static Complex[] tanh ( Complex z[] ) {
      Complex tanh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         tanh[i] = Complex.tanh(z[i]);
      }
      return tanh;
   }


   /**
    * Returns the hyperbolic tangent of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  tanh(z)
    */
   public static Complex[][] tanh ( Complex z[][] ) {
      Complex tanh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         tanh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            tanh[i][j] = Complex.tanh(z[i][j]);
         }
      }
      return tanh;
   }


   /**
    * Returns the inverse sine of the elements in an array,
    * in the range of -<i>pi</i>/2 through <i>pi</i>/2.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  asin(a)
    */
   public static double[] asin ( double a[] ) {
      double asin[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         asin[i] = Math.asin(a[i]);
      }
      return asin;
   }


   /**
    * Returns the inverse sine of the elements in an array,
    * in the range of -<i>pi</i>/2 through <i>pi</i>/2.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  asin(a)
    */
   public static double[][] asin ( double a[][] ) {
      double asin[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         asin[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            asin[i][j] = Math.asin(a[i][j]);
         }
      }
      return asin;
   }


   /**
    * Returns the inverse sine of the elements in a <code>Complex</code> array.
    *
    * @param   z   input array
    * @return  asin(z)
    */
   public static Complex[] asin ( Complex z[] ) {
      Complex asin[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         asin[i] = Complex.asin(z[i]);
      }
      return asin;
   }


   /**
    * Returns the inverse sine of the elements in a <code>Complex</code> array.
    *
    * @param   z   input array
    * @return  asin(z)
    */
   public static Complex[][] asin ( Complex z[][] ) {
      Complex asin[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         asin[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            asin[i][j] = Complex.asin(z[i][j]);
         }
      }
      return asin;
   }


   /**
    * Returns the inverse sine of the elements in an array,
    * in the range of -90 through 90 <i>degrees</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  asind(a)
    */
   public static double[] asind ( double a[] ) {
      double asind[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         asind[i] = Maths.asind(a[i]);
      }
      return asind;
   }


   /**
    * Returns the inverse sine of the elements in an array,
    * in the range of -90 through 90 <i>degrees</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  asind(a)
    */
   public static double[][] asind ( double a[][] ) {
      double asind[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         asind[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            asind[i][j] = Maths.asind(a[i][j]);
         }
      }
      return asind;
   }


   /**
    * Returns the inverse hyperbolic sine of the elements in an array
    *
    * @param   a   input array
    * @return  asinh(a)
    */
   public static double[] asinh ( double a[] ) {
      double asinh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         asinh[i] = Maths.asinh(a[i]);
      }
      return asinh;
   }


   /**
    * Returns the inverse hyperbolic sine of the elements in an array
    *
    * @param   a   input array
    * @return  asinh(a)
    */
   public static double[][] asinh ( double a[][] ) {
      double asinh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         asinh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            asinh[i][j] = Maths.asinh(a[i][j]);
         }
      }
      return asinh;
   }


   /**
    * Returns the inverse hyperbolic sine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  asinh(z)
    */
   public static Complex[] asinh ( Complex z[] ) {
      Complex asinh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         asinh[i] = Complex.asinh(z[i]);
      }
      return asinh;
   }


   /**
    * Returns the inverse hyperbolic cosine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  asinh(z)
    */
   public static Complex[][] asinh ( Complex z[][] ) {
      Complex asinh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         asinh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            asinh[i][j] = Complex.asinh(z[i][j]);
         }
      }
      return asinh;
   }


   /**
    * Returns the inverse cosine of the elements in an array,
    * in the range of 0 through <i>pi</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  acos(a)
    */
   public static double[] acos ( double a[] ) {
      double acos[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         acos[i] = Math.acos(a[i]);
      }
      return acos;
   }


   /**
    * Returns the inverse cosine of the elements in an array,
    * in the range of 0 through <i>pi</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  acos(a)
    */
   public static double[][] acos ( double a[][] ) {
      double acos[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         acos[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            acos[i][j] = Math.acos(a[i][j]);
         }
      }
      return acos;
   }


   /**
    * Returns the inverse cosine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  acos(z)
    */
   public static Complex[] acos ( Complex z[] ) {
      Complex acos[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         acos[i] = Complex.acos(z[i]);
      }
      return acos;
   }


   /**
    * Returns the inverse cosine of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  acos(z)
    */
   public static Complex[][] acos ( Complex z[][] ) {
      Complex acos[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         acos[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            acos[i][j] = Complex.acos(z[i][j]);
         }
      }
      return acos;
   }


   /**
    * Returns the inverse cosine of the elements in an array,
    * in the range of 0 through 180 <i>degrees</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  acosd(a)
    */
   public static double[] acosd ( double a[] ) {
      double acosd[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         acosd[i] = Maths.acosd(a[i]);
      }
      return acosd;
   }


   /**
    * Returns the inverse cosine of the elements in an array,
    * in the range of 0 through 180 <i>degrees</i>.
    *
    * @param   a   input array, in the range -1.0 through 1.0
    * @return  acosd(a)
    */
   public static double[][] acosd ( double a[][] ) {
      double acosd[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         acosd[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            acosd[i][j] = Maths.acosd(a[i][j]);
         }
      }
      return acosd;
   }


   /**
    * Returns the inverse hyperbolic cosine of the elements in an array
    *
    * @param   a   input array, a >= 1.0
    * @return  acosh(a)
    */
   public static double[] acosh ( double a[] ) {
      double acosh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         acosh[i] = Maths.acosh(a[i]);
      }
      return acosh;
   }


   /**
    * Returns the inverse hyperbolic cosine of the elements in an array
    *
    * @param   a   input array
    * @return  acosh(a)
    */
   public static double[][] acosh ( double a[][] ) {
      double acosh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         acosh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            acosh[i][j] = Maths.acosh(a[i][j]);
         }
      }
      return acosh;
   }


   /**
    * Returns the inverse hyperbolic cosine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  acosh(z)
    */
   public static Complex[] acosh ( Complex z[] ) {
      Complex acosh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         acosh[i] = Complex.acosh(z[i]);
      }
      return acosh;
   }


   /**
    * Returns the inverse hyperbolic cosine of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  acosh(z)
    */
   public static Complex[][] acosh ( Complex z[][] ) {
      Complex acosh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         acosh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            acosh[i][j] = Complex.acosh(z[i][j]);
         }
      }
      return acosh;
   }


   /**
    * Returns the inverse tangent of the elements in an array,
    * in the range of -<i>pi</i>/2 through <i>pi</i>/2.
    *
    * @param   a   input array
    * @return  atan(a)
    */
   public static double[] atan ( double a[] ) {
      double atan[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         atan[i] = Math.atan(a[i]);
      }
      return atan;
   }


   /**
    * Returns the inverse tangent of the elements in an array,
    * in the range of -<i>pi</i>/2 through <i>pi</i>/2.
    *
    * @param   a   input array
    * @return  atan(a)
    */
   public static double[][] atan ( double a[][] ) {
      double atan[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         atan[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            atan[i][j] = Math.atan(a[i][j]);
         }
      }
      return atan;
   }


   /**
    * Returns the inverse tangent of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  atan(z)
    */
   public static Complex[] atan ( Complex z[] ) {
      Complex atan[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         atan[i] = Complex.atan(z[i]);
      }
      return atan;
   }


   /**
    * Returns the inverse tangent of the elements in a <code>Complex</code> array
    *
    * @param   z   input array
    * @return  atan(z)
    */
   public static Complex[][] atan ( Complex z[][] ) {
      Complex atan[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         atan[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            atan[i][j] = Complex.atan(z[i][j]);
         }
      }
      return atan;
   }


   /**
    * Returns the inverse tangent of the elements in an array,
    * in the range of -90 through 90 <i>degrees</i>.
    *
    * @param   a   input array
    * @return  atand(a)
    */
   public static double[] atand ( double a[] ) {
      double atand[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         atand[i] = Maths.atand(a[i]);
      }
      return atand;
   }


   /**
    * Returns the inverse tangent of the elements in an array,
    * in the range of -90 through 90 <i>degrees</i>.
    *
    * @param   a   input array
    * @return  atand(a)
    */
   public static double[][] atand ( double a[][] ) {
      double atand[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         atand[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            atand[i][j] = Maths.atand(a[i][j]);
         }
      }
      return atand;
   }


   /**
    * Returns the inverse hyperbolic tangent of the elements in an array
    *
    * @param   a   input array, a >= 1.0
    * @return  atanh(a)
    */
   public static double[] atanh ( double a[] ) {
      double atanh[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         atanh[i] = Maths.atanh(a[i]);
      }
      return atanh;
   }


   /**
    * Returns the inverse hyperbolic tangent of the elements in an array
    *
    * @param   a   input array
    * @return  atanh(a)
    */
   public static double[][] atanh ( double a[][] ) {
      double atanh[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         atanh[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            atanh[i][j] = Maths.atanh(a[i][j]);
         }
      }
      return atanh;
   }


   /**
    * Returns the inverse hyperbolic tangent of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  atanh(z)
    */
   public static Complex[] atanh ( Complex z[] ) {
      Complex atanh[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         atanh[i] = Complex.atanh(z[i]);
      }
      return atanh;
   }


   /**
    * Returns the inverse hyperbolic tangent of the elements in a
    * <code>Complex</code> array
    *
    * @param   z   input array
    * @return  atanh(z)
    */
   public static Complex[][] atanh ( Complex z[][] ) {
      Complex atanh[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         atanh[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            atanh[i][j] = Complex.atanh(z[i][j]);
         }
      }
      return atanh;
   }


   /**
    * Converts rectangular coordinates (<code>b</code>,&nbsp;<code>a</code>)
    * to polar (r,&nbsp;<i>theta</i>).
    * This method computes the phase <i>theta</i> by computing an arc tangent
    * of <code>b/a</code> in the range of -<i>pi</i> to <i>pi</i>.
    *
    * @param   a   a <code>double</code> value.
    * @param   b   a <code>double</code> value.
    * @return  the <i>theta</i> component of the point
    *          (<i>r</i>,&nbsp;<i>theta</i>)
    *          in polar coordinates that corresponds to the point
    *          (<i>b</i>,&nbsp;<i>a</i>) in Cartesian coordinates.
    */
   public static double[] atan2 ( double a[], double b[] ) {
      double atan2[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "atan2: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         atan2[i] = Math.atan2(a[i],b[i]);
      }
      return atan2;
   }


   /**
    * Converts rectangular coordinates (<code>b</code>,&nbsp;<code>a</code>)
    * to polar (r,&nbsp;<i>theta</i>).
    * This method computes the phase <i>theta</i> by computing an arc tangent
    * of <code>b/a</code> in the range of -<i>pi</i> to <i>pi</i>.
    *
    * @param   a   a <code>double</code> value.
    * @param   b   a <code>double</code> value.
    * @return  the <i>theta</i> component of the point
    *          (<i>r</i>,&nbsp;<i>theta</i>)
    *          in polar coordinates that corresponds to the point
    *          (<i>b</i>,&nbsp;<i>a</i>) in Cartesian coordinates.
    */
   public static double[][] atan2 ( double a[][], double b[][] ) {
      double atan2[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "atan2: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "atan2: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            atan2[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               atan2[i][j] = Math.atan2(a[i][j],b[i][j]);
            }
         }
      }
      return atan2;
   }


   /**
    * Converts rectangular coordinates (<code>b</code>,&nbsp;<code>a</code>)
    * to polar (r,&nbsp;<i>theta</i>).
    * This method computes the phase <i>theta</i> by computing an arc tangent
    * of <code>b/a</code> in the range of -180 to 180 <i>degrees</i>.
    *
    * @param   a   a <code>double</code> value.
    * @param   b   a <code>double</code> value.
    * @return  the <i>theta</i> component of the point
    *          (<i>r</i>,&nbsp;<i>theta</i>)
    *          in polar coordinates that corresponds to the point
    *          (<i>b</i>,&nbsp;<i>a</i>) in Cartesian coordinates.
    */
   public static double[] atan2d ( double a[], double b[] ) {
      double atan2d[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "atan2d: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         atan2d[i] = Maths.atan2d(a[i],b[i]);
      }
      return atan2d;
   }


   /**
    * Converts rectangular coordinates (<code>b</code>,&nbsp;<code>a</code>)
    * to polar (r,&nbsp;<i>theta</i>).
    * This method computes the phase <i>theta</i> by computing an arc tangent
    * of <code>b/a</code> in the range of -180 to 180 <i>degrees</i>.
    *
    * @param   a   a <code>double</code> value.
    * @param   b   a <code>double</code> value.
    * @return  the <i>theta</i> component of the point
    *          (<i>r</i>,&nbsp;<i>theta</i>)
    *          in polar coordinates that corresponds to the point
    *          (<i>b</i>,&nbsp;<i>a</i>) in Cartesian coordinates.
    */
   public static double[][] atan2d ( double a[][], double b[][] ) {
      double atan2d[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "atan2d: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "atan2d: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            atan2d[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               atan2d[i][j] = Maths.atan2d(a[i][j],b[i][j]);
            }
         }
      }
      return atan2d;
   }


   /**
    * Returns exp(a) for the elements in an array
    *
    * @param   a   input array
    * @return  exp(a)
    */
   public static double[] exp ( double a[] ) {
      double exp[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         exp[i] = Math.exp(a[i]);
      }
      return exp;
   }


   /**
    * Returns exp(a) for the elements in an array
    *
    * @param   a   input array
    * @return  exp(a)
    */
   public static double[][] exp ( double a[][] ) {
      double exp[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            exp[i][j] = Math.exp(a[i][j]);
         }
      }
      return exp;
   }


   /**
    * Returns exp(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  exp(z)
    */
   public static Complex[] exp ( Complex z[] ) {
      Complex exp[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         exp[i] = Complex.exp(z[i]);
      }
      return exp;
   }


   /**
    * Returns exp(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  exp(z)
    */
   public static Complex[][] exp ( Complex z[][] ) {
      Complex exp[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         exp[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            exp[i][j] = Complex.exp(z[i][j]);
         }
      }
      return exp;
   }


   /**
    * Returns the natural logarithm log(a) for the elements in an array
    *
    * @param   a   input array, where all a[i] > 0.0
    * @return  log(a)
    */
   public static double[] log ( double a[] ) {
      double log[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         log[i] = Math.log(a[i]);
      }
      return log;
   }


   /**
    * Returns the natural logarithm log(a) for the elements in an array
    *
    * @param   a   input array, where all a[i] > 0.0
    * @return  log(a)
    */
   public static double[][] log ( double a[][] ) {
      double log[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            log[i][j] = Math.log(a[i][j]);
         }
      }
      return log;
   }


   /**
    * Returns log(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  log(z)
    */
   public static Complex[] log ( Complex z[] ) {
      Complex log[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         log[i] = Complex.log(z[i]);
      }
      return log;
   }


   /**
    * Returns log(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  log(z)
    */
   public static Complex[][] log ( Complex z[][] ) {
      Complex log[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         log[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            log[i][j] = Complex.log(z[i][j]);
         }
      }
      return log;
   }


   /**
    * Returns the base-10 logarithm log10(a) for the elements in an array
    *
    * @param   a   input array, where all a[i] > 0.0
    * @return  log10(a)
    */
   public static double[] log10 ( double a[] ) {
      double log10[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         log10[i] = Maths.log10(a[i]);
      }
      return log10;
   }


   /**
    * Returns the base-10 logarithm log10(a) for the elements in an array
    *
    * @param   a   input array, where all a[i] > 0.0
    * @return  log10(a)
    */
   public static double[][] log10 ( double a[][] ) {
      double log10[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            log10[i][j] = Maths.log10(a[i][j]);
         }
      }
      return log10;
   }


   /**
    * Returns the square root of a for the elements in an array
    *
    * @param   a   input array, where all a[i] >= 0.0
    * @return  sqrt(a)
    */
   public static double[] sqrt ( double a[] ) {
      double sqrt[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         sqrt[i] = Math.sqrt(a[i]);
      }
      return sqrt;
   }


   /**
    * Returns the square root of a for the elements in an array
    *
    * @param   a   input array, where all a[i] >= 0.0
    * @return  sqrt(a)
    */
   public static double[][] sqrt ( double a[][] ) {
      double sqrt[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         for ( int j = 0; j < a[i].length; j++ ) {
            sqrt[i][j] = Math.sqrt(a[i][j]);
         }
      }
      return sqrt;
   }


   /**
    * Returns sqrt(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  sqrt(z)
    */
   public static Complex[] sqrt ( Complex z[] ) {
      Complex sqrt[] = new Complex[z.length];

      for ( int i = 0; i < z.length; i++ ) {
         sqrt[i] = Complex.sqrt(z[i]);
      }
      return sqrt;
   }


   /**
    * Returns sqrt(z) for the elements in a <code>Complex</code> array
    *
    * @param   z   <code>Complex</code> input array
    * @return  sqrt(z)
    */
   public static Complex[][] sqrt ( Complex z[][] ) {
      Complex sqrt[][] = new Complex[z.length][];

      for ( int i = 0; i < z.length; i++ ) {
         sqrt[i] = new Complex[z[i].length];
         for ( int j = 0; j < z[i].length; j++ ) {
            sqrt[i][j] = Complex.sqrt(z[i][j]);
         }
      }
      return sqrt;
   }


   /**
    * Returns an array of the first argument raised to the power of the
    * second argument.
    * <p>
    * If (<code>a&nbsp;==&nbsp;0.0</code>), then <code>b</code> must be
    * greater than <code>0.0</code>; otherwise an exception is thrown.
    * An exception also will occur if (<code>a&nbsp;&lt;=&nbsp;0.0</code>)
    * and <code>b</code> is not equal to a whole number.
    *
    * @param   a   a <code>double</code> array.
    * @param   b   a <code>double</code> array.
    * @return  the array <code>a<sup>b</sup></code>.
    * @exception ArithmeticException  if (<code>a&nbsp;==&nbsp;0.0</code>) and
    *              (<code>b&nbsp;&lt;=&nbsp;0.0</code>), or
    *              if (<code>a&nbsp;&lt;=&nbsp;0.0</code>) and <code>b</code>
    *              is not equal to a whole number.
    * @exception InvalidArraySizeException  if <code>a</code> is not the same
    *              size as <code>b</code>
    */
   public static double[] pow ( double a[], double b[] ) {
      double pow[] = new double[a.length];

      if ( a.length != b.length ) {
         String s = "pow: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      for ( int i = 0; i < a.length; i++ ) {
         pow[i] = Math.pow(a[i],b[i]);
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to the power of the
    * second argument.
    * <p>
    * If (<code>a&nbsp;==&nbsp;0.0</code>), then <code>b</code> must be
    * greater than <code>0.0</code>; otherwise an exception is thrown.
    * An exception also will occur if (<code>a&nbsp;&lt;=&nbsp;0.0</code>)
    * and <code>b</code> is not equal to a whole number.
    *
    * @param   a   a <code>double</code> array.
    * @param   b   a <code>double</code> array.
    * @return  the array <code>a<sup>b</sup></code>.
    * @exception ArithmeticException  if (<code>a&nbsp;==&nbsp;0.0</code>) and
    *              (<code>b&nbsp;&lt;=&nbsp;0.0</code>), or
    *              if (<code>a&nbsp;&lt;=&nbsp;0.0</code>) and <code>b</code>
    *              is not equal to a whole number.
    * @exception InvalidArraySizeException  if <code>a</code> is not the same
    *              size as <code>b</code>
    */
   public static double[][] pow ( double a[][], double b[][] ) {
      double pow[][] = new double[a.length][];

      // Check for incompatibilities in the first dimension
      if ( a.length != b.length ) {
         String s = "pow: Incompatible array lengths";
         throw new InvalidArraySizeException(s);
      }

      else {
         for ( int i = 0; i < a.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( a[i].length != b[i].length ) {
               String s = "pow: Incompatible array lengths";
               throw new InvalidArraySizeException(s);
            }
            pow[i] = new double[a[i].length];
            for ( int j = 0; j < a[i].length; j++ ) {
               pow[i][j] = Math.pow(a[i][j],b[i][j]);
            }
         }
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    * <p>
    * If (<code>a&nbsp;==&nbsp;0.0</code>), then <code>b</code> must be
    * greater than <code>0.0</code>; otherwise an exception is thrown.
    * An exception also will occur if (<code>a&nbsp;&lt;=&nbsp;0.0</code>)
    * and <code>b</code> is not equal to a whole number.
    *
    * @param   a   a <code>double</code> array.
    * @param   b   a scalar exponent.
    * @return  the array <code>a<sup>b</sup></code>.
    * @exception ArithmeticException  if (<code>a&nbsp;==&nbsp;0.0</code>) and
    *              (<code>b&nbsp;&lt;=&nbsp;0.0</code>), or
    *              if (<code>a&nbsp;&lt;=&nbsp;0.0</code>) and <code>b</code>
    *              is not equal to a whole number.
    */
   public static double[] pow ( double a[], double b ) {
      double pow[] = new double[a.length];

      for ( int i = 0; i < a.length; i++ ) {
         pow[i] = Math.pow(a[i],b);
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    * <p>
    * If (<code>a&nbsp;==&nbsp;0.0</code>), then <code>b</code> must be
    * greater than <code>0.0</code>; otherwise an exception is thrown.
    * An exception also will occur if (<code>a&nbsp;&lt;=&nbsp;0.0</code>)
    * and <code>b</code> is not equal to a whole number.
    *
    * @param   a   a <code>double</code> array.
    * @param   b   a scalar exponent.
    * @return  the array <code>a<sup>b</sup></code>.
    * @exception ArithmeticException  if (<code>a&nbsp;==&nbsp;0.0</code>) and
    *              (<code>b&nbsp;&lt;=&nbsp;0.0</code>), or
    *              if (<code>a&nbsp;&lt;=&nbsp;0.0</code>) and <code>b</code>
    *              is not equal to a whole number.
    */
   public static double[][] pow ( double a[][], double b ) {
      double pow[][] = new double[a.length][];

      for ( int i = 0; i < a.length; i++ ) {
         pow[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            pow[i][j] = Math.pow(a[i][j],b);
         }
      }
      return pow;
   }

   /**
    * Returns an array of the first argument raised to the power of the
    * second argument.
    *
    * @param   za   a <code>Complex</code> array.
    * @param   zb   a <code>Complex</code> array.
    * @return  the array <code>za<sup>zb</sup></code>.
    * @exception RuntimeException if <code>za</code> is not the same
    *              size as <code>zb</code>
    */
   public static Complex[] pow ( Complex za[], Complex zb[] ) {
      Complex pow[] = new Complex[za.length];

      if ( za.length != zb.length ) {
         String s = "pow: Incompatible array lengths";
         throw new RuntimeException (s);
      }

      for ( int i = 0; i < za.length; i++ ) {
         pow[i] = Complex.pow(za[i],zb[i]);
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to the power of the
    * second argument.
    *
    * @param   za   a <code>Complex</code> array.
    * @param   zb   a <code>Complex</code> array.
    * @return  the array <code>za<sup>zb</sup></code>.
    * @exception RuntimeException   if <code>za</code> is not the same
    *              size as <code>zb</code>
    */
   public static Complex[][] pow ( Complex za[][], Complex zb[][] ) {
      Complex pow[][] = new Complex[za.length][];

      // Check for incompatibilities in the first dimension
      if ( za.length != zb.length ) {
         String s = "pow: Incompatible array lengths";
         throw new RuntimeException (s);
      }

      else {
         for ( int i = 0; i < za.length; i++ ) {
            // Check for incompatibilities in the second dimension
            if ( za[i].length != zb[i].length ) {
               String s = "pow: Incompatible array lengths";
               throw new RuntimeException (s);
            }
            pow[i] = new Complex[za[i].length];
            for ( int j = 0; j < za[i].length; j++ ) {
               pow[i][j] = Complex.pow(za[i][j],zb[i][j]);
            }
         }
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    *
    * @param   za   a <code>Complex</code> array.
    * @param   zb   a <code>Complex</code> scalar exponent.
    * @return  the array <code>za<sup>zb</sup></code>.
    */
   public static Complex[] pow ( Complex za[], Complex zb ) {
      Complex pow[] = new Complex[za.length];

      for ( int i = 0; i < za.length; i++ ) {
         pow[i] = Complex.pow(za[i],zb);
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    *
    * @param   za   a <code>Complex</code> array.
    * @param   zb   a <code>Complex</code> scalar exponent.
    * @return  the array <code>za<sup>zb</sup></code>.
    */
   public static Complex[][] pow ( Complex za[][], Complex zb ) {
      Complex pow[][] = new Complex[za.length][];

      for ( int i = 0; i < za.length; i++ ) {
         pow[i] = new Complex[za[i].length];
         for ( int j = 0; j < za[i].length; j++ ) {
            pow[i][j] = Complex.pow(za[i][j],zb);
         }
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    *
    * @param   za  a <code>Complex</code> array.
    * @param   b   a <code>double</code> scalar exponent.
    * @return  the array <code>za<sup>b</sup></code>.
    */
   public static Complex[] pow ( Complex za[], float b ) {
      Complex pow[] = new Complex[za.length];

      for ( int i = 0; i < za.length; i++ ) {
         pow[i] = Complex.pow(za[i],b);
      }
      return pow;
   }


   /**
    * Returns an array of the first argument raised to a scalar power.
    *
    * @param   za  a <code>Complex</code> array.
    * @param   b   a <code>double</code> scalar exponent.
    * @return  the array <code>za<sup>b</sup></code>.
    */
   public static Complex[][] pow ( Complex za[][], float b ) {
      Complex pow[][] = new Complex[za.length][];

      for ( int i = 0; i < za.length; i++ ) {
         pow[i] = new Complex[za[i].length];
         for ( int j = 0; j < za[i].length; j++ ) {
            pow[i][j] = Complex.pow(za[i][j],b);
         }
      }
      return pow;
   }


   /**
    * Returns an array of the smallest (closest to negative infinity)
    * <code>double</code> values that are not less than the argument and are
    * equal to mathematical integers.
    *
    * @param   a   a <code>double</code> array.
    * <!--@return  the value &lceil;&nbsp;<code>a</code>&nbsp;&rceil;.-->
    * @return  the array of the smallest (closest to negative infinity)
    *          <code>double</code> values that are not less than the arguments
    *          and are equal to a mathematical integer.
    */
   public static double[] ceil ( double a[] ) {
      double ceil[] = new double[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         ceil[i] = Math.ceil(a[i]);
      }
      return ceil;
   }


   /**
    * Returns an array of the smallest (closest to negative infinity)
    * <code>double</code> values that are not less than the argument and are
    * equal to mathematical integers.
    *
    * @param   a   a <code>double</code> array.
    * <!--@return  the value &lceil;&nbsp;<code>a</code>&nbsp;&rceil;.-->
    * @return  the array of the smallest (closest to negative infinity)
    *          <code>double</code> values that are not less than the arguments
    *          and are equal to a mathematical integer.
    */
   public static double[][] ceil ( double a[][] ) {
      double ceil[][] = new double[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         ceil[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            ceil[i][j] = Math.ceil(a[i][j]);
         }
      }
      return ceil;
   }


   /**
    * Returns an array of the smallest (closest to positive infinity)
    * <code>double</code> values that are not greater than the argument and are
    * equal to mathematical integers.
    *
    * @param   a   a <code>double</code> array.
     * <!--@return  the value &lfloor;&nbsp;<code>a</code>&nbsp;&rfloor;.-->
    * @return  the array of the largest (closest to positive infinity)
    *          <code>double</code> values that are not greater than the arguments
    *          and are equal to a mathematical integer.
    */
   public static double[] floor ( double a[] ) {
      double floor[] = new double[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         floor[i] = Math.floor(a[i]);
      }
      return floor;
   }


   /**
    * Returns an array of the smallest (closest to positive infinity)
    * <code>double</code> values that are not greater than the argument and are
    * equal to mathematical integers.
    *
    * @param   a   a <code>double</code> array.
     * <!--@return  the value &lfloor;&nbsp;<code>a</code>&nbsp;&rfloor;.-->
    * @return  the array of the largest (closest to positive infinity)
    *          <code>double</code> values that are not greater than the arguments
    *          and are equal to a mathematical integer.
    */
   public static double[][] floor ( double a[][] ) {
      double floor[][] = new double[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         floor[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            floor[i][j] = Math.floor(a[i][j]);
         }
      }
      return floor;
   }


   /**
    * Returns an array of the closest <code>long</code> values to the input
    * arguments.
    *
    * @param   a   a <code>double</code> array.
    * @return  the array of the <code>long</code> values closest to the input
    *          values
    */
   public static long[] round ( double a[] ) {
      long round[] = new long[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         round[i] = Math.round(a[i]);
      }
      return round;
   }


   /**
    * Returns an array of the closest <code>long</code> values to the input
    * arguments.
    *
    * @param   a   a <code>double</code> array.
    * @return  the array of the <code>long</code> values closest to the input
    *          values
    */
   public static long[][] round ( double a[][] ) {
      long round[][] = new long[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         round[i] = new long[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            round[i][j] = Math.round(a[i][j]);
         }
      }
      return round;
   }


   /**
    * Returns an array of the closest <code>int</code> values to the input
    * arguments.
    *
    * @param   a   a <code>float</code> array.
    * @return  the array of the <code>int</code> values closest to the input
    *          values
    */
   public static int[] round ( float a[] ) {
      int round[] = new int[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         round[i] = Math.round(a[i]);
      }
      return round;
   }


   /**
    * Returns an array of the closest <code>int</code> values to the input
    * arguments.
    *
    * @param   a   a <code>float</code> array.
    * @return  the array of the <code>int</code> values closest to the input
    *          values
    */
   public static int[][] round ( float a[][] ) {
      int round[][] = new int[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         round[i] = new int[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            round[i][j] = Math.round(a[i][j]);
         }
      }
      return round;
   }


   /**
    * Returns a <code>double</code> array containing the closest integer to
    * the argument.
    *
    * @param   a   a <code>double</code> array.
    * @return  a <code>double</code> array containing the the closest
    *          <code>double</code> values to <code>a</code> that are
    *          equal to mathematical integers. If two <code>double</code>
    *          values that are mathematical integers are equally close to the
    *          value of the argument, the result is the integer value that
    *          is even.
    */
   public static double[] rint ( double a[] ) {
      double rint[] = new double[a.length];
      for ( int i = 0; i < a.length; i++ ) {
         rint[i] = Math.rint(a[i]);
      }
      return rint;
   }


   /**
    * Returns a <code>double</code> array containing the closest integer to
    * the argument.
    *
    * @param   a   a <code>double</code> array.
    * @return  a <code>double</code> array containing the the closest
    *          <code>double</code> values to <code>a</code> that are
    *          equal to mathematical integers. If two <code>double</code>
    *          values that are mathematical integers are equally close to the
    *          value of the argument, the result is the integer value that
    *          is even.
    */
   public static double[][] rint ( double a[][] ) {
      double rint[][] = new double[a.length][];
      for ( int i = 0; i < a.length; i++ ) {
         rint[i] = new double[a[i].length];
         for ( int j = 0; j < a[i].length; j++ ) {
            rint[i][j] = Math.rint(a[i][j]);
         }
      }
      return rint;
   }


   //******************************************************
   //  Random number generator methods
   //******************************************************

   /**
    * Reference to hold random number generator.
    */
    private static Random randomNumberGenerator;

   /**
    * Returns an array of <i>i</i> pseudorandom <code>double</code> values
    * uniformly distributed in the range 0.0 <= value < 1.0.
    *
    * @param   i   the length of the array of random numbers to return.
    * @return  an array of random <code>double</code> values in the range
    *          0.0 <= value < 1.0.
    */
   public static double[] random ( int i ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[] = new double[i];
      for ( int i1 = 0; i1 < i; i1++ ) {
         random[i1] = randomNumberGenerator.nextDouble();
      }
      return random;
   }


   /**
    * Returns two-dimensional array of <code>[i][j]</code> pseudorandom
    * <code>double</code> values uniformly distributed in the range
    * 0.0 <= value < 1.0.
    *
    * @param   i   the length of first dimension of the array.
    * @param   j   the length of second dimension of the array.
    * @return  an array of random <code>double</code> values in the range
    *          0.0 <= value < 1.0.
    */
   public static double[][] random ( int i, int j ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[][] = new double[i][j];
      for ( int i1 = 0; i1 < i; i1++ ) {
         for ( int j1 = 0; j1 < j; j1++ ) {
            random[i1][j1] = randomNumberGenerator.nextDouble();
         }
      }
      return random;
   }


   /**
    * Returns an array of pseudorandom <code>double</code> values
    * with a Gaussian normal distribution.
    *
    * @param   i   the length of first dimension of the array.
    * @param   j   the length of second dimension of the array.
    * @return  an array of pseudorandom <code>double</code> values
    *          with a Gaussian normal distribution
    */
   public static double[] randomGaussian ( int i ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[] = new double[i];
      for ( int i1 = 0; i1 < i; i1++ ) {
         random[i1] = randomNumberGenerator.nextGaussian();
      }
      return random;
   }


   /**
    * Returns two-dimensional array of <code>[i][j]</code> pseudorandom
    * <code>double</code> values with a Gaussian normal distribution.
    *
    * @param   i   the length of the array of random numbers to return.
    * @return  an array of pseudorandom <code>double</code> values
    *          with a Gaussian normal distribution.
    */
   public static double[][] randomGaussian ( int i, int j ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[][] = new double[i][j];
      for ( int i1 = 0; i1 < i; i1++ ) {
         for ( int j1 = 0; j1 < j; j1++ ) {
            random[i1][j1] = randomNumberGenerator.nextGaussian();
         }
      }
      return random;
   }


   /**
    * Returns an array of pseudorandom <code>double</code> values
    * with a Rayleigh distribution.
    *
    * @param   i   the length of first dimension of the array.
    * @return  an array of pseudorandom <code>double</code> values
    *          with a Rayleigh normal distribution
    */
   public static double[] randomRayleigh ( int i ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[] = new double[i];
      double x, y;
      for ( int i1 = 0; i1 < i; i1++ ) {
         x = randomNumberGenerator.nextGaussian();
         y = randomNumberGenerator.nextGaussian();
         random[i1] = Math.sqrt( x*x + y*y );
      }
      return random;
   }


   /**
    * Returns two-dimensional array of <code>[i][j]</code> pseudorandom
    * <code>double</code> values with a Rayleigh distribution.
    *
    * @param   i   the length of the array of random numbers to return.
    * @return  an array of pseudorandom <code>double</code> values
    *          with a Rayleigh distribution.
    */
   public static double[][] randomRayleigh ( int i, int j ) {
      if (randomNumberGenerator == null) {
         randomNumberGenerator = new Random();
      }
      double random[][] = new double[i][j];
      double x, y;
      for ( int i1 = 0; i1 < i; i1++ ) {
         for ( int j1 = 0; j1 < j; j1++ ) {
            x = randomNumberGenerator.nextGaussian();
            y = randomNumberGenerator.nextGaussian();
            random[i1][j1] = Math.sqrt( x*x + y*y );
         }
      }
      return random;
   }


   public static double norm2(double [] x) {
     double d=0;
     for (int i=0;i<x.length;i++)
       d += x[i]*x[i];
     d = Math.sqrt(d);
     return d;
   }
   public static float norm2(float [] x) {
     float d=0;
     for (int i=0;i<x.length;i++)
       d += x[i]*x[i];
     d = (float)Math.sqrt(d);
     return d;
   }
   public static float norm2(float [] a, float [] b) {
     if ( a.length != b.length ) {
        String s = "norm2: Incompatible array lengths";
        throw new InvalidArraySizeException(s);
     }
    else {
     double d=0;
     for (int i=0;i<a.length;i++)
       d += (a[i]-b[i])*(a[i]-b[i]);
//     d = Math.sqrt(d);
     return (float)Math.sqrt(d);
    }
   }












}

