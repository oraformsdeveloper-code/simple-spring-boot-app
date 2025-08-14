package com.example.demo.service.impl;

import com.example.demo.dto.PersonDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public void create(PersonDto personDto) {
        log.info("Работает метод create");
        Person person = personMapper.toEntity(personDto);
        personRepository.save(person);
    }

    @Override
    public PersonDto getById(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        PersonDto personDto = personMapper.toPersonDto(person);

        return personDto;
    }

    @Override
    public List<PersonDto> getAl() {
        List<Person> persons = personRepository.findAll();
        List<PersonDto> personDtos= new ArrayList<>();
        for(Person person: persons) {
            personDtos.add(personMapper.toPersonDto(person));
        }
        return personDtos;
    }

    @Override
    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        personRepository.deleteById(id);
    }

    @Override
    public PersonDto update(PersonDto personDto) {
        Person person = personMapper.toEntity(personDto);
        personRepository.save(person);
        person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personDto.getId()));
        return personMapper.toPersonDto(person);

    }
}
