package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.generator.entities.EntityGenerator;
import ch.magejo.randomgame.generator.world.buildings.HouseInteriorGenerator;
import ch.magejo.randomgame.gui.Minimap;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.entity.things.armor.BreastArmor;
import ch.magejo.randomgame.mecanics.entity.things.armor.Helmet;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Spear;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Sword;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.House;
import ch.magejo.randomgame.mecanics.places.Interior;
import ch.magejo.randomgame.mecanics.places.Place;
import ch.magejo.randomgame.mecanics.places.Region;
import ch.magejo.randomgame.mecanics.places.Scene;
import ch.magejo.randomgame.mecanics.places.Tree;
import ch.magejo.randomgame.mecanics.places.Village;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Here the actual Fun happens, this is the raw game class which controls the PLayer and its interactions with
 * the game world, further the whole game gets drawn from here
 * @author M.Geissbberger
 *
 */
public class RunningGameScreen implements Screen{

	private Main game;				//Main class to get shared stuff
	private Renderer2D renderer;	//Renderer which can draw tiles

	private FrameBuffer fbo;

	private OrthographicCamera cam;
	private Matrix4 pm;

	private World world;
	private Vector origin;
	private Minimap minimap;

	private int SPEED = 1;

	private int width, height;

	private Charakter npc;			//Debug
	private Charakter player;		//The Players Charakter

	private Stage hud;				//Hud for Player

	private HouseInteriorGenerator houseGenerator;

	public RunningGameScreen(Main game) {
		//----------engine Stuff-------------
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());
		pm = game.getBatch().getProjectionMatrix().cpy();
		hud = new Stage();
		game.getInputMultiplexer().addProcessor(hud);
		//----------/engine Stuff--------------

		origin = new Vector(0, 0);
		cam = new OrthographicCamera(1000, 1000);

		//NameGeneratorTest
		//NameGenerator ng = new NameGenerator();
		try {
			//ng.change(Gdx.files.internal("Text/elven.txt").reader());
			game.addEvent("text", Color.BLUE);
		} catch (Exception e) {

		}

		String name = "Mittelerde";
		FileSystem.createSubFolder(name);
		if(!FileSystem.getSaveFile(name, name).exists()){
			new Generator().generate(name, 0);
		}
		world = SaveSystem.load(FileSystem.getSaveFile(name, name));
		if(world.getActiveRegion() == null){
			world.loadRegion(0);
			world.getActiveRegion().setCentralScene(0);

			//TODO do this in world object
			world.getWorldPos().x = world.getActiveRegion().getCentralScene().globalX*10-5;
			world.getWorldPos().y = world.getActiveRegion().getCentralScene().globalY*10-5;
		}

		//to see entire region
		//world.getActiveRegion().loadWidth = 151;
		//world.getActiveRegion().loadHeight = 91;
		world.getActiveRegion().loadWidth = 9;
		world.getActiveRegion().loadHeight = 7;

		world.getActiveRegion().moveActiveScenes(0, 0);

		updatePos(world.getActivePos());

		houseGenerator = new HouseInteriorGenerator();

		minimap = new Minimap(name);
		minimap.setPosition(world.getWorldPos());

		game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
		updateOrigin();
		cam.position.set(-origin.x, -origin.y, 0);



		//create npc which can be talked to and traded with
		EntityGenerator generator = new EntityGenerator((long) (Math.random()*10000));
		npc = generator.generateNextCharakter(generator.getLevelArround(20), true);
		npc.addMoney(1000);
		npc.setPosition(new Vector(10, 10));

		//Player must be a Charakter, add inventory
		player = generator.generateNextCharakter(generator.getLevelArround(15), true);
		player.addMoney(10000);
		player.setPosition(new Vector(0, 0));

