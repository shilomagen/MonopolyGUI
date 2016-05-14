package com.monopoly.data;

import com.monopoly.engine.GameEngine;
//import com.monopoly.engine.GameController;
import com.monopoly.player.Player;
import com.monopoly.utility.EventTypes;

import javafx.application.Platform;

public class GoToCard extends Card {
	String cellToGo;

	public GoToCard(String cardText, int cardCode, String cellToGo) {
		super(cardText, cardCode);
		this.cellToGo = cellToGo;
	}

	@Override
	public void surpriseAction(Player currentPlayer) {
		if (this.cellToGo.equals("START")) {
			Platform.runLater(()->{
				GameEngine.addEventToEngine(EventTypes.GO_TO_START_CELL);
			});
		} else if (this.cellToGo.equals("NEXT_SURPRISE")) {
			Platform.runLater(()->{
				GameEngine.addEventToEngine(EventTypes.GO_TO_NEXT_SURPRISE);
			});
		}
	}

	@Override
	public void warrantAction(Player currentPlayer) {
		if (this.cellToGo.equals("JAIL")) {
			Platform.runLater(()->{
				GameEngine.addEventToEngine(EventTypes.ON_GO_TO_JAIL);
			});
			Platform.runLater(()->{
				GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_WARRANT_DECK);
			});
		} else if (this.cellToGo.equals("NEXT_WARRANT")) {
			Platform.runLater(()->{
				GameEngine.addEventToEngine(EventTypes.GO_TO_NEXT_WARRANT);
			});
		}
	}
	
	public String toString(){
		return String.format(this.cardText, this.cellToGo);
	}


}
