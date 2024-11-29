-- liquibase formatted sql

-- changeset Максим:1
CREATE TABLE dynamic_rules (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL
    );
-- changeset Максим:2
CREATE TABLE user_rules (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    rule_id UUID NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (rule_id) REFERENCES rules(id)
);