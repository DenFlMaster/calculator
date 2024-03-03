package com.flmaster.calculator.repo;

import com.flmaster.calculator.model.CategoryModel;
import com.flmaster.calculator.model.CategoryRequest;
import com.flmaster.calculator.rowmapper.CategoryRowMapper;
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

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
@Component
public class CategoryRepository {
    private final CategoryRowMapper categoryRowMapper;
    private final NamedParameterJdbcTemplate template;
    private final CategoryExerciseRequirementRepository requirementRepo;

    public List<CategoryModel> findCategories() {
        var rowSet = template.queryForRowSet("""
                SELECT c.id                                     as id,
                       c.name                                   as name,
                       c.level                                  as level,
                       c.requirement_min_combo                  as requirement_min_combo,
                       c.requirement_min_elements_of_same_level as requirement_min_elements_of_same_level,
                       c.requirement_min_female_score           as requirement_min_female_score,
                       c.requirement_min_male_score             as requirement_min_male_score,
                       cer.count,
                       e.id                                     as exercise_id,
                       e.name                                   as exercise_name
                FROM category as c
                         LEFT JOIN category_exercise_requirement as cer ON cer.category_id = c.id
                         LEFT JOIN exercise as e ON e.id = cer.exercise_id
                ORDER BY c.id
                """, emptyMap());

        return categoryRowMapper.mapRows(rowSet);
    }

    public Optional<CategoryModel> findCategoryByName(String name) {
        var rowSet = template.queryForRowSet("""
                SELECT c.id                                     as id,
                       c.name                                   as name,
                       c.level                                  as level,
                       c.requirement_min_combo                  as requirement_min_combo,
                       c.requirement_min_elements_of_same_level as requirement_min_elements_of_same_level,
                       c.requirement_min_female_score           as requirement_min_female_score,
                       c.requirement_min_male_score             as requirement_min_male_score,
                       cer.count,
                       e.id                                     as exercise_id,
                       e.name                                   as exercise_name
                FROM category as c
                         LEFT JOIN category_exercise_requirement as cer ON cer.category_id = c.id
                         LEFT JOIN exercise as e ON e.id = cer.exercise_id
                WHERE c.name = :name
                ORDER BY c.id
                """, Map.of("name", name));

        return Optional.ofNullable(DataAccessUtils.singleResult(categoryRowMapper.mapRows(rowSet)));
    }

    public Optional<CategoryModel> findCategory(long id) {
        var rowSet = template.queryForRowSet("""
                SELECT c.id                                     as id,
                       c.name                                   as name,
                       c.level                                  as level,
                       c.requirement_min_combo                  as requirement_min_combo,
                       c.requirement_min_elements_of_same_level as requirement_min_elements_of_same_level,
                       c.requirement_min_female_score           as requirement_min_female_score,
                       c.requirement_min_male_score             as requirement_min_male_score,
                       cer.count,
                       e.id                                     as exercise_id,
                       e.name                                   as exercise_name
                FROM category as c
                         LEFT JOIN category_exercise_requirement as cer ON cer.category_id = c.id
                         LEFT JOIN exercise as e ON e.id = cer.exercise_id
                WHERE c.id = :id
                ORDER BY c.id
                """, Map.of("id", id));

        return Optional.ofNullable(DataAccessUtils.singleResult(categoryRowMapper.mapRows(rowSet)));
    }

    public CategoryModel updateCategory(long id, CategoryRequest request) {
        template.update("""
                UPDATE category
                SET name = :name,
                level = :level,
                requirement_min_combo = :requirement_min_combo,
                requirement_min_elements_of_same_level = :requirement_min_elements_of_same_level,
                requirement_min_female_score = :requirement_min_female_score,
                requirement_min_male_score = :requirement_min_male_score
                WHERE id = :id
                """, Map.of(
                "id", id,
                "name", request.getName(),
                "level", request.getLevel(),
                "requirement_min_combo", request.getRequirements().getMinCombo(),
                "requirement_min_elements_of_same_level", request.getRequirements().getMinElementsOfSameLevel(),
                "requirement_min_female_score", request.getRequirements().getMinFemaleScore(),
                "requirement_min_male_score", request.getRequirements().getMinMaleScore()
        ));
        return findCategory(id).get();
    }

    public CategoryModel insertCategory(CategoryRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                """
                        INSERT INTO category (
                        name,
                        level,
                        requirement_min_combo,
                        requirement_min_elements_of_same_level,
                        requirement_min_female_score,
                        requirement_min_male_score
                        )
                        VALUES (
                        :name,
                        :level,
                        :requirement_min_combo,
                        :requirement_min_elements_of_same_level,
                        :requirement_min_female_score,
                        :requirement_min_male_score
                        )
                        """,
                new MapSqlParameterSource(Map.of(
                        "name", request.getName(),
                        "level", request.getLevel(),
                        "requirement_min_combo", request.getRequirements().getMinCombo(),
                        "requirement_min_elements_of_same_level", request.getRequirements().getMinElementsOfSameLevel(),
                        "requirement_min_female_score", request.getRequirements().getMinFemaleScore(),
                        "requirement_min_male_score", request.getRequirements().getMinMaleScore()
                )),
                keyHolder
        );
        return findCategory(keyHolder.getKey().longValue()).get();
    }

    public void deleteCategory(Integer id) {
        requirementRepo.deleteRequirementWithCategory(id);
        template.update("""
                        DELETE FROM category
                        WHERE id = :id
                        """,
                Map.of("id", id));


    }

}
