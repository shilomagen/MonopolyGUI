package com.monopoly.engine;

import java.util.ArrayList;

import com.monopoly.player.Player;
import com.monopoly.scenes.MainBoardController;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.IntPair;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class GameEngine {
	private MainBoardController boardController;
	private PlayersManager playersManager;
	private ObservableList<String> eventList;

	public void setMainBoardController(MainBoardController mainBoardController) {
		this.boardController = mainBoardController;

	}

	public void setPlayersManager(PlayersManager playersManager) {
		this.playersManager = playersManager;

	}

	public void startObserv() {
		this.eventList = FXCollections.observableArrayList();
		this.eventList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(ListChangeListener.Change c) {

				while (c.next()) {
					if (c.wasAdded()) {
						String event = eventList.get(eventList.size()-1);
						eventHandler(event);
					}

					if (c.wasRemoved()) {
						System.out.println("Item Was removed");
						System.out.println(eventList.isEmpty());

					}
				}
			}
		});
	}

	public void addEventToEngine(String eventType) {
		this.eventList.add(eventType);

	}

	public void eventHandler(String str) {
		int currentPlayerIndex = this.playersManager.getCurrentPlayer();
		Player currentPlayer = ((ArrayList<Player>) this.playersManager.getPlayers())
				.get(this.playersManager.getCurrentPlayer());
		switch (str) {
		case EventTypes.PLAY_TURN:
			this.boardController.activatePlayer(currentPlayerIndex);
			this.boardController.informPlayerTurn(currentPlayer);
			this.boardController.activateRoll(true);
			break;
		case EventTypes.ROLL_DICE:
			PairOfDice.roll();
			int firstDie = PairOfDice.getFirstDice();
			int secondDie = PairOfDice.getSecondDice();
			this.boardController.updateDice(firstDie, secondDie);
			this.boardController.activateRoll(false);
			this.boardController.movePlayerIconToSpecificCell(firstDie + secondDie, currentPlayer);
			break;
		}
	}

	public void removeEventFromEngine(String string) {
		this.eventList.remove(string);
	}
	
	public ObservableList<String> getEventList(){
		return this.eventList;
	}
	

}
