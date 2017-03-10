package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.gui.TextBox;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.entity.things.armor.BreastArmor;
import ch.magejo.randomgame.mecanics.entity.things.armor.Helmet;
import ch.magejo.randomgame.mecanics.entity.things.cloth.Sock;
import ch.magejo.randomgame.mecanics.entity.things.cloth.Type;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Spear;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Sword;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;
import ch.magejo.randomgame.world.RegionGenerator;

/**
 * Here the actual Fun happens, this is the raw game class which controls the PLayer and its interactions with
 * the game world, further the whole game gets drawn from here
 * @author M.Geissbberger
 *
 */
public class RunningGameScreen implements Screen{

	private Main game;				//Main class to get shared stuff
	private Renderer2D renderer;	//Renderer which can draw tiles
	private TileSet tileSet;		//a Tileset which holds all the possible 32x32px Tiles for the game
	private int tile, tile2;		//id's of already defined tiles

	private World world;
	private Texture map, regionMap, playerPos;
	private boolean renderRegions = false;
	private Vector origin;

	private int width, height;

	private Charakter npc;			//Debug
	private Charakter player;		//The Players Charakter

	private Stage hud;				//Hud for Player

	public RunningGameScreen(Main game) {
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());

		hud = new Stage();
		game.getInputMultiplexer().addProcessor(hud);

		origin = new Vector(0, 0);

		String name = "Mordor";
		FileSystem.createSubFolder(name);
		if(!FileSystem.getSaveFile(name, name).exists()){
			new Generator().generate(name, 0);
		}
		world = SaveSystem.load(FileSystem.getSaveFile(name, name));
		world.loadRegion(0);
		world.getActiveRegion().setCentralScene(0);
		world.getActiveRegion().moveActiveScenes(0, 0);
		origin.x = -world.getActiveRegion().getCentralScene().globalX*10+11;
		origin.y = -world.getActiveRegion().getCentralScene().globalY*10+11;
		origin.scale(32);
		map = new Texture(new FileHandle(FileSystem.getSaveFile(name, "map.png")));
		regionMap = new Texture(new FileHandle(FileSystem.getSaveFile(name, "regionMap.png")));
		Pixmap pixmap = new Pixmap( 8, 8, Format.RGBA8888 );
		pixmap.setColor( 1, 0, 0, 1 );
		pixmap.drawRectangle(0, 0, 8, 8);
		playerPos = new Texture( pixmap );


		//create npc which can be talked to and traded with
		npc = new Charakter("Npc", 100, 1);
		npc.addMoney(1000);
		Sword sword = new Sword("rosty Sword", 10, 1, 1, 10);
		sword.setAmount(10);
		npc.addToInventory(sword);

		//Player must be a Charakter, add inventory
		player = new Charakter("Saturn91", 100, 9999999);
		player.addMoney(10000);
		Sock sock = new Sock("fantastic sock", Type.Wool, 10, true, 1, 5);
		sock.setAmount(100);
		player.addToInventory(sock);

		//define Player armor
		Helmet h = new Helmet("shine helmet", ch.magejo.randomgame.mecanics.entity.things.armor.Type.Mithril, 1, 1, 1, 10);
		BreastArmor b = new BreastArmor("dull Breatsplate", ch.magejo.randomgame.mecanics.entity.things.armor.Type.Gambeson, 1, 5, 1, 15);
		player.addToInventory(h);
		player.equipArmor(h);
		player.addToInventory(b);
		player.equipArmor(b);
		Spear s = new Spear("simple Spear", 10, 1, 5, 2);
		player.addToInventory(s);
		player.equip(s);

		DialogManager.setTextGenerator(game.getTextGenerator());
	}

	/**
	 * update every single instance in the game which is currently loaded
	 * @param delta
	 */
	public void update(float delta){
		world.update(game.getInput());
		if(game.getInput().isClicked(Key.UP)){
			if(world.getActiveRegion().moveActiveScenes(0, 1)){
				origin.x = -world.getActiveRegion().getCentralScene().globalX*10+11;
				origin.y = -world.getActiveRegion().getCentralScene().globalY*10+11;
				origin.scale(32);
			}
		}
		if(game.getInput().isClicked(Key.DOWN)){
			if(world.getActiveRegion().moveActiveScenes(0, -1)){
				origin.x = -world.getActiveRegion().getCentralScene().globalX*10+11;
			origin.y = -world.getActiveRegion().getCentralScene().globalY*10+11;
			origin.scale(32);
			}
		}
		if(game.getInput().isClicked(Key.LEFT)){
			if(world.getActiveRegion().moveActiveScenes(-1, 0)){
				origin.x = -world.getActiveRegion().getCentralScene().globalX*10+11;
				origin.y = -world.getActiveRegion().getCentralScene().globalY*10+11;
				origin.scale(32);
			}
		}
		if(game.getInput().isClicked(Key.RIGHT)){
			if(world.getActiveRegion().moveActiveScenes(1, 0)){
				origin.x = -world.getActiveRegion().getCentralScene().globalX*10+11;
				origin.y = -world.getActiveRegion().getCentralScene().globalY*10+11;
				origin.scale(32);
			}
		}

		if(game.getInput().isClicked(Key.ESCAPE)){
			renderRegions = !renderRegions;
		}

		if(game.getInput().isClicked(Key.ENTER)){
			changeScreen(new DialogScreen(game, makeScreenshot(false), npc, player));
		}

		if(game.getInput().isClicked(Key.PAUSE)){
			changeScreen(new PausedGameScreen(game, makeScreenshot(true)));
		}
	}

	/**
	 * render all things which are currently loaden in the game (game is running)
	 * @param delta
	 */
	public void render(float delta){
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getBatch().begin();
		//draw game here
		world.render(renderer, origin);
		if(renderRegions){
			game.getBatch().draw(regionMap, width-500, height-500, 500, 500);
		}else{
			game.getBatch().draw(map, width-500, height-500, 500, 500);
		}

		game.getBatch().draw(playerPos, -origin.x/32/4+width-500, -origin.y/32/4+height-500);
		game.getBatch().end();
		game.getEventLogger().render(game.getBatch());
		hud.draw();
	}

	private void changeScreen(Screen screen){
		game.getInputMultiplexer().removeProcessor(hud);
		game.setScreen(screen);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	/**
	 * make a screenshot from the game
	 */
	public Texture makeScreenshot(boolean darkedOverlay){
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

		Pixmap screenShot = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, screenShot.getPixels(), pixels.length);
		if(darkedOverlay){
			Pixmap.setBlending(Blending.SourceOver);
			screenShot.setColor(new Color(0, 0, 0, 0.5f));
			screenShot.fillRectangle(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
			Pixmap.setBlending(Blending.None);
		}
		return new Texture(screenShot);
	}
}
