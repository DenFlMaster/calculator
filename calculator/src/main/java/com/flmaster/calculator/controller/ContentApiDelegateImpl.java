package com.flmaster.calculator.controller;

import com.flmaster.calculator.api.ContentApiDelegate;
import com.flmaster.calculator.model.Content;
import com.flmaster.calculator.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentApiDelegateImpl implements ContentApiDelegate {
    private final ContentService contentService;
    @Override
    public ResponseEntity<Content> getContent() {
        var result = contentService.getContent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
