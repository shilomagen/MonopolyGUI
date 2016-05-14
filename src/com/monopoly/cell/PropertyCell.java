package com.monopoly.cell;

import com.monopoly.data.City;
import com.monopoly.player.Player;

public class PropertyCell extends Cell implements Buyable {
	private Player owner;
	private boolean hasOwner;
	private int numOfHouses;
	private City data;

	public PropertyCell(String name, int position, City data) {
		super(name, position);
		this.owner = null;
		this.hasOwner = false;
		this.numOfHouses = 0;
		this.setData(data);
		
	}

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

	public int getNumOfHouses() {
		return numOfHouses;
	}

	public void setNumOfHouses(int numOfHouses) {
		this.numOfHouses = numOfHouses;
	}

//	@Override
//	public void playAction(Player currentPlayer) {
//		if (this.hasOwner){
//			if (currentPlayer == this.owner){
//				GameController.buyHouseProcedure(this);
//			} else { //needs to pay fine
//				int theFine = GameController.calculateFine(this);
//				GameController.payFine(currentPlayer, this.getOwner(), theFine);
//			}
//		} 
//		else { //the player doesnt have owner
//			GameController.buyPropertyProcedure(currentPlayer, this);
//		}
//	}

	public City getData() {
		return data;
	}

	public void setData(City data) {
		this.data = data;
	}
	



}
