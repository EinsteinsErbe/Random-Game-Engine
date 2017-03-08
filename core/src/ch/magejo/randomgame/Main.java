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
import ch.magejo.randomgame.screens.GeneratorScreen;
import ch.magejo.randomgame.screens.MainMenuScreen;
import ch.magejo.randomgame.screens.PlayScreen;
import ch.magejo.randomgame.screens.ScreenList;
import ch.magejo.randomgame.screens.SettingsScreen;
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

	@Override
	public void create () {			
		
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
		
		changeScreen(ScreenList.MainMenu);	
	}

	@Override
	public void resize (int width, int height) {
		Log.printLn("resized Window to: " + width +":"+ height, getClass().getName(), 3);
		this.width = width;
		this.height = height;
		getScreen().resize(width, height);
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
		Log.printLn("changed to screen: " + screen.toString(), getClass().getName(), 3);
		activeState = screen;
		if(activeState.equals(ScreenList.MainMenu)){
			setScreen(new MainMenuScreen(this));
		}
		if(activeState.equals(ScreenList.Generator)){
			setScreen(new GeneratorScreen(this));
		}
		if(activeState.equals(ScreenList.Game)){
			setScreen(new PlayScreen(this));
		}
		if(activeState.equals(ScreenList.Settings)){
			setScreen(new SettingsScreen(this));
		}		
	}
}
