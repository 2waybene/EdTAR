package gov.nih.niehs.EPIGseq.myutility.statistics;

import java.util.Random;
import gov.nih.niehs.EPIGseq.myutility.numerical.*;
import java.util.*;

public class ANOVA {
//  public static float []  pvalue_histogram;
  /*
  public static float profile_ANOVA_bootstrapping_shuffle_paired(float [] _data, int [][] repIndex,int [][] pairedIndex,
      int numberOfbootstrapping,Random random, float [] bin, float [][] decomposedData,ArrayList  arrayList) {
    float [] result;
    int [] pairedRandomIndex = new int[pairedIndex.length];
    for (int k=0;k<pairedIndex.length;k++)
      pairedRandomIndex[k] = k;
    float MSA,f,p_value;
      result = rowData_ANOVA(_data,repIndex,decomposedData);
      MSA = result[0];
     f = result[1];
     p_value = result[2];
      float [] shuffled_data = new float[_data.length];
      float [] r;
      int number_larger_than_p=0;

      for (int k=0;k<numberOfbootstrapping;k++) {
        Statistics.bootstraping_shuffle(random,pairedIndex,pairedRandomIndex,_data,shuffled_data,arrayList);
           r = rowData_ANOVA(shuffled_data,repIndex,decomposedData);
           pvalue[k] = r[2];
           if (r[2] <= p_value ) {
             number_larger_than_p++;
           }
    }
    pvalue_histogram = DataConversion.getHistogramData(pvalue,bin);
    return ((float)number_larger_than_p)/numberOfbootstrapping;
  }
  */
//  public static void Shuffle(int [] shuffledIndex, Random random, ArrayList arrayList) {

  public static float profile_oneWayANOVA_bootstrapping_shuffle(float [] _data, float [] shuffled_data, float [] mean,float [] bootsNum_pvalues,
      int [][] repIndex, int [] randomIndex,
      int numberOfbootstrapping,Random random, float [] bin, float [] histoData,float [][] decomposedData,ArrayList  arrayList) {
    float [] result = new float[getANOVA_Name().length];
    rowData_oneWayANOVA(_data,repIndex,mean,decomposedData,result);
//      public static void rowData_oneWayANOVA(float [] d,int [][] factor_index, float [][] decomposedData,
//          float [] mean, float [] result) {


    float r=result[2];
    int number_larger_than_p=0;
//      for (int k=0;k<_data.length;k++)
//        randomIndex[k] = k;
    for (int k=0;k<numberOfbootstrapping;k++) {
      Statistics.Shuffle(randomIndex,random,arrayList);
      Statistics.Shuffled_data(_data,shuffled_data,randomIndex);
      rowData_oneWayANOVA(shuffled_data,repIndex,mean,decomposedData,result);
      bootsNum_pvalues[k] = result[2];
      if (r <= bootsNum_pvalues[k] ) {
        number_larger_than_p++;
      }
    }
    DataConversion.getHistogramData(bootsNum_pvalues,bin,histoData);
    return ((float)number_larger_than_p)/numberOfbootstrapping;
  }
//  pvalue[i] = ANOVA.profile_ANOVA_bootstrapping(data,shuffledData,repNameIndex,randomIndex,
//    number_of_bootstrapping,random,bin,decomposedData);

