# --- !Ups

CREATE SEQUENCE s_users_id;

CREATE TABLE users (
  id   BIGINT      NOT NULL DEFAULT (nextval('s_users_id')),
  name VARCHAR(32) NOT NULL,
  age  INT,
  CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE INDEX idx_users_id ON users (name);

# --- !Downs

DROP INDEX idx_users_id;

DROP TABLE users;

DROP SEQUENCE s_users_id;
