//***********************************************************************
// Program:     bmimnorrl-n8.cpp ->mg3n or mg3u(normal or uniform priori)
//                                                                       
// Programmers: Wen Zeng and Bin Xiang 
//
//              modified by Bin Xiang, Mar 8, 2002. 
//                  Reorgnized program. Added data management functions.
//                  Ksquare is set as 0.5sigmaE for u, a, d normal priors
//              modified by Bin Xiang, Feb 1, 2002. 
//                  Corrected a bug in updateW function.
//              modified by Bin Xiang, May 1, 2001. 
//                  Changes made to compile program using VC++ in windows 
//      		    Changed MAXOBS to varible type 
//					Changed static arrays to dynamic arrays
//                  Program automatically check file size to get MAXOBS
//                  Changed function to tranfer data via pointer for arrays
//
//              modified by Wen Zeng May. 6, 2000 to analyze real data by 
//                  changing MAXOBS number and using different design 
//                  matrix named incidicr.dat instead incidic.dat           
//
//              modified by Wen Zeng Mar. 24, 00                                       
//              Original code by Wen Zeng, Mar. 12, 2000                                   
//
// Purpose:     to implement the Bayesian analysis for the major gene       
//              detection using MIM model. The genotypes of parents and     
//              their progenies will be updated by the parent block method. 
// Mixed Inheriance Model:                                               
//              Y = XB + ZU + WLm + e                                         
//
//***********************************************************************

#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <io.h>
#include <math.h>
#include <time.h>       // time(0)                         
#include <fstream.h>    // file I/O                        
#include <iostream.h>   // input/output stream             
#include <iomanip.h>    // manipulate the format of output 

//#include "cdflib.h"
//#include "randlib.h"
#include "bmimnorrl_n.h"

//#ifndef MAXPARA 
//#define MAXPARA 24 
//#endif

using namespace std;

const int STRING_LENGTH = 40;
typedef char stringType[STRING_LENGTH+1];

// Global Varibles: 
int MAXOBS;
	//total number of observations in the org.dat or lines in incidcr.dat.
int NP;
	//number of parents in the diallel design.
int NC;
	//number of crosses in the diallel design.
int MAXPARA;
	//total number of location parameters or columns in the incidcr.dat.
 
// Data management functions: 
void argResolve ( stringType InF[], stringType OF[], int argc, char* argv[], 
				 int& nIte, int& lag );
     //Resolve the arguments of main function. Obtain file names, Iteration
     //number, lag length etc.

void getMAXOBS( stringType InF1 );
     //Get the number of obseration from INF_incidc.dat file.

void getData(stringType InF[], int * fe, int * ma, 
			 double * y, int **hMat, double * theta, double * sigma, double& sigmaE, 
		     double& freq, double * hyperPar );
     //Arguements:     
     //      read in phenotype observations Y, fixed para B, random para U,
     //      major gene effects m, design matrix X, incidence matrix Z, and
     //      genotype matrix W. The initial values for MCMC is also readin.

void updateBuffer(::ofstream& outT, ::ofstream& outS, ::ofstream& outPTG,
				  double ** Buffer_theta, double ** Buffer_sigma, double ** Buffer_PG, 
				  int& Bi, int lag, int ite, int nIte, double * sigma, double& sigmaE, 
				  double& freq, double * theta, int * PG );

//Core functions:
int cross( int p1, int p2 );

     //Arguement:      
     //   Given the genotypes of two parents p1 & p2, the genotype of a     
     //   progeny will be generated under two alleles one major gene        
     //   genetic model assumption.                                         
     //                                                                     
     //Side effects:   
     //   the indicator of genotype of the progeny will be returned after   
     //   the function is called.                                           

void parentIndex(int * fe, int * ma, int **feIndex, int **maIndex);
	 //To create two index tables feIndex, maIndex with obs numbers for each 
	 //half-sib family or parent. The last rows give the total number of obs 
	 //for that parent.

void hMatSum(int **hMat, int * hh);
	 //Arguements:
	 //		Calculate h'(j)*h(j) i.e. sum square of the corresponding column in 
	 //		h matrix hMat for each location parameter theta.

double genoProb( int p1, int p2, int A );

     //Arguments:      
     //        calculate the the genotype probability of a progeny if the  
     //        genotypes of two parents are known.                         

//MCMC update functions:
void updateGenotype( double freq, int * fe, int * ma, double * y, int * PG, 
        int * YG, double * theta, int **hMat, double sigmaE, double * Yt,
		double ** Yw, int ** feIndex, int ** maIndex );
     
     //Arguments:      
     //      update the parent's and progeny's genotypes by parent block.  
     //      one parent's genotype will be updated first, then all its     
     //      progenies will be updated immdeiately. Then the second        
     //      parent. So, ezch progeny will be updated twice after one cycle
     //                                                                    
     //Side effects:   
     //      parent's and progeny's genotypes are changed after the call.  
     
