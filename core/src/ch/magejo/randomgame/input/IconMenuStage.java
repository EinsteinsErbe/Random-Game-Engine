package ch.magejo.randomgame.input;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.utils.FileSystem;

public class IconMenuStage extends MenuStage{

	public IconMenuStage(ButtonNames button, String[] buttonNames, Main game) {
		this(button, buttonNames, game, 0);
	}

	public IconMenuStage(ButtonNames button, String[] buttonNames, Main game, int yOffset) {
		super(button, buttonNames, game, yOffset);
	}

	@Override
	public void init() {
		buttons = new RGTextButton[length];
		game.getInputMultiplexer().addProcessor(this);
		clear();
		int btnWidth = (int) game.getScreenSize().x/2;
		for(int i = 0; i < length; i++){
			RGTextButton b;
			Image image = null;
			if(additionalButton != null && i>=length-1){
				b = new RGTextButton(game.getTextGenerator().getButtonText(additionalButton), additionalButton);
			}
			else{
				b = new RGTextButton(buttonNamesString[i]);
				image = new Image(new Texture(new FileHandle(FileSystem.getSaveFile(buttonNamesString[i], "map.png"))));

			}
			b.setTransform(true);
			b.setPosition(
					game.getScreenSize().x/2 - btnWidth/2,
					((game.getScreenSize().y)/2 - ((length*(marginButtons+btnHeight))-marginButtons)/2) + (length-i-1)*(marginButtons+btnHeight)-yOffset);
			b.setWidth(btnWidth);
			b.setHeight(btnHeight);
			buttons[i] = b;
			addActor(buttons[i]);
			if(image != null){
				int dim = btnHeight+marginButtons*2/3;
				image.setBounds(b.getX()+btnWidth+marginButtons, b.getY()-marginButtons/3, dim, dim);
				addActor(image);
			}
		}

		for(Actor actor: textboxes){
			addActor(actor);
		}
	}
}
