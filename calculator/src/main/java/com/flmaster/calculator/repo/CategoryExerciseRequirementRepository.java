package com.flmaster.calculator.repo;

import com.flmaster.calculator.model.CategoryExerciseRequirement;
import com.flmaster.calculator.model.CategoryExerciseRequirementModel;
import com.flmaster.calculator.rowmapper.CategoryExerciseRequirementRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryExerciseRequirementRepository {
    private final CategoryExerciseRequirementRowMapper categoryExerciseRequirementRowMapper;
    private final NamedParameterJdbcTemplate template;

    public Optional<CategoryExerciseRequirementModel> findCategoryExerciseRequirement(Integer categoryId, Integer exerciseId) {
        return Optional.ofNullable(template.queryForObject("""
                SELECT cer.category_id as category_id,
                       cer.exercise_id as exercise_id,
                       cer.count       as count,
                       e.name          as exercise_name
                FROM category_exercise_requirement as cer
                         JOIN exercise as e ON cer.exercise_id = e.id
                WHERE category_id = :category_id AND exercise_id = :exercise_id
                """, Map.of(
                "category_id", categoryId,
                "exercise_id", exerciseId
        ), categoryExerciseRequirementRowMapper));
    }

    public CategoryExerciseRequirementModel setCategoryExerciseRequirement(
            Integer categoryId,
            Integer exerciseId,
            CategoryExerciseRequirement request
    ) {
        System.out.println(template.queryForObject("SELECT sqlite_version()", Map.of(), String.class));
        try{
            template.update("""
                    INSERT INTO category_exercise_requirement (category_id, exercise_id, count)
                    VALUES(:category_id, :exercise_id, :count)
                    ON CONFLICT(category_id, exercise_id) DO UPDATE
                    SET count = :count
                    """,
                    new MapSqlParameterSource(Map.of(
                            "category_id", categoryId,
                            "exercise_id", exerciseId,
                            "count", request.getCount()
                    ))
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        return findCategoryExerciseRequirement(categoryId, exerciseId).get();
    }

    public void deleteCategoryExerciseRequirement(long categoryId, long exerciseId) {
        template.update("""
                DELETE FROM category_exercise_requirement
                WHERE category_id = :category_id AND exercise_id = :exercise_id
                """, Map.of(
                "category_id", categoryId,
                "exercise_id", exerciseId
        ));
    }

}
