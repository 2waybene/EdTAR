/*****************************************************
* MG_Input.sas
*
* By Bin Xiang May 17, 2002
*
* Purpose: Read in a half-diallel data set and prepare 
*          for inputs for MajorGene programs.
*
******************************************************/

/* For debugging
%let ser=015;
%let path=d:\research\dialldatadj;
%let file=i015.DAT;
%let dataset=one;
%let np=5;
*/
*options mprint ;

%MACRO desg( path, ser, dataset, np=6 );
***np is the number of parents;
 %let nc=%eval(&np*(&np-1)/2);  **Maximum number of crosses;
 %let dummyp1=; %let dummyp2=; %let dummyc=;
 %macro dummy;					***Aggregate dummy variable names;
   %do i=1 %to &np;
     %let dummyp1=&dummyp1. "p&i";	***Produce p1 ... pn;
     %let dummyp2=&dummyp2. p&i.*t; ***Produce p1*t ... pn*t;
   %end;
   %do i=1 %to &nc;
     %let dummyc=&dummyc. "c&i";	***Produce "c1" ... "c&cn";
   %end;
 %mend dummy;
 %dummy;

%put _user_;

  PROC IML;
    use &dataset; read all var{female male} into FM;
    N=NROW(FM);

 /*** Create a parent vector npx1 ***/
   p=shape('000000', &np, 1);
   p[1,1]=FM[1,1]; p[2,1]=FM[1,2]; PN=2; /*PN parent number*/
   i=1;
   do until(i>=N | PN>=&np); F=0;  M=0;
      do j=1 to PN;
         if FM[i,1]=p[j,1] then F=1;
         if FM[i,2]=p[j,1] then M=1;
         end;
      if F=0 then do;  PN=PN+1;  p[PN,1]=FM[i,1];
         end;
      if M=0 then do;  PN=PN+1;  p[PN,1]=FM[i,2];
         end;
      i=i+1;
   end;
   print 'Parent index:' p;

  /*** Create Dummy variable for cross ***/
   nc=(&np-1)*&np/2; 
   parent=shape(0,N,&np+2);  
   cross = shape(0, N, nc+1);

   do i=1 to N;
      k=0;
      do j=1 to &np;
	     if FM[i,1]=p[j,1] then parent[i,&np+1]=j; **female parent column;
         if FM[i,2]=p[j,1] then parent[i,&np+2]=j; **male parent column;
         if FM[i,1]=p[j,1] | FM[i,2]=p[j,1] then
            do; parent[i,j]=1; k = k+1; end;  **dummy variable columns 1 to np;
      end;
      if k<2 then print 'Error parent:' i (FM[i,]);
  end;
  
  c=0;
  do i=1 to &np-1;
    do j=i+1 to &np;
	  c=c+1;
      cross[,c]=parent[,i]#parent[,j];
	end;
  end;

  do i=1 to &nc; cross[,&nc+1]=cross[,&nc+1]+i@cross[,i]; end;

   tl = {&dummyp1 'female' 'male'};
   t2 = {&dummyc 'CROSS'};
   create mparent from parent [colname=tl];
   append from parent [colname=tl];

   create mcross from cross [colname=t2];
   append from cross [colname=t2];

QUIT;

DATA &dataset;
  merge &dataset(drop=female male) mparent mcross;
  if ht=. then delete;
*proc print data=&dataset(obs=100);
RUN;

 /**** polygene analysis ****/
 ods listing close; 
 ods html close;
PROC mixed data=&dataset covtest;
      class t b cross;
      model ht=t b(t)/outpm=yadj;
      random p1-p&np/type=toep(1) s;
      random &dummyp2/type=toep(1);
      random cross cross*t cross*b(t)/;
	  ods output SolutionR=ran CovParms=varc;
RUN;