void updateW( int **hMat, int * YG, int * hh );

     //Arguments:      
     //        update the genotype incidence matrix for all progenies.    
	 //		   calculate h'(j)h(j) for location parameter a and d.    

void updateFreq( double& freq, int * PG, double p5, double p6 );

     //Arguments:      
     //        update the frequence of favorable allele                   

void updateTheta( int **hMat, double * theta, double * sigma,
                  double sigmaE, double * y, int *hh );

     //Arguments:      
     //        update the location parameters.                            
     //        normal priors for the fixed and major gene effects         

void updateThetaUni( int **hMat, double * theta, double * sigma,
                  double sigmaE, double * y, int *hh );
     //Same as update Theta except using uniform prior for u, a, d.

void updateSigma( double *theta, double * sigma, double& sigmaE, 
                  int **hMat, double * y, double * hyperPar );

    //Arguments:      
    //        update the scale parameters.                            



//*************************** Main program *********************************/
int main( int argc, char* argv[])

// argc = 6 if input files specified, otherwise argc = 1
// argv[0] = file name of compiled program;
// argv[1] - argv[3] = input file names;
// argv[4] = chainID; argv[5] = Iteration number.

{
  int nIte=100; //declare iteration number.
  int lag=5;    //lag distance for thinning the chain.
  NP=6;     //defaut number of parents;
  NC=NP*(NP-1)/2; //defaut number of crosses;

  stringType InF[3];  //Names for input file: org, incidcr, initial.
  stringType OF[3];   //Names for output file: btheta, bsigma, btgeno.
  argResolve (InF, OF, argc, argv, nIte, lag );
  MAXPARA=3 + NP + NC; 
  cout<<"MAXPARA="<<MAXPARA<<endl;

  getMAXOBS( InF[1] ); //Get total number of observations.

//*************  declare the variables  **************//  
  int *fe = new int[ MAXOBS ];
  int *ma = new int[ MAXOBS ];
  double *y = new double[ MAXOBS ];
  int *YG = new int[ MAXOBS ];  // major genotypes for each obs 
  int *PG = new int[ NP ];         
	// major genotypes for NP parents     
	// major genotype: 2: A1A1   theta[1]
	//                 1: A1A2   theta[2]
	//                 0: A2A2  -theta[1]
  
  double *theta = new double[ MAXPARA ];     //location paramenter vector.
  double *sigma = new double[ MAXPARA ];     //variance of each parameter.
  double sigmaE = 0;           //error variance.
  double freq = 0;             //major gene frequency.
  double hyperPar[ 7 ];        //hyper parameters K^2, Ui, Vi, alpha, betha.

  double ** Buffer_PG = new double * [5000];   //create Buffer array
  double ** Buffer_theta = new double * [5000];   
  double ** Buffer_sigma = new double * [5000];   
  for(int n=0; n<5000; n++) {
	  Buffer_PG[n] = new double [NP+1];
	  Buffer_theta[n] = new double [NP+1];
	  Buffer_sigma[n] = new double [7];
  }
  int Bi=0;         // counter in Buffer
  
  int ** hMat = new int * [ MAXOBS ];
  for ( int r = 0;  r < MAXOBS;  r++ )
      hMat[r] = new int [ MAXPARA ];
  int *hh = new int[ MAXPARA+1 ];  //sum of squares of corresponding columns of hMat.
                       //hh[MAXPARA] is the sum of the 2nd column of hMat i.e. hMat[][1].

  double * Yt = new double [MAXOBS]; //calculate y-xb-zu;
  double ** Yw =new double * [MAXOBS]; // prob for three possible genotypes of progenies  
  for( n=0; n<MAXOBS; n++) Yw[n] = new double [3];

  int ** feIndex= new int * [MAXOBS];
  int ** maIndex= new int * [MAXOBS];
  for ( r = 0;  r < MAXOBS;  r++ ) 
  {
	  feIndex[r] = new int [NP];
	  maIndex[r] = new int [NP];
	  for (int k =0; k < NP; k++ )
	  {
		  feIndex[r][k]=0;
		  maIndex[r][k]=0;
	  }
  }

    
//****** read input files and initialize parameters. ********/
  getData(InF, fe, ma, y, hMat, 
		theta, sigma, sigmaE, freq, hyperPar );

  // generate the progeny's genotypes from the initial parents
  long seed = time(0);
  setall ( abs(seed), abs(seed/2) );

  cout << "Parent genotype";
  double gen = 0;
  double bb = freq*freq;
  double bbb = freq*(2-freq);
  for( int i = 0; i < NP ; i++ )
  {
      gen = genunf(0.0, 1.0);
      if( gen <= bb ) PG[i] = 2;
      else if( gen <= bbb ) PG[i] = 1;
      else PG[i] = 0;
      cout << PG[i] << ", ";
  }
  cout << endl;

  for( i = 0; i < MAXOBS ; i++ )
  {
      YG[i] = cross( PG[fe[i]-1], PG[ma[i]-1] );
  }
  updateW( hMat, YG, hh );
  parentIndex(fe, ma, feIndex, maIndex);
  hMatSum(hMat, hh);
//****** open output files and ready for writing ********/
  ::ofstream outT;
  ::ofstream outS;
  ::ofstream outPTG;

  outT.open( OF[0] );	  // output theta file name
  while ( !outT )
  {
      cerr <<"The file, btheta.dat, can not be open!!" << endl;
      exit(1);
  }
//  outT<<"iter   g1     g2     g3     g4     g5     g6"<<endl;
  outT <<"iter";
  for(i = 1; i < NP+1; i++) outT << "     g" << i;
  outT << endl;

  outS.open( OF[1] );	  // output sigma file name 
  while ( !outS )
  {
      cerr <<"The file, bsigma.dat, can not be open!!" << endl;
      exit(1);
  }
  outS << "iter     sigmaG  sigmaS  sigmaE  freq  a    d" << endl;

  outPTG.open( OF[2] );     //output parental genotype file name	 
  while ( !outPTG )
  {
      cerr <<"The file, btGeno.dat can not be open!!" <<  endl;
      exit(1);
  }

  
//***********  MCMC begin **************/
  for( int ite=0; ite < nIte; ite++ )
  {   
	  if (ite%100 == 0 || ite == nIte-1 ) cout << "Iteration:" << ite << endl;
      //print iter number every 100 iteration, modify on 3/5/2002; 
      updateGenotype( freq, fe, ma, y, PG, YG, theta, hMat, sigmaE, 
		              Yt, Yw, feIndex, maIndex );
      updateW( hMat, YG, hh );
      updateFreq( freq, PG, hyperPar[5], hyperPar[6] );
      updateTheta( hMat, theta, sigma, sigmaE, y, hh );      //Normal prior.
	  //updateThetaUni( hMat, theta, sigma, sigmaE, y, hh ); //Uniform prior.
      updateSigma( theta, sigma, sigmaE, hMat, y, hyperPar );
	  updateBuffer( outT, outS, outPTG,Buffer_theta, Buffer_sigma, Buffer_PG, 
		            Bi, lag, ite, nIte, sigma, sigmaE, freq, theta, PG);
  } // for loop 

  outT.close();
  outS.close();
  outPTG.close();
  
  //delete matrices
  //for ( r = 0;  r < MAXOBS;  r++ )
  //       delete[]  hMat[r] ;

  for( n=0; n<MAXOBS; n++) delete[] hMat[n], Yw[n];
  for( n=0; n<5000; n++) delete[] Buffer_PG[n], Buffer_theta[n], Buffer_sigma[n];
  delete[] Yw, Yt, hMat, fe, ma, y, YG;
  return 0;

} // main loop 

