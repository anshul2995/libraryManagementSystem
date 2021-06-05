package com.hackathon.book.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Member {
	
	private UUID id;
	
	private String name;
	
	private String roles;

	private String username;
	
	private String password;
	
	private List<Book> books;
}
