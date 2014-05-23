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
	
	public void setup(){		
		rects = new Block[255];  
		
		
		
		Thread t2 = new Thread(){
			public void run(){
				int grid = 64;
				
				int i = 0;
				for(int x = 0; x < 1080; x += grid){
					if(i >= rects.length) break;
					for(int y = 0; y < 960; y += grid){
						if(y <= 128 + 64){
							rects[i] = new Block(x,y,64,64,AIR);
							i++;
							if(i >= rects.length) break;
						}else if(y == 256){
							int randAir = 1 + (int)(Math.random() * ((10 - 1) + 1));
							if(randAir == 1){
								rects[i] = new Block(x,y,64,64,AIR);
							}else{
								rects[i] = new Block(x,y,64,64,GRASS);
							}
							
							i++;
						}else if(y >= 320){
							int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
							if(rand == 1)rects[i] = new Block(x,y,64,64,DIRT);
							if(rand == 2)rects[i] = new Block(x,y,64,64,STONE);
							if(rand == 3)rects[i] = new Block(x,y,64,64,AIR);
							i++;
						}else{
							int randBlock = (int) Math.round(Math.random() * JOBBY);
							rects[i] = new Block(x,y,64,64,randBlock);
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
