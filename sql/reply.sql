CREATE TABLE reply(
	reply_id INT AUTO_INCREMENT PRIMARY KEY,
    blog_id int not null,
    reply_writer VARCHAR(40) not null,
    reply_contente varchar(200) not null,
    published_at datetime default now(),
    updated_at datetime default now(),
    constraint fk_reply foreign key (blog_id) references blog(blog_id)
);

drop table reply;

INSERT INTO reply VALUES
(null, 2, '박자바', '안녕하세여ㅛ', now(),now()),
(null, 2, '박w', '안녕하세여ㅛ2', now(),now()),
(null, 2, 'we', '안녕하세여ㅛ3', now(),now()),
(null, 3, '박자바', '안녕하세dy', now(),now());

SELECT * FROM reply;
