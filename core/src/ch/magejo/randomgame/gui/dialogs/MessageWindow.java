package ch.magejo.randomgame.gui.dialogs;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.Window;
import ch.magejo.randomgame.utils.math.Vector;

/**
 * Window which can print multiple textlines, can i.e. be used for descriptions of entities in games
 * @author M.Geissbberger
 *
 */
public class MessageWindow extends Window{
	private int borderRight = 70;
	private int borderLeft = 70;
	private int borderTop = 70;
	private int lineSpeperator = 20;
	private int yOffset;
	private int height;

	private String message;

	/**
	 * create a default message window
	 * @param message	= text to show
	 * @param game		= instance of main-class
	 * @param height	= height of the window (will be opened on the under screen side)
	 */
	public MessageWindow(String message, Main game, int height, TextureRegion screenShot) {
		super(new Vector(0, 0), (int) game.getScreenSize().x, height, game, screenShot);
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
	public MessageWindow(String message, Main game, int height, int yOffset, TextureRegion screenShot) {
		super(new Vector(0, 0), (int) game.getScreenSize().x, height, game, screenShot);
		this.yOffset = yOffset;
		this.height = height;
		initText(message, game);
	}

	/**
	 * create a MessageWindow and open it at a different position than defaults 0,0
	 * @param message	= text to show
	 * @param game		= instance of main-class
	 * @param height	= height of the window (will be opened on the under screen side)
	 * @param yOffset	= offset from the top of the window to the text (y)
	 */
	public MessageWindow(String message, Vector position, Main game, int height, int yOffset, TextureRegion screenShot) {
		super(position, (int) game.getScreenSize().x, height, game, screenShot);
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
		this.message = message;
		clearTexts();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, message);
		int height = (int) layout.height;
		int lines  = 1;
		if(layout.width >= game.getScreenSize().x - borderRight - borderLeft){
			lines = (int) (layout.width/(game.getScreenSize().x - borderRight - borderLeft))+1;
		}

		layout.setText(font, "");
		StringBuilder line = new StringBuilder();
		int cursorPosition = 0;
		int lineLength = 0;
		for(int i = 0; i < lines; i++){
			lineLength = 0;
			while(layout.width < game.getScreenSize().x - borderRight - borderLeft){
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
			addText(line.toString(), new Vector(borderLeft, this.height - borderTop - i*(height+lineSpeperator) - yOffset), 1);
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

	public void setBorderRight(int borderRight) {
		this.borderRight = borderRight;
	}

	public void setBorderLeft(int borderLeft) {
		this.borderLeft = borderLeft;
	}

	public void setBorderTop(int borderTop) {
		this.borderTop = borderTop;
	}

	public void setLineSeperator(int pixel){
		this.lineSpeperator = pixel;
		initText(message, game);
	}

	@Override
	public void onLayoutChange() {
		initText(message, game);
	}

}
