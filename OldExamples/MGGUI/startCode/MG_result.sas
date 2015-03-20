/*   MG_result.sas     */
/* Calculate Genotype  */
/* probab. in chains   */
/* from MajorGene.exe. */
/*                     */ 
/* May change output   */
/* name file           */

/*
%let path=d:\research\dialldat;
%let path=d:\majorgene\output;
%let chnID=158twoI2_; 
*/

%MACRO geno( path, chnID, fileID, Minit, lag=10 );
data one;
  infile "&path.\&chnID.btgenoc.dat" firstobs=2;
  input iter g1 g2 g3 g4 g5 g6;
  if iter>=&Minit;
  if Int((iter-&Minit)/&lag) = (iter-&Minit)/&lag; 
RUN;

proc summary print;
  var g1-g6;
run;

proc freq data=one;
tables g1/out=gp1;
tables g2/out=gp2;
tables g3/out=gp3;
tables g4/out=gp4;
tables g5/out=gp5;
tables g6/out=gp6;

data genoprob;
merge gp1(rename=(g1=genotype percent=p1)) gp2(rename=(g2=genotype percent=p2))
      gp3(rename=(g3=genotype percent=p3)) gp4(rename=(g4=genotype percent=p4))
      gp5(rename=(g5=genotype percent=p5)) gp6(rename=(g6=genotype percent=p6));
by genotype;
chnID=reverse(substr(reverse("&chnID"),2));
keep p1-p6 genotype chnID;
proc print;
run;

data _null_;
  set genoprob;
  file "&path.\genoprob_&fileID..txt" mod;
  *put (p1-p6) (10.4) @65 genotype chnID;
   put chnID genotype(p1-p6) (10.4) @65;
   *this is for report purposes
   *modified by jianying li 
run;
%MEND geno;

%MACRO sigma( path, chnID, fileID, Minit, lag=10 );
data two;
  infile "&path.\&chnID.bsigmac.dat" firstobs=2;
  input iter vg vs ve f a d;
  if iter>=&Minit;
  if Int((iter-&Minit)/&lag) = (iter-&Minit)/&lag; 
RUN;

proc means data=two;
  output out=sigmamn1;
proc print;
run;
proc means data=two;
  output out=sigmamn2 median=;
data sigmamn2;  set sigmamn2;  _stat_="MEDIAN";
run;

proc sort data=sigmamn1; by _stat_;
data sigmamn;
  merge sigmamn1 sigmamn2; by _stat_;
  chnID=reverse(substr(reverse("&chnID"),2));
  stat=_stat_;
  if stat="MEAN"|stat="STD"|stat="MEDIAN";

data _null_;
  set sigmamn;
  file "&path.\sigmamn_&fileID..txt" mod;
 * put chnID stat (vg vs ve f a d) (8.4) ;
  put chnID stat (a d f vg vs ve) (8.4); 
     *this is for output report purposes
     *modified by jianying li
run;

%MEND;

%MACRO varm( path, chnID, fileID, Minit, lag=10 );
data four;
  infile "&path.\&chnID.varm.dat" firstobs=2;
  input iter vma vmd vm;
  if iter>=&Minit;
  if Int((iter-&Minit)/&lag) = (iter-&Minit)/&lag; 
RUN;

proc means data=four;
  output out=vmmn1;
*proc print;
proc means data=four;
  output out=vmmn2 median=;
data vmmn2;  set vmmn2;  _stat_="MEDIAN";
run;

proc sort data=vmmn1; by _stat_;
data vmmn;
  merge vmmn1 vmmn2; by _stat_;
  chnID=reverse(substr(reverse("&chnID"),2));
  stat=_stat_;
  if stat="MEAN"|stat="STD"|stat="MEDIAN";
*proc print;

data _null_;
  set vmmn;
  file "&path.\varmmn_&fileID..txt" mod;
  put chnID stat (vma vmd vm) (8.4) ;
run;
%MEND;

%MACRO theta( path, chnID, fileID, Minit, lag=10 );
data three;
  infile "&path.\&chnID.btheta.dat" firstobs=2;
  input iter g1 g2 g3 g4 g5 g6;
  if iter>=&Minit;
  if Int((iter-&Minit)/&lag) = (iter-&Minit)/&lag; 
RUN;

proc means data=three;
  output out=thetamn;
proc print;
run;

data thetamn;
  set thetamn;
  chnID=reverse(substr(reverse("&chnID"),2));
  stat=_stat_;
  if stat="MEAN"|stat="STD";

data _null_;
  set thetamn;
  file "&path.\thetamn_&fileID..txt" mod;
  put chnID stat (g1 g2 g3 g4 g5 g6) (8.4) ;
run;
%MEND;

%MACRO MGresult( path, chnID, fileID, Minit, lag=10 );
%geno( &path, &chnID, &fileID, &Minit, lag=&lag );
%sigma( &path, &chnID, &fileID, &Minit, lag=&lag );
%theta( &path, &chnID, &fileID, &Minit, lag=&lag );
%varm( &path, &chnID, &fileID, &Minit, lag=&lag );
%MEND;

