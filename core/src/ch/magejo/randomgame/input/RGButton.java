package ch.magejo.randomgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class RGButton extends TextButton{
	
	private static TextButtonStyle textButtonStyle = getDefaultStyle();
    
    //track btnStates
    private boolean wasPressed = false;
	
	public RGButton(String name, TextButtonStyle style) {
		super(name, style);
	}
	
	public RGButton(String name) {
		super(name, textButtonStyle);
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
	
	public boolean isHovered(InputHandler input){
		Vector mousePosition = input.getMousePosition();
		if(
				mousePosition.x > getOriginX() && 
				mousePosition.x < getOriginX() + getWidth() &&
				mousePosition.y > getOriginY() &&
				mousePosition.y < getOriginY() + getHeight()){
			Log.printLn("Hover!", getClass().getName(), 0);
			return true;
		}
		return false;
	} 
	
	private static TextButtonStyle getDefaultStyle(){
		TextButtonStyle style;
		BitmapFont btnFont = new BitmapFont(Gdx.files.internal("UI/Buttons/colarableFont.fnt"));
		Skin btnSkin = new Skin();
		TextureAtlas btnAtlas = new TextureAtlas(Gdx.files.internal("UI/Buttons/button.pack"));
		btnSkin.addRegions(btnAtlas);
        style = new TextButtonStyle();
        style.font = btnFont;
        style.up = btnSkin.getDrawable("buttonOn");
        style.down = btnSkin.getDrawable("buttonOff");
		return style;
	}
}
