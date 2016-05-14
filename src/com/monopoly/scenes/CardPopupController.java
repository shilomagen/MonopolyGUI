
package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CardPopupController implements Initializable {

	final private BooleanProperty finish = new SimpleBooleanProperty(this, "finish card popup");
	@FXML
	private Button okButton;

	@FXML
	private Label kindOfCard;

	@FXML
	private Label theMessage;

	public void setKindOfCard(String kindOfCard) {
		
		this.kindOfCard.setText(kindOfCard.toUpperCase() + "!");
		if (kindOfCard.toLowerCase().equals("warrant")) {
			this.kindOfCard.setTextFill(Color.RED);
		}
		if (kindOfCard.toLowerCase().equals("surprise")) {
			this.kindOfCard.setTextFill(Color.GREEN);
		}
	}

	public void setTheMessage(String theMessage) {
		this.theMessage.setText(theMessage);
	}

	public void okButtonOnAction() {
		this.finish.setValue(true);
	}

	public BooleanProperty getFinish() {
		return finish;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
