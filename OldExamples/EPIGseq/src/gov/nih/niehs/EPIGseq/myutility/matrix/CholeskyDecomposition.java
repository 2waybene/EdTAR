package gov.nih.niehs.EPIGseq.myutility.matrix;

/**
 * <p>Title: Gene Expression Dependence Extraction</p>
 * <p>Description: This is a software applied to gene expression profiles to extract gene expression dependence on treatments, agents, doses, times, etc.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: NIEHS</p>
 * @author Jeff Chou
 * @version 1.0
 */

public class CholeskyDecomposition  implements java.io.Serializable {

/* ------------------------
   Class variables
 * ------------------------ */

   /** Array for internal storage of decomposition.
   @serial internal array storage.
   */
   private float[][] L;

   /** Row and column dimension (square matrix).
   @serial matrix dimension.
   */
   private int n;

   /** Symmetric and positive definite flag.
   @serial is symmetric and positive definite flag.
   */
   private boolean isspd;

/* ------------------------
   Constructor
 * ------------------------ */

   /** Cholesky algorithm for symmetric and positive definite matrix.
   @param  A   Square, symmetric matrix.
   @return     Structure to access L and isspd flag.
   */

   public CholeskyDecomposition (Matrix Arg) {
      // Initialize.
      float[][] A = Arg.getArray();
      n = Arg.getRowDimension();
      L = new float[n][n];
      isspd = (Arg.getColumnDimension() == n);
      // Main loop.
      for (int j = 0; j < n; j++) {
         float[] Lrowj = L[j];
         float d = 0.0f;
         for (int k = 0; k < j; k++) {
            float[] Lrowk = L[k];
            float s = 0.0f;
            for (int i = 0; i < k; i++) {
               s += Lrowk[i]*Lrowj[i];
            }
            Lrowj[k] = s = (A[j][k] - s)/L[k][k];
            d = d + s*s;
            isspd = isspd & (A[k][j] == A[j][k]);
         }
         d = A[j][j] - d;
         isspd = isspd & (d > 0.0);
         L[j][j] = (float)Math.sqrt(Math.max(d,0.0));
         for (int k = j+1; k < n; k++) {
            L[j][k] = 0.0f;
         }
      }
   }

/* ------------------------
   Temporary, experimental code.
 * ------------------------ *\

   \** Right Triangular Cholesky Decomposition.
   <P>
   For a symmetric, positive definite matrix A, the Right Cholesky
   decomposition is an upper triangular matrix R so that A = R'*R.
   This constructor computes R with the Fortran inspired column oriented
   algorithm used in LINPACK and MATLAB.  In Java, we suspect a row oriented,
   lower triangular decomposition is faster.  We have temporarily included
   this constructor here until timing experiments confirm this suspicion.
   *\

   \** Array for internal storage of right triangular decomposition. **\
   private transient float[][] R;

   \** Cholesky algorithm for symmetric and positive definite matrix.
   @param  A           Square, symmetric matrix.
   @param  rightflag   Actual value ignored.
   @return             Structure to access R and isspd flag.
   *\

   public CholeskyDecomposition (Matrix Arg, int rightflag) {
      // Initialize.
      float[][] A = Arg.getArray();
      n = Arg.getColumnDimension();
      R = new float[n][n];
      isspd = (Arg.getColumnDimension() == n);
      // Main loop.
      for (int j = 0; j < n; j++) {
         float d = 0.0;
         for (int k = 0; k < j; k++) {
            float s = A[k][j];
            for (int i = 0; i < k; i++) {
               s = s - R[i][k]*R[i][j];
            }
            R[k][j] = s = s/R[k][k];
            d = d + s*s;
            isspd = isspd & (A[k][j] == A[j][k]);
         }
         d = A[j][j] - d;
         isspd = isspd & (d > 0.0);
         R[j][j] = Math.sqrt(Math.max(d,0.0));
         for (int k = j+1; k < n; k++) {
            R[k][j] = 0.0;
         }
      }
   }

   \** Return upper triangular factor.
   @return     R
   *\

   public Matrix getR () {
      return new Matrix(R,n,n);
   }

\* ------------------------
   End of temporary code.
 * ------------------------ */

/* ------------------------
   Public Methods
 * ------------------------ */

   /** Is the matrix symmetric and positive definite?
   @return     true if A is symmetric and positive definite.
   */

   public boolean isSPD () {
      return isspd;
   }

   /** Return triangular factor.
   @return     L
   */

   public Matrix getL () {
      return new Matrix(L,n,n);
   }

   /** Solve A*X = B
   @param  B   A Matrix with as many rows as A and any number of columns.
   @return     X so that L*L'*X = B
   @exception  IllegalArgumentException  Matrix row dimensions must agree.
   @exception  RuntimeException  Matrix is not symmetric positive definite.
   */

   public Matrix solve (Matrix B) {
      if (B.getRowDimension() != n) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      }
      if (!isspd) {
         throw new RuntimeException("Matrix is not symmetric positive definite.");
      }

      // Copy right hand side.
      float[][] X = B.getArrayCopy();
      int nx = B.getColumnDimension();

      // Solve L*Y = B;
      for (int k = 0; k < n; k++) {
         for (int i = k+1; i < n; i++) {
            for (int j = 0; j < nx; j++) {
               X[i][j] -= X[k][j]*L[i][k];
            }
         }
         for (int j = 0; j < nx; j++) {
            X[k][j] /= L[k][k];
         }
      }

      // Solve L'*X = Y;
      for (int k = n-1; k >= 0; k--) {
         for (int j = 0; j < nx; j++) {
            X[k][j] /= L[k][k];
         }
         for (int i = 0; i < k; i++) {
            for (int j = 0; j < nx; j++) {
               X[i][j] -= X[k][j]*L[k][i];
            }
         }
      }
      return new Matrix(X,n,nx);
   }
}