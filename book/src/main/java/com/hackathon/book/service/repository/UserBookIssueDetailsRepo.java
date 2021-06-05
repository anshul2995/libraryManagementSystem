package com.hackathon.book.service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.book.entity.UseBookIssueEntity;

@Repository
public interface UserBookIssueDetailsRepo extends JpaRepository<UseBookIssueEntity, UUID> {
	
	List<UseBookIssueEntity> findByUserIdAndStatus(UUID userid, String status);

	UseBookIssueEntity findByBookId(UUID bookBy); 

}