//************************* data management functions *******************************/
void argResolve ( stringType InF[], stringType OF[], int argc, char* argv[], 
				 int& nIte, int& lag )
// argc = 6-9 if input files specified, otherwise argc = 1-4
// argv[0] = file name of compiled program;
// argv[1] - argv[3] = input file names;
// argv[4] = chainID; argv[5] = Iteration number.
{
  //if(argc != 1 && argc!=2 && argc!=3 && argc != 6 && argc !=7 && argc !=8)
  if(argc == 5 || argc>9 )
  {
	  cout <<"Usage: majorgene [file1 file2 file3 chainID Iteration-number] [lag] [NP] [NC]!" << '\n';
	  exit(0);
  }
  for(int i=0; i<argc; i++)
  {
	  cout << "arg" << i << "=" << argv[i] << '\n';
  }
  
  if ( argc == 1 || argc==2 || argc == 3 || argc == 4) 
  {
  	  strcpy(InF[0],"org.dat");
	  strcpy(InF[1], "incidcr.dat");
	  strcpy(InF[2], "initial.dat");
	  
	  strcpy(OF[0], "btheta.dat");
	  strcpy(OF[1], "bsigma.dat");
	  strcpy(OF[2], "btgeno.dat");
	  
	  if (argc==2) lag=atoi(argv[1]);
	  if (argc==3) { lag=atoi(argv[1]); NP=atoi(argv[2]); NC=NP*(NP-1)/2;}
	  if (argc==4) { lag=atoi(argv[1]); NP=atoi(argv[2]); NC=atoi(argv[3]);}

	  cout << endl;
	  cout << "Please enter the number of iteration:";
	  cin >> nIte;
  }

  if ( argc == 6 || argc ==7 || argc == 8 || argc == 9) {
	  strcpy(InF[0], argv[1]);
	  strcat(InF[0],".dat");
	  strcpy(InF[1], argv[2]);
	  strcat(InF[1],".dat");
	  strcpy(InF[2], argv[3]);
	  strcat(InF[2],".dat");
	  
	  strcpy(OF[0], argv[4]);
	  strcat(OF[0],"_btheta.dat");
	  strcpy(OF[1], argv[4]);
	  strcat(OF[1],"_bsigma.dat");
	  strcpy(OF[2], argv[4]);
	  strcat(OF[2],"_btgeno.dat");
 
	  nIte=atoi(argv[5]);
	  if (argc==7) lag=atoi(argv[6]);
	  if (argc==8) { lag=atoi(argv[6]); NP=atoi(argv[7]); NC=NP*(NP-1)/2;}
	  if (argc==9) { lag=atoi(argv[6]); NP=atoi(argv[7]); NC=atoi(argv[8]);}
	  
	  cout << "The total number of iteration will be executed:" << nIte <<endl;
  }
  
   cout << "org file name" << "=" << InF[0]  << '\n';
   cout << "incidc file name" << "=" << InF[1] << '\n';
   cout << "initial file name" << "=" << InF[2] << '\n';

   for( i=0; i<argc; i++) {
	   cout << "arg" << i << "=" << argv[i] << '\n';
   }
}

