/*     change.sas      */
/* Change a (<0) to    */
/* positive in chains  */
/* from MajorGene.exe. */

/*
%let path=e:\MG Simu; 
%let id=l10twoI1_;  
*/
%MACRO change( path, id );
data one;
  infile "&path.\&id.btgeno.dat" firstobs=1;
  input iter g1 g2 g3 g4 g5 g6;
RUN;

data two;
  infile "&path.\&id.bsigma.dat" firstobs=2;
  input iter Vg Vs Ve f a d;
RUN;

data com;
   merge one two;  by iter;
RUN;

PROC datasets; delete one two; RUN;

data com; set com;

   if a < 0.0 then
   do;
      a = -a;
      f = 1-f;
	  g1=2-g1;
	  g2=2-g2;
	  g3=2-g3;
	  g4=2-g4;
	  g5=2-g5;
	  g6=2-g6;
   end;
*** To avoid extreme values (0 or 1) for f and a in BOA;
   if f<.00001 then f=.00001;
   if f>.99999 then f=.99999;
   if a<.00001 then a=.00001;

*** To obtain major gene variance (additive, dominance and total) ;
   Vma=2*f*(1-f)*((1-2*f)*d+a)**2;
   Vmd=4*(f*(1-f)*d)**2;
   Vm=Vma+Vmd;
   if Vma<.00001 then Vma=.00001;
   if Vmd<.00001 then Vmd=.00001;
   if Vm<.00001 then Vm=.00001;
run;

data _null_;
   file "&path.\&id.btgenoc.dat";
   put 'iter ' 'g1 ' 'g2 ' 'g3 ' 'g4 ' 'g5 ' 'g6';
data _null_; set com;
   file "&path.\&id.btgenoc.dat" mod;
   put iter g1 g2 g3 g4 g5 g6;
RUN;

data _null_;
   file "&path.\&id.bsigmac.dat";
   put 'iter ' 'Vg ' 'Vs ' 'Ve ' 'f ' 'a ' 'd ';
data _null_; set com;
   file "&path.\&id.bsigmac.dat" mod;
   put iter Vg Vs Ve f a d;
RUN;

data _null_;
   file "&path.\&id.varm.dat";
   put 'iter ' 'Vma ' 'Vmd ' 'Vm ';
data _null_; set com;
   Vma=round(Vma,.00001);
   Vmd=round(Vmd,.00001);
   Vm=round(Vm,.00001);
   file "&path.\&id.varm.dat" mod;
   put iter Vma Vmd Vm;
RUN;

PROC datasets; delete com; RUN;

%MEND change;

