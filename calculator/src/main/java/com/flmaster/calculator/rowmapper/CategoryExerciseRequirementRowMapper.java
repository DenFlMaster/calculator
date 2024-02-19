package com.flmaster.calculator.rowmapper;

import com.flmaster.calculator.model.CategoryExerciseRequirementModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryExerciseRequirementRowMapper implements RowMapper<CategoryExerciseRequirementModel> {
    @Override
    public CategoryExerciseRequirementModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CategoryExerciseRequirementModel(
                rs.getInt("category_id"),
                rs.getInt("exercise_id"),
                rs.getInt("count"),
                rs.getString("exercise_name")
        );
    }
}