  public static float profile_oneWayANOVA_bootstrapping(float [] _data, float [] random_data,float [] mean,float [] bootsNum_pvalues,
      int [][] repIndex,int [] randomIndex,
      int numberOfbootstrapping,Random random, float [] bin, float [] histoData,float [][] decomposedData) {
    float [] result = new float[getANOVA_Name().length];
//    float MSA,f,p_value;
//      public static void rowData_oneWayANOVA(float [] d,int [][] factor_index, float [][] decomposedData,
//          float [] mean, float [] result) {

    rowData_oneWayANOVA(_data,repIndex,mean,decomposedData,result);
//      MSA = result[0];
//     f = result[1];
//     p_value = result[4];
//      float [] random_data = new float[_data.length];
    float r=result[2];
    int number_larger_than_p=0;
    for (int k=0;k<numberOfbootstrapping;k++) {
      Statistics.Random_index(randomIndex,random);
      Statistics.Random_data(_data,random_data,randomIndex);
//         Statistics.bootstraping(random,_data,random_data);
      rowData_oneWayANOVA(random_data,repIndex,mean,decomposedData,result);
      bootsNum_pvalues[k] = result[2];
      if (r <= bootsNum_pvalues[k] ) {
        number_larger_than_p++;
      }
    }
    DataConversion.getHistogramData(bootsNum_pvalues,bin,histoData);
    return ((float)number_larger_than_p)/numberOfbootstrapping;
  }

/*
  public static float [] anova(float [][] x) {
    int [] wg_df = new int[x.length];
    float [] wg_mean = new float[x.length];
   float pooled_variance = Statistics.pooled_variance(x,wg_df,wg_mean);
   int df_wg_total = 0;
   for (int i=0;i<wg_df.length;i++)
     df_wg_total+=wg_df[i];
    float bg_var = wg_mean.length*Statistics.var(wg_mean);
    int df_bg = wg_mean.length-1;
    float [] result = new float[3];
    float f = bg_var/pooled_variance;
    float prob = Statistics.betai(0.5f*df_wg_total,0.5f*df_bg,df_wg_total/(df_wg_total+df_bg*f));
    result[0] = bg_var;  // MSA - ANOVA
    result[1] = f; // F value
    result[2] = prob;
    return result;
  }
*/

/*
  static String [] anova_name = {"MSA","ANOVA","ANOVA_pvalue"};
  public static String [] getANOVA_Name() {
    return anova_name;
  }
  public static float [] rowData_ANOVA(float [] d, int [][] repIndex, float [][] decomposedData) {
   Statistics.getRepIndexedData(d,repIndex,decomposedData);
   return anova(decomposedData);
  }
  */
/*
  Analysis of variance of two-way hierarchical models with three variance components
  page 245, Bayesian inference in statistical analysis, Box and Tiao
  y (i,j,k) = theta + e (i) + e (i,j) + e (i,j,k)
  i : factor 1 - level one effects
  j : factor 2 - level two effects
  i=1,...,I
  j=1,...,J
  k=1,...,K
  y (i,j,k) : observation
  theta: a common local parameter
  e(j), e(i,j) and e(i,j,k) are independently distributed random variables with zero mean and variance sigma_sqaure_1,
  sigma_sqaure_2,sigma_sqaure_3
  total variance = sigma_sqaure_1 + sigma_sqaure_2 + sigma_sqaure_3
  */
  public static void rowData_twoWayANOVA_hierarchical_model(float [] d,int [][][] factor_index, float [][][] decomposedData,
      float [] mean_1, float [] mean_2, float [][] mean, float [] result) {

    FactorAnalysis.twoReplicateDecomposition(d,factor_index, decomposedData);
//   for (int i=0;i<factor_index.length;i++)
//     for (int j=0;j<factor_index[i].length;j++)
//       for (int k=0;k<factor_index[i][j].length;k++)
//          System.out.println("i="+i + ", j="+j+", k="+k+", value="+decomposedData[i][j][k]);

    int I = factor_index.length;      //effect 1, number of obsrevation
    int J = factor_index[0].length;   //effect 2, number of obsrevation
    int K = factor_index[0][0].length;// analysss per sample
    float grandMean =  Statistics.mean(d);
    int df_grandMean = 1;
    float SS_grandmean = 0;
    for (int i=0;i<d.length;i++)
      SS_grandmean += (d[i]-grandMean)*(d[i]-grandMean);
    SS_grandmean *= d.length;

    float S_3 = 0;
    int N=0;
    for (int i=0;i<factor_index.length;i++) {
      mean_1[i] = 0;
      N=0;
      for (int j=0;j<factor_index[i].length;j++) {
        mean[i][j] = Statistics.mean(decomposedData[i][j]);
        for (int k=0;k<factor_index[i][j].length;k++) {
          mean_1[i] += decomposedData[i][j][k];
          N++;
        }
      }
      mean_1[i] /= N;
      S_3 += (mean_1[i] - grandMean)*(mean_1[i] - grandMean);
    }
    S_3 *= J*K;


    float S_2 = 0;
    for (int i=0;i<factor_index.length;i++) {
      for (int j=0;j<factor_index[j].length;j++) {
        S_2 += (mean[i][j]-mean_1[i])*(mean[i][j]-mean_1[i]);
      }
    }
    S_2 *= K;

    float S_1 = 0;
    for (int i=0;i<factor_index.length;i++) {
      for (int j=0;j<factor_index[i].length;j++) {
        for (int k=0;k<factor_index[i][j].length;k++)
          S_1 += (decomposedData[i][j][k] - mean[i][j])*(decomposedData[i][j][k] - mean[i][j]);
      }
    }

    int df_S_3 = I-1;
    float m3 = S_3/df_S_3;
    int df_S_2 = I*(J-1);
    float m2 = S_2/df_S_2;
    int df_S_1 = I*J*(K-1);
    float m1 = S_1/df_S_1;

    float total_S = S_1+S_2+S_3;
    int total_df = I*J*K-1;
    float total_m = total_S/total_df;

    float sigma_square_1=m1;
    float sigma_square_2= (m2-m1)/K;
    float sigma_square_3= (m3-m2)/(J*K);

//   float sigma_square_123 = m3;
//   float sigma_square_12 = m2;
//   float sigma_square_1 = m1;

    float F_3 = m3/total_m;
    float prob_3 = Statistics.betai(0.5f*total_df,0.5f*df_S_3,total_df/(total_df+df_S_3*F_3));
    float F_2 = m2/total_m;
    float prob_2 = Statistics.betai(0.5f*total_df,0.5f*df_S_2,total_df/(total_df+df_S_2*F_2));
    float F_1 = m1/total_m;
    float prob_1 = Statistics.betai(0.5f*total_df,0.5f*df_S_1,total_df/(total_df+df_S_1*F_1));
/*
   sigma_square_residual = MS_r
   sigma_square_12 = (MS_12 - MS_r)/K
   sigma_square_2 = (MS_2 - MS_12)/(I*K)
   sigma_square_1 = (MS_1 - MS_12)/(J*K)

   E.M.S.
   total = sigma_square_residual + K*sigma_square_12 + (I*K)*sigma_square_2 + (J*K)*sigma_square_1
   1  = sigma_square_residual + K*sigma_square_12 + (J*K)*sigma_square_1
   2  = sigma_square_residual + K*sigma_square_12 + (I*K)*sigma_square_2
   12  = sigma_square_residual + K*sigma_square_12
   residual = sigma_square_residual
   */
    result[0] = m3;
    result[1] = df_S_3;
    result[2] = F_1;
    result[3] = prob_3;
    result[4] = m2;
    result[5] = df_S_2;
    result[6] = F_2;
    result[7] = prob_2;
    result[8] = m1;
    result[9] = df_S_1;
    result[10] = F_1;
    result[11] = prob_1;
    result[12] = total_S;
    result[13] = total_df;
//   result[14] = SS_grandmean+SS_effect_1+SS_effect_2+SS_effect_12+SS_residual;
//   result[15] = I*J*K;
  }
  static String [] twoway_anova_name_hierarchical_model = {"MS_top","DF_top","F_top","pvalue_top",
    "MS_middle","DF_middle","F_middle","pvalue_middle","MS_bottom","DF_bottom","F_bottom","pvalue_bottom",
    "SS_total","DF_total"};
  public static String [] get_Two_Way_ANOVA_Name_hierarchical_model() {
    return twoway_anova_name_hierarchical_model;
  }


/*
  Analysis of variance of two-way random effect models
  page 330, Bayesian inference in statistical analysis, Box and Tiao
  y (i,j,k) = theta + r (i) + c (j) + t (i,j) + e (i,j,k)
  r : factor 1
  c : factor 2
  t : interation of factors 1 and 2
  i=1,...,I
  j=1,...,J
  k=1,...,K
  y (i,j,k) : observation
  theta: a local parameter
  r,c,t are three different kinds of random effects
  e : the residual errors
  */
  /*
  public static void rowData_twoWayANOVA_good(float [] d,int [][][] factor_index, float [][][] decomposedData,
      float [] mean_1, float [] mean_2, float [][] mean, float [] result) {

    DataConversion.twoReplicateDecomposition(d,factor_index, decomposedData);
    for (int i=0;i<factor_index.length;i++)
      for (int j=0;j<factor_index[i].length;j++)
        for (int k=0;k<factor_index[i][j].length;k++)
          System.out.println("i="+i + ", j="+j+", k="+k+", value="+decomposedData[i][j][k]);

    int I = factor_index.length;      //effect 1, number of obsrevation
    int J = factor_index[0].length;   //effect 2, number of obsrevation
    int K = factor_index[0][0].length;// analysss per sample
    float grandMean =  Statistics.mean(d);
    int df_grandMean = 1;
    float SS_grandmean = 0;
    for (int i=0;i<d.length;i++)
      SS_grandmean += (d[i]-grandMean)*(d[i]-grandMean);
    SS_grandmean *= d.length;

    float SS_effect_1 = 0;
    int N=0;
    for (int i=0;i<factor_index.length;i++) {
      mean_1[i] = 0;
      N=0;
      for (int j=0;j<factor_index[i].length;j++) {
        mean[i][j] = Statistics.mean(decomposedData[i][j]);
        for (int k=0;k<factor_index[i][j].length;k++) {
          mean_1[i] += decomposedData[i][j][k];
          N++;
        }
      }
      mean_1[i] /= N;
      SS_effect_1 += (mean_1[i] - grandMean)*(mean_1[i] - grandMean);
    }
    SS_effect_1 *= J*K;
    float SS_effect_2 = 0;
    for (int j=0;j<factor_index[0].length;j++) {
      mean_2[j] = 0;
      N = 0;
      for (int i=0;i<factor_index.length;i++) {
        for (int k=0;k<factor_index[i][j].length;k++) {
          mean_2[j] += decomposedData[i][j][k];
          N++;
        }
      }
      mean_2[j] /= N;
      SS_effect_2 += (mean_2[j] - grandMean)*(mean_2[j] - grandMean);
    }
    SS_effect_2 *= I*K;

    float SS_effect_12 = 0;
    float tem;
    for (int i=0;i<factor_index.length;i++) {
      for (int j=0;j<factor_index[i].length;j++) {
        tem = mean[i][j] - mean_1[i] - mean_2[j] + grandMean;
        SS_effect_12 += tem*tem;
      }
    }
    SS_effect_12 *= K;

    float SS_residual = 0;
    for (int i=0;i<factor_index.length;i++) {
      for (int j=0;j<factor_index[i].length;j++) {
        for (int k=0;k<factor_index[i][j].length;k++)
          SS_residual += (decomposedData[i][j][k] - mean[i][j])*(decomposedData[i][j][k] - mean[i][j]);
      }
    }

    int df_effect_1 = I-1;
    float MS_1 = SS_effect_1/df_effect_1;
    int df_effect_2 = J-1;
    float MS_2 = SS_effect_2/df_effect_2;
    int df_effect_12 = df_effect_1*df_effect_2;
    float MS_12 = SS_effect_12/df_effect_12;
    int df_residual = I*J*(K-1);
    float MS_r = SS_residual/df_residual;


    float F_1 = MS_1/MS_r;
    float prob_1 = Statistics.betai(0.5f*df_residual,0.5f*df_effect_1,df_residual/(df_residual+df_effect_1*F_1));
    float F_2 = MS_2/MS_r;
    float prob_2 = Statistics.betai(0.5f*df_residual,0.5f*df_effect_2,df_residual/(df_residual+df_effect_2*F_2));
    float F_12 = MS_12/MS_r;
    float prob_12 = Statistics.betai(0.5f*df_residual,0.5f*df_effect_12,df_residual/(df_residual+df_effect_12*F_12));
    result[0] = MS_1;
    result[1] = df_effect_1;
    result[2] = F_1;
    result[3] = prob_1;
    result[4] = MS_2;
    result[5] = df_effect_2;
    result[6] = F_2;
    result[7] = prob_2;
    result[8] = MS_12;
    result[9] = df_effect_12;
    result[10] = F_12;
    result[11] = prob_12;
    result[12] = MS_r;
    result[13] = df_residual;
    result[14] = SS_grandmean+SS_effect_1+SS_effect_2+SS_effect_12+SS_residual;
    result[15] = I*J*K;
    result[16] = 1-MS_r/(MS_1+MS_2+MS_12+MS_r);
  }
  */
  static String [] twoway_anova_name = {"MS_1","DF_1","F_1","pvalue_1",
    "MS_2","DF_2","F_2","pvalue_2","MS_12","DF_12","F_12","pvalue_12","MS_residual","DF_residual","Total SS","Total DF","R_square"};
  public static String [] get_Two_Way_ANOVA_Name() {
    return twoway_anova_name;
  }
/*
  Analysis of variance of two-way random effect models
  page 330, Bayesian inference in statistical analysis, Box and Tiao
  y (i,j,k) = theta + r (i) + c (j) + t (i,j) + e (i,j,k)
  r : factor 1
  c : factor 2
  t : interation of factors 1 and 2
  i=1,...,I
  j=1,...,J
  k=1,...,K
  y (i,j,k) : observation
  theta: a local parameter
  r,c,t are three different kinds of random effects
  e : the residual errors
  */
  public static void rowData_oneWayANOVA_with_baseline(float [] d,float [] d_zero,int [][] factor_index,float [] mean, float [][] decomposedData,
       float [] result) {

    FactorAnalysis.decompositionOneFactorData(d,factor_index, decomposedData);

    int J = factor_index.length;      // number of between group
    int K = factor_index[0].length;   // number of within group
    for (int i=0;i<d.length;i++)
      d_zero[i] = d[i];
    for (int i=d.length;i<d_zero.length;i++)
      d_zero[i] = 0;

    float grandMean =  Statistics.mean(d_zero);
    float SS_effect=0;
    for (int i=0;i<factor_index.length;i++) {
      mean[i] = Statistics.mean(decomposedData[i]);
      SS_effect += (mean[i]-grandMean)*(mean[i]-grandMean);
    }
    SS_effect += grandMean*grandMean;
    SS_effect *= K;

    float SS_residual=0;
    for (int j=0;j<factor_index.length;j++)
      for (int k=0;k<factor_index.length;k++)
        SS_residual += (decomposedData[j][k]-mean[j])*(decomposedData[j][k]-mean[j]);


    int df_SS_effect = J-1;
    int df_SS_residual = J*(K-1);

    float m_effect = SS_effect/df_SS_effect;
    float m_residual = SS_residual/df_SS_residual;

    float total_S = SS_residual+SS_effect;
    int total_df = J*K-1;


    float F = m_effect/m_residual;
    float prob = Statistics.betai(0.5f*df_SS_residual,0.5f*df_SS_effect,df_SS_residual/(df_SS_residual+df_SS_effect*F));
/*
   sigma_square_1 = m1
   sigma_square_2 = (m2-m1)/K

   E.M.S.
   sigma_square_12 = sigma_square_1 + K*sigma_square_2
   sigma_square_1  = sigma_square_1
   */
    result[0] = m_effect;
    result[1] = F;
    result[2] = prob;
    result[3] = df_SS_effect;
    result[4] = m_residual;
    result[5] = df_SS_residual;
    result[6] = total_S;
    result[7] = total_df;
  }
//  public static void rowData_twoWayANOVA(float [] d,
//      int [][] factor_1_index,float [] mean_1,float [][] decomposedData1,
//      int [][] factor_2_index,float [] mean_2,float [][] decomposedData2,
//      int [][][] factor_12_index,float [][] mean_12,float [][][] decomposedData12,
//       float [] result) {

