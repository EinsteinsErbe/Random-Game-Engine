package ch.magejo.randomgame.gui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class Minimap {

	private Texture map, regionMap, playerPos;
	private boolean renderRegions = false;
	private int width;
	private int height;
	private Vector3 position;
	private float scale = 6;

	public Minimap(String name){
		map = new Texture(new FileHandle(FileSystem.getSaveFile(name, "map.png")));
		regionMap = new Texture(new FileHandle(FileSystem.getSaveFile(name, "regionMap.png")));

		Pixmap pixmap = new Pixmap((int)(45/scale), (int)(30/scale), Format.RGBA8888 );
		pixmap.setColor( 1, 0, 0, 1 );
		if(scale>6){
			pixmap.fillRectangle(0, 0, (int)(45/scale), (int)(30/scale));
		}
		else{
			pixmap.drawRectangle(0, 0, (int)(45/scale), (int)(30/scale));
		}
		
		playerPos = new Texture( pixmap );
	}

	public void update(InputHandler input){
		if(input.isClicked(Key.ESCAPE)){
			renderRegions = !renderRegions;
		}
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setPosition(Vector3 position){
		this.position = position;
	}

	public void render(SpriteBatch batch){
		batch.begin();
		if(renderRegions){
			batch.draw(regionMap, width-2000/scale, height-2000/scale, 2000/scale, 2000/scale);
		}else{
			batch.draw(map, width-2000/scale, height-2000/scale, 2000/scale, 2000/scale);
		}
		batch.draw(playerPos, (int)((position.x/32)/scale+width-2000/scale-(45/scale)/2), (int)((position.y/32)/scale+height-2000/scale-(int)(30/scale)/2));
		batch.end();
	}
}
