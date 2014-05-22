import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Block extends Rectangle{
	public int type;
	
	public Block(float x, float y, float width, float height, int type) {
		super(x, y, width, height);
		this.type = type;
	}
}
