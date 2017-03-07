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

		tileSet = new TileSet("TileSet/TestTileSet.png", 32, 32);
		wall = tileSet.createTileAdress(0, 0);
		sand = tileSet.createTileAdress(0, 1);
		sand2 = tileSet.createTileAdress(1, 1);
		tileSet.createTileAdress(1, 0);
		tileSet.createTileAdress(2, 0);
		tileSet.createTileAdress(3, 0);
		tileSet.createTileAdress(4, 0);
	}

	@Override
	public void renderTile(int address, Vector offset){
		tileSet.render(batch, offset.x, offset.y, address);
	}

	public void dispose(){
		tileSet.dispose();
	}
}