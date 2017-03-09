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
import ch.magejo.randomgame.gui.MessageDialog;
import ch.magejo.randomgame.gui.dialogs.Message;
import ch.magejo.randomgame.input.RGButton;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.objects.TileSet;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class RunningGame {
	
	private Main game;
	private Renderer2D renderer;
	private TileSet tileSet;
	private int tile, tile2;
	
	private int dialogBtnId = 0;
	private int dialogText;
	
	Texture screenShot;
	
	private MessageDialog dialog;
	
	private Message message;
	
	public RunningGame(Main game) {
		this.game = game;
		this.renderer = new Renderer2D(game.getBatch());
		tileSet = new TileSet("TileSet/TestTileSet.png", 32, 32);
		tile = tileSet.createTileAdress(0, 0);
		tile2 = tileSet.createTileAdress(1, 0);
		
		dialog = new MessageDialog(new Vector(100, 100), 1024, 720, game);
		dialogBtnId = dialog.addButton(new RGButton("test"), new Vector(100, 100), 200, 100);
		dialog.addIcon("TileSet/TestTileSet.png", new Vector(300, 300), 100, 100);
		dialogText = dialog.addText("Hello", new Vector(100, 300));
		
		message = new Message("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur ", game);
		message.open();
	}
	
	public void update(float delta){
		if(game.getInput().isClicked(Key.ENTER)){
			if(dialog.isOpen()){
				dialog.close();
			}else{
				dialog.open();
			}
		}
		
		if(dialog.getButton(dialogBtnId).isClicked()){
			if(dialog.getText(dialogText).getText().equals("Hello")){
				dialog.getText(dialogText).setText("Hi");
			}else{
				dialog.getText(dialogText).setText("Hello");
			}
		}
	}
	
	public void render(float delta){
		game.getBatch().begin();
		//draw game here
		for(int i = 0; i < 10 ; i++){
			for(int j = 0; j < 10; j++){
				renderer.renderTile(tile2, new Vector(i*tileSet.getTextureWidth(), j*tileSet.getTextureHeight()));
			}
		}
		game.getBatch().end();
		
		dialog.render(delta);
		message.render(delta);
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
		screenShot.setColor(new Color(0, 0, 0, 0.5f));		
		screenShot.fillRectangle(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Pixmap.setBlending(Blending.None);
		this.screenShot = new Texture(screenShot);
		screenShot.dispose();
	}
}
