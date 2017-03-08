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
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class RunningGame {
	
	private Main game;
	private Renderer2D renderer;
	private TileSet tileSet;
	private int tile, tile2;
	
	Texture screenShot;
	
	public RunningGame(Main game) {
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());
		tileSet = new TileSet("TileSet/TestTileSet.png", 32, 32);
		tile = tileSet.createTileAdress(0, 0);
		tile2 = tileSet.createTileAdress(1, 0);
	}
	
	public void update(float delta){
		
	}
	
	public void render(float delta){
		for(int i = 0; i < 10 ; i++){
			for(int j = 0; j < 10; j++){
				renderer.renderTile(tile2, new Vector(i*tileSet.getTextureWidth(), j*tileSet.getTextureHeight()));
			}
		}
	}
	
	public void renderScreenShot(){
		if(screenShot == null){
			Log.printErrorLn("please call makeScreenShot first!", getClass().getName(), 1);
		}else{
			game.getBatch().begin();
			game.getBatch().draw(screenShot, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
			game.getBatch().end();
		}	
	}
	
	public void makeScreenshot(){
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);		
		
		
		
		Pixmap screenShot = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, screenShot.getPixels(), pixels.length);		
		Pixmap.setBlending(Blending.SourceOver);
		screenShot.setColor(new Color(0, 0, 0, 0.75f));		
		screenShot.fillRectangle(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Pixmap.setBlending(Blending.None);
		this.screenShot = new Texture(screenShot);
		screenShot.dispose();
	}
}
