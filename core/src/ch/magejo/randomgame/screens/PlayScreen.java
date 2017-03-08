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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.input.CombinedInputHandler;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.Vector;

public class PlayScreen implements Screen {
	
	private World world;
	private Renderer2D renderer;
	private int nRegions = 10;
	private int activeRegion = 0;
	private String name = "MyWorld";
	private Texture map;
	
	private Main game;
	
	Stage stage;
    RGButton button;
	
	public PlayScreen(Main game) {
		this.game = game;
		init();
	}

	private void init() {
		
		renderer = new Renderer2D(game.getBatch());
		
		stage = new Stage();
		game.getInputMultiplexer().addProcessor(stage);
		
        button = new RGButton("play");
        stage.addActor(button);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void update(){
		if(game.getInput().isPressed(Key.ATTACK)){
			/*activeRegion++;
			if(activeRegion>=nRegions){
				activeRegion = 0;
			}
			//world.loadRegion(activeRegion);*/
		}
		
		if(button.isClicked()){
			Log.printLn("clicked Play", getClass().getName(), 0);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

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
		

	}

}
