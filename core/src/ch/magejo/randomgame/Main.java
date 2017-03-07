package ch.magejo.randomgame;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.Region;
import ch.magejo.randomgame.mecanics.places.Scene;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.stateManager.StateList;
import ch.magejo.randomgame.stateManager.StateManager;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private World world;
	private Renderer2D renderer;
	CombinedInputHandler cInputHandler;
	private InputHandler input;
	private int nRegions = 10;
	private int activeRegion = 0;
	private String name = "MyWorld";
	private Texture map;
	
	private int width, height;
	
	private StateManager stateManager;

	@Override
	public void create () {
		
		//set debugmod of log so we can see everything important
		Log.setDebugMode(6);

		stateManager = new StateManager();
		
		new Generator().generate(name , nRegions);

		world = SaveSystem.load(FileSystem.getSaveFile(name, name));

		world.loadRegion(activeRegion);
		map = new Texture(new FileHandle(world.getMap()));

		batch = new SpriteBatch();
		renderer = new Renderer2D(batch);

		cInputHandler = new CombinedInputHandler();
		input = cInputHandler;
		Gdx.input.setInputProcessor(cInputHandler);
	}

	@Override
	public void resize (int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void update() {
		cInputHandler.update();
		stateManager.update(Gdx.graphics.getDeltaTime());
		
		if(input.isClicked(Key.ENTER)){
			StateManager.changeState(StateList.GAME);
		}
		
		if(input.isClicked(Key.ESCAPE)){
			StateManager.changeState(StateList.MAINMEU);
		}
		
		if(input.isClicked(Key.ENTER)){
			activeRegion++;
			if(activeRegion>=nRegions){
				activeRegion = 0;
			}
			world.loadRegion(activeRegion);
		}
		for(Key k : Key.values()){
			if(input.isClicked(k)){
				Log.printLn("is clicked", k.name(),0);
			}
		}
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		stateManager.render(renderer);
		batch.draw(map, width-200, height-200, 200, 200);
		world.render(renderer, new Vector(0, 0));
		
		batch.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}
}
