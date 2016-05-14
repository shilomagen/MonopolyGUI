package com.monopoly.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.monopoly.cell.Buyable;
import com.monopoly.cell.CellModel;
import com.monopoly.cell.PropertyCell;
import com.monopoly.cell.TransportationCell;
import com.monopoly.cell.UtilityCell;
import com.monopoly.data.Assets;
import com.monopoly.data.Card;
import com.monopoly.data.City;
import com.monopoly.data.CountryGame;
import com.monopoly.player.Player;
import com.monopoly.scenes.BuyingHousePopupController;
import com.monopoly.scenes.BuyingPopupController;
import com.monopoly.scenes.MainBoardController;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.GameConstants;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameEngine {
	private MainBoardController boardController;
	private PlayersManager playersManager;
	private static ObservableList<String> eventList;
	private CellModel cellModel;
	private LinkedList<Card> surpriseDeck;
	private LinkedList<Card> warrantDeck;
	private BuyingPopupController buyingPopupController;

	public void setMainBoardController(MainBoardController mainBoardController) {
		this.boardController = mainBoardController;
	}

	public void setPlayersManager(PlayersManager playersManager) {
		this.playersManager = playersManager;

	}

	public void startObserv() {
		eventList = FXCollections.observableArrayList();
		eventList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(ListChangeListener.Change c) {

				while (c.next()) {
					if (c.wasAdded()) {
						String event = eventList.get(eventList.size() - 1);
						try {
							eventHandler(event);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if (c.wasRemoved()) {
						System.out.println("Item Was removed");
						System.out.println(eventList.isEmpty());

					}
				}
			}
		});
	}

	public static void addEventToEngine(String eventType) {
		eventList.add(eventType);

	}

	public void eventHandler(String str) throws IOException {
		int currentPlayerIndex = this.playersManager.getCurrentPlayer();
		Player currentPlayer = ((ArrayList<Player>) this.playersManager.getPlayers())
				.get(this.playersManager.getCurrentPlayer());

		switch (str) {
		case EventTypes.PLAY_TURN:
			this.boardController.activatePlayer(currentPlayerIndex, true);
			this.boardController.activateRoll(true);
			break;
		case EventTypes.TURN_FINISHED:
			this.boardController.activatePlayer(currentPlayerIndex, false);
			this.playersManager.nextPlayer();

			break;
		case EventTypes.ROLL_DICE:
			PairOfDice.roll();
			int firstDie = PairOfDice.getFirstDice();
			int secondDie = PairOfDice.getSecondDice();
			this.boardController.updateDice(firstDie, secondDie);
			this.boardController.activateRoll(false);
			this.boardController.movePlayerIconToSpecificCell(5, currentPlayer);
			break;
		case EventTypes.ON_CITY: {
			PropertyCell cell = (PropertyCell) cellModel.getCells().get(currentPlayer.getPosition());
			if (cell.isHasOwner()) {
				if (currentPlayer == cell.getOwner()) {
					this.buyHouseProcedure(cell);
				} else { // Player need to pay the fine
					int fine = this.calculateFine(cell);
					this.payFine(currentPlayer, cell.getOwner(), fine);
				}
			} else {
				this.buyPropertyProcedure(currentPlayer, cell);
			}
			break;
		}

		case EventTypes.ON_FREE_PARKING:
			this.setPlayerToFreeParking(currentPlayer);
			Platform.runLater(() -> {
				eventList.add(EventTypes.TURN_FINISHED);
			});
			break;
		case EventTypes.ON_JAIL_FREE_PASS:
			this.boardController.showMessage("Luck you " + currentPlayer.getPlayerName() + "! It's a free pass!");
			Platform.runLater(() -> {
				eventList.add(EventTypes.TURN_FINISHED);
			});

			break;
		case EventTypes.ON_GO_TO_JAIL:
			break;
		case EventTypes.ON_TRANSPORTATION: {
			TransportationCell cell = (TransportationCell) cellModel.getCells().get(currentPlayer.getPosition());
			if (cell.isHasOwner()) {
				Player cellOwner = cell.getOwner();
				if (currentPlayer == cellOwner) {
					this.boardController
							.showMessage(currentPlayer.getPlayerName() + " You already own this transportation center");
					Platform.runLater(() -> {
						eventList.add(EventTypes.TURN_FINISHED);
					});
				}

				else {
					if (this.hasOwnerOwnAllTransportation(cellOwner)) {
						this.payFine(currentPlayer, cellOwner, InitiateGame.getAssets().getTransportationStayCost());
					} else {
						this.payFine(currentPlayer, cellOwner, cell.getData().getStayCost());
					}
				}
			} else {
				this.buyTransportationProcedure(currentPlayer, cell);
			}
			break;
		}

		case EventTypes.ON_UTILITY: {
			UtilityCell cell = (UtilityCell) cellModel.getCells().get(currentPlayer.getPosition());
			if (cell.isHasOwner()) {
				Player cellOwner = cell.getOwner();

				if (currentPlayer == cellOwner) {
					this.boardController
							.showMessage(currentPlayer.getPlayerName() + " You already own this utility center");
					Platform.runLater(() -> {
						eventList.add(EventTypes.TURN_FINISHED);
					});
				}

				else {
					if (this.hasOwnerOwnAllUtilities(cellOwner)) {
						this.payFine(currentPlayer, cellOwner, InitiateGame.getAssets().getUtilityStayCost());
					} else {
						this.payFine(currentPlayer, cellOwner, cell.getData().getStayCost());
					}
				}
			} else {
				this.buyUtilityProcedure(currentPlayer, cell);
			}
			break;
		}

		case EventTypes.ON_START:
			this.boardController.showMessage("You are on the START CELL! GET 400 $$$");
			currentPlayer.setMoney(currentPlayer.getMoney() + 400);
//			Platform.runLater(() -> {
//				eventList.add(EventTypes.TURN_FINISHED);
//			});
			break;
		case EventTypes.ON_SUPRISE:
			break;
		case EventTypes.ON_WARRANT:
			break;
		case EventTypes.PLAYER_CANT_BUY_HOUSE:
			this.boardController
					.showMessage("We are so sorry " + currentPlayer.getPlayerName() + ", you cant buy the house");
			break;
		case EventTypes.PLAYER_PAID_FINE: {
			Buyable buyable = (Buyable) this.cellModel.getCells().get(currentPlayer.getPosition());
			this.boardController.showMessage(currentPlayer.getPlayerName() + " Paid to "
					+ buyable.getOwner().getPlayerName() + " " + currentPlayer.getLastFine() + "$");
			break;
		}
		case EventTypes.PLAYER_LOST_GAME:
			for (Player player : playersManager.getPlayers()) {
				if (player.isBankrupt()) {
					this.boardController.setBankruptIndication(player);
					this.boardController.showMessage(player.getPlayerName() + " has lost the game, bye bye!");
					this.boardController.removePlayerIconFromBoard(player);
				}
			}
			break;
		case EventTypes.PLAYER_WANTS_TO_BUY_PROPERTY: {
			PropertyCell cell = (PropertyCell) cellModel.getCells().get(currentPlayer.getPosition());
			this.boardController.showMessage("You've bought " + cell.getName());
			currentPlayer.setMoney(currentPlayer.getMoney() - cell.getData().getCost());
			cell.setHasOwner(true);
			cell.setOwner(currentPlayer);
			break;
		}
		case EventTypes.PLAYER_DIDNT_WANT_TO_BUY:
			this.boardController.showMessage("You chose not to buy this property!");
			break;

		default:
			this.boardController.showMessage(str);
			break;
		}

	}

	public void removeEventFromEngine(String string) {
		eventList.remove(string);
	}

	public ObservableList<String> getEventList() {
		return eventList;
	}

	public void setCellModel(CellModel cellModel) {
		this.cellModel = cellModel;

	}

	private int calculateFine(PropertyCell theCell) {
		int numOfHouses = theCell.getNumOfHouses();
		switch (numOfHouses) {
		case 0:
			return theCell.getData().getStayCost();
		case 1:
			return theCell.getData().getStayCost();
		case 2:
			return theCell.getData().getStayCost2();
		case 3:
			return theCell.getData().getStayCost3();
		default:
			return 0;
		}

	}

	private void payFine(Player payer, Player owner, int theFine) {
		if (payer.getMoney() < theFine) {
			theFine = payer.getMoney();
			this.setPlayerOutOfTheGame(payer);
		} else {
			payer.setMoney(payer.getMoney() - theFine);
		}
		owner.setMoney(owner.getMoney() + theFine);
		payer.setLastFine(theFine);
		eventList.add(EventTypes.PLAYER_PAID_FINE);
		Platform.runLater(() -> {
			eventList.add(EventTypes.TURN_FINISHED);
		});
	}

	private void setPlayerOutOfTheGame(Player loser) {
		loser.setMoney(0);
		loser.setIsBankrupt(true);
		Platform.runLater(() -> {
			eventList.add(EventTypes.PLAYER_LOST_GAME);
		});

	}

	private void buyHouseProcedure(PropertyCell property) throws IOException {
		boolean playerOwnCountry = this.isPlayerOwnCountry(property);
		boolean playerCouldBuyHouse = this.playerCouldBuyHouse(property);
		boolean playerNotHaveMaxHouses = property.getNumOfHouses() < 3;

		if (playerOwnCountry && playerCouldBuyHouse && playerNotHaveMaxHouses) {
			boolean isWantToBuy = this.openBuyingHousePopup(property.getData().getName(),
					property.getData().getHouseCost() + "");
			if (isWantToBuy) {
				this.boardController.showMessage("You've bought house on " + property.getName());
				property.setNumOfHouses(property.getNumOfHouses() + 1);
				property.getOwner().setMoney(property.getOwner().getMoney() - property.getData().getHouseCost());
			} else {
				this.boardController.showMessage("You chose not to buy house on this property!");
			}
		} else {
			this.boardController.showMessage("You can't buy the house, sorry!");
		}

		Platform.runLater(() -> {
			eventList.add(EventTypes.TURN_FINISHED);
		});
	}

	private boolean isPlayerOwnCountry(PropertyCell property) {
		Assets gameAssets = InitiateGame.getAssets();
		int playerCitiesOnCountry = 0;
		LinkedList<CountryGame> theCountries = gameAssets.getTheCountries();
		String countryName = property.getData().getCountry();
		int citiesByCountry = howManyCitiesInCountry(theCountries, countryName);
		if (citiesByCountry == 0) {
			eventList.add("Could not find the country");
		} else {
			playerCitiesOnCountry = getPlayerCitiesInCountry(property.getOwner(), countryName);
		}
		return (playerCitiesOnCountry == citiesByCountry);
	}

	private int howManyCitiesInCountry(LinkedList<CountryGame> theCountries, String countryName) {
		for (CountryGame country : theCountries) {
			if (country.getName().equals(countryName)) {
				return country.getCitiesNum();
			}
		}
		return 0;
	}

	private int getPlayerCitiesInCountry(Player owner, String countryName) {
		ArrayList<City> playerCities = owner.getPlayerCities();
		int cityCounter = 0;
		for (City city : playerCities) {
			if (city.getCountry().equals(countryName))
				cityCounter++;
		}
		return cityCounter;
	}

	private boolean playerCouldBuyHouse(PropertyCell property) {
		return (property.getData().getHouseCost() >= property.getOwner().getMoney());
	}

	private void buyPropertyProcedure(Player currentPlayer, PropertyCell theCell) throws IOException {
		boolean playerCouldBuy = theCell.getData().getCost() <= currentPlayer.getMoney();
		if (playerCouldBuy) {
			this.openPropertyPopup("Property", theCell.getData().getCost() + "", theCell.getName());
		} else {
			this.boardController.showMessage("You can't buy the property, sorry!");
		}
		
	}

	private void setPlayerToFreeParking(Player currentPlayer) {
		this.boardController.showMessage("Oh! " + currentPlayer.getPlayerName() + " You're PARKED! Wait one turn.");
		currentPlayer.setIsParked(true);

	}

	private boolean hasOwnerOwnAllTransportation(Player owner) {
		int numberOfTrans = InitiateGame.getAssets().getTransportation().size();
		return (owner.getTransportation().size() == numberOfTrans);

	}

	private void buyTransportationProcedure(Player currentPlayer, TransportationCell theCell) {
		boolean playerCouldBuy = theCell.getData().getCost() <= currentPlayer.getMoney();
		// POPUP
		// if (playerCouldBuy) {
		// if (GameView.askIfPlayerWantsToBuyTransportation(currentPlayer,
		// theCell)) {
		// GameView.printToUser("You've bought " + theCell.getData().getName() +
		// " Transportation, Congrats!");
		// currentPlayer.setMoney(currentPlayer.getMoney() -
		// theCell.getData().getCost());
		// theCell.setHasOwner(true);
		// theCell.setOwner(currentPlayer);
		// } else {
		// GameView.playerGaveUp();
		// }
		// } else {
		// GameView.printToUser("We are very sorry, but you can't buy the
		// Transportation because of the following reasons");
		// GameView.printToUser("You basically dont have enough money");
		// }
		Platform.runLater(() -> {
			eventList.add(EventTypes.TURN_FINISHED);
		});
	}

	private boolean hasOwnerOwnAllUtilities(Player owner) {
		return (owner.getTransportation().size() == InitiateGame.getAssets().getUtility().size());
	}

	private void buyUtilityProcedure(Player currentPlayer, UtilityCell theCell) {
		boolean playerCouldBuy = theCell.getData().getCost() <= currentPlayer.getMoney();
		// if (playerCouldBuy) {
		// if (GameView.askIfPlayerWantsToBuyUtility(currentPlayer, theCell)) {
		// GameView.printToUser("You've bought " + theCell.getData().getName() +
		// " Utility, Congrats!");
		// currentPlayer.setMoney(currentPlayer.getMoney() -
		// theCell.getData().getCost());
		// theCell.setHasOwner(true);
		// theCell.setOwner(currentPlayer);
		// } else {
		// GameView.playerGaveUp();
		// }
		// } else {
		// GameView.printToUser("We are very sorry, but you can't buy the
		// Transportation because of the following reasons");
		// GameView.printToUser("You basically dont have enough money");
		// }
		//
		Platform.runLater(() -> {
			eventList.add(EventTypes.TURN_FINISHED);
		});
	}

	public void setSurpriseDeck(LinkedList<Card> surpriseDeck) {
		this.surpriseDeck = surpriseDeck;
	}

	public void setWarrantDeck(LinkedList<Card> warrantDeck) {
		this.warrantDeck = warrantDeck;
	}

	private void openPropertyPopup(String property, String price, String nameOfProperty) throws IOException {

		Popup buyingPopUp = new Popup();
		buyingPopUp.setX(500);
		buyingPopUp.setY(150);
		this.buyingPopupController = new BuyingPopupController();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(MainBoardController.class.getResource(GameConstants.POPUP_BUYING_PROPERTY_PATH));
		Pane buyingPopupPane = load.load();
		buyingPopupController = (BuyingPopupController) load.getController();
		buyingPopupController.setProperty(property);
		buyingPopupController.setNameOfProperty(nameOfProperty);
		buyingPopupController.setPrice(price);
		buyingPopUp.getContent().add(buyingPopupPane);
		Stage stage = (Stage) this.boardController.getMainBoardScene().getWindow();
		buyingPopUp.show(stage);
		buyingPopupController.getFinish().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				this.handlePlayerPropertyChoice(this.buyingPopupController);
				buyingPopUp.hide();
			}
		});

	}

	private void handlePlayerPropertyChoice(BuyingPopupController buyingPopupController) {
		boolean isWantToBuy = buyingPopupController.isWantToBuy();
		if (isWantToBuy) {
			Platform.runLater(() -> {
				eventList.add(EventTypes.PLAYER_WANTS_TO_BUY_PROPERTY);
			});
		} else {
			Platform.runLater(() -> {
				eventList.add(EventTypes.PLAYER_DIDNT_WANT_TO_BUY);
			});
		}
		Platform.runLater(() -> {
			eventList.add(EventTypes.TURN_FINISHED);
		});

	}

	public boolean openBuyingHousePopup(String cityName, String price) throws IOException {

		Popup buyingHousePopUp = new Popup();
		buyingHousePopUp.setX(500);
		buyingHousePopUp.setY(150);

		BuyingHousePopupController buyingHousePopup = new BuyingHousePopupController();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(MainBoardController.class.getResource(GameConstants.POPUP_BUYING_HOUSE_PATH));
		Pane buyingHousePopupPane = load.load();
		buyingHousePopup = (BuyingHousePopupController) load.getController();
		buyingHousePopup.setCityLabel(cityName);
		buyingHousePopup.setPriceLabel(price);
		buyingHousePopUp.getContent().add(buyingHousePopupPane);
		Stage stage = (Stage) this.boardController.getMainBoardScene().getWindow();
		buyingHousePopUp.show(stage);
		buyingHousePopup.getFinish().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				buyingHousePopUp.hide();
			}
		});
		return buyingHousePopup.isWantToBuy();
	}
}
