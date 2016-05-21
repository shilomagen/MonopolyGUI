package com.monopoly.data;

import com.monopoly.cell.UtilityCell;

public class Utility {
	private final int stayCost;
	private final String name;
	private final int cost;
	private UtilityCell cell;
	
	public Utility(String name, int stayCost, int cost){
		this.stayCost = stayCost;
		this.name = name;
		this.cost = cost;
		this.cell = null;
	}

	public int getStayCost() {
		return stayCost;
	}

	public String getName() {
		return name;
	}
	public int getCost(){
		return this.cost;
	}

	public void setUtilityCell(UtilityCell utilityCell) {
		this.cell = utilityCell;
		
	}

	public UtilityCell getCell() {
		return this.cell;
	}


}
