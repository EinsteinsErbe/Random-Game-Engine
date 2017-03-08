package ch.magejo.randomgame;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.test.TextGeneratorDummy;
import ch.magejo.randomgame.mecanics.text.TextGeneratorInterface;
import ch.magejo.randomgame.screens.MainMenuScreen;
import ch.magejo.randomgame.screens.PlayScreen;
import ch.magejo.randomgame.screens.ScreenList;
import ch.magejo.randomgame.stateManager.StateManager;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class Main extends Game {
	private SpriteBatch batch;
	CombinedInputHandler cInputHandler;
	private InputHandler input;
	
	private InputMultiplexer inputHandler;
	
	private ScreenList activeState = ScreenList.MainMenu;
	
	private PlayScreen game;
	
	private int width, height;
	
	private TextGeneratorInterface textGenerator;
	
	private HashMap<ScreenList, Screen> screens;

	@Override
	public void create () {	
		screens = new HashMap<>();
		
		
		
		//set debugmod of log so we can see everything important
		Log.setDebugMode(6);
		
		textGenerator = new TextGeneratorDummy();
		
		inputHandler = new InputMultiplexer();

		cInputHandler = new CombinedInputHandler();
		inputHandler.addProcessor(cInputHandler);
		input = cInputHandler;
		Gdx.input.setInputProcessor(inputHandler);
		
		// new renderer and game stuff!
		batch = new SpriteBatch();
		
		screens.put(ScreenList.MainMenu, new MainMenuScreen(this));
		screens.put(ScreenList.Game, new PlayScreen(this));
		
		changeScreen(ScreenList.MainMenu);
				
	}

	@Override
	public void resize (int width, int height) {
		Log.printLn("resized Window to: " + width +":"+ height, getClass().getName(), 3);
		this.width = width;
		this.height = height;
		screens.get(activeState).resize(width, height);
	}

	private void update() {
		cInputHandler.update();
		
		for(Key k : Key.values()){
			if(input.isClicked(k)){
				Log.printLn("is clicked", k.name(), 3);
			}
		}
	}

	@Override
	public void render () {
		update();
		
		//new stuff
		super.render();
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
	
	public InputHandler getInput(){
		return input;
	}
	
	public InputMultiplexer getInputMultiplexer(){
		return inputHandler;
	}
	
	public TextGeneratorInterface getTextGenerator(){
		return textGenerator;
	}

	public Vector getScreenSize() {
		return new Vector(width, height);
	}
	
	public void changeScreen(ScreenList screen){
		activeState = screen;
		setScreen(screens.get(activeState));
	}
}
