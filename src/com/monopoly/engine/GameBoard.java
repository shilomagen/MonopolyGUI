package com.monopoly.engine;

import java.util.LinkedList;

import com.monopoly.cell.CellModel;
import com.monopoly.cell.FreeParkingCell;
import com.monopoly.cell.GoToJailCell;
import com.monopoly.cell.JailFreePassCell;
import com.monopoly.cell.PropertyCell;
import com.monopoly.cell.StartCell;
import com.monopoly.cell.SupriseCardCell;
import com.monopoly.cell.TransportationCell;
import com.monopoly.cell.UtilityCell;
import com.monopoly.cell.WarrantCardCell;
import com.monopoly.data.Assets;
import com.monopoly.data.BoardData;
import com.monopoly.data.Card;
import com.monopoly.data.CellData;
import com.monopoly.data.City;
import com.monopoly.data.CountryGame;
import com.monopoly.data.Transportation;
import com.monopoly.data.Utility;
import com.monopoly.scenes.MainBoard;
import com.monopoly.utility.CountryColors;
import com.monopoly.utility.PositionHelper;

public class GameBoard {
	private BoardData boardData;
	private Assets gameAssets;
	private MainBoard mainBoard;
	

	public GameBoard(MainBoard mainBoard) {
		this.mainBoard = mainBoard;
	}

	public void loadTheBoard(CellModel cellModel, LinkedList<Card> surpriseDeck, LinkedList<Card> warrantDeck) {
		boardData = InitiateGame.getBoardData();
		gameAssets = InitiateGame.getAssets();
		LinkedList<CountryGame> theCountries = gameAssets.getTheCountries();
		LinkedList<Transportation> transportation = gameAssets.getTransportation();
		LinkedList<Utility> utilities = gameAssets.getUtility();
		surpriseDeck = InitiateGame.getSupriseCards();
		warrantDeck = InitiateGame.getWarrantCards();
		this.LoadDataOnCells(cellModel, theCountries, transportation, utilities, surpriseDeck, warrantDeck);

	}

	private void LoadDataOnCells(CellModel cellModel , LinkedList<CountryGame> theCountries, LinkedList<Transportation> transportation,
			LinkedList<Utility> utilities, LinkedList<Card> supriseCards, LinkedList<Card> warrantCards) {
		int placeOnBoard = 0;
		int countryCounter = 0, cityCounter = 0;
		int transPlace = 0;
		int utilityPlace = 0;
		int suprisePlace = 0;
		int warrantPlace = 0;
		LinkedList<CellData> cellList = boardData.getTheBoard();
		PositionHelper newHelper = new PositionHelper();

		mainBoard.createBoardLogo();
		for (CellData dataCell : cellList) {
			String cellType = dataCell.getType();
			if (cellType.equals("Start Square")) {
				mainBoard.createStartPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new StartCell("StartCell", placeOnBoard));
				placeOnBoard++;
			} else if (cellType.equals("CITY")) {
				if (cityCounter >= theCountries.get(countryCounter).getCitiesNum()) {
					countryCounter++;
					cityCounter = 0;
				}
				City cityToData = theCountries.get(countryCounter).getCities().get(cityCounter);
				mainBoard.createCityPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow(), cityToData.getCountry(), cityToData.getName(),
						cityToData.getCost(), CountryColors.countryArr[countryCounter]);
				cellModel.addCell(new PropertyCell(cityToData.getName(), placeOnBoard, cityToData));
				cityCounter++;
				placeOnBoard++;
			} else if (cellType.equals("SURPRISE")) {
				mainBoard.createSurprisePane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new SupriseCardCell("SupriseCell", placeOnBoard));
				suprisePlace++;
				placeOnBoard++;
			} else if (cellType.equals("WARRANT")) {
				mainBoard.createWarrantPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new WarrantCardCell("WarrantCell", placeOnBoard));
				warrantPlace++;
				placeOnBoard++;
			} else if (cellType.equals("TRANSPORTATION")) {
				mainBoard.createTransportationPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow(), transportation.get(transPlace).getName(),
						transportation.get(transPlace).getCost());
				cellModel.addCell(new TransportationCell(transportation.get(transPlace).getName(), placeOnBoard,
						transportation.get(transPlace)));
				placeOnBoard++;
				transPlace++;
			} else if (cellType.equals("GotoJail")) {
				mainBoard.createGoToJailPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new GoToJailCell("Go To Jail", placeOnBoard));
				placeOnBoard++;

			} else if (cellType.equals("Parking")) {
				mainBoard.createFreeParkingPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new FreeParkingCell("Free Parking", placeOnBoard));
				placeOnBoard++;
			} else if (cellType.equals("UTILITY")) {
				mainBoard.createUtilityPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow(), utilities.get(utilityPlace).getName(),
						utilities.get(utilityPlace).getCost());
				cellModel.addCell(new UtilityCell(utilities.get(utilityPlace).getName(), placeOnBoard,
						utilities.get(utilityPlace)));
				placeOnBoard++;
				utilityPlace++;
			} else if (cellType.equals("Jail")) {
				mainBoard.createJailPane(PositionHelper.arr.get(placeOnBoard).getCol(),
						PositionHelper.arr.get(placeOnBoard).getRow());
				cellModel.addCell(new JailFreePassCell("Jail Free Pass", placeOnBoard));
				placeOnBoard++;
			}
		}

	}
	

}
