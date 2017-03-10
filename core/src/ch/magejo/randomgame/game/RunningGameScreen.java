package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Blending;
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

	private Charakter npc;			//Debug
	private Charakter player;		//The Players Charakter

	private Stage hud;				//Hud for Player

	public RunningGameScreen(Main game) {
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());

		hud = new Stage();
		game.getInputMultiplexer().addProcessor(hud);

		tileSet = new TileSet("TileSet/TestTileSet.png", 32, 32);
		tile = tileSet.createTileAdress(0, 0);
		tile2 = tileSet.createTileAdress(1, 0);

		String name = "Mordor";
		FileSystem.createSubFolder(name);
		if(!FileSystem.getSaveFile(name, name).exists()){
			new Generator().generate(name, 0);
		}
		world = SaveSystem.load(FileSystem.getSaveFile(name, name));
		world.loadRegion(0);
		world.getActiveRegion().setCentralScene(0);

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
		/*
		for(int i = 0; i < 10 ; i++){
			for(int j = 0; j < 10; j++){
				renderer.renderTile(tile2, new Vector(i*tileSet.getTextureWidth(), j*tileSet.getTextureHeight()));
			}
		}
		*/
		world.render(renderer, new Vector(0,0));
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
