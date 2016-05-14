package com.monopoly.cell;

import com.monopoly.data.Card;

public class SupriseCardCell extends Cell{
	private Card data;
	public SupriseCardCell(String name, int position){
		super(name, position);
		this.data = null;
	}

//	@Override
//	public void playAction(Player currentPlayer) {
//		GameController.surpriseCardProcedure(this.data, currentPlayer);
//		
//	}
	
	
}
