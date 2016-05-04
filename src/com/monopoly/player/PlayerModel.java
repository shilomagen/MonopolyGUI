package com.monopoly.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlayerModel {
	private Set<PlayerInitiate> players;

	public PlayerModel() {
		players = new HashSet<>();
	}

	public boolean isPlayerExists(PlayerInitiate player) {
		return players.contains(player);
	}

	public void addPlayer(PlayerInitiate player) {
		players.add(player);
	}

	// public void setPlayers (Collection<Player> players) {
	// players.clear();
	// players.addAll(players);
	// }

	public Collection<PlayerInitiate> getPlayers() {
		return Collections.unmodifiableSet(players);
	}

	public boolean isPlayersFullyLoaded() {
		return this.players.size() == 6;
	}

	public boolean isThereHumanPlayer() {
		for (PlayerInitiate player : players){
			if (player.isHuman())
				return true;
		}
		return false;
	}
	


}
