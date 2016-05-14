package com.monopoly.cell;


import com.monopoly.data.Transportation;
import com.monopoly.player.Player;

public class TransportationCell extends Cell implements Buyable{
	private Player owner;
	private boolean hasOwner;
	private Transportation data;
	
	
	public TransportationCell(String name, int position, Transportation data) {
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
//				if (GameController.hasOwnerOwnAllTransportation(this.owner)){
//					GameController.payFine(currentPlayer, this.owner, InitiateGame.getAssets().getTransportationStayCost());
//				} else {
//					GameController.payFine(currentPlayer, this.owner , data.getStayCost());
//				}
//			}
//		}else {
//			GameController.buyTransportationProcedure(currentPlayer, this);
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
	public Transportation getData(){
		return this.data;
	}
	
	
}
