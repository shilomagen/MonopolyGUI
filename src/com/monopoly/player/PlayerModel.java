package com.monopoly.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.monopoly.utility.GameConstants;

public class PlayerModel {
	private Set<Player> players;

	public PlayerModel() {
		players = new HashSet<>();
	}

	public boolean isPlayerExists(PlayerData player) {
		return players.contains(player);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	// public void setPlayers (Collection<Player> players) {
	// players.clear();
	// players.addAll(players);
	// }

	public Collection<Player> getPlayers() {
		return Collections.unmodifiableSet(players);
	}

	public boolean isPlayersFullyLoaded() {
		return this.players.size() == GameConstants.MAX_PLAYERS;
	}

	public boolean isThereHumanPlayer() {
		for (Player player : players){
			if (player.getData().isHuman())
				return true;
		}
		return false;
	}
	


}
