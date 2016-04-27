package com.monopoly.garbage;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;

public class GameBoardController {

	// Event Listener on SplitPane.onMouseClicked
	@FXML
	public void splitRightData(MouseEvent event) {
		SplitPane thePane = (SplitPane)event.getSource();
		System.out.println("orientation");
		System.out.println(thePane.getOrientation());
		System.out.println("dividers");
		System.out.println(thePane.getDividers().get(0).getPosition());
		System.out.println("dividerpositions");
		System.out.println(thePane.getDividerPositions());
		System.out.println("max and pref height");
		System.out.println(thePane.getMaxHeight());
		System.out.println(thePane.getPrefHeight());
		System.out.println("max and pref width");
		System.out.println(thePane.getMaxWidth());
		System.out.println(thePane.getPrefWidth());
	}
	// Event Listener on SplitPane.onMouseClicked
	@FXML
	public void splitDownData(MouseEvent event) {
		SplitPane thePane = (SplitPane)event.getSource();
		System.out.println("orientation");
		System.out.println(thePane.getOrientation());
		System.out.println("dividers");
		System.out.println(thePane.getDividers().get(0).getPosition());
		System.out.println("dividerpositions");
		System.out.println(thePane.getDividerPositions());
		System.out.println("max and pref height");
		System.out.println(thePane.getMaxHeight());
		System.out.println(thePane.getPrefHeight());
		System.out.println("max and pref width");
		System.out.println(thePane.getMaxWidth());
		System.out.println(thePane.getPrefWidth());
	}
	// Event Listener on SplitPane.onMouseClicked
	@FXML
	public void splitLeftData(MouseEvent event) {
		SplitPane thePane = (SplitPane)event.getSource();
		System.out.println("orientation");
		System.out.println(thePane.getOrientation());
		System.out.println("dividers");
		System.out.println(thePane.getDividers().get(0).getPosition());
		System.out.println("dividerpositions");
		System.out.println(thePane.getDividerPositions());
		System.out.println("max and pref height");
		System.out.println(thePane.getMaxHeight());
		System.out.println(thePane.getPrefHeight());
		System.out.println("max and pref width");
		System.out.println(thePane.getMaxWidth());
		System.out.println(thePane.getPrefWidth());
	}
	// Event Listener on SplitPane.onMouseClicked
	@FXML
	public void splitUpData(MouseEvent event) {
		SplitPane thePane = (SplitPane)event.getSource();
		System.out.println("orientation");
		System.out.println(thePane.getOrientation());
		System.out.println("dividers");
		System.out.println(thePane.getDividers().get(0).getPosition());
		System.out.println("dividerpositions");
		System.out.println(thePane.getDividerPositions());
		System.out.println("max and pref height");
		System.out.println(thePane.getMaxHeight());
		System.out.println(thePane.getPrefHeight());
		System.out.println("max and pref width");
		System.out.println(thePane.getMaxWidth());
		System.out.println(thePane.getPrefWidth());
	}
}
