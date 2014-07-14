import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Bee {

	public String beename;
	public float x;
	public float y;
	public boolean isMovingLeft;
	public boolean isMovingRight;
	public boolean isOnGround;
	public SpriteSheet bsprite;
	public SpriteSheet bsprite2;
	public Animation beeRight;
	public Animation beeLeft;
	
	public Rectangle beeBoundingRect;
	public Rectangle beeHitBox;
	public Block[] map;
	
	private float playerMapY;
	private int delta;
	private int timer;
	
	public Bee(String name, Block[] rects, float x, float y) {
		this.beename = name;
		this.x = x;
		this.y = y;
		map = rects;
		
		try {
			bsprite = new SpriteSheet("src/Assets/beesheet.png", 128, 128);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		try {
			bsprite2 = new SpriteSheet("src/Assets/beesheet2.png", 128, 128);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		beeRight = new Animation(bsprite, 100);
		beeLeft = new Animation(bsprite2, 100);
		
		beeBoundingRect = new Rectangle(x,y, 128, 128);
		beeHitBox = new Rectangle(x,y,128,128);

	}
	
	

	public void movement(Input input, int delta, float playerX, float playerY, float mapY) {

		this.delta = delta;
		this.playerMapY = mapY;
		
		//MOVEMENT

		
		if(playerY + playerMapY < y && !isBlocked(x, y - 2)){
			y -= 2;
		}
		
		if(playerY + playerMapY > y && !isBlocked(x, y + 2)){
			y += 2;
		}
		
		
		if (playerX > x) {
			if(!isBlocked(x + 5, y)){
				x += 5;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 4, y)){
				x += 4;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 3, y)){
				x += 3;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 2, y)){
				x += 2;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 1, y)){
				x += 1;

				isMovingRight = true;
				isMovingLeft = false;
			}
		}else if (playerX < x) {
			if(!isBlocked(x - 5, y)){
				x -= 5;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 4, y)){
				x -= 4;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 3, y)){
				x -= 3;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 2, y)){
				x -= 2;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 1, y)){
				x -= 1;

				isMovingRight = false;
				isMovingLeft = true;
			}
		}else{
			isMovingRight = false;
			isMovingLeft = false;
		}
	
				
		
		}
		
	
	
	
	private void destroy(float x, float y) {

        beeHitBox.setLocation(x + 8,y);
		for(int i = 0; i < map.length; i++){
			if(beeHitBox.intersects(map[i])){
				if(map[i].type != 0){
					map[i].type = 0;
				}
			}
		}
    }
	
	private boolean isBlocked(float x, float y) {
        boolean blocked = false;
        
        float tweakedX = x + 4;
        float tweakedY = y + 8;
        
        beeBoundingRect.setLocation(tweakedX,tweakedY);
		for(int i = 0; i < map.length; i++){
			if(beeBoundingRect.intersects(map[i])){
				if(map[i].type != 0){
					blocked = true;
				}
			}
		}
		return blocked;
    }
}
