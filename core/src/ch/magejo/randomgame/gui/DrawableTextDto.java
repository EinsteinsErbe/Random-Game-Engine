package ch.magejo.randomgame.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Holds information needed to draw a text on to the screen
 * @author M.Geissbberger
 *
 */
public class DrawableTextDto {
	private String text;
	private Vector position;
	private float size;
	private BitmapFont font;
	
	public DrawableTextDto(String text, Vector position, float size, BitmapFont font) {
		super();
		this.text = text;
		this.font = font;
		this.font.getData().setScale(size);
		this.position = position;
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public Vector getPosition() {
		return position;
	}

	public float getSize() {
		return size;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setText(String text, Color color){
		font.setColor(color);
		this.text = text;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void addTextToLine(String text){
		this.text = this.text + text;
	}
	
	public void addTextToLine(String text, Color color){
		font.setColor(color);
		this.text = this.text + text;
	}
	
	public void renderText(Batch batch){
		batch.begin();
		font.draw(batch, text, position.x, position.y);
		batch.end();
	}

	public Color getColor() {
		return font.getColor();
	}

	public void setColor(Color color) {
		font.setColor(color);		
	}
	
	
	
	
	
	
}
