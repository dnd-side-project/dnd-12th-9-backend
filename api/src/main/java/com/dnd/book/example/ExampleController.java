package com.dnd.book.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping
    public ResponseEntity<String> find() {
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam String name) {
        exampleService.save(name);
        return ResponseEntity
                .ok("저장 성공");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity
                .ok(exampleService.get(id));
    }

}
