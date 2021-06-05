package com.hackathon.book.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "user_book_issue_details")
@Data
public class UseBookIssueEntity {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private Integer id;
	
	@Column(name = "return_date")
	private String return_date;
	
	@Column(name = "issue_date")
	private String issue_date;
	
	@Column(name = "book_id")
	private UUID bookId;
	
	@Column(name = "user_id")
	private UUID userId;
	
	@Column(name = "status")
	private String status;
}