/*
%change(path=d:\majorgene\try, id=l20one2hI1_ );
%change(path=d:\majorgene\try, id=l20one2hI2_ );

%change(path=c:\mggui, id=016oneI1_);
%change(path=c:\mggui, id=016oneI2_);
%change(path=c:\mggui, id=016twoI1_);
%change(path=c:\mggui, id=016twoI2_);

%change(path=d:\actualData\AIND356, id=356oneI1_);
%change(path=d:\actualData\AIND356, id=356oneI2_);

%change(path=d:\actualData\AIND357, id=357oneI1_);
%change(path=d:\actualData\AIND357, id=357oneI2_);

%change(path=d:\actualData\AIND370, id=370oneI1_);
%change(path=d:\actualData\AIND370, id=370oneI2_);

%change(path=d:\actualData\AIND408, id=408oneI1_);
%change(path=d:\actualData\AIND408, id=408oneI2_);


%change(path=d:\actualData\AIND343, id=343twoI1_);
%change(path=d:\actualData\AIND343, id=343twoI2_);

%change(path=d:\actualData\AIND356, id=356twoI1_);
%change(path=d:\actualData\AIND356, id=356twoI2_);

%change(path=d:\actualData\AIND357, id=357twoI1_);
%change(path=d:\actualData\AIND357, id=357twoI2_);

%change(path=d:\actualData\AIND370, id=370twoI1_);
%change(path=d:\actualData\AIND370, id=370twoI2_);

%change(path=d:\actualData\AIND408, id=408twoI1_);
%change(path=d:\actualData\AIND408, id=408twoI2_);


%change(path=d:\MG research, id=205two2hI1_ );
%change(path=d:\MG research, id=205two2hI2_);

%change(path=d:\MG research, id=016one2hI1_ );
%change(path=d:\MG research, id=016one2hI2_);
%change(path=d:\MG research, id=016two2hI1_ );
%change(path=d:\MG research, id=016two2hI2_);

%change(path=d:\MG research, id=114one2hI1_ );
%change(path=d:\MG research, id=114one2hI2_);
%change(path=d:\MG research, id=114two2hI1_ );
%change(path=d:\MG research, id=114two2hI2_);

%change(path=d:\MG research, id=181one2hI1_ );
%change(path=d:\MG research, id=181one2hI2_);
%change(path=d:\MG research, id=181two2hI1_ );
%change(path=d:\MG research, id=181two2hI2_);

%change(path=d:\MG research, id=015one2hI1_ );
%change(path=d:\MG research, id=015one2hI2_);
%change(path=d:\MG research, id=015two2hI1_ );
%change(path=d:\MG research, id=015two2hI2_);

%change(path=d:\MG research, id=124one2hI1_ );
%change(path=d:\MG research, id=124one2hI2_);
%change(path=d:\MG research, id=124two2hI1_ );
%change(path=d:\MG research, id=124two2hI2_);

%change(path=d:\MG research, id=154one2hI1_ );
%change(path=d:\MG research, id=154one2hI2_);
%change(path=d:\MG research, id=154two2hI1_ );
%change(path=d:\MG research, id=154two2hI2_);

%change(path=d:\MG research, id=112one2hI1_ );
%change(path=d:\MG research, id=112one2hI2_);
%change(path=d:\MG research, id=112two2hI1_ );
%change(path=d:\MG research, id=112two2hI2_);

%change(path=d:\MG research, id=135one2hI1_ );
%change(path=d:\MG research, id=135one2hI2_);
%change(path=d:\MG research, id=135two2hI1_ );
%change(path=d:\MG research, id=135two2hI2_);

%change(path=d:\MG research, id=168one2hI1_ );
%change(path=d:\MG research, id=168one2hI2_);
%change(path=d:\MG research, id=168two2hI1_ );
%change(path=d:\MG research, id=168two2hI2_);

%change(path=d:\MG research, id=073one2hI1_ );
%change(path=d:\MG research, id=073one2hI2_);
%change(path=d:\MG research, id=073two2hI1_ );
%change(path=d:\MG research, id=073two2hI2_);

%change(path=d:\MG research, id=074one2hI1_ );
%change(path=d:\MG research, id=074one2hI2_);

%change(path=d:\MG research, id=081one2hI1_ );
%change(path=d:\MG research, id=081one2hI2_);
%change(path=d:\MG research, id=081two2hI1_ );
%change(path=d:\MG research, id=081two2hI2_);

%change(path=d:\MG research, id=326one2hI1_ );
%change(path=d:\MG research, id=326one2hI2_);
%change(path=d:\MG research, id=326two2hI1_ );
%change(path=d:\MG research, id=326two2hI2_);

%change(path=d:\MG research, id=121one2hI1_ );
%change(path=d:\MG research, id=121one2hI2_);
%change(path=d:\MG research, id=121two2hI1_ );
%change(path=d:\MG research, id=121two2hI2_);

%change(path=d:\majorgene\output, );

%change(path=d:\research\dialldat, id=016oneI1_ );
%change(path=d:\research\dialldat, id=016oneI2_);
%change(path=d:\research\dialldat, id=016twoI1_ );
%change(path=d:\research\dialldat, id=016twoI2_);

%change(path=d:\research\dialldat, id=114oneI1_ );
%change(path=d:\research\dialldat, id=114oneI2_);
%change(path=d:\research\dialldat, id=114twoI1_ );
%change(path=d:\research\dialldat, id=114twoI2_);

%change(path=d:\research\dialldat, id=181oneI1_ );
%change(path=d:\research\dialldat, id=181oneI2_);
%change(path=d:\research\dialldat, id=181twoI1_ );
%change(path=d:\research\dialldat, id=181twoI2_);

%change(path=d:\research\dialldat, id=205oneI1_ );
%change(path=d:\research\dialldat, id=205oneI2_);
%change(path=d:\research\dialldat, id=205twoI1_ );
%change(path=d:\research\dialldat, id=205twoI2_);

%change(path=d:\research\dialldat, id=015oneI1_ );
%change(path=d:\research\dialldat, id=015oneI2_);
%change(path=d:\research\dialldat, id=015twoI1_ );
%change(path=d:\research\dialldat, id=015twoI2_);

%change(path=d:\research\dialldat, id=087oneI1_ );
%change(path=d:\research\dialldat, id=087oneI2_);
%change(path=d:\research\dialldat, id=087twoI1_ );
%change(path=d:\research\dialldat, id=087twoI2_);

%change(path=d:\research\dialldat, id=124oneI1_ );
%change(path=d:\research\dialldat, id=124oneI2_);
%change(path=d:\research\dialldat, id=124twoI1_ );
%change(path=d:\research\dialldat, id=124twoI2_);

%change(path=d:\research\dialldat, id=265oneI1_ );
%change(path=d:\research\dialldat, id=265oneI2_);
%change(path=d:\research\dialldat, id=265twoI1_ );
%change(path=d:\research\dialldat, id=265twoI2_);

%change(path=d:\research\dialldat, id=144oneI1_ );
%change(path=d:\research\dialldat, id=144oneI2_);
%change(path=d:\research\dialldat, id=144twoI1_ );
%change(path=d:\research\dialldat, id=144twoI2_);

%change(path=d:\research\dialldat, id=148oneI1_ );
%change(path=d:\research\dialldat, id=148oneI2_);
%change(path=d:\research\dialldat, id=148twoI1_ );
%change(path=d:\research\dialldat, id=148twoI2_);

%change(path=d:\research\dialldat, id=154oneI1_ );
%change(path=d:\research\dialldat, id=154oneI2_);
%change(path=d:\research\dialldat, id=154twoI1_ );
%change(path=d:\research\dialldat, id=154twoI2_);

%change(path=d:\research\dialldat, id=112oneI1_ );
%change(path=d:\research\dialldat, id=112oneI2_);
%change(path=d:\research\dialldat, id=112twoI1_ );
%change(path=d:\research\dialldat, id=112twoI2_);

%change(path=d:\research\dialldat, id=135oneI1_ );
%change(path=d:\research\dialldat, id=135oneI2_);
%change(path=d:\research\dialldat, id=135twoI1_ );
%change(path=d:\research\dialldat, id=135twoI2_);

%change(path=d:\research\dialldat, id=168oneI1_ );
%change(path=d:\research\dialldat, id=168oneI2_);
%change(path=d:\research\dialldat, id=168twoI1_ );
%change(path=d:\research\dialldat, id=168twoI2_);

%change(path=d:\research\dialldat, id=158oneI1_ );
%change(path=d:\research\dialldat, id=158oneI2_);
%change(path=d:\research\dialldat, id=158twoI1_ );
%change(path=d:\research\dialldat, id=158twoI2_);

%change(path=d:\research\dialldat, id=073oneI1_ );
%change(path=d:\research\dialldat, id=073oneI2_);
%change(path=d:\research\dialldat, id=073twoI1_ );
%change(path=d:\research\dialldat, id=073twoI2_);

%change(path=d:\research\dialldat, id=081oneI1_ );
%change(path=d:\research\dialldat, id=081oneI2_);
%change(path=d:\research\dialldat, id=081twoI1_ );
%change(path=d:\research\dialldat, id=081twoI2_);

%change(path=d:\research\dialldat, id=326oneI1_ );
%change(path=d:\research\dialldat, id=326oneI2_);
%change(path=d:\research\dialldat, id=326twoI1_ );
%change(path=d:\research\dialldat, id=326twoI2_);

%change(path=d:\research\dialldat, id=074oneI1_ );
%change(path=d:\research\dialldat, id=074oneI2_);
%change(path=d:\research\dialldat, id=074twoI1_ );
%change(path=d:\research\dialldat, id=074twoI2_);

%change(path=d:\research\dialldat, id=121oneI1_ );
%change(path=d:\research\dialldat, id=121oneI2_);
%change(path=d:\research\dialldat, id=121twoI1_ );
%change(path=d:\research\dialldat, id=121twoI2_);

%change(path=d:\research\dialldat, id=268oneI1_ );
%change(path=d:\research\dialldat, id=268oneI2_);
%change(path=d:\research\dialldat, id=268twoI1_ );
%change(path=d:\research\dialldat, id=268twoI2_);


%change(path=e:\MG Simu, id=a1twoI1_ );
%change(path=d:\MG, id=d12twoI2_ );
%macro changebatch(path=d:\MG simu, did=g, run1=1, nrun=50);
  %do run=&run1 %to &nrun;
    %change(path=&path, id=&did.&run.one3nI1_ );
    %change(path=&path, id=&did.&run.one3nI2_ );
    %change(path=&path, id=&did.&run.two3nI1_ );
    %change(path=&path, id=&did.&run.two3nI2_ );
  %end;
%mend;

* To run change macro on simulated datasets in the specified list (e.g. list=" '3one' '7two' ");
%MACRO changebatchL(path=d:\MG\, did=d, run1=1, nrun=50, list= );
%let delm=%str(%');
%do run=&run1 %to &nrun;
  %let onedo=%sysfunc(index(&list, &delm&run.one&delm));
  %let twodo=%sysfunc(index(&list, &delm&run.two&delm));
%put &list &delm&run.one&delm &delm&run.two&delm &onedo &twodo;
  %if &onedo ne 0 %then %do;
    %change(path=&path, id=&did.&run.one3nlI1_ );
    %change(path=&path, id=&did.&run.one3nlI2_ );
  %end;
  %if &twodo ne 0 %then %do;
    %change(path=&path, id=&did.&run.two3nlI1_ );
    %change(path=&path, id=&did.&run.two3nlI2_ );
  %end;
%end;
%mend;

%changebatch(path=d:\Majorgene\try,did=n, run1=6, nrun=6);
%changebatch(path=d:\MG,did=l, nrun=50);
%changebatch(path=d:\MG,did=mn, nrun=50);
%changebatch(path=d:\MG,did=p, nrun=50);

%changebatchL(path=d:\MG, did=jk, list="'7one', '22one', '23one', '29two', '39one', '44two', '50two'" );
%changebatchL(path=d:\MG, did=l, list="'5one', '8one', '10two', '13two', '14one', '15one', '15two',
                                '16one', '17one', '17two', '21two', '23one', '39two', '41one',
                                '45two', '47one', '50two'" );

*/