  public static void rowData_oneWayANOVA(float [] d,int [][] factor_index,float [] mean, float [][] decomposedData,
       float [] result) {
    FactorAnalysis.decompositionOneFactorData(d,factor_index, decomposedData);
    float grandMean =  Statistics.mean(d);

    float SStot = 0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<decomposedData[i].length;j++) {
        SStot += (grandMean-decomposedData[i][j])*(grandMean-decomposedData[i][j]);
      }
    }
    for (int i=0;i<decomposedData.length;i++)
       mean[i] = Statistics.mean(decomposedData[i]);
    float SSBetw =  0;
    for (int i=0;i<decomposedData.length;i++) {
      SSBetw += decomposedData[i].length*(mean[i]-grandMean)*(mean[i]-grandMean);
    }
    float MSBetw =  SSBetw/(decomposedData.length-1);

    float SSWin = 0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<decomposedData[i].length;j++) {
        SSWin += (mean[i]-decomposedData[i][j])*(mean[i]-decomposedData[i][j]);
      }
    }
    float MSWin =  SSWin/(d.length-decomposedData.length);
    float F = MSBetw/MSWin;
 //   System.out.print("\nMSBetw="+MSBetw+ "\nMSWin="+MSWin +"\nF="+F);
    int df_SS_residual = d.length-decomposedData.length;
    int df_SS_effect = decomposedData.length-1;

 //   System.out.print("\nANOVA betai\n1="+0.5f*df_SS_residual + "\n2="+0.5f*df_SS_effect
 //                    + "\n3="+df_SS_residual+"\n4="+df_SS_residual
 //                    + "\n5="+df_SS_effect*F);

    float prob = Statistics.betai(0.5f*df_SS_residual,0.5f*df_SS_effect,
                                  df_SS_residual/(df_SS_residual+df_SS_effect*F));

    /*
    int [] wg_df = new int[decomposedData.length];
    float pooled_variance = Statistics.pooled_variance(decomposedData,wg_df,wg_mean);


    int J = factor_index.length;      // number of between group
    int K = factor_index[0].length;   // number of within group
    float SS_effect=0;
    for (int i=0;i<factor_index.length;i++) {
      mean[i] = Statistics.mean(decomposedData[i]);
      SS_effect += (mean[i]-grandMean)*(mean[i]-grandMean);
    }
    SS_effect *= K;

    float SS_residual=0;
    for (int j=0;j<factor_index.length;j++)
      for (int k=0;k<factor_index[j].length;k++)
        SS_residual += (decomposedData[j][k]-mean[j])*(decomposedData[j][k]-mean[j]);


    int df_SS_effect = J-1;
    int df_SS_residual = J*(K-1);

    float m_effect = SS_effect/df_SS_effect;
    float m_residual = SS_residual/df_SS_residual;

    float total_S = SS_residual+SS_effect;
    int total_df = J*K-1;


    float F = m_effect/m_residual;
    System.out.print("\nANOVA betai\n1 ="+0.5f*df_SS_residual + "\n2="+0.5f*df_SS_effect
                     + "\n3="+df_SS_residual+"\n4="+df_SS_effect
                     + "\n5="+df_SS_effect*F);

    float prob = Statistics.betai(0.5f*df_SS_residual,0.5f*df_SS_effect,df_SS_residual/(df_SS_residual+df_SS_effect*F));
    */
