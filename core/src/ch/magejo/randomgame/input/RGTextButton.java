package ch.magejo.randomgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ch.magejo.randomgame.mecanics.sound.SFX;
import ch.magejo.randomgame.mecanics.sound.Sounds;
import ch.magejo.randomgame.mecanics.text.ButtonNames;

public class RGTextButton extends TextButton{

	private static TextButtonStyle textButtonStyle = getDefaultStyle();

	private ButtonNames name;

	//track btnStates
	private boolean wasPressed = false;

	public RGTextButton(String name) {
		super(name, textButtonStyle);
	}

	public RGTextButton(String name, ButtonNames buttonName) {
		super(name, textButtonStyle);
		this.setName(name);
		this.name = buttonName;
	}

	public boolean isClicked(){
		if(!isPressed() && wasPressed){
			wasPressed = false;
			Sounds.playSound(SFX.CLICK_BTN, 1.0f);
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
		return this.name;
	}
}
