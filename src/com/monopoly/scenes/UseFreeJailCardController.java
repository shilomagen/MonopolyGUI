
package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class UseFreeJailCardController implements Initializable {

    final private BooleanProperty finish = new SimpleBooleanProperty(this, "finish buying popup");
    
   @FXML
    private Button yesButton;
    
    @FXML
    private Button noButton;
    
    private boolean wantToUse;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void yesButtonOnAction(){
        this.wantToUse = true;
        this.finish.setValue(true);
    }
    
    public void noButtonOnAction(){
        this.wantToUse = false;
        this.finish.setValue(true);
    }

    public BooleanProperty getFinish() {
        return finish;
	}

	public boolean isWantToUse() {
		return wantToUse;
	}

	public void setWantToUse(boolean wantToBuy) {
		this.wantToUse = wantToBuy;
	}
    
}
