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
	public void init(GameContainer arg0) throws SlickException {
		terrain = new terrainGen();
		terrain.setup();
		player = new Char("Char", terrain.rects, 960, 64);
		Cosmos1 = new Image("src/Assets/Cosmos1.png");
		input = arg0.getInput();
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
		
		
		for(Block i : terrain.rects){ 
			g.setColor(terrain.BlockColor(i.type));
			g.fill(i);
			g.setColor(RRGGBB.black);
		}
		
		g.draw(player.playerBoundingRect);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		this.input = input;

		playerX = player.x;
		playerY = player.y;

		player.movement(input, delta);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new main());
			app.setDisplayMode(1280, 960, false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
