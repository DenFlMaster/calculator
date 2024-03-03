package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.ElementModel;
import com.flmaster.calculator.model.ElementResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ElementTypeMapper.class)
public interface ElementMapper {
    ElementResponse convert(ElementModel model);
}