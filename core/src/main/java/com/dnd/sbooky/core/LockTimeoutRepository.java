package com.dnd.sbooky.core;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockTimeoutRepository {

    private final JdbcTemplate jdbcTemplate;

    public void setInnodbLockWaitTimeout(int seconds) {
        jdbcTemplate.execute("SET SESSION lock_wait_timeout = " + seconds);
    }
}
