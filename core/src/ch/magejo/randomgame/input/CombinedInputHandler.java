package ch.magejo.randomgame.input;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import ch.magejo.randomgame.mecanics.input.InputHandler;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.utils.math.Vector;

public class CombinedInputHandler implements InputHandler, InputProcessor, ControllerListener{

	private Vector mousePosition;

	private boolean[] pressedState;
	private boolean[] clickedStateBuffer;
	private boolean[] clickedState;

	boolean mousePressedState;
	boolean mouseClickedState;

	private HashMap<Integer, Key> inputAllocation;

	public CombinedInputHandler() {
		mousePosition = new Vector(0, 0);

		pressedState = new boolean[Key.values().length];
		clickedStateBuffer = new boolean[Key.values().length];
		clickedState = new boolean[Key.values().length];

		mousePressedState = false;
		mouseClickedState= false;

		inputAllocation = new HashMap<>();
		inputAllocation.put(Keys.UP, Key.UP);
		inputAllocation.put(Keys.W, Key.UP);
		inputAllocation.put(Keys.DOWN, Key.DOWN);
		inputAllocation.put(Keys.S, Key.DOWN);
		inputAllocation.put(Keys.LEFT, Key.LEFT);
		inputAllocation.put(Keys.A, Key.LEFT);
		inputAllocation.put(Keys.RIGHT, Key.RIGHT);
		inputAllocation.put(Keys.D, Key.RIGHT);
		inputAllocation.put(Keys.ENTER, Key.ENTER);
		inputAllocation.put(Keys.P, Key.PAUSE);
		inputAllocation.put(Keys.E, Key.ATTACK);
		inputAllocation.put(Keys.Q, Key.BLOCK);
		inputAllocation.put(Keys.SPACE, Key.INTERACT);
		inputAllocation.put(Keys.ESCAPE, Key.ESCAPE);
		inputAllocation.put(Keys.CONTROL_LEFT, Key.CTRL);
	}

	public void update(){
		for(int i=0; i<clickedState.length; i++){
			clickedState[i] = clickedStateBuffer[i];
			clickedStateBuffer[i] = false;
		}
	}

	@Override
	public boolean isPressed(Key key) {
		return pressedState[key.ordinal()];
	}

	@Override
	public boolean isClicked(Key key) {
		return clickedState[key.ordinal()];
	}

	@Override
	public boolean isMouseClicked() {
		return mouseClickedState;
	}

	@Override
	public boolean isMousePressed() {
		return mousePressedState;
	}

	@Override
	public Vector getMousePosition() {
		return mousePosition;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(inputAllocation.containsKey(keycode)){
			pressedState[inputAllocation.get(keycode).ordinal()] = true;
			clickedStateBuffer[inputAllocation.get(keycode).ordinal()] = true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(inputAllocation.containsKey(keycode)){
			pressedState[inputAllocation.get(keycode).ordinal()] = false;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mousePressedState = true;

		mousePosition.x = screenX;
		mousePosition.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mousePressedState = false;

		mousePosition.x = screenX;
		mousePosition.y = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition.x = screenX;
		mousePosition.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {

		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

}
