package ch.magejo.randomgame.gui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ch.magejo.randomgame.utils.math.Vector;

public class TextBox {
	private DrawableTextDto[] textContent;
	private int lines = 1;
	private BitmapFont font;
	
	public TextBox(Vector position, int lines, float size) {
		super();
		textContent = new DrawableTextDto[lines];
		this.lines = lines;
		GlyphLayout layout = new GlyphLayout();
		font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		font.getData().setScale(size);
		layout.setText(font, "");
		int height = (int) ((float)layout.height * 1.3f);
		for(int i = 0; i < lines; i++){
			font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
			font.getData().setScale(size);
			textContent[i] = new DrawableTextDto("", new Vector(position.x, position.y + i*height+ height), size, font);
		}
	}

	public void addTextLine(String text, Color color){
		scrollUp();
		textContent[0].setText(text, color);
	}
	
	public void addTextLine(String text){
		scrollUp();
		textContent[0].setText(text);
	}
	
	public void addToLastLine(String text){
		textContent[0].addTextToLine(text);
	}
	
	public void scrollUp(){
		for(int i = lines-1; i > 0; i--){
			textContent[i].setText(textContent[i-1].getText());
			textContent[i].setColor(textContent[i-1].getColor());
		}		
		textContent[0].setText("");
		textContent[0].setColor(new Color(1,1,1,1));
	}
	
	public void render(Batch batch){
		for(DrawableTextDto text: textContent){
			text.renderText(batch);
		}
	}
}
