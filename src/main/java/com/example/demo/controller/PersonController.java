package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody PersonDto personDto) {
        log.info(">> Получили объект: {}", personDto);
        personService.create(personDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable long id) {
        PersonDto person = personService.getById(id);
        log.info(">> Получили объект: {}", person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll() {
        List<PersonDto> persons = personService.getAl();
        return ResponseEntity.ok(persons);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        personService.deleteById(id);
        log.info(">> Удалили с идентификатором: {}", id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable long id, @RequestBody PersonDto personDto) {
        log.info(">> Получили объект: {}", personDto);
        PersonDto person  = personService.getById(id);
        if (personDto.getEmail() != null) {
            person.setEmail(personDto.getEmail());
        }
        if (personDto.getName() != null) {
            person.setName(personDto.getName());
        }
        personService.update(person);
        log.info(">> После обновления: {}", personDto);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    // TODO: getAll, getById, updatePerson, deleteById
}
