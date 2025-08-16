package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockitoSpyBean
    private PersonRepository personRepository;

    @MockitoSpyBean
    private PersonMapper personMapper;

    @BeforeEach
    void setUp() {
        var person1 = new Person();
        person1.setName("name1");
        person1.setEmail("email1@email.com");
        var person2 = new Person();
        person2.setName("name2");
        person2.setEmail("email2@email.com");
        personRepository.save(person1);
        personRepository.save(person2);
    }

    @Test
    @DisplayName("Проверка нахождения всех пользователей.")
    void testGetAll_returnsAllPersons() {
        var actual = personService.getAll();

        assertEquals(2, actual.size());
        assertEquals(1, actual.get(0).getId());
        assertEquals("name1", actual.get(0).getName());
        assertEquals("email1@email.com", actual.get(0).getEmail());
        assertEquals(2, actual.get(1).getId());
        assertEquals("name2", actual.get(1).getName());
        assertEquals("email2@email.com", actual.get(1).getEmail());

        verify(personRepository).findAll();
        verify(personMapper, times(2)).toPersonDto(any(Person.class));
    }

    @Test
    @DisplayName("Проверка поиска по идентификатору")
    void testGetById(){
        var person = personService.getById(1L);

        assertEquals("name1", person.getName());
        assertEquals("email1@email.com", person.getEmail());
        verify(personRepository).findById(1L);
        verify(personMapper).toPersonDto(any(Person.class));
    }

    @Test
    @DisplayName("Проверка удаления существующей персоны")
    void testDeleteById_personFound() {
        Long personId = 1L;
        personService.deleteById(personId);
        var exception = assertThrows(ResourceNotFoundException.class, () -> personService.deleteById(personId));
    }

    @Test
    @DisplayName("Проверка удаления НЕ существующей персоны")
    void testDeleteById_personNotFound() {


        var exception = assertThrows(ResourceNotFoundException.class, () -> personService.deleteById(3L));
        assertEquals("Person not found with id: 3", exception.getMessage());
    }
}