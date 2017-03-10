package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.Window;
import ch.magejo.randomgame.gui.dialogs.DialogWindow;
import ch.magejo.randomgame.gui.dialogs.MessageWindow;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.entity.things.armor.BreastArmor;
import ch.magejo.randomgame.mecanics.entity.things.armor.Helmet;
import ch.magejo.randomgame.mecanics.entity.things.cloth.Sock;
import ch.magejo.randomgame.mecanics.entity.things.cloth.Type;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Spear;
import ch.magejo.randomgame.mecanics.entity.things.weapons.Sword;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.test.TextGeneratorDummy;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Here the actual Fun happens, this is the raw game class which controls the PLayer and its interactions with 
 * the game world, further the whole game gets drawn from here
 * @author M.Geissbberger
 *
 */
public class GameManager {

	private Texture screenShot;

	public GameManager(Main game) {
		
	}

	/**
	 * update every single instance in the game which is currently loaded
	 * @param delta
	 */
	public void update(float delta){
		
	}

	/**
	 * render all things which are currently loaden in the game (game is running)
	 * @param delta
	 */
	public void render(float delta){
		
	}

	/**
	 * draw only a screenshot of the screen (if game is paused)
	 */
	public void renderScreenShot(){
		
	}
	
	/**
	 * make a screenshot from the game
	 */
	public Texture makeScreenshot(){
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);				

		Pixmap screenShot = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, screenShot.getPixels(), pixels.length);		
		Pixmap.setBlending(Blending.SourceOver);
		screenShot.setColor(new Color(0, 0, 0, 0.5f));		
		screenShot.fillRectangle(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Pixmap.setBlending(Blending.None);
		this.screenShot = new Texture(screenShot);
		screenShot.dispose();
		return this.screenShot;
	}
}