inline void getMAXOBS( stringType InF1 )
// check the total number of observations in org.dat 
{  
  struct stat buf;
  int in_file;
  int errno;

  in_file = open(InF1, O_RDONLY);
  
  if( in_file != 0 ) {
     errno = fstat(in_file, &buf);
  }
  else {
     cout << "Cannot open file!";
     exit(0);
  }
  
  (void)close(in_file);

  if (errno == 0) 
	  MAXOBS=buf.st_size/(MAXPARA*2 + 1); // obs=file-size/(line-size+1) for windows text file.
  else
	  cout << "Error code:"<< errno << endl;

  cout << "Total number of observations is:" << MAXOBS <<endl;
}

void getData(stringType InF[], int * fe, int * ma, 
			 double * y, int **hMat, double * theta, double * sigma, double& sigmaE, 
		     double& freq, double * hyperPar )
{

  /* read the observations in */
  ::ifstream inA;
  inA.open( InF[0] );	 
  int i = 0;
  while(  inA && !inA.eof() )
  {
      inA >> fe[i];
      inA >> ma[i];
      inA >> y[i];
      inA.ignore ( 80, '\n' );
	  i++;
  }
  inA.close();


  /* read the incidence matrix in */
  ::ifstream inB;
  inB.open( InF[1] );	 
  while ( !inB )
  {
      cerr <<"The file, incidcr.dat, can not be open!!" << endl;
      exit(1);
  }

  int ii=0;
  while( inB && !inB.eof() )
  {
      for( int j = 0; j < MAXPARA; j++ )  inB >> hMat[ii][j];
      inB.ignore ( 80, '\n' );
	  ii++;
  }
  inB.close();

  /* read the initial values in */
  ::ifstream inC;
  inC.open( InF[2] );	 
  while ( !inC )
  {
      cerr <<"The file, initial.dat, can not be open!!" << endl;
      exit(1);
  }

  inC >> theta[0] >> theta[1] >> theta[2];
  inC.ignore ( 80, '\n' );

  for( i = 3; i < NP+3; i++ )  inC >> theta[i];
  inC.ignore ( 80, '\n' );

  for( i = NP+3; i < MAXPARA; i++ )  inC >> theta[i];
  inC.ignore ( 80, '\n' );

  inC >> sigma[3] >> sigma[NP+3] >> sigmaE >> freq;
  inC.ignore ( 80, '\n' );

  for( i = 4; i < NP+3; i++ )   sigma[i] = sigma[3]; 
  for( i = NP+4; i < MAXPARA; i++ )  sigma[i] = sigma[NP+3]; 
 
  inC >> hyperPar[0] >> hyperPar[1] >> hyperPar[5] >> hyperPar[6];
  inC.ignore ( 80, '\n' );
  
  inC.close();

  for( i = 0; i < 3; i++ )   sigma[i] = 0.5*sigmaE; // hyperPar for u, a, d: Ki= .05sigmaE.
  //for( i = 0; i < 3; i++ )   sigma[i] = hyperPar[0];
  hyperPar[2] = (hyperPar[1] - 1)*sigma[3];
  hyperPar[3] = (hyperPar[1] - 1)*sigma[NP+3];
  hyperPar[4] = (hyperPar[1] - 1)*sigmaE;

  //hyperPar's are chosen to obtain flat priors.
/*
  for( i = 0; i < 3; i++ ) sigma[i] = 0.5*sigmaE; //hyperPar for u, a, d: Ki= 0.5sigmaE.
  hyperPar[1] = -1;                               //hyperPar Ui for sigmaG, sigmaS, sigmaE.
  for( i = 2; i < 5; i++ ) hyperPar[i] = 0;       //hyperPar V1 for sigmaG, sigmaS, sigmaE.
*/

  for(int k=0; k<7; k++) cout << hyperPar[k] << " ";
  cout << "hyperparameters" << endl;

  for( k=0; k<NP+5; k++) cout << sigma[k] << " ";
  cout << "sigma " << sigmaE << endl;
  cout << "Freq: " << freq << endl;  
}

