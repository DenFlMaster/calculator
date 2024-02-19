package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ElementTypeMapper;
import com.flmaster.calculator.model.ElementType;
import com.flmaster.calculator.repo.ElementTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public List<ElementType> findElementTypes() {
        return repo.findElementTypes().stream().map(mapper::convert).toList();
    }

    private void validateRequest(ElementType request) {
        if (!(request.getName().length() > 0)) {
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public Optional<ElementType> findElementType(long id) {
        return repo.findElementType(id).map(mapper::convert);
    }

    public ElementType updateElementType(long id, ElementType request) {
        validateRequest(request);
        findElementType(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.TYPE_NOT_FOUND));
        checkElementTypeName(request.getName());
        return mapper.convert(repo.updateElementType(id, request));
    }

    public ElementType insertElementType(ElementType request) {
        validateRequest(request);
        checkElementTypeName(request.getName());
        return mapper.convert(repo.insertElementType(request));
    }

    public void deleteElementType(long id) {
        findElementType(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.TYPE_NOT_FOUND));
        repo.deleteElementType(id);
    }

}