/*
   sigma_square_1 = m1
   sigma_square_2 = (m2-m1)/K

   E.M.S.
   sigma_square_12 = sigma_square_1 + K*sigma_square_2
   sigma_square_1  = sigma_square_1
   */
    result[0] = MSBetw;
//    result[1] = MSWin;
    result[1] = F;
    result[2] = prob;
//    result[4] = m_residual;
//    result[5] = df_SS_residual;
//    result[6] = total_S;
//    result[7] = total_df;
  }
  public static void rowData_oneWayANOVA(float [][] decomposedData,float [] mean, float [] result) {
    float grandMean=0;
    int N=0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<decomposedData[i].length;j++) {
        grandMean += decomposedData[i][j];
        N++;
      }

    }
    grandMean /= N;

    float SStot = 0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<decomposedData[i].length;j++) {
        SStot += (grandMean-decomposedData[i][j])*(grandMean-decomposedData[i][j]);
      }
    }
    float SSBetw =  0;
    for (int i=0;i<decomposedData.length;i++) {
      SSBetw += decomposedData[i].length*(mean[i]-grandMean)*(mean[i]-grandMean);
    }
    float MSBetw =  SSBetw/(decomposedData.length-1);

    float SSWin = 0;
    for (int i=0;i<decomposedData.length;i++) {
      for (int j=0;j<decomposedData[i].length;j++) {
        SSWin += (mean[i]-decomposedData[i][j])*(mean[i]-decomposedData[i][j]);
      }
    }
    float MSWin =  SSWin/(N-decomposedData.length);
    float F = MSBetw/MSWin;
    int df_SS_residual = N-decomposedData.length;
    int df_SS_effect = decomposedData.length-1;
    float prob = Statistics.betai(0.5f*df_SS_residual,0.5f*df_SS_effect,
                                  df_SS_residual/(df_SS_residual+df_SS_effect*F));

    result[0] = MSBetw;
    result[1] = F;
    result[2] = prob;
  }

  static String [] oneway_anova_name = {"between_group_MS","F_value","ANOVA pvalue"};
