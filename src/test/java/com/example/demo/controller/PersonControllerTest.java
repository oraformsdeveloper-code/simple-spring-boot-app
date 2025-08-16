package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PersonControllerTest {

    @Autowired
    MockMvc mvc;

    //@MockitoBean
    @Autowired
    private PersonService personService;

    @MockitoSpyBean
    private PersonRepository personRepository;

    @MockitoSpyBean
    private PersonMapper personMapper;


    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        var person1 = new Person();
        person1.setName("name1");
        person1.setEmail("email1@email.com");
        personRepository.save(person1);

        var person2 = new Person();
        person2.setName("name2");
        person2.setEmail("email2@email.com");
        personRepository.save(person2);
    }


    @Test
    @DisplayName("Проверка поиска по идентификатору для существующей записи")
    void testGetPersonById() throws Exception {
        mvc.perform(get("/api/v1/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.email").value("email1@email.com"));
    }

    @Test
    @DisplayName("Проверка поиска по идентификатору для не существующей записи")
    void testGetPersonByIdNotFound() throws Exception {
        mvc.perform(get("/api/v1/person/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Проверка поиска всех персон")
    void testGetAllPersons() throws Exception {
        mvc.perform(get("/api/v1/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Проверка создания")
    void testCreatePerson() throws Exception {
        PersonDto person = new PersonDto();
        person.setName("Andrey");
        person.setEmail("andrey@gmail.com");

        mvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/person")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Вопрос 1
        // Как получить идентификатор созданной записи

        // Вопрос 2
        // Нужно ли в методе создания возвращать созданный объект?

        // Вопрос 3
        // Достаточно ли проверить статус ответа или нужно в тесте проверить, что появилась такая запись?

        // Вопрос 4
        // Как по хорошему предзаполнять базу для тестов

        // Вопрос 5
        // Как отключать тесты


    }

    @Test
    @DisplayName("Проверка обновления")
    void testUpdatePerson() throws Exception {
        PersonDto person = new PersonDto();
        person.setId(1L);
        person.setName("Andrey");
        person.setEmail("andrey@gmail.com");

        mvc.perform( MockMvcRequestBuilders
                        .put("/api/v1/person")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Andrey"))
                .andExpect(jsonPath("$.email").value("andrey@gmail.com"));
    }


    @Test
    @DisplayName("Проверка удаления существующего")
    void testDeletePerson() throws Exception {
        mvc.perform(delete("/api/v1/person/1"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Проверка удаления НЕ существующего")
    void testDeletePersonNotFound() throws Exception {
        mvc.perform(delete("/api/v1/person/3"))
                .andExpect(status().isNotFound());

    }



}
