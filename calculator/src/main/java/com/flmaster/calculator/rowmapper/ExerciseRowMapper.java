package com.flmaster.calculator.rowmapper;

import com.flmaster.calculator.model.ExerciseModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ExerciseRowMapper implements RowMapper<ExerciseModel> {
    @Override
    public ExerciseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExerciseModel(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}