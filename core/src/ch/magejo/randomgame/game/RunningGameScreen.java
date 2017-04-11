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
import ch.magejo.randomgame.generator.entities.EntityGenerator;
import ch.magejo.randomgame.generator.world.buildings.HouseInteriorGenerator;
import ch.magejo.randomgame.gui.Minimap;
import ch.magejo.randomgame.mecanics.entity.Entity;
import ch.magejo.randomgame.mecanics.entity.creatures.Creature;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Player;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.Direction;
import ch.magejo.randomgame.mecanics.places.House;
import ch.magejo.randomgame.mecanics.places.Region;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.Log;
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
	private Direction direction;

	private Charakter npc;			//Debug

	private Stage hud;				//Hud for Player

	private HouseInteriorGenerator houseGenerator;

	//tickline and deltaMS
	int deltaMS;
	long lastTime;
	int longestDelta;
	long lastTickLine;
	int tickCounter;

	private boolean speedmode;

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

		//to see entire region
		//Region.setRenderDimension(151, 91);
		Region.setRenderDimension(9, 7);

		/*String name = "Mittelerde";
		FileSystem.createSubFolder(name);
		if(!FileSystem.getSaveFile(name, name).exists()){
			new Generator().generate(name, 0);
		}
		game.setWorld(SaveSystem.load(FileSystem.getSaveFile(name, name)));*/

		world = game.getWorld();
		world.load();
		world.getActiveRegion().moveActiveScenes(0, 0);

		houseGenerator = new HouseInteriorGenerator();

		minimap = new Minimap(world.getName());
		minimap.setPosition(world.getWorldPos());

		//updatePos(world.getPlayer().getPositionFloat());

		game.addEvent(game.getTextGenerator().getName(world.getActiveRegion()), Color.GREEN);
		updateOrigin();

		//create npc which can be talked to and traded with
		EntityGenerator generator = new EntityGenerator((long) (Math.random()*10000), world.getStartScene());
		npc = generator.generateNextCharakter(generator.getLevelArround(20), true, world.getActiveRegion().getScenes().get(0));
		npc.addMoney(1000);
		npc.enableFreewalk();
		//Player must be a Charakter, add inventory

		DialogManager.setTextGenerator(game.getTextGenerator());

		lastTime = System.currentTimeMillis();
	}

	public void reSetInputFocusOnGame(){
		game.getInputMultiplexer().addProcessor(hud);
	}

	private void updatePos(Vector vector) {
		cam.position.x = (int)((vector.x+0.5f)*32);
		cam.position.y = (int)((vector.y+0.5f)*32);
		cam.update();
	}

	/**
	 * update every single instance in the game which is currently loaded
	 * @param delta
	 */
	public void update(float delta){
		calculateDeltaMS();	//must be first!

		direction = Direction.NON;

		//new speed mode (debug)-----
		speedmode = game.getInput().isPressed(Key.CTRL);

		if(game.getInput().isPressed(Key.RIGHT)){
			direction = Direction.EAST;
			if(speedmode){
				world.moveOnWorld(direction);
			}
		}
		if(game.getInput().isPressed(Key.LEFT)){
			direction = Direction.WEST;
			if(speedmode){
				world.moveOnWorld(direction);
			}
		}
		if(game.getInput().isPressed(Key.UP)){
			direction = Direction.NORTH;
			if(speedmode){
				world.moveOnWorld(direction);
			}
		}
		if(game.getInput().isPressed(Key.DOWN)){
			direction = Direction.SOUTH;
			if(speedmode){
				world.moveOnWorld(direction);
			}
		}

		if(!speedmode){
			if(!direction.equals(Direction.NON)){
				if(game.getInput().isClicked(Key.INTERACT)){
					Entity e = world.getPlayer().getObstacle(direction);
					if(e!=null){
						game.addEvent(e.toString(), Color.BLACK);
						if(e instanceof Creature){
							changeScreen(game.getGameState().openDialog((Creature) e, world.getPlayer()));
						}
					}
				}
				else{
					world.getPlayer().move(direction);
				}
			}
			else{
				if(game.getInput().isClicked(Key.INTERACT)){
					Entity e = world.getPlayer().getObstacle(Direction.NORTH);
					if(e!=null){
						game.addEvent(e.toString(), Color.BLACK);
						if(e instanceof House){
							House h = (House) e;
							if(h.canGoIn(world.getPlayer().getLocalPosition())){
								world.gotoInterior(houseGenerator.generateInterior(h, world));
							}
						}
					}
				}
			}
		}

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

		updatePos(world.getPlayer().getPositionFloat());

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

		world.update(game.getInput(), deltaMS);
		minimap.update(game.getInput());
		/*
		if(game.getInput().isClicked(Key.ENTER)){
			House h = world.goInHouse();
			if(h!=null){
				world.gotoInterior(houseGenerator.generateInterior(h, world));
			}
		}
		 */
		if(game.getInput().isClicked(Key.ESCAPE)){
			world.gotoOverworld();
		}

		if(game.getInput().isClicked(Key.PAUSE)){
			changeScreen(new PausedGameScreen(game, makeScreenshot(true)));
		}
	}

	private void calculateDeltaMS() {
		tickCounter ++;
		deltaMS = (int) (System.currentTimeMillis() - lastTime);
		if(deltaMS > longestDelta){
			longestDelta = deltaMS;
		}
		lastTime = System.currentTimeMillis();
		if(System.currentTimeMillis() - lastTickLine >= 1000){
			game.addEvent("Ticks:" + tickCounter + " longest Delta: " + longestDelta, Color.ORANGE);
			Log.printLn("Ticks:" + tickCounter + " longest Delta: " + longestDelta, getClass().getName(), 0);
			lastTickLine = System.currentTimeMillis();
			longestDelta = 0;
			tickCounter = 0;
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

		//----------Transparent Overlay------------
		game.getBatch().setColor(1f, 1f, 1f, 0.2f);
		world.getPlayer().render(renderer);
		game.getBatch().setColor(Color.WHITE);
		//-----------------------------------------

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

	}

	@Override
	public void resize(int width, int height) {
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
