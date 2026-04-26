-- Tabela de cursos
CREATE TABLE COURSE (
                        COURSE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        COURSE_NAME VARCHAR(255) NOT NULL,
                        DURATION_HOURS INT NOT NULL,
                        PRICE DECIMAL(10,2) NOT NULL,
                        RELEASE_DATE DATE NOT NULL
);

-- Inserir 10 cursos
INSERT INTO COURSE (COURSE_NAME, DURATION_HOURS, PRICE, RELEASE_DATE) VALUES
                                                                          ('Java para Iniciantes', 40, 299.99, '2025-05-01'),
                                                                          ('Spring Boot Avançado', 60, 499.99, '2025-06-15'),
                                                                          ('MySQL e Banco de Dados', 30, 199.99, '2025-04-25'),
                                                                          ('HTML e CSS Completo', 20, 149.99, '2025-05-10'),
                                                                          ('JavaScript Moderno', 35, 249.99, '2025-05-20'),
                                                                          ('React.js do Zero', 45, 399.99, '2025-06-01'),
                                                                          ('Python para Data Science', 50, 449.99, '2025-07-01'),
                                                                          ('Docker e Kubernetes', 25, 299.99, '2025-04-30'),
                                                                          ('Spring Security na Prática', 40, 349.99, '2025-06-10'),
                                                                          ('Microsserviços com Spring', 55, 599.99, '2025-07-15');

-- Tabelas de autenticação
CREATE TABLE tbl_role (
                          role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          role VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE tbl_user (
                          user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_name VARCHAR(250) NOT NULL UNIQUE,
                          user_password VARCHAR(250) NOT NULL,
                          user_fk_role BIGINT NOT NULL,
                          FOREIGN KEY (user_fk_role) REFERENCES tbl_role(role_id)
);

-- Inserir papéis
INSERT INTO tbl_role (role) VALUES ('ROLE_ADMIN');
INSERT INTO tbl_role (role) VALUES ('ROLE_USER');

-- Senhas: admin = 54321, seu_nome = 12345
-- IMPORTANTE: Substitua os hashes abaixo pelos gerados em https://bcrypt-generator.com/
INSERT INTO tbl_user (user_name, user_password, user_fk_role) VALUES
                                                                  ('admin', '$2a$12$GERAR_HASH_PARA_54321_AQUI', 1),
                                                                  ('seunome', '$2a$12$GERAR_HASH_PARA_12345_AQUI', 2);