package com.flmaster.calculator.rowmapper;


import com.flmaster.calculator.model.ElementTypeModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ElementTypeRowMapper implements RowMapper<ElementTypeModel> {
    @Override
    public ElementTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ElementTypeModel(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
