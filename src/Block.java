import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Block extends Rectangle{
	public int type;
	public float breakTime;
	
	public Block(float x, float y, float width, float height, int type) {
		super(x, y, width, height);
		this.type = type;
		
		if(type == 0) breakTime = 0;
		if(type == 1) breakTime = 1 * 1000;
		if(type == 2) breakTime = 1 * 1000;
		if(type == 3) breakTime = 0.1f * 1000;
		if(type == 4) breakTime = 0.1f * 1000;
		if(type == 5) breakTime = 2 * 1000;
	}

	public void switchBlockColor(int type) {
		if(this.type != -1){
			this.type = -1;
		} else{
			this.type = type;
		}
	}
}
