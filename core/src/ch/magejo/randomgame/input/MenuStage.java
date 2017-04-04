package ch.magejo.randomgame.input;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.text.ButtonNames;

public class MenuStage extends Stage{

	private RGTextButton[] buttons;
	private int marginButtons = 100;
	private int btnHeight = 100;
	private Main game;
	private int yOffset = 0;				//offset which pushes whole menu down

	private ButtonNames[] buttonNames;
	
	private ArrayList<RGTextField> textboxes;

	public MenuStage(ButtonNames[] buttonNames, Main game) {
		super();
		textboxes = new ArrayList<>();
		this.game = game;
		this.buttonNames = buttonNames;
		init();
	}
	
	public MenuStage(ButtonNames[] buttonNames, Main game, int yOffset) {
		super();
		textboxes = new ArrayList<>();
		this.game = game;
		this.buttonNames = buttonNames;
		this.yOffset = yOffset;
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
					((game.getScreenSize().y)/2 - ((buttonNames.length*(marginButtons+btnHeight))-marginButtons)/2) + (buttonNames.length-i-1)*(marginButtons+btnHeight)-yOffset);
			b.setWidth(btnWidth);
			b.setHeight(btnHeight);
			buttons[i] = b;
			super.addActor(buttons[i]);
		}
		
		for(Actor actor: textboxes){
			super.addActor(actor);
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
	
	public int addTextbox(RGTextField textField){
		textboxes.add(textField);
		super.addActor(textField);
		return textboxes.size()-1;
	}
	
	public RGTextField getTextField(int index){
		return textboxes.get(index);
	}
}
