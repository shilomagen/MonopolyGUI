package com.monopoly.player;

import com.monopoly.utility.ImageUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author iblecher
 */
public class PlayerView extends VBox{

    public PlayerView(String name, String gender,  boolean isHuman, Image image) {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(createImage(isHuman, image), createLabel(name), createLabel(gender));
    }
    
    private Label createLabel(String title){
        return new Label(title);
    }
    
    private ImageView createImage(boolean isHuman, Image image){
    	if (!isHuman){
    		return new ImageView(getImage(isHuman));
    	}
    	ImageView playerImage = new ImageView(image);
    	playerImage.setPreserveRatio(false);
    	playerImage.setFitHeight(150);
    	playerImage.setFitWidth(100);
    	
        return playerImage;
    }

    private Image getImage(boolean isHuman) {
        String filename = isHuman ? "human" : "computer";
        return ImageUtils.getImage(filename);
    }
}