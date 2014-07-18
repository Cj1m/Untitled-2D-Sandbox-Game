import java.awt.Font;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class main extends BasicGameState implements Game{
	private Char player;
	private Bee bee;
	public terrainGen terrain;
	private float playerX;
	private float playerY;
	private float beeX;
	private float beeY;
	private int screenHeight;
	private int blockDropTimer;
	private int blockDropTime;
	public int selectedInv;
	
	private Font titleFont;
	private TrueTypeFont titleTtf;
	private Font deathNoteFont;
	private TrueTypeFont deathTtf;
	private Rectangle restart;
	Input input;
	Image Cosmos1 = null;
	
	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		return "Java Blocks Game";
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		screenHeight = con.getHeight();
		terrain = new terrainGen();
		terrain.setup(con.getWidth());
		player = new Char("Char", this, 960, con.getScreenHeight() / 2 - 256, screenHeight);
		bee = new Bee("Bee", terrain.rects, 64, 64);
		Cosmos1 = new Image("src/Assets/Cosmos1.png");
		input = con.getInput();
		
		titleFont = new Font("Serif", Font.BOLD, 46);
		titleTtf = new TrueTypeFont(titleFont, true);
		deathNoteFont = new Font("Serif", Font.ITALIC, 22);
		deathTtf = new TrueTypeFont(deathNoteFont, true);
		restart = new Rectangle(con.getWidth() / 2 - 256 / 2, con.getHeight() / 2 - 128 + 200, 256, 128);
		blockDropTimer = 0;
		blockDropTime = 30 * 1000;
		
		selectedInv = 0;
	}


	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		Cosmos1.draw();
		if(!player.isDead){
			if (player.isMovingRight) {
				player.walkingRightAnimation.draw(playerX, playerY);
			} else if (player.isMovingLeft) {
				player.walkingLeftAnimation.draw(playerX, playerY);
			} else {
				player.ss.getSprite(0, 0).draw(playerX, playerY);
			}
			
			if (bee.isMovingRight) {
				bee.beeRight.draw(beeX, beeY - player.mapY);
			} else if (bee.isMovingLeft) {
				bee.beeLeft.draw(beeX, beeY- player.mapY);
			} else {
				bee.beeRight.draw(beeX,beeY- player.mapY);
			}
			
			int blockSize = terrain.grid;
			int rowCount = terrain.xGen / blockSize;

			int startY =  (Math.round(player.mapY / blockSize))* (rowCount);          
			int endY = (Math.round((player.mapY + screenHeight) / blockSize))* (rowCount);
			
			if(startY < 0) startY = 0;
			if(endY > terrain.rects.length) endY = terrain.rects.length;
			
	        
			
			
			for(int i = startY;i < endY; i++){
				Rectangle rect = new Rectangle(terrain.rects[i].getX(),terrain.rects[i].getY(),terrain.rects[i].getWidth(),terrain.rects[i].getHeight());
				g.setColor(terrain.BlockColor(terrain.rects[i].type));
				g.fillRect(rect.getX(), rect.getY() - player.mapY, rect.getWidth(), rect.getHeight());
				g.setColor(RRGGBB.black);
				//g.drawString("" + i, terrain.rects[i].getX(), terrain.rects[i].getY() - player.mapY);
			}
			
			int invX = 8;
			g.setColor(RRGGBB.white);
			g.drawString("INVENTORY", 8, 30);
			g.setColor(RRGGBB.black);
			
			for(int i = 0; i < 16; i++){
				player.playerInv.inv[i].setLocation(invX , 50);	
				g.setColor(RRGGBB.white);
				g.setLineWidth(4);
				if(selectedInv == i){ /*yoda*/ 
					g.setColor(RRGGBB.magenta); 
					g.setLineWidth(10);
				}
				
				g.draw(player.playerInv.inv[i]);
					
				if(player.playerInv.inv[i].type == 0){
					g.setColor(RRGGBB.white);
				}else{
					g.setColor(terrain.BlockColor(player.playerInv.inv[i].type));
				}
					
				g.fill(player.playerInv.inv[i]);
					
				g.setLineWidth(1);
				g.setColor(Color.black);
				invX+= 8 + 16;
			}
			
			//g.draw(player.playerBoundingRect); needs to be bit-level not a rectangle!!!
			//g.drawRect(player.playerBoundingRect.getX(), player.playerBoundingRect.getY() - player.mapY, player.playerBoundingRect.getWidth(), player.playerBoundingRect.getHeight());
			g.drawRect(bee.beeBoundingRect.getX(), bee.beeBoundingRect.getY() - player.mapY, bee.beeBoundingRect.getWidth(),bee.beeBoundingRect.getHeight());
		}else{
			g.setColor(RRGGBB.white);
			
			titleTtf.drawString(con.getWidth() / 2 - titleTtf.getWidth("GAME OVER") / 2, con.getHeight() / 2 - titleTtf.getHeight(), "GAME OVER");
			deathTtf.drawString(con.getWidth() / 2 - deathTtf.getWidth("'"+ player.causeOfDeath +"'") / 2, con.getHeight() / 2 + 40 - deathTtf.getHeight(), "'"+ player.causeOfDeath +"'");
			g.setLineWidth(5);
			g.setColor(RRGGBB.white);
			g.draw(restart);
			g.setLineWidth(1);
			g.setColor(RRGGBB.green);
			g.fill(restart);
			titleTtf.drawString(con.getWidth() / 2 - titleTtf.getWidth("Restart") / 2, con.getHeight() / 2 - titleTtf.getHeight() + 160, "Restart");
		}
	}


	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		this.input = input;
		if(!player.isDead){
			playerX = player.x;
			playerY = player.y;
			beeX = bee.x;
			beeY = bee.y;
			
			if(input.isKeyDown(input.KEY_Q)){
					player.playerInv.removeItem(selectedInv);
			}
			
			bee.movement(input, delta, playerX, playerY, player.mapY);
			player.movement(input, delta, terrain.rects);
			
			blockDropTimer += delta;
			if(blockDropTimer > blockDropTime){
				terrain.removeOutside();
				if(player.mapY > terrain.yGen / 2)blockDropTime = 5 * 1000; 
				if(player.mapY > terrain.yGen / 1.5)blockDropTime = 1 * 1000;
				blockDropTimer = 0;
				
				
			}
		}else{
			if(input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
				Rectangle rect = new Rectangle(input.getMouseX(), input.getMouseY(), 1,1);
				if(rect.intersects(restart)){
					container.reinit();
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new main());
			app.setDisplayMode(1280, 768, false); //was 960 and 1080
			app.setTargetFrameRate(60);
			app.setFullscreen(false);;
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	
	
	@Override 
	public void mouseWheelMoved(int change){
		selectedInv += change / 120;
		if(selectedInv > player.playerInv.inv.length - 1) selectedInv = 0;
		if(selectedInv < 0) selectedInv = player.playerInv.inv.length -1;
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
