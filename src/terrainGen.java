import org.newdawn.slick.Color;



 
public class terrainGen {
	public Block[] rects;
	public boolean finishedGen = false;

	//BLOCKS
	public final int AIR = 0;
	public final int DIRT = 1;
	public final int GRASS = 2;
	public final int STONE = 5;

	public final int TNT = 3;
	public final int JOBBY = 4;
	public final int COAL = 6;
	public final int BRONZE = 7;
	public final int SILVER = 8;
	public final int GOLD = 9;
	public final int MOONSTONE = 10;
	public final int AMETHYST = 11;
	public final int ALUMINIUM = 12;
	//INSERT MORE BLOCKS HERE 
	public final int INFINITESTONE = 16;
	
	public int xGen;
	public int yGen;
	public int grid;
	
	private int outsideCounter;
	private int outsideTimesCounter;
	private int rightOutsideCounter;
	private int rightOutsideTimesCounter;
	

	public void setup(int screenWidth){		
		grid = 32;
		xGen = 1080 - 32 * 2;
		xGen = Math.round(screenWidth / grid) * grid; 
		yGen = 960 * 8;

		outsideCounter = 0;
		outsideTimesCounter = 0;
		rightOutsideCounter = xGen / grid - 1;
		rightOutsideTimesCounter = xGen / grid - 1;
		
		
		int blocksX = (int) (Math.ceil((float)xGen / grid));
		int blocksY = (int) (Math.ceil((float)yGen / grid));
		
		rects = new Block[blocksX * blocksY];  

		Thread t2 = new Thread(){
			public void run(){
				
				
				int i = 0;
				for(int y = 0; y < yGen; y += grid){
					if(i >= rects.length) break;
					for(int x = 0; x < xGen; x += grid){
						if(y <= 128 + grid){
							rects[i] = new Block(x,y,grid,grid,AIR);
							i++;
							if(i >= rects.length) break;
						}else if(y == 256){
							int randAir = 1 + (int)(Math.random() * ((10 - 1) + 1));
							if(randAir == 1){
								rects[i] = new Block(x,y,grid,grid,AIR);
							}else{
								rects[i] = new Block(x,y,grid,grid,GRASS);
							}
							
							i++;
						}else if (y > 960 && y < 1600){
							if(x == 0 || x == xGen){
								int randBronze = (int) Math.round(Math.random() * 5);
								if(randBronze == 5){
									rects[i] = new Block(x,y,grid,grid,BRONZE);
									i++;
								}else if(randBronze == 1){ 
									rects[i] = new Block(x,y,grid,grid,COAL);
									i++;
								}else{
									int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
									if(rand == 1)rects[i] = new Block(x,y,grid,grid,DIRT);
									if(rand == 2)rects[i] = new Block(x,y,grid,grid,STONE);
									if(rand == 3)rects[i] = new Block(x,y,grid,grid,AIR);
									i++;
								}
							
							}else if(x == 32 || x == xGen - 32){
								int randCoal = (int) Math.round(Math.random() * 3);
								if(randCoal == 1){
									rects[i] = new Block(x,y,grid,grid,COAL);
									i++;
								}else{
									int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
									if(rand == 1)rects[i] = new Block(x,y,grid,grid,DIRT);
									if(rand == 2)rects[i] = new Block(x,y,grid,grid,STONE);
									if(rand == 3)rects[i] = new Block(x,y,grid,grid,AIR);
									i++;
								}
							
							}else{
								int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
								if(rand == 1)rects[i] = new Block(x,y,grid,grid,DIRT);
								if(rand == 2)rects[i] = new Block(x,y,grid,grid,STONE);
								if(rand == 3)rects[i] = new Block(x,y,grid,grid,AIR);
								i++;
							}
							
						}else if(y >= 320){
							int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
							if(rand == 1)rects[i] = new Block(x,y,grid,grid,DIRT);
							if(rand == 2)rects[i] = new Block(x,y,grid,grid,STONE);
							if(rand == 3)rects[i] = new Block(x,y,grid,grid,AIR);
							i++;
						}else{
							int randBlock = (int) Math.round(Math.random() * JOBBY);
							rects[i] = new Block(x,y,grid,grid,randBlock);
							i++;
							if(i >= rects.length) break;
						}
					}
				}
				finishedGen = true;
			}
			};t2.start();
		
		}
	
	public void removeOutside(){
		if(outsideTimesCounter < xGen / grid / 2){
			for(int i = 0; i < yGen / grid; i++){
				rects[outsideCounter].type = 0;
				outsideCounter += xGen / grid;
			}
			for(int i = 0; i < yGen / grid; i++){
				rects[rightOutsideCounter].type = 0;
				rightOutsideCounter += xGen / grid;
			}
			outsideTimesCounter++;
			outsideCounter = outsideTimesCounter;
			rightOutsideTimesCounter--;
			rightOutsideCounter = rightOutsideTimesCounter;
			
		}
	}
	public Color BlockColor(int type){
		Color c = null;
		 
		if(type == -1)c = RRGGBB.white;
		if(type == DIRT) c = new RRGGBB(87,59,12);
		if(type == GRASS)c = RRGGBB.green;
		if(type == AIR)c = RRGGBB.transparent;
		if(type == TNT)c = RRGGBB.red;
		if(type == JOBBY)c = new RRGGBB(89,48,1);
		if(type == STONE)c = new RRGGBB(105,105,105);
		if(type == COAL)c = new RRGGBB(44,44,44); 
		if(type == BRONZE)c = new RRGGBB(205,127,50); 
		
		return c;
	}
}
