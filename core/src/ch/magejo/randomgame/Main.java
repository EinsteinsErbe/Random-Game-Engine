package ch.magejo.randomgame;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.async.AsyncTask;

import ch.magejo.randomgame.game.GameState;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.generator.text.TextGenerator;
import ch.magejo.randomgame.generator.world.buildings.HouseInteriorGenerator;
import ch.magejo.randomgame.gui.TextBox;
import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.sound.Sounds;
import ch.magejo.randomgame.mecanics.world.World;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.screens.GeneratorScreen;
import ch.magejo.randomgame.screens.LoadingScreen;
import ch.magejo.randomgame.screens.MainMenuScreen;
import ch.magejo.randomgame.screens.SavesScreen;
import ch.magejo.randomgame.screens.ScreenList;
import ch.magejo.randomgame.screens.SettingsScreen;
import ch.magejo.randomgame.sound.MusicType;
import ch.magejo.randomgame.sound.SoundManager;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.AbstractLog;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * This Class gets loaded from the game first, here the whole game is controlled
 * @author M.Geissbberger
 */
public class Main extends Game {
	private SpriteBatch batch;					//libgdx batch to draw on screen, shared by all screens
	CombinedInputHandler cInputHandler;			//input for Keyboard/Mouse/Controller
	private InputHandler input;

	private InputMultiplexer inputHandler;		//allows using multiple input sources

	private ScreenList activeState = ScreenList.MainMenu; //actual active Screen

	private int width, height;					//width and height of the game window

	private TextGenerator textGenerator;	//textgenerator which gets shared by the whole game
	private Generator generator;
	private HouseInteriorGenerator hiGenerator;
	private SoundManager soundManager;

	private static TextBox eventLogger;

	private static AbstractLog log;

	private GameState gameState;

	private World world;
	private Renderer2D renderer;

	/**
	 * Initialize project
	 */
	@Override
	public void create () {

		inputHandler = new InputMultiplexer();
		cInputHandler = new CombinedInputHandler();
		inputHandler.addProcessor(cInputHandler);
		input = cInputHandler;
		Gdx.input.setInputProcessor(inputHandler);
		Controllers.addListener(cInputHandler);

		eventLogger = new TextBox(new Vector(20, 0), 15 ,0.8f, false);

		log = new AbstractLog() {

			@Override
			public void printExternInfo(String info) {
				eventLogger.addTextLine(info, Color.YELLOW);
			}

			@Override
			public void printExternError(String error) {
				eventLogger.addTextLine(error, new Color(1, 0, 0, 1));
			}
		};

		//set debug log so every debug stuff is shown
		log.setDebugMode(1);

		// new renderer and game stuff!
		batch = new SpriteBatch();
		renderer = new Renderer2D(batch);

		changeViaLoadScreen(new LoadingScreen(this, ScreenList.MainMenu, false, new AsyncTask<Void>() {
			@Override
			public Void call() throws Exception {
				//set debugmod of log so we can see everything important
				Log.setDebugMode(6);

				FileSystem.createRootFolder("RandomGame");

				soundManager = new SoundManager();
				Sounds.setSoundPlayer(soundManager);
				
				//soundManager.playMusic(MusicType.MENU);

				generator = new Generator();
				hiGenerator = new HouseInteriorGenerator(generator.getEntityGenerator());
				textGenerator = new TextGenerator(0);
				try {
					textGenerator.addNameFile(Gdx.files.internal("Text/elven.txt").reader());
					textGenerator.loadTextFile(Gdx.files.internal("Text/dialog.xml").read());
				} catch (IOException e) {
					e.printStackTrace();
				}

				//Load standard settings
				File f = FileSystem.createFile("lang.cfg");
				if(f.exists()){
					textGenerator.setLanguage(SaveSystem.load(f));
				}

				logInfo("initialized Engine", getClass().getName(), 1);
				return null;
			}
		}));
	}

