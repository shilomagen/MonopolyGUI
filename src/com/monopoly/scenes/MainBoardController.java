package com.monopoly.scenes;

import org.xml.sax.SAXException;

import com.monopoly.engine.GameBoard;
import com.monopoly.engine.InitiateGame;
import com.monopoly.engine.PlayersManager;
import com.monopoly.player.PlayerInitiate;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainBoardController{
	private MainBoard mainBoard;
	private AnchorPane root;
	private Scene mainBoardScene;
	private PlayersManager playersManager;
	private SceneManager sceneManager;
	
	
	public MainBoardController(SceneManager sceneManager) {
		try {
			InitiateGame.xmlLoad();
			mainBoard = new MainBoard();
			root = mainBoard.getRoot();
			GameBoard gameBoard = new GameBoard(mainBoard);
			gameBoard.loadTheBoard();
			this.mainBoardScene = new Scene(root, 1024, 768);
			this.mainBoardScene.getStylesheets().add(getClass().getResource("/com/monopoly/stylesheets/board-stylesheet.css").toExternalForm());
			this.sceneManager = sceneManager;
			
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Scene getMainBoardScene(){
		return this.mainBoardScene;
	}
	
	public void setPlayersManager(PlayersManager playersManager){
		this.playersManager = playersManager;
	}
	
	public void addPlayersToMainBoard(){
		VBox playersVBox = (VBox)mainBoard.getPlayersAnchorPane().getChildren().get(0);
		for (PlayerInitiate player : playersManager.getPlayers()){
			this.mainBoard.createPlayerProfile(playersVBox, player);
		}
		
		this.mainBoard.getRoot().getChildren().add(this.mainBoard.getPlayersAnchorPane());
		
		
	}
	
	

}
