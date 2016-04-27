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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;


public class MainBoard {
	private BorderPane borderPane;
	private GridPane gridPane;
	
	public MainBoard(){
		this.borderPane = new BorderPane();
		this.borderPane.getStyleClass().add("board-border-pane");
		this.borderPane.setPrefHeight(768.0);
		this.borderPane.setPrefWidth(1024.0);
		this.gridPane = new GridPane();
		this.borderPane.setCenter(this.gridPane);
		this.gridPane.getStyleClass().add("general-grid");
		this.gridPane.setPrefHeight(606.0);
		this.gridPane.setPrefWidth(737.0);
		this.gridPane.setMaxHeight(606.0);
		this.gridPane.setMaxWidth(737.0);
	}
	
	public void createBasicGridPane(){
		
		//Create columns
		ColumnConstraints colEdge, colRegular;
		colEdge = new ColumnConstraints();
		colRegular = new ColumnConstraints();
		
		colEdge.setHgrow(Priority.SOMETIMES);
		colEdge.setMaxWidth(121.12863159179688);
		colEdge.setMinWidth(10.0);
		colEdge.setPrefWidth(134.0);
		colEdge.setHalignment(HPos.CENTER);
	
		
		colRegular.setHgrow(Priority.SOMETIMES);
		colRegular.setMaxWidth(73.7);
		colRegular.setMinWidth(10.0);
		colRegular.setPrefWidth(73.7);
		colRegular.setHalignment(HPos.CENTER);
		
		//Create Rows
		RowConstraints rowEdge, rowRegular;
		rowEdge = new RowConstraints();
		rowRegular = new RowConstraints();
		
		rowEdge.setMaxHeight(121.12863159179688);
		rowEdge.setMinHeight(10.0);
		rowEdge.setPrefHeight(90.9);
		rowEdge.setVgrow(Priority.SOMETIMES);
		rowEdge.setValignment(VPos.CENTER);
		
		rowRegular.setMaxHeight(60.6);
		rowRegular.setMinHeight(10.0);
		rowRegular.setPrefHeight(49.0);
		rowRegular.setVgrow(Priority.SOMETIMES);
		rowRegular.setValignment(VPos.CENTER);
		
		this.gridPane.getColumnConstraints().add(colEdge);
		this.gridPane.getRowConstraints().add(rowEdge);
		for (int i=0;i<8;i++){
			this.gridPane.getColumnConstraints().add(colRegular);
			this.gridPane.getRowConstraints().add(rowRegular);
		}
		this.gridPane.getColumnConstraints().add(colEdge);
		this.gridPane.getRowConstraints().add(rowEdge);
		
		for (int i=1;i<9;i++){
				this.createUtilityPane(i, 0);
		}
		for (int i=1;i<9;i++){
			this.createUtilityPane(i, 9);
		}
		for (int i=1;i<9;i++){
			this.createUtilityPane(0, i);
		}
		for (int i=1;i<9;i++){
			this.createUtilityPane(9, i);
		}
		
		this.createBoardLogo();
		this.createStartPane();
		this.createGoToJailPane();
		this.createFreeParkingPane();
		this.createJailPane();
		
		
	}
	
	public void createJailPane() {
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
		this.gridPane.add(jailPane, 0, 9);
	}

	public void createFreeParkingPane() {
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
		this.gridPane.add(freeParkingPane, 0, 0);
		
	}


	public void createGoToJailPane() {
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
		this.gridPane.add(goToJailPane, 9, 0);

	}


	public void createStartPane() {
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
		this.gridPane.add(startPane, 9, 9);
		
	}


	public void assignPaneOnGridByCoordinates(int gridCol,int gridRow, Node paneToAssign){
		this.gridPane.add(paneToAssign, gridCol, gridRow);
	}
	
	public void createCityPane(int col, int row){
		 SplitPane cityPane = new SplitPane();
		 cityPane.getStyleClass().add("city-cell");
		 Pane infoPane = new StackPane();
		 Label countryLabel = new Label("Country");
		 Label cityLabel = new Label("City");
		 Label priceLabel = new Label("155$");
		 cityLabel.getStyleClass().add("city-label");
		 countryLabel.getStyleClass().add("country-label");
		 priceLabel.getStyleClass().add("price-label");
		 infoPane.getChildren().add(countryLabel);
		 infoPane.getChildren().add(cityLabel);
		 infoPane.getChildren().add(priceLabel);

		 
		 if (col == 0){ //LEFT SIDE OF MONOPOLY BOARD
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
		 }
		 else if (col==9) { //RIGHT SIDE OF MONOPOLY BOARD
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
		 }
		 else if (row==0){//UPPER SIDE OF MONOPOLY BOARD
			 cityPane.setOrientation(Orientation.VERTICAL);
			 cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_UP_DOWN);
			 cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_UP_DOWN);
			 cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_UP_LEFT);
			 cityLabel.getStyleClass().add("top");
			 countryLabel.getStyleClass().add("top");
			 priceLabel.getStyleClass().add("top");
			 cityPane.getItems().add(infoPane); 
			 cityPane.getItems().add(new Pane());
