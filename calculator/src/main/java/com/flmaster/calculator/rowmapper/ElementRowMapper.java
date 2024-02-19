package com.flmaster.calculator.rowmapper;

import com.flmaster.calculator.model.ElementModel;
import com.flmaster.calculator.model.ElementTypeModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ElementRowMapper implements RowMapper<ElementModel> {
    @Override
    public ElementModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ElementModel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("level"),
                rs.getInt("score"),
                new ElementTypeModel(
                        rs.getInt("type_id"),
                        rs.getString("type_name")
                )
        );
    }
}
