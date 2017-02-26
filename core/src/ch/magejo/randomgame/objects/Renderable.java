package ch.magejo.randomgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ch.magejo.randomgame.Renderer2D;

public interface Renderable {
	public void render(Renderer2D renderer, Vector2 offset);
}
