package com.flmaster.calculator.repo;

import com.flmaster.calculator.model.CategoryExerciseRequirementModel;
import com.flmaster.calculator.model.CategoryExerciseRequirementRequest;
import com.flmaster.calculator.rowmapper.CategoryExerciseRequirementRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryExerciseRequirementRepository {
    private final CategoryExerciseRequirementRowMapper categoryExerciseRequirementRowMapper;
    private final NamedParameterJdbcTemplate template;

    public Optional<CategoryExerciseRequirementModel> findRequirement(Integer categoryId, Integer exerciseId) {
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

    public CategoryExerciseRequirementModel setRequirement(
            Integer categoryId,
            Integer exerciseId,
            CategoryExerciseRequirementRequest request
    ) {
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
        return findRequirement(categoryId, exerciseId).get();
    }

    public void deleteRequirementWithExercise(Integer exerciseId) {
        template.update("""
                DELETE FROM category_exercise_requirement
                WHERE exercise_id = :exercise_id
                """, Map.of(
                        "exercise_id", exerciseId
        ));
    }

    public void deleteRequirementWithCategory(Integer categoryId) {
        template.update("""
                DELETE FROM category_exercise_requirement
                WHERE category_id = :category_id
                """, Map.of(
                "category_id", categoryId
        ));
    }

    public void deleteRequirement(Integer categoryId, Integer exerciseId) {
        template.update("""
                DELETE FROM category_exercise_requirement
                WHERE category_id = :category_id AND exercise_id = :exercise_id
                """, Map.of(
                "category_id", categoryId,
                "exercise_id", exerciseId
        ));
    }

}
