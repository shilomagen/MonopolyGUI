package com.monopoly.player;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


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
    	ImageView playerImage = new ImageView(image);
    	playerImage.setPreserveRatio(false);
    	playerImage.setFitHeight(150);
    	playerImage.setFitWidth(100);
    	
        return playerImage;
    }

}