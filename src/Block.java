import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Block extends Rectangle{
	public int type; 
	public float breakTime;
	
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
	public final int INFINITESTONE = 13;
	public final int INDESTRUCTUBLOCK = 14;
	
	public Block(float x, float y, float width, float height, int type) {
		super(x, y, width, height);
		this.type = type;
		
		if(type == AIR) breakTime = 0;
		if(type == DIRT) breakTime = 1 * 1000;
		if(type == GRASS) breakTime = 1 * 1000;
		if(type == TNT) breakTime = 0.1f * 1000;
		if(type == JOBBY) breakTime = 0.1f * 1000;
		if(type == STONE) breakTime = 2 * 1000;
		if(type == COAL) breakTime = 2.1f * 1000;
		if(type == BRONZE) breakTime = 2.8f * 1000;
		if(type == SILVER) breakTime = 3.5f * 1000;
		if(type == GOLD) breakTime = 4.2f * 1000;
		if(type == MOONSTONE) breakTime = 3f * 1000;
		if(type == AMETHYST) breakTime = 3.5f * 1000;
		if(type == ALUMINIUM) breakTime = 4f * 1000;
		
		if(type == INFINITESTONE) breakTime = 10f * 1000;
	}

	public void switchBlockColor(int type) {
		if(this.type != -1){
			this.type = -1;
		} else{
			this.type = type;
		}
	}
}
