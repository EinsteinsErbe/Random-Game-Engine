package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.TextBox;
import ch.magejo.randomgame.gui.Window;
import ch.magejo.randomgame.input.RGIconButtons;
import ch.magejo.randomgame.input.RGTextButton;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.entity.things.Thing;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.mecanics.trade.TradeManager;
import ch.magejo.randomgame.mecanics.trade.TradeTransaction;
import ch.magejo.randomgame.utils.math.Vector;

public class TradeScreen implements Screen{

	private Main game;
	private Window tradeWindow;

	private int changeInventoryBtnId;
	private int closeBtnId;
	private int tradeBtnId;

	private Charakter trader;
	private Charakter player;

	private boolean isPlayerActive;

	private TextBox thingName;
	private TextBox thingValue;
	private TextBox thingAmount;
	private TextBox thingTradeAmount;

	private Vector topLeft;
	private Vector startInventorList;

	private Texture plusButton;
	private Texture minusButton;

	private Charakter charakter;

	private TradeTransaction tradeTransaction;

	private int[] inventorySlotsAmountPlayer;
	private int[] inventorySlotsAmountTrader;

	private int itemsCounter = 0;


	public TradeScreen(Main game, TextureRegion screenShot, Charakter trader, Charakter player) {
		this.game = game;

		this.trader = trader;
		this.player = player;

		topLeft = new Vector(40, Gdx.graphics.getHeight()-40);

		tradeWindow = new Window(new Vector(20, 20), Gdx.graphics.getBackBufferWidth()-40, Gdx.graphics.getHeight()-40, game, screenShot) {

			@Override
			public void onLayoutChange() {
				//Do nothing
			}
		};

		tradeWindow.setTextSize(1.5f);

		changeInventoryBtnId = tradeWindow.addButton(new RGTextButton(ButtonNames.Change_Trader, game.getTextGenerator()), new Vector(20, 20), 400, 75);
		closeBtnId = tradeWindow.addButton(new RGTextButton(ButtonNames.Close, game.getTextGenerator()), new Vector(Gdx.graphics.getBackBufferWidth()-370, 20), 350, 75);
		tradeBtnId = tradeWindow.addButton(new RGTextButton(ButtonNames.Trade, game.getTextGenerator()), new Vector(440, 20), 350, 75);

		thingName = new TextBox(new Vector(topLeft.x+40, topLeft.y - 150), 20, 1.5f, true);
		thingAmount = new TextBox(new Vector(topLeft.x+830, topLeft.y - 150), 20, 1.5f, true);
		thingValue = new TextBox(new Vector(topLeft.x+1130, topLeft.y - 150), 20, 1.5f, true);
		thingTradeAmount = new TextBox(new Vector(topLeft.x+1300, topLeft.y - 150), 20, 1.5f, true);

		plusButton = new Texture("UI/Buttons/Button_Plus.png");
		minusButton = new Texture("UI/Buttons/Button_Minus.png");

		inventorySlotsAmountPlayer = new int[player.getInventorySlots()];
		inventorySlotsAmountTrader = new int[trader.getInventorySlots()];

		changeInventory();
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tradeWindow.render(delta);
		game.getEventLogger().render(game.getBatch());

		renderInventory();
	}

	private void renderInventory() {
		thingName.render(game.getBatch());
		thingAmount.render(game.getBatch());
		thingValue.render(game.getBatch());
		thingTradeAmount.render(game.getBatch());
	}

