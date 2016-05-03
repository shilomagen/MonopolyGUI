/*
 */

package com.monopoly.scenes;

import com.monopoly.engine.PlayersManager;
import com.monopoly.player.PlayerView;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author iblecher
 */
public class PlayersPane extends Scene{

    final private FlowPane root;
    
    public PlayersPane(PlayersManager playersManager) {
        super(new FlowPane(), 500, 400);
        root = (FlowPane) getRoot();
        root.setHgap(10);
        root.setVgap(10);
        playersManager.getPlayers().forEach(
                (player) -> root.getChildren().add(new PlayerView(player.getName(),player.getGender(), player.isHuman(), player.getImage())));
        setRoot(root);
    }
}
