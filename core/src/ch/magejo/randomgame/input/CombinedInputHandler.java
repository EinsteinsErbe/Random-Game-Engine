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
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class CombinedInputHandler implements InputHandler, InputProcessor, ControllerListener{

	private Vector mousePosition;

	private boolean[] pressedState;
	private boolean[] clickedStateBuffer;
	private boolean[] clickedState;

	boolean mousePressedState;
	boolean mouseClickedState;

	private HashMap<Integer, Key> keyboardAllocation;
	private HashMap<Integer, Key> axisAllocation;
	private HashMap<PovDirection, Key> povAllocation;
	private HashMap<Integer, Key> buttonAllocation;

	private float axisSensitivity = 0.5f;

	public CombinedInputHandler() {
		mousePosition = new Vector(0, 0);

		pressedState = new boolean[Key.values().length];
		clickedStateBuffer = new boolean[Key.values().length];
		clickedState = new boolean[Key.values().length];

		mousePressedState = false;
		mouseClickedState= false;

		keyboardAllocation = new HashMap<>();
		keyboardAllocation.put(Keys.UP, Key.UP);
		keyboardAllocation.put(Keys.W, Key.UP);
		keyboardAllocation.put(Keys.DOWN, Key.DOWN);
		keyboardAllocation.put(Keys.S, Key.DOWN);
		keyboardAllocation.put(Keys.LEFT, Key.LEFT);
		keyboardAllocation.put(Keys.A, Key.LEFT);
		keyboardAllocation.put(Keys.RIGHT, Key.RIGHT);
		keyboardAllocation.put(Keys.D, Key.RIGHT);
		keyboardAllocation.put(Keys.ENTER, Key.ENTER);
		keyboardAllocation.put(Keys.P, Key.PAUSE);
		keyboardAllocation.put(Keys.E, Key.ATTACK);
		keyboardAllocation.put(Keys.Q, Key.BLOCK);
		keyboardAllocation.put(Keys.SPACE, Key.INTERACT);
		keyboardAllocation.put(Keys.ESCAPE, Key.ESCAPE);
		keyboardAllocation.put(Keys.CONTROL_LEFT, Key.CTRL);

		axisAllocation = new HashMap<>();
		axisAllocation.put(-1, Key.UP);
		axisAllocation.put(-2, Key.LEFT);
		axisAllocation.put(1, Key.DOWN);
		axisAllocation.put(2, Key.RIGHT);

		povAllocation = new HashMap<>();
		povAllocation.put(PovDirection.north, Key.UP);
		povAllocation.put(PovDirection.west, Key.LEFT);
		povAllocation.put(PovDirection.south, Key.DOWN);
		povAllocation.put(PovDirection.east, Key.RIGHT);

		buttonAllocation = new HashMap<>();
		buttonAllocation.put(0, Key.ATTACK);
		buttonAllocation.put(1, Key.BLOCK);
		buttonAllocation.put(2, Key.INTERACT);
		buttonAllocation.put(3, Key.PAUSE);
		buttonAllocation.put(4, Key.CTRL);
		buttonAllocation.put(5, Key.ESCAPE);
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
		if(keyboardAllocation.containsKey(keycode)){
			pressedState[keyboardAllocation.get(keycode).ordinal()] = true;
			clickedStateBuffer[keyboardAllocation.get(keycode).ordinal()] = true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keyboardAllocation.containsKey(keycode)){
			pressedState[keyboardAllocation.get(keycode).ordinal()] = false;
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
		Log.printLn("connected", controller.getName(), 0);
	}

	@Override
	public void disconnected(Controller controller) {
		Log.printLn("disconnected", controller.getName(), 0);
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		if(buttonAllocation.containsKey(buttonCode)){
			pressedState[buttonAllocation.get(buttonCode).ordinal()] = true;
			clickedStateBuffer[buttonAllocation.get(buttonCode).ordinal()] = true;
		}

		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		if(buttonAllocation.containsKey(buttonCode)){
			pressedState[buttonAllocation.get(buttonCode).ordinal()] = false;
		}

		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		int code = (axisCode+1)*(int)Math.signum(value);
		if(axisAllocation.containsKey(code)){
			if(Math.abs(value)>axisSensitivity){
				pressedState[axisAllocation.get(code).ordinal()] = true;
				clickedStateBuffer[axisAllocation.get(code).ordinal()] = true;
			}
			else{
				pressedState[axisAllocation.get(code).ordinal()] = false;
			}
		}
		if(axisAllocation.containsKey(-code)){
			pressedState[axisAllocation.get(-code).ordinal()] = false;
		}
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		pressedState[Key.UP.ordinal()] = false;
		pressedState[Key.DOWN.ordinal()] = false;
		pressedState[Key.LEFT.ordinal()] = false;
		pressedState[Key.RIGHT.ordinal()] = false;

		switch(value){
		case northEast:
			handlePOV(PovDirection.north);
			handlePOV(PovDirection.east);
			break;
		case northWest:
			handlePOV(PovDirection.north);
			handlePOV(PovDirection.west);
			break;
		case southEast:
			handlePOV(PovDirection.south);
			handlePOV(PovDirection.east);
			break;
		case southWest:
			handlePOV(PovDirection.south);
			handlePOV(PovDirection.west);
			break;
		default:
			handlePOV(value);
		}
		return false;
	}

	private void handlePOV(PovDirection value) {
		if(povAllocation.containsKey(value)){
			pressedState[povAllocation.get(value).ordinal()] = true;
			clickedStateBuffer[povAllocation.get(value).ordinal()] = true;
		}
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		Log.printLn("xSlider: "+sliderCode+" Value: "+value, controller.getName(), 0);
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		Log.printLn("ySlider: "+sliderCode+" Value: "+value, controller.getName(), 0);
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		Log.printLn("Acc: "+accelerometerCode+" Value: "+value.len(), controller.getName(), 0);
		return false;
	}

}
