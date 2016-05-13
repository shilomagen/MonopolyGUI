
package com.monopoly.scenes;

import java.io.IOException;

import com.monopoly.engine.GameEngine;
import com.monopoly.utility.EventTypes;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class SceneManager extends Application {

	private static final String USER_LANDING_SCENE_PATH = "LandingScene.fxml";
	private static final String USER_CREATING_SCENE_PATH = "UserCreatingScene.fxml";

	private Stage primaryStage;

	private Pane landingSceneLayout;
	private Scene landingScene;
	private LandingSceneController landingSceneController;

	private UserCreatingSceneController userCreatingSceneController;
	private Scene userCreatingScene;
	private Pane userCreatingSceneLayout;

	private Scene mainBoardScene;
	private MainBoardController mainBoardController;
	private BooleanProperty finishedInit = new SimpleBooleanProperty(this, "Finish Init");

	private GameEngine gameEngine;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Start Screen");
		this.loadLandingScreen();
		this.loadUserCreatingScreen();
		this.gameEngine = new GameEngine();
		mainBoardController = new MainBoardController(this);
		mainBoardScene = mainBoardController.getMainBoardScene();
		mainBoardController.setPlayersManager(userCreatingSceneController.getPlayersManager());
		mainBoardController.setGameEngine(this.gameEngine);
		mainBoardController.registerButtonEvents();
		this.gameEngine.setMainBoardController(this.mainBoardController);
		this.gameEngine.setPlayersManager(userCreatingSceneController.getPlayersManager());

		primaryStage.setScene(landingScene);
		primaryStage.show();
		userCreatingSceneController.getFinishedInit().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				landingSceneController.activateStartGame(true);
			}
		});
		this.landingSceneController.getStartGame().addListener((source, oldValue, newValue) -> {
			if (newValue){
				this.gameEngine.startObserv();	
				this.gameEngine.addEventToEngine(EventTypes.PLAY_TURN);
			}
		});
	}

	public Stage getPrimaryStage() {
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

	public static void main(String[] args) {
		launch(args);
	}

	public Scene getStartScene() {
		return this.landingScene;
	}

	public Scene getUserCreationScene() {
		return this.userCreatingScene;
	}

	public Scene getMainBoardScene() {
		return this.mainBoardScene;
	}

	public MainBoardController getMainBoardController() {
		return this.mainBoardController;
	}

}
