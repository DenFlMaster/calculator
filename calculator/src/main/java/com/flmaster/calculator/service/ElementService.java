package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ElementMapper;
import com.flmaster.calculator.model.Element;
import com.flmaster.calculator.repo.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ElementService {
    private final ElementRepository repo;
    private final ElementMapper mapper;

    public List<Element> findElements() {
        return repo.findElements().stream().map(mapper::convert).toList();
    }

    public Optional<Element> findElement(long id) {
        return repo.findElement(id).map(mapper::convert);
    }

    private void validateRequest(Element request) {
        if (!(request.getName().length() > 0
                && request.getLevel() >= 0
                && request.getScore() > 0)) {
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public void checkElementName(String name) {
        if (repo.findElementByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.ELEMENT_NAME_ALREADY_TAKEN);
    }

    public Element updateElement(long id, Element request) {
        validateRequest(request);
        findElement(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.ELEMENT_NOT_FOUND));
        checkElementName(request.getName());
        return mapper.convert(repo.updateElement(id, request));
    }

    public Element insertElement(Element request) {
        validateRequest(request);
        checkElementName(request.getName());
        return mapper.convert(repo.insertElement(request));
    }

    public void deleteElement(long id) {
        findElement(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.ELEMENT_NOT_FOUND));
        repo.deleteElement(id);
    }

}
