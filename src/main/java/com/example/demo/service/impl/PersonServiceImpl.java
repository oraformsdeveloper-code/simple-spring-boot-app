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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonDto create(PersonDto personDto) {
        log.debug("Работает метод create");
        Person person = personMapper.toEntity(personDto);
        person = personRepository.save(person);
        return personMapper.toPersonDto(person);
    }

    @Override
    public PersonDto getById(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        return personMapper.toPersonDto(person);
    }

    @Override
    public List<PersonDto> getAll() {
        List<Person> persons = personRepository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        for (Person person : persons) {
            personDtos.add(personMapper.toPersonDto(person));
        }
        return personDtos;
    }

    @Override
    public void deleteById(Long id) {
        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        personRepository.deleteById(id);
    }

    @Override
    public PersonDto update(PersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personDto.getId()));
        copyFrom(personDto, person);
        person.setUpdatedAt(LocalDateTime.now());
        personRepository.save(person);
        return personMapper.toPersonDto(person);

    }

    private void copyFrom(PersonDto personDto, Person person) {
        Optional.of(personDto.getEmail()).ifPresent(email -> person.setEmail(email));
        Optional.of(personDto.getName()).ifPresent(name -> person.setName(name));

    }
}