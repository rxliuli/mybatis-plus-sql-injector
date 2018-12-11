package com.rxliuli.example.mybatisplussqlinjector.dao;

import com.rxliuli.example.mybatisplussqlinjector.entity.User;
import common.test.BaseDaoAndServiceTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
public class UserDaoTest extends BaseDaoAndServiceTest<UserDao> {
    @Test
    public void existsById() {
        final Boolean res = base.existsById(1L);
        log.debug("res: {}", res);
        assertThat(res)
                .isTrue();
    }

    @Test
    public void selectById() {
        final User user = base.selectById(1L);
        log.debug("user: {}", user);
        assertThat(user)
                .isNotNull();
    }
}