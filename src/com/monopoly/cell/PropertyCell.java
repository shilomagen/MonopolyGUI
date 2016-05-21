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
		data.setPropertyCell(this);
		

	}

	public Player getOwner() {
		return owner;
	}

	@Override
	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}

	public int getNumOfHouses() {
		return numOfHouses;
	}

	public void setNumOfHouses(int numOfHouses) {
		this.numOfHouses = numOfHouses;
	}

	public City getData() {
		return data;
	}

	public void setData(City data) {
		this.data = data;
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