*proc print data=init_4(obs=100);run;

  data ran;  set ran;
    if t='';
    if effect^='CROSS' then 
       do; genpar='PARENT'; level=substr(effect,2);end; 
    else do; genpar=effect; level=left(cross); end;
    keep  genpar level estimate stderrpred; RUN;

  data _null_;  set ran;
     file "&path.\&ser.&dataset._ran.dat";
     put genpar $ level estimate 7.3 stderrpred 7.3;
  
  data _null_;  set varc;
     file "&path.\&ser.&dataset._varc.dat"; 
     put covparm $ estimate 8.4 stderr 8.4;

  data init_l1I1; u=0; a=0; d=0;
  data init_l1I2; u=0; a=.75; d=.5;
  proc transpose data=ran(keep=level estimate) out=init_l23;
  proc transpose data=varc(keep=estimate) out=init_l4;

data init_l5; Kisq=4; Ui=2; alphaF=1; betaF=1;
  run;
  data _null_; set init_l1I1;  ***Line1 in initial.dat, values for u, a, d;
	  file "&path.\&ser.&dataset.I1_initial.dat"; 
  	  put u 4.1 a 4.1 d 4.1;

  data _null_; set init_l1I2;  ***Line1 in initial.dat, values for u, a, d;
	  file "&path.\&ser.&dataset.I2_initial.dat"; 
  	  put u 4.1 a 5.2 d 4.1;

  data _null_; set init_l23;  ***Line2 in initial.dat, values for gca;
	  file "&path.\&ser.&dataset.I1_initial.dat" mod; 
  	  put (col1-col&np) (8.4);
	  file "&path.\&ser.&dataset.I2_initial.dat" mod; 
  	  put (col1-col&np) (8.4);

  %let cstart=%eval(&np+1); **start column (variable);
  %let cend=%eval(&np+&nc); **end column (variable);
  data _null_; set init_l23;  ***Line3 in initial.dat, values for sca;
	  file "&path.\&ser.&dataset.I1_initial.dat" mod; 
  	  put (col&cstart-col&cend) (8.4);
	  file "&path.\&ser.&dataset.I2_initial.dat" mod; 
  	  put (col&cstart-col&cend) (8.4);

  data init_l4; set init_l4;
     if col1=0 then col1=.0001;
     if col3=0 then col3=.0001;
     if col6=0 then col6=.0001;
     *COL7=.1;

  data _null_; set init_l4;
	  file "&path.\&ser.&dataset.I1_initial.dat" mod; 
	  f=.1;
  	  put (col1 col3 col6 f) (8.4);

  data _null_; set init_l4;
	  file "&path.\&ser.&dataset.I2_initial.dat" mod; 
	  f=.4;
  	  put (col1 col3 col6 f) (8.4);

  data _null_; set init_l5;
	  file "&path.\&ser.&dataset.I1_initial.dat" mod; 
  	  put Kisq Ui alphaF betaF;
	  file "&path.\&ser.&dataset.I2_initial.dat" mod; 
  	  put Kisq Ui alphaF betaF;

  data _null_; set yadj;
	  file "&path.\&ser.&dataset._org.dat"; 
      put female male resid 8.4;

  data _null_; set &dataset;
	u=1; a=0; d=0;
	file "&path.\&ser.&dataset._incidcr.dat"; 
    put u a d p1-p&np c1-c&nc;
  run;

 ods listing; 
 ods html;

%MEND desg;

%MACRO readata( path, ser, np=6 );

DATA orig;
INFILE "&path.\aind&ser..dat";
****For origianal data format;
*INPUT TESTID $ 1-12 ORG $ 1-2 SER $ 3-5 TEST $ 6 YRPL 7-8
        YRMEAS 9-10 AGE 11-12 DIALL $ 13 FEMALE $ 14-19 MALE $ 20-25 REP 26-27 TREE 28-29
        STATUS 30 HEIGHT 31-33 .1 RUST 34 STRT 35 DBH 36-38 .1;

****For adjusted file format;
INPUT CONO SERIES$ TEST$ REGION AGE DIALL FEMALE $ MALE $ REP TREE
      STATUS HEIGHT VOL RUST STRT;

