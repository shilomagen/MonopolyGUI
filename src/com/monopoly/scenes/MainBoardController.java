package com.monopoly.scenes;

import java.util.LinkedList;

import org.xml.sax.SAXException;

import com.monopoly.cell.CellModel;
import com.monopoly.data.Card;
import com.monopoly.engine.GameBoard;
import com.monopoly.engine.GameEngine;
import com.monopoly.engine.InitiateGame;
import com.monopoly.engine.PlayersManager;
import com.monopoly.player.Player;
import com.monopoly.utility.BoardConsts;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.GameConstants;

import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

public class MainBoardController {
	private MainBoard mainBoard;
	private AnchorPane root;
	private Scene mainBoardScene;
	private PlayersManager playersManager;
	private SceneManager sceneManager;
	private GameEngine gameEngine;
	private VBox playersVBox;
	private CellModel cellModel;
	private LinkedList<Card> surpriseDeck;
	private LinkedList<Card> warrantDeck;

	public MainBoardController(SceneManager sceneManager) {
		try {
			InitiateGame.xmlLoad();
			mainBoard = new MainBoard();
			root = mainBoard.getRoot();
			GameBoard gameBoard = new GameBoard(mainBoard);
			this.cellModel = new CellModel();
			this.surpriseDeck = new LinkedList<>();
			this.warrantDeck = new LinkedList<>();
			gameBoard.loadTheBoard(this.cellModel);
			this.surpriseDeck = InitiateGame.getSupriseCards();
			this.warrantDeck = InitiateGame.getWarrantCards();
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
	
	public void refreshPlayersOnMainBoard(){
		VBox playersVBox = this.mainBoard.getPlayersVBox();
		playersVBox.getChildren().clear();
		for (Player player : playersManager.getPlayers()) {
			this.mainBoard.createPlayerProfile(playersVBox, player);
		}
	}

	public void movePlayerIconToSpecificCell(int diceRes, Player player) {
		int newLocation = (player.getPosition() + diceRes) % GameConstants.TOTAL_CELL;
		this.removePlayerIconFromBoard(player);
		ImageView icon = new ImageView(player.getData().getIcon());
		icon.setFitHeight(30);
		icon.setFitWidth(29);
		icon.setLayoutY(10);
		icon.setLayoutX(10);
		icon.setPickOnBounds(true);
		icon.setPreserveRatio(true);
		player.setPosition(newLocation);
		FlowPane playerBox = mainBoard.getPlayerBox(newLocation);
		playerBox.getChildren().add(icon);
	}
        
        public void addHouseToSpecificCell(int location){
            ImageView house = new ImageView("file:src/com/monopoly/assets/house.png");
            house.setFitHeight(20);
            house.setFitWidth(19);
            house.setLayoutY(5);
            house.setLayoutX(5);
            house.setPickOnBounds(true);
            house.setPreserveRatio(true);
            FlowPane houseBox = mainBoard.getHouseBox(location);
            if(location >= 9 && location <=17)
                house.setRotate(90);
            if(location >=18 && location <=27)
                house.setRotate(180);
            if(location >=27 && location <= 35)
                house.setRotate(270);
            houseBox.getChildren().add(house);
        }

	private ImageView findImageViewByImage(FlowPane currentPlayerBox, Image icon) {
		for (Node node : currentPlayerBox.getChildren()) {
			if (((ImageView) node).getImage() == icon) {
				return (ImageView) node;
			}

		}
		return null;
	}

	public void activatePlayer(int currentPlayer, boolean val) {
		if (val)
			this.playersVBox.getChildren().get(currentPlayer).getStyleClass().add("active");
		else 
			this.playersVBox.getChildren().get(currentPlayer).getStyleClass().remove("active");
	}

	public void informPlayerTurn(Player currentPlayer) {
		this.mainBoard.getScreenConsole().setText(currentPlayer.getPlayerName() + " Roll the dice!");
		FadeTransition animation = new FadeTransition();
		animation.setNode(this.mainBoard.getScreenConsole());
		animation.setDuration(Duration.seconds(2));
		animation.setFromValue(0.0);
		animation.setToValue(1.0);
		animation.play();

		

	}

	public void activateRoll(boolean val) {
		this.mainBoard.getRollButton().setDisable(!val);

	}

	public void updateDice(int firstDie, int secondDie) {
		Pane dicePane = this.mainBoard.getDicePane();
		ImageView firstDieImg = (ImageView) dicePane.getChildren().get(0);
		ImageView secondDieImg = (ImageView) dicePane.getChildren().get(1);

		firstDieImg.setImage(new Image("file:src/com/monopoly/assets/dice/dice-" + firstDie + ".png"));
		secondDieImg.setImage(new Image("file:src/com/monopoly/assets/dice/dice-" + secondDie + ".png"));

	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public CellModel getCellModel() {
		return this.cellModel;
	}

	public void showMessage(String string) {
		this.mainBoard.getScreenConsole().setText(string);
		FadeTransition animation = new FadeTransition();
		animation.setNode(this.mainBoard.getScreenConsole());
		animation.setDuration(Duration.seconds(0.4));
		animation.setFromValue(0.0);
		animation.setToValue(1.0);
		animation.play();

	}

	public void setBankruptIndication(Player player) {
		for (Node node : this.playersVBox.getChildren()) {
			// Looking for the player node in the vbox
			if (node.getId() != null && node.getId().equals(player.getPlayerName()+"-player")) {
				for (Node secondNode : ((Pane) node).getChildren()) {
					// looking for the status image in the players pane
					if (secondNode.getId() != null && secondNode.getId().equals(player.getPlayerName() + "-status")) {
						((ImageView) secondNode).setImage(new Image(BoardConsts.IMAGE_URL + "/player-off.png"));
					}
				}
			}
		}

	}

	public void removePlayerIconFromBoard(Player player) {
		int currentPlayerPositionToDelete = player.getPosition();
		FlowPane currentPlayerBox = mainBoard.getPlayerBox(currentPlayerPositionToDelete);
		ImageView currentImageView = this.findImageViewByImage(currentPlayerBox, player.getData().getIcon());
		if (currentImageView != null)
			currentPlayerBox.getChildren().remove(currentImageView);
		// else, player is already bankrupt and he's not on the cell

	}
	
	public LinkedList<Card> getSurpriseDeck(){
		return this.surpriseDeck;
	}
	public LinkedList<Card> getWarrantDeck(){
		return this.warrantDeck;
	}

}
