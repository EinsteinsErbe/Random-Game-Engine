package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
    TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
	
	public PlayScreen(Main game) {
		this.game = game;
		init();
	}

	private void init() {
		
		renderer = new Renderer2D(game.getBatch());
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		font = new BitmapFont(Gdx.files.internal("UI/Buttons/MyFont.fnt"));
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("UI/Buttons/button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("buttonOn");
        textButtonStyle.down = skin.getDrawable("buttonOff");
        button = new TextButton("Play", textButtonStyle);
        stage.addActor(button);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	private boolean isPLayClicked = false;
	private boolean isPlayReleased = false;
	public void update(){
		if(game.getInput().isClicked(Key.ENTER)){
			activeRegion++;
			if(activeRegion>=nRegions){
				activeRegion = 0;
			}
			world.loadRegion(activeRegion);
		}
		
		boolean lastClick = isPLayClicked;
		 isPlayReleased = false;
		if(button.isPressed()){
			isPLayClicked = true;
		}else{
			isPLayClicked = false;
		}
		
		if(lastClick &! isPLayClicked){
			isPlayReleased = true;
		}
		
		if(isPlayReleased){
			Log.printLn("clicked", getClass().getName(), 0);
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
		// TODO Auto-generated method stub

	}

}
