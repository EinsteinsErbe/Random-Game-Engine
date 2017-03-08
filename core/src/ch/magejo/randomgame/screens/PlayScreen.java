package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class PlayScreen implements Screen {

	private Renderer2D renderer;

	private Main game;

	private MenuStage gameMenu;

	public PlayScreen(Main game) {
		this.game = game;

		//renderer = new Renderer2D(game.getBatch());

		ButtonNames[] buttons = {ButtonNames.Play, ButtonNames.Settings, ButtonNames.Close};

		gameMenu = new MenuStage(buttons, game);

		game.getInputMultiplexer().addProcessor(gameMenu);

		init();
	}

	private void init() {
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void update(){
		gameMenu.act();
		
		if(game.getInput().isPressed(Key.ATTACK)){
			
		}
		
		if(gameMenu.isClicked(ButtonNames.Close)){
			game.dispose();
			System.exit(0);
		}

		if(gameMenu.isClicked(ButtonNames.Play)){
			Log.printLn("is clicked Play", getClass().getName(), 0);
		}
		
		if(gameMenu.isClicked(ButtonNames.Settings)){
			Log.printLn("is clicked Settings", getClass().getName(), 0);
		}
		
		

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameMenu.draw();
	}

	@Override
	public void resize(int width, int height) {
		gameMenu.init(game);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {


	}

}
