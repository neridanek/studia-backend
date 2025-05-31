-- Create user table

CREATE TABLE user (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(255) NOT NULL,
                       lastLoginDate TIMESTAMP,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL
);

INSERT INTO user (login, lastLoginDate, password, role, shapes)
ALTER TABLE user Add CONSTRAINT fk_employer_ID FOREIGN KEY (employer_ID) REFERENCES employer (employer_ID)
CREATE TABLE employer (
                      id SERIAL PRIMARY KEY,
                      login VARCHAR(255) NOT NULL,
                      lastLoginDate TIMESTAMP,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(255) NOT NULL
);

INSERT INTO employer (login, lastLoginDate, password, role, shapes)