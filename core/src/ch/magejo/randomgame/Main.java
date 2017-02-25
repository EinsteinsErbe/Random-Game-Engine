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

	@Override
	public void create () {
		world = new WorldObject(5, 10);
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.render(batch, new Vector2(0, 0));
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
	}
}
