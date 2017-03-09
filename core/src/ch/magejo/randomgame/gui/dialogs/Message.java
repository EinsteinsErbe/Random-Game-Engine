package ch.magejo.randomgame.gui.dialogs;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.MessageDialog;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.math.Vector;

public class Message extends MessageDialog{
	private int textId;
	private final int border = 50;
	private final int lineSpeperator = 20;
	
	public Message(String message, Main game) {
		super(new Vector(0, 0), (int) game.getScreenSize().x, (int) game.getScreenSize().y/3, game);
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
		int alreadyWritten = 0;
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
			alreadyWritten += cursorPosition;
			textId = addText(line.toString(), new Vector(border, (int) game.getScreenSize().y/3-border - i*(height+lineSpeperator)));	
			line.delete(0, cursorPosition);
		}		
	}
	
	
	public void changeText(String s){
		getText(textId).setText(s);
	}
	

}
