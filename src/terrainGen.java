import org.newdawn.slick.Color;



 
public class terrainGen {
	public Block[] rects;
	public boolean finishedGen = false;
	
	//BLOCKS
	public final int AIR = 0;
	public final int DIRT = 1;
	public final int GRASS = 2;

	public final int TNT = 3;
	public final int JOBBY = 4;
	float[] x;
	float[] y;
	public void setup(){	
		x = new float[256];
		y = new float[256];
		
		rects = new Block[256];  
		
		
		
		Thread t2 = new Thread(){
			public void run(){
				int grid = 64;
				
				for(int i = 0; i < rects.length; i++){
					int type = (int) Math.round(Math.random() * JOBBY);
					boolean same = true;
			
					x[i] = Math.round(Math.random() * 1280 / grid) * grid;
					y[i] = Math.round(Math.random() * 700 / grid) * grid + 190;
			
					rects[i] = new Block(Math.round(Math.random() * 1280 / grid) * grid, Math.round(Math.random() * 700 / grid) * grid + 190, 64, 64, type);
			
			
					while(same){
						boolean isSame = false;
						for(int j = 0; j < i; j++){
							if(x[i] == x[j] && y[i] == y[j]){
								x[i] = x[i] + 64;
								isSame = true;
							}
						}
						same = isSame;
					}
					
					
			}
				finishedGen  = true;
			}
			
			
		};t2.start();
		
	}
	
	public Color BlockColor(int type){
		Color c = null;
		 
		
		if(type == DIRT) c = RRGGBB.gray;
		if(type == GRASS)c = RRGGBB.green;
		if(type == AIR)c = RRGGBB.transparent;
		if(type == TNT)c = RRGGBB.red;
		if(type == JOBBY)c = new RRGGBB(89,48,1);
		
		return c;
	}
}
