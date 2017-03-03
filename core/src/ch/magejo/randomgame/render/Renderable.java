package ch.magejo.randomgame.render;

import com.badlogic.gdx.math.Vector2;

public interface Renderable {
	public void render(Renderer2D renderer, Vector2 offset);
}
