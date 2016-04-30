package com.monopoly.exception;

/**
 *
 * @author Iblecher
 */
public class EmptyNameException extends RuntimeException {

    public EmptyNameException() {
	super ("Name is empty");
    }
}
