package com.api.persons.controller;

import com.api.persons.enums.RelationshipType;
import com.api.persons.models.Person;
import com.api.persons.responses.RelationshipResponse;
import com.api.persons.services.PersonService;
import com.api.persons.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private PersonService personService;

    @PostMapping("/personas/{primaryId}/{secondId}")
    public ResponseEntity<?> createParentRelationship(@PathVariable Long primaryId, @RequestParam RelationshipType relationshipType, @PathVariable Long secondId) {
        try {
            Person primaryPerson = personService.getPersonById(primaryId);
            Person secondPerson = personService.getPersonById(secondId);
            if (primaryPerson == null || secondPerson == null) {
                throw new IllegalArgumentException("Una o ambas personas no existen en el sistema");
            }
            relationshipService.createRelationship(primaryPerson, secondPerson, relationshipType);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/relaciones/{primaryId}/{secondId}")
    public ResponseEntity<RelationshipResponse> getRelationship(@PathVariable Long primaryId, @PathVariable Long secondId) {
        Person firstPerson = personService.getPersonById(primaryId);
        Person secondPerson = personService.getPersonById(secondId);
        if (firstPerson == null || secondPerson == null) {
            return ResponseEntity.notFound().build();
        }
        RelationshipType relationshipType = relationshipService.getRelationshipType(firstPerson, secondPerson);

        String relationshipMessage = String.format("%s es %s de %s",
                firstPerson.getName(), relationshipType.name(), secondPerson.getName());
        RelationshipResponse response = new RelationshipResponse(relationshipMessage);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/relaciones/{relationshipId}")
    public ResponseEntity<?> deleteRelationship(@PathVariable Long relationshipId) {
        try {
            relationshipService.deleteRelationship(relationshipId);
            return ResponseEntity.ok("Relación eliminada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
