package ch.magejo.randomgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.objects.WorldObject;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.Log;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private WorldObject world;
	private Renderer2D renderer;
	CombinedInputHandler cInputHandler;
	private InputHandler input;

	@Override
	public void create () {
		world = new WorldObject(5, 10);
		world.setTile(2, 4, 2);
		world.setTile(2, 3, 1);
		batch = new SpriteBatch();
		renderer = new Renderer2D(batch);

		cInputHandler = new CombinedInputHandler();
		input = cInputHandler;
		Gdx.input.setInputProcessor(cInputHandler);
	}

	@Override
	public void resize (int width, int height) {
	}

	private void update() {
		cInputHandler.update();

	}

	@Override
	public void render () {
		update();
		for(Key k : Key.values()){
			if(input.isClicked(k)){
				Log.printLn("is clicked", k.name(),0);
			}
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.render(renderer, new Vector2(0, 0));
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
