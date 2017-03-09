package ch.magejo.randomgame.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.utils.math.Vector;

public class MessageDialog{
	private Vector origin;
	private int width;
	private int height;
	protected Main game;
	
	private boolean fillScreen = false;
	
	private static Texture background;
	
	private Stage overlay;
	
	private boolean open = false;
	
	protected BitmapFont font;
	
	private ArrayList<RGButton> buttons;
	private ArrayList<Icon> icons;
	private ArrayList<DrawableTextDto> texts;
	
	public MessageDialog(Vector origin, int width, int height, Main game) {
		super();
		font = new BitmapFont(Gdx.files.internal("UI/Buttons/colarableFont.fnt"));
		buttons = new ArrayList<>();
		icons = new ArrayList<>();
		texts = new ArrayList<>();
		background = new Texture("UI/Dialog/background.png");
		overlay = new Stage();
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.game = game;
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
		texts.add(new DrawableTextDto(text, position.add(origin), 1));
		return texts.size()-1;
	}
	
	public void render(float delta){
		update(delta);
			
		if(open){
			game.getBatch().begin();
			game.getBatch().draw(background, origin.x, origin.y, width, height);
			for(Icon texture: icons){
				if(texture.isVisible()){
					game.getBatch().draw(texture.getTexture(), texture.getPosition().x, texture.getPosition().y, texture.getWidth(), texture.getHeight());
				}
			}
			for(DrawableTextDto text: texts){
				font.draw(game.getBatch(), text.getText(), (int) text.getPosition().x, (int) text.getPosition().y);
			}
			game.getBatch().end();
			overlay.draw();
		}
	}
	
	public void update(float delta){
		if(open){
			overlay.act();
		}
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
	
	public void open(){
		open = true;
		game.getInputMultiplexer().addProcessor(overlay);
	}
	
	public void close(){
		open = false;
		game.getInputMultiplexer().removeProcessor(overlay);
	}
	
	public boolean isOpen(){
		return open;
	}
	
}
