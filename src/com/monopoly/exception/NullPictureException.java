package com.monopoly.exception;

public class NullPictureException extends RuntimeException{
	public NullPictureException(){
		super("Please Select a Photo");
	}

}
