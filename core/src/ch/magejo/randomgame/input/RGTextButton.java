package ch.magejo.randomgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.mecanics.text.TextGeneratorInterface;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class RGTextButton extends TextButton{
	
	private static TextButtonStyle textButtonStyle = getDefaultStyle();
	
	private ButtonNames name;
    
    //track btnStates
    private boolean wasPressed = false;
	
	public RGTextButton(String name) {
		super(name, textButtonStyle);
	}
	
	public RGTextButton(ButtonNames name, TextGeneratorInterface textGenerator) {
		super(textGenerator.getButtomText(name), textButtonStyle);
		this.name = name;
	}

	public boolean isClicked(){
		if(!isPressed() && wasPressed){
			wasPressed = false;
			return true;
		}else{
			wasPressed = isPressed();
		}
		return false;
	}
	
	private static TextButtonStyle getDefaultStyle(){
		TextButtonStyle style;
		BitmapFont btnFont = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		Skin btnSkin = new Skin();
		TextureAtlas btnAtlas = new TextureAtlas(Gdx.files.internal("UI/Buttons/button.pack"));
		btnSkin.addRegions(btnAtlas);
        style = new TextButtonStyle();
        style.font = btnFont;
        style.down = btnSkin.getDrawable("buttonOn");
        style.up = btnSkin.getDrawable("buttonOff");
		return style;
	}
	
	public ButtonNames getButtonName(){
		if(this.name == null){
			Main.logError(getName() + "-Button has no ButtonName assigned!", getClass().getName(), 1);
		}
		return this.name;
	}
}
