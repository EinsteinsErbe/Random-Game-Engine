package ch.magejo.randomgame.gui;

import com.badlogic.gdx.graphics.Texture;

import ch.magejo.randomgame.objects.TextureManager;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * stores a texture and its screenposition
 * @author M.Geissbberger
 *
 */
public class Icon{
	
	private int width; 
	private int height;
	private Vector position;
	private boolean visible;
	private int textureId;

	public Icon(String path, Vector position, int width, int height) {
		textureId = TextureManager.addTexture(path);
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector getPosition() {
		return position;
	}
	
	public void show(){
		this.visible = true;
	}
	
	public void hide(){
		this.visible = false;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public Texture getTexture(){
		return TextureManager.getTexture(textureId);
	}
}
