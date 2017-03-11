package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.gui.Minimap;
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

	private OrthographicCamera cam;
	private Matrix4 pm;

	private World world;
	private Vector origin;
	private Minimap minimap;

	private int SPEED = 10;

	private int width, height;

	private Charakter npc;			//Debug
	private Charakter player;		//The Players Charakter

	private Stage hud;				//Hud for Player

	public RunningGameScreen(Main game) {
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());

		pm = game.getBatch().getProjectionMatrix().cpy();

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

		minimap = new Minimap(name);
		updateOrigin();



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
		minimap.update(game.getInput());

		if(game.getInput().isPressed(Key.RIGHT)){
			cam.translate(SPEED, 0);
			cam.update();
		}
		if(game.getInput().isPressed(Key.LEFT)){
			cam.translate(-SPEED, 0);
			cam.update();
		}
		if(game.getInput().isPressed(Key.UP)){
			cam.translate(0, SPEED);
			cam.update();
		}
		if(game.getInput().isPressed(Key.DOWN)){
			cam.translate(0, -SPEED);
			cam.update();
		}

		if(game.getInput().isPressed(Key.ATTACK)){
			cam.zoom -= 0.1f;
			if(cam.zoom<0.1f){
				cam.zoom = 0.1f;
			}
			cam.update();
		}

		if(game.getInput().isPressed(Key.BLOCK)){
			cam.zoom += 0.1f;
			if(cam.zoom>10f){
				cam.zoom = 10f;
			}
			cam.update();
		}

		if(game.getInput().isClicked(Key.ENTER)){
			changeScreen(new DialogScreen(game, makeScreenshot(false), npc, player));
		}

		if(game.getInput().isClicked(Key.PAUSE)){
			changeScreen(new PausedGameScreen(game, makeScreenshot(true)));
		}

		if(cam.position.x + origin.x > 160){
			world.getActiveRegion().moveActiveScenes(1, 0);
			updateOrigin();
		}
		if(cam.position.x + origin.x < -160){
			world.getActiveRegion().moveActiveScenes(-1, 0);
			updateOrigin();
		}
		if(cam.position.y + origin.y > 160){
			world.getActiveRegion().moveActiveScenes(0, 1);
			updateOrigin();
		}
		if(cam.position.y + origin.y < -160){
			world.getActiveRegion().moveActiveScenes(0, -1);
			updateOrigin();
		}
	}

	private void updateOrigin() {
		origin.x = -world.getActiveRegion().getCentralScene().globalX*10-5;
		origin.y = -world.getActiveRegion().getCentralScene().globalY*10-5;
		origin.scale(32);

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

		game.getBatch().setProjectionMatrix(cam.combined);
		world.render(renderer, new Vector(0, 0));
		//world.render(renderer, origin);
		game.getBatch().setProjectionMatrix(pm);

		game.getBatch().end();
		game.getEventLogger().render(game.getBatch());
		hud.draw();
		minimap.render(game.getBatch());
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

		minimap.resize(width, height);

		cam = new OrthographicCamera(1000.0f, 1000.0f * height / width);
		//cam.position.set(cam.viewportWidth / 2.0f, cam.viewportHeight / 2.0f, 0.0f);
		cam.position.set(-origin.x, -origin.y, 0);
		cam.update();
		minimap.setPosition(cam.position);
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
