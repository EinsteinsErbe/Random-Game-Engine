package ch.magejo.randomgame;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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
	private String name = "Mordor";
	private Texture map, red;

	private int width, height;

	@Override
	public void create () {

		FileSystem.createRootFolder();
		new Generator().generate(name , 0);

		world = SaveSystem.load(FileSystem.getSaveFile(name, name));

		world.loadRegion(activeRegion);
		map = new Texture(new FileHandle(world.getMap()));

		red = new Texture("red.png");

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

		world.update(input);

		if(input.isClicked(Key.PAUSE)){
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

		batch.draw(map, width-1000, height-1000, 1000, 1000);

		world.render(renderer, new Vector(0, 0));

		if(!world.getActiveRegion().activeScenes.isEmpty()){
			int x = world.getActiveRegion().activeScenes.get(0).globalX*5+width-1000;
			int y = -world.getActiveRegion().activeScenes.get(0).globalY*5+height;
			batch.draw(red, x, y-5, 5, 5);
		}

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
