package com.monopoly.application;
	
import com.monopoly.engine.GameBoard;
import com.monopoly.engine.InitiateGame;
import com.monopoly.scenes.MainBoard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("../scenes/LandingScene.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
			InitiateGame.xmlLoad();
			AnchorPane root = new AnchorPane();
			MainBoard mainBoard = new MainBoard();
			mainBoard.createBasicGridPane();
			
			BorderPane borderPane = mainBoard.getBorderPane();
			root.getChildren().add(borderPane);
			GameBoard gameBoard = new GameBoard(mainBoard);
			gameBoard.loadTheBoard();
			Scene scene = new Scene(root,1024, 768);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
	        borderPane.prefWidthProperty().bind(scene.widthProperty());
			scene.getStylesheets().add(getClass().getResource("/com/monopoly/stylesheets/board-stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("testttt");
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
