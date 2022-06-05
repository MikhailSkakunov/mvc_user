package ru.gb.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.gb.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString(2));

        return user;
    }
}
