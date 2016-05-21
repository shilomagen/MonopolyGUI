package com.monopoly.engine;

import java.util.UUID;

public class MonopolyEvent {
	private String eventType;
	private final UUID eventID;
	
	public MonopolyEvent(String type){
		this.eventType = type;
		this.eventID = UUID.randomUUID();
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public UUID getEventID() {
		return eventID;
	}
	
}
