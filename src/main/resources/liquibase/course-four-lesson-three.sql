-- liquibase formatted sql

-- changeset darya:1

CREATE TABLE dynamic_rules (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL
    );