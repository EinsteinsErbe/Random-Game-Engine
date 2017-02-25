package ch.magejo.randomgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.mecanics.World;

@SuppressWarnings("serial")
public class WorldObject extends World{
	private TileSet tileSet;
	private int wall;
	private int sand;
	private int sand2;
	public WorldObject(int width, int height) {
		super(width, height);
		//define Tileset
		tileSet = new TileSet("TileSet/TestTileSet.png", 32, 32);
		wall = tileSet.createTileAdress(0, 0);
		sand = tileSet.createTileAdress(0, 1);
		sand2 = tileSet.createTileAdress(1, 1);
	}
	
	public void render(SpriteBatch batch, Vector2 offset){
		for(int x = 0; x < getWidth(); x++){
			for(int y = 0; y < getHeight(); y++){
				if(getTile(x, y) == wall && x != 0){
					tileSet.render(
							batch,
							offset.x+x*tileSet.getTextureWidth()+tileSet.getTextureWidth(),
							offset.y+y*tileSet.getTextureHeight()+tileSet.getTextureHeight(),
							wall
							);
				}else{
					if(y == 0){
						tileSet.render(
								batch,
								offset.x+x*tileSet.getTextureWidth()+tileSet.getTextureWidth(),
								offset.y+y*tileSet.getTextureHeight()+tileSet.getTextureHeight(),
								sand2
								);
					}else{
						tileSet.render(
								batch,
								offset.x+x*tileSet.getTextureWidth()+tileSet.getTextureWidth(),
								offset.y+y*tileSet.getTextureHeight()+tileSet.getTextureHeight(),
								sand
								);
					}					
				}
			}
		}
	}
	
	public void update(){
		super.update();
	}
	
	public void dispose(){
		tileSet.dispose();
	}
}
