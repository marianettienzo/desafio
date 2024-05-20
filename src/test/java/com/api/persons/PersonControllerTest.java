package com.api.persons;

import com.api.persons.controller.PersonController;
import com.api.persons.models.Person;
import com.api.persons.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersons_ReturnsListOfPersons() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        when(personService.getAllPersons()).thenReturn(persons);

        ResponseEntity<List<Person>> responseEntity = personController.getAllPersons();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(persons, responseEntity.getBody());
    }

    @Test
    void getPersonById_ExistingId_ReturnsPerson() {
        Long id = 1L;
        Person person = new Person();
        when(personService.getPersonById(id)).thenReturn(person);

        ResponseEntity<Person> responseEntity = personController.getPersonById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(person, responseEntity.getBody());
    }

    @Test
    void getPersonById_NonExistingId_ReturnsNotFound() {
        Long id = 1L;
        when(personService.getPersonById(id)).thenReturn(null);

        ResponseEntity<Person> responseEntity = personController.getPersonById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
    }

    @Test
    void createPerson_ReturnsCreatedPerson() {
        Person person = new Person();
        when(personService.createPerson(any())).thenReturn(person);

        ResponseEntity<?> responseEntity = personController.createPerson(person);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(person, responseEntity.getBody());
    }

    @Test
    void updatePerson_ExistingId_ReturnsUpdatedPerson() {
        Long id = 1L;
        Person updatedPerson = new Person();
        when(personService.updatePerson(id, updatedPerson)).thenReturn(updatedPerson);

        ResponseEntity<Person> responseEntity = personController.updatePerson(id, updatedPerson);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPerson, responseEntity.getBody());
    }

    @Test
    void updatePerson_NonExistingId_ReturnsNotFound() {
        Long id = 1L;
        Person updatedPerson = new Person();
        when(personService.updatePerson(id, updatedPerson)).thenReturn(null);

        ResponseEntity<Person> responseEntity = personController.updatePerson(id, updatedPerson);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
    }

    @Test
    void deletePerson_ExistingId_ReturnsNoContent() {
        Long id = 1L;
        when(personService.deletePerson(id)).thenReturn(true);

        ResponseEntity<Void> responseEntity = personController.deletePerson(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
    }

    @Test
    void deletePerson_NonExistingId_ReturnsNotFound() {
        Long id = 1L;
        when(personService.deletePerson(id)).thenReturn(false);

        ResponseEntity<Void> responseEntity = personController.deletePerson(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
    }
}