void updateBuffer(::ofstream& outT, ::ofstream& outS, ::ofstream& outPTG,
				  double ** Buffer_theta, double ** Buffer_sigma, double ** Buffer_PG, 
				  int& Bi, int lag, int ite, int nIte, double * sigma, double& sigmaE, 
				  double& freq, double * theta, int * PG )
{
	  if (ite%lag == 0 && Bi < 5000) {
		  Buffer_theta[Bi][0]=ite+1;
		  for(int k=1; k < NP+1; k++) Buffer_theta[Bi][k]=theta[k+2];
		  
		  Buffer_sigma[Bi][0]=ite+1;
		  Buffer_sigma[Bi][1]=sigma[3];
		  Buffer_sigma[Bi][2]=sigma[9];
		  Buffer_sigma[Bi][3]=sigmaE;
		  Buffer_sigma[Bi][4]=freq;
		  Buffer_sigma[Bi][5]=theta[1];
		  Buffer_sigma[Bi][6]=theta[2];
		  
		  Buffer_PG[Bi][0]=ite+1;
		  for( k=1; k < NP+1; k++) Buffer_PG[Bi][k]=PG[k-1];
		  Bi++;
	  }

	  if (Bi%5000 == 0) Bi=0;

	  // write the samples in buffer to external files 
      
	  if ( (Bi==0 && ite%lag == 0) || (ite==nIte-1 && Bi!=0) )
		  //either buffer is full or ite reaches nIte-1, buffer is flushed out to files.
	  {
		 int nj= 5000*(Bi==0) + Bi*(Bi>0);
			 
		 for( int j=0; j < nj; j++ ) 
		 { 
			 outT.precision(9);
			 outT << Buffer_theta[j][0] << " ";  
			 for (int k=1; k < NP+1; k++ ) 
			 {
				 outT.precision(4);
				 outT << Buffer_theta[j][k] << " ";
				 //outT << "writing to files" << " ";
			 }
			 outT << endl;
		 }
         
		 for( j=0; j < nj; j++ ) 
		 { 
			 outS.precision(9);
			 outS << Buffer_sigma[j][0] << " ";  
			 for (int k=1; k < 7; k++ ) 
			 {
				 outS.precision(4);
				 outS << Buffer_sigma[j][k] << " ";
			 }
			 outS << endl;
		 }

		 for( j=0; j < nj; j++ ) 
		 { 
			 outPTG.precision(9);
			 outPTG << Buffer_PG[j][0] << " ";  
			 for (int k=1; k < NP+1; k++ ) 
			 {
				 outPTG.precision(4);
				 outPTG << Buffer_PG[j][k] << " ";
			 }
			 outPTG << endl;
		 }
	 }
}

//*********** Core functions called by main and MCMC update functions***********/
inline int cross( int p1, int p2 ){
// Coding of genotypes in p1, p2, and progeny:
// 2:A1A1 1:A1A2 0:A1A2, where A1 is favorable allele.
	   
	int progeny;
	double num;
	
	switch((p1+1)*(p2+1))
	{
	   case 1:
		   progeny = 0; // p1=0, p2=0;
		   break;
	   case 2:
           num = genunf( 0.0, 1.0 );
	       if( num <= 0.5 ) progeny = 0;
	       else progeny=1; //p1=0, p2=1 or p1=1, p2=0;
	       break;
	   case 3:
		   progeny=1; //p1=0, p2=2 or p1=2, p2=0;
		   break;
	   case 4:
		   num = genunf(0.0, 1.0);
	       if( num <= 0.25 ) progeny = 0;
	       else if( num <= 0.75 ) progeny = 1;
	       else progeny = 2; //p1, p2=1;
	       break;
	   case 6:
	       num = genunf(0.0, 1.0);
	       if( num <= 0.5 ) progeny = 1;
	       else progeny = 2;  // p1=1, p2=2 or p1=2, p2=1;
	       break;
	   case 9:
		   progeny = 2;  // p1, p2=2;
		   break;
	   default:
		   cout <<"Something WRONG with genotype of parent 2"<<endl;
		   cout <<"!! Please check the program again !!"<<endl;
		   abort();
	   }

       return progeny;
}

inline void parentIndex(int * fe, int * ma, int **feIndex, int **maIndex)
{
//To create two tables with obs numbers for each half-sib family, i.e. parent;
  int *pf = new int[NP]; //number of obs for each female parent;
  int *pm = new int[NP]; //number of obs for each male parent;
  for( int i = 0; i < NP; i++) { pf[i] = 0; pm[i] = 0; }

  for( int n = 0; n < MAXOBS; n++ )
  {
	  feIndex[pf[fe[n]-1]][fe[n]-1] = n;
	  maIndex[pm[ma[n]-1]][ma[n]-1] = n;
	  
	  pf[fe[n]-1]++;
	  pm[ma[n]-1]++;
  }

  for(i = 0; i < NP; i++)
  {
	  feIndex[MAXOBS-1][i] = pf[i];
	  maIndex[MAXOBS-1][i] = pm[i];
	  //cout << "feIndex[" << MAXOBS-1 << "][" << i <<"]=" << feIndex[MAXOBS-1][i];
	  //cout << "  maIndex[" << MAXOBS-1 << "][" << i <<"]=" << maIndex[MAXOBS-1][i] << endl;
  }
  delete[] pf, pm;
}