****For adjusted file format of EDMS data. Pred_X is growth curve corrected -Bin Xiang;
*INPUT TESTID $ 1-12 ORG $ 1-2 SER $ 3-5 TEST $ 6 YRPL 7-8 YRMEAS 9-10 AGE 11-12 DIALL $ 15
        FEMALE $ 18-23 MALE $ 25-31 REP TREE STATUS HEIGHT RUST STRT DBH PRED_H PRED_D;

	  * Occassionally there is a blank entry;
	  IF AGE=6;
      IF HEIGHT=. THEN DELETE;
      IF DIALL=0 THEN DELETE;
	  If STATUS NE 0;
	  if female='29018' & male='29021' then delete;  ****For SER 074 only!;

if female='03501' | male='03501' then delete;  ****checking program for 5 parents in 015 only!;

RUN;

DATA orig;  set orig;
  t=test; b=rep; ht=height; 
proc sort; by test;
RUN;

****************Adjust for heterogenity of tests*************;
proc means data=orig noprint;
  var height; by t;
  output out=testvar(drop=_type_ _freq_) mean=tmn std=tstd;
proc means data=testvar noprint;
  var tstd; 
  output out=testvarmn(drop=_type_ _freq_) mean=tstdmn;
*proc print;
data _null_; set testvarmn;
  call symput('tstdmn',tstdmn);
%*put &tstdmn;
run;

data orig;
  merge orig testvar; by t;
  ht=tmn + (height-tmn)/tstd*&tstdmn;
*proc print data=orig(obs=100);
run;
**************************************************************;

DATA orig;  set orig;
     keep diall t b female male ht;
     RUN;

DATA one(drop=diall);
     set orig;
     if diall=2 then delete;
RUN;
proc sort data=one; by t b;
run;

DATA two(drop=diall);
     set orig;
     if diall=1 then delete;
RUN;
proc sort data=two; by t b;
run;

%desg( &path, &ser, one, np=&np );
%desg( &path, &ser, two, np=&np );
proc datasets kill;
run;
%MEND readata;

/*
%readata(c:\mggui, 016 );

%readata(c:\mgTemp, 344 );
%readata(c:\mgTemp, 345 );
%readata(c:\mgTemp, 346 );
%readata(c:\mgTemp, 348);

%readata(d:\AIND035, 035 );
%readata(d:\AIND430, 430 );
%readata(d:\AIND482, 482 );
%readata(d:\AIND096, 096);
%readata(d:\AIND355, 355 );
%readata(d:\AIND375, 375 );

%readata(d:\actualData\AIND356 , 356 );
%readata(d:\actualData\AIND357 , 357 );
%readata(d:\actualData\AIND370 , 370 );
%readata(d:\actualData\AIND408 , 408 );

%readata(d:\research\dialldatadj , 016 );
%readata(d:\research\dialldatadj , 114 );
%readata(d:\research\dialldatadj , 181 );
%readata(d:\research\dialldatadj , 205 );
%readata(d:\research\dialldatadj , 352 );

%readata(d:\research\dialldatadj , 015, np=5 );
%readata(d:\research\dialldatadj , 087 );
%readata(d:\research\dialldatadj , 124 );
%readata(d:\research\dialldatadj , 246 );
%readata(d:\research\dialldatadj , 265 );
%readata(d:\research\dialldatadj , 144 );
%readata(d:\research\dialldatadj , 148 );
%readata(d:\research\dialldatadj , 154 );

%readata(d:\research\dialldatadj , 112 );
%readata(d:\research\dialldatadj , 135 );
%readata(d:\research\dialldatadj , 168 );
%readata(d:\research\dialldatadj , 158 );
%readata(d:\research\dialldatadj , 073 );
%readata(d:\research\dialldatadj , 081 );
%readata(d:\research\dialldatadj , 326 );
%readata(d:\research\dialldatadj , 074 );
%readata(d:\research\dialldatadj , 121 );
%readata(d:\research\dialldatadj , 353 );
%readata(d:\research\dialldatadj , 268 );


%readata(d:\research\try , 326 );

*/