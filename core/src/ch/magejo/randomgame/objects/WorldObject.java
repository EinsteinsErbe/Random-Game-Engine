package ch.magejo.randomgame.objects;

import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.mecanics.World;
import ch.magejo.randomgame.render.Renderable;
import ch.magejo.randomgame.render.Renderer2D;

@SuppressWarnings("serial")
public class WorldObject extends World implements Renderable{

	public WorldObject(int width, int height) {
		super(width, height);
		//define Tileset

	}

	public void update(){
		super.update();
	}

	@Override
	public void render(Renderer2D renderer, Vector2 offset) {
		for(int x = 0; x < getWidth(); x++){
			for(int y = 0; y < getHeight(); y++){
				renderer.renderTile(getTile(x, y), new Vector2(offset).add(new Vector2(x, y).scl(32)));
			}
		}
	}
}
