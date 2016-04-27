package com.monopoly.data;

//import com.monopoly.engine.GameController;
import com.monopoly.player.Player;

public class MontaryCard extends Card {
	private int sum;

	public MontaryCard(String cardText, int cardCode, int sum) {
		super(cardText, cardCode);
		this.sum = sum;
	}

	public String toString() {
		return String.format(this.cardText, sum);
	}

//	@Override
//	public void surpriseAction(Player currentPlayer) {
//		if (this.cardCode == 1) {
//			GameController.takeMoneyFromPlayers(currentPlayer, this.sum);
//		} else if (this.cardCode == 2) {
//			currentPlayer.setMoney(currentPlayer.getMoney() + this.sum);
//		}
//		GameController.returnMontaryCardToSurpriseDeck(this);
//
//	}
//
//	@Override
//	public void warrantAction(Player currentPlayer) {
//		if (this.cardCode == 1) {
//			GameController.payToAllPlayers(currentPlayer, this.sum);
//
//		} else if (this.cardCode == 2) {
//			GameController.payToTreasury(currentPlayer,this.sum);
//		}
//		GameController.returnMontaryCardToWarrantDeck(this);
//	}

}