	private void update() {
		if(tradeWindow.getTextButton(changeInventoryBtnId).isClicked()){
			isPlayerActive = !isPlayerActive;
			changeInventory();
		}

		if(tradeWindow.getTextButton(closeBtnId).isClicked()){
			changeScreen();
		}

		if(itemsCounter > 0){
			for(int i = 0; i < itemsCounter; i++){
				if(tradeWindow.getIconButton(i*2).isClicked()){
					if(isPlayerActive){
						if(inventorySlotsAmountPlayer[i] +1 <= player.getInventorySlotInfos(itemsCounter-i-1).getAmount()){
							inventorySlotsAmountPlayer[i]++;
						}
					}else{
						if(inventorySlotsAmountTrader[i] +1 <= trader.getInventorySlotInfos(itemsCounter-i-1).getAmount()){
							inventorySlotsAmountTrader[i]++;
						}
					}
					changeAmounts();
				}
				if(tradeWindow.getIconButton(i*2+1).isClicked()){
					if(isPlayerActive){
						if(inventorySlotsAmountPlayer[i] -1 >= 0 ){
							inventorySlotsAmountPlayer[i]--;
						}
					}else{
						if(inventorySlotsAmountTrader[i] -1 >= 0 ){
							inventorySlotsAmountTrader[i]--;
						}
					}
					changeAmounts();
				}
			}
		}

		if(tradeWindow.getTextButton(tradeBtnId).isClicked()){

			Thing offer = null;
			int offerAmount = 0;
			Thing request = null;
			int requestAmount = 0;

			for(int i = 0; i < player.getInventorySlots(); i++){
				if(inventorySlotsAmountPlayer[i] > 0){
					offer = player.getInventorySlotInfos(player.getInventorySlots()-i-1);
					offerAmount = inventorySlotsAmountPlayer[i];
				}
			}

			for(int i = 0; i < trader.getInventorySlots(); i++){
				if(inventorySlotsAmountTrader[i] > 0){
					request = trader.getInventorySlotInfos(trader.getInventorySlots()-i-1);
					requestAmount = inventorySlotsAmountTrader[i];
				}
			}

			TradeTransaction trade;

			if(offer != null && request != null){
				trade = new TradeTransaction(player, trader, offer, offerAmount, request, requestAmount, 0);
				game.addEvent(TradeManager.getNextTradeText(trade), new Color(1, 1, 1, 1));
				inventorySlotsAmountPlayer = new int[player.getInventorySlots()];
				inventorySlotsAmountTrader = new int[trader.getInventorySlots()];
				changeInventory();
			}
		}
	}


	private void changeInventory() {
		tradeWindow.clearTexts();
		if(isPlayerActive){
			charakter = player;
		}else{
			charakter = trader;
		}

		thingName.clear();
		thingAmount.clear();
		thingValue.clear();

		tradeWindow.clearIconButtons();

		tradeWindow.addText(charakter.getName() + "'s goods:", new Vector(topLeft.x, topLeft.y-50), 1f);
		tradeWindow.addText("|                           Name                           |", new Vector(topLeft.x, topLeft.y-50-50), 1f);
		tradeWindow.addText(" Amount |", new Vector(topLeft.x+800, topLeft.y-50-50), 1f);
		tradeWindow.addText(" Val.   |", new Vector(topLeft.x+1100, topLeft.y-50-50), 1f);
		tradeWindow.addText(" Trade  |", new Vector(topLeft.x+1400, topLeft.y-50-50), 1f);

		itemsCounter = 0;

		for(int i = 0; i < charakter.getInventorySlots(); i++){
			thingName.addTextLine( charakter.getInventorySlotInfos(i).getName(), new Color(0, 0, 0, 1));
			thingAmount.addTextLine(""+charakter.getInventorySlotInfos(i).getAmount(), new Color(0, 0, 0, 1));
			thingValue.addTextLine(""+charakter.getInventorySlotInfos(i).getValue(), new Color(0, 0, 0, 1));
			tradeWindow.addButton(new RGIconButtons(plusButton), new Vector(topLeft.x+1350 +250 , topLeft.y - 260 - i*53), 50, 50);
			tradeWindow.addButton(new RGIconButtons(minusButton), new Vector(topLeft.x+1350 +315 , topLeft.y - 260 - i*53), 50, 50);
			itemsCounter++;
		}

		changeAmounts();
	}

	private void changeAmounts(){
		thingTradeAmount.clear();
		for(int i = 0; i < charakter.getInventorySlots(); i++){
			if(isPlayerActive){
				thingTradeAmount.addTextLine("" + inventorySlotsAmountPlayer[itemsCounter-i-1], new Color(0, 0, 0, 1));
			}else{
				thingTradeAmount.addTextLine("" + inventorySlotsAmountTrader[itemsCounter-i-1], new Color(0, 0, 0, 1));
			}
		}
	}


	private void changeScreen(){
		tradeWindow.dispose();
		TradeManager.close();
		game.setScreen(game.getGameState().openLastDialog());
	}

	@Override
	public void resize(int width, int height) {	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
