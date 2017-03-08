package ch.magejo.randomgame.input;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.text.ButtonNames;

public class MenuStage extends Stage{

	private RGButton[] buttons;
	private int marginButtons = 100;
	private int btnHeight = 100;
	private boolean open = false;

	private ButtonNames[] buttonNames;

	public MenuStage(ButtonNames[] buttonNames, Main game) {
		super();
		this.buttonNames = buttonNames;
		init(game);
	}

	public void init(Main game) {
		buttons = new RGButton[buttonNames.length];
		game.getInputMultiplexer().addProcessor(this);
		clear();
		int btnWidth = (int) game.getScreenSize().x/2;
		for(int i = 0; i < buttonNames.length; i++){
			RGButton b = new RGButton(buttonNames[i], game.getTextGenerator()); 
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
		if(open){
			for(RGButton b: buttons){
				if(b.getButtonName().equals(name)){
					if(b.isClicked()){
						return true;
					}				
				}
			}
		}		
		return false;
	}
	
	public void setDisabled(ButtonNames name, boolean disabled){
		for(RGButton b: buttons){
			if(b.getButtonName().equals(name)){
				b.setDisabled(disabled);				
			}
		}
	}

	public void render() {
		if(open){
			draw();
		}
	}
	
	public void open(){
		this.open = true;
	}
	
	public void close(){
		this.open = false;
	}

	public void toggleOpen() {
		this.open = ! this.open;		
	}
}
