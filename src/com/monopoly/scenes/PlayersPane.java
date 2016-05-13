/*
 */

package com.monopoly.scenes;

import com.monopoly.engine.PlayersManager;
import com.monopoly.player.PlayerView;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

public class PlayersPane extends Scene{

    final private FlowPane root;
    
    public PlayersPane(PlayersManager playersManager) {
        super(new FlowPane(), 500, 400);
        root = (FlowPane) getRoot();
        root.setHgap(10);
        root.setVgap(10);
        playersManager.getPlayers().forEach(
                (player) -> root.getChildren().add(new PlayerView(player.getData().getName(),player.getData().getGender(),
                		player.getData().isHuman(), player.getData().getImage(), player.getData().getIcon())));
        setRoot(root);
    }
}