//  static String [] oneway_anova_name = {"between_group_MS","F_value","pvalue","BG_DF",
//    "within_group_MS","WG_DF","total_SS","total_DF"};

  public static String [] getANOVA_Name() {
    return oneway_anova_name;
  }

  public static void rowData_twoWayANOVA(float [] d,
      int [][] factor_1_index,float [] mean_1,float [][] decomposedData1,
      int [][] factor_2_index,float [] mean_2,float [][] decomposedData2,
      int [][][] factor_12_index,float [][] mean_12,float [][][] decomposedData12,
      float [] result) {

    // equal numbers of samples in each of replicate group
     FactorAnalysis.decompositionOneFactorData(d,factor_1_index, decomposedData1);
    FactorAnalysis.decompositionOneFactorData(d,factor_2_index, decomposedData2);
    FactorAnalysis.decompositionTwoFactorData(d,factor_12_index, decomposedData12);

    int I = mean_1.length;      //effect 1, number of obsrevation
    int J = mean_2.length;   //effect 2, number of obsrevation
    int K = factor_12_index[0][0].length;

    float grandMean =  Statistics.mean(d);
    int df_grandMean = 1;
    float SS_grandmean = 0;
    for (int i=0;i<d.length;i++)
      SS_grandmean += (d[i]-grandMean)*(d[i]-grandMean);
    SS_grandmean *= d.length;

    for (int i=0;i<mean_1.length;i++)
      mean_1[i] = Statistics.mean(decomposedData1[i]);

    for (int i=0;i<mean_2.length;i++)
      mean_2[i] = Statistics.mean(decomposedData2[i]);

    for (int i=0;i<factor_12_index.length;i++)
      for (int j=0;j<factor_12_index[i].length;j++)
            mean_12[i][j] = Statistics.mean(decomposedData12[i][j]);



            float SS_effect_1 = 0;
            for (int i=0;i<mean_1.length;i++)
              SS_effect_1 += (mean_1[i]-grandMean)*(mean_1[i]-grandMean);
            SS_effect_1 *= J*K;
            int DF_1 = I-1;

            float SS_effect_2 = 0;
            for (int j=0;j<mean_2.length;j++)
              SS_effect_2 += (mean_2[j]-grandMean)*(mean_2[j]-grandMean);
            SS_effect_2 *= I*K;
            int DF_2 = J-1;

            float SS_effect_12 = 0;
            for (int i=0;i<mean_1.length;i++)
               for (int j=0;j<mean_2.length;j++)
                  SS_effect_12 += (mean_12[i][j]-mean_1[i] - mean_2[j] + grandMean)*(mean_12[i][j]-mean_1[i] - mean_2[j] + grandMean);
            SS_effect_12 *= K;
            int DF_12 = DF_1*DF_2;

                  float SS_effect_residual = 0;
                  for (int i=0;i<factor_12_index.length;i++)
                    for (int j=0;j<factor_12_index[i].length;j++)
                        for (int n=0;n<factor_12_index[i][j].length;n++)
                           SS_effect_residual += (decomposedData12[i][j][n]-mean_12[i][j])*
                          (decomposedData12[i][j][n]-mean_12[i][j]);
                   int DF_residual = I*J*(K-1);



                        float MS_1 = SS_effect_1/DF_1;
                        float MS_2 = SS_effect_2/DF_2;
                        float MS_12 = SS_effect_12/DF_12;
                        float MS_residual = SS_effect_residual/DF_residual;


                        float F_1 = MS_1/MS_residual;
                        float prob_1 = Statistics.betai(0.5f*DF_residual,0.5f*DF_1,DF_residual/(DF_residual+DF_1*F_1));
                        float F_2 = MS_2/MS_residual;
                        float prob_2 = Statistics.betai(0.5f*DF_residual,0.5f*DF_2,DF_residual/(DF_residual+DF_2*F_2));
                        float F_12 = MS_12/MS_residual;
                        float prob_12 = Statistics.betai(0.5f*DF_residual,0.5f*DF_12,DF_residual/(DF_residual+DF_12*F_12));

                        result[0] = MS_1;
                        result[1] = DF_1;
                        result[2] = F_1;
                        result[3] = prob_1;

                        result[4] = MS_2;
                        result[5] = DF_2;
                        result[6] = F_2;
                        result[7] = prob_2;

                        result[8] = MS_12;
                        result[9] = DF_12;
                        result[10] = F_12;
                        result[11] = prob_12;

                        result[12] = MS_residual;
                        result[13] = DF_residual;

                        result[14] = SS_effect_1+SS_effect_2+SS_effect_12
                                   +SS_effect_residual;;
                        result[15] = I*J*K;
                        result[16] = 1-SS_effect_residual/result[14];

    /*    FactorAnalysis.decompositionOneFactorData(d,factor_1_index, decomposedData1);
    FactorAnalysis.decompositionOneFactorData(d,factor_2_index, decomposedData2);
    FactorAnalysis.decompositionTwoFactorData(d,factor_12_index, decomposedData12);
    float grandMean =  Statistics.mean(d);
    float SStot = 0;
    for (int i=0;i<d.length;i++) {
      SStot += (grandMean-d[i])*(grandMean-d[i]);
    }
//factor 1
    for (int i=0;i<decomposedData1.length;i++)
      mean_1[i] = Statistics.mean(decomposedData1[i]);
    float SSBetw_1 = 0;
    for (int i=0;i<decomposedData1.length;i++)
      SSBetw_1 += decomposedData1[i].length*(mean_1[i]-grandMean)*(mean_1[i]-grandMean);
    float MSBetw_1 =  SSBetw_1/(decomposedData1.length-1);
    float SSWin_1 = 0;
    for (int i=0;i<decomposedData1.length;i++) {
      for (int j=0;j<decomposedData1[i].length;j++) {
        SSWin_1 += (mean_1[i]-decomposedData1[i][j])*(mean_1[i]-decomposedData1[i][j]);
      }
    }
    float MSWin_1 =  SSWin_1/(d.length-decomposedData1.length);
    float F_1 = MSBetw_1/MSWin_1;

//factor 2
    for (int i=0;i<decomposedData2.length;i++)
      mean_2[i] = Statistics.mean(decomposedData1[i]);
    float SSBetw_2 = 0;
    for (int i=0;i<decomposedData2.length;i++)
      SSBetw_2 += decomposedData2[i].length*(mean_2[i]-grandMean)*(mean_2[i]-grandMean);
    float MSBetw_2 =  SSBetw_2/(decomposedData2.length-1);
    float SSWin_2 = 0;
    for (int i=0;i<decomposedData2.length;i++) {
      for (int j=0;j<decomposedData2[i].length;j++) {
        SSWin_2 += (mean_2[i]-decomposedData2[i][j])*(mean_2[i]-decomposedData2[i][j]);
      }
    }
    float MSWin_2 =  SSWin_2/(d.length-decomposedData2.length);
    float F_2 = MSBetw_2/MSWin_2;

// interaction 1 and 2
    for (int i=0;i<factor_12_index.length;i++) {
      for (int j=0;j<factor_12_index[i].length;j++) {
        mean_12[i][j] = Statistics.mean(decomposedData12[i][j]);
      }
    }
    float SSBetw_12 = 0;
    float x;
    for (int i=0;i<decomposedData1.length;i++)
      for (int j=0;j<decomposedData2.length;j++) {
        x = mean_12[i][j]-mean_1[i] - mean_2[j] + grandMean;
        SSBetw_12 += decomposedData1[i].length*decomposedData2[j].length*x*x;
      }
    float MSBetw_12 = SSBetw_12/((decomposedData1.length-1)*(decomposedData2.length-1));
    float SSWin_12 = 0;
    for (int i=0;i<factor_12_index.length;i++)
      for (int j=0;j<factor_12_index[i].length;j++)
        for (int n=0;n<factor_12_index[i][j].length;n++)
          SSWin_12 += (decomposedData12[i][j][n]-mean_12[i][j])*(decomposedData12[i][j][n]-mean_12[i][j]);
    float MSWin_12 =  SSWin_12/(d.length-decomposedData1.length-decomposedData2.length);
    float F_12 = MSBetw_2/MSWin_2;
    */



  }