inline void hMatSum(int **hMat, int * hh)
{
	hh[0] = MAXOBS;
	for( int j = 1; j < MAXPARA + 1 ; j++) hh[j] = 0;
	for( j = 3; j < MAXPARA; j++)
	{
		for( int k = 0; k < MAXOBS; k++ ) hh[j] += hMat[k][j];
		// hmat[k][j] is either 1 or 0 for j>2. So hMat[k][j]^2=hMat[k][j]
	}
}

inline double genoProb( int p1, int p2, int A  )
{
  double prob = 0;

  	switch((p1+1)*(p2+1)){
	   case 1:
		   prob = ( A==0 );; // p1=0, p2=0;
		   break;
	   case 2:
           prob = ( A==0 )*.5 + ( A==1 )*.5; //p1=0, p2=1 or p1=1, p2=0;
	       break;
	   case 3:
		   prob = ( A==1 ); //p1=0, p2=2 or p1=2, p2=0;
		   break;
	   case 4:
		   switch(A){
		   case 0: prob = .25; break;
		   case 1: prob = .5; break;
		   case 2: prob = .25; break;
		   //prob=(A==0)*.25 + (A==1)*.5 + (A==2)*.25; //p1, p2=1;
		   default: cout <<"WRONG genotype value for A"<<endl; abort();
		   }
		   break;
	   case 6:
	       prob = ( A==1 )*.5 + ( A==2 )*.5;  // p1=1, p2=2 or p1=2, p2=1;
	       break;
	   case 9:
		   prob = ( A==2 );  // p1, p2=2;
		   break;
	   default:
		   cout <<"Something WRONG with genotype value of p1, p2 or A"<<endl;
		   cout <<"!! Please check the program again !!"<<endl;
		   abort();
	   }
  return prob;
}

