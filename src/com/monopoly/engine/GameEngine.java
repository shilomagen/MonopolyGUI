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
import com.monopoly.data.MontaryCard;
import com.monopoly.player.Player;
import com.monopoly.scenes.BuyingHousePopupController;
import com.monopoly.scenes.BuyingPopupController;
import com.monopoly.scenes.CardPopupController;
import com.monopoly.scenes.MainBoardController;
import com.monopoly.scenes.UseFreeJailCardController;
import com.monopoly.utility.EventTypes;
import com.monopoly.utility.GameConstants;

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
	private static ObservableList<MonopolyEvent> eventList;
	private CellModel cellModel;
	private LinkedList<Card> surpriseDeck;
	private LinkedList<Card> warrantDeck;
	private BuyingPopupController buyingPopupController;
	private BuyingHousePopupController buyingHousePopupController;
	private CardPopupController cardPopupController;
	private UseFreeJailCardController useFreeJailCardController;
	private Card currentCard;
	public static boolean isEventHandlerOn = false;
	private Player currentPlayer;

	public void setMainBoardController(MainBoardController mainBoardController) {
		this.boardController = mainBoardController;
	}

	public void setPlayersManager(PlayersManager playersManager) {
		this.playersManager = playersManager;

	}

	public void startObserv() {
		eventList = FXCollections.observableArrayList();
		isEventHandlerOn = true;
		eventList.addListener(new ListChangeListener<MonopolyEvent>() {

			@Override
			public void onChanged(ListChangeListener.Change c) {

				while (c.next()) {
					if (c.wasAdded()) {
						MonopolyEvent event = eventList.get(eventList.size() - 1);
						System.out.println(event);
						System.out.println(event.getEventID());
						try {
							eventHandler(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if (c.wasRemoved()) {
					System.out.println("Item Was removed");
					System.out.println(eventList.isEmpty());

				}
			}

		});

	}

	public static void addEventToEngine(String eventType) {
		eventList.add(new MonopolyEvent(eventType));

	}

	public void eventHandler(MonopolyEvent event) throws IOException {
		int currentPlayerIndex = this.playersManager.getCurrentPlayer();
		this.currentPlayer = ((ArrayList<Player>) this.playersManager.getPlayers())
				.get(this.playersManager.getCurrentPlayer());

		switch (event.getEventType()) {
		case EventTypes.PLAY_TURN:
			if (this.playersManager.howManyActivePlayers() == 1) {
				// IF ONLY ONE PLAY LAST, HE IS THE WINNER
				// EDEN PUT THE LANDING SCENE
			}
			this.boardController.activatePlayer(currentPlayerIndex, true);
			if (!currentPlayer.isBankrupt()) {
				if (currentPlayer.isParked()) {
					this.boardController.showMessage(
							"Sorry, " + currentPlayer.getPlayerName() + " you are PARKED! Wait for next turn");
					currentPlayer.setIsParked(false);
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
				} else {
					this.boardController.activateRoll(true);
					if (!this.currentPlayer.getData().isHuman()) {
						addEventToEngine(EventTypes.ROLL_DICE);

					}
				}
			} else {
				eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			}
			break;
		case EventTypes.TURN_FINISHED:
			this.boardController.activatePlayer(currentPlayerIndex, false);
			this.boardController.refreshPlayersOnMainBoard();
			this.playersManager.nextPlayer();

			break;
		case EventTypes.ROLL_DICE:
			PairOfDice.roll();
			int firstDie = PairOfDice.getFirstDice();
			int secondDie = PairOfDice.getSecondDice();
			this.boardController.updateDice(firstDie, secondDie);
			this.boardController.activateRoll(false);
			if (currentPlayer.isInJail() && firstDie != secondDie) {
				this.boardController
						.showMessage("Sorry, " + currentPlayer.getPlayerName() + " you are JAIL! Wait for a double");
				eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			} else if (currentPlayer.isInJail() && firstDie == secondDie) {
				currentPlayer.setInJail(false);
				this.boardController.showMessage("Congrats " + currentPlayer.getPlayerName() + ", You're out of JAIL");
				this.boardController.movePlayerIconToSpecificCell(firstDie + secondDie, currentPlayer);
			} else {
				this.boardController.movePlayerIconToSpecificCell(firstDie + secondDie, currentPlayer);
			}

			break;
		case EventTypes.ON_CITY: {

			PropertyCell cell = (PropertyCell) cellModel.getCells().get(currentPlayer.getPosition());
			if (cell.isHasOwner()) {
				if (currentPlayer == cell.getOwner()) {
					this.buyHouseProcedure(cell);

				} else { // Player need to pay the fine
					int fine = this.calculateFine(cell);
					this.payFine(currentPlayer, cell.getOwner(), fine);
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
				}
			} else {
				this.buyPropertyProcedure(currentPlayer, cell);

			}

			break;
		}

		case EventTypes.ON_FREE_PARKING:
			this.setPlayerToFreeParking(currentPlayer);
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		case EventTypes.ON_JAIL_FREE_PASS:
			if (eventList.get(eventList.size() - 3).getEventType() != EventTypes.ON_GO_TO_JAIL) {
				this.boardController.showMessage("Lucky you " + currentPlayer.getPlayerName() + "! It's a free pass!");
				eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			}

			break;
		case EventTypes.ON_GO_TO_JAIL:
			if (currentPlayer.isHasFreeJailCard()) {
				this.openAskPlayerIfWantsToUseJailFreePopup();
			} else {
				currentPlayer.setInJail(true);
				this.setPlayerNewLocation(currentPlayer, "Jail");
				this.boardController.showMessage(currentPlayer.getPlayerName() + " Go to jail, wait for DOUBLE!");
				eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			}

			break;
		case EventTypes.ON_TRANSPORTATION: {
			TransportationCell cell = (TransportationCell) cellModel.getCells().get(currentPlayer.getPosition());
			if (cell.isHasOwner()) {
				Player cellOwner = cell.getOwner();
				if (currentPlayer == cellOwner) {
					this.boardController
							.showMessage(currentPlayer.getPlayerName() + " You already own this transportation center");
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
				}

				else {
					if (this.hasOwnerOwnAllTransportation(cellOwner)) {
						this.payFine(currentPlayer, cellOwner, InitiateGame.getAssets().getTransportationStayCost());
					} else {
						this.payFine(currentPlayer, cellOwner, cell.getData().getStayCost());
					}
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
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
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
				} else {
					if (this.hasOwnerOwnAllUtilities(cellOwner)) {
						this.payFine(currentPlayer, cellOwner, InitiateGame.getAssets().getUtilityStayCost());
					} else {
						this.payFine(currentPlayer, cellOwner, cell.getData().getStayCost());
					}
					eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
				}
			} else {
				this.buyUtilityProcedure(currentPlayer, cell);
			}
			break;
		}

		case EventTypes.ON_START:

			this.boardController.showMessage("You are on the START CELL! GET 400 $$$");
			currentPlayer.setMoney(currentPlayer.getMoney() + 400);
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));

			break;
		case EventTypes.ON_SUPRISE: {
			this.currentCard = this.getSurpriseCard();
			this.openCardPopup("surprise", this.currentCard.toString());
			break;

		}

		case EventTypes.ON_WARRANT:
			this.currentCard = this.getWarrantCard();
			this.openCardPopup("warrant", this.currentCard.toString());
			break;
		case EventTypes.PLAYER_CANT_BUY_HOUSE:
			this.boardController
					.showMessage("We are so sorry " + currentPlayer.getPlayerName() + ", you cant buy the house");
			break;
		case EventTypes.PLAYER_PAID_FINE: {
			this.boardController.showMessage(currentPlayer.getPlayerName() + " Paid to "
					+ currentPlayer.getPaidPlayerName() + " " + currentPlayer.getLastFine() + "$");
			break;
		}
		case EventTypes.PLAYER_LOST_GAME:
			for (Player player : playersManager.getPlayers()) {
				if (player.isBankrupt()) {
					player.releasePlayerAssets();
					this.boardController.setBankruptIndication(player);
					this.boardController.showMessage(player.getPlayerName() + " has lost the game, bye bye!");
					this.boardController.removePlayerIconFromBoard(player);
				}
			}
			break;
		case EventTypes.PLAYER_WANTS_TO_BUY_BUYABLE: {
			Buyable cell = (Buyable) cellModel.getCells().get(currentPlayer.getPosition());
			this.boardController.showMessage("You've bought " + cell.getName());
			currentPlayer.setMoney(currentPlayer.getMoney() - cell.getCost());
			cell.setHasOwner(true);
			cell.setOwner(currentPlayer);

			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.PLAYER_DIDNT_WANT_TO_BUY:
			this.boardController.showMessage("You chose not to buy this property!");
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		case EventTypes.PLAYER_WANTS_TO_BUY_HOUSE: {
			PropertyCell cell = (PropertyCell) cellModel.getCells().get(currentPlayer.getPosition());
			this.boardController.showMessage("You've bought house on " + cell.getName());
			cell.setNumOfHouses(cell.getNumOfHouses() + 1);
			cell.getOwner().setMoney(cell.getOwner().getMoney() - cell.getData().getHouseCost());
			this.boardController.addHouseToSpecificCell(currentPlayer.getPosition());
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.TAKE_MONEY_FROM_ALL_PLAYERS: {
			this.takeMoneyFromPlayers(currentPlayer, ((MontaryCard) this.currentCard).getSum());
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.TAKE_MONEY_FROM_JACKPOT: {
			int sum = ((MontaryCard) this.currentCard).getSum();
			currentPlayer.setMoney(currentPlayer.getMoney() + sum);
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.GO_TO_START_CELL: {
			this.boardController.movePlayerIconToSpecificCell((-1) * currentPlayer.getPosition(), currentPlayer);

			break;
		}
		case EventTypes.GO_TO_NEXT_SURPRISE: {
			int lastLocation = currentPlayer.getPosition();
			this.setPlayerNewLocation(currentPlayer, "NEXT_SURPRISE");
			if (lastLocation > currentPlayer.getPosition()) {
				this.boardController.showMessage("You've passed on start, get 200$!");
				currentPlayer.setMoney(currentPlayer.getMoney() + 200);
			}
			break;
		}
		case EventTypes.GET_OUT_OF_JAIL_CARD: {
			currentPlayer.setHasFreeJailCard(true);
			currentPlayer.setJailFreeCard(this.currentCard);
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}

		case EventTypes.RETURN_CARD_TO_WARRANT_DECK: {
			this.returnToWarrantDeck();
			break;
		}
		case EventTypes.RETURN_CARD_TO_SURPRISE_DECK: {
			this.returnSurpriseCardToDeck();
			break;
		}
		case EventTypes.GO_TO_NEXT_WARRANT: {
			this.setPlayerNewLocation(currentPlayer, "NEXT_WARRANT");

			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.PAY_TO_ALL_PLAYERS: {
			int sum = ((MontaryCard) this.currentCard).getSum();
			int activePlayers = this.playersManager.howManyActivePlayers();
			int totalSum = sum * activePlayers;
			if (currentPlayer.getMoney() < totalSum) {
				totalSum = currentPlayer.getMoney();
				sum = totalSum / activePlayers;
				this.payFineToEveryPlayer(currentPlayer, sum);
				eventList.add(new MonopolyEvent(EventTypes.PLAYER_LOST_GAME));
			} else {
				payFineToEveryPlayer(currentPlayer, sum);
			}
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.PAY_TO_JACKPOT: {
			int sum = ((MontaryCard) this.currentCard).getSum();
			if (currentPlayer.getMoney() < sum) {
				eventList.add(new MonopolyEvent(EventTypes.PLAYER_LOST_GAME));
			} else {
				currentPlayer.setMoney(currentPlayer.getMoney() - sum);
				this.boardController.showMessage(currentPlayer.getPlayerName() + " Paid " + sum + " To Jackpot!");
			}
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		}
		case EventTypes.PLAYER_WANTS_TO_USE_CARD:
			currentPlayer.setHasFreeJailCard(false);
			this.returnSurpriseCardToDeck(currentPlayer.getJailFreeCard());
			currentPlayer.setJailFreeCard(null);
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		case EventTypes.PLAYER_DIDNT_WANT_TO_USE_CARD:
			this.boardController.showMessage("You chose not to use the card!");
			currentPlayer.setInJail(true);
			this.setPlayerNewLocation(currentPlayer, "Jail");
			this.boardController.showMessage(currentPlayer.getPlayerName() + " Go to jail, wait for DOUBLE!");
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
			break;
		default:
			this.boardController.showMessage(event.toString());
			break;
		}

	}

	public void removeEventFromEngine(String string) {
		eventList.remove(string);
	}

	public ObservableList<MonopolyEvent> getEventList() {
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
		if (!owner.isBankrupt()) {
			if (payer.getMoney() < theFine) {
				theFine = payer.getMoney();
				this.setPlayerOutOfTheGame(payer);
			} else {
				payer.setMoney(payer.getMoney() - theFine);
			}
			owner.setMoney(owner.getMoney() + theFine);
			payer.setLastFine(theFine, owner);
		}

		eventList.add(new MonopolyEvent(EventTypes.PLAYER_PAID_FINE));

	}

	private void setPlayerOutOfTheGame(Player loser) {
		loser.setMoney(0);
		loser.setIsBankrupt(true);
		eventList.add(new MonopolyEvent(EventTypes.PLAYER_LOST_GAME));

	}

	private void buyHouseProcedure(PropertyCell property) throws IOException {
		boolean playerOwnCountry = this.isPlayerOwnCountry(property);
		boolean playerCouldBuyHouse = this.playerCouldBuyHouse(property);
		boolean playerNotHaveMaxHouses = property.getNumOfHouses() < 3;
		if (playerOwnCountry && playerCouldBuyHouse && playerNotHaveMaxHouses) {
			this.openBuyingHousePopup(property.getData().getName(), property.getData().getHouseCost() + "");
		} else {
			this.boardController.showMessage("You can't buy the house, sorry!");
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
		}

	}

	private boolean isPlayerOwnCountry(PropertyCell property) {
		Assets gameAssets = InitiateGame.getAssets();
		int playerCitiesOnCountry = 0;
		LinkedList<CountryGame> theCountries = gameAssets.getTheCountries();
		String countryName = property.getData().getCountry();
		int citiesByCountry = howManyCitiesInCountry(theCountries, countryName);
		if (citiesByCountry == 0) {
			eventList.add(new MonopolyEvent(EventTypes.ERROR));
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
			this.openGeneralBuyingPopup("Property", theCell.getData().getCost() + "", theCell.getName());
		} else {
			this.boardController.showMessage("You can't buy the property, sorry!");
			eventList.add(new MonopolyEvent(EventTypes.TURN_FINISHED));
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

	private void buyTransportationProcedure(Player currentPlayer, TransportationCell theCell) throws IOException {
		boolean playerCouldBuy = theCell.getData().getCost() <= currentPlayer.getMoney();
		if (playerCouldBuy) {
			this.openGeneralBuyingPopup("Transportation", theCell.getData().getCost() + "", theCell.getName());
		} else
			this.boardController.showMessage("You can't buy the utility, sorry!");

	}

	private boolean hasOwnerOwnAllUtilities(Player owner) {
		return (owner.getTransportation().size() == InitiateGame.getAssets().getUtility().size());
	}

	private void buyUtilityProcedure(Player currentPlayer, UtilityCell theCell) throws IOException {
		boolean playerCouldBuy = theCell.getData().getCost() <= currentPlayer.getMoney();
		if (playerCouldBuy) {
			this.openGeneralBuyingPopup("Utility", theCell.getData().getCost() + "", theCell.getName());
		} else
			this.boardController.showMessage("You can't buy the utility, sorry!");

	}

	public void setSurpriseDeck(LinkedList<Card> surpriseDeck) {
		this.surpriseDeck = surpriseDeck;
	}

	public void setWarrantDeck(LinkedList<Card> warrantDeck) {
		this.warrantDeck = warrantDeck;
	}

	private void openGeneralBuyingPopup(String property, String price, String nameOfProperty) throws IOException {

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
				this.handlePlayerBuyableChoice(this.buyingPopupController);
				buyingPopUp.hide();
			}
		});
		if (!this.currentPlayer.getData().isHuman()) {
			this.buyingPopupController.yesButtonOnAction();
		}
	}

	private void handlePlayerBuyableChoice(BuyingPopupController buyingPopupController) {
		boolean isWantToBuy = buyingPopupController.isWantToBuy();
		if (isWantToBuy) {
			eventList.add(new MonopolyEvent(EventTypes.PLAYER_WANTS_TO_BUY_BUYABLE));

		} else {

			eventList.add(new MonopolyEvent(EventTypes.PLAYER_DIDNT_WANT_TO_BUY));

		}

	}

	private void openBuyingHousePopup(String cityName, String price) throws IOException {

		Popup buyingHousePopUp = new Popup();
		buyingHousePopUp.setX(500);
		buyingHousePopUp.setY(150);

		this.buyingHousePopupController = new BuyingHousePopupController();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(MainBoardController.class.getResource(GameConstants.POPUP_BUYING_HOUSE_PATH));
		Pane buyingHousePopupPane = load.load();
		buyingHousePopupController = (BuyingHousePopupController) load.getController();
		buyingHousePopupController.setCityLabel(cityName);
		buyingHousePopupController.setPriceLabel(price);
		buyingHousePopUp.getContent().add(buyingHousePopupPane);
		Stage stage = (Stage) this.boardController.getMainBoardScene().getWindow();
		buyingHousePopUp.show(stage);
		buyingHousePopupController.getFinish().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				this.handlePlayerHouseChoice(this.buyingHousePopupController);
				buyingHousePopUp.hide();
			}
		});
		if (!this.currentPlayer.getData().isHuman()) {
			this.buyingHousePopupController.yesButtonOnAction();
		}
	}

	private void openAskPlayerIfWantsToUseJailFreePopup() throws IOException {
		Popup useFreeJailPassCardPopup = new Popup();
		useFreeJailPassCardPopup.setX(500);
		useFreeJailPassCardPopup.setY(150);
		this.useFreeJailCardController = new UseFreeJailCardController();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(MainBoardController.class.getResource(GameConstants.POPUP_USER_FREE_JAIL));
		Pane useFreeJailCardPane = load.load();
		useFreeJailCardController = (UseFreeJailCardController) load.getController();
		useFreeJailPassCardPopup.getContent().add(useFreeJailCardPane);
		Stage stage = (Stage) this.boardController.getMainBoardScene().getWindow();
		useFreeJailPassCardPopup.show(stage);
		useFreeJailCardController.getFinish().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				this.handlePlayerUseFreeJailCard(this.useFreeJailCardController);
				useFreeJailPassCardPopup.hide();
			}
		});
		if (!this.currentPlayer.getData().isHuman()) {
			this.useFreeJailCardController.yesButtonOnAction();
		}
	}

	private void handlePlayerUseFreeJailCard(UseFreeJailCardController useFreeJailCardController) {
		boolean isWantToUse = useFreeJailCardController.isWantToUse();
		if (isWantToUse) {
			eventList.add(new MonopolyEvent(EventTypes.PLAYER_WANTS_TO_USE_CARD));
		} else {
			eventList.add(new MonopolyEvent(EventTypes.PLAYER_DIDNT_WANT_TO_USE_CARD));
		}

	}

	private void handlePlayerHouseChoice(BuyingHousePopupController buyingHousePopupController) {
		boolean isWantToBuy = buyingHousePopupController.isWantToBuy();
		if (isWantToBuy) {
			eventList.add(new MonopolyEvent(EventTypes.PLAYER_WANTS_TO_BUY_HOUSE));
		} else {
			eventList.add(new MonopolyEvent(EventTypes.PLAYER_DIDNT_WANT_TO_BUY));

		}

	}

	public void openCardPopup(final String cardType, String message) throws IOException {

		Popup cardPopUp = new Popup();
		cardPopUp.setX(500);
		cardPopUp.setY(150);

		FXMLLoader load = new FXMLLoader();
		load.setLocation(MainBoardController.class.getResource(GameConstants.POPUP_CARD_PATH));
		Pane cardPopupPane = load.load();
		this.cardPopupController = new CardPopupController();
		this.cardPopupController = (CardPopupController) load.getController();
		this.cardPopupController.setKindOfCard(cardType);
		this.cardPopupController.setTheMessage(message);
		cardPopUp.getContent().add(cardPopupPane);
		Stage stage = (Stage) this.boardController.getMainBoardScene().getWindow();
		cardPopUp.show(stage);
		this.cardPopupController.getFinish().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				if (cardType.equals("warrant")) {
					this.currentCard.warrantAction(((ArrayList<Player>) this.playersManager.getPlayers())
							.get(this.playersManager.getCurrentPlayer()));
				} else {
					this.currentCard.surpriseAction(((ArrayList<Player>) this.playersManager.getPlayers())
							.get(this.playersManager.getCurrentPlayer()));
				}
				cardPopUp.hide();

			}
		});
		if (!this.currentPlayer.getData().isHuman()) {
			this.cardPopupController.okButtonOnAction();
		}

	}

	public Card getSurpriseCard() {
		Card theCard = this.surpriseDeck.get(0);
		this.surpriseDeck.remove(0);
		return theCard;
	}

	public Card getWarrantCard() {
		Card theCard = this.warrantDeck.get(0);
		this.warrantDeck.remove(0);
		return theCard;
	}

	public void takeMoneyFromPlayers(Player getter, int sum) {
		for (Player payer : playersManager.getPlayers()) {
			if (!(payer.isBankrupt()))
				this.payFine(payer, getter, sum);
		}

	}

	public void setPlayerNewLocation(Player currentPlayer, String string) {
		int newPlace = 0;
		if (string.equals("NEXT_SURPRISE")) {
			newPlace = this.cellModel.getNextSurpriseOnBoard(currentPlayer.getPosition() + 1);
			this.boardController.movePlayerIconToSpecificCell(Math.abs(currentPlayer.getPosition() - newPlace),
					currentPlayer);
		} else if (string.equals("Jail")) {
			newPlace = this.cellModel.getPlaceOnBoardByName("Jail Free Pass");
			this.boardController.movePlayerIconToSpecificCell(Math.abs(currentPlayer.getPosition() - newPlace),
					currentPlayer);
		} else if (string.equals("NEXT_WARRANT")) {
			newPlace = this.cellModel.getNextWarrantOnBoard(currentPlayer.getPosition() + 1);
			this.boardController.movePlayerIconToSpecificCell(Math.abs(currentPlayer.getPosition() - newPlace),
					currentPlayer);
		}
	}

	private void returnToWarrantDeck() {
		this.warrantDeck.addLast(this.currentCard);

	}

	private void returnSurpriseCardToDeck() {
		this.surpriseDeck.addLast(this.currentCard);

	}

	private void returnSurpriseCardToDeck(Card card) {
		this.surpriseDeck.addLast(card);

	}

	private void payFineToEveryPlayer(Player payer, int sum) {
		for (Player getter : this.playersManager.getPlayers()) {
			if (!(payer.isBankrupt())) {
				this.payFine(payer, getter, sum);
			}
		}

	}

}
