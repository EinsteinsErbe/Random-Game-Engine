package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.screens.ScreenList;

public class PausedGameScreen implements Screen{

	private MenuStage mainStage;
	private Main game;
	private TextureRegion screenShot;

	public PausedGameScreen(Main game, TextureRegion screenShot) {
		this.game = game;
		this.screenShot = screenShot;
		ButtonNames[] buttons = {ButtonNames.Play, ButtonNames.Save, ButtonNames.Settings, ButtonNames.Mainmenu};

		mainStage = new MenuStage(buttons, game);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getBatch().begin();
		game.getBatch().draw(screenShot, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		game.getBatch().end();
		mainStage.render();
	}

	private void update() {
		if(mainStage.isClicked(ButtonNames.Play)){
			dispose();
			game.getGameState().unpauseGame();
		}

		if(mainStage.isClicked(ButtonNames.Save)){
			game.getWorld().save();
		}

		if(mainStage.isClicked(ButtonNames.Settings)){
			game.logInfo("Settings was pressed", getClass().getName(), 3);
		}

		if(mainStage.isClicked(ButtonNames.Mainmenu)){
			dispose();
			game.changeScreen(ScreenList.MainMenu);
		}
	}

	@Override
	public void resize(int width, int height) {
		mainStage.init();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		game.getInputMultiplexer().removeProcessor(mainStage);
	}

}

