import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneticAlgorithm {
	int counter=0;
	Node parent ;
   Node rects;

	Gene[] genes ;
	
	int rect_count;
    int gene_count;
    int[]  co_prob;
    Random srand = new Random(System.currentTimeMillis());
	Random rand = new Random();
	
	// Genetic Algorithm Code
public void GeneticAlgorithm()
	{
	
	  parent.width = 255;
	  parent.height = 255;

	  rect_count = 0;
	  rects = null;

	  gene_count = 0;
	  genes = null;

	  co_prob = null;
	}

	

	public int LoadData() throws IOException
	{
	
	  BufferedReader br = new BufferedReader(new FileReader("input.txt"));
	
		  String line = null;
		  String line2 = null;
		  line = br.readLine();
		  String[] split = line.split(" ");
		 
		parent =new Node(); 
	    parent.width = Integer.parseInt(split[0]);
	    parent.height = Integer.parseInt(split[1]);

	    int icount = 0;
	    int width, height;
	    line = br.readLine();
	    line2=br.readLine();
	    while (line != null)
	    {
	    
	      String [] split2 = line.split(" ");
	      
	      width = Integer.parseInt(split[0]);
	      height = Integer.parseInt(split[1]);
	      icount++;
	    
	      
	      line = br.readLine();
	    }
	    br.close();
	    BufferedReader ar= new BufferedReader(new FileReader("input1.txt"));
	    line2=ar.readLine();
	    rect_count = icount;
	      rects=new Node();
		   
		  rects.height2=new int[rect_count+1];
		  rects.width1=new int [rect_count+1];
	  while(line2!=null) {
  
       int width1;
       int height2;
          
         
	      String [] split3 = line2.split(" ");
	      
	        width1= Integer.parseInt(split3[0]);
	    
	       height2= Integer.parseInt(split3[1]);
		    
		  rects.height2[counter]=height2;
		  rects.width1[counter]=width1;

		      counter++;
		  
		      line2=ar.readLine();
		      
		     
	  }
	  ar.close();
	  
	
	
	    return rect_count;
	  
	}

	public int getRectangleCount()
	{
	  return rect_count;
	}

	public int getNodeWidth(int index)
	{
	  return rects.width1[index];
	}
	public int getNodeheight(int index)
	{
	  return rects.height2[index];
	}

	public Node getParentNode()
	{
	  return parent;
	}

	public int getPopulationCount()
	{
	  return gene_count;
	}

	Gene getGene(int index)
	{
	  return genes[index];
	}

	void createPopulation(int count)
	{
	

	  genes = new Gene[count];
	  
	  for (int i=0; i<count; i++)
	  {  
      genes[i]=new Gene(); 
	    createGene(genes[i]);
	    findFittingValue(genes[i]);
	  }

	  co_prob = new int[count];
	  gene_count = count;
	}


	void createGene(Gene gene)
	{
	  for (int i=0; i<rect_count; i++)
	  {
	    if (gene.genes == null) {
	      gene.genes = new String [rect_count * 3];
	    }
	    for (int j=0; j<rect_count; j++)
	    {
	    	
	      gene.genes[j*3+2] = Integer.toString(rand.nextInt()%255);
	
	      if (Integer.parseInt(gene.genes[j*3+2])<128) {
	    
	        gene.genes[j*3+0] =Integer.toString(Math.abs(rand.nextInt()%(parent.width-rects.width1[j])));
	        gene.genes[j*3+1] = Integer.toString(Math.abs(rand.nextInt()%(parent.height-rects.height2[j])));
	      
	      }
	      else
	      {
	    	  
	    	
	        gene.genes[j*3+0] = Integer.toString(Math.abs(rand.nextInt()%(parent.height-rects.height2[j])));
	        gene.genes[j*3+1] = Integer.toString(Math.abs(rand.nextInt()%(parent.width-rects.width1[j])));
	      
	      }
	    }
	  }
	}

	void sortPopulation()
	{
	  for (int i=0; i<gene_count-1; i++)
	  for (int j=i+1; j<gene_count; j++)
	  {
	    if (genes[i].fitting>genes[j].fitting)
	    {
	      String[] ptr = genes[i].genes;
	      genes[i].genes = genes[j].genes;
	      genes[j].genes = ptr;

	      int ftmp = genes[i].fitting;
	      genes[i].fitting = genes[j].fitting;
	      genes[j].fitting = ftmp;
	    }
	  }
	}

	void mutate(Gene gene, Gene target, int ratio)
	{
	  for (int i=0; i<rect_count*3; i++)
	  {
	    if ((i+1) % 3 == 0)
	    {
	      if (rand.nextInt()% 256 == 1)
	      {
	        int mutated_gene = Integer.parseInt(gene.genes[i])+ ( Math.abs(rand.nextInt())%200 - 100);
	        if (mutated_gene<0) mutated_gene = 0;
	        if (mutated_gene>255) mutated_gene = 255;
	        target.genes[i] = ""+ mutated_gene;
	      }
	    }
	    else
	    {
	      if (rand.nextInt()% 256== 1)
	      {
	        int mutated_gene = Integer.parseInt(gene.genes[i]) + (Math.abs(rand.nextInt())% ratio - (ratio/2));
	        if (mutated_gene<0) mutated_gene = 0;
	        if (mutated_gene>255) mutated_gene = 255;
	        target.genes[i] = ""+ mutated_gene;
	      }
	    }
	  }
	}

	void crossover(Gene gene1, Gene gene2, Gene target)
	{
	  for (int i=0; i<rect_count*3; i++)
	      if (rand.nextInt() % 10 >= 5)
	         target.genes[i] = gene1.genes[i];
	      else target.genes[i] = gene2.genes[i];
	}

	int findFittingValue(Gene gene)
	{
	  int fit_value = 0;

	  Rectangle prect=new Rectangle();
	  prect.x[0] = 0;
	  prect.y[0] = 0;
	  prect.x[1] = getParentNode().width;
	  prect.y[1] = getParentNode().height;


	  Rectangle [] rectsa= new Rectangle[rect_count];
	  for (int a=0; a<rect_count; a++) {
		  
		 rectsa[a]=new Rectangle();
	  }
	  for (int i=0; i<rect_count; i++)
	  {
		  
		

	    rectsa[i].x[0] = Integer.parseInt(gene.genes[i*3+0]);
	    rectsa[i].y[0] = Integer.parseInt(gene.genes[i*3+1]);
	   
	    if (Integer.parseInt(gene.genes[i*3+2])<128)
	    {
	  
	      rectsa[i].x[1] =(Integer.parseInt(gene.genes[i*3+0]) + getNodeWidth(i));
	      rectsa[i].y[1] =  (Integer.parseInt(gene.genes[i*3+1]) + getNodeheight(i));
	     
	    }
	    else
	    {
	      rectsa[i].x[1] =(Integer.parseInt(gene.genes[i*3+0]) + getNodeheight(i));
	      rectsa[i].y[1] = (Integer.parseInt(gene.genes[i*3+1]) +getNodeWidth(i));
	    }


	    Rectangle isect = FindIntersection(rectsa[i], prect);
	   
	    int rect_area = Math.abs(rectsa[i].x[1]-rectsa[i].x[0]) * Math.abs(rectsa[i].y[1]-rectsa[i].y[0]);

	    if (isect.x[0] == -1) {
	    	 fit_value += rect_area * rect_area;
	      
	    }
	      
	    else {
	    	
	    	fit_value += Math.abs(rect_area - Math.abs(isect.x[1]-isect.x[0]) * Math.abs(isect.y[1]-isect.y[0]));
	    	
	    }
	  }
	 
	  for (int i=0; i<rect_count-1; i++)
	  for (int j=i+1; j<rect_count; j++)
	  {
	    Rectangle isect = FindIntersection(rectsa[i], rectsa[j]);
	    if (isect.x[0] != -1)
	       fit_value += Math.abs(isect.x[1]-isect.x[0]) * (isect.y[1]-isect.y[0]);
	
	  }
	
	  gene.fitting = fit_value;

	  return fit_value;
	}

	public int EvolveGenes(int ratio)
	{
	  if (gene_count <=0 )
	     return 0;

	  for (int i=0; i<gene_count; i++) {
	      findFittingValue(genes[i]);
	  }
	  sortPopulation();

	  Gene []mgenes = new Gene[gene_count];
	  for (int i=0; i<gene_count; i++)
	  {
		  mgenes[i]=new Gene();
	    mgenes[i].genes = null;
	    
	    createGene(mgenes[i]);
	  }

	  for (int i=0; i<rect_count*3; i++) {
	      mgenes[0].genes[i] = genes[0].genes[i];
	    
	  }
	  for (int i=1; i<gene_count; i++)
	  {
		  
		  
	
	    int g1 = Math.abs(rand.nextInt() % (gene_count/3+1));
	    int g2 =Math.abs(rand.nextInt() % (gene_count/3+1));
	    if (g1 != g2) {
	    	
	       crossover(genes[g1], genes[g2], mgenes[i]);
	    }
	  }

	  for (int i=1; i<gene_count; i++) {
	      mutate(mgenes[i], mgenes[i], ratio);
	  }
	  for (int i=1; i<gene_count; i++) {
		  genes[i] = mgenes[i];
	  }
	
	
	  findFittingValue(genes[0]);
	 
	  return genes[0].fitting;
	}


	public boolean isInsideRectangle(int x, int y, Rectangle rect) {

		int x1 = rect.x[0];
		int x2 = rect.x[1];

		if (x1 > x2) {
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
		}

		int y1 = rect.y[0];
		int y2 = rect.y[1];
		if (y1 > y2) {
			int tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		return (x > x1 && x < x2 && y > y1 && y < y2);
	}

	Point GetLineIntersection(Line line1, Line line2) {
		Point initialPoint = new Point(-1, -1);

		if (    line1.p1.x == line1.p2.x && 
				line2.p1.y == line2.p2.y && 
				line1.p1.x >= line2.p1.x && 
				line1.p1.x <= line2.p2.x && 
				line2.p1.y <= line1.p2.y) {
			
			initialPoint.x = line1.p1.x;
			initialPoint.y = line2.p1.y;
		}

		if (line2.p1.x == line2.p2.x &&
			      line1.p1.y == line1.p2.y &&
			      line2.p1.x >= line1.p1.x &&
			      line2.p1.x <= line1.p2.x &&
			      line1.p1.y >= line2.p1.y &&
			      line1.p1.y <= line2.p2.y) {
			
			initialPoint.x = line2.p1.x;
			initialPoint.y = line1.p1.y;
		}

		return initialPoint;
	}	Node rects2 ;

	Line Line(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
		}

		if (y1 > y2) {
			int tmp = y1;
		    y1 = y2;
		    y2 = tmp;
		}

		Line line = new Line();
	
		line.p1.x=x1;
		line.p1.y = y1;
		line.p2.x = x2;
		line.p2.y = y2;
		return line;
	}
	
	public Rectangle FindIntersection(Rectangle rect1, Rectangle rect2)
	{
	  Point [] points = new Point [4];
	  int IntersectionCount = 0;
	  int inter=0;
	  points[0]=new Point(0,0);
  	  points[1]=new Point(0,0);
  	  points[2]=new Point(0,0);
  	  points[3]=new Point(0,0);
	  // rectangle 1 edges inside rectangle 2
	  for (int i=0; i<2; i++)
	  for (int j=0; j<2; j++)
	    if ( isInsideRectangle(rect1.x[i], rect1.y[j], rect2) )
	    {
	    	
	
	      points[IntersectionCount].x = rect1.x[i];
	      points[IntersectionCount].y = rect1.y[j];
	    
	      IntersectionCount++;
	    }

	  // rectangle 2 edges inside rectangle 1
	  for (int i=0; i<2; i++)
	  for (int j=0; j<2; j++)
	    if ( isInsideRectangle(rect2.x[i], rect2.y[j], rect1) )
	    {
	      points[IntersectionCount].x = rect2.x[i];
	      points[IntersectionCount].y = rect2.y[j];
	      IntersectionCount++;
	    }

	  // line intersections
	  int [][] line_orders  =  { {0,0}, {1,0}, {1,1}, {0,1} };

	  Point ipoint=new Point(0,0);
	  for (int i=0; i<4; i++)
	  for (int j=0; j<4; j++)
	  {
	    int pa_x1 = line_orders[i][0];
	    int pa_y1 = line_orders[i][1];
	    int pa_x2 = line_orders[(i+1)%4][0];
	    int pa_y2 = line_orders[(i+1)%4][1];

	    int pb_x1 = line_orders[j][0];
	    int pb_y1 = line_orders[j][1];
	    int pb_x2 = line_orders[(j+1)%4][0];
	    int pb_y2 = line_orders[(j+1)%4][1];

	    ipoint = GetLineIntersection(Line(rect1.x[pa_x1], rect1.y[pa_y1], rect1.x[pa_x2], rect1.y[pa_y2]),
	                                 Line(rect2.x[pb_x1], rect2.y[pb_y1], rect2.x[pb_x2], rect2.y[pb_y2]));
if (ipoint.x != -1)
	    {
	      points[j].x = ipoint.x;
	      points[j].y = ipoint.y;
	      inter++;


	    }
	  }

	  Rectangle isect  = new Rectangle();
	  isect.x[0] = -1;
	  isect.x[1] = -1;
	  isect.y[0] = -1;
	  isect.y[1] = -1;

	  if (IntersectionCount == 4)
	  {
	    for (int i=0; i<4; i++)
	    {
	      if (isect.x[0] == -1)
	         isect.x[0] = points[i].x;

	      if (isect.x[1] == -1 &&
	          isect.x[0] != points[i].x)
	         isect.x[1] = points[i].x;

	      if (isect.y[0] == -1)
	         isect.y[0] = points[i].y;

	      if (isect.y[1] == -1 &&
	          isect.y[0] != points[i].y)
	         isect.y[1] = points[i].y;
	    }

	    if (isect.x[0]>isect.x[1])
	    {
	      int tmp = isect.x[0];
	      isect.x[0] = isect.x[1];
	      isect.x[1] = tmp;
	    }

	    if (isect.y[0]>isect.y[1])
	    {
	      int tmp = isect.y[0];
	      isect.y[0] = isect.y[1];
	      isect.y[1] = tmp;
	    }
	  }

	  return isect;
	}
}
