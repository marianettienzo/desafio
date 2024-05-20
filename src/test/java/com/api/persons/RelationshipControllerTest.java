package com.api.persons;

import com.api.persons.controller.RelationshipController;
import com.api.persons.enums.RelationshipType;
import com.api.persons.models.Person;
import com.api.persons.responses.RelationshipResponse;
import com.api.persons.services.PersonService;
import com.api.persons.services.RelationshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RelationshipControllerTest {

    @Mock
    private RelationshipService relationshipService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private RelationshipController relationshipController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParentRelationship_ValidInput_ReturnsNoContent() {
        Long primaryId = 1L;
        Long secondId = 2L;
        RelationshipType relationshipType = RelationshipType.PADRE;
        Person primaryPerson = new Person();
        Person secondPerson = new Person();

        when(personService.getPersonById(primaryId)).thenReturn(primaryPerson);
        when(personService.getPersonById(secondId)).thenReturn(secondPerson);

        ResponseEntity<?> responseEntity = relationshipController.createParentRelationship(primaryId, relationshipType, secondId);

        verify(relationshipService).createRelationship(primaryPerson, secondPerson, relationshipType);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void createParentRelationship_InvalidInput_ReturnsBadRequest() {
        Long primaryId = 1L;
        Long secondId = 2L;
        RelationshipType relationshipType = RelationshipType.PADRE;

        when(personService.getPersonById(primaryId)).thenReturn(null);
        when(personService.getPersonById(secondId)).thenReturn(new Person());

        ResponseEntity<?> responseEntity = relationshipController.createParentRelationship(primaryId, relationshipType, secondId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


    @Test
    void getRelationship_ValidInput_ReturnsRelationshipResponse() {
        Long primaryId = 1L;
        Long secondId = 2L;
        Person primaryPerson = new Person();
        Person secondPerson = new Person();
        RelationshipType relationshipType = RelationshipType.HERMANO;

        when(personService.getPersonById(primaryId)).thenReturn(primaryPerson);
        when(personService.getPersonById(secondId)).thenReturn(secondPerson);
        when(relationshipService.getRelationshipType(primaryPerson, secondPerson)).thenReturn(relationshipType);

        ResponseEntity<RelationshipResponse> responseEntity = relationshipController.getRelationship(primaryId, secondId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getRelationship_InvalidInput_ReturnsNotFound() {
        Long primaryId = 1L;
        Long secondId = 2L;

        when(personService.getPersonById(primaryId)).thenReturn(null);
        when(personService.getPersonById(secondId)).thenReturn(null);

        ResponseEntity<RelationshipResponse> responseEntity = relationshipController.getRelationship(primaryId, secondId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteRelationship_ValidInput_ReturnsOk() {
        Long relationshipId = 1L;

        ResponseEntity<?> responseEntity = relationshipController.deleteRelationship(relationshipId);

        verify(relationshipService).deleteRelationship(relationshipId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteRelationship_InvalidInput_ReturnsBadRequest() {
        Long relationshipId = 1L;

        doThrow(new IllegalArgumentException("La relación especificada no existe")).when(relationshipService).deleteRelationship(relationshipId);

        ResponseEntity<?> responseEntity = relationshipController.deleteRelationship(relationshipId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
