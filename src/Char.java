import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Char {

	public String name;
	public float x;
	public float y;
	public boolean isMovingLeft;
	public boolean isMovingRight;
	public boolean isOnGround;
	public SpriteSheet ss;
	public SpriteSheet ss2;
	public Animation walkingRightAnimation;
	public Animation walkingLeftAnimation;
	
	public Rectangle playerBoundingRect;
	public Rectangle playerHitBox;
	public Block[] map;
	public Inventory playerInv;
	
	private float velY;
	private int delta;
	private int timer;
	
	public Char(String name, Block[] rects, float x, float y) {
		this.name = name;
		this.x = x;
		this.y = y;
		map = rects;
		
		try {
			ss = new SpriteSheet("src/Assets/HumanWalkCaucasian.png", 32, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		try {
			ss2 = new SpriteSheet("src/Assets/HumanWalkCaucasianFlip.png", 32, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		walkingRightAnimation = new Animation(ss, 100);
		walkingLeftAnimation = new Animation(ss2, 100);
		
		playerBoundingRect = new Rectangle(x,y, 28, 56);
		playerHitBox = new Rectangle(x,y,16,16);
		
		playerInv = new Inventory();

	}
	
	public void falling() {
		if(!isBlocked(x, y + velY + 1f)){
			timer += delta;
			
			if(velY < 7){
				if(timer > 1 * 50){
					velY = velY += 1f;
					timer = 0;
				}
			}
			y = y + velY;
			isOnGround = false;
		}else{
			velY = 0;	
			isOnGround = true;
		}
	}

	public void movement(Input input, int delta) {
		int KEYD = Input.KEY_D;
		int KEYRIGHT = Input.KEY_RIGHT;

		int KEYA = Input.KEY_A;
		int KEYLEFT = Input.KEY_LEFT;
		
		int KEYSPACE = Input.KEY_SPACE;
		int KEYW = Input.KEY_W;
		int KEYUP = Input.KEY_UP;
		
		int KEYDOWN = Input.KEY_DOWN;
		
		int jumpHeight = -8;
	
		this.delta = delta;
		
		//MOVEMENT
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && !isBlocked(x, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
		}
		
		
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYD)) && !isBlocked(x + 5, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x+= 5;
		}else if (input.isKeyDown(KEYD)) {
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
			
		}else if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYA)) && !isBlocked(x - 5, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x-= 5;
		}else if (input.isKeyDown(KEYA)) {
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
		
		//SANDBOX
		
		if(input.isKeyDown(KEYUP)){
			destroy(x,y - 32);
		}
		if(input.isKeyDown(KEYDOWN)){
			destroy(x,y + 64);
		}
		if(input.isKeyDown(KEYLEFT)){
			destroy(x - 32,y + 32);
		}
		if(input.isKeyDown(KEYRIGHT)){
			destroy(x + 32,y + 32);
		}
		
		falling(); 
		playerInv.printInventory();
	}
	
	
	
	private void destroy(float x, float y) {

        playerHitBox.setLocation(x + 8,y);
		for(int i = 0; i < map.length; i++){
			if(playerHitBox.intersects(map[i])){
				if(map[i].type != 0){
					playerInv.addItem(map[i].type);
					
					map[i].type = 0;
				}
			}
		}
    }
	
	private boolean isBlocked(float x, float y) {
        boolean blocked = false;
        
        float tweakedX = x + 4;
        float tweakedY = y + 8;
        
        playerBoundingRect.setLocation(tweakedX,tweakedY);
		for(int i = 0; i < map.length; i++){
			if(playerBoundingRect.intersects(map[i])){
				if(map[i].type != 0){
					blocked = true;
				}
			}
		}
		return blocked;
    }
}
