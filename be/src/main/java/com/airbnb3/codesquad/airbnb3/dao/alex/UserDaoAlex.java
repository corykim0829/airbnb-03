package com.airbnb3.codesquad.airbnb3.dao.alex;

import com.airbnb3.codesquad.airbnb3.oauth.GithubUserAlex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoAlex {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveUserInfo(GithubUserAlex userInfo) {
        String sql = "INSERT INTO user (id, name, email) VALUES (?, ?, ?)";
        Object[] parameter = new Object[]{userInfo.getGithubId(), userInfo.getUserId(), userInfo.getEmail()};
        jdbcTemplate.update(sql, parameter);
    }

    public boolean checkUserInfo(String githubId) {
        String sql = "SELECT EXISTS(SELECT id FROM user WHERE id = ?) AS id_check";
        return jdbcTemplate.queryForObject(sql, new Object[]{githubId}, (rs, rowNum) -> rs.getBoolean("id_check"));
    }

    public Long getIdFromUserId(String name) {
        String sql = "SELECT u.id FROM user u WHERE u.name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, (rs, rowNum) -> rs.getLong("name"));
        } catch (EmptyResultDataAccessException e) {
            return -1L;
        }

    }

}
