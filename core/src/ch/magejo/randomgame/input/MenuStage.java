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

	protected RGTextButton[] buttons;
	protected int marginButtons = 80;
	protected int btnHeight = 100;
	protected Main game;
	protected int yOffset;				//offset which pushes whole menu down

	protected ArrayList<RGTextField> textboxes;

	protected ButtonNames[] buttonNames;
	protected ButtonNames additionalButton;
	protected String[] buttonNamesString;
	protected int length;

	public MenuStage(ButtonNames[] buttonNames, Main game) {
		this(buttonNames, game, 0);
	}

	public MenuStage(ButtonNames button, String[] buttonNames, Main game) {
		this(button, buttonNames, game, 0);
	}

	public MenuStage(ButtonNames[] buttonNames, Main game, int yOffset) {
		super();
		textboxes = new ArrayList<>();
		this.buttonNames = buttonNames;
		this.game = game;
		this.yOffset = yOffset;
		length = buttonNames.length;
	}

	public MenuStage(ButtonNames button, String[] buttonNames, Main game, int yOffset) {
		super();
		textboxes = new ArrayList<>();
		this.additionalButton = button;
		this.buttonNamesString = buttonNames;
		this.game = game;
		length = buttonNames.length + 1;
	}

	public void init() {
		buttons = new RGTextButton[length];
		game.getInputMultiplexer().addProcessor(this);
		clear();
		int btnWidth = (int) game.getScreenSize().x/2;
		for(int i = 0; i < length; i++){
			RGTextButton b;
			if(additionalButton != null && i>=length-1){
				b = new RGTextButton(game.getTextGenerator().getButtonText(additionalButton), additionalButton);
			}
			else if(buttonNames != null){
				b = new RGTextButton(game.getTextGenerator().getButtonText(buttonNames[i]), buttonNames[i]);
			}
			else{
				b = new RGTextButton(buttonNamesString[i]);
			}
			b.setTransform(true);
			b.setPosition(
					game.getScreenSize().x/2 - btnWidth/2,
					((game.getScreenSize().y)/2 - ((length*(marginButtons+btnHeight))-marginButtons)/2) + (length-i-1)*(marginButtons+btnHeight)-yOffset);
			b.setWidth(btnWidth);
			b.setHeight(btnHeight);
			buttons[i] = b;
			addActor(buttons[i]);
		}

		for(Actor actor: textboxes){
			addActor(actor);
		}
	}

	public boolean isClicked(ButtonNames name){

		for(RGTextButton b: buttons){
			if(name.equals(b.getButtonName())){
				if(b.isClicked()){
					return true;
				}
			}
		}
		return false;
	}

	public String getClicked(){

		for(RGTextButton b: buttons){
			if(b.isClicked()){
				return b.getLabel().getText().toString();
			}
		}
		return null;
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
		addActor(textField);
		return textboxes.size()-1;
	}

	public RGTextField getTextField(int index){
		return textboxes.get(index);
	}
}
