package com.monopoly.data;

//import com.monopoly.engine.GameController;
import com.monopoly.player.Player;

public class GoToCard extends Card {
	String cellToGo;

	public GoToCard(String cardText, int cardCode, String cellToGo) {
		super(cardText, cardCode);
		this.cellToGo = cellToGo;
	}

//	@Override
//	public void surpriseAction(Player currentPlayer) {
//		if (this.cellToGo.equals("START")) {
//			GameController.setPlayerNewLocation(currentPlayer, "START");
//			GameController.addMoneyToPlayer(currentPlayer, 400);
//		} else if (this.cellToGo.equals("NEXT_SURPRISE")) {
//			int lastLocation = currentPlayer.getPosition();
//			GameController.setPlayerNewLocation(currentPlayer, "NEXT_SURPRISE");
//			if (lastLocation > currentPlayer.getPosition())
//				GameController.addMoneyToPlayer(currentPlayer, 200);
//		}
//		GameController.returnGoToCardToSurpriseDeck(this);
//	}
//
//	@Override
//	public void warrantAction(Player currentPlayer) {
//		if (this.cellToGo.equals("JAIL")) {
//			GameController.setPlayerNewLocation(currentPlayer, "Jail");
//		} else if (this.cellToGo.equals("NEXT_WARRANT")) {
//			GameController.setPlayerNewLocation(currentPlayer, "NEXT_WARRANT");
//		}
//		GameController.returnGoToCardToWarrantDeck(this);
//	}
	
	public String toString(){
		return String.format(this.cardText, this.cellToGo);
	}


}
