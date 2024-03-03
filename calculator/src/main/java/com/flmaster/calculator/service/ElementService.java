package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ElementMapper;
import com.flmaster.calculator.model.ElementRequest;
import com.flmaster.calculator.model.ElementResponse;
import com.flmaster.calculator.repo.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ElementService {
    private final ElementRepository repo;
    private final ElementMapper mapper;

    public List<ElementResponse> findElements() {
        return repo.findElements().stream().map(mapper::convert).toList();
    }

    public Optional<ElementResponse> findElement(long id) {
        return repo.findElement(id).map(mapper::convert);
    }

    private void validateRequest(ElementRequest request) {
        if (!(StringUtils.hasText(request.getName())
                && request.getLevel() >= 0
                && request.getScore() > 0)) {
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public void checkElementName(String name) {
        if (repo.findElementByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.ELEMENT_NAME_ALREADY_TAKEN);
    }

    public ElementResponse updateElement(long id, ElementRequest request) {
        validateRequest(request);
        var element = findElement(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.ELEMENT_NOT_FOUND));
        if (!Objects.equals(element.getName(), request.getName())) {
            checkElementName(request.getName());
        }
        return mapper.convert(repo.updateElement(id, request));
    }

    public ElementResponse insertElement(ElementRequest request) {
        validateRequest(request);
        checkElementName(request.getName());
        return mapper.convert(repo.insertElement(request));
    }

    public void deleteElement(Integer id) {
        findElement(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.ELEMENT_NOT_FOUND));
        repo.deleteElement(id);
    }

}
