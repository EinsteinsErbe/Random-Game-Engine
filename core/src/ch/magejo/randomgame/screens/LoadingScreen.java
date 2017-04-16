package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

import ch.magejo.randomgame.Main;

public class LoadingScreen extends BaseScreen{

	private Stage stage;
	private boolean init = false;

	private AsyncExecutor asyncExecutor = new AsyncExecutor(10);
	private AsyncResult<Void> task;

	private ScreenList targetScreen;

	private boolean animated = false;

	public LoadingScreen(Main game, ScreenList targetScreen, boolean animated, AsyncTask<Void> asyncTask) {
		super(game);

		this.targetScreen = targetScreen;
		this.animated = animated;

		stage = new Stage();

		Image loading = new Image(new TextureRegion(new Texture(Gdx.files.internal("UI/loading.png"))));

		loading.setPosition(Gdx.graphics.getWidth() / 2 - loading.getWidth() / 2, Gdx.graphics.getHeight() / 2 - loading.getHeight() / 2);
		loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2);
		loading.setZIndex(1);

		loading.addAction(new Action() {

			@Override
			public boolean act(float delta) {
				loading.rotateBy(delta*20);
				return false;
			}
		});

		stage.addActor(loading);


		BitmapFont font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		font.getData().setScale(2);

		TextFieldStyle style = new TextFieldStyle();
		style.font = font;
		style.fontColor = Color.WHITE;
		TextField tf = new TextField("Loading...", style);
		tf.setWidth(320);
		tf.setPosition(Gdx.graphics.getWidth()/2-tf.getWidth()/2, (Gdx.graphics.getHeight()-tf.getHeight()*3-loading.getHeight())/2);
		tf.setOrigin(tf.getWidth() / 2, tf.getHeight() / 2);
		stage.addActor(tf);

		task = asyncExecutor.submit(asyncTask);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!init){
			if(!task.isDone()) {
				if(animated){
					stage.act();
				}
				stage.draw();
				return;
			}
			else{
				changeScreen(targetScreen, stage);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}
}