//			 cityPane.getItems().get(1).setStyle("-fx-background-color:yellow;");
			 this.assignPaneOnGridByCoordinates(col, row, cityPane);
		 } 
		 else if (row == 9){ //LOWER SIDE OF MONOPOLY BOARD
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
	
	public BorderPane getBorderPane(){
		return this.borderPane;
	}
	
	public void createBoardLogo(){
		StackPane logoPane = new StackPane();
		logoPane.setPrefHeight(150.0);
		logoPane.setPrefWidth(200.0);
		ImageView logoImage = new ImageView();
		logoImage.setFitHeight(426.0);
		logoImage.setFitWidth(498.0);
		logoImage.setPickOnBounds(true);
		logoImage.setPreserveRatio(true);
		logoImage.setImage(new Image(BoardConsts.IMAGE_URL + "/main-pic.png"));
		logoPane.getChildren().add(logoImage);
		this.gridPane.add(logoPane, 1, 1, 8, 8);
	}
	
	
	public void createTransportationPane(int col, int row){
			StackPane transPane = new StackPane();
			transPane.getStyleClass().add("trans-util-cell");
			ImageView transImage = new ImageView(BoardConsts.IMAGE_URL + "/transportation.png");
			transImage.getStyleClass().add("trans-util-image");
			transImage.setPickOnBounds(true);
			
			Label transLabel = new Label("Trans");
			transLabel.getStyleClass().add("trans-util-label");
			Label transPrice = new Label("120$");
			transPrice.getStyleClass().add("trans-util-price");


		 if (col == 0){ //LEFT SIDE OF MONOPOLY BOARD
			 transPane.getStyleClass().add("left");
			 transImage.getStyleClass().add("left");
			 transLabel.getStyleClass().add("left");
			 transPrice.getStyleClass().add("left");
			 StackPane.setAlignment(transLabel, Pos.CENTER);
			 StackPane.setAlignment(transPrice, Pos.CENTER_LEFT);
			 transImage.setFitHeight(57);
			 transImage.setFitWidth(60);
			 transPane.getChildren().addAll(transImage, transLabel,transPrice);
			 this.assignPaneOnGridByCoordinates(col, row, transPane);
			 
		 }
		 else if (col==9) { //RIGHT SIDE OF MONOPOLY BOARD
			 transPane.getStyleClass().add("right");
			 transImage.getStyleClass().add("right");
			 transLabel.getStyleClass().add("right");
			 transPrice.getStyleClass().add("right");
			 StackPane.setAlignment(transImage, Pos.CENTER_LEFT);
			 StackPane.setAlignment(transLabel, Pos.CENTER);
			 StackPane.setAlignment(transPrice, Pos.CENTER_RIGHT);
			 transImage.setFitHeight(57);
			 transImage.setFitWidth(60);
			 transPane.getChildren().addAll(transImage, transLabel,transPrice);
			 this.assignPaneOnGridByCoordinates(col, row, transPane);
		 }
		 else if (row==0){//UPPER SIDE OF MONOPOLY BOARD
			 transPane.getStyleClass().add("top");
			 transImage.getStyleClass().add("top");
			 transLabel.getStyleClass().add("top");
			 transPrice.getStyleClass().add("top");
			 StackPane.setAlignment(transLabel, Pos.CENTER);
			 StackPane.setAlignment(transImage, Pos.BOTTOM_CENTER);
			 StackPane.setAlignment(transPrice, Pos.TOP_CENTER);
			 transImage.setFitHeight(50);
			 transImage.setFitWidth(50);
			 transPane.getChildren().addAll(transImage, transLabel,transPrice);
			 this.assignPaneOnGridByCoordinates(col, row, transPane);
		 } 
		 else if (row == 9){ //LOWER SIDE OF MONOPOLY BOARD
			 transPane.getStyleClass().add("bottom");
			 transImage.getStyleClass().add("bottom");
			 transLabel.getStyleClass().add("bottom");
			 transPrice.getStyleClass().add("bottom");
			 StackPane.setAlignment(transLabel, Pos.CENTER);
			 StackPane.setAlignment(transImage, Pos.TOP_CENTER);
			 StackPane.setAlignment(transPrice, Pos.BOTTOM_CENTER);
			 transImage.setFitHeight(50);
			 transImage.setFitWidth(50);
			 transPane.getChildren().addAll(transImage, transLabel,transPrice);
			 this.assignPaneOnGridByCoordinates(col, row, transPane);
		 }
	}
	
	public void createUtilityPane(int col, int row){
		StackPane utilityPane = new StackPane();
		utilityPane.getStyleClass().add("trans-util-cell");
		ImageView utilityImage = new ImageView(BoardConsts.IMAGE_URL + "/utilities.png");
		utilityImage.getStyleClass().add("trans-util-image");
		utilityImage.setPickOnBounds(true);
		
		Label utilityLabel = new Label("Util");
		utilityLabel.getStyleClass().add("trans-util-label");
		Label utilityPrice = new Label("100$");
		utilityPrice.getStyleClass().add("trans-util-price");


	 if (col == 0){ //LEFT SIDE OF MONOPOLY BOARD
		 utilityPane.getStyleClass().add("left");
		 utilityImage.getStyleClass().add("left");
		 utilityLabel.getStyleClass().add("left");
		 utilityPrice.getStyleClass().add("left");
		 StackPane.setAlignment(utilityLabel, Pos.CENTER);
		 StackPane.setAlignment(utilityPrice, Pos.CENTER_LEFT);
		 utilityImage.setFitHeight(57);
		 utilityImage.setFitWidth(60);
		 utilityPane.getChildren().addAll(utilityImage, utilityLabel,utilityPrice);
		 this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		 
	 }
	 else if (col==9) { //RIGHT SIDE OF MONOPOLY BOARD
		 utilityPane.getStyleClass().add("right");
		 utilityImage.getStyleClass().add("right");
		 utilityLabel.getStyleClass().add("right");
		 utilityPrice.getStyleClass().add("right");
		 StackPane.setAlignment(utilityImage, Pos.CENTER_LEFT);
		 StackPane.setAlignment(utilityLabel, Pos.CENTER);
		 StackPane.setAlignment(utilityPrice, Pos.CENTER_RIGHT);
		 utilityImage.setFitHeight(57);
		 utilityImage.setFitWidth(60);
		 utilityPane.getChildren().addAll(utilityImage, utilityLabel,utilityPrice);
		 this.assignPaneOnGridByCoordinates(col, row, utilityPane);
	 }
	 else if (row==0){//UPPER SIDE OF MONOPOLY BOARD
		 utilityPane.getStyleClass().add("top");
		 utilityImage.getStyleClass().add("top");
		 utilityLabel.getStyleClass().add("top");
		 utilityPrice.getStyleClass().add("top");
		 StackPane.setAlignment(utilityLabel, Pos.CENTER);
		 StackPane.setAlignment(utilityImage, Pos.BOTTOM_CENTER);
		 StackPane.setAlignment(utilityPrice, Pos.TOP_CENTER);
		 utilityImage.setFitHeight(50);
		 utilityImage.setFitWidth(50);
		 utilityPane.getChildren().addAll(utilityImage, utilityLabel,utilityPrice);
		 this.assignPaneOnGridByCoordinates(col, row, utilityPane);
	 } 
	 else if (row == 9){ //LOWER SIDE OF MONOPOLY BOARD
		 utilityPane.getStyleClass().add("bottom");
		 utilityImage.getStyleClass().add("bottom");
		 utilityLabel.getStyleClass().add("bottom");
		 utilityPrice.getStyleClass().add("bottom");
		 StackPane.setAlignment(utilityLabel, Pos.CENTER);
		 StackPane.setAlignment(utilityImage, Pos.TOP_CENTER);
		 StackPane.setAlignment(utilityPrice, Pos.BOTTOM_CENTER);
		 utilityImage.setFitHeight(50);
		 utilityImage.setFitWidth(50);
		 utilityPane.getChildren().addAll(utilityImage, utilityLabel,utilityPrice);
		 this.assignPaneOnGridByCoordinates(col, row, utilityPane);
	 }
}
	

}




	
