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
	private TileSet playerMaleTileSet;
	private TileSet dwarfMaleTileSet;
	private TileSet humanMaleTileSet;
	//private TileSet humanFemaleTileSet;
	private TileSet elfenMaleTileSet;
	//private TileSet elfenFemaleTileSet;
	private TileSet orcMaleTileSet;
	private TileSet defaultTileSet;

	public Renderer2D(SpriteBatch batch) {
		this.batch = batch;

		landscapeTileSet = new TileSet("TileSet/WorldSetgross.png", 32, 32);
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

		landscapeTileSet.addTile(2, 2, Tile.TREE2_TRUNK.ID);	//Trunk
		landscapeTileSet.addTile(3, 5, Tile.TREE2_C_BL.ID);
		landscapeTileSet.addTile(4, 5, Tile.TREE2_C_BM.ID);
		landscapeTileSet.addTile(5, 5, Tile.TREE2_C_BR.ID);
		landscapeTileSet.addTile(3, 4, Tile.TREE2_C_ML.ID);
		landscapeTileSet.addTile(4, 4, Tile.TREE2_C_MM.ID);
		landscapeTileSet.addTile(5, 4, Tile.TREE2_C_MR.ID);
		landscapeTileSet.addTile(3, 3, Tile.TREE2_C_TL.ID);
		landscapeTileSet.addTile(4, 3, Tile.TREE2_C_TM.ID);
		landscapeTileSet.addTile(5, 3, Tile.TREE2_C_TR.ID);

		landscapeTileSet.addTile(0, 6, Tile.FLOWER1.ID);
		landscapeTileSet.addTile(1, 6, Tile.FLOWER2.ID);
		landscapeTileSet.addTile(2, 6, Tile.FLOWER3.ID);
		landscapeTileSet.addTile(3, 6, Tile.FLOWER4.ID);
		landscapeTileSet.addTile(4, 6, Tile.FLOWER5.ID);
		landscapeTileSet.addTile(5, 6, Tile.FLOWER6.ID);
		landscapeTileSet.addTile(6, 6, Tile.FLOWER7.ID);
		landscapeTileSet.addTile(7, 6, Tile.FLOWER8.ID);
		landscapeTileSet.addTile(8, 6, Tile.FLOWER9.ID);
		landscapeTileSet.addTile(9, 6, Tile.FLOWER10.ID);
		landscapeTileSet.addTile(0, 7, Tile.FLOWER11.ID);
		landscapeTileSet.addTile(1, 7, Tile.FLOWER12.ID);
		landscapeTileSet.addTile(2, 7, Tile.FLOWER13.ID);
		landscapeTileSet.addTile(3, 7, Tile.FLOWER14.ID);
		landscapeTileSet.addTile(4, 7, Tile.FLOWER15.ID);
		landscapeTileSet.addTile(5, 7, Tile.FLOWER16.ID);
		landscapeTileSet.addTile(6, 7, Tile.FLOWER17.ID);
		landscapeTileSet.addTile(7, 7, Tile.FLOWER18.ID);
		landscapeTileSet.addTile(8, 7, Tile.FLOWER19.ID);
		landscapeTileSet.addTile(9, 7, Tile.FLOWER20.ID);
		landscapeTileSet.addTile(0, 8, Tile.FLOWER21.ID);
		landscapeTileSet.addTile(1, 8, Tile.FLOWER22.ID);
		landscapeTileSet.addTile(2, 8, Tile.FLOWER23.ID);
		landscapeTileSet.addTile(3, 8, Tile.FLOWER24.ID);
		landscapeTileSet.addTile(4, 8, Tile.FLOWER25.ID);
		landscapeTileSet.addTile(5, 8, Tile.FLOWER26.ID);
		landscapeTileSet.addTile(6, 8, Tile.FLOWER27.ID);
		landscapeTileSet.addTile(7, 8, Tile.FLOWER28.ID);
		landscapeTileSet.addTile(8, 8, Tile.FLOWER29.ID);
		landscapeTileSet.addTile(9, 8, Tile.FLOWER30.ID);
		landscapeTileSet.addTile(0, 9, Tile.FLOWER31.ID);
		landscapeTileSet.addTile(1, 9, Tile.FLOWER32.ID);
		landscapeTileSet.addTile(2, 9, Tile.FLOWER33.ID);
		landscapeTileSet.addTile(3, 9, Tile.FLOWER34.ID);
		landscapeTileSet.addTile(4, 9, Tile.FLOWER35.ID);
		landscapeTileSet.addTile(5, 9, Tile.FLOWER36.ID);
		landscapeTileSet.addTile(6, 9, Tile.FLOWER37.ID);
		landscapeTileSet.addTile(7, 9, Tile.FLOWER38.ID);
		landscapeTileSet.addTile(8, 9, Tile.FLOWER39.ID);
		landscapeTileSet.addTile(9, 9, Tile.FLOWER40.ID);

		landscapeTileSet.addTile(4, 2, Tile.TRUNK_ONLY.ID);
		landscapeTileSet.addTile(8, 4, Tile.TRUNK_GROUND.ID);

		landscapeTileSet.addTile(5, 2, Tile.BUSH1.ID);
		landscapeTileSet.addTile(6, 2, Tile.BUSH2.ID);

		landscapeTileSet.addTile(7, 4, Tile.PLANT1.ID);
		landscapeTileSet.addTile(9, 4, Tile.PLANT2.ID);

		landscapeTileSet.addTile(6, 3, Tile.ROCK1.ID);
		landscapeTileSet.addTile(6, 4, Tile.ROCK2.ID);
		landscapeTileSet.addTile(6, 5, Tile.ROCK3.ID);
		landscapeTileSet.addTile(7, 5, Tile.ROCK4.ID);
		landscapeTileSet.addTile(8, 5, Tile.ROCK5.ID);
		landscapeTileSet.addTile(9, 5, Tile.ROCK6.ID);

		landscapeTileSet.addTile(7, 1, Tile.DOUBLE_ROCK1_BOTTOM.ID);
		landscapeTileSet.addTile(7, 0, Tile.DOUBLE_ROCK1_TOP.ID);
		landscapeTileSet.addTile(8, 1, Tile.DOUBLE_ROCK2_BOTTOM.ID);
		landscapeTileSet.addTile(8, 0, Tile.DOUBLE_ROCK2_TOP.ID);
		landscapeTileSet.addTile(9, 1, Tile.DOUBLE_ROCK3_BOTTOM.ID);
		landscapeTileSet.addTile(9, 0, Tile.DOUBLE_ROCK3_TOP.ID);
		landscapeTileSet.addTile(7, 3, Tile.DOUBLE_ROCK4_BOTTOM.ID);
		landscapeTileSet.addTile(7, 2, Tile.DOUBLE_ROCK4_TOP.ID);

		landscapeTileSet.addTile(8, 3, Tile.STATUE1_BOTTOM.ID);
		landscapeTileSet.addTile(8, 2, Tile.STATUE1_TOP.ID);

		landscapeTileSet.addTile(9, 3, Tile.TABLE1_BOTTOM.ID);
		landscapeTileSet.addTile(9, 2, Tile.TABLE1_TOP.ID);

		landscapeTileSet.addTile(10, 0, Tile.BAG1.ID);
		landscapeTileSet.addTile(11, 0, Tile.BARREL1.ID);
		landscapeTileSet.addTile(10, 1, Tile.CANDLE1.ID);
		landscapeTileSet.addTile(11, 1, Tile.CHAIR1.ID);

		initCreatureTileset();
	}

	private void initCreatureTileset(){
		playerMaleTileSet = new TileSet("TileSet/Creatures/playerMaleSprites.png", 32, 32);
		initTiles(playerMaleTileSet);
		humanMaleTileSet = new TileSet("TileSet/Creatures/humanMaleSprites.png", 32, 32);
		initTiles(humanMaleTileSet);
		//humanFemaleTileSet = new TileSet("TileSet/Creatures/humanFemaleSprites.png", 32, 32);
		//initTiles(humanFemaleTileSet);
		elfenMaleTileSet = new TileSet("TileSet/Creatures/elfenMaleSprites.png", 32, 32);
		initTiles(elfenMaleTileSet);
		//elfenFemaleTileSet = new TileSet("TileSet/Creatures/elfenFemaleSprites.png", 32, 32);
		//initTiles(elfenFemaleTileSet);
		dwarfMaleTileSet = new TileSet("TileSet/Creatures/dwarfMaleSprites.png", 32, 32);
		initTiles(dwarfMaleTileSet);
		orcMaleTileSet  = new TileSet("TileSet/Creatures/orcMaleSprites.png", 32, 32);
		initTiles(orcMaleTileSet);
		defaultTileSet = new TileSet("TileSet/Creatures/defaultSprites.png", 32, 32);
		initTiles(defaultTileSet);

	}

	private void initTiles(TileSet tileset){
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 10; y++){
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
	public void renderSprite(CreaturesTypes type, byte address, Vector position, float yOffset) {
		renderSprite(type, address, position.x, position.y, yOffset);
	}

	@Override
	public void renderSprite(CreaturesTypes type, byte address, float x, float y, float yOffset) {
		switch (type){
		case Player:
			playerMaleTileSet.render(batch, x, y, address, yOffset);
			break;
		case Elven:
			elfenMaleTileSet.render(batch, x, y, address, yOffset);
			break;
		case Human:
			humanMaleTileSet.render(batch, x, y, address, yOffset);
			break;
		case Dwarf:
			dwarfMaleTileSet.render(batch, x, y, address, yOffset);
			break;
		case Ork:
			orcMaleTileSet.render(batch, x, y, address, yOffset);
			break;
		default:
			defaultTileSet.render(batch, x, y, address, yOffset);
			break;
		}
	}
}
