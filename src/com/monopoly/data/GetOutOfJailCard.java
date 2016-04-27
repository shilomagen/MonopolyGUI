package com.monopoly.data;

public class GetOutOfJailCard extends Card {
	public GetOutOfJailCard(String cardText, int cardCode){
		super(cardText, cardCode);
	}

	
	public String toString(){
		return cardText;
	}


//	@Override
//	public void surpriseAction(Player currentPlayer) {
//		currentPlayer.setHasFreeJailCard(true);
//		currentPlayer.setJailFreeCard(this);
//	}
//
//
//	@Override
//	public void warrantAction(Player currentPlayer) {}



	
	
}
