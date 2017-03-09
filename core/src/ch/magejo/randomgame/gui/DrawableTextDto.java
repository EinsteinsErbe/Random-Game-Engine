package ch.magejo.randomgame.gui;

import ch.magejo.randomgame.utils.math.Vector;

public class DrawableTextDto {
	private String text;
	private Vector position;
	private int size;
	
	public DrawableTextDto(String text, Vector position, int size) {
		super();
		this.text = text;
		this.position = position;
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public Vector getPosition() {
		return position;
	}

	public int getSize() {
		return size;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	
	
	
}
