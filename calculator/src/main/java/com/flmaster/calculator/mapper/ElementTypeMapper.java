package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.ElementType;
import com.flmaster.calculator.model.ElementTypeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ElementTypeMapper {
    ElementType convert(ElementTypeModel model);
}