//*************************** MCMC update functions *********************************/
inline void updateGenotype( double freq, int * fe, int * ma, double * y, int * PG, 
        int * YG, double * theta, int **hMat, double sigmaE, double * Yt,
		double ** Yw, int ** feIndex, int ** maIndex )
{
  double sum = 0;
  double b1 = 0;
  double b2 = 0;
  double ran = 0;
  double **Pw = new double *[ NP+1 ];  // prob for three possible genotypes of six parents
  for(int i = 0; i < NP+1; i++) Pw[i]= new double[3];

  double a[3];		// take the major gene effects for the futher use
  a[0] = - theta[1];  a[1] = theta[2];  a[2] = theta[1];
  
  double ZU=0, sumLog=0;   // temporary variables
  
  for( int n=0; n<MAXOBS; n++ )
  {
	  ZU=0;
	  for( int i = 3; i < MAXPARA; i++ ) ZU += hMat[n][i]*theta[i];
	  Yt[n] = y[n] - theta[0] - ZU;
	  //cout << " Yt=" << Yt[n] << endl;
  }

  for( int p = 1; p < NP+1; p++ )
  {
	  for( int g = 0; g < 3; g++ )
	  {
          sumLog = 0; 
		  switch(g){
		  case 2: Pw[p][g] = log(freq*freq); break;
		  case 1: Pw[p][g] = log(2*freq*(1-freq)); break;
		  case 0: Pw[p][g] = log((1-freq)*(1-freq)); break;
		  }

		  //Pw[p][g] = log((g==2)*freq*freq+(g==1)*2*freq*(1-freq)+
		  //         (g==0)*(1-freq)*(1-freq));
		  
		  if ( feIndex[MAXOBS-1][p-1] > 0 ) 
		  {
			  for( int r = 0; r < feIndex[MAXOBS-1][p-1]; r++)
			  {
				  int n = feIndex[r][p-1];
				  double result = 0;
				  for( int i = 0; i < 3; i++ )
				  {
					result +=genoProb(g, PG[ma[n]-1], i)*exp(-(Yt[n]-a[i])*(Yt[n]-a[i])/2.0/sigmaE);
				  }
				  sumLog += log(result);
				  //cout << "sumLog result="<< result << " n="<< n << endl;   
			  }
		  }
		  
		  if ( maIndex[MAXOBS-1][p-1] > 0 ) 
		  {
			  for( int r = 0; r < maIndex[MAXOBS-1][p-1]; r++)
			  {
				  int n = maIndex[r][p-1];
				  double result = 0;
				  for( int i = 0; i < 3; i++ )
				  {
					result +=genoProb(PG[fe[n]-1], g, i)*exp(-(Yt[n]-a[i])*(Yt[n]-a[i])/2.0/sigmaE);
				  }
				  sumLog += log(result);
				  //cout << "sumLog result="<< result << " n="<< n << " g="<< g << endl;   
			  }
		  }
		  
		  Pw[p][g] += sumLog;
      } //g loop
      //cout <<"Pw:"<<Pw[p][0]<<" : "<<Pw[p][1]<<" : "<<Pw[p][2]<<endl;
	  
      b1=1/(1 + exp(Pw[p][1] - Pw[p][2]) + exp(Pw[p][0] - Pw[p][2]));
      b2=1-1/(1 + exp(Pw[p][2] - Pw[p][0]) + exp(Pw[p][1] - Pw[p][0]));

      ran = genunf( 0.0, 1.0 );
      if( ran <= b1 ) PG[p-1] = 2;
      else if( ran <= b2 ) PG[p-1] = 1;
      else PG[p-1] = 0;

      //cout << "b1=" << b1 << " b2=" << b2 <<  endl; 

      // update progenies of the pth parent
	  
	  if ( feIndex[MAXOBS-1][p-1] > 0 ) 
	  {
		  for( int r = 0; r < feIndex[MAXOBS-1][p-1]; r++)
		  {
			  int n = feIndex[r][p-1];
			  for( int pg = 0; pg < 3; pg++ )
			  {
				  Yw[n][pg] = genoProb( PG[fe[n]-1], PG[ma[n]-1], pg );
				  Yw[n][pg] *= exp( -(Yt[n]-a[pg])*(Yt[n]-a[pg])/2.0/sigmaE );
				  //cout << " Yw1="<< Yw[n][pg] << " n="<< n <<" pg="<< pg << endl;   
			  }
			  sum = Yw[n][0] + Yw[n][1] + Yw[n][2];
			  //cout << " sum="<< sum << " n="<< n << endl;

			  b1 = Yw[n][2]/sum;
			  b2 = ( Yw[n][2] + Yw[n][1] )/sum;
			  ran = genunf( 0.0, 1.0 );
			  if( ran <= b1 ) YG[n] = 2;
			  else if( ran <= b2 ) YG[n] =1;
			  else YG[n] = 0;
		  }
	  }
	  
	  if ( maIndex[MAXOBS-1][p-1] > 0 ) 
	  {
		  for( int r = 0; r < maIndex[MAXOBS-1][p-1]; r++)
		  {
			  int n = maIndex[r][p-1];
			  for( int pg = 0; pg < 3; pg++ )
			  {
				  Yw[n][pg] = genoProb( PG[fe[n]-1], PG[ma[n]-1], pg );
				  Yw[n][pg] *= exp( -(Yt[n]-a[pg])*(Yt[n]-a[pg])/2.0/sigmaE );
				  //cout << " Yw1="<< Yw[n][pg] << " n="<< n <<" pg="<< pg << endl;   
			  }
			  sum = Yw[n][0] + Yw[n][1] + Yw[n][2];
			  //cout << " sum="<< sum << " n="<< n << endl;
			  
			  b1 = Yw[n][2]/sum;
			  b2 = ( Yw[n][2] + Yw[n][1] )/sum;
			  ran = genunf( 0.0, 1.0 );
			  if( ran <= b1 ) YG[n] = 2;
			  else if( ran <= b2 ) YG[n] =1;
			  else YG[n] = 0;
		  }
	  }
  } // p loop 
  return;
}
 
inline void updateW( int **hMat, int * YG, int * hh )
{
	hh[1]=0; hh[2]=0; hh[MAXPARA]=0; //hh[MAXPARA] is the sum of hMat[][1]
	for( int n = 0; n < MAXOBS; n++  )
	{
		switch( YG[n] )
		{
		case 0:
			hMat[n][1] = -1;
			hMat[n][2] = 0;
			hh[1]++;
			hh[MAXPARA]+= -1;
			break;
		case 1:
			hMat[n][1] = 0;
			hMat[n][2] = 1;
			hh[2]++;
			break;
		case 2:
			hMat[n][1] = 1;
			hMat[n][2] = 0;
			hh[1]++;
			hh[MAXPARA]+= 1;
			break;
		default:
	      cerr <<"No such a code:" << YG[n] << endl;
	      cerr <<"!! Please check the program again !!" << endl;
	      exit(1);	            
		}
	}
	return;
}

inline void updateFreq( double& freq, int * PG, double p5, double p6 )
{
  double nA = 0;
  for( int i = 0; i < NP; i ++ )  nA += PG[i];
  double na = 2*NP - nA;
  na += p6;
  nA += p5;
  //cout << "nA: " << nA << ", na: " << na << ", p5:" << p5 << ", p6:" << p6 << ", NP:" << NP <<endl;
  freq = genbet( nA, na );
  //cout << "nA: " << nA << ", na: " << na << ", freq:" << freq << endl;
}