//  ANOVA.rowData_threeWayANOVA(data,
//                              factor_1_index,mean1,
//                              factor_2_index,mean2,
//                              factor_3_index,mean3,
//                              factor_12_index,mean12,
//                              factor_13_index,mean13,
//                              factor_23_index,mean23,
//                              factor_123_index,decomposedData123,
//                              threewayanovaresult);


  public static void rowData_threeWayANOVA(float [] d,
      int [][] factor_1_index,float [] mean_1,float [][] decomposedData1,
      int [][] factor_2_index,float [] mean_2,float [][] decomposedData2,
      int [][] factor_3_index,float [] mean_3,float [][] decomposedData3,
      int [][][] factor_12_index,float [][] mean_12,float [][][] decomposedData12,
      int [][][] factor_13_index,float [][] mean_13,float [][][] decomposedData13,
      int [][][] factor_23_index,float [][] mean_23,float [][][] decomposedData23,
      int [][][][] factor_123_index, float [][][] mean_123,float [][][][] decomposedData123,
      float [] result) {

    FactorAnalysis.decompositionOneFactorData(d,factor_1_index, decomposedData1);
    FactorAnalysis.decompositionOneFactorData(d,factor_2_index, decomposedData2);
    FactorAnalysis.decompositionOneFactorData(d,factor_3_index, decomposedData3);
    FactorAnalysis.decompositionTwoFactorData(d,factor_12_index, decomposedData12);
    FactorAnalysis.decompositionTwoFactorData(d,factor_13_index, decomposedData13);
    FactorAnalysis.decompositionTwoFactorData(d,factor_23_index, decomposedData23);
    FactorAnalysis.decompositionThreeFactorData(d,factor_123_index, decomposedData123);
//    public static void decompositionThreeFactorData(float [] d, int [][][][] threeFactorIndex, float [][][][] decomposedData) {

    int I = mean_1.length;      //effect 1, number of obsrevation
    int J = mean_2.length;   //effect 2, number of obsrevation
    int K = mean_3.length;// analysss per sample
    int L1 = factor_1_index[0].length;
    int L2 = factor_2_index[0].length;
    int L3 = factor_3_index[0].length;
    int L12 = factor_12_index[0][0].length;
    int L13 = factor_13_index[0][0].length;
    int L23 = factor_23_index[0][0].length;
    int L123 = factor_123_index[0][0][0].length;
/*
    for (int i=0;i<factor_123_index.length;i++)
       if (factor_123_index[i] != null)
       for (int j=0;j<factor_123_index[i].length;j++)
         if (factor_123_index[i][j] != null)
         for (int k=0;k<factor_123_index[i][j].length;k++)
           if (factor_123_index[i][j][k] != null) {
              L = factor_123_index[i][j][k].length;// analysss per sample
              k=factor_123_index[i][j].length;
              j=factor_123_index[i].length;
              i=factor_123_index.length;
             }

    for (int i=0;i<factor_123_index.length;i++)
      if (factor_123_index[i]!=null)
      for (int j=0;j<factor_123_index[i].length;j++)
        if (factor_123_index[i][j]!=null)
          for (int k=0;k<factor_123_index[i][j].length;k++)
            if (factor_123_index[i][j][k]!=null) {
                 L = factor_123_index[i][j][k].length;// analysss per sample
                 k=factor_123_index[i][j].length;
                 j=factor_123_index[i].length;
                 i=factor_123_index.length;
            }
            */
    float tim;
    float grandMean =  Statistics.mean(d);
    int df_grandMean = 1;
    float SS_grandmean = 0;
    for (int i=0;i<d.length;i++)
      SS_grandmean += (d[i]-grandMean)*(d[i]-grandMean);
    SS_grandmean *= d.length;


    for (int i=0;i<factor_123_index.length;i++)
      for (int j=0;j<factor_123_index[i].length;j++)
        for (int k=0;k<factor_123_index[i][j].length;k++) {
          if (factor_123_index[i][j][k]!=null)
              mean_123[i][j][k] = Statistics.mean(decomposedData123[i][j][k]);
        }

    for (int i=0;i<factor_12_index.length;i++)
      if (factor_12_index[i]!=null)
      for (int j=0;j<factor_12_index[i].length;j++)
        if (factor_12_index[i][j] !=null)
          mean_12[i][j] = Statistics.mean(decomposedData12[i][j]);

    for (int i=0;i<factor_13_index.length;i++)
      if (factor_13_index[i]!=null)
      for (int j=0;j<factor_13_index[i].length;j++)
        if (factor_13_index[i][j] !=null)
          mean_13[i][j] = Statistics.mean(decomposedData13[i][j]);

    for (int i=0;i<factor_23_index.length;i++)
      if (factor_23_index[i]!=null)
      for (int j=0;j<factor_23_index[i].length;j++)
        if (factor_23_index[i][j] !=null)
          mean_23[i][j] = Statistics.mean(decomposedData23[i][j]);


    for (int i=0;i<factor_1_index.length;i++)
      mean_1[i] = Statistics.mean(decomposedData1[i]);

    for (int i=0;i<factor_2_index.length;i++)
      mean_2[i] = Statistics.mean(decomposedData2[i]);

    for (int i=0;i<factor_3_index.length;i++)
      mean_3[i] = Statistics.mean(decomposedData3[i]);

        float SS_effect_1 = 0;
        for (int i=0;i<factor_1_index.length;i++)
          SS_effect_1 += (mean_1[i]-grandMean)*(mean_1[i]-grandMean);
        SS_effect_1 *= L1;
        int DF_1 = (I-1);

        float SS_effect_2 = 0;
        for (int j=0;j<factor_2_index.length;j++)
          SS_effect_2 += (mean_2[j]-grandMean)*(mean_2[j]-grandMean);
        SS_effect_2 *= L2;
        int DF_2 = (J-1);

        float SS_effect_3 = 0;
        for (int k=0;k<factor_3_index.length;k++)
          SS_effect_3 += (mean_3[k]-grandMean)*(mean_3[k]-grandMean);
        SS_effect_3 *= L3;
        int DF_3 = (K-1);

        float SS_effect_12 = 0;
        for (int i=0;i<mean_1.length;i++)
          if(factor_12_index[i]!=null)
          for (int j=0;j<mean_2.length;j++) {
            tim = mean_12[i][j]-mean_1[i] - mean_2[j] + grandMean;
            SS_effect_12 += tim*tim;
          }
            SS_effect_12 *= L12;
        int DF_12 = (I-1)*(J-1);

        float SS_effect_residual_12 = 0;
        for (int i=0;i<factor_12_index.length;i++)
          if (factor_12_index[i]!=null)
          for (int j=0;j<factor_12_index[i].length;j++)
            if (factor_12_index[i][j]!=null)
              for (int n=0;n<factor_12_index[i][j].length;n++)
                 SS_effect_residual_12 += (decomposedData12[i][j][n]-mean_12[i][j])*
                (decomposedData12[i][j][n]-mean_12[i][j]);
              int DF_residual_12 = I*J*(L12-1);

        float SS_effect_13 = 0;
        for (int i=0;i<mean_1.length;i++)
          if(factor_13_index[i]!=null)
          for (int k=0;k<mean_3.length;k++) {
            tim = mean_13[i][k]-mean_1[i] - mean_3[k] + grandMean;
            SS_effect_13 += tim*tim;
          }
        SS_effect_13 *= L13;
        int DF_13 = (I-1)*(K-1);

        float SS_effect_residual_13 = 0;
        for (int i=0;i<factor_13_index.length;i++)
          if (factor_13_index[i]!=null)
          for (int k=0;k<factor_13_index[i].length;k++)
            if (factor_13_index[i][k]!=null)
              for (int n=0;n<factor_13_index[i][k].length;n++)
                 SS_effect_residual_13 += (decomposedData13[i][k][n]-mean_13[i][k])*
                (decomposedData13[i][k][n]-mean_13[i][k]);
              int DF_residual_13 = I*K*(L13-1);

        float SS_effect_23 = 0;
        for (int j=0;j<mean_2.length;j++)
          if(factor_23_index[j]!=null)
         for (int k=0;k<mean_3.length;k++) {
        tim = mean_23[j][k]-mean_2[j] - mean_3[k] + grandMean;
            SS_effect_23 += tim*tim;
         }
        SS_effect_23 *= L23;
        int DF_23 = (J-1)*(K-1);

        float SS_effect_residual_23 = 0;
        for (int i=0;i<factor_23_index.length;i++)
          if (factor_23_index[i]!=null)
          for (int k=0;k<factor_23_index[i].length;k++)
            if (factor_23_index[i][k]!=null)
              for (int n=0;n<factor_23_index[i][k].length;n++)
                 SS_effect_residual_23 += (decomposedData23[i][k][n]-mean_23[i][k])*
                (decomposedData23[i][k][n]-mean_23[i][k]);
              int DF_residual_23 = J*K*(L23-1);


        float SS_effect_123 = 0;
        for (int i=0;i<mean_1.length;i++)
          for (int j=0;j<mean_2.length;j++)
            for (int k=0;k<mean_3.length;k++)
              if (factor_123_index[i][j][k]!=null)
              for (int l=0;l<factor_123_index[i][j][k].length;l++) {
                 tim=mean_123[i][j][k]-mean_1[i]-mean_2[j]-mean_3[k]
                          +mean_12[i][j]+mean_13[i][k]+mean_23[j][k]
                                  - grandMean;
                SS_effect_123 += tim*tim;
              }
        SS_effect_123 *= L123;
        int DF_123 = (I-1)*(J-1)*(K-1);



       float SS_effect_residual123 = 0;
       for (int i=0;i<factor_123_index.length;i++)
         if (factor_123_index[i]!=null)
          for (int j=0;j<factor_123_index[i].length;j++)
            if (factor_123_index[i][j]!=null)
             for (int k=0;k<factor_123_index[i][j].length;k++)
               if (factor_123_index[i][j][k]!=null)
                 for (int l=0;l<factor_123_index[i][j][k].length;l++)
                    SS_effect_residual123 += (decomposedData123[i][j][k][l]-mean_123[i][j][k])*
                      (decomposedData123[i][j][k][l]-mean_123[i][j][k]);
       int DF_residual_123 = I*J*K*(L123-1);


       float MS_1 = SS_effect_1/DF_1;
       float MS_2 = SS_effect_2/DF_2;
       float MS_3 = SS_effect_3/DF_3;
       float MS_12 = SS_effect_12/DF_12;
       float MS_13 = SS_effect_13/DF_13;
       float MS_23 = SS_effect_23/DF_23;
       float MS_123 = SS_effect_123/DF_123;
  //     float MS_residual_12 = SS_effect_residual_12/DF_residual_12;
  //     float MS_residual_13 = SS_effect_residual_13/DF_residual_13;
  //     float MS_residual_23 = SS_effect_residual_23/DF_residual_23;
       float MS_residual_123 = SS_effect_residual123/DF_residual_123;


       float F_1 = MS_1/MS_residual_123;
       float prob_1 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_1,DF_residual_123/(DF_residual_123+DF_1*F_1));
       float F_2 = MS_2/MS_residual_123;
       float prob_2 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_2,DF_residual_123/(DF_residual_123+DF_2*F_2));
       float F_3 = MS_3/MS_residual_123;
       float prob_3 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_3,DF_residual_123/(DF_residual_123+DF_3*F_3));
       float F_12 = MS_12/MS_residual_123;
       float prob_12 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_12,DF_residual_123/(DF_residual_123+DF_12*F_12));
       float F_13 = MS_13/MS_residual_123;
       float prob_13 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_13,DF_residual_123/(DF_residual_123+DF_13*F_13));
       float F_23 = MS_23/MS_residual_123;
       float prob_23 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_23,DF_residual_123/(DF_residual_123+DF_23*F_23));
       float F_123 = MS_123/MS_residual_123;
       float prob_123 = Statistics.betai(0.5f*DF_residual_123,0.5f*DF_123,DF_residual_123/(DF_residual_123+DF_123*F_123));

