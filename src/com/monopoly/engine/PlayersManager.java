package com.monopoly.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.monopoly.exception.DuplicateNameException;
import com.monopoly.exception.EmptyNameException;
import com.monopoly.exception.NullPictureException;
import com.monopoly.player.PlayerInitiate;
import com.monopoly.player.PlayerModel;

import javafx.scene.image.Image;

public class PlayersManager {

    private final PlayerModel playersModel;

    public PlayersManager() {
	playersModel = new PlayerModel();
    }

    public PlayerInitiate addPlayer(String name, boolean isHuman, String gender, Image image) throws DuplicateNameException, EmptyNameException, NullPictureException {
	if (name == null || name.isEmpty()){
	    throw new EmptyNameException();
	}
	if (image == null){
		throw new NullPictureException();
	}
	PlayerInitiate newPlayer = new PlayerInitiate(name, isHuman, gender, image);
	if (playersModel.isPlayerExists(newPlayer)) {
	    throw new DuplicateNameException();
	} else {
	    playersModel.addPlayer(newPlayer);
	}
	
	return newPlayer;
    }

    public Collection<PlayerInitiate> getPlayers(){
	ArrayList<PlayerInitiate> sortedPlayersList = new ArrayList<>(playersModel.getPlayers());
	Collections.sort(sortedPlayersList, new PlayerComparator()) ;
	return sortedPlayersList;
    }

    static class PlayerComparator implements Comparator<PlayerInitiate> {
	@Override
	public int compare(PlayerInitiate o1, PlayerInitiate o2) {
	    return o1.getName().compareTo(o2.getName());
	}
    }
}
