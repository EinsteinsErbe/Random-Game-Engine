package ch.magejo.randomgame.objects;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector2i;

public class TileSet {
	private Texture tileSet;
	private int textureWidth;
	private int textureHeight;
	private HashMap<Integer, Vector2i> addressedTiles;
	private TextureRegion actualRegion;

	public TileSet(String tileset, int textureWidth, int textureHeigth) {
		this.tileSet = new Texture(tileset);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeigth;
		this.addressedTiles = new HashMap<Integer, Vector2i>();
		this.actualRegion = new TextureRegion(tileSet);
	}

	public void addTile(int xPos, int yPos, int id){
		addressedTiles.put(id, new Vector2i(xPos, yPos));
	}

	public void render(SpriteBatch batch, float x, float y, int addres){
		actualRegion.setRegion(
				addressedTiles.get(addres).x*textureWidth,
				addressedTiles.get(addres).y*textureHeight,
				textureWidth,
				textureHeight
				);

		batch.draw(actualRegion, x*textureWidth, y*textureHeight);
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public void dispose(){
		tileSet.dispose();
	}
}
