package ch.magejo.randomgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.mecanics.World;
import ch.magejo.randomgame.objects.WorldObject;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private WorldObject world;
	private Renderer2D renderer;

	@Override
	public void create () {
		world = new WorldObject(5, 10);
		world.setTile(2, 4, 2);
		world.setTile(2, 3, 1);
		batch = new SpriteBatch();
		renderer = new Renderer2D(batch);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.render(renderer, new Vector2(0, 0));
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}
}
