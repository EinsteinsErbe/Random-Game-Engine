package ch.magejo.randomgame;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.screens.PlayScreen;
import ch.magejo.randomgame.stateManager.StateList;
import ch.magejo.randomgame.stateManager.StateManager;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class Main extends Game {
	private SpriteBatch batch;
	CombinedInputHandler cInputHandler;
	private InputHandler input;
	
	private StateManager stateManager;
	
	private PlayScreen game;
	
	private int width, height;

	@Override
	public void create () {
		
		//set debugmod of log so we can see everything important
		Log.setDebugMode(6);

		stateManager = new StateManager();

		cInputHandler = new CombinedInputHandler();
		input = cInputHandler;
		Gdx.input.setInputProcessor(cInputHandler);
		
		// new renderer and game stuff!
		batch = new SpriteBatch();
		game = new PlayScreen(this);
		setScreen(game);
		
	}

	@Override
	public void resize (int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void update() {
		cInputHandler.update();
		
		for(Key k : Key.values()){
			if(input.isClicked(k)){
				Log.printLn("is clicked", k.name(),0);
			}
		}
		
		//new stuff
		game.update();
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
	
	public void beginBatch(){
		batch.begin();
	}
	
	public void endBatch(){
		batch.end();
	}
	
	public Vector getScreenSize(){
		return new Vector(width, height);
	}
	
	public InputHandler getInput(){
		return input;
	}
}
