package com.dnd.sbooky.core;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LockTimeoutRepository {

    private final JdbcTemplate jdbcTemplate;

    public LockTimeoutRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setInnodbLockWaitTimeout(int seconds) {
        jdbcTemplate.execute("SET SESSION lock_wait_timeout = " + seconds);
    }
}


