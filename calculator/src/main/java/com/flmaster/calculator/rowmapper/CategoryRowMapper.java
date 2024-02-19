package com.flmaster.calculator.rowmapper;

import com.flmaster.calculator.model.CategoryExerciseRequirementModel;
import com.flmaster.calculator.model.CategoryModel;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryRowMapper {
    public List<CategoryModel> mapRows(SqlRowSet rs) {
        List<CategoryModel> result = new ArrayList<>();
        int currentId = Integer.MIN_VALUE;
        while (rs.next()) {

            if(currentId != rs.getInt("id")){
                result.add(new CategoryModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("level"),
                        new CategoryModel.Requirements(
                            rs.getInt("requirement_min_combo"),
                            rs.getInt("requirement_min_elements_of_same_level"),
                            rs.getInt("requirement_min_female_score"),
                            rs.getInt("requirement_min_male_score")
                        ),
                        new ArrayList<>()
                ));
                currentId = rs.getInt("id");
            }

            rs.getInt("count");
            if (!rs.wasNull()){
                result.get(result.size() - 1).exerciseRequirements().add(new CategoryExerciseRequirementModel(
                        rs.getInt("id"),
                        rs.getInt("exercise_id"),
                        rs.getInt("count"),
                        rs.getString("exercise_name")
                ));
            }
        }
        return result;
    }
}
