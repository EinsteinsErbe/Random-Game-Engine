package ch.magejo.randomgame.gui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class TextBox {
	private DrawableTextDto[] textContent;
	private int lines = 1;
	private BitmapFont font;
	
	public TextBox(Vector position, int lines, float size, boolean directionDown) {
		super();
		textContent = new DrawableTextDto[lines];
		this.lines = lines;
		GlyphLayout layout = new GlyphLayout();
		font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		font.getData().setScale(size);
		layout.setText(font, "");
		int height = (int) ((float)layout.height * 1.3f);
		if(directionDown){
			for(int i = 0; i < lines; i++){
				font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
				font.getData().setScale(size);
				textContent[i] = new DrawableTextDto("", new Vector(position.x, position.y - i*height- height), size, font);
			}
		}else{
			for(int i = 0; i < lines; i++){
				font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
				font.getData().setScale(size);
				textContent[i] = new DrawableTextDto("", new Vector(position.x, position.y + i*height+ height), size, font);
			}
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
	
	public void setLine(int line, Color color){
		if(line < lines && line >=0){
			textContent[line].setColor(color);
		}else{
			Log.printLn("not able to set line: " + line, getClass().getName(), 1);
		}		
	}
	
	public void multiplayColor(int line, Color color){
		if(line < lines && line >=0){
			Color oldColor = textContent[line].getColor();
			textContent[line].setColor(new Color(oldColor.r*color.r, oldColor.g*color.g, oldColor.b*color.b, oldColor.a*color.a));
		}else{
			Log.printLn("not able to change Color of: " + line, getClass().getName(), 1);
		}
		
		
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
	
	public void clear(){
		for(int i = 0; i < lines; i++){
			textContent[i].setText("");
		}
	}
	
	public int getLineNumber(){
		return lines;
	}
}
