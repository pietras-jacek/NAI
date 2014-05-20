package liczby;

import java.util.Random;

/**
 * Implementacja algorytmu kieszonkowego
 * @author Jacek Pietras
 */
public class Algorithm {
    static final int SIZE = 35,N=250;	
    static final double PSTWO1=0.1,PSTWO2=0.3;
     int[][] E=new int[7*N][SIZE];
     int[]   C=new int[7*N];
    
 //---------------------------Pocket Algorithm----------------------------------// 
 //http://en.wikipedia.org/wiki/Perceptron
 //Opis algorytmu znajduje się w sekcji Variants 
   
     public Algorithm(int[] number){
	   
	   
	    int[][] accept = new int[N][SIZE];    //zbiór przykładów
	    int[][] unaccept = new int[6*N][SIZE];  //negatywnych i pozytywnych 
	    
	    for(int j=0;j<6*N;j++){
	  	  for(int i=0;i<SIZE;i++){
	  		  if(j<N) 
	  		      accept[j][i]=number[i];   //przyklady pozytywne
	  		  if(j>=2*N && j<3*N) 
	  		      unaccept[j][i]=number[i]; //przyklady negatywne
	  		  if(j>=4*N &&j<5*N)
	  		      unaccept[j][i]=1; //przyklady negatywne
	  		  else  
	  		      unaccept[j][i]=-1;	   
	  	  }
	    } 
	   
	    
 for(int j=0;j<6*N;j++){
  	
  for(int i=0;i<SIZE;i++){  
	
	  if(number[i]==1 && j<N){ 
  	  
		  Random generator = new Random();
	  
		  double p = generator.nextDouble(); //losuje r [0,1]
    
		  if(p<PSTWO1){
    
			  accept[j][i]=-1;         	
              
		  } 
			  
	  }
  }
	  for(int i=0;i<SIZE;i++){  
			
		  //if(number[i]==1){ 
	  	  
			  Random generator = new Random();
		  
			  double p = generator.nextDouble(); //losuje r [0,1]
	    
			  if(p>PSTWO2){
	    
				  unaccept[j][i]=-1;         	  
			  }   
		//  }	  	  
  }

}   
    for(int j=0;j<6*N;j++){
	  for(int i=0;i<SIZE;i++){
	    if(j<N) 
		 E[j][i]=accept[j][i];
	    else 
	    E[j][i]=unaccept[j][i];  
	   // System.out.print(E[j][i]+" ");
	   
	  }
	 if(j<N) 
	  C[j]=1;
	 else
	  C[j]=-1; 
	   } 
   
   }
	
   public  double[] pocketAlg(int[][] example, int[] C,int max){
	  double[] pocketWeight = new double[SIZE+1];
	  double[] w = new double[SIZE+1];
	  int[] y=new int[2*N];
	  double sum=0;
	  int run=0,runw=0; //czas życia 
	  int n=0;
	  int iterations=0,modifications;  
	  Random generator = new Random();
	 for(int i=0;i<SIZE;i++) w[i]=generator.nextGaussian();
	for(int j=0;j<SIZE;j++) pocketWeight[j]=generator.nextGaussian();  
	  
	 while(max>=0 || iterations==max){ 
		 modifications=0;
		 iterations++;
    for(int j=0;j<2*N;j++){
		 n = generator.nextInt(2*N);
	 for(int k=0;k<SIZE;k++)	 
	   sum=sum+pocketWeight[k]*example[n][k];
		 y[n]=(int)Math.signum(sum);
	  if(y[n]!=C[n]) {
		  modifications++;
	  
	     run=0;
	     if(y[n]==1 && C[n]==-1){ 
	    	for(int k=0;k<SIZE;k++)  
	    	     w[k]=w[k]-example[n][k];
	     } else{
	    	 for(int k=0;k<SIZE;k++) 	 
	    		 	w[k]=w[k]+example[n][k];	    	 
	     }
	} else run++;
	  if(run>runw) {
		  runw=run; 
	for(int i=0;i<SIZE;i++) 
	   pocketWeight[i]=w[i];
	  }
	  
	  max--;
             }
	 }
	//for(int i=0;i<SIZE;i++) System.out.print(pocketWeight[i]+" ");  
	
	
	
	return pocketWeight;
 }
   
   
   
   
}
