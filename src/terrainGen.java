import org.newdawn.slick.Color;



 
public class terrainGen {
	public Block[] rects;
	
	//BLOCKS
	public final int AIR = 0;
	public final int DIRT = 1;
	public final int GRASS = 2;

	public final int TNT = 3;
	public final int JOBBY = 4;
	 
	
	public void setup(){
		rects = new Block[256];  // Tested this, can currently deal with 11,000 rects before it starts to slow down
									 //Then we need to redesign the coliision!
		int grid = 64;
		
		
		for(int i = 0; i < rects.length; i++){
			int type = (int) Math.round(Math.random() * JOBBY);
			rects[i] = new Block(Math.round(Math.random() * 1280 / grid) * grid, Math.round(Math.random() * 700 / grid) * grid + 190, 64, 64, type);
			for(int j = 0; j < i; j++){
				if(rects[i].getLocation() == rects[j].getLocation()){
					rects[i].setHeight(0);
					rects[i].setWidth(0);
				}
			}
		}
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
