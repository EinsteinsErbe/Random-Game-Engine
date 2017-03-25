package ch.magejo.randomgame.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;

/**
 * Holds the actual screen of the gamestate. If the screen is changed within the gamestate the other screens
 * are still open in the background and get not deleted
 * @author M.Geissbberger
 *
 */
public class GameState {
	private Main game;
	private Screen activeScreen;
	private RunningGameScreen runningGame;		//running Game gets only created and deleted with the gameState
	private PausedGameScreen pausedGame;
	private DialogScreen	dialog;
	private TradeScreen		trade;
	private TextureRegion 	screenShot;
	
	public GameState(Main game) {
		this.game = game;
		runningGame = new RunningGameScreen(game);
		activeScreen = runningGame;
	}
	
	public Screen getActiveScreen(){
		return activeScreen;
	}
	
	public void changeActiveGameScreen(Screen gameScreen){
		activeScreen = null;
		activeScreen = gameScreen;
		game.setScreen(activeScreen);
	}
	
	public void unpauseGame(){
		activeScreen = null;
		activeScreen = runningGame;
		game.setScreen(activeScreen);
	}
	
	public TextureRegion makeScreenShot(boolean darkedOverlay){
		return runningGame.makeScreenshot(darkedOverlay);
	}	
}
