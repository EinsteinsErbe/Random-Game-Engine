package ch.magejo.randomgame.render;

import com.badlogic.gdx.math.Vector2;

public interface Renderer {
	public void renderTile(int address, Vector2 offset);
}
