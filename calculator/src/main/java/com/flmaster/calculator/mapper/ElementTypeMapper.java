package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.ElementTypeModel;
import com.flmaster.calculator.model.ElementTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ElementTypeMapper {
    ElementTypeResponse convert(ElementTypeModel model);
}
