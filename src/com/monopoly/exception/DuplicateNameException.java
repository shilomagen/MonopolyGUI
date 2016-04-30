package com.monopoly.exception;

public class DuplicateNameException extends RuntimeException{

    public DuplicateNameException() {
	super ("Name already exists");
    }
}
