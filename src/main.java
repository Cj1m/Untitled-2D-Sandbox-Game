import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class main implements Game {
	private Char player;
	private Bee bee;
	private terrainGen terrain;
	private float playerX;
	private float playerY;
	private float beeX;
	private float beeY;
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
		terrain = new terrainGen();
		terrain.setup();
		player = new Char("Char", terrain.rects, 960, con.getScreenHeight() / 2 - 128);
		bee = new Bee("Bee", terrain.rects, 64, 64);
		Cosmos1 = new Image("src/Assets/Cosmos1.png");
		input = con.getInput();
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		Cosmos1.draw();
		
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
		
		for(int i = 0;i < terrain.rects.length; i++){ 
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
		
		for(int i = 0; i < player.playerInv.inv.length; i++){
			g.setColor(RRGGBB.white);
			g.drawString(player.playerInv.inv[i].type + " ",invX, 50);
			g.setColor(RRGGBB.black);
			invX+= 32;
		}
		
		//g.draw(player.playerBoundingRect);
		g.drawRect(player.playerBoundingRect.getX(), player.playerBoundingRect.getY() - player.mapY, player.playerBoundingRect.getWidth(), player.playerBoundingRect.getHeight());
		g.drawRect(bee.beeBoundingRect.getX(), bee.beeBoundingRect.getY() - player.mapY, bee.beeBoundingRect.getWidth(),bee.beeBoundingRect.getHeight());
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		this.input = input;

		playerX = player.x;
		playerY = player.y;
		beeX = bee.x;
		beeY = bee.y;
		
		bee.movement(input, delta, playerX, playerY, player.mapY);

		player.movement(input, delta, terrain.rects);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new main());
			app.setDisplayMode(1080, 960, false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
