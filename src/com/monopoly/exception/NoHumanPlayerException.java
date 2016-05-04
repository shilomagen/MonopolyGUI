package com.monopoly.exception;

public class NoHumanPlayerException extends RuntimeException{

    public NoHumanPlayerException() {
	super ("You must have at least one human player");
    }
}
