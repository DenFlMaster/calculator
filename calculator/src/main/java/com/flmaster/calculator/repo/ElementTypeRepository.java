package com.flmaster.calculator.repo;


import com.flmaster.calculator.model.ElementTypeModel;
import com.flmaster.calculator.model.ElementTypeRequest;
import com.flmaster.calculator.rowmapper.ElementTypeRowMapper;
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

@RequiredArgsConstructor
@Component
public class ElementTypeRepository {
    private final ElementTypeRowMapper elementTypeRowMapper;
    private final NamedParameterJdbcTemplate template;

    public List<ElementTypeModel> findElementTypes() {
        return template.query("""
                SELECT name, id
                FROM element_type
                """, elementTypeRowMapper);
    }

    public Optional<ElementTypeModel> findElementTypeByName(String name){
        List<ElementTypeModel> result = template.query("""
                SELECT name, id FROM element_type
                WHERE name = :name
                """, Map.of("name", name), elementTypeRowMapper);

        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public Optional<ElementTypeModel> findElementType(long id) {
        var result = template.query("""
                SELECT name, id FROM element_type
                WHERE id = :id
                """, Map.of("id", id), elementTypeRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(result));
    }

    public ElementTypeModel updateElementType(long id, ElementTypeRequest request) {
        template.update("""
                UPDATE element_type
                SET name = :name
                WHERE id = :id
                """, Map.of("id", id, "name", request.getName()));
        return findElementType(id).get();
    }

    public ElementTypeModel insertElementType(ElementTypeRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update("""
                        INSERT INTO element_type (name)
                        VALUES (:name)
                        """,
                new MapSqlParameterSource(Map.of("name", request.getName())),
                keyHolder
        );
        return findElementType(keyHolder.getKey().longValue()).get();
    }

    public void deleteElementType(long id) {
        template.update("""
                DELETE FROM element_type
                WHERE id = :id
                """, Map.of("id", id));
    }


}
