package com.monopoly.player;

import java.util.ArrayList;

import com.monopoly.data.Card;
import com.monopoly.data.City;
import com.monopoly.data.Transportation;
import com.monopoly.data.Utility;

public interface Player {
	public void setPosition(int position);
	public boolean canPlay();
	public boolean isBankrupt();
	public boolean isInJail();
	public void setInJail(boolean val);
	public boolean isParked();
	public void setIsParked(boolean val);
	public int getPosition();
	public String getPlayerName();
	public void setMoney(int money);
	public int getMoney();
	public ArrayList<City> getPlayerCities();
	public void setPlayerCities(ArrayList<City> playerCities);
	public ArrayList<Transportation> getPlayerTrans();
	public void setPlayerTrans(ArrayList<Transportation> playerTrans);
	public ArrayList<Utility> getPlayerUtil();
	public void setPlayerUtil(ArrayList<Utility> playerUtil);
//	public boolean getAnswer();
	public void setIsBankrupt(boolean b);
	public boolean isHasFreeJailCard();
	public void setHasFreeJailCard(boolean hasFreeJailCard);
	public void setJailFreeCard(Card freeJailCard);
	public Card getJailFreeCard();
	public ArrayList<Transportation> getTransportation();
	public ArrayList<Utility> getUtilites();
}
