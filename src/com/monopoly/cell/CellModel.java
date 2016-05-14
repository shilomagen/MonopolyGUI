package com.monopoly.cell;

import java.util.ArrayList;

public class CellModel {
	private ArrayList<Cell> cells;
	
	
	public CellModel() {
		cells = new ArrayList<>();
	};
	
	public void addCell(Cell c){
		this.cells.add(c);
	}
	
	public ArrayList<Cell> getCells() {
		return this.cells;
	}
	
}
