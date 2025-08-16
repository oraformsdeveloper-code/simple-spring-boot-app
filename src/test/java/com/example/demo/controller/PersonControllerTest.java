package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

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
        // given
        var response = new PersonDto();
        response.setName("Andrey");
        response.setEmail("andrey@gmail.com");
        response.setUpdatedAt(LocalDateTime.now());
        response.setCreatedAt(LocalDateTime.now());
        doReturn(response).when(personService).create(any(PersonDto.class));

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/person")
                        .content(objectMapper.writeValueAsString(new PersonDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Andrey"))
                .andExpect(jsonPath("$.email").value("andrey@gmail.com"));
    }

    @Test
    @DisplayName("Проверка обновления")
    void testUpdatePerson() throws Exception {
        PersonDto person = new PersonDto();
        person.setId(1L);
        person.setName("Andrey");
        person.setEmail("andrey@gmail.com");

        mvc.perform(MockMvcRequestBuilders
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
