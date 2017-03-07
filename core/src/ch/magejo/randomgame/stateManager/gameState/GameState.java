package ch.magejo.randomgame.stateManager.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.stateManager.State;
import ch.magejo.randomgame.stateManager.StateList;

/**
 * Decides if the Game is Paused or not
 * @author M.Geissbberger
 *
 */
public class GameState extends State{
	
	private static RunningStates state = RunningStates.RUNNING;
	
	private RunningGame runningGame;
	private PausedGame pausedGame;
	
	private static Pixmap screenshot;

	public GameState() {
		super(StateList.GAME);
		runningGame = new RunningGame();
		pausedGame = new PausedGame();
	}

	@Override
	protected void update(float delta) {
		if(state.equals(RunningStates.RUNNING)){
			runningGame.update(delta);
		}else{
			pausedGame.update(delta);
		}
	}

	@Override
	protected void render(Renderer2D renderer) {
		if(state.equals(RunningStates.RUNNING)){
			runningGame.render();
		}else{
			//render screenshot
			pausedGame.render();
		}
	}
	
	public static void pauseGame(){
		makeScreenShot();
		state = RunningStates.PAUSED;
	}
	
	private static void makeScreenShot() {
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		
		screenshot = pixmap;
	}

	public static void resumeGame(){
		state = RunningStates.RUNNING;
	}
	
	private enum RunningStates{
		PAUSED, RUNNING
	}
	
	
	
}