	/**
	 * gets called if one of the Platforms changes the Window size
	 */
	@Override
	public void resize (int width, int height) {
		Log.printLn("resized Window to: " + width +":"+ height, getClass().getName(), 3);
		this.width = width;
		this.height = height;
		//getScreen().resize(width, height);
	}

	/**
	 * updates the input
	 */
	private void update() {
		cInputHandler.update();

		for(Key k : Key.values()){
			if(input.isClicked(k)){
				logInfo("is clicked", k.name(), 3);
			}
		}
	}

	@Override
	public void dispose(){
		//TODO dispose all screens
		soundManager.dispose();
	}

	public static void fadeOutEventLogger(){
		for(int i = 0; i < 12; i++){
			eventLogger.multiplayColor(eventLogger.getLineNumber()-i-1, new Color(1, 1, 1, 0.75f));
		}
	}

	/**
	 * render active screen -> don't touch!
	 */
	@Override
	public void render () {
		update();
		super.render();
	}

	/**
	 * provides the libgdx batch which can draw to the screen
	 * @return
	 */
	public SpriteBatch getBatch(){
		return batch;
	}

	/**
	 * get the Keyboard and mouse input
	 * @return
	 */
	public InputHandler getInput(){
		return input;
	}

	/**
	 * get the input multiplexer to add or remove a scene from it
	 * @return
	 */
	public InputMultiplexer getInputMultiplexer(){
		return inputHandler;
	}

	/**
	 * get the combined input handler
	 * @return
	 */
	public CombinedInputHandler getInputManager(){
		return cInputHandler;
	}

	/**
	 * get the one textGenerator of the Game
	 * @return
	 */
	public TextGenerator getTextGenerator(){
		return textGenerator;
	}

	/**
	 * get actual screensize of the game (changes by resize) to change components in Screens manually
	 * @return
	 */
	public Vector getScreenSize() {
		return new Vector(width, height);
	}

	/**
	 * change the screen of the game, all possible screens are specified in ch.magejo.randomgame.screens.ScreenList
	 * @param screen
	 */
	public void changeScreen(ScreenList screen){
		logInfo("changed to screen: " + screen.toString(), getClass().getName(), 3);
		activeState = screen;
		switch(activeState){
		case MainMenu:
			setScreen(new MainMenuScreen(this));
			break;
		case Generator:
			setScreen(new GeneratorScreen(this));
			break;
		case Game:
			setGameState(new GameState(this));
			setScreen(gameState.getActiveScreen());
			break;
		case Settings:
			setScreen(new SettingsScreen(this));
			break;
		case Saves:
			setScreen(new SavesScreen(this));
			break;
		default:
			addEvent("Unknown gamestate: " + screen.toString(), new Color(1, 0, 0, 1));
		}
	}

	public void changeViaLoadScreen(Screen screen) {
		logInfo("changed to loading screen", getClass().getName(), 3);
		activeState = ScreenList.LoadWindow;
		setScreen(screen);
	}

	public TextBox getEventLogger(){
		return eventLogger;
	}

	public void addEvent(String text, Color color){
		fadeOutEventLogger();
		eventLogger.addTextLine(text, color);
	}

	public void logInfo(String msg, String className, int _debugMode){
		fadeOutEventLogger();
		log.printLn(msg, className, _debugMode);
	}

	public void logError(String error, String className, int _debugMode){
		fadeOutEventLogger();
		log.printErrorLn(error, className, _debugMode);
	}

	public void setGameState(GameState gameState){
		this.gameState = gameState;
	}

	public GameState getGameState(){
		return gameState;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(File file) {
		world = SaveSystem.load(file);
		SaveSystem.save(file, FileSystem.createFile("lastWorld.dat"));
	}

	public File getLastWorld() {
		File f = FileSystem.createFile("lastWorld.dat");
		if(f.exists()){
			return SaveSystem.load(f);
		}
		return null;
	}

	public Generator getGenerator() {
		return generator;
	}

	public HouseInteriorGenerator getHiGenerator() {
		return hiGenerator;
	}

	public Renderer2D getRenderer() {
		return renderer;
	}

	public SoundManager getSoundManager(){
		return soundManager;
	}
}
