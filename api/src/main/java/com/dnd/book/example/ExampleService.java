package com.dnd.book.example;

import com.dnd.ExampleEntity;
import com.dnd.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public void save(String name) {
        exampleRepository.save(new ExampleEntity(name));
    }

    @Transactional(readOnly = true)
    public String get(Long id) {
        ExampleEntity example = exampleRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return example.getName();
    }
}