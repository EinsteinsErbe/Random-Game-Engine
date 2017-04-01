package ch.magejo.randomgame.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.mecanics.entity.creatures.CreaturesTypes;
import ch.magejo.randomgame.mecanics.render.Renderer;
import ch.magejo.randomgame.mecanics.render.Tile;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;
import ch.magejo.randomgame.utils.math.Vector2i;

public class Renderer2D implements Renderer {

	SpriteBatch batch;
	private TileSet landscapeTileSet;
	private TileSet creatureTileSet;

	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		landscapeTileSet = new TileSet("TileSet/WorldSet.png", 32, 32);
		landscapeTileSet.addTile(0, 0, Tile.WATER.ID);	//Deep Water
		landscapeTileSet.addTile(0, 1, Tile.SAND.ID);	//Sand
		landscapeTileSet.addTile(2, 0, Tile.GRASS_1.ID);	//Grass 1
		landscapeTileSet.addTile(3, 0, Tile.GRASS_2.ID);	//Grass 2
		landscapeTileSet.addTile(4, 0, Tile.GRASS_3.ID);	//Grass 3
		landscapeTileSet.addTile(5, 0, Tile.STONE.ID);	//Stone
		landscapeTileSet.addTile(6, 0, Tile.SNOW.ID);	//Snow

		landscapeTileSet.addTile(0, 2, Tile.ROOF.ID);	//House Roof
		landscapeTileSet.addTile(2, 1, Tile.WAY.ID);		//Path (Village)
		landscapeTileSet.addTile(3, 1, Tile.WALL.ID);	//Wall (Village)
		landscapeTileSet.addTile(1, 1, Tile.FACADE.ID);	//Facade (Village)

		landscapeTileSet.addTile(1, 2, Tile.TREE_TRUNK.ID);	//Trunk
		landscapeTileSet.addTile(2, 2, Tile.TREE_TOP.ID);	//Tree top
		landscapeTileSet.addTile(3, 2, Tile.TREE_MID.ID);	//Tree mid

		initCreatureTileset();
	}

	private void initCreatureTileset(){
		creatureTileSet = new TileSet("TileSet/humanSprites.png", 32, 32);

		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 9; y++){
				creatureTileSet.addTile(x, y, y*4+x);
			}
		}
	}

	@Override
	public void renderTile(byte address, Vector2i offset){
		renderTile(address, offset.x, offset.y);
	}

	public void dispose(){
		landscapeTileSet.dispose();
	}

	@Override
	public void renderTile(byte address, int x, int y) {
		landscapeTileSet.render(batch, x, y, address);
	}

	@Override
	public void renderSprite(CreaturesTypes type, byte address, Vector position) {
		creatureTileSet.render(batch, position.x, position.y, address);
	}

	@Override
	public void renderSprite(CreaturesTypes type, byte address, float x, float y) {
		creatureTileSet.render(batch, x, y, address);
	}
}
