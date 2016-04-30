package com.monopoly.scenes;

import com.monopoly.utility.BoardConsts;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainBoard {
	AnchorPane root;
	AnchorPane gridAnchor;
	GridPane boardPane;
	AnchorPane playersAnchor;

	public MainBoard() {
		root = new AnchorPane();
		root.setPrefHeight(768);
		root.setPrefWidth(1024);

		gridAnchor = new AnchorPane();
		gridAnchor.getStyleClass().add("grid-anchor");
		gridAnchor.setLayoutX(167.0);
		gridAnchor.setLayoutY(124);
		gridAnchor.setPrefHeight(768);
		gridAnchor.setPrefWidth(835);
		AnchorPane.setRightAnchor(gridAnchor, 189.0);
		AnchorPane.setTopAnchor(gridAnchor, 0.0);
		AnchorPane.setBottomAnchor(gridAnchor, 0.0);
		AnchorPane.setLeftAnchor(gridAnchor, 0.0);
		
		boardPane = new GridPane();
		boardPane.setLayoutX(85);
		boardPane.setLayoutY(86);
		boardPane.setPrefHeight(768);
		boardPane.setPrefWidth(835);
		boardPane.getStyleClass().add("general-grid");
		AnchorPane.setBottomAnchor(boardPane, 0.0);
		AnchorPane.setLeftAnchor(boardPane, 0.0);
		AnchorPane.setRightAnchor(boardPane, 0.0);
		AnchorPane.setTopAnchor(boardPane, 0.0);

		gridAnchor.getChildren().add(boardPane);
		this.createBasicGridPane();

		playersAnchor = new AnchorPane();
		playersAnchor.setLayoutX(835);
		playersAnchor.setPrefHeight(768);
		playersAnchor.setPrefWidth(189);
		this.createPlayerPane();
		
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));
		this.createPlayerProfile((VBox)this.playersAnchor.getChildren().get(0));

		
		root.getChildren().addAll(gridAnchor, playersAnchor);

	}

	public void createBasicGridPane() {

		// Create columns
		ColumnConstraints colEdge, colRegular;
		colEdge = new ColumnConstraints();
		colRegular = new ColumnConstraints();

		colEdge.setHgrow(Priority.SOMETIMES);
		colEdge.setMaxWidth(113.85);
		colEdge.setMinWidth(10.0);
		colEdge.setPrefWidth(113.85);
		colEdge.setHalignment(HPos.CENTER);

		colRegular.setHgrow(Priority.SOMETIMES);
		colRegular.setMaxWidth(75.9);
		colRegular.setMinWidth(10.0);
		colRegular.setPrefWidth(75.9);
		colRegular.setHalignment(HPos.CENTER);

		// Create Rows
		RowConstraints rowEdge, rowRegular;
		rowEdge = new RowConstraints();
		rowRegular = new RowConstraints();

		rowEdge.setMaxHeight(104.7);
		rowEdge.setMinHeight(10.0);
		rowEdge.setPrefHeight(104.7);
		rowEdge.setVgrow(Priority.SOMETIMES);
		rowEdge.setValignment(VPos.CENTER);

		rowRegular.setMaxHeight(69.8);
		rowRegular.setMinHeight(10.0);
		rowRegular.setPrefHeight(69.8);
		rowRegular.setVgrow(Priority.SOMETIMES);
		rowRegular.setValignment(VPos.CENTER);

		this.boardPane.getColumnConstraints().add(colEdge);
		this.boardPane.getRowConstraints().add(rowEdge);
		for (int i = 0; i < 8; i++) {
			this.boardPane.getColumnConstraints().add(colRegular);
			this.boardPane.getRowConstraints().add(rowRegular);
		}
		this.boardPane.getColumnConstraints().add(colEdge);
		this.boardPane.getRowConstraints().add(rowEdge);

		// for (int i = 1; i < 9; i++) {
		// this.createCityPane(i, 0, "SHILO", "MANGAM", 22);
		// }
		// for (int i = 1; i < 9; i++) {
		// this.createSurprisePane(i, 9);
		// }
		// for (int i = 1; i < 9; i++) {
		// this.createSurprisePane(0, i);
		// }
		// for (int i = 1; i < 9; i++) {
		// this.createSurprisePane(9, i);
		// }
		//
		// this.createBoardLogo();
		// this.createStartPane();
		// this.createGoToJailPane();
		// this.createFreeParkingPane();
		// this.createJailPane();

	}

	public void createJailPane(int col, int row) {
		StackPane jailPane = new StackPane();
		jailPane.setPrefHeight(200);
		jailPane.setPrefWidth(200);
		ImageView jailImage = new ImageView();
		jailImage.setFitHeight(91);
		jailImage.setFitWidth(91);
		jailImage.setPickOnBounds(true);
		jailImage.setPreserveRatio(true);
		jailImage.setImage(new Image(BoardConsts.IMAGE_URL + "/jail-free-pass.png"));
		jailImage.setRotate(270);
		jailPane.getChildren().add(jailImage);
		StackPane.setAlignment(jailImage, Pos.CENTER);
		this.boardPane.add(jailPane, col, row);
	}

	public void createFreeParkingPane(int col, int row) {
		StackPane freeParkingPane = new StackPane();
		freeParkingPane.setPrefHeight(200);
		freeParkingPane.setPrefWidth(200);
		ImageView freeParkingImage = new ImageView();
		freeParkingImage.setFitHeight(91);
		freeParkingImage.setFitWidth(91);
		freeParkingImage.setPickOnBounds(true);
		freeParkingImage.setPreserveRatio(true);
		freeParkingImage.setImage(new Image(BoardConsts.IMAGE_URL + "/free-parking.png"));
		freeParkingPane.getChildren().add(freeParkingImage);
		StackPane.setAlignment(freeParkingImage, Pos.CENTER);
		this.boardPane.add(freeParkingPane, col, row);

	}

	public void createGoToJailPane(int col, int row) {
		StackPane goToJailPane = new StackPane();
		goToJailPane.setPrefHeight(200);
		goToJailPane.setPrefWidth(200);
		ImageView goToJailImage = new ImageView();
		goToJailImage.setFitHeight(91);
		goToJailImage.setFitWidth(91);
		goToJailImage.setPickOnBounds(true);
		goToJailImage.setPreserveRatio(true);
		goToJailImage.setImage(new Image(BoardConsts.IMAGE_URL + "/gotojail.png"));
		goToJailPane.getChildren().add(goToJailImage);
		StackPane.setAlignment(goToJailImage, Pos.CENTER);
		this.boardPane.add(goToJailPane, col, row);

	}

	public void createStartPane(int col, int row) {
		StackPane startPane = new StackPane();
		startPane.setPrefHeight(200);
		startPane.setPrefWidth(200);
		ImageView startImage = new ImageView();
		startImage.setFitHeight(91);
		startImage.setFitWidth(91);
		startImage.setPickOnBounds(true);
		startImage.setPreserveRatio(true);
		startImage.setImage(new Image(BoardConsts.IMAGE_URL + "/start.png"));
		startPane.getChildren().add(startImage);
		StackPane.setAlignment(startImage, Pos.CENTER);
		this.boardPane.add(startPane, col, row);

	}

	public void assignPaneOnGridByCoordinates(int gridCol, int gridRow, Node paneToAssign) {
		this.boardPane.add(paneToAssign, gridCol, gridRow);
	}

	public void createCityPane(int col, int row, String countryName, String cityName, int cityPrice) {
		SplitPane cityPane = new SplitPane();
		cityPane.getStyleClass().add("city-cell");
		Pane infoPane = new StackPane();
		Label countryLabel = new Label(countryName);
		Label cityLabel = new Label(cityName);
		Label priceLabel = new Label(cityPrice + "$");
		cityLabel.getStyleClass().add("city-label");
		countryLabel.getStyleClass().add("country-label");
		priceLabel.getStyleClass().add("price-label");
		infoPane.getChildren().add(countryLabel);
		infoPane.getChildren().add(cityLabel);
		infoPane.getChildren().add(priceLabel);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.HORIZONTAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_LEFT_RIGHT);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_LEFT_RIGHT);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_UP_LEFT);
			cityLabel.getStyleClass().add("left");
			countryLabel.getStyleClass().add("left");
			priceLabel.getStyleClass().add("left");
			cityPane.getItems().add(infoPane);
			cityPane.getItems().add(new Pane());
			cityPane.getItems().get(1).getStyleClass().add("country-header");

			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.HORIZONTAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_LEFT_RIGHT);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_LEFT_RIGHT);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_DOWN_RIGHT);
			cityLabel.getStyleClass().add("right");
			countryLabel.getStyleClass().add("right");
			priceLabel.getStyleClass().add("right");
			cityPane.getItems().add(new Pane());
			cityPane.getItems().get(0).getStyleClass().add("country-header");
			cityPane.getItems().add(infoPane);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.VERTICAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_UP_DOWN);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_UP_DOWN);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_UP_LEFT);
			cityLabel.getStyleClass().add("top");
			countryLabel.getStyleClass().add("top");
			priceLabel.getStyleClass().add("top");
			cityPane.getItems().add(infoPane);
			cityPane.getItems().add(new Pane());
			// cityPane.getItems().get(1).setStyle("-fx-background-color:yellow;");
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.VERTICAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_UP_DOWN);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_UP_DOWN);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_DOWN_RIGHT);
			cityLabel.getStyleClass().add("bottom");
			countryLabel.getStyleClass().add("bottom");
			priceLabel.getStyleClass().add("bottom");
			cityPane.getItems().add(new Pane());
			cityPane.getItems().add(infoPane);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		}
	}

	public void createBoardLogo() {
		StackPane logoPane = new StackPane();
		logoPane.setPrefHeight(150.0);
		logoPane.setPrefWidth(200.0);
		ImageView logoImage = new ImageView();
		logoPane.getStyleClass().add("logo-pane");
		logoImage.setFitHeight(554.0);
		logoImage.setFitWidth(604.0);
		logoImage.setPickOnBounds(true);
		logoImage.setPreserveRatio(true);
		logoImage.setImage(new Image(BoardConsts.IMAGE_URL + "/main-pic.png"));
		logoPane.getChildren().add(logoImage);
		this.boardPane.add(logoPane, 1, 1, 8, 8);
		this.createCardDecks();
	}

	private void createCardDecks() {
		Pane surprisePane = new Pane();
		surprisePane.setPrefSize(200, 200);
		ImageView surpriseImage = new ImageView(BoardConsts.IMAGE_URL + "/surprise-deck.png");
		surpriseImage.setFitHeight(223);
		surpriseImage.setFitWidth(189);
		surpriseImage.setLayoutX(38);
		surpriseImage.setLayoutY(21);
		surpriseImage.setPickOnBounds(true);
		surpriseImage.setRotate(6.3);
		surprisePane.getChildren().add(surpriseImage);
		this.boardPane.add(surprisePane, 1, 1, 4, 4);

		Pane warrantPane = new Pane();
		warrantPane.setPrefSize(200, 200);
		ImageView warrantImage = new ImageView(BoardConsts.IMAGE_URL + "/warrant-deck.png");
		warrantImage.setFitHeight(223);
		warrantImage.setFitWidth(189);
		warrantImage.setLayoutX(76);
		warrantImage.setLayoutY(29);
		warrantImage.setPickOnBounds(true);
		warrantImage.setRotate(8.0);
		warrantPane.getChildren().add(warrantImage);
		this.boardPane.add(warrantPane, 5, 5, 4, 4);

	}

	public void createTransportationPane(int col, int row, String transName, int transportPrice) {
		StackPane transPane = new StackPane();
		transPane.getStyleClass().add("trans-util-cell");
		ImageView transImage = new ImageView(BoardConsts.IMAGE_URL + "/transportation.png");
		transImage.getStyleClass().add("trans-util-image");
		transImage.setPickOnBounds(true);

		Label transLabel = new Label(transName);
		transLabel.getStyleClass().add("trans-util-label");
		Label transPrice = new Label(transportPrice + "");
		transPrice.getStyleClass().add("trans-util-price");

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("left");
			transImage.getStyleClass().add("left");
			transLabel.getStyleClass().add("left");
			transPrice.getStyleClass().add("left");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transPrice, Pos.CENTER_LEFT);
			transImage.setFitHeight(57);
			transImage.setFitWidth(60);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("right");
			transImage.getStyleClass().add("right");
			transLabel.getStyleClass().add("right");
			transPrice.getStyleClass().add("right");
			StackPane.setAlignment(transImage, Pos.CENTER_LEFT);
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transPrice, Pos.CENTER_RIGHT);
			transImage.setFitHeight(57);
			transImage.setFitWidth(60);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("top");
			transImage.getStyleClass().add("top");
			transLabel.getStyleClass().add("top");
			transPrice.getStyleClass().add("top");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transImage, Pos.BOTTOM_CENTER);
			StackPane.setAlignment(transPrice, Pos.TOP_CENTER);
			transImage.setFitHeight(50);
			transImage.setFitWidth(50);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("bottom");
			transImage.getStyleClass().add("bottom");
			transLabel.getStyleClass().add("bottom");
			transPrice.getStyleClass().add("bottom");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transImage, Pos.TOP_CENTER);
			StackPane.setAlignment(transPrice, Pos.BOTTOM_CENTER);
			transImage.setFitHeight(50);
			transImage.setFitWidth(50);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		}
	}

	public void createUtilityPane(int col, int row, String utilName, int utilPrice) {
		StackPane utilityPane = new StackPane();
		utilityPane.getStyleClass().add("trans-util-cell");
		ImageView utilityImage = new ImageView(BoardConsts.IMAGE_URL + "/utilities.png");
		utilityImage.getStyleClass().add("trans-util-image");
		utilityImage.setPickOnBounds(true);

		Label utilityLabel = new Label(utilName);
		utilityLabel.getStyleClass().add("trans-util-label");
		Label utilityPrice = new Label(utilPrice + "$");
		utilityPrice.getStyleClass().add("trans-util-price");

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("left");
			utilityImage.getStyleClass().add("left");
			utilityLabel.getStyleClass().add("left");
			utilityPrice.getStyleClass().add("left");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityPrice, Pos.CENTER_LEFT);
			utilityImage.setFitHeight(57);
			utilityImage.setFitWidth(60);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("right");
			utilityImage.getStyleClass().add("right");
			utilityLabel.getStyleClass().add("right");
			utilityPrice.getStyleClass().add("right");
			StackPane.setAlignment(utilityImage, Pos.CENTER_LEFT);
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityPrice, Pos.CENTER_RIGHT);
			utilityImage.setFitHeight(57);
			utilityImage.setFitWidth(60);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("top");
			utilityImage.getStyleClass().add("top");
			utilityLabel.getStyleClass().add("top");
			utilityPrice.getStyleClass().add("top");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityImage, Pos.BOTTOM_CENTER);
			StackPane.setAlignment(utilityPrice, Pos.TOP_CENTER);
			utilityImage.setFitHeight(50);
			utilityImage.setFitWidth(50);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("bottom");
			utilityImage.getStyleClass().add("bottom");
			utilityLabel.getStyleClass().add("bottom");
			utilityPrice.getStyleClass().add("bottom");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityImage, Pos.TOP_CENTER);
			StackPane.setAlignment(utilityPrice, Pos.BOTTOM_CENTER);
			utilityImage.setFitHeight(50);
			utilityImage.setFitWidth(50);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		}
	}

	public void createWarrantPane(int col, int row) {
		StackPane warrantPane = new StackPane();
		warrantPane.getStyleClass().add("warrant-pane");
		ImageView warrantImage = new ImageView(BoardConsts.IMAGE_URL + "/warrant.png");
		warrantImage.getStyleClass().add("warrant-image");
		warrantImage.setPickOnBounds(true);

		StackPane.setAlignment(warrantImage, Pos.CENTER);
		warrantPane.getChildren().add(warrantImage);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(50);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("left");
			warrantImage.setRotate(90);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(45);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("right");
			warrantImage.setRotate(270);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(45);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("top");
			warrantImage.setRotate(180);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(50);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("bottom");
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		}
	}

	public void createSurprisePane(int col, int row) {
		StackPane surprisePane = new StackPane();
		surprisePane.getStyleClass().add("warrant-pane");
		ImageView surpriseImage = new ImageView(BoardConsts.IMAGE_URL + "/surprise.png");
		surpriseImage.getStyleClass().add("surprise-image");
		surpriseImage.setPickOnBounds(true);
		StackPane.setAlignment(surpriseImage, Pos.CENTER);
		surprisePane.getChildren().add(surpriseImage);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(50);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("left");
			surpriseImage.setRotate(90);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(45);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("right");
			surpriseImage.setRotate(270);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(45);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("top");
			surpriseImage.setRotate(180);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(50);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("bottom");
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		}
	}

	public AnchorPane getRoot() {
		return this.root;
	}

	public void createPlayerPane(){
		VBox playerVBox = new VBox();
		playerVBox.setPrefSize(189.0, 768.0);
		AnchorPane.setBottomAnchor(playerVBox, 0.0);
		AnchorPane.setLeftAnchor(playerVBox, 0.0);
		AnchorPane.setRightAnchor(playerVBox, 0.0);
		AnchorPane.setTopAnchor(playerVBox, 0.0);
		this.playersAnchor.getChildren().add(playerVBox);
	}
	
	public void createPlayerProfile(VBox playersPane){
		Pane playerProfile = new Pane();
		playerProfile.setPrefSize(200.0, 128.0);
		
		ImageView playerAvatar = new ImageView(BoardConsts.IMAGE_URL + "/players-avatar/Ricketts.png");
		playerAvatar.setFitHeight(70.0);
		playerAvatar.setFitWidth(69.0);
		playerAvatar.setLayoutY(7.0);
		playerAvatar.setLayoutX(60);
		playerAvatar.setPickOnBounds(true);
		playerAvatar.setPreserveRatio(true);
		
		Label playerName = new Label("Eden Swissa");
		playerName.setAlignment(Pos.CENTER);
		playerName.setLayoutX(1.0);
		playerName.setLayoutY(85.0);
		playerName.setPrefSize(187.0, 17.0);
		
		Label playerMoney = new Label("820$");
		playerMoney.setAlignment(Pos.CENTER);
		playerMoney.setLayoutX(1.0);
		playerMoney.setLayoutY(105.0);
		playerMoney.setPrefSize(187.0, 17.0);
		
		ImageView playerStatus = new ImageView(BoardConsts.IMAGE_URL + "/player-on.png");
		playerStatus.setFitHeight(17.0);
		playerStatus.setFitWidth(15.0);
		playerStatus.setLayoutX(169.0);
		playerStatus.setLayoutY(4.0);
		playerStatus.setPickOnBounds(true);
		playerStatus.setPreserveRatio(true);
		
		playerProfile.getChildren().addAll(playerAvatar, playerName, playerMoney,playerStatus);
		playersPane.getChildren().add(playerProfile);
		
	}
	
}