/*
   sigma_square_residual = MS_r
   sigma_square_12 = (MS_12 - MS_r)/K
   sigma_square_2 = (MS_2 - MS_12)/(I*K)
   sigma_square_1 = (MS_1 - MS_12)/(J*K)

   E.M.S.
   total = sigma_square_residual + K*sigma_square_12 + (I*K)*sigma_square_2 + (J*K)*sigma_square_1
   1  = sigma_square_residual + K*sigma_square_12 + (J*K)*sigma_square_1
   2  = sigma_square_residual + K*sigma_square_12 + (I*K)*sigma_square_2
   12  = sigma_square_residual + K*sigma_square_12
   residual = sigma_square_residual
   */

              result[0] = MS_1;
              result[1] = DF_1;
              result[2] = F_1;
              result[3] = prob_1;

              result[4] = MS_2;
              result[5] = DF_2;
              result[6] = F_2;
              result[7] = prob_2;

              result[8] = MS_3;
              result[9] = DF_3;
              result[10] = F_3;
              result[11] = prob_3;

              result[12] = MS_12;
              result[13] = DF_12;
              result[14] = F_12;
              result[15] = prob_12;

              result[16] = MS_13;
              result[17] = DF_13;
              result[18] = F_13;
              result[19] = prob_13;

              result[20] = MS_23;
              result[21] = DF_23;
              result[22] = F_23;
              result[23] = prob_23;

              result[24] = MS_123;
              result[25] = DF_123;
              result[26] = F_123;
              result[27] = prob_123;

              result[28] = MS_residual_123;
              result[29] = MS_residual_123;

              result[30] = SS_effect_1+SS_effect_2+SS_effect_3+SS_effect_12
                         +SS_effect_13+SS_effect_23+SS_effect_123+SS_effect_residual123;;
              result[31] = I*J*K*L123;
              result[32] = 1-SS_effect_residual123/result[30];

  }

  static String [] threeway_anova_name = {"MS_1","DF_1","F_1","pvalue_1",
    "MS_2","DF_2","F_2","pvalue_2",
    "MS_3","DF_3","F_3","pvalue_3",
    "MS_12","DF_12","F_12","pvalue_12",
    "MS_13","DF_13","F_13","pvalue_13",
    "MS_23","DF_23","F_23","pvalue_23",
    "MS_123","DF_123","F_123","pvalue_123",
    "MS_residual","DF_residual",
    "Total SS","Total DF","R square"};
  public static String [] get_Three_Way_ANOVA_Name() {
    return threeway_anova_name;
  }
