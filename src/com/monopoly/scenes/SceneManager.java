/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.scenes;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Eden
 */
public class SceneManager extends Application {

	private static final String USER_LANDING_SCENE_PATH = "LandingScene.fxml";
	private static final String USER_CREATING_SCENE_PATH = "userCreatingScene.fxml";

	private Stage primaryStage;

	private Pane landingSceneLayout;
	private Scene landingScene;
	private LandingSceneController landingSceneController;

	private UserCreatingSceneController userCreatingSceneController;
	private Scene userCreatingScene;
	private Pane userCreatingSceneLayout;

	private BooleanProperty finishedInit = new SimpleBooleanProperty(this, "Finish Init");

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Start Screen");
		this.loadLandingScreen();
		this.loadUserCreatingScreen();

		primaryStage.setScene(landingScene);
		primaryStage.show();
//		// listen to create player button on start screen
//		landingSceneController.getCreatePlayerProperty().addListener((source, oldValue, newValue) -> {
//			if (newValue) {
//				showUserCreatingScreen();
//			}
//		});
		// listen to continue button on create player screen
		userCreatingSceneController.getFinishedInit().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				landingSceneController.activateStartGame(true);
			}
		});
	}

	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	public void loadLandingScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SceneManager.class.getResource(USER_LANDING_SCENE_PATH));
		landingSceneLayout = loader.load();
		landingSceneController = (LandingSceneController) loader.getController();
		landingSceneController.setManager(this);
		this.landingScene = new Scene(landingSceneLayout);
	}

	public void loadUserCreatingScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SceneManager.class.getResource(USER_CREATING_SCENE_PATH));
		userCreatingSceneLayout = loader.load();
		userCreatingSceneController = (UserCreatingSceneController) loader.getController();
		userCreatingSceneController.setManager(this);
		this.userCreatingScene = new Scene(userCreatingSceneLayout);
	}

	public void showUserCreatingScreen() {
		this.primaryStage.setScene(userCreatingScene);
	}

	public void showLandingScreen() {
		this.primaryStage.setScene(landingScene);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public Scene getStartScene() {
		return this.landingScene;
	}
	
	public Scene getUserCreationScene(){
		return this.userCreatingScene;
	}
	

	
}