/*
%let path=d:\research\dialldat;
%let fileID=sum;
data polyvar;
  infile "d:\research\dialldat\polygen varp.dat";
  input did$ (vg_p vs_p ve_p se_vgp se_vsp se_vep) (8.4);
  vt_p=2*vg_p+vs_p+ve_p;
proc sort; by did;
run;

data sigmasum;
  infile "&path.\sigmamn_&fileID..txt" ;
  input chnID: $12. stat$ vg vs ve f a d ;
  chnID=compress(substr(chnID,1,6)||reverse(substr(reverse(trim(chnID)),1,2)));
  did=substr(chnID,1,6);
proc sort; by chnID;
run;

data vmsum;
  infile "&path.\varmmn_&fileID..txt" ;
  input chnID: $12. stat$ vma vmd vm ;
  chnID=compress(substr(chnID,1,6)||reverse(substr(reverse(trim(chnID)),1,2)));
  did=substr(chnID,1,6);
proc sort; by chnID;
run;
data sigmasum;
  merge sigmasum vmsum; by chnID;
run;

data sigmasum2;
  merge sigmasum(where=(stat="MEAN")) sigmasum(where=(stat="STD") rename=(vg=vg_std
        vs=vs_std ve=ve_std f=f_std a=a_std d=d_std vma=vma_std vmd=vmd_std vm=vm_std)) 
        sigmasum(where=(stat="MEDIAN") rename=(vg=vg_med vs=vs_med ve=ve_med vma=vma_med 
        vmd=vmd_med vm=vm_med));
  vt=2*vg+vs+ve+vm;
  vt_med=2*vg_med+vs_med+ve_med+vm_med;
  rvmvt_mn=vm/vt;  rvmvt_med=vm_med/vt_med;
  r2avt=2*a/sqrt(vt);  r2avt_med=2*a/sqrt(vt_med);
  drop stat;
proc means noprint; by did;
  output out=sigmasummn mean=;
run;
data compvar;
  merge sigmasummn polyvar; by did;
  drop _type_ _freq_;
proc sort; by descending r2avt ;
proc print round;
  var did a d f vm_med rvmvt_med vg_med vs_med vt_med vg_p vs_p vt_p;
run;

proc gplot;
  plot vt*vt_p vt_med*vt_p;
  plot vg*vg_p vg_med*vg_p;
  plot vs*vs_p vs_med*vs_p;
  *plot d*a;
run;   

proc gchart;
  vbar f;
run;

proc print 

*/
 
/*
**************Region 1 *************;
%MGresult(c:\mg\runningDir, 370oneI1_, reg1, 80001 );
%MGresult(c:\mg\runningDir, 370oneI2_, reg1, 80001 );
%MGresult(c:\mg\runningDir, 370twoI1_, reg1, 80001 );
%MGresult(c:\mg\runningDir, 370twoI2_, reg1, 80001 );

%MGresult(d:\AIND343, 343oneI2_, reg1, 80001 );

%MGresult(d:\research\dialldat, 016one2hI2_, reg1, 80001 );

%MGresult(d:\research\dialldat, 016one2hI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 016one2hI2_, reg1, 80001 );
%MGresult(d:\research\dialldat, 016two2hI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 016two2hI2_, reg1, 80001 );

%MGresult(d:\research\dialldat, 114one2nbI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 114one2nbI2_, reg1, 80001 );
%MGresult(d:\research\dialldat, 114two2nbI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 114two2nbI2_, reg1, 80001 );

%MGresult(d:\research\dialldat, 181two2hI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 181two2hI2_, reg1, 80001 );

%MGresult(d:\research\dialldat, 205one2nbI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 205one2nbI2_, reg1, 80001 );
%MGresult(d:\research\dialldat, 205two2nbI1_, reg1, 80001 );
%MGresult(d:\research\dialldat, 205two2nbI2_, reg1, 80001 );

**************Region 2 *************;
%MGresult(d:\research\dialldat, 015one2hI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 015one2hI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 015two2hI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 015two2hI2_, reg2, 80001 );

%MGresult(d:\research\dialldat, 087one2hI1_, reg2, 200001, lag=20 );
%MGresult(d:\research\dialldat, 087one2hI2_, reg2, 200001, lag=20 );
%MGresult(d:\research\dialldat, 087two3nI1_, reg2, 100001, lag=20 );
%MGresult(d:\research\dialldat, 087two3nI2_, reg2, 100001, lag=20 );

%MGresult(d:\research\dialldat, 124one3nI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 124one3nI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 124two3nI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 124two3nI2_, reg2, 80001 );

%MGresult(d:\research\dialldat, 265one2naI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 265one2naI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 265two2naI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 265two2naI2_, reg2, 80001 );

%MGresult(d:\research\dialldat, 144one2naI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 144one2naI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 144two2naI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 144two2naI2_, reg2, 80001 );

%MGresult(d:\research\dialldat, 148one3nI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 148one3nI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 148two2eI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 148two2eI2_, reg2, 80001 );

%MGresult(d:\research\dialldat, 154one2hI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 154one2hI2_, reg2, 80001 );
%MGresult(d:\research\dialldat, 154two3nI1_, reg2, 80001 );
%MGresult(d:\research\dialldat, 154two3nI2_, reg2, 80001 );

**************Region 3 *************;
%MGresult(d:\research\dialldat, 112one2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 112one2hI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 112two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 112two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 135one2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 135one2hI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 135two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 135two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 168one2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 168one2hI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 168two3nI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 168two3nI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 158one2eI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 158one2eI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 073two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 073two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 081one3nI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 081one3nI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 081two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 081two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 326one3nI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 326one3nI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 326two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 326two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 074one2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 074one2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 121one2hI1_, reg3, 100001 );
%MGresult(d:\research\dialldat, 121one2hI2_, reg3, 100001 );
%MGresult(d:\research\dialldat, 121two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 121two2hI2_, reg3, 80001 );

%MGresult(d:\research\dialldat, 268one2eI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 268one2eI2_, reg3, 80001 );
%MGresult(d:\research\dialldat, 268two2hI1_, reg3, 80001 );
%MGresult(d:\research\dialldat, 268two2hI2_, reg3, 80001 );

*/