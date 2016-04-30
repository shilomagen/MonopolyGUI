package com.monopoly.player;

import java.util.Objects;

import javafx.scene.image.Image;

public class PlayerInitiate {

    private String name;
    private boolean isHuman;
    private String gender;
    private Image image;

    public PlayerInitiate(String name) {
	this(name, true);
    }

    public PlayerInitiate(String name, boolean isHuman) {
	this.name = name;
	this.isHuman = isHuman;
    }

    public PlayerInitiate(String name, boolean isHuman, String gender, Image image) {
        this.name = name;
        this.isHuman = isHuman;
        this.gender = gender;
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    

    public String getName() {
	return name;
    }

    public boolean isHuman() {
	return isHuman;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 47 * hash + Objects.hashCode(this.name);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final PlayerInitiate other = (PlayerInitiate) obj;
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	return true;
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}