import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Char {

	public String name;
	public float x;
	public float y;
	public float mapY;
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
	
	private int screenHeight;
	private int delta;
	private int timer;
	
	public Char(String name, Block[] rects, float x, float y, int screenHeight) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.screenHeight = screenHeight;
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
		mapY = -336;
	}
	
	public void falling() {
		if(!newIsBlocked(x, y + velY + 1f + 23)){
			timer += delta;
			
			if(velY < 7){
				if(timer > 1 * 50){
					velY = velY += 1f;
					timer = 0;
				}
			}
			mapY += velY;
			isOnGround = false;
		}else{
			velY = 0;	
			isOnGround = true;
		}
	}

	public void movement(Input input, int delta, Block[] map) {
		this.map = map;
		
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
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && !newIsBlocked(x, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
		}
		
		
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYD)) && !newIsBlocked(x + 5 + 20, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x+= 5;
		}else if (input.isKeyDown(KEYD)) {
			if(!newIsBlocked(x + 5 + 20, y)){
				x += 5;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!newIsBlocked(x + 4 + 20, y)){
				x += 4;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!newIsBlocked(x + 3 + 20, y)){
				x += 3;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!newIsBlocked(x + 2 + 20, y)){
				x += 2;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!newIsBlocked(x + 1 + 20, y)){
				x += 1;

				isMovingRight = true;
				isMovingLeft = false;
			}
			
		}else if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYA)) && !newIsBlocked(x - 5 - 9, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x-= 5;
		}else if (input.isKeyDown(KEYA)) {
			if(!newIsBlocked(x - 5 - 9, y)){
				x -= 5;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!newIsBlocked(x - 4- 9, y)){
				x -= 4;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!newIsBlocked(x - 3- 9, y)){
				x -= 3;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!newIsBlocked(x - 2- 9, y)){
				x -= 2;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!newIsBlocked(x - 1- 9, y)){
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

        playerHitBox.setLocation(x + 8,y + mapY);
        
        int startY = 64 * Math.round(mapY / 64) - 64;
		int endY = 64 * Math.round((mapY + screenHeight) / 64) - 64;
        
		for(int i = 0; i < map.length; i++){
			if(map[i].getY() > startY && map[i].getY() < endY){
				if(playerHitBox.intersects(map[i])){
					if(map[i].type != 0){
						playerInv.addItem(map[i].type);
					
						map[i].type = 0;
					}
				}
			}
		}
    }
	
	private boolean newIsBlocked(float x, float y) {
        boolean blocked = false;
        
        
        
        int xGen = 1080;
		int yGen = 960 * 8;
		int grid =32;
        
		float tweakedX = x;
        float tweakedY = y + mapY - grid;
		
       
        
        System.out.println((Math.round((tweakedX + playerBoundingRect.getWidth()) / map[1].getWidth()) - 1) * ((yGen / grid)) + Math.round((tweakedY + playerBoundingRect.getHeight()) / map[1].getWidth()));
        
        if(map[(int) ((Math.round((tweakedX + playerBoundingRect.getWidth()) / grid) - 1) * ((yGen / grid)) + Math.round((tweakedY + playerBoundingRect.getHeight()) / map[1].getWidth()))].type != 0){
        	blocked = true;
        }
        if(map[(int) ((Math.round((tweakedX + playerBoundingRect.getWidth()) / grid) - 1) * ((yGen / grid)) + Math.round((tweakedY + playerBoundingRect.getHeight() / 2) / map[1].getWidth()))].type != 0){
        	blocked = true;
        }
        return blocked;
    }
	
	private boolean isBlocked(float x, float y) {
        boolean blocked = false;
        
        float tweakedX = x + 4;
        float tweakedY = y + 8 + mapY;
        
        int startY = 64 * Math.round(mapY / 64) - 64;
		int endY = 64 * Math.round((mapY + screenHeight) / 64) - 64;
        
        playerBoundingRect.setLocation(tweakedX,tweakedY);
		for(int i = 0; i < map.length; i++){
			
			if(map[i].getY() > startY && map[i].getY() < endY){
				if(playerBoundingRect.intersects(map[i])){
					if(map[i].type != 0){
						blocked = true;
					}
				}
			}
			
		}
		return blocked;
    }
}