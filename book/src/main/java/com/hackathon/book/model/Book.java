package com.hackathon.book.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Book {
	
	private UUID id;
	
	private String title;
	
	private String author;

	private String edition;
	
	private String category;
}
