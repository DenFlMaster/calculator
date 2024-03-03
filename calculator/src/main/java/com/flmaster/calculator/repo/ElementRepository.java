package com.flmaster.calculator.repo;

import com.flmaster.calculator.model.ElementModel;
import com.flmaster.calculator.model.ElementRequest;
import com.flmaster.calculator.rowmapper.ElementRowMapper;
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
public class ElementRepository {
    private final ElementRowMapper elementRowMapper;
    private final NamedParameterJdbcTemplate template;

    public Optional<ElementModel> findElementByName(String name){
        var result = template.query("""
                SELECT e.id    as id,
                       e.name  as name,
                       e.level as level,
                       e.score as score,
                       et.id   as type_id,
                       et.name as type_name
                FROM element as e
                JOIN element_type as et ON e.type_id = et.id
                WHERE e.name = :name
                """, Map.of("name", name), elementRowMapper);

        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public List<ElementModel> findElements() {
        return template.query("""
                SELECT e.id    as id,
                       e.name  as name,
                       e.level as level,
                       e.score as score,
                       et.id   as type_id,
                       et.name as type_name
                FROM element as e
                JOIN element_type as et ON e.type_id = et.id
                """,
                elementRowMapper);
    }

    public Optional<ElementModel> findElement(long id) {
        var result = template.query("""
                SELECT e.id as id,
                       e.name as name,
                       e.level as level,
                       e.score as score,
                       et.id as type_id,
                       et.name as type_name
                FROM element as e
                JOIN element_type as et ON e.type_id = et.id
                WHERE e.id = :id
                """, Map.of("id", id), elementRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public ElementModel updateElement(long id, ElementRequest request) {
        template.update("""
                UPDATE element
                SET name = :name,
                level = :level,
                score = :score,
                type_id = :type_id
                WHERE id = :id
                """, Map.of(
                "id", id,
                "name", request.getName(),
                "level", request.getLevel(),
                "score", request.getScore(),
                "type_id", request.getTypeId()
        ));
        return findElement(id).get();
    }

    public ElementModel insertElement(ElementRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                """
                        INSERT INTO element (
                        name,
                        level,
                        score,
                        type_id
                        )
                        VALUES (
                        :name,
                        :level,
                        :score,
                        :type_id
                        )
                        """,
                new MapSqlParameterSource(Map.of(
                        "name", request.getName(),
                        "level", request.getLevel(),
                        "score", request.getScore(),
                        "type_id", request.getTypeId()
                )),
                keyHolder
        );
        return findElement(keyHolder.getKey().longValue()).get();
    }

    public void deleteElement(long id) {
        template.update("""
                        DELETE FROM element
                        WHERE id = :id
                        """,
                Map.of("id", id));
    }

}
