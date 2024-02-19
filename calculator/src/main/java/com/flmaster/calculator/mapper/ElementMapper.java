package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.Element;
import com.flmaster.calculator.model.ElementModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ElementTypeMapper.class)
public interface ElementMapper {
    Element convert(ElementModel model);
}