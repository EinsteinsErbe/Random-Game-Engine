package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class PlayScreen implements Screen {
	
	private World world;
	private Renderer2D renderer;
	private int nRegions = 10;
	private int activeRegion = 0;
	private String name = "MyWorld";
	private Texture map;
	
	private Main game;
	
	public PlayScreen(Main game) {
		this.game = game;
		init();
	}

	private void init() {
		new Generator().generate(name , nRegions);

		world = SaveSystem.load(FileSystem.getSaveFile(name, name));

		world.loadRegion(activeRegion);
		map = new Texture(new FileHandle(world.getMap()));
		
		renderer = new Renderer2D(game.getBatch());		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void update(){
		if(game.getInput().isClicked(Key.ENTER)){
			activeRegion++;
			if(activeRegion>=nRegions){
				activeRegion = 0;
			}
			world.loadRegion(activeRegion);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getBatch().begin();
		//stateManager.render(renderer);
		game.getBatch().draw(map, game.getScreenSize().x-200, game.getScreenSize().y-200, 200, 200);
		world.render(renderer, new Vector(0, 0));
		
		game.getBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
