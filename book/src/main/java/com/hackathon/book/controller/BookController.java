package com.hackathon.book.controller;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.book.entity.BookEntity;
import com.hackathon.book.entity.MemberEntity;
import com.hackathon.book.handler.BookHandler;
import com.hackathon.book.model.Book;
import com.hackathon.book.model.CustomException;
import com.hackathon.book.model.Member;

@RestController
public class BookController {

	@Autowired
	private BookHandler bookHandler;
	
	@PostMapping("/addNewBooks")
	public ResponseEntity<BookEntity> addBook(@RequestBody Book book) {
		return bookHandler.addNewBook(book);
	}
	
	@PostMapping("/addNewUser")
	public ResponseEntity<MemberEntity> addBook(@RequestBody Member member) {
		return bookHandler.addMember(member);
	}
	
	@PostMapping("/borrowBooks/{userId}")
	public ResponseEntity<BookEntity> borrowBook(@PathParam("userId") String userId, @RequestBody List<Book> listofBooks) throws CustomException {
		return bookHandler.borrowBook(userId, listofBooks);
	}
	
	@PostMapping("/returnBooks/{userId}")
	public ResponseEntity<Map<String, Integer>> returnBook(@PathParam("userId") String userId, @RequestBody List<Book> listofBooks) throws CustomException {
		return bookHandler.returnBook(userId, listofBooks);
	}
}
