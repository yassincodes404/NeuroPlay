CREATE TABLE IF NOT EXISTS  quiz_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS quiz_answer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quiz_question_id BIGINT NOT NULL,
    answer_text VARCHAR(255) NOT NULL,
    correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (quiz_question_id) REFERENCES quiz_question(id)
);