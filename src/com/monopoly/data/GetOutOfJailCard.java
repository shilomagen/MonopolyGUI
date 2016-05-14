package com.monopoly.data;

import com.monopoly.engine.GameEngine;
import com.monopoly.player.Player;
import com.monopoly.utility.EventTypes;

import javafx.application.Platform;

public class GetOutOfJailCard extends Card {
	public GetOutOfJailCard(String cardText, int cardCode){
		super(cardText, cardCode);
	}

	
	public String toString(){
		return cardText;
	}


	@Override
	public void surpriseAction(Player currentPlayer) {
		Platform.runLater(()->{
			GameEngine.addEventToEngine(EventTypes.GET_OUT_OF_JAIL_CARD);
		});
	}

	@Override
	public void warrantAction(Player currentPlayer) {}



	
	
}
