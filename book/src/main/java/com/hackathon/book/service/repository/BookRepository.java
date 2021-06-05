package com.hackathon.book.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackathon.book.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
	
	@Query(value = "update table books set status=: status where id =:id", nativeQuery = true)
	void updateStatusByBookId(UUID id , String status);
}
