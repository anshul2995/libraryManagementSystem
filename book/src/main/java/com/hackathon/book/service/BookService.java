package com.hackathon.book.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.book.entity.BookEntity;
import com.hackathon.book.entity.MemberEntity;
import com.hackathon.book.entity.UseBookIssueEntity;
import com.hackathon.book.model.Book;
import com.hackathon.book.model.CustomException;
import com.hackathon.book.service.repository.BookRepository;
import com.hackathon.book.service.repository.MemberRepository;
import com.hackathon.book.service.repository.UserBookIssueDetailsRepo;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserBookIssueDetailsRepo userBookIssueDetailsRepo;

	@Autowired
	private MemberRepository memberRepository;
	
	public MemberEntity saveMember(MemberEntity memberEntity) {
		return memberRepository.save(memberEntity);
	}

	public BookEntity addNewBook(BookEntity bookEntity) throws Exception {

		if (bookRepository.existsById(bookEntity.getId()))
			throw new CustomException("Book with uid already exits, Please provide a new book uid");

		BookEntity save = bookRepository.save(bookEntity);
		return save;
	}

	public void borrowBook(String user, List<Book> listofBooks) {

		UUID userId = UUID.fromString(user);

		List<UseBookIssueEntity> UseBookIssueEntity = new ArrayList<UseBookIssueEntity>();

		for (Book book : listofBooks) {
			UseBookIssueEntity useBookIssueEntity = new UseBookIssueEntity();
			useBookIssueEntity.setBookId(book.getId());
			useBookIssueEntity.setStatus("Issued");
			useBookIssueEntity.setUserId(userId);
			Date date = new Date();
			useBookIssueEntity.setIssue_date(String.valueOf(new Date()));
			Date returnDate = find2weeksAfterDate(date);
			useBookIssueEntity.setReturn_date(String.valueOf(returnDate));
			UseBookIssueEntity.add(useBookIssueEntity);
			bookRepository.updateStatusByBookId(book.getId(), "Unavailable");
		}
		userBookIssueDetailsRepo.saveAll(UseBookIssueEntity);
	}

	private Date find2weeksAfterDate(Date date) {
		int noOfDays = 14; // i.e two weeks
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date returnDate = calendar.getTime();
		return returnDate;
	}

	public int checkNumberOfIssuedBook(UUID userID) {

		return userBookIssueDetailsRepo.findByUserIdAndStatus(userID, "Issued").size();
	}

	public Map<String, Integer> returnBook(String user, List<Book> listofBooks) {

		UUID userId = UUID.fromString(user);

		List<UseBookIssueEntity> UseBookIssueEntity = new ArrayList<UseBookIssueEntity>();

		int fine = 0;

		for (Book book : listofBooks) {

			UseBookIssueEntity issuedBook = userBookIssueDetailsRepo.findByBookId(book.getId());

			if (String.valueOf(new Date()).compareTo(issuedBook.getReturn_date()) > 0) {

				int diff = (int) (new Date().getTime() - Date.parse(issuedBook.getReturn_date()));
				if (diff < 3)
					fine = fine + 20 * diff;
				if (diff > 3)
					fine = fine + 50 * diff;
			}
			issuedBook.setStatus("Returned");
			issuedBook.setUserId(userId);
			bookRepository.updateStatusByBookId(book.getId(), "Available");
		}
		userBookIssueDetailsRepo.saveAll(UseBookIssueEntity);

		Map<String, Integer> map = new HashMap<>();
		map.put("fine", fine);

		return map;
	}
}
