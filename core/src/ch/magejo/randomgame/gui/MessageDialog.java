package ch.magejo.randomgame.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.utils.math.Vector;

public class MessageDialog extends Stage{
	private Vector origin;
	private int width;
	private int height;
	private Main game;
	
	private boolean fillScreen = false;
	
	private static Texture background;
	
	private boolean open = false;
	
	public MessageDialog(Vector origin, int width, int height, Main game) {
		super();
		background = new Texture("UI/Dialog/background.png");
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.game = game;
	}
	
	public void addActor(Actor actor, Vector position, int width, int height){
		actor.setPosition(origin.x + position.x, origin.y + position.y);
		actor.setWidth(width);
		actor.setHeight(height);
		super.addActor(actor);
	}
	
	public void init(){
		
	}
	
	public void render(float delta){
		update(delta);
			
		if(open){
			//draw Background
			game.getBatch().draw(background, origin.x, origin.y, width, height);
			//draw(); --> todo to see background
		}
	}
	
	public void update(float delta){
		if(open){
			act();
		}
	}
	
	public void open(){
		open = true;
		game.getInputMultiplexer().addProcessor(this);
	}
	
	public void close(){
		open = false;
		game.getInputMultiplexer().removeProcessor(this);
	}
	
	public boolean isOpen(){
		return open;
	}
	
}
