package com.flmaster.calculator.service;

import com.flmaster.calculator.model.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentService {
    private final CategoryService categoryService;
    private final ElementService elementService;
    private final ElementTypeService elementTypeService;
    private final ExerciseService exerciseService;

    public Content getContent(){
        Content result = new Content(
                categoryService.findCategories(),
                elementTypeService.findElementTypes(),
                elementService.findElements(),
                exerciseService.findExercises()
        );
        return result;
    }
}
