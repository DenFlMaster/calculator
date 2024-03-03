package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ElementTypeMapper;
import com.flmaster.calculator.model.ElementTypeRequest;
import com.flmaster.calculator.model.ElementTypeResponse;
import com.flmaster.calculator.repo.ElementTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ElementTypeService {
    private final ElementTypeRepository repo;
    private final ElementTypeMapper mapper;


    private void checkElementTypeName(String name) {
        if (repo.findElementTypeByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.TYPE_NAME_ALREADY_TAKEN);
    }

    public List<ElementTypeResponse> findElementTypes() {
        return repo.findElementTypes().stream().map(mapper::convert).toList();
    }

    private void validateRequest(ElementTypeRequest request) {
        if (!(StringUtils.hasText(request.getName()))) {
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public Optional<ElementTypeResponse> findElementType(long id) {
        return repo.findElementType(id).map(mapper::convert);
    }

    public ElementTypeResponse updateElementType(long id, ElementTypeRequest request) {
        validateRequest(request);
        var elementType = findElementType(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.TYPE_NOT_FOUND));
        if(!Objects.equals(elementType.getName(), request.getName())){
            checkElementTypeName(request.getName());
        }
        return mapper.convert(repo.updateElementType(id, request));
    }

    public ElementTypeResponse insertElementType(ElementTypeRequest request) {
        validateRequest(request);
        checkElementTypeName(request.getName());
        return mapper.convert(repo.insertElementType(request));
    }

    public void deleteElementType(long id) {
        findElementType(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.TYPE_NOT_FOUND));
        repo.deleteElementType(id);
    }

}
