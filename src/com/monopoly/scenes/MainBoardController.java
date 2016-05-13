package com.monopoly.scenes;

import org.xml.sax.SAXException;

import com.monopoly.engine.GameBoard;
import com.monopoly.engine.GameEngine;
import com.monopoly.engine.InitiateGame;
import com.monopoly.engine.PlayersManager;
import com.monopoly.player.Player;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.GameConstants;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainBoardController {
	private MainBoard mainBoard;
	private AnchorPane root;
	private Scene mainBoardScene;
	private PlayersManager playersManager;
	private SceneManager sceneManager;
	private GameEngine gameEngine;
	private VBox playersVBox;

	public MainBoardController(SceneManager sceneManager) {
		try {
			InitiateGame.xmlLoad();
			mainBoard = new MainBoard();
			root = mainBoard.getRoot();
			GameBoard gameBoard = new GameBoard(mainBoard);
			gameBoard.loadTheBoard();
			this.mainBoardScene = new Scene(root, 1024, 768);
			this.mainBoardScene.getStylesheets()
					.add(getClass().getResource("/com/monopoly/stylesheets/board-stylesheet.css").toExternalForm());
			this.sceneManager = sceneManager;

		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	public void registerButtonEvents() {
		Button rollButton = this.mainBoard.getRollButton();
		System.out.println(this.gameEngine);
		System.out.println(gameEngine);
		rollButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	
            	gameEngine.addEventToEngine(EventTypes.ROLL_DICE);
            }
        });
		
	}

	public Scene getMainBoardScene() {
		return this.mainBoardScene;
	}

	public void setPlayersManager(PlayersManager playersManager) {
		this.playersManager = playersManager;
	}

	public void addPlayersToMainBoard() {
		this.playersVBox = (VBox) mainBoard.getPlayersAnchorPane().getChildren().get(0);

		for (Player player : playersManager.getPlayers()) {
			this.movePlayerIconToSpecificCell(GameConstants.START, player);
			this.mainBoard.createPlayerProfile(playersVBox, player);
		}
		this.mainBoard.getRoot().getChildren().add(this.mainBoard.getPlayersAnchorPane());

	}

	public void movePlayerIconToSpecificCell(int diceRes, Player player) {
		int newLocation = (player.getPosition() + diceRes) % GameConstants.TOTAL_CELL;
		int currentPlayerPositionToDelete = player.getPosition();
		FlowPane currentPlayerBox = mainBoard.getPlayerBox(currentPlayerPositionToDelete);
		ImageView currentImageView = this.findImageViewByImage(currentPlayerBox, player.getData().getIcon());
		currentPlayerBox.getChildren().remove(currentImageView);
		ImageView icon = new ImageView(player.getData().getIcon());
		icon.setFitHeight(30);
		icon.setFitWidth(29);
		icon.setLayoutY(10);
		icon.setLayoutX(10);
		icon.setPickOnBounds(true);
		icon.setPreserveRatio(true);
		FlowPane playerBox = mainBoard.getPlayerBox(newLocation);
		playerBox.getChildren().add(icon);
	}



	private ImageView findImageViewByImage(FlowPane currentPlayerBox, Image icon) {
		for (Node node : currentPlayerBox.getChildren()){
			if (((ImageView)node).getImage() == icon){
				return (ImageView)node;
			}
			
		}
		return null;
	}

	public void activatePlayer(int currentPlayer) {
		this.playersVBox.getChildren().get(currentPlayer).getStyleClass().add("active");
	}

	public void informPlayerTurn(Player currentPlayer) {
		String playerName = currentPlayer.getPlayerName();
		this.mainBoard.getScreenConsole().setText(playerName + " Roll the dice!");
		

	}

	public void activateRoll(boolean val) {
		this.mainBoard.getRollButton().setDisable(!val);
		
	}

	public void updateDice(int firstDie, int secondDie) {
		Pane dicePane = this.mainBoard.getDicePane();
		ImageView firstDieImg = (ImageView)dicePane.getChildren().get(0);
		ImageView secondDieImg = (ImageView)dicePane.getChildren().get(1);
		
		firstDieImg.setImage(new Image("file:src/com/monopoly/assets/dice/dice-"+ firstDie +".png"));
		secondDieImg.setImage(new Image("file:src/com/monopoly/assets/dice/dice-"+ secondDie +".png"));
		
		
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		
	}

}
