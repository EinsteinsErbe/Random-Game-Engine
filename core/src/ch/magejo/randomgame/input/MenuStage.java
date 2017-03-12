package ch.magejo.randomgame.input;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.text.ButtonNames;

public class MenuStage extends Stage{

	private RGTextButton[] buttons;
	private int marginButtons = 100;
	private int btnHeight = 100;
	private Main game;

	private ButtonNames[] buttonNames;

	public MenuStage(ButtonNames[] buttonNames, Main game) {
		super();
		this.game = game;
		this.buttonNames = buttonNames;
		init();
	}

	public void init() {
		buttons = new RGTextButton[buttonNames.length];
		game.getInputMultiplexer().addProcessor(this);
		clear();
		int btnWidth = (int) game.getScreenSize().x/2;
		for(int i = 0; i < buttonNames.length; i++){
			RGTextButton b = new RGTextButton(buttonNames[i], game.getTextGenerator()); 
			b.setTransform(true);
			b.setPosition(
					game.getScreenSize().x/2 - btnWidth/2,
					(game.getScreenSize().y/2 - ((buttonNames.length*(marginButtons+btnHeight))-marginButtons)/2) + (buttonNames.length-i-1)*(marginButtons+btnHeight));
			b.setWidth(btnWidth);
			b.setHeight(btnHeight);
			buttons[i] = b;
			addActor(buttons[i]);
		}		
	}

	public boolean isClicked(ButtonNames name){

		for(RGTextButton b: buttons){
			if(b.getButtonName().equals(name)){
				if(b.isClicked()){
					return true;
				}				
			}
		}	
		return false;
	}

	public void setDisabled(ButtonNames name, boolean disabled){
		for(RGTextButton b: buttons){
			if(b.getButtonName().equals(name)){
				b.setDisabled(disabled);				
			}
		}
	}

	public void render() {
		draw();
	}
}
