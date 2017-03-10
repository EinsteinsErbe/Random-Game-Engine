package ch.magejo.randomgame.gui.dialogs;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.Window;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Window which can print multiple textlines, can i.e. be used for descriptions of entities in games
 * @author M.Geissbberger
 *
 */
public class MessageWindow extends Window{
	private final int border = 70;
	private final int lineSpeperator = 20;
	private int yOffset;
	private int height;
	
	/**
	 * create a default message window
	 * @param message	= text to show
	 * @param game		= instance of main-class
	 * @param height	= height of the window (will be opened on the under screen side)
	 */
	public MessageWindow(String message, Main game, int height) {
		super(new Vector(0, 0), (int) game.getScreenSize().x, height, game);
		this.height = height;
		initText(message, game);			
	}
	
	/**
	 * create a MessageWindow and open it
	 * @param message	= text to show
	 * @param game		= instance of main-class
	 * @param height	= height of the window (will be opened on the under screen side)
	 * @param yOffset	= offset from the top of the window to the text (y)
	 */
	public MessageWindow(String message, Main game, int height, int yOffset) {
		super(new Vector(0, 0), (int) game.getScreenSize().x, height, game);
		this.yOffset = yOffset;
		this.height = height;
		initText(message, game);
	}
	
	/**
	 * devide the Text into multiple textlines which get drawn speratly
	 * @param message
	 * @param game
	 */
	private void initText(String message, Main game){
		clearTexts();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, message);
		int height = (int) layout.height;
		int lines  = 1;
		if(layout.width >= game.getScreenSize().x - 2*border){
			lines = (int) (layout.width/(game.getScreenSize().x - 2*border))+1;
		}
		
		layout.setText(font, "");
		StringBuilder line = new StringBuilder();
		int cursorPosition = 0;
		int lineLength = 0;
		for(int i = 0; i < lines; i++){
			lineLength = 0;
			while(layout.width < game.getScreenSize().x - 2*border){
				line.append( message.charAt(cursorPosition));
				layout.setText(font, line.toString());
				cursorPosition++;
				lineLength ++;
				if(cursorPosition >= message.length()){
					break;
				}
			}
			layout.setText(font, "");
			line.setLength(lineLength);
			addText(line.toString(), new Vector(border, this.height - border - i*(height+lineSpeperator) - yOffset));	
			line.delete(0, cursorPosition);
		}
	}
	
	/**
	 * change the actual text in  the window
	 * @param s
	 */
	public void changeText(String s){
		initText(s, game);
	}
}
