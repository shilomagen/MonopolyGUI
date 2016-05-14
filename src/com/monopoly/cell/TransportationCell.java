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
	@Override
	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}
	public Transportation getData(){
		return this.data;
	}
	@Override
	public int getCost() {
		return this.data.getCost();
	}
	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
		
	}

	@Override
	public boolean isHasOwner() {
		return this.hasOwner;
	}
	
	
}
