package com.monopoly.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.monopoly.exception.DuplicateNameException;
import com.monopoly.exception.EmptyNameException;
import com.monopoly.exception.NoHumanPlayerException;
import com.monopoly.exception.NullPictureException;
import com.monopoly.player.HumanPlayer;
import com.monopoly.player.PcPlayer;
import com.monopoly.player.Player;
import com.monopoly.player.PlayerData;
import com.monopoly.player.PlayerModel;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.GameConstants;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class PlayersManager {

	private final PlayerModel playersModel;
	int currentPlayer=0;
	public PlayersManager() {
		playersModel = new PlayerModel();
	}

	public PlayerData addPlayer(String name, boolean isHuman, String gender, Image image, Image icon)
			throws DuplicateNameException, EmptyNameException, NullPictureException {
		if (name == null || name.isEmpty()) {
			throw new EmptyNameException();
		}
		if (image == null) {
			throw new NullPictureException();
		}
		PlayerData newPlayerData = new PlayerData(name, isHuman, gender, image, icon);
		if (playersModel.isPlayerExists(newPlayerData)) {
			throw new DuplicateNameException();
		} else {
			if (newPlayerData.isHuman()) {
				HumanPlayer newPlayer = new HumanPlayer(newPlayerData);
				playersModel.addPlayer(newPlayer);
			} else {
				PcPlayer newPlayer = new PcPlayer(newPlayerData);
				playersModel.addPlayer(newPlayer);
			}

		}

		return newPlayerData;
	}
	public int getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	public void nextPlayer(){
		this.currentPlayer++;
		this.currentPlayer %= this.getPlayers().size();
//		Platform.runLater(()->{
			GameEngine.addEventToEngine(EventTypes.PLAY_TURN);
//		});
		
	}
	public Collection<Player> getPlayers() {
		ArrayList<Player> sortedPlayersList = new ArrayList<>(playersModel.getPlayers());
		Collections.sort(sortedPlayersList, new PlayerComparator());
		return sortedPlayersList;
	}

	static class PlayerComparator implements Comparator<Player> {
		@Override
		public int compare(Player o1, Player o2) {
			return o1.getData().getName().compareTo(o2.getData().getName());
		}
	}

	public boolean isPlayersFullyLoaded() {
		return true;
	}

	public boolean isThereHumanPlayer() throws NoHumanPlayerException {
		Collection<Player> players = playersModel.getPlayers();
		for (Player player : players) {
			if (player.getData().isHuman())
				return true;
		}
		throw new NoHumanPlayerException();
	}

	public int howManyActivePlayers() {
		int counter = 0;
		for (Player player : this.playersModel.getPlayers()){
			if (!player.isBankrupt())
				counter++;
		}
		return counter;
	}

}