inline void updateTheta( int **hMat, double * theta, double * sigma,
                  double sigmaE, double * y, int * hh )
{
  double sum1, sum2, mean, sd;
  //double mean1, sd1, theta1;

  for( int j=0; j < MAXPARA; j++ )
  {
      sum1 = 0; sum2 = 0; mean = 0; sd = 0;

      for( int k=0; k < MAXOBS; k++ )
      {
		  sum1 = 0;
		  for( int r=0; r < MAXPARA; r++ )
		  {
	      if( r != j ) sum1 +=hMat[k][r]*theta[r];
		  }
      	  sum2 += ( y[k] - sum1 )*hMat[k][j];
      }

	  if ( ( hh[j] == 0 ) || ( j == 1 && abs ( hh[MAXPARA] ) == MAXOBS ) ||
			  ( j == 2 && hh[j] == MAXOBS ) ) {
		  sd = sqrt(sigma[j]);
		  theta[j] = gennor( 0.0, sd );
		  //if hh[1]=0 or hh[MAXPARA], theta[1] takes prior N(0,sigma[1]);
		  //if hh[2]=0 or hh[2]=MAXOBS, theta[2]=0 takes prior N(0,sigma[2]).
	  }
	  else {
		  mean = sum2/( hh[j] + sigmaE/sigma[j] ) ;
		  sd = sqrt( sigmaE/( hh[j] + sigmaE/sigma[j] ) );
		  theta[j] = gennor( mean, sd );
	  }
  }
  return;
}

inline void updateThetaUni( int **hMat, double * theta, double * sigma,
                  double sigmaE, double * y, int * hh )
//Using uniform prior for u,a,d.
{
  double sum1, sum2, mean, sd;
  //double mean1, sd1, theta1;

  for( int j=0; j < MAXPARA; j++ )
  {
      sum1 = 0; sum2 = 0; mean = 0; sd = 0;

      for( int k=0; k < MAXOBS; k++ )
      {
		  sum1 = 0;
		  for( int r=0; r < MAXPARA; r++ )
		  {
	      if( r != j ) sum1 +=hMat[k][r]*theta[r];
		  }
      	  sum2 += ( y[k] - sum1 )*hMat[k][j];
      }
	  
	  if ( j<3 ) {
		  if ( ( hh[j] == 0 ) || ( j == 1 && abs ( hh[MAXPARA] ) == MAXOBS ) ||
			  ( j == 2 && hh[j] == MAXOBS )) {
			  theta[j]=0;
			  //if hh[1]=0 or hh[MAXPARA], theta[1]=0;
			  //if hh[2]=0 or hh[2]=MAXOBS, theta[2]=0.

			  //theta[j]=genunf( -sigmaE, sigmaE );
		  }
		  else {
			  mean = sum2/hh[j];
			  sd = sqrt( sigmaE/hh[j]  );
			  theta[j] = gennor( mean, sd );
		  }
 	  }
	  else {
		  mean = sum2/( hh[j] + sigmaE/sigma[j] ) ;
		  sd = sqrt( sigmaE/( hh[j] + sigmaE/sigma[j] ) );
		  theta[j] = gennor( mean, sd );
	  }

  }
  return;
}

inline void updateSigma( double * theta, double * sigma, double& sigmaE, 
               int **hMat, double * y, double * hyperPar )
{
  int ng = NP;
  int ns = NP*(NP-1)/2;
  int n = MAXOBS;
  double sum1 = 0;
  double sum2 = 0;
  double sum3 = 0;
  double deiv;
  
  for( int i=3; i < 9; i++ )  sum1 += theta[i]*theta[i];  
  for( int j=9; j < MAXPARA ; j++ )  sum2 += theta[j]*theta[j];  
  for( int k =0; k < MAXOBS ; k++ )
  {
      deiv = 0;
      for( int l = 0; l < MAXPARA ; l ++ ) 
      {
           deiv += hMat[k][l]*theta[l];
      }
      sum3 += ( y[k] - deiv )*( y[k] - deiv );
  }

  double ug = ng/2.0 + hyperPar[1]; 
  double vg = sum1/2.0 + hyperPar[2];
  double us = ns/2.0 + hyperPar[1];  //int ns=15, 15/2!=15/2.0 !!!
  double vs = sum2/2.0 + hyperPar[3];
  double ue = n/2.0 + hyperPar[1];
  double ve = sum3/2.0 + hyperPar[4];
  
  //cout << "sum1=" << sum1 << " vg=" << vg << " ug=" << ug << endl;

  sigma[3] = 1/gengam( vg, ug ); 
  while ( sigma[3] < 1e-12 ) sigma[3] = 1/gengam( vg, ug ); 
  for( i=4; i < ng+3; i++ )   sigma[i] = sigma[3];
  sigma[ng+3] = 1/gengam( vs, us ); 
  while ( sigma[ng+3] < 1e-12 ) sigma[ng+3] = 1/gengam( vs, us ); 
  for( i=ng+4; i < MAXPARA; i++ ) sigma[i] = sigma[9];
  sigmaE = 1/gengam( ve, ue );
  while ( sigmaE < 1e-12 ) sigmaE = 1/gengam( ve, ue );
  return;
}