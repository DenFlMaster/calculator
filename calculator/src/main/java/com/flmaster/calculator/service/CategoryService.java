package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.CategoryMapper;
import com.flmaster.calculator.model.Category;
import com.flmaster.calculator.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    private void checkCategoryName(String name) {
        if (repo.findCategoryByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.CATEGORY_NAME_ALREADY_TAKEN);
    }

    private void validateRequest(Category request) {
        if (!(request.getName().length() > 0
                && request.getLevel() >= 0
                && request.getRequirements().getMinCombo() > 0
                && request.getRequirements().getMinElementsOfSameLevel() > 0
                && request.getRequirements().getMinFemaleScore() > 0
                && request.getRequirements().getMinMaleScore() > 0)) {
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public List<Category> findCategories() {
        return repo.findCategories().stream().map(mapper::convert).toList();
    }

    public Optional<Category> findCategory(long id) {
        return repo.findCategory(id).map(mapper::convert);
    }

    public Category updateCategory(long id, Category request) {
        validateRequest(request);
        findCategory(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.CATEGORY_NOT_FOUND));
        checkCategoryName(request.getName());
        return mapper.convert(repo.updateCategory(id, request));
    }

    public Category insertCategory(Category request) {
        validateRequest(request);
        checkCategoryName(request.getName());
        return mapper.convert(repo.insertCategory(request));
    }

    public void deleteCategory(long id) {
        findCategory(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.CATEGORY_NOT_FOUND));
        repo.deleteCategory(id);
    }
}
