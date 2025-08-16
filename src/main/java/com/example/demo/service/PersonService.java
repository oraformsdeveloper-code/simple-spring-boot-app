package com.example.demo.service;

import com.example.demo.dto.PersonDto;

import java.util.List;

public interface PersonService {

    void create(PersonDto personDto);

    PersonDto getById(Long id);

    List<PersonDto> getAll();

    void deleteById(Long id);

    PersonDto update(PersonDto person);
}
