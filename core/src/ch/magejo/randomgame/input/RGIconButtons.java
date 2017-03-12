package ch.magejo.randomgame.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RGIconButtons extends ImageButton{

	//track btnStates
    private boolean wasPressed = false;
    
	public RGIconButtons(Texture imageSet) {
		super(
			new TextureRegionDrawable(
					new TextureRegion(imageSet, 0, 0, imageSet.getWidth()/2, imageSet.getHeight())),
			new TextureRegionDrawable(
					new TextureRegion(imageSet, imageSet.getWidth()/2, 0, imageSet.getWidth()/2, imageSet.getHeight())));
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
}
