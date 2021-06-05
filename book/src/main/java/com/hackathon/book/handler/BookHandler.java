package com.hackathon.book.handler;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hackathon.book.entity.BookEntity;
import com.hackathon.book.entity.MemberEntity;
import com.hackathon.book.model.Book;
import com.hackathon.book.model.CustomException;
import com.hackathon.book.model.Member;
import com.hackathon.book.service.BookService;

@Component
public class BookHandler {

	@Autowired
	private BookService bookService;

	public ResponseEntity<BookEntity> addNewBook(Book book) {
		try {
			BookEntity bookEntity = new BookEntity();
			bookEntity.setId(book.getId());
			bookEntity.setAuthor(book.getAuthor());
			bookEntity.setTitle(book.getTitle());
			bookEntity.setEdition(book.getEdition());
			bookEntity.setRack(generateRandomNumber());
			bookEntity.setCategory(book.getCategory());
			bookEntity.setStatus("Available");

			BookEntity newlyAddedBook = bookService.addNewBook(bookEntity);
			return new ResponseEntity<>(newlyAddedBook, HttpStatus.CREATED);
		} catch (CustomException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private int generateRandomNumber() {
		Random random = new Random();
		int low = 1;
		int high = 10000;
		int result = random.nextInt(high - low) + low;
		return result;
	}

	public ResponseEntity<BookEntity> borrowBook(String userId, List<Book> listofBooks) throws CustomException {
		if (listofBooks.size() + bookService.checkNumberOfIssuedBook(UUID.fromString(userId)) > 3)
			throw new CustomException("Cannot issue more than 3 books");
		bookService.borrowBook(userId, listofBooks);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

	public ResponseEntity<Map<String, Integer>> returnBook(String userId, List<Book> listofBooks) {
		Map<String, Integer> response = bookService.returnBook(userId, listofBooks);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<MemberEntity> addMember(Member member) {
		try {
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setId(member.getId());
			memberEntity.setName(member.getName());
			memberEntity.setRoles(member.getRoles());
			memberEntity.setUsername(member.getUsername());
			memberEntity.setPassword(String.valueOf(member.getPassword().hashCode()));
			MemberEntity newlyAddedBook = bookService.saveMember(memberEntity);
			return new ResponseEntity<>(newlyAddedBook, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
