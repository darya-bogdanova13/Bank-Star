package com.skypro.bank_star.repository;

import com.skypro.bank_star.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TelegramBotRepository {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TelegramBotRepository(
            @Qualifier("recommendationsJdbcTemplate")
            JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(cacheNames = "telegramBot", key = "#root.methodName + #username")
    public Collection<UserDto> getUser(String username) {
        String sql = "SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME " +
                "          FROM USERS u " +
                "          WHERE u.USERNAME = ?";

        Collection<UserDto> result = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDto.class), username);

        logger.info("Executing a SQL query \"isUserExists\" in the database for userName: {}", username);
        return result;
    }

}