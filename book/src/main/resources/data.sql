CREATE TABLE members (
  id uuid PRIMARY KEY,
  name VARCHAR(250),
  roles VARCHAR,
  username varchar,
  password varchar
);

create table books (
	id uuid PRIMARY KEY,
	title varchar,
	author varchar, 
	category varchar,
	status varchar,
	edition varchar,
	rack int
);

create table user_book_issue_details (
	id uuid,
	book_id uuid,
	user_id uuid,
	return_date varchar,
	issue_date varchar,
	status varchar,
	CONSTRAINT FK_Book_ID FOREIGN KEY (book_id) REFERENCES books(id),
	CONSTRAINT FK_User_ID FOREIGN KEY (user_id) REFERENCES members(id)
);