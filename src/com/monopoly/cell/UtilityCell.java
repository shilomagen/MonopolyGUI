package com.monopoly.cell;

import com.monopoly.data.Utility;
import com.monopoly.player.Player;

public class UtilityCell extends Cell implements Buyable{
	private Player owner;
	private boolean hasOwner;
	private Utility data;
	
	public UtilityCell(String name, int position, Utility data) {
		super(name, position);
		this.owner = null;
		this.hasOwner = false;
		this.data = data;
		
	}
//	@Override
//	public void playAction(Player currentPlayer) {
//		if (this.hasOwner){
//			if (currentPlayer == this.owner)
//				GameView.printToUser(currentPlayer.getPlayerName() + " You already own this transportation center");
//			else {
//				if (GameController.hasOwnerOwnAllUtilities(this.owner)){
//					GameController.payFine(currentPlayer, this.owner, InitiateGame.getAssets().getUtilityStayCost());
//				} else {
//					GameController.payFine(currentPlayer, this.owner , data.getStayCost());
//				}
//			}
//		}else {
//			GameController.buyUtilityProcedure(currentPlayer, this);
//		}
//	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public boolean isHasOwner() {
		return hasOwner;
	}
	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}
	public Utility getData() {
		return data;
	}
	public void setData(Utility data) {
		this.data = data;
	}

}
