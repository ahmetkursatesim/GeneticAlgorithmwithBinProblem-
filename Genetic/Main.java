import java.io.IOException;

public class Main {



	public static void main(String[] args) throws IOException {
		
		GeneticAlgorithm genetic = new GeneticAlgorithm();
		  int rcount =  genetic.LoadData();
		 
		 int old_fitting = 1;
			Rectangle prect = new Rectangle();
		  
		  prect.x[0] = 0;
		  prect.y[0] = 0;
		  prect.x[1] = genetic.getParentNode().width;
		  prect.y[1] = genetic.getParentNode().height;

		  genetic.createPopulation(256);
		  Rectangle[]rects = new Rectangle[rcount];
		  for (int a=0; a<rcount; a++) {
			  
				 rects[a]=new Rectangle();
			  }
		  int zcount = 0;
		  int lcount = 0;
		  int mratio = 256;
		  while (old_fitting > 0 && zcount<192)
		  {
		    lcount++;

		    int ratio = mratio - lcount;
		    if (ratio<4) ratio = 4;
		    int fitting = genetic.EvolveGenes(ratio+zcount);
           
		    if (old_fitting == fitting)
		       zcount++;
		    else zcount = 0;
		 
		    old_fitting = fitting;

		    if (lcount == 1 || lcount % 100 == 0 || old_fitting == 0 || zcount>=192)
		    {
		    	System.out.println("Iteration number : " + lcount);
		    	System.out.println(" --- Fitting (intersection area) : " + fitting);
		    


		      Gene gene = genetic.getGene(0);
		      for (int j=0; j<rcount; j++)
		      {
		        rects[j].x[0] = Integer.parseInt(gene.genes[j*3+0]);
		        rects[j].y[0] = Integer.parseInt(gene.genes[j*3+1]);

		        if (Integer.parseInt(gene.genes[j*3+2])<128)
		        {
		          rects[j].x[1] = Integer.parseInt(gene.genes[j*3+0]) + genetic.getNodeWidth(j);
		          rects[j].y[1] = Integer.parseInt(gene.genes[j*3+1]) + genetic.getNodeheight(j);

		        }
		        else
		        {
		          rects[j].x[1] = Integer.parseInt(gene.genes[j*3+0]) + genetic.getNodeheight(j);
		          rects[j].y[1] = Integer.parseInt(gene.genes[j*3+1]) + genetic.getNodeWidth(j);
		        }
		        System.out.println("rectangleX"+rects[j].x[0]+""+"rectangleY"+rects[j].y[0]+"Rectangle2X"+rects[j].x[1]+"Rectangle2Y"+rects[j].y[1]);
		 
		 
		      }


		    }
		  }

		  
		


	
	}
	  

	 
}
