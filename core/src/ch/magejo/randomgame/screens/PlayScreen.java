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
import ch.magejo.randomgame.game.RunningGame;
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

public class PlayScreen extends abstractScreen {

	private Renderer2D renderer;

	private MenuStage gameMenu;
	
	private State curentGameState = State.RUNNING;
	
	private RunningGame runningGame;

	public PlayScreen(Main game) {
		super(game);

		runningGame = new RunningGame(game);

		initMenu();
	}
	
	private void initMenu(){
		ButtonNames[] buttons = {ButtonNames.Play, ButtonNames.Settings, ButtonNames.Mainmenu};

		gameMenu = new MenuStage(buttons, game);
	}

	@Override
	public void show() {
		gameMenu.init();
	}

	public void update(float delta){
		gameMenu.act();
		
		if(game.getInput().isClicked(Key.PAUSE)){
			if(gameMenu.isOpen()){
				curentGameState = State.RUNNING;
				gameMenu.close();
			}else{
				curentGameState = State.PAUSED;
				runningGame.makeScreenshot();
				gameMenu.open();
			}
		}
		
		if(game.getInput().isPressed(Key.ESCAPE)){
			runningGame.makeScreenshot();
		}

		if(gameMenu.isClicked(ButtonNames.Play)){
			Log.printLn("is clicked Play", getClass().getName(), 0);
		}
		
		if(gameMenu.isClicked(ButtonNames.Settings)){
			Log.printLn("is clicked Settings", getClass().getName(), 0);
		}
		
		if(gameMenu.isClicked(ButtonNames.Mainmenu)){
			changeScreen(ScreenList.MainMenu, gameMenu);
		}	
		
		handleStates(delta);
	}

	private void handleStates(float delta) {
		if(curentGameState.equals(State.PAUSED)){
			runningGame.renderScreenShot();
		}
		if(curentGameState.equals(State.RUNNING)){
			runningGame.update(delta);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(curentGameState.equals(State.RUNNING)){
			game.getBatch().begin();
			runningGame.render(delta);
			game.getBatch().end();
		}else{
			runningGame.renderScreenShot();
		}
		
		gameMenu.render();	
	}

	@Override
	public void resize(int width, int height) {
		gameMenu.init();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
	
	public enum State{
		PAUSED, RUNNING, INVENTORY, MESSAGE, DIALOG 
	}

}
