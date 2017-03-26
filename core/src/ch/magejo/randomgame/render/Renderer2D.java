package ch.magejo.randomgame.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.mecanics.render.Renderer;
import ch.magejo.randomgame.mecanics.render.Tile;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.utils.math.Vector;

public class Renderer2D implements Renderer {

	SpriteBatch batch;
	private TileSet tileSet;

	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		tileSet = new TileSet("TileSet/WorldSet.png", 32, 32);
		tileSet.addTile(0, 0, Tile.WATER.ID);	//Deep Water
		tileSet.addTile(0, 1, Tile.SAND.ID);	//Sand
		tileSet.addTile(2, 0, Tile.GRASS_1.ID);	//Grass 1
		tileSet.addTile(3, 0, Tile.GRASS_2.ID);	//Grass 2
		tileSet.addTile(4, 0, Tile.GRASS_3.ID);	//Grass 3
		tileSet.addTile(5, 0, Tile.STONE.ID);	//Stone
		tileSet.addTile(6, 0, Tile.SNOW.ID);	//Snow

		tileSet.addTile(0, 2, Tile.ROOF.ID);	//House Roof
		tileSet.addTile(2, 1, Tile.WAY.ID);	//Way (Village)
		tileSet.addTile(3, 1, Tile.WALL.ID);	//Wall (Village)

		tileSet.addTile(1, 2, Tile.TREE_TRUNK.ID);	//Trunk
		tileSet.addTile(2, 2, Tile.TREE_TOP.ID);	//Tree top
		tileSet.addTile(3, 2, Tile.TREE_MID.ID);	//Tree mid
	}

	@Override
	public void renderTile(byte address, Vector offset){
		renderTile(address, (int)offset.x, (int)offset.y);
	}

	public void dispose(){
		tileSet.dispose();
	}

	@Override
	public void renderTile(byte address, int x, int y) {
		tileSet.render(batch, x, y, address);
	}
}
