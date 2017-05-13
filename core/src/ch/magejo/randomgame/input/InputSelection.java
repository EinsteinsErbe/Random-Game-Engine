package ch.magejo.randomgame.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import ch.magejo.randomgame.mecanics.input.Key;

public class InputSelection implements ControllerListener{

	private HashMap<Integer, Key> keyboardAllocation;
	private HashMap<Integer, Key> axisAllocation;
	private HashMap<PovDirection, Key> povAllocation;
	private HashMap<Integer, Key> buttonAllocation;

	private Key key;

	public InputSelection(HashMap<Integer, Key> keyboardAllocation, HashMap<Integer, Key> axisAllocation,
			HashMap<PovDirection, Key> povAllocation, HashMap<Integer, Key> buttonAllocation) {
		this.keyboardAllocation = keyboardAllocation;
		this.axisAllocation = axisAllocation;
		this.povAllocation = povAllocation;
		this.buttonAllocation = buttonAllocation;
	}

	@Override
	public void connected(Controller controller) {

	}

	@Override
	public void disconnected(Controller controller) {

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		removeAllKeys();
		buttonAllocation.put(buttonCode, key);
		key = null;
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

	private void removeAllKeys(){
		removeKey(buttonAllocation.values());
		removeKey(axisAllocation.values());
		removeKey(povAllocation.values());
		removeKey(keyboardAllocation.values());
	}

	private void removeKey(Collection<Key> keys){
		while(keys.remove(key));
	}

	public void setKey(Key key){
		this.key = key;
	}

	public boolean isFinished(){
		return key == null;
	}
}
