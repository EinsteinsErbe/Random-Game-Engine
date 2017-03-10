package ch.magejo.randomgame.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * a default small windo which opens on top of the screen and can hold multiple elements, 
 * those elements can be texts, icons or textures
 * @author M.Geissbberger
 *
 */
public class Window{
	private Vector origin;
	private int width;
	private int height;
	protected Main game;
	
	private static Texture background;
	
	private Stage overlay;
	
	protected BitmapFont font;
	
	private ArrayList<RGButton> buttons;
	private ArrayList<Icon> icons;
	private ArrayList<DrawableTextDto> texts;
	
	private Texture screenShot;
	
	public Window(Vector origin, int width, int height, Main game, Texture screenShot) {
		super();
		font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		font.setColor(new Color(0, 0, 0.2f, 1));
		buttons = new ArrayList<>();
		icons = new ArrayList<>();
		texts = new ArrayList<>();
		background = new Texture("UI/Dialog/background.png");
		overlay = new Stage();
		game.getInputMultiplexer().addProcessor(overlay);
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.game = game;
		this.screenShot = screenShot;
	}
	
	public int addButton(RGButton button, Vector position, int width, int height){
		button.setPosition(origin.x + position.x, origin.y + position.y);
		button.setWidth(width);
		button.setHeight(height);
		overlay.addActor(button);
		buttons.add(button);
		return buttons.size()-1;
	}
	
	public int addIcon(String path, Vector position, int width, int height){
		icons.add(new Icon(path, position.add(origin), width, height));
		icons.get(icons.size()-1).show();
		return icons.size()-1;
	}
	
	public int addText(String text, Vector position){
		texts.add(new DrawableTextDto(text, position.add(origin), 1, font));
		return texts.size()-1;
	}
	
	public void render(float delta){
		update(delta);	
		game.getBatch().begin();
		game.getBatch().draw(screenShot, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		game.getBatch().draw(background, origin.x, origin.y, width, height);
		for(Icon texture: icons){
			if(texture.isVisible()){
				game.getBatch().draw(texture.getTexture(), texture.getPosition().x, texture.getPosition().y, texture.getWidth(), texture.getHeight());
			}
		}
		game.getBatch().end();
		for(DrawableTextDto text: texts){
			text.renderText(game.getBatch());
		}
		overlay.draw();
	}
	
	public void update(float delta){
		overlay.act();
	}
	
	public RGButton getButton(int id){
		return buttons.get(id);
	}
	
	public Icon getIcon(int id){
		return icons.get(id);
	}
	
	public DrawableTextDto getText(int id){
		return texts.get(id);
	}
	
	public void clearTexts(){
		texts.clear();
	}
	
	public void clearButtons(){
		for(RGButton b: buttons){
			b.remove();
		}
		buttons.clear();
	}
	
	public void clearIcons(){
		icons.clear();
	}	
	
	public void dispose(){
		game.getInputMultiplexer().removeProcessor(overlay);
	}
}
