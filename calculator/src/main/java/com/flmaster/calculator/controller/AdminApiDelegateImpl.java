package com.flmaster.calculator.controller;

import com.flmaster.calculator.api.AdminApiDelegate;
import com.flmaster.calculator.model.*;
import com.flmaster.calculator.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminApiDelegateImpl implements AdminApiDelegate {
    private final CategoryService categoryService;
    private final ContentService contentService;
    private final ElementService elementService;
    private final ElementTypeService elementTypeService;
    private final ExerciseService exerciseService;
    private final CategoryExerciseRequirementService categoryExerciseRequirementService;

    @Override
    public ResponseEntity<CategoryResponse> adminCreateCategory(CategoryRequest category) {
        var result = categoryService.insertCategory(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<CategoryResponse> adminUpdateCategory(Integer categoryId, CategoryRequest category) {
        var result = categoryService.updateCategory(categoryId, category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
    @Override
    public ResponseEntity<CategoryExerciseRequirementResponse> adminSetExerciseRequirement(
            Integer categoryId,
            Integer exerciseId,
            CategoryExerciseRequirementRequest categoryExerciseRequirement
    ) {

        //pipa popa
        var result = categoryExerciseRequirementService.setCategoryExerciseRequirement(
                categoryId,
                exerciseId,
                categoryExerciseRequirement);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<ElementResponse> adminUpdateElement(Integer exerciseId, ElementRequest element) {
        var result = elementService.updateElement(exerciseId, element);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<ElementTypeResponse> adminCreateElementType(ElementTypeRequest elementType) {
        var result = elementTypeService.insertElementType(elementType);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<ElementTypeResponse> adminUpdateElementType(Integer elementTypeId, ElementTypeRequest elementType) {
        var result = elementTypeService.updateElementType(elementTypeId, elementType);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<ExerciseResponse> adminUpdateExercise(Integer exerciseId, ExerciseRequest exercise) {
        var result = exerciseService.updateExercise(exerciseId, exercise);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> adminGetCategories() {
        var result = categoryService.findCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<List<ElementResponse>> adminGetElements() {
        var result = elementService.findElements();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<List<ElementTypeResponse>> adminGetElementTypes() {
        var result = elementTypeService.findElementTypes();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<List<ExerciseResponse>> adminGetExercises() {
        var result = exerciseService.findExercises();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity<Void> adminCheckAuth() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<Void> adminDeleteCategory(Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<Void> adminDeleteElement(Integer elementId) {
        elementService.deleteElement(elementId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<Void> adminDeleteElementType(Integer elementTypeId) {
        elementTypeService.deleteElementType(elementTypeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<Void> adminDeleteExercise(Integer exerciseId) {
        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<Void> adminDeleteExerciseRequirement(Integer categoryId, Integer exerciseId) {
        categoryExerciseRequirementService.deleteCategoryExerciseRequirement(categoryId, exerciseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @Override
    public ResponseEntity<ElementResponse> adminCreateElement(ElementRequest element) {
        var result = elementService.insertElement(element);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<ExerciseResponse> adminCreateExercise(ExerciseRequest exercise) {
        var result = exerciseService.insertExercise(exercise);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
}
