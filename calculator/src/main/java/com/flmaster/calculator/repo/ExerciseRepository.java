package com.flmaster.calculator.repo;

import com.flmaster.calculator.model.ExerciseModel;
import com.flmaster.calculator.model.ExerciseRequest;
import com.flmaster.calculator.rowmapper.ExerciseRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExerciseRepository {
    private final ExerciseRowMapper exerciseRowMapper;
    private final NamedParameterJdbcTemplate template;
    private final CategoryExerciseRequirementRepository requirementRepo;

    public List<ExerciseModel> findExercises() {
        return template.query("""
                SELECT id, name
                FROM exercise
                """, exerciseRowMapper);
    }

    public Optional<ExerciseModel> findExercise(long id) {
        return Optional.ofNullable(template.queryForObject("""
                SELECT id, name
                FROM exercise
                WHERE id = :id
                """, Map.of("id", id), exerciseRowMapper));
    }

    public Optional<ExerciseModel> findExerciseByName(String name){
        List<ExerciseModel> result = template.query("""
                SELECT name, id FROM exercise
                WHERE name = :name
                """, Map.of("name", name), exerciseRowMapper);

        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public ExerciseModel updateExercise(long id, ExerciseRequest request) {
        template.update("""
                UPDATE exercise
                SET name = :name
                WHERE id = :id
                """, Map.of("id", id, "name", request.getName()));
        return findExercise(id).get();
    }

    public ExerciseModel insertExercise(ExerciseRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update("""
                        INSERT INTO exercise (name)
                        VALUES (:name)
                        """,
                new MapSqlParameterSource(Map.of("name", request.getName())),
                keyHolder
        );
        return findExercise(keyHolder.getKey().longValue()).get();
    }

    public void deleteExercise(Integer id) {
        requirementRepo.deleteRequirementWithExercise(id);
        template.update("""
                DELETE FROM exercise
                WHERE id = :id
                """, Map.of("id", id));
    }

}
