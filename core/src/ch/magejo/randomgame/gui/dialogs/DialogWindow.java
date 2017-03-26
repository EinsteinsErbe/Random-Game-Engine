package ch.magejo.randomgame.gui.dialogs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.RGTextButton;
import ch.magejo.randomgame.mecanics.entity.creatures.Creature;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.text.Dialog;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Window that opens if the Player talks to a creature, here the dialog can be read and
 * the answers can be chose
 * @author M.Geissbberger
 *
 */
public class DialogWindow extends MessageWindow{

	private Dialog dialog;
	private final static int height = 750;

	public DialogWindow(Main game, TextureRegion screenShot) {
		super("empty", game, height, 100, screenShot);
	}

	/**
	 * open a dialog with creature
	 * @param creature
	 * @param player
	 */
	public void openDialog(Creature creature, Charakter player){
		dialog = DialogManager.openDialog(creature, player);
		changeText(dialog.getActualText());
		drawOptions();
	}

	/**
	 * Draw all available options of the current dialog state on to the screen as buttons
	 */
	private void drawOptions(){
		String[] options = dialog.getOptions();
		clearButtons();
		for(int i = 0; i < options.length; i++){
			addButton(new RGTextButton(options[i]), new Vector(70, i*100), (int) (game.getScreenSize().x-140), 100);
		}
	}

	public void update(){
		if(dialog != null){
			for(int i = 0; i < dialog.getOptions().length; i++){
				if(getTextButton(i).isClicked()){
					dialog.choseOption(i);
					changeText(dialog.getActualText());
					drawOptions();
				}
			}
		}
	}
}
