package ch.magejo.randomgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RGTextField extends TextField{

	private static TextFieldStyle style = getDefaultStyle();

	public RGTextField(String text) {
		super(text, style);
		setAlignment(1);
	}

	private static TextFieldStyle getDefaultStyle(){
		TextFieldStyle style;
		BitmapFont btnFont = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		Skin txtFieldSkin = new Skin();
		TextureAtlas btnAtlas = new TextureAtlas(Gdx.files.internal("UI/Buttons/button.pack"));
		txtFieldSkin.addRegions(btnAtlas);
		style = new TextFieldStyle();
		style.font = btnFont;
		style.background = txtFieldSkin.getDrawable("buttonOff");
		style.fontColor = Color.WHITE;
		return style;
	}

}
