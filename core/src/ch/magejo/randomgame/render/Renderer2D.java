package ch.magejo.randomgame.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.mecanics.render.Renderer;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.utils.math.Vector;

public class Renderer2D implements Renderer {

	SpriteBatch batch;
	private TileSet tileSet;
	private int wall;
	private int sand;
	private int sand2;

	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		tileSet = new TileSet("TileSet/WorldSet.png", 32, 32);
		tileSet.createTileAdress(0, 0);
		tileSet.createTileAdress(0, 1);
		tileSet.createTileAdress(2, 0);
		tileSet.createTileAdress(3, 0);
		tileSet.createTileAdress(4, 0);
		tileSet.createTileAdress(5, 0);
		tileSet.createTileAdress(6, 0);

		tileSet.createTileAdress(0, 2);
	}

	@Override
	public void renderTile(int address, Vector offset){
		renderTile(address, (int)offset.x, (int)offset.y);
	}

	public void dispose(){
		tileSet.dispose();
	}

	@Override
	public void renderTile(int address, int x, int y) {
		tileSet.render(batch, x, y, address);
	}
}
