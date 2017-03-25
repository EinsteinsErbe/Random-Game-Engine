package ch.magejo.randomgame.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.mecanics.render.Renderer;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.utils.math.Vector;

public class Renderer2D implements Renderer {

	SpriteBatch batch;
	private TileSet tileSet;

	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		tileSet = new TileSet("TileSet/WorldSet.png", 32, 32);
		tileSet.addTile(0, 0, 0);	//Deep Water
		tileSet.addTile(0, 1, 1);	//Sand
		tileSet.addTile(2, 0, 2);	//Grass 1
		tileSet.addTile(3, 0, 3);	//Grass 2
		tileSet.addTile(4, 0, 4);	//Grass 3
		tileSet.addTile(5, 0, 5);	//Stone
		tileSet.addTile(6, 0, 6);	//Snow

		tileSet.addTile(0, 2, 7);	//House Roof
		tileSet.addTile(2, 1, 8);	//Way (Village)
		tileSet.addTile(3, 1, 9);	//Wall (Village)
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
