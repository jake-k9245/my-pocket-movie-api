DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS review_likes;
DROP TABLE IF EXISTS wishlists;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS contents;
DROP TABLE IF EXISTS members;

-- Member 테이블
CREATE TABLE members
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);

-- Content 테이블
CREATE TABLE contents
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);

-- Review 테이블
CREATE TABLE reviews
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);

-- Comment 테이블
CREATE TABLE comments
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);

-- Wishlist 테이블
CREATE TABLE wishlists
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);

-- Review Like 테이블
CREATE TABLE review_likes
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);