/*
  public static float [] rowData_ANOVA_with_baseline(float [] d, int [][] repIndex, float [][] decomposedData) {
   Statistics.getRepIndexedData(d,repIndex,decomposedData);
     return anova_with_baseline(decomposedData);
  }
  */
  /*
  public static float []  anova_with_baseline(float [][] x) {
    int [] wg_df = new int[x.length];
    float [] wg_mean = new float[x.length];
   float pooled_variance = Statistics.pooled_variance(x,wg_df,wg_mean);
   int df_wg_total = 0;
   for (int i=0;i<wg_df.length;i++)
     df_wg_total+=wg_df[i];
   float [] wg_mean_with_baseline = new float[wg_mean.length+1];
   for (int i=0;i<wg_mean.length;i++)
     wg_mean_with_baseline[i] = wg_mean[i];
   wg_mean_with_baseline[wg_mean.length] = 0;
    float bg_var = wg_mean.length*Statistics.var(wg_mean_with_baseline);
    int df_bg = wg_mean.length-1;
    float [] result = new float[3];
    float f = bg_var/pooled_variance;
    float prob = Statistics.betai(0.5f*df_wg_total,0.5f*df_bg,df_wg_total/(df_wg_total+df_bg*f));
    result[0] = bg_var;  // MSA - ANOVA
    result[1] = f; // F value
    result[2] = prob;
    return result;
   }
   */
}