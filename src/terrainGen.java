import org.newdawn.slick.Color;



 
public class terrainGen {
	public Block[] rects;
	public boolean finishedGen = false;
	private int timer;
	//BLOCKS
	public final int AIR = 0;
	public final int DIRT = 1;
	public final int GRASS = 2;
	public final int STONE = 5;

	public final int TNT = 3;
	public final int JOBBY = 4;
	
	public int xGen;
	public int yGen;
	public int grid;
	
	public void setup(int screenWidth){		
		grid = 32;
		xGen = 1080 - 32 * 2;
		xGen = Math.round(screenWidth / grid) * grid; 
		yGen = 960 * 8;
		
		
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
				
				for(int j = 0; j < rects.length; j++){
					
				}
				
				finishedGen = true;
			}
			
		};t2.start();
		
	}
	
	public void breakBlock(Block block, Inventory inv, int delta){
		
	}
	
	public Color BlockColor(int type){
		Color c = null;
		 
		
		if(type == DIRT) c = new RRGGBB(87,59,12);
		if(type == GRASS)c = RRGGBB.green;
		if(type == AIR)c = RRGGBB.transparent;
		if(type == TNT)c = RRGGBB.red;
		if(type == JOBBY)c = new RRGGBB(89,48,1);
		if(type == STONE)c = new RRGGBB(105,105,105);
		
		return c;
	}
}
