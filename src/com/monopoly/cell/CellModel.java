package com.monopoly.cell;

import java.util.ArrayList;

import com.monopoly.utility.GameConstants;

public class CellModel {
	private ArrayList<Cell> cells;

	public CellModel() {
		cells = new ArrayList<>();
	};

	public void addCell(Cell c) {
		this.cells.add(c);
	}

	public ArrayList<Cell> getCells() {
		return this.cells;
	}

	public int getNextSurpriseOnBoard(int currentPosition) {
		while (!(cells.get(currentPosition % GameConstants.TOTAL_CELL).getName().equals("SupriseCell"))) {
			currentPosition++;
		}
		return currentPosition % GameConstants.TOTAL_CELL;
	}
	
	public int getPlaceOnBoardByName(String string){
		Cell tempCell;
		for (int i=0;i<cells.size(); ++i){
			tempCell = cells.get(i);
			if (tempCell.getName().equals(string))
				return tempCell.getPosition();
		}
		return 0;
	}

	public int getNextWarrantOnBoard(int currentPosition) {
		while (!(cells.get(currentPosition % GameConstants.TOTAL_CELL).getName().equals("WarrantCell"))) {
			currentPosition++;
		}
		return currentPosition % GameConstants.TOTAL_CELL;
	}
}
