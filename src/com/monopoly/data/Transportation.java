package com.monopoly.data;

import com.monopoly.cell.TransportationCell;

public class Transportation {
	private final int stayCost;
	private final String name;
	private final int cost;
	private TransportationCell cell;
	
	public Transportation(String name, int stayCost, int cost){
		this.stayCost = stayCost;
		this.name = name;
		this.cost = cost;
		this.cell = null;
	}

	public String getName() {
		return name;
	}

	public int getStayCost() {
		return stayCost;
	}
	public int getCost(){
		return this.cost;
	}

	public void setTransportationCell(TransportationCell transportationCell) {
		this.cell = transportationCell;
		
	}

	public TransportationCell getCell() {
		return this.cell;
	}


	
}