		DialogManager.setTextGenerator(game.getTextGenerator());
	}

	public void reSetInputFocusOnGame(){
		game.getInputMultiplexer().addProcessor(hud);
	}

	private void updatePos(Vector pos) {
		cam.position.x = pos.x*32;
		cam.position.y = pos.y*32;
		cam.update();
	}

	/**
	 * update every single instance in the game which is currently loaded
	 * @param delta
	 */
	public void update(float delta){
		
		player.update((int)delta); 
		npc.update((int) delta);
		
		//remove into player which is in world
		player.setPosition(world.getActivePos());

		//new speed modus (debug)-----
		if(game.getInput().isPressed(Key.RIGHT)){
			world.movePlayer(SPEED, 0);
		}
		if(game.getInput().isPressed(Key.LEFT)){
			world.movePlayer(-SPEED, 0);
		}
		if(game.getInput().isPressed(Key.UP)){
			world.movePlayer(0, SPEED);
		}
		if(game.getInput().isPressed(Key.DOWN)){
			world.movePlayer(0, -SPEED);
		}
		//---------------------------------------

		if(game.getInput().isPressed(Key.ATTACK)){
			cam.zoom -= 0.1f;
			if(cam.zoom<0.1f){
				cam.zoom = 0.1f;
			}
		}

		if(game.getInput().isPressed(Key.BLOCK)){
			cam.zoom += 0.1f;
			if(cam.zoom>50f){
				cam.zoom = 50f;
			}
		}

		updatePos(world.getActivePos());

		if(cam.position.x + origin.x >= 160){
			if(world.moveActiveScenes(1, 0)){
				game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
			}
			updateOrigin();
		}
		if(cam.position.x + origin.x < -160){
			if(world.moveActiveScenes(-1, 0)){
				game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
			}
			updateOrigin();
		}
		if(cam.position.y + origin.y >= 160){
			if(world.moveActiveScenes(0, 1)){
				game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
			}
			updateOrigin();
		}
		if(cam.position.y + origin.y < -160){
			if(world.moveActiveScenes(0, -1)){
				game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
			}
			updateOrigin();
		}

		world.update(game.getInput());
		minimap.update(game.getInput());



		if(game.getInput().isClicked(Key.ENTER)){
			House h = world.goInHouse();
			if(h!=null){
				world.gotoInterior(houseGenerator.generateInterior(h));
			}
		}

		if(game.getInput().isClicked(Key.ESCAPE)){
			world.gotoOverworld();
		}

		if(game.getInput().isClicked(Key.PAUSE)){
			world.save();
			changeScreen(new PausedGameScreen(game, makeScreenshot(true)));
		}

		if(game.getInput().isClicked(Key.INTERACT)){
			changeScreen(game.getGameState().openDialog(npc, player));
		}
	}

	private void updateOrigin() {
		origin.x = -world.getActiveRegion().getCentralScene().globalX*10-5;
		origin.y = -world.getActiveRegion().getCentralScene().globalY*10-5;
		origin.scale(32);

		game.addEvent(game.getTextGenerator().getName(world.getActiveRegion().getCentralScene(), world.getActiveRegion()), Color.GREEN);
	}

	/**
	 * render all things which are currently loaded in the game (game is running)
	 * @param delta
	 */
	public void render(float delta){
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderGame();
		renderHud();
	}

	private void renderGame(){
		//game.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		game.getBatch().enableBlending();
		game.getBatch().begin();
		//draw game here

		game.getBatch().setProjectionMatrix(cam.combined);
		world.render(renderer);
		player.render(renderer);
		npc.render(renderer);
		//world.render(renderer, origin);
		game.getBatch().setProjectionMatrix(pm);
		game.getBatch().end();
		minimap.render(game.getBatch());
	}

	private void renderHud() {
		game.getEventLogger().render(game.getBatch());
		hud.draw();
	}

	private void changeScreen(Screen screen){
		game.getInputMultiplexer().removeProcessor(hud);
		game.getGameState().changeActiveGameScreen(screen);
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

		cam.viewportWidth = 1000.0f;
		cam.viewportHeight = 1000.0f * height / width;

		cam.update();

		fbo = new FrameBuffer(Format.RGBA8888, width, height, false);
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
		fbo.dispose();
	}

	/**
	 * make a screenshot from the game
	 */
	public TextureRegion makeScreenshot(boolean darkedOverlay){
		fbo.begin();
		if(darkedOverlay){
			game.getBatch().setColor(0.3f, 0.3f, 0.3f, 1);
		}
		renderGame();
		game.getBatch().setColor(Color.WHITE);
		fbo.end();

		TextureRegion fbotr = new TextureRegion(fbo.getColorBufferTexture());
		fbotr.flip(false, true);

		return fbotr;
	}
}
