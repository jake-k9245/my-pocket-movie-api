DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS review_likes;
DROP TABLE IF EXISTS wishlists;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS contents;
DROP TABLE IF EXISTS members;

-- Member 테이블
CREATE TABLE members (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         email VARCHAR(254) NOT NULL UNIQUE,
                         password VARCHAR(100) NOT NULL,
                         nickname VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Content 테이블
CREATE TABLE contents (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          member_id BIGINT NOT NULL,
                          external_id VARCHAR(255) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          poster_path VARCHAR(512),
                          overview TEXT,
                          release_date DATE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Review 테이블
CREATE TABLE reviews (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         member_id BIGINT NOT NULL,
                         content_id BIGINT NOT NULL,
                         rating INT CHECK (rating >= 1 AND rating <= 5),
                         text TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (member_id) REFERENCES members(id),
                         FOREIGN KEY (content_id) REFERENCES contents(id)
);

-- Comment 테이블
CREATE TABLE comments (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          member_id BIGINT NOT NULL,
                          review_id BIGINT NOT NULL,
                          text TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (member_id) REFERENCES members(id),
                          FOREIGN KEY (review_id) REFERENCES reviews(id)
);

-- Wishlist 테이블
CREATE TABLE wishlists (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           member_id BIGINT NOT NULL,
                           content_id BIGINT NOT NULL,
                           external_id VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (member_id) REFERENCES members(id),
                           FOREIGN KEY (content_id) REFERENCES contents(id)
);

-- Like 테이블 (이름 변경)
CREATE TABLE review_likes (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              member_id BIGINT NOT NULL,
                              review_id BIGINT NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (member_id) REFERENCES members(id),
                              FOREIGN KEY (review_id) REFERENCES reviews(id)
);