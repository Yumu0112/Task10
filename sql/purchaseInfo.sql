-- 結局手動でターミナルから追加 --

DROP TABLE IF EXISTS purchase_info;

CREATE TABLE purchase_info (
id int unsigned AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
email  VARCHAR(100) unique,
purchaseDate timestamp,
price int,
PRIMARY KEY(id)
);

INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Tanaka', 'aaa@example.com', '2009-12-31 09:58:34', 500);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Suzuki', 'bbb@example.com', '2010-01-15 14:23:19', 750);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Yamada', 'ccc@example.com', '2010-02-28 18:45:12', 1000);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Sato', 'ddd@example.com', '2010-03-07 11:30:00', 800);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Watanabe', 'eee@example.com', '2010-04-19 09:15:42', 1200);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Ito', 'fff@example.com', '2010-05-02 16:20:55', 900);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Kobayashi', 'ggg@example.com', '2010-06-11 13:48:27', 1500);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Nakamura', 'hhh@example.com', '2010-07-26 20:10:03', 1100);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Okada', 'iii@example.com', '2010-08-14 07:55:18', 700);
INSERT INTO purchase_info (name, email, purchaseDate, price) VALUES ('Takahashi', 'jjj@example.com', '2010-09-05 12:40:09', 1000);