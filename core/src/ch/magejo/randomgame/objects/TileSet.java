package ch.magejo.randomgame.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.utils.Log;

public class TileSet {
	private Texture tileSet;
	private int textureWidth;
	private int textureHeight;
	private ArrayList<Vector2> addressedTiles;
	private TextureRegion actualRegion;

	public TileSet(String tileset, int textureWidth, int textureHeigth) {
		this.tileSet = new Texture(tileset);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeigth;
		this.addressedTiles = new ArrayList<Vector2>();
		this.actualRegion = new TextureRegion(tileSet);
	}

	public int createTileAdress(int xPos, int yPos){
		addressedTiles.add(new Vector2(xPos, yPos));
		return addressedTiles.size()-1;
	}

	public void render(SpriteBatch batch, float x, float y, int addres){
		/*Log.printLn("x: " + addressedTiles.get(addres).x*textureWidth
				+ " y: " + addressedTiles.get(addres).y*textureHeight
				+ " w: " + textureWidth
				+ " h: " + textureHeight
				, getClass().getName(), 0);*/
		actualRegion.setRegion(
				(int) addressedTiles.get(addres).x*textureWidth,
				(int) addressedTiles.get(addres).y*textureHeight,
				textureWidth,
				textureHeight
				);

		batch.draw(actualRegion, x, y);
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
