package ch.magejo.randomgame.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ch.magejo.randomgame.mecanics.entity.creatures.CreaturesTypes;
import ch.magejo.randomgame.mecanics.render.Renderer;
import ch.magejo.randomgame.mecanics.render.Tile;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.utils.math.Vector;
import ch.magejo.randomgame.utils.math.Vector2i;

public class Renderer2D implements Renderer {

	SpriteBatch batch;
	private TileSet landscapeTileSet;
	private TileSet dwarfMaleTileSet;
	private TileSet humanMaleTileSet;
	private TileSet humanFemaleTileSet;
	private TileSet elfenMaleTileSet;
	private TileSet elfenFemaleTileSet;
	private TileSet orcMaleTileSet;
	private TileSet defaultTileSet;
	
	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		landscapeTileSet = new TileSet("TileSet/WorldSet.png", 32, 32);
		landscapeTileSet.addTile(0, 0, Tile.WATER.ID);		//Deep Water
		landscapeTileSet.addTile(0, 1, Tile.SAND.ID);		//Sand
		landscapeTileSet.addTile(2, 0, Tile.GRASS_1.ID);	//Grass 1
		landscapeTileSet.addTile(3, 0, Tile.GRASS_2.ID);	//Grass 2
		landscapeTileSet.addTile(4, 0, Tile.GRASS_3.ID);	//Grass 3
		landscapeTileSet.addTile(5, 0, Tile.STONE.ID);		//Stone
		landscapeTileSet.addTile(6, 0, Tile.SNOW.ID);		//Snow

		landscapeTileSet.addTile(2, 1, Tile.WAY.ID);		//Path (Village)
		landscapeTileSet.addTile(3, 1, Tile.CITY_WALL.ID);		//Wall (Village)

		landscapeTileSet.addTile(0, 2, Tile.ROOF_1.ID);		//House Roof
		landscapeTileSet.addTile(1, 1, Tile.FACADE_1.ID);	//Facade (House)
		landscapeTileSet.addTile(6, 1, Tile.FACADE_2.ID);	//Facade (House)

		landscapeTileSet.addTile(4, 1, Tile.DOOR_1.ID);		//Door (House)
		landscapeTileSet.addTile(2, 1, Tile.FLOOR_1.ID);	//Floor (House)
		landscapeTileSet.addTile(3, 1, Tile.DOOR_IN_1.ID);	//Door Interior (House)
		landscapeTileSet.addTile(5, 1, Tile.WINDOW_1.ID);	//Window (House)

		landscapeTileSet.addTile(1, 2, Tile.TREE_TRUNK.ID);	//Trunk
		landscapeTileSet.addTile(3, 2, Tile.TREE_MID.ID);	//Tree mid
		landscapeTileSet.addTile(0, 5, Tile.TREE_C_BL.ID);
		landscapeTileSet.addTile(1, 5, Tile.TREE_C_BM.ID);
		landscapeTileSet.addTile(2, 5, Tile.TREE_C_BR.ID);
		landscapeTileSet.addTile(0, 4, Tile.TREE_C_ML.ID);
		landscapeTileSet.addTile(1, 4, Tile.TREE_C_MM.ID);
		landscapeTileSet.addTile(2, 4, Tile.TREE_C_MR.ID);
		landscapeTileSet.addTile(0, 3, Tile.TREE_C_TL.ID);
		landscapeTileSet.addTile(1, 3, Tile.TREE_C_TM.ID);
		landscapeTileSet.addTile(2, 3, Tile.TREE_C_TR.ID);

		initCreatureTileset();
	}

	private void initCreatureTileset(){
		humanMaleTileSet = new TileSet("TileSet/Creatures/humanMaleSprites.png", 32, 32);
		initTiles(humanMaleTileSet);
		humanFemaleTileSet = new TileSet("TileSet/Creatures/humanFemaleSprites.png", 32, 32);
		initTiles(humanFemaleTileSet);
		elfenMaleTileSet = new TileSet("TileSet/Creatures/elfenMaleSprites.png", 32, 32);
		initTiles(elfenMaleTileSet);
		elfenFemaleTileSet = new TileSet("TileSet/Creatures/elfenFemaleSprites.png", 32, 32);
		initTiles(elfenFemaleTileSet);
		dwarfMaleTileSet = new TileSet("TileSet/Creatures/dwarfMaleSprites.png", 32, 32);
		initTiles(dwarfMaleTileSet);
		orcMaleTileSet  = new TileSet("TileSet/Creatures/orcMaleSprites.png", 32, 32);
		initTiles(orcMaleTileSet);
		defaultTileSet = new TileSet("TileSet/Creatures/defaultSprites.png", 32, 32);
		initTiles(defaultTileSet);
		
	}
	
	private void initTiles(TileSet tileset){
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 9; y++){
				tileset.addTile(x, y, y*4+x);
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
		switch (type){
		case Elven:
			elfenMaleTileSet.render(batch, position.x, position.y, address);
		case Human:
			humanMaleTileSet.render(batch, position.x, position.y, address);
		case Ork:
			dwarfMaleTileSet.render(batch, position.x, position.y, address);
			break;
		case Dwarf:
			orcMaleTileSet.render(batch, position.x, position.y, address);
			break;
		default:
			defaultTileSet.render(batch, position.x, position.y, address);
		}
		
	}

	@Override
	public void renderSprite(CreaturesTypes type, byte address, float x, float y) {
		switch (type){
		case Elven:
			elfenMaleTileSet.render(batch, x, y, address);
			break;
		case Human:
			humanMaleTileSet.render(batch, x, y, address);
			break;
		case Dwarf:
			dwarfMaleTileSet.render(batch, x, y, address);
			break;
		case Ork:
			orcMaleTileSet.render(batch, x, y, address);
			break;
		default:
			defaultTileSet.render(batch, x, y, address);
			break;
		}
	}